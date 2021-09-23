package com.data.data.hmly.service.hotel;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.*;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.dao.HotelDao;
import com.data.data.hmly.service.hotel.entity.*;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.util.Clock;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.MapUtils;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

//import com.data.data.hmly.service.common.ProductimageService;
//import com.data.data.hmly.service.common.entity.Productimage;

@Service
@Transactional
public class HotelService extends SolrIndexService<Hotel, HotelSolrEntity> {

    Logger logger = Logger.getLogger(HotelService.class);

    @Resource
    private HotelDao hotelDao;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TbAreaDao areaDao;
    @Resource
    private LabelService labelService;
    @Resource
    private MulticoreSolrTemplate solrTemplate;
    @Resource
    private HotelAreaService hotelAreaService;
    @Resource
    private HotelRoomIndexService hotelRoomIndexService;
    @Resource
    private HotelElongService hotelElongService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private HotelExtendService hotelExtendService;
    @Resource
    private LabelItemService labelItemService;
    @Resource
    private HotelAmenitiesService hotelAmenitiesService;
    @Resource
    private MemberService memberService;


    private static final Logger LOGGER = Logger.getLogger(HotelService.class);

    public HotelPrice findByHotelAndDate(Long hotelId, Date date) {
        return hotelPriceService.findByHotelAndDate(hotelId, date);
    }

    public Hotel get(Long id) {
        return hotelDao.load(id);
    }

    public Hotel findFullById(Long id) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        criteria.createCriteria("hotelPriceList");
        criteria.eq("id", id);
        return hotelDao.findUniqueByCriteria(criteria);
    }

    public Hotel getByTargetId(Long targetId) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        criteria.eq("targetId", targetId);
