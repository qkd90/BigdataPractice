package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.cruiseship.CruiseShipPlanService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caiys on 2016/9/18.
 */
public class CruiseShipPlanAction extends FrameBaseAction {
    @Resource
    private CruiseShipPlanService cruiseShipPlanService;
    private List<CruiseShipPlan> plans;

    /**
     * 添加、编辑保存
     * @return
     */
    @AjaxCheck
    public Result save() {
        String cruiseShipIdStr = (String) getParameter("productId");
        if (plans != null && !plans.isEmpty()) {
            cruiseShipPlanService.saveList(plans, Long.valueOf(cruiseShipIdStr));
        }

        simpleResult(result, true, "");
        return jsonResult(result);
    }

    public List<CruiseShipPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<CruiseShipPlan> plans) {
        this.plans = plans;
    }
}
