package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.dao.HotelPriceCalendarDao;
import com.data.data.hmly.service.hotel.dao.HotelPriceDao;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.vo.HotelRoomSolrEntity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
@Service
public class HotelPriceService extends SolrIndexService<HotelPrice, HotelRoomSolrEntity> {

    private final Logger logger = Logger.getLogger(HotelPriceService.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private HotelPriceDao hotelPriceDao;

    @Resource
    private HotelPriceCalendarDao calendarDao;

    @Resource
    private ProductimageService productimageService;

    public PriceStatus getPriceStatus(Long hotelPriceId) {
        String sql = "select status from hotel_price where id=?";
        List<?> s = hotelPriceDao.findBySQL(sql, hotelPriceId);
        if (s != null && !s.isEmpty() && s.get(0) != null) {
            return PriceStatus.valueOf(s.get(0).toString());
        }
        return null;
    }

    public ShowStatus getShowStatus(Long id) {
        String sql = "select show_status from hotel_price where id=?";
        List<?> s = hotelPriceDao.findBySQL(sql, id);
        if (s != null && !s.isEmpty() && s.get(0) != null) {
            return ShowStatus.valueOf(s.get(0).toString());
        }
        return null;
    }

    public void updateCalendar(HotelPriceCalendar hotelPriceCalendar) {
        calendarDao.update(hotelPriceCalendar);
    }

    /**
     * 查询酒店房型，线路组合使用
     */
    public List<HotelPrice> listHotelPriceForLine(HotelPrice hotelPrice, Page page, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {
        return hotelPriceDao.listHotelPriceForLine(hotelPrice, page, sysUser, isSiteAdmin, isSupperAdmin);
    }

    // 根据酒店ID和房型描述查询某一天的房价
    public List<HotelPrice> findByHotel(Hotel hotel) {
        HotelPrice hotelPrice = new HotelPrice();
//        Hotel hotel = new Hotel();
//        hotel.setId(hotelId);
        hotelPrice.setHotel(hotel);
        Criteria<HotelPrice> criteria = createCriteria(hotelPrice);
        return hotelPriceDao.findByCriteria(criteria);
    }

    // 根据酒店ID和房型描述查询某一天的房价
    public HotelPrice findByHotelAndDateString(Long hotelId, String dateString, String ratePlanCode) {
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("解析时间失败");
        }
        HotelPrice hotelPrice = new HotelPrice();
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        hotelPrice.setHotel(hotel);
        hotelPrice.setDate(date);
        hotelPrice.setRatePlanCode(ratePlanCode);
        Criteria<HotelPrice> criteria = createCriteria(hotelPrice);
        return hotelPriceDao.findUniqueByCriteria(criteria);
    }

    // 通过时间段和房型信息查询房价
    public List<HotelPrice> getListForOrder(Long hotelId, String startDate, String endDate, Long priceId) {
        Date priceStartDate = null;
        try {
            priceStartDate = simpleDateFormat.parse(startDate);
        } catch (ParseException e) {
            logger.error("解析start date失败", e);
        }
        Date priceEndDate = null;
        try {
            priceEndDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            logger.error("解析end date失败", e);
        }
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        if (priceStartDate != null) {
            criteria.or(Restrictions.isNull("start"), Restrictions.le("start", priceStartDate));
//            criteria.le("start", priceStartDate);
        }
        if (priceEndDate != null) {
//            criteria.ge("end", priceEndDate);
            criteria.or(Restrictions.isNull("end"), Restrictions.ge("end", priceEndDate));
        }
        criteria.eq("hotel.id", hotelId);
        criteria.eq("id", priceId);
        return hotelPriceDao.findByCriteria(criteria);
    }

    public HotelPrice get(Long id) {
        return hotelPriceDao.load(id);
    }

    public HotelPrice findFullById(Long id) {
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.createCriteria("hotel");
        criteria.eq("id", id);
        return hotelPriceDao.findUniqueByCriteria(criteria);
    }

    public HotelPrice findByHotelAndDate(Long hotelId, Date date) {
        HotelPrice hotelPrice = new HotelPrice();
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        hotelPrice.setHotel(hotel);
        hotelPrice.setDate(date);
        Criteria<HotelPrice> criteria = createCriteria(hotelPrice);
        return hotelPriceDao.findUniqueByCriteria(criteria);
    }

    /**
     * 查找酒店下所有房型
     * @param hotelId
     * @param page
     * @return
     */
    public List<HotelPrice> findByHotel(Long hotelId, Page page) {
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.createCriteria("hotel");
        criteria.eq("hotel.id", hotelId);
        if (page != null) {
            return hotelPriceDao.findByCriteria(criteria, page);
        }
        return hotelPriceDao.findByCriteria(criteria);
    }

    public List<HotelPrice> findByHotel(HotelPrice condition, SysUnit companyUnit, Page page, String... orderProperties) {
        Criteria<HotelPrice> criteria = createCriteria(condition);
        List<PriceStatus> notStatus = Arrays.asList(PriceStatus.DEL);
        criteria.notin("status", notStatus);
        criteria.eq("showStatus", ShowStatus.SHOW);
        criteria.eq("companyUnit.id", companyUnit.getId());

        if (orderProperties.length == 2) {
            if (orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            }
        } else if (orderProperties.length == 1) {
            if (orderProperties[0] != null) {
                criteria.orderBy(Order.asc(orderProperties[0]));
            }
        }

        List<HotelPrice> priceList;
        if (page != null) {
            priceList = hotelPriceDao.findByCriteria(criteria, page);
        } else {
            priceList = hotelPriceDao.findByCriteria(criteria);
        }
        return priceList;
    }

    public List<HotelPrice> findByHotelAndDateRange(Long hotelId, String startDate, String endDate) {
        Date priceStartDate = null;
        try {
            priceStartDate = simpleDateFormat.parse(startDate);
        } catch (ParseException e) {
            logger.error("解析start date失败", e);
        }
        Date priceEndDate = null;
        try {
            priceEndDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            logger.error("解析end date失败", e);
        }
        return findByHotelAndDateRange(hotelId, priceStartDate, priceEndDate);
    }

    public List<HotelPrice> findByHotelAndDateRange(Long hotelId, Date startDate, Date endDate) {
        HotelPrice hotelPrice = new HotelPrice();
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        hotelPrice.setHotel(hotel);
        StringBuilder builder = new StringBuilder();
        builder.append(" from HotelPrice h where hotelId=?");
        List<Object> list = new ArrayList<Object>();
        list.add(hotelId);
        if (startDate != null) {
            builder.append(" and ");
            builder.append(" start >= ? ");
            list.add(startDate);
        }
        if (endDate != null) {
            builder.append(" and ");
            builder.append(" h.end <= ? ");
            list.add(endDate);
        }
        builder.append(" group by ratePlanCode ");
        return hotelPriceDao.findByHQL(builder.toString(), list.toArray());
    }

    public List<HotelPrice> list(HotelPrice hotelPrice, List<PriceStatus> statuses, Page page, String... orderProperties) {
        Criteria<HotelPrice> criteria = createCriteria(hotelPrice, orderProperties);
        if (statuses != null && !statuses.isEmpty()) {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (PriceStatus statuse : statuses) {
                simpleExpressions.add(Restrictions.eq("status", statuse));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        if (page == null) {
            return hotelPriceDao.findByCriteriaWithOutCount(criteria, new Page(0, Integer.MAX_VALUE));
        }
        return hotelPriceDao.findByCriteriaWithOutCount(criteria, page);
    }

    public Criteria<HotelPrice> createCriteria(HotelPrice hotelPrice, String... orderProperties) {
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        DetachedCriteria hotelCriterion = criteria.createCriteria("hotel", "hotel");
        hotelCriterion.createCriteria("companyUnit", "companyUnit");
//        criteria.orderBy("hotel.id", "desc");
        if (hotelPrice.getHotel() != null && hotelPrice.getHotel().getId() != null) {
            hotelCriterion.add(Restrictions.eq("id", hotelPrice.getHotel().getId()));
        }
        if (hotelPrice.getHotel() != null && StringUtils.isNotBlank(hotelPrice.getHotel().getName())) {
            hotelCriterion.add(Restrictions.like("name", hotelPrice.getHotel().getName(), MatchMode.ANYWHERE));
        }
        if (hotelPrice.getHotel() != null && hotelPrice.getHotel().getSource() != null) {
            /*if (hotelPrice.getHotel().getSource() == ProductSource.LXB) {
                hotelCriterion.add(Restrictions.isNull("source"));
//                criteria.isNull("hotel.source");
            } else {*/
            hotelCriterion.add(Restrictions.eq("source", hotelPrice.getHotel().getSource()));
//                criteria.eq("hotel.source", hotelPrice.getHotel().getSource());
//            }
        }

        if (StringUtils.hasText(hotelPrice.getRoomName())) {
            criteria.like("roomName", hotelPrice.getRoomName(), MatchMode.ANYWHERE);
        }

        if (hotelPrice.getDate() != null) {
            criteria.eq("date", hotelPrice.getDate());
        }
        if (hotelPrice.getStart() != null) {
            criteria.le("start", hotelPrice.getStart());
        }
        if (hotelPrice.getEnd() != null) {
            criteria.ge("end", hotelPrice.getEnd());
        }
        if (hotelPrice.getShowStatus() != null) {
            criteria.eq("showStatus", hotelPrice.getShowStatus());
        }
        if (StringUtils.isNotBlank(hotelPrice.getRoomDescription())) {
            criteria.eq("roomDescription", hotelPrice.getRoomDescription());
        }
        if (hotelPrice.getRatePlanCode() != null) {
            criteria.eq("ratePlanCode", hotelPrice.getRatePlanCode());
        }
        criteria.ne("status", PriceStatus.DEL);

        if (hotelPrice.getStatus() != null) {
            criteria.eq("status", hotelPrice.getStatus());
        }

        if (hotelPrice.getPriceStatusList() != null && !hotelPrice.getPriceStatusList().isEmpty()) {
            Disjunction dis = Restrictions.disjunction();
            for (PriceStatus priceStatus : hotelPrice.getPriceStatusList()) {
                dis.add(Restrictions.eq("status", priceStatus));
            }
            criteria.add(dis);
        }
        if (hotelPrice.getNormalStatus() != null) {
            criteria.Add(Restrictions.or(Restrictions.eq("status", PriceStatus.UP), Restrictions.eq("status", PriceStatus.GUARANTEE)));
        }
        if (StringUtils.isNotBlank(hotelPrice.getBntBedType())) {   // 床型：1-大床,2-双床,3-圆床
            criteria.like("roomDescription", bedType(hotelPrice.getBntBedType()), MatchMode.START);
        }
        if (hotelPrice.getBreakfast() != null) {
            criteria.eq("breakfast", hotelPrice.getBreakfast());
        }
        if (hotelPrice.getPriceLow() != null && hotelPrice.getPriceHigh() != null) {
            criteria.ge("price", hotelPrice.getPriceLow());
            criteria.le("price", hotelPrice.getPriceHigh());
        }

        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }

        return criteria;
    }

    /**
     * 床型：1-大床,2-双床,3-圆床
     * @param type
     * @return
     */
    private String bedType(String type) {
        if ("1".equals(type)) {
            return "大床";
        } else if ("2".equals(type)) {
            return "双床";
        } else if ("3".equals(type)) {
            return "圆床";
        } else {
            return "";
        }
    }

    @Transactional
    public void updateWithIndex(HotelPrice hotelPrice) {
        hotelPriceDao.update(hotelPrice);
        indexHotelPrice(hotelPrice);
    }

    public void update(HotelPrice hotelPrice) {
        hotelPriceDao.update(hotelPrice);
    }

    public void save(HotelPrice hotelPrice) {
        hotelPriceDao.save(hotelPrice);
    }


    public void saveAllWithIndex(List<HotelPrice> hotelPriceList) {
        hotelPriceDao.save(hotelPriceList);
        indexHotelPrice(hotelPriceList);
    }

    public void saveAll(List<HotelPrice> hotelPriceList) {
        hotelPriceDao.save(hotelPriceList);
    }

    public void updateAll(List<HotelPrice> hotelPrices) {
        hotelPriceDao.updateAll(hotelPrices);
    }

    public List<HotelPrice> getForPlanOrder(Date startDate, Date leaveDate, Long hotelId, Integer days) {
        //
        String hql = "select from HotelPrice where date >= ? and date <= ? and hotelId = ? GROUP BY ratePlanCode having count(*) = ?  order by min(price)";
        return hotelPriceDao.findByHQL(hql, startDate, leaveDate, hotelId, days);
    }

    public HotelPrice findAvailableHotelPrice(Long cityId, Date startDate, Date leaveDate, Integer days) {
        String hql = "from HotelPrice where hotel.cityId = :cityId and date >= :startDate and date <= :end GROUP BY hotelId,ratePlanCode having count(*) = ? order by min(price)";
        return hotelPriceDao.findByHQLWithUniqueObject(hql, cityId, startDate, leaveDate, days);
    }

    // 根据自由行传输数据查询价格信息
    public List<HotelPrice> getListForOrder(Date startDate, Date leaveDate, Long hotelId, String ratePlanCode) {
        //
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.ge("date", startDate);
        criteria.lt("date", leaveDate);
        criteria.eq("hotel.id", hotelId);
        criteria.eq("ratePlanCode", ratePlanCode);
        return hotelPriceDao.findByCriteria(criteria);
    }

    public List<HotelPrice> findOneByRatePlanCode(String ratePlanCode) {
        //
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.eq("ratePlanCode", ratePlanCode);
        return hotelPriceDao.findByCriteria(criteria);
    }

    public void clear() {
        hotelPriceDao.getHibernateTemplate().clear();
    }


    public void indexHotelPrice(HotelPrice hotelPrice) {
        try {
            HotelRoomSolrEntity entity = new HotelRoomSolrEntity(hotelPrice);
            List<HotelRoomSolrEntity> entities = Lists.newArrayList(entity);
            index(entities);
        } catch (Exception e) {
            logger.error("未知异常，index hotelPrice#" + hotelPrice.getId() + "索引失败", e);
        }
    }

    public void indexHotelPrice(List<HotelPrice> hotelPrices) {
        try {
            List<HotelRoomSolrEntity> entities = new ArrayList<HotelRoomSolrEntity>();
            for (HotelPrice hotelPrice : hotelPrices) {
                entities.add(new HotelRoomSolrEntity(hotelPrice));
            }
//            index(entities);
        } catch (Exception e) {
            logger.error("未知异常，index hotelPrice# 索引失败", e);
        }
    }

    public void saveOrUpdate(HotelPrice hotelPrice) {

        if (hotelPrice.getId() != null) {
            hotelPrice.setModifyTime(new Date());
            hotelPriceDao.update(hotelPrice);
        } else {
            hotelPrice.setCreateTime(new Date());
            hotelPrice.setModifyTime(new Date());
            hotelPriceDao.save(hotelPrice);
        }

    }

    public void saveDatePrice(String typePriceDate, HotelPrice hotelPrice) {

        List<HotelPriceCalendar> calendars = new ArrayList<HotelPriceCalendar>();


        if (!StringUtils.isNotBlank(typePriceDate)) {
            hotelPrice.setPrice(0f);
            hotelPrice.setMarketPrice(0f);
            hotelPrice.setCprice(0f);
            hotelPriceDao.update(hotelPrice);
            return;
        }
        JSONArray jsonArray = JSONArray.fromObject(typePriceDate);
        Date startDate = null;
        Date endDate = null;

        if (!jsonArray.isEmpty()) {
            JSONObject firstObj = JSONObject.fromObject(jsonArray.get(0));
            startDate = DateUtils.toDate(firstObj.get("start").toString());
            hotelPrice.setStart(startDate);
            if (jsonArray.size() > 1) {
                JSONObject lastObj = JSONObject.fromObject(jsonArray.get(jsonArray.size() - 1));
                endDate = DateUtils.toDate(lastObj.get("start").toString());
                hotelPrice.setEnd(endDate);
            }
        }

        List<Float> minMemberList = new ArrayList<Float>();
        List<Float> minCpriceList = new ArrayList<Float>();
        List<Float> minMarketPriceList = new ArrayList<Float>();

        for (Object o : jsonArray) {
            JSONObject object = JSONObject.fromObject(o);
            HotelPriceCalendar hotelPriceCalendar = new HotelPriceCalendar();
            Float cost = 0f;
            Float member = 0f;
            Float marketPrice = 0f;
            if (object.get("member") != null) {
                member = Float.parseFloat(object.get("member").toString());
            }
            if (object.get("marketPrice") != null) {
                marketPrice = Float.parseFloat(object.get("marketPrice").toString());
            }
            if (object.get("cost") != null) {
                cost = Float.parseFloat(object.get("cost").toString());
            }
            if (object.get("inventory") != null) {
                hotelPriceCalendar.setInventory(Integer.parseInt(object.get("inventory").toString()));
            }
            hotelPriceCalendar.setMember(member);
            minMemberList.add(member);
            hotelPriceCalendar.setMarketPrice(marketPrice);
            minMarketPriceList.add(marketPrice);
            hotelPriceCalendar.setCost(cost);
            minCpriceList.add(cost);

            hotelPriceCalendar.setDate(DateUtils.toDate(object.get("start").toString()));
            hotelPriceCalendar.setHotelPrice(hotelPrice);
            hotelPriceCalendar.setHotelId(hotelPrice.getHotel().getId());
            hotelPriceCalendar.setCreateTime(new Date());
            calendars.add(hotelPriceCalendar);
        }
        calendarDao.save(calendars);
        hotelPrice.setPrice(Collections.min(minMemberList));
        hotelPrice.setMarketPrice(Collections.min(minMarketPriceList));
        hotelPrice.setCprice(Collections.min(minCpriceList));
        hotelPriceDao.update(hotelPrice);

    }


    public List<HotelPriceCalendar> findTypePriceDate(Long typeRriceId, Date dateStart, Date dateEnd) {
        return calendarDao.findTypePriceDate(typeRriceId, dateStart, dateEnd);
    }

    public List<HotelPriceCalendar> findTypePriceDate(Long typeRriceId, Date dateStart, Date dateEnd, Integer num) {
        return calendarDao.findTypePriceDate(typeRriceId, dateStart, dateEnd, num);
    }

    public void delPriceDate(HotelPrice hotelPrice) {
        calendarDao.dePriceDate(hotelPrice);
    }

    public void delRooType(HotelPrice hotelPrice) {
        calendarDao.dePriceDate(hotelPrice);
        hotelPriceDao.delete(hotelPrice);

    }

    public void saveCalendarList(List<HotelPriceCalendar> priceCalendarList) {
        calendarDao.save(priceCalendarList);
    }

    public List<HotelPriceCalendar> getCalendarList(Long typePriceId, Date date) {
        return calendarDao.getCalendarList(typePriceId, date);
    }

    public Date getMaxDateByPrice(HotelPrice hotelPrice) {
        return calendarDao.findMaxDateByPirce(hotelPrice);
    }

    public Date getMinDateByPrice(HotelPrice hotelPrice) {
        return calendarDao.findMinDateByPirce(hotelPrice);
    }
    public Float findHotelMinPrice(Hotel hotel) {
        return calendarDao.findMinValue(hotel.getId(), new Date());
    }

    public Float findMinPriceByType(Long hotelId, HotelPrice hotelPrice, Date start) {
        return calendarDao.findMinValue(hotelId, hotelPrice.getPriceId(), start);
    }

    public List<HotelPrice> getHotelPriceList(HotelPrice hotelPrice, Page pageInfo, String... orderProperties) {
        Criteria<HotelPrice> criteria = createCriteria(hotelPrice);
        if (pageInfo == null) {
            return hotelPriceDao.findByCriteria(criteria, new Page(0, Integer.MAX_VALUE));
        }

       /* DetachedCriteria hotelCriteria = criteria.createAlias("hotel", "hotel_");
//        DetachedCriteria userCriteria = hotelCriteria.createAlias("hotel_.user", "sysUser_");

        if (!isSuperAdmin) {
            // 注释的写法也可以
            criteria.eq("hotel_.user.sysSite.id", loginUser.getSysSite().getId());
            if (!isSiteAdmin) {
                // 注释的写法也可以
                criteria.eq("hotel_.companyUnit.id", loginUser.getSysUnit().getId());
            }
        }*/
        criteria.ne("status", PriceStatus.DEL);

        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        return hotelPriceDao.findByCriteria(criteria, pageInfo);
    }

    public void delete(HotelPrice hotelPrice) {
        hotelPriceDao.delete(hotelPrice);
    }

    /**
     * 获取所有房型的临时数据
     * @param hotel
     * @return
     */
    public List<HotelPrice> findTempHotelPriceList(Hotel hotel) {
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.eq("hotel.id", hotel.getId());
        criteria.isNotNull("originId");
        return hotelPriceDao.findByCriteria(criteria);
    }

    /**
     * 获取所有房型的原始数据
     * @param hotel
     * @return
     */
    public List<HotelPrice> findOrginalHotelPriceList(Hotel hotel) {
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.eq("hotel.id", hotel.getId());
        criteria.isNull("originId");
        return hotelPriceDao.findByCriteria(criteria);
    }

    /**
     * 删除所有
     * @param hotelPriceList
     */
    public void delete(List<HotelPrice> hotelPriceList) {
        hotelPriceDao.deleteAll(hotelPriceList);
    }

    /**
     * 下架所有房型
     * @param hotel
     */
    public void doHandleHotelPriceAll(Hotel hotel, PriceStatus status) {
        List<HotelPrice> hotelPriceList = findOrginalHotelPriceList(hotel);
        for (HotelPrice hotelPrice : hotelPriceList) {
            if (status == PriceStatus.DOWN) {
                hotelPrice.setShowStatus(ShowStatus.SHOW);
                hotelPrice.setStatus(PriceStatus.DOWN);
            } else if (status == PriceStatus.DEL) {
                hotelPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
                hotelPrice.setStatus(PriceStatus.DEL);
            }
            hotelPriceDao.update(hotelPrice);
        }
    }

    public void deleteTempHotelPriceList(Hotel hotel) {
        List<HotelPrice> hotelPriceList = findTempHotelPriceList(hotel);
        for (HotelPrice hotelPrice : hotelPriceList) {
            productimageService.delImages(hotel.getId(), hotelPrice.getId(), "hotel/hotelRoom/");   //删除临时数据图片
        }
        hotelPriceDao.deleteAll(hotelPriceList);
    }

    public List<HotelPriceCalendar> listCruiseShipDates(Hotel hotel, Date dateStart, Date dateEnd) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelId", hotel.getId());
        if (dateStart != null) {
            criteria.ge("date", dateStart);
        }

        if (dateEnd != null) {
            criteria.le("date", dateEnd);
        }
        criteria.orderBy("date", "asc");
        return calendarDao.findByCriteria(criteria);
    }
}
