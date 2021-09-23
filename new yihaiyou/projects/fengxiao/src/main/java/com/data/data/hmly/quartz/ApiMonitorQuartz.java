package com.data.data.hmly.quartz;

import com.data.data.hmly.service.apidata.ApiMonitorService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 接口退单定时器
 */
@Component
public class ApiMonitorQuartz {
//    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private ApiMonitorService apiMonitorService;

    /**
     * 接口监控
     */
    public void monitorApi() {
        // 微信公众号
        try {
            apiMonitorService.doWechat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 神州专车
        try {
            apiMonitorService.doShenzhou();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 轮渡船票
        try {
            apiMonitorService.doFerry();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 携程门票
        try {
            apiMonitorService.doCtrip();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 艺龙酒店
        try {
            apiMonitorService.doElong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 七牛云
        try {
            apiMonitorService.doFileServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
