package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class HotelResponse extends BaseHotelResponse {
    private Integer star;
    private Double longitude;
    private Double latitude;
    private Long cityId;
    private List<HotelPriceResponse> prices = Lists.newArrayList();

    public HotelResponse() {
    }

    public HotelResponse(Hotel hotel) {
        super(hotel);
        this.star = hotel.getStar();
        this.cityId = hotel.getCityId();
        if (hotel.getExtend() != null) {
            this.longitude = hotel.getExtend().getLongitude();
            this.latitude = hotel.getExtend().getLatitude();
        }
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public List<HotelPriceResponse> getPrices() {
        return prices;
    }

    public void setPrices(List<HotelPriceResponse> prices) {
        this.prices = prices;
    }
}
