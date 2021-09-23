package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.lvxbang.response.HotelPriceResponse;
import com.data.data.hmly.action.lvxbang.response.MiniLineResponse;
import com.data.data.hmly.action.lvxbang.response.MinifyHotelPrice;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.*;
import com.data.data.hmly.service.hotel.entity.*;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * Created by guoshijie on 2015/12/16.
 */
public class HotelWebAction extends JxmallAction implements ModelDriven<HotelSearchRequest> {

    private static final int LIST_RECOMMEND_HOTEL_SIZE = 9;

    @Resource
    private AreaService areaService;
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelRegionService hotelRegionService;
    @Resource
    private HotelCityServiceService hotelCityServiceService;
    @Resource
    private HotelCityBrandService hotelCityBrandService;
    //    @Resource
//    private HotelPriceService hotelPriceService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private SuggestService suggestService;
//    @Resource
//    private HotelElongService hotelElongService;
    @Resource
    private ScenicInfoService scenicInfoServiceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;

    public Long hotelId;
    public PriceStatus status;
    public Long planId;
    public String priceStartDate;
    public String priceEndDate;
    public String json;
    public Long cityId;
    public Long hotelCityId;
    private HotelSearchRequest searchRequest = new HotelSearchRequest();
    private int pageNo;
    private int pageSize;
    public String startDate;
    public String endDate;
    public String startDate1;
    public String endDate1;
    public String minDate;
    public String maxDate;
    public String firstLeaveDate;
    public String areaName;
    public String name;
    public Long coreScenic;
    public Integer priceIndex;
    public String region;
    private List<HotelSolrEntity> recommendHotelList;
    private List<HotelRegion> regionList;


