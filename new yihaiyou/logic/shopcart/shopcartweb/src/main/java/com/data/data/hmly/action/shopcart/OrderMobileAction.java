package com.data.data.hmly.action.shopcart;

import com.data.data.hmly.action.shopcart.form.OrderForm;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.shopcart.ShopCartOrderService;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/20.
 */
public class OrderMobileAction extends JxmallAction implements ModelDriven<OrderForm> {


	private Logger logger = Logger.getLogger(OrderWebAction.class);

	@Resource
	private ShopCartOrderService shopCartOrderService;

	private OrderForm orderForm = new OrderForm();


	@AjaxCheck
	public Result create() {

		//todo: 需要考虑多个商品不同日期的情况
		Date playDate;
		try {
			playDate = new SimpleDateFormat("yyyy-MM-dd").parse(orderForm.getPlayDate());
		} catch (ParseException e) {
			logger.error("日期格式错误{" + orderForm.getPlayDate() + "}", e);
			playDate = new Date();
		}

		Member user = getLoginUser();
		Order order = shopCartOrderService.doCreateOrder(user, orderForm.getOrder(), orderForm.getOrderDetails(), orderForm.getTourists(), playDate);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("id", order.getId());
		return json(JSONObject.fromObject(result));
	}


	public OrderForm getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(OrderForm orderForm) {
		this.orderForm = orderForm;
	}

	@Override
	public OrderForm getModel() {
		return orderForm;
	}

	public Member getLoginUser() {
		Member user = (Member) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}
}