//        criteria.ne("status", ProductStatus.DEL);
        try {
            List<Hotel> hotels = hotelDao.findByCriteria(criteria);
            if (hotels != null && !hotels.isEmpty())
                return hotels.get(0);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Hotel> listAll() {
        return list(new Hotel(), null);
    }

    public List<Hotel> list(Hotel hotel, Page page, String... orderProperties) {
        Criteria<Hotel> criteria = createCriteria(hotel, orderProperties);
        if (page == null) {
            return hotelDao.findByCriteria(criteria);
        }
        return hotelDao.findByCriteria(criteria, page);
    }

    public List<Hotel> listWithoutCount(Hotel hotel, Page page, String... orderProperties) {
        Criteria<Hotel> criteria = createCriteria(hotel, orderProperties);
        if (page == null) {
            return hotelDao.findByCriteriaWithOutCount(criteria, new Page(0, Integer.MAX_VALUE));
        }
        return hotelDao.findByCriteriaWithOutCount(criteria, page);
    }

    public List<Hotel> listByIdRange(Long fromId, Long endId, int limit) {
        String hql = "from Hotel where productId>:fromId ";
        Map<String, Object> data = Maps.newHashMap();
        if (endId != null) {
            hql += " and productId<=:endId ";
            data.put("endId", endId);
        }
        hql += " and status=:status order by id";
        data.put("fromId", fromId);
        data.put("status", ProductStatus.UP);
        return hotelDao.findByHQL2(hql, new Page(0, limit), data);
    }

    /**
     * 查询酒店
     * @return
     */
    public List<Hotel> listByIdRange(Integer pageIndex, Integer pageSize) {
        String hql = "from Hotel where 1=1 and source = ? and sourceStatus = ? order by id";
        Page page = new Page(pageIndex, pageSize);
        return hotelDao.findByHQLNoTotal(hql, page, ProductSource.ELONG, Boolean.TRUE);
    }

    /**
     * 统计酒店数量
     * @return
     */
    public Long countHotel() {
        StringBuilder hql = new StringBuilder("select count(*) from Hotel t where 1=1 and source = ? and sourceStatus = ? ");
        Long count = hotelDao.findLongByHQL(hql.toString(), ProductSource.ELONG, Boolean.TRUE);
        return count;
    }

    /**
     * 查询正常却没有更新到价格日期的酒店
     * @return
     */
    public List<Hotel> listWithoutPriceHotel() {
        StringBuilder hql = new StringBuilder();
        hql.append("select new Hotel(h.id, h.sourceId) from Hotel h where status = :status and sourceStatus = :sourceStatus and source = :source ");
////        hql.append("and not exists(select 1 from HotelPrice hp where hp.hotel.id = h.productId and hp.end > ?) ");
//        List<Object> params = new ArrayList<Object>();
//        params.add(ProductStatus.UP);
//        params.add(Boolean.FALSE);
//        params.add(ProductSource.ELONG);
////        params.add(new Date());
//        return hotelDao.findByHQL(hql.toString(), params.toArray());
        // 改用分页查询，防止内存溢出，处理上需循环处理
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("status", ProductStatus.UP);
        mapParam.put("sourceStatus", Boolean.FALSE);
        mapParam.put("source", ProductSource.ELONG);
        Page page = new Page(1, 50000);
        return hotelDao.findByHQL2ForNew(hql.toString(), page, mapParam);
    }

    public List<Hotel> listDownByIdRange(Long fromId, Long endId, int limit) {
        String hql = "from Hotel where productId>:fromId ";
        Map<String, Object> data = Maps.newHashMap();
        if (endId != null) {
            hql += " and productId<=:endId ";
            data.put("endId", endId);
        }
        hql += " and status=:status order by id";
        data.put("fromId", fromId);
        data.put("status", ProductStatus.DOWN);
        return hotelDao.findByHQL2(hql, new Page(0, limit), data);
    }

    public List<Hotel> listByIds(List<Long> ids) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        criteria.in("id", ids);
        return hotelDao.findByCriteria(criteria);
    }

    public Criteria<Hotel> createCriteria(Hotel hotel, String... orderProperties) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(hotel.getName())) {
            criteria.like("name", hotel.getName(), MatchMode.ANYWHERE);
        }
        if (hotel.getShowStatus() != null) {
            criteria.eq("showStatus", hotel.getShowStatus());
        }
        if (hotel.getCityId() != null) {
            if (hotel.getCityId() % 100 == 0) {
                criteria.between("cityId", hotel.getCityId(), hotel.getCityId() + 99);
            } else {
                criteria.eq("cityId", hotel.getCityId());
            }
        }

        if (hotel.getStar() != null) {
            criteria.eq("star", hotel.getStar());
        }
        if (hotel.getWeight() != null) {
            criteria.eq("weight", hotel.getWeight());
        }
        if (hotel.getPrice() != null && hotel.getPrice() < 0) {
            criteria.gt("price", 0f);
        }
        if (hotel.getStatus() != null) {
            criteria.eq("status", hotel.getStatus());
        }
        if (hotel.getProductStatusList() != null && !hotel.getProductStatusList().isEmpty()) {
            Disjunction dis = Restrictions.disjunction();
            for (ProductStatus productStatus : hotel.getProductStatusList()) {
                dis.add(Restrictions.eq("status", productStatus));
            }
            criteria.add(dis);
        }
        if (hotel.getSource() != null) {
            if (hotel.getSource() == ProductSource.LXB) {
                Disjunction dis = Restrictions.disjunction();
                dis.add(Restrictions.isNull("source"));
                dis.add(Restrictions.eq("source", hotel.getSource()));
            } else {
                criteria.eq("source", hotel.getSource());
            }

        }
        return criteria;
    }

    public Criteria<Hotel> fmtCreateCriteria(Criteria<Hotel> criteria, Hotel hotel) {
        if (StringUtils.isNotBlank(hotel.getName())) {
            criteria.like("name", hotel.getName(), MatchMode.ANYWHERE);
        }
        if (hotel.getShowStatus() != null) {
            criteria.eq("showStatus", hotel.getShowStatus());
        }
        if (hotel.getCityId() != null) {
            if (hotel.getCityId() % 100 == 0) {
                criteria.between("cityId", hotel.getCityId(), hotel.getCityId() + 99);
            } else {
                criteria.eq("cityId", hotel.getCityId());
            }
        }

        if (hotel.getStar() != null) {
            criteria.eq("star", hotel.getStar());
        }
        if (hotel.getWeight() != null) {
            criteria.eq("weight", hotel.getWeight());
        }
        if (hotel.getPrice() != null && hotel.getPrice() < 0) {
            criteria.gt("price", 0f);
        }
        if (hotel.getStatus() != null) {
            criteria.eq("status", hotel.getStatus());
        }
        if (hotel.getProductStatusList() != null && !hotel.getProductStatusList().isEmpty()) {
            Disjunction dis = Restrictions.disjunction();
            for (ProductStatus productStatus : hotel.getProductStatusList()) {
                dis.add(Restrictions.eq("status", productStatus));
            }
            criteria.add(dis);
        }
        if (hotel.getSource() != null) {
            if (hotel.getSource() == ProductSource.LXB) {
                Disjunction dis = Restrictions.disjunction();
                dis.add(Restrictions.isNull("source"));
                dis.add(Restrictions.eq("source", hotel.getSource()));
                criteria.add(dis);
            } else {
                criteria.eq("source", hotel.getSource());
            }

        }
        return criteria;
    }

    public void indexHotel() {
        Long startId = 0l;
        indexHotel(startId, null);
    }

    public void indexHotels(List<Hotel> hotels) {
        for (Hotel hotel : hotels) {
            this.indexHotel(hotel);
        }

    }

    public void indexHotel(Long startId, Long endId) {

        Clock clock = new Clock();
        Long currentId = startId - 1;
        int pageSize = 100;
        int processed = 0;
        while (true) {
            List<Hotel> list = listByIdRange(currentId, endId, pageSize);
            processed += list.size();
            if (list.isEmpty()) {
                break;
            }
//            List<HotelSolrEntity> entities = Lists.transform(list, new Function<Hotel, HotelSolrEntity>() {
//                @Override
//                public HotelSolrEntity apply(Hotel hotel) {
//                    if (hotel.getPrice() == null || hotel.getPrice() == 0) {
//                        setPrice(hotel);
//                    }
//                    HotelSolrEntity entity = new HotelSolrEntity(hotel);
//                    TbArea area = areaDao.getById(hotel.getCityId());
//                    if (area != null) {
//                        entity.setCity(area.getName());
//                    }
//                    return entity;
//                }
//            });
//            index(entities);
            for (Hotel hotel : list) {
                indexHotel(hotel);
            }
            currentId = list.get(list.size() - 1).getId();
            logger.info("currentId is " + currentId + ", save 100/" + processed + " hotel cost " + clock.elapseTime() + "/" + clock.totalTime() + "ms, speed " + ((float) processed / clock.totalTime() * 1000) + "/s");
            if (list.size() < pageSize) {
                break;
            }
            clear();
        }
        logger.info("finish in " + clock.totalTime() + "ms with " + processed);

    }

    public void indexHotel(Hotel hotel) {
        try {
            if (hotel.getStatus().equals(ProductStatus.UP) || hotel.getStatus().equals(ProductStatus.DOWN_CHECKING)) {
//                float minPrice = hotelElongService.getMinHotelPrice(hotel, null);
                hotel = hotelDao.load(hotel.getId());
//                Float minPrice = Float.MAX_VALUE;
//                HotelPrice searchCondition = new HotelPrice();
//                searchCondition.setHotel(hotel);
//                searchCondition.setStatus(PriceStatus.UP);
//                List<HotelPrice> list = hotelPriceService.list(searchCondition, null, new Page(0, Integer.MAX_VALUE), "price", "asc");
//                Date start = null;
//                Date end = null;
//                for (HotelPrice hotelPrice : list) {
//                    if (start == null || start.after(hotelPrice.getStart())) {
//                        start = hotelPrice.getStart();
//                    }
//                    if (end == null || end.before(hotelPrice.getEnd())) {
//                        end = hotelPrice.getEnd();
//                    }
//                    minPrice = Math.min(minPrice, hotelPrice.getPrice());
//                }
                HotelSolrEntity entity = new HotelSolrEntity(hotel);
//                if (start != null) {
//                    entity.setStart(DateUtils.add(start, Calendar.HOUR_OF_DAY, 8));
//                }
//                if (end != null) {
//                    entity.setEnd(DateUtils.add(end, Calendar.HOUR_OF_DAY, 8));
//                }
                List<HotelArea> hotelAreas = hotelAreaService.getByHotel(hotel.getId());
                List<String> regions = new ArrayList<>();
                for (HotelArea ha : hotelAreas) {
                    setRegion(ha, regions);
                }
                entity.setRegion(regions);
//                if (minPrice != Float.MAX_VALUE) {
//                    entity.setPrice(minPrice);
//                } else {
//                    entity.setPrice(0f);
//                }
                Productimage productimage = productimageService.findCover(hotel.getId(), hotel.getId(), ProductType.hotel);
                if (productimage != null) {
                    entity.setCover(cover(productimage.getPath()));
                }
                List<HotelSolrEntity> entities = Lists.newArrayList(entity);
                UpdateResponse response = index(entities);
                if (response == null) {
                    logger.error("酒店信息#" + hotel.getId() + "索引失败");
                }
                // 索引酒店价格
//                List<HotelRoomSolrEntity> priceEntities = new ArrayList<HotelRoomSolrEntity>();
//                for (HotelPrice hotelPrice : hotel.getHotelPriceList()) {
//                    HotelRoomSolrEntity e = new HotelRoomSolrEntity(hotelPrice);
//                    priceEntities.add(e);
//                }
//                response = hotelRoomIndexService.indexHotelRoom(priceEntities);
//                if (response == null) {
//                    logger.error("酒店价格#" + hotel.getId() + "索引失败");
//                }
                return;
            }
            deleteIndexByEntityId(hotel.getId(), SolrType.hotel);
//            deleteIndexBy("hotelId", String.valueOf(hotel.getId()), CORE_HOTEL_PRICE);
        } catch (Exception e) {
            logger.error("未知异常，hotel#" + hotel.getId() + "索引失败", e);
        }
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        }
        if (cover.startsWith("http")) {
            return cover;
        }
        return QiniuUtil.URL + cover;
    }

    private void setRegion(HotelArea ha, List<String> regions) {
        if (ha.getRegion() != null) {
            regions.add(ha.getRegion().getId().toString());
        }
    }
    public void setPrice(Hotel hotel) {
        HotelPrice priceFilter = new HotelPrice();
        Hotel hotelFilter = new Hotel();
        hotelFilter.setId(hotel.getId());
        priceFilter.setHotel(hotelFilter);
        List<HotelPrice> hotelPrices = hotelPriceService.list(priceFilter, null, new Page(0, 1));
        if (!hotelPrices.isEmpty()) {
            hotel.setPrice(hotelPrices.get(0).getPrice());
        } else {
            hotel.setStatus(ProductStatus.DOWN);
            hotel.setPrice(0f);
        }
    }

    public List<Hotel> getHotelLabels(Hotel info, TbArea area, String tagIds,
                                      Page pageInfo) {

        List<Hotel> infos = new ArrayList<Hotel>();
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
//		String hql = "from Hotel h INNER JOIN Product p on p_.productId = h_.id ";
        if (info != null) {
            criteria.like("name", "%" + info.getName() + "%");
        }
        criteria.eq("status", ProductStatus.UP);
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                Long[] aIds = new Long[areas.size()];
                for (int i = 0; i < areas.size(); i++) {
                    aIds[i] = areas.get(i).getId();
                }
                criteria.in("cityId", aIds);
            } else if (area.getLevel() == 2) {
                criteria.eq("cityId", area.getId());
            }

        }
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
//			criteria.createCriteria("product",JoinType.INNER_JOIN);
            criteria.in("id", ids);
            infos = hotelDao.findByCriteria(criteria, pageInfo);
