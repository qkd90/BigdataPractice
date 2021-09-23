package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.enums.FerryIdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huangpeijie on 2016-11-21,0021.
 */
@Entity
@Table(name = "ferryOrderItem")
@JsonIgnoreProperties
public class FerryOrderItem extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String ticketId;
    private String ticketName;
    private Float price;
    private String number;
    @Enumerated(EnumType.STRING)
    private FerryIdType idTypeName;
    private String name;
    private String idnumber;
    private String mobile;
    private Boolean hasChecked;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    @ManyToOne
    @JoinColumn(name = "ferryOrder")
    private FerryOrder ferryOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public FerryIdType getIdTypeName() {
        return idTypeName;
    }

    public void setIdTypeName(FerryIdType idTypeName) {
        this.idTypeName = idTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public FerryOrder getFerryOrder() {
        return ferryOrder;
    }

    public void setFerryOrder(FerryOrder ferryOrder) {
        this.ferryOrder = ferryOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(Boolean hasChecked) {
        this.hasChecked = hasChecked;
    }
}
