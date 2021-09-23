package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.other.util.VisitHistoryCookieUtils;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.build.LvXBangBuildService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.ComplexSearchRequest;
import com.data.data.hmly.service.common.vo.ComplexSolrEntity;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.lvxbang.ComplexSearchService;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.lvxbang.response.SimpleRecommendPlanResponse;
import com.data.data.hmly.service.other.OtherVisitHistoryService;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.data.data.hmly.service.plan.RecommendPlanDayService;
import com.data.data.hmly.service.plan.RecommendPlanPhotoService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.RecommendPlanTripService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanPhoto;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.plan.vo.SimpleRecommendPlanPhoto;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/23.
 */
public class RecommendPlanWebAction extends LxbAction {

    private static final int RECOMMEND_PLAN_SUGGEST_COUNT = 6;

    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private RecommendPlanDayService recommendPlanDayService;
    @Resource
    private RecommendPlanTripService recommendPlanTripService;
    @Resource
    private RecommendPlanPhotoService recommendPlanPhotoService;
    @Resource
    private TransportationService transportationService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private ComplexSearchService complexSearchService;
    @Resource
    private OtherVisitHistoryService otherVisitHistoryService;
    @Resource
    private LvXBangBuildService lvXBangBuildService;

    public RecommendPlanSearchRequest recommendPlanSearchRequest = new RecommendPlanSearchRequest();
    public ComplexSearchRequest complexSearchRequest = new ComplexSearchRequest();
    public RecommendPlan recommendPlan = new RecommendPlan();
    public RecommendPlanPhoto recommendPlanPhoto = new RecommendPlanPhoto();
    public List<LabelItem> labelItems = new ArrayList<LabelItem>();
    public String startTime = "";
    public Long recplanId = 0L;
    public Integer pageIndex = 0;
    public Integer pageSize = 10;
    public Comment comment = new Comment();
    public OtherVisitHistory otherVisitHistory = new OtherVisitHistory();

