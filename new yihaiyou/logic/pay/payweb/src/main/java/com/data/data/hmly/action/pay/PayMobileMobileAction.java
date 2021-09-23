package com.data.data.hmly.action.pay;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.pay.WxMobilePayService;
import com.data.data.hmly.service.pay.WxService;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.*;

public class PayMobileMobileAction extends JxmallAction {

	@Resource
	private WechatAccountService wechatAccountService;
	@Resource
	private WxMobilePayService wxMobilePayService;
	@Resource
	private OrderService orderService;
	@Resource
	private WxService wxService;

	private Long orderId;
	private Long accountId;
	private String prepayId;
    private Boolean expired;

	private Order order = new Order();

	private HashMap<String, Object> orderMap = new HashMap<String, Object>();


	public Result pay(){
		return dispatch();
	}

	public Result getPrepayId(){
		accountId = Long.parseLong(getSession().getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID).toString());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip)){
            ip = getRequest().getRemoteAddr();
        }
        User user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
        String openId = user.getAccount();
		WechatAccount wechatAccount = wechatAccountService.get(accountId);

		Order order = orderService.get(orderId);

		Map<String, Object> resultMap = wxMobilePayService.doWeixinOrder(order, wechatAccount, openId, ip);
		return json(JSONObject.fromObject(resultMap));
	}

	public Result getPayConfig() throws Exception {
		//TODO 帐号应该从前端传输进来OR在ACTION中可以直接取到
        accountId = Long.parseLong(getSession().getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID).toString());
		Map<String, Object> resultMap = wxService.doMakeConfig(accountId, prepayId);
		resultMap.put("success", true);
		return json(JSONObject.fromObject(resultMap));
	}


	public Result confirmOrder(){
		order = orderService.get(orderId);
		expired = false;
		for (OrderDetail orderDetail : order.getOrderDetails()){
			if (orderDetail.getPlayDate().before(new Date())){
				expired = true;
				break;
			}
		}
		return dispatch();
	}


	public Result payOrder(){
		order = orderService.get(orderId);
		if (order.getStatus() != OrderStatus.WAIT) {
			return redirect("/");
		}
		float cost = 0;
		int num = 0;
		HashSet<String> productName = new HashSet<String>();
		List<OrderDetail> orderDetails = order.getOrderDetails();
		for(OrderDetail orderDetail : orderDetails){
			cost += orderDetail.getFinalPrice();
			num += orderDetail.getNum();
			productName.add(orderDetail.getProduct().getName());
		}

		orderMap.put("id", order.getId());
		orderMap.put("cost", cost);
		orderMap.put("num", num);
		orderMap.put("productName", productName);
		return dispatch();
	}

	public Result paySuccess(){
        order = orderService.get(orderId);
        order.setStatus(OrderStatus.PAYED);
        orderService.update(order);
		return dispatch();
	}

    public Result mobileTest(){
        return dispatch();
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public HashMap<String, Object> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(HashMap<String, Object> orderMap) {
		this.orderMap = orderMap;
	}
}
