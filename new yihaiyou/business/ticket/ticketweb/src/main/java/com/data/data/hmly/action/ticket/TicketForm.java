package com.data.data.hmly.action.ticket;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;

public class TicketForm {
	
	private Ticket ticket;
	private Product product;
	private TicketExplain ticketExplain;
	
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public TicketExplain getTicketExplain() {
		return ticketExplain;
	}
	public void setTicketExplain(TicketExplain ticketExplain) {
		this.ticketExplain = ticketExplain;
	}

}
