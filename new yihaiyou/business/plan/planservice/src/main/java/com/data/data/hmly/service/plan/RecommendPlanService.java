package com.data.data.hmly.service.plan;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.util.HTMLFilterUtils;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.plan.dao.RecommendPlanDao;
import com.data.data.hmly.service.plan.dao.RecommendPlanDayDao;
import com.data.data.hmly.service.plan.dao.RecommendPlanTripDao;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanPhoto;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.plan.vo.SimpleRecommendPlanPhoto;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.RestaurantService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyRestaurant;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.vo.LabelItemsVo;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class RecommendPlanService extends SolrIndexService<RecommendPlan, RecommendPlanSolrEntity> {

    Logger logger = Logger.getLogger(RecommendPlanService.class);

    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private RecommendPlanDao recommendPlanDao;
    @Resource
    private RecommendPlanDayDao recommendPlanDayDao;
    @Resource
    private RecommendPlanTripDao recommendPlanTripDao;
    @Resource
    private LabelService labelService;
    @Resource
    private LabelItemService labelItemService;
    @Resource
    private TbAreaDao areaDao;
    @Resource
    private PlanService planService;


    private static final int LVXBANG_DESTINATION_DETAIL_HOT_RECOMMEND_PLAN_NUMBER = 6;
    private static final Logger LOGGER = Logger.getLogger(RecommendPlanService.class);

    public void save(RecommendPlan recommendPlan) {
        recommendPlanDao.save(recommendPlan);
        if (recommendPlan.getRecommendPlanDays() == null) {
            return;
        }
        for (RecommendPlanDay recommendPlanDay : recommendPlan.getRecommendPlanDays()) {
            recommendPlanDay.setRecommendPlan(recommendPlan);
            recommendPlanDayDao.save(recommendPlanDay);
            if (recommendPlanDay.getRecommendPlanTrips() == null) {
                return;
            }
            for (RecommendPlanTrip recommendPlanTrip : recommendPlanDay.getRecommendPlanTrips()) {
                recommendPlanTrip.setRecommendPlan(recommendPlan);
                recommendPlanTrip.setRecommendPlanDay(recommendPlanDay);
                recommendPlanTripDao.save(recommendPlanTrip);
            }
        }
    }

    public Integer getViewNum(Long recplanId) {
        String sql = "select view_num as result from recommend_plan where id=?";
        BigDecimal viewNum = recommendPlanDao.findIntegerBySQL(sql, recplanId);
        return viewNum == null ? 0 : viewNum.intValue();
    }

    public Integer getQuoteNum(Long recplanId) {
        String sql = "select quote_num as result from recommend_plan where id=?";
        BigDecimal quoteNum = recommendPlanDao.findIntegerBySQL(sql, recplanId);
        return quoteNum == null ? 0 : quoteNum.intValue();
    }

    public void updateViewNum(Long recplanId, Integer viewNum) {
        String sql = "update recommend_plan set view_num =? where id=?";
        recommendPlanDao.updateBySQL(sql, viewNum, recplanId);
    }

    public void doHandleRecommendPlanLabel(Long targetId, List<LabelItem> items) {
        List<LabelItem> labelItems = labelItemService.getLabelItemsByParent(targetId, 44L, LabelStatus.USE);
        labelItemService.deleteAll(labelItems);
        List<LabelItem> saveItems = new ArrayList<LabelItem>();
        for (LabelItem labelItem : items) {
            LabelItem item = new LabelItem();
            Label label = new Label();
            label.setId(labelItem.getLabel().getId());
            item.setLabel(label);
            item.setTargetId(labelItem.getTargetId());
            item.setTargetType(labelItem.getTargetType());
            item.setOrder(0);
            item.setCreateTime(new Date());
            saveItems.add(item);
        }
        labelItemService.saveAll(saveItems);
    }

    public void update(RecommendPlan targetRecplan) {
        Long recplanId = targetRecplan.getId();
        RecommendPlan sourceRecommendPlan = recommendPlanDao.load(recplanId);
        sourceRecommendPlan = updateBaseRecplanInfo(sourceRecommendPlan, targetRecplan);
        recommendPlanDao.update(sourceRecommendPlan);
        if (sourceRecommendPlan.getLineId() == null || sourceRecommendPlan.getLineId() < 1) {
            return;
        }
        Plan plan = planService.get(sourceRecommendPlan.getLineId());
        if (plan != null) {
            plan.setSourceId(sourceRecommendPlan.getId());
            planService.update(plan);
        }
    }

    public void updateRecplanAfterAddDay(Long recplanId) {
        String sql = "update recommend_plan set days = days + 1, scenics = scenics + 1 where id=?";
        recommendPlanDao.updateBySQL(sql, recplanId);
    }

    public void updateRecplanAfterAddTrip(Long recplanId) {
        String sql = "update recommend_plan set scenics = scenics + 1 where id=?";
        recommendPlanDao.updateBySQL(sql, recplanId);
    }

    public void updateRecplanAfterDeleteDay(Long recplanId, Integer scenics) {
        Integer deleteScenics;
        if (scenics != null && scenics > 0) {
            deleteScenics = scenics;
        } else {
            deleteScenics = 0;
        }
        String updateRecplanSql = "update recommend_plan set days = days -1, scenics = scenics - " + deleteScenics + " where id=?";
        recommendPlanDao.updateBySQL(updateRecplanSql, recplanId);
    }

    public void updateRecplanAfterDeleteTrip(Long recplanId) {
        String updateRecplanSql = "update recommend_plan set scenics = scenics - 1 where id =?";
        recommendPlanDao.updateBySQL(updateRecplanSql, recplanId);
    }

    public Integer getStatus(Long id) {
        String sql = "select status as result from recommend_plan where id=?";
        BigDecimal status = recommendPlanDao.findIntegerBySQL(sql, id);
        return status == null ? 0 : status.intValue();
    }

    private RecommendPlan updateBaseRecplanInfo(RecommendPlan sourceRecplan, RecommendPlan targetRecplan) {
        if (targetRecplan.getScenicId() != null) {
            sourceRecplan.setScenicId(targetRecplan.getScenicId());
        }
        if (targetRecplan.getCity() != null && targetRecplan.getCity().getId() != null) {
            TbArea city = new TbArea();
            city.setId(targetRecplan.getCity().getId());
            sourceRecplan.setCity(city);
        }
        sourceRecplan.setCityIds(targetRecplan.getCityIds());
        if (targetRecplan.getProvince() != null) {
            TbArea province = new TbArea();
            province.setId(targetRecplan.getProvince().getId());
            sourceRecplan.setProvince(province);
        }
        sourceRecplan.setPlanName(targetRecplan.getPlanName());
        sourceRecplan.setDescription(targetRecplan.getDescription());
        sourceRecplan.setCoverPath(targetRecplan.getCoverPath());
        sourceRecplan.setDays(targetRecplan.getDays());
        sourceRecplan.setStatus(targetRecplan.getStatus());
        sourceRecplan.setStartTime(targetRecplan.getStartTime());
        sourceRecplan.setValid(1);
        sourceRecplan.setScenics(targetRecplan.getScenics());
        sourceRecplan.setDeleteFlag(2);
        // 注意! 游记天要排序!
        sortRecommendPlanDay(sourceRecplan);
        return updateBaseRecplanDayInfo(sourceRecplan, targetRecplan);
    }

    private RecommendPlan updateBaseRecplanDayInfo(RecommendPlan sourceRecplan, RecommendPlan targetRecplan) {
        for (int i = 0; i < targetRecplan.getRecommendPlanDays().size(); i++) {
            RecommendPlanDay sourceDay = sourceRecplan.getRecommendPlanDays().get(i);
            RecommendPlanDay targetDay = targetRecplan.getRecommendPlanDays().get(i);
            sourceDay.setDay(targetDay.getDay());
            sourceDay.setModifyTime(new Date());
            sourceDay.setScenics(targetDay.getScenics());
            sourceDay.setCitys(targetDay.getCitys());
            sourceDay.setDescription(targetDay.getDescription());
            // 注意! 游记节点要排序!
            sortRecommendPlanTrip(sourceDay);
            updateBaseRecplanTripInfo(sourceDay, targetDay);
        }
        return sourceRecplan;
    }

    private RecommendPlanDay updateBaseRecplanTripInfo(RecommendPlanDay sourceDay, RecommendPlanDay targetDay) {
        if (sourceDay.getRecommendPlanTrips() == null || sourceDay.getRecommendPlanTrips().isEmpty()) {
            return null;
        }
        for (int i = 0; i < targetDay.getRecommendPlanTrips().size(); i++) {
            RecommendPlanTrip sourceTrip = sourceDay.getRecommendPlanTrips().get(i);
            RecommendPlanTrip targetTrip = targetDay.getRecommendPlanTrips().get(i);
            sourceTrip.setTripType(targetTrip.getTripType());
            // 行程为美食时候特殊处理
            if (targetTrip.getTripType() != null && targetTrip.getTripType() == 2 && targetTrip.getDelicacy() != null && targetTrip.getDelicacy().getId() != null) {
                Delicacy delicacy = delicacyService.get(targetTrip.getDelicacy().getId());
                sourceTrip.setDelicacy(delicacy);
                if (getRestaurantIdByDelicacy(delicacy) != null) {
                    sourceTrip.setScenicId(getRestaurantIdByDelicacy(delicacy));
                }
            } else {
                sourceTrip.setScenicId(targetTrip.getScenicId());
            }
            sourceTrip.setOrderNum(targetTrip.getOrderNum());
            sourceTrip.setSort(targetTrip.getSort());
            sourceTrip.setModifyTime(new Date());
            sourceTrip.setExa(targetTrip.getExa());
            sourceTrip.setCityCode(targetTrip.getCityCode());
            sourceTrip.setScenicName(targetTrip.getScenicName());
            sourceTrip.setCoverImg(targetTrip.getCoverImg());
        }
        return sourceDay;
    }

    private Long getRestaurantIdByDelicacy(Delicacy delicacy) {
        if (delicacy != null) {
            List<DelicacyRestaurant> restaurants = delicacy.getRestaurants();
            if (restaurants.isEmpty()) {
                return null;
            }
            // 默认取出该美食分数最高的餐厅
            Collections.sort(restaurants, new Comparator<DelicacyRestaurant>() {
                @Override
                public int compare(DelicacyRestaurant o1, DelicacyRestaurant o2) {
                    return -(o1.getRestaurant().getScore().intValue() - o2.getRestaurant().getScore().intValue());
                }
            });
            return restaurants.get(0).getRestaurant().getId();
        }
        return null;
    }

    public void delById(Long id) {
        RecommendPlan recommendPlan = recommendPlanDao.load(id);
        recommendPlan.setDeleteFlag(1);
        recommendPlanDao.update(recommendPlan);
        Plan plan = planService.get(recommendPlan.getLineId());
        if (plan != null) {
            plan.setSourceId(null);
            planService.update(plan);
        }
    }

    public List<RecommendPlan> listTopOnIndex(int count) {
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setShowIndex(true);
        return list(recommendPlan, new Page(0, count));
    }

    public RecommendPlan get(Long id) {
        return recommendPlanDao.load(id);
    }

    private void sortRecommendPlanDay(RecommendPlan recommendPlan) {
        // 排序游记的每一天
        Collections.sort(recommendPlan.getRecommendPlanDays(), new Comparator<RecommendPlanDay>() {
            @Override
            public int compare(RecommendPlanDay o1, RecommendPlanDay o2) {
                return o1.getDay() - o2.getDay();
            }
        });
    }

    private void sortRecommendPlanTrip(RecommendPlanDay recommendPlanDay) {
        // 排序游记某天的每一个节点
        Collections.sort(recommendPlanDay.getRecommendPlanTrips(), new Comparator<RecommendPlanTrip>() {
            @Override
            public int compare(RecommendPlanTrip o1, RecommendPlanTrip o2) {
                if (o1.getSort() == null && o2.getSort() == null) {
                    return o1.getId().intValue() - o2.getId().intValue();
                } else if (o1.getSort() == null || o2.getSort() == null) {
                    return (o1.getSort() == null ? Integer.MAX_VALUE : o1.getSort()) - (o2.getSort() == null ? Integer.MAX_VALUE : o2.getSort());
                } else {
                    return o1.getSort() - o2.getSort();
                }
            }
        });
    }

    /**
     * 对行程节点补充分数数据
     *
     * @param trip
     * @return
     */
    private Integer getScore(RecommendPlanTrip trip) {
        Integer score;
        if (trip.getTripType() == null || trip.getScenicId() == null) {
            return null;
        }
        switch (trip.getTripType()) {
            case 1:
                score = scenicInfoService.getScenicScore(trip.getScenicId());
                break;
            case 2:
                score = restaurantService.getResScore(trip.getScenicId()).intValue();
                break;
            default:
                score = 100;
        }
        return score;
    }

    private List<SimpleRecommendPlanPhoto> makePhotos(RecommendPlanTrip trip) {
        List<SimpleRecommendPlanPhoto> photos = new ArrayList<SimpleRecommendPlanPhoto>();
        for (RecommendPlanPhoto photo : trip.getRecommendPlanPhotos()) {
            SimpleRecommendPlanPhoto simpleRecommendPlanPhoto = new SimpleRecommendPlanPhoto();
            simpleRecommendPlanPhoto.setId(photo.getId());
            simpleRecommendPlanPhoto.setPhotoLarge(photo.getPhotoLarge());
            simpleRecommendPlanPhoto.setWidth(photo.getWidth());
            simpleRecommendPlanPhoto.setHeight(photo.getHeight());
            simpleRecommendPlanPhoto.setSort(photo.getSort());
            photos.add(simpleRecommendPlanPhoto);
        }
        return photos;
    }


    /**
     * build静态页面时候需要补充的参数
     *
     * @param recommendPlan
     */
    public void makeDetailRecplanOnBuildOrEdit(RecommendPlan recommendPlan) {
        sortRecommendPlanDay(recommendPlan);
        // 处理标签
        List<LabelItem> labelItems = labelItemService.getLabelItemsByParent(recommendPlan.getId(), 44L, LabelStatus.USE);
        if (labelItems != null && !labelItems.isEmpty()) {
            Set<LabelItemsVo> labelItemsVos = new HashSet<LabelItemsVo>();
            for (LabelItem labelItem : labelItems) {
                LabelItemsVo labelItemsVo = new LabelItemsVo();
                labelItemsVo.setId(labelItem.getId());
                labelItemsVo.setLabelId(labelItem.getLabel().getId());
                labelItemsVo.setTargetId(labelItem.getTargetId());
                labelItemsVo.setTargetType(TargetType.RECOMMEND_PLAN);
                labelItemsVos.add(labelItemsVo);
            }
            recommendPlan.setLabelItemsVos(labelItemsVos);
        }
        // 处理照片, 描述文本, trip分数
        for (RecommendPlanDay day : recommendPlan.getRecommendPlanDays()) {
            day.setDescription(HTMLFilterUtils.doHTMLFilter(day.getDescription()));
            sortRecommendPlanTrip(day);
            for (RecommendPlanTrip trip : day.getRecommendPlanTrips()) {
                trip.setRecplanId(recommendPlan.getId());
                trip.setRecdayId(day.getId());
                trip.setRectripId(trip.getId());
                trip.setPhotos(makePhotos(trip));
                trip.setScore(getScore(trip));
                trip.setExa(HTMLFilterUtils.doHTMLFilter(trip.getExa()));
            }
        }
    }


//    /**
//     * solr索引时候需要补充的参数
//     *
//     * @param recommendPlan
//     */
//    private void makeDetailRecplanOnIndex(RecommendPlan recommendPlan) {
//        Integer picNum = 0;
//        for (RecommendPlanDay day : recommendPlan.getRecommendPlanDays()) {
//            for (RecommendPlanTrip trip : day.getRecommendPlanTrips()) {
//                if (trip.getRecommendPlanPhotos() != null) {
//                    picNum += trip.getRecommendPlanPhotos().size();
//                }
//            }
//        }
//        recommendPlan.setPicNum(picNum);
//    }

    public List<RecommendPlan> list(RecommendPlan recommendPlan, Page page, String... orderProperties) {
        Criteria<RecommendPlan> criteria = createCriteria(recommendPlan, orderProperties);
        if (page == null) {
            return recommendPlanDao.findByCriteria(criteria);
        }
        return recommendPlanDao.findByCriteria(criteria, page);
    }


    public List<RecommendPlan> list(Criteria<RecommendPlan> criteria) {
        return recommendPlanDao.findByCriteria(criteria);
    }

    public List<RecommendPlan> listAll() {
        RecommendPlan condition = new RecommendPlan();
        condition.setStatus(2);
        condition.setDeleteFlag(2);
        return list(condition, null);
    }

    public List<RecommendPlan> getMyRecplan(User user, Boolean started, Page page) {
        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);
        criteria.eq("user.id", user.getId());
        if (started) {
            criteria.eq("status", 2);
        } else {
            criteria.eq("status", 1);
        }
        criteria.ne("deleteFlag", 1);
        criteria.orderBy(Order.desc("createTime"));
        return recommendPlanDao.findByCriteriaWithOutCount(criteria, page);
    }

    public Long countMyRecplan(User user, Boolean started) {
        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);
        criteria.eq("user.id", user.getId());
        if (started) {
            criteria.eq("status", 2);
        } else {
            criteria.eq("status", 1);
        }
        criteria.ne("deleteFlag", 1);
        criteria.setProjection(Projections.rowCount());
        return recommendPlanDao.findLongCriteria(criteria);
    }

    public List<RecommendPlan> getDelicacyRecommendPlan() {
        Label label = new Label();
        label.setName("美食之旅");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setLabelItems(list);
        return list(recommendPlan, null);
    }

    public List<RecommendPlan> getSeasonSelectRecplans() {
        Label label = new Label();
        label.setName("当季精选游记");
        return this.getRecplanByLabel(label, new Page(1, 6), new RecommendPlan());
    }

    public List<RecommendPlan> getHotRecommendPlans() {
        Label label = new Label();
        label.setName("热门游记");
        return this.getRecplanByLabel(label, new Page(1, 6), new RecommendPlan());
    }

    public List<RecommendPlan> getRecommendPlansInPlanModule() {
        Label label = new Label();
        label.setName("行程规划热门行程");
        return this.getRecplanByLabel(label, new Page(1, 6), new RecommendPlan());
    }

    public List<RecommendPlan> getHomeHotRecommendPlans() {
        Label label = new Label();
        label.setName("首页热门游记");
        RecommendPlan rr = new RecommendPlan();
        rr.setStatus(2);    // 2-上架
        return this.getRecplanByLabel(label, new Page(1, 5), rr);
    }

    public List<RecommendPlan> getGoodRecommendPlans() {
        Label label = new Label();
        label.setName("发现好游记");
        return this.getRecplanByLabel(label, new Page(1, 6), new RecommendPlan());
    }

    public List<RecommendPlan> getHomeGoodRecommendPlans() {
        Label label = new Label();
        label.setName("首页发现好游记");
        RecommendPlan rr = new RecommendPlan();
        rr.setStatus(2);    // 2-上架
        return this.getRecplanByLabel(label, new Page(1, 4), rr);
    }

    public List<RecommendPlan> getTrafficRecommendPlans() {
        Label label = new Label();
        label.setName("交通发现好游记");
        com.data.data.hmly.service.plan.entity.RecommendPlan rr = new com.data.data.hmly.service.plan.entity.RecommendPlan();
        rr.setStatus(2);    // 2-上架
        return this.getRecplanByLabel(label, new Page(1, 4), rr);
    }

    public List<RecommendPlan> getTrafficHotRecommendPlans() {
        Label label = new Label();
        label.setName("交通热门行程");
        com.data.data.hmly.service.plan.entity.RecommendPlan rr = new com.data.data.hmly.service.plan.entity.RecommendPlan();
        rr.setStatus(2);    // 2-上架
        return this.getRecplanByLabel(label, new Page(1, 5), rr);
    }

    public List<RecommendPlan> getHotRecommendPlans(Long cityCode) {
        RecommendPlan condition = new RecommendPlan();
        TbArea area = tbAreaService.getArea(cityCode);
        condition.setCity(area);
        Label label = new Label();
        label.setName("热门游记");
        return this.getRecplanByLabel(label, new Page(1, 6), condition);
    }

    public List<RecommendPlan> getThemeRecommendPlan() {
        Label label = new Label();
        label.setName("主题游记");
        return this.getRecplanByLabel(label, new Page(1, 7), new RecommendPlan());
    }

    public List<RecommendPlan> getRecommendPlanByLabel(String labelName, Page page) {
        Label label = new Label();
        label.setName(labelName);
        return this.getRecplanByLabel(label, page, new RecommendPlan());
    }

    private List<RecommendPlan> getRecplanByLabel(Label label, Page page, RecommendPlan recommendPlan) {
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        recommendPlan.setLabelItems(list);
        return this.list(recommendPlan, page);
    }

    public List<RecommendPlan> getUserDraftRecplan(Page page, Long userId) {
        RecommendPlan condition = new RecommendPlan();
        condition.setStatus(1);
        Member user = new Member();
        user.setId(userId);
        condition.setUser(user);
        return this.list(condition, page);
    }

    public List<RecommendPlan> getUserPublishedRecplan(Page page, Long userId) {
        RecommendPlan condition = new RecommendPlan();
        condition.setStatus(2);
        Member user = new Member();
        user.setId(userId);
        condition.setUser(user);
        return this.list(condition, page);
    }

    public List<RecommendPlan> getDestinationRecplan(TbArea area) {

        TbArea city = new TbArea();
        if (area.getLevel() < 2) {
            city.setId(area.getId() / 10000);
        } else {
            city.setId(area.getId());
        }
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setCity(city);
        recommendPlan.setStatus(2);
        return list(recommendPlan, new Page(0, 6));
    }

    public Criteria<RecommendPlan> createCriteria(RecommendPlan recommendPlan, String... orderProperties) {
        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (recommendPlan.getId() != null) {
            criteria.eq("id", recommendPlan.getId());
        }
        if (StringUtils.isNotBlank(recommendPlan.getCityIds())) {
            criteria.like("cityIds", recommendPlan.getCityIds());
        }
        if (recommendPlan.getCity() != null) {
            if (String.valueOf(recommendPlan.getCity().getId()).length() == 2) {
                criteria.between("city.id", recommendPlan.getCity().getId() * 10000, recommendPlan.getCity().getId() * 10000 + 9999);
            } else if (String.valueOf(recommendPlan.getCity().getId()).length() == 4) {
                criteria.between("city.id", recommendPlan.getCity().getId() * 100, recommendPlan.getCity().getId() * 100 + 99);
            } else {
                criteria.eq("city.id", recommendPlan.getCity().getId());
            }
        }
        if (recommendPlan.getProvince() != null) {
            criteria.eq("province.id", recommendPlan.getProvince().getId());
        }
        if (StringUtils.isNotBlank(recommendPlan.getPlanName())) {
            criteria.like("planName", recommendPlan.getPlanName());
        }
        if (StringUtils.isNotBlank(recommendPlan.getDescription())) {
            criteria.like("description", recommendPlan.getDescription());
        }
        if (recommendPlan.getDays() != null) {
            criteria.eq("days", recommendPlan.getDays());
        }
        if (recommendPlan.getScenics() != null) {
            criteria.eq("scenics", recommendPlan.getScenics());
        }
        if (recommendPlan.getValid() != null) {
            criteria.eq("valid", recommendPlan.getValid());
        }
        if (StringUtils.isNotBlank(recommendPlan.getTags())) {
            criteria.like("tags", recommendPlan.getTags());
        }
        if (recommendPlan.getSelected() != null) {
            criteria.eq("selected", recommendPlan.getSelected());
        }
        if (recommendPlan.getRecLoc() != null) {
            criteria.eq("recLoc", recommendPlan.getRecLoc());
        }
        if (recommendPlan.getStatus() != null) {
            criteria.eq("status", recommendPlan.getStatus());
        }
        if (recommendPlan.getDeleteFlag() != null) {
            criteria.eq("deleteFlag", recommendPlan.getDeleteFlag());
        }
        if (recommendPlan.getMark() != null) {
            criteria.eq("mark", recommendPlan.getMark());
        }
        if (StringUtils.isNotBlank(recommendPlan.getDataSource())) {
            criteria.like("dataSource", recommendPlan.getDataSource());
        }
        if (StringUtils.isNotBlank(recommendPlan.getDataSourceId())) {
            criteria.like("dataSourceId", recommendPlan.getDataSourceId());
        }
        if (StringUtils.isNotBlank(recommendPlan.getDataSourceCode())) {
            criteria.like("dataSourceCode", recommendPlan.getDataSourceCode());
        }
        if (StringUtils.isNotBlank(recommendPlan.getTagStr())) {
            criteria.like("tagStr", recommendPlan.getTagStr());
        }
        if (recommendPlan.getShowIndex() != null) {
            criteria.eq("showIndex", recommendPlan.getShowIndex());
        }
        if (recommendPlan.getStartId() != null) {
            criteria.ge("id", recommendPlan.getStartId());
        }
        if (recommendPlan.getEndId() != null) {
            criteria.le("id", recommendPlan.getEndId());
        }
        if (recommendPlan.getLabelItems() != null && !recommendPlan.getLabelItems().isEmpty()) {
            DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
//            criteria.orderBy("labelItem.order", "desc");
            for (LabelItem labelItem : recommendPlan.getLabelItems()) {
                criterion.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
            }
            criterion.addOrder(Order.asc("order"));
        }
        if (recommendPlan.getUser() != null) {
            criteria.eq("user.id", recommendPlan.getUser().getId());
        }
        return criteria;
    }

    /**
     * 批量索引, 可附加条件, 可附加自定义分页, 排序
     *
     * @param originCondition
     * @param customPage
     * @param orderProperties
     */
    public void indexRecommendPlan(RecommendPlan originCondition, Page customPage, String... orderProperties) {
        List<RecommendPlan> recommendPlans = new ArrayList<RecommendPlan>();
        List<RecommendPlanSolrEntity> entities = new ArrayList<RecommendPlanSolrEntity>();
        Clock clock = new Clock();
        int pageNo = 1;
        int pageSize = 100;
        int processed = 0;
        int total;
        Page page;
        RecommendPlan condition = new RecommendPlan();
        if (originCondition != null) {
            condition = originCondition;
        }
        condition.setStatus(2);
        condition.setDeleteFlag(2);
        while (true) {
            page = customPage != null ? customPage : new Page(pageNo, pageSize);
            recommendPlans = list(condition, page, orderProperties);
            // 已处理的记录数目
            processed += recommendPlans.size();
            // 符合条件总记录数目
            total = page.getTotalCount();
            if (recommendPlans.isEmpty()) {
                break;
            }
//            for (RecommendPlan recommendPlan : recommendPlans) {
//                makeDetailRecplanOnIndex(recommendPlan);
//            }
            entities = Lists.transform(recommendPlans, new Function<RecommendPlan, RecommendPlanSolrEntity>() {
                @Override
                public RecommendPlanSolrEntity apply(RecommendPlan recommendPlan) {
                    return new RecommendPlanSolrEntity(recommendPlan);
                }
            });
            index(entities);
            logger.info("Save " + pageSize + " recommendPlan records cost " + clock.elapseTime() + "ms");
            if (processed >= total) {
                break;
            }
            pageNo += 1;
            recommendPlans.clear();
            entities.clear();
        }
        logger.info("Index all recommendPlan in " + clock.totalTime() + "ms with " + processed + "records");
    }

    /**
     * 单条索引, 可引起索引删除
     *
     * @param recommendPlan
     */
    public void indexRecommendPlan(RecommendPlan recommendPlan) {
        try {
            if (recommendPlan.getStatus() != 2 || recommendPlan.getStatus() != 2) {
                // 删除草稿和删除的游记索引
                UpdateResponse updateResponse = deleteIndexByEntityId(recommendPlan.getId(), SolrType.recommend_plan);
                if (updateResponse == null || updateResponse.getStatus() != 0) {
                    logger.error("发生错误: recommend_plan#" + recommendPlan.getId() + "删除索引失败!");
                } else {
                    logger.error("操作成功: recommend_plan#" + recommendPlan.getId() + "删除索引成功!");
                }
            } else {
//                makeDetailRecplanOnIndex(recommendPlan);
                RecommendPlanSolrEntity entity = new RecommendPlanSolrEntity(recommendPlan);
                List<RecommendPlanSolrEntity> entities = Lists.newArrayList(entity);
                index(entities);
            }
        } catch (Exception e) {
            logger.error("未知异常，recommendPlan#" + recommendPlan.getId() + "索引失败", e);
        }
    }

    // 批量删除
    public void delByIds(String recommendIds, User user) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : recommendIds.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);
        criteria.in("id", idList);
        criteria.eq("user.id", user.getId());
        List<RecommendPlan> recommendPlanList = recommendPlanDao.findByCriteria(criteria);
        if (!recommendPlanList.isEmpty()) {
            for (RecommendPlan recommendPlan : recommendPlanList) {
                // 1草稿 2上架 3下架
                recommendPlan.setStatus(3);
            }
            recommendPlanDao.save(recommendPlanList);
        }
    }

    public List<RecommendPlan> getRecPlanLabels(Plan info, TbArea area, String tagIds, Page pageInfo) {
        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);
        criteria.isNotNull("city");
        criteria.eq("status", 2);
        if (info != null) {
            criteria.like("planName", "%" + info.getName() + "%");
        }
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                areas.add(area);
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                List<TbArea> areas = areaDao.findCityByCity(area.getId());
                areas.add(area);
                criteria.eq("city", area);
            }

        }
        List<RecommendPlan> infos = new ArrayList<RecommendPlan>();
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = recommendPlanDao.findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {
            infos = recommendPlanDao.findByCriteria(criteria, pageInfo);
        }
        return infos;

    }

    public RecommendPlan addCollect(Long id) {
        RecommendPlan recommendPlan = recommendPlanDao.load(id);
        Integer collectNum = recommendPlan.getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        recommendPlan.setCollectNum(collectNum);
        recommendPlanDao.update(recommendPlan);
        indexRecommendPlan(recommendPlan );
        return recommendPlan;
    }

    public Integer deleteCollect(Long id) {
        RecommendPlan recommendPlan = recommendPlanDao.load(id);
        Integer collectNum = recommendPlan.getCollectNum();
        if (collectNum == null || collectNum == 0) {
            return 0;
        }
        collectNum--;
        recommendPlan.setCollectNum(collectNum);
        recommendPlanDao.update(recommendPlan);
        return collectNum;
    }

    public Integer getCollectNum(Long id) {
        RecommendPlan recommendPlan = recommendPlanDao.load(id);
        Integer collectNum = recommendPlan.getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        return collectNum;
    }

    public Integer getBrowsingNum(Long id) {
        RecommendPlan recommendPlan = recommendPlanDao.load(id);
        if (recommendPlan == null) {
            return 0;
        }
        Integer browsingNum = recommendPlan.getViewNum();
        if (browsingNum == null) {
            browsingNum = 0;
        }
        return browsingNum;
    }

    public void flush(Object obj) {
        recommendPlanDao.evict(obj);
    }

    public void flushAll(Collection collection) {
        Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            flush(iterator.next());
        }
    }

    public void clear() {
        recommendPlanDao.clear();
    }

    public List<RecommendPlan> getRecplanByDestinationAndLabel(TbArea area,
                                                               Label searchLabel) {
        Label label = labelService.findUnique(searchLabel);
        List<RecommendPlan> plansByLabels = new ArrayList<RecommendPlan>();
        if (label != null) {
            plansByLabels = getHotRecPlanByLabel(area, label);
        }

        if (plansByLabels.size() == LVXBANG_DESTINATION_DETAIL_HOT_RECOMMEND_PLAN_NUMBER) {
            return plansByLabels;
        }
        List<RecommendPlan> plansByDestinations = getHotRecPlanByDest(area, new Page(0, LVXBANG_DESTINATION_DETAIL_HOT_RECOMMEND_PLAN_NUMBER));
        for (RecommendPlan plansByDestination : plansByDestinations) {
            if (plansByLabels.size() >= LVXBANG_DESTINATION_DETAIL_HOT_RECOMMEND_PLAN_NUMBER) {
                return plansByLabels;
            }
            if (!plansByLabels.contains(plansByDestination)) {
                plansByLabels.add(plansByDestination);
            }
        }

        return plansByLabels;
    }

    private List<RecommendPlan> getHotRecPlanByDest(TbArea area, Page page) {
        Clock clock = new Clock();

        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);

        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                areas.add(area);
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                criteria.eq("city", area);
            }

        }
        criteria.eq("status", 2);
        criteria.isNotNull("planName");
        criteria.isNotNull("coverPath");
        List<RecommendPlan> recommendPlanList = recommendPlanDao.findByCriteria(criteria, page);
        LOGGER.info("getHotRecPlanByDest cost " + clock.totalTime() + "ms");
        return recommendPlanList;
    }

    public List<RecommendPlan> getHotRecPlanByLabel(TbArea area,
                                                    Label searchLabel) {
        Criteria<RecommendPlan> criteria = new Criteria<RecommendPlan>(RecommendPlan.class);
        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
        criterion.add(Restrictions.eq("label.id", searchLabel.getId()));
        criterion.addOrder(Order.asc("order"));
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                areas.add(area);
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                criteria.eq("city", area);
            }

        }
        criteria.eq("status", 2);
        criteria.isNotNull("planName");
        criteria.isNotNull("coverPath");
        Page page = new Page(0, LVXBANG_DESTINATION_DETAIL_HOT_RECOMMEND_PLAN_NUMBER);
        return recommendPlanDao.findByCriteria(criteria, page);

    }

    public List<RecommendPlan> getInIdRange(Long startId, Long endId, int pageSize) {
        StringBuilder hql = new StringBuilder("from RecommendPlan where id>:startId");
        Map<String, Object> data = Maps.newHashMap();
        data.put("startId", startId);
        if (endId != null) {
            hql.append(" and id<=:endId");
            data.put("endId", endId);
        }
        hql.append(" and status=:status");
        data.put("status", 2);

        return recommendPlanDao.findByHQL2(hql.toString(), new Page(1, pageSize), data);
    }
}
