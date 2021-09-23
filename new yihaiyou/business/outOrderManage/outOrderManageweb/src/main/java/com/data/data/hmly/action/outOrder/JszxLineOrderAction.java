package com.data.data.hmly.action.outOrder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.outOrder.dataEntities.JszxLineDetailEntity;
import com.data.data.hmly.action.outOrder.dataEntities.OrderDetailEntity;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.ctriphotel.base.StringUtil;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.outOrder.JszxOrderDetailService;
import com.data.data.hmly.service.outOrder.JszxOrderService;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailPriceType;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.outOrder.entity.enums.SourceType;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dy on 2016/3/17.
 */
public class JszxLineOrderAction extends FrameBaseAction {
    private Long lineId;
    private Long typePriceId;
    private String type;
    private Integer index;
    private Line line;
    private Long outOrderId;
    private Integer isConfim;
    private SysUser sysUser;

    private Integer			page				= 1;
    private Integer			rows				= 10;
    private JszxOrder jszxOrder = new JszxOrder();
    Map<String, Object> map = new HashMap<String, Object>();
    List<JszxOrder> jszxOrderList = new ArrayList<JszxOrder>();

    @Resource
    private LineService lineService;
    @Resource
    private MsgService msgService;
    @Resource
    private AccountLogService accountLogService;
    @Resource
    private LineexplainService lineexplainService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private BalanceService balanceService;




    public Result checkBalance() {

        String otIdStr = (String) getParameter("otIds");
        if (!StringUtil.isNotBlank(otIdStr) && outOrderId == null) {
            simpleResult(map, false, "订单不存在！");
            return jsonResult(map);
        }
        jszxOrder = jszxOrderService.load(outOrderId);
        String[] otIdArr = otIdStr.split(",");
        List<JszxOrderDetail> jszxOrderDetails = new ArrayList<JszxOrderDetail>();


        Float actualPrice = 0f;
        for (String strId : otIdArr) {
            JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(Long.parseLong(strId));
            actualPrice = actualPrice + jszxOrderDetail.getActualPay();
        }

        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());

        if (sysUser.getBalance() < actualPrice) {


            map.put("money", ( actualPrice - sysUser.getBalance()));
            simpleResult(map, false, "账户余额不足，无法确认退款，请重新充值后再操作！");
            return jsonResult(map);
        }

