package com.data.data.hmly.service.transportation.entity;

import javax.persistence.*;

/**
 * Created by Sane on 15/9/11.
 */
@Entity
@Table(name = "tb_transportation")
public class Transportation extends com.framework.hibernate.util.Entity {
    private Long id;
    private String name;
    private Double lng;
    private Double lat;
    private Double gcjLng;
    private Double gcjLat;
    private String cityCode;
    private Integer cityId;
    private String regionName;
    private String address;
    private String telephone;
    private String coverImg;
    private Integer type;
//    private Timestamp arriveTime;
//    private Timestamp leaveTime;
//    private Timestamp createTime;
//    private Timestamp modifyTime;
    private String dataSource;
    private String dataSourceUrl;
    private Integer dataStatus;
    private String cityName;
    private String code;
    private Integer status;
    private String searchName;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    @Id
//    @Column(name = "id", nullable = false, insertable = true, updatable = true)
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    @Basic
    @Column(name = "name", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Basic
    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "gcj_lng")
    public Double getGcjLng() {
        return gcjLng;
    }

    public void setGcjLng(Double gcjLng) {
        this.gcjLng = gcjLng;
    }

    @Basic
    @Column(name = "gcj_lat")
    public Double getGcjLat() {
        return gcjLat;
    }

    public void setGcjLat(Double gcjLat) {
        this.gcjLat = gcjLat;
    }

    @Basic
    @Column(name = "city_id", length = 255)
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "city_code", length = 255)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "region_name", length = 200)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "address", length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone", length = 45)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "cover_img", length = 255)
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

//    @Basic
//    @Column(name = "arrive_time", nullable = true, insertable = true, updatable = true)
//    public Timestamp getArriveTime() {
//        return arriveTime;
//    }
//
//    public void setArriveTime(Timestamp arriveTime) {
//        this.arriveTime = arriveTime;
//    }
//
//    @Basic
//    @Column(name = "leave_time", nullable = true, insertable = true, updatable = true)
//    public Timestamp getLeaveTime() {
//        return leaveTime;
//    }
//
//    public void setLeaveTime(Timestamp leaveTime) {
//        this.leaveTime = leaveTime;
//    }
//
//    @Basic
//    @Column(name = "create_time", nullable = true, insertable = true, updatable = true)
//    public Timestamp getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Timestamp createTime) {
//        this.createTime = createTime;
//    }
//
//    @Basic
//    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
//    public Timestamp getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(Timestamp modifyTime) {
//        this.modifyTime = modifyTime;
//    }

    @Basic
    @Column(name = "data_source",length = 45)
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Basic
    @Column(name = "data_source_url", length = 200)
    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    @Basic
    @Column(name = "data_status")
    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Basic
    @Column(name = "search_name", length = 45)
    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Basic
    @Column(name = "city_name", length = 45)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "code", length = 45)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transportation that = (Transportation) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        if (gcjLng != null ? !gcjLng.equals(that.gcjLng) : that.gcjLng != null) return false;
        if (gcjLat != null ? !gcjLat.equals(that.gcjLat) : that.gcjLat != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (coverImg != null ? !coverImg.equals(that.coverImg) : that.coverImg != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
//        if (arriveTime != null ? !arriveTime.equals(that.arriveTime) : that.arriveTime != null) return false;
//        if (leaveTime != null ? !leaveTime.equals(that.leaveTime) : that.leaveTime != null) return false;
//        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
//        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataSourceUrl != null ? !dataSourceUrl.equals(that.dataSourceUrl) : that.dataSourceUrl != null)
            return false;
        if (dataStatus != null ? !dataStatus.equals(that.dataStatus) : that.dataStatus != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (searchName != null ? !searchName.equals(that.searchName) : that.searchName != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (gcjLng != null ? gcjLng.hashCode() : 0);
        result = 31 * result + (gcjLat != null ? gcjLat.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (coverImg != null ? coverImg.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
//        result = 31 * result + (arriveTime != null ? arriveTime.hashCode() : 0);
//        result = 31 * result + (leaveTime != null ? leaveTime.hashCode() : 0);
//        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
//        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataSourceUrl != null ? dataSourceUrl.hashCode() : 0);
        result = 31 * result + (dataStatus != null ? dataStatus.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (searchName != null ? searchName.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