    public Result index() {
        setAttribute(LXBConstants.LVXBANG_HOTEL_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_HOTEL_BANNER));
        setAttribute(LXBConstants.LVXBANG_HOTEL_SIDE_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_HOTEL_SIDE_BANNER));
        setAttribute(LXBConstants.LVXBANG_HOTEL_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_HOTEL_INDEX));
        return dispatch();
    }

    public Result hotelLargeMap() {
        return dispatch();
    }

    public Result detail() {
        Hotel hotel = hotelService.get(hotelId);
        if (hotel == null) {
            return redirect((String) getServletContext().getAttribute("HOTEL_PATH"));
        }
        setAttribute(LXBConstants.LVXBANG_HOTEL_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_HOTEL_DETAIL + hotelId));
        setAttribute(LXBConstants.LVXBANG_HOTEL_HEAD_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_HOTEL_HEAD + hotelId));
        return dispatch();
    }

    /**
     * request hotel id
     *
     * @return json of hotel detail for large map
     */
    public Result detailForMap() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.setExcludes(new String[]{"weight", "commentList", "extend"});
        Hotel hotel = hotelService.get(hotelId);
        HotelDetailForMap detail = new HotelDetailForMap();
        Set<Productimage> productimages = hotel.getProductimage();
        List<String> images = new ArrayList<String>();
        int sum = 0;
        for (Productimage image : productimages) {
            if (sum > 2)
                break;
            sum++;
            images.add(image.getPath());
        }
        detail.setImages(images);
        detail.setCommentsNum(hotel.getCommentList() == null ? 0 : hotel.getCommentList().size());
        detail.setName(hotel.getName());
        detail.setAddress(hotel.getExtend().getAddress());
        detail.setStar(hotel.getStar());
        detail.setScore(hotel.getScore());
        return json(JSONArray.fromObject(detail, jsonConfig));
    }

    public Result list() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HttpServletRequest request = getRequest();
        if (hotelCityId != null) {
            cityId = hotelCityId;
            List<Long> cityIds = new ArrayList<Long>(1);
            cityIds.add(cityId);
            searchRequest.setCityIds(cityIds);
        }

        if (StringUtils.isNotBlank(request.getParameter("startDate"))) {
            startDate = request.getParameter("startDate");
            startDate1 = request.getParameter("startDate");
            searchRequest.setStartDate(startDate);
        }
        if (StringUtils.isNotBlank(request.getParameter("endDate"))) {
            endDate = request.getParameter("endDate");
            endDate1 = request.getParameter("endDate");
            searchRequest.setStartDate(endDate);
        }

        List<Hotel> hotelList = hotelService.getRecommendHotelOnList(LIST_RECOMMEND_HOTEL_SIZE);
        recommendHotelList = new ArrayList<HotelSolrEntity>();

        recommendHotelList.addAll(Lists.transform(hotelList, new Function<Hotel, HotelSolrEntity>() {

            @Override
            public HotelSolrEntity apply(Hotel hotel) {
                return new HotelSolrEntity(hotel, true);
            }
        }));
        LOG.info(stopWatch.toString());
        if (hotelList.size() < 9) {
            Hotel hotel = new Hotel();
            hotel.setCityId(hotelCityId);
            hotel.setStatus(ProductStatus.UP);

            List<HotelSolrEntity> list = hotelService.listFromSolr(searchRequest, new Page(0, LIST_RECOMMEND_HOTEL_SIZE));
            for (HotelSolrEntity hotelSolrEntity : list) {
                if (recommendHotelList.size() == 9) {
                    break;
                }
                Boolean canAppend = true;
                for (HotelSolrEntity solrEntity : recommendHotelList) {
                    if (solrEntity.getId().longValue() == hotelSolrEntity.getId().longValue()) {
                        canAppend = false;
                        break;
                    }
                }
                if (canAppend) {
                    recommendHotelList.add(hotelSolrEntity);
                }
            }
//            List<Hotel> cityHotelList = hotelService.list(hotel, new Page(0, 9), "score", "desc");
//            List<Long> hotelIds = Lists.transform(hotelList, new Function<Hotel, Long>() {
//                @Override
//                public Long apply(Hotel hotel) {
//                    return hotel.getId();
//                }
//            });
//            for (Hotel h: cityHotelList) {
//                if (hotelList.size() == 9) {
//                    break;
//                }
//                if (!hotelIds.contains(h.getId())) {
//                    hotelList.add(h);
//                }
//            }
        }


        LOG.info(stopWatch.toString());
        if (cityId != null) {
            TbArea city = areaService.get(cityId);
            searchRequest.getCities().add(city.getName());
        }

        LOG.info(stopWatch.toString());
        return dispatch();
    }

    @AjaxCheck
    public Result listHotel() {
        List<HotelSolrEntity> list;
        if (coreScenic == null || coreScenic < 1) {
            list = hotelService.listFromSolr(searchRequest, new Page(pageNo, pageSize));
        } else {
            ScenicInfo scenicInfo = scenicInfoServiceService.get(coreScenic);
            list = hotelService.findNearByHotel(searchRequest.getSolrQueryStr(), scenicInfo.getScenicGeoinfo().getBaiduLng(), scenicInfo.getScenicGeoinfo().getBaiduLat(), 1000000f, new Page(pageNo, pageSize));
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
        return json(JSONArray.fromObject(list));
    }

    public Result countHotel() {
            long hotelCount = hotelService.countFromSolr(searchRequest);
            return text(hotelCount + "");
    }

    public Result listHotelRegion() {
        if (cityId == null)
            return json(JSONArray.fromObject(null));
        List<HotelRegion> regions = hotelRegionService.listByCity(String.valueOf(cityId));
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(regions, JsonFilter.getIncludeConfig("unitAreas", "leftArea", "rightArea", "toArea")));
    }

    public Result listHotelBrand() {
        if (cityId == null)
            return json(JSONArray.fromObject(null));
        List<HotelCityBrand> brands = hotelCityBrandService.listByCity(cityId.intValue());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(brands));
    }

    public Result listHotelService() {
        if (cityId == null)
            return json(JSONArray.fromObject(null));
        List<HotelCityService> services = hotelCityServiceService.listByCity(cityId.intValue());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(services));
    }

    public Result listPrice() throws ParseException {
        HotelPriceResponse hotelPriceResponse = new HotelPriceResponse();
        if (priceStartDate == null) {
            hotelPriceResponse.setSuccess(false);
            hotelPriceResponse.setMsg("缺少入住时间");
            return json(JSONObject.fromObject(hotelPriceResponse));
        }
        Date start = DateUtils.parseShortTime(priceStartDate);
        Date end = DateUtils.parseShortTime(priceEndDate);
        hotelPriceResponse.setSuccess(true);
        hotelPriceResponse.setHotelId(hotelId);
        HotelPrice searchCondition = new HotelPrice();
        Hotel hotelCondition = new Hotel();
        hotelCondition.setId(hotelId);
        searchCondition.setHotel(hotelCondition);
        List<HotelPrice> list = hotelPriceService.list(searchCondition, null, new Page(0, Integer.MAX_VALUE), "price", "asc");
        List<MinifyHotelPrice> hotelPrices = Lists.newArrayList();
        for (HotelPrice hotelPrice : list) {
            List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPrice.getId(), start, end, 1);
            if (calendarList.size() == DateUtils.getDateDiff(start, end)) {
                MinifyHotelPrice minifyHotelPrice = createMinifyHotelPriceByHotelPrice(hotelPrice);
                minifyHotelPrice.price = hotelPriceCalendarPrice(calendarList);
                minifyHotelPrice.priceStartDate = priceStartDate;
                minifyHotelPrice.priceEndDate = priceEndDate;
                hotelPrices.add(minifyHotelPrice);
            }
        }
        hotelPriceResponse.setHotelPrices(hotelPrices);

        List<Line> lineList = linedaysProductPriceService.listLine(hotelId, ProductType.hotel, null);
        Set<Line> lines = Sets.newHashSet(lineList);
        List<MiniLineResponse> lineResponses = Lists.newArrayList();
        for (Line line : lines) {
            if (line.getPrice() > 0) {
                lineResponses.add(new MiniLineResponse(line));
            }
        }
        hotelPriceResponse.setLineResponses(lineResponses);

        //去数据库查酒店价格
