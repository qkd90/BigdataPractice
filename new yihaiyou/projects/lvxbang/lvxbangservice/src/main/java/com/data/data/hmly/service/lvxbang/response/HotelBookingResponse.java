package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.vo.HotelRoomSolrEntity;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class HotelBookingResponse extends BookingResponse {

    private float score;
    private int star;
    private String checkInDate;
    private String checkOutDate;
    private int commentCount;
    private String source;
    private List<HotelRoomSolrEntity> availableRooms = Lists.newArrayList();

    public HotelBookingResponse(HotelPrice hotelPrice) {
        super(hotelPrice);
        this.star = hotelPrice.getHotel().getStar();
        this.score = hotelPrice.getHotel().getScore();
        this.commentCount = hotelPrice.getHotel().getCommentList().size();
        this.source = String.valueOf(hotelPrice.getHotel().getSource());
    }

    public HotelBookingResponse checkIn(Date date) {
        this.checkInDate = DateUtils.formatShortDate(date);
        return this;
    }

    public HotelBookingResponse checkOut(Date date) {
        this.checkOutDate = DateUtils.formatShortDate(date);
        return this;
    }

    public HotelBookingResponse addAvailableRoom(HotelRoomSolrEntity room) {
        availableRooms.add(room);
        return this;
    }

    public HotelBookingResponse addAvailableRooms(Collection<HotelRoomSolrEntity> rooms) {
        availableRooms.addAll(rooms);
        return this;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<HotelRoomSolrEntity> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(List<HotelRoomSolrEntity> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
