package com.data.data.hmly.service;

import com.data.data.hmly.enums.FerryIdType;
import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.FerryOrderItem;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderWay;
import com.data.data.hmly.service.order.util.FerryUrl;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristIdType;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.data.data.hmly.util.GenOrderNo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2017-02-06,0006.
 */
@Service
public class FerryWebService {
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private MemberService memberService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private FerryMemberService ferryMemberService;
    @Resource
    private TouristService touristService;
    @Resource
    private YhyMsgService yhyMsgService;

    public Map<String, Object> saveOrder(Map<String, Object> data, Member user, Boolean inPlan) {
        FerryOrder order = new FerryOrder();
        order.setOrderWay(OrderWay.web);
        order.setOrderNumber(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
        order.setDailyFlightGid(data.get("dailyFlightGid").toString());
        order.setFlightNumber(data.get("flightNumber").toString());
        order.setFlightLineName(data.get("flightLineName").toString());
        order.setDepartTime(data.get("departTime").toString());
        order.setStatus(OrderStatus.PROCESSING);
        order.setUser(user);
        Date date = new Date();
        order.setCreateTime(date);
        order.setModifyTime(date);
        ferryOrderService.saveOrder(order);
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) data.get("ferryOrderItemList");
        List<FerryOrderItem> orderItemList = Lists.newArrayList();
        Float price = 0f;
        for (Map<String, Object> map : itemList) {
            FerryOrderItem orderItem = toOrderItem(map, order);
            orderItemList.add(orderItem);
            price += orderItem.getPrice();
        }
        order.setAmount(price);
        order.setSeat(orderItemList.size());
        order.setFerryOrderItemList(orderItemList);
        Map<String, Object> params = getSaveOrderParams(order);
        Map<String, Object> map = FerryUtil.addContacts(order);
        if (!(Boolean) map.get("success")) {
            order.setStatus(OrderStatus.INVALID);
            order.setDeleteFlag(inPlan);
            ferryOrderService.updateOrder(order);
            return map;
        }
        Map<String, Object> resultMap = FerryUtil.post(FerryUrl.SAVE_SALE_ORDER, params);
        Map<String, Object> result = Maps.newHashMap();
        if (!"0".equals(resultMap.get("code").toString())) {
            order.setStatus(OrderStatus.INVALID);
            order.setDeleteFlag(inPlan);
            ferryOrderService.updateOrder(order);
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
            return result;
        }
        String ferryNumber = ((Map) ((Map) resultMap.get("content")).get("SaleOrder")).get("sno").toString();
        order.setFerryNumber(ferryNumber);
        Integer minute = Integer.valueOf(propertiesManager.getString("ORDER_FERRY_PAY_WAIT_TIME"));
        order.setWaitTime(DateUtils.add(new Date(), Calendar.MINUTE, minute));
        order.setStatus(OrderStatus.WAIT);
        ferryOrderService.updateOrder(order);
        result.put("success", true);
        result.put("order", order);
        Map<String, Object> msg = Maps.newHashMap();
        msg.put("orderNo", order.getOrderNumber());
        msg.put("timeout", minute);
        // @WEB_MSG
        yhyMsgService.doSendSMS(msg, order.getUser().getFerryMember().getMobile(), MsgTemplateKey.WEB_FERRY_ORDER_SUCCESS_TLE);
        return result;
    }

    public FerryOrderItem toOrderItem(Map<String, Object> map, FerryOrder order) {
        FerryOrderItem orderItem = new FerryOrderItem();
        orderItem.setTicketId(map.get("ticketId").toString());
        orderItem.setTicketName(map.get("ticketName").toString());
        orderItem.setPrice(Float.valueOf(map.get("price").toString()));
        orderItem.setNumber(map.get("number").toString());
        orderItem.setIdTypeName(FerryIdType.valueOf(map.get("idType").toString()));
        orderItem.setName(map.get("name").toString());
        orderItem.setIdnumber(map.get("idnumber").toString());
        orderItem.setMobile(map.get("mobile").toString());
        Date date = new Date();
        orderItem.setCreateTime(date);
        orderItem.setModifyTime(date);
        orderItem.setFerryOrder(order);
        ferryOrderService.saveItem(orderItem);
        return orderItem;
    }

