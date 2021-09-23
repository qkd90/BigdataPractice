package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.action.yihaiyou.response.FerryOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderSimpleResponse;
import com.data.data.hmly.service.FerryMemberService;
import com.data.data.hmly.service.FerryMobileService;
import com.data.data.hmly.service.ctriphotel.base.XMLUtil;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.FerryOrderItem;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.pay.util.Base64;
import com.data.data.hmly.util.GenValidateCode;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.framework.struts.NotNeedLogin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-11-09,0009.
 */
public class FerryWebAction extends BaseAction {
    @Resource
    private FerryMobileService ferryMobileService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private FerryMemberService ferryMemberService;
    @Resource
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    public static final String ferryUsername = "yihaiyou";
    public static final String ferryPassword = "yihaiyou";

    public final String realBackLoginUrl = propertiesManager.getString("LVXBANG_M_URL") + "/yihaiyou/ferry/doRealBackLogin.jhtml";
    public final String realBackRegisterUrl = propertiesManager.getString("LVXBANG_M_URL") + "/yihaiyou/ferry/doRealBackRegister.jhtml";

    public Member user;

    public String name;
    public String password;
    public String startTime;
    public String endTime;
    public Integer page;
    public String flightLineId;
    public String date;
    public Long orderId;

    public String json;

    public Integer pageNo;
    public Integer pageSize;

    @NeedLogin
    @AjaxCheck
    public Result queryReal() throws IOException {
        user = getLoginUser();
        FerryMember ferryMember = mapper.readValue(json, FerryMember.class);
        user.setUserName(ferryMember.getTrueName());
        user.setTelephone(ferryMember.getMobile());
        user.setIdNumber(ferryMember.getIdnumber());
        user.setEmail(ferryMember.getEmail());
        memberService.update(user);
        result = FerryUtil.queryReal(ferryMember);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        if (!(Boolean) result.get("success")) {
            return jsonResult(result);
        }
        ferryMember.setIsReal((Boolean) result.get("isReal"));
        if (ferryMember.getIsReal()) {
            ferryMember.setName(result.get("name").toString());
        } else {
            Map<String, Object> resultMap;
            if ((Boolean) result.get("hasMember")) {
                ferryMember.setName(result.get("name").toString());
                resultMap = FerryUtil.doRealname(ferryMember, realBackLoginUrl, "mobile");
            } else {
                ferryMember.setName("1haiu_" + ferryMember.getMobile());
                ferryMember.setPassword(GenValidateCode.createSMSCode(false, 6));
                resultMap = FerryUtil.doRealname(ferryMember, realBackRegisterUrl, "mobile");
            }
            if ((Boolean) resultMap.get("success")) {
                result.put("url", resultMap.get("url"));
            } else {
                return jsonResult(resultMap);
            }
        }
        user.setTemFerryMember(ferryMember);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        return jsonResult(result);
    }

    //    @NeedLogin
    @AjaxCheck
    public Result doRealBackLogin() {
        user = getLoginUser();
        if (user == null) {
            return redirect("/#/timeOut");
        }
        String content = (String) getParameter("content");
        String code = content.substring(content.indexOf("<code>") + "<code>".length(), content.indexOf("</code>"));
        if (!"0".equals(code)) {
            return redirect("/#/ferryRealname");
        }
        return redirect("/#/ferryLogin");
    }

    //    @NeedLogin
    @AjaxCheck
    public Result doRealBackRegister() {
        user = getLoginUser();
        if (user == null) {
            return redirect("/#/timeOut");
        }
        String content = (String) getParameter("content");
        String code = content.substring(content.indexOf("<code>") + 6, content.indexOf("</code>"));
        if (!"0".equals(code)) {
            return redirect("/#/ferryRealname");
        }
        FerryMember ferryMember = user.getTemFerryMember();
        ferryMember.setPassword(MD5.Encode(ferryMember.getPassword()));
        result = FerryUtil.ferryRegister(ferryMember);
        if ((Boolean) result.get("success")) {
            ferryMember.setIsReal(true);
            ferryMemberService.save(ferryMember);
            user.setFerryMember(ferryMember);
            memberService.update(user);
            getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        }
        return redirect("/#/ferryRegisterBack");
    }

//    @NeedLogin
//    @AjaxCheck
//    public Result doRealname() throws IOException {
//        user = getLoginUser();
//        FerryMember ferryMember = mapper.readValue(json, FerryMember.class);
//        result = ferryMobileService.doRealName(user, ferryMember);
//        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
//        return jsonResult(result);
//    }

