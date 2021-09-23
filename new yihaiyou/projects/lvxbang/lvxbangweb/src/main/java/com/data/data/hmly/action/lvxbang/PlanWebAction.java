package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.ctriphotel.base.StringUtil;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.lvxbang.PlanBookingService;
import com.data.data.hmly.service.lvxbang.PlanOperationService;
import com.data.data.hmly.service.lvxbang.PlanOptimizeService;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.lvxbang.request.PlanBookingRequest;
import com.data.data.hmly.service.lvxbang.request.PlanOptimizeRequest;
import com.data.data.hmly.service.lvxbang.request.PlanUpdateRequest;
import com.data.data.hmly.service.lvxbang.response.PlanBookingResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeResponse;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.request.PlanSearchRequest;
import com.data.data.hmly.service.plan.vo.PlanSolrEntity;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.util.Clock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jonathan.Guo
 */
public class PlanWebAction extends LxbAction implements ModelDriven<PlanUpdateRequest> {

    private static final int PLAN_SUGGEST_COUNT = 6;

    private static final String CREATE_PLAN_DESTINATIONS_COOKIE_NAME = "create_plan_destination";
    private static final String CHANGED_TRAFFIC = "CHANGED-TRAFFIC";
    private static final String CHANGED_CITY = "CHANGED-CITY";
    private static final String CHANGED_TRAFFIC_TYPE = "CHANGED_TRAFFIC_TYPE";
    private final ObjectMapper mapper = new ObjectMapper();

    @Resource
    private PlanService planService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private PlanOperationService planOperationService;
    @Resource
    private PlanOptimizeService planOptimizeService;
    @Resource
    private PlanBookingService planBookingService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private AreaService areaService;

    public Long planId;
    public String planName;
    public Plan plan;
    public RecommendPlan recommendPlan;
    public List<Long> destinationIds;
    public PlanUpdateRequest planUpdateRequest = new PlanUpdateRequest();
    public List<PlanBookingResponse> bookingList = Lists.newArrayList();
    public TbArea startCity = new TbArea();
    public String json;
    public Boolean isReturn;
    public Boolean newOne;
    public String code;
    public Long city;
    public String type;
    public String firstLeave;
    public String secondLeave;
    public String newLeave;
    public List<TbArea> cityList;
    public String cityIdStr;
    public PlanSearchRequest searchRequest = new PlanSearchRequest();
    public int pageIndex = 0;
    public int pageSize = 10;

    public TrafficType trafficType;

