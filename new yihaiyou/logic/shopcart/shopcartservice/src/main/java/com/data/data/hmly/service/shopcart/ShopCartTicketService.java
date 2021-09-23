package com.data.data.hmly.service.shopcart;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/6.
 */
@Service
public class ShopCartTicketService extends ShopCartProductService {

	@Resource
	private TicketService          ticketService;
	@Resource
	private TicketDatepriceService ticketDatepriceService;
	@Resource
	private TicketPriceService     ticketPriceService;


	public Map<String, Object> getProductInfo(Product product, Long priceId, String startDay) {
		Ticket ticket = ticketService.loadTicket(product.getId());
		if (ticket == null) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("product", ticket);

		result.put("startDay", startDay);

		TicketPrice ticketPrice = ticketPriceService.getPrice(priceId);
		List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findAvailableByType(ticketPrice);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = simpleDateFormat.parse(startDay);
			for (TicketDateprice dateprice : ticketDatepriceList) {
				if (dateprice.getHuiDate().compareTo(startDate) == 0) {
					Map<String, Object> priceInfo = createPriceInfo(dateprice);
					result.put("priceInfo", priceInfo);
					break;
				}
			}
		} catch (ParseException e) {
			for (TicketDateprice dateprice : ticketDatepriceList) {
				Map<String, Object> priceInfo = createPriceInfo(dateprice);
				result.put("priceInfo", priceInfo);
				break;
			}
			e.printStackTrace();
		}


		return result;
	}

	public Map<String, Object> createPriceInfo(Object o) {
		Map<String, Object> priceInfo = new HashMap<String, Object>();

		TicketDateprice datePrice = (TicketDateprice) o;
		priceInfo.put("dateId", datePrice.getId());
		priceInfo.put("priceId", datePrice.getTicketPriceId().getId());
		priceInfo.put("costType", OrderCostType.scenic);
		priceInfo.put("costName", datePrice.getTicketPriceId().getName());
		priceInfo.put("date", datePrice.getHuiDate());
		priceInfo.put("discountPrice", datePrice.getPriPrice());
		priceInfo.put("rebate", datePrice.getTicketPriceId().getCommission());
		priceInfo.put("childPrice", null);
		priceInfo.put("childPrice", null);
		return priceInfo;
	}

	public List<TicketDateprice> load(Long priceId) {
		TicketPrice condition = new TicketPrice();
		condition.setId(priceId);
		return ticketDatepriceService.findAvailableByType(condition);
	}
}
