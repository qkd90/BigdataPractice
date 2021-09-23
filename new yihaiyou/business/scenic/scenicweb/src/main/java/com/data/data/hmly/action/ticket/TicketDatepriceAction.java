package com.data.data.hmly.action.ticket;

import java.text.DecimalFormat;
import java.util.*;

import javax.annotation.Resource;

import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;

public class TicketDatepriceAction extends FrameBaseAction implements
ModelDriven<TicketDateprice> {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private TicketDatepriceService tDatepriceService;
	@Resource
	private TicketPriceService ticketPriceService;
	
	private TicketDateprice ticketDateprice = new TicketDateprice();
	
	private Long ticketPriceId;
	
	private Integer			page				= 1;
	private Integer			rows				= 10;

	Map<String, Object> map = new HashMap<String, Object>();
	
//	public Result list(){
//		
//			
//			TicketPrice ticketPrice = ticketPriceService.findById(ticketPriceId);
//			
//			ticketDateprice.setTicketPriceId(ticketPrice);
//			
//			Page pageInfo = new Page(page,rows);
//			
//			List<TicketDateprice> ticDatePrices = tDatepriceService.findTicketList(ticketDateprice, pageInfo);
//			return datagrid(ticDatePrices, pageInfo.getTotalCount(), "ticDatePrice","parent");
//	}


	public Result saveDatePrice() {

		String dataJsonStr = (String) getParameter("dataJson");
		String ticketPriceIdStr = (String) getParameter("ticketPriceIdStr");

		if (!StringUtils.isNotBlank(dataJsonStr) && !StringUtils.isNotBlank(ticketPriceIdStr)) {
			simpleResult(map, false, "");
			return jsonResult(map);
		}
		TicketPrice ticketPrice = ticketPriceService.getPrice(Long.parseLong(ticketPriceIdStr));

		JSONArray jsonArray = JSONArray.fromObject(dataJsonStr);

		List<TicketDateprice> dateprices = new ArrayList<TicketDateprice>();

		for (Object o : jsonArray) {
			JSONObject object = JSONObject.fromObject(o);
			TicketDateprice dateprice = new TicketDateprice();

			dateprice.setPrice(Float.parseFloat(object.get("price").toString()));
			dateprice.setPriPrice(Float.parseFloat(object.get("priPrice").toString()));
			dateprice.setHuiDate(DateUtils.toDate(object.get("huiDate").toString()));
			dateprice.setTicketPriceId(ticketPrice);
			dateprices.add(dateprice);
		}
		tDatepriceService.save(dateprices);

		simpleResult(map, true, "");
		return jsonResult(map);
	}
	
	
	public Result findPriceDate() {
		
		String ticketpriceId = (String) getParameter("ticketpriceId");
		String dateStartStr = (String) getParameter("dateStart");
		String dateEndStr = (String) getParameter("dateEnd");

		Date dateStart = null;
		Date dateEnd = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		if (StringUtils.isNotBlank(ticketpriceId)) {


			if (StringUtils.isNotBlank(dateStartStr)) {
				dateStart = DateUtils.getDate(dateStartStr, "yyyy-MM-dd");
			}
			if (StringUtils.isNotBlank(dateEndStr)) {
				dateEnd = DateUtils.getDate(dateEndStr, "yyyy-MM-dd");
			}
			// 如果起始时间不为空且结束时间为空，则设置结束时间为开始时间当月最后一天
			if (dateStart != null && dateEnd == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateStart);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
				dateEnd = calendar.getTime();
			}
			// 如果起始时间为空且结束时间不为空，则设置起始时间为结束时间当月的第一天
			if (dateStart == null && dateEnd != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateEnd);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				dateStart = calendar.getTime();
			}

			List<TicketDateprice> ticketpricedates = tDatepriceService.findTypePriceDate(Long.valueOf(ticketpriceId), dateStart, dateEnd);

			// 返回页面数据格式
			DecimalFormat df = new DecimalFormat("0.00");

			for (TicketDateprice td : ticketpricedates) {

				if (td.getPriPrice() != null) {

					Map<String, String> map = new HashMap<String, String>();
					Date day = td.getHuiDate();
					map.put("id", "1" + day.getTime());
					map.put("priPrice", String.valueOf(td.getPriPrice()));
					map.put("title", "a.销售:" + df.format(td.getPriPrice()));
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}

				if (td.getInventory() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = td.getHuiDate();
					map.put("id", "4" + day.getTime());
					map.put("inventory", String.valueOf(td.getInventory()));
					map.put("title", "d.库存:" + df.format(td.getInventory()));
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
				if (td.getMaketPrice() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = td.getHuiDate();
					map.put("id", "2" + day.getTime());
					map.put("marketPrice", String.valueOf(td.getMaketPrice()));
					map.put("title", "b.市价:" + df.format(td.getMaketPrice()));
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
				if (td.getPrice() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = td.getHuiDate();
					map.put("id", "3" + day.getTime());
					map.put("price", String.valueOf(td.getPrice()));
					map.put("title", "b.结算:" + df.format(td.getPrice()));
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}

			}

		}

		JSONArray json = JSONArray.fromObject(list);
		return json(json);
	}
	
	
	@Override
	public TicketDateprice getModel() {
		
		return ticketDateprice;
	}



	public Long getTicketPriceId() {
		return ticketPriceId;
	}



	public void setTicketPriceId(Long ticketPriceId) {
		this.ticketPriceId = ticketPriceId;
	}



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public Integer getRows() {
		return rows;
	}



	public void setRows(Integer rows) {
		this.rows = rows;
	}

}
