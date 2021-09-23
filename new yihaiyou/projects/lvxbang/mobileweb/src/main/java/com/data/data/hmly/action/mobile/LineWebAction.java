package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.mobile.response.CityResponse;
import com.data.data.hmly.action.mobile.response.LineDayResponse;
import com.data.data.hmly.action.mobile.response.LineExplainResponse;
import com.data.data.hmly.action.mobile.response.LineResponse;
import com.data.data.hmly.service.LineMobileService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Lineexplain;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.request.LineSearchRequest;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.apache.solr.client.solrj.SolrQuery;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/7/19.
 */
public class LineWebAction extends FrameBaseAction {

    @Resource
    private LineService lineService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private LineMobileService lineMobileService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private LineexplainService lineexplainService;

    @Resource
    private ScenicInfoService scenicInfoService;

    public LineSearchRequest lineSearchRequest = new LineSearchRequest();
    private Map<String, Object> map = new HashMap<String, Object>();

    public Long lineId;
    public Long lineTypePriceId;
    private Line line = new Line();
    private Integer page = 1;
    private Integer rows = 10;
    private TbArea area = new TbArea();



    @AjaxCheck
    public Result searchLine() {
        Page pageInfo = new Page(1, 5);
        String keyword = (String) getParameter("keyword");
        List<LineSolrEntity> lineList = new ArrayList<LineSolrEntity>();
        ScenicSearchRequest request = new ScenicSearchRequest();
        List<ScenicSolrEntity> scenicList = new ArrayList<ScenicSolrEntity>();
        if (StringUtils.isNotBlank(keyword)) {
            lineSearchRequest.setName(keyword);
            lineList = lineService.listFromSolr(lineSearchRequest, pageInfo);

            request.setName(keyword);
            scenicList = scenicInfoService.listFromSolr(request, pageInfo);

            if (!lineList.isEmpty()) {
                map.put("lineList", lineList);
                map.put("lineIsShow", true);
            } else {
                map.put("lineIsShow", false);
            }
            if (!scenicList.isEmpty()) {
                map.put("scenicList", scenicList);
                map.put("scenicIsShow", true);
            } else {
                map.put("scenicIsShow", false);
            }
            simpleResult(map, true, "");

        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }


    @AjaxCheck
    public Result lineDetail() {
        LineResponse response = lineMobileService.lineDetail(lineId);
        result.put("line", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result lineDay() {
        List<LineDayResponse> lineDayList = lineMobileService.lineDayList(lineId);
        result.put("lineDayList", lineDayList);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    public Result lineTypePrice() {
        Linetypeprice linetypeprice = linetypepriceService.getLinetypeprice(lineTypePriceId);
        if (linetypeprice != null) {
            result.put("quoteContainDesc", linetypeprice.getQuoteContainDesc());
            result.put("quoteNoContainDesc", linetypeprice.getQuoteNoContainDesc());
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result lineExplain() {
        Lineexplain lineexplain = lineexplainService.getByLine(lineId);
        if (lineexplain != null) {
            LineExplainResponse response = new LineExplainResponse(lineexplain);
            result.put("lineExplain", response);
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return json(JSONObject.fromObject(result));
    }

    public Result getLineList() {

        SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");
        boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
        ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);

        Page pageInfo = new Page(page, rows);

        String productAttr = (String) getParameter("productAttr");
        String cityIds = (String) getParameter("cityIds");
        String days = (String) getParameter("days");
        String features = (String) getParameter("features");
        String sort = (String) getParameter("sort");
        String name = (String) getParameter("name");

        if (StringUtils.isNotBlank(name)) {
            lineSearchRequest.setName(name);
        }

        List<Long> destinationList = new ArrayList<Long>();
        if (StringUtils.isNotBlank(cityIds)) {
            for (String idStr : cityIds.split(",")) {
                destinationList.add(Long.parseLong(idStr));
            }
        }
        List<Integer> dayList = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(days)) {
            for (String dayStr : days.split(",")) {
                if (!"0".equals(dayStr)) {
                    dayList.add(Integer.parseInt(dayStr));
                }
            }
        }

        List<String> featuresList = new ArrayList<String>();
        if (StringUtils.isNotBlank(features)) {
            for (String fea : features.split(",")) {
                featuresList.add(fea);
            }
        }

        if (StringUtils.isNotBlank(sort)) {
            if ("satisfaction".equals(sort)) {
                lineSearchRequest.setOrderColumn("satisfaction");
                lineSearchRequest.setOrderType(SolrQuery.ORDER.desc);
            }
            if ("saleHight".equals(sort)) {
                lineSearchRequest.setOrderColumn("orderNum");
                lineSearchRequest.setOrderType(SolrQuery.ORDER.desc);
            }

            if ("saleLow".equals(sort)) {
                lineSearchRequest.setOrderColumn("orderNum");
                lineSearchRequest.setOrderType(SolrQuery.ORDER.asc);
            }
            if ("priceHight".equals(sort)) {
                lineSearchRequest.setOrderColumn("price");
                lineSearchRequest.setOrderType(SolrQuery.ORDER.desc);
            }

            if ("priceLow".equals(sort)) {
                lineSearchRequest.setOrderColumn("price");
                lineSearchRequest.setOrderType(SolrQuery.ORDER.asc);
            }

        } else {
            lineSearchRequest.setOrderColumn("satisfaction");
            lineSearchRequest.setOrderType(SolrQuery.ORDER.desc);
        }

        lineSearchRequest.setProductAttr(productAttr);
        lineSearchRequest.setDestinationIdList(destinationList);
        lineSearchRequest.setFeatureList(featuresList);
        lineSearchRequest.setDays(dayList);
        List<LineSolrEntity> list = new ArrayList<LineSolrEntity>();
        list = lineService.listFromSolr(lineSearchRequest, pageInfo);
        if (pageInfo.getPageIndex() >= pageInfo.getPageCount()) {
            map.put("nomore", true);
        } else {
            map.put("nomore", false);
        }
        map.put("lineList", list);
        map.put("success", true);
        return json(JSONObject.fromObject(map));
    }

    public Result getAreaList() {
        String levelStr = (String) getParameter("level");
        String fatherIdStr = (String) getParameter("fatherId");
        area.setId(Long.parseLong(fatherIdStr));
        area.setLevel(Integer.parseInt(levelStr));
        List<TbArea> areaList = new ArrayList<TbArea>();
        areaList = tbAreaService.findAreaListByFather(area);
        return datagrid(areaList, JsonFilter.getIncludeConfig(""));
    }

    public Result hotAreaList() {

        Page pageInfo = new Page(1, 15);

        Label label = new Label();
        label.setName("首页-国内游目的地");

        List<TbArea> list = tbAreaService.findAreasByLabelName(label, pageInfo);
        List<CityResponse> cityResponses = new ArrayList<CityResponse>();
        for (TbArea a : list) {
            CityResponse cityResponse = new CityResponse();
            cityResponse.setId(a.getId());
            cityResponse.setName(a.getName());
            cityResponses.add(cityResponse);
        }

        if (cityResponses.isEmpty()) {
            simpleResult(map, false, "");
        } else {
            map.put("hotAreaList", cityResponses);
            simpleResult(map, true, "");
        }
        return jsonResult(map);

    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public TbArea getArea() {
        return area;
    }

    public void setArea(TbArea area) {
        this.area = area;
    }
}
