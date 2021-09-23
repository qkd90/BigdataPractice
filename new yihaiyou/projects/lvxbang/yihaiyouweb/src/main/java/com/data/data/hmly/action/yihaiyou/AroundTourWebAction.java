package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.AroundTourMobileService;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/7/25.
 */
public class AroundTourWebAction extends BaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    @Resource
    private AroundTourMobileService aroundTourMobileService;

    public Result getAtiData() {
        String json = getJsonDate(JsonDateFileName.AROUND_TOUR_ATI_DATA, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        result = aroundTourMobileService.getAtiData();
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONObject jsonObject = JSONObject.fromObject(result, jsonConfig);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.AROUND_TOUR_ATI_DATA, json);
        return json(jsonObject);
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
