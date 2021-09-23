package com.data.data.hmly.action.build;

import com.data.data.hmly.service.build.YhyBuildService;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by dy on 2016/12/28.
 */
public class YhyWebAction extends JxmallAction {

    @Resource
    private YhyBuildService yhyBuildService;


    private Long id;


    public Result buildOneSailboat() {
        yhyBuildService.buildOneTicket(id);
        return text("success");
    }
    public Result buildOneCruiseship() {
        yhyBuildService.buildOneCruiseship(id);
        return text("success");
    }

    public Result buildAllCruiseship() {
        yhyBuildService.buildAllCruiseship(id);
        return text("success");
    }


    public Result index() {
        return dispatch();
    }

    public Result yhyIndex() {
        yhyBuildService.buildIndex();
        return text("success");
    }
    public Result yhyScenicIndex() {
        yhyBuildService.buildScenicIndex();
        return text("success");
    }
    public Result yhyHotelIndex() {
        yhyBuildService.buildHotelIndex();
        return text("success");
    }

    public Result sailboatIndex() {
        yhyBuildService.buildSailboatIndex();
        return text("success");
    }

    public Result cruiseshipIndex() {
        yhyBuildService.buildCruiseshipIndex();
        return text("success");
    }
    public Result recommendPlanIndex() {
        yhyBuildService.buildRecommendPlanIndex();
        return text("success");
    }



    public Result sailboatDetail() {
        if (getParameter("scenicId") != null && StringUtils.isNotBlank(getParameter("scenicId").toString())) {
            Long ticketId = Long.valueOf(getParameter("scenicId").toString());
            yhyBuildService.buildOneTicket(ticketId);
        } else if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("startId").toString())) {
            Long startId = Long.valueOf(getParameter("startId").toString());
            Long endId;
            if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("endId").toString())) {
                endId = Long.valueOf(getParameter("endId").toString());
            } else {
                endId = null;
            }
            yhyBuildService.buildTicketDetail(startId, endId);
        } else {
            yhyBuildService.buildAllTicketDetail();
        }
        return text("success");
    }

    public Result cruiseshipDetail() {
        if (getParameter("cruiseshipId") != null && StringUtils.isNotBlank(getParameter("cruiseshipId").toString())) {
            Long cruiseshipId = Long.valueOf(getParameter("cruiseshipId").toString());
            yhyBuildService.buildOneCruiseship(cruiseshipId);
        } else if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("startId").toString())) {
            Long startId = Long.valueOf(getParameter("startId").toString());
            Long endId;
            if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("endId").toString())) {
                endId = Long.valueOf(getParameter("endId").toString());
            } else {
                endId = null;
            }
            yhyBuildService.buildCruiseshipDetail(startId, endId);
        } else {
            yhyBuildService.buildAllCruiseshipDetail();
        }
        return text("success");
    }

    public Result recommendPlanDetail() {
        if (getParameter("recommendPlanId") != null && StringUtils.isNotBlank(getParameter("recommendPlanId").toString())) {
            Long recommendPlanId = Long.valueOf(getParameter("recommendPlanId").toString());
            yhyBuildService.buildOneRecommendPlan(recommendPlanId);
        } else if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("startId").toString())) {
            Long startId = Long.valueOf(getParameter("startId").toString());
            Long endId;
            if (getParameter("startId") != null && StringUtils.isNotBlank(getParameter("endId").toString())) {
                endId = Long.valueOf(getParameter("endId").toString());
            } else {
                endId = null;
            }
            yhyBuildService.buildRecommendPlanDetail(startId, endId);
        } else {
            yhyBuildService.buildAllRecommendPlanDetail();
        }
        return text("success");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
