package com.data.data.hmly.service.other.entity;

// Generated 2015-12-22 10:51:28 by Hibernate Tools 3.4.0.CR1

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.other.enums.MsgType;
import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * OtherMessage generated by hbm2java
 */
@Entity
@Table(name = "other_message")
public class OtherMessage extends com.framework.hibernate.util.Entity {

	private static final long serialVersionUID = 456903894164849013L;
	private Long id;
	private MsgType msgType;
	private String title;
	private String content;
	private Member fromUser;
	private Member toUser;
    private Long companyUnitId;
	private Date createTime;
	private Boolean readFlag;
	private Date readTime;
	private Boolean deleteFlag;
	private Long commentTargetId;
	private ProductType commentTargetType;
	private Long messageId;
	
	// 页面字段
	private String fromUserName;
	private String fromUserHead;

	public OtherMessage() {
	}
	

	public OtherMessage(Long id, MsgType msgType, String title, String content,
			Date createTime, Boolean readFlag, Long commentTargetId, Long messageId,
			ProductType commentTargetType, String fromUserName,
			String fromUserHead) {
		this.id = id;
		this.msgType = msgType;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.readFlag = readFlag;
		this.commentTargetId = commentTargetId;
		this.commentTargetType = commentTargetType;
		this.fromUserName = fromUserName;
		this.fromUserHead = fromUserHead;
        this.messageId = messageId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "msgType", nullable = false)
	@Enumerated(EnumType.STRING)
	public MsgType getMsgType() {
		return this.msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false, length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne
	@JoinColumn(name = "fromUserId")
	public Member getFromUser() {
		return fromUser;
	}

	public void setFromUser(Member fromUser) {
		this.fromUser = fromUser;
	}

	@ManyToOne
	@JoinColumn(name = "toUserId")
	public Member getToUser() {
		return toUser;
	}

	public void setToUser(Member toUser) {
		this.toUser = toUser;
	}

    @Column(name = "company_unit_id")
    public Long getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(Long companyUnitId) {
        this.companyUnitId = companyUnitId;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "readFlag", nullable = false)
	public Boolean getReadFlag() {
		return this.readFlag;
	}

	public void setReadFlag(Boolean readFlag) {
		this.readFlag = readFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "readTime", length = 19)
	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@Column(name = "deleteFlag", nullable = false)
	public Boolean getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "commentTargetId")
	public Long getCommentTargetId() {
		return commentTargetId;
	}

	public void setCommentTargetId(Long commentTargetId) {
		this.commentTargetId = commentTargetId;
	}

	@Column(name = "commentTargetType")
	@Enumerated(EnumType.STRING)
	public ProductType getCommentTargetType() {
		return commentTargetType;
	}

	public void setCommentTargetType(ProductType commentTargetType) {
		this.commentTargetType = commentTargetType;
	}

	@Column(name = "messageId")
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Transient
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	@Transient
	public String getFromUserHead() {
		return fromUserHead;
	}

	public void setFromUserHead(String fromUserHead) {
		this.fromUserHead = fromUserHead;
	}
	
	@Transient
	public String getCreateTimeStr() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm");
	}

}
