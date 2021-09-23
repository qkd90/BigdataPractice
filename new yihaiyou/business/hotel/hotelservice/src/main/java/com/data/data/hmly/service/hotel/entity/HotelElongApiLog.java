package com.data.data.hmly.service.hotel.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2016/5/27.
 */
//@Entity
//@Table(name = "hotel_elong_api_log")
public class HotelElongApiLog extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long	id;
    @Column(name = "uuid", length = 128)
    private String uuid;        // 唯一标识，用于问题跟踪
    @Column(name = "hotelId")
    private Long hotelId;
    @Column(name = "statusLine", length = 512)
    private String statusLine;
    @Column(name = "apiCode", length = 256)
    private String apiCode;
    @Column(name = "description", length = 256)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "execTime", length = 19)
    private Date execTime;
    @Column(name = "errorCode", length = 256)
    private String errorCode;
    @Column(name = "errorMessage", length = 256)
    private String errorMessage;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "handleTime", length = 19)
    private Date handleTime;    // 处理时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
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

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}
