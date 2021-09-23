package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;

import java.util.List;

/**
 * Created by huangpeijie on 2016-11-24,0024.
 */
public class FerryOrderResponse extends OrderSimpleResponse {
    private String ferryNumber;
    private List<SimpleTourist> touristList;

    public FerryOrderResponse() {
    }

    public FerryOrderResponse(FerryOrder ferryOrder) {
        super(ferryOrder);
        this.ferryNumber = ferryOrder.getFerryNumber();
    }

    public String getFerryNumber() {
        return ferryNumber;
    }

    public void setFerryNumber(String ferryNumber) {
        this.ferryNumber = ferryNumber;
    }

    public List<SimpleTourist> getTouristList() {
        return touristList;
    }

    public void setTouristList(List<SimpleTourist> touristList) {
        this.touristList = touristList;
    }
}
