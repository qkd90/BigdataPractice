package com.hmlyinfo.app.soutu.order.service;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.order.domain.Order;
import com.hmlyinfo.app.soutu.order.domain.OrderContact;
import com.hmlyinfo.app.soutu.order.domain.OrderPassenger;
import com.hmlyinfo.app.soutu.order.mapper.OrderMapper;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.ShoppingCartItem;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.app.soutu.plan.service.ShoppingCartItemService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketSubOrder;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPassenger;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends BaseService<Order, Long>{
    
    Logger logger = Logger.getLogger(OrderService.class);

	@Autowired
	private OrderMapper<Order> mapper;
	@Autowired
	private ScenicTicketOrderService scenicTicketOrderService;
	@Autowired
	private ShoppingCartItemService shoppingService;
	@Autowired
	private OrderPassengerService orderPassengerService;
	@Autowired
	private OrderContactService orderContactService;
	@Autowired
	private PlanService planService;

	@Override
	public BaseMapper<Order> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	@SuppressWarnings("unchecked")
	public ScenicTicketOrder makeScenicTicketOrder(ShoppingCartItem shoppingCartItem, OrderContact contact){
		
	    long userId = MemberService.getCurrentUserId();
	    
		ScenicTicketOrder scenicTicketOrder = new ScenicTicketOrder();
		scenicTicketOrder.setUserId(userId);
		List<ScenicTicketSubOrder> sceticTicketSubOrders = new ArrayList<ScenicTicketSubOrder>();
		Map<String, Object> ext = new HashMap<String, Object>();
		List<QunarPassenger> qunarPassengers = new ArrayList<QunarPassenger>();
		
		// 购物车指定的游客
		OrderPassenger orderPassenger = orderPassengerService.info(shoppingCartItem.getPassengerId());
		// 去哪儿游客
		QunarPassenger qunarPassenger = new QunarPassenger();
		qunarPassenger.setName(orderPassenger.getName());
		qunarPassenger.setIdCard(orderPassenger.getIdCard());
		qunarPassenger.setPassport(orderPassenger.getPassport());
		qunarPassenger.setTaiwanPermit(orderPassenger.getTaiwanPermit());
		qunarPassenger.setHkAndMacauPermit(orderPassenger.getHkAndMacauPermit());
		qunarPassenger.setUserDefinedI(shoppingCartItem.getAdditional1());
		qunarPassenger.setUserDefinedIi(shoppingCartItem.getAdditional2());
		qunarPassengers.add(qunarPassenger);
		
		// 去哪儿联系人
		ext.put("mobile", contact.getMobile());
		ext.put("name", contact.getName());
		ext.put("email", contact.getEmail());
		ext.put("postalCode", contact.getPostCode());
		ext.put("postalInfo", contact.getPostalInfo());
		ext.put("idCard", contact.getIdCard());
		ext.put("ywrArr", qunarPassengers);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String ticketDate = format.format(shoppingCartItem.getPlayDate());
		
		// ScenicTicketSubOrdr
		ScenicTicketSubOrder scenicTicketSubOrder = new ScenicTicketSubOrder();
		scenicTicketSubOrder.setScenicTicketId(shoppingCartItem.getTicketId());
		scenicTicketSubOrder.setTicketType(shoppingCartItem.getTicketType());
		scenicTicketSubOrder.setCount(1);
		scenicTicketSubOrder.setTicketDate(ticketDate);
		scenicTicketSubOrder.setExt(ext);
		sceticTicketSubOrders.add(scenicTicketSubOrder);
		
		// ScenicTicketorder
		scenicTicketOrder.setPlanId(shoppingCartItem.getPlanId());
		scenicTicketOrder.setMobile(contact.getMobile());
		scenicTicketOrder.setBuyerName(contact.getName());
		scenicTicketOrder.setIdCardNo(contact.getIdCard());
		scenicTicketOrder.setBuyerEmail(contact.getEmail());
		scenicTicketOrder.setBuyerPostCode(contact.getPostCode());
		scenicTicketOrder.setBuyerAddress(contact.getPostalInfo());
		scenicTicketOrder.setOrderDate(shoppingCartItem.getPlayDate());
		// payType ？
		scenicTicketOrder.setSubOrders(sceticTicketSubOrders);
		
		return scenicTicketOrder;
	}
	
	// 通过planId找出购物车中的所有内容并组装参数
	public List<ScenicTicketOrder> makeScenicTicketOrderList(long planId){
		// 查询购物车中物品列表
		Map<String, Object> shoppingMap = new HashMap<String, Object>();
		shoppingMap.put("planId", planId);
		List<ShoppingCartItem> shoppingCartItems = shoppingService.list(shoppingMap);
		if(shoppingCartItems.isEmpty()){
			// 没有查询到购物车
			return null;
		}
		
		long userId = MemberService.getCurrentUserId();
		List<OrderContact> contacts = orderContactService.list(Collections.<String, Object> singletonMap("userId", userId));
		if(contacts.isEmpty()){
			// 没有查询到对应的联系人信息
			return null;
		}
		
		OrderContact contact = contacts.get(0);
		
		List<ScenicTicketOrder> scenicTicketOrders = new ArrayList<ScenicTicketOrder>();
		for(ShoppingCartItem shoppingCartItem : shoppingCartItems){
			ScenicTicketOrder scenicTicketOrder = makeScenicTicketOrder(shoppingCartItem, contact);
			scenicTicketOrders.add(scenicTicketOrder);
		}
		
		return scenicTicketOrders;
	}
	
	
	// 属于一个行程的所有订单下单
	@SuppressWarnings("unchecked")
	public Order preOrder(HttpServletRequest request){
		
		// 获取当前登录用户
		long userId = MemberService.getCurrentUserId();
		// 当前订单所属的行程
		long planId = Long.parseLong(request.getParameter("planId").toString());
		Plan plan = planService.info(planId);
		// 新建订单
		Order order = new Order();
		order.setUserId(userId);
		order.setStatus(Order.STATUS_NOT_PAIED);
		order.setSourceType(Order.SOURCE_PLAN);
		order.setSourceId(planId);
		order.setPlanName(plan.getPlanName());
		// 订单起止日期
		long startDateMil = Long.parseLong(request.getParameter("fromTime").toString());
		Date startDate = new Date(startDateMil);
		// 计算订单结束日期
		Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(calendar.DATE, plan.getPlanDays());
        Date endDate = calendar.getTime();
        // 插入起止日期
		order.setStartDate(new Date(startDateMil));
		order.setEndDate(endDate);
		
		insert(order);
		long orderId = order.getId();
		
		// 门票订单列表
		List<ScenicTicketOrder> scenicTicketOrders = makeScenicTicketOrderList(planId);
		int totalFee = 0;
		String exceptionMessage = "";
		if (scenicTicketOrders.isEmpty()) {
			logger.error("购物车为空或者创建订单失败");
			return order;
		}
		
		for (ScenicTicketOrder scenicTicketOrder : scenicTicketOrders) {
		    scenicTicketOrder.setOrderId(orderId);
			long scenicTicketId = scenicTicketOrder.getSubOrders().get(0).getScenicTicketId();
			try {
				scenicTicketOrderService.preOrder(scenicTicketOrder);
				totalFee += scenicTicketOrder.getTotalFee();
			} catch (Exception e) {
				logger.error("订单#" + scenicTicketId + "下单失败", e);
				exceptionMessage = exceptionMessage
					+ "门票id为" + scenicTicketId
					+ "对应的订单下单失败："
					+ e.getMessage() + "\n";
			}
		}
		
		// TODO 酒店的订单
		// List<HotelOrder> hotelOrders = new ObjectMapper().readValue(request.getParameter("hotelOrders"), ArrayList.class);
		
		order.setPrice(totalFee);
		update(order);

		if (!"".equals(exceptionMessage)) {
			logger.error(exceptionMessage);
		}
		
		return order;
	}
	
	// 付款
	@Transactional
	public void pay(long payOrderId) {
		
		// 更新payOrderId查询对应的订单order
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("payOrderId", payOrderId);
		List<Order> orders = list(orderMap);
		if(orders.isEmpty()){
			throw new BizValidateException(ErrorCode.ERROR_51001, "订单不存在");
		}
		
		Order order = orders.get(0);
		if(order.getStatus() == Order.STATUS_PAIED){
			logger.info("订单已经支付");;
			return;
		}
		// 是否有一单支付成功
		int payFlag = 0;
		// 查询order对应的所有scenicTicketOrder并支付
		orderMap.remove("payOrderId");
		orderMap.put("orderId", order.getId());
		orderMap.put("status", ScenicTicketOrder.STATUS_NOT_PAID);
		List<ScenicTicketOrder> scenicTicketOrders = scenicTicketOrderService.list(orderMap);
		String exceptionMessage = "";
		for(ScenicTicketOrder scenicTicketOrder : scenicTicketOrders){
			if (scenicTicketOrder.getStatus() == ScenicTicketOrder.STATUS_NOT_PAID) {
				try {
                    scenicTicketOrderService.payOrder(scenicTicketOrder.getId());
                    payFlag = 1;
                } catch (Exception e) {
                    
                    exceptionMessage = exceptionMessage 
                            + "scenicTicketOrderId=" 
                            + scenicTicketOrder.getId() 
                            + "对应的订单支付出现异常\n";
                }
			}
		}
		if(!"".equals(exceptionMessage)){
		    logger.error(exceptionMessage);
		}
		// 更改订单状态
        if (payFlag == 1) {
            order.setStatus(Order.STATUS_PAIED);
            update(order);
            // 更新行程
            Plan plan = planService.info(order.getSourceId());
            plan.setTag(Plan.TAG_PAID);
            planService.update(plan);
        }
	}

}
