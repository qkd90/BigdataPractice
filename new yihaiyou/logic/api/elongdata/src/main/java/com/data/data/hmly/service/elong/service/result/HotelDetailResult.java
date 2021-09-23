package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.Detail;

public class HotelDetailResult extends BaseResult {


    @JSONField(name = "Result")
    private Detail result;

    public Detail getResult() {
        return result;
    }

    public void setResult(Detail result) {
        this.result = result;
    }


}
