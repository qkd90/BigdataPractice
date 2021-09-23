package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.OrderDetailResult;

public class HotelOrderDetailResult extends BaseResult {
    @JSONField(name = "Result")
    OrderDetailResult result;

    public OrderDetailResult getResult() {
        return result;
    }

    public void setResult(OrderDetailResult result) {
        this.result = result;
    }
}
