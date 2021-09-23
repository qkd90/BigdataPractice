package com.data.data.hmly.service.apidata;

import com.data.data.hmly.service.apidata.dao.ApiMonitorDao;
import com.data.data.hmly.service.apidata.entity.ApiMonitor;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.service.result.HotelDetail;
import com.data.data.hmly.service.elong.util.Tool;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.ShenzhouUtil;
import com.data.data.hmly.service.wechat.WechatService;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2017/1/4.
 */
@Service
public class ApiMonitorService {
    @Resource
    private ApiMonitorDao apiMonitorDao;
    @Resource
    private WechatService wechatService;
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private PropertiesManager propertiesManager;

    private final String success = "正常";
    private final String exception = "异常";
    private final String apiKeyWechat = "wechat";
    private final String apiKeyShenzhou = "shenzhou";
    private final String apiKeyFerry = "ferry";
    private final String apiKeyCtrip = "ctrip";
    private final String apiKeyElong = "elong";
    private final String apiKeySns = "sns";
    private final String apiKeyFileServer = "fileServer";

    /**
     * 分页查询
     * @param apiMonitor
     * @param pageInfo
     * @return
     */
    public List<ApiMonitor> list(ApiMonitor apiMonitor, Page pageInfo) {
        return apiMonitorDao.list(apiMonitor, pageInfo);
    }

    /**
     * 重新测试
     */
    public Map<String, Object> doReTest(String apiMonitorId, SysUser loginUser) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (apiKeyWechat.equals(apiMonitorId)) {
            doWechat();
        } else if (apiKeyShenzhou.equals(apiMonitorId)) {
            doShenzhou();
        } else if (apiKeyFerry.equals(apiMonitorId)) {
            doFerry();
        } else if (apiKeyCtrip.equals(apiMonitorId)) {
            doCtrip();
        } else if (apiKeyElong.equals(apiMonitorId)) {
            doElong();
        } else if (apiKeyFileServer.equals(apiMonitorId)) {
            doFileServer();
        } else {
            map.put("success", false);
            map.put("errorMsg", "选择的接口无法进行测试。");
        }
        return map;
    }

    /**
     * 微信公众号
     */
    public void doWechat() throws Exception {
        Long accountId = propertiesManager.getLong("WEBCHAT_ACCOUNT_ID");
        String token = wechatService.doGetTokenBy(accountId);
        ApiMonitor apiMonitor = apiMonitorDao.load(apiKeyWechat);
        apiMonitor.setTestTime(new Date());
        if (token != null) {
            apiMonitor.setStatus(success);
            apiMonitor.setRemark(null);
        } else {
            apiMonitor.setStatus(exception);
            apiMonitor.setRemark(null);
        }
        apiMonitorDao.update(apiMonitor);
    }

    /**
     * 神州专车
     */
    public void doShenzhou() {
        Map<String, Object> result = ShenzhouUtil.clientToken();
        Boolean b = (Boolean) result.get("success");
        ApiMonitor apiMonitor = apiMonitorDao.load(apiKeyShenzhou);
        apiMonitor.setTestTime(new Date());
        if (b) {
            apiMonitor.setStatus(success);
            apiMonitor.setRemark(null);
        } else {
            apiMonitor.setStatus(exception);
            apiMonitor.setRemark(null);
        }
        apiMonitorDao.update(apiMonitor);
    }

    /**
     * 轮渡船票
     */
    public void doFerry() {
        Map<String, Object> result = FerryUtil.getFlightLine();
        Boolean b = (Boolean) result.get("success");
        ApiMonitor apiMonitor = apiMonitorDao.load(apiKeyFerry);
        apiMonitor.setTestTime(new Date());
        if (b) {
            apiMonitor.setStatus(success);
            apiMonitor.setRemark(null);
        } else {
            apiMonitor.setStatus(exception);
            apiMonitor.setRemark((String) result.get("errMsg"));
        }
        apiMonitorDao.update(apiMonitor);
    }

    /**
     * 携程门票
     */
    public void doCtrip() throws Exception {
        String token = ctripTicketApiService.getToken();
        ApiMonitor apiMonitor = apiMonitorDao.load(apiKeyCtrip);
        apiMonitor.setTestTime(new Date());
        if (token != null) {
            apiMonitor.setStatus(success);
            apiMonitor.setRemark(null);
        } else {
            apiMonitor.setStatus(exception);
            apiMonitor.setRemark(null);
        }
        apiMonitorDao.update(apiMonitor);
    }

    /**
     * 艺龙酒店
     */
    public void doElong() throws Exception {
        // 酒店价格详情
        Date date = new Date();
        String dateStr = DateUtils.format(date, "yyyyMMdd");
        Date arriveDate = Tool.addDate(DateUtils.getDate(dateStr, "yyyyMMdd"), 1);
        Date departureDate = Tool.addDate(DateUtils.getDate(dateStr + " 23:59:59", "yyyyMMdd HH:mm:ss"), 3);  // 加载3天数据
        HotelDetail hotelDetail = elongHotelService.getHotelDetail(Long.parseLong("51401056"), arriveDate, departureDate);

        ApiMonitor apiMonitor = apiMonitorDao.load(apiKeyElong);
        apiMonitor.setTestTime(new Date());
        if (hotelDetail != null && hotelDetail.getResult() != null) {
            apiMonitor.setStatus(success);
            apiMonitor.setRemark(null);
        } else {
            apiMonitor.setStatus(exception);
            apiMonitor.setRemark(hotelDetail != null ? hotelDetail.getCode() : null);
        }
        apiMonitorDao.update(apiMonitor);
    }

    /**
     * 七牛云
     */
    public void doFileServer() {
        String token = QiniuUtil.getUpToken("test.jpg");
        ApiMonitor apiMonitor = apiMonitorDao.load(apiKeyFileServer);
        apiMonitor.setTestTime(new Date());
        if (token != null) {
            apiMonitor.setStatus(success);
            apiMonitor.setRemark(null);
        } else {
            apiMonitor.setStatus(exception);
            apiMonitor.setRemark(null);
        }
        apiMonitorDao.update(apiMonitor);
    }




}
