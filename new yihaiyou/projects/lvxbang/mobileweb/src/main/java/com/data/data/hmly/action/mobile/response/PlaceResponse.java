package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.impression.entity.PlaceType;
import com.data.data.hmly.service.restaurant.vo.RestaurantSolrEntity;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class PlaceResponse {
    private String name;
    private Long targetId;
    private PlaceType type;

    public PlaceResponse() {
    }

    public PlaceResponse(SuggestionEntity suggestionEntity) {
        this.name = suggestionEntity.getName();
        this.targetId = Long.valueOf(suggestionEntity.getId());
        if (suggestionEntity.getType().equals(SolrType.scenic_info.toString())) {
            this.type = PlaceType.scenic;
        } else if (suggestionEntity.getType().equals(SolrType.hotel.toString())) {
            this.type = PlaceType.hotel;
        } else if (suggestionEntity.getType().equals(SolrType.restaurant.toString())) {
            this.type = PlaceType.food;
        }
    }

    public PlaceResponse(ScenicSolrEntity scenicSolrEntity) {
        this.name = scenicSolrEntity.getName();
        this.targetId = scenicSolrEntity.getId();
        this.type = PlaceType.scenic;
    }

    public PlaceResponse(HotelSolrEntity hotelSolrEntity) {
        this.name = hotelSolrEntity.getName();
        this.targetId = hotelSolrEntity.getId();
        this.type = PlaceType.hotel;
    }

    public PlaceResponse(RestaurantSolrEntity restaurantSolrEntity) {
        this.name = restaurantSolrEntity.getName();
        this.targetId = restaurantSolrEntity.getId();
        this.type = PlaceType.food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }
}