//        实时去艺龙查酒店价格
//        Hotel h = hotelService.get(hotelId);
//        HotelPriceRequest hotelPriceRequest = new HotelPriceRequest();
//        hotelPriceRequest.setElongId(String.valueOf(h.getSourceId()));
//        hotelPriceRequest.setHotelId(hotelId);
//        hotelPriceRequest.setArrive(searchCondition.getStart());
//        hotelPriceRequest.setDeparture(searchCondition.getEnd());
//        List<HotelPrice> list =
//                hotelElongService.doHotelPrices(hotelPriceRequest);

//        if (!list.isEmpty()) {
//            hotelPriceResponse.setHotelPrices(Lists.transform(list, new Function<HotelPrice, MinifyHotelPrice>() {
//                @Override
//                public MinifyHotelPrice apply(HotelPrice hotelPrice) {
//                    MinifyHotelPrice minifyHotelPrice = createMinifyHotelPriceByHotelPrice(hotelPrice);
//                    minifyHotelPrice.priceStartDate = priceStartDate;
//                    minifyHotelPrice.priceEndDate = priceEndDate;
//                    return minifyHotelPrice;
//                }
//            }));
//        }
        return json(JSONObject.fromObject(hotelPriceResponse));
    }

    private MinifyHotelPrice createMinifyHotelPriceByHotelPrice(HotelPrice hotelPrice) {
        MinifyHotelPrice minifyHotelPrice = new MinifyHotelPrice();
        minifyHotelPrice.id = hotelPrice.getId();
        minifyHotelPrice.name = hotelPrice.getRoomName();
        minifyHotelPrice.canCancel = (hotelPrice.getCancelStart() == null && hotelPrice.getCancelEnd() == null) || (hotelPrice.getCancelStart().before(new Date()) && hotelPrice.getCancelEnd().after(new Date()));
        minifyHotelPrice.hasBreakfast = hotelPrice.getBreakfast() == null ? false : hotelPrice.getBreakfast();
        minifyHotelPrice.ratePlanCode = hotelPrice.getRatePlanCode();
        minifyHotelPrice.roomDescription = hotelPrice.getRoomDescription();
        minifyHotelPrice.status = hotelPrice.getStatus();
        minifyHotelPrice.changeRule = hotelPrice.getChangeRule();
        return minifyHotelPrice;
    }

    private Float hotelPriceCalendarPrice(List<HotelPriceCalendar> calendarList) {
        Float price = 0f;
        for (HotelPriceCalendar calendar : calendarList) {
            Float cPrice = calendar.getCprice() == null ? 0f : calendar.getCprice();
            price += calendar.getMember() + cPrice;
        }
        return price / calendarList.size();
    }

    public Result suggest() {
        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        String name = (String) getParameter("name");
        List<Long> cityIds = new ArrayList<>();
        if (cityId != null) {
            cityIds.add(cityId);
        } else {
            return json(JSONArray.fromObject(suggestService.suggestHotel(name, 4)));
        }

        return json(JSONArray.fromObject(suggestService.suggestHotel(cityIds, name, 4)));
    }

    public Result indexHotel() {
        hotelService.indexHotel();
        return text("索引成功");
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public HotelSearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(HotelSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public List<HotelSolrEntity> getRecommendHotelList() {
        return recommendHotelList;
    }

    public void setRecommendHotelList(List<HotelSolrEntity> recommendHotelList) {
        this.recommendHotelList = recommendHotelList;
    }

    @Override
    public HotelSearchRequest getModel() {
        return searchRequest;
    }
}
