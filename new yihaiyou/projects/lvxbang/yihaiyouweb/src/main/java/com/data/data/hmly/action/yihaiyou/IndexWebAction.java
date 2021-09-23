package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.CruiseShipResponse;
import com.data.data.hmly.service.IndexMobileService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.request.CruiseShipSearchRequest;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.line.request.LineSearchRequest;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.request.TicketSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.*;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.solr.client.solrj.SolrQuery;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

public class IndexWebAction extends BaseAction {

    @Resource
    private IndexMobileService indexMobileService;
    @Resource
    private CruiseShipService cruiseShipService;

    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;

    @Resource
    private AdvertisingService advertisingService;

    private Map<String, Object> result = new HashMap<String, Object>();
    private final ObjectMapper mapper = new ObjectMapper();
    private int areaLineListPageSize = 0;
    private int areaLineListPageSizeNo = 0;
    private int page = 1;
    private int pageSize = 10;
    private String json;

    private LineSearchRequest lineSearchRequest = new LineSearchRequest();

    public Result qiniu() {
        return text(QiniuUtil.URL);
    }

    public Result index() {
        return redirect("/index.html");
    }

    public Result computer() {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String pcUrl = propertiesManager.getString("LVXBANG_WEB_URL");
        return redirect(pcUrl);
    }

    public Result yihyIndex() {
        String route = (String) getParameter("route");
        String url = "/";
        if (StringUtils.isNotBlank(route)) {
            url = url + "#" + route;
        }
        return redirect(url);
    }

    public Result getHomeTopBannerAds() {
        List<Ads> topBannerAds = advertisingService.getMobileHomeBanner();
        result.put("ads", topBannerAds);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public Result getIndexData() throws IOException {
        CruiseShipSearchRequest request = new CruiseShipSearchRequest();
        List<String> dateStrList = new ArrayList<String>();
        String beginDate = DateUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateStrList.add(beginDate);
//        String endDate = DateUtils.format(DateUtils.getEndDay(new Date(), 100), "yyyy-MM-dd'T'HH:mm:ss'Z'");
//        dateStrList.add(endDate);
        request.setDateRange(dateStrList);
        request.setOrderColumn("start");
        request.setOrderType(SolrQuery.ORDER.asc);
        List<CruiseShipSolrEntity> cruiseShipSolrEntityList = cruiseShipService.listFromSolr(request, new Page(1, 2));
        List<CruiseShipResponse> cruiseShipResponses = Lists.transform(cruiseShipSolrEntityList, new Function<CruiseShipSolrEntity, CruiseShipResponse>() {
            @Override
            public CruiseShipResponse apply(CruiseShipSolrEntity input) {
                return new CruiseShipResponse(input);
            }
        });
        result.put("cruiseShipList", cruiseShipResponses);

        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest();
        List<String> ticketTypes = Lists.newArrayList();
        ticketTypes.add("sailboat");
        ticketTypes.add("yacht");
        ticketTypes.add("huanguyou");
        ticketSearchRequest.setTicketTypes(ticketTypes);
        List<TicketSolrEntity> ticketSolrEntities = ticketService.listFromSolr(ticketSearchRequest, new Page(1, 6));

        if (ticketSolrEntities != null && !ticketSolrEntities.isEmpty()) {

            List<TicketSolrEntity> tempTicketList = Lists.newArrayList();
            for (TicketSolrEntity ticketSolrEntity : ticketSolrEntities) {

//                TicketMinData minDatePrice = ticketPriceService.findTicketMinPrice(ticketSolrEntity.getId(), new Date(), null, null);
//                Float minPriPrice = minDatePrice.getMinPriPrice();
//                Float minRebate = minDatePrice.getMinRebate();
//                if (minPriPrice == null) {
//                    minPriPrice = 0f;
//                }
//                if (minRebate == null) {
//                    minRebate = 0f;
//                }
                ticketSolrEntity.setDisCountPrice(ticketDatepriceService.findMinPriceByTicketId(ticketSolrEntity.getId(), new Date(), null, "priPrice"));
                tempTicketList.add(ticketSolrEntity);
            }

            result.put("ticketList", tempTicketList);
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result getAreaLineList() {
        if (areaLineListPageSize == 0 || areaLineListPageSizeNo == 0) {
            result.put("hasMore", false);
            result.put("msg", "没有更多数据了!");
            return json(JSONObject.fromObject(result));
        }
        Page page = new Page(areaLineListPageSizeNo, areaLineListPageSize);
        List<LineSolrEntity> lineList = indexMobileService.getIndexAreaLineData(lineSearchRequest, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("hasMore", false);
        } else {
            result.put("hasMore", true);
        }
        result.put("lineList", lineList);
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public int getAreaLineListPageSize() {
        return areaLineListPageSize;
    }

    public void setAreaLineListPageSize(int areaLineListPageSize) {
        this.areaLineListPageSize = areaLineListPageSize;
    }

    public int getAreaLineListPageSizeNo() {
        return areaLineListPageSizeNo;
    }

    public void setAreaLineListPageSizeNo(int areaLineListPageSizeNo) {
        this.areaLineListPageSizeNo = areaLineListPageSizeNo;
    }

    public LineSearchRequest getLineSearchRequest() {
        return lineSearchRequest;
    }

    public void setLineSearchRequest(LineSearchRequest lineSearchRequest) {
        this.lineSearchRequest = lineSearchRequest;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
