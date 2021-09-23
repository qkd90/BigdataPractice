package com.data.data.hmly.service.line.entity;

import com.data.data.hmly.service.line.entity.enums.ContactType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dy on 2016/6/21.
 */
@Entity
@Table(name = "line_contact")
public class LineContact extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    /*
    `id` bigint(20) NOT NULL,
    `contact_type` enum('') DEFAULT NULL COMMENT '联系人类型',
    `contact_name` varchar(255) DEFAULT NULL COMMENT '联系人姓名',
    `contact_phone` varchar(255) DEFAULT NULL COMMENT '联系人手机',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `line_id` bigint(20) DEFAULT NULL COMMENT '线路编号',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    */

    private static final long serialVersionUID = -3872525549480231213L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "contact_type")
    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
