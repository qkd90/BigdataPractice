package com.data.data.hmly.action.outOrder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.outOrder.dataEntities.JszxOrderDetailEntity;
import com.data.data.hmly.action.outOrder.dataEntities.OrderDetailEntity;
import com.data.data.hmly.action.outOrder.utils.HTMLFilterUtils;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.QuantitySalesService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.ProductValidateRecord;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.ctriphotel.base.StringUtil;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.outOrder.JszxOrderDetailService;
import com.data.data.hmly.service.outOrder.JszxOrderService;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailPriceType;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.outOrder.entity.enums.SourceType;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dy on 2016/2/23.
 */
public class OutOrderAction extends FrameBaseAction implements ModelDriven<JszxOrder> {
    private JszxOrder jszxOrder = new JszxOrder();
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private ProductService productService;

    @Resource
    private QuantitySalesService quantitySalesService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private TicketPriceService ticketPriceService;

    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private BalanceService balanceService;

    private SysUser sysUser;
    private Ticket ticket;
    @Resource
    private AccountLogService accountLogService;



    @Resource
    private TicketService ticketService;

    Map<String, Object> map = new HashMap<String, Object>();
    List<JszxOrder> jszxOrderList = new ArrayList<JszxOrder>();

    //outOrder/outOrder/getOutOrderList.jhtml
    private Integer			page				= 1;
    private Integer			rows				= 10;

    private Long outOrderId;

    private Long ticketPriceId;

    private Long ticketId;

    private Integer index;

    private List<OrderDetailEntity> orderDetailEntities;

    private String type;

    private JszxOrderDetail jszxOrderDetail;





    public Result getTicketValidataInfo() {

        List<ProductValidateRecord> validateRecords = new ArrayList<ProductValidateRecord>();
        if (outOrderId != null) {
            ProductValidateCode productValidateCode = new ProductValidateCode();
            productValidateCode.setOrderId(outOrderId);
            List<ProductValidateCode> codeList = productValidateCodeService.getValidateInfoList(productValidateCode);
            validateRecords = productValidateCodeService.findValidateRecodsInfoList(codeList);
            for (ProductValidateRecord productValidateRecord : validateRecords) {
                productValidateRecord.setValidateByName(sysUserService.load(productValidateRecord.getValidateBy()).getAccount());
            }
        }
        return datagrid(validateRecords);
    }

