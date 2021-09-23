package com.data.data.hmly.service.scenic.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data_city")
public class DataCity extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "ctrip_scenic_url")
    private String ctripScenicUrl;
    @Column(name = "ctrip_scenic_used")
    private Integer ctripScenicUsed;
    @Column(name = "ctrip_plan_used")
    private Integer ctripPlanUsed;
    @Column(name = "ctrip_scenic_date")
    private Date ctripScenicDate;
    @Column(name = "cncn_scenic_url")
    private String cncnScenicUrl;
    @Column(name = "cncn_scenic_used")
    private Integer cncnScenicUsed;
    @Column(name = "cncn_scenic_data")
    private Date cncnScenicData;
    @Column(name = "dianping_city_id")
    private Integer dianpingCityId;
    @Column(name = "ctrip_city_id")
    private Integer ctripCityId;
    @Column(name = "ctrip_city_py")
    private String ctripCityPy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCtripScenicUrl() {
        return ctripScenicUrl;
    }

    public void setCtripScenicUrl(String ctripScenicUrl) {
        this.ctripScenicUrl = ctripScenicUrl;
    }

    public Integer getCtripScenicUsed() {
        return ctripScenicUsed;
    }

    public void setCtripScenicUsed(Integer ctripScenicUsed) {
        this.ctripScenicUsed = ctripScenicUsed;
    }

    public Integer getCtripPlanUsed() {
        return ctripPlanUsed;
    }

    public void setCtripPlanUsed(Integer ctripPlanUsed) {
        this.ctripPlanUsed = ctripPlanUsed;
    }

    public Date getCtripScenicDate() {
        return ctripScenicDate;
    }

    public void setCtripScenicDate(Date ctripScenicDate) {
        this.ctripScenicDate = ctripScenicDate;
    }

    public String getCncnScenicUrl() {
        return cncnScenicUrl;
    }

    public void setCncnScenicUrl(String cncnScenicUrl) {
        this.cncnScenicUrl = cncnScenicUrl;
    }

    public Integer getCncnScenicUsed() {
        return cncnScenicUsed;
    }

    public void setCncnScenicUsed(Integer cncnScenicUsed) {
        this.cncnScenicUsed = cncnScenicUsed;
    }

    public Date getCncnScenicData() {
        return cncnScenicData;
    }

    public void setCncnScenicData(Date cncnScenicData) {
        this.cncnScenicData = cncnScenicData;
    }

    public Integer getDianpingCityId() {
        return dianpingCityId;
    }

    public void setDianpingCityId(Integer dianpingCityId) {
        this.dianpingCityId = dianpingCityId;
    }

    public Integer getCtripCityId() {
        return ctripCityId;
    }

    public void setCtripCityId(Integer ctripCityId) {
        this.ctripCityId = ctripCityId;
    }

    public String getCtripCityPy() {
        return ctripCityPy;
    }

    public void setCtripCityPy(String ctripCityPy) {
        this.ctripCityPy = ctripCityPy;
    }
}
