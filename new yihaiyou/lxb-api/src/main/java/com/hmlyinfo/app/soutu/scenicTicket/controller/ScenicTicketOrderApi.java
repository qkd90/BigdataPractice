package com.hmlyinfo.app.soutu.scenicTicket.controller;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/scenicTicketOrder")
public class ScenicTicketOrderApi
{
	@Autowired
	private ScenicTicketOrderService service;


	/**
	 * 查询订单列表
	 *
	 * @return
	 */
	@RequestMapping("/list")
	public
	@ResponseBody
	ActionResult list(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("userId", MemberService.getCurrentUserId());
		paramMap.put("orderColumns", "create_time");
		paramMap.put("orderType", "desc");
		List<ScenicTicketOrder> scenicTicketOrderList = service.listOrder(paramMap);
		return ActionResult.createSuccess(scenicTicketOrderList);
	}

	/**
	 * 查询订单列表
	 *
	 * @return
	 */
	@RequestMapping("/listWithDetail")
	public
	@ResponseBody
	ActionResult listWithDetail(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("userId", MemberService.getCurrentUserId());
		paramMap.put("orderColumn", "create_time");
		paramMap.put("orderType", "desc");
		List<ScenicTicketOrder> scenicTicketOrderList = service.listWithDetail(paramMap);
		return ActionResult.createSuccess(scenicTicketOrderList);
	}

    /**
     * 计数接口
     */
    @RequestMapping("/count")
    @ResponseBody
    public ActionResult count(final HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("userId", MemberService.getCurrentUserId());
        return service.countAsResult(paramMap);
    }

	/**
	 * 查询下过的订单的具体信息，根据返回来的订单编号查询。no
	 * @return
	 */
	@RequestMapping("/getOrderDetail")
	@ResponseBody
	public ActionResult getOrderDetail(long no,int type){
		return ActionResult.createSuccess(service.getOrderDetail(no,type));
	}

	/**
	 * 获取总订单详情， 本地
	 * @return
	 */
	@RequestMapping("/info")
	@ResponseBody
	public ActionResult info(long id){
		return ActionResult.createSuccess(service.info(id));
	}

	/**
	 * 预订票接口
	 *
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/preOrder")
	@ResponseBody
	public ActionResult preOrder(HttpServletRequest request) throws Exception {
		String jsonStr = request.getParameter("json");
		ScenicTicketOrder scenicTicketOrder = new ObjectMapper().readValue(jsonStr, ScenicTicketOrder.class);
		if (request.getParameter("supplierId") != null) {
			scenicTicketOrder.getSubOrders().get(0).setRelateOrderId(Long.parseLong(request.getParameter("supplierId").toString()));
		}
		scenicTicketOrder.setUserId(MemberService.getCurrentUserId());
		service.preOrder(scenicTicketOrder);
		return ActionResult.createSuccess(scenicTicketOrder);
	}

	/**
	 * 返回preOrderId
	 * @throws Exception 
	 */
	@RequestMapping("/prePay")
	@ResponseBody
	public ActionResult prePay(Long id, String ip) throws Exception {
		Long userId = MemberService.getCurrentUserId();
		return ActionResult.createSuccess(service.prePay(id, ip, userId));
	}

//	/**
//	 * 支付订单，根据传入的id，查询对应订单信息，进行支付。支持批量支付
//	 *
//	 * @return
//	 */
//	@RequestMapping("/payOrder")
//	@ResponseBody
//	public ActionResult payOrder(Long id) {
//		service.payOrder(id);
//		return ActionResult.createSuccess();
//	}

	/**
	 *退票
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/refundTicket")
	@ResponseBody
	public ActionResult refundTicket(HttpServletRequest request) throws Exception{
		Long id = Long.valueOf(request.getParameter("id"));
		int type = Integer.valueOf(request.getParameter("type"));
		return ActionResult.createSuccess(service.refundTicket(id, type));
	}

	/**
	 * 退单
	 * @throws Exception 
	 */
	@RequestMapping("/refundOrder")
	@ResponseBody
	public ActionResult refundOrder(Long id) throws Exception {
		return ActionResult.createSuccess(service.refundOrder(id));
	}

	/**
	 * 删除订单
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ActionResult delete(Long orderId) {
		Validate.notNull(orderId, ErrorCode.ERROR_51001);
		ScenicTicketOrder scenicTicketOrder = service.info(orderId);
		Validate.notNull(scenicTicketOrder, ErrorCode.ERROR_51001);
		if (scenicTicketOrder.getStatus() <= ScenicTicketOrder.STATUS_PAID) {
			service.del(orderId.toString());
			return ActionResult.createSuccess();
		} else {
			return ActionResult.createFail(ErrorCode.ERROR_51001, "已支付订单不能删除");
		}
	}

	/**
	 * 传入一个总订单id,如果超过15分钟将重新下预下订单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/preOrderAgain")
	@ResponseBody
	public ActionResult preOrderAgain(Long id) throws Exception {
		ScenicTicketOrder scenicTicketOrder = service.info(id);
		service.preOrderValidate(scenicTicketOrder);
		return ActionResult.createSuccess(scenicTicketOrder);
	}

	/**
	 *
	 * 发送短信
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/sendSms")
	@ResponseBody
	public ActionResult sendSms(Long id, int type) throws Exception{
		return ActionResult.createSuccess(service.sendSms(id, type));
	}
	
	
	
	/**
	 * 编辑订单
	 *
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editOrder")
	@ResponseBody
	public ActionResult editOrder(HttpServletRequest request) throws Exception {
		String jsonStr = request.getParameter("json");
		String oldId = request.getParameter("id");
		ScenicTicketOrder scenicTicketOrder = new ObjectMapper().readValue(jsonStr, ScenicTicketOrder.class);
		scenicTicketOrder.setUserId(MemberService.getCurrentUserId());
		service.editOrder(scenicTicketOrder, oldId);
		return ActionResult.createSuccess(scenicTicketOrder);
	}
	
	
	/**
	 * Admin处理用户已经付款但是lvxbang未向经销商付款情况
	 * 必选：id{id}
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/orderAndPayOrder")
	@ResponseBody
	public ActionResult payOrder(HttpServletRequest request) throws Exception {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001, "订单id为必选参数");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.orderAndPay(paramMap);
		return ActionResult.createSuccess();
	}
}
