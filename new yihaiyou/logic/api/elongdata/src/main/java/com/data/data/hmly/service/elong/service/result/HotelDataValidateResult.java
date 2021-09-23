package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.ValidateResult;

;

public class HotelDataValidateResult extends BaseResult {


    @JSONField(name = "Result")
    private ValidateResult result;

    public ValidateResult getResult() {
        return result;
    }

    public void setResult(ValidateResult result) {
        this.result = result;
    }
}