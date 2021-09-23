package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.InternalTourMobileService;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/7/25.
 */
public class InternalTourWebAction extends BaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    @Resource
    private InternalTourMobileService internalTourMobileService;

    public Result getItiData() {
        result = internalTourMobileService.getItiData();
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
