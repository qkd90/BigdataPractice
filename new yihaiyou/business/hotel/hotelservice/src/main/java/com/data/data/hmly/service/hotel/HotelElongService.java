package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.common.AreaRelationService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.dao.ProductimageDao;
import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.GuaranteeRule;
import com.data.data.hmly.service.elong.pojo.HotelIDListCondition;
import com.data.data.hmly.service.elong.pojo.HotelIdListResult;
import com.data.data.hmly.service.elong.pojo.statics.hotelDetail.DetailType;
import com.data.data.hmly.service.elong.pojo.statics.hotelDetail.FacilitiesV2Type;
import com.data.data.hmly.service.elong.pojo.statics.hotelDetail.HotelType;
import com.data.data.hmly.service.elong.pojo.statics.hotelDetail.ImageType;
import com.data.data.hmly.service.elong.service.result.HotelDetail;
import com.data.data.hmly.service.elong.service.result.HotelDetail.ResultEntity.HotelsEntity.BookingRulesEntity;
import com.data.data.hmly.service.elong.util.Tool;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.dao.HotelElongStaticInfoDao;
import com.data.data.hmly.service.hotel.dao.HotelPriceCalendarDao;
import com.data.data.hmly.service.hotel.dao.HotelPriceDao;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelExtend;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.request.HotelPriceRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HotelElongService extends SolrIndexService<Hotel, HotelSolrEntity> {
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private HotelExtendService hotelExtendService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private AreaRelationService areaRelationService;
    @Resource
    private ProductimageDao productimageDao;
    @Resource
    private HotelPriceCalendarDao hotelPriceCalendarDao;
    @Resource
    private HotelPriceDao hotelPriceDao;
    @Resource
    private HotelElongStaticInfoDao hotelElongStaticInfoDao;
    @Resource
    PropertiesManager propertiesManager;

    private final Log log = LogFactory.getLog(HotelElongService.class);
    private final int threadNum = 20;


    public void doElongToLxbHotel(final Map<String, String> ids) {
        final Map<Integer, Long> areaIds = new HashMap<Integer, Long>();
        List<AreaRelation> areaRelations = areaRelationService.findAll();
        for (AreaRelation areaRelation : areaRelations) {
            if (areaRelation.getElongHotelCity() != null)
                areaIds.put(areaRelation.getElongHotelCity(), areaRelation.getId());
        }
        final Semaphore sem = new Semaphore(threadNum);
        final CountDownLatch down = new CountDownLatch(ids.size());
        for (final String id : ids.keySet()) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GlobalTheadPool.instance.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return runCall(id, areaIds, down, sem, ids.get(id));
                }
            });
        }
        try {
            down.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Object runCall(String id, Map<Integer, Long> areaIds, CountDownLatch down, Semaphore sem, String line) {
        try {
            SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            doElongToHotel(id, areaIds, line);
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            down.countDown();
            sem.release();
        }
        return null;
    }

    /**
     * 酒店索引
     */
    public void indexHotel() {
        Long count = hotelService.countHotel();
        int pageIndex = 1;
        final int pageSize = 20;
        int pageCount = count.intValue() / pageSize;
        if (count.intValue() % pageSize != 0) {
            pageCount = pageCount + 1;
        }
        final Semaphore sem = new Semaphore(threadNum);
        final CountDownLatch down = new CountDownLatch(pageCount);
        log.error(">> update elong hotel index count=" + count + ", pageCount=" + pageCount + ", pageSize=" + pageSize);
        while (pageIndex <= pageCount) {    // 如果不止一页，循环更新
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int index = pageIndex;
            GlobalTheadPool.instance.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return runIndex(index, pageSize, down, sem);
                }
            });
            pageIndex++;
        }
        try {
            down.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 酒店索引线程
     * @return
     */
    private Object runIndex(int pageIndex, int pageSize, CountDownLatch down, Semaphore sem) {
        try {
            SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);

            List<Hotel> list = hotelService.listByIdRange(pageIndex, pageSize);
            for (final Hotel hotel : list) {
                hotelService.indexHotel(hotel);
            }

            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            down.countDown();
            sem.release();
        }
        return null;
    }

    public boolean doElongToHotel(String id, Map<Integer, Long> areaIds, String line) {
        Hotel hotel = hotelService.getByTargetId(Long.parseLong(id));
        HotelType elongHotel = elongHotelService.getStaticsHotelDetail(id);
        if (elongHotel == null || elongHotel.getDetail() == null) {   // 不存在或者获取有问题时，更新状态为DEL
            log.error("request hotel(hotelId=" + id + ") detail is null.");
            if (hotel != null) {    // 艺龙酒店已经不存在，设置删除状态
                hotel.setStatus(ProductStatus.DEL);
                hotel.setUpdateTime(new Date());
                hotel.setSourceStatus(Boolean.TRUE);
                hotelService.updateTransactional(hotel);
            }
            return true;
        }
        String elongCityId = elongHotel.getDetail().getCityId();

        Long areaId = null;
        if (StringUtils.isNotBlank(elongCityId) && areaIds.get(Integer.parseInt(elongCityId)) != null) {
            areaId = areaIds.get(Integer.parseInt(elongCityId));
            doElongToHotel(elongHotel, areaId, line, hotel);
            info("end:" + areaId + "\t" + id);
        } else {
            log.error(String.format("(hotelId=%s)elongCityId %s AreaId %d is Not Match", id, elongCityId, areaId));
        }
        return false;
    }

    public void doElongToLxbHotel() {
        Map<String, String> ids = new HashMap<String, String>();
        Integer elongCity = propertiesManager.getInteger("ELONG_CITY");
        if (elongCity == null) {    // 艺龙所有城市酒店
            ids = elongHotelService.getStaticsHotelList(new Date().getTime(), false);
        } else {    // 区域酒店，本地酒店静态数据必须已同步（艺龙城市酒店只能查询某时间段可入住的酒店）
            ids = hotelElongStaticInfoDao.findElongHotelIdList(elongCity);
        }
        doElongToLxbHotel(ids);
    }

    public void doElongToLxbHotel(Date date) {
        Map<String, String> ids = elongHotelService.getStaticsHotelList(date.getTime(), true);
        doElongToLxbHotel(ids);
    }

    private List<Productimage> getProductImageList(Hotel hotel, List<ImageType> ctripHotelImgList) {
        List<Productimage> productimageList = new ArrayList<Productimage>();
        for (ImageType ctripHotelImg : ctripHotelImgList) {
            Productimage productimage = new Productimage();
            productimage.setProduct(hotel);
            productimage.setProType(ProductType.hotel);
            productimage.setStatus(ProductStatus.UP);   // 酒店图片状态
            productimage.setPath(ctripHotelImg.getLocations().getLocation().get(0).getValue());
            productimage.setCoverFlag(false);
            productimage.setUserId(hotel.getUser().getId());
            productimage.setCompanyUnitId(hotel.getCompanyUnit().getId());
            productimage.setCreateTime(new Date());
            productimage.setShowOrder(999); // 默认排序值
            productimageList.add(productimage);
        }
        return productimageList;
    }

    private Hotel doElongToHotel(HotelType elongHotel, long areaId, String line, Hotel hotel) {
        Boolean newFlag = true;
//        Hotel hotel = hotelService.getByTargetId(Long.parseLong(elongHotel.getId()));
        HotelExtend hotelExtend = null;
        if (hotel == null) {
            hotel = new Hotel();
            hotel.setNeedConfirm(false);
            hotel.setCreateTime(new Date());
            hotel.setUpdateTime(new Date());
        } else {
            newFlag = false;
            hotel.setUpdateTime(new Date());
        }
        hotel.setSourceStatus(Boolean.FALSE);
        hotelExtend = new HotelExtend();
        if (hotel.getExtend() != null) {
            hotelExtend = hotel.getExtend();
        }
        hotelExtend = fillHotelExtend(elongHotel, hotel, hotelExtend, areaId);
        if (getHotelStatus(line)) {
            hotel.setStatus(ProductStatus.UP);
        } else {
            hotel.setStatus(ProductStatus.DOWN);
        }
        Boolean newExtend = false;
        if (hotelExtend.getId() == null) {
            newExtend = true;
            hotelExtend.setId(hotel.getId());
        }
        if (newFlag) {
            hotelService.saveTransactional(hotel);
        } else {
            hotelService.updateTransactional(hotel);
        }
        hotel.setTopProduct(hotel);
        saveExtend(hotel, hotelExtend, newExtend);
        hotel.setExtend(hotelExtend);

        // update img
        if (!newFlag) { // 删除旧图片
            productimageDao.delBy(hotel.getId(), null);
        }
        if (elongHotel.getImages() != null) {
            List<ImageType> ctripHotelImgList = elongHotel.getImages().getImage();
            List<Productimage> productImageList = getProductImageList(hotel, ctripHotelImgList);
            if (!productImageList.isEmpty()) {
                // select the forst img as cover
                productImageList.get(0).setCoverFlag(true);
                hotel.setCover(productImageList.get(0).getPath());
                productimageService.saveAll(productImageList);
            }
        }
        // update prices
        doGetAndSavePrices(elongHotel.getId(), hotel);
        hotelService.updateTransactional(hotel);
//        hotelService.indexHotel(hotel);
        return hotel;
    }

    private void info(String s) {
        log.info(s);
    }

    private void doGetAndSavePrices(String elongId, Hotel hotel) {
        hotel.setUpdateTime(new Date());
        if (hotel.getStatus() != ProductStatus.UP) {    // 状态不为UP，直接返回
            hotel.setPrice(0f);
            hotel.setSourceStatus(Boolean.TRUE);
            return;
        }
        List<HotelPrice> hotelPriceList = new ArrayList<HotelPrice>();
        List<HotelPriceCalendar> hotelPriceCalendarList = new ArrayList<HotelPriceCalendar>();
        // 酒店信息判断
        Date date = new Date();
        String dateStr = DateUtils.format(date, "yyyyMMdd");
        Date arriveDate = Tool.addDate(DateUtils.getDate(dateStr, "yyyyMMdd"), 1);
        Date departureDate = Tool.addDate(DateUtils.getDate(dateStr + " 23:59:59", "yyyyMMdd HH:mm:ss"), 30);  // 加载30天数据
        HotelDetail hotelDetail = elongHotelService.getHotelDetail(Long.parseLong(elongId), arriveDate, departureDate);
        if (hotelDetail == null || hotelDetail.getResult() == null) {
            log.warn("request hotel(hotelId=" + elongId + ") price detail is null. errorInfo:"+ hotelDetail.getCode() != null ? hotelDetail.getCode(): "");
            float minPrice = getMinHotelPrice(hotel, null);
            hotel.setPrice((float) Math.round(minPrice));   // 索引时要根据状态和价格来过滤
            return;
        }
        // 数据格式不正确
        if (hotelDetail.getResult().getHotels() == null || hotelDetail.getResult().getHotels().get(0) == null) {
            hotel.setPrice(0f);
            hotel.setStatus(ProductStatus.DOWN);
            hotel.setSourceStatus(Boolean.TRUE);
            return;
        }
        // 数据格式不正确
        HotelDetail.ResultEntity.HotelsEntity hotelsEntity = hotelDetail.getResult().getHotels().get(0);
        if (hotelsEntity.getRooms() == null || hotelsEntity.getRooms().isEmpty()) {
            hotel.setPrice(0f);
            hotel.setStatus(ProductStatus.DOWN);
            hotel.setSourceStatus(Boolean.TRUE);
            return;
        }
        // 查询已经存在的酒店价格
        List<HotelPrice> exists = hotelPriceService.findByHotel(hotel);
        Map<String, HotelPrice> existIds = new HashMap<>();
        for (HotelPrice p : exists) {
            existIds.put(p.getRatePlanCode(), p);
            // 删除旧天价格
            hotelPriceCalendarDao.delByPriceIdAndDate(p.getId(), arriveDate, departureDate);
        }
        // 房型信息价格处理
        float minhotelPrice = 0f;
        List<HotelDetail.ResultEntity.HotelsEntity.RoomsEntity> roomsEntityList = hotelsEntity.getRooms();
        for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity room : roomsEntityList) {    // 房型
            if (room.getRatePlans() == null || room.getRatePlans().isEmpty()) {
                continue;
            }
            Float minPrice = hotelPlan(hotel, existIds, room, hotelPriceList, hotelPriceCalendarList);
            if (minPrice != null && (minhotelPrice <= 0 || minPrice < minhotelPrice)) {
                minhotelPrice = minPrice;
            }
        }

        hotel.setPrice((float) Math.round(minhotelPrice));
        hotel.setUpdateTime(new Date());
        hotel.setSourceStatus(Boolean.TRUE);
        if (!hotelPriceList.isEmpty()) {
            hotelPriceService.saveAll(hotelPriceList);
            if (!hotelPriceCalendarList.isEmpty()) {
                hotelPriceCalendarDao.save(hotelPriceCalendarList);
            }
        } else {    // 确实取不到价格
            hotel.setStatus(ProductStatus.DOWN);
        }
    }

    /**
     * 获取酒店最小价格
     * 查询是否存在一定时间内的房型价格，存在更新酒店价格为最小值，不存在直接更新为0
     * @param hotel
     */
    public float getMinHotelPrice(Hotel hotel, Long hotelPriceId) {
        float minPrice = 0f;
        Date endDate = new Date();
        String endDateStr = DateUtils.format(endDate, "yyyyMMdd");
//        endDate = Tool.addDate(DateUtils.getDate(endDateStr, "yyyyMMdd"), 7);   // 判断是否有大于7天的价格数据
        Float minValue = hotelPriceCalendarDao.findMinValue(hotel.getId(), hotelPriceId, endDate);
        if (minValue != null) {
           minPrice = minValue;
        }
        return minPrice;
    }

    /**
     * 构建酒店房型价格和天价格并返回第一个有价格数据的价格作为酒店价格
     * @param hotel
     * @param existIds
     * @param room
     * @param hotelPriceList
     * @param hotelPriceCalendarList
     * @return
     */
    private Float hotelPlan(Hotel hotel, Map<String, HotelPrice> existIds, HotelDetail.ResultEntity.HotelsEntity.RoomsEntity room,
                           List<HotelPrice> hotelPriceList, List<HotelPriceCalendar> hotelPriceCalendarList) {
        Float minPrice = null;
        for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity.RatePlansEntity ratePlan : room.getRatePlans()) {    // 计划
            if (ratePlan.getNightlyRates() == null || ratePlan.getNightlyRates().isEmpty()) {
                continue;
            }
            // 酒店价格
            HotelPrice hotelPrice = existIds.get(ratePlan.getRatePlanId() + "-" + room.getRoomId());
            if (hotelPrice == null) {
                hotelPrice = new HotelPrice();
                hotelPrice.setCreateTime(new Date());
                hotelPrice.setModifyTime(new Date());
            } else {
                hotelPrice.setModifyTime(new Date());
            }
            Date start = null;
            Date end = null;
            // 天价格
            for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity.RatePlansEntity.NightlyRatesEntity nightlyRate : ratePlan.getNightlyRates()) {
                if (nightlyRate.getMember() <= 0) { // 过滤掉价格为-1（不可销售）
                    continue;
                }
                Date nightlyRateDate = DateUtils.getDate(nightlyRate.getDate(), "yyyy-MM-dd'T'HH:mm:ss");
                if (start == null || start.compareTo(nightlyRateDate) > 0) {
                    start = nightlyRateDate;
                }
                if (end == null || end.compareTo(nightlyRateDate) < 0) {
                    end = nightlyRateDate;
                }
                // 保存天价格
                HotelPriceCalendar c = new HotelPriceCalendar();
                c.setHotelPrice(hotelPrice);
                c.setHotelId(hotel.getId());
                c.setCreateTime(new Date());
                c.setDate(nightlyRateDate);
                c.setMember((float) Math.round(nightlyRate.getMember()));
                c.setCost((float) Math.round(nightlyRate.getCost()));
                c.setAddBed((float) Math.round(nightlyRate.getAddBed()));
                c.setStatus(nightlyRate.isStatus());
                hotelPriceCalendarList.add(c);
                // 取天内价格最小值
                if (minPrice == null || minPrice > c.getMember()) {
                    minPrice = c.getMember();
                }
            }
