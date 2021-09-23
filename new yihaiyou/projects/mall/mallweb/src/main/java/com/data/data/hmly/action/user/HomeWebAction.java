package com.data.data.hmly.action.user;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/16.
 */
public class HomeWebAction extends JxmallAction {

	@Resource
	private com.data.data.hmly.action.user.HomeOrderService homeOrderService;

	private Page page = new Page();
	private Order order = new Order();
	private String sortName;
	private String sortType;
	private String startDate;
	private String endDate;
	private Float totalPayAmount;

	public Result home() {

		return dispatch();
	}

	public Result order() {

		return dispatch();
	}

	public Result countOrder() {
		User user = getLoginUser();
		List<Order> list = homeOrderService.listOrder(order, page, user);
		return text(list.size() + "");
	}

	public Result listOrder() {
		User user = getLoginUser();
		List<Order> list = homeOrderService.listOrder(order, page, user);
		// 检查订单是否过期，如果有定时刷新过期订单，可取消检查
		for (Order o : list) {
			if (o.getStatus() != OrderStatus.WAIT) {	// 只查找待支付状态
				continue ;
			}
			for (OrderDetail orderDetail : o.getOrderDetails()) {
				if (orderDetail.getPlayDate() == null || orderDetail.getPlayDate().before(new Date())){
					o.setStatus(OrderStatus.INVALID);
					break;
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("totalCount", page.getTotalCount());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("orderDetails", "product", "rootProduct", "user");
		return json(JSONObject.fromObject(result, jsonConfig) );
	}

	public Result detail() {
		Object id = getParameter("id");
		if (id == null) {
			return text("没有id");
		}
		order = homeOrderService.detail(Long.valueOf(id.toString()));
		totalPayAmount = 0f;
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			totalPayAmount += orderDetail.getFinalPrice();
		}
		return dispatch();
	}

	public Result lineOrderDetail() {

		return dispatch();
	}

	public Result ticketOrderDetail() {

		return dispatch();
	}

	public User getLoginUser() {
		User user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Float getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(Float totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}
}
