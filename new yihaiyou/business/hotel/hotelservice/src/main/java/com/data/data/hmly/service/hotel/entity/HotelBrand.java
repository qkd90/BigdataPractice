package com.data.data.hmly.service.hotel.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sane on 16/5/6.
 */
@Entity
@Table(name = "hotel_brand")
public class HotelBrand extends com.framework.hibernate.util.Entity {
    private Integer brandId;
    private Integer groupId;
    private String shortName;
    private String name;
    private String letters;

    @Id
    @Column(name = "brand_id", nullable = false)
    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Basic
    @Column(name = "group_id", nullable = true)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "short_name", nullable = true, length = 50)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "letters", nullable = true, length = 50)
    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelBrand that = (HotelBrand) o;

        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (letters != null ? !letters.equals(that.letters) : that.letters != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brandId != null ? brandId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (letters != null ? letters.hashCode() : 0);
        return result;
    }
}
