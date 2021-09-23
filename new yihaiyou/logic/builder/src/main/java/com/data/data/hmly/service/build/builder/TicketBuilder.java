package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.zuipin.util.PropertiesManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/22.
 */
@Component
public class TicketBuilder {

	private final Logger logger = Logger.getLogger(TicketBuilder.class);

	private static final String TICKET_DETAIL_TEMPLATE = "/ticket/detail.ftl";
	private static final String TICKET_DETAIL_TARGET   = "/ticket/detail{id}.htm";

	@Resource
	private TicketService          ticketService;
	@Resource
	private TicketDatepriceService ticketDatepriceService;
	@Resource
	private TicketExplainService   ticketExplainService;
	@Resource
	private TicketPriceService     ticketPriceService;
	@Resource
	private PropertiesManager propertiesManager;

    public void build() {

		buildDetail();
	}

	public void buildOne(Long id) {
		Ticket ticket = ticketService.loadTicket(id);
		buildDetail(ticket);
	}

	public void buildDetail() {
		Ticket ticketCondition = new Ticket();
		ticketCondition.setStatus(ProductStatus.UP);
		List<Ticket> ticketList = ticketService.findTicketList(ticketCondition, new Page(0, Integer.MAX_VALUE));
		for (Ticket ticket : ticketList) {
			buildDetail(ticket);
		}
	}

	public void buildDetail(Ticket ticket) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("picPath", propertiesManager.getString("mall.resource"));
		data.put("ticket", ticket);
		TicketDateprice lowestPrice = ticketDatepriceService.getLowestPrice(ticket.getId());
		if (lowestPrice == null) {
			logger.error("景点门票#" + ticket.getId() + "没有任何价格");
			data.put("lowestPrice", 0);
		} else {
			data.put("lowestPrice", lowestPrice.getPriPrice());
		}
		TicketExplain ticketExplain = ticketExplainService.findExplainByTicketId(ticket.getId());
		data.put("ticketExplain", ticketExplain);
		List<TicketPrice> ticketPriceList = ticketPriceService.findTicketList(ticket.getId(), new Page(0, Integer.MAX_VALUE));
		Multimap<String, TicketPrice> ticketPriceMultiMap = Multimaps.index(ticketPriceList, new Function<TicketPrice, String>() {
			@Override
			public String apply(TicketPrice ticketPrice) {
				return ticketPrice.getType();
			}
		});
		List<Map<String, Object>> priceList = new ArrayList<Map<String, Object>>();
		for (String key : ticketPriceMultiMap.keySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", key);
			map.put("list", ticketPriceMultiMap.get(key));
			priceList.add(map);
		}
		data.put("priceList", priceList);
		data.put("imguriPreffix", getImguriPreffix());
		FreemarkerUtil.create(data, TICKET_DETAIL_TEMPLATE, TICKET_DETAIL_TARGET.replace("{id}", ticket.getId().toString()));
	}

	public String getImguriPreffix() {
        return propertiesManager.getString("mall.imguri.preffix");
	}
}
