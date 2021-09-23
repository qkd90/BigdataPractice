package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;


public class BaseResult {

    @JSONField(name = "Code")
    private String Code;


    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

}