    public Map<String, Object> getSaveOrderParams(FerryOrder order) {
        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> saleOrder = Maps.newLinkedHashMap();
        saleOrder.put("ebusinessOrder", order.getOrderNumber());
        saleOrder.put("dailyFlightGid", order.getDailyFlightGid());
        saleOrder.put("flightNumber", order.getFerryNumber());
        saleOrder.put("departTime", order.getDepartTime());
        saleOrder.put("seat", order.getSeat());
        saleOrder.put("amount", order.getAmount());
        saleOrder.put("username", order.getUser().getFerryMember().getName());
        List<Map<String, Object>> list = Lists.newArrayList();
        for (FerryOrderItem orderItem : order.getFerryOrderItemList()) {
            Map<String, Object> itemMap = Maps.newLinkedHashMap();
            Map<String, Object> item = Maps.newLinkedHashMap();
            Map<String, Object> ticket = Maps.newLinkedHashMap();
            ticket.put("id", orderItem.getTicketId());
            ticket.put("name", orderItem.getTicketName());
            ticket.put("price", orderItem.getPrice());
            ticket.put("number", orderItem.getNumber());
            item.put("ticket", ticket);
            Map<String, Object> person = Maps.newLinkedHashMap();
            person.put("idTypeName", orderItem.getIdTypeName().getDescription());
            person.put("trueName", orderItem.getName());
            person.put("idnumber", orderItem.getIdnumber());
            person.put("mobile", orderItem.getMobile());
            item.put("person", person);
            itemMap.put("item", item);
            list.add(itemMap);
        }
        saleOrder.put("list", list);
        contentMap.put("SaleOrder", saleOrder);
        StringBuffer content = new StringBuffer();
        FerryUtil.mapToXMLTest2(contentMap, content);
        params.put("content", content.toString());
        params.put("checknum", FerryUtil.getCheckNum(content.toString()));
        return params;
    }

//    public String doRealName(Member user, FerryMember member, Map<String, Object> data) {
//        FerryMember ferryMember = ferryMemberService.findByName(member.getName());
//        if (ferryMember == null) {
//            ferryMember = new FerryMember();
//            ferryMember.setIsReal(false);
//        }
//        if (ferryMember.getIsReal()) {
//            user.setFerryMember(ferryMember);
//            memberService.update(user);
//            return "";
//        }
//        ferryMember.setPassword(member.getPassword());
//        ferryMember.setBankNo(member.getBankNo());
//        if ("实名认证".equals(data.get("state"))) {
//            ferryMember.setName(data.get("name").toString());
//            ferryMember.setTrueName(data.get("trueName").toString());
//            ferryMember.setMobile(data.get("mobile").toString());
//            ferryMember.setIdTypeName(FerryIdType.getByDesc(data.get("idTypeName").toString()));
//            ferryMember.setIdnumber(data.get("idnumber").toString());
//            ferryMember.setEmail(data.get("email").toString());
//            ferryMember.setIsReal(true);
//            ferryMemberService.saveOrUpdate(ferryMember);
//            user.setFerryMember(ferryMember);
//            completeMemberReal(user, ferryMember);
//            memberService.update(user);
//            return "";
//        }
//        Map<String, Object> map = FerryUtil.doRealname(member);
//        ferryMember.setName(member.getName());
//        ferryMember.setTrueName(member.getTrueName());
//        ferryMember.setMobile(member.getMobile());
//        ferryMember.setIdTypeName(member.getIdTypeName());
//        ferryMember.setIdnumber(member.getIdnumber());
//        ferryMember.setIsReal("0".equals(map.get("code")));
//        ferryMemberService.saveOrUpdate(ferryMember);
//        user.setFerryMember(ferryMember);
//        completeMemberReal(user, ferryMember);
//        memberService.update(user);
//        return map.get("message").toString();
//    }

//    public Map<String, Object> doRealName(Member user, FerryMember member) {
//        Map<String, Object> result = Maps.newHashMap();
//        FerryMember ferryMember = user.getFerryMember();
//        if (ferryMember == null) {
//            result.put("success", false);
//            result.put("errMsg", "未绑定轮渡账号");
//            return result;
//        }
//        if (ferryMember.getIsReal()) {
//            result.put("success", true);
//            result.put("isReal", true);
//            return result;
//        }
//        ferryMember.setTrueName(member.getTrueName());
//        ferryMember.setMobile(member.getMobile());
//        ferryMember.setIdTypeName(member.getIdTypeName());
//        ferryMember.setIdnumber(member.getIdnumber());
//        ferryMember.setBankNo(member.getBankNo());
//        Map<String, Object> map = FerryUtil.doRealname(ferryMember);
//        ferryMember.setIsReal("0".equals(map.get("code")));
//        ferryMemberService.saveOrUpdate(ferryMember);
//        user.setFerryMember(ferryMember);
//        completeMemberReal(user, ferryMember);
//        memberService.update(user);
//        result.put("success", true);
//        result.put("isReal", ferryMember.getIsReal());
//        result.put("errMsg", map.get("message").toString());
//        return result;
//    }

