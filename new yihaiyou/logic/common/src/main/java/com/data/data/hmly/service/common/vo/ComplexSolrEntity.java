package com.data.data.hmly.service.common.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.framework.hibernate.util.Entity;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by zzl on 2016/1/11.
 */
@SolrDocument(solrCoreName = "products")
public class ComplexSolrEntity extends SolrEntity<Entity> {
    @Field
    public Long id;
    @Field
    public String name;
    @Field
    public SolrType type;
    @Field
    public String city;
    @Field
    public Long cityId;

    public Integer transportationType; // 交通类型优化显示 1-火车站, 2-机场, 3-汽车站

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SolrType getType() {
        return type;
    }

    public void setType(SolrType type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(Integer transportationType) {
        this.transportationType = transportationType;
    }
}
