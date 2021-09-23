package com.data.data.hmly.service.nctripticket.entity;

import com.data.data.hmly.service.ctripcommon.enums.RowStatus;
import com.data.data.hmly.service.ctripcommon.enums.TagTargetType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nctrip_display_tag_group")
public class CtripDisplayTagGroup extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long    id;	              // 展示tag分组ID
    @Column(name = "targetId", length = 20)
    private Long    targetId;	         // 目标标签ID
    @Column(name = "targetType")
    @Enumerated(EnumType.STRING)
    private TagTargetType targetType;       // 目标类型
    @Column(name = "groupKey", length = 256)
    private String  groupKey;		          //	展示tag分组Key
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;

    @Transient
    private String  key;		          //	展示tag分组Key
    @Transient
    private List<CtripDisplayTag> displayTagList;		// 展示tag集合

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public TagTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TagTargetType targetType) {
        this.targetType = targetType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    public List<CtripDisplayTag> getDisplayTagList() {
        return displayTagList;
    }

    public void setDisplayTagList(List<CtripDisplayTag> displayTagList) {
        this.displayTagList = displayTagList;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}
