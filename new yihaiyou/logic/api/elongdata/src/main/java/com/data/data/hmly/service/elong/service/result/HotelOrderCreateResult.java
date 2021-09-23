package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.CreateOrderResult;

public class HotelOrderCreateResult extends BaseResult {
    @JSONField(name = "Result")
    private CreateOrderResult result;

    public CreateOrderResult getResult() {
        return result;
    }

    public void setResult(CreateOrderResult result) {
        this.result = result;
    }


}
