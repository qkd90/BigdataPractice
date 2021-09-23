package com.data.data.hmly.service.order;

import cmb.netpayment.Security;
import cmb.netpayment.Settle;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.CmbOrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-08-31,0031.
 */
public class CmbService {
    private static Security security;
    private static Settle settle;
    private OrderService orderService = SpringContextHolder.getBean("orderService");;

    private final Logger logger = Logger.getLogger(CmbService.class);

    private Map<String, Object> result = Maps.newHashMap();
    private static String branchId;
    private static String coNo;
    private static String pwd;
    private int count = 10;

    static {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String keyPath = propertiesManager.getString("CMB_KEY_PATH");
        String httpServer = propertiesManager.getString("CMB_HTTP_SERVER");
        branchId = propertiesManager.getString("BRANCH_ID");
        coNo = propertiesManager.getString("CO_NO");
        pwd = propertiesManager.getString("CMB_PWD");
        try {
            security = new Security(keyPath);
            settle = new Settle();
            int returnNum = settle.SetOptions(httpServer);
            if (returnNum != 0) {
                throw new Exception(settle.GetLastErr(returnNum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证签名
     *
     * @param baMessage
     * @return
     */
    public Boolean checkInfoFromBank(byte[] baMessage) {
        return security.checkInfoFromBank(baMessage);
    }

    /**
     * 验证签名
     *
     * @param sign
     * @return
     */
    public Boolean checkInfoFromBank(String sign) {
        return security.checkInfoFromBank(sign);
    }

    /**
     * 登录
     *
     * @return 0表示成功
     */
    public int login() {
        return login(branchId, coNo, pwd);
    }

    /**
     * 登录
     *
     * @param branchId 商户开户行代码，4位
     * @param coNo     商户代码及操作员代码6位或者10位。6位则为商户号，表示用管理员9999登录。10位则前面6位为商户号，后面4位为操作员号。
     * @param pwd      商户密码
     * @return 0表示成功
     */
    public int login(String branchId, String coNo, String pwd) {
        return settle.LoginC(branchId, coNo, pwd);
    }

    /**
     * 退出
     *
     * @return 0表示成功
     */
    public int logout() {
        return settle.Logout();
    }

    /**
     * 获取错误信息
     *
     * @param no 上一个操作返回的 int 型变
     * @return
     */
    public String getErrMsg(int no) {
        return settle.GetLastErr(no);
    }

    /**
     * 支付回调方法
     *
     * @param map 返回参数
     * @return
     */
    public Map<String, Object> doPayBack(Map<String, String> map) {
        if ("N".equals(map.get("Succeed"))) {
            result.put("success", false);
            result.put("errMsg", "付款失败");
            return result;
        }
        String billNo = map.get("BillNo");
        String cmbTime = map.get("Date");
        Long orderId = Long.valueOf(map.get("MerchantPara"));
        Order order = orderService.get(orderId);
        if (order == null) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return result;
        }
        if (!order.getBillNo().equals(billNo) || !order.getCmbTime().equals(cmbTime)) {
            result.put("success", false);
            result.put("errMsg", "订单信息错误");
            return result;
        }
        if (OrderStatus.WAIT.equals(order.getStatus())) {
            String refNo = map.get("Msg").substring(18);
            order.setRefNo(refNo);
            order.setStatus(OrderStatus.PAYED);
            order.setCmbStatus(CmbOrderStatus.unsettled);
            orderService.update(order);
        }
        String url = "";
        switch (order.getOrderType()) {
            case line:
                url = "/lvxbang/order/lineOrderDetail.jhtml?orderId=";
                break;
            case ticket:
                url = "/lvxbang/order/ticketOrderDetail.jhtml?orderId=";
                break;
            case hotel:
                url = "/lvxbang/order/hotelOrderDetail.jhtml?orderId=";
                break;
            case plan:
                url = "/lvxbang/order/planOrderDetail.jhtml?orderId=";
                break;
            case train:
                if (order.getOrderDetails().size() == 1) {
                    url = "/lvxbang/order/singleOrderDetail.jhtml?orderId=";
                } else {
                    url = "/lvxbang/order/returnOrderDetail.jhtml?orderId=";
                }
                break;
            case flight:
                if (order.getOrderDetails().size() == 1) {
                    url = "/lvxbang/order/singleOrderDetail.jhtml?orderId=";
                } else {
                    url = "/lvxbang/order/returnOrderDetail.jhtml?orderId=";
                }
                break;
            default:
                break;
        }
        result.put("success", true);
        result.put("url", url + order.getId());
        return result;
    }

    /**
     * 单个订单结账（需先登录）
     *
     * @param order 结账订单
     * @return 0表示成功
     */
    public int settleOrder(Order order) {
        return settle.SettleOrder(order.getBillNo(), order.getRefNo());
    }

    /**
     * 多个订单结账（需先登录）
     *
     * @param orderList 结账订单列表
     */
    public void settleOrder(List<Order> orderList) {
        for (Order order : orderList) {
            if (StringUtils.isBlank(order.getBillNo()) || StringUtils.isBlank(order.getRefNo())) {
                continue;
            }
            int settleResult = settleOrder(order);
            if (settleResult == 0) {
                order.setCmbStatus(CmbOrderStatus.settled);
            } else {
                logger.info("订单" + order.getId() + "结账失败,失败原因：" + getErrMsg(settleResult));
            }
            orderService.update(order);
        }
    }

    /**
     * 结账所有数据库中未结账订单（需先登录）
     *
     * @return
     */
    public void settleOrderFromSystem() {
        settleOrder(orderService.findByCmbStatus(CmbOrderStatus.unsettled));
    }

    /**
     * 设置分页查询的位置为第一页
     */
    public void pageReset() {
        settle.PageReset();
    }

    /**
     * 用来判断分页查询是否还有数据
     *
     * @return 在调用pageReset后为false；在调用了分页查询后，如果还有更多的数据，则为false，否则为true
     */
    public Boolean isLastPage() {
        return settle.m_bIsLastPage;
    }

    /**
     * 结账接口查询的未结账订单（需先登录）
     */
    public void settleOrderFromQuery() {
        pageReset();
        List<Order> orderList = Lists.newArrayList();
        while (!isLastPage()) {
            StringBuffer stringBuffer = new StringBuffer();
            int resultNum = settle.QueryUnsettledOrderByPage(count, stringBuffer);
            if (resultNum != 0) {
                logger.info("未结账订单分页查询失败,失败原因：" + getErrMsg(resultNum));
                return;
            }
            List<String> strList = Lists.newArrayList(stringBuffer.toString().split("\n"));
            for (int i = 0; i < strList.size() / 4; i++) {
                Order order = orderService.findByBillNoAndCmbTime(strList.get(i), strList.get(i + 2));
                if (order == null) {
                    continue;
                }
                if (!order.getPrice().equals(Float.valueOf(strList.get(i + 1)))) {
                    continue;
                }
                order.setRefNo(strList.get(i + 3));
                if (OrderStatus.WAIT.equals(order.getStatus())) {
                    order.setStatus(OrderStatus.PAYED);
                }
                order.setCmbStatus(CmbOrderStatus.unsettled);
                orderList.add(order);
            }
        }
        settleOrder(orderList);
    }
}
