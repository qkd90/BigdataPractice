package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.enums.OrderBillTarget;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/10/28.
 */
@Repository
public class OrderBillSummaryDao extends DataAccess<OrderBillSummary> {

    /**
     * 按结算日期查询汇总交易成功的订单明细
     * @param orderBillSummary
     * @param pageInfo
     * @return
     */
    public List<OrderBillSummary> summaryOrderDetail(List<Contract> contracts, OrderBillSummary orderBillSummary, Date yesterday, Page pageInfo) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new OrderBillSummary(p.companyUnit.id, sum(d.totalPrice), sum(d.orderBillPrice), count(*)) ");
        hql.append("from OrderDetail d inner join d.product p where 1 = 1 ");
        hql.append("and d.status in (:status1, :status2, :status3, :status4) ");
        // 退款起始时间推算
//        hql.append("(case when d.orderBillType='month' then date_add(d.orderBillDate, -1, month) ")
//                .append("when d.orderBillType='week' then date_add(d.orderBillDate, -7, week) else date_add(d.orderBillDate, -d.orderBillDays, day) end)");
        params.put("status1", OrderDetailStatus.SUCCESS);
        params.put("status2", OrderDetailStatus.CHECKIN);
        params.put("status3", OrderDetailStatus.CHECKOUT);
        params.put("status4", OrderDetailStatus.REFUNDED);  // 如果是在账单期之后的退款，账单包含这部分数据，后续退款也会包含这部分数据

        // T0查询结算日期为账单日期前一天的订单
        hql.append("and ((d.orderBillType <> :billTypeT0 and d.orderBillDate = :orderBillDate) ");
        hql.append("or (d.orderBillType = :billTypeT0 and d.orderBillDate = :yesterday)) ");
        params.put("orderBillDate", orderBillSummary.getBillSummaryDate());
        params.put("yesterday", yesterday);
        params.put("billTypeT0", OrderBillType.T0);
//        if (orderBillSummary.getProductId() != null) {
//            hql.append("and d.product.id = :productId ");
//            params.put("productId", orderBillSummary.getProductId());
//        }
        if (contracts != null && !contracts.isEmpty()) {
            hql.append("and p.companyUnit.id in (");
            for (Contract c : contracts) {
                hql.append(c.getPartyBunit().getId()).append(",");
            }
            hql.deleteCharAt(hql.length() - 1);
            hql.append(") ");
        }
        if (orderBillSummary.getNotBillSummary()) {
            hql.append("and d.billSummaryId is null ");
        }
        if (orderBillSummary.getBillType() != null) {
            hql.append("and d.orderBillType = :orderBillType ");
            params.put("orderBillType", orderBillSummary.getBillType());
        }
        hql.append("group by p.companyUnit.id ");
        if (pageInfo != null) {
            return findByHQL2ForNew(hql.toString(), pageInfo, params);
        }
        return findByHQL2ForNew(hql.toString(), params);
    }

    /**
     * 回写订单明细对账单标识
     */
    public void updateDetailBillId(List<Contract> contracts, List<OrderBillSummary> billSummaries, Date yesterday, OrderBillSummary orderBillSummary) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update OrderDetail d set d.billSummaryId = ");
        hql.append("(select max(s.id) from OrderBillSummary s where d.product.companyUnit.id = s.companyUnit.id and s.id in (");
        for (OrderBillSummary billSummary : billSummaries) {
            hql.append(billSummary.getId()).append(",");
        }
        hql.deleteCharAt(hql.length() - 1);
        hql.append(")) ");
        hql.append("where 1 = 1 ");
        if (contracts != null && !contracts.isEmpty()) {
            hql.append("and exists (select 1 from Product p where p.id = d.product.id and p.companyUnit.id in (");
            for (Contract c : contracts) {
                hql.append(c.getPartyBunit().getId()).append(",");
            }
            hql.deleteCharAt(hql.length() - 1);
            hql.append(")) ");
        }
        hql.append("and d.status in (:status1, :status2, :status3, :status4) ");
        // 退款起始时间推算