    public void completeMemberReal(Member member, FerryMember ferryMember) {
        member.setUserName(ferryMember.getTrueName());
        member.setTelephone(ferryMember.getMobile());
        member.setIdNumber(ferryMember.getIdnumber());
        member.setBankNo(ferryMember.getBankNo());
    }

    public void completeFerryMember(FerryMember oldMember, FerryMember newMember) {
        oldMember.setPassword(newMember.getPassword());
        oldMember.setTrueName(newMember.getTrueName());
        oldMember.setIdTypeName(newMember.getIdTypeName());
        oldMember.setIdnumber(newMember.getIdnumber());
        oldMember.setEmail(newMember.getEmail());
        oldMember.setMobile(newMember.getMobile());
        oldMember.setIsReal(newMember.getIsReal());
    }

    public void updateSyncTourist(Member user) {
        if (user.getFerryMember() == null) {
            return;
        }
        Map<String, Object> resultMap= FerryUtil.getContacts(user.getFerryMember().getName());
        if (!(Boolean) resultMap.get("success")) {
            return;
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("list");
        for (Map<String, Object> map : list) {
            if ("二代身份证".equals(map.get("idTypeName"))) {
                String idNumber = map.get("idnumber").toString();
                Tourist tourist = touristService.getByUserAndIdNumber(user.getId(), idNumber);
                if (tourist != null) {
                    if (!tourist.getInFerry()) {
                        tourist.setInFerry(true);
                        touristService.saveTourist(tourist);
                    }
                    continue;
                }
                tourist = new Tourist();
                tourist.setUser(user);
                tourist.setIdType(TouristIdType.IDCARD);
                tourist.setIdNumber(idNumber);
                tourist.setName(map.get("trueName").toString());
                tourist.setTel(map.get("mobile").toString());
                tourist.setGender(Gender.male);
                tourist.setStatus(TouristStatus.normal);
                tourist.setPeopleType(isAdult(idNumber));
                tourist.setInFerry(true);
                touristService.saveTourist(tourist);
            }
        }
    }

    private TouristPeopleType isAdult(String idNumber) {
        try {
            String dateStr = idNumber.substring(6, 14);
            Date birth = DateUtils.parse(dateStr, "yyyyMMdd");
            Integer age = DateUtils.age(birth, new Date());
            if (age >= 12) {
                return TouristPeopleType.ADULT;
            } else {
                return TouristPeopleType.KID;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TouristPeopleType.ADULT;
    }

    public void saveBalancePay(FerryOrder order, Member user) {
        user.setBalance(user.getBalance() - order.getAmount());
        order.setPayType(OrderPayType.ONLINE);
        memberService.update(user);
        Map<String, Object> result = FerryUtil.payNotify(order.getFerryNumber(), true);
        if (!(Boolean) result.get("success")) {
            order.setStatus(OrderStatus.FAILED);
            ferryOrderService.updateOrder(order);
            return;
        }
        order.setStatus(OrderStatus.SUCCESS);
        balanceService.savePayResult(order);
        ferryOrderService.updateOrderBill(order);
        Map<String, Object> data = Maps.newHashMap();
        data.put("lineName", order.getFlightLineName());
        // @WEB_MSG
        yhyMsgService.doSendSMS(data, order.getUser().getFerryMember().getMobile(), MsgTemplateKey.WEB_FERRY_BOOKING_SUCCESS_TLE);
    }

    public Map<String, Object> cancelOrder(FerryOrder order) {
        Map<String, Object> result = Maps.newHashMap();
        if (!OrderStatus.WAIT.equals(order.getStatus())) {
            result.put("success", false);
            result.put("errMsg", "订单不可取消");
            return result;
        }
        order.setStatus(OrderStatus.CANCELED);
        ferryOrderService.updateOrder(order);
        FerryUtil.payNotify(order.getFerryNumber(), false);
        result.put("success", true);
        return result;
    }
}
