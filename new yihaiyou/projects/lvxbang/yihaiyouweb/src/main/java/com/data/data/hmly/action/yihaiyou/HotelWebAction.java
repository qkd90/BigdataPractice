package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.request.PlanRecommendRequest;
import com.data.data.hmly.action.yihaiyou.response.HotelResponse;
import com.data.data.hmly.action.yihaiyou.response.HotelSimpleResponse;
import com.data.data.hmly.service.HotelMobileService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelCityBrandService;
import com.data.data.hmly.service.hotel.HotelCityServiceService;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelRegionService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelCityBrand;
import com.data.data.hmly.service.hotel.entity.HotelCityService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.solr.client.solrj.SolrQuery;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class HotelWebAction extends BaseAction {
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelMobileService hotelMobileService;
    @Resource
    private HotelRegionService hotelRegionService;
    @Resource
    private HotelCityBrandService hotelCityBrandService;
    @Resource
    private HotelCityServiceService hotelCityServiceService;
    @Resource
    private ScenicInfoService scenicInfoServiceService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private CommentService commentService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;

    private final ObjectMapper mapper = new ObjectMapper();

    public String json;
    public Long id;
    public String startDate;
    public String endDate;
    public Integer pageNo;
    public Integer pageSize;
    public Long cityId;
    public Long coreScenic;
    private HotelSearchRequest hotelSearchRequest = new HotelSearchRequest();
    public PlanRecommendRequest planRecommendRequest;

    private Map<String, Object> result = new HashMap<String, Object>();

    /**
     * 推荐酒店
     *
     * @return
     */
    @AjaxCheck
    public Result recommend() throws IOException, ParseException {
        planRecommendRequest = mapper.readValue(json, PlanRecommendRequest.class);
        List<HotelSimpleResponse> responseList = hotelMobileService.recommendHotel(planRecommendRequest);
        List<HotelSimpleResponse> res = new ArrayList<>();
        for (HotelSimpleResponse response : responseList) {
            if (response.getPriceId() != null) {
                res.add(response);
            }
        }
        result.put("hotelList", res);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 酒店列表
     *
     * @return
     */
    @AjaxCheck
    public Result list() throws IOException, ParseException {
        hotelSearchRequest = mapper.readValue(json, HotelSearchRequest.class);
        Page page = new Page(pageNo, pageSize);
        List<HotelSimpleResponse> list = hotelMobileService.listHotel(hotelSearchRequest, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("hotelList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result listHotel() {

        Page page = new Page(pageNo, pageSize);

        List<HotelSolrEntity> list;
        if (coreScenic == null || coreScenic < 1) {
            list = hotelService.listFromSolr(hotelSearchRequest, page);
        } else {
            ScenicInfo scenicInfo = scenicInfoServiceService.get(coreScenic);
            list = hotelService.findNearByHotel(hotelSearchRequest.getSolrQueryStr(), scenicInfo.getScenicGeoinfo().getBaiduLng(), scenicInfo.getScenicGeoinfo().getBaiduLat(), 1000000f, page);
        }
        RecommendPlanSearchRequest request = new RecommendPlanSearchRequest();
        for (HotelSolrEntity entity : list) {
            request.setHotelId(entity.getId());
            request.setOrderColumn("viewNum");
            request.setOrderType(SolrQuery.ORDER.desc);
            List<RecommendPlanSolrEntity> planList = recommendPlanService.listFromSolr(request, new Page(0, 1));
            if (planList != null && !planList.isEmpty()) {
                entity.setRecommendPlanId(planList.get(0).getId());
                entity.setRecommendPlanName(planList.get(0).getName());
            }
        }

        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("hotelList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));

//        return json(JSONArray.fromObject(list));
    }

    /**
     * 酒店区域
     *
     * @return
     */
    public Result listHotelRegion() {
        if (cityId == null)
            return json(JSONArray.fromObject(null));
        List<HotelRegion> regions = hotelRegionService.listByCity(String.valueOf(cityId));
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(regions, JsonFilter.getIncludeConfig("unitAreas", "leftArea", "rightArea", "toArea")));
    }

    /**
     * 酒店品牌
     *
     * @return
     */
    public Result listHotelBrand() {
        if (cityId == null)
            return json(JSONArray.fromObject(null));
        List<HotelCityBrand> brands = hotelCityBrandService.listByCity(cityId.intValue());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(brands));
    }

    /**
     * 酒店服务
     *
     * @return
     */
    public Result listHotelService() {
        if (cityId == null)
            return json(JSONArray.fromObject(null));
        List<HotelCityService> services = hotelCityServiceService.listByCity(cityId.intValue());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(services));
    }

    /**
     * 酒店详情
     *
     * @return
     */
    @AjaxCheck
    public Result detail() throws ParseException {
        Hotel hotel = hotelService.get(id);
        if (hotel == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        HotelResponse response = hotelMobileService.hotelDeatil(hotel, startDate, endDate);
        result.put("hotel", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result getHteiData() {
        if (cityId != null) {
            result = hotelMobileService.getHteiData(cityId);
            if (result.isEmpty()) {
                result.put("success", false);
                result.put("msg", "没有数据");
                return json(JSONObject.fromObject(result));
            }
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "无法获取城市信息");
            return json(JSONObject.fromObject(result));
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }


    /**
     * 酒店概要信息
     *
     * @return
     */
    @AjaxCheck
    public Result hotelInfo() throws LoginException {
        Hotel hotel = hotelService.get(id);
        result.put("hotelId", hotel.getId());
        result.put("hotelName", hotel.getName());
        result.put("score", hotel.getScore());
        result.put("star", hotel.getStar());
        result.put("starDesc", hotelStar(hotel.getStar()));
        result.put("address", hotel.getExtend().getAddress());
        result.put("shortDesc", hotel.getShortDesc());
        result.put("description", hotel.getExtend().getDescription());
        result.put("lat", hotel.getExtend().getLatitude());
        result.put("lng", hotel.getExtend().getLongitude());
        result.put("source", hotel.getSource().name());
        Productimage coverImage = productimageService.findCover(id, id, ProductType.hotel);
        List<Productimage> productImages = productimageService.findAllImagesByProIdAadTarId(id, null, null, "showOrder", "asc");
        // 酒店封面
        if (coverImage != null) {
            result.put("hotelCover", coverImage.getPath());
        } else {
            result.put("hotelCover", "");
        }
        // 酒店图片
        List<String> images = new ArrayList<String>();
        for (Productimage image : productImages) {
            images.add(image.getPath());
        }
        Member member = getLoginUser();
        if (member == null) {
            result.put("favorite", false);
        } else {
            result.put("favorite", otherFavoriteService.checkExists(ProductType.hotel, hotel.getId(), getLoginUser().getId()));
        }
        result.put("images", images);
        Comment c = new Comment();
        c.setTargetId(id);
        c.setType(ProductType.hotel);
        c.setStatus(CommentStatus.NORMAL);
        Long commentCount = commentService.countMyComment(c);
        result.put("commentCount", commentCount);

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 酒店房型列表信息
     *
     * @return
     */
    @AjaxCheck
    public Result listHotelPrice() {
        // 参数
        String hotelIdStr = (String) getParameter("hotelId");
        String tipInDateStr = (String) getParameter("tipInDate");   // 入住日期
        String tipOutDateStr = (String) getParameter("tipOutDate"); // 离店日期
//        String bntBedTypeStr = (String) getParameter("bntBedType"); // 床型
        String bntBreakfastStr = (String) getParameter("bntBreakfast"); // 早餐
        String bntPayWay = (String) getParameter("bntPayWay"); // 支付方式
        String bntPriceStr = (String) getParameter("bntPrice"); // 价格范围
        String priceLowStr = (String) getParameter("priceLow");
        String priceHighStr = (String) getParameter("priceHigh");

        HotelPrice searchCondition = new HotelPrice();
        Hotel hotelCondition = new Hotel();
        hotelCondition.setId(Long.valueOf(hotelIdStr));
        List<PriceStatus> statuses = Lists.newArrayList();
        if (StringUtils.isNotBlank(bntPayWay)) {
            if ("GUARANTEE".equals(bntPayWay)) {
                hotelCondition.setSource(ProductSource.ELONG);
                statuses.add(PriceStatus.GUARANTEE);
            } else if ("UP".equals(bntPayWay)) {
                hotelCondition.setSource(ProductSource.ELONG);
                statuses.add(PriceStatus.UP);
            } else if ("PAY".equals(bntPayWay)) {
                hotelCondition.setSource(ProductSource.LXB);
                statuses.add(PriceStatus.UP);
            }
        } else {
            statuses.add(PriceStatus.GUARANTEE);
            statuses.add(PriceStatus.UP);
        }
        searchCondition.setHotel(hotelCondition);
        searchCondition.setStart(DateUtils.getDate(tipInDateStr, "yyyy-MM-dd"));
        searchCondition.setEnd(DateUtils.getDate(tipOutDateStr, "yyyy-MM-dd"));
//        if (StringUtils.isNotBlank(bntBedTypeStr)) {
//            searchCondition.setBntBedType(bntBedTypeStr);
//        }
        if (StringUtils.isNotBlank(bntBreakfastStr)) {
            searchCondition.setBreakfast(Boolean.valueOf(bntBreakfastStr));
        }
        if (StringUtils.isNotBlank(bntPriceStr)) {
            searchCondition.setPriceLow(Float.valueOf(priceLowStr));
            searchCondition.setPriceHigh(Float.valueOf(priceHighStr));
        }

        //去数据库查酒店价格
        List<HotelPrice> list = hotelPriceService.list(searchCondition, statuses, new Page(0, Integer.MAX_VALUE), "showOrder", "asc");
        List<HotelPrice> priceList = Lists.newArrayList();
        for (HotelPrice hotelPrice : list) {
            List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPrice.getId(), searchCondition.getStart(), searchCondition.getEnd());
            if (!calendarList.isEmpty()) {
                Float calendarPrice = 0f;
                for (HotelPriceCalendar hotelPriceCalendar : calendarList) {
                    calendarPrice += hotelPriceCalendar.getMember();
                }
                hotelPrice.setPrice(Math.round((calendarPrice / calendarList.size()) * 100) / 100f);
                priceList.add(hotelPrice);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(priceList, JsonFilter.getIncludeConfig());
        result.put("hotelPriceList", jsonArray);

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result hotelCommentInfo() {
        String hotelIdStr = (String) getParameter("hotelId");

        // 获取评论列表、点评数
        Comment comment = new Comment();
        comment.setType(ProductType.hotel);
        comment.setTargetId(Long.valueOf(hotelIdStr));
        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> commentList = commentService.list(comment, null);

        Integer fiveStar = 0;
        Integer fourStar = 0;
        Integer threeStar = 0;
        Integer twoStar = 0;
        Integer oneStar = 0;
        Integer zeoStar = 0;
        Integer totalScore = 0;
        Integer avgScore = 0;
        Float resultScore = 0F;
        for (Comment c : commentList) {
            List<CommentScore> commentScores = c.getCommentScores();
            for (CommentScore cs : commentScores) {
                if (cs.getScore() != null && cs.getScore() / 20F <= 5 && cs.getScore() / 20F > 4) {
                    fiveStar++;
                } else if (cs.getScore() != null && cs.getScore() / 20F <= 4 && cs.getScore() / 20F > 3) {
                    fourStar++;
                } else if (cs.getScore() != null && cs.getScore() / 20F <= 3 && cs.getScore() / 20F > 2) {
                    threeStar++;
                } else if (cs.getScore() != null && cs.getScore() / 20F <= 2 && cs.getScore() / 20F > 1) {
                    twoStar++;
                } else if (cs.getScore() != null && cs.getScore() / 20F <= 1 && cs.getScore() / 20F > 0) {
                    oneStar++;
                } else {
                    zeoStar++;
                }
                if (cs.getScore() != null) {
                    totalScore += cs.getScore();
                }

            }
        }
        if (totalScore > 0) {
            avgScore = totalScore / commentList.size();
            resultScore = avgScore / 20F;
        }

        result.put("fiveStar", fiveStar);
        result.put("fourStar", fourStar);
        result.put("threeStar", threeStar);
        result.put("twoStar", twoStar);
        result.put("oneStar", oneStar);
        result.put("zeoStar", zeoStar);
        result.put("avgScore", avgScore);
        result.put("totalCount", commentList.size());
        result.put("resultScore", resultScore);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 酒店评论
     *
     * @return
     */
    @AjaxCheck
    public Result hotelCommentList() {
        String hotelIdStr = (String) getParameter("hotelId");

        // 获取评论列表、点评数
        Page page = new Page(pageNo, pageSize);
        Comment comment = new Comment();
        comment.setType(ProductType.hotel);
        comment.setTargetId(Long.valueOf(hotelIdStr));
        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> list = commentService.list(comment, page);
        for (Comment c : list) {
            if (c.getUser() != null) {
                c.setNickName(c.getUser().getNickName());
            }
            // 获取最高评分和最低评分
            List<CommentScore> scores = c.getCommentScores();
            for (CommentScore s : scores) {
                if (c.getMaxScore() == null || (s.getScore() != null && s.getScore() > c.getMaxScore())) {
                    c.setMaxScore(s.getScore());
                }
                if (c.getMinScore() == null || (s.getScore() != null && s.getScore() < c.getMinScore())) {
                    c.setMinScore(s.getScore());
                }
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(list, JsonFilter.getIncludeConfig("comments"));
        result.put("commentList", jsonArray);
        result.put("commentCount", page.getTotalCount());
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    private String hotelStar(Integer star) {
        if (star == null) {
            return "客栈/经济";
        }
        switch (star) {
            case 1:
                return "一星级";
            case 2:
                return "二星级";
            case 3:
                return "三星级";
            case 4:
                return "四星级";
            case 5:
                return "五星级";
            default:
                return "客栈/经济";
        }
    }

    /**
     * 酒店日历选择 - 页面日期数据
     *
     * @return
     */
    @AjaxCheck
    public Result calendar() {
        List<Map<String, Object>> monthList = new ArrayList<Map<String, Object>>();    // 月列表
        Calendar calendar = Calendar.getInstance();
        String today = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");
        for (int i = 0; i < 3; i++) {  // 默认展示3个月
            calendar.set(Calendar.DAY_OF_MONTH, 1);     // 月第1天
            int curMonth = calendar.get(Calendar.MONTH);
            int month = calendar.get(Calendar.MONTH);
            int curWeek = 0;
            Map<String, Object> curWeekMap = null;
            List<Map<String, Object>> weekList = new ArrayList<Map<String, Object>>();    // 周列表
            // 月信息
            Map<String, Object> curMonthMap = new HashMap<>();
            curMonthMap.put("name", monthName(month));
            curMonthMap.put("year", calendar.get(Calendar.YEAR));
            curMonthMap.put("month", month + 1);
            curMonthMap.put("weeks", weekList);
            monthList.add(curMonthMap);
            while (curMonth == month) {
                int week = calendar.get(Calendar.WEEK_OF_MONTH);
                if (week != curWeek) {
                    // 周信息
                    Map<String, Object> weekMap = new HashMap<>();
                    weekMap.put("weekOfMonth", calendar.get(Calendar.WEEK_OF_MONTH));
                    weekMap.put("year", calendar.get(Calendar.YEAR));
                    weekMap.put("month", month + 1);
                    ((List) curMonthMap.get("weeks")).add(weekMap);
                    curWeekMap = weekMap;
                    curWeek = week;
                }
                // 天信息
                Map<String, Object> curDayMap = new HashMap<>();
                String date = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");
                if (today.equals(date)) {
                    curDayMap.put("dayOfMonth", "今天");
                } else {
                    curDayMap.put("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
                }
                curDayMap.put("year", calendar.get(Calendar.YEAR));
                curDayMap.put("month", month + 1);
                curDayMap.put("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));
                curDayMap.put("date", date);
                curWeekMap.put(weekKey(calendar.get(Calendar.DAY_OF_WEEK)), curDayMap);
                // 加1天
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                month = calendar.get(Calendar.MONTH);
            }
        }

        result.put("months", monthList);
        result.put("today", today);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    private String weekKey(int week) {
        switch (week) {
            case 1:
                return "sun";
            case 2:
                return "mon";
            case 3:
                return "tue";
            case 4:
                return "wed";
            case 5:
                return "thu";
            case 6:
                return "fri";
            case 7:
                return "sat";
            default:
                return "";
        }
    }

    private String monthName(int month) {
        switch (month) {
            case 0:
                return "一月";
            case 1:
                return "二月";
            case 2:
                return "三月";
            case 3:
                return "四月";
            case 4:
                return "五月";
            case 5:
                return "六月";
            case 6:
                return "七月";
            case 7:
                return "八月";
            case 8:
                return "九月";
            case 9:
                return "十月";
            case 10:
                return "十一月";
            case 11:
                return "十二月";
            default:
                return "";
        }
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public HotelSearchRequest getHotelSearchRequest() {
        return hotelSearchRequest;
    }

    public void setHotelSearchRequest(HotelSearchRequest hotelSearchRequest) {
        this.hotelSearchRequest = hotelSearchRequest;
    }

    public Long getCoreScenic() {
        return coreScenic;
    }

    public void setCoreScenic(Long coreScenic) {
        this.coreScenic = coreScenic;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
