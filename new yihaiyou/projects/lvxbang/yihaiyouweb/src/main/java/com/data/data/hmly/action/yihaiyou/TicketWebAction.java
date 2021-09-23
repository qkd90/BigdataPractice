package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.TicketMobileService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/8/2.
 */
public class TicketWebAction extends BaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();
    private ScenicSearchRequest scenicSearchRequest = new ScenicSearchRequest();

    @Resource
    private TicketMobileService ticketMobileService;


    public Result getThemeList() {
        Label condition = new Label();
        condition.setName("公共标签_景点主题");
        List<Label> themeLabels = ticketMobileService.getScenicList(condition);
        if (themeLabels.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        result.put("theme", themeLabels);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getTkeiData() {
        scenicSearchRequest.setScenicInfoType(ScenicInfoType.scenic);
        result = ticketMobileService.getTkeiData(scenicSearchRequest);
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getTkeiNear() {
        String json = getJsonDate(JsonDateFileName.TICKET_TKEI_NEAR, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        result = ticketMobileService.getTkeiNear();
        if (result.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONObject jsonObject = JSONObject.fromObject(result, jsonConfig);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.TICKET_TKEI_NEAR, json);
        return json(jsonObject);
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public ScenicSearchRequest getScenicSearchRequest() {
        return scenicSearchRequest;
    }

    public void setScenicSearchRequest(ScenicSearchRequest scenicSearchRequest) {
        this.scenicSearchRequest = scenicSearchRequest;
    }
}
