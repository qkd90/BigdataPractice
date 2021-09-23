package com.data.data.hmly.service.ticket;

import com.data.data.hmly.service.ticket.dao.TicketPriceAgentDao;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPriceAgent;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TicketPriceAgentService {

    @Resource
    private TicketPriceAgentDao ticketPriceAgentDao;

    public List<TicketPriceAgent> findTicketPriceListByTicket(Ticket ticket) {

        Criteria<TicketPriceAgent> criteria = new Criteria<TicketPriceAgent>(TicketPriceAgent.class);

        criteria.eq("ticket.id", ticket.getId());

        return ticketPriceAgentDao.findByCriteria(criteria);

    }

}
