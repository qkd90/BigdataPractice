package com.data.spider.service.tb;


import com.data.spider.service.dao.RecommendPlanDao;
import com.data.spider.service.pojo.tb.RecommendPlan;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecommendPlanService {
    @Resource
    private RecommendPlanDao recommendPlanDao;

    public void save(RecommendPlan recommendPlan) {
        recommendPlanDao.save(recommendPlan);
    }


    public void update(RecommendPlan recommendPlan) {

        recommendPlanDao.update(recommendPlan);
    }
    public List<RecommendPlan> gets(int size, Criteria<RecommendPlan> c) {
        Page page = new Page(1, size);

        List<RecommendPlan> dis = recommendPlanDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
}