//        hql.append("(case when d.orderBillType='month' then date_add(d.orderBillDate, -1, month) ")
//                .append("when d.orderBillType='week' then date_add(d.orderBillDate, -7, week) else date_add(d.orderBillDate, -d.orderBillDays, day) end)");
        params.put("status1", OrderDetailStatus.SUCCESS);
        params.put("status2", OrderDetailStatus.CHECKIN);
        params.put("status3", OrderDetailStatus.CHECKOUT);
        params.put("status4", OrderDetailStatus.REFUNDED);  // 如果是在账单期之后的退款，账单包含这部分数据，后续退款也会包含这部分数据

        // T0查询结算日期为账单日期前一天的订单
        hql.append("and ((d.orderBillType <> :billTypeT0 and d.orderBillDate = :orderBillDate) ");
        hql.append("or (d.orderBillType = :billTypeT0 and d.orderBillDate = :yesterday)) ");
        params.put("orderBillDate", orderBillSummary.getBillSummaryDate());
        params.put("yesterday", yesterday);
        params.put("billTypeT0", OrderBillType.T0);
//        if (orderBillSummary.getProductId() != null) {
//            hql.append("and d.product.id = :productId ");
//            params.put("productId", orderBillSummary.getProductId());
//        }
        if (orderBillSummary.getNotBillSummary()) {
            hql.append("and d.billSummaryId is null ");
        }
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 更新订单明细结算状态
     */
    public void updateDetailBillStatus(Long billSummaryId, Integer orderBillStatus) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update OrderDetail d set d.orderBillStatus = :orderBillStatus where d.billSummaryId = :billSummaryId ");
        params.put("orderBillStatus", orderBillStatus);
        params.put("billSummaryId", billSummaryId);
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 更新订单明细账单关联
     */
    public void updateDetailBillSummaryId(Long billSummaryId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("billSummaryId", billSummaryId);
        updateByHQL2("update OrderDetail d set d.billSummaryId = null where d.billSummaryId = :billSummaryId ", params);
        updateByHQL2("update OrderDetail d set d.refundBillSummaryId = null where d.refundBillSummaryId = :billSummaryId ", params);
    }

    public List<OrderBillSummary> findOrderBillSummaryList(SysUnit companyUnit) {
        Criteria<OrderBillSummary> criteria = new Criteria<OrderBillSummary>(OrderBillSummary.class);
        criteria.eq("companyUnit.id", companyUnit.getId());
        return findByCriteria(criteria);
    }

    /**
     * 按结算日期查询汇总交易成功的订单明细-神州专车
     * @param orderBillSummary
     * @param pageInfo
     * @return
     */
    public List<OrderBillSummary> summaryOrderDetailShenzhou(OrderBillSummary orderBillSummary, Page pageInfo) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new OrderBillSummary(sum(o.orderBillPrice), count(*)) ");
        hql.append("from ShenzhouOrder o where 1 = 1 ");
        hql.append("and o.status in (:status1, :status2) ");
        params.put("status1", ShenzhouOrderStatus.paid);
        params.put("status2", ShenzhouOrderStatus.completed);
        hql.append("and o.orderBillDate = :orderBillDate ");
        params.put("orderBillDate", orderBillSummary.getBillSummaryDate());
        if (orderBillSummary.getNotBillSummary()) {
            hql.append("and o.billSummaryId is null ");
        }
        if (orderBillSummary.getBillType() != null) {
            hql.append("and o.orderBillType = :orderBillType ");
            params.put("orderBillType", orderBillSummary.getBillType());
        }
