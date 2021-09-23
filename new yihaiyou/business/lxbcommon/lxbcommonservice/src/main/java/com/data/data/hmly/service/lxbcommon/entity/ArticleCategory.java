package com.data.data.hmly.service.lxbcommon.entity;

import com.data.data.hmly.service.lxbcommon.entity.enums.ArticleCategoryStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/4/13.
 */
@Entity
@Table(name = "article_category")
public class ArticleCategory extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "level")
    private Integer level;

    @Column(name = "status")
    private ArticleCategoryStatus status;

    @ManyToOne
    @JoinColumn(name = "parent_category")
    private ArticleCategory parentCategory;

    @ManyToOne
    @JoinColumn(name = "grand_category")
    private ArticleCategory grandCategory;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private List<ArticleCategory> children;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ArticleCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleCategoryStatus status) {
        this.status = status;
    }

    public ArticleCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ArticleCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ArticleCategory getGrandCategory() {
        return grandCategory;
    }

    public void setGrandCategory(ArticleCategory grandCategory) {
        this.grandCategory = grandCategory;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ArticleCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleCategory> children) {
        this.children = children;
    }
}
