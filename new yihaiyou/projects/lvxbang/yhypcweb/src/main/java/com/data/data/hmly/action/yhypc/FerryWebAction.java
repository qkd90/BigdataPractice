package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.vo.FerryInfoVo;
import com.data.data.hmly.service.FerryMemberService;
import com.data.data.hmly.service.FerryWebService;
import com.data.data.hmly.service.LvjiWebService;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.LvjiOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.util.GenValidateCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/2/6.
 */
public class FerryWebAction extends YhyAction {
    @Resource
    private MemberService memberService;
    @Resource
    private FerryWebService ferryWebService;
    @Resource
    private FerryMemberService ferryMemberService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private LvjiOrderService lvjiOrderService;
    @Resource
    private LvjiWebService lvjiWebService;
    @Resource
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private final ObjectMapper mapper = new ObjectMapper();

    public final String realBackLoginUrl = propertiesManager.getString("LVXBANG_WEB_URL") + "/yhypc/ferry/doRealBackLogin.jhtml";
    public final String realBackRegisterUrl = propertiesManager.getString("LVXBANG_WEB_URL") + "/yhypc/ferry/doRealBackRegister.jhtml";

    private String date;
    private String flightLineId;
    private String departTime;
    private String departProt;
    private String arrivePort;
    private String number;
    private Integer freeCount;
    private Float price;

    public Long orderId;
    public FerryOrder order;
    public Member user;

    private int pageIndex = 0;
    private int pageSize = 100;
    private FerryInfoVo ferryInfo = new FerryInfoVo();

    public String json;
    public String password;
    public String status;

    public Long productId;
    public Long ljOrderId;
    public LvjiOrder lvjiOrder;
    public String orderTime;
    public String contactJson;

    public Result list() {
        return dispatch("/WEB-INF/jsp/yhypc/ferry/ferry_list.jsp");
    }

    /*驴记填写订单  -----2017/8/10*/
    public Result glyfillOrder() throws IOException {    //填写订单
        user = getLoginUser();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderTime = formatter.format(currentTime);

        return dispatch("/WEB-INF/jsp/yhypc/ferry/gly_fill_order.jsp");
    }

    public Result hlsfillOrder() {    //填写订单
        user = getLoginUser();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderTime = formatter.format(currentTime);
        return dispatch("/WEB-INF/jsp/yhypc/ferry/hls_fill_order.jsp");
    }

    public Result jgfillOrder() {    //填写订单
        user = getLoginUser();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderTime = formatter.format(currentTime);
        return dispatch("/WEB-INF/jsp/yhypc/ferry/jg_fill_order.jsp");
    }

    public Result ljfillOk() throws IOException{   //付款成功
        user = getLoginUser();
        Map<String, Object> data = mapper.readValue(contactJson, Map.class);
        LvjiOrder order = lvjiWebService.setValueForOrder(Long.valueOf(data.get("productId").toString()), user);
        order.setUserName(data.get("name").toString());
        order.setMobile(data.get("mobile").toString());
        lvjiOrderService.saveOrder(order);
        result.put("success", true);
        result.put("orderId", order.getId());
        return jsonResult(result);
    }

    public Result ljOrderDetail() {   //订单详情
        return dispatch("/WEB-INF/jsp/yhypc/personal/ljOrderDetail.jsp");
    }

    //    @NeedLogin
    public Result fillOrder() {
        return dispatch("/WEB-INF/jsp/yhypc/ferry/ferry_fill_order.jsp");
    }

    public Result orderComplete() {
        return dispatch("/WEB-INF/jsp/yhypc/ferry/ferry_order_complete.jsp");
    }

    public Result lvjiOrderDetail(){
        return dispatch("/WEB-INF/jsp/yhypc/ferry/lj_order_complete.jsp");
    }

    public Result orderPay() {
        user = getLoginUser();
        if(orderId != null){
            order = ferryOrderService.getOrder(orderId);
            if (order.getWaitTime() != null) {
                order.setWaitSeconds(Long.valueOf(DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000).intValue());
            }
            return dispatch("/WEB-INF/jsp/yhypc/ferry/ferry_order_pay.jsp");
        }
        if(ljOrderId != null){
            lvjiOrder = lvjiOrderService.query(ljOrderId);
            return dispatch("/WEB-INF/jsp/yhypc/ferry/lvji_order_pay.jsp");
        }
        return null;
    }


    public Result getTotalFerryPage() {
        return json(JSONArray.fromObject(100));
    }

