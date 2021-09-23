package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.order.CmbService;
import org.springframework.stereotype.Component;

/**
 * Created by huangpeijie on 2016-09-02,0002.
 */
@Component
public class CmbOrderSettleQuzrtz {
    public void doSettleCmbOrder() {
        CmbService cmbService = new CmbService();
        cmbService.login();
        cmbService.settleOrderFromSystem();
        cmbService.settleOrderFromQuery();
        cmbService.logout();
    }
}
