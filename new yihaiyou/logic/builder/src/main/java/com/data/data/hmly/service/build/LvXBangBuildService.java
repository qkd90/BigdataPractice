package com.data.data.hmly.service.build;

import com.data.data.hmly.service.build.builder.AdsBuilder;
import com.data.data.hmly.service.build.builder.CommonBuilder;
import com.data.data.hmly.service.build.builder.DelicacyBuilder;
import com.data.data.hmly.service.build.builder.DestinationBuilder;
import com.data.data.hmly.service.build.builder.HotelBuilder;
import com.data.data.hmly.service.build.builder.IndexBuilder;
import com.data.data.hmly.service.build.builder.LineBuilder;
import com.data.data.hmly.service.build.builder.PlanBuilder;
import com.data.data.hmly.service.build.builder.RecommendPlanBuilder;
import com.data.data.hmly.service.build.builder.ScenicBuilder;
import com.data.data.hmly.service.build.builder.TrafficBuilder;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/12/16.
 */
@Service
public class LvXBangBuildService {

    @Resource
    private IndexBuilder indexBuilder;
    @Resource
    private AdsBuilder adsBuilder;
    @Resource
    private ScenicBuilder scenicBuilder;
    @Resource
    private DelicacyBuilder delicacyBuilder;
    @Resource
    private RecommendPlanBuilder recommendPlanBuilder;
    @Resource
    private DestinationBuilder destinationBuilder;
    @Resource
    private PlanBuilder planBuilder;
    @Resource
    private HotelBuilder hotelBuilder;
    @Resource
    private TrafficBuilder trafficBuilder;
    @Resource
    private CommonBuilder commonBuilder;
    @Resource
    private LineBuilder lineBuilder;
    @Resource
    private LineService lineService;

    private final Log log = LogFactory.getLog(LvXBangBuildService.class);

    public void buildAll() {
        // TODO 按需加入静态页面的build方法
        log.info("invoke buildAll=======================================================");
    }

    public void buildTrafficBanner() {
        adsBuilder.buildLXBTraffic();
    }

    public void buildHotelBanner() {
        adsBuilder.buildLXBHotel();
    }

    public void buildIndex() {
//		adsBuilder.buildLXBIndex();
        indexBuilder.buildLXBIndex();
    }

    public void buildScenicIndex() {
        scenicBuilder.buildLXBIndex();
    }

    public void buildAllScenicDetail() {
        scenicBuilder.buildLXBDetail();
    }

    public void buildScenicDetail(Long startId, Long endId) {
        scenicBuilder.buildLXBDetail(startId, endId);
    }

    public void buildOneScenic(Long scenicId) {
        scenicBuilder.buildLXBDetail(scenicId);
    }

    public void buildDelicacyIndex() {
        adsBuilder.buildLXBDelicacy();
        delicacyBuilder.buildLXBIndex();
    }

    public void buildAllDelicacyDetail() {
        delicacyBuilder.buildLXBDetail();
    }

    public void buildOneDelicacy(Long delicacyId) {
        delicacyBuilder.buildLXBDetail(delicacyId);
    }

    public void buildRecplanIndex() {
        adsBuilder.buildLXBRecplan();
        recommendPlanBuilder.buildLXBIndex();
    }

    public void buildAllRecplanDetail() {
        recommendPlanBuilder.buildLXBDetail();
    }

    public void buildOneRecplan(Long planId) {
        recommendPlanBuilder.buildLXBDetail(planId);
    }

    public void buildRecplanByCondition(RecommendPlan condition) {
        recommendPlanBuilder.buildLXBDetailByCondition(condition);
    }

    public void buildDestinationIndex() {
//        adsBuilder.buildLXBDestination();
        destinationBuilder.buildLXBIndex();
    }

    public void buildAllDestinationDetail() {
        destinationBuilder.buildLXBDetail();
    }

    public void buildDestinationDetail(Long startId, Long endId) {
        destinationBuilder.buildLXBDetail(startId, endId);
    }

    public void buildOneDetination(Long areaId) {
        destinationBuilder.buildLXBDetail(areaId);
    }

    public void buildPlanIndex() {
//        adsBuilder.buildPlanIndex();
        planBuilder.buildLXBIndex();
    }

    public void buildHotelIndex() {
//        adsBuilder.buildLXBHotel();
//        adsBuilder.buildLXBHotelSide();
        hotelBuilder.buildLxbIndex();
    }

    public void buildTrafficIndex() {
//        adsBuilder.buildLXBTraffic();
        trafficBuilder.buildLxbIndex();
    }

    public void buildHotelDetails() {
        hotelBuilder.buildLxbDetail();
    }

    public void buildHotelDetail(Long hotelId) {
        hotelBuilder.buildLxbDetail(hotelId);
    }

    public void buildOneLine(Long lineId) {
        lineBuilder.buildLXBDetail(lineId);
    }

    public void buildCommon() {
        commonBuilder.buildLxbCitySelector();
        commonBuilder.buildLxbHandDrawCity();
    }

    public void buildLineCustomTourIndex() {
        lineBuilder.buildCustomTourIndex();
    }
    public void buildLineGroupTourIndex() {
        lineBuilder.buildGroupTourIndex();
    }
    public void buildLineSelfTourIndex() {
        lineBuilder.buildSelfTourIndex();
    }
    public void buildLineDriveTourIndex() {
        lineBuilder.buildDriveTourIndex();
    }

    public void createCloneLine(Long lineId, Integer num) throws CloneNotSupportedException {
        Line line = lineService.loadLine(lineId);
        for (int i = 0; i < num; i++) {
            lineService.createCloneLine(line);
        }
    }
}
