package com.hmlyinfo.app.soutu.scenicTicket.service;

import com.google.common.collect.Lists;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.domain.OrderRenwoyou;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.util.HttpUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任我游的服务
 * Created by Fox on 2015-03-25,0025.
 */
@Service
public class RenwoyouService {

    public static String VALIDATE_RESULT_SUCCESS = "OK";

    String url = "http://www.zmyou.net/api/market/scenic!api.action";
    private static final String userName = "18659253371";
    private static final String password = "0659c7992e268962384eb17fafe88364";
    private static final String md5key = "die4x3v5qull6vd7";

    //todo 任我游提供的测试账号，密码，key等
//    public static final String url = "http://test.zmyou.com/union/api/market/scenic!api.action";
//    private static final String userName = "api_test";
//    private static final String password = "c4ca4238a0b923820dcc509a6f75849b";
//    private static final String md5key = "ot7M30XwoGL35IOl";

    @Autowired
    private OrderRenwoyouService orderRenwoyouService;
    /**
     *是否满足订单要求
     * @throws Exception 
     */
    public String ifSatisfyPreOrderTicket(ScenicTicketOrder scenicTicketOrder, long outerTicketId, int count, String ticketDate) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        /*下单项集合请求数据*/
        List<Map<String, Object>> orderItemList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", outerTicketId);//任我游那边门票的id，对应的是outer_ticket_id
        map.put("qty", count);
        map.put("startDate", ticketDate);
        orderItemList.add(map);
        /*任我游请求的数据*/
        params.put("idCardNo", scenicTicketOrder.getIdCardNo());
        params.put("action", "CAN_ORDER");
        params.put("mobile", scenicTicketOrder.getMobile());
        params.put("orderItemList", orderItemList);
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw e;
        }
        // 组装参数，请求远程api，获取景点信息
        Map<String, String> paramMap = prepareParam(bodyStr);
        String JsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        // 格式化返回的json数据
        return getValidateResult(JsonStr);
    }

    /**
     * 预备参数,由 u、p、body和系统分配的16位的key，按u+p+body+key的顺序，直接连接起来，进行一次md5计算
     */
    public Map<String, String> prepareParam(String bodyStr) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("url", url);
        paramMap.put("u", userName);
        paramMap.put("p", password);
        paramMap.put("body", bodyStr);
        String md5 = DigestUtils.md5Hex(userName + password + bodyStr + md5key);
        paramMap.put("sign", md5);
        return paramMap;
    }

    /**
     * 任我游服务器返回的结果
     * @throws IOException 
     */
    public String getValidateResult(String jsonStr) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(jsonStr, Map.class);
            if ("OK".equalsIgnoreCase(map.get("status").toString())) {
                return "OK";
            }
            return map.get("body").toString();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 返回任我游服务器结果
     */
    Map<String, Object> toZMYouResult(String jsonStr) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(jsonStr, Map.class);
            if (!"OK".equalsIgnoreCase(map.get("status").toString())) {
                return map;
            }

            String body = map.get("body").toString();
            Map<String, Object> bodyMap = mapper.readValue(body, Map.class);
            bodyMap.put("status","OK");
            return bodyMap;
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
    }

    /**
     * 返回任我游服务器结果
     * @param jsonStr
     * @return
     */
    public List renwoyouResult(String jsonStr) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> scenicMap = mapper.readValue(jsonStr, Map.class);
            if (!"OK".equalsIgnoreCase(scenicMap.get("status").toString())) {
                throw new BizValidateException(ErrorCode.ERROR_51001, scenicMap.get("body").toString());
            }
            String body = scenicMap.get("body").toString();
            return mapper.readValue(body, List.class);
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
    }

    /**
     * 预下单
     */
    public Map<String, Object> preOrderTicket(ScenicTicketOrder scenicTicketOrder, String orderNum, long outerTicketId, int count, String ticketDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map<String, Object>> orderItemList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qty", count);
        map.put("id", outerTicketId);
        map.put("startDate", ticketDate);
        orderItemList.add(map);
        params.put("orderItemList", orderItemList);
        params.put("action", "PRE_ORDER");
        params.put("buyer",scenicTicketOrder.getBuyerName());
        params.put("mobile", scenicTicketOrder.getMobile());
        //params.put("outOrderNo",id);//本地系统表的id
        params.put("outOrderNo", orderNum);//本地系统表的订单编号
        params.put("idCardNo", scenicTicketOrder.getIdCardNo());
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
        // 组装参数，请求远程api，获取景点门票信息
        Map<String, String> paramMap = prepareParam(bodyStr);
        String JsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        // 格式化返回的json数据
        return toZMYouResult(JsonStr);
    }

    /**
     * 查询下过订单的具体信息
     * @param no
     */
    public Map<String, Object> getOrderDetail(long no) {
    	OrderRenwoyou orderRenwoyou = orderRenwoyouService.info(no);
    	
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "GET_ORDER_DETAIL");
        params.put("no",orderRenwoyou.getOrderNum());
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
        // 组装参数，请求远程api，获取订单信息
        Map<String, String> paramMap = prepareParam(bodyStr);
        String JsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        return toZMYouResult(JsonStr);
    }

    /**
     * 根据任我游订单进行支付确认
     * @param id
     * @return 支付结果
     * @throws Exception 
     */
    public boolean payRenwoyouOrder(Long id) throws Exception {
        OrderRenwoyou orderRenwoyou = orderRenwoyouService.info(id);
        String result = payOrder(orderRenwoyou);
        if (result == null) {
            orderRenwoyou.setStatus(OrderRenwoyou.STATUS_PAID);
            orderRenwoyouService.update(orderRenwoyou);
            return true;
        } else {
            orderRenwoyou.setStatus(OrderRenwoyou.STATUS_FAILD);
            orderRenwoyou.setFailReason(result);
            orderRenwoyouService.update(orderRenwoyou);
            return false;
        }
    }

    /**
     * 支付订单
     * @param
     * @throws IOException 
     */
    public String payOrder(OrderRenwoyou orderRenwoyou) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "PAY_ORDER");
        //params.put("no", scenicTicketOrder.getOuterId());
        //params.put("outOrderNo",orderRenwoyou.getId());
        params.put("outOrderNo",orderRenwoyou.getOrderNum());
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw e;
        }
        // 组装参数，请求远程api，获取订单信息
        Map<String, String> paramMap = prepareParam(bodyStr);
        String JsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        String validateResult = getValidateResult(JsonStr);
        if (validateResult.equals(RenwoyouService.VALIDATE_RESULT_SUCCESS)) {
            return null;
        }
        return validateResult;
    }

    /**
     * 订单退款退票
     * @throws Exception 
     */
    public String refundTicket(OrderRenwoyou orderRenwoyou) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "REFUND_ORDER");
        //params.put("outOrderNo", orderRenwoyou.getId());
        params.put("outOrderNo", orderRenwoyou.getOrderNum());
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
        // 组装参数，请求远程api，获取订单信息
        Map<String, String> paramMap = prepareParam(bodyStr);
        String JsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        return getValidateResult(JsonStr);
    }

    /**
     * 发送短信
     * @param orderRenwoyou
     * @return
     */
    public List<Map<String, Object>> sendSms(OrderRenwoyou orderRenwoyou) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "SEND_SMS");
        //params.put("outOrderNo", orderRenwoyou.getId());//任我游订单的id
        params.put("outOrderNo", orderRenwoyou.getOrderNum());
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
        //组装参数，请求远程api
        Map<String, String> paramMap = prepareParam(bodyStr);
        String jsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        return renwoyouResult(jsonStr);
    }
    
    
    /**
     * 库存查询
     * @param
     */
    @SuppressWarnings("unchecked")
	public int availableCount(String ticketId, String date) {
    	
    	int count = 0;
    	
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "GET_SCENIC_TICKET_INVT");
        params.put("id", ticketId);
        params.put("startDate", date);
        params.put("endDate", date);
        params.put("idList", Lists.newArrayList(ticketId));
        String bodyStr;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(params);
        } catch (IOException e) {
            throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
        }
        // 组装参数，请求远程api，获取订单信息
        Map<String, String> paramMap = prepareParam(bodyStr);
        String jsonStr = HttpUtil.postStrFromUrl(url, paramMap);
        
        // 格式化返回的json数据
		Map<String, Object> map;
		try {
			map = new ObjectMapper().readValue(jsonStr, Map.class);
		} catch (Exception e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage() + " 格式化任我游返回数据出现错误");
		}
		if (!"OK".equalsIgnoreCase(map.get("status").toString()) || map.get("body") == null) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "任我游返回数据错误");
		}
		// 返回数据列表
		String bodyString = map.get("body").toString();
		List<Map<String, Object>> body = new ArrayList<Map<String,Object>>();
		try {
			body = new ObjectMapper().readValue(bodyString, ArrayList.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage() + " 格式化任我游返回数据出现错误");
		}
		if(body.size() == 0){
			return count;
		}
		Map<String, Object> invtMap = body.get(0);
		count = Integer.parseInt(invtMap.get("qty").toString());
        
        return count;
    }
}
