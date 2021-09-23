package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by guoshijie on 2015/11/4.
 */

@Entity
@Table(name = "scenic_statistics")
public class ScenicStatistics extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@Column(name = "id")
	private Long id;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn
	private ScenicInfo scenicInfo;
	@Column(name = "comment_num")
	private Integer    commentNum;            //评论数
	@Column(name = "going_count")
	private Integer    goingCount;            //要去的人
	@Column(name = "came_count")
	private Integer    cameCount;                //去过的人
	@Column(name = "modify_time")
	private Date       modifyTime;
	@Column(name = "create_time")
	private Date       createTime;

	@Column(name = "satisfaction")
	private Integer satisfaction;
	@Column(name = "order_num")
	private Integer orderNum;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ScenicInfo getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(ScenicInfo scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getGoingCount() {
		return goingCount;
	}

	public void setGoingCount(Integer goingCount) {
		this.goingCount = goingCount;
	}

	public Integer getCameCount() {
		return cameCount;
	}

	public void setCameCount(Integer cameCount) {
		this.cameCount = cameCount;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(Integer satisfaction) {
		this.satisfaction = satisfaction;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
