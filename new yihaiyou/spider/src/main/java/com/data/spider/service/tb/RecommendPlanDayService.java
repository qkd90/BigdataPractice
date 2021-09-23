package com.data.spider.service.tb;


import com.data.spider.service.dao.RecommendPlanDayDao;
import com.data.spider.service.pojo.tb.RecommendPlanDay;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecommendPlanDayService {

    @Resource
    private RecommendPlanDayDao recommendPlanDayDao;


    public void save(RecommendPlanDay RecommendPlanDay) {
        recommendPlanDayDao.save(RecommendPlanDay);

    }


}
