package com.data.data.hmly.action.lvxbang.solrindex;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.zuipin.util.GlobalTheadPool;

import javax.annotation.Resource;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * Created by HMLY on 2016/5/4.
 */
public class SolrIndex extends TimerTask {
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private PlanService planService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private HotelService hotelService;
//    @Resource
//    private HotelRoomIndexService hotelRoomIndexService;
    @Resource
    private AreaService areaService;


    @Override
    public void run() {
        solrIndexAll();
    }

    public void solrIndexAll() {
//        solrTemplate.deleteByQuery("*:*", "products");
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return solrScenicTask();
            }
        });
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return solrDelicacy();
            }
        });
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return solrPlan();
            }
        });
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return solrRecommendPlan();
            }
        });
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return solrHotel();
            }
        });
//        GlobalTheadPool.instance.submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                return solrHotelRoom();
//            }
//        });
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return solrArea();
            }
        });

    }

    private Object solrScenicTask() {
        scenicInfoService.indexScenicInfoAll(new ScenicInfo());
        return null;
    }
    private Object solrDelicacy() {
        delicacyService.indexDelicacy();
        return null;
    }
    private Object solrPlan() {
        planService.indexPlans(new Plan());
        return null;
    }
    private Object solrRecommendPlan() {
        recommendPlanService.indexRecommendPlan(null);
        return null;
    }
    private Object solrHotel() {
        hotelService.indexHotel();
        return null;
    }
//    private Object solrHotelRoom() {
//        hotelRoomIndexService.indexHotelRoom();
//        return null;
//    }
    private Object solrArea() {
        areaService.indexArea();
        return null;
    }
}
