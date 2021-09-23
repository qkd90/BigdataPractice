package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jonathan.Guo
 */
public class PlanBookingResponse {

    private Long fromCityId;
    private String fromCityName;
    private Long cityId;
    private String cityName;
    private int days;
    private Date fromDate;
    private Date toDate;
    private Long coreScenic;
    private TrafficType trafficType;

    private final List<BookingResponse> hotels = new CopyOnWriteArrayList<BookingResponse>();
    private final List<BookingResponse> planes = new CopyOnWriteArrayList<BookingResponse>();
    private final List<BookingResponse> trains = new CopyOnWriteArrayList<BookingResponse>();
    /**
     * 旅游线路     *
     */
    private List<List<String>> tripLines = Lists.newArrayList();

    public PlanBookingResponse addHotel(BookingResponse hotel) {
        hotels.add(hotel);
        return this;
    }

    public PlanBookingResponse addPlane(BookingResponse plane) {
        planes.add(plane);
        return this;
    }

    public PlanBookingResponse addTrain(BookingResponse train) {
        trains.add(train);
        return this;
    }

    public PlanBookingResponse addTripLine(List<String> tripLine) {
        this.tripLines.add(tripLine);
        return this;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public PlanBookingResponse withFromCityId(Long cityId) {
        this.fromCityId = cityId;
        return this;
    }

    public PlanBookingResponse withFromCityName(String cityName) {
        this.fromCityName = cityName;
        return this;
    }

    public PlanBookingResponse withCityId(Long cityId) {

        this.cityId = cityId;
        return this;
    }

    public PlanBookingResponse withCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public PlanBookingResponse withFromDate(Date fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public PlanBookingResponse withToDate(Date toDate) {
        this.toDate = toDate;
        return this;
    }

    public PlanBookingResponse withCoreScenic(Long scenicId) {
        this.coreScenic = scenicId;
        return this;
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public PlanBookingResponse addDays() {
        return addDays(1);
    }

    public PlanBookingResponse addDays(int days) {
        this.days += days;
        return this;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getCoreScenic() {
        return coreScenic;
    }

    public void setCoreScenic(Long coreScenic) {
        this.coreScenic = coreScenic;
    }

    public List<BookingResponse> getHotels() {
        return hotels;
    }

    public List<BookingResponse> getPlanes() {
        return planes;
    }

    public List<BookingResponse> getTrains() {
        return trains;
    }

    public List<List<String>> getTripLines() {
        return tripLines;
    }

    public void setTripLines(List<List<String>> tripLines) {
        this.tripLines = tripLines;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }
}
