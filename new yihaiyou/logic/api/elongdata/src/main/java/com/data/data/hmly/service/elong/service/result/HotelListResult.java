package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.HotelList;

public class HotelListResult extends BaseResult {


    @JSONField(name = "Result")
    private HotelList result;

    public HotelList getResult() {
        return result;
    }

    public void setResult(HotelList result) {
        this.result = result;
    }


}
