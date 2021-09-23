package com.data.data.hmly.service.order.vo;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;

/**
 * Created by guoshijie on 2015/10/14.
 */
public class OrderDataForm {

	private Order       order = new Order();
	private int         page;
	private int         rows;
	private boolean     asc;
	private String      orderProperty;
	private Product     product;
	private OrderDetail orderDetail = new OrderDetail();

	private OrderStatus confirmStatus;
	private OrderStatus payStatus;
	private String      operationDesc;
    private String 		filterOrderTypes;
	private String 		playDate;
	private String 		leaveDate;
	private String 		startTime;
	private String 		endTime;
	private String 		qryEndTime;
	private String 		playDateStartTime;
	private String 		playDateEndTime;
	private String 		source;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public org.hibernate.criterion.Order getOrderType() {
		if (asc) {
			return org.hibernate.criterion.Order.asc(orderProperty);
		} else {
			return org.hibernate.criterion.Order.desc(orderProperty);
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public OrderStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(OrderStatus payStatus) {
		this.payStatus = payStatus;
	}

	public OrderStatus getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(OrderStatus confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

    public String getFilterOrderTypes() {
        return filterOrderTypes;
    }

    public void setFilterOrderTypes(String filterOrderTypes) {
        this.filterOrderTypes = filterOrderTypes;
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		if (StringUtils.isNotBlank(startTime)) {
			this.order.setStartTime(DateUtils.toDate(startTime));
		}
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		if (StringUtils.isNotBlank(endTime)) {
			this.order.setEndTime(DateUtils.toDate(endTime));
		}
		this.endTime = endTime;
	}

	public String getPlayDate() {
		return playDate;
	}

	public void setPlayDate(String playDate) {
		if (StringUtils.isNotBlank(playDate)) {
			this.orderDetail.setPlayTime(DateUtils.toDate(playDate));
		}
		this.playDate = playDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		if (StringUtils.isNotBlank(leaveDate)) {
			this.orderDetail.setLeaveTime(DateUtils.toDate(leaveDate));
		}
		this.leaveDate = leaveDate;
	}

	public String getQryEndTime() {
		return qryEndTime;
	}

	public void setQryEndTime(String qryEndTime) {
		if (StringUtils.isNotBlank(qryEndTime)) {
			this.order.setEndTime(DateUtils.toDate(qryEndTime));
		}
		this.qryEndTime = qryEndTime;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getPlayDateStartTime() {
		return playDateStartTime;
	}

	public void setPlayDateStartTime(String playDateStartTime) {

		if (StringUtils.isNotBlank(playDateStartTime)) {
			this.orderDetail.setMinPlayDate(DateUtils.getStartDay(DateUtils.toDate(playDateStartTime), 0));
		}

		this.playDateStartTime = playDateStartTime;
	}

	public String getPlayDateEndTime() {
		return playDateEndTime;
	}

	public void setPlayDateEndTime(String playDateEndTime) {

		if (StringUtils.isNotBlank(playDateEndTime)) {
			this.orderDetail.setMaxPlayDate(DateUtils.getEndDay(DateUtils.toDate(playDateEndTime), 0));
		}

		this.playDateEndTime = playDateEndTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
