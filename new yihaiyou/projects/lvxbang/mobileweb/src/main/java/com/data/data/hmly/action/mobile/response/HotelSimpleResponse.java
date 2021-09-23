package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.vo.HotelRoomSolrEntity;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class HotelSimpleResponse extends BaseHotelResponse {
    private Long priceId;
    private String priceName;
    private Float price;
    private Long cityId;
    private String startDate;
    private String endDate;
    private String payType;

    public HotelSimpleResponse() {
    }

    public HotelSimpleResponse(HotelSolrEntity hotelSolrEntity) {
        this.id = hotelSolrEntity.getId();
        this.name = hotelSolrEntity.getName();
        this.cover = cover(hotelSolrEntity.getCover());
        this.score = hotelSolrEntity.getScore().floatValue() / 20f;
        this.address = hotelSolrEntity.getAddress();
        this.cityId = hotelSolrEntity.getCityId();
    }

    public HotelSimpleResponse(com.data.data.hmly.service.lvxbang.response.HotelResponse hotelResponse) {
        this.id = hotelResponse.getId();
        this.name = hotelResponse.getName();
        this.priceId = hotelResponse.getPriceId();
        this.priceName = hotelResponse.getRoomName();
        this.price = hotelResponse.getPrice();
        this.startDate = DateUtils.formatShortDate(hotelResponse.getStartDate());
        this.endDate = DateUtils.formatShortDate(hotelResponse.getLeaveDate());
        this.payType = hotelResponse.getPayType();
    }

    public HotelSimpleResponse(HotelPrice hotelPrice) {
        this.id = hotelPrice.getHotel().getId();
        this.name = hotelPrice.getHotel().getName();
        this.priceId = hotelPrice.getPriceId();
        this.priceName = hotelPrice.getName();
        this.price = hotelPrice.getPrice();
        if (PriceStatus.UP.equals(hotelPrice.getStatus())) {
            this.payType = "到付";
        } else {
            this.payType = "担保";
        }
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + cover;
            }
        }
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public HotelSimpleResponse completeWithHotelPrice(HotelPrice hotelPrice) {
        this.id = hotelPrice.getHotel().getId();
        this.name = hotelPrice.getHotel().getName();
        this.cover = hotelPrice.getHotel().getCover();
        this.score = hotelPrice.getHotel().getScore() / 20f;
        this.cityId = hotelPrice.getHotel().getCityId();
        this.priceId = hotelPrice.getId();
        this.price = hotelPrice.getPrice();
        if (hotelPrice.getHotel().getExtend() != null) {
            this.address = hotelPrice.getHotel().getExtend().getAddress();
        }
        if (PriceStatus.UP.equals(hotelPrice.getStatus())) {
            this.payType = "到付";
        } else {
            this.payType = "担保";
        }
        return this;
    }

    public HotelSimpleResponse completeWithHotelPriceEntity(HotelRoomSolrEntity hotelRoomSolrEntity) {
        this.id = hotelRoomSolrEntity.getHotelId();
        this.priceId = hotelRoomSolrEntity.getPriceId();
        this.priceName = hotelRoomSolrEntity.getRoomName();
        this.price = hotelRoomSolrEntity.getPrice();
        if ("UP".equals(hotelRoomSolrEntity.getStatus())) {
            this.payType = "到付";
        } else {
            this.payType = "担保";
        }
        return this;
    }

    public HotelSimpleResponse completeWithHotelEntity(HotelSolrEntity hotelSolrEntity) {
        this.name = hotelSolrEntity.getName();
        this.cover = hotelSolrEntity.getCover();
        this.score = hotelSolrEntity.getScore() / 20f;
        this.cityId = hotelSolrEntity.getCityId();
        this.address = hotelSolrEntity.getAddress();
        return this;
    }
}