    @NeedLogin
    @AjaxCheck
    public Result ferryLogin() throws IOException {
        user = getLoginUser();
        FerryMember ferryMember = user.getTemFerryMember();
        ferryMember.setPassword(MD5.Encode(password));
        Map<String, Object> resultMap = FerryUtil.ferryLogin(ferryMember);
        if (!(Boolean) resultMap.get("success")) {
            return jsonResult(resultMap);
        }
        if (!ferryMember.getIsReal()) {
            Map<String, Object> realResult = FerryUtil.realnameFerry(ferryMember);
            if (!(Boolean) realResult.get("success")) {
                return jsonResult(realResult);
            }
            ferryMember.setIsReal(true);
        }
        FerryMember oldFerryMember = ferryMemberService.findByName(ferryMember.getName());
        if (oldFerryMember != null) {
            ferryMobileService.completeFerryMember(oldFerryMember, ferryMember);
            ferryMemberService.update(oldFerryMember);
            user.setFerryMember(oldFerryMember);
        } else {
            ferryMemberService.save(ferryMember);
            user.setFerryMember(ferryMember);
        }
        memberService.update(user);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        ferryMobileService.updateSyncTourist(user);
        result.put("success", true);
        return jsonResult(result);
    }

//    @NeedLogin
//    @AjaxCheck
//    public Result ferryRegister() throws IOException {
//        user = getLoginUser();
//        FerryMember ferryMember = mapper.readValue(json, FerryMember.class);
//        ferryMember.setPassword(MD5.Encode(ferryMember.getPassword()));
//        Map<String, Object> map1 = FerryUtil.checkFerryMember(ferryMember.getName());
//        if (!"0".equals(map1.get("code"))) {
//            result.put("success", false);
//            result.put("errMsg", map1.get("message"));
//            return jsonResult(result);
//        }
//        Map<String, Object> map2 = FerryUtil.ferryRegister(ferryMember);
//        if (!"0".equals(map2.get("code"))) {
//            result.put("success", false);
//            result.put("errMsg", map2.get("message"));
//            return jsonResult(result);
//        }
//        ferryMemberService.save(ferryMember);
//        user.setFerryMember(ferryMember);
//        memberService.update(user);
//        result = ferryMobileService.doRealName(user, ferryMember);
//        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
//        return jsonResult(result);
//    }

