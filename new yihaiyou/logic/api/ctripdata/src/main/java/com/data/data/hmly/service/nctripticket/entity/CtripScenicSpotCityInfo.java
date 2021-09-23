package com.data.data.hmly.service.nctripticket.entity;


import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2016/1/25.
 */
@Entity
@Table(name = "nctrip_scenic_spot_city_info")
public class CtripScenicSpotCityInfo extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "id", length = 20)
    private Long id;	            //  同景点ID
    @Column(name = "cityId", length = 11)
    private Integer cityId;		//	城市ID
    @Column(name = "cityName", length = 256)
    private String cityName;		//	城市名称
    @Column(name = "cityEName", length = 256)
    private String cityEName;		//	城市拼音
    @Column(name = "provinceId", length = 11)
    private Integer provinceId;		//	景点所属省ID
    @Column(name = "provinceName", length = 256)
    private String provinceName;		//	景点所属省名称
    @Column(name = "provinceEName", length = 256)
    private String provinceEName;		//	景点所属省英文名
    @Column(name = "countryId", length = 11)
    private Integer countryId;		//	景点所属国家ID
    @Column(name = "countryName", length = 256)
    private String countryName;		//	景点所属国家名称
    @Column(name = "countryEName", length = 256)
    private String countryEName;	      //	景点所属国家英文名
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityEName() {
        return cityEName;
    }

    public void setCityEName(String cityEName) {
        this.cityEName = cityEName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceEName() {
        return provinceEName;
    }

    public void setProvinceEName(String provinceEName) {
        this.provinceEName = provinceEName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryEName() {
        return countryEName;
    }

    public void setCountryEName(String countryEName) {
        this.countryEName = countryEName;
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

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
}
