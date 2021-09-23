package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.CustomTourMobileService;
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
public class CustomTourWebAction extends BaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    @Resource
    private CustomTourMobileService customTourMobileService;

    public Result getCtiData() {
        String json = getJsonDate(JsonDateFileName.CUSTOM_TOUR_CTI_DATA, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        result = customTourMobileService.getCtiData();
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONObject jsonObject = JSONObject.fromObject(result, jsonConfig);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.CUSTOM_TOUR_CTI_DATA, json);
        return json(jsonObject);
    }
}
