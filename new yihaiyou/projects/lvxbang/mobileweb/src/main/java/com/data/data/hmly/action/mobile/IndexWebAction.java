package com.data.data.hmly.action.mobile;

import com.data.data.hmly.service.IndexMobileService;
import com.data.data.hmly.service.line.request.LineSearchRequest;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexWebAction extends MobileBaseAction {

    @Resource
    private IndexMobileService indexMobileService;

    private Map<String, Object> result = new HashMap<String, Object>();

    private int areaLineListPageSize = 0;
    private int areaLineListPageSizeNo = 0;

    private LineSearchRequest lineSearchRequest = new LineSearchRequest();



    public Result index() {
        return redirect("/index.html");
    }

    public Result computer() {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String pcUrl = propertiesManager.getString("LVXBANG_WEB_URL");
        return redirect(pcUrl);
    }

    /**
     * 请求范例：/mobile/index/lxbIndex.jhtml?route=/distributeshare/100803
     * 重定向后：/lxb.html#/distributeshare/100803
     */
    public Result lxbIndex() {
        String route = (String) getParameter("route");
        String url = "/lxb.html";
        if (StringUtils.isNotBlank(route)) {
            url = url + "#" + route;
        }
        return redirect(url);
    }

    public Result indexIndex() {
        String route = (String) getParameter("route");
        String url = "/index.html";
        if (StringUtils.isNotBlank(route)) {
            url = url + "#" + route;
        }
        return redirect(url);
    }

    public Result getIndexData() {
        String json = getJsonDate(JsonDateFileName.INDEX_INDEX_DATA, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        result = indexMobileService.getIndexData();
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONObject jsonObject = JSONObject.fromObject(result, jsonConfig);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.INDEX_INDEX_DATA, json);
        return json(jsonObject);
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
}
