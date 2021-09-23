package com.data.data.hmly.service.line.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/6/21.
 */
@Entity
@Table(name = "line_departure")
public class LineDeparture extends com.framework.hibernate.util.Entity implements Serializable {
/*

    CREATE TABLE `line_departure` (
        `id` bigint(20) NOT NULL,
        `line_id` bigint(20) DEFAULT NULL,
        `sign_info` varchar(255) DEFAULT NULL COMMENT '举旗标识',
        `emergency_contact` varchar(255) DEFAULT NULL COMMENT '紧急联系人',
        `emergency_phone` varchar(255) DEFAULT NULL COMMENT '紧急联系电话',
        `auto_send_info` tinyint(10) DEFAULT NULL COMMENT '可自动发送出团通知',
        `has_transfer` int(11) DEFAULT NULL COMMENT '是否有接送机（站）--》0:不提供；1：提供；-1：有条件提供',
        `transfer_desc` varchar(500) DEFAULT NULL COMMENT '接送机备注信息',
        `departure_desc` varchar(500) DEFAULT NULL COMMENT '线路信息备注',
        `create_time` datetime DEFAULT NULL,
        `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/

    private static final long serialVersionUID = -3872525549480231213L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "sign_info")
    private String signInfo;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @Column(name = "auto_send_info")
    private Boolean autoSendInfo;

    @Column(name = "has_transfer")
    private Integer hasTransfer;

    @Column(name = "transfer_desc")
    private String transferDesc;

    @Column(name = "departure_desc")
    private String departureDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @Transient
    private List<LineDepartureInfo> lineDepartureInfos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignInfo() {
        return signInfo;
    }

    public void setSignInfo(String signInfo) {
        this.signInfo = signInfo;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public Boolean getAutoSendInfo() {
        return autoSendInfo;
    }

    public void setAutoSendInfo(Boolean autoSendInfo) {
        this.autoSendInfo = autoSendInfo;
    }

    public Integer getHasTransfer() {
        return hasTransfer;
    }

    public void setHasTransfer(Integer hasTransfer) {
        this.hasTransfer = hasTransfer;
    }

    public String getTransferDesc() {
        return transferDesc;
    }

    public void setTransferDesc(String transferDesc) {
        this.transferDesc = transferDesc;
    }

    public String getDepartureDesc() {
        return departureDesc;
    }

    public void setDepartureDesc(String departureDesc) {
        this.departureDesc = departureDesc;
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

    public List<LineDepartureInfo> getLineDepartureInfos() {
        return lineDepartureInfos;
    }

    public void setLineDepartureInfos(List<LineDepartureInfo> lineDepartureInfos) {
        this.lineDepartureInfos = lineDepartureInfos;
    }
}
