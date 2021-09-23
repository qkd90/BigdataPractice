package com.data.data.hmly.service.ctripticket.dao;

import com.data.data.hmly.service.ctripticket.entity.CtripTicketPriceDaily;
import com.framework.hibernate.DataAccess;

import java.util.Date;

//@Repository
public class CtripTicketPriceDailyDao extends DataAccess<CtripTicketPriceDaily> {
	
	/**
	 * 根据门票标识、门票资源标识、价格时间起始删除
	 * @author caiys
	 * @date 2015年12月3日 下午6:08:56
	 * @param ticketId
	 * @param ticketResourceId
	 */
	public void delByPriceIdAndDay(Integer ticketId, Integer ticketResourceId, Date startDate, Date endDate) {
		String hql = " delete CtripTicketPriceDaily where ticketId = ? and ticketResourceId = ? and date >= ? and date <= ? ";
		updateByHQL(hql, ticketId, ticketResourceId, startDate, endDate);
	}
	
}
