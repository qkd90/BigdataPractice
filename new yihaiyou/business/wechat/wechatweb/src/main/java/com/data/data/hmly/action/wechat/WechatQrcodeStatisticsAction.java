package com.data.data.hmly.action.wechat;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatQrcodeService;
import com.data.data.hmly.service.wechat.WechatReceiveMsgLogService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.EntityData.StatisticsQrcode;
import com.data.data.hmly.service.wechat.entity.WechatQrcode;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/11/26.
 */
public class WechatQrcodeStatisticsAction extends FrameBaseAction {

//    /wechat/wechatQrcodeStatistics/statisticsManage.jhtml
    @Resource
    private WechatQrcodeService wechatQrcodeService;
    @Resource
    private WechatService wechatService;
    @Resource
    private WechatAccountService wechatAccountService;

    @Resource
    private WechatReceiveMsgLogService wechatReceiveMsgLogService;

    private int page;
    private int rows;

    private Long id;
    private Long accountId;
    private String sceneStr;
    private String name;

    private Map<String, Object> map = new HashMap<String, Object>();


    public Result getStatisticsQrcodeList() {

        Page pageInfo = new Page(page, rows);

        WechatQrcode qrcode = new WechatQrcode();

        String seaStartTime = (String) getParameter("seaStartTime");
        String seaEndTime = (String) getParameter("seaEndTime");
        String qrcodeIdStr = (String) getParameter("seaUnit");
        String accountIdStr = (String) getParameter("seaProName");

        if (StringUtils.isNotBlank(seaStartTime)) {
            Date startTime = DateUtils.getDate(seaStartTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            startTime = DateUtils.getStartDay(startTime, 0);
            qrcode.setStartTime(startTime);
        }
        if (StringUtils.isNotBlank(seaEndTime)) {
            Date endTime = DateUtils.getDate(seaEndTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtils.getEndDay(endTime, 0);
            qrcode.setEndTime(endTime);
        }

        if (StringUtils.isNotBlank(qrcodeIdStr)) {
            qrcode.setId(Long.parseLong(qrcodeIdStr));
            WechatQrcode wq = wechatQrcodeService.get(Long.parseLong(qrcodeIdStr));
            qrcode.setSceneStr(wq.getSceneStr());
        }
        if (StringUtils.isNotBlank(accountIdStr)) {
            accountId = Long.parseLong(accountIdStr);
        }

//        List<StatisticsQrcode> statisticsQrcodes = wechatReceiveMsgLogService.getStatisticsQrcode(accountId, qrcode, EventTypes.subscribe, getCompanyUnit(), getLoginUser(), isSupperAdmin(), isSiteAdmin(), pageInfo);
        List<StatisticsQrcode> statisticsQrcodes = wechatReceiveMsgLogService.statisticsQrcode(accountId, qrcode, getCompanyUnit(), isSupperAdmin(), isSiteAdmin(), pageInfo);
        return datagrid(statisticsQrcodes, pageInfo.getTotalCount());
    }

    public Result statisticsManage() {
        return dispatch();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
