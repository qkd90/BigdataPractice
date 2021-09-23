package com.data.data.hmly.action.build;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.LvXBangBuildService;
import com.data.data.hmly.service.hotel.HotelRoomIndexService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/12/14.
 */
public class LxbWebAction extends JxmallAction {

    @Resource
    private LvXBangBuildService lvXBangBuildService;
    @Resource
    private LineService lineService;
    @Resource
    private PlanService planService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelRoomIndexService hotelRoomIndexService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private AreaService areaService;

    public Result index() {
        return dispatch();
    }

    public Result build() {
        return text("success");
    }

    public Result buildIndex() {
        lvXBangBuildService.buildIndex();
        return text("success");
    }

    public Result buildDestinationIndex() {
        lvXBangBuildService.buildDestinationIndex();
        return text("success");
    }

    public Result buildDestinationDetail() {
        if (getParameter("cityCode") != null && StringUtils.isNotBlank(getParameter("cityCode").toString())) {
            Long cityCode = Long.parseLong(getParameter("cityCode").toString());
            lvXBangBuildService.buildOneDetination(cityCode);
        } else if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("startId").toString())) {
            Long startId = Long.valueOf(getParameter("startId").toString());
            Long endId = null;
            if (getParameter("endId") != null && StringUtils.isNotBlank(getParameter("endId").toString())) {
                endId = Long.valueOf(getParameter("endId").toString());
            }
            lvXBangBuildService.buildDestinationDetail(startId, endId);
        } else {
            lvXBangBuildService.buildAllDestinationDetail();
        }
        return text("success");
    }

    public Result buildHotelIndex() {
        lvXBangBuildService.buildHotelIndex();
        return text("success");
    }

    public Result buildHotelDetail() {
        lvXBangBuildService.buildHotelDetails();
        return text("success");
    }

    public Result buildOneHotelDetail() {
        lvXBangBuildService.buildHotelDetail(Long.valueOf(getParameter("hotelId").toString()));
        return text("success");
    }

    public Result buildRecommendPlanIndex() {
        lvXBangBuildService.buildRecplanIndex();
        return text("success");
    }

    public Result buildRecommendPlanDetail() {
        if (getParameter("recplanId") != null && StringUtils.isNotBlank(getParameter("recplanId").toString())) {
            Long recplanId = Long.valueOf(getParameter("recplanId").toString());
            lvXBangBuildService.buildOneRecplan(recplanId);
        } else if ((getParameter("recplanStartId") != null && StringUtils.isNotBlank(getParameter("recplanStartId").toString()))
                || (getParameter("recplanEndId") != null && StringUtils.isNotBlank(getParameter("recplanEndId").toString()))) {
            Long startId = null;
            Long endId = null;
            RecommendPlan condition = new RecommendPlan();
            if (getParameter("recplanStartId") != null) {
                startId = Long.parseLong(getParameter("recplanStartId").toString());
            }
            if (getParameter("recplanEndId") != null) {
                endId = Long.parseLong(getParameter("recplanEndId").toString());
            }
            condition.setStartId(startId);
            condition.setEndId(endId);
            lvXBangBuildService.buildRecplanByCondition(condition);
        } else if (getParameter("cityIds") != null && StringUtils.isNotBlank(getParameter("cityIds").toString())) {
            String cityIds = getParameter("cityIds").toString();
            RecommendPlan condition = new RecommendPlan();
            condition.setCityIds(cityIds);
            lvXBangBuildService.buildRecplanByCondition(condition);
        } else {
            lvXBangBuildService.buildAllRecplanDetail();
        }
        return text("success");
    }

    public Result buildTrafficIndex() {
        lvXBangBuildService.buildTrafficIndex();
        return text("success");
    }

    public Result buildScenicIndex() {
        lvXBangBuildService.buildScenicIndex();
        return text("success");
    }

    public Result buildScenicDetail() {
        if (getParameter("scenicId") != null && StringUtils.isNotBlank(getParameter("scenicId").toString())) {
            Long scenicId = Long.valueOf(getParameter("scenicId").toString());
            lvXBangBuildService.buildOneScenic(scenicId);
        } else if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("startId").toString())) {
            Long startId = Long.valueOf(getParameter("startId").toString());
            Long endId;
            if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("endId").toString())) {
                endId = Long.valueOf(getParameter("endId").toString());
            } else {
                endId = null;
            }
            lvXBangBuildService.buildScenicDetail(startId, endId);
        } else {
            lvXBangBuildService.buildAllScenicDetail();
        }
        return text("success");
    }

    public Result buildDelicacyIndex() {
        lvXBangBuildService.buildDelicacyIndex();
        return text("success");
    }

    public Result buildDelicacyDetail() {
        if (getParameter("delicacyId") != null && StringUtils.isNotBlank(getParameter("delicacyId").toString())) {
            Long delicacyId = Long.valueOf(getParameter("delicacyId").toString());
            lvXBangBuildService.buildOneDelicacy(delicacyId);
        } else {
            lvXBangBuildService.buildAllDelicacyDetail();
        }
        return text("success");
    }

    public Result buildPlanIndex() {
        lvXBangBuildService.buildPlanIndex();
        return text("success");
    }

    public Result buildCommon() {
        lvXBangBuildService.buildCommon();
        return text("success");
    }


    public Result buildLineCustomTourIndex() {
        lvXBangBuildService.buildLineCustomTourIndex();
        return text("success");
    }

    public Result buildLineGroupTourIndex() {
        lvXBangBuildService.buildLineGroupTourIndex();
        return text("success");
    }

    public Result buildLineSelfTourIndex() {
        lvXBangBuildService.buildLineSelfTourIndex();
        return text("success");
    }

    public Result buildLineDetail() {
        if (getParameter("lineId") != null && StringUtils.isNotBlank(getParameter("lineId").toString())) {
            Long lineId = Long.valueOf(getParameter("lineId").toString());
            lvXBangBuildService.buildOneLine(lineId);
            return text("success");
        }
        return text("fail");
    }

    public Result cloneLine() throws CloneNotSupportedException {
        if (getParameter("lineId") != null && StringUtils.isNotBlank(getParameter("lineId").toString()) && getParameter("cloneNum") != null && StringUtils.isNotBlank(getParameter("cloneNum").toString())) {
            Long lineId = Long.valueOf(getParameter("lineId").toString());
            Integer cloneNum = Integer.valueOf(getParameter("cloneNum").toString());
            lvXBangBuildService.createCloneLine(lineId, cloneNum);
            return text("success");
        }
        return text("fail");
    }

    public Result indexLine() {
        try {
            lineService.indexAllLine();
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexPlan() {
        try {
            planService.indexPlans(new Plan());
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexScenic() {
        try {
            scenicInfoService.indexScenicInfoAll(new ScenicInfo());
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexHotel() {
        try {
            hotelService.indexHotel();
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexHotelRoom() {
        try {
//            hotelRoomIndexService.indexHotelRoom();
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexRecommendPlan() {
        try {
            recommendPlanService.indexRecommendPlan(null, null);
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexDelicacy() {
        try {
            delicacyService.indexDelicacy();
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }

    public Result indexArea() {
        try {
            areaService.indexArea();
            return text("success");
        } catch (Exception e) {
            return text("fail");
        }
    }
}
