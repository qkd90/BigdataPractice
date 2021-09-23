package com.data.data.hmly.service.wechat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.framework.hibernate.util.Entity;
import com.gson.inf.EventTypes;
import com.gson.inf.MsgTypes;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_receive_msg_log")
public class WechatReceiveMsgLog extends Entity implements Serializable {

    private static final long serialVersionUID = -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

	@Enumerated(EnumType.STRING)
    @Column(name = "msgType")
    private MsgTypes msgType;

	@Enumerated(EnumType.STRING)
    @Column(name = "event")
    private EventTypes event;

    @Column(name = "eventKey")
    private String eventKey;

    @Column(name = "type")
    private Long type;

    @Column(name = "openId")
    private String openId;

    @Column(name = "originalId")
    private String originalId;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "exception")
    private String exception;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    @Column(name = "msgId")
    private Long msgId;


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getException() {
        return exception;
    }

    public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setException(String exception) {
        this.exception = exception;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public MsgTypes getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgTypes msgType) {
		this.msgType = msgType;
	}

	public EventTypes getEvent() {
		return event;
	}

	public void setEvent(EventTypes event) {
		this.event = event;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

}
