package com.data.spider.service.pojo;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Table;

import com.framework.hibernate.util.Entity;

/**
 * By ZZL 2015.10.22
 */
@javax.persistence.Entity
@Table(name = "tb_station")
public class TbStation extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5996263797005763162L;

	 private long id;

	    @Id
	    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    private String name;

	    @Basic
	    @javax.persistence.Column(name = "name", nullable = true, insertable = true, updatable = true, length = 255)
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    private Double lng;

	    @Basic
	    @javax.persistence.Column(name = "lng", nullable = true, insertable = true, updatable = true, precision = 0)
	    public Double getLng() {
	        return lng;
	    }

	    public void setLng(Double lng) {
	        this.lng = lng;
	    }

	    private Double lat;

	    @Basic
	    @javax.persistence.Column(name = "lat", nullable = true, insertable = true, updatable = true, precision = 0)
	    public Double getLat() {
	        return lat;
	    }

	    public void setLat(Double lat) {
	        this.lat = lat;
	    }

	    private Double gcjLng;

	    @Basic
	    @javax.persistence.Column(name = "gcj_lng", nullable = true, insertable = true, updatable = true, precision = 0)
	    public Double getGcjLng() {
	        return gcjLng;
	    }

	    public void setGcjLng(Double gcjLng) {
	        this.gcjLng = gcjLng;
	    }

	    private Double gcjLat;

	    @Basic
	    @javax.persistence.Column(name = "gcj_lat", nullable = true, insertable = true, updatable = true, precision = 0)
	    public Double getGcjLat() {
	        return gcjLat;
	    }

	    public void setGcjLat(Double gcjLat) {
	        this.gcjLat = gcjLat;
	    }

	    private String cityCode;

	    @Basic
	    @javax.persistence.Column(name = "city_code", nullable = true, insertable = true, updatable = true, length = 255)
	    public String getCityCode() {
	        return cityCode;
	    }

	    public void setCityCode(String cityCode) {
	        this.cityCode = cityCode;
	    }

	    private String regionName;

	    @Basic
	    @javax.persistence.Column(name = "region_name", nullable = true, insertable = true, updatable = true, length = 200)
	    public String getRegionName() {
	        return regionName;
	    }

	    public void setRegionName(String regionName) {
	        this.regionName = regionName;
	    }

	    private String address;

	    @Basic
	    @javax.persistence.Column(name = "address", nullable = true, insertable = true, updatable = true, length = 255)
	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    private String telephone;

	    @Basic
	    @javax.persistence.Column(name = "telephone", nullable = true, insertable = true, updatable = true, length = 45)
	    public String getTelephone() {
	        return telephone;
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	    }

	    private String coverImg;

	    @Basic
	    @javax.persistence.Column(name = "cover_img", nullable = true, insertable = true, updatable = true, length = 255)
	    public String getCoverImg() {
	        return coverImg;
	    }

	    public void setCoverImg(String coverImg) {
	        this.coverImg = coverImg;
	    }

	    private Integer type;

	    @Basic
	    @javax.persistence.Column(name = "type", nullable = true, insertable = true, updatable = true)
	    public Integer getType() {
	        return type;
	    }

	    public void setType(Integer type) {
	        this.type = type;
	    }

	    private Timestamp arriveTime;

	    @Basic
	    @javax.persistence.Column(name = "arrive_time", nullable = true, insertable = true, updatable = true)
	    public Timestamp getArriveTime() {
	        return arriveTime;
	    }

	    public void setArriveTime(Timestamp arriveTime) {
	        this.arriveTime = arriveTime;
	    }

	    private Timestamp leaveTime;

	    @Basic
	    @javax.persistence.Column(name = "leave_time", nullable = true, insertable = true, updatable = true)
	    public Timestamp getLeaveTime() {
	        return leaveTime;
	    }

	    public void setLeaveTime(Timestamp leaveTime) {
	        this.leaveTime = leaveTime;
	    }

	    private Timestamp createTime;

	    @Basic
	    @javax.persistence.Column(name = "create_time", nullable = true, insertable = true, updatable = true)
	    public Timestamp getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Timestamp createTime) {
	        this.createTime = createTime;
	    }

	    private Timestamp modifyTime;

	    @Basic
	    @javax.persistence.Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
	    public Timestamp getModifyTime() {
	        return modifyTime;
	    }

	    public void setModifyTime(Timestamp modifyTime) {
	        this.modifyTime = modifyTime;
	    }

	    private String dataSource;

	    @Basic
	    @javax.persistence.Column(name = "data_source", nullable = true, insertable = true, updatable = true, length = 45)
	    public String getDataSource() {
	        return dataSource;
	    }

	    public void setDataSource(String dataSource) {
	        this.dataSource = dataSource;
	    }

	    private String dataSourceUrl;

	    @Basic
	    @javax.persistence.Column(name = "data_source_url", nullable = true, insertable = true, updatable = true, length = 200)
	    public String getDataSourceUrl() {
	        return dataSourceUrl;
	    }

	    public void setDataSourceUrl(String dataSourceUrl) {
	        this.dataSourceUrl = dataSourceUrl;
	    }

	    private Integer dataStatus;

	    @Basic
	    @javax.persistence.Column(name = "data_status", nullable = true, insertable = true, updatable = true)
	    public Integer getDataStatus() {
	        return dataStatus;
	    }

	    public void setDataStatus(Integer dataStatus) {
	        this.dataStatus = dataStatus;
	    }

//	    private Integer cityCodeTmp;
	//
//	    @Basic
//	    @javax.persistence.Column(name = "city_code_tmp", nullable = true, insertable = true, updatable = true)
//	    public Integer getCityCodeTmp() {
//	        return cityCodeTmp;
//	    }
	//
//	    public void setCityCodeTmp(Integer cityCodeTmp) {
//	        this.cityCodeTmp = cityCodeTmp;
//	    }

//	    private String cityName;
	//
//	    @Basic
//	    @javax.persistence.Column(name = "city_name", nullable = true, insertable = true, updatable = true, length = 45)
//	    public String getCityName() {
//	        return cityName;
//	    }
	//
//	    public void setCityName(String cityName) {
//	        this.cityName = cityName;
//	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        TbStation that = (TbStation) o;

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
	        if (arriveTime != null ? !arriveTime.equals(that.arriveTime) : that.arriveTime != null) return false;
	        if (leaveTime != null ? !leaveTime.equals(that.leaveTime) : that.leaveTime != null) return false;
	        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
	        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
	        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
	        if (dataSourceUrl != null ? !dataSourceUrl.equals(that.dataSourceUrl) : that.dataSourceUrl != null)
	            return false;
	        if (dataStatus != null ? !dataStatus.equals(that.dataStatus) : that.dataStatus != null) return false;
//	        if (cityCodeTmp != null ? !cityCodeTmp.equals(that.cityCodeTmp) : that.cityCodeTmp != null) return false;
//	        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;

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
	        result = 31 * result + (arriveTime != null ? arriveTime.hashCode() : 0);
	        result = 31 * result + (leaveTime != null ? leaveTime.hashCode() : 0);
	        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
	        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
	        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
	        result = 31 * result + (dataSourceUrl != null ? dataSourceUrl.hashCode() : 0);
	        result = 31 * result + (dataStatus != null ? dataStatus.hashCode() : 0);
//	        result = 31 * result + (cityCodeTmp != null ? cityCodeTmp.hashCode() : 0);
//	        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
	        return result;
	    }
}