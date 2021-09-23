package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.plan.dao.RecommendPlanTagDao;
import com.data.data.hmly.service.plan.entity.RecommendPlanTag;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by HMLY on 2015/12/21.
 */
@Service
public class RecommednPlanTagService {

    @Resource
    private RecommendPlanTagDao recommendPlanTagDao;

    public RecommendPlanTag get(Long id) {
        return recommendPlanTagDao.load(id);
    }

    public RecommendPlanTag save(RecommendPlanTag recommendPlanTag) {
        recommendPlanTagDao.save(recommendPlanTag);
        return recommendPlanTag;
    }

    public List<RecommendPlanTag> listAll() {
        return list(new RecommendPlanTag(), null);
    }

    public List<RecommendPlanTag> list(RecommendPlanTag recommendPlanTag, Page page, String... orderProperties) {
        Criteria<RecommendPlanTag> criteria = createCriteria(recommendPlanTag, orderProperties);
        if (page == null) {
            return recommendPlanTagDao.findByCriteria(criteria);
        }
        return recommendPlanTagDao.findByCriteria(criteria, page);
    }

    public Criteria<RecommendPlanTag> createCriteria(RecommendPlanTag recommendPlanTag, String... orderProperties) {
        Criteria<RecommendPlanTag> criteria = new Criteria<RecommendPlanTag>(RecommendPlanTag.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(recommendPlanTag.getName())) {
            criteria.like("name", recommendPlanTag.getName());
        }

        return criteria;
    }
}