    @AjaxCheck
    public Result getDailyFlight() throws ParseException {
        Map<String, Object> resultMap = FerryUtil.getDailyFlight(DateUtils.parseShortTime(date), flightLineId);
        if ("0".equals(resultMap.get("code"))) {
            List flightList = FerryUtil.castList((((Map) resultMap.get("content")).get("list")));
            result.put("success", true);
            result.put("flightList", flightList);
            return jsonResult(result);
        }
        result.put("success", false);
        result.put("errMsg", resultMap.get("message"));
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result saveOrder() throws IOException {
        user = memberService.get(getLoginUser().getId());
        if (user.getFerryMember() == null) {
            result.put("success", false);
            result.put("noMember", true);
            result.put("errMsg", "未绑定轮渡账号");
            return jsonResult(result);
        }
        if (!user.getFerryMember().getIsReal()) {
            result.put("success", false);
            result.put("noReal", true);
            result.put("errMsg", "账号未实名");
            return jsonResult(result);
        }
        Map<String, Object> data = mapper.readValue(json, Map.class);
        Map<String, Object> map = ferryMobileService.saveOrder(data, user, false);
        if (!(Boolean) map.get("success")) {
            return jsonResult(map);
        }
        result.put("success", true);
        result.put("orderId", ((FerryOrder) map.get("order")).getId());
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result simpleOrderDetail() {
        user = getLoginUser();
        FerryOrder order = ferryOrderService.getOrder(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        OrderSimpleResponse response = new OrderSimpleResponse(order);
        result.put("success", true);
        result.put("order", response);
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result orderDetail() {
        user = getLoginUser();
        FerryOrder order = ferryOrderService.getOrder(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        FerryOrderResponse response = new FerryOrderResponse(order);
        response.setTouristList(Lists.transform(order.getFerryOrderItemList(), new Function<FerryOrderItem, SimpleTourist>() {
            @Override
            public SimpleTourist apply(FerryOrderItem input) {
                SimpleTourist tourist = new SimpleTourist();
                tourist.setName(input.getName());
                return tourist;
            }
        }));
        result.put("success", true);
        result.put("order", response);
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result personalOrderList() {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        List<FerryOrder> list = ferryOrderService.getByUserId(user.getId(), page, "createTime", "desc");
        List<OrderSimpleResponse> responseList = Lists.transform(list, new Function<FerryOrder, OrderSimpleResponse>() {
            @Override
            public OrderSimpleResponse apply(FerryOrder input) {
                return new OrderSimpleResponse(input);
            }
        });
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("orderList", responseList);
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result syncTourist() {
        user = getLoginUser();
        ferryMobileService.updateSyncTourist(user);
        result.put("success", true);
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result cancelOrder() {
        user = getLoginUser();
        FerryOrder order = ferryOrderService.getOrder(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (OrderStatus.CANCELED.equals(order.getStatus())) {
            result.put("success", true);
            return jsonResult(result);
        }
        result = ferryMobileService.cancelOrder(order);
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result delete() {
        user = getLoginUser();
        FerryOrder order = ferryOrderService.getOrder(orderId);
        if (order == null) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        order.setDeleteFlag(true);
        ferryOrderService.updateOrder(order);
        result.put("success", true);
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result refundOrder() {
        user = getLoginUser();
        FerryOrder order = ferryOrderService.getOrder(orderId);
        if (order == null) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (!OrderStatus.SUCCESS.equals(order.getStatus())) {
            result.put("success", false);
            result.put("errMsg", "订单状态为" + order.getStatus().getDescription() + "，不能申请退款。");
            return jsonResult(result);
        }
        result = ferryOrderService.doDefundOrder(order, user);
        if ((Boolean) result.get("success")) {
            FerryOrderResponse response = new FerryOrderResponse(order);
            response.setTouristList(Lists.transform(order.getFerryOrderItemList(), new Function<FerryOrderItem, SimpleTourist>() {
                @Override
                public SimpleTourist apply(FerryOrderItem input) {
                    SimpleTourist tourist = new SimpleTourist();
                    tourist.setName(input.getName());
                    return tourist;
                }
            }));
            result.put("order", response);
        }
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result checkReal() {
        user = getLoginUser();
        if (user.getFerryMember() == null) {
            result.put("success", false);
            result.put("noMember", true);
            result.put("errMsg", "未绑定轮渡账号");
            return jsonResult(result);
        }
        if (!user.getFerryMember().getIsReal()) {
            result.put("success", false);
            result.put("noReal", true);
            result.put("errMsg", "账号未实名");
            return jsonResult(result);
        }
        result.put("success", true);
        return jsonResult(result);
    }

    @NotNeedLogin
    public Result checkTicket() {
        Document document = DocumentHelper.createDocument();
        Element result = document.addElement("result");
        Element code = result.addElement("code");
        Element message = result.addElement("message");
        Element checknumEle = result.addElement("checknum");
        Element contentEle = result.addElement("content");
        checknumEle.setText("null");
        contentEle.setText("");

        String authorization = getRequest().getHeader("authorization");
        if (StringUtils.isBlank(authorization) || authorization.split(" ").length < 2) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        String userAndPass = new String(Base64.decode(authorization.split(" ")[1]));
        if (StringUtils.isBlank(userAndPass) || userAndPass.split(":").length < 2) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        String username = userAndPass.split(":")[0];
        String password = userAndPass.split(":")[1];
        if (!ferryUsername.equals(username) || !ferryPassword.equals(password)) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }

        if (getParameter("sno") == null || getParameter("idnumber") == null || getParameter("checknum") == null) {
            code.setText("-2");
            message.setText("参数错误");
            return text(document.asXML());
        }
        String sno = getParameter("sno").toString();
        String idnumber = getParameter("idnumber").toString();
        String checknum = getParameter("checknum").toString();
        if (!FerryUtil.verifyTransResponse(sno + ":" + idnumber, checknum)) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        List<FerryOrderItem> ferryOrderItemList = ferryOrderService.getItemByOrderNoAndIdnumber(sno, idnumber);
        if (ferryOrderItemList.isEmpty()) {
            code.setText("-2");
            message.setText("订单不存在");
            return text(document.asXML());
        }
        FerryOrderItem ferryOrderItem = ferryOrderItemList.get(0);
        ferryOrderItem.setHasChecked(true);
        ferryOrderService.updateItem(ferryOrderItem);
        code.setText("0");
        message.setText("成功");
        return text(document.asXML());
    }

    @NotNeedLogin
    public Result refundResult() {
        Document document = DocumentHelper.createDocument();
        Element result = document.addElement("result");
        Element code = result.addElement("code");
        Element message = result.addElement("message");
        Element checknumEle = result.addElement("checknum");
        Element contentEle = result.addElement("content");

        String authorization = getRequest().getHeader("authorization");
        if (StringUtils.isBlank(authorization) || authorization.split(" ").length < 2) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        String userAndPass = new String(Base64.decode(authorization.split(" ")[1]));
        if (StringUtils.isBlank(userAndPass) || userAndPass.split(":").length < 2) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        String username = userAndPass.split(":")[0];
        String password = userAndPass.split(":")[1];
        if (!ferryUsername.equals(username) || !ferryPassword.equals(password)) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }

        if (getParameter("content") == null || getParameter("checknum") == null) {
            code.setText("-2");
            message.setText("参数错误");
            return text(document.asXML());
        }
        String content = getParameter("content").toString();
        String checknum = getParameter("checknum").toString();
        checknumEle.setText("null");
        contentEle.setText("");
        if (!FerryUtil.verifyTransResponse(content, checknum)) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        Map<String, Object> contentmap = XMLUtil.readStringXmlOut(content);
        Object object = contentmap.get("SaleOrder");
        List<Map<String, Object>> orderList = Lists.newArrayList();
        if (object instanceof Map) {
            orderList = Lists.newArrayList((Map<String, Object>) object);
        } else if (object instanceof List) {
            orderList = (List<Map<String, Object>>) object;
        }
        for (Map<String, Object> order : orderList) {
            List<FerryOrder> ferryOrderList = ferryOrderService.getBySno(order.get("sno").toString());
            if (ferryOrderList.isEmpty()) {
                continue;
            }
            FerryOrder ferryOrder = ferryOrderList.get(0);
            if (!OrderStatus.CANCELING.equals(ferryOrder.getStatus())) {
                continue;
            }
            ferryOrder.setReturnAmount(Float.valueOf(order.get("returnAmount").toString()));
            ferryOrder.setPoundageAmount(Float.valueOf(order.get("poundageAmount").toString()));
            ferryOrder.setPoundageDescribe(order.get("poundageDescribe").toString());
            ferryOrder.setRefundDate(new Date());
            ferryOrder.setStatus(OrderStatus.REFUND);
            ferryOrder.setModifyTime(new Date());
            ferryOrderService.updateOrder(ferryOrder);
        }
        code.setText("0");
        message.setText("成功");
        return text(document.asXML());
    }
}
