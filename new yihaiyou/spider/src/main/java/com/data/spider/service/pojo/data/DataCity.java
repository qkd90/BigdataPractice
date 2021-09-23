package com.data.spider.service.pojo.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/10/27.
 */
@Entity
@Table(name = "data_city")
public class DataCity extends com.framework.hibernate.util.Entity {
    private int id;
    private String cityName;
    private String ctripScenicUrl;
    private Integer ctripScenicUsed;
    private Integer ctripPlanUsed;
    private Timestamp ctripScenicDate;
    private String cncnScenicUrl;
    private Integer cncnScenicUsed;
    private Timestamp cncnScenicData;
    private Integer dianpingCityId;
    private Integer ctripCityId;
    private String ctripCityPy;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "city_name", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "ctrip_scenic_url", nullable = true, insertable = true, updatable = true, length = 300)
    public String getCtripScenicUrl() {
        return ctripScenicUrl;
    }

    public void setCtripScenicUrl(String ctripScenicUrl) {
        this.ctripScenicUrl = ctripScenicUrl;
    }

    @Basic
    @Column(name = "ctrip_scenic_used", nullable = true, insertable = true, updatable = true)
    public Integer getCtripScenicUsed() {
        return ctripScenicUsed;
    }

    public void setCtripScenicUsed(Integer ctripScenicUsed) {
        this.ctripScenicUsed = ctripScenicUsed;
    }

    @Basic
    @Column(name = "ctrip_plan_used", nullable = true, insertable = true, updatable = true)
    public Integer getCtripPlanUsed() {
        return ctripPlanUsed;
    }

    public void setCtripPlanUsed(Integer ctripPlanUsed) {
        this.ctripPlanUsed = ctripPlanUsed;
    }

    @Basic
    @Column(name = "ctrip_scenic_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getCtripScenicDate() {
        return ctripScenicDate;
    }

    public void setCtripScenicDate(Timestamp ctripScenicDate) {
        this.ctripScenicDate = ctripScenicDate;
    }

    @Basic
    @Column(name = "cncn_scenic_url", nullable = true, insertable = true, updatable = true, length = 300)
    public String getCncnScenicUrl() {
        return cncnScenicUrl;
    }

    public void setCncnScenicUrl(String cncnScenicUrl) {
        this.cncnScenicUrl = cncnScenicUrl;
    }

    @Basic
    @Column(name = "cncn_scenic_used", nullable = true, insertable = true, updatable = true)
    public Integer getCncnScenicUsed() {
        return cncnScenicUsed;
    }

    public void setCncnScenicUsed(Integer cncnScenicUsed) {
        this.cncnScenicUsed = cncnScenicUsed;
    }

    @Basic
    @Column(name = "cncn_scenic_data", nullable = true, insertable = true, updatable = true)
    public Timestamp getCncnScenicData() {
        return cncnScenicData;
    }

    public void setCncnScenicData(Timestamp cncnScenicData) {
        this.cncnScenicData = cncnScenicData;
    }

    @Basic
    @Column(name = "dianping_city_id", nullable = true, insertable = true, updatable = true)
    public Integer getDianpingCityId() {
        return dianpingCityId;
    }

    public void setDianpingCityId(Integer dianpingCityId) {
        this.dianpingCityId = dianpingCityId;
    }

    @Basic
    @Column(name = "ctrip_city_id", nullable = true, insertable = true, updatable = true)
    public Integer getCtripCityId() {
        return ctripCityId;
    }

    public void setCtripCityId(Integer ctripCityId) {
        this.ctripCityId = ctripCityId;
    }

    @Basic
    @Column(name = "ctrip_city_py", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCtripCityPy() {
        return ctripCityPy;
    }

    public void setCtripCityPy(String ctripCityPy) {
        this.ctripCityPy = ctripCityPy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataCity dataCity = (DataCity) o;

        if (id != dataCity.id) return false;
        if (cityName != null ? !cityName.equals(dataCity.cityName) : dataCity.cityName != null) return false;
        if (ctripScenicUrl != null ? !ctripScenicUrl.equals(dataCity.ctripScenicUrl) : dataCity.ctripScenicUrl != null)
            return false;
        if (ctripScenicUsed != null ? !ctripScenicUsed.equals(dataCity.ctripScenicUsed) : dataCity.ctripScenicUsed != null)
            return false;
        if (ctripPlanUsed != null ? !ctripPlanUsed.equals(dataCity.ctripPlanUsed) : dataCity.ctripPlanUsed != null)
            return false;
        if (ctripScenicDate != null ? !ctripScenicDate.equals(dataCity.ctripScenicDate) : dataCity.ctripScenicDate != null)
            return false;
        if (cncnScenicUrl != null ? !cncnScenicUrl.equals(dataCity.cncnScenicUrl) : dataCity.cncnScenicUrl != null)
            return false;
        if (cncnScenicUsed != null ? !cncnScenicUsed.equals(dataCity.cncnScenicUsed) : dataCity.cncnScenicUsed != null)
            return false;
        if (cncnScenicData != null ? !cncnScenicData.equals(dataCity.cncnScenicData) : dataCity.cncnScenicData != null)
            return false;
        if (dianpingCityId != null ? !dianpingCityId.equals(dataCity.dianpingCityId) : dataCity.dianpingCityId != null)
            return false;
        if (ctripCityId != null ? !ctripCityId.equals(dataCity.ctripCityId) : dataCity.ctripCityId != null)
            return false;
        if (ctripCityPy != null ? !ctripCityPy.equals(dataCity.ctripCityPy) : dataCity.ctripCityPy != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (ctripScenicUrl != null ? ctripScenicUrl.hashCode() : 0);
        result = 31 * result + (ctripScenicUsed != null ? ctripScenicUsed.hashCode() : 0);
        result = 31 * result + (ctripPlanUsed != null ? ctripPlanUsed.hashCode() : 0);
        result = 31 * result + (ctripScenicDate != null ? ctripScenicDate.hashCode() : 0);
        result = 31 * result + (cncnScenicUrl != null ? cncnScenicUrl.hashCode() : 0);
        result = 31 * result + (cncnScenicUsed != null ? cncnScenicUsed.hashCode() : 0);
        result = 31 * result + (cncnScenicData != null ? cncnScenicData.hashCode() : 0);
        result = 31 * result + (dianpingCityId != null ? dianpingCityId.hashCode() : 0);
        result = 31 * result + (ctripCityId != null ? ctripCityId.hashCode() : 0);
        result = 31 * result + (ctripCityPy != null ? ctripCityPy.hashCode() : 0);
        return result;
    }
}
