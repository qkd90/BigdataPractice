package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.goods.entity.Category;

import java.util.List;

/**
 * Created by huangpeijie on 2016-09-26,0026.
 */
public class CruiseShipCategoryResponse {
    private Long id;
    private String name;
    private Long parentId;
    private List<CruiseShipCategoryResponse> children;

    public CruiseShipCategoryResponse() {
    }

    public CruiseShipCategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentId = category.getParentId();
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<CruiseShipCategoryResponse> getChildren() {
        return children;
    }

    public void setChildren(List<CruiseShipCategoryResponse> children) {
        this.children = children;
    }
}
