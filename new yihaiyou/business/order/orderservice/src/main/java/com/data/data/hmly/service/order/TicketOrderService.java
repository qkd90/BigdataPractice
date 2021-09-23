package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/10/29.
 */
@Service
public class TicketOrderService extends ProductOrderService{

	@Resource
	private TicketDatepriceService ticketDatepriceService;

	@Override
	public boolean isNeedConfirm(OrderDetail orderDetail) {
		return false;
	}

	@Override
	public boolean saveOrderDetail(OrderDetail orderDetail) {
		return false;
	}

	@Override
	public void fixPrice(OrderDetail orderDetail) {
		TicketDateprice ticketDateprice = ticketDatepriceService.load(orderDetail.getCostId());
		orderDetail.setUnitPrice(ticketDateprice.getPriPrice());
		orderDetail.setCostName(orderDetail.getPriceType().getName());
		orderDetail.setTotalPrice(orderDetail.getUnitPrice() * orderDetail.getNum());
	}

	@Override
	public float getRebate(OrderDetail orderDetail) {
		TicketDateprice ticketDateprice = ticketDatepriceService.load(orderDetail.getCostId());
		return ticketDateprice.getTicketPriceId().getCommission();
	}
}
