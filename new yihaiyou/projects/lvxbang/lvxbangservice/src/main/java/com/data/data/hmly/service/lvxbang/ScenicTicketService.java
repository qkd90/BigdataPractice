package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.lvxbang.response.ScenicBookingResponse;
import com.data.data.hmly.service.lvxbang.response.TicketBookingResponse;
import com.data.data.hmly.service.lvxbang.response.TicketPrice;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ScenicTicketService {

    @Resource
    private TicketService ticketService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;

    public ScenicBookingResponse listTicketByScenic(ScenicInfo scenicInfo, final Date date) {
        Ticket condition = new Ticket();
        condition.setScenicInfo(scenicInfo);
        List<Ticket> tickets = ticketService.findTicketList(condition, new Page(0, Integer.MAX_VALUE));
        ScenicBookingResponse scenicBookingResponse = new ScenicBookingResponse();
        scenicBookingResponse.id = scenicInfo.getId();
        scenicBookingResponse.name = scenicInfo.getName();
        scenicBookingResponse.date = date;
        scenicBookingResponse.productId = scenicInfo.getId();
        scenicBookingResponse.type = ProductType.scenic;
        scenicBookingResponse.tickets = Lists.transform(tickets, new Function<Ticket, TicketBookingResponse>() {
            @Override
            public TicketBookingResponse apply(Ticket ticket) {
                return createTicketBookingResponse(ticket, date);
            }
        });
        return scenicBookingResponse;
    }

    private TicketBookingResponse createTicketBookingResponse(Ticket ticket, Date date) {
        TicketBookingResponse response = new TicketBookingResponse();
        response.id = ticket.getId();
        response.name = ticket.getName();
        for (com.data.data.hmly.service.ticket.entity.TicketPrice price : ticket.getTicketPriceSet()) {
            List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(price.getId(), date, date);
            if (ticketDatepriceList.isEmpty()) {
                continue;
            }
            TicketPrice ticketPrice = new TicketPrice();
            ticketPrice.id = ticketDatepriceList.get(0).getId();
            ticketPrice.name = ticketDatepriceList.get(0).getName();
            ticketPrice.price = ticketDatepriceList.get(0).getPriPrice();
            response.prices.add(ticketPrice);
        }
        return response;
    }
}
