package com.data.data.hmly.action.goods.vo;

import java.util.List;

/**
 * Created by zzl on 2016/4/21.
 * 树形下拉框分类数据
 */
public class CategoryTreeVo {

    private Long id;
    private String text;

    private List<CategoryTreeVo> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CategoryTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryTreeVo> children) {
        this.children = children;
    }
}
