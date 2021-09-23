package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.request.PlanSaveRequest;
import com.data.data.hmly.action.yihaiyou.response.OptimizeDetailResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanSimpleResponse;
import com.data.data.hmly.action.yihaiyou.vo.PlanVo;
import com.data.data.hmly.service.PlanMobileService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.lvxbang.PlanBookingService;
import com.data.data.hmly.service.lvxbang.PlanOperationService;
import com.data.data.hmly.service.lvxbang.PlanOptimizeService;
import com.data.data.hmly.service.lvxbang.request.PlanOptimizeRequest;
import com.data.data.hmly.service.lvxbang.request.PlanUpdateRequest;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeDayResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeResponse;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.request.PlanSearchRequest;
import com.data.data.hmly.service.plan.vo.PlanSolrEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class PlanWebAction extends BaseAction {
    @Resource
    private PlanOperationService planOperationService;
    @Resource
    private PlanBookingService planBookingService;
    @Resource
    private PlanService planService;
    @Resource
    private PlanOptimizeService planOptimizeService;
    @Resource
    private PlanMobileService planMobileService;

    private final ObjectMapper mapper = new ObjectMapper();

    public String json;
    public PlanSaveRequest planSaveRequest;
    public PlanOptimizeRequest planOptimizeRequest;
    public PlanSearchRequest planSearchRequest;
    public Long planId;
    public String planName;
    public Member user;
    public Integer pageNo;
    public Integer pageSize;
    public String startDate;
    public Boolean needHotel;
    public String hotelSearch;
    public String planJson;

//    private final static Log LOG = LogFactory.getLog(PlanWebAction.class);

    /**
     * 保存行程
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result save() throws IOException, ParseException, LoginException {
        planSaveRequest = mapper.readValue(json, PlanSaveRequest.class);
        user = getLoginUser();

        PlanUpdateRequest planUpdateRequest = planSaveRequest.toPlanUpdateRequest();

        Plan plan;
        if (planUpdateRequest.id == null || planUpdateRequest.id < 1) {
            plan = planOperationService.createPlanByMobile(planUpdateRequest, user, getRequest().getHeader("x-real-ip"));
        } else {
            plan = planOperationService.updatePlan(planUpdateRequest, user, getRequest().getHeader("x-real-ip"));
        }

        Map<String, Object> map = planSaveRequest.toTrafficAndHotelMap();
        try {
            planBookingService.savePlanTransAndHotel(map, plan.getId());

            result.put("success", true);
            result.put("id", plan.getId());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result.put("success", false);
            result.put("errorMsg", e.getMessage());
        }
        return json(JSONObject.fromObject(result));

    }

    /**
     * 行程列表
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result list() throws LoginException {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        List<Plan> plans = planService.listMyPlan(user.getId(), page);
        List<PlanSimpleResponse> list = Lists.transform(plans, new Function<Plan, PlanSimpleResponse>() {
            @Override
            public PlanSimpleResponse apply(Plan input) {
                return new PlanSimpleResponse(input);
            }
        });
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("planList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 推荐线路列表
     *
     * @return
     */
    @AjaxCheck
    public Result hotList() {
        String json = getJsonDate(JsonDateFileName.PLAN_HOT_LIST, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        List<Plan> plans = planService.getHotPlans();
        List<PlanSimpleResponse> list = Lists.transform(plans, new Function<Plan, PlanSimpleResponse>() {
            @Override
            public PlanSimpleResponse apply(Plan input) {
                return new PlanSimpleResponse(input);
            }
        });
        result.put("planList", list);
        result.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(result);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.PLAN_HOT_LIST, json);
        return json(jsonObject);
    }

    /**
     * 搜索行程列表
     *
     * @return
     */
    @AjaxCheck
    public Result searchList() throws IOException {
        planSearchRequest = mapper.readValue(json, PlanSearchRequest.class);
        Page page = new Page(pageNo, pageSize);
        List<PlanSolrEntity> plans = planService.listFromSolr(planSearchRequest, page);
        List<PlanSimpleResponse> list = Lists.transform(plans, new Function<PlanSolrEntity, PlanSimpleResponse>() {
            @Override
            public PlanSimpleResponse apply(PlanSolrEntity input) {
                return new PlanSimpleResponse(input);
            }
        });
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("planList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 行程详情
     *
     * @return
     */
    @AjaxCheck
    public Result detail() {
        String json = getJsonDate(String.format(JsonDateFileName.PLAN_DETAIL, planId), 30);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        Plan plan = planService.get(planId);
        if (plan == null) {
            result.put("errMsg", "该线路不存在！");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (plan.getStatus() != 1) {
            result.put("errMsg", "该线路已删除！");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        PlanResponse response = planMobileService.planToResponse(plan);
        result.put("plan", response);
        result.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(result);
        json = jsonObject.toString();
        setJsonDate(String.format(JsonDateFileName.PLAN_DETAIL, planId), json);
        return json(jsonObject);
    }

    /**
     * 修改线路名称
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result updateName() {
        Plan plan = planService.get(planId);
        if (plan == null) {
            result.put("errMsg", "该线路不存在！");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (plan.getStatus() != 1) {
            result.put("errMsg", "该线路已删除！");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        plan.setName(planName);
        planService.update(plan);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 删除行程
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result delete() throws LoginException {
        Plan plan = planService.get(planId);
        user = getLoginUser();
        if (plan == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!plan.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        planService.delById(planId);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 智能排序
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result optimize() throws IOException {
        user = getLoginUser();
        planOptimizeRequest = mapper.readValue(json, PlanOptimizeRequest.class);
        HotelSearchRequest hotelSearchRequest = mapper.readValue(hotelSearch, HotelSearchRequest.class);
        OptimizeDetailResponse response = planMobileService.optimize(planOptimizeRequest, startDate, needHotel, hotelSearchRequest);
        response.setName(user.getNickName() + "的" + response.getDays().size() + "日游");
        result.put("plan", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 保存行程
     * @return
     */
    public Result doSavePlan() {
        Member user = getLoginUser();
        if (user == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Gson gson = new Gson();
        PlanVo webPlan = gson.fromJson(planJson, PlanVo.class);
        Plan plan = new Plan();
        plan.setUser(user);
        planMobileService.doSaveWebPlan(webPlan, plan);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 复制线路
     *
     * @return
     */
    @AjaxCheck
    public Result quote() {
        PlanOptimizeResponse response = planOperationService.getPlanResponse(planId);
        planOptimizeService.fillInfo(response);
        planService.addQuoteNum(planId);
        response.id = 0l;
        for (PlanOptimizeDayResponse dayResponse : response.data) {
            dayResponse.city.id = dayResponse.city.id / 100 * 100;
        }
        result.put("result", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result personalList() {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        List<Plan> planList = planService.listMyPlan(user.getId(), page);
        for (Plan plan : planList) {
            Float price = 0f;
            for (PlanDay planDay : plan.getPlanDayList()) {
                Float ticketCost = planDay.getTicketCost() == null ? 0F : planDay.getTicketCost();
                Float includeSeasonticketCost = planDay.getIncludeSeasonticketCost() == null ? 0F : planDay.getIncludeSeasonticketCost();
                Float trafficCost = planDay.getTrafficCost() == null ? 0F : planDay.getTrafficCost();
                Float returnTrafficCost = planDay.getReturnTrafficCost() == null ? 0F : planDay.getReturnTrafficCost();
                Float hotelCost = planDay.getHotelCost() == null ? 0F : planDay.getHotelCost();
                Float foodCost = planDay.getFoodCost() == null ? 0F : planDay.getFoodCost();
                Float dayTotalCost = ticketCost + includeSeasonticketCost + trafficCost + returnTrafficCost + hotelCost + foodCost;
                price += dayTotalCost;
            }
            plan.setPrice(price);
        }
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("planList", planList);
        return json(JSONObject.fromObject(result, JsonFilter.getExcludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
    public Result planDetail() {
        Plan plan = planService.get(planId);
        OptimizeDetailResponse response = planMobileService.planDetail(plan);
        result.put("success", true);
        result.put("plan", response);
        return jsonResult(result);
    }

    /**
     * 行程下单
     *
     * @return
     */
//    @AjaxCheck
//    @NeedLogin
//    public Result order() throws IOException, ParseException, LoginException {
//        Plan plan = planService.get(planId);
//        user = getLoginUser();
//        if (plan == null) {
//            result.put("success", false);
//            return json(JSONObject.fromObject(result));
//        }
//        if (!plan.getUser().getId().equals(user.getId())) {
//            result.put("success", false);
//            return json(JSONObject.fromObject(result));
//        }
//
//        PlanOrderResponse response = planMobileService.planToOrder(plan);
//
//        result.put("order", response);
//        result.put("success", true);
//        return json(JSONObject.fromObject(result));
//    }

    @AjaxCheck
    @NeedLogin
    public Result orderNoPlan() throws IOException, ParseException, LoginException {
        Map<String, Object> data = mapper.readValue(json, Map.class);
        user = getLoginUser();
        PlanOrderResponse response = planMobileService.planToOrder(data);
        response.setName(user.getNickName() + "的" + response.getDay() + "日游");
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }
}