//            if (start == null || end == null) { // 没有天价格直接返回
//                continue;
//            }
            hotelPrice.setHotel(hotel);
            hotelPrice.setRoomId(room.getRoomId());
            hotelPrice.setPrice((float) Math.round(ratePlan.getAverageRate()));
            hotelPrice.setRoomTypeId(ratePlan.getRoomTypeId());
            if (ratePlan.getRatePlanName().contains("不含早")) {
                hotelPrice.setBreakfast(false);
            } else {
                hotelPrice.setBreakfast(true);
            }
            hotelPrice.setRatePlanCode(ratePlan.getRatePlanId() + "-" + room.getRoomId());
            if (ratePlan.getGuaranteeRuleIds().isEmpty()) {
                hotelPrice.setStatus(PriceStatus.UP);
            } else {
                hotelPrice.setStatus(PriceStatus.GUARANTEE);
            }
            hotelPrice.setRoomName(room.getName());
            hotelPrice.setRoomDescription(room.getDescription());
            hotelPrice.setCapacity(calCapacity(room.getDescription(), room.getName()));   // 可容纳人数
            // 如果已有开始日期，就不再做更新，保证有当天酒店价格数据
            if (hotelPrice.getStart() == null) {
                hotelPrice.setStart(start);
            }
            hotelPrice.setEnd(end);
            hotelPriceList.add(hotelPrice);
            // 取日均价最小值
