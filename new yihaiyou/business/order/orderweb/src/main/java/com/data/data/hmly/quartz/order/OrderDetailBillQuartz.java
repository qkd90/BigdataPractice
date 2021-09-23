package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderBillService;
import com.data.data.hmly.service.order.OrderBillSummaryService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zzl on 2016/10/26.
 */
@Component
public class OrderDetailBillQuartz {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderBillService orderBillService;
    @Resource
    private OrderBillSummaryService orderBillSummaryService;
    @Resource
    private SysUnitService sysUnitService;
    @Resource
    private ContractService contractService;

    /**
     * 废弃时间：2016-11-24
     */
    @Deprecated
    public void doOrderDetailBill() {
        User user = orderLogService.getSysOrderLogUser();
        List<OrderDetail> orderDetailList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        while (true) {
            page = new Page(pageIndex, pageSize);
            orderDetailList = orderDetailService.getBillOrderDetailList();
            if (orderDetailList.isEmpty()) {
                break;
            }
            // 总待处理订单数目
            total = page.getTotalCount();
            Iterator<OrderDetail> iterator = orderDetailList.iterator();
            while (iterator.hasNext()) {
                OrderDetail orderDetail = iterator.next();
                orderBillService.doOrderDetailBill(orderDetail);
            }
            // 处理对账汇总
            orderBillSummaryService.doCreateBillSummary();
            // 本次已处理总订单详情数目
            processed += orderDetailList.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            orderDetailList.clear();
        }
    }

    /**
     * 每天生成对账单
     * 查询T0已结算和非T0未结算的订单数据进行汇总
     */
    public void genOrderBillSummary() {
        // 商户结算
        Integer pageIndex = 1;
        Integer pageSize = 20;
        Page page = null;
//        Calendar calendar = Calendar.getInstance();
        Date today = DateUtils.getDate(DateUtils.format(new Date(), "yyyyMMdd"), "yyyyMMdd");
        log.info(">> 开始结算，账单日期为：" + DateUtils.format(today, "yyyyMMdd"));
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setBillSummaryDate(today);
//        orderBillSummary.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
        try {
            SysUnit unit = new SysUnit();
            unit.setStatus(0);
            unit.setUnitType(UnitType.company);
            unit.setTestFlag(false);
            while (true) {
                page = new Page(pageIndex, pageSize);
                List<SysUnit> companys = sysUnitService.listCompanys(unit, page);
                pageIndex++;
                // 查询合同信息
                List<Contract> contracts = new ArrayList<Contract>();
                for (SysUnit u : companys) {
                    Contract contract = contractService.getContractByCompanyB(u.getId(), today);
                    if (contract != null) {
                        contracts.add(contract);
                    }
                }
                if (contracts.isEmpty()) {
                    if (page.getHasNext()) {
                        continue;
                    } else {
                        break;
                    }
                }
                orderBillSummaryService.doCreateBillSummary(contracts, orderBillSummary, null);
                if (!page.getHasNext()) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 神州专车结算
        try {
            orderBillSummaryService.doCreateBillSummaryShenzhou(orderBillSummary, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 轮渡船票结算
        try {
            orderBillSummaryService.doCreateBillSummaryFerry(orderBillSummary, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
