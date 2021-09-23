package com.data.data.hmly.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.data.data.hmly.service.user.dao.TicketReceiverDao;
import com.data.data.hmly.service.user.entity.TicketReceiver;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

@Service
public class TicketReveiverService {
   
	@Resource
	private TicketReceiverDao ticketReceiverDao;
	
	public List<TicketReceiver> listAll()  {
		return ticketReceiverDao.findAll();
	}
	public List<TicketReceiver> list(TicketReceiver ticketReceiver, Page page, String... orderProperty) {
		Criteria<TicketReceiver> criteria = createCriteria(ticketReceiver, orderProperty);
		if (page == null) {
			return ticketReceiverDao.findByCriteria(criteria);
		}
		return ticketReceiverDao.findByCriteria(criteria, page);
	}

	public Criteria<TicketReceiver> createCriteria(TicketReceiver ticketReceiver, String... orderProperty) {
		Criteria<TicketReceiver> criteria = new Criteria<TicketReceiver>(TicketReceiver.class);
		if (orderProperty.length == 2) {
			criteria.orderBy(orderProperty[0], orderProperty[1]);
		} else if (orderProperty.length == 1) {
			criteria.orderBy(orderProperty[0], "desc");
		}
		if (ticketReceiver == null) {
			return criteria;
		}
		if (ticketReceiver.getUser() != null) {
			criteria.eq("user.id", ticketReceiver.getUser().getId());
		}
		if (ticketReceiver.getName() != null) {
			criteria.like("name", ticketReceiver.getName());
		}
		return criteria;
	}
}
