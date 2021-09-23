package com.hmlyinfo.app.soutu.pay.allin.service;

import com.hmlyinfo.app.soutu.pay.allin.domain.AllInPayLog;
import com.hmlyinfo.app.soutu.pay.allin.mapper.AllInPayLogMapper;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllInPayLogService extends BaseService<AllInPayLog, Long> {

    @Autowired
    private AllInPayLogMapper<AllInPayLog> mapper;

    @Override
    public BaseMapper<AllInPayLog> getMapper() {
        return mapper;
    }

    @Override
	public String getKey() {
        return "id";
    }


    public void log(PayOrder payOrder, String callbackUrl, String notifyUrl,  String handler) {
        AllInPayLog allInPayLog = new AllInPayLog();
        allInPayLog.setCallbackUrl(callbackUrl);
        allInPayLog.setNotifyUrl(notifyUrl);
        allInPayLog.setOrderId(Long.valueOf(payOrder.getOrderNum()));
        allInPayLog.setNotifyService(handler);
        allInPayLog.setSubject(payOrder.getBody());
        allInPayLog.setTotalFee(payOrder.getTotalFee());
        insert(allInPayLog);
    }

    public AllInPayLog getByOrder(String orderNumber) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderNumber);
        List<AllInPayLog> list = list(params);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
