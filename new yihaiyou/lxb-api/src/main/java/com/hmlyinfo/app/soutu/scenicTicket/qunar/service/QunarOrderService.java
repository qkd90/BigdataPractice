package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.common.service.SequenceService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketSubOrder;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.ScenicTicketSubOrderMapper;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarContant;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarOrder;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPassenger;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPrice;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarOrderMapper;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarTicketMapper;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketSubOrderService;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class QunarOrderService extends BaseService<QunarOrder, Long> {

	private static final String									QUNAR_ORDER_TYPE	= "001";
	private static final String									PARTNER_CODE		= "3999347859";
	private static final String									PARTNER_KEY			= "a61ac440fe54d5e99025d5541aa12038";

	private static final String									VERSION				= "1.0.0";
	private static final String									CREATE_ORDER_URL	= "http://b2b.piao.qunar.com/openapi/createOrder";
	private static final String									REQUEST_PAY_URL		= "http://b2b.piao.qunar.com/openapi/requestPay";
	private static final String									RESEND_CODE_URL		= "http://b2b.piao.qunar.com/openapi/resendCode";
	private static final String									ORDER_DETAIL_URL	= "http://b2b.piao.qunar.com/openapi/orderDetail";

	private ObjectMapper										objectMapper		= new ObjectMapper();

	@Autowired
	private QunarOrderMapper<QunarOrder>						mapper;
	@Autowired
	private QunarTicketMapper<QunarTicket>						ticketMapper;
	@Autowired
	private QunarPriceService									priceService;
	@Autowired
	private QunarTicketService									qunarTicketService;
	@Autowired
	private QunarPassengerService								qunarPassengerService;
	@Autowired
	private ScenicTicketSubOrderMapper<ScenicTicketSubOrder>	subMapper;
	@Autowired
	private ScenicTicketOrderService							scenicTicketOrderService;
	@Autowired
	private ScenicTicketSubOrderService							scenicTicketSubOrderService;
	@Autowired
	private SequenceService										sequenceService;

	private ObjectMapper										om					= new ObjectMapper();

	@Override
	public BaseMapper<QunarOrder> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	/**
	 * 预下单
	 *
	 * @param scenicTicketOrder
	 * @param subOrder
	 * @return
	 * @throws Exception
	 */
	public QunarOrder preOrderQunar(ScenicTicketOrder scenicTicketOrder, ScenicTicketSubOrder subOrder) {

		String failString = "";
		long ticketId = subOrder.getScenicTicketId();

		String useDate = subOrder.getTicketDate();
		// 创建去哪儿订单并生成价格

		QunarTicket qunarTicket = qunarTicketService.info(ticketId);
		long productId = Long.parseLong(qunarTicket.getProductId());
		// 订单名称
		scenicTicketOrder.setOrderName(qunarTicket.getProductName());
		QunarPrice qp = priceService.bizinfo(productId + "", useDate);
		double salePrice = 0;
		double qunarPrice = 0;
		// 门票价格不存在
		if (qp == null) {
			failString = failString + "门票价格不存在";
		} else {
			salePrice = qp.getSalePrice();
			qunarPrice = qp.getQunarPrice();
		}

		subOrder.setTotalAmount(salePrice * subOrder.getCount());

		String contactString = "";
		try {
			contactString = om.writeValueAsString(subOrder.getExt());
		} catch (Exception e) {
			failString = failString + ", 游客信息错误" + e.getMessage();
		}

		QunarOrder newOrder = new QunarOrder();

		List<QunarPassenger> passengerList = new ArrayList<QunarPassenger>();
		QunarContant qunarContant = new QunarContant();
		try {
			qunarContant = new ObjectMapper().readValue(contactString, QunarContant.class);

			newOrder.setContactMobile(qunarContant.getMobile());
			newOrder.setContactUser(qunarContant.getName());
			newOrder.setContactUserPinyin(toPinYin(qunarContant.getName()));
			newOrder.setContactEmail(qunarContant.getEmail());
			newOrder.setContactPostCode(qunarContant.getPostalCode());
			newOrder.setContactPostAddress(qunarContant.getPostalInfo());

			passengerList = qunarContant.getYwrArr();

		} catch (Exception e) {
			failString = failString + ", 游客信息格式化错误" + e.getMessage();
		}

		newOrder.setOrderNum("qunar" + sequenceService.getOrderQunarSeq());
		newOrder.setSubOrderId(subOrder.getId());
		newOrder.setQuantity(subOrder.getCount());
		newOrder.setUseDate(subOrder.getTicketDate());
		newOrder.setScenicId(subOrder.getScenicId());
		newOrder.setProductId(productId + "");
		newOrder.setQunarPrice(qunarPrice + "");
		newOrder.setPayStatus(QunarOrder.PAY_STATUS_FAILED);
		newOrder.setFailReason(failString);

		if ("".equals(failString)) {
			createOrder(newOrder, passengerList);
		} else {
			subOrder.setStatus(ScenicTicketSubOrder.STATUS_FAILED);
		}
		insert(newOrder);

		if (newOrder.getFailReason() != null && !"".equals(newOrder.getFailReason())) {
			subOrder.setStatus(ScenicTicketSubOrder.STATUS_FAILED);
		}

		long newOrderId = newOrder.getId();
		if (passengerList != null && passengerList.size() > 0) {
			for (QunarPassenger passenger : passengerList) {
				passenger.setOrderQunarId(newOrderId);
				qunarPassengerService.insert(passenger);
			}
		}
		return newOrder;

	}

	// 创建订单
	public QunarOrder createOrder(QunarOrder newOrder, List<QunarPassenger> passengerList) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		// 分销商订单Id
		params.put("distributorOrderId", newOrder.getOrderNum());
		// 订单联系电话
		params.put("contactMobile", newOrder.getContactMobile());
		//
		if (newOrder.getContactUser() != null && !newOrder.getContactUser().equals("")) {
			params.put("contactUser", newOrder.getContactUser());
			params.put("contactUserPinyin", toPinYin(newOrder.getContactUser()));
		}
		//
		if (newOrder.getContactEmail() != null && !newOrder.getContactEmail().equals("")) {
			params.put("contactEmail", newOrder.getContactEmail());
		}
		//
		if (newOrder.getContactPostCode() != null && !newOrder.getContactPostCode().equals("")) {
			params.put("contactPostCode", newOrder.getContactPostCode());
		}
		//
		if (newOrder.getContactPostAddress() != null && !newOrder.getContactPostAddress().equals("")) {
			params.put("contactPostAddress", newOrder.getContactPostAddress());
		}
		// 购买门票数量
		params.put("quantity", newOrder.getQuantity() + "");
		//
		if (newOrder.getUseDate() != null && !newOrder.getUseDate().equals("")) {
			params.put("useDate", newOrder.getUseDate());
		}
		// 产品ID
		params.put("productId", newOrder.getProductId());
		// 单个产品分销价
		params.put("qunarPrice", newOrder.getQunarPrice());

		if (passengerList != null && passengerList.size() > 0) {
			int i = 0;
			String forString = "passengers[";
			for (QunarPassenger passenger : passengerList) {

				String passengerName = forString + i + "].name";
				String passengerPinyin = forString + i + "].namePinyin";
				String passengerIdNumber = forString + i + "].idNumber";
				String passengerIdType = forString + i + "].idType";
				String passengerUserDefineI = forString + i + "].userDefineI";
				String passengerUserDefineII = forString + i + "].userDefineII";
				i++;
				//
				if (passenger.getName() != null && !passenger.getName().equals("")) {
					params.put(passengerName, passenger.getName());
					params.put(passengerPinyin, toPinYin(passenger.getName()));
				}
				//
				if (passenger.getIdCard() != null && !passenger.getIdCard().equals("")) {
					params.put(passengerIdType, "0");
					params.put(passengerIdNumber, passenger.getIdCard());
				}

				//
				if (passenger.getPassport() != null && !passenger.getPassport().equals("")) {
					params.put(passengerIdType, "1");
					params.put(passengerIdNumber, passenger.getPassport());
				}
				//
				if (passenger.getTaiwanPermit() != null && !passenger.getTaiwanPermit().equals("")) {
					params.put(passengerIdType, "4");
					params.put(passengerIdNumber, passenger.getTaiwanPermit());
				}
				//
				if (passenger.getHkAndMacauPermit() != null && !passenger.getHkAndMacauPermit().equals("")) {
					params.put(passengerIdType, "8");
					params.put(passengerIdNumber, passenger.getHkAndMacauPermit());
				}
				//
				if (passenger.getUserDefinedI() != null && !passenger.getUserDefinedI().equals("")) {
					params.put(passengerUserDefineI, passenger.getUserDefinedI());
				}
				//
				if (passenger.getUserDefinedIi() != null && !passenger.getUserDefinedIi().equals("")) {
					params.put(passengerUserDefineII, passenger.getUserDefinedIi());
				}

			}
		}

		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);

		String failReasonString = "";
		Map<String, Object> resMap = null;
		try {
			resMap = postStrFromRest(CREATE_ORDER_URL, params);
		} catch (Exception e) {
			// 接下来会处理失败，所以此处不处理
			failReasonString += e.getMessage();
		}

		if (resMap == null || resMap.get("status") != null) {
			newOrder.setOrderStatus(QunarOrder.ORDER_STATUS_FAILED);
			newOrder.setFailReason(failReasonString + resMap.get("errorMessage").toString());
		} else {
			String qunarOrderId = (String) resMap.get("orderId");
			newOrder.setQunarOrderId(qunarOrderId);
			newOrder.setOrderStatus(QunarOrder.ORDER_STATUS_SUCCESS);
		}

		return newOrder;
	}

	// 支付
	public boolean qunarPay(Long id) throws Exception {
		QunarOrder payOrder = mapper.selById(id.toString());
		String orderId = payOrder.getQunarOrderId();
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("orderId", orderId);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(REQUEST_PAY_URL, params);

		if (resMap.get("status") == null) {
			payOrder.setPayAmount((Integer) resMap.get("payAmount"));
			payOrder.setPayStatus(QunarOrder.PAY_STATUS_SUCCESS);
			mapper.update(payOrder);
			return true;
		} else {
			payOrder.setPayStatus(QunarOrder.PAY_STATUS_FAILED);
			payOrder.setFailReason((String) resMap.get("errorMessage"));
			mapper.update(payOrder);
			return false;
		}

	}

	// 重发码
	public Map<String, Object> resendCode(String orderId) throws Exception {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("orderId", orderId);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(RESEND_CODE_URL, params);

		return resMap;
	}

	// 重新下单
	public void qunarReorder(ScenicTicketOrder scenicTicketOrder, ScenicTicketSubOrder subOrder) throws Exception {
		Date date = new Date();
		Long id = subOrder.getRelateOrderId();
		QunarOrder oldOrder = mapper.selById(id.toString());

		// 查询门票价格数据
		long ticketId = subOrder.getScenicTicketId();
		String useDate = subOrder.getTicketDate();
		QunarTicket qunarTicket = qunarTicketService.info(ticketId);
		long productId = Long.parseLong(qunarTicket.getProductId());
		QunarPrice qp = priceService.bizinfo(productId + "", useDate);
		// 门票价格不存在
		Validate.notNull(qp, ErrorCode.ERROR_56006);
		double salePrice = qp.getSalePrice();
		double qunarPrice = qp.getQunarPrice();
		// 判断订单是否已经过了支付时间
		int delayTime = qunarTicket.getDelayPayTime();
		if (((date.getTime() - oldOrder.getModifyTime().getTime()) / 60000) < delayTime) {
			return;
		}
		subOrder.setTotalAmount(subOrder.getCount() * salePrice);
		scenicTicketOrder.setTotalFee(subOrder.getTotalAmount());
		// 获取游客列表
		Map<String, Object> qunarOrderMap = new HashMap<String, Object>();
		qunarOrderMap.put("orderQunarId", id);
		List<QunarPassenger> passengerList = qunarPassengerService.list(qunarOrderMap);
		// 旧的qunar订单，更新ordernum后插入新的订单，其余条件不变
		oldOrder.setOrderNum("qunar" + sequenceService.getOrderQunarSeq());
		oldOrder.setQunarPrice(qunarPrice + "");
		oldOrder.setPayStatus(QunarOrder.PAY_STATUS_FAILED);
		this.insert(oldOrder);
		// 插入新的游客表（新旧游客列表不变，可考虑更新而不是插入）
		long newOrderId = oldOrder.getId();
		if (passengerList != null && passengerList.size() > 0) {
			for (QunarPassenger passenger : passengerList) {
				passenger.setOrderQunarId(newOrderId);
				qunarPassengerService.insert(passenger);
			}
		}
		// 下单
		QunarOrder newOrder = this.createOrder(oldOrder, passengerList);
		subOrder.setRelateOrderId(newOrderId);
		newOrder.setSubOrderId(subOrder.getId());
		scenicTicketSubOrderService.update(subOrder);
		this.update(newOrder);
		// 更新总订单和子订单
		scenicTicketSubOrderService.update(subOrder);
		scenicTicketOrderService.update(scenicTicketOrder);

	}

	// 查询订单详情（根据分销商订单id）
	public Map<String, Object> orderDetail(Long distributorOrderId) throws Exception {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("distributorOrderId", distributorOrderId.toString());
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(ORDER_DETAIL_URL, params);

		return resMap;
	}

	// 申请退款

	// 接收出票通知
	public Map<String, String> qunarTicketing(final HttpServletRequest request) {

		Map<String, String> params = getParameterMap(request);

		String sign = params.get("sign");
		// checkResult为true，则验签通过
		// SignUtil.checkSign详见附录5.1.1
		boolean checkResult = SignUtil.checkSign(params, "partnerKey", sign);

		Map<String, String> responseMap = new HashMap<String, String>();
		if (checkResult) {
			responseMap.put("status", "true");
			String resSign = SignUtil.sign(params, PARTNER_KEY);
			responseMap.put("sign", resSign);

			final String id = params.get("distributorOrderId");
			final String status = params.get("status").toString();
			new Thread() {
				@Override
				public void run() {

					QunarOrder oldOrder = mapper.selById(id);
					if (status.equals("true")) {
						oldOrder.setTicketingStatus(QunarOrder.TICKETING_STATUS_SUCCESS);
					} else {
						oldOrder.setTicketingStatus(QunarOrder.TICKETING_STATUS_FAILED);
					}
					mapper.update(oldOrder);
				}
			}.start();

		} else {
			responseMap.put("status", "false");
			String resSign = SignUtil.sign(params, PARTNER_KEY);
			responseMap.put("sign", resSign);
		}

		return responseMap;
	}

	// 退款通知
	public Map<String, String> refundPay(final HttpServletRequest request) {
		Map<String, String> params = getParameterMap(request);

		String sign = params.get("sign");
		// checkResult为true，则验签通过
		// SignUtil.checkSign详见附录5.1.1
		boolean checkResult = SignUtil.checkSign(params, "partnerKey", sign);

		Map<String, String> responseMap = new HashMap<String, String>();
		if (checkResult) {
			responseMap.put("status", "true");
			String resSign = SignUtil.sign(params, PARTNER_KEY);
			responseMap.put("sign", resSign);
		} else {
			responseMap.put("status", "false");
			String resSign = SignUtil.sign(params, PARTNER_KEY);
			responseMap.put("sign", resSign);
		}

		return responseMap;
	}

	// 调用Qunar提供的API
	public Map<String, Object> postStrFromRest(String url, Map<String, String> paramMap) throws Exception {
		String strRes = HttpUtil.postStrFromUrl(url, paramMap);

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> paramSign = new HashMap<String, String>();
		try {
			params = objectMapper.readValue(strRes, Map.class);
			paramSign = objectMapper.readValue(strRes, Map.class);
		} catch (Exception e) {

			throw e;
		}

		// 如果没有正常返回就把出错信息返回
		if ((Boolean) params.get("status") == false) {
			return params;
		}
		if (params.get("data") == null) {
			return params;
		}

		String data = "";
		if (SignUtil.checkSign(paramSign, PARTNER_KEY, paramSign.get("sign"))) {
			// 解密后得到业务数据
			// BASE64Utils.decode详见附录5.1.2
			data = BASE64Utils.decode(paramSign.get("data"));
		}

		// 处理直接返回以[]包起来的MapList
		if (data.indexOf('[') == 0) {
			data = "{\"datalist\":" + data + "}";
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			resMap = objectMapper.readValue(data, Map.class);
		} catch (Exception e) {
			throw e;
		}
		return resMap;
	}

	// 根据字段是否传入判断是否放在请求Map中
	public Map<String, String> readyMap(Map<String, String> params, Map<String, Object> paramMap, String targetkey) {
		if (paramMap.get(targetkey) != null) {
			params.put(targetkey, (String) paramMap.get(targetkey));
		}
		return params;
	}

	// 汉字转化为拼音
	public static String toPinYin(String str) {
		String py = "";
		String[] t = new String[str.length()];

		char[] hanzi = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			hanzi[i] = str.charAt(i);
		}

		net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat t1 = new HanyuPinyinOutputFormat();
		t1.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t1.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t1.setVCharType(HanyuPinyinVCharType.WITH_V);

		try {
			for (int i = 0; i < str.length(); i++) {
				if ((str.charAt(i) >= 'a' && str.charAt(i) < 'z') || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
						|| (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
					py += str.charAt(i);
				} else {
					if (hanzi[i] == '厦') {
						t = new String[]{"xia"};
					} else {
						t = PinyinHelper.toHanyuPinyinStringArray(hanzi[i], t1);
					}
					if (t == null) {
						continue;
					}
					py = py + t[0];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}

		return py.trim().toString();
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	// 包装重发码返回数据
	public List<Map<String, Object>> sendSms(QunarOrder orderQunar) throws Exception {
		Map<String, Object> resMap = resendCode(orderQunar.getQunarOrderId());
		boolean status = (Boolean) resMap.get("status");
		Validate.isTrue(status, ErrorCode.ERROR_51001, (String) resMap.get("errorMessage"));
		return null;
	}

}
