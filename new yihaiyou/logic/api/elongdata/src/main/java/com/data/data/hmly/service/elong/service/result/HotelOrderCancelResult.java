package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.CancelOrderResult;

;

public class HotelOrderCancelResult extends BaseResult {
    @JSONField(name = "Result")
    private CancelOrderResult result;

    public CancelOrderResult getResult() {
        return result;
    }

    public void setResult(CancelOrderResult result) {
        this.result = result;
    }
}
