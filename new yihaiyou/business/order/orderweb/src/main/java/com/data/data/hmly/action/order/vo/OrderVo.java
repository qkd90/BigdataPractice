package com.data.data.hmly.action.order.vo;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAlias;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderReceiveType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by guoshijie on 2015/11/10.
 */
public class OrderVo {

	private long             id;
	private Long             orderDetailId;
	private String           orderNo;
	private User             user;
	private String           recName;
	private String           address;
	private String           mobile;
	private OrderPayType     payType;
	private OrderReceiveType receiveGoodType;
	private String           remark;
	private Date             modifyTime;
	private Date             createTime;
	private OrderStatus      status;
	private OrderDetailStatus detailStatus;
	private String           operationDesc;
	private String           orderType;
	private String           orderName;
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	private List<OrderAlias> orderAliases = new ArrayList<OrderAlias>();
	private Product          rootProduct;

	public OrderVo() {}

	public OrderVo(Order order) {
		this.id = order.getId();
		this.orderNo = order.getOrderNo();
		this.user = generateUser(order.getUser());
		this.recName = order.getRecName();
		this.address = order.getAddress();
		this.mobile = order.getMobile();
		this.payType = order.getPayType();
		this.receiveGoodType = order.getReceiveGoodType();
		this.remark = order.getRemark();
		this.modifyTime = order.getModifyTime();
		this.createTime = order.getCreateTime();
		this.status = order.getStatus();
		this.operationDesc = order.getOperationDesc();
		this.orderType = order.getOrderType().name();
//		this.orderName = generateOrderName(order.getOrderDetails());
//		this.orderType = generateOrderTypes(order.getOrderDetails());
		this.orderDetails = order.getOrderDetails();
		this.orderAliases = order.getOrderAliases();
		for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProduct() != null) {
                this.rootProduct = orderDetail.getProduct().getTopProduct();
            }
        }
    }

	public User generateUser(User user) {
		User result = new User();
		result.setId(user.getId());
		result.setUserName(user.getUserName());
		result.setMobile(user.getMobile());
		result.setAddress(user.getAddress());
		result.setEmail(user.getEmail());
		result.setUserType(user.getUserType());
		result.setQqNo(user.getQqNo());
		result.setStatus(user.getStatus());
		return result;
	}

	public String generateOrderName(List<OrderDetail> orderDetailList) {
		StringBuilder builder = new StringBuilder();
		for (OrderDetail orderDetail : orderDetailList) {
			builder.append(orderDetail.getProduct().getName()).append("/");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}

	public String generateOrderTypes(List<OrderDetail> orderDetailList) {
		Set<ProductType> set = new HashSet<ProductType>();
		for (OrderDetail orderDetail : orderDetailList) {
			set.add(orderDetail.getProduct().getProType());
		}
		StringBuilder builder = new StringBuilder();
		for (ProductType productType : set) {
			builder.append(productType.name()).append("/");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public OrderPayType getPayType() {
		return payType;
	}

	public void setPayType(OrderPayType payType) {
		this.payType = payType;
	}

	public OrderReceiveType getReceiveGoodType() {
		return receiveGoodType;
	}

	public void setReceiveGoodType(OrderReceiveType receiveGoodType) {
		this.receiveGoodType = receiveGoodType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public OrderDetailStatus getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(OrderDetailStatus detailStatus) {
		this.detailStatus = detailStatus;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	//	public List<OrderDetailVo> getOrderDetails() {
//		return orderDetails;
//	}
//
//	public void fillOrderDetails(List<OrderDetail> orderDetailList) {
//		for (OrderDetail orderDetail : orderDetailList) {
//			this.orderDetails.add(createOrderDetail(orderDetail));
//		}
//	}


	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<OrderAlias> getOrderAliases() {
		return orderAliases;
	}

	public void setOrderAliases(List<OrderAlias> orderAliases) {
		this.orderAliases = orderAliases;
	}

	public Product getRootProduct() {
		return rootProduct;
	}

	public void setRootProduct(Product rootProduct) {
		this.rootProduct = rootProduct;
	}

	private OrderDetailVo createOrderDetail(OrderDetail orderDetail) {
		OrderDetailVo result = new OrderDetailVo();
		result.setUnitPrice(orderDetail.getUnitPrice());
		result.setNum(orderDetail.getNum());
		result.setTotalPrice(orderDetail.getTotalPrice());
		result.setPromDiscount(orderDetail.getPromDiscount());
		result.setDiscountPrice(orderDetail.getDiscountPrice());
		result.setYuePay(orderDetail.getYuePay());
		result.setOnlinePay(orderDetail.getOnlinePay());
		result.setCouponPay(orderDetail.getCouponPay());
		result.setJifenPay(orderDetail.getJifenPay());
		result.setFinalPrice(orderDetail.getFinalPrice());
		result.setPlayDate(orderDetail.getPlayDate());
		result.setProductType(orderDetail.getProductType());
		result.setCostId(orderDetail.getCostId());
		result.setCostType(orderDetail.getCostType());
		result.setCostName(orderDetail.getCostName());
		result.setPriceType(orderDetail.getPriceType());
		return result;
	}
}