    public Result index() {
        setAttribute(LXBConstants.LVXBANG_PLAN_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_PLAN_BANNER));
        setAttribute(LXBConstants.LVXBANG_PLAN_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_PLAN_INDEX));
        return dispatch();
    }

    public Result detail() {
        plan = planService.get(planId);
        if (plan == null || plan.getStatus() != 1) {
            return dispatch404();
        }
        if (plan.getSourceId() != null) {
            recommendPlan = recommendPlanService.get(plan.getSourceId());
        }
        Collections.sort(plan.getPlanDayList(), new Comparator<PlanDay>() {
            @Override
            public int compare(PlanDay o1, PlanDay o2) {
                return o1.getDays() - o2.getDays();
            }
        });
//        PlanOptimizeResponse response = planOperationService.getPlanResponse(planId);
//        planOptimizeService.fillInfo(response);
        Map<String, Object> map = collectPlanBookingData(plan);
        json = JSONObject.fromObject(map).toString();
        return dispatch();
    }

    public Result detailJson() {
        PlanOptimizeResponse response = planOperationService.getPlanResponse(planId);
        planOptimizeService.fillInfo(response);
        return json(JSONObject.fromObject(response));
    }

    private Map<String, Object> collectPlanBookingData(Plan plan) {
        Map<String, Object> map = Maps.newHashMap();
        StringBuilder builder = new StringBuilder();
        Map<Long, Map<String, Object>> hotelMap = Maps.newLinkedHashMap();
        for (PlanDay planDay : plan.getPlanDayList()) {
            if (planDay.getTrafficPriceId() != null) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(planDay.getTrafficPriceId());
            }
            if (planDay.getReturnTrafficPriceId() != null) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(planDay.getReturnTrafficPriceId());
            }
            if (planDay.getHotel() == null) {
                continue;
            }
            Map<String, Object> hotel = hotelMap.get(planDay.getHotel().getId());
            if (hotel == null) {
                hotel = Maps.newHashMap();
                hotel.put("name", planDay.getHotel().getName());
                hotel.put("hotelId", planDay.getHotel().getId());
                hotel.put("priceId", planDay.getHotelCode());
                hotel.put("startDate", DateUtils.getDate(DateUtils.add(planDay.getDate(), Calendar.DAY_OF_MONTH, -1)));
            }

            hotel.put("leaveDate", DateUtils.getDate(DateUtils.add(planDay.getDate(), Calendar.DAY_OF_MONTH, 1)));
            hotelMap.put(planDay.getHotel().getId(), hotel);
        }
        List<Map<String, Object>> hotelList = Lists.newArrayList(hotelMap.values());
        map.put("traffic", builder.toString());
        map.put("hotel", hotelList);
        return map;
    }

    public Result quoteFromPlan() {
//        Member user = getLoginUser();
//        planOperationService.doQuoteFromPlan(planId, user, getRequest().getHeader("x-real-ip"));
        PlanOptimizeResponse response = planOperationService.getPlanResponse(planId);
        planService.addQuoteNum(planId);
        response.id = 0l;
        result.put("success", true);
        result.put("data", response);
        return json(JSONObject.fromObject(result));
    }

    public Result quoteFromRecommend() {
        Map<String, Object> result = Maps.newHashMap();
        Set<String> failedScenic = new LinkedHashSet<String>();
        PlanOptimizeResponse response = planOperationService.getRecommendPlanResponse(planId, failedScenic);
        if (response != null && response.data != null && response.data.size() > 0) {
            result.put("success", true);
            result.put("data", response);
            result.put("failedScenic", failedScenic);
        } else {
            result.put("success", false);
            result.put("errorMsg", "该游记无法复制！");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result exportRecommend() {
        Map<String, Object> result = Maps.newHashMap();
        try {
            RecommendPlan recommendPlan = planOperationService.exportRecommend(planId);
            result.put("success", true);
            result.put("id", recommendPlan.getId());
            return json(JSONObject.fromObject(result));
        } catch (Exception e) {
            //
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
    }

    public Result create() {
        if (destinationIds == null || destinationIds.isEmpty()) {
            return redirect("/lvxbang/plan/index.jhtml");
        }
        addCookie(CREATE_PLAN_DESTINATIONS_COOKIE_NAME, StringUtils.listToString(destinationIds));
        return redirect("/lvxbang/scenic/scenicList.jhtml");
    }

    public Result save() throws IOException {
        planUpdateRequest = mapper.readValue(json, PlanUpdateRequest.class);
        if (planUpdateRequest.id == null || planUpdateRequest.id < 1) {
            plan = planOperationService.createPlan(planUpdateRequest, getLoginUser(), getRequest().getHeader("x-real-ip"));
        } else {
            plan = planOperationService.updatePlan(planUpdateRequest, getLoginUser(), getRequest().getHeader("x-real-ip"));
        }
        planService.indexPlan(plan);
        Map<String, Object> result = Maps.newHashMap();
        result.put("success", true);
        result.put("id", plan.getId());
        return json(JSONObject.fromObject(result));
    }

    public Result update() {
        plan = planOperationService.updatePlan(planUpdateRequest, getLoginUser(), getRequest().getHeader("x-real-ip"));
        return text(plan.getId() + "");
    }

    public Result updateName() {
        plan = planService.get(planId);
        plan.setName(planName);
        planService.update(plan);
        return text(plan.getId() + "");
    }

    public Result saveTransAndHotel() throws IOException {
        if (StringUtils.isBlank(json)) {
            return text("success");
        }
        Map<String, Object> map = mapper.readValue(json, Map.class);
        List<Long> list = planBookingService.savePlanTransAndHotel(map, planId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("success", true);
        result.put("list", list);
        return json(JSONObject.fromObject(result));
    }

    public Result optimize() throws IOException {
        Clock clock = new Clock();
        PlanOptimizeRequest optimizeRequest = mapper.readValue(json, PlanOptimizeRequest.class);
        System.out.println("parse json cost " + clock.elapseTime() + "ms");
        PlanOptimizeResponse response = planOptimizeService.optimize(optimizeRequest);
        System.out.println("optimize cost " + clock.elapseTime() + "ms");
//        PlanOptimizeResponse response = planOptimizeService.testOptimize(optimizeRequest);
        return json(JSONObject.fromObject(response));
    }

    public Result getPlanResponse() {
        PlanOptimizeResponse response = planOperationService.getPlanResponse(planId);
        return json(JSONObject.fromObject(response));
    }

    public Result fillDetail() throws IOException {
        PlanOptimizeResponse response = mapper.readValue(json, PlanOptimizeResponse.class);
        planOptimizeService.fillInfo(response);
        return json(JSONObject.fromObject(response));
    }

    public Result edit() {
        if (planId != null) {
            plan = planService.get(planId);
        }
        return dispatch();
    }

    public Result booking() throws IOException {
        String ipAddr = StringUtils.getIpAddr(getRequest());
        Map<Long, Map<String, String>> changedTraffic = (Map<Long, Map<String, String>>) getSession().getAttribute(CHANGED_TRAFFIC);
        Map<Long, TrafficType> changedTrafficType = (Map<Long, TrafficType>) getSession().getAttribute(CHANGED_TRAFFIC_TYPE);
        Long changeCity = (Long) getSession().getAttribute(CHANGED_CITY);
        if (newOne != null && newOne) {
            changedTraffic = Maps.newHashMap();
            changedTrafficType = Maps.newHashMap();
            changeCity = null;
            getSession().setAttribute(CHANGED_CITY, null);
        } else {
            if (changedTraffic == null) {
                changedTraffic = Maps.newHashMap();
            }
            if (changedTrafficType == null) {
                changedTrafficType = Maps.newHashMap();
            }
            changeTraffic(changedTraffic);
        }
        Date nowDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateStr = getRequest().getParameter("nowDate");
        try {
            if (StringUtils.isNotBlank(nowDateStr)) {
                nowDate = format.parse(nowDateStr);
            } else if (city != null) {
                Date firstDate = format.parse(firstLeave);
                Date secondDate = format.parse(secondLeave);
                Date newDate = format.parse(newLeave);
                Long change = newDate.getTime() - secondDate.getTime();
                nowDate = new Date(firstDate.getTime() + change);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getSession().setAttribute(CHANGED_TRAFFIC, changedTraffic);
        Plan plan = null;
        if (planId != null) {
            plan = planService.get(planId);
        } else if (StringUtils.isNotBlank(json)) {
            plan = mapper.readValue(json, Plan.class);
        }
        if (plan != null) {
            TbArea startCity = plan.getStartCity();
            if (startCity == null) {
                startCity = planBookingService.getLocalCity(plan, ipAddr);
                plan.setStartCity(startCity);
            }
            bookingList = planBookingService.doBook(plan, changedTraffic, changeCity, changedTrafficType, nowDate);
        }
        return dispatch();
    }

    private void changeTraffic(Map<Long, Map<String, String>> changedTraffic) {
        if (city != null) {
            Map<String, String> changedCityTraffic = changedTraffic.get(city);
            if (changedCityTraffic == null) {
                changedCityTraffic = Maps.newHashMap();
            }
            changedCityTraffic.put(type, code);
            changedTraffic.put(city, changedCityTraffic);
        }
    }

    public Result bookingByCity() throws IOException {
        PlanBookingRequest bookingRequest = mapper.readValue(json, PlanBookingRequest.class);
        String firstCityId = (String) getParameter("firstCityId");
        if (StringUtils.isNotBlank(firstCityId)) {
            getSession().setAttribute(CHANGED_CITY, Long.valueOf(firstCityId));
        }
//        if (!bookingRequest.getFromCityId().equals(bookingRequest.getPrevFromCityId())) {
//            changeCity(bookingRequest);
//        }
        changeTrafficType(bookingRequest);
        PlanBookingResponse bookingResponse = planBookingService.doRecommendBooking(bookingRequest, isReturn);
        return json(JSONObject.fromObject(bookingResponse));
    }

    private void changeTrafficType(PlanBookingRequest bookingRequest) {
        Map<Long, TrafficType> map = (Map<Long, TrafficType>) getSession().getAttribute(CHANGED_TRAFFIC_TYPE);
        if (map == null) {
            map = Maps.newHashMap();
        }
        map.put(bookingRequest.getToCityId(), bookingRequest.getTrafficType());
        getSession().setAttribute(CHANGED_TRAFFIC_TYPE, map);
    }

    public Result list() {
        if (StringUtil.isNotBlank(cityIdStr)) {
            String[] cityIdStrArray = cityIdStr.split(",");
            List<Long> cityIdList = Lists.newArrayList(Lists.transform(Lists.newArrayList(cityIdStrArray), new Function<String, Long>() {
                @Override
                public Long apply(String s) {
                    return Long.valueOf(s);
                }
            }));
            cityList = areaService.getByIds(cityIdList);
        }
        return dispatch();
    }

    public Result getTotalPage() {
        Long result = planService.countFromSolr(searchRequest);
        return json(JSONArray.fromObject(result));
    }

    public Result getPlanList() {
        List<PlanSolrEntity> planSolrEntities = planService.listFromSolr(searchRequest, new Page(pageIndex, pageSize));
        return json(JSONArray.fromObject(planSolrEntities));
    }

    public Result suggest() {
        List<SuggestionEntity> list = suggestService.suggestPlan((String) getParameter("name"), PLAN_SUGGEST_COUNT);
        return json(JSONArray.fromObject(list));
    }

//    private void changeCity(PlanBookingRequest bookingRequest) {
//
//        if (bookingRequest.getPrevFromCityId() == null || bookingRequest.getFromCityId() == null) {
//            return;
//        }
//        Map<Long, Long> changedCity = (Map<Long, Long>) getSession().getAttribute(CHANGED_CITY);
//        if (changedCity == null) {
//            changedCity = Maps.newHashMap();
//        }
//        changedCity.put(bookingRequest.getPrevFromCityId(), bookingRequest.getFromCityId());
//        getSession().setAttribute(CHANGED_CITY, changedCity);
//    }

    @Override
    public PlanUpdateRequest getModel() {
        return planUpdateRequest;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
