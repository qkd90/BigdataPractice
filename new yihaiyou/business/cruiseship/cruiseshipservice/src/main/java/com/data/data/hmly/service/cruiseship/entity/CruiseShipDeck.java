package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_deck")
public class CruiseShipDeck extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cruiseShipId")
    private CruiseShip cruiseShip;
    @Column(name = "level")
    private Integer level;
    @Column(name = "levelDesc")
    private String levelDesc;
    @Column(name = "deckFacility")
    private String deckFacility;
    @Column(name = "shapeImage")
    private String shapeImage;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;

    @OneToMany(mappedBy = "cruiseShipDeck", fetch = FetchType.LAZY)
    private Set<CruiseShipDeckFacility> cruiseShipDeckFacilitys;

    public CruiseShipDeck() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public String getDeckFacility() {
        return deckFacility;
    }

    public void setDeckFacility(String deckFacility) {
        this.deckFacility = deckFacility;
    }

    public String getShapeImage() {
        return shapeImage;
    }

    public void setShapeImage(String shapeImage) {
        this.shapeImage = shapeImage;
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

    public Set<CruiseShipDeckFacility> getCruiseShipDeckFacilitys() {
        return cruiseShipDeckFacilitys;
    }

    public void setCruiseShipDeckFacilitys(Set<CruiseShipDeckFacility> cruiseShipDeckFacilitys) {
        this.cruiseShipDeckFacilitys = cruiseShipDeckFacilitys;
    }
}


