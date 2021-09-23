package com.data.data.hmly.service.ticket;

import com.data.data.hmly.service.ticket.dao.TicketExplainDao;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TicketExplainService {
	@Resource
	private TicketExplainDao dao;

    public void save(TicketExplain ticketExplain) {
        dao.save(ticketExplain);
    }

    public void 	delete(TicketExplain ticketExplain) {
        dao.delete(ticketExplain);
    }

	public void deleteBySql(Long ticketId) {
		String sql = "delete from ticketexplain tp where tp.ticketId=?";
		dao.updateBySQL(sql, ticketId);
	}

    public void update(TicketExplain explain) {
		dao.update(explain);
	}

	public TicketExplain findExplainByTicketId(Long ticketId) {
		
		Criteria<TicketExplain> criteria = new Criteria<TicketExplain>(TicketExplain.class);
		
		criteria.eq("ticketId", ticketId);
		
//		String hql = "from TicketExplain where ticket.id="+ticketId;
//		
//		return dao.findOneByHQL(hql);
		return dao.findUniqueByCriteria(criteria);
	}

	public TicketExplain load(Long id) {
		return dao.load(id);
	}
}
