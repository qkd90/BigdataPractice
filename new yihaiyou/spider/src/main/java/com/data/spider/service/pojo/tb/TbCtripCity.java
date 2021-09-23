package com.data.spider.service.pojo.tb;

import com.framework.hibernate.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created by Sane on 15/9/10.
 */
@Entity
@Table(name = "tb_ctrip_city")
public class TbCtripCity extends com.framework.hibernate.util.Entity {
    private long id;
    private Long cityId;
    private String name;
    private String codeName;
    private Long province;
    private Long hotelCount;
    private Long priceCount;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "city_id")
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code_name")
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Basic
    @Column(name = "province")
    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    @Basic
    @Column(name = "hotel_count")
    public Long getHotelCount() {
        return hotelCount;
    }

    public void setHotelCount(Long hotelCount) {
        this.hotelCount = hotelCount;
    }

    @Basic
    @Column(name = "price_count")
    public Long getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(Long priceCount) {
        this.priceCount = priceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbCtripCity that = (TbCtripCity) o;

        if (id != that.id) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (codeName != null ? !codeName.equals(that.codeName) : that.codeName != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (hotelCount != null ? !hotelCount.equals(that.hotelCount) : that.hotelCount != null) return false;
        if (priceCount != null ? !priceCount.equals(that.priceCount) : that.priceCount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (codeName != null ? codeName.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (hotelCount != null ? hotelCount.hashCode() : 0);
        result = 31 * result + (priceCount != null ? priceCount.hashCode() : 0);
        return result;
    }
}
