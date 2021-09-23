package com.data.data.hmly.service.ticket.dao;

import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketExplainDao extends DataAccess<TicketExplain> {

	public void delAllByTicket(Ticket ticket) {
		
		Criteria<TicketExplain> criteria = new Criteria<TicketExplain>(TicketExplain.class);
		
		criteria.eq("ticketId", ticket.getId());
		
		List<TicketExplain> exs = findByCriteria(criteria);
		
		deleteAll(exs);
		
		
	}
	
	/**
	 * 查询门票说明
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param ticketExplain
	 * @return
	 */
	public List<TicketExplain> findTicketexplain(TicketExplain ticketExplain) {
		Criteria<TicketExplain> criteria = new Criteria<TicketExplain>(TicketExplain.class);
		if (ticketExplain.getTicketId() != null) {
			criteria.eq("ticketId", ticketExplain.getTicketId());
		}
//		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}

}
