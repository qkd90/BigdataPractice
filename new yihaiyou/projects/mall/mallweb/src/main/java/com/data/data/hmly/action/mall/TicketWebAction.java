package com.data.data.hmly.action.mall;

import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.solr.client.solrj.SolrServerException;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by guoshijie on 2015/11/4.
 */
public class TicketWebAction extends MallAction {

	@Resource
	private TicketDatepriceService ticketDatepriceService;
	@Resource
	private TicketPriceService     ticketPriceService;
	@Resource
	private TicketService ticketService;
	@Resource
	private UserService userService;
    @Resource
    private SysUnitService sysUnitService;
	@Resource
	private PropertiesManager propertiesManager;

	private String id;
	private Long   ticketId;
	private TicketDateprice priceDateCondition = new TicketDateprice();
	private List<TicketPrice> ticketPrices;

	private String city;
	private String level;
	private String cost;
	private String mincost;
	private String maxcost;
	private String ticketName;
	private int page;
	
	private Ticket ticketDisplay;

	private long supplierId;
    private String supplierName;

    private int total;

	private Map<String, Object> supplierInfo = new HashMap<String, Object>();

	
	
	
	/**
	 * 分销
	 * 
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result doAgent() {
		String productId = (String) getParameter("productId");
		String ticketStatus = (String) getParameter("ticketStatus");
		String ticketName = (String) getParameter("ticketName");
		if (getLoginUser() != null) {
			if (getLoginUser() instanceof SysUser) {
				String staticPath = propertiesManager.getString("IMG_DIR");
				ticketService.doAgentTicket(Long.valueOf(productId), ticketName, (SysUser) getLoginUser(), ProductStatus.valueOf(ticketStatus), staticPath);
				supplierInfo.put("success", true);
			} else {
				supplierInfo.put("success", false);
				supplierInfo.put("errorMsg", "用户类型不正确");
			}
		} else {
			supplierInfo.put("success", false);
			supplierInfo.put("errorMsg", "用户未登录");
		}
		JSONObject.fromObject(supplierInfo);
		return json(JSONObject.fromObject(supplierInfo));
	}

	

	/**
	 * 转向分销页面
	 * 
	 * @author caiys
	 * @date 2015年11月3日 上午11:44:58
	 * @return
	 */
	public Result agent() {
		String productId = (String) getParameter("productId");
		ticketDisplay = ticketService.findTicketById(Long.valueOf(productId));
		TicketPrice ticketPrice = new TicketPrice();
		ticketPrice.setTicket(ticketDisplay);
		ticketPrices = ticketPriceService.findTickettypepriceList(ticketPrice);
		return dispatch();
	}

