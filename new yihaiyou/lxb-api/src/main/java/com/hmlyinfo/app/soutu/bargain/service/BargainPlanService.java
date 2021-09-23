package com.hmlyinfo.app.soutu.bargain.service;

import com.hmlyinfo.app.soutu.bargain.domain.BargainPlan;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanGallery;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanTrip;
import com.hmlyinfo.app.soutu.bargain.mapper.BargainPlanMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BargainPlanService extends BaseService<BargainPlan, Long> {

    @Autowired
    private BargainPlanMapper<BargainPlan> mapper;
    @Autowired
    BargainPlanDayService bargainPlanDayService;
    @Autowired
    BargainPlanTripService bargainPlanTripService;
    @Autowired
    BargainPlanGalleryService bargainPlanGalleryService;

    @Override
    public BaseMapper<BargainPlan> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public BargainPlan detail(Long id) {
        BargainPlan bargainPlan = info(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bargainPlanId", id);
        List<BargainPlanTrip> bargainPlanTripList = bargainPlanTripService.list(paramMap);

        List<List<BargainPlanTrip>> dayList = new ArrayList<List<BargainPlanTrip>>();
        for (int i = 0; i < bargainPlan.getDayCount(); i++) {
            dayList.add(new ArrayList<BargainPlanTrip>());
        }
        for (BargainPlanTrip bargainPlanTrip : bargainPlanTripList) {
            int day = bargainPlanTrip.getDay();
            List<BargainPlanTrip> trips = dayList.get(day - 1);
            trips.add(bargainPlanTrip);
        }
        for (List<BargainPlanTrip> bargainPlanTrips : dayList) {
            Collections.sort(bargainPlanTrips, new Comparator<BargainPlanTrip>() {
                @Override
                public int compare(BargainPlanTrip o1, BargainPlanTrip o2) {
                    return o1.getIndex() - o2.getIndex();
                }
            });
        }
        bargainPlan.setDayList(dayList);
        List<BargainPlanGallery> galleryList = bargainPlanGalleryService.list(paramMap);
        bargainPlan.setGalleryList(galleryList);
        return bargainPlan;
    }

}
