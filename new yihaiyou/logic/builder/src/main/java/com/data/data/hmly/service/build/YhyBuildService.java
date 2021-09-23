package com.data.data.hmly.service.build;

import com.data.data.hmly.service.build.yhybuilder.*;
import com.data.data.hmly.service.build.yhybuilder.YhyScenicBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zzl on 2016/12/27.
 */
@Service
public class YhyBuildService {

    @Resource
    private YhyHotelBuilder yhyHotelBuilder;
    @Resource
    private YhyScenicBuilder yhyScenicBuilder;
    @Resource
    private YhySailboatBuilder yhySailboatBuilder;
    @Resource
    private YhyIndexBuilder yhyIndexBuilder;
    @Resource
    private YhyCruiseShipBuilder yhyCruiseShipBuilder;
    @Resource
    private YhyRecommendPlanBuilder yhyRecommendBuilder;

    public void buildHotelIndex() {
        yhyHotelBuilder.buildYhyIndex();
    }

    public void buildScenicIndex() {
        yhyScenicBuilder.buildYhyIndex();
    }

    public void buildSailboatIndex() {
        yhySailboatBuilder.buildYhySailboatIndex();
    }

    public void buildSailboatDetail(Long id) {
        yhySailboatBuilder.buildSailboatDetail(id);
    }

    public void buildOneTicket(Long id) {
        yhySailboatBuilder.buildSailboatDetail(id);
    }

    public void buildTicketDetail(Long startId, Long endId) {
        yhySailboatBuilder.buildYhyDetail(startId, endId);
    }

    public void buildAllTicketDetail() {
        yhySailboatBuilder.buildYhyDetail();
    }

    public void buildIndex() {
        yhyIndexBuilder.buildIndex();
    }

    public void buildCruiseshipIndex() {
        yhyCruiseShipBuilder.buildCruiseshipIndex();
    }

    public void buildOneCruiseship(Long cruiseshipId) {
        yhyCruiseShipBuilder.buildCruiseShipDetail(cruiseshipId);
    }

    public void buildCruiseshipDetail(Long startId, Long endId) {
        yhyCruiseShipBuilder.buildYhyDetail(startId, endId);
    }

    public void buildAllCruiseshipDetail() {
        yhyCruiseShipBuilder.buildYhyDetail();
    }

    public void buildRecommendPlanIndex() {
        yhyRecommendBuilder.buildYhyRecommendPlanIndex();
    }

    public void buildOneRecommendPlan(Long recommendPlanId) {
        yhyRecommendBuilder.buildRecommendPlanDetail(recommendPlanId);
    }

    public void buildRecommendPlanDetail(Long startId, Long endId) {
        yhyRecommendBuilder.buildYhyDetail(startId, endId);
    }

    public void buildAllRecommendPlanDetail() {
        yhyRecommendBuilder.buildYhyDetail();
    }

    public void buildAllCruiseship(Long cruiseShipId) {
        yhyCruiseShipBuilder.buildAllCruiseship(cruiseShipId);
    }
}
