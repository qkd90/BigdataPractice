package com.data.data.hmly.service.comment.entity;

import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/3.
 */
@Entity(name = "comment")
public class Comment extends com.framework.hibernate.util.Entity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
//	@Column(name = "userId")
//	private Long userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	@Column(name = "targetId")
	private Long targetId;
	@Column(name = "priceId")
	private Long priceId;
	@Column(name = "content")
	private String content;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ProductType type;
	@Column(name = "createTime")
	private Date createTime;
	@Column(name = "repliedId")
	private Long repliedId;
	@Column(name = "orderNo")
	private String orderNo;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "repliedId", referencedColumnName = "id")
	private List<Comment> comments;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "commentId", referencedColumnName = "id")
	private List<CommentScore> commentScores;
	@OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
	private List<CommentPhoto> commentPhotos;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CommentStatus status;

	@Column(name = "replyStatus")
	private Integer replyStatus;

	@Column(name = "orderId")
	private Long orderId;
	@Column(name = "orderDetailId")
	private Long orderDetailId;

	@Column(name = "nickName")
	private String nickName;
	@Transient
    private Boolean hasPic;
    @Transient
    private Long scoreTypeId;
    @Transient
    private Integer minScore;
    @Transient
    private Integer maxScore;
	@Transient
	private Float avgScore;
	@Transient
	private List<Long> targetIdList;
	@Transient
	private String targetName;
	@Transient
	private List<Long> productIdList;
	@Transient
	private Long companyUnitId;
	@Transient
	private Long productId;
	@Transient
	private String userName;

	@Transient
	private String head;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public CommentStatus getStatus() {
		return status;
	}

	public void setStatus(CommentStatus status) {
		this.status = status;
	}

	public Long getRepliedId() {
		return repliedId;
	}

	public void setRepliedId(Long repliedId) {
		this.repliedId = repliedId;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<CommentScore> getCommentScores() {
		return commentScores;
	}

	public void setCommentScores(List<CommentScore> commentScores) {
		this.commentScores = commentScores;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<CommentPhoto> getCommentPhotos() {
		return commentPhotos;
	}

	public void setCommentPhotos(List<CommentPhoto> commentPhotos) {
		this.commentPhotos = commentPhotos;
	}

    public Boolean getHasPic() {
        return hasPic;
    }

    public void setHasPic(Boolean hasPic) {
        this.hasPic = hasPic;
    }

    public Long getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCreateTimeStr() {
		if (this.createTime != null) {
			return DateUtils.format(this.createTime, "yyyy-MM-dd");
		}
		return "";
	}

	public List<Long> getTargetIdList() {
		return targetIdList;
	}

	public void setTargetIdList(List<Long> targetIdList) {
		this.targetIdList = targetIdList;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public List<Long> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Long> productIdList) {
		this.productIdList = productIdList;
	}

	public Long getCompanyUnitId() {
		return companyUnitId;
	}

	public void setCompanyUnitId(Long companyUnitId) {
		this.companyUnitId = companyUnitId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Integer replyStatus) {
		this.replyStatus = replyStatus;
	}

	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
}
