package com.data.data.hmly.service.elong.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.service.result.BaseResult;

public class CreditCardValidateResult extends BaseResult {


    @JSONField(name = "Result")
    private ValidateCreditCardResult Result;

    public ValidateCreditCardResult getResult() {
        return Result;
    }

    public void setResult(ValidateCreditCardResult result) {
        this.Result = result;
    }
}
