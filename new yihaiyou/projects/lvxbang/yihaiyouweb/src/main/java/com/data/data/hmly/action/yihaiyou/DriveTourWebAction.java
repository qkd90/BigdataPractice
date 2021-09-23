package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.DriveTourMobileService;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/7/21.
 */
public class DriveTourWebAction extends BaseAction {

    @Resource
    private DriveTourMobileService driveTourMobileService;

    private Map<String, Object> result = new HashMap<String, Object>();

    public Result getDtiData() {
        String json = getJsonDate(JsonDateFileName.DRIVE_TOUR_DTI_DATA, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        result = driveTourMobileService.getDtiData();
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONObject jsonObject = JSONObject.fromObject(result, jsonConfig);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.DRIVE_TOUR_DTI_DATA, json);
        return json(jsonObject);
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
