package com.data.data.hmly.action.apidata;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.apidata.ApiMonitorService;
import com.data.data.hmly.service.apidata.entity.ApiMonitor;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2017/1/4.
 */
public class ApiMonitorAction extends FrameBaseAction {
    @Resource
    private ApiMonitorService apiMonitorService;

    private int page;
    private int rows;
    private Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 跳转到分页查询
     * @return
     */
    public Result toList() {
        return dispatch();
    }

    /**
     * 分页查询
     * @return
     */
    public Result list() {
        ApiMonitor apiMonitor = new ApiMonitor();
        List<ApiMonitor> list = apiMonitorService.list(apiMonitor, null);

        JsonConfig config = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(list, config);
        return json(json);
    }

    /**
     * 重新测试
     * @return
     */
    public Result reTest() throws Exception {
        String apiMonitorId = (String) getParameter("id");
        map = apiMonitorService.doReTest(apiMonitorId, getLoginUser());
        return jsonResult(map);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
