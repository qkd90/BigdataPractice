package com.data.data.hmly.service.ctripdestination.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sane on 15/12/15.
 */
@Entity
@Table(name = "ctrip_destination_city", schema = "", catalog = "ctrip")
public class CtripDestinationCity  extends com.framework.hibernate.util.Entity {

    private Long id;
    private int cityDataId;
    private int cityId;
    private Integer districtId;
    private String cityName;
    private String cityEName;
    private String cityNamePy;
    private String firstLetter;
    private String jianPin;
    private int isDefault;
    private String flag;
    private Integer weightFlag;
    private String countryId;
    private String countryName;
    private String latitude;
    private String longitude;
    private Double hotFlag;

    @Id
//    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cityDataId", nullable = false, insertable = true, updatable = true)
    public int getCityDataId() {
        return cityDataId;
    }

    public void setCityDataId(int cityDataId) {
        this.cityDataId = cityDataId;
    }

    @Basic
    @Column(name = "cityId", nullable = false, insertable = true, updatable = true)
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "districtId", nullable = true, insertable = true, updatable = true)
    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    @Basic
    @Column(name = "cityName", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "cityEName", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getCityEName() {
        return cityEName;
    }

    public void setCityEName(String cityEName) {
        this.cityEName = cityEName;
    }

    @Basic
    @Column(name = "cityNamePY", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getCityNamePy() {
        return cityNamePy;
    }

    public void setCityNamePy(String cityNamePy) {
        this.cityNamePy = cityNamePy;
    }

    @Basic
    @Column(name = "firstLetter", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Basic
    @Column(name = "jianPin", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getJianPin() {
        return jianPin;
    }

    public void setJianPin(String jianPin) {
        this.jianPin = jianPin;
    }

    @Basic
    @Column(name = "is_default", nullable = false, insertable = true, updatable = true)
    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Basic
    @Column(name = "FLAG", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Basic
    @Column(name = "weightFlag", nullable = true, insertable = true, updatable = true)
    public Integer getWeightFlag() {
        return weightFlag;
    }

    public void setWeightFlag(Integer weightFlag) {
        this.weightFlag = weightFlag;
    }

    @Basic
    @Column(name = "countryID", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "countryName", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "latitude", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "hotFlag", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(Double hotFlag) {
        this.hotFlag = hotFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CtripDestinationCity that = (CtripDestinationCity) o;

        if (cityDataId != that.cityDataId) return false;
        if (cityId != that.cityId) return false;
        if (isDefault != that.isDefault) return false;
        if (districtId != null ? !districtId.equals(that.districtId) : that.districtId != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (cityEName != null ? !cityEName.equals(that.cityEName) : that.cityEName != null) return false;
        if (cityNamePy != null ? !cityNamePy.equals(that.cityNamePy) : that.cityNamePy != null) return false;
        if (firstLetter != null ? !firstLetter.equals(that.firstLetter) : that.firstLetter != null) return false;
        if (jianPin != null ? !jianPin.equals(that.jianPin) : that.jianPin != null) return false;
        if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;
        if (weightFlag != null ? !weightFlag.equals(that.weightFlag) : that.weightFlag != null) return false;
        if (countryId != null ? !countryId.equals(that.countryId) : that.countryId != null) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (hotFlag != null ? !hotFlag.equals(that.hotFlag) : that.hotFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cityDataId;
        result = 31 * result + cityId;
        result = 31 * result + (districtId != null ? districtId.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (cityEName != null ? cityEName.hashCode() : 0);
        result = 31 * result + (cityNamePy != null ? cityNamePy.hashCode() : 0);
        result = 31 * result + (firstLetter != null ? firstLetter.hashCode() : 0);
        result = 31 * result + (jianPin != null ? jianPin.hashCode() : 0);
        result = 31 * result + isDefault;
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (weightFlag != null ? weightFlag.hashCode() : 0);
        result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (hotFlag != null ? hotFlag.hashCode() : 0);
        return result;
    }
}