        simpleResult(map, true, "");
        return jsonResult(map);
    }


    /**
     * 供应商修改实际支付总价
     * @return
     */
    public Result editActualPayPrice() {

        String actualPriceStr = (String) getParameter("actualPrice");


        if (outOrderId != null && StringUtils.isNotBlank(actualPriceStr)) {

            jszxOrder = jszxOrderService.load(outOrderId);
            Float actualPrice = Float.parseFloat(actualPriceStr);

            jszxOrder.setActualPayPrice(actualPrice);

            List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOutTicketList(jszxOrder);

            List<JszxOrderDetail> newOrderDetailList = new ArrayList<JszxOrderDetail>();


            Float tempActualPrice = 0f;

            for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {
                Float detailActualPrice = jszxOrderDetail.getPrice() * (actualPrice / jszxOrder.getTotalPrice());
                jszxOrderDetail.setActualPay(detailActualPrice);
                tempActualPrice = tempActualPrice + detailActualPrice;
                newOrderDetailList.add(jszxOrderDetail);
            }




            if (tempActualPrice > actualPrice) {
                Float detailActualPay = newOrderDetailList.get(0).getActualPay();
                detailActualPay = detailActualPay - (tempActualPrice - actualPrice);
                newOrderDetailList.get(0).setActualPay(detailActualPay);
            } else if (tempActualPrice < actualPrice) {
                Float detailActualPay = newOrderDetailList.get(0).getActualPay();
                detailActualPay = detailActualPay + (actualPrice - tempActualPrice);
                newOrderDetailList.get(0).setActualPay(detailActualPay);
            }
            jszxOrderDetailService.updateAll(newOrderDetailList);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());

            simpleResult(map, true, "修改成功！");
            return jsonResult(map);
        } else {
            simpleResult(map, false, "输入参数有误！");
            return jsonResult(map);
        }


    }





    /**
     * 订单取消确认
     * @return
     */
    public Result confirmOrderDetail() {
        String orderTicketId = (String) getParameter("orderTicketId");
        if (StringUtil.isNotBlank(orderTicketId)) {
            JszxOrderDetail orderDetail = jszxOrderDetailService.load(Long.parseLong(orderTicketId));

            SysUser user =  balanceService.findBalanceAccountBy(getLoginUser().getId());    //获取当前用户下公司的账户余额

            if (user.getBalance() >= orderDetail.getActualPay()) {
                //账户余额充足
                orderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
                Float refundPrice = orderDetail.getActualPay();
                jszxOrder = orderDetail.getJszxOrder();
                jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice() - refundPrice);
                sysUser = sysUserService.load(jszxOrder.getUser().getId());
                balanceService.updateBalance(refundPrice.doubleValue(), AccountType.refund, sysUser.getId(), jszxOrder.getSupplierId(), getLoginUser().getId(), orderDetail.getTicketNo(), orderDetail.getId());
                jszxOrderDetailService.update(orderDetail);
                jszxOrderDetailService.updateJszxOrderCancel(jszxOrder);
                simpleResult(map, true, "确认退款成功！");
                return jsonResult(map);
            } else {
                //账户余额不足
                map.put("money", ( orderDetail.getActualPay() - user.getBalance()));
                simpleResult(map, false, "账户余额不足，无法确认退款，请重新充值后再操作！");
                return jsonResult(map);
            }


        } else {
            simpleResult(map, false, "确认退款失败！");
            return jsonResult(map);
        }

    }

    /**
     * 拒绝客户订单
     * @return
     */
    public Result confuseLineOrder() {

        if (outOrderId != null) {

            jszxOrder = jszxOrderService.load(outOrderId);

            jszxOrder.setIsConfirm(-1);

            jszxOrder.setStatus(JszxOrderStatus.CANCELED);

            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());

            simpleResult(map, true, "拒绝成功！");

            return jsonResult(map);

        }

        simpleResult(map, false, "拒绝失败！");

        return jsonResult(map);

    }


    /**
     * 确认订单
     * @return
     */
    public Result confimLineOrder() {
        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
            jszxOrder.setIsConfirm(1);
            List<JszxOrderDetail> orderDetails = jszxOrderDetailService.getOrderDetailList(jszxOrder, null);
            Float totalPrice = 0f;
            Set<Long> detailIdSet = new HashSet<Long>();
            for (JszxOrderDetail detail : orderDetails) {
                int cout = 0;
                if (detail.getRefundCount() != null) {
                    cout = detail.getCount() - detail.getRefundCount();
                } else {
                    cout = detail.getCount();
                }
                totalPrice = totalPrice + cout * detail.getActualPay();
                detailIdSet.add(detail.getTypePriceId());
            }
            //账户余额扣款
            balanceService.updateBalance(totalPrice.doubleValue(), AccountType.consume, jszxOrder.getUser().getId(), jszxOrder.getSupplierId(), getLoginUser().getId(), jszxOrder.getOrderNo(), jszxOrder.getId());

            int msgCount = 0;
            if (jszxOrder.getMsgCount() != null) {
                msgCount =  jszxOrder.getMsgCount() + 1;
            } else {
                msgCount++;
            }
            jszxOrder.setMsgCount(msgCount);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());

            if (jszxOrder.getSourceType() != SourceType.LVXBANG) {
                //发送短信
                sendConfirmSuccessMsg(jszxOrder, orderDetails);
            }
            simpleResult(map, true, "确认成功！");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);

    }



    /**
     * 确认成功后，发送短信
     * @param jszxOrder
     * @param orderDetails
     */
    public void sendConfirmSuccessMsg(JszxOrder jszxOrder, List<JszxOrderDetail> orderDetails) {




        Map<Long, List<JszxOrderDetail>> mapList = new HashMap<Long, List<JszxOrderDetail>>();




        //每个门票类型都发送一次
        for (JszxOrderDetail orderDetail : orderDetails) {
            List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();
            for (JszxOrderDetail orderDetail2 : orderDetails) {
                if (orderDetail.getTypePriceId() == orderDetail2.getTypePriceId()) {
                    orderDetailMapList.add(orderDetail2);
                }
            }
            mapList.put(orderDetail.getTypePriceId(), orderDetailMapList);
        }



        for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
            Long typePriceId = entry1.getKey();
            List<JszxOrderDetail> typeOrderList1 = entry1.getValue();

            Map<String, List<JszxOrderDetail>> mapList2 = new HashMap<String, List<JszxOrderDetail>>();

            for (JszxOrderDetail jszxOrderDetail : typeOrderList1) {

                List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();

                for (JszxOrderDetail jszxOrderDetail2 : typeOrderList1) {

                    if (jszxOrderDetail.getType() == jszxOrderDetail2.getType()
                            && jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {

                        orderDetailMapList.add(jszxOrderDetail2);
                    }
                }
                mapList2.put(jszxOrderDetail.getType().toString(), orderDetailMapList);
            }

            JszxOrderDetail jszxOrderDetail = typeOrderList1.get(0);

            String content = "您已成功预订。";

            content = content + "产品：" + jszxOrder.getProduct().getName();

            content = content + "+" + jszxOrderDetail.getTicketName();


            String msgDetail = "";

            for (Map.Entry<String, List<JszxOrderDetail>> entry2 : mapList2.entrySet()) {

//        【厦旅集散】您已成功预订：南靖云水谣一日游【成人】份数，数量：1。
//        订单号123456789，使用日期：2016-06-09。
//        使用方法:导游会在出行前一天21：30前电话联系客人预约接车时间与地点；如您在出发前一天21：30分前尚未收到电话或短信，请速联系短信确认单上紧急联系电话。
//        紧急联系人电话：18950031826。如有疑问请致电4000-918-168。
                type = entry2.getKey();
                List<JszxOrderDetail> typeOrderList2 = entry2.getValue();

                if ("adult".equals(type)) {
                    type = "成人";
                    String adultType = "【" + type + "】份数" + typeOrderList2.size() + "；";
                    msgDetail = msgDetail + adultType;
                } else {
                    type = "儿童";
                    String childType = "【" + type + "】份数" + typeOrderList2.size() + "；";
                    msgDetail = msgDetail + childType;
                }
            }

            if (msgDetail.length() > 0) {
                msgDetail = msgDetail.substring(0, msgDetail.length() - 1);
                content = content + msgDetail + "，";
            }

//            content = content + "订单号" + jszxOrder.getOrderNo() + "，";
            String useTime = "";
            if (jszxOrderDetail.getStartTime() != null) {
                useTime = jszxOrderDetail.getStartTime().toString();
                useTime = useTime.substring(0, 10);
                content = content + "使用日期：" + useTime + "。";
            }

            content = content + "使用方法:导游会在出行前一天21：30前电话联系客人预约接车时间与地点；如您在出发前一天21：30分前尚未收到电话或短信，请速联系短信确认单上紧急联系电话。";
            sysUser = jszxOrder.getUser();

            if (sysUser != null) {
                if (sysUser.getMobile() != null) {
                    content = content + "紧急联系人电话：" + sysUser.getMobile() + "。";
                }
            }
            content = content + "如有疑问请致电4000918168。";

            ProductValidateCode productValidateCode = new ProductValidateCode();
            productValidateCode.setOrderNo(jszxOrder.getOrderNo());
            productValidateCode.setBuyerMobile(jszxOrder.getPhone());

            if (jszxOrder.getSourceType() != SourceType.LVXBANG) {
                msgService.addSendMsgCode(productValidateCode, jszxOrder.getPhone(), content);
            }





        }





    }

    /**
     * 下单时，发送短信
     * @param jszxOrder
     * @param orderDetails
     */
    public void sendConfirmMsg(JszxOrder jszxOrder, List<JszxOrderDetail> orderDetails) {


        Map<Long, List<JszxOrderDetail>> mapList = new HashMap<Long, List<JszxOrderDetail>>();


        //每个门票类型都发送一次
        for (JszxOrderDetail orderDetail : orderDetails) {
            List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();
            for (JszxOrderDetail orderDetail2 : orderDetails) {
                if (orderDetail.getTypePriceId() == orderDetail2.getTypePriceId()) {
                    orderDetailMapList.add(orderDetail2);
                }
            }
            mapList.put(orderDetail.getTypePriceId(), orderDetailMapList);
        }



        for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
            Long typePriceId = entry1.getKey();
            List<JszxOrderDetail> typeOrderList1 = entry1.getValue();

            Map<String, List<JszxOrderDetail>> mapList2 = new HashMap<String, List<JszxOrderDetail>>();

            for (JszxOrderDetail jszxOrderDetail : typeOrderList1) {

                List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();

                for (JszxOrderDetail jszxOrderDetail2 : typeOrderList1) {

                    if (jszxOrderDetail.getType() == jszxOrderDetail2.getType()
                            && jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {

                        orderDetailMapList.add(jszxOrderDetail2);
                    }
                }
                mapList2.put(jszxOrderDetail.getType().toString(), orderDetailMapList);
            }

            JszxOrderDetail jszxOrderDetail = typeOrderList1.get(0);

            String content = "您的订单已提交。";

            content = content + "产品：" + jszxOrder.getProduct().getName();

            content = content + "+" + jszxOrderDetail.getTicketName();


            String msgDetail = "";
            for (Map.Entry<String, List<JszxOrderDetail>>
                    entry2 : mapList2.entrySet()) {


//        【厦旅集散】份数您的订单已提交。产品：南靖云水谣一日游 【成人】份数，数量：1。
//        订单号123456789，使用日期：2016-06-09。
//        我们会在2小时内给您发送确认短信。请您耐心等待。如有疑问请致电4000-918-168。

                type = entry2.getKey();

                List<JszxOrderDetail> typeOrderList2 = entry2.getValue();

                if ("adult".equals(type)) {
                    type = "成人";
                    String adultType = "【" + type + "】份数" + typeOrderList2.size() + "；";
                    msgDetail = msgDetail + adultType;
                } else {
                    type = "儿童";
                    String childType = "【" + type + "】份数" + typeOrderList2.size() + "；";
                    msgDetail = msgDetail + childType;
                }
            }

            if (msgDetail.length() > 0) {
                msgDetail = msgDetail.substring(0, msgDetail.length() - 1);
                content = content + msgDetail + "，";
            }

//            content = content + "订单号" + jszxOrder.getOrderNo() + "，";
            String useTime = "";
            if (jszxOrderDetail.getStartTime() != null) {
                useTime = jszxOrderDetail.getStartTime().toString();
                useTime = useTime.substring(0, 10);
                content = content + "使用日期：" + useTime + "。";
            }
            content = content + "我们会在2小时内给您发送确认短信。请您耐心等待。如有疑问请致电4000-918-168。";
            ProductValidateCode productValidateCode = new ProductValidateCode();
            productValidateCode.setOrderNo(jszxOrder.getOrderNo());
            productValidateCode.setBuyerMobile(jszxOrder.getPhone());

            if (jszxOrder.getSourceType() != SourceType.LVXBANG) {

                msgService.addSendMsgCode(productValidateCode, jszxOrder.getPhone(), content);

            }

        }



    }


    /**
     * 无需确认订单，直接发送成功预定信息
     * @param jszxOrder
     * @param orderDetails
     */
    public void sendNoConfirmMsg(JszxOrder jszxOrder, List<JszxOrderDetail> orderDetails) {


//            【厦旅集散】份数您已成功预订：南靖云水谣一日游【成人】份数，数量：1。
//            订单号123456789，使用日期：2016-06-09。
//            使用方法:导游会在出行前一天21：30前电话联系客人预约接车时间与地点；如您在出发前一天21：30分前尚未收到电话或短信，请速联系短信确认单上紧急联系电话。
//            紧急联系人电话：18950031826。
//            如有疑问请致电4000-918-168。

        Map<Long, List<JszxOrderDetail>> mapList = new HashMap<Long, List<JszxOrderDetail>>();




        //每个门票类型都发送一次
        for (JszxOrderDetail orderDetail : orderDetails) {
            List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();
            for (JszxOrderDetail orderDetail2 : orderDetails) {
                if (orderDetail.getTypePriceId() == orderDetail2.getTypePriceId()) {
                    orderDetailMapList.add(orderDetail2);
                }
            }
            mapList.put(orderDetail.getTypePriceId(), orderDetailMapList);
        }



        for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
            List<JszxOrderDetail> typeOrderList1 = entry1.getValue();

            Map<String, List<JszxOrderDetail>> mapList2 = new HashMap<String, List<JszxOrderDetail>>();

            for (JszxOrderDetail jszxOrderDetail : typeOrderList1) {

                List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();

                for (JszxOrderDetail jszxOrderDetail2 : typeOrderList1) {

                    if (jszxOrderDetail.getType() == jszxOrderDetail2.getType()
                            && jszxOrderDetail2.getUseStatus() == JszxOrderDetailStatus.UNUSED) {

                        orderDetailMapList.add(jszxOrderDetail2);
                    }
                }
                mapList2.put(jszxOrderDetail.getType().toString(), orderDetailMapList);
            }

            JszxOrderDetail jszxOrderDetail = typeOrderList1.get(0);

            String content = "您已成功预订:";

            content = content + jszxOrder.getProduct().getName();

            content = content + "+" + jszxOrderDetail.getTicketName();


            String msgDetail = "";


            for (Map.Entry<String, List<JszxOrderDetail>> entry2 : mapList2.entrySet()) {
                type = entry2.getKey();

                List<JszxOrderDetail> typeOrderList2 = entry2.getValue();


                if ("adult".equals(type)) {
                    type = "成人";
                    String adultType = "【" + type + "】份数" + typeOrderList2.size() + "；";
                    msgDetail = msgDetail + adultType;
                } else {
                    type = "儿童";
                    String childType = "【" + type + "】份数" + typeOrderList2.size() + "；";
                    msgDetail = msgDetail + childType;
                }
            }

            if (msgDetail.length() > 0) {
                msgDetail = msgDetail.substring(0, msgDetail.length() - 1);
                content = content + msgDetail + "，";
            }

//            content = content + "订单号" + jszxOrder.getOrderNo() + "，";
            String useTime = "";
            if (jszxOrderDetail.getStartTime() != null) {
                useTime = jszxOrderDetail.getStartTime().toString();
                useTime = useTime.substring(0, 10);
                content = content + "使用日期：" + useTime + "。";
            }
            content = content + "使用方法:工作人员会在出行前一天20:30前电话联系客人接车时间与地点；如您在出行前一天20:30前尚未收到电话或短信通知，请速联系短信确认单上紧急联系人电话。";
            sysUser = jszxOrder.getUser();

            if (sysUser != null) {
                if (sysUser.getMobile() != null) {
                    content = content + "紧急联系人电话：" + sysUser.getMobile() + "。";
                }
            }
            content = content + "如有疑问请致电4000918168。";

            ProductValidateCode productValidateCode = new ProductValidateCode();
            productValidateCode.setOrderNo(jszxOrder.getOrderNo());
            productValidateCode.setBuyerMobile(jszxOrder.getPhone());
            if (jszxOrder.getSourceType() != SourceType.LVXBANG) {
                msgService.addSendMsgCode(productValidateCode, jszxOrder.getPhone(), content);
            }
        }






    }


    /**
     * 获取需要确认的订单
     * @return
     */
    public Result getClientOrderList() {

        Page pageInfo = new Page(page, rows);
        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");


        String startUseTimeStr = (String) getParameter("startUseTimeStr");
        String endUseTimeStr = (String) getParameter("endUseTimeStr");



        if (type != null) {
//            jszxOrder = new JszxOrder();

            jszxOrder.setProType(ProductType.line);
            if (StringUtil.isNotBlank(startCreateTimeStr)) {
                Date startTime = com.zuipin.util.DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                jszxOrder.setStartCreateTime(startTime);
            }
            if (StringUtil.isNotBlank(endCreateTimeStr)) {
                Date endTime = com.zuipin.util.DateUtils.getDate(endCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                jszxOrder.setEndCreateTime(com.zuipin.util.DateUtils.getEndDay(endTime, 0));
            }

            if (StringUtils.isNotBlank(startUseTimeStr)) {
                Date startUseDate = com.zuipin.util.DateUtils.getDate(startUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                jszxOrder.setStartUseTime(startUseDate);
            }
            if (StringUtils.isNotBlank(endUseTimeStr)) {
                Date endUseDate = com.zuipin.util.DateUtils.getDate(endUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                endUseDate = com.zuipin.util.DateUtils.getEndDay(endUseDate, 0);
                jszxOrder.setEndUseTime(endUseDate);
            }

            jszxOrderList = jszxOrderService.getClientOrderList(jszxOrder, getLoginUser(), getCompanyUnit(), isSiteAdmin(), isSupperAdmin(), pageInfo);
            jszxOrderList = jszxOrderService.getLineJszxOrderTypetList(jszxOrder, jszxOrderList);
        }
        return datagrid(jszxOrderList, pageInfo.getTotalCount());
    }

    /**
     * 客户订单列表页
     * @return
     */
    public Result clientOrderManage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch();
    }

    public Result clientCruiseShipOrderManage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch("/WEB-INF/jsp/outOrder/cruiseship/index.jsp");
    }
    //  /outOrder/jszxLineOrder/clientTicketOrderManage.jhtml
    public Result clientTicketOrderManage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch("/WEB-INF/jsp/outOrder/ticketOrder/ticketOrderIndex.jsp");
    }

    //    /outOrder/jszxLineOrder/clientHotelOrderManage.jhtml
    public Result clientHotelOrderManage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch("/WEB-INF/jsp/outOrder/hotelOrder/hotelOrderIndex.jsp");
    }

    public Result clientSailboatOrderManage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch("/WEB-INF/jsp/outOrder/sailboat/index.jsp");
    }
