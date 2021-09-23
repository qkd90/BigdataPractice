package com.data.data.hmly.service.order;

import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.nums.SettlementType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.dao.FerryOrderDao;
import com.data.data.hmly.service.order.dao.OrderBillSummaryDao;
import com.data.data.hmly.service.order.dao.OrderDetailDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderBillTarget;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.vo.OrderBillSummaryData;
import com.data.data.hmly.util.GenBillNo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/10/28.
 */
@Service
public class OrderBillSummaryService {

    @Resource
    private OrderBillSummaryDao orderBillSummaryDao;
    @Resource
    private OrderBillService orderBillService;
    @Resource
    private FerryOrderDao ferryOrderDao;
    @Resource
    private OrderDetailDao orderDetailDao;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private ContractService contractService;

    /**
     * 结算分页数据
     * @return
     */
    public List<OrderBillSummary> list(OrderBillSummary orderBillSummary, Page page, String... orderProperties) {
        Criteria<OrderBillSummary> criteria = createCriteria(orderBillSummary, orderProperties);
        if (page != null) {
            if (orderBillSummary.getBillTarget() == OrderBillTarget.SYSTEM) {
                criteria.createCriteria("companyUnit", "c", JoinType.LEFT_OUTER_JOIN);
            }
            return orderBillSummaryDao.findByCriteria(criteria, page);
        }
        return orderBillSummaryDao.findByCriteria(criteria);
    }

    public void doCreateBillSummary() {
        List<OrderBillSummaryData> summaryDatas = orderBillService.getOrderBillSummaryData();
        List<OrderBillSummary> orderBillSummaryList = new ArrayList<OrderBillSummary>();
        for (OrderBillSummaryData summaryData : summaryDatas) {
            OrderBillSummary billSummary = new OrderBillSummary();
            SysUnit sysUnit = new SysUnit();
            sysUnit.setId(summaryData.getCompanyUnitId().longValue());
            Float alreadyBillPrice = summaryData.getAlreadyBillPrice().floatValue();
            Float notBillPrice = summaryData.getNotBillPrice().floatValue();
            Float totalBillPrice = alreadyBillPrice + notBillPrice;
            billSummary.setBillDate(summaryData.getBillDate());
            billSummary.setBillType(summaryData.getBillType());
            billSummary.setCompanyUnit(sysUnit);
            billSummary.setAlreadyBillPrice(alreadyBillPrice);
            billSummary.setNotBillPrice(notBillPrice);
            billSummary.setTotalBillPrice(totalBillPrice);
            billSummary.setBillSummaryDate(new Date());
            if (notBillPrice != 0f) {
                billSummary.setStatus(2);
            } else if (notBillPrice == 0f) {
                billSummary.setStatus(1);
            } else if (alreadyBillPrice == 0f) {
                billSummary.setStatus(0);
            }
            billSummary.setConfirmStatus(0);
            billSummary.setCreateTime(new Date());
            orderBillSummaryList.add(billSummary);
        }
        orderBillSummaryDao.save(orderBillSummaryList);
    }

