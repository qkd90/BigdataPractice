package com.data.data.hmly.quartz;

import com.data.data.hmly.service.apidata.ApiOrderCancelService;
import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 接口退单定时器
 */
@Component
public class ApiOrderCancelQuartz {
//    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private CtripTicketService ctripTicketService;
//    @Resource
//    private CtripApiLogService ctripApiLogService;
    @Resource
    private ApiOrderCancelService apiOrderCancelService;
    @Resource
    private FerryOrderService ferryOrderService;

    /**
     * 携程门票退单状态同步，
     * 申请“门票退单”定时检查携程退单状态，成功则更新本地订单状态
     */
    public void syncCtripOrderCancel() {
        // 查询订单状态为退订中的订单
        Integer pageIndex = 1;
        Integer pageSize = 20;
        CtripOrderFormInfo orderFormInfo = new CtripOrderFormInfo();
        orderFormInfo.setOrderStatus(OrderStatus.CANCELING);
        orderFormInfo.setCancelHandleTime(new Date());
        Page page = null;
        while (true) {
            page = new Page(pageIndex, pageSize);

            int time = 1;	// 控制出错时，尝试次数
            while (time <= 3) {    // 如果不止一页，循环更新
                try {
                    List<CtripOrderFormInfo> list = ctripTicketService.listCtripOrderFormInfo(orderFormInfo, page);
                    for (CtripOrderFormInfo ctripOrderFormInfo : list) {
                        String uuid = UUID.randomUUID().toString();
                        OrderStatus orderStatus = ctripTicketApiService.doGetOrderStatus(ctripOrderFormInfo.getCtripOrderId(), uuid, null);
                        apiOrderCancelService.doSyncCtripOrderCancel(ctripOrderFormInfo, orderStatus);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    time++;
                }
            }

            if (!page.getHasNext()) {
                break;
            }
            pageIndex++;
        }
    }

    public void syncFerryOrderCancel() {
        Integer pageIndex = 1;
        Integer pageSize = 20;
        FerryOrder ferryOrder = new FerryOrder();
        ferryOrder.setStatus(com.data.data.hmly.service.order.entity.enums.OrderStatus.CANCELING);
        ferryOrder.setCancelHandleTime(new Date());

        Page page = null;
        while (true) {
            page = new Page(pageIndex, pageSize);

            int time = 1;
            while (time <= 3) {
                try {
                    List<FerryOrder> list = ferryOrderService.list(ferryOrder, page);
                    for (FerryOrder order : list) {
                        apiOrderCancelService.doSyncFerryOrderCancel(order);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    time++;
                }
            }

            if (!page.getHasNext()) {
                break;
            }
            pageIndex++;
        }

    }
}
