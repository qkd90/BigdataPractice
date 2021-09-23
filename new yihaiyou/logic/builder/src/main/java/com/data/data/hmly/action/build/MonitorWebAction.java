package com.data.data.hmly.action.build;

import com.data.data.hmly.service.build.MonitorService;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import net.sf.json.JSONObject;

import javax.annotation.Resource;

/**
 * @author Jonathan.Guo
 */
public class MonitorWebAction extends JxmallAction {

    @Resource
    private MonitorService monitorService;

    public Result scenic() {
        return json(JSONObject.fromObject(monitorService.monitorScenic()));
    }

    public Result resetScenic() {
        monitorService.resetScenicDetail();
        return text("success");
    }

     public Result destination() {
        return json(JSONObject.fromObject(monitorService.monitorDestination()));
    }

    public Result resetDestination() {
        monitorService.resetDestination();
        return text("success");
    }

}