//			hotelDao.findByHQL(hqlstr, page);
        } else if (tagIds == null) {
            infos = hotelDao.findByCriteria(criteria, pageInfo);
        }
        return infos;
    }

    public List<Hotel> getRecommendHotelOnList(int size) {
        String name = "酒店列表页的推荐酒店";
        return getHotelByLabel(name, new Page(0, size));
    }

    public List<Hotel> getHotelByLabel(String name, Page page) {
        Label searchLabel = new Label();
        searchLabel.setName(name);
        Label label = labelService.findUnique(searchLabel);

        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);

        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
        criterion.add(Restrictions.eq("label.id", label.getId()));
        criterion.addOrder(Order.asc("order"));
        if (page != null) {
            return hotelDao.findByCriteriaWithOutCount(criteria, page);
        } else {
            return hotelDao.findByCriteriaWithOutCount(criteria, new Page(0, Integer.MAX_VALUE));
        }
    }

    public List<Hotel> getHotelByLabel(Label label, Page page) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
        criterion.add(Restrictions.eq("label.id", label.getId()));
        criterion.addOrder(Order.asc("order"));
        if (page != null) {
            return hotelDao.findByCriteriaWithOutCount(criteria, page);
        } else {
            return hotelDao.findByCriteriaWithOutCount(criteria, new Page(0, Integer.MAX_VALUE));
        }
    }

    public List<Hotel> getHotAreaHotel(Label label, TbArea tbArea, Page page) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);

        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
        criterion.add(Restrictions.eq("label.id", label.getId()));
        criterion.addOrder(Order.asc("order"));
        if (tbArea != null) {
            if (tbArea.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(tbArea.getId());
                Long[] aIds = new Long[areas.size()];
                for (int i = 0; i < areas.size(); i++) {
                    aIds[i] = areas.get(i).getId();
                }
                criteria.in("cityId", aIds);
            } else if (tbArea.getLevel() == 2) {
                criteria.eq("cityId", tbArea.getId());
            }

        }

        if (page != null) {
            return hotelDao.findByCriteria(criteria, page);
        } else {
            return hotelDao.findByCriteria(criteria);
        }
    }

    /**
     * 通过某个表签查找目的地下的酒店排行榜
     *
     * @return
     */
    public List<Hotel> getTopHotelByDestination(TbArea tbArea, Page page) {
        Clock clock = new Clock();
        Hotel hotelCondition = new Hotel();
        hotelCondition.setStatus(ProductStatus.UP);

        Criteria<Hotel> criteria = createCriteria(new Hotel(), "score", "desc");
        Long cityId = tbArea.getId();
        if (cityId % 10000 == 0) {
            criteria.eq("provienceId", cityId);
        } else if (cityId % 100 == 0) {
            criteria.between("cityId", cityId, cityId + 99);
        }
        criteria.eq("status", ProductStatus.UP);
        criteria.isNotNull("score");
        criteria.isNotNull("extend");
        criteria.createAlias("extend", "extend", JoinType.LEFT_OUTER_JOIN);
        criteria.isNotNull("extend.description");
        LOGGER.info("1:" + clock.elapseTime() + "ms");
        List<Hotel> list = hotelDao.findByCriteriaWithOutCount(criteria, page);
        LOGGER.info("2:" + clock.elapseTime() + "ms");
//        Collections.sort(list, new Comparator<Hotel>() {
//            @Override
//            public int compare(Hotel o1, Hotel o2) {
//                int r1 = o1.getRanking() == null ? 9999 : o1.getRanking();
//                int r2 = o2.getRanking() == null ? 9999 : o2.getRanking();
//                return r1 - r2;
//            }
//        });
        return list;
    }


    public Hotel addCollect(Long id) {
        Hotel hotel = get(id);
        Integer collectNum = hotel.getExtend().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        hotel.getExtend().setCollectNum(collectNum);
        hotelDao.update(hotel);
        return hotel;
    }

    public Integer deleteCollect(Long id) {
        Hotel hotel = get(id);
        Integer collectNum = hotel.getExtend().getCollectNum();
        if (collectNum == null || collectNum == 0) {
            return 0;
        }
        collectNum--;
        hotel.getExtend().setCollectNum(collectNum);
        hotelDao.update(hotel);
        return collectNum;
    }

    public Integer getCollectNum(Long id) {
        Hotel hotel = get(id);
        Integer collectNum = hotel.getExtend().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        return collectNum;
    }

    public List<HotelSolrEntity> findNearByHotel(String query, Double longitude, Double latitude, Float distance, Page page) {
        SolrQuery solrQuery = new SolrQuery(query);
        if (page != null) {
            solrQuery.setStart(page.getFirstResult());
            solrQuery.setRows(page.getPageSize());
        }
        QueryResponse response = solrTemplate.nearBy(solrQuery, latitude + "", longitude + "", HotelSolrEntity.class, SolrQuery.ORDER.asc, distance);
        if (page != null) {
            page.setTotalCount(Long.valueOf(response.getResults().getNumFound()).intValue());
        }
        return solrTemplate.convertQueryResponseToBeans(response, HotelSolrEntity.class);
    }

    public void save(Hotel hotel) {
        hotelDao.save(hotel);
    }

    public void saveWithIndex(Hotel hotel) {
        hotelDao.save(hotel);
        indexHotel(hotel);
    }

    public void update(Hotel hotel) {
        hotelDao.update(hotel);
    }

    public void updateWithIndex(Hotel hotel) {
        hotelDao.update(hotel);
        indexHotel(hotel);
    }


    public void clear() {
        hotelDao.getHibernateTemplate().clear();
    }

    public void updateTransactional(Hotel hotel) {
        hotelDao.update(hotel);
//        indexHotel(hotel);
    }

    public void saveTransactional(Hotel hotel) {
        hotelDao.save(hotel);
//        indexHotel(hotel);
    }

    public List<Hotel> getByCompanyUnit(Hotel condition, SysUnit companyUnit, Page page) {
        Criteria<Hotel> criteria = createCriteria(condition);
        criteria.createCriteria("companyUnit", "companyUnit");
        criteria.eq("companyUnit.id", companyUnit.getId());
        List<ProductStatus> notStatuses = Arrays.asList(ProductStatus.DEL);
        criteria.notin("status", notStatuses);
        criteria.eq("showStatus", ShowStatus.SHOW);
        List<Hotel> hotelList;
        if (page != null) {
            hotelList = hotelDao.findByCriteria(criteria, page);
        } else {
            hotelList =  hotelDao.findByCriteria(criteria);
        }
        for (Hotel hotel : hotelList) {
            hotel.setTelephone(hotel.getCompanyUnit().getSysUnitDetail().getTelphone());
            hotel.setContactName(hotel.getCompanyUnit().getSysUnitDetail().getContactName());
        }
        return hotelList;
    }

    public Integer countByCompanyUnit(SysUnit companyUnit) {
        String sql = "select count(id) result from product where companyUnitId=?";
        BigDecimal totalCount = hotelDao.findIntegerBySQL(sql, companyUnit.getId());
        if (totalCount != null) {
            return  totalCount.intValue();
        }
        return 0;
    }

    public List<Hotel> getShowHotelList(Hotel hotel, Page pageInfo, Boolean isSupperAdmin, Boolean isSiteAdmin, SysUser loginUser) {

        Criteria<Hotel> criteria = createCriteria(hotel, "updateTime");
        criteria.eq("status", ProductStatus.UP);
        if (!isSupperAdmin) {
            criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
            criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
            if (!isSiteAdmin) {
                criteria.eq("u.id", loginUser.getSysUnit().getCompanyUnit().getId());
            }
        }

        if (hotel.getQryStartTime() != null) {
            criteria.ge("updateTime", hotel.getQryStartTime());
        }

        if (hotel.getQryEndTime() != null) {
            criteria.le("updateTime", hotel.getQryEndTime());
        }

        if (pageInfo == null) {
            return hotelDao.findByCriteria(criteria);
        }
        return hotelDao.findByCriteria(criteria, pageInfo);
    }

    public List<Hotel> getShowList(Hotel hotel, Page pageInfo, Boolean isSupperAdmin, Boolean isSiteAdmin, SysUser loginUser) {
        List param = new ArrayList();
        StringBuilder sb = new StringBuilder("select new com.data.data.hmly.service.hotel.entity.Hotel(h.id, h.name, h.star, h.ranking, hc.companyUnit.name, he.address)");
        sb.append(" from Hotel h left join h.extend he left join h.companyUnit hc where");

        doFormattHql(sb, param, hotel);

        if (hotel.getQryStartTime() != null) {
            sb.append(" and h.updateTime > ?");
            param.add(hotel.getQryStartTime());
        }
        if (hotel.getQryEndTime() != null) {
            sb.append(" and h.updateTime <= ?");
            param.add(hotel.getQryEndTime());
        }
        sb.append(" order by h.updateTime desc");
        return hotelDao.findByHQL(sb.toString(), pageInfo, param.toArray());
    }


    public void doFormattHql(StringBuilder sb, List param, Hotel hotel) {

        if (StringUtils.isNotBlank(hotel.getName())) {
            if (param.isEmpty()) {
                sb.append(" h.name like '%'||?||'%'");
            } else {
                sb.append(" and h.name like '%'||?||'%'");
            }
            param.add(hotel.getName());
        }
        if (hotel.getCityId() != null) {
            if (hotel.getCityId() % 100 == 0) {
                if (param.isEmpty() ) {
                    sb.append(" (h.cityId between ?");
                    param.add(hotel.getCityId());
                    sb.append(" and ?)");
                    param.add(hotel.getCityId() + 99);
                } else {
                    sb.append(" and (h.cityId between ?");
                    param.add(hotel.getCityId());
                    sb.append(" and ?)");
                    param.add(hotel.getCityId() + 99);
                }

//                criteria.between("cityId", hotel.getCityId(), hotel.getCityId() + 99);
            } else {
                if (param.isEmpty() ) {
                    sb.append(" h.cityId=?");
                } else {
                    sb.append(" and h.cityId=?");
                }


                param.add(hotel.getCityId());
            }
        }

        if (hotel.getStar() != null) {
            if (param.isEmpty() ) {
                sb.append(" h.star=?");
            } else {
                sb.append(" and h.star=?");
            }

            param.add(hotel.getStar());
        }
        if (hotel.getWeight() != null) {
            if (param.isEmpty() ) {
                sb.append(" h.weight=?");
            } else {
                sb.append(" and h.weight=?");
            }

            param.add(hotel.getWeight());
        }
        if (hotel.getPrice() != null && hotel.getPrice() < 0) {
            if (param.isEmpty() ) {
                sb.append(" h.price=?");
            } else {
                sb.append(" and h.price=?");
            }

            param.add(0f);
        }
        if (hotel.getStatus() != null) {
            if (param.isEmpty() ) {
                sb.append(" h.status=?");
            } else {
                sb.append(" and h.status=?");
            }

            param.add(hotel.getStatus());
        }
/*

        if (hotel.getSource() != null) {
            if (hotel.getSource() == ProductSource.LXB) {
                if (param.isEmpty() ) {
                    sb.append(" h.source is null");
                } else {
                    sb.append(" and h.source is null");
                }

            } else {
                if (param.isEmpty() ) {
                    sb.append(" h.source=?");
                } else {
                    sb.append(" and h.source=?");
                }

                param.add(hotel.getSource());
            }

        }
*/


    }


    public List<Hotel> getAllHotelList(Hotel hotel, Page pageInfo, Boolean isSupperAdmin, Boolean isSiteAdmin, SysUser loginUser, String... orderProperties) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        DetachedCriteria productCriteria = DetachedCriteria.forClass(Product.class, "p");
        fmtCreateCriteria(criteria, hotel);
        criteria.ne("status", ProductStatus.DEL);
        criteria.isNull("originId");
        if (!isSupperAdmin) {
            criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
            criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
            if (!isSiteAdmin) {
                criteria.eq("u.id", loginUser.getSysUnit().getCompanyUnit().getId());
            }
        }
        if (hotel.getQryStartTime() != null) {
            criteria.ge("updateTime", hotel.getQryStartTime());
        }

        if (hotel.getQryEndTime() != null) {
            criteria.le("updateTime", hotel.getQryEndTime());
        }

        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }

        if (pageInfo == null) {
            return hotelDao.findByCriteria(criteria);
        }
        return hotelDao.findByCriteria(criteria, pageInfo);
    }

    public List<Map<String, String>> listCoordinates(String cityId, String rankingLimit) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        if (cityId.length() <= 4) {
            cityId = cityId + "00";
        }
        criteria.eq("cityId", Long.parseLong(cityId));
