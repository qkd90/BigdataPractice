package com.data.data.hmly.action.yihaiyou.request;

import com.data.data.hmly.service.impression.entity.PlaceType;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class ImpressionUpdateRequest {
    private Long id;
    private String placeName;
    private Long targetId;
    private PlaceType placeType;
    private String content;
    private String cover;
    private Integer type;
    private List<Photo> gallery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Photo> getGallery() {
        return gallery;
    }

    public void setGallery(List<Photo> gallery) {
        this.gallery = gallery;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
