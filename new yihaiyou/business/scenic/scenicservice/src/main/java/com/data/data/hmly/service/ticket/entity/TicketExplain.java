package com.data.data.hmly.service.ticket.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketexplain")
public class TicketExplain extends com.framework.hibernate.util.Entity implements Serializable {

	private static final long	serialVersionUID	= 1L;
	
	private Long				id;									// 主键ID
	private String				enterDesc;							// 入园说明
	private String 				rule;								// 退改规则
	private String 				privilege;							// 优惠政策
	private String 				proInfo;							// 产品详情
	private String 				openTime;							// 营业时间
	private String 				tips;								// 小贴士
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="ticketId")
//	private ticket 				ticket;
	private Long 				ticketId;
	
	public TicketExplain() {
		super();
	}





	public TicketExplain(Long id, String enterDesc, String rule,
			String privilege, String proInfo, Long ticketId) {
		
		this.id = id;
		this.enterDesc = enterDesc;
		this.rule = rule;
		this.privilege = privilege;
		this.proInfo = proInfo;
		this.ticketId = ticketId;

	}




	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "enterDesc", length = 19)
	public String getEnterDesc() {
		return enterDesc;
	}

	public void setEnterDesc(String enterDesc) {
		this.enterDesc = enterDesc;
	}
	@Column(name = "rule", length = 19)
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	@Column(name = "privilege", length = 19)
	public String getPrivilege() {
		return privilege;
	}


	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	@Column(name = "proInfo", length = 19)
	public String getProInfo() {
		return proInfo;
	}

	public void setProInfo(String proInfo) {
		this.proInfo = proInfo;
	}




	@Column(name = "ticketId", length = 19)
	public Long getTicketId() {
		return ticketId;
	}





	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}


	@Column(name = "openTime")
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	@Column(name = "tips")
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
