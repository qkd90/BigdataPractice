package com.data.data.hmly.service.lxbcommon.entity;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.entity.enums.FeedBackStatus;

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
import java.util.Date;

/**
 * Created by zzl on 2016/4/15.
 */
@Entity
@Table(name = "feed_back")
public class Feedback extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "contact")
    private String contact;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Member creator;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private SysUser replier;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "reply_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FeedBackStatus status;

    @Column(name = "del_flag")
    private Integer delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }

    public SysUser getReplier() {
        return replier;
    }

    public void setReplier(SysUser replier) {
        this.replier = replier;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public FeedBackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedBackStatus status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
