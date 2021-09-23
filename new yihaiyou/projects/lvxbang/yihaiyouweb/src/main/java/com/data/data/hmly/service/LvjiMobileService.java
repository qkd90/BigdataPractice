package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.response.OrderSimpleResponse;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.LvjiOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.entity.enums.OrderWay;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.LvjiUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.util.GenOrderNo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Think on 2017/8/21.
 */
@Service
public class LvjiMobileService {

    @Resource
    private LvjiOrderService lvjiOrderService;
    @Resource
    private TicketService ticketService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private MemberService memberService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private YhyMsgService yhyMsgService;

    public OrderSimpleResponse orderDetail(Long id) {
        LvjiOrder order = lvjiOrderService.query(id);
        OrderSimpleResponse response = new OrderSimpleResponse();
        response.setOrderNo(order.getOrderNo());
        response.setRecName(order.getScenicName());
        response.setStartDate(DateUtils.formatDate(order.getStartDate()));
        response.setWaitTime(1800000);
        response.setName(order.getUserName());
        response.setStatus(order.getStatus().getDescription());
        response.setOrderStatus(order.getStatus());
        response.setPrice(order.getPrice());
        response.setType(OrderType.lvji);
        response.setUrl(order.getUrl());
        return response;
    }

    public LvjiOrder setValueForOrder(Long productId, Member user) {
        Ticket ticket = ticketService.findByTicketId(productId);
        LvjiOrder lvjiOrder = new LvjiOrder();
        lvjiOrder.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
        lvjiOrder.setUser(user);
        lvjiOrder.setTplId(Lists.newArrayList(ticket.getTicketPriceSet()).get(0).getName());
        lvjiOrder.setScenicName(ticket.getTicketName());
        lvjiOrder.setNum(1);
        lvjiOrder.setOrderWay(OrderWay.weixin);
        lvjiOrder.setPrice(Lists.newArrayList(ticket.getTicketPriceSet()).get(0).getMaketPrice());
        lvjiOrder.setStartDate(new Date());
        lvjiOrder.setStatus(OrderStatus.WAIT);
        lvjiOrder.setDeleteFlag(Boolean.FALSE);

        //获取IP地址
        String ip;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**返回 IP 地址字符串（以文本表现形式）*/
            ip = addr.getHostAddress();
        } catch (Exception ex) {
            ip = "";
        }

//        //获取MacAddr
//        String mac = null;
//        try {
//            Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");
//
//            InputStream is = pro.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String message = br.readLine();
//
//            int index = -1;
//            while (message != null) {
//                if ((index = message.indexOf("Physical Address")) > 0) {
//                    mac = message.substring(index + 36).trim();
//                    break;
//                }
//                message = br.readLine();
//            }
//            System.out.println(mac);
//            br.close();
//            pro.destroy();
//        } catch (IOException e) {
//            System.out.println("Can't get mac address!");
//            return null;
//        }

        lvjiOrder.setMacAddr("4A-E2-44-7B-80-DB");

        Map<String, Object> data = Maps.newHashMap();
        data.put("orderNo", lvjiOrder.getOrderNo());
        // @WEB_MSG
        yhyMsgService.doSendSMS(data, lvjiOrder.getMobile(), MsgTemplateKey.LVJI_TICKET_WAIT_PAY_TLE);
        return lvjiOrder;
    }

    public Map<String, Object> cancelOrder(LvjiOrder order) {
        Map<String, Object> result = Maps.newHashMap();
        if (!OrderStatus.WAIT.equals(order.getStatus())) {
            result.put("success", false);
            result.put("errMsg", "订单不可取消");
            return result;
        }
        order.setStatus(OrderStatus.CANCELED);
        order.setModifyTime(new Date());
        lvjiOrderService.updateOrder(order);
        result.put("success", true);
        return result;
    }

    public void saveBalancePay(LvjiOrder order, Member user) {
        memberService.update(user);
        order.setPayType(OrderPayType.ONLINE);
        order.setStatus(OrderStatus.PAYED);
        Map<String, Object> result = LvjiUtil.saveOrder(order);
        if (!"0".equals(result.get("status"))) {
            order.setStatus(OrderStatus.FAILED);
            order.setMsg(result.get("msg").toString());
            lvjiOrderService.updateOrder(order);
            return;
        }
        user.setBalance(user.getBalance() - order.getPrice());
        order.setMsg(result.get("msg").toString());
        Map<String, Object> content = (Map<String, Object>) result.get("content");
        ArrayList<String> urlList = (ArrayList<String>) content.get("urls");
        ArrayList<String> codes = (ArrayList<String>) content.get("codes");
        order.setUrl(urlList.get(0));
        order.setCode(codes.get(0));
        order.setEffDays(Integer.valueOf(content.get("effDays").toString()));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = null;
        try {
            endDate = format.parse(content.get("endDate").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order.setEndDate(endDate);
        order.setLvjiOrderNo(content.get("orderNo").toString());
        order.setProductNotice(content.get("productNotice").toString());
        balanceService.savePayResult(order);
        lvjiOrderService.updateOrder(order);

        Map<String, Object> data = Maps.newHashMap();
        data.put("recName", order.getScenicName());
        data.put("codes", order.getCode());
        // @WEB_MSG
        yhyMsgService.doSendSMS(data, order.getMobile(), MsgTemplateKey.LVJI_TICKET_SUCCESS_TLE);
    }
}
