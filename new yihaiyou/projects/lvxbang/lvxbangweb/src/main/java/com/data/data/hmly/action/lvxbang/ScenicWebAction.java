package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.lvxbang.response.MiniLineResponse;
import com.data.data.hmly.action.lvxbang.response.MiniScenicResponse;
import com.data.data.hmly.action.lvxbang.response.TicketPriceResponse;
import com.data.data.hmly.action.lvxbang.response.TicketResponse;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.ctriphotel.base.StringUtil;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.lvxbang.request.TripNode;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.request.TicketSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ScenicWebAction extends LxbAction {

    @Resource
    private ScenicInfoService scenicInfoService;
//    @Resource
//    private LabelService labelService;
    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private CommentService commentService;
    @Resource
    private TicketService ticketService;
    @Resource
    private AreaService areaService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;

    private ScenicSearchRequest scenicRequest = new ScenicSearchRequest();
    private int pageIndex = 0;
    private int pageSize = 10;
    private ScenicInfo scenicInfo;
    private Long id;
    public Long scenicId;
    public Long planId;
    public String scenicName;
    public String theme;
    public Long cityCode;
    public Long father;
    public String fatherName;
    public List<String> cities;
    public List<TbArea> cityList;
    public String cityIdStr;
    public ScenicGeoinfo scenicGeoinfo;
    public String json;
    public Comment comment = new Comment();

    public Result index() {
        setAttribute(LXBConstants.LVXBANG_SCENIC_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_SCENIC_BANNER));
        setAttribute(LXBConstants.LVXBANG_SCENIC_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_SCENIC_INDEX));

        return dispatch();
    }

    public Result detail() {
        Integer status = scenicInfoService.getStatus(scenicId);
        if (status != 1) {
            return dispatch404();
        } else {
            setAttribute(LXBConstants.LVXBANG_SCENIC_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_SCENIC_DETAIL + scenicId));
            setAttribute(LXBConstants.LVXBANG_SCENIC_HEAD_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_SCENIC_HEAD + scenicId));
            return dispatch();
        }
    }

    public Result getTicketList() {
        List<TicketPrice> ticketPriceTypeList = ticketPriceService.findTicketPriceTypeByScenicId(scenicInfo.getId());
        Ticket ticket = new Ticket();
        ticket.setScenicInfo(scenicInfo);
        ticket.setStatus(ProductStatus.UP);
        TicketPrice ticketPrice = new TicketPrice();
        ticketPrice.setTicket(ticket);
        List<TicketPrice> ticketPriceAllList = ticketPriceService.findTickettypepriceList(ticketPrice);
        List<TicketResponse> ticketResponseList = Lists.newArrayList();
        Float minPrice = Float.MAX_VALUE;
        Set<Long> ticketIds = Sets.newHashSet();
        for (TicketPrice type : ticketPriceTypeList) {
            List<TicketPriceResponse> list = Lists.newArrayList();
            for (TicketPrice price : ticketPriceAllList) {
                if (price.getType().equals(type.getType())) {
                    List<TicketDateprice> datepriceList = ticketDatepriceService.findTypePriceDate(price.getId(), new Date(), null, 1);
                    if (datepriceList != null && !datepriceList.isEmpty()) {
                        TicketPriceResponse response = new TicketPriceResponse(datepriceList.get(0));
                        list.add(response);
                        ticketIds.add(price.getTicket().getId());
                        minPrice = minPrice > response.getDiscountPrice() ? response.getDiscountPrice() : minPrice;
                    }
                }
            }
            if (!list.isEmpty()) {
                TicketResponse response = new TicketResponse();
                response.setType(type.getFormatType());
                response.setPriceList(list);
                ticketResponseList.add(response);
            }
        }
        scenicInfo = scenicInfoService.get(scenicInfo.getId());
        minPrice = minPrice < Float.MAX_VALUE ? minPrice : 0f;
        if (!minPrice.equals(scenicInfo.getPrice())) {
            scenicInfo.setPrice(minPrice);
            scenicInfoService.update(scenicInfo);
            scenicInfoService.indexScenicInfo(scenicInfo);
        }
        Set<Line> lines = Sets.newHashSet();
        for (Long ticketId : ticketIds) {
            List<Line> lineList = linedaysProductPriceService.listLine(ticketId, ProductType.scenic, null);
            lines.addAll(lineList);
        }
        List<MiniLineResponse> lineResponses = Lists.newArrayList();
        for (Line line : lines) {
            if (line.getPrice() > 0) {
                lineResponses.add(new MiniLineResponse(line));
            }
        }

        result.put("ticketList", ticketResponseList);
        result.put("lineList", lineResponses);
        return json(JSONObject.fromObject(result));
    }

    public Result getTicketListFromSolr() {
        TicketSearchRequest request = new TicketSearchRequest();
        request.setScenicId(scenicId);
        Page page = new Page(1, pageSize);
        List<TicketSolrEntity> ticketSolrEntityList = ticketService.listFromSolr(request, page);
        return json(JSONArray.fromObject(ticketSolrEntityList));
    }

    public Result list() {
        String citiesStr = (String) getParameter("citiesStr");
        if (cityCode != null && cityCode > 0) {
            cityList = Lists.newArrayList();
            cityList.add(areaService.get(cityCode));
        } else if (StringUtil.isNotBlank(cityIdStr)) {
            String[] cityIdStrArray = cityIdStr.split(",");
            List<Long> cityIdList = Lists.newArrayList(Lists.transform(Lists.newArrayList(cityIdStrArray), new Function<String, Long>() {
                @Override
                public Long apply(String s) {
                    return Long.valueOf(s);
                }
            }));
            cityList = areaService.getByIds(cityIdList);
        } else if (StringUtil.isNotBlank(citiesStr)) {
            citiesStr = citiesStr.replaceAll("；", ";");
            cities = Lists.newArrayList(citiesStr.split(";"));
            cityList = areaService.getAreaListByName(cities);
        }
        return dispatch();
    }


    public Result getScenicList() {
        Page page = new Page(pageIndex, pageSize);
        List<ScenicSolrEntity> scenicSolrEntities = scenicInfoService.listFromSolr(scenicRequest, page);
        RecommendPlanSearchRequest request = new RecommendPlanSearchRequest();
        for (ScenicSolrEntity entity : scenicSolrEntities) {
            request.setScenicId(entity.getId());
            request.setOrderColumn("viewNum");
            request.setOrderType(SolrQuery.ORDER.desc);
            List<RecommendPlanSolrEntity> planList = recommendPlanService.listFromSolr(request, new Page(0, 1));
            if (planList != null && !planList.isEmpty()) {
                entity.setRecommendPlanId(planList.get(0).getId());
                entity.setRecommendPlanName(planList.get(0).getName());
            }
        }
        return json(JSONArray.fromObject(scenicSolrEntities));
    }

    public Result searchScenicList() throws UnsupportedEncodingException {
        int page = 10;
        String name = (String) getParameter("name");
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(suggestService.suggestScenic(name, page), jsonConfig);
        return json(json);
    }

    public Result commentList() {
        List<Comment> list = scenicInfoService.get(scenicId).getScenicCommentList();
        return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig("comments", "user")));
    }

    public Result saveComment() {
        Member user = getLoginUser();
        comment.setUser(user);
        comment.setType(ProductType.scenic);
        commentService.saveComment(comment);
        //scenicInfoService.saveScenicComment(comment);
        return json(JSONArray.fromObject("success"));
    }

    public Result scenicInfo() {
        scenicInfo = scenicInfoService.get(id);
        return dispatch();
    }

    public Result scenicGeoinfo() {
        return json(JSONArray.fromObject(scenicInfoService.get(id).getScenicGeoinfo()));
    }

    public Result getScenicTheme() {
        List<String> scenicTheme = scenicInfoService.getScenicTheme();
        return json(JSONArray.fromObject(scenicTheme));
    }

    public Result getAdsList() {
        return json(JSONArray.fromObject(advertisingService.getScenicBanner()));
    }

    public Result getTotalPage() {
        Long result = scenicInfoService.countFromSolr(scenicRequest);
        return json(JSONArray.fromObject(result));
    }

    public Result suggest() throws UnsupportedEncodingException {
        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        String name = (String) getParameter("name");
        List<SuggestionEntity> suggestion = new ArrayList<>();
        if (scenicRequest != null) {
            if (scenicRequest.getCityIds().isEmpty()) {
               suggestion = suggestService.suggestScenic(name, 10);
            } else {
                suggestion = suggestService.suggestScenic(scenicRequest.getCityIds(), name, 10);
            }
        } else {
            suggestion = suggestService.suggestScenic(name, 10);
        }
        return json(JSONArray.fromObject(suggestion));
    }

    public Result getScenicBriefData() throws IOException {
        List<Number> ids = new ObjectMapper().readValue(json, List.class);

        if (ids.isEmpty()) {
            return json(JSONObject.fromObject(Maps.newHashMap()));
        }
        List<Long> scenicIds = Lists.newArrayList();
        for (Number number : ids) {
            scenicIds.add(number.longValue());
        }
        List<ScenicInfo> scenicInfoList = scenicInfoService.getScenicByIds(scenicIds);
        List<MiniScenicResponse> briefScenicList = Lists.transform(scenicInfoList, new Function<ScenicInfo, MiniScenicResponse>() {
            @Override
            public MiniScenicResponse apply(ScenicInfo scenicInfo) {
                return new MiniScenicResponse(scenicInfo);
            }
        });
        Map<String, MiniScenicResponse> result = Maps.uniqueIndex(briefScenicList, new Function<MiniScenicResponse, String>() {
            @Override
            public String apply(MiniScenicResponse miniScenicResponse) {
                return miniScenicResponse.id.toString();
            }
        });
        return json(JSONObject.fromObject(result));
    }

    public Result getNearScenic() {
        String fromId = (String) getParameter("fromId");
        String toIds = (String) getParameter("toIds");
        List<String> list = Arrays.asList(toIds.split(","));
        List<Long> toId = Lists.newArrayList(Lists.transform(list, new Function<String, Long>() {

            @Override
            public Long apply(String input) {
                return Long.valueOf(input);
            }
        }));
        ScenicInfo from = scenicInfoService.get(Long.valueOf(fromId));
        List<ScenicInfo> to = scenicInfoService.getScenicByIds(toId);
        ScenicInfo nearest = scenicInfoService.findNeaerScenic(from, to);
        List<ScenicInfo> scenicList = Lists.newArrayList(nearest, from);
        List<TripNode> result = Lists.newArrayList(Lists.transform(scenicList, new Function<ScenicInfo, TripNode>() {
            @Override
            public TripNode apply(ScenicInfo input) {
                TripNode node = new TripNode(input);
                node.type = 1;
                return node;
            }
        }));
        return json(JSONArray.fromObject(result));
    }

//     public void  testScenic() {
//         ScenicInfo scenicInfo = scenicInfoService.get(new Long(3540));
//         System.out.println("scenicInfo: " + scenicInfo.getPrice());
//         ScenicSolrEntity so = new ScenicSolrEntity(scenicInfo);
//         System.out.println("so: " + so.getPrice());
//     }
//
    public ScenicSearchRequest getScenicRequest() {
        return scenicRequest;
    }

    public void setScenicRequest(ScenicSearchRequest scenicRequest) {
        this.scenicRequest = scenicRequest;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public ScenicInfo getScenicInfo() {
        return scenicInfo;
    }

    public void setScenicInfo(ScenicInfo scenicInfo) {
        this.scenicInfo = scenicInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScenicGeoinfo getScenicGeoinfo() {
        return scenicGeoinfo;
    }

    public void setScenicGeoinfo(ScenicGeoinfo scenicGeoinfo) {
        this.scenicGeoinfo = scenicGeoinfo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