//        hql.append("group by o.orderBillType, o.orderBillDays ");
        if (pageInfo != null) {
            return findByHQL2ForNew(hql.toString(), pageInfo, params);
        }
        return findByHQL2ForNew(hql.toString(), params);
    }

    /**
     * 回写订单明细对账单标识-神州专车
     */
    public void updateDetailBillIdShenzhou(OrderBillSummary billSummary, OrderBillSummary orderBillSummary) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update ShenzhouOrder o set o.billSummaryId = :billId ");
        params.put("billId", billSummary.getId());
        hql.append("where o.status in (:status1, :status2) ");
        params.put("status1", ShenzhouOrderStatus.paid);
        params.put("status2", ShenzhouOrderStatus.completed);
        hql.append("and o.orderBillDate = :orderBillDate ");
        params.put("orderBillDate", orderBillSummary.getBillSummaryDate());
        if (orderBillSummary.getNotBillSummary()) {
            hql.append("and o.billSummaryId is null ");
        }
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 更新订单明细账单关联-神州专车
     */
    public void updateDetailBillShenzhou(Long billSummaryId) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update ShenzhouOrder o set o.billSummaryId = null where o.billSummaryId = :billSummaryId ");
        params.put("billSummaryId", billSummaryId);
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 更新订单明细状态-神州专车
     */
    public void updateDetailStatusShenzhou(Long billSummaryId) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update ShenzhouOrder o set o.orderBillStatus = :orderBillStatus where o.billSummaryId = :billSummaryId ");
        params.put("orderBillStatus", 1);   // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        params.put("billSummaryId", billSummaryId);
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 按结算日期查询汇总订单明细-轮渡船票
     * @param orderBillSummary
     * @param pageInfo
     * @return
     */
    public List<OrderBillSummary> summaryOrderDetailFerry(OrderBillSummary orderBillSummary, Page pageInfo) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new OrderBillSummary(sum(o.orderBillPrice), count(*)) ");
        hql.append("from FerryOrder o where 1 = 1 ");
        hql.append("and o.status in (:status1, :status2, :status3)");
        hql.append("and o.orderBillDate = :orderBillDate ");
        params.put("status1", OrderStatus.SUCCESS);
        params.put("status2", OrderStatus.CANCELING);
        params.put("status3", OrderStatus.REFUND);  // 如果是在账单期之后的退款，账单包含这部分数据，后续退款也会包含这部分数据
        params.put("orderBillDate", orderBillSummary.getBillSummaryDate());
        if (orderBillSummary.getNotBillSummary()) { // 人工重新生成
            hql.append("and o.billSummaryId is null ");
        }
        if (orderBillSummary.getBillType() != null) {
            hql.append("and o.orderBillType = :orderBillType ");
            params.put("orderBillType", orderBillSummary.getBillType());
        }
//        hql.append("group by o.orderBillType, o.orderBillDays ");
        if (pageInfo != null) {
            return findByHQL2ForNew(hql.toString(), pageInfo, params);
        }
        return findByHQL2ForNew(hql.toString(), params);
    }

    /**
     * 回写订单明细对账单标识-轮渡船票
     */
    public void updateDetailBillIdFerry(OrderBillSummary billSummary, OrderBillSummary orderBillSummary) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update FerryOrder o set o.billSummaryId = :billId ");
        params.put("billId", billSummary.getId());
        hql.append("where 1 = 1 ");
        hql.append("and o.status in (:status1, :status2, :status3)");
        hql.append("and o.orderBillDate = :orderBillDate ");
        params.put("status1", OrderStatus.SUCCESS);
        params.put("status2", OrderStatus.CANCELING);
        params.put("status3", OrderStatus.REFUND);  // 如果是在账单期之后的退款，账单包含这部分数据，后续退款也会包含这部分数据
        params.put("orderBillDate", orderBillSummary.getBillSummaryDate());
        if (orderBillSummary.getNotBillSummary()) { // 人工重新生成
            hql.append("and o.billSummaryId is null ");
        }
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 更新订单明细账单关联-轮渡船票
     */
    public void updateDetailBillFerry(Long billSummaryId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("billSummaryId", billSummaryId);
        updateByHQL2("update FerryOrder o set o.billSummaryId = null where o.billSummaryId = :billSummaryId ", params);
        updateByHQL2("update FerryOrder o set o.refundBillSummaryId = null where o.refundBillSummaryId = :billSummaryId ", params);
    }

    /**
     * 更新订单明细状态-轮渡船票
     */
    public void updateDetailStatusFerry(Long billSummaryId) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update FerryOrder o set o.orderBillStatus = :orderBillStatus where o.billSummaryId = :billSummaryId ");
        params.put("orderBillStatus", 1);   // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        params.put("billSummaryId", billSummaryId);
        updateByHQL2(hql.toString(), params);
    }

    /**
     * 更新同一天对账单的退款信息
     */
    public void updateBillRefundFerry(Long billSummaryId, Date billDate) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update OrderBillSummary s set s.refundPrice = null, s.refundCount = null, s.refundFee = null ")
            .append("where s.id <> :billSummaryId and s.billDate = :billDate and s.billTarget = :billTarget ");
        params.put("billDate", billDate);
        params.put("billSummaryId", billSummaryId);
        params.put("billTarget", OrderBillTarget.FERRY);
        updateByHQL2(hql.toString(), params);
    }


}
