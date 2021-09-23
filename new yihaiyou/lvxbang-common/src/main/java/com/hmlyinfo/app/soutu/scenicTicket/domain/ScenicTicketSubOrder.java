package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPassenger;
import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScenicTicketSubOrder extends BaseEntity {

	public static final int STATUS_FAILED = -1;
	public static final int STATUS_ORDERED = 1;
	public static final int STATUS_PAID = 1;
	public static final int STATUS_CHECKED = 2;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long scenicTicketOrderId;

	/**
	 *
	 */
	private long scenicTicketId;

	/**
	 *
	 */
	private long scenicId;

	/**
	 *
	 */
	private int ticketType;

	/**
	 *
	 */
	private long relateOrderId;

	/**
	 *
	 */
	private int count;

	/**
	 *
	 */
	private String ticketDate;

	/**
	 *
	 */
	private double totalAmount;

	private int status;

	/**
	 * 可能需要的订票的passenger信息列表
	 */
	private transient List<QunarPassenger> passengerList = new ArrayList<QunarPassenger>();

	/**
	 * 其他门票供应商所需要的额外信息
	 */
	private transient Map<String, Object> ext;

	public void setScenicTicketOrderId(long scenicTicketOrderId) {
		this.scenicTicketOrderId = scenicTicketOrderId;
	}

	@JsonProperty
	public long getScenicTicketOrderId() {
		return scenicTicketOrderId;
	}

	public void setScenicTicketId(long scenicTicketId) {
		this.scenicTicketId = scenicTicketId;
	}

	@JsonProperty
	public long getScenicTicketId() {
		return scenicTicketId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}

	@JsonProperty
	public int getTicketType() {
		return ticketType;
	}

	public void setRelateOrderId(long relateOrderId) {
		this.relateOrderId = relateOrderId;
	}

	@JsonProperty
	public long getRelateOrderId() {
		return relateOrderId;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonProperty
	public int getCount() {
		return count;
	}

	@JsonProperty
	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@JsonProperty
	public double getTotalAmount() {
		return totalAmount;
	}

	@JsonProperty
	public List<QunarPassenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<QunarPassenger> passengerList) {
		this.passengerList = passengerList;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public Map<String, Object> getExt() {
		return ext;
	}

	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}


}