//            if (minPrice == null || minPrice > hotelPrice.getPrice()) {
//                minPrice = hotelPrice.getPrice();
//            }
        }
        return minPrice;
    }

    /**
     * 根据描述和名称计算可容纳人数
     * @return
     */
    private Integer calCapacity(String description, String name) {
        Integer capacity = null;
        // 先从描述解析
        if (StringUtils.isNotBlank(description)) {
            Pattern p = Pattern.compile("可入住(\\d+)人");
            Matcher m = p.matcher(description);
            if (m.find()) {
                String g = m.group(1);
                capacity = Integer.valueOf(g);
            }
        }
        // 描述未满足时，根据名称解析：单人间或有单字的为1人，三人间的为3人，其他的默认2人；7表示6人以上；
        if (capacity == null && StringUtils.isNotBlank(name)) {
            if ("单".indexOf(name) > -1) {
                capacity = 1;
            } else if ("三".indexOf(name) > -1) {
                capacity = 3;
            } else if ("7".indexOf(name) > -1) {
                capacity = 7;
            } else {
                capacity = 2;
            }
        }
        return capacity;
    }

    private void saveExtend(Hotel hotel, HotelExtend hotelExtend, Boolean newExtend) {
        // extend info
        if (newExtend) {
            hotelExtend.setId(hotel.getId());
            hotelExtendService.save(hotelExtend);
        } else {
            hotelExtendService.update(hotelExtend);
        }
    }

    private HotelExtend fillHotelExtend(HotelType elongHotel, Hotel hotel, HotelExtend hotelExtend, long areaId) {
        DetailType detail = elongHotel.getDetail();
        if (detail == null)
            return null;
        if (detail != null) {
            hotel.setName(detail.getName());
            hotel.setShortDesc(detail.getDescription());
            hotelExtend.setDescription(detail.getIntroEditor());
            hotelExtend.setAddress(detail.getAddress());
            if (detail.getStarRate() != null)
                hotel.setStar(Integer.parseInt(detail.getStarRate()));
            setLngLat(detail, hotelExtend);
        }
        hotel.setStatus(ProductStatus.UP);
        SysUser sysUser = new SysUser();
        sysUser.setId(1L);
        hotel.setUser(sysUser);
        SysUnit sysUnit = new SysUnit();
        sysUnit.setId(1L);
        hotel.setCompanyUnit(sysUnit);
        hotel.setProType(ProductType.hotel);
        hotel.setCityId(areaId);
        hotel.setSupplier(sysUser);
        hotel.setSource(ProductSource.ELONG);
        hotel.setSourceId(Long.parseLong(elongHotel.getId()));

        hotel.setGroupId(Integer.parseInt(detail.getGroupId()));
        hotel.setBrandId(Integer.parseInt(detail.getBrandId()));

        hotel.setFacilities(detail.getFacilities());
        FacilitiesV2Type facilitiesV2 = detail.getFacilitiesV2();
        if (facilitiesV2 != null) {
            hotel.setGeneralAmenities(facilitiesV2.getGeneralAmenities());
            hotel.setRecreationAmenities(facilitiesV2.getRecreationAmenities());
            hotel.setServiceAmenities(facilitiesV2.getServiceAmenities());
        }
        hotel.setTheme(detail.getThemes());

        hotel.setScore(80f);
        if (elongHotel.getReview() != null && StringUtils.isNotBlank(elongHotel.getReview().getScore())) {
            String score = elongHotel.getReview().getScore();
            score = score.replace("%", "");
            if (StringUtils.isNotBlank(score)) {
                hotel.setScore(Float.parseFloat(score));
            }
        }
        hotel.setTargetId(Long.parseLong(elongHotel.getId()));

        hotelExtend.setHotel(hotel);
        return hotelExtend;
    }
    /**
     * 经纬度：以正确经纬度为准，如果百度和谷歌存在较大误差，以谷歌为准
     * 百度非大陆坐标出现经纬度互换问题（可能是百度或艺龙的问题）
     * 百度或谷歌经纬度都有可能存在问题，选择比较正确的经纬度
     * */
    private void setLngLat(DetailType detail, HotelExtend hotelExtend) {
        // 如果百度格式不正确，以谷歌为准
        if (!checkLng(detail.getBaiduLon()) && checkLng(detail.getGoogleLon())
                || !checkLat(detail.getBaiduLat()) && checkLat(detail.getGoogleLat())) {
            hotelExtend.setLongitude(Double.parseDouble(detail.getGoogleLon()));
            hotelExtend.setLatitude(Double.parseDouble(detail.getGoogleLat()));
        } else {
            if (StringUtils.isNotBlank(detail.getBaiduLon())) {
                hotelExtend.setLongitude(Double.parseDouble(detail.getBaiduLon()));
            }
            if (StringUtils.isNotBlank(detail.getBaiduLat())) {
                hotelExtend.setLatitude(Double.parseDouble(detail.getBaiduLat()));
            }
        }
        // 谷歌暂时不处理
        if (StringUtils.isNotBlank(detail.getGoogleLon())) {
            hotelExtend.setGcjLng(Double.parseDouble(detail.getGoogleLon()));
//            if (!compareMainDegree(detail.getBaiduLon(), detail.getGoogleLon())) {
//                hotelExtend.setLongitude(Double.parseDouble(detail.getGoogleLon()));
//            }
        }
        if (StringUtils.isNotBlank(detail.getGoogleLat())) {
            hotelExtend.setGcjLat(Double.parseDouble(detail.getGoogleLat()));
//            if (!compareMainDegree(detail.getBaiduLat(), detail.getGoogleLat())) {
//                hotelExtend.setLatitude(Double.parseDouble(detail.getGoogleLat()));
//            }
        }
    }

    /**
     * 经度校验
     * @param lngStr
     * @return
     */
    private boolean checkLng(String lngStr) {
        if (StringUtils.isBlank(lngStr)) {
            return false;
        }
        double lng = Double.parseDouble(lngStr);
        if (lng >= -180 && lng <= 180) {
            return true;
        }
        return false;
    }

    /**
     * 纬度检查
     * @param latStr
     * @return
     */
    private boolean checkLat(String latStr) {
        if (StringUtils.isBlank(latStr)) {
            return false;
        }
        double lat = Double.parseDouble(latStr);
        if (lat >= -90 && lat <= 90) {
            return true;
        }
        return false;
    }

     /**
     * 比较经纬度的主度数是否相等(小数点之前)
     * @param degree1
     * @param degree2
     * @return 接近相等(误差正负1)返回true，不等返回false
     */
    private boolean compareMainDegree(String degree1, String degree2) {
        if (StringUtils.isBlank(degree1) || StringUtils.isBlank(degree2)) {
            return false;
        }
        double mainDegree1 = Double.parseDouble(degree1);
        double mainDegree2 = Double.parseDouble(degree2);
        double offset = mainDegree1 - mainDegree2;
        return Math.abs(offset) < 1;
    }

    public boolean updateEmptyPrices() {
        Map<Long, Long> hotels = getHotelsEmptyPrices();
        if (hotels.isEmpty()) { // 没有需要同步的酒店价格数据
            return false;
        }
        doUpdateHotelPrices(hotels);
        return true;
    }


    private Map<Long, Long> getHotelsEmptyPrices() {
        Map<Long, Long> hotels = new HashMap<Long, Long>();
//        Long startId = 1400000l;
//        Long endId = 1718157l;
//        Long currentId = startId - 1;
//        int pageSize = 100;
//        while (true) {
//            List<Hotel> list = hotelService.listDownByIdRange(currentId, endId, pageSize);
//            if (list.isEmpty()) {
//                break;
//            }
//            for (Hotel hotel : list) {
//                hotels.put(hotel.getId(), hotel.getSourceId());
//            }
//            currentId = list.get(list.size() - 1).getId();
//            if (list.size() < pageSize) {
//                break;
//            }
//            hotelService.clear();
//        }
        List<Hotel> list = hotelService.listWithoutPriceHotel();
        for (Hotel hotel : list) {
            hotels.put(hotel.getId(), hotel.getSourceId());
        }
        return hotels;
    }

