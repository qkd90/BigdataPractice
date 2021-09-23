package com.data.data.hmly.service.ctripcommon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by caiys on 2016/1/26.
 */
@Entity
@Table(name = "nctrip_api_log")
public class CtripApiLog extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long	id;
    @Column(name = "uuid", length = 128)
    private String uuid;        // 唯一标识，用于问题跟踪
    @Column(name = "handleIds", length = 256)
    private String handleIds;    // 处理的标识集合，逗号分隔
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "execTime", length = 19)
    private Date execTime;
    @Column(name = "icode", length = 256)
    private String icode;
    @Column(name = "description", length = 256)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "nextTime", length = 19)
    private Date nextTime;
    @Column(name = "paramJson", length = 512)
    private String paramJson;
    @Column(name = "success")
    private Boolean success;
    @Column(name = "errorCode", length = 256)
    private String errorCode;
    @Column(name = "errorMessage", length = 256)
    private String errorMessage;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "handleTime", length = 19)
    private Date handleTime;    // 处理时间

    // 页面字段
    @Transient
    private Date execTimeStart;
    @Transient
    private Date execTimeEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public String getIcode() {
        return icode;
    }

    public void setIcode(String icode) {
        this.icode = icode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHandleIds() {
        return handleIds;
    }

    public void setHandleIds(String handleIds) {
        this.handleIds = handleIds;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getExecTimeStart() {
        return execTimeStart;
    }

    public void setExecTimeStart(Date execTimeStart) {
        this.execTimeStart = execTimeStart;
    }

    public Date getExecTimeEnd() {
        return execTimeEnd;
    }

    public void setExecTimeEnd(Date execTimeEnd) {
        this.execTimeEnd = execTimeEnd;
    }
}
