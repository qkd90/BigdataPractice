package com.data.data.hmly.action.yihaiyou.response;

/**
 * Created by dy on 2016/7/19.
 */
public class LineListResponse {

    private Long id;
    private String cover;
    private String name;
    private String appendTitle;
    private String tuanQi;
    private Float minPrice;
    private Integer orderSum;
    private Integer satisfaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppendTitle() {
        return appendTitle;
    }

    public void setAppendTitle(String appendTitle) {
        this.appendTitle = appendTitle;
    }

    public String getTuanQi() {
        return tuanQi;
    }

    public void setTuanQi(String tuanQi) {
        this.tuanQi = tuanQi;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Integer orderSum) {
        this.orderSum = orderSum;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }
}
