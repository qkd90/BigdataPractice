package com.data.data.hmly.action.plan;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.plan.vo.PlanMgrVo;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.plan.PlanMgrService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/4/28.
 */
public class PlanMgrAction extends FrameBaseAction {

    @Resource
    private PlanMgrService planMgrService;

    private Long id;

    private Plan plan = new Plan();

    public Result toList() {
        return dispatch("/WEB-INF/jsp/plan/list.jsp");
    }

    public Result getPlanList() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<Plan> palnDataPlanList = planMgrService.page(Plan.class, paramMap);
        List<Plan> planList = palnDataPlanList.getRows();
        List<PlanMgrVo> planMgrVoList = new ArrayList<PlanMgrVo>();
        for (Plan plan : planList) {
            PlanMgrVo planMgrVo = new PlanMgrVo();
            planMgrVo.setId(plan.getId());
            Member member = plan.getUser();
            if (member != null) {
                planMgrVo.setUserName(member.getUserName());
                planMgrVo.setNickName(member.getNickName());
            } else {
                planMgrVo.setUserName("匿名用户");
                planMgrVo.setNickName("匿名用户");
            }
            planMgrVo.setName(plan.getName());
            planMgrVo.setSubtitle(plan.getSubTitle());
            planMgrVo.setRecReason(plan.getRecReason());
            planMgrVo.setCover(plan.getCoverPath());
            planMgrVo.setDays(plan.getPlanDays());
            planMgrVo.setDate(DateUtils.format(plan.getStartTime(), "yyyy-MM-dd"));
            planMgrVo.setStatus(plan.getStatus());
            planMgrService.setCostAndCities(plan, planMgrVo);
            planMgrVoList.add(planMgrVo);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(planMgrVoList, palnDataPlanList.getTotal(), jsonConfig);
    }

    public Result upPlan() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Plan plan = planMgrService.load(id);
            plan.setStatus(1);
            planMgrService.update(plan);
            result.put("success", true);
            result.put("msg", "行程上架成功!");
            return json(JSONObject.fromObject(result));
        } else {
            result.put("success", false);
            result.put("msg", "行程上架失败!");
            return json(JSONObject.fromObject(result));
        }
    }

    public Result downPlan() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Plan plan = planMgrService.load(id);
            plan.setStatus(2);
            planMgrService.update(plan);
            planMgrService.delLabelItems(plan);
            result.put("success", true);
            result.put("msg", "行程下架成功!");
            return json(JSONObject.fromObject(result));
        } else {
            result.put("success", false);
            result.put("msg", "行程下架失败!");
            return json(JSONObject.fromObject(result));
        }
    }

    public Result delPlan() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Plan plan = planMgrService.load(id);
            plan.setStatus(3);
            planMgrService.update(plan);
            planMgrService.delLabelItems(plan);
            result.put("success", true);
            result.put("msg", "行程删除成功!");
            return json(JSONObject.fromObject(result));
        } else {
            result.put("success", false);
            result.put("msg", "行程删除失败!");
            return json(JSONObject.fromObject(result));
        }
    }

    public Result getPlanDetail() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Plan plan = planMgrService.load(id);
            if (plan != null) {
                result.put("id", plan.getId());
                result.put("plan.id", plan.getId());
                result.put("plan.name", plan.getName());
                result.put("plan.subTitle", plan.getSubTitle());
                result.put("plan.recReason", plan.getRecReason());
            } else {
                result.put("id", "ID不可为空或该反馈不存在!");
            }
        } else {
            result.put("id", "ID不可为空或该反馈不存在!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result doEditPlan() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Plan sourcePlan = planMgrService.load(id);
            if (sourcePlan != null) {
                sourcePlan.setName(plan.getName());
                sourcePlan.setSubTitle(plan.getSubTitle());
                sourcePlan.setRecReason(plan.getRecReason());
                planMgrService.update(sourcePlan);
                result.put("success", true);
                result.put("msg", "修改成功!");
            } else {
                result.put("success", false);
                result.put("msg", "该行程不存在! 可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "行程ID错误, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
