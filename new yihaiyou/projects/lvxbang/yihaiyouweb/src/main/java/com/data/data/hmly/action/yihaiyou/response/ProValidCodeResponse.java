package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.entity.ProValidCode;

/**
 * Created by huangpeijie on 2016-12-26,0026.
 */
public class ProValidCodeResponse {
    private String code;
    private Integer used;

    public ProValidCodeResponse() {
    }

    public ProValidCodeResponse(ProValidCode proValidCode) {
        this.code = proValidCode.getCode();
        this.used = proValidCode.getUsed();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }
}
