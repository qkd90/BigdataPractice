package com.data.data.hmly.service.traffic.entity;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.interfaces.ProductPrice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by guoshijie on 2015/12/11.
 * Modified by Sane on 2015/12/25.
 */
@Entity
@Table(name = "traffic_price")
public class TrafficPrice extends com.framework.hibernate.util.Entity implements ProductPrice {

    public TrafficPrice() {
    }

    public TrafficPrice(Traffic traffic, Date leaveTime, Date arriveTime) {
        this.traffic = traffic;
        this.leaveTime = leaveTime;
        this.arriveTime = arriveTime;
    }

    public TrafficPrice(String seatCode, String seatName, Date arriveTime, Date leaveTime, Float price, String seatNum, String seatType, Traffic traffic) {
        this.seatCode = seatCode;
        this.seatName = seatName;
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
        this.price = price;
        this.seatNum = seatNum;
        this.seatType = seatType;
        this.traffic = traffic;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trafficId")
    private Traffic traffic;

    @Column(name = "seatType", nullable = true, insertable = true, updatable = true)
    private String seatType;
    @Column(name = "seatNum", nullable = true, insertable = true, updatable = true)
    private String seatNum;
    @Column(name = "price")
    private Float price;
    @Column(name = "marketPrice")
    private Float marketPrice;
    @Column(name = "cPrice")
    private Float cprice;
    @Column(name = "airportBuildFee")
    private Float airportBuildFee;
    @Column(name = "additionalFuelTax")
    private Float additionalFuelTax;
    @Column(name = "modifyTime")
    private Date modifyTime;
    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "leaveTime", nullable = true, insertable = true, updatable = true, length = 11)
    private Date leaveTime;
    @Column(name = "arriveTime", nullable = true, insertable = true, updatable = true, length = 11)
    private Date arriveTime;
    @Column(name = "seatName", nullable = true, insertable = true, updatable = true, length = 50)
    private String seatName;
    @Column(name = "seatCode", nullable = true, insertable = true, updatable = true, length = 50)
    private String seatCode;
    @Column(name = "changePolicy", nullable = true, insertable = true, updatable = true, length = 1000)
    private String changePolicy;
    @Column(name = "backPolicy", nullable = true, insertable = true, updatable = true, length = 1000)
    private String backPolicy;

    private String hashCode;


    @Transient
    private Double distance;
    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Traffic getTraffic() {
        return traffic;
    }

    public void setTraffic(Traffic traffic) {
        this.traffic = traffic;
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

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getProductId() {
        return traffic.getId();
    }

    @Override
    public Long getPriceId() {
        return id;
    }

    @Override
    public String getName() {
        return traffic.getTrafficCode();
    }

    @Override
    public Float getPrice() {
        return price;
    }

    @Override
    public Date getDate() {
        return leaveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Override
    public ProductType getProductType() {
        return traffic.getProType();
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getChangePolicy() {
        return changePolicy;
    }

    public void setChangePolicy(String changePolicy) {
        this.changePolicy = changePolicy;
    }

    public String getBackPolicy() {
        return backPolicy;
    }

    public void setBackPolicy(String backPolicy) {
        this.backPolicy = backPolicy;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Float getCprice() {
        return cprice;
    }

    public void setCprice(Float cprice) {
        this.cprice = cprice;
    }
}
