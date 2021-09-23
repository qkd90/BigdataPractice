package com.data.data.hmly.service.traffic.entity;

import com.framework.hibernate.util.Entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * Created by dy on 2016/8/24.
 */
@javax.persistence.Entity
@Table(name = "traffic_price_calendar")
public class TrafficPriceCalender extends Entity {
    /*`id` bigint(20) NOT NULL,
    `leaveTime` datetime DEFAULT NULL COMMENT '离开时间',
    `arriveTime` datetime DEFAULT NULL COMMENT '到达时间',
    `price` float(10,0) DEFAULT NULL COMMENT '价格',
    `inventory` int(11) DEFAULT NULL COMMENT '库存',
    `trafficPriceId` bigint(20) DEFAULT NULL,
    `trafficId` bigint(20) DEFAULT NULL,*/

    private static final long serialVersionUID = -9215023448601457011L;


    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "leaveTime")
    private Date leaveTime;

    @Column(name = "arriveTime")
    private Date arriveTime;

    @Column(name = "price")
    private Float price;
    @Column(name = "marketPrice")
    private Float marketPrice;
    @Column(name = "cPrice")
    private Float cprice;
    @Column(name = "inventory")
    private Integer inventory;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trafficPriceId")
    private TrafficPrice trafficPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trafficId")
    private Traffic traffic;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public TrafficPrice getTrafficPrice() {
        return trafficPrice;
    }

    public void setTrafficPrice(TrafficPrice trafficPrice) {
        this.trafficPrice = trafficPrice;
    }

    public Traffic getTraffic() {
        return traffic;
    }

    public void setTraffic(Traffic traffic) {
        this.traffic = traffic;
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
