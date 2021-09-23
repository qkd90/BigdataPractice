package com.data.data.hmly.action.mobile.request;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class GuideTripUpdateRequest {
    private Long tripId;
    private Long scenicId;
    private String scenicName;
    private Integer tripType;
    private String exa;
    private Integer sort;
    private List<Photo> addPhotos;
    private List<Long> removePhotos;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public Integer getTripType() {
        return tripType;
    }

    public void setTripType(Integer tripType) {
        this.tripType = tripType;
    }

    public String getExa() {
        return exa;
    }

    public void setExa(String exa) {
        this.exa = exa;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Photo> getAddPhotos() {
        return addPhotos;
    }

    public void setAddPhotos(List<Photo> addPhotos) {
        this.addPhotos = addPhotos;
    }

    public List<Long> getRemovePhotos() {
        return removePhotos;
    }

    public void setRemovePhotos(List<Long> removePhotos) {
        this.removePhotos = removePhotos;
    }
}
