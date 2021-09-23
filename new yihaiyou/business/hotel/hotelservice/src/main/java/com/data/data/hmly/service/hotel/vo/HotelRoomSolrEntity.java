package com.data.data.hmly.service.hotel.vo;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
@SolrDocument(solrCoreName = "HotelPrice")
public class HotelRoomSolrEntity extends SolrEntity<HotelPrice> {

    @Field
    private Long priceId;
    @Field
    private Long hotelId;
    @Field
    private Long cityId;
    @Field
    private int star;
    @Field
    private String ratePlanCode;
    @Field
    private String roomName;
    @Field
    private String description;
    @Field
    private Boolean breakfast;
    @Field
    private Float price;
    @Field
    private List<String> date;
    @Field
    private String latlng;
    @Field
    private String status;
    @Field
    private Date start;
    @Field
    private Date end;
//    @Field
//    private String py;

    public HotelRoomSolrEntity() {
    }

    public HotelRoomSolrEntity(HotelPrice hotelPrice) {
        this.priceId = hotelPrice.getId();
        this.hotelId = hotelPrice.getHotel().getId();
        if (hotelPrice.getHotel() != null) {
            if (hotelPrice.getHotel().getStar() != null) {

                this.star = hotelPrice.getHotel().getStar();
            }
            if (hotelPrice.getHotel().getCityId() != null) {
                this.cityId = hotelPrice.getHotel().getCityId();
            }
            if (hotelPrice.getHotel().getExtend() != null && hotelPrice.getHotel().getExtend().getLatitude() != null) {
                this.latlng = hotelPrice.getHotel().getExtend().getLatitude() + "," + hotelPrice.getHotel().getExtend().getLongitude();
            }

        }
        this.ratePlanCode = hotelPrice.getRatePlanCode();
        this.roomName = hotelPrice.getRoomName();
//        this.py = PinyinUtil.converterToSpell(this.roomName);
        this.description = hotelPrice.getRoomDescription();
        this.breakfast = hotelPrice.getBreakfast();
        this.price = hotelPrice.getPrice();
        this.date = Lists.newArrayList();
        if (hotelPrice.getStart() != null) {   // solr索引时间需加8个小时
            this.start = DateUtils.add(hotelPrice.getStart(), Calendar.HOUR_OF_DAY, 8);
        }
        if (hotelPrice.getEnd() != null) {   // solr索引时间需加8个小时
            this.end = DateUtils.add(hotelPrice.getEnd(), Calendar.HOUR_OF_DAY, 8);
        }
        this.status = hotelPrice.getStatus().toString();
    }

    public HotelRoomSolrEntity addDate(String date) {
        this.date.add(date);
        return this;
    }

    public HotelRoomSolrEntity addDate(Date date) {
        if (date == null) {
            return this;
        }
        String dateName = DateUtils.getDate(date);
        return addDate(dateName);
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

//    public String getPy() {
//        return py;
//    }
//
//    public void setPy(String py) {
//        this.py = py;
//    }
}