    public Criteria<OrderBillSummary> createCriteria(OrderBillSummary orderBillSummary, String... orderProperties) {
        Criteria<OrderBillSummary> criteria = new Criteria<OrderBillSummary>(OrderBillSummary.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (orderBillSummary == null) {
            return criteria;
        }
        if (StringUtils.isNotBlank(orderBillSummary.getBillNo())) {
            criteria.like("billNo", orderBillSummary.getBillNo(), MatchMode.ANYWHERE);
        }
        if (orderBillSummary.getBillDate() != null) {
            criteria.eq("billDate", orderBillSummary.getBillDate());
        }
        if (orderBillSummary.getStatus() != null) {
            criteria.eq("status", orderBillSummary.getStatus());
        }
        if (orderBillSummary.getBillTarget() != null) {
            criteria.eq("billTarget", orderBillSummary.getBillTarget());
        }
        if (orderBillSummary.getId() != null) {
            criteria.eq("id", orderBillSummary.getId());
        }
        if (orderBillSummary.getConfirmStatus() != null) {
            criteria.eq("confirmStatus", orderBillSummary.getConfirmStatus());
        }
        if (orderBillSummary.getBillSummaryDate() != null) {
            criteria.eq("billSummaryDate", orderBillSummary.getBillSummaryDate());
        }
        if (orderBillSummary.getProductId() != null) {
            criteria.eq("productId", orderBillSummary.getProductId());
        }
        if (orderBillSummary.getCompanyUnit() != null && orderBillSummary.getCompanyUnit().getId() != null) {
            criteria.eq("companyUnit.id", orderBillSummary.getCompanyUnit().getId());
        }
        if (orderBillSummary.getCompanyUnitId() != null) {
            criteria.eq("companyUnit.id", orderBillSummary.getCompanyUnitId());
        }
        if (orderBillSummary.getBillDateStart() != null) {
            criteria.ge("billSummaryDate", DateUtils.getStartDay(orderBillSummary.getBillDateStart(), 0));
        }
        if (orderBillSummary.getBillDateEnd() != null) {
            criteria.le("billSummaryDate", DateUtils.getEndDay(orderBillSummary.getBillDateEnd(), 0));
        }
        return criteria;
    }

    public OrderBillSummary get(Long id) {
        return orderBillSummaryDao.load(id);
    }

    /**
     * 确认账单
     * @param orderBillSummary
     * @param loginUser
     */
    public void doConfirmOrderBillSummary(OrderBillSummary orderBillSummary, SysUser loginUser) {
        orderBillSummary.setConfirmStatus(1);
        orderBillSummary.setStatus(1);
        orderBillSummary.setSiteConfirmor(loginUser);
        orderBillSummary.setUpdateTime(new Date());
        orderBillSummaryDao.update(orderBillSummary);
    }

    /**
     * 判断是否需要生产账单，对于按周、按月需要做此判断，其他每天生成
     * @param billSummaryDate
     * @param billType
     * @param billDays
     * @return
     */
    public boolean isNeedGenBill(Date billSummaryDate, OrderBillType billType, Integer billDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(billSummaryDate);
        if (billType == OrderBillType.week) {
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (billDays != null && dayOfWeek == billDays) {
                return true;
            }
            return false;
        } else if (billType == OrderBillType.month) {
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            if (billDays != null && dayOfMonth == billDays) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 生成对账单
     * 结算方式一旦确定不能随意修改，否则退款信息会有问题
     * 规则：1.结算方式为非T0查询结算日期为账单日期的订单，包含结算日期为账单日期且退款日期大于等于结算日期的退款订单，
     *        退款金额查询结算时间内（大于等于上个结算日期且小于本次结算日期）的退款订单；
     *      2.结算方式为T0查询结算日期为账单日期前一天的订单，包含结算日期为账单日期前一天且退款日期大于等于结算日期的退款订单，
     *        退款金额查询结算时间内（等于结算日期）的退款订单。
     */
    public void doCreateBillSummary(List<Contract> contracts, OrderBillSummary orderBillSummary, Page pageInfo) {
        Date yesterday = DateUtils.add(orderBillSummary.getBillSummaryDate(), Calendar.DAY_OF_MONTH, -1);   // T0查询结算日期为账单日期前一天的订单
        List<OrderBillSummary> list = orderBillSummaryDao.summaryOrderDetail(contracts, orderBillSummary, yesterday, pageInfo);
        // 结算信息中的商户
        Map<Long, OrderBillSummary> unitIdAndBillMap = new HashMap<Long, OrderBillSummary>();
        for (OrderBillSummary billSummary : list) {
            unitIdAndBillMap.put(billSummary.getCompanyUnitId(), billSummary);
        }
        // 根据合同信息判断如果没有结算信息，默认增加一条账单信息
        for (Contract contract : contracts) {
            OrderBillType billType = convertBillType(contract.getSettlementType(), contract.getSettlementValue());
            Integer billDays = contract.getSettlementValue();
            OrderBillSummary billSummary = unitIdAndBillMap.get(contract.getPartyBunit().getId());
            if (billSummary == null && isNeedGenBill(orderBillSummary.getBillSummaryDate(), billType, billDays)) {  // 默认增加一条账单信息
                billSummary = new OrderBillSummary(contract.getPartyBunit().getId(), 0d, 0d, 0l);
                list.add(billSummary);
            }
            billSummary.setBillType(billType);
            billSummary.setBillDays(billDays);
        }
        // 设置账单其他信息
        for (OrderBillSummary billSummary : list) {
            billSummary.setBillNo(GenBillNo.generate());
            Date billDateStart = null;  // 上个账单日期始
            Date billDateEnd = null;    // 上个账单日期止
            if (OrderBillType.D0 == billSummary.getBillType() || OrderBillType.T0 == billSummary.getBillType()) {
                billSummary.setStatus(1);    // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
                billSummary.setConfirmStatus(1); // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
                billSummary.setBillDate(yesterday);
                // 退款日期条件
                billDateStart = billSummary.getBillDate();
                billDateEnd = DateUtils.add(billSummary.getBillDate(), Calendar.DAY_OF_MONTH, 1);
            } else {
                billSummary.setStatus(0);    // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
                billSummary.setConfirmStatus(0); // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
                billSummary.setBillDate(orderBillSummary.getBillSummaryDate());
                // 退款日期条件
                billDateStart = calBillDateStart(billSummary.getBillDate(), billSummary.getBillType(), billSummary.getBillDays());
                billDateEnd = billSummary.getBillDate();
            }
            SysUnit companyUnit = new SysUnit();
            companyUnit.setId(billSummary.getCompanyUnitId());
            billSummary.setCompanyUnit(companyUnit);
            billSummary.setBillTarget(OrderBillTarget.SYSTEM);
            billSummary.setBillSummaryDate(orderBillSummary.getBillSummaryDate());
            billSummary.setCreateTime(new Date());

            // 简单处理退款订单
            List<OrderDetail> refundOrders = orderDetailDao.getRefundOrderDetail(billDateStart, billDateEnd, billSummary.getCompanyUnitId(), null);
            float refundPrice = 0f;
            for (OrderDetail refundOrder : refundOrders) {
                if (refundOrder.getRefundBillAmount() != null) {
                    refundPrice += refundOrder.getRefundBillAmount();
                }
            }
            billSummary.setRefundPrice(refundPrice);
            billSummary.setRefundCount(refundOrders.size());
            if (billSummary.getStatus() == 1) { // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
                billSummary.setAlreadyBillPrice(billSummary.getTotalBillPrice() - billSummary.getRefundPrice());
                billSummary.setNotBillPrice(0f);
            } else {
                billSummary.setAlreadyBillPrice(0f);
                billSummary.setNotBillPrice(billSummary.getTotalBillPrice() - billSummary.getRefundPrice());
            }
            orderBillSummaryDao.save(billSummary);
            // 更新退款订单信息
            for (OrderDetail refundOrder : refundOrders) {
                refundOrder.setRefundBillSummaryId(billSummary.getId());
                orderDetailDao.save(refundOrder);
            }
        }
        if (list.size() < 1) {
            return;
        }
        orderBillSummaryDao.updateDetailBillId(contracts, list, yesterday, orderBillSummary);
    }

    /**
     * 未结算对账单重新生成
     */
    public Map<String, Object> doRegenBillSummary(Long billSummaryId, SysUser loginUser) {
        OrderBillSummary billSummary = orderBillSummaryDao.load(billSummaryId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (billSummary.getStatus() != 0 && (billSummary.getBillType() != OrderBillType.D0 && billSummary.getBillType() != OrderBillType.T0)) {
            map.put("success", false);
            map.put("errorMsg", "可重新生成的账单必须是结算方式为T0或者账单是未结算，请获取最新数据进行操作。");
            return map;
        }
        // 更新对账单的订单详情关联
        orderBillSummaryDao.updateDetailBillSummaryId(billSummaryId);
        // 重新汇总订单
        // 查询合同信息
        Date today = DateUtils.getDate(DateUtils.format(new Date(), "yyyyMMdd"), "yyyyMMdd");
        List<Contract> contracts = new ArrayList<Contract>();
        Contract contract = contractService.getContractByCompanyB(billSummary.getCompanyUnit().getId(), today);
        if (contract != null) {
            contracts.add(contract);
        } else {
            map.put("success", false);
            map.put("errorMsg", "合同不在有效期内或者无合同信息。");
            return map;
        }
        Date yesterday = DateUtils.add(billSummary.getBillSummaryDate(), Calendar.DAY_OF_MONTH, -1);   // T0查询结算日期为账单日期前一天的订单
        OrderBillSummary obs = new OrderBillSummary();
        obs.setBillSummaryDate(billSummary.getBillSummaryDate());
        obs.setNotBillSummary(true);
        List<OrderBillSummary> list = orderBillSummaryDao.summaryOrderDetail(contracts, obs, yesterday, null);
        // 更新对账单信息，正常只有一条，否则会有问题
        if (list.size() < 1) {
            billSummary.setTotalOrderCount(0);
            billSummary.setTotalOrderPrice(0f);
            billSummary.setTotalBillPrice(0f);
        } else {
            OrderBillSummary temp = list.get(0);
            billSummary.setTotalOrderCount(temp.getTotalOrderCount());
            billSummary.setTotalOrderPrice(temp.getTotalOrderPrice());
            billSummary.setTotalBillPrice(temp.getTotalBillPrice());
        }
        billSummary.setUpdateTime(new Date());
        billSummary.setRemark("账单重新生成时间:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        // 简单处理退款订单
        Date billDateStart = null;  // 上个账单日期始
        Date billDateEnd = null;    // 上个账单日期止
        if (OrderBillType.D0 == billSummary.getBillType() || OrderBillType.T0 == billSummary.getBillType()) {
            billDateStart = billSummary.getBillDate();
            billDateEnd = DateUtils.add(billSummary.getBillDate(), Calendar.DAY_OF_MONTH, 1);
        } else {
            billDateStart = calBillDateStart(billSummary.getBillDate(), billSummary.getBillType(), billSummary.getBillDays());
            billDateEnd = billSummary.getBillDate();
        }
        List<OrderDetail> refundOrders = orderDetailDao.getRefundOrderDetail(billDateStart, billDateEnd, billSummary.getCompanyUnit().getId(), billSummaryId);
        float refundPrice = 0f;
        for (OrderDetail refundOrder : refundOrders) {
            if (refundOrder.getRefundBillAmount() != null) {
                refundPrice += refundOrder.getRefundBillAmount();
            }
        }
        billSummary.setRefundPrice(refundPrice);
        billSummary.setRefundCount(refundOrders.size());
        if (billSummary.getStatus() == 1) { // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
            billSummary.setAlreadyBillPrice(billSummary.getTotalBillPrice() - billSummary.getRefundPrice());
            billSummary.setNotBillPrice(0f);
        } else {
            billSummary.setAlreadyBillPrice(0f);
            billSummary.setNotBillPrice(billSummary.getTotalBillPrice() - billSummary.getRefundPrice());
        }
        orderBillSummaryDao.save(billSummary);
        // 更新退款订单信息
        for (OrderDetail refundOrder : refundOrders) {
            refundOrder.setRefundBillSummaryId(billSummary.getId());
            orderDetailDao.save(refundOrder);
        }
//        orderBillSummaryDao.save(orderBillSummary);
        // 更新订单详情关联
        if (list.size() < 1) {
            return map;
        }
        List<OrderBillSummary> orderBillSummaries = new ArrayList<OrderBillSummary>();
        orderBillSummaries.add(billSummary);
        orderBillSummaryDao.updateDetailBillId(contracts, orderBillSummaries, yesterday, obs);
        return map;
    }

    /**
     * 生成对账单-神州专车
     * 暂只考虑T1、TN，结算方式一旦确定不能随意修改
     */
    public boolean doCreateBillSummaryShenzhou(OrderBillSummary orderBillSummary, Page pageInfo) {
        List<OrderBillSummary> list = orderBillSummaryDao.summaryOrderDetailShenzhou(orderBillSummary, pageInfo);
        String shenzhouBillType = propertiesManager.getString("SHENZHOU_BILL_TYPE");
        String shenzhouBillDayStr = propertiesManager.getString("SHENZHOU_BILL_DAY");
        Integer shenzhouBillDay = StringUtils.isBlank(shenzhouBillDayStr) ? null : Integer.valueOf(shenzhouBillDayStr);
        if (!isNeedGenBill(orderBillSummary.getBillSummaryDate(), OrderBillType.valueOf(shenzhouBillType), shenzhouBillDay)) {
            return false;
        }
        // 正常只有一条，否则会有问题
        OrderBillSummary billSummary = null;
        if (list.size() < 1) {
            billSummary = new OrderBillSummary();
            billSummary.setTotalBillPrice(0f);
            billSummary.setTotalOrderCount(0);
            billSummary.setNotBillPrice(0f);
        } else {
            billSummary = list.get(0);
            billSummary.setNotBillPrice(billSummary.getTotalBillPrice());
        }
        billSummary.setTotalOrderPrice(billSummary.getTotalBillPrice());
        billSummary.setBillType(OrderBillType.valueOf(shenzhouBillType));
        billSummary.setBillDays(shenzhouBillDay);
        billSummary.setBillNo(GenBillNo.generate());
        billSummary.setAlreadyBillPrice(0f);
        billSummary.setStatus(0);    // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        billSummary.setConfirmStatus(0); // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
        billSummary.setBillDate(orderBillSummary.getBillSummaryDate());
        billSummary.setBillTarget(OrderBillTarget.SHENZHOU);
        billSummary.setBillSummaryDate(orderBillSummary.getBillSummaryDate());
        billSummary.setCreateTime(new Date());
        orderBillSummaryDao.save(billSummary);
        if (list.size() < 1) {
            return true;
        }
        orderBillSummaryDao.updateDetailBillIdShenzhou(billSummary, orderBillSummary);
        return true;
    }

    /**
     * 未结算对账单重新生成-神州专车
     */
    public Map<String, Object> doRegenBillSummaryShenzhou(Long billSummaryId, SysUser loginUser) {
        OrderBillSummary orderBillSummary = orderBillSummaryDao.load(billSummaryId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (orderBillSummary.getStatus() != 0) {
            map.put("success", false);
            map.put("errorMsg", "可重新生成的账单必须是未结算，请获取最新数据进行操作。");
            return map;
        }
        // 更新对账单的订单详情关联
        orderBillSummaryDao.updateDetailBillShenzhou(billSummaryId);
        // 重新汇总订单
        OrderBillSummary obs = new OrderBillSummary();
        obs.setBillSummaryDate(orderBillSummary.getBillSummaryDate());
//        obs.setBillType(orderBillSummary.getBillType());
        obs.setNotBillSummary(true);
//        obs.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
        List<OrderBillSummary> list = orderBillSummaryDao.summaryOrderDetailShenzhou(obs, null);
        // 正常只有一条，否则会有问题
        if (list.size() < 1) {
            orderBillSummary.setTotalOrderCount(0);
            orderBillSummary.setTotalBillPrice(0f);
            orderBillSummary.setNotBillPrice(0f);
        } else {
            OrderBillSummary billSummary = list.get(0);
            orderBillSummary.setTotalOrderCount(billSummary.getTotalOrderCount());
            orderBillSummary.setTotalBillPrice(billSummary.getTotalBillPrice());
            orderBillSummary.setNotBillPrice(billSummary.getTotalBillPrice());
        }
        // 更新对账单信息
        orderBillSummary.setAlreadyBillPrice(0f);
        orderBillSummary.setTotalOrderPrice(orderBillSummary.getTotalBillPrice());
        orderBillSummary.setUpdateTime(new Date());
        orderBillSummary.setRemark("账单重新生成时间:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        orderBillSummaryDao.save(orderBillSummary);
        // 更新订单详情关联
        if (list.size() < 1) {
            return map;
        }
        orderBillSummaryDao.updateDetailBillIdShenzhou(orderBillSummary, obs);
        return map;
    }

    /**
     * 对账单进行结算-神州专车
     */
    public Map<String, Object> doCfmBillSummaryShenzhou(Long billSummaryId, SysUser loginUser) {
        OrderBillSummary orderBillSummary = orderBillSummaryDao.load(billSummaryId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (orderBillSummary.getStatus() != 0) {
            map.put("success", false);
            map.put("errorMsg", "该账单不是未结算状态，请获取最新数据进行操作。");
            return map;
        }
        orderBillSummary.setAlreadyBillPrice(orderBillSummary.getNotBillPrice());
        orderBillSummary.setNotBillPrice(0f);
        orderBillSummary.setStatus(1);    // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        orderBillSummary.setConfirmStatus(1); // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
        orderBillSummary.setSiteConfirmor(loginUser);
        orderBillSummary.setUpdateTime(new Date());
        orderBillSummaryDao.save(orderBillSummary);
        // 更新订单详情状态
        orderBillSummaryDao.updateDetailStatusShenzhou(billSummaryId);
        return map;
    }

    /**
     * 生成对账单-轮渡船票
     * 暂只考虑T1、TN，结算方式一旦确定不能随意修改，否则退款信息会有问题
     * 规则：查询结算日期为参数日期的订单，包含交易成功、退款中、已退款（因为轮渡可能已经产生费用），
     *      退款金额查询结算时间内（退款日期大于等于上个结算日期且小于本次结算日期）的退款订单；
     */
    public boolean doCreateBillSummaryFerry(OrderBillSummary orderBillSummary, Page pageInfo) {
        List<OrderBillSummary> list = orderBillSummaryDao.summaryOrderDetailFerry(orderBillSummary, pageInfo);
        String ferryBillType = propertiesManager.getString("FERRY_BILL_TYPE");
        String ferryBillDayStr = propertiesManager.getString("FERRY_BILL_DAY");
        Integer ferryBillDay = StringUtils.isBlank(ferryBillDayStr) ? null : Integer.valueOf(ferryBillDayStr);
        if (!isNeedGenBill(orderBillSummary.getBillSummaryDate(), OrderBillType.valueOf(ferryBillType), ferryBillDay)) {
            return false;
        }
        // 正常只有一条，否则会有问题
        OrderBillSummary billSummary = null;
        if (list.size() < 1) {
            billSummary = new OrderBillSummary();
            billSummary.setTotalBillPrice(0f);
            billSummary.setTotalOrderCount(0);
        } else {
            billSummary = list.get(0);
        }
        billSummary.setTotalOrderPrice(billSummary.getTotalBillPrice());
        billSummary.setBillType(OrderBillType.valueOf(ferryBillType));
        billSummary.setBillDays(ferryBillDay);
        billSummary.setBillNo(GenBillNo.generate());
        billSummary.setAlreadyBillPrice(0f);
        billSummary.setStatus(0);    // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        billSummary.setConfirmStatus(0); // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
        billSummary.setBillDate(orderBillSummary.getBillSummaryDate());
        billSummary.setBillTarget(OrderBillTarget.FERRY);
        billSummary.setBillSummaryDate(orderBillSummary.getBillSummaryDate());
        billSummary.setCreateTime(new Date());

        // 简单处理退款订单，退款关联账单一旦生成不会再做修改，不区分结算类型
        Date billDateStart = calBillDateStart(billSummary.getBillDate(), OrderBillType.valueOf(ferryBillType), billSummary.getBillDays());
        List<FerryOrder> refundOrders = ferryOrderDao.getRefundOrderDetail(billDateStart, billSummary.getBillDate(), null);
        float refundPrice = 0f;
        float refundFee = 0f;
        for (FerryOrder refundOrder : refundOrders) {
            if (refundOrder.getReturnAmount() != null) {
                refundPrice += refundOrder.getReturnAmount();
            }
            if (refundOrder.getPoundageAmount() != null) {
                refundFee += refundOrder.getPoundageAmount();
            }
        }

        billSummary.setRefundPrice(refundPrice);
        billSummary.setRefundFee(refundFee);
        billSummary.setRefundCount(refundOrders.size());
        billSummary.setNotBillPrice(billSummary.getTotalBillPrice() - billSummary.getRefundPrice());
        orderBillSummaryDao.save(billSummary);
        // 更新同一天对账单的其他退款信息，只保留一天只有一条退款信息
//        orderBillSummaryDao.updateBillRefundFerry(billSummary.getId(), billSummary.getBillDate());
        // 更新退款订单信息
        for (FerryOrder refundOrder : refundOrders) {
            refundOrder.setRefundBillSummaryId(billSummary.getId());
            ferryOrderDao.save(refundOrder);
        }
        if (list.size() < 1) {
            return true;
        }
        // 更新订单信息
        orderBillSummaryDao.updateDetailBillIdFerry(billSummary, orderBillSummary);
        return true;
    }

    /**
     * 未结算对账单重新生成-轮渡船票
     */
    public Map<String, Object> doRegenBillSummaryFerry(Long billSummaryId, SysUser loginUser) {
        OrderBillSummary orderBillSummary = orderBillSummaryDao.load(billSummaryId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (orderBillSummary.getStatus() != 0) {
            map.put("success", false);
            map.put("errorMsg", "可重新生成的账单必须是未结算，请获取最新数据进行操作。");
            return map;
        }
        // 更新对账单的订单详情关联
        orderBillSummaryDao.updateDetailBillFerry(billSummaryId);
        // 重新汇总订单
        OrderBillSummary obs = new OrderBillSummary();
        obs.setBillSummaryDate(orderBillSummary.getBillSummaryDate());
//        obs.setBillType(orderBillSummary.getBillType());
        obs.setNotBillSummary(true);
//        obs.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
        List<OrderBillSummary> list = orderBillSummaryDao.summaryOrderDetailFerry(obs, null);
        // 正常只有一条，否则会有问题
        if (list.size() < 1) {
            orderBillSummary.setTotalOrderCount(0);
            orderBillSummary.setTotalBillPrice(0f);
        } else {
            OrderBillSummary billSummary = list.get(0);
            orderBillSummary.setTotalOrderCount(billSummary.getTotalOrderCount());
            orderBillSummary.setTotalBillPrice(billSummary.getTotalBillPrice());
        }
        orderBillSummary.setTotalOrderPrice(orderBillSummary.getTotalBillPrice());

        // 简单处理退款订单，退款关联账单一旦生成不会再做修改，不区分结算类型
        Date billDateStart = calBillDateStart(orderBillSummary.getBillDate(), orderBillSummary.getBillType(), orderBillSummary.getBillDays());
        List<FerryOrder> refundOrders = ferryOrderDao.getRefundOrderDetail(billDateStart, orderBillSummary.getBillDate(), billSummaryId);
        float refundPrice = 0f;
        float refundFee = 0f;
        for (FerryOrder refundOrder : refundOrders) {
            if (refundOrder.getReturnAmount() != null) {
                refundPrice += refundOrder.getReturnAmount();
            }
            if (refundOrder.getPoundageAmount() != null) {
                refundFee += refundOrder.getPoundageAmount();
            }
        }

        // 更新对账单信息
        orderBillSummary.setUpdateTime(new Date());
        orderBillSummary.setRemark("账单重新生成时间:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        orderBillSummary.setRefundPrice(refundPrice);
        orderBillSummary.setRefundFee(refundFee);
        orderBillSummary.setRefundCount(refundOrders.size());
        orderBillSummary.setNotBillPrice(orderBillSummary.getTotalBillPrice() - orderBillSummary.getRefundPrice());
        orderBillSummaryDao.save(orderBillSummary);
        // 更新同一天对账单的其他退款信息，只保留一天只有一条退款信息
//        orderBillSummaryDao.updateBillRefundFerry(orderBillSummary.getId(), orderBillSummary.getBillDate());
        // 更新退款订单信息
        for (FerryOrder refundOrder : refundOrders) {
            refundOrder.setRefundBillSummaryId(billSummaryId);
            ferryOrderDao.save(refundOrder);
        }
        // 更新订单详情关联
        if (list.size() < 1) {
            return map;
        }
        orderBillSummaryDao.updateDetailBillIdFerry(orderBillSummary, obs);
        return map;
    }

    /**
     * 对账单进行结算-轮渡船票
     */
    public Map<String, Object> doCfmBillSummaryFerry(OrderBillSummary orderBillSummary, SysUser loginUser) {
//        OrderBillSummary orderBillSummary = orderBillSummaryDao.load(billSummaryId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (orderBillSummary.getStatus() != 0) {
            map.put("success", false);
            map.put("errorMsg", "该账单不是未结算状态，请获取最新数据进行操作。");
            return map;
        }
        orderBillSummary.setAlreadyBillPrice(orderBillSummary.getNotBillPrice());
        orderBillSummary.setNotBillPrice(0f);
        orderBillSummary.setStatus(1);    // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        orderBillSummary.setConfirmStatus(1); // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
        orderBillSummary.setSiteConfirmor(loginUser);
        orderBillSummary.setUpdateTime(new Date());
        orderBillSummaryDao.save(orderBillSummary);
        // 更新订单详情状态
        orderBillSummaryDao.updateDetailStatusFerry(orderBillSummary.getId());
        return map;
    }

    /**
     * 非T0的结算周期起始时间推算
     * 主要用于查询起始时间和结算时间中产生的退款
     * @param billDate
     * @param billType
     * @param billDays
     * @return
     */
    public Date calBillDateStart(Date billDate, OrderBillType billType, Integer billDays) {
        Date date = billDate;
        if (OrderBillType.month == billType) {
            date = DateUtils.add(billDate, Calendar.MONTH, -1);
        } else if (OrderBillType.week == billType) {
            date = DateUtils.add(billDate, Calendar.DAY_OF_MONTH, -7);
        } else if (OrderBillType.T1 == billType || OrderBillType.TN == billType || OrderBillType.D1 == billType || OrderBillType.DN == billType) {
            date = DateUtils.add(billDate, Calendar.DAY_OF_MONTH, -billDays);
        } else {

        }
        return date;
    }

    /**
     * 结算类型转换
     */
    public OrderBillType convertBillType(SettlementType settlementType, Integer settlementValue) {
        switch (settlementType) {
            case week:
                return OrderBillType.week;
            case month:
                return OrderBillType.month;
            case tday:
                if (settlementValue == 0) {
                    return OrderBillType.T0;
                } else if (settlementValue == 1) {
                    return OrderBillType.T1;
                } else {
                    return OrderBillType.TN;
                }
        }
        return null;
    }
}