//    /outOrder/jszxLineOrder/clientFerryOrderManage.jhtml
    public Result clientFerryOrderManage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch("/WEB-INF/jsp/outOrder/ferryOrder/ferryOrderIndex.jsp");
    }


    public Result hotelOrderDetail() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            int sum = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED).size();
            int refund = 0;
            int isRefund = jszxOrderDetailService.getOrderDetailListByRefund(jszxOrder, refund).size();
            jszxOrder.setBtnStatus(sum);
            jszxOrder.setShowRefund(isRefund);
        }
        return dispatch("/WEB-INF/jsp/outOrder/hotelOrder/hotelOrderDetail.jsp");
    }

    public Result cruiseshipOrderDetail() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            int sum = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED).size();
            int refund = 0;
            int isRefund = jszxOrderDetailService.getOrderDetailListByRefund(jszxOrder, refund).size();
            jszxOrder.setBtnStatus(sum);
            jszxOrder.setShowRefund(isRefund);
        }
         return dispatch("/WEB-INF/jsp/outOrder/cruiseship/cruiseshipOrderDetail.jsp");
    }
    public Result sailboatOrderDetail() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            int sum = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED).size();
            int refund = 0;
            int isRefund = jszxOrderDetailService.getOrderDetailListByRefund(jszxOrder, refund).size();
            jszxOrder.setBtnStatus(sum);
            jszxOrder.setShowRefund(isRefund);
        }
        return dispatch("/WEB-INF/jsp/outOrder/sailboat/sailboatOrderDetail.jsp");
    }
    public Result ferryOrderDetail() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            int sum = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED).size();
            int refund = 0;
            int isRefund = jszxOrderDetailService.getOrderDetailListByRefund(jszxOrder, refund).size();
            jszxOrder.setBtnStatus(sum);
            jszxOrder.setShowRefund(isRefund);
        }
        return dispatch("/WEB-INF/jsp/outOrder/ferry/ferryOrderDetail.jsp");
    }



    /**
     * 取消订单的票号
     * @return
     */
    public Result cancelOutOrderTickets() {
        String otIdStr = (String) getParameter("otIds");
        String descStr = (String) getParameter("desc");
        if (!StringUtil.isNotBlank(otIdStr) && outOrderId == null) {
            simpleResult(map, false, "订单不存在！");
            return jsonResult(map);
        }
        jszxOrder = jszxOrderService.load(outOrderId);
        String[] otIdArr = otIdStr.split(",");
        List<JszxOrderDetail> jszxOrderDetails = new ArrayList<JszxOrderDetail>();

        for (String strId : otIdArr) {
            JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(Long.parseLong(strId));


            int refundCount = 0;
            int count = jszxOrderDetail.getCount();
            if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
                if (jszxOrderDetail.getRefundCount() != null) {
                    refundCount = jszxOrderDetail.getRefundCount();
                    refundCount = refundCount + 1;
                } else {
                    refundCount = refundCount + 1;
                }

                jszxOrderDetail.setRefundCount(refundCount);

                if (StringUtil.isNotBlank(descStr)) {
                    jszxOrderDetail.setDesc(descStr);
                }

                Long endLong = DateUtils.getDateDiffLong(jszxOrderDetail.getEndTime() , new Date());
                if (endLong < 0) {
                    simpleResult(map, false, "该订单已过期，不能退款！");
                    return jsonResult(map);
                }

                jszxOrderDetailService.cancelOrderDetail(jszxOrder, jszxOrderDetail, getLoginUser(), getCompanyUnit());


            }
        }



        simpleResult(map, true, "");


        return jsonResult(map);
    }

    /**
     * 获取未被使用的线路
     * @return
     */
    public Result getUnusedTicketStatusList() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED);

            List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
            for (JszxOrderDetail orderDetail: jszxOrderDetails) {
                int count = orderDetail.getCount();
                int refundCount = 0;
                if (orderDetail.getRefundCount() != null) {
                    refundCount = orderDetail.getRefundCount();
                }

                int totalCount = count - refundCount;
                for (int i = 0; i < totalCount; i++) {
                    JszxOrderDetail detail = new JszxOrderDetail();
                    detail.setId(orderDetail.getId());
                    detail.setPrice(orderDetail.getPrice());
                    detail.setType(orderDetail.getType());
                    detail.setTicketName(orderDetail.getTicketName());
                    detail.setTicketNo(orderDetail.getTicketNo());
                    detail.setUseStatus(orderDetail.getUseStatus());

                    jszxOrderDetailList.add(detail);
                }
            }
            return datagrid(jszxOrderDetailList);
        }
        return null;

    }



    public Result getTicketStatusList() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailService.getOutTicketList(jszxOrder);

            List<JszxOrderDetail> newOrderDetails = new ArrayList<JszxOrderDetail>();

            for (JszxOrderDetail orderDetail : jszxOrderDetails) {

                if (orderDetail.getUseStatus() != JszxOrderDetailStatus.USED
                        && orderDetail.getUseStatus() != JszxOrderDetailStatus.UNUSED) {

                    newOrderDetails.add(orderDetail);
                }
            }

            return datagrid(newOrderDetails);
        }
        return null;
    }




    /**
     * 查看线路订单详情
     * @return
     */
    public Result checkClientLineDetail() {

        if (outOrderId != null) {
            isConfim = isConfim;
            jszxOrder = jszxOrderService.load(outOrderId);
            int sum = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED).size();
            int refund = 0;
            int isRefund = jszxOrderDetailService.getOrderDetailListByRefund(jszxOrder, refund).size();
            jszxOrder.setBtnStatus(sum);
            jszxOrder.setShowRefund(isRefund);
        }

        return dispatch();
    }

    /**
     * 查看线路订单详情
     * @return
     */
    public Result checkLineDetail() {

        if (outOrderId != null) {

            isConfim = isConfim;
            jszxOrder = jszxOrderService.load(outOrderId);
            int sum = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED).size();
            int refund = 0;
            int isRefund = jszxOrderDetailService.getOrderDetailListByRefund(jszxOrder, refund).size();
            jszxOrder.setBtnStatus(sum);
            jszxOrder.setShowRefund(isRefund);
        }

        return dispatch();
    }


    public Result sendLineMsgAgain() {

        if (outOrderId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        jszxOrder = jszxOrderService.load(outOrderId);

        if (jszxOrder.getSourceType() != SourceType.LVXBANG) {

            List<JszxOrderDetail> orderDetails = jszxOrderDetailService.getOrderDetailList(jszxOrder, null);
            if (jszxOrder.getIsConfirm() == -2) {

                sendNoConfirmMsg(jszxOrder, orderDetails);

            } else if (jszxOrder.getIsConfirm() == 1) {
                sendConfirmSuccessMsg(jszxOrder, orderDetails);
            }

            int msgCount = 0;

            if (jszxOrder.getMsgCount() != null) {
                msgCount = jszxOrder.getMsgCount() + 1;
            }
            jszxOrder.setMsgCount(msgCount);

            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
        }
        map.put("ticketMsgCount", jszxOrder.getMsgCount());

        simpleResult(map, true, "发送成功");

        return jsonResult(map);

    }


    /**
     * 提交线路订单
     * @return
     */
    public Result payOutOrder() {
        if (outOrderId == null) {
            simpleResult(map, false, "订单支付失败！");
            return jsonResult(map);
        }

        jszxOrder = jszxOrderService.load(outOrderId);
        List<JszxOrderDetail> orderDetails = jszxOrderDetailService.getOrderDetailList(jszxOrder, null);
        line = lineService.loadLine(jszxOrder.getProduct().getId());
        if ("confirm".equals(line.getConfirmAndPay())) {
            //需确认时发送短信

            if (jszxOrder.getSourceType() != SourceType.LVXBANG) {
                sendConfirmMsg(jszxOrder, orderDetails);
            }
        } else {
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
//            jszxOrder.setActualPayPrice(jszxOrder.getTotalPrice());
            Float totalPrice = 0f;
            Set<Long> detailIdSet = new HashSet<Long>();
            for (JszxOrderDetail detail : orderDetails) {
                int cout = 0;
                if (detail.getRefundCount() != null) {
                    cout = detail.getCount() - detail.getRefundCount();
                } else {
                    cout = detail.getCount();
                }
                totalPrice = totalPrice + cout * detail.getActualPay();
                detailIdSet.add(detail.getTypePriceId());
            }
            //账户余额扣款
            sysUser = jszxOrder.getUser();
            balanceService.updateBalance(totalPrice.doubleValue(), AccountType.consume, sysUser.getId(), jszxOrder.getSupplierId(), getLoginUser().getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
            if (jszxOrder.getSourceType() != SourceType.LVXBANG) {
                //发送短信
                sendNoConfirmMsg(jszxOrder, orderDetails);
            }
        }
        int msgCount = 0;
        if (jszxOrder.getMsgCount() != null) {
            msgCount =  jszxOrder.getMsgCount() + 1;
        } else {
            msgCount++;
        }
        jszxOrder.setMsgCount(msgCount);
        jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
        simpleResult(map, true, "订单提交成功！");
        return jsonResult(map);
    }


    /**
     * 跳转确认订单
     * @return
     */
    public Result confimOrder() {
        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
        }
        return dispatch();
    }




    /**
     * 确认未取消的订单详情列表
     * @return
     */
    public Result getNoConfimOrderDetails() {

        List<JszxOrderDetail> orderDetails = new ArrayList<JszxOrderDetail>();

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            JszxOrderDetail orderDetail = new JszxOrderDetail();
            orderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
            orderDetails = jszxOrderDetailService.getOrderDetailList(jszxOrder, orderDetail);
        }
        return datagrid(orderDetails);
    }

    /**
     * 确认订单详情列表
     * @return
     */
    public Result getConfimOrderDetails() {

        List<JszxOrderDetail> orderDetails = new ArrayList<JszxOrderDetail>();

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            orderDetails = jszxOrderDetailService.getOrderDetailList(jszxOrder, null);
        }
        return datagrid(orderDetails);
    }

    /**
     * 保存订单详情
     * @return
     */
    public void saveOrderDetail(String orderDetailListStr, JszxOrder jszxOrder) {


        String[]  orderDetailStrArr = orderDetailListStr.split(";");

        List<JszxOrderDetail> orderDetails = new ArrayList<JszxOrderDetail>();
        if (orderDetailStrArr.length > 0) {

            Float orderTotalPrice = 0f;

            Float orderQuantityTotalPrice = 0f;

            for (String objStr : orderDetailStrArr) {

                    JSONObject resultJson = JSONObject.fromObject(objStr);

                OrderDetailEntity orderDetailEntity = (OrderDetailEntity) JSONObject.toBean(resultJson, OrderDetailEntity.class);


                for (int i = 0; i < orderDetailEntity.getCount(); i++ ) {

                    JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();
                    //订单
                    jszxOrderDetail.setJszxOrder(jszxOrder);
                    //获取门票价格类型
//                TicketPrice ticketPrice = ticketPriceService.getPrice(orderDetailEntity.getTicketPriceId());
                    //门票价格
                    jszxOrderDetail.setTypePriceId(orderDetailEntity.getTicketPriceId());
                    //门票价格类型名称
                    jszxOrderDetail.setTicketName(orderDetailEntity.getTicketName());
                    //门票价格类型
                    jszxOrderDetail.setType(formatPriceType(orderDetailEntity.getType()));
//                    Integer countInt = orderDetailEntity.getCount();
                    //门票类型数量
                    jszxOrderDetail.setCount(1);
                    jszxOrderDetail.setRestCount(1);
                    //销售额
                    jszxOrderDetail.setSalesPrice(orderDetailEntity.getSalesPrice());

                    jszxOrderDetail.setRefundCount(0);
                    Float totalPrice = orderDetailEntity.getTicketPrice();

                    //订单门票类型总价
                    jszxOrderDetail.setTotalPrice(orderDetailEntity.getTicketPrice());

                    //拱量
                    QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), orderDetailEntity.getTicketPriceId(), jszxOrder.getCompanyUnit(), jszxOrder);
                    if (quantitySales != null) {
                        Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, 1, orderDetailEntity.getTicketPrice(), orderDetailEntity.getType());
                        //设置拱量金额
                        jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                        //订单票种实际支付总额
                        jszxOrderDetail.setActualPay(quantityTotalPrice);
                        orderTotalPrice += quantityTotalPrice;
                        orderQuantityTotalPrice += quantityTotalPrice;
                    } else {
                        jszxOrderDetail.setActualPay(totalPrice);
                        orderQuantityTotalPrice += totalPrice;
                        orderTotalPrice += totalPrice;
                    }

                    //订单票种实际支付总额
                    //jszxOrderDetail.setActualPay(orderDetailEntity.getTicketPrice());

                    //订单门票类型单价
                    jszxOrderDetail.setPrice(orderDetailEntity.getTicketPrice());
                    Integer validDay = orderDetailEntity.getValiday();
                    try {
                        //使用时间
                        Date startTime = DateUtils.parse(orderDetailEntity.getOrderStartTime() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                        jszxOrderDetail.setStartTime(startTime);
                        //门票使用截至时间
                        Date endTime = DateUtils.getEndDay(startTime, validDay - 1);
                        jszxOrderDetail.setEndTime(endTime);
                    } catch (ParseException e) {
                        System.out.println(e);
                    }
                    orderDetails.add(jszxOrderDetail);

                }



            }


            jszxOrder.setActualPayPrice(orderTotalPrice); //更新拱量后的总价

            jszxOrder.setQuantityTotalPrice(orderQuantityTotalPrice); //更新拱量价

            jszxOrderDetailService.saveAll(orderDetails);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
        }

    }


    public JszxOrderDetailPriceType formatPriceType(String type) {

        if ("adult".equals(type)) {
            return JszxOrderDetailPriceType.adult;
        } else if ("student".equals(type)) {
            return JszxOrderDetailPriceType.student;
        } else if ("child".equals(type)) {
            return JszxOrderDetailPriceType.child;
        } else if ("oldman".equals(type)) {
            return JszxOrderDetailPriceType.oldman;
        } else if ("taopiao".equals(type)) {
            return JszxOrderDetailPriceType.taopiao;
        } else if ("team".equals(type)) {
            return JszxOrderDetailPriceType.team;
        } else {
            return JszxOrderDetailPriceType.other;
        }

    }

    /**
     * 保存线路订单
     * @return
     */
    public Result saveLineOrder() {
        String orderDetailListStr = (String) getParameter("orderDetailListStr");
        SysUser accountUser = balanceService.findBalanceAccountBy(getLoginUser().getId());

        if (outOrderId != null) {
            JszxOrder newJszxOrder = jszxOrderService.load(outOrderId);

            newJszxOrder.setContact(jszxOrder.getContact());
            newJszxOrder.setPhone(jszxOrder.getPhone());
            newJszxOrder.setIdcard(jszxOrder.getIdcard());
            newJszxOrder.setTotalPrice(jszxOrder.getTotalPrice());
            newJszxOrder.setSource(jszxOrder.getSource());
            jszxOrderService.update(newJszxOrder, getLoginUser(), getCompanyUnit());
            jszxOrderDetailService.delOutOrderTicketByOutOrder(newJszxOrder);
            if (StringUtil.isNotBlank(orderDetailListStr)) {
                saveOrderDetail(orderDetailListStr, newJszxOrder);
            } else {
                simpleResult(map, false, "订单保存失败！");
                return jsonResult(map);
            }

            if (newJszxOrder.getId() != null) {
                map.put("orderId", newJszxOrder.getId());
                simpleResult(map, true, "订单保存成功！");
                return jsonResult(map);
            } else {
                simpleResult(map, false, "订单保存失败！");
                return jsonResult(map);
            }

        } else {
            if (jszxOrder.getProduct() != null) {
                line = lineService.loadLine(jszxOrder.getProduct().getId());
                jszxOrder.setProName(line.getName());
                jszxOrder.setProduct(line);
                if ("confirm".equals(line.getConfirmAndPay())) {
                    jszxOrder.setIsConfirm(0);
                } else {
                    jszxOrder.setIsConfirm(-2);
                }
            }

            jszxOrder.setStatus(JszxOrderStatus.WAITING);
            jszxOrder.setActualPayPrice(jszxOrder.getTotalPrice());
            if (line.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(line.getCompanyUnit());
            }
            jszxOrderService.save(jszxOrder, getLoginUser(), getCompanyUnit());
            if (StringUtil.isNotBlank(orderDetailListStr)) {
                saveOrderDetail(orderDetailListStr, jszxOrder);
            } else {
                simpleResult(map, false, "订单保存失败！");
                return jsonResult(map);
            }

            if (jszxOrder.getId() != null) {
                map.put("orderId", jszxOrder.getId());
                simpleResult(map, true, "订单保存成功！");
                return jsonResult(map);
            } else {
                simpleResult(map, false, "订单保存失败！");
                return jsonResult(map);
            }

        }






    }


    /**
     * 跳转选择价格日历页面
     * @return
     */
    public Result selectDatePrice() {

        if (typePriceId != null && index != null && type != null && lineId != null) {
            typePriceId = typePriceId;
            type = type;
            index = index;
            lineId = lineId;
        }

        return dispatch();
    }


    public Result getLineTypeList() {

        Page pageInfo = new Page(page, rows);
        List<JszxLineDetailEntity> detailEntityList = new ArrayList<JszxLineDetailEntity>();

        if (lineId != null) {

            line = lineService.loadLine(lineId);

            List<Linetypeprice> linetypeprices = linetypepriceService.getTypePriceList(line);


            for (Linetypeprice linetypeprice : linetypeprices) {

                JszxLineDetailEntity lineAdultDetail = new JszxLineDetailEntity();
                JszxLineDetailEntity lineChildDetail = new JszxLineDetailEntity();

                lineAdultDetail.setId(linetypeprice.getId());
                lineChildDetail.setId(linetypeprice.getId());

                lineAdultDetail.setType("adult");
                lineChildDetail.setType("child");


                lineAdultDetail.setName(linetypeprice.getQuoteName());
                lineChildDetail.setName(linetypeprice.getQuoteName());

                lineAdultDetail.setPrice(linetypepricedateService.getMinAdultPrice(line, linetypeprice, new Date()));
                lineChildDetail.setPrice(linetypepricedateService.getMinChildPrice(line, linetypeprice, new Date()));

                detailEntityList.add(lineAdultDetail);
                detailEntityList.add(lineChildDetail);

            }
        }

        return datagrid(detailEntityList);
    }


    public Result addLineOrder() {

        if (lineId != null) {
            line = lineService.loadLine(lineId);
            sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        }

        return dispatch();
    }


    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Long getTypePriceId() {
        return typePriceId;
    }

    public void setTypePriceId(Long typePriceId) {
        this.typePriceId = typePriceId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JszxOrder getJszxOrder() {
        return jszxOrder;
    }

    public void setJszxOrder(JszxOrder jszxOrder) {
        this.jszxOrder = jszxOrder;
    }

    public Long getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
        this.outOrderId = outOrderId;
    }

    public Integer getIsConfim() {
        return isConfim;
    }

    public void setIsConfim(Integer isConfim) {
        this.isConfim = isConfim;
    }
}
