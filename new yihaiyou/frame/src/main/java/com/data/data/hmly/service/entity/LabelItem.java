package com.data.data.hmly.service.entity;


import com.data.data.hmly.enums.TargetType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "label_item")
public class LabelItem extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "labelId")
	private Label label;	//标签Id
	@Column(name = "targetId")
	private Long targetId;		//对象Id
    @Column(name = "targetType")
    @Enumerated(EnumType.STRING)
    private TargetType targetType;
    @Column(name = "sort")
    private Integer order;
    @Column(name = "createTime")
	private Date createTime;

    @Transient
    private List<Long> labelIds;
	@Transient
	private String targetName;
    @Transient
    private Date targetTime;
    @Transient
    private Long labelId;
    @Transient
    private Float targetPrice;
    @Transient
    private String targetCover;
    @Transient
    private String targetSource;

    public LabelItem() {
    }

    public LabelItem(Long id, Long labelId, Long targetId, TargetType targetType, String targetName, Date targetTime) {
        this.id = id;
        this.labelId = labelId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.targetTime = targetTime;
        this.targetName = targetName;
    }

    public LabelItem(Long id, Long labelId, Long targetId, TargetType targetType, String targetName) {
        this.id = id;
        this.labelId = labelId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.targetName = targetName;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date targetTime) {
        this.targetTime = targetTime;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(Float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getTargetCover() {
        return targetCover;
    }

    public void setTargetCover(String targetCover) {
        this.targetCover = targetCover;
    }

    public String getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(String targetSource) {
        this.targetSource = targetSource;
    }
}
