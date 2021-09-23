package com.data.data.hmly.service.hotel.entity.vo;

import java.util.List;

/**
 * Created by dy on 2016/6/3.
 */
public class AmenititesTree {

    private Integer id;
    private String name;
    private Integer parentId;
    private List<AmenititesTree> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<AmenititesTree> getChildren() {
        return children;
    }

    public void setChildren(List<AmenititesTree> children) {
        this.children = children;
    }
}

