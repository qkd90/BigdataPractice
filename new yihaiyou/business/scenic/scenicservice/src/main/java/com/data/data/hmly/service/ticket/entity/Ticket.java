package com.data.data.hmly.service.ticket.entity;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ticket")
@PrimaryKeyJoinColumn(name = "productId")
public class Ticket extends Product implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scenicInfoId")
	private ScenicInfo scenicInfo;
	@Column(name = "ticketName", length = 20)
	private String ticketName;                                    // 景点名称
	@Column(name = "ticketType", length = 20)
	@Enumerated(EnumType.STRING)
	private TicketType ticketType;                                    // 门票类型
	@Column(name = "sceAji", length = 20)
	private String sceAji;                                        // 景区A级
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startTime", length = 20)
	private Date startTime;                                    // 开始时间
	@Column(name = "address")
	private String address;                                    // 详细地址
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "preOrderDay", length = 20)
	private Integer preOrderDay;                                // 提前预定时间
	@Column(name = "ticketImgUrl", length = 20)
	private String ticketImgUrl;                                //  景点图片描述
//	@Column(name = "showOrder", length = 20)
//	private Integer showOrder;                                    // 排序
	@Column(name = "agreeRule", length = 20)
	private String agreeRule;                                    // 选择同意入园规则
	@Column(name = "payway", length = 20)
	private String payway;                                        // 付款方式
	@Column(name = "orderCounts", length = 11)
	private Integer orderCounts;                                // 订单数
	@Column(name = "popCounts", length = 20)
	private Integer popCounts;                                    // 人气
	@Column(name = "validOrderDay", length = 20)
	private Integer validOrderDay;                                // 有效天数
	@Column(name = "orderConfirm", length = 20)
	private String orderConfirm;                                // 是否需要确认傅
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addTime", length = 20)
	private Date addTime;                                    // 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	private Date updateTime;                                    // 更新时间
	@OneToMany
	@JoinColumn(name = "ticketId")
	private Set<TicketPrice> ticketPriceSet;

	//页面字段

	@Transient
	private List<TicketPrice> ticketPriceList;

	@Transient
	private String agentTicket;

	@Transient
	private String sourceStr;

	@Transient
	private String suplierName;

	@Transient
	private String contactName;



	@Transient
	private boolean agent;

    @Transient
    private List<TicketType> filterTicketTypeList;

	@Transient
	private List<TicketType> includeTicketTypeList;

	@Transient
	private List<ProductStatus> includeTicketStatusList;

	public Ticket() {

	}

	public ScenicInfo getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(ScenicInfo scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public String getSceAji() {
		return sceAji;
	}

	public void setSceAji(String sceAji) {
		this.sceAji = sceAji;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPreOrderDay() {
		return preOrderDay;
	}

	public void setPreOrderDay(Integer preOrderDay) {
		this.preOrderDay = preOrderDay;
	}

	public String getTicketImgUrl() {
		return ticketImgUrl;
	}

	public void setTicketImgUrl(String ticketImgUrl) {
		this.ticketImgUrl = ticketImgUrl;
	}

//	public Integer getShowOrder() {
//		return showOrder;
//	}
//
//	public void setShowOrder(Integer showOrder) {
//		this.showOrder = showOrder;
//	}

	public String getAgreeRule() {
		return agreeRule;
	}

	public void setAgreeRule(String agreeRule) {
		this.agreeRule = agreeRule;
	}

	public String getPayway() {
		return payway;
	}


	public String getOrderConfirm() {
		return orderConfirm;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getOrderCounts() {
		return orderCounts;
	}

	public void setOrderCounts(Integer orderCounts) {
		this.orderCounts = orderCounts;
	}

	public Integer getPopCounts() {
		return popCounts;
	}

	public void setPopCounts(Integer popCounts) {
		this.popCounts = popCounts;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public void setOrderConfirm(String orderConfirm) {
		this.orderConfirm = orderConfirm;
	}

	public Set<TicketPrice> getTicketPriceSet() {
		return ticketPriceSet;
	}

	public void setTicketPriceSet(Set<TicketPrice> ticketPriceSet) {
		this.ticketPriceSet = ticketPriceSet;
	}

	public Integer getValidOrderDay() {
		return validOrderDay;
	}

	public void setValidOrderDay(Integer validOrderDay) {
		this.validOrderDay = validOrderDay;
	}

	public String getAgentTicket() {
		return agentTicket;
	}

	public void setAgentTicket(String agentTicket) {
		this.agentTicket = agentTicket;
	}

	public boolean isAgent() {
		return agent;

	}

	public void setAgent(boolean agent) {
		this.agent = agent;
	}

    public List<TicketType> getFilterTicketTypeList() {
        return filterTicketTypeList;
    }

    public void setFilterTicketTypeList(List<TicketType> filterTicketTypeList) {
        this.filterTicketTypeList = filterTicketTypeList;
    }

    public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}


	public String getSuplierName() {
		return suplierName;
	}

	public void setSuplierName(String suplierName) {
		this.suplierName = suplierName;
	}


	public List<TicketType> getIncludeTicketTypeList() {
		return includeTicketTypeList;
	}

	public void setIncludeTicketTypeList(List<TicketType> includeTicketTypeList) {
		this.includeTicketTypeList = includeTicketTypeList;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public TicketType fmtTicketType(String ticketTypeStr) {

		TicketType ticketTypetemp = null;

		if ("scenic".equals(ticketTypeStr)) {
			ticketTypetemp = TicketType.scenic;
		}
		if ("sailboat".equals(ticketTypeStr)) {
			ticketTypetemp = TicketType.sailboat;
		}
		if ("shows".equals(ticketTypeStr)) {
			ticketTypetemp = TicketType.shows;
		}
        if ("yacht".equals(ticketTypeStr)) {
            ticketTypetemp = TicketType.yacht;
        }
		return ticketTypetemp;
	}

	public List<TicketPrice> getTicketPriceList() {
		return ticketPriceList;
	}

	public void setTicketPriceList(List<TicketPrice> ticketPriceList) {
		this.ticketPriceList = ticketPriceList;
	}

	public List<ProductStatus> getIncludeTicketStatusList() {
		return includeTicketStatusList;
	}

	public void setIncludeTicketStatusList(List<ProductStatus> includeTicketStatusList) {
		this.includeTicketStatusList = includeTicketStatusList;
	}
}