    public Result checkClientTicketDetail() {

        if (outOrderId != null) {
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
     * 获取需要确认的订单
     * @return
     */
    public Result getClientOrderList() {

        Page pageInfo = new Page(page, rows);

        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");
        String startUseTimeStr = (String) getParameter("startUseTimeStr");
        String endUseTimeStr = (String) getParameter("endUseTimeStr");
        String startCheckoutTimeStr = (String) getParameter("startCheckoutTimeStr");
        String endCheckoutTimeStr = (String) getParameter("endCheckoutTimeStr");


        if (type != null) {
            jszxOrder.setProType(ProductType.valueOf(type));
            if (JszxOrderDetailStatus.UNUSED.equals(jszxOrder.getDetailUseStatus())) {
                jszxOrder.setStatus(JszxOrderStatus.PAYED);
            }

            if (StringUtil.isNotBlank(startCreateTimeStr)) {
                Date startTime = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                jszxOrder.setStartCreateTime(startTime);
            }
            if (StringUtil.isNotBlank(endCreateTimeStr)) {
                Date endTime = DateUtils.getDate(endCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                jszxOrder.setEndCreateTime(DateUtils.getEndDay(endTime, 0));
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
            if (StringUtils.isNotBlank(startCheckoutTimeStr)) {
                Date startCheckoutDate = com.zuipin.util.DateUtils.getDate(startCheckoutTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                startCheckoutDate = com.zuipin.util.DateUtils.getEndDay(startCheckoutDate, 0);
                jszxOrder.setStartCheckoutTime(startCheckoutDate);
            }
            if (StringUtils.isNotBlank(endCheckoutTimeStr)) {
                Date endCheckoutDate = com.zuipin.util.DateUtils.getDate(endCheckoutTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                endCheckoutDate = com.zuipin.util.DateUtils.getEndDay(endCheckoutDate, 0);
                jszxOrder.setEndCheckoutTime(endCheckoutDate);
            }
            jszxOrderList = jszxOrderService.getClientOrderList(jszxOrder, getLoginUser(), getCompanyUnit(), isSiteAdmin(), isSupperAdmin(), pageInfo);
            jszxOrderList = jszxOrderService.getTicketJszxOrderTypetList(jszxOrder, jszxOrderList);
        }

        return datagrid(jszxOrderList, pageInfo.getTotalCount());

    }


    /**
     * 直销订单线路订单列表
     * @return
     */
    public Result loadClientTicketExcel() {

        jszxOrder.setProType(ProductType.scenic);
        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");

        boolean isSuplier = true;

        if (StringUtil.isNotBlank(startCreateTimeStr)) {
            Date startTime = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtil.isNotBlank(endCreateTimeStr)) {
            Date endTime = DateUtils.getDate(endCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setEndCreateTime(DateUtils.getEndDay(endTime, 0));
        }
        map = jszxOrderService.getTicketExcel(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), isSuplier);


        if ((boolean) map.get("success")) {
            simpleResult(map, true, "导出成功！");
        } else {
            simpleResult(map, false, "导出成功！");
        }

        return jsonResult(map);


    }


    /**
     * 直销订单线路订单列表
     * @return
     */
    public Result loadClientLineExcel() {

        jszxOrder.setProType(ProductType.line);

        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");

        String startUseTimeStr = (String) getParameter("startUseTimeStr");
        String endUseTimeStr = (String) getParameter("endUseTimeStr");

        if (StringUtils.isNotBlank(startUseTimeStr)) {
            Date startUseDate = DateUtils.getDate(startUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartUseTime(startUseDate);
        }
        if (StringUtils.isNotBlank(endUseTimeStr)) {
            Date endUseDate = DateUtils.getDate(endUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endUseDate = DateUtils.getEndDay(endUseDate, 0);
            jszxOrder.setEndUseTime(endUseDate);
        }

        boolean isSuplier = true;

        if (StringUtil.isNotBlank(startCreateTimeStr)) {
            Date startTime = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtil.isNotBlank(endCreateTimeStr)) {
            Date endTime = DateUtils.getDate(endCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setEndCreateTime(DateUtils.getEndDay(endTime, 0));
        }

        map = jszxOrderService.getLineExcel(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), isSuplier);

        if ((boolean) map.get("success")) {
            simpleResult(map, true, "导出成功！");
        } else {
            simpleResult(map, false, "导出成功！");
        }
        return jsonResult(map);
    }

    /**
     * 直销订单线路订单列表
     * @return
     */
    public Result loadTicketExcel() {

        jszxOrder.setProType(ProductType.scenic);
        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");

        if (StringUtil.isNotBlank(startCreateTimeStr)) {
            Date startTime = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtil.isNotBlank(endCreateTimeStr)) {
            Date endTime = DateUtils.getDate(endCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setEndCreateTime(DateUtils.getEndDay(endTime, 0));
        }
        map = jszxOrderService.getTicketExcel(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), false);


        if ((boolean) map.get("success")) {
            simpleResult(map, true, "导出成功！");
        } else {
            simpleResult(map, false, "导出成功！");
        }
        return jsonResult(map);


    }


    /**
     * 直销订单线路订单列表
     * @return
     */
    public Result loadLineExcel() {



        jszxOrder.setProType(ProductType.line);

        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");

        String startUseTimeStr = (String) getParameter("startUseTimeStr");
        String endUseTimeStr = (String) getParameter("endUseTimeStr");

        if (StringUtils.isNotBlank(startUseTimeStr)) {
            Date startUseDate = DateUtils.getDate(startUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartUseTime(startUseDate);
        }
        if (StringUtils.isNotBlank(endUseTimeStr)) {
            Date endUseDate = DateUtils.getDate(endUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endUseDate = DateUtils.getEndDay(endUseDate, 0);
            jszxOrder.setEndUseTime(endUseDate);
        }

        if (StringUtil.isNotBlank(startCreateTimeStr)) {
            Date startTime = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtil.isNotBlank(endCreateTimeStr)) {
            Date endTime = DateUtils.getDate(endCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setEndCreateTime(DateUtils.getEndDay(endTime, 0));
        }

        map = jszxOrderService.getLineExcel(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), false);


        if ((boolean) map.get("success")) {
            simpleResult(map, true, "导出成功！");
        } else {
            simpleResult(map, false, "导出成功！");
        }
        return jsonResult(map);
    }


    /**
     * 直销订单线路订单列表
     * @return
     */
    public Result getJszxOrderLineList() {

        Page pageInfo = new Page(page, rows);


        String proNameStr = (String) getParameter("proName");
        String contactStr = (String) getParameter("contact");
        String phoneStr = (String) getParameter("phone");
        String orderNoStr = (String) getParameter("orderNo");
        String createbyNameStr = (String) getParameter("createbyName");
        String statusStr = (String) getParameter("statusStr");
        String useStatusStr = (String) getParameter("useStatusStr");
        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");
        String startUseTimeStr = (String) getParameter("startUseTimeStr");
        String endUseTimeStr = (String) getParameter("endUseTimeStr");


        if (StringUtil.isNotBlank(orderNoStr)) {
            jszxOrder.setOrderNo(orderNoStr);
        }

        if (StringUtil.isNotBlank(createbyNameStr)) {
            sysUser = new SysUser();
            sysUser.setAccount(createbyNameStr);
            jszxOrder.setUser(sysUser);
        }

        if (StringUtils.isNotBlank(startUseTimeStr)) {
            Date startUseDate = DateUtils.getDate(startUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartUseTime(startUseDate);
        }
        if (StringUtils.isNotBlank(endUseTimeStr)) {
            Date endUseDate = DateUtils.getDate(endUseTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endUseDate = DateUtils.getEndDay(endUseDate, 0);
            jszxOrder.setEndUseTime(endUseDate);
        }

        if (StringUtils.isNotBlank(startCreateTimeStr)) {
            Date startCreateDate = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startCreateDate);
        }
        if (StringUtils.isNotBlank(endCreateTimeStr)) {
            Date endCreateDate = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endCreateDate = DateUtils.getEndDay(endCreateDate, 0);
            jszxOrder.setEndCreateTime(endCreateDate);
        }

        if (StringUtils.isNotBlank(phoneStr)) {
            jszxOrder.setPhone(phoneStr);
        }

        if (StringUtils.isNotBlank(proNameStr)) {
            jszxOrder.setProName(proNameStr);
        }

        if (StringUtils.isNotBlank(contactStr)) {
            jszxOrder.setContact(contactStr);
        }
        jszxOrder.setProType(ProductType.line);

        if ("CANCELED".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
        }
        if ("UNCANCEL".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.UNCANCEL);
        }
        if ("PAYED".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
        }
        if ("UNPAY".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.UNPAY);
        }
        if ("WAITING".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.WAITING);
        }


        if ("UNUSED".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.UNUSED);
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
        }
        if ("CANCEL".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.CANCEL);
        }
        if ("REFUNDING".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.REFUNDING);
        }
        if ("USED".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.USED);
        }

//        jszxOrder.setProduct(product);

        jszxOrderList = jszxOrderService.getOutOrderList(jszxOrder, pageInfo, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin());

        jszxOrderList = jszxOrderService.getLineJszxOrderTypetList(jszxOrder, jszxOrderList);

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");

        return datagrid(jszxOrderList, pageInfo.getTotalCount(), jsonConfig);
    }




    /**
     * 直销门票订单列表处理
     * @return
     */
    public Result getJszxOrderTicketList() {

        Page pageInfo = new Page(page, rows);


        String proNameStr = (String) getParameter("proName");
        String contactStr = (String) getParameter("contact");
        String orderNoStr = (String) getParameter("orderNo");
        String createbyNameStr = (String) getParameter("createbyName");
        String phoneStr = (String) getParameter("phone");
        String statusStr = (String) getParameter("statusStr");
        String useStatusStr = (String) getParameter("useStatusStr");

        String startCreateTimeStr = (String) getParameter("startCreateTimeStr");
        String endCreateTimeStr = (String) getParameter("endCreateTimeStr");


        if (StringUtil.isNotBlank(orderNoStr)) {
//            orderNoStr = orderNoStr.replace(" ", "");
            jszxOrder.setOrderNo(orderNoStr);
        }

        if (StringUtil.isNotBlank(createbyNameStr)) {
            sysUser = new SysUser();
//            createbyNameStr = createbyNameStr.replace(" ", "");
            sysUser.setAccount(createbyNameStr);
            jszxOrder.setUser(sysUser);
        }

        if (StringUtils.isNotBlank(startCreateTimeStr)) {
            Date startCreateDate = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startCreateDate);
        }
        if (StringUtils.isNotBlank(endCreateTimeStr)) {
            Date endCreateDate = DateUtils.getDate(startCreateTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endCreateDate = DateUtils.getEndDay(endCreateDate, 0);
            jszxOrder.setEndCreateTime(endCreateDate);
        }


        if (StringUtils.isNotBlank(phoneStr)) {
//            phoneStr = phoneStr.replace(" ", "");
            jszxOrder.setPhone(phoneStr);
        }

        if (StringUtils.isNotBlank(proNameStr)) {
//            proNameStr = proNameStr.replace(" ", "");
            jszxOrder.setProName(proNameStr);
        }

        if (StringUtils.isNotBlank(contactStr)) {
//            contactStr = contactStr.replace(" ", "");
            jszxOrder.setContact(contactStr);
        }
        jszxOrder.setProType(ProductType.scenic);

        if ("CANCELED".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
        }
        if ("UNCANCEL".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.UNCANCEL);
        }
        if ("PAYED".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
        }
        if ("UNPAY".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.UNPAY);
        }

        if ("UNUSED".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.UNUSED);
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
        }
        if ("CANCEL".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.CANCEL);
        }
        if ("REFUNDING".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.REFUNDING);
        }
        if ("USED".equals(useStatusStr)) {
            jszxOrder.setDetailUseStatus(JszxOrderDetailStatus.USED);
        }


