package com.data.data.hmly.action.yihaiyou.request;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
public class PlanTrafficAndHotelRequest {
    private Long cityId;
    private PlanTrafficRequest traffic;
    private PlanTrafficRequest returnTraffic;
    private PlanHotelSaveRequest hotel;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public PlanTrafficRequest getTraffic() {
        return traffic;
    }

    public void setTraffic(PlanTrafficRequest traffic) {
        this.traffic = traffic;
    }

    public PlanTrafficRequest getReturnTraffic() {
        return returnTraffic;
    }

    public void setReturnTraffic(PlanTrafficRequest returnTraffic) {
        this.returnTraffic = returnTraffic;
    }

    public PlanHotelSaveRequest getHotel() {
        return hotel;
    }

    public void setHotel(PlanHotelSaveRequest hotel) {
        this.hotel = hotel;
    }
}
