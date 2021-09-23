package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.response.OptimizeDetailResponse;
import com.data.data.hmly.action.yhypc.response.PlanOrderResponse;
import com.data.data.hmly.action.yhypc.vo.PlanVo;
import com.data.data.hmly.service.CommentWebService;
import com.data.data.hmly.service.HotelWebService;
import com.data.data.hmly.service.PlanWebService;
import com.data.data.hmly.service.ScenicWebService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.lvxbang.request.PlanOptimizeRequest;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.user.entity.Tourist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.struts.AjaxCheck;
import com.google.gson.Gson;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2017-01-09,0009.
 */
public class PlanWebAction extends YhyAction {

    @Resource
    private PlanService planService;
    @Resource
    private PlanWebService planWebService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private HotelWebService hotelWebService;
    @Resource
    private ScenicWebService scenicWebService;
    @Resource
    private CommentWebService commentWebService;

    private final ObjectMapper mapper = new ObjectMapper();

    public String planOptimize;
    public String hotelSearch;
    public String startDate;
    public String endDate;
    public Boolean needHotel;
    private String planJson;
    public Long planId;
    public PlanVo planVo;

    public Member user;
    public List<Tourist> touristList;

    public Long scenicId;
    public ScenicInfo scenicInfo;
    public Integer commentCount;

    public Long hotelId;
    public Hotel hotel;

    public String json;

    public Result demand() {
        return dispatch();
    }

    public Result scenicList() {
        return dispatch();
    }

    public Result scenicDetail() {
        if (scenicId != null) {
            scenicInfo = scenicWebService.findAllDetailById(scenicId);
            commentCount = commentWebService.commentCount(scenicInfo.getScenicCommentList());
            if (scenicInfo != null) {
                return dispatch();
            } else {
                return redirect("/yhypc/plan/scenicList.jhtml");
            }
        } else {
            return redirect("/yhypc/plan/scenicList.jhtml");
        }
    }

    public Result detail() {
        user = getLoginUser();
//        user = memberService.get(1000526l);
//        touristList = touristService.getMyTourist(user, null, null);
        return dispatch();
    }

    public Result myPlanDetail() {
        final HttpServletRequest request = getRequest();
        final HttpServletResponse response = getResponse();
        if (planId != null) {
            Plan plan = planService.get(planId);
            if (plan == null) {
                return redirect("/yhypc/plan/demand.jhtml");
            }
            user = plan.getUser();
            planVo = new PlanVo();
            planWebService.toWebPlan(planVo, plan);
            return dispatch();
        } else {
            return redirect("/yhypc/plan/demand.jhtml");
        }
    }

    public Result getMyPlanDetail() {

        return null;
    }

    public Result changeFerry() {
        return dispatch();
    }

    public Result changeHotelList() {
        return dispatch();
    }

    public Result changeHotelDetail() {
        if (hotelId != null) {
            hotel = hotelWebService.findAllDetailById(hotelId);
            commentCount = commentWebService.commentCount(hotel.getCommentList());
            Productimage productimage = productimageService.findCover(hotel.getId(), hotel.getId(), ProductType.hotel);
            if (productimage != null) {
                hotel.setCover(productimage.getCompletePath());
            }
            if (hotel != null) {
                return dispatch();
            } else {
                return redirect("/yhypc/plan/changeHotelList.jhtml");
            }
        } else {
            return redirect("/yhypc/plan/changeHotelList.jhtml");
        }
    }

    @AjaxCheck
    public Result optimize() throws IOException {
        user = getLoginUser();
//        user = memberService.get(1000526l);
        PlanOptimizeRequest planOptimizeRequest = mapper.readValue(planOptimize, PlanOptimizeRequest.class);
        HotelSearchRequest hotelSearchRequest = mapper.readValue(hotelSearch, HotelSearchRequest.class);
        OptimizeDetailResponse response = planWebService.optimize(planOptimizeRequest, startDate, needHotel, hotelSearchRequest);
        response.setName(user.getNickName() + "的" + response.getDays().size() + "日游");
        result.put("plan", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

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
        planWebService.doSaveWebPlan(webPlan, plan);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result orderNoPlan() throws IOException, ParseException, LoginException {
        Map<String, Object> data = mapper.readValue(json, Map.class);
        user = getLoginUser();
//        user = memberService.get(1000526l);
        PlanOrderResponse response = planWebService.planToOrder(data);
        response.setName(user.getNickName() + "的" + response.getDay() + "日游");
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public String getPlanJson() {
        return planJson;
    }

    public void setPlanJson(String planJson) {
        this.planJson = StringUtils.htmlEncode(planJson);
    }
}
