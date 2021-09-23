package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.plan.dao.PlanDao;
import com.data.data.hmly.service.plan.dao.PlanStatisticDao;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanStatistic;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.vo.PlanSolrEntity;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanService extends SolrIndexService<Plan, PlanSolrEntity> {

    Logger logger = Logger.getLogger(PlanService.class);

    @Resource
    private PlanDao planDao;
    @Resource
    private PlanStatisticDao planStatisticDao;
    @Resource
    private TbAreaDao areaDao;
    @Resource
    private LabelService labelService;
    @Resource
    private RecommendPlanService recommendPlanService;

    public Plan get(Long id) {
		return planDao.load(id);
	}

    public Plan getNoCache(Long id) {
        SessionFactory sf = planDao.getHibernateTemplate().getSessionFactory();
        Session session = sf.getCurrentSession();
        session.clear();
        return planDao.load(id);
    }

    public Plan save(Plan plan) {
        planDao.save(plan);
        return plan;
    }

    public List<Plan> listAll() {
        return list(new Plan(), null);
    }

    public List<Plan> listMyPlan(Long userId, Boolean completed, Page page) {
        Criteria<Plan> criteria = new Criteria<Plan>(Plan.class);
        criteria.eq("user.id", userId);
        criteria.eq("status", 1);
        if (completed != null) {
            criteria.eq("completeFlag", completed);
        }
        criteria.orderBy(Order.desc("startTime"));
        return planDao.findByCriteria(criteria, page);
    }

    public List<Plan> listMyPlan(Long userId, Page page) {
        Criteria<Plan> criteria = new Criteria<Plan>(Plan.class);
        if (userId != null) {
            criteria.eq("user.id", userId);
        }
        criteria.eq("status", 1);
        criteria.orderBy(Order.desc("startTime"));
        return planDao.findByCriteria(criteria, page);
    }

    public Long countMyPlan(Long userId, Boolean completed) {
        //
        Criteria<Plan> criteria = new Criteria<Plan>(Plan.class);
        criteria.eq("user.id", userId);
        criteria.eq("status", 1);
        if (completed != null) {
            criteria.eq("completeFlag", completed);
        }
        criteria.setProjection(Projections.rowCount());
        return planDao.findLongCriteria(criteria);
    }

    public List<Plan> list(Plan plan, Page page, String... orderProperties) {
        Criteria<Plan> criteria = createCriteria(plan, orderProperties);
        criteria.createCriteria("planStatistic", "planStatistic");
        if (page == null) {
            return planDao.findByCriteria(criteria);
        }
        return planDao.findByCriteria(criteria, page);
    }

    public Criteria<Plan> createCriteria(Plan plan, String... orderProperties) {
        Criteria<Plan> criteria = new Criteria<Plan>(Plan.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(plan.getName())) {
            criteria.like("name", plan.getName());
        }
        if (plan.getUser() != null) {
            criteria.eq("user.id", plan.getUser().getId());
        }
        if (plan.getCity() != null) {
            criteria.eq("city.id", plan.getCity().getId());
        }
        if (plan.getStatus() != null) {
            criteria.eq("status", plan.getStatus());
        }
        if (plan.getValid() != null) {
            criteria.eq("valid", plan.getValid());
        }
        if (plan.getLabelItems() != null && !plan.getLabelItems().isEmpty()) {
            DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
            for (LabelItem labelItem : plan.getLabelItems()) {
                criterion.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
            }
            criterion.addOrder(Order.asc("order"));
        }
        return criteria;
    }


    // 批量删除
    public void delByIds(String planIds, User user) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : planIds.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<Plan> criteria = new Criteria<Plan>(Plan.class);
        criteria.in("id", idList);
        criteria.eq("user.id", user.getId());
        List<Plan> planList = planDao.findByCriteria(criteria);
        if (!planList.isEmpty()) {
            for (Plan plan : planList) {
                // 1表示有效，其他值表示无效
                plan.setStatus(-1);
            }
            planDao.save(planList);
        }
    }

    public void delById(Long id) {
        Plan plan = get(id);
        plan.setStatus(-1);
        planDao.update(plan);
        if (plan.getSourceId() == null || plan.getSourceId() < 1) {
            return;
        }
        RecommendPlan recommendPlan = recommendPlanService.get(plan.getSourceId());
        if (recommendPlan != null) {
            recommendPlan.setLineId(null);
            recommendPlanService.update(recommendPlan);
        }
    }

    public void update(Plan plan) {
        planDao.update(plan);
    }

    public void saveStatistic(PlanStatistic planStatistic) {
        planStatisticDao.save(planStatistic);
    }

    public List<Plan> getPlanLabels(Plan info, TbArea area, String tagIds, Page pageInfo) {

        Criteria<Plan> criteria = new Criteria<Plan>(Plan.class);

        if (info != null) {
            criteria.like("name", "%" + info.getName() + "%");
        }
        criteria.eq("status", 1);
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                criteria.eq("city", area);
            }

        }
        List<Plan> infos = new ArrayList<Plan>();
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = planDao.findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {
            infos = planDao.findByCriteria(criteria, pageInfo);
        }
        return infos;
    }

    public void updateStatistic(PlanStatistic planStatistic) {
        planStatisticDao.update(planStatistic);
    }

    public Plan addCollect(Long id) {
        Plan plan = get(id);
        Integer collectNum = plan.getPlanStatistic().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        plan.getPlanStatistic().setCollectNum(collectNum);
        planDao.update(plan);
        return plan;
    }

    public Integer deleteCollect(Long id) {
        Plan plan = get(id);
        Integer collectNum = plan.getPlanStatistic().getCollectNum();
        if (collectNum == null || collectNum == 0) {
            return 0;
        }
        collectNum--;
        plan.getPlanStatistic().setCollectNum(collectNum);
        planDao.update(plan);
        return collectNum;
    }

    public Integer getCollectNum(Long id) {
        Plan plan = get(id);
        Integer collectNum = plan.getPlanStatistic().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        return collectNum;
    }

    public void addQuoteNum(Long id) {
        Plan plan = get(id);
        Integer quoteNum = plan.getPlanStatistic().getQuoteNum();
        if (quoteNum == null) {
            quoteNum = 0;
        }
        quoteNum++;
        plan.getPlanStatistic().setQuoteNum(quoteNum);
        planDao.update(plan);
    }

    public void indexPlans(Plan plan) {
        Clock clock = new Clock();
        int pageNo = 1;
        int pageSize = 100;
        int processed = 0;
        int total;
        plan.setStatus(1);
        while (true) {
            Page page = new Page(pageNo, pageSize);
            List<Plan> list = list(plan, page);
            processed += list.size();
            total = page.getTotalCount();
            if (list.isEmpty()) {
                break;
            }
            List<PlanSolrEntity> entities = Lists.transform(list, new Function<Plan, PlanSolrEntity>() {
                @Override
                public PlanSolrEntity apply(Plan plan) {
                    System.out.println("planId: " + plan.getId());
                    return new PlanSolrEntity(plan);
                }
            });
            index(entities);
            logger.info("save 100 record cost " + clock.elapseTime() + "ms");
            if (processed >= total) {
                break;
            }
            pageNo += 1;
            list.clear();
            entities.clear();
        }
        logger.info("finish in " + clock.totalTime() + "ms with " + processed);
    }

    public void indexPlan(Plan plan) {
        try {
            if (plan.getStatus() != 1) {
                // 删除状态不为正常的景点索引
                UpdateResponse updateResponse = deleteIndexByEntityId(plan.getId(), SolrType.scenic_info);
                if (updateResponse.getStatus() != 0) {
                    logger.error("发生错误: plan#" + plan.getId() + "删除索引失败!");
                } else {
                    logger.info("操作成功: plan#" + plan.getId() + "删除索引成功!");
                }
            } else {
                PlanSolrEntity entity = new PlanSolrEntity(plan);
                List<PlanSolrEntity> entities = Lists.newArrayList(entity);
                index(entities);
            }
        } catch (Exception e) {
            logger.error("未知异常，plan#" + plan.getId() + "索引失败", e);
        }
    }

    public List<Plan> getPlansInPlanModule() {
        Label label = new Label();
        label.setName("行程规划热门行程");
        return this.getPlanByLabel(label, new Page(1, 6), new Plan());
    }

    public List<Plan> getHotPlans() {
        Label label = new Label();
        label.setName("行程规划热门行程");
        return this.getPlanByLabel(label, null, new Plan());
    }

    private List<Plan> getPlanByLabel(Label label, Page page, Plan plan) {
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        plan.setLabelItems(list);
        return this.list(plan, page);
    }
}