        jszxOrderList = jszxOrderService.getOutOrderList(jszxOrder, pageInfo, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin());

        jszxOrderList = jszxOrderService.getTicketJszxOrderTypetList(jszxOrder, jszxOrderList);
        return datagrid(jszxOrderList, pageInfo.getTotalCount());

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
            int restCount = jszxOrderDetail.getRestCount();
            if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
                if (jszxOrderDetail.getRefundCount() != null) {
                    refundCount = jszxOrderDetail.getRefundCount();
                }
                refundCount = refundCount + 1;
                restCount = restCount -1;
                if (count == refundCount) {
                    jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
                }
                jszxOrderDetail.setRestCount(restCount);
                jszxOrderDetail.setRefundCount(refundCount);
                if (StringUtil.isNotBlank(descStr)) {
                    jszxOrderDetail.setDesc(descStr);
                }
                Long endLong = DateUtils.getDateDiffLong(jszxOrderDetail.getEndTime(), new Date());
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
     * 录入订单页面
     * @return
     */
    public Result addOrder() {

        String outOrderIdStr = (String) getParameter("outOrderId");

        if (StringUtils.isNotBlank(outOrderIdStr)) {
            jszxOrder = jszxOrderService.load(Long.parseLong(outOrderIdStr));
        }
        return dispatch();
    }


