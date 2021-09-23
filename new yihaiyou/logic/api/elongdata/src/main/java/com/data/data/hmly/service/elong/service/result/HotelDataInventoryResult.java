package com.data.data.hmly.service.elong.service.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.data.data.hmly.service.elong.pojo.InventoryResult;

public class HotelDataInventoryResult extends BaseResult {


    @JSONField(name = "Result")
    private InventoryResult result;

    public InventoryResult getResult() {
        return result;
    }

    public void setResult(InventoryResult result) {
        this.result = result;
    }
}