//        criteria.le("ranking", Integer.parseInt(rankingLimit));
        criteria.eq("status", ProductStatus.UP);
        List<Hotel> hotelList = hotelDao.findByCriteria(criteria);
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        for (Hotel hotel : hotelList) {
            Map<String, String> itemMap = new HashMap<String, String>();
            itemMap.put("hotelId", Long.toString(hotel.getId()));
            itemMap.put("scenicName", hotel.getName());
            itemMap.put("address", hotel.getExtend().getAddress());
            itemMap.put("longitude", Double.toString(hotel.getExtend().getLongitude()));
            itemMap.put("latitude", Double.toString(hotel.getExtend().getLatitude()));
            resultList.add(itemMap);
        }
        return resultList;
    }

    public void saveHotel(Hotel hotel) {
        HotelExtend hotelExtend = hotel.getExtend();
        hotelExtend.setGcjLat(hotelExtend.getLatitude());
        hotelExtend.setGcjLng(hotelExtend.getGcjLng());
        hotelDao.save(hotel);
        hotelExtend.setId(hotel.getId());
        hotelExtendService.saveExtend(hotelExtend);
    }

    public void updateHotel(Hotel hotel) {
        HotelExtend hotelExtend = hotel.getExtend();
        hotelDao.update(hotel);
        hotelExtend.setId(hotel.getId());
        hotelExtendService.updateExtend(hotelExtend);
    }

    public void doChangeStatus(List<Long> ids, ProductStatus status) {
        if (!ids.isEmpty()) {
            List<Hotel> hotels = getHotelListByIds(ids);
            for (Hotel hotel : hotels) {
                hotel.setStatus(status);
                hotel.setUpdateTime(new Date());
                hotelDao.update(hotel);
            }

        }
    }

    public List<Hotel> getHotelListByIds(List<Long> ids) {
        List<Hotel> hotels = new ArrayList<Hotel>();
        for (Long id : ids) {
            Hotel hotel = get(id);
            hotels.add(hotel);
        }
        return hotels;
    }

    public void delLabelItems(List<Long> ids) {

        for (Long id : ids) {
            labelItemService.delItemListByTargetIdId(id, TargetType.PRODUCT);
        }

    }

    public List<Hotel> getCheckingList(Hotel hotel, SysUser loginUser, Page pageInfo, Boolean siteAdmin) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        createCriteria(hotel, "updateTime", "desc");
        if (!siteAdmin) {
            criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
            criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
        }
        if (hotel.getQryStartTime() != null) {
            criteria.ge("updateTime", hotel.getQryStartTime());
        }

        if (hotel.getQryEndTime() != null) {
            criteria.le("updateTime", hotel.getQryEndTime());
        }
        return hotelDao.findByCriteria(criteria, pageInfo);
    }

    public void delete(Hotel hotel) {
        hotelDao.delete(hotel);
    }

    public void addComment(Long id, Integer score) {
        if (score == null || id == null) {
            return;
        }
        Hotel hotel = get(id);
        if (hotel == null) {
            return;
        }
        HotelExtend hotelExtend = hotel.getExtend();
        if (hotelExtend == null) {
            return;
        }
        Integer num = hotelExtend.getCommentNum();
        if (num == null) {
            num = 0;
        }
        Integer oldScore;
        if (hotel.getScore() == null) {
            oldScore = 0;
        } else {
            oldScore = hotel.getScore().intValue();
        }
        Integer newScore = (oldScore * num + score) / (num + 1);
        hotel.setScore(newScore.floatValue());
        num++;
        hotelExtend.setCommentNum(num);
        update(hotel);
        indexHotel(hotel);
    }

    public Float getMinPriceByHotel(Hotel hotel) {
        List<HotelPriceCalendar> dateList = hotelPriceService.listCruiseShipDates(hotel, new Date(), null);
        List<Float> priceList = Lists.transform(dateList, new Function<HotelPriceCalendar, Float>() {
            @Override
            public Float apply(HotelPriceCalendar hotelPriceCalendar) {
                return hotelPriceCalendar.getMember();
            }
        });
        return Collections.min(priceList);
    }

    public List<Hotel> getHotelListIndexData(Hotel hotel, Page page, String... orderProperties) {

/*
        SELECT
        p.id as id,
                p.name as name,
        pimg.path as path,
                hed.address as address,
        min(hpc.member) as price
        FROM
        product p
        LEFT JOIN productimage pimg ON pimg.productId = p.id and pimg.targetId = p.id
        LEFT JOIN hotel_extend hed on hed.id = p.id
        LEFT JOIN hotel_price_calendar hpc on hpc.hotelId = p.id
        WHERE
        p.source = 'LXB'
        AND p.proType = 'hotel'
        AND p. STATUS = 'UP'
        AND p.origin_id is null
        AND pimg.coverFlag = 1
        GROUP BY
        p.id
        ORDER BY min(hpc.member) asc;*/

        List params = Lists.newArrayList();

        StringBuffer sb = new StringBuffer("SELECT");
        sb.append(" p.id as id,").append(" p.name as name,").append(" pimg.path as cover,")
                .append(" hed.address as address,").append(" hpc.price as price,").append(" p.source as sourceStr");

        sb.append(" FROM product p");
        sb.append(" JOIN hotel h ON p.id = h.productId");
        sb.append(" LEFT JOIN productimage pimg ON pimg.productId = p.id");
        sb.append(" LEFT JOIN hotel_extend hed ON hed.id = p.id");
        sb.append(" LEFT JOIN (select hotelId, min(member) AS price from hotel_price_calendar GROUP BY hotelId) hpc ON hpc.hotelId = p.id");
        sb.append(" WHERE p.origin_id is null");
        sb.append(" AND p.status = ?");
        params.add(ProductStatus.UP.toString());
        sb.append(" AND (p.source = ? OR p.source = ?)");
        params.add(ProductSource.LXB.toString());
        params.add(ProductSource.ELONG.toString());
        sb.append(" AND hpc.price is not null");
        sb.append(" AND p.proType = ?");
        params.add(ProductType.hotel.toString());
        sb.append(" AND pimg.coverFlag = ?");
        params.add(1);

        if (hotel.getCityId() != null) {
            String cityCode = hotel.getCityId().toString();
            if (cityCode.length() == 4) {
                sb.append(" AND p.cityId >= ? AND p.cityId < ?");
                params.add(cityCode + 00);
                params.add(cityCode + 99);
            } else if (cityCode.length() == 2) {
                sb.append(" AND p.cityId >= ? AND p.cityId < ?");
                params.add(cityCode + 0000);
                params.add(cityCode + 9999);
            } else {
                sb.append(" AND p.cityId = ?");
                params.add(hotel.getCityId());
            }
        }

        if (orderProperties != null && orderProperties.length > 0 && orderProperties.length == 1) {
            sb.append(" ORDER BY "+ orderProperties[0] + " asc");
        } else if (orderProperties != null && orderProperties.length > 0 && orderProperties.length == 2) {
            sb.append(" ORDER BY "+ orderProperties[0] + " " + orderProperties[1] + "");
        }

        List<Map<String, Object>> mapList = hotelDao.findEntitiesBySQL4(sb.toString(), page, params.toArray());

        List<Hotel> hotels = Lists.transform(mapList, new Function<Map<String, Object>, Hotel>() {
            @Override
            public Hotel apply(Map<String, Object> map) {
                try {
                    return (Hotel) MapUtils.mapToObject(map, Hotel.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new Hotel();
            }
        });
        return hotels;
    }
}
