package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.order.entity.CreditCard;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 16/1/12.
 */
public class OrderDetailResponse {

    private Long id;
    private OrderStatus status;
    private OrderDetailStatus detailStatus;
    private String printStatus;
    private String orderNo;
    private Date orderDate;
    private Float cost;
    private String confirmNo;
    private String name;
    private Date date;
    private String type;
    private Float price;
    private Integer num;
    private List<OrderTourist> touristList;
    private String contactsName;
    private String tel;
    private Float singleCost;

    private Long productId;
    private Long priceId;

    // hotel
    private Float singlePrice;
    private Date leaveDate;
    private String breakfast;
    private String ratePlanCode;
    private Integer days;
    private String source;
    private PriceStatus priceStatus;
    private CreditCard creditCard;

    // traffic
    private String trafficType;
    private String company;
    private String trafficCode;
    private String leavePort;
    private String arrivePort;
    private String leaveTime;
    private String arriveTime;
    private String trafficTime;
    private String leaveCity;
    private String arriveCity;
    private Float airportBuildFee;
    private Float additionalFuelTax;

    //ticket
    private Long cityId;
    private int day;

    private Float singleRoomPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getConfirmNo() {
        return confirmNo;
    }

    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<OrderTourist> getTouristList() {
        return touristList;
    }

    public void setTouristList(List<OrderTourist> touristList) {
        this.touristList = touristList;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Float getSingleCost() {
        return singleCost;
    }

    public void setSingleCost(Float singleCost) {
        this.singleCost = singleCost;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrafficCode() {
        return trafficCode;
    }

    public void setTrafficCode(String trafficCode) {
        this.trafficCode = trafficCode;
    }

    public String getLeavePort() {
        return leavePort;
    }

    public void setLeavePort(String leavePort) {
        this.leavePort = leavePort;
    }

    public String getArrivePort() {
        return arrivePort;
    }

    public void setArrivePort(String arrivePort) {
        this.arrivePort = arrivePort;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getTrafficTime() {
        return trafficTime;
    }

    public void setTrafficTime(String trafficTime) {
        this.trafficTime = trafficTime;
    }

    public Float getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(Float singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getLeaveCity() {
        return leaveCity;
    }

    public void setLeaveCity(String leaveCity) {
        this.leaveCity = leaveCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Float getAirportBuildFee() {
        return airportBuildFee;
    }

    public void setAirportBuildFee(Float airportBuildFee) {
        this.airportBuildFee = airportBuildFee;
    }

    public Float getAdditionalFuelTax() {
        return additionalFuelTax;
    }

    public void setAdditionalFuelTax(Float additionalFuelTax) {
        this.additionalFuelTax = additionalFuelTax;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public PriceStatus getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(PriceStatus priceStatus) {
        this.priceStatus = priceStatus;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public OrderDetailStatus getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(OrderDetailStatus detailStatus) {
        this.detailStatus = detailStatus;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Float getSingleRoomPrice() {
        return singleRoomPrice;
    }

    public void setSingleRoomPrice(Float singleRoomPrice) {
        this.singleRoomPrice = singleRoomPrice;
    }
}
