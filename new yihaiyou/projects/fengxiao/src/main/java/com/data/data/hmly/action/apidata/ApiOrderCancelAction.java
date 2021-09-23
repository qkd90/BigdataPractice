package com.data.data.hmly.action.apidata;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.apidata.ApiOrderCancelService;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Map;

/**
 */
public class ApiOrderCancelAction extends FrameBaseAction {
    @Resource
    private ApiOrderCancelService apiOrderCancelService;

    /**
     * 携程门票退单申请（已付款），
     * 提交退单申请，状态先为退订中
     * 后续定时查询退单中订单状态，可退设为已退款，不可退设为已关闭
     * @return
     */
    public Result cancelForCtrip() throws Exception {
        String detailId = (String) getParameter("orderDetailId");
        Map<String, Object> result = apiOrderCancelService.doCancelForCtrip(Long.valueOf(detailId));
        return json(JSONObject.fromObject(result));
    }

    /**
     * 艺龙酒店退单（已付款）
     * @return
     */
    public Result cancelForElong() throws Exception {
        String detailId = (String) getParameter("orderDetailId");
        Map<String, Object> result = apiOrderCancelService.doCancelForElong(Long.valueOf(detailId));
        return json(JSONObject.fromObject(result));
    }

}