    public Result getTicketStatusList() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailService.getOutTicketList(jszxOrder);
            return datagrid(jszxOrderDetails);
        }
        return null;
    }


    public Result getTicketTypeList() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailService.getOutTicketList(jszxOrder);
            List<JszxOrderDetailEntity> jszxOrderDetailEntityList = new ArrayList<JszxOrderDetailEntity>();
            jszxOrderDetailEntityList = formatterOticket(jszxOrderDetails);
            return datagrid(jszxOrderDetailEntityList);
        }
        return null;

    }

    public List<JszxOrderDetailEntity> formatterOticket(List<JszxOrderDetail> jszxOrderDetails) {

        List<JszxOrderDetailEntity> jszxOrderDetailEntityList = new ArrayList<JszxOrderDetailEntity>();

        Set<Long> ticketPriceIdSet = new HashSet<Long>();

        for (JszxOrderDetail jszxOrderDetail : jszxOrderDetails) {
            ticketPriceIdSet.add(jszxOrderDetail.getTypePriceId());
        }

        Map<String, List<JszxOrderDetail>> tempMap = new HashMap<String, List<JszxOrderDetail>>();

        Iterator<Long> it = ticketPriceIdSet.iterator();

        for (Long tpIid : ticketPriceIdSet) {
            List<JszxOrderDetail> jszxOrderDetailTemp = new ArrayList<JszxOrderDetail>();
            for (JszxOrderDetail temp : jszxOrderDetails) {
                TicketPrice ticketPrice = ticketPriceService.getPrice(tpIid);
                if (ticketPrice.getId() == temp.getTypePriceId()) {
                    jszxOrderDetailTemp.add(temp);
                }
            }
            tempMap.put(tpIid.toString(), jszxOrderDetailTemp);
        }

        for (String key : tempMap.keySet()) {

            JszxOrderDetail jszxOrderDetail = tempMap.get(key).get(0);
            JszxOrderDetailEntity jszxOrderDetailEntity = new JszxOrderDetailEntity();
            jszxOrderDetailEntity.setName(jszxOrderDetail.getTicketName());
            jszxOrderDetailEntity.setType(ticketPriceService.findById(jszxOrderDetail.getTypePriceId()).getType());
            jszxOrderDetailEntity.setCount(tempMap.get(key).size());
            jszxOrderDetailEntity.setPrice(jszxOrderDetail.getPrice());
            jszxOrderDetailEntityList.add(jszxOrderDetailEntity);

        }

        return jszxOrderDetailEntityList;

    }
    /**
     * 获取产品下的门票列表
     * @return
     */
    public Result getProList() {

        String proIdStr = (String) getParameter("proId");
        Page pageInfo = new Page(page, rows);

        List<TicketPrice> priceList = new ArrayList<TicketPrice>();

        if (StringUtils.isNotBlank(proIdStr)) {
            Product ticket = productService.get(Long.parseLong(proIdStr));
            priceList = ticketPriceService.findTicketList(Long.parseLong(proIdStr), pageInfo);
        }


        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("ticket");
        return datagrid(priceList, pageInfo.getTotalCount(), jsonConfig);

    }


    /**
     * 查看订单详情
     * @return
     */
    public Result checkDetail() {

        if (outOrderId != null) {
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
     * 获取票号列表
     * @return
     */
    public Result getOutTicketList() {

        List<JszxOrderDetail> jszxOrderDetails = new ArrayList<JszxOrderDetail>();

        Page pageInfo = new Page(page, rows);

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            jszxOrderDetails = jszxOrderDetailService.getOutTicketListByoutOrderId(pageInfo, jszxOrder);
        }

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("outOrder");

        return datagrid(jszxOrderDetails, pageInfo.getTotalCount(), jsonConfig);
    }

    public Result delOutOrderTicket() {

        String outOrderIdStr = (String) getParameter("outOrderIdStr");
        String tickePriceIdStr = (String) getParameter("tickePriceId");

        if (StringUtils.isNotBlank(outOrderIdStr) && StringUtil.isNotBlank(tickePriceIdStr)) {
            jszxOrder = jszxOrderService.load(Long.parseLong(outOrderIdStr));
            TicketPrice ticketPrice = ticketPriceService.getPrice(Long.parseLong(tickePriceIdStr));

            JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();

            jszxOrderDetail.setJszxOrder(jszxOrder);
            jszxOrderDetail.setTypePriceId(ticketPrice.getId());
            jszxOrderDetailService.delOutOrderTicket(jszxOrderDetail);
        }

        simpleResult(map, true, "");
        return jsonResult(map);
    }


    public Result deleteOutOrder() {
        String ids = (String) getParameter("ids");
        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            jszxOrderDetailService.delOutOrderTicketByOutOrder(jszxOrder);
            jszxOrderService.deleteOutOrder(jszxOrder);
            simpleResult(map, true, "");
            return jsonResult(map);
        } else if (StringUtils.isNotBlank(ids)) {
            String[] idsArr = ids.split(",");
            for (String strId : idsArr) {
                jszxOrder = jszxOrderService.load(Long.parseLong(strId));
                jszxOrderDetailService.delOutOrderTicketByOutOrder(jszxOrder);
                jszxOrderService.deleteOutOrder(jszxOrder);
            }
            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);
    }

    /**
     * 增加票号
     * @return
     * @throws java.text.ParseException
     */
    public void saveOrderDetail(String orderDetailListStr, JszxOrder jszxOrder) throws java.text.ParseException {

        String[]  orderDetailStrArr = orderDetailListStr.split(";");

        List<JszxOrderDetail> orderDetails = new ArrayList<JszxOrderDetail>();

        Float orderTotalPrice = 0f;

        Float orderQuantityTotalPrice = 0f;

        if (orderDetailStrArr.length > 0) {

            for (String objStr : orderDetailStrArr) {

                JSONObject resultJson = JSONObject.fromObject(objStr);

                OrderDetailEntity orderDetailEntity = (OrderDetailEntity) JSONObject.toBean(resultJson, OrderDetailEntity.class);

                JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();

                //订单
                jszxOrderDetail.setJszxOrder(jszxOrder);
                //获取门票价格类型
                TicketPrice ticketPrice = ticketPriceService.getPrice(orderDetailEntity.getTicketPriceId());


                //门票价格
                jszxOrderDetail.setTypePriceId(orderDetailEntity.getTicketPriceId());
                //门票价格类型名称
                jszxOrderDetail.setTicketName(ticketPrice.getName());
                //门票价格类型
                jszxOrderDetail.setType(formatPriceType(ticketPrice.getType()));

                Integer countInt = orderDetailEntity.getCount();
                //门票类型数量
                jszxOrderDetail.setCount(countInt);

                //门票类型剩余数量（未验票数量）
                jszxOrderDetail.setRestCount(countInt);

                //门票类型数量
                jszxOrderDetail.setSalesPrice(orderDetailEntity.getSalesPrice());

                Float totalPrice = orderDetailEntity.getTicketPrice() * countInt;
                //订单门票类型总价
                jszxOrderDetail.setTotalPrice(totalPrice);

                //拱量
                QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), ticketPrice.getId(), jszxOrder.getCompanyUnit(), jszxOrder);
                if (quantitySales != null) {
                    Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, countInt, orderDetailEntity.getTicketPrice(), "scenic");
                    jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                    jszxOrderDetail.setActualPay(quantityTotalPrice);
                    orderTotalPrice += quantityTotalPrice;
                    orderQuantityTotalPrice += quantityTotalPrice;
                } else {
                    jszxOrderDetail.setActualPay(totalPrice);
                    orderQuantityTotalPrice += totalPrice;
                    orderTotalPrice += totalPrice;
                }

                //订单门票类型单价
                jszxOrderDetail.setPrice(orderDetailEntity.getTicketPrice());

                Integer validDay = orderDetailEntity.getValiday();
                //使用时间
                Date startTime = DateUtils.parse(orderDetailEntity.getOrderStartTime() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                jszxOrderDetail.setStartTime(startTime);
                //门票使用截至时间
                Date endTime = DateUtils.getEndDay(startTime, validDay - 1);
                jszxOrderDetail.setEndTime(endTime);
                jszxOrderDetail.setRefundCount(0);
                orderDetails.add(jszxOrderDetail);
            }

            jszxOrder.setActualPayPrice(orderQuantityTotalPrice); //更新拱量后的总价

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




    public Result getJszxOrderList() {

        Page pageInfo = new Page(page, rows);


        String proNameStr = (String) getParameter("proName");
        String contactStr = (String) getParameter("contact");
        String typeStr = (String) getParameter("typeStr");
        String phoneStr = (String) getParameter("phone");
        String statusStr = (String) getParameter("statusStr");

        Product product = new Product();


        if (StringUtils.isNotBlank(phoneStr)) {
            jszxOrder.setPhone(phoneStr);
        }

        if (StringUtils.isNotBlank(proNameStr)) {
            product.setName(proNameStr);
        }

        if (StringUtils.isNotBlank(contactStr)) {
            jszxOrder.setContact(contactStr);
        }
        if ("line".equals(typeStr)) {
            product.setProType(ProductType.line);
            jszxOrder.setProType(ProductType.line);
        }
        if ("scenic".equals(typeStr)) {
            product.setProType(ProductType.scenic);
            jszxOrder.setProType(ProductType.scenic);
        }

        if ("CANCELED".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
        }
        if ("UNCANCEL".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.UNCANCEL);
        }
        if ("PAYED".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.PAYED);
        }
        if ("UNPAY".equals(statusStr)) {
            jszxOrder.setStatus(JszxOrderStatus.UNPAY);
        }

        jszxOrder.setProduct(product);


        jszxOrderList = jszxOrderService.getOutOrderList(jszxOrder, pageInfo, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin());


        JsonConfig jsonConfig = null;

        if ("line".equals(typeStr)) {
             jsonConfig = JsonFilter.getIncludeConfig("product" , "supplierUnit");
        } else {
            jsonConfig = JsonFilter.getIncludeConfig("product");
        }



        return datagrid(jszxOrderList, pageInfo.getTotalCount(), jsonConfig);
    }


    /**
     * 保存录入订单
     * @return
     */
    public Result saveOutOrder() {
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
                try {
                    saveOrderDetail(orderDetailListStr, newJszxOrder);
                } catch (ParseException e) {
                    System.out.println(e);
                }
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
            if (ticketId != null) {
                ticket = ticketService.loadTicket(ticketId);

                jszxOrder.setProName(ticket.getName());

                jszxOrder.setProduct(ticket);
            }
            if (ticket.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(ticket.getCompanyUnit());
            }
            jszxOrder.setStatus(JszxOrderStatus.UNPAY);
            jszxOrder.setCompanyUnit(getCompanyUnit());
            jszxOrder.setOrderSourceId(jszxOrder.getId());
            jszxOrder.setSourceType(SourceType.JSZX);
            jszxOrderService.save(jszxOrder, getLoginUser(), getCompanyUnit());

            if (StringUtil.isNotBlank(orderDetailListStr)) {
                try {
                    saveOrderDetail(orderDetailListStr, jszxOrder);
                } catch (ParseException e) {
                    System.out.println(e);
                }

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

    public Result checkUserBalance() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            SysUser accountUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
            Double rechargePrice = jszxOrder.getActualPayPrice() - accountUser.getBalance();
            if (rechargePrice > 0 ) {
                map.put("rechargePrice", rechargePrice);
                simpleResult(map, false, "还需充值：￥" + rechargePrice.floatValue()  + "，您确定要充值吗？");
            } else {
                simpleResult(map, true, "余额充足！");
            }
        } else {
            simpleResult(map, false, "获取金额失败！");
        }

        return jsonResult(map);
    }

    public Result checkBuyerBalance() {

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            SysUser accountUser = balanceService.findBalanceAccountBy(jszxOrder.getUser().getId());
            Double rechargePrice = jszxOrder.getActualPayPrice() - accountUser.getBalance();
            if (rechargePrice > 0 ) {
                map.put("rechargePrice", rechargePrice);
                simpleResult(map, false, "还需充值：￥" + rechargePrice.floatValue()  + "，请联系下单者，并提醒充值！");
            } else {
                simpleResult(map, true, "余额充足！");
            }
        } else {
            simpleResult(map, false, "获取金额失败！");
        }

        return jsonResult(map);
    }



    public Result confimOrder() {
        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
        }
        return dispatch();
    }

    public Result getConfimOrderDetails() {

        List<JszxOrderDetail> orderDetails = new ArrayList<JszxOrderDetail>();

        if (outOrderId != null) {
            jszxOrder = jszxOrderService.load(outOrderId);
            orderDetails = jszxOrderDetailService.getOutTicketList(jszxOrder);


        }
        return datagrid(orderDetails);
    }

    /**
     * 支付订单
     * @return
     */
    public Result payOutOrder() {
        if (outOrderId == null) {
            simpleResult(map, false, "订单支付失败！");
            return jsonResult(map);
        }

        jszxOrder = jszxOrderService.load(outOrderId);
        Double totalPrice = jszxOrder.getActualPayPrice().doubleValue();
        SysUser user = jszxOrder.getUser();
        balanceService.updateBalance(totalPrice, AccountType.consume, user.getId(), jszxOrder.getSupplierId(), getLoginUser().getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
        jszxOrder.setStatus(JszxOrderStatus.PAYED);
        jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice());
        int msgCount = 0;
        if (jszxOrder.getMsgCount() != null) {
            msgCount =  jszxOrder.getMsgCount() + 1;
        } else {
            msgCount++;
        }
        jszxOrder.setMsgCount(msgCount);
        jszxOrderService.update(jszxOrder, user, jszxOrder.getCompanyUnit());

        //发送短信
        jszxOrderService.doSendMsg(jszxOrder, getSite().getId());
        simpleResult(map, true, "支付成功");
        return jsonResult(map);
    }

    /**
     * 重新发送短信
     * @return
     */
    public Result sendTicketMsgAgain() {

        if (outOrderId == null) {
            simpleResult(map, false, "发送失败！");
            return jsonResult(map);
        }

        jszxOrder = jszxOrderService.load(outOrderId);

        jszxOrderService.doSendMsg(jszxOrder, getSite().getId());

        int msgCount = 0;

        if (jszxOrder.getMsgCount() != null) {
            msgCount = jszxOrder.getMsgCount() + 1;
        }
        jszxOrder.setMsgCount(msgCount);

        jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());

        map.put("ticketMsgCount", jszxOrder.getMsgCount());

        simpleResult(map, true, "发送成功");

        return jsonResult(map);
    }




    /**
     * 外入订单首页
     * @return
     */
    public Result manage() {
        sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
//        sysUser = sysUserService.load(getLoginUser().getId());
        return dispatch();
    }

    /**
     * 该验证码下的订单门票
     * @return
     */
    public Result getProTicketList() {

		String orderNo = (String) getParameter("orderNO");
		String productId = (String) getParameter("productId");

		if (StringUtil.isNotBlank(orderNo) && StringUtil.isNotBlank(productId)) {

            JszxOrderDetail detail = jszxOrderDetailService.getOrderDetailByOrderNo(orderNo);
//            Product product = productService.get(Long.parseLong(productId));
//            jszxOrder = jszxOrderService.findOutOrderByOrderNoAndProduct(orderNo, product);
//            List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailService.getOutTicketList(jszxOrder, JszxOrderDetailStatus.UNUSED);
//            List<JszxOrderDetailEntity> orderTicketEntities = formatterOticket(jszxOrderDetails);



            map.put("detail", detail);

            simpleResult(map, true, "");

            return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("")));

		}

        simpleResult(map, false, "");

        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("")));
	}


    /**
     * 进入录入订单时，先暂存一个订单
     * @return
     */
    public Result saveTempOutOrder() {

        String ticketId = (String) getParameter("ticketId");


        if (StringUtil.isNotBlank(ticketId)) {
            ticket = ticketService.loadTicket(Long.parseLong(ticketId));
            jszxOrder.setProduct(ticket);
        }
        jszxOrderService.save(jszxOrder, getLoginUser(), getCompanyUnit());
        map.put("outOrderId", jszxOrder.getId());
        simpleResult(map, true, "");
        return jsonResult(map);
    }


    /**
     * 外入订单录入页面
     * @return
     */
    public Result addTicketOrder() {
        if (ticketId != null) {
            sysUser = balanceService.findBalanceAccountBy(getLoginUser().getId());

            ticket = ticketService.loadTicket(ticketId);

        }
        return dispatch();
    }


    /**
     * 外入订单录入页面
     * @return
     */
    public Result addOutOrder() {


        String outOrderIdStr = (String) getParameter("outOrderId");

        if (StringUtils.isNotBlank(outOrderIdStr)) {
            jszxOrder = jszxOrderService.load(Long.parseLong(outOrderIdStr));
            ticket = ticketService.findTicketById(jszxOrder.getProduct().getId());
        }
        return dispatch();
    }

    /**
     * 编辑外入订单录入页面
     * @return
     */
    public Result editOutOrder() {
        String outOrderIdStr = (String) getParameter("outOrderId");
        if (StringUtils.isNotBlank(outOrderIdStr)) {
            jszxOrder = jszxOrderService.load(Long.parseLong(outOrderIdStr));
        }
        return dispatch();
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

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

                int totalCount = 0;

                ProductValidateCode productValidateCode = productValidateCodeService.getPvCode(orderDetail.getTicketNo());

                if (productValidateCode != null) {
                    totalCount = productValidateCode.getOrderCount();
                }


                for (int i = 0; i < totalCount; i++) {
                    JszxOrderDetail detail = new JszxOrderDetail();
                    detail.setId(orderDetail.getId());
                    detail.setPrice(orderDetail.getPrice());
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


    public Result selectDatePrice() {

        if (ticketPriceId != null && index != null && ticketId != null) {
            ticketPriceId = ticketPriceId;
            index = index;
            ticketId = ticketId;
        }

        return dispatch();
    }

    public Result testApi() {
        return dispatch();
    }


    @Override
    public JszxOrder getModel() {
        return jszxOrder;
    }

    public JszxOrder getJszxOrder() {
        return jszxOrder;
    }

    public void setJszxOrder(JszxOrder jszxOrder) {
        this.jszxOrder = jszxOrder;
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

    public Long getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
        this.outOrderId = outOrderId;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }


    public List<OrderDetailEntity> getOrderDetailEntities() {
        return orderDetailEntities;
    }

    public void setOrderDetailEntities(List<OrderDetailEntity> orderDetailEntities) {
        this.orderDetailEntities = orderDetailEntities;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

