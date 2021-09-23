package com.data.data.hmly.service.common.entity;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 已废弃，由com.data.data.hmly.service.common.entity.ProValidCode取代
 * Created by guoshijie on 2015/11/9.
 */
@Entity
@Table(name = "productvalidatecode")
@Deprecated
public class ProductValidateCode  extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -9215023448601457011L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long    id;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	@Column(name = "code")
	private String  code;

	@Column(name = "used")
	private Integer used;

	@Column(name = "updateTime")
	private Date    updateTime;

	@Column(name = "createTime")
	private Date    createTime;

	@Column(name = "buyerName")
	private String  buyerName;

	@Column(name = "buyerMobile")
	private String  buyerMobile;

	@ManyToOne
	@JoinColumn(name = "buyerId")
	private User    buyer;

	@Column(name = "orderNo")
	private String orderNo;

	@Column(name = "ticketNo")
	private String ticketNo;

	@Column(name = "orderInitCount")
	private Integer orderInitCount;

	@Column(name = "orderCount")
	private Integer orderCount;

	@Column(name = "refundCount")
	private Integer refundCount;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "scenicid")
	private Long scenicId;

	@Column(name = "ticket_id")
	private Long ticketId;

	@Column(name = "supplierId")
	private Long supplierId;

	@Column(name = "supplierName")
	private String supplierName;

	@ManyToOne
	@JoinColumn(name = "validateBy")
	private SysUser validateBy;

	@Column(name = "relateOrderId")
	private String relateOrderId;

	@Column(name = "ticketName")
	private String ticketName;

	@Column(name = "valid_startTime")
	private Date validStartTime;

	@Column(name = "valid_endTime")
	private Date validEndTime;

	@OneToMany(mappedBy = "productValidateCode", fetch = FetchType.LAZY)
	protected Set<ProductValidateRecord> productValidateRecords;

    // 页面字段
    @Transient
    private Date    updateTimeStart;

    @Transient
    private Date    updateTimeEnd;

	@Transient
	private String   typeFlag;

	@Transient
	private String   productName;

	@Transient
	private List<ProductValidateCode> productValidateCodeList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public SysUser getValidateBy() {
		return validateBy;
	}

	public void setValidateBy(SysUser validateBy) {
		this.validateBy = validateBy;
	}


	public String getRelateOrderId() {
		return relateOrderId;
	}

	public void setRelateOrderId(String relateOrderId) {
		this.relateOrderId = relateOrderId;
	}


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public String getValidateTimeStr() {
		if (updateTime != null) {
			return DateUtils.format(updateTime, "M.d HH:mm");
		}
		return "";
	}

    public Date getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(Date updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public Date getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

	public Integer getOrderInitCount() {
		return orderInitCount;
	}

	public void setOrderInitCount(Integer orderInitCount) {
		this.orderInitCount = orderInitCount;
	}

	public Integer getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	public Set<ProductValidateRecord> getProductValidateRecords() {
		return productValidateRecords;
	}

	public void setProductValidateRecords(Set<ProductValidateRecord> productValidateRecords) {
		this.productValidateRecords = productValidateRecords;
	}

    /**
     * 根据数量字段同步验证码状态：1已使用，0未使用，-1无效
     * 1.有效期不为空且已过期：如果可验票数为零且有使用，状态为已使用；否则，状态为无效
     * 1.可验票数为零时：如果全部退款，状态为无效；如果有使用，状态为已使用
     * 2.可验票数不为零时：状态为未使用
     * @param endTime
     */
    public void syncUsed(Date endTime) {
        if (endTime != null) {
            Long endLong = DateUtils.getDateDiffLong(endTime, new Date());
			if (endLong > 0 || endLong == 0) {
				this.setUsed(1);
			} else {
				this.setUsed(-1);
			}

        }
    }

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Date getValidStartTime() {
		return validStartTime;
	}

	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ProductValidateCode> getProductValidateCodeList() {
		return productValidateCodeList;
	}

	public void setProductValidateCodeList(List<ProductValidateCode> productValidateCodeList) {
		this.productValidateCodeList = productValidateCodeList;
	}
}
