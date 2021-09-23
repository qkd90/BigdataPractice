package com.data.data.hmly.service.restaurant.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "products")
public class RestaurantSolrEntity extends SolrEntity<Restaurant> {
    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private Double lng;
    @Field
    private Double lat;
    @Field
    private final SolrType type = SolrType.restaurant;
    @Field
    private String typeid;

    public RestaurantSolrEntity() {

    }

    public RestaurantSolrEntity(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        if (restaurant.getGeoInfo() != null) {
            this.lng = restaurant.getGeoInfo().getBaiduLng();
            this.lat = restaurant.getGeoInfo().getBaiduLat();
        }
        this.typeid = this.type.toString() + this.id;
    }

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

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public SolrType getType() {
        return type;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }
}