package com.data.data.hmly.service.lxbcommon.entity;

import com.data.data.hmly.service.entity.TbArea;

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

/**
 * Created by caiys on 2016/6/15.
 * 定制需求目的地
 */
@Entity
@Table(name = "custom_require_destination")
public class CustomRequireDestination extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id; // 标识
    @ManyToOne
    @JoinColumn(name = "customRequireId")
    private CustomRequire customRequire; // 定制需求标识
//    private Long cityId; // 城市标识
    @ManyToOne
    @JoinColumn(name = "cityId")
    private TbArea city; // 城市标识
    @Column(name = "cityName")
    private String cityName;
    @Column(name = "level")
    private Integer level;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime; // 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomRequire getCustomRequire() {
        return customRequire;
    }

    public void setCustomRequire(CustomRequire customRequire) {
        this.customRequire = customRequire;
    }

    public TbArea getCity() {
        return city;
    }

    public void setCity(TbArea city) {
        this.city = city;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
