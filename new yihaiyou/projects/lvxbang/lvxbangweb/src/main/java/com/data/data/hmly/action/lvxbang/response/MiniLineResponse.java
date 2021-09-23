package com.data.data.hmly.action.lvxbang.response;

import com.data.data.hmly.service.line.entity.Line;

/**
 * Created by huangpeijie on 2016-08-25,0025.
 */
public class MiniLineResponse {
    private Long id;
    private String name;
    private String appendTitle;
    private Float price;

    public MiniLineResponse() {
    }

    public MiniLineResponse(Line line) {
        this.id = line.getId();
        this.name = line.getName();
        this.appendTitle = line.getAppendTitle();
        this.price = line.getPrice();
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

    public String getAppendTitle() {
        return appendTitle;
    }

    public void setAppendTitle(String appendTitle) {
        this.appendTitle = appendTitle;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
