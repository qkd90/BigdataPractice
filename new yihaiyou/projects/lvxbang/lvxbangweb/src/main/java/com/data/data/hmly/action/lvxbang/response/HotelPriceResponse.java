package com.data.data.hmly.action.lvxbang.response;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class HotelPriceResponse {

    private Long hotelId;
    private boolean success;
    private String msg;
    private List<MinifyHotelPrice> hotelPrices;
    private List<MiniLineResponse> lineResponses;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MinifyHotelPrice> getHotelPrices() {
        return hotelPrices;
    }

    public void setHotelPrices(List<MinifyHotelPrice> hotelPrices) {
        this.hotelPrices = hotelPrices;
    }

    public List<MiniLineResponse> getLineResponses() {
        return lineResponses;
    }

    public void setLineResponses(List<MiniLineResponse> lineResponses) {
        this.lineResponses = lineResponses;
    }
}
