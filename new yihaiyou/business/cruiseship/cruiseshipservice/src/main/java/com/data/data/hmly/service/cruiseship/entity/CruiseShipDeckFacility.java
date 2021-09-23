package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_deck_facility")
public class CruiseShipDeckFacility extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "cruiseShipId")
    private Long cruiseShipId;
    @ManyToOne
    @JoinColumn(name = "deckId")
    private CruiseShipDeck cruiseShipDeck;
    @Column(name = "name")
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;

    public CruiseShipDeckFacility() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCruiseShipId() {
        return cruiseShipId;
    }

    public void setCruiseShipId(Long cruiseShipId) {
        this.cruiseShipId = cruiseShipId;
    }

    public CruiseShipDeck getCruiseShipDeck() {
        return cruiseShipDeck;
    }

    public void setCruiseShipDeck(CruiseShipDeck cruiseShipDeck) {
        this.cruiseShipDeck = cruiseShipDeck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}


