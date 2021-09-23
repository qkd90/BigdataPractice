package com.data.data.hmly.service.order;

import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.dao.OrderBillDao;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderBill;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.vo.OrderBillSummaryData;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/10/26.
 */
@Service
public class OrderBillService {


    @Resource
    private OrderBillDao orderBillDao;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private UserService userService;


    /**
     * 已废弃，请参加com.data.data.hmly.service.balance.BalanceService#doOrderDetailBill(com.data.data.hmly.service.order.entity.OrderDetail)
     * @param orderDetail
     */
    @Deprecated
    public void doOrderDetailBill(OrderDetail orderDetail) {
        User orderLogUser = orderLogService.getSysOrderLogUser();
        Order order = orderDetail.getOrder();
        Product product = orderDetail.getProduct();
        SysUser productUser = product.getUser();
        ProductSource productSource = product.getSource();
        Long orderId = order.getId();
        // 订单详情状态
        OrderDetailStatus status = orderDetail.getStatus();
        if (productSource != null && !ProductSource.LXB.equals(productSource)) {
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                    + "非本平台产品, 不结算", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
            return;
        }
        if (!OrderDetailStatus.SUCCESS.equals(status)) {
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                            + "状态不为成功! 不结算! 现在状态: " + status.getDescription(),
                    orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
            return;
        }
        // 结算状态
        Integer billStatus = orderDetail.getOrderBillStatus();
        // 结算方式
        OrderBillType billType = orderDetail.getOrderBillType();
        // 结算价格
        Float billPrice = orderDetail.getOrderBillPrice();
        // 结算日期
        Date billDate = orderDetail.getOrderBillDate();
        Calendar billCalendar = Calendar.getInstance();
        billCalendar.clear();
        billCalendar.setTime(billDate);
        Calendar nowCalendar = Calendar.getInstance();
        if (nowCalendar.get(Calendar.YEAR) != billCalendar.get(Calendar.YEAR)
                || nowCalendar.get(Calendar.MONTH) != billCalendar.get(Calendar.MONTH)
                || nowCalendar.get(Calendar.DATE) != billCalendar.get(Calendar.DATE)) {
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                            + "结算日期未到, 结算日期应为: " + DateUtils.format(billCalendar.getTime(), "yyyy-MM-dd"),
                    orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
            return;
        }
        if (billStatus != null) {
            switch (billStatus) {
                // 已结算
                case 1:
                    OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                            + "已经结算! 不能重复结算!", orderId, orderDetail.getId(), OrderLogLevel.warn);
                    orderLogService.loggingOrderLog(log1);
                    // 已结算单的订单, 在此生成对账单(对于前一天的D0和T0结算的订单)
//                    this.doCreateOrderBill(orderDetail);
                    break;
                // 未结算
                case 0:
                    if (billPrice == null) {
                        OrderLog log2 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                                + "结算价格为空, 不能结算", orderId, orderDetail.getId(), OrderLogLevel.warn);
                        orderLogService.loggingOrderLog(log2);
                        break;
                    }
                    // D0或者T0结算(实时结算的订单, 在此立即结算, 但不生成对账单(第二天生成))
                    if (OrderBillType.D0.equals(billType) || OrderBillType.T0.equals(billType)) {
                        // 结算操作
                        productUser.setBalance(productUser.getBalance() + billPrice.doubleValue());
                        userService.update(productUser);
                        //
                        OrderLog log3 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                                + "结算成功! 结算价格: " + billPrice, orderId, orderDetail.getId(), OrderLogLevel.warn);
                        orderLogService.loggingOrderLog(log3);
                        // 更新结算状态
                        orderDetail.setOrderBillStatus(1);
                        orderDetailService.update(orderDetail);
                    } else {
                        // 其他结算方式的订单, 未结算的在此生成对账单, 等待商家与平台确认结算
//                        this.doCreateOrderBill(orderDetail);
                    }
                    break;
                // 其他结算状态, 未知
                default:
                    OrderLog log2 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                                    + "未知的结算状态! 不结算! 现在结算状态: " + orderDetail.getOrderBillStatus(),
                            orderId, orderDetail.getId(), OrderLogLevel.warn);
                    orderLogService.loggingOrderLog(log2);
                    break;
            }
        }
    }

    /**
     * 生成对账详情条目
     * @param orderDetail
     */
    public void doCreateOrderBill(OrderDetail orderDetail) {
        Product product = orderDetail.getProduct();
        OrderBill orderBill = new OrderBill();
        orderBill.setCompanyUnit(product.getCompanyUnit());
        orderBill.setProduct(product);
        orderBill.setOrder(orderDetail.getOrder());
        orderBill.setOrderDetail(orderDetail);
        orderBill.setOrderDate(orderBill.getOrder().getCreateTime());
        orderBill.setBillType(orderDetail.getOrderBillType());
        orderBill.setBillPrice(orderBill.getBillPrice());
        orderBill.setBillDate(orderDetail.getOrderBillDate());
        orderBill.setBillStatus(orderBill.getBillStatus());
        orderBill.setBillCreateTime(new Date());
        orderBill.setCreateTime(new Date());
        orderBillDao.save(orderBill);
    }

    /**
     * 获取账单汇总明细列表
     * @param billDate
     * @param companyUnit
     * @param page
     * @return
     */
    public List<OrderBill> getOrderBillList(Date billDate, SysUnit companyUnit, Page page) {
        Criteria<OrderBill> criteria = new Criteria<OrderBill>(OrderBill.class);
        criteria.createCriteria("companyUnit", "companyUnit");
        criteria.eq("billDate", billDate);
        criteria.eq("companyUnit.id", companyUnit.getId());
        if (page != null) {
            return orderBillDao.findByCriteria(criteria, page);
        }
        return orderBillDao.findByCriteria(criteria);
    }

    /**
     * 获取原始汇总数据
     * @return
     */
    public List<OrderBillSummaryData> getOrderBillSummaryData() {
        String date = DateUtils.formatShortDate(new Date());
        String sql1 = "(select *, sum(bill_price) sum_bill_price_0 from order_bill "
                + "where bill_create_time=? and bill_status=? group by company_unit_id) order_bill_0";
        String sql2 = "(select *, sum(bill_price) sum_bill_price_1 from order_bill "
                + "where bill_create_time=? and bill_status=? group by company_unit_id) order_bill_1";
        String sql = "select order_bill_0.sum_bill_price_0 notBillPrice,"
                + "order_bill_0.company_unit_id companyUnitId,"
                + "order_bill_0.bill_type billType, order_bill_0.bill_date billDate,"
                + "order_bill_1.sum_bill_price_1 alreadyBillPrice"
                + " from " + sql1 + "," + sql2
                + " where order_bill_0.company_unit_id=order_bill_1.company_unit_id";
        List<OrderBillSummaryData> summaryDatas = orderBillDao.findEntitiesBySQL(sql, OrderBillSummaryData.class, date, 0, date, 1);
        return summaryDatas;
    }

    public List<OrderBill> list(OrderBill orderBill, Page page, String orderProperty, String orderType) {
        Criteria<OrderBill> criteria = new Criteria<OrderBill>(OrderBill.class);
        if (orderBill == null) {
            return orderBillDao.findByCriteria(criteria);
        }
        if (orderBill.getCompanyUnit() != null && orderBill.getCompanyUnit().getId() != null) {
            criteria.eq("companyUnit.id", orderBill.getCompanyUnit().getId());
        }
        if (orderBill.getBillDate() != null) {
            criteria.eq("billDate", orderBill.getBillDate());
        }
        return orderBillDao.findByCriteria(criteria);
    }

    /**
     * 确认账单明细
     * @param billDate
     * @param companyUnit
     */
    public void doConfirmOrderBill(Date billDate, SysUnit companyUnit) {
        List<OrderBill> orderBills = getOrderBillList(billDate, companyUnit, null);
        for (OrderBill orderBill : orderBills) {
            orderBill.setBillStatus(1);
            orderBill.setUpdateTime(new Date());
            orderBillDao.update(orderBill);
        }
    }
}
