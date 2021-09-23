//package com.data.data.hmly.service.hotel;
//
//import com.data.data.hmly.service.CtripHotelDescriptionService;
//import com.data.data.hmly.service.LabelService;
//import com.data.data.hmly.service.common.ProductimageService;
//import com.data.data.hmly.service.common.SolrIndexService;
//import com.data.data.hmly.service.common.entity.AreaRelation;
//import com.data.data.hmly.service.common.entity.Productimage;
//import com.data.data.hmly.service.common.entity.enums.ProductSource;
//import com.data.data.hmly.service.common.entity.enums.ProductStatus;
//import com.data.data.hmly.service.common.entity.enums.ProductType;
//import com.data.data.hmly.service.ctriphotel.CtripHotelStaticInfoService;
//import com.data.data.hmly.service.ctriphotel.PriceInfoService;
//import com.data.data.hmly.service.ctriphotel.entity.CtripHotel;
//import com.data.data.hmly.service.ctriphotel.entity.CtripHotelDescription;
//import com.data.data.hmly.service.ctriphotel.entity.CtripHotelImg;
//import com.data.data.hmly.service.ctriphotel.entity.CtripHotelPrice;
//import com.data.data.hmly.service.dao.TbAreaDao;
//import com.data.data.hmly.service.elong.ElongHotelService;
//import com.data.data.hmly.service.elong.service.result.HotelDetailResult;
//import com.data.data.hmly.service.entity.Label;
//import com.data.data.hmly.service.entity.SysUnit;
//import com.data.data.hmly.service.entity.SysUser;
//import com.data.data.hmly.service.entity.TbArea;
//import com.data.data.hmly.service.hotel.dao.HotelDao;
//import com.data.data.hmly.service.hotel.entity.Hotel;
//import com.data.data.hmly.service.hotel.entity.HotelExtend;
//import com.data.data.hmly.service.hotel.entity.HotelPrice;
//import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
//import com.framework.hibernate.util.Criteria;
//import com.framework.hibernate.util.Page;
//import com.google.common.base.Function;
//import com.google.common.collect.Lists;
//import com.zuipin.util.StringUtils;
//import org.apache.log4j.Logger;
//import org.hibernate.criterion.DetachedCriteria;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class HotelServiceBak extends SolrIndexService<Hotel, HotelSolrEntity> {
//
//    Logger logger = Logger.getLogger(HotelServiceBak.class);
//
//    @Resource
//    private HotelDao hotelDao;
//    @Resource
//    private HotelPriceService hotelPriceService;
//    @Resource
//    private TbAreaDao areaDao;
//    @Resource
//    private LabelService labelService;
//    @Resource
//    private CtripHotelStaticInfoService ctripHotelStaticInfoService;
//    @Resource
//    private PriceInfoService priceInfoService;
//    @Resource
//    private ProductimageService productimageService;
//    @Resource
//    private CtripHotelDescriptionService ctripHotelDescriptionService;
//    @Resource
//    private HotelExtendService hotelExtendService;
//
//    public HotelPrice findByHotelAndDate(Long hotelId, Date date) {
//        return hotelPriceService.findByHotelAndDate(hotelId, date);
//    }
//
//    public Hotel get(Long id) {
//        return hotelDao.load(id);
//    }
//
//    public Hotel getByTargetId(Long targetId) {
//        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
//        criteria.eq("targetId", targetId);
//        try {
//            return hotelDao.findUniqueByCriteria(criteria);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<Hotel> listAll() {
//        return list(new Hotel(), null);
//    }
//
//    public List<Hotel> list(Hotel hotel, Page page, String... orderProperties) {
//        Criteria<Hotel> criteria = createCriteria(hotel, orderProperties);
//        if (page == null) {
//            return hotelDao.findByCriteria(criteria);
//        }
//        return hotelDao.findByCriteria(criteria, page);
//    }
//
//    public Criteria<Hotel> createCriteria(Hotel hotel, String... orderProperties) {
//        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
//        if (orderProperties.length == 2) {
//            criteria.orderBy(orderProperties[0], orderProperties[1]);
//        } else if (orderProperties.length == 1) {
//            criteria.orderBy(Order.desc(orderProperties[0]));
//        }
//        if (StringUtils.isNotBlank(hotel.getName())) {
//            criteria.eq("name", hotel.getName());
//        }
//        if (hotel.getCityId() != null) {
//            criteria.eq("cityId", hotel.getCityId());
//        }
//
//        if (hotel.getStar() != null) {
//            criteria.eq("star", hotel.getStar());
//        }
//        if (hotel.getWeight() != null) {
//            criteria.eq("weight", hotel.getWeight());
//        }
//        return criteria;
//    }
//
//    public void indexHotel() {
//        List<Hotel> list = listAll();
//        List<HotelSolrEntity> entities = Lists.transform(list, new Function<Hotel, HotelSolrEntity>() {
//            @Override
//            public HotelSolrEntity apply(Hotel hotel) {
//                if (hotel.getPrice() == null || hotel.getPrice() == 0) {
//                    setPrice(hotel);
//                }
//                HotelSolrEntity entity = new HotelSolrEntity(hotel);
//                TbArea area = areaDao.getById(hotel.getCityId());
//                if (area != null) {
//                    entity.setCity(area.getName());
//                }
//                return entity;
//            }
//        });
//        index(entities);
//    }
//
//    public void indexHotel(Hotel hotel) {
//        try {
//            HotelSolrEntity entity = new HotelSolrEntity(hotel);
//            List<HotelSolrEntity> entities = Lists.newArrayList(entity);
//            index(entities);
//        } catch (Exception e) {
//            logger.error("未知异常，delicacy#" + hotel.getId() + "索引失败", e);
//        }
//    }
//
//    public void setPrice(Hotel hotel) {
//        HotelPrice priceFilter = new HotelPrice();
//        Hotel hotelFilter = new Hotel();
//        hotelFilter.setId(hotel.getId());
//        priceFilter.setHotel(hotelFilter);
//        List<HotelPrice> hotelPrices = hotelPriceService.list(priceFilter, new Page(0, 1));
//        if (!hotelPrices.isEmpty()) {
//            hotel.setPrice(hotelPrices.get(0).getPrice());
//        } else {
//            hotel.setPrice(0f);
//        }
//    }
//
//    public List<Hotel> getHotelLabels(Hotel info, TbArea area, String tagIds,
//                                      Page pageInfo) {
//
//        List<Hotel> infos = new ArrayList<Hotel>();
//
//        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
////		String hql = "from Hotel h INNER JOIN Product p on p_.productId = h_.id ";
//        if (info != null) {
//            criteria.like("name", "%" + info.getName() + "%");
//        }
//        if (area != null) {
//            if (area.getLevel() == 1) {
//                List<TbArea> areas = areaDao.findCityByPro(area.getId());
//                Long[] aIds = new Long[areas.size()];
//                for (int i = 0; i < areas.size(); i++) {
//                    aIds[i] = areas.get(i).getId();
//                }
//                criteria.in("cityId", aIds);
//            } else if (area.getLevel() == 2) {
//                criteria.eq("cityId", area);
//            }
//
//        }
//        if (StringUtils.isNotBlank(tagIds)) {
//            String[] tIdStrs = tagIds.split(",");
//            Long[] ids = new Long[tIdStrs.length];
//            for (int i = 0; i < tIdStrs.length; i++) {
//                ids[i] = Long.parseLong(tIdStrs[i]);
//            }
////			criteria.createCriteria("product",JoinType.INNER_JOIN);
//            criteria.in("id", ids);
//            infos = hotelDao.findByCriteria(criteria, pageInfo);
////			hotelDao.findByHQL(hqlstr, page);
//        } else if (tagIds == null) {
//            infos = hotelDao.findByCriteria(criteria, pageInfo);
//        }
//        return infos;
//    }
//
//    public List<Hotel> getRecommendHotelOnList(int size) {
//        String name = "酒店列表页的推荐酒店";
//        return getHotelByLabel(name, new Page(0, size));
//    }
//
//    public List<Hotel> getHotelByLabel(String name, Page page) {
//        Label searchLabel = new Label();
//        searchLabel.setName(name);
//        Label label = labelService.findUnique(searchLabel);
//
//        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
//
//        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
//        criterion.add(Restrictions.eq("label.id", label.getId()));
//        criterion.addOrder(Order.asc("order"));
//        if (page != null) {
//            return hotelDao.findByCriteria(criteria, page);
//        } else {
//            return hotelDao.findByCriteria(criteria);
//        }
//    }
//
//    public List<Hotel> getHotAreaHotel(Label label, TbArea tbArea, Page page) {
//        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
//
//        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
//        criterion.add(Restrictions.eq("label.id", label.getId()));
//        criterion.addOrder(Order.asc("order"));
//        if (tbArea != null) {
//            if (tbArea.getLevel() == 1) {
//                List<TbArea> areas = areaDao.findCityByPro(tbArea.getId());
//                Long[] aIds = new Long[areas.size()];
//                for (int i = 0; i < areas.size(); i++) {
//                    aIds[i] = areas.get(i).getId();
//                }
//                criteria.in("cityId", aIds);
//            } else if (tbArea.getLevel() == 2) {
//                criteria.eq("cityId", tbArea.getId());
//            }
//
//        }
//
//        if (page != null) {
//            return hotelDao.findByCriteria(criteria, page);
//        } else {
//            return hotelDao.findByCriteria(criteria);
//        }
//    }
//
//    // 更新开始前把所有酒店数据设为下架状态
//    public void doInvalidCtripHotel(TbArea tbArea) {
//        String hql = " update Hotel set status = ? where cityId = ? and source = ?";
//        hotelDao.updateByHQL(hql, ProductStatus.DEL, tbArea.getId(), ProductSource.CTRIP);
//
//    }
//
//    // 更新开始前把所有酒店图片数据删除
//    public void doInvalidCtripImage(TbArea tbArea) {
////        String hql = " delete Productimage where product.cityId = ? and product.proType = ? and product.source = ?";
////        hotelDao.updateByHQL(hql, tbArea.getId(), ProductType.hotel, ProductSource.CTRIP);
//
//        String sql = "delete img from productimage img, product"
//                + " where product.cityId = ? and product.proType = ?"
//                + " and product.source = ? and img.productId = product.id";
//        hotelDao.updateBySQL(sql, tbArea.getId(), ProductType.hotel, ProductSource.CTRIP);
//
//    }
//
//    // 更新前把所有携程酒店的价格数据删除
//    public void doInvalidCtripPrice(TbArea tbArea) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        Date startDate = calendar.getTime();
//        calendar.add(Calendar.DAY_OF_MONTH, 28);
//        Date endDate = calendar.getTime();
////        String hql = " delete HotelPrice where hotel.cityId = ? and hotel.source = ? and start >= ? and end <= ?";
////        hotelDao.updateByHQL(hql, tbArea.getId(), ProductSource.CTRIP, startDate, endDate);
//
//        String sql = "delete price from hotel_price price, product"
//                + " where price.hotelId = product.id and product.cityId = ?"
//                + " and product.source = ? and price.start >= ? and price.end <= ?";
//        hotelDao.updateBySQL(sql, tbArea.getId(), ProductSource.CTRIP, startDate, endDate);
//    }
//
//    // 删除旧数据
//    public void doDealOldCtripHotelData(TbArea tbArea) {
//        doInvalidCtripHotel(tbArea);
//        doInvalidCtripImage(tbArea);
//        doInvalidCtripPrice(tbArea);
//    }
//
//
//    public void doCtripToLxbHotel(AreaRelation areaRelation) {
//
//        Long areaId = areaRelation.getId();
//        Long ctripCityId = areaRelation.getCtripHotelCity();
//
//        // delete old data
//        TbArea tbArea = new TbArea();
//        tbArea.setId(areaId);
//        doDealOldCtripHotelData(tbArea);
//
//        Page countPage = new Page(1, 10);
//        int totalPageCount = countPage.getPageCount();
//        int pageSize = 10;
//        int index = 1;
//        while (index <= totalPageCount) {
//            Page page = new Page(index, pageSize);
//            index++;
//            List<CtripHotel> ctripHotelList = ctripHotelStaticInfoService.getList(ctripCityId, page);
//            for (CtripHotel ctripHotel : ctripHotelList) {
//                //
//                doCtripToHotel(ctripHotel, areaId);
//            }
//        }
//    }
//
//    public Hotel doCtripToHotel(CtripHotel ctripHotel, Long areaId) {
//        Boolean newFlag = true;
//        Hotel hotel = getByTargetId(ctripHotel.getId());
//        HotelExtend hotelExtend = null;
//        if (hotel == null) {
//            hotel = new Hotel();
//            hotel.setCreateTime(new Date());
//
//            hotelExtend = new HotelExtend();
//        } else {
//            newFlag = false;
//            hotel.setUpdateTime(new Date());
//
//            hotelExtend = hotel.getExtend();
//        }
//
//        Boolean newExtend = fillHotel(ctripHotel, areaId, hotel, hotelExtend);
//
//        // update img
//        List<CtripHotelImg> ctripHotelImgList = ctripHotelStaticInfoService.getImgList(ctripHotel.getId());
//        List<Productimage> productImageList = getProductImageList(hotel, ctripHotelImgList);
//        if (!productImageList.isEmpty()) {
//            // select the forst img as cover
//            productImageList.get(0).setCoverFlag(true);
//            hotel.setCover(productImageList.get(0).getPath());
//            productimageService.saveAll(productImageList);
//        }
//
//        if (newFlag) {
//            hotelDao.save(hotel);
//        } else {
//            hotelDao.update(hotel);
//        }
//        hotel.setTopProduct(hotel);
//        // extend info
//        if (newExtend) {
//            hotelExtend.setId(hotel.getId());
//            hotelExtendService.save(hotelExtend);
//        } else {
//            hotelExtendService.update(hotelExtend);
//        }
//        hotel.setExtend(hotelExtend);
//        hotelDao.update(hotel);
//
//        hotelExtend.setHotel(hotel);
//
//        // update price
//        List<HotelPrice> hotelPriceList = new ArrayList<HotelPrice>();
//        List<CtripHotelPrice> ctripHotelPriceList = priceInfoService.getList(ctripHotel.getId());
//        for (CtripHotelPrice ctripHotelPrice : ctripHotelPriceList) {
//            //
//            HotelPrice hotelPrice = new HotelPrice();
//            hotelPrice.setHotel(hotel);
//            hotelPrice.setPrice(ctripHotelPrice.getAmountBeforeTax());
//            hotelPrice.setDate(ctripHotelPrice.getStart());
//            hotelPrice.setCreateTime(new Date());
//            hotelPrice.setModifyTime(new Date());
//            hotelPrice.setBreakfast(ctripHotelPrice.getBreakfast());
//            hotelPrice.setRatePlanCode(ctripHotelPrice.getRatePlanCode());
//            hotelPrice.setStart(ctripHotelPrice.getStart());
//            hotelPrice.setEnd(ctripHotelPrice.getEnd());
//            hotelPrice.setStatus(ProductStatus.UP);
//            hotelPrice.setCancelStart(ctripHotelPrice.getCancelStart());
//            hotelPrice.setCancelEnd(ctripHotelPrice.getCancelEnd());
//            hotelPrice.setRoomDescription(ctripHotelPrice.getRoomDescription());
//            hotelPriceList.add(hotelPrice);
//        }
//        if (!hotelPriceList.isEmpty()) {
//            hotelPriceService.saveAll(hotelPriceList);
//        }
//        return hotel;
//    }
//
//
//    /**
//     * 通过某个表签查找目的地下的酒店排行榜
//     *
//     * @param label
//     * @return
//     */
//    public List<Hotel> getTopHotelByDestination(List<TbArea> tbAreas, Page page) {
//        Hotel hotelCodition = new Hotel();
//        return hotelDao.getTopHotelByDestination(hotelCodition, null, tbAreas, page);
//    }
//
//    private List<Productimage> getProductImageList(Hotel hotel, List<CtripHotelImg> ctripHotelImgList) {
//        List<Productimage> productimageList = new ArrayList<Productimage>();
//        for (CtripHotelImg ctripHotelImg : ctripHotelImgList) {
//            Productimage productimage = new Productimage();
//            productimage.setProduct(hotel);
//            productimage.setProType(ProductType.hotel);
//            productimage.setPath(ctripHotelImg.getUrl());
//            productimage.setCoverFlag(false);
//            productimage.setUserId(hotel.getUser().getId());
//            productimage.setCompanyUnitId(hotel.getCompanyUnit().getId());
//            productimageList.add(productimage);
//        }
//        return productimageList;
//    }
//
//    private Boolean fillHotel(CtripHotel ctripHotel, Long areaId, Hotel hotel, HotelExtend hotelExtend) {
//        hotel.setName(ctripHotel.getHotelName());
//        hotel.setStatus(ProductStatus.UP);
//        SysUser sysUser = new SysUser();
//        sysUser.setId(9L);
//        hotel.setUser(sysUser);
//        SysUnit sysUnit = new SysUnit();
//        sysUnit.setId(-1L);
//        hotel.setCompanyUnit(sysUnit);
//        hotel.setProType(ProductType.hotel);
//        hotel.setCityId(areaId);
//        hotel.setSupplier(sysUser);
//        hotel.setScore(ctripHotel.getCtripUserRate());
//        // todo which star
//        hotel.setStar(ctripHotel.getHotelStarLicence().intValue());
//        hotel.setTargetId(ctripHotel.getId());
//        CtripHotelDescription shortHotelDescription = ctripHotelDescriptionService.getShortDescription(ctripHotel.getId());
//        if (shortHotelDescription != null && StringUtils.isNotBlank(shortHotelDescription.getDescription())) {
//            hotel.setShortDesc(shortHotelDescription.getDescription());
//        }
//        // TODO hotelextend
//        // extend info
//        Boolean newExtend = false;
//        if (hotelExtend.getId() == null) {
//            newExtend = true;
//            hotelExtend.setId(hotel.getId());
//        }
//        hotelExtend.setHotel(hotel);
//        hotelExtend.setAddress(ctripHotel.getAddress());
//        hotelExtend.setLongitude(ctripHotel.getLongitude());
//        hotelExtend.setLatitude(ctripHotel.getLatitude());
//
//        CtripHotelDescription ctripHotelDescription = ctripHotelDescriptionService.getLongDescription(ctripHotel.getId());
//        if (ctripHotelDescription != null && StringUtils.isNotBlank(ctripHotelDescription.getDescription())) {
//            //
//            hotelExtend.setDescription(ctripHotelDescription.getDescription());
//        }
//        return newExtend;
//    }
//
//    public Integer addCollect(Long id) {
//        Hotel hotel = get(id);
//        Integer collectNum = hotel.getExtend().getCollectNum();
//        if (collectNum == null) {
//            collectNum = 0;
//        }
//        collectNum++;
//        hotel.getExtend().setCollectNum(collectNum);
//        hotelDao.update(hotel);
//        return collectNum;
//    }
//
//    public Integer deleteCollect(Long id) {
//        Hotel hotel = get(id);
//        Integer collectNum = hotel.getExtend().getCollectNum();
//        if (collectNum == null || collectNum == 0) {
//            return 0;
//        }
//        collectNum--;
//        hotel.getExtend().setCollectNum(collectNum);
//        hotelDao.update(hotel);
//        return collectNum;
//    }
//
//    public Integer getCollectNum(Long id) {
//        Hotel hotel = get(id);
//        Integer collectNum = hotel.getExtend().getCollectNum();
//        if (collectNum == null) {
//            collectNum = 0;
//        }
//        return collectNum;
//    }
//}
