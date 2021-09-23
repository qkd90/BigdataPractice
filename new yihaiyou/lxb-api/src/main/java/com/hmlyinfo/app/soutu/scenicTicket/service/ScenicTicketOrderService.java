package com.hmlyinfo.app.soutu.scenicTicket.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.account.domain.ThridPartyUser;
import com.hmlyinfo.app.soutu.account.mapper.ThridPartyUserMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.common.service.SequenceService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.OrderRenwoyou;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketSubOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.TicketRenwoyou;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.ScenicTicketOrderMapper;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarOrder;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPassenger;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPrice;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarPassengerService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarPriceService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarTicketService;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScenicTicketOrderService extends BaseService<ScenicTicketOrder, Long> {

	Logger												logger				= Logger.getLogger(ScenicTicketOrderService.class);

	public static final int								TYPE_RENWOYOU		= 1;
	public static final int								TYPE_CTRIP			= 2;
	public static final int								TYPE_QUNAR			= 3;

	public static final String							ORDER_RENWOYOU_NUM	= "001";
	public static final String							ORDER_QUNAR_NUM		= "002";

	public static SimpleDateFormat						DATE_FORMAT			= new SimpleDateFormat("yyyy-MM-dd");
	private static final String							NOTIFY_URL			= Config.get("NOTIFY_URL");

	@Autowired
	private ScenicTicketOrderMapper<ScenicTicketOrder>	mapper;
	@Autowired
	private TicketRenwoyouService						ticketRenwoyouService;
	@Autowired
	private RenwoyouService								renwoyouService;
	@Autowired
	private OrderRenwoyouService						orderRenwoyouService;
	@Autowired
	private ScenicTicketSubOrderService					scenicTicketSubOrderService;
	@Autowired
	private PayOrderService								payOrderService;
	@Autowired
	private ThridPartyUserMapper						thridPartyUserMapper;
	@Autowired
	private QunarOrderService							qunarOrderService;
	@Autowired
	private QunarPassengerService						qunarPassengerService;
	@Autowired
	private QunarTicketService							qunarTicketService;
	@Autowired
	private QunarPriceService							qunarPriceService;
	@Autowired
	private SequenceService								sequenceService;

	@Override
	public BaseMapper<ScenicTicketOrder> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	public List<ScenicTicketOrder> listWithDetail(Map<String, Object> paramMap) {
		List<ScenicTicketOrder> scenicTicketOrderList = super.list(paramMap);
		for (ScenicTicketOrder scenicTicketOrder : scenicTicketOrderList) {
			List<ScenicTicketSubOrder> list = scenicTicketSubOrderService.list(Collections.<String, Object> singletonMap(
					"scenicTicketOrderId", scenicTicketOrder.getId()));
			String scenicIds = "";
			for (ScenicTicketSubOrder subOrder : list) {
				scenicIds = scenicIds + subOrder.getScenicId() + ",";
			}
			scenicTicketOrder.setScenicIds(scenicIds);
			scenicTicketOrder.setSubOrders(list);
		}
		return scenicTicketOrderList;
	}

	@Override
	public ScenicTicketOrder info(Long id) {
		ScenicTicketOrder scenicTicketOrder = super.info(id);
		List<ScenicTicketSubOrder> list = scenicTicketSubOrderService.list(Collections.<String, Object> singletonMap("scenicTicketOrderId",
				scenicTicketOrder.getId()));
		for (ScenicTicketSubOrder stSubOrder : list) {
			if (stSubOrder.getRelateOrderId() != 0 && stSubOrder.getTicketType() == 3) {
				Map<String, Object> passMap = new HashMap<String, Object>();
				passMap.put("orderQunarId", stSubOrder.getRelateOrderId());
				List<QunarPassenger> passengerList = qunarPassengerService.list(passMap);
				stSubOrder.setPassengerList(passengerList);
			}

		}
		scenicTicketOrder.setSubOrders(list);
		return scenicTicketOrder;
	}

	/**
	 * 预订票
	 * 
	 * @throws Exception
	 */
	public void preOrder(ScenicTicketOrder scenicTicketOrder) throws Exception {

		double totalFee = 0;
		List<ScenicTicketSubOrder> subOrders = scenicTicketOrder.getSubOrders();

		int ticketType = ListUtil.getSingle(subOrders).getTicketType();

		// 生成订单编号
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		String orderTime = df.format(new Date());
		String orderNum = "";
		if (ticketType == TYPE_RENWOYOU) {
			orderNum = orderNum + orderTime + ORDER_RENWOYOU_NUM + sequenceService.getOrderSeq();
		} else if (ticketType == TYPE_QUNAR) {
			orderNum = orderNum + orderTime + ORDER_QUNAR_NUM + sequenceService.getOrderSeq();
		}
		scenicTicketOrder.setOrderNum(orderNum);

		insert(scenicTicketOrder);

		Map<String, Object> resMap = new HashMap<String, Object>();
		for (ScenicTicketSubOrder subOrder : subOrders) {
			subOrder.setScenicTicketOrderId(scenicTicketOrder.getId());
			resMap = preOrder(scenicTicketOrder, subOrder);
			totalFee += subOrder.getTotalAmount();
		}

		// 判断门票是否支持在线支付和是否是套票
		int seasonFlag = Integer.parseInt(resMap.get("seasonFlag").toString());
		int onlineFlag = Integer.parseInt(resMap.get("onlineFlag").toString());
		if (seasonFlag == ScenicTicketOrder.SEASON_TICKET_YES) {
			scenicTicketOrder.setSeasonTicket(ScenicTicketOrder.SEASON_TICKET_YES);
		} else {
			scenicTicketOrder.setSeasonTicket(ScenicTicketOrder.SEASON_TICKET_NO);
		}
		if (onlineFlag == ScenicTicketOrder.PAY_ONLINE) {
			scenicTicketOrder.setOnlinePay(ScenicTicketOrder.PAY_ONLINE);
		} else {
			scenicTicketOrder.setOnlinePay(ScenicTicketOrder.PAY_OFFLINE);
		}

		scenicTicketOrder.setTotalFee(totalFee);
		scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_NOT_PAID);
		try {
			scenicTicketOrder.setOrderDate(DATE_FORMAT.parse(subOrders.get(0).getTicketDate()));
		} catch (ParseException e) {
			// 不做处理
		}

		String failReasonString = resMap.get("failReason").toString();
		if (failReasonString != null && !"".equals(failReasonString)) {
			scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_FAILED);
			update(scenicTicketOrder);
			throw new Exception(failReasonString);
		}
		update(scenicTicketOrder);
	}

	private Map<String, Object> preOrder(ScenicTicketOrder scenicTicketOrder, ScenicTicketSubOrder subOrder) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		int seasonFlag = ScenicTicketOrder.SEASON_TICKET_NO;
		int onlineFlag = ScenicTicketOrder.PAY_ONLINE;
		String failReasonString = "";
		if (subOrder.getTicketType() == TYPE_RENWOYOU) {
			scenicTicketSubOrderService.insert(subOrder);
			OrderRenwoyou orderRenwoyou = preOrderRenwoyou(scenicTicketOrder, subOrder);
			failReasonString = orderRenwoyou.getFailReason();
			subOrder.setTicketType(TYPE_RENWOYOU);
			scenicTicketSubOrderService.update(subOrder);
		} else if (subOrder.getTicketType() == TYPE_QUNAR) {
			scenicTicketSubOrderService.insert(subOrder);
			QunarOrder qunarOrder = qunarOrderService.preOrderQunar(scenicTicketOrder, subOrder);
			failReasonString = qunarOrder.getFailReason();
			subOrder.setRelateOrderId(qunarOrder.getId());
			subOrder.setTicketType(TYPE_QUNAR);
			scenicTicketSubOrderService.update(subOrder);
			// 判断门票是否支持在线支付和是否是套票

			long ticketId = subOrder.getScenicTicketId();
			QunarTicket qunarTicket = qunarTicketService.info(ticketId);
			if (qunarTicket.getSeasonTicketFlag().equals("T")) {
				seasonFlag = ScenicTicketOrder.SEASON_TICKET_YES;
			}
			if (qunarTicket.getPayWay() == QunarTicket.PAY_WAY_OUTLINE) {
				onlineFlag = ScenicTicketOrder.PAY_OFFLINE;
			} else {
				onlineFlag = ScenicTicketOrder.PAY_ONLINE;
			}
		}
		resMap.put("failReason", failReasonString);
		resMap.put("seasonFlag", seasonFlag);
		resMap.put("onlineFlag", onlineFlag);
		return resMap;
	}

	private OrderRenwoyou preOrderRenwoyou(ScenicTicketOrder scenicTicketOrder, ScenicTicketSubOrder subOrder) {
		String failReasonString = "";
		TicketRenwoyou scenicTicket = ticketRenwoyouService.info(subOrder.getScenicTicketId());
		String validateResult = "";
		try {
			validateResult = renwoyouService.ifSatisfyPreOrderTicket(scenicTicketOrder, scenicTicket.getOuterTicketId(),
					subOrder.getCount(), subOrder.getTicketDate());
		} catch (Exception e) {
			logger.error("不满足下单要求：" + e.getMessage());
			failReasonString = failReasonString + "不满足下单要求：" + e.getMessage();
		}
		if (!validateResult.equals(RenwoyouService.VALIDATE_RESULT_SUCCESS)) {
			logger.error("不满足下单要求：" + validateResult);
			failReasonString = failReasonString + "不满足下单要求：" + validateResult;
		}
		// 订单名称
		scenicTicketOrder.setOrderName(scenicTicket.getScenicName() + scenicTicket.getName());
		/* 如果满足下订单要求，就调用任我游预下单功能 */
		OrderRenwoyou orderRenwoyou = new OrderRenwoyou();
		orderRenwoyou.setOrderNum("renwoyou" + sequenceService.getOrderRenwoyouSeq());
		orderRenwoyou.setTotalAmount(scenicTicket.getLvxbangPrice() * subOrder.getCount());
		orderRenwoyou.setScenicTicketId(subOrder.getScenicTicketId());
		orderRenwoyou.setScenicTicketOrderId(scenicTicketOrder.getId());
		orderRenwoyou.setSubOrderId(subOrder.getId());
		orderRenwoyou.setCount(subOrder.getCount());
		try {
			orderRenwoyou.setTicketDate(DATE_FORMAT.parse(subOrder.getTicketDate()));
		} catch (ParseException e) {
			logger.error("日期格式错误");
			failReasonString += ",日期格式错误";
		}
		orderRenwoyou.setStatus(ScenicTicketOrder.STATUS_WAITING);
		orderRenwoyou.setScenicId(scenicTicket.getScenicId());
		orderRenwoyouService.insert(orderRenwoyou);

		subOrder.setRelateOrderId(orderRenwoyou.getId());
		subOrder.setTotalAmount(orderRenwoyou.getTotalAmount());
		subOrder.setScenicId(orderRenwoyou.getScenicId());

		// 已经确认出现异常，不需要去任我游下单
		if (!"".equals(failReasonString)) {
			orderRenwoyou.setFailReason(failReasonString);
			orderRenwoyouService.update(orderRenwoyou);
			subOrder.setStatus(ScenicTicketSubOrder.STATUS_FAILED);
			return orderRenwoyou;
		}

		Map<String, Object> orderMap = renwoyouService.preOrderTicket(scenicTicketOrder, orderRenwoyou.getOrderNum(),
				scenicTicket.getOuterTicketId(), subOrder.getCount(), subOrder.getTicketDate());

		if (!orderMap.get("status").equals(RenwoyouService.VALIDATE_RESULT_SUCCESS)) {
			logger.error(orderMap.get("body").toString());
			failReasonString = failReasonString + ", " + orderMap.get("body").toString();
			subOrder.setStatus(ScenicTicketSubOrder.STATUS_FAILED);
		} else {
			orderRenwoyou.setStatus(ScenicTicketOrder.STATUS_NOT_PAID);
			orderRenwoyou.setOuterId(Long.valueOf(orderMap.get("no").toString()));
			orderRenwoyou.setRealAmount((Double) orderMap.get("amount"));

		}
		orderRenwoyou.setFailReason(failReasonString);
		orderRenwoyouService.update(orderRenwoyou);
		return orderRenwoyou;
	}

	public Map<String, Object> prePay(Long id, String ip, Long userId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<ThridPartyUser> thridPartyUserList = thridPartyUserMapper.list(Collections.singletonMap("userId", userId));
		if (thridPartyUserList.isEmpty()) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "找不到当前用户的openId");
		}
		String openId = thridPartyUserList.get(0).getOpenId();
		ScenicTicketOrder scenicTicketOrder = info(id);
		if (scenicTicketOrder.getStatus() >= ScenicTicketOrder.STATUS_PAID) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "订单已经支付过了");
		}
		// 验证是否过期，过期了才去才重新下订单，超过15分钟
		preOrderValidate(scenicTicketOrder);
		PayOrder payOrder = new PayOrder();
		payOrder.setBody(scenicTicketOrder.getOrderName());
		payOrder.setIp(ip);
		payOrder.setOpenId(openId);
		payOrder.setTotalFee((int) scenicTicketOrder.getTotalFee());
		payOrder.setUserId(scenicTicketOrder.getUserId());
		payOrder.setPayType(PayOrder.PAY_TYPE_WX);
		payOrderService.unifiedOrder(payOrder, PayOrderService.PAY_ORDER_TYPE_WEIXIN, NOTIFY_URL);

		scenicTicketOrder.setPayOrder(payOrder.getId());
		update(scenicTicketOrder);
		result.put("prePayId", payOrder.getPreOrderId());
		return result;
	}

	/**
	 * 查询下过订单的具体信息
	 */
	public Map<String, Object> getOrderDetail(long no, int type) {
		Map<String, Object> bodyMap = null;
		if (type == TYPE_RENWOYOU) {
			bodyMap = renwoyouService.getOrderDetail(no);
		}
		return bodyMap;
	}

	/**
	 * 订单支付,支持批量支付
	 * 
	 * @param id
	 *            总订单编号
	 * @throws Exception
	 */
	public void payOrder(Long id) throws Exception {
		ScenicTicketOrder scenicTicketOrder = info(id);
		scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_PAID);
		update(scenicTicketOrder);
		// 特殊处理
		if (scenicTicketOrder.getPlanId() != null && scenicTicketOrder.getPlanId().intValue() == -1) {
			processSpringOrder(scenicTicketOrder);
			return;
		}
		boolean checked = true;
		for (ScenicTicketSubOrder scenicTicketSubOrder : scenicTicketOrder.getSubOrders()) {
			switch (scenicTicketSubOrder.getTicketType()) {
			case TYPE_RENWOYOU:
				boolean flag = renwoyouService.payRenwoyouOrder(scenicTicketSubOrder.getRelateOrderId());
				if (!flag) {
					checked = false;
				} else {
					scenicTicketSubOrder.setStatus(ScenicTicketSubOrder.STATUS_CHECKED);
					scenicTicketSubOrderService.update(scenicTicketSubOrder);
				}
				break;
			case TYPE_QUNAR:
				long orderQunarId = scenicTicketSubOrder.getRelateOrderId();
				boolean qunarFlag = qunarOrderService.qunarPay(orderQunarId);
				if (!qunarFlag) {
					checked = false;
				} else {
					scenicTicketSubOrder.setStatus(ScenicTicketSubOrder.STATUS_CHECKED);
					scenicTicketSubOrderService.update(scenicTicketSubOrder);
				}
				break;
			default:
				return;
			}
		}
		if (checked) {
			scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_CHECKED);
			update(scenicTicketOrder);
		}
	}

	/**
	 * @param payOrderId
	 * @throws Exception
	 */
	public long payOrderByPayOrder(Long payOrderId) throws Exception {
		List<ScenicTicketOrder> list = list(Collections.<String, Object> singletonMap("payOrder", payOrderId));
		if (list.isEmpty()) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "门票订单不存在");
		}
		ScenicTicketOrder scenicTicketOrder = list.get(0);
		if (scenicTicketOrder.getStatus() == ScenicTicketOrder.STATUS_NOT_PAID) {
			payOrder(scenicTicketOrder.getId());
		}

		return scenicTicketOrder.getId();
	}

	/**
	 * 退单
	 * 
	 * @throws Exception
	 */
	public String refundOrder(Long id) throws Exception {
		ScenicTicketOrder scenicTicketOrder = info(id);
		if (scenicTicketOrder.getStatus() < ScenicTicketOrder.STATUS_PAID) {
			throw new BizValidateException(ErrorCode.ERROR_56003, "订单未支付");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scenicTicketOrderId", scenicTicketOrder.getId());
		List<ScenicTicketSubOrder> subOrders = scenicTicketSubOrderService.list(map);
		for (ScenicTicketSubOrder subOrder : subOrders) {
			refundTicket(subOrder.getId(), subOrder.getTicketType());
		}
		scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_REFUNDING_ORDER);
		update(scenicTicketOrder);
		if (scenicTicketOrder.getPayType() == ScenicTicketOrder.PAY_TYPE_WEIXIN) {
			payOrderService.refund(scenicTicketOrder.getPayOrder(), PayOrderService.PAY_ORDER_TYPE_WEIXIN);
		}
		return "success";
	}

	/**
	 * 退票
	 * 
	 * @param id
	 *            ,type
	 * @return 退订结果
	 * @throws Exception
	 */
	public String refundTicket(long id, int type) throws Exception {
		String bodyStr = "";
		if (type == TYPE_RENWOYOU) {
			OrderRenwoyou orderRenwoyou = orderRenwoyouService.info(id);
			bodyStr = renwoyouService.refundTicket(orderRenwoyou);
			if (bodyStr.equals(RenwoyouService.VALIDATE_RESULT_SUCCESS)) {
				// scenicTicketOrder.setStatus(scenicTicketOrder.STATUS_REFUND_TICKET);
				orderRenwoyouService.update(orderRenwoyou);
			} else {
				throw new BizValidateException(ErrorCode.ERROR_56003, bodyStr);
			}
		}
		return bodyStr;
	}

	/**
	 * 验证是否过期，过期了才去才重新下订单，超过15分钟
	 * 
	 * @throws Exception
	 */
	public void preOrderValidate(ScenicTicketOrder scenicTicketOrder) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("scenicTicketOrderId", scenicTicketOrder.getId());
		List<ScenicTicketSubOrder> subOrders = scenicTicketSubOrderService.list(map);

		for (ScenicTicketSubOrder subOrder : subOrders) {
			rePreOrder(scenicTicketOrder, subOrder);
		}
		scenicTicketOrder.setModifyTime(new Date());
		update(scenicTicketOrder);
	}

	/**
	 * 任我游重新下订单,验证是否过期，过期了才去才重新下订单，超过15分钟
	 * 
	 * @param scenicTicketOrder
	 * @param subOrder
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public void rePreOrder(ScenicTicketOrder scenicTicketOrder, ScenicTicketSubOrder subOrder) throws Exception {
		Date date = new Date();
		if (subOrder.getTicketType() == TYPE_RENWOYOU) {
			if (((date.getTime() - scenicTicketOrder.getModifyTime().getTime()) / 60000) < 15) {
				return;
			}
			OrderRenwoyou orderRenwoyou = preOrderRenwoyou(scenicTicketOrder, subOrder);
			subOrder.setRelateOrderId(orderRenwoyou.getId());
			subOrder.setTotalAmount(orderRenwoyou.getTotalAmount());
			scenicTicketSubOrderService.update(subOrder);
		} else if (subOrder.getTicketType() == TYPE_QUNAR) {
			qunarOrderService.qunarReorder(scenicTicketOrder, subOrder);
		}

	}

	/**
	 * 发送短信
	 * 
	 * @param id
	 * @param type
	 * @throws Exception
	 */
	public List sendSms(Long id, int type) throws Exception {
		List<Map<String, Object>> list = null;
		if (type == TYPE_RENWOYOU) {
			OrderRenwoyou orderRenwoyou = orderRenwoyouService.info(id);
			list = renwoyouService.sendSms(orderRenwoyou);
		} else if (type == TYPE_QUNAR) {
			QunarOrder orderQunar = qunarOrderService.info(id);
			list = qunarOrderService.sendSms(orderQunar);
		}
		return list;
	}

	/**
	 * 查询我的订单列表，在列表中插入一个order包含的所有景点id，用逗号隔开存在scenicIds中
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<ScenicTicketOrder> listOrder(Map<String, Object> paramMap) {
		List<ScenicTicketOrder> scenicTicketOrders = list(paramMap);
		List orderIds = ListUtil.getIdList(scenicTicketOrders, "id");
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("orderIds", orderIds);
		List<ScenicTicketSubOrder> subOrders = scenicTicketSubOrderService.list(subMap);

		Multimap<Long, Long> myMultimap = ArrayListMultimap.create();
		for (ScenicTicketSubOrder subOrder : subOrders) {
			myMultimap.put(subOrder.getScenicTicketOrderId(), subOrder.getScenicId());
		}

		for (ScenicTicketOrder order : scenicTicketOrders) {
			Collection<Long> scenicCollection = myMultimap.get(order.getId());
			String scenicIds = "";
			for (Long scenicId : scenicCollection) {
				scenicIds = scenicIds + scenicId + ",";
			}
			order.setScenicIds(scenicIds);
		}

		return scenicTicketOrders;
	}

	/**
	 * 编辑订单
	 * 
	 * @param scenicTicketOrder
	 * @param oldId
	 * @throws Exception
	 */
	public void editOrder(ScenicTicketOrder scenicTicketOrder, String oldId) throws Exception {
		// 取得旧的订单信息
		ScenicTicketOrder oldOrder = mapper.selById(oldId);
		Validate.notNull(oldOrder, ErrorCode.ERROR_51001, "原订单不存在");
		int status = oldOrder.getStatus();
		Validate.isTrue(status != ScenicTicketOrder.STATUS_PAID && status != ScenicTicketOrder.STATUS_CHECKED, ErrorCode.ERROR_51001,
				"订单已支付或者已出票，不可编辑");
		// 从新的的订单信息更新部分信息后更新订单
		scenicTicketOrder.setId(oldOrder.getId());
		scenicTicketOrder.setOrderName(oldOrder.getOrderName());
		scenicTicketOrder.setPayType(oldOrder.getPayType());
		scenicTicketOrder.setPlanId(oldOrder.getPlanId());

		// 从新的的subOrder更新部分信息后更新suborder
		List<ScenicTicketSubOrder> newSubOrders = scenicTicketOrder.getSubOrders();
		Map<String, Object> oldSubMap = new HashMap<String, Object>();
		oldSubMap.put("scenicTicketOrderId", oldId);
		List<ScenicTicketSubOrder> oldSubOrders = scenicTicketSubOrderService.list(oldSubMap);

		ScenicTicketSubOrder subOrder = ListUtil.getSingle(newSubOrders);
		ScenicTicketSubOrder oldSubOrder = ListUtil.getSingle(oldSubOrders);

		subOrder.setId(oldSubOrder.getId());
		subOrder.setScenicTicketOrderId(oldSubOrder.getScenicTicketOrderId());
		subOrder.setScenicTicketId(oldSubOrder.getScenicTicketId());
		subOrder.setTicketType(oldSubOrder.getTicketType());
		subOrder.setScenicId(oldSubOrder.getScenicId());

		// 重新下单
		if (subOrder.getTicketType() == TYPE_RENWOYOU) {
			OrderRenwoyou orderRenwoyou = preOrderRenwoyou(scenicTicketOrder, subOrder);
			scenicTicketOrder.setTotalFee(subOrder.getTotalAmount());
		} else if (subOrder.getTicketType() == TYPE_QUNAR) {
			QunarOrder qunarOrder = qunarOrderService.preOrderQunar(scenicTicketOrder, subOrder);
			scenicTicketOrder.setTotalFee(subOrder.getTotalAmount());
			subOrder.setRelateOrderId(qunarOrder.getId());
		}

		scenicTicketSubOrderService.update(subOrder);
		scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_NOT_PAID);
		try {
			scenicTicketOrder.setOrderDate(DATE_FORMAT.parse(subOrder.getTicketDate()));
		} catch (ParseException e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "日期格式错误");
		}
		mapper.update(scenicTicketOrder);

	}

	//
	//
	//
	public void orderAndPay(Map<String, Object> paramMap) throws Exception {
		String id = (String) paramMap.get("id");
		ScenicTicketOrder scenicTicketOrder = mapper.selById(id);

		// 判断当前门票是否处在支付状态
		if (scenicTicketOrder.getStatus() != ScenicTicketOrder.STATUS_PAID) {
			throw new BizValidateException(ErrorCode.ERROR_50002, "不满足管理员支付条件，不可进行此操作");
		}
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("scenicTicketOrderId", id);
		List<ScenicTicketSubOrder> subOrders = scenicTicketSubOrderService.list(subMap);

		for (ScenicTicketSubOrder subOrder : subOrders) {
			if (subOrder.getTicketType() == TYPE_QUNAR) {
				// 旧的qunar订单的id
				long orderQunarId = subOrder.getRelateOrderId();
				// 查询门票价格数据
				long ticketId = subOrder.getScenicTicketId();
				String useDate = subOrder.getTicketDate();
				QunarTicket qunarTicket = qunarTicketService.info(ticketId);
				long productId = Long.parseLong(qunarTicket.getProductId());
				QunarPrice qp = qunarPriceService.bizinfo(productId + "", useDate);
				// 门票价格不存在
				Validate.notNull(qp, ErrorCode.ERROR_56006);
				double salePrice = qp.getSalePrice();
				double qunarPrice = qp.getQunarPrice();
				// 门票价格改变
				Validate.isTrue(salePrice * subOrder.getCount() == subOrder.getTotalAmount(), ErrorCode.ERROR_56006, "价格数据已经变化");
				// 获取游客列表
				Map<String, Object> qunarOrderMap = new HashMap<String, Object>();
				qunarOrderMap.put("orderQunarId", orderQunarId);
				List<QunarPassenger> passengerList = qunarPassengerService.list(qunarOrderMap);
				// 旧的qunar订单，更新ordernum后插入新的订单，其余条件不变
				QunarOrder oldOrder = qunarOrderService.info(orderQunarId);
				oldOrder.setOrderNum("qunar" + sequenceService.getOrderQunarSeq());
				oldOrder.setQunarPrice(qunarPrice + "");
				oldOrder.setPayStatus(QunarOrder.PAY_STATUS_FAILED);
				qunarOrderService.insert(oldOrder);
				// 插入新的游客表（新旧游客列表不变，可考虑更新而不是插入）
				long newOrderId = oldOrder.getId();
				if (passengerList != null && passengerList.size() > 0) {
					for (QunarPassenger passenger : passengerList) {
						passenger.setOrderQunarId(newOrderId);
						qunarPassengerService.insert(passenger);
					}
				}
				QunarOrder newOrder = qunarOrderService.createOrder(oldOrder, passengerList);
				subOrder.setRelateOrderId(newOrderId);
				newOrder.setSubOrderId(subOrder.getId());
				scenicTicketSubOrderService.update(subOrder);
				qunarOrderService.update(newOrder);
			} else if (subOrder.getTicketType() == TYPE_RENWOYOU) {
				TicketRenwoyou scenicTicket = ticketRenwoyouService.info(subOrder.getScenicTicketId());
				// 价格数据已经改变
				Validate.isTrue(scenicTicket.getLvxbangPrice() * subOrder.getCount() == subOrder.getTotalAmount(), ErrorCode.ERROR_56006,
						"价格数据已经变化");
				// 下单
				OrderRenwoyou orderRenwoyou = preOrderRenwoyou(scenicTicketOrder, subOrder);
				// 更新外部订单id
				scenicTicketSubOrderService.update(subOrder);
			}
		}
		// 付款
		payOrder(Long.parseLong(id));
	}


	public void processSpringOrder(ScenicTicketOrder scenicTicketOrder) {
		// 特殊处理订单
		scenicTicketOrder.setStatus(ScenicTicketOrder.STATUS_CHECKED);
		update(scenicTicketOrder);

		String url = "http://weixin.lvxbang.com/vticket/checkTicket/genCode.jhtml";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			ScenicTicketSubOrder scenicTicketSubOrder = scenicTicketOrder.getSubOrders().get(0);
			String productId = scenicTicketSubOrder.getScenicTicketId() + "";   // 产品标识
			String buyerName = scenicTicketOrder.getBuyerName();   // 购买人
			String buyerMobile = scenicTicketOrder.getMobile(); // 购买人手机号
			String orderCount = scenicTicketSubOrder.getCount() + "";   // 购买数量
//            String userId = scenicTicketOrder.getUserId() + "";            // 用户标识
			String orderNo = scenicTicketOrder.getOrderNum();          // 订单号（订单标识）
			Long supplierId = scenicTicketSubOrder.getRelateOrderId();
			Long orderId = scenicTicketOrder.getId();

			Map<Long, String> supplierMap = new HashMap<Long, String>();
			supplierMap.put(1L, "港中旅");
			supplierMap.put(2L, "自行车网点1");
			supplierMap.put(3L, "自行车网点2");
			supplierMap.put(4L, "自行车网点3");
			supplierMap.put(5L, "自行车网点4");
			supplierMap.put(6L, "自行车网点5");
			supplierMap.put(7L, "自行车网点6");
			supplierMap.put(8L, "自行车网点7");
			supplierMap.put(9L, "自行车网点8");
			supplierMap.put(10L, "直通车1");
			supplierMap.put(11L, "直通车2");
			supplierMap.put(12L, "直通车3");
			supplierMap.put(13L, "直通车4");
			supplierMap.put(14L, "直通车5");
			supplierMap.put(15L, "集散服务网点1");
			supplierMap.put(16L, "集散服务网点2");
			supplierMap.put(17L, "集散服务网点3");
			supplierMap.put(18L, "集散服务网点4");
			supplierMap.put(19L, "集散服务网点5");
			supplierMap.put(20L, "集散服务网点6");
			supplierMap.put(21L, "沿线网点1");
			supplierMap.put(22L, "沿线网点2");
			supplierMap.put(23L, "沿线网点3");
			supplierMap.put(24L, "沿线网点4");
			supplierMap.put(25L, "沿线网点5");
			supplierMap.put(26L, "沿线网点6");
			supplierMap.put(27L, "旅行帮");

			String supplierName = supplierMap.get(supplierId);

			Map<String, String> param = new HashMap<String, String>();
			param.put("productId", productId);
			param.put("buyerName", buyerName);
			param.put("buyerMobile", buyerMobile);
			param.put("orderCount", orderCount);
//            param.put("userId", userId);
			param.put("orderNo", orderNo);
			param.put("supplierId", supplierId.toString());
			param.put("supplierName", supplierName);
			param.put("orderId", orderId.toString());

			System.out.println(param);
			String result = HttpUtil.postStrFromUrl(url, param);
			Map resultMap = new ObjectMapper().readValue(result, Map.class);
			if ("true".equals(resultMap.get("success"))) {
				//
				System.out.println("处理成功");
			} else {
				System.out.println("处理失败:" + resultMap.get("errorMsg"));
			}
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}

}