    public Result ferryList() throws ParseException {
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

    @AjaxCheck
    public Result saveOrder() throws IOException {
        Member user = memberService.get(getLoginUser().getId());
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
        Map<String, Object> map = ferryWebService.saveOrder(data, user, false);
        if (!(Boolean) map.get("success")) {
            return jsonResult(map);
        }
        result.put("success", true);
        result.put("orderId", ((FerryOrder) map.get("order")).getId());
        return jsonResult(result);
    }

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
                resultMap = FerryUtil.doRealname(ferryMember, realBackLoginUrl, "pc");
            } else {
                ferryMember.setName("1haiu_" + ferryMember.getMobile());
                ferryMember.setPassword(GenValidateCode.createSMSCode(false, 6));
                resultMap = FerryUtil.doRealname(ferryMember, realBackRegisterUrl, "pc");
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

    public Result doRealBackLogin() {
        user = getLoginUser();
        if (user == null) {
            return redirect("/yhypc/personal/myInfo.jhtml");
        }
        String content = (String) getParameter("content");
        String code = content.substring(content.indexOf("<code>") + "<code>".length(), content.indexOf("</code>"));
        if (!"0".equals(code)) {
            return redirect("/yhypc/ferry/list.jhtml?status=realname");
        }
        return redirect("/yhypc/ferry/list.jhtml?status=backLogin");
    }

    public Result doRealBackRegister() {
        user = getLoginUser();
        if (user == null) {
            return redirect("/yhypc/personal/myInfo.jhtml");
        }
        String content = (String) getParameter("content");
        String code = content.substring(content.indexOf("<code>") + 6, content.indexOf("</code>"));
        if (!"0".equals(code)) {
            return redirect("/yhypc/ferry/list.jhtml?status=realname");
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
        return redirect("/yhypc/ferry/list.jhtml");
    }

    public Result ferryLogin() throws IOException {
        Member user = getLoginUser();
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
            ferryWebService.completeFerryMember(oldFerryMember, ferryMember);
            ferryMemberService.update(oldFerryMember);
            user.setFerryMember(oldFerryMember);
        } else {
            ferryMemberService.save(ferryMember);
            user.setFerryMember(ferryMember);
        }
        memberService.update(user);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        ferryWebService.updateSyncTourist(user);
        result.put("success", true);
        return jsonResult(result);
    }

//    public Result ferryRegister() throws IOException {
//        Member user = getLoginUser();
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
//        result = ferryWebService.doRealName(user, ferryMember);
//        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
//        return jsonResult(result);
//    }

//    @AjaxCheck
//    public Result doRealname() throws IOException {
//        user = getLoginUser();
//        FerryMember ferryMember = mapper.readValue(json, FerryMember.class);
//        result = ferryWebService.doRealName(user, ferryMember);
//        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
//        return jsonResult(result);
//    }

    public Result orderStatus() {
        FerryOrder order = ferryOrderService.getOrder(orderId);
        result.put("success", true);
        result.put("status", order.getStatus().name());
        return jsonResult(result);
    }

    public Result ljOrderStatus() {
        LvjiOrder order = lvjiOrderService.query(orderId);
        result.put("success", true);
        result.put("status", order.getStatus());
        return jsonResult(result);
    }

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
        result = ferryWebService.cancelOrder(order);
        return jsonResult(result);
    }

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
        Date date = new Date();
        String orderTime = date.toString();
        result.put("orderTime", orderTime);
        result.put("success", true);
        return jsonResult(result);
    }

    public Result cancelLjOrder() {
        user = getLoginUser();
        LvjiOrder order = lvjiOrderService.query(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (OrderStatus.CANCELED.equals(order.getStatus())) {
            result.put("success", true);
            return jsonResult(result);
        }
        result = lvjiWebService.cancelOrder(order);
        return jsonResult(result);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = StringUtils.htmlEncode(date);
    }

    public String getFlightLineId() {
        return flightLineId;
    }

    public void setFlightLineId(String flightLineId) {
        this.flightLineId = StringUtils.htmlEncode(flightLineId);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getDepartProt() {
        return departProt;
    }

    public void setDepartProt(String departProt) {
        this.departProt = departProt;
    }

    public String getArrivePort() {
        return arrivePort;
    }

    public void setArrivePort(String arrivePort) {
        this.arrivePort = arrivePort;
    }

    public Integer getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public FerryInfoVo getFerryInfo() {
        return ferryInfo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setFerryInfo(FerryInfoVo ferryInfo) {
        this.ferryInfo = ferryInfo;
    }
}
