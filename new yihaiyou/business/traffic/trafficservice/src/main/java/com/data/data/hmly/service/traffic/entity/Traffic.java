package com.data.data.hmly.service.traffic.entity;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.entity.Transportation;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;


/**
 * Created by guoshijie on 2015/12/11.
 * Modified by Sane on 2015/12/25.
 */
@Entity
@Table(name = "traffic")
@PrimaryKeyJoinColumn(name = "productId")
public class Traffic extends Product {

    @Column(name = "trafficType")
    @Enumerated(EnumType.STRING)
    private TrafficType trafficType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaveCity")
    private TbArea leaveCity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arriveCity")
    private TbArea arriveCity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leavePort")
    private Transportation leaveTransportation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrivePort")
    private Transportation arriveTransportation;
    @Column(name = "trafficCode", nullable = true, insertable = true, updatable = true, length = 11)
    private String trafficCode;
    @Column(name = "trafficModel", nullable = true, insertable = true, updatable = true, length = 11)
    private String trafficModel;
    @Column(name = "leaveTime", nullable = true, insertable = true, updatable = true, length = 11)
    private String leaveTime;
    @Column(name = "arriveTime", nullable = true, insertable = true, updatable = true, length = 11)
    private String arriveTime;
    @Column(name = "flightTime", nullable = true, insertable = true, updatable = true, length = 11)
    private Long flightTime;
    @Column(name = "company", nullable = true, insertable = true, updatable = true, length = 11)
    private String company;
    @Column(name = "companyCode", nullable = true, insertable = true, updatable = true, length = 11)
    private String companyCode;
    @OneToMany(mappedBy = "traffic", fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private List<TrafficPrice> prices;
    @Column(name = "startPort", nullable = true, insertable = true, updatable = true, length = 50)
    private String startPort;
    @Column(name = "endPort", nullable = true, insertable = true, updatable = true, length = 50)
    private String endPort;

    private String hashCode;

    public List<TrafficPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<TrafficPrice> prices) {
        this.prices = prices;
    }

    // 航班持续时间字符串（2h30min），临时使用
    @Transient
    private String flightTimeString;


    @Transient
    private Float ferryPrice;

    @Transient
    private Integer ferryInventory;

    @Transient
    private Date leaveDateStart;
    @Transient
    private Date leaveDateEnd;

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public TbArea getLeaveCity() {
        return leaveCity;
    }

    public void setLeaveCity(TbArea leaveCity) {
        this.leaveCity = leaveCity;
    }

    public TbArea getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(TbArea arriveCity) {
        this.arriveCity = arriveCity;
    }

    public Transportation getLeaveTransportation() {
        return leaveTransportation;
    }

    public void setLeaveTransportation(Transportation leaveTransportation) {
        this.leaveTransportation = leaveTransportation;
    }

    public Transportation getArriveTransportation() {
        return arriveTransportation;
    }

    public void setArriveTransportation(Transportation arriveTransportation) {
        this.arriveTransportation = arriveTransportation;
    }

    public String getTrafficCode() {
        return trafficCode;
    }

    public void setTrafficCode(String trafficCode) {
        this.trafficCode = trafficCode;
    }

    public String getTrafficModel() {
        return trafficModel;
    }

    public void setTrafficModel(String trafficModel) {
        this.trafficModel = trafficModel;
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

    public Long getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Long flightTime) {
        this.flightTime = flightTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getFlightTimeString() {
        return flightTimeString;
    }

    public void setFlightTimeString(String flightTimeString) {
        this.flightTimeString = flightTimeString;
    }

    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    public String getEndPort() {
        return endPort;
    }

    public void setEndPort(String endPort) {
        this.endPort = endPort;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Float getFerryPrice() {
        return ferryPrice;
    }

    public void setFerryPrice(Float ferryPrice) {
        this.ferryPrice = ferryPrice;
    }

    public Integer getFerryInventory() {
        return ferryInventory;
    }

    public void setFerryInventory(Integer ferryInventory) {
        this.ferryInventory = ferryInventory;
    }

    public Date getLeaveDateStart() {
        return leaveDateStart;
    }

    public void setLeaveDateStart(Date leaveDateStart) {
        this.leaveDateStart = leaveDateStart;
    }

    public Date getLeaveDateEnd() {
        return leaveDateEnd;
    }

    public void setLeaveDateEnd(Date leaveDateEnd) {
        this.leaveDateEnd = leaveDateEnd;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Traffic traffic = (Traffic) o;
//
//        if (id != traffic.id) return false;
//        if (trafficType != null ? !trafficType.equals(traffic.trafficType) : traffic.trafficType != null) return false;
//        if (leaveCity != null ? !leaveCity.equals(traffic.leaveCity) : traffic.leaveCity != null) return false;
//        if (arriveCity != null ? !arriveCity.equals(traffic.arriveCity) : traffic.arriveCity != null) return false;
//        if (trafficCode != null ? !trafficCode.equals(traffic.trafficCode) : traffic.trafficCode != null) return false;
//        if (trafficModel != null ? !trafficModel.equals(traffic.trafficModel) : traffic.trafficModel != null)
//            return false;
//        if (leaveTime != null ? !leaveTime.equals(traffic.leaveTime) : traffic.leaveTime != null) return false;
//        if (arriveTime != null ? !arriveTime.equals(traffic.arriveTime) : traffic.arriveTime != null) return false;
//        if (flightTime != null ? !flightTime.equals(traffic.flightTime) : traffic.flightTime != null) return false;
//        if (company != null ? !company.equals(traffic.company) : traffic.company != null) return false;
//        if (companyCode != null ? !companyCode.equals(traffic.companyCode) : traffic.companyCode != null) return false;
//        if (startPort != null ? !startPort.equals(traffic.startPort) : traffic.startPort != null) return false;
//        if (endPort != null ? !endPort.equals(traffic.endPort) : traffic.endPort != null) return false;
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        int result = (int) (id ^ (id >>> 32));
//        result = 31 * result + (trafficType != null ? trafficType.hashCode() : 0);
//        result = 31 * result + (leaveCity != null ? leaveCity.hashCode() : 0);
//        result = 31 * result + (arriveCity != null ? arriveCity.hashCode() : 0);
//        result = 31 * result + (trafficCode != null ? trafficCode.hashCode() : 0);
//        result = 31 * result + (trafficModel != null ? trafficModel.hashCode() : 0);
//        result = 31 * result + (leaveTime != null ? leaveTime.hashCode() : 0);
//        result = 31 * result + (arriveTime != null ? arriveTime.hashCode() : 0);
//        result = 31 * result + (flightTime != null ? flightTime.hashCode() : 0);
//        result = 31 * result + (company != null ? company.hashCode() : 0);
//        result = 31 * result + (companyCode != null ? companyCode.hashCode() : 0);
//        result = 31 * result + (startPort != null ? startPort.hashCode() : 0);
//        result = 31 * result + (endPort != null ? endPort.hashCode() : 0);
//        return result;
//    }
}