	/**
	 * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
	 * 
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result findMinValue() {
		String linetypepriceId = (String) getParameter("tickettypepriceId");
		String prop = (String) getParameter("prop");
		// 起始时间 - 当前时间第二天
		Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
		String dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
		dateStart = DateUtils.getDate(dateStartStr, "yyyy-MM-dd"); // 除去时分秒
		// 结束时间 - 当前时间次月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateStart);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		Date dateEnd = calendar.getTime();
		Float propValue = ticketPriceService.findMinValue(Long.valueOf(linetypepriceId), dateStart, dateEnd, prop);
		supplierInfo.put("propValue", propValue);
		JSONObject.fromObject(supplierInfo);
		return json(JSONObject.fromObject(supplierInfo));
	}
	
	
	
	public Result list() throws UnsupportedEncodingException, SolrServerException {
//		getLineSearcher();

		Map<String, Object> map = new HashMap<String, Object>();
		Page pages = new Page(page + 1, 10);
		if (ticketName != null && !"".equals(ticketName)) {
			map = ticketService.searchByName(pages, ticketName, supplierId);
		} else {
			map = ticketService.serarchBySelect(pages, city, level, cost, mincost, maxcost, supplierId);
		}
        total = Integer.parseInt(map.get("total").toString());
		setAttribute("tickets", map.get("product"));
		setAttribute("pageIndex", page - 1);
		setAttribute("totalCount", map.get("total"));
		return dispatch();
	}

	public Result detail() {
		if (id == null) {
			return redirect("/");
		}

		setAttribute(MallConstants.TICKET_DETAIL_KEY, FileUtil.loadHTML(MallConstants.TICKET_DETAIL + id));
		return dispatch();
	}


	public void getLineSearcher() {
//		setAttribute(MallConstants.TICKET_SEARCHER_KEY, FileUtil.loadHTML(MallConstants.TICKET_DETAIL));
	}

	@AjaxCheck
	public Result getPriceList() {

		List<TicketPrice> ticketPriceList = ticketPriceService.findTicketList(ticketId, new Page(0, Integer.MAX_VALUE));
		Map<String, Object> result = new HashMap<String, Object>();
		for (TicketPrice ticketPrice : ticketPriceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<TicketDateprice> list = ticketDatepriceService.findAvailableByType(ticketPrice);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String startDate = simpleDateFormat.format(list.get(0).getHuiDate());
			map.put("startDate", startDate);
			map.put("dateList", list);
			map.put("price", ticketPrice);
			result.put(ticketPrice.getId().toString(), map);
		}
		result.put("success", true);
		JsonConfig jsonConfig = JsonFilter.getFilterConfig("ticket");
		return json(JSONObject.fromObject(result, jsonConfig));
	}


	public Result facetTickets() throws SolrServerException {

		Map<String, Object> map = ticketService.query(supplierId);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return json(jsonObject);
	}

	public Result getProductTicket() throws UnsupportedEncodingException, SolrServerException {
		Map<String, Object> map = new HashMap<String, Object>();
		Page pages = new Page(page + 1, 10);
		if (ticketName != null && !"".equals(ticketName)) {
			map = ticketService.searchByName(pages, ticketName, supplierId);
		} else {
			map = ticketService.serarchBySelect(pages, city, level, cost, mincost, maxcost, supplierId);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		return json(jsonObject);
	}

	public Result countTicket() throws UnsupportedEncodingException, SolrServerException {
		Map<String, Object> map = new HashMap<String, Object>();
		Page pages = new Page(page + 1, 10);
		if (ticketName != null && !"".equals(ticketName)) {
			map = ticketService.searchByName(pages, ticketName, supplierId);
		} else {
			map = ticketService.serarchBySelect(pages, city, level, cost, mincost, maxcost, supplierId);
		}
		Map<String, Object> countMap = new HashMap<String, Object>();
		if (map.get("total") != null) {
			countMap.put("total", map.get("total"));
		} else {
			countMap.put("total", 0);
		}
		JSONObject jsonObject = JSONObject.fromObject(countMap);
		return json(jsonObject);
	}



	public Result gysTicket() throws UnsupportedEncodingException, SolrServerException {
		if (supplierId == 0) {
			return redirect("/");
		}
		SysUnit unit = sysUnitService.findUnitById(supplierId);
		setAttribute("unit", unit);
		Map<String, Object> map = new HashMap<String, Object>();
        Page pages = new Page(page + 1, 10);
        if (ticketName != null && !"".equals(ticketName)) {
            map = ticketService.searchByName(pages, ticketName, supplierId);
        } else {
            map = ticketService.serarchBySelect(pages, city, level, cost, mincost, maxcost, supplierId);
        }
        total = Integer.parseInt(map.get("total").toString());
        setAttribute("tickets", map.get("product"));
        setAttribute("pageIndex", page - 1);
        setAttribute("totalCount", map.get("total"));

		SysUnit sysUnit = (SysUnit) sysUnitService.findSysUnitById(supplierId);
		if (sysUnit.getSysUnitDetail() != null) {
			supplierName = sysUnit.getSysUnitDetail().getBrandName();
		}

		return dispatch();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TicketDateprice getPriceDateCondition() {
		return priceDateCondition;
	}

	public void setPriceDateCondition(TicketDateprice priceDateCondition) {
		this.priceDateCondition = priceDateCondition;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getMincost() {
		return mincost;
	}

	public void setMincost(String mincost) {
		this.mincost = mincost;
	}

	public String getMaxcost() {
		return maxcost;
	}

	public void setMaxcost(String maxcost) {
		this.maxcost = maxcost;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public Map<String, Object> getSupplierInfo() {
		return supplierInfo;
	}

	public void setSupplierInfo(Map<String, Object> supplierInfo) {
		this.supplierInfo = supplierInfo;
	}

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

	public List<TicketPrice> getTicketPrices() {
		return ticketPrices;
	}

	public void setTicketPrices(List<TicketPrice> ticketPrices) {
		this.ticketPrices = ticketPrices;
	}

	public Ticket getTicketDisplay() {
		return ticketDisplay;
	}

	public void setTicketDisplay(Ticket ticketDisplay) {
		this.ticketDisplay = ticketDisplay;
	}
}
