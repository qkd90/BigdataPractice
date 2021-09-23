package com.data.data.hmly.action.yhypc.response;

import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-10-08,0008.
 */
public class PlanOrderHotelResponse extends BaseHotelResponse {
    private String startDate;
    private String endDate;
    private List<HotelPriceResponse> hotelPrices = Lists.newArrayList();
    private HotelPriceResponse selectedHotelPrice;
    private List<SimpleTourist> tourists;
    private Float price = 0f;
    private List<ProValidCodeResponse> codes = Lists.newArrayList();

    public PlanOrderHotelResponse() {
    }

    public PlanOrderHotelResponse(HotelPrice hotelPrice) {
        this.id = hotelPrice.getHotel().getId();
        this.name = hotelPrice.getHotel().getName();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<HotelPriceResponse> getHotelPrices() {
        return hotelPrices;
    }

    public void setHotelPrices(List<HotelPriceResponse> hotelPrices) {
        this.hotelPrices = hotelPrices;
    }

    public HotelPriceResponse getSelectedHotelPrice() {
        return selectedHotelPrice;
    }

    public void setSelectedHotelPrice(HotelPriceResponse selectedHotelPrice) {
        this.selectedHotelPrice = selectedHotelPrice;
    }

    public List<SimpleTourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<SimpleTourist> tourists) {
        this.tourists = tourists;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<ProValidCodeResponse> getCodes() {
        return codes;
    }

    public void setCodes(List<ProValidCodeResponse> codes) {
        this.codes = codes;
    }
}