    public Result index() {
        setAttribute(LXBConstants.LVXBANG_RECOMMENDPLAN_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_RECOMMENDPLAN_INDEX));
        return dispatch();
    }

    public Result detail() {
        Integer status = recommendPlanService.getStatus(recplanId);
        if (status != 2) {
            return dispatch404();
        } else {
            setAttribute(LXBConstants.LVXBANG_RECOMMENDPLAN_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_RECOMMENDPLAN_DETAIL + recplanId));
            setAttribute(LXBConstants.LVXBANG_RECOMMENDPLAN_HEAD_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_RECOMMENDPLAN_HEAD + recplanId));
            return dispatch();
        }
    }

    public Result list() {
        HttpServletRequest request = getRequest();
        if (StringUtils.isNotBlank(request.getParameter("name")) && StringUtils.isNotBlank(request.getParameter("relatedId"))
            && StringUtils.isNotBlank(request.getParameter("type"))) {
            request.setAttribute("relatedId", Long.parseLong(request.getParameter("relatedId")));
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("type", request.getParameter("type"));
        }
        if (StringUtils.isNotBlank(request.getParameter("cityIds"))) {
            List<Long> cityIds = new ArrayList<Long>();
            List<TbArea> areas = new ArrayList<TbArea>();
            String[] strs = request.getParameter("cityIds").split(",");
            for (String str : strs) {
                cityIds.add(Long.parseLong(str));
                areas.add(tbAreaService.getArea(Long.parseLong(str)));
            }
            request.setAttribute("areas", JSONArray.fromObject(areas, JsonFilter.getIncludeConfig()));
            recommendPlanSearchRequest.setCityIds(cityIds);
        }
       return dispatch();
    }

    public Result suggest() {
        List<SuggestionEntity> list = suggestService.suggestRecommendPlan((String) getParameter("name"), RECOMMEND_PLAN_SUGGEST_COUNT);
        return json(JSONArray.fromObject(list));
    }

    /**
     * 相关游记,游记列表入口
     * @return
     */
    public Result getRecplanList() {
//        HttpServletRequest request = getRequest();
        Page page = new Page(pageIndex, pageSize);
        List<RecommendPlanSolrEntity> planSolrEntityList = recommendPlanService.listFromSolr(recommendPlanSearchRequest, page);
        return json(JSONArray.fromObject(planSolrEntityList));
    }

    public Result edit() {
        final HttpServletRequest request = getRequest();
        getLoginUser();
        if (recplanId != null && recplanId > 0) {
            SimpleRecommendPlanResponse simpleRecommendPlanResponse = new SimpleRecommendPlanResponse();
            RecommendPlan recommendPlan = recommendPlanService.get(recplanId);
            if (recommendPlan == null) {
                return dispatch404();
            }
            simpleRecommendPlanResponse.id = recommendPlan.getId();
            simpleRecommendPlanResponse.planName = recommendPlan.getPlanName();
            simpleRecommendPlanResponse.description = recommendPlan.getDescription();
            simpleRecommendPlanResponse.coverPath = recommendPlan.getCoverPath();
            simpleRecommendPlanResponse.days = recommendPlan.getDays();
            simpleRecommendPlanResponse.cityId = recommendPlan.getCity() != null ? recommendPlan.getCity().getId() : null;
            simpleRecommendPlanResponse.cityIds = recommendPlan.getCityIds();
            simpleRecommendPlanResponse.scenics = recommendPlan.getScenics();
            simpleRecommendPlanResponse.status = recommendPlan.getStatus();
            simpleRecommendPlanResponse.startTime = recommendPlan.getStartTime();
            request.setAttribute("recommendPlan", simpleRecommendPlanResponse);
            return dispatch();
        } else {
            // 重定向新建游记链接
            Member user = getLoginUser();
            RecommendPlan recommendPlan = new RecommendPlan();
            recommendPlan.setUser(user);
            recommendPlan.setStatus(1);
            recommendPlan.setDeleteFlag(2);
            recommendPlan.setValid(1);
            recommendPlan.setCreateTime(new Date());
            recommendPlanService.save(recommendPlan);
            // 默认增加一天
            RecommendPlanDay recommendPlanDay = new RecommendPlanDay();
            recommendPlanDay.setRecommendPlan(recommendPlan);
            recommendPlanDay.setDay(1);
            recommendPlanDayService.save(recommendPlanDay);
            // 更新游记天数 和 游记经过的节点数目(前台默认会为新的一天添加一个节点)
            recommendPlanService.updateRecplanAfterAddDay(recommendPlan.getId());
            // 默认增加一个节点
            RecommendPlanTrip recommendPlanTrip = new RecommendPlanTrip();
            recommendPlanTrip.setRecommendPlan(recommendPlan);
            recommendPlanTrip.setRecommendPlanDay(recommendPlanDay);
            recommendPlanTripService.save(recommendPlanTrip);
            // 更新当天节点数目
            recommendPlanDayService.updateAfterAddTrip(recommendPlanDay.getId());
            // 更新整个游记经过的节点数目(...)
            recommendPlanService.updateRecplanAfterAddTrip(recommendPlan.getId());
            return redirect("/lvxbang/recommendPlan/edit.jhtml?recplanId=" + recommendPlan.getId());
        }
    }
    public Result preview() {
        final HttpServletRequest request = getRequest();
        if (recplanId > 0) {
            RecommendPlan recplan = recommendPlanService.get(recplanId);
            User user = getLoginUser();
            if (!user.getId().equals(recplan.getUser().getId())) {
                return dispatch404();
            }
            Integer status = recommendPlanService.getStatus(recplanId);
            if (status != 1) {
                return dispatch404();
            }
            SimpleRecommendPlanResponse simpleRecommendPlanResponse = new SimpleRecommendPlanResponse();
            simpleRecommendPlanResponse.id = recplan.getId();
            simpleRecommendPlanResponse.planName = recplan.getPlanName();
            simpleRecommendPlanResponse.description = recplan.getDescription();
            simpleRecommendPlanResponse.coverPath = recplan.getCoverPath();
            simpleRecommendPlanResponse.days = recplan.getDays();
            simpleRecommendPlanResponse.cityId = recplan.getCity() != null ? recplan.getCity().getId() : null;
            simpleRecommendPlanResponse.cityName = recplan.getCity() != null ? recplan.getCity().getName() : "";
            simpleRecommendPlanResponse.cityIds = recplan.getCityIds();
            simpleRecommendPlanResponse.scenics = recplan.getScenics();
            simpleRecommendPlanResponse.status = recplan.getStatus();
            simpleRecommendPlanResponse.startTime = recplan.getStartTime();
            request.setAttribute("recplan", simpleRecommendPlanResponse);
            return dispatch();
        } else {
            return dispatch404();
        }
    }

    public Result previewRecommendPlan() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (recplanId == null || recplanId <= 0) {
            result.put("result", "not_exists");
            return json(JSONObject.fromObject(result));
        } else {
            User user = getLoginUser();
            RecommendPlan recommendPlan = recommendPlanService.get(recplanId);
            if (!recommendPlan.getUser().getId().equals(user.getId())) {
                result.put("result", "user_not_match");
                return json(JSONObject.fromObject(result));
            } else {
                recommendPlanService.makeDetailRecplanOnBuildOrEdit(recommendPlan);
                return json(JSONObject.fromObject(recommendPlan, JsonFilter.getIncludeConfig("recommendPlanDays", "recommendPlanTrips", "photos")));
            }
        }
    }

    public Result editRecommendPlan() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (recplanId == null || recplanId <= 0) {
            result.put("result", "not_exists");
            return json(JSONObject.fromObject(result));
        } else {
            User user = getLoginUser();
            RecommendPlan recommendPlan = recommendPlanService.get(recplanId);
            if (!recommendPlan.getUser().getId().equals(user.getId())) {
                result.put("result", "user_not_match");
                return json(JSONObject.fromObject(result));
            } else {
                recommendPlanService.makeDetailRecplanOnBuildOrEdit(recommendPlan);
                return json(JSONObject.fromObject(recommendPlan, JsonFilter.getIncludeConfig("city", "delicacy", "recommendPlanDays", "recommendPlanTrips", "photos", "labelItemsVos")));
            }
        }
    }

    public Result newRecommendPlan() {
        Member user = getLoginUser();
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setUser(user);
        recommendPlan.setStatus(1);
        recommendPlan.setDeleteFlag(2);
        recommendPlan.setValid(1);
        recommendPlan.setCreateTime(new Date());
        recommendPlanService.save(recommendPlan);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("recplanId", recommendPlan.getId());
        return json(JSONObject.fromObject(result));
    }
    public Result addOneDay() {
        RecommendPlanDay recommendPlanDay = new RecommendPlanDay();
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setId(recplanId);
        recommendPlanDay.setRecommendPlan(recommendPlan);
        recommendPlanDay.setDay(Integer.parseInt(getRequest().getParameter("day")));
        recommendPlanDayService.save(recommendPlanDay);
        // 更新游记天数 和 游记经过的节点数目(前台默认会为新的一天添加一个节点)
        recommendPlanService.updateRecplanAfterAddDay(recplanId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("recommendPlanDayId", recommendPlanDay.getId());
        return json(JSONObject.fromObject(result));
    }

    public Result addOneTrip() {
        RecommendPlanTrip recommendPlanTrip = new RecommendPlanTrip();
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setId(recplanId);
        recommendPlanTrip.setRecommendPlan(recommendPlan);
        RecommendPlanDay recommendPlanDay = new RecommendPlanDay();
        Long recommendPlanDayId = Long.parseLong(getRequest().getParameter("recommendPlanDayId"));
        recommendPlanDay.setId(recommendPlanDayId);
        recommendPlanTrip.setRecommendPlanDay(recommendPlanDay);
        recommendPlanTripService.save(recommendPlanTrip);
        // 更新当天节点数目
        recommendPlanDayService.updateAfterAddTrip(recommendPlanDayId);
        // 更新整个游记经过的节点数目(...)
        recommendPlanService.updateRecplanAfterAddTrip(recplanId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("recommendPlanTripId", recommendPlanTrip.getId());
        return json(JSONObject.fromObject(result));
    }

    public Result deleteOneDay() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("day")) && StringUtils.isNotBlank(request.getParameter("recplanId"))
                && StringUtils.isNotBlank(request.getParameter("scenics")) && StringUtils.isNotBlank(request.getParameter("recommendPlanDayId"))) {
            Integer day = Integer.parseInt(request.getParameter("day"));
            Integer scenics = Integer.parseInt(request.getParameter("scenics"));
            Long recplanId = Long.parseLong(request.getParameter("recplanId"));
            Long recommendPlanDayId = Long.parseLong(request.getParameter("recommendPlanDayId"));
            // 删除该天, 并更新该游记天数次序,
            recommendPlanDayService.deleteOneDay(day, recplanId, recommendPlanDayId);
            // 更新游记经过景点数目
            recommendPlanService.updateRecplanAfterDeleteDay(recplanId, scenics);
            // 删除该天下的所有节点
            recommendPlanTripService.deleteTripByDay(recommendPlanDayId);
            result.put("msg", "success");
            return redirect("222");
//            return json(JSONObject.fromObject(result));
        } else {
            throw new RuntimeException("缺少必要的参数!");
        }
    }

    public Result deleteOneTrip() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("sort")) && StringUtils.isNotBlank(request.getParameter("recommendPlanTripId"))) {
            Integer sort = Integer.parseInt(request.getParameter("sort"));
            Long recplanId = Long.parseLong(request.getParameter("recplanId"));
            Long recommendPlanDayId = Long.parseLong(request.getParameter("recommendPlanDayId"));
            Long recommendPlanTripId = Long.parseLong(request.getParameter("recommendPlanTripId"));
            // 删除该节点, 更新该天行程节点次序
            recommendPlanTripService.deleteOneTrip(sort, recommendPlanDayId, recommendPlanTripId);
            // 更新该天经过的景点数目
            recommendPlanDayService.updateAfterDeleteTrip(recommendPlanDayId);
            // 更新游记经过景点数目
            recommendPlanService.updateRecplanAfterDeleteTrip(recplanId);
            result.put("msg", "success");
            return json(JSONObject.fromObject(result));
        } else {
            throw new RuntimeException("缺少必要的参数");
        }
    }
    public Result deleteOnePhoto() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("id"))) {
            Long id = Long.parseLong(request.getParameter("id"));
            recommendPlanPhotoService.delete(id);
            result.put("msg", "success");
            return json(JSONObject.fromObject(result));
        } else {
            throw  new RuntimeException("缺少必要的参数! ");
        }
    }
    public Result doEdit() {
        try {
            if (startTime != null && !"".equals(startTime)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = df.parse(startTime);
                recommendPlan.setStartTime(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        recommendPlanService.update(recommendPlan);
        // 处理游记推荐人标签
        recommendPlanService.doHandleRecommendPlanLabel(recommendPlan.getId(), labelItems);
        // 索引 和 构建静态页面
        lvXBangBuildService.buildOneRecplan(recommendPlan.getId());
        lvXBangBuildService.buildRecplanIndex();
        lvXBangBuildService.buildIndex();
        recommendPlanService.indexRecommendPlan(recommendPlan);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "success");
        return json(JSONObject.fromObject(result));
    }

    public Result multipleNameList() {
        List<ComplexSolrEntity> complexSolrEntityList;
        if ("other".equals(complexSearchRequest.getType().toString())) {
            return null;
        } else if (complexSearchRequest.getType() == SolrType.transportation) {
            complexSolrEntityList = new ArrayList<ComplexSolrEntity>();
            List<Transportation> transportations = transportationService.findByName(complexSearchRequest.getName());
            for (Transportation transportation : transportations) {
                ComplexSolrEntity complexSolrEntity = new ComplexSolrEntity();
                complexSolrEntity.setId(transportation.getId());
                complexSolrEntity.setName(transportation.getName());
                complexSolrEntity.setCity(transportation.getCityName());
                complexSolrEntity.setCityId(Long.parseLong(transportation.getCityCode()));
                complexSolrEntity.setType(SolrType.transportation);
                complexSolrEntity.setTransportationType(transportation.getType() != null ? transportation.getType() : 0);
                complexSolrEntityList.add(complexSolrEntity);
            }
        } else if (complexSearchRequest.getType() == null) {
            return null;
        } else {
            complexSolrEntityList = complexSearchService.listFromSolr(complexSearchRequest, new Page(1, 10));
        }
        return json(JSONArray.fromObject(complexSolrEntityList));
    }

    public Result saveImg() {
        Map<String, Object> result = new HashMap<String, Object>();
        recommendPlanPhoto.setCreateTime(new Date());
        recommendPlanPhoto.setModifyTime(new Date());
        Integer nowSort = recommendPlanPhoto.getSort();
        Integer maxSort = recommendPlanPhotoService.getMaxSort(recommendPlanPhoto.getRecommendPlanTrip().getId());
        recommendPlanPhoto.setSort(nowSort + maxSort + 1);
        recommendPlanPhotoService.save(recommendPlanPhoto);
        SimpleRecommendPlanPhoto simpleRecommendPlanPhoto = new SimpleRecommendPlanPhoto();
        simpleRecommendPlanPhoto.setId(recommendPlanPhoto.getId());
        simpleRecommendPlanPhoto.setPhotoLarge(recommendPlanPhoto.getPhotoLarge());
        simpleRecommendPlanPhoto.setWidth(recommendPlanPhoto.getWidth());
        simpleRecommendPlanPhoto.setHeight(recommendPlanPhoto.getHeight());
        simpleRecommendPlanPhoto.setSort(recommendPlanPhoto.getSort());
        result.put("msg", "success");
        result.put("data", simpleRecommendPlanPhoto);
        return json(JSONObject.fromObject(result));
    }

    public Result getTotalPage() {
        Long count = recommendPlanService.countFromSolr(recommendPlanSearchRequest);
        return json(JSONArray.fromObject(count));
    }

    /**
     * 处理游记浏览次数
     * @return
     */
    public Result addViewNum() {
        final HttpServletRequest request = getRequest();
        final HttpServletResponse response = getResponse();
        User user = (User) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        Long resObjectId = otherVisitHistory.getResObjectId();
        String cookieId = VisitHistoryCookieUtils.getCookieId(request, response);
        simpleResult(result, true, "");
        if (user != null) {
            otherVisitHistory.setUserId(user.getId());
            otherVisitHistory.setCookieId(cookieId);
            otherVisitHistory.setDeleteFlag(true);
        } else {
//            String cookieId = getRequest().getCookies()[0].getValue();
            otherVisitHistory.setUserId(-9999L);
            otherVisitHistory.setCookieId(cookieId);
            otherVisitHistory.setDeleteFlag(true);
        }
        // 取出现有游记的浏览数目
        Integer nowViewNum = recommendPlanService.getViewNum(resObjectId);
        // 取出引用数目
        Integer quoteNum = recommendPlanService.getQuoteNum(resObjectId);
        Page page = new Page(1, 10);
        // 取出已经浏览过的记录(至少一条有效), 若无此记录, 则代表未访问过, 需要将游记的viewNum加一
        List<OtherVisitHistory> list = otherVisitHistoryService.findOtherVisitHistoryList(otherVisitHistory, page);
        if (list.size() > 1) {
            // 返回分享数目
            result.put("viewNum", nowViewNum);
            // 返回引用数目
            result.put("quoteNum", quoteNum);
            return jsonResult(result);
        }
        recommendPlanService.updateViewNum(resObjectId, nowViewNum + 1);

        // 返回分享数目
        result.put("viewNum", nowViewNum + 1);
        // 返回引用数目
        result.put("quoteNum", quoteNum);
        return jsonResult(result);
    }

    public Result indexRecommendPlan() {
        try {
            RecommendPlan condotion = new RecommendPlan();
            if (getRequest().getParameter("startId") != null) {
                long startId = Long.parseLong(getRequest().getParameter("startId").toString());
                condotion.setStartId(startId);
            }
            if (getRequest().getParameter("endId") != null) {
                long endId = Long.parseLong(getRequest().getParameter("endId").toString());
                condotion.setEndId(endId);
            }
            recommendPlanService.indexRecommendPlan(condotion, null);
            return text("索引成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return text("索引失败!");
        }
    }
    public RecommendPlanSearchRequest getRecommendPlanSearchRequest() {
        return recommendPlanSearchRequest;
    }

    public void setRecommendPlanSearchRequest(RecommendPlanSearchRequest recommendPlanSearchRequest) {
        this.recommendPlanSearchRequest = recommendPlanSearchRequest;
    }

    public ComplexSearchRequest getComplexSearchRequest() {
        return complexSearchRequest;
    }

    public void setComplexSearchRequest(ComplexSearchRequest complexSearchRequest) {
        this.complexSearchRequest = complexSearchRequest;
    }

    public RecommendPlan getRecommendPlan() {
        return recommendPlan;
    }

    public void setRecommendPlan(RecommendPlan recommendPlan) {
        this.recommendPlan = recommendPlan;
    }

    public RecommendPlanPhoto getRecommendPlanPhoto() {
        return recommendPlanPhoto;
    }

    public void setRecommendPlanPhoto(RecommendPlanPhoto recommendPlanPhoto) {
        this.recommendPlanPhoto = recommendPlanPhoto;
    }

    public List<LabelItem> getLabelItems() {
        return labelItems;
    }

    public void setLabelItems(List<LabelItem> labelItems) {
        this.labelItems = labelItems;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getRecplanId() {
        return recplanId;
    }

    public void setRecplanId(Long recplanId) {
        this.recplanId = recplanId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public OtherVisitHistory getOtherVisitHistory() {
        return otherVisitHistory;
    }

    public void setOtherVisitHistory(OtherVisitHistory otherVisitHistory) {
        this.otherVisitHistory = otherVisitHistory;
    }
}
