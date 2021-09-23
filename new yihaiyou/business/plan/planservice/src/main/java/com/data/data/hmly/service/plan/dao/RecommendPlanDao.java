package com.data.data.hmly.service.plan.dao;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.framework.hibernate.DataAccess;

@Repository
public class RecommendPlanDao extends DataAccess<RecommendPlan> {

    public void clear() {
        getHibernateTemplate().clear();
    }

	

}