//    @Transactional
    public void doUpdateHotelPrices(final Map<Long, Long> hotels) {
        final Semaphore sem = new Semaphore(threadNum);
        final CountDownLatch down = new CountDownLatch(hotels.size());
        for (final Long id : hotels.keySet()) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GlobalTheadPool.instance.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return pricesRunCall(id, hotels.get(id), down, sem);
                }
            });
        }
        try {
            down.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private Object pricesRunCall(Long hotelId, Long elongId, CountDownLatch down, Semaphore sem) {
        try {
            SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);

            Hotel hotel = hotelService.get(hotelId);
            doGetAndSavePrices(String.valueOf(elongId), hotel);
            if (hotel.getRepeatFlag() != null && hotel.getRepeatFlag()) {    //判断酒店是否因重复而强制下架的，
                hotel.setStatus(ProductStatus.DOWN);
            }
            hotelService.updateTransactional(hotel);

            Boolean syncOnestepHotelIndex = propertiesManager.getBoolean("SYNC_ONESTEP_HOTEL_INDEX");
            if (syncOnestepHotelIndex == null || syncOnestepHotelIndex) {
                if (hotel.getSourceStatus()) {  // 更新为已处理才进行索引，减少重复索引
                    hotelService.indexHotel(hotel);   // 酒店索引
                }
            }

            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            down.countDown();
            sem.release();
        }
        return null;
    }

    public List<HotelPrice> doHotelPrices(HotelPriceRequest hpr) {
//        String elongId, Long hotelId, Date arrive,  Date departure,  String roomId, Integer ratePla
        List<HotelPrice> exists = hotelPriceService.findByHotel(hotelService.get(hpr.getHotelId()));
        List<HotelPrice> newPriceList = new ArrayList<HotelPrice>();
        List<HotelPrice> allPriceList = new ArrayList<HotelPrice>();
        List<HotelPrice> returnList = new ArrayList<>();
        Map<String, HotelPrice> existIds = new HashMap<>();
        int days = DateUtils.DayDiff(hpr.getDeparture(), hpr.getArrive());
        for (HotelPrice p : exists) {
            existIds.put(p.getRatePlanCode() + "_" + p.getRoomId() + "_" + p.getRoomTypeId(), p);
        }
        HotelDetail hotelDetail = elongHotelService.getHotelDetail(hpr.getArrive(), hpr.getDeparture(), Long.parseLong(hpr.getElongId()), 1, "2", hpr.getRoomTypeId(), hpr.getRatePla());
        if (hotelDetail == null || hotelDetail.getResult() == null || hotelDetail.getResult().getHotels() == null || hotelDetail.getResult().getHotels().get(0).getRooms() == null) {
            info("result is null:" + hpr.getHotelId());
            return returnList;
        }
        Map<String, BookingRulesEntity> bookingRulesMap = new HashMap<String, BookingRulesEntity>();
        for (BookingRulesEntity rule : hotelDetail.getResult().getHotels().get(0).getBookingRules()) {
            bookingRulesMap.put(String.valueOf(rule.getBookingRuleId()), rule);
        }
        Map<String, GuaranteeRule> guaranteeRuleMap = new HashMap<String, GuaranteeRule>();
        for (GuaranteeRule rule : hotelDetail.getResult().getHotels().get(0).getGuaranteeRules()) {
            guaranteeRuleMap.put(String.valueOf(rule.getGuranteeRuleId()), rule);
        }
        for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity room : hotelDetail.getResult().getHotels().get(0).getRooms()) {
            if (room.getRatePlans() == null)
                continue;
            for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity.RatePlansEntity ratePlan : room.getRatePlans()) {

//                String key = ratePlan.getRatePlanId() + "_" + room.getRoomId();
                String key = ratePlan.getRatePlanId() + "_" + room.getRoomId() + "_" + ratePlan.getRoomTypeId();
                if (existIds.containsKey(key)) {
                    HotelPrice p = existIds.get(key);
                    p = getHotelPrice(hpr.getHotelId(), bookingRulesMap, room, ratePlan, p);
                    if (!ratePlan.getGuaranteeRuleIds().isEmpty()) {
                        GuaranteeRule rule = guaranteeRuleMap.get(ratePlan.getGuaranteeRuleIds().split(",")[0]);
                        p.setChangeRule(getChangeRule(rule, hpr.getArrive()));
                    }
                    p.setPrice((float) Math.ceil(Double.parseDouble((p.getPrice() / days) + "")));
                    info("update price:" + p.getId() + "\t" + p.getRoomName() + "\t" + p.getStatus());
                    hotelPriceService.updateWithIndex(p);
                    allPriceList.add(p);
                } else {
                    HotelPrice p = getHotelPrice(hpr.getHotelId(), bookingRulesMap, room, ratePlan, null);
                    p.setPrice((float) Math.ceil(Double.parseDouble((p.getPrice() / days) + "")));
                    if (!ratePlan.getGuaranteeRuleIds().isEmpty()) {
                        GuaranteeRule rule = guaranteeRuleMap.get(ratePlan.getGuaranteeRuleIds().split(",")[0]);
                        p.setChangeRule(getChangeRule(rule, hpr.getArrive()));
                    }
                    info("save price:" + key + "\t" + p.getRoomName() + "\t" + p.getStatus());
                    newPriceList.add(p);
                    allPriceList.add(p);
                }

            }
        }
        if (!newPriceList.isEmpty()) {
            hotelPriceService.saveAllWithIndex(newPriceList);
        }
        for (HotelPrice hp : allPriceList) {
            if ("UP".equals(hp.getStatus().toString()) || "GUARANTEE".equals(hp.getStatus().toString())) {
                returnList.add(hp);
            }
        }

        return returnList;
    }
    private String getChangeRule(GuaranteeRule rule, Date arrive) {

        if ("NoChange".equals(rule.getChangeRule().toString())) {
           return "不可取消";
        } else if ("NeedSomeDay".equals(rule.getChangeRule().toString())) {

            int time = Integer.parseInt(rule.getTime().toString()) / 3600;
            return "需要在" + DateUtils.formatShortDate(rule.getDay()) + String.valueOf(time) + "时之前";
        } else {

            return getChangeRule(rule.getHour(), arrive);
        }
//        else if ("NeedCheckinTime".equals(rule.getChangeRule().toString())) {
//            return "需要提前" + String.valueOf(rule.getHour()) + "小时";
//        }

    }
    private String getChangeRule(int hour, Date arrive) {
        int leaveHour = Integer.valueOf(DateUtils.getDateDiffHour(arrive, new Date()).toString());
        if (leaveHour >= hour) {
           String day = String.valueOf(hour / 24);
           String h = String.valueOf(hour % 24);
            if ("0".equals(day)) {
                return "需要提前" + h + "小时";
            }
            if ("0".equals(h)) {
                return "需要提前" + day + "天";
            }

            return "需要提前"  + day + "天" + h + "小时";
        }
        return "不可取消";
    }
    private HotelPrice getHotelPrice(Long hotelId, Map<String, BookingRulesEntity> bookingRulesMap, HotelDetail.ResultEntity.HotelsEntity.RoomsEntity room, HotelDetail.ResultEntity.HotelsEntity.RoomsEntity.RatePlansEntity ratePlan, HotelPrice p) {
        HotelPrice hotelPrice = null;
        if (p != null) {
            hotelPrice = p;
        } else {
            hotelPrice = new HotelPrice();
        }
        Hotel hotel = hotelService.get(hotelId);
        hotelPrice.setHotel(hotel);
        hotelPrice.setRoomId(room.getRoomId());

        hotelPrice.setPrice((float) ratePlan.getTotalRate());
        hotelPrice.setCreateTime(new Date());
        if (ratePlan.getRatePlanName().contains("不含早")) {
            hotelPrice.setBreakfast(false);
        } else {
            hotelPrice.setBreakfast(true);
        }
        hotelPrice.setRatePlanCode(String.valueOf(ratePlan.getRatePlanId()));
        if (ratePlan.getGuaranteeRuleIds().isEmpty()) {
            hotelPrice.setStatus(PriceStatus.UP);

        } else {
            hotelPrice.setStatus(PriceStatus.GUARANTEE);
        }
        hotelPrice.setRoomName(room.getName());
        hotelPrice.setRoomDescription(room.getDescription());
        hotelPrice.setRoomTypeId(ratePlan.getRoomTypeId());
        if (ratePlan.getBookingRuleIds() != null && ratePlan.getBookingRuleIds().length() > 0) {
            String[] ids = ratePlan.getBookingRuleIds().split(",");
            for (String id : ids) {
                BookingRulesEntity bookingRule = bookingRulesMap.get(id);
                if (bookingRule != null)
                    try {
                        hotelPrice.setStart(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(bookingRule.getStartDate()));
                        hotelPrice.setEnd(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(bookingRule.getEndDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
        }
        return hotelPrice;
    }

    public String[] getElongHotelIdsByElongCity(String cityId) {
        Date arriveDate = new Date(new Date().getTime() + 3600000);
        Date leaveDate = new Date(arriveDate.getTime() + 86400000);

        HotelIDListCondition idListCondition = new HotelIDListCondition();
        idListCondition.setCityId(cityId);
        idListCondition.setArrivalDate(arriveDate);
        idListCondition.setDepartureDate(leaveDate);
        HotelIdListResult idListResult = elongHotelService.getHotelIdList(idListCondition);
        return idListResult.getResult().getHotelIds().split(",");
    }

    /**
     * 获取酒店状态是否可用
     *
     * @param line 艺龙示例:<Hotel HotelId="11401923" UpdatedTime="2016-03-17 16:03:31" Products="0,1" Status="1" />
     * @return true-可用，false-不可用
     */
    private boolean getHotelStatus(String line) {
        Pattern p = Pattern.compile("Status=\"(\\d+)\"");
        Matcher m = p.matcher(line);
        if (m.find()) {
            String status = m.group(1);
            return "0".equals(status);  // 可用状态: 0--可用，1--不可用
        }
        return false;
    }
}
