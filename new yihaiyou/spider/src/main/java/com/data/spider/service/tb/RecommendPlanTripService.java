package com.data.spider.service.tb;


import com.data.spider.service.dao.RecommendPlanTripDao;
import com.data.spider.service.pojo.tb.RecommendPlanTrip;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecommendPlanTripService {

    @Resource
    private RecommendPlanTripDao recommendPlanTripDao;


    public void save(RecommendPlanTrip recommendPlanTrip) {
        recommendPlanTripDao.save(recommendPlanTrip);

    }


    public void update(RecommendPlanTrip recommendPlanTrip) {

        recommendPlanTripDao.update(recommendPlanTrip);
    }

    public List<RecommendPlanTrip> gets(int size, Criteria<RecommendPlanTrip> c) {
        Page page = new Page(1, size);

        List<RecommendPlanTrip> dis = recommendPlanTripDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
}
