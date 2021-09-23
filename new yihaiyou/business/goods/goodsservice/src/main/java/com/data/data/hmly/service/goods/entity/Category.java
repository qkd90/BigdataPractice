package com.data.data.hmly.service.goods.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.goods.entity.enums.CategoryStatus;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/14.
 */

@javax.persistence.Entity
@Table(name = "category")
public class Category extends Entity implements Serializable {

    private static final long serialVersionUID = -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "PARENTID")
    private long parentId;

    @Column(name = "SORTORDER")
    private int sortOrder;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

//    @Column(name = "TYPE")
//    @Enumerated(EnumType.STRING)
//    private LinecategoryType type;

    @ManyToOne
    @JoinColumn(name = "type")
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
    private User user;

    @Column(name = "ADDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "CATEGORYIMGURL")
    private String categoryImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unitId")
    private SysUnit sysUnit;

    @Transient
    private List<Category> children;

    /**
     * @return
     */

    public long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }


    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }


    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


    public String getCategoryImgUrl() {
        return categoryImgUrl;
    }

    public void setCategoryImgUrl(String categoryImgUrl) {
        this.categoryImgUrl = categoryImgUrl;
    }

    public SysUnit getSysUnit() {
        return sysUnit;
    }

    public void setSysUnit(SysUnit sysUnit) {
        this.sysUnit = sysUnit;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

}
