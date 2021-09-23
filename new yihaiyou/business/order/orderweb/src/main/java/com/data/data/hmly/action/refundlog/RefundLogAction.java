package com.data.data.hmly.action.refundlog;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.order.RefundLogService;
import com.data.data.hmly.service.order.entity.RefundLog;
import com.data.data.hmly.service.order.entity.enums.RefundStatus;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.util.Page;
import com.framework.struts.NotNeedLogin;
import com.gson.WeChatPay;
import com.gson.bean.QueryRefundResult;
import com.gson.bean.RefundResult;
import com.gson.bean.RefundResultDetail;
import com.opensymphony.xwork2.Result;
import com.taobao.AliPayService;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by dy on 2016/6/2.
 */
public class RefundLogAction extends FrameBaseAction{

    @Resource
    private RefundLogService refundLogService;

    @Resource
    private WechatAccountService wechatAccountService = SpringContextHolder.getBean("wechatAccountService");

    @Resource
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private String refundRequest;
    private String refundFlag;
    private int page = 1;
    private int rows = 10;

    private Map<String, Object> map = new HashMap<String, Object>();

    private List<Long> ids = new ArrayList<Long>();

    private RefundLog refundLog = new RefundLog();


    public Result wechatRefund() {
        Map<String, String> paramMap = new HashMap<String, String>();
        WechatAccount wechatAccount = new WechatAccount();
        Long companyId = Long.parseLong(propertiesManager.getString("WX_COMPANY_ID"));
        String wxAccount = propertiesManager.getString("WX_ACCOUNT_NAME");
        wechatAccount = wechatAccountService.findUniqueSiteAccount(wxAccount, companyId);
//        wechatAccount = wechatAccountService.get(Long.parseLong(wxAccount));
        if (refundLog.getId() == null) {
            simpleResult(map, false, "请选择一条记录！");
            return jsonResult(map);
        }
        refundLog = refundLogService.get(refundLog.getId());
        if (refundLog == null) {
            simpleResult(map, false, "此退款记录不存在！");
            return jsonResult(map);
        }

        String tradeNo = refundLog.getTradeNo();
        String paternerKey = wechatAccount.getMchKey();

        //退款查询
        paramMap.clear();
        paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
        paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
        paramMap.put("out_trade_no", tradeNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
        QueryRefundResult queryRefundResult = WeChatPay.queryRefundOrder(paramMap, paternerKey);


        if (!queryRefundResult.getReturnCode() || !queryRefundResult.getResultCode()) {
//            refundLog.setRequeryRefundDesc(queryRefundResult.getErrCode() + ":" +queryRefundResult.getErrCodeDesc());
//            refundLog.setUpdateTime(new Date());
//            refundLogService.saveOrUpdate(refundLog);
            simpleResult(map, false, queryRefundResult.getErrCodeDesc());
            return jsonResult(map);
        }
        String resultMsg = "";
        if (queryRefundResult.getRefundCount() > 0) {
            for (RefundResultDetail detail : queryRefundResult.getRefundResultDetailList()) {
                if ("PROCESSING".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.PROCESSING);
                    resultMsg = "正在处理中！";
                    refundLog.setRefundDesc(resultMsg);
                } else if ("SUCCESS".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.SUCCESS);
                    resultMsg = "退款成功！";
                    refundLog.setRefundDesc(resultMsg);
                } else if ("NOTSURE".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.NOTSURE);
                    resultMsg = "未确定，需要商户原退款单号重新发起！";
                    refundLog.setRefundDesc(resultMsg);
                } else if ("CHANGE".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.CHANGE);
                    resultMsg = "转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款！";
                    refundLog.setRefundDesc(resultMsg);
                }
            }
        }
        refundLog.setUpdateTime(new Date());
        refundLogService.saveOrUpdate(refundLog);
        map.put("refundLog", refundLog);
        simpleResult(map, true, resultMsg);
        return jsonResult(map);
    }

    public Result refundAliRequest() {
        refundRequest = "";
        String batchNum = (String) getParameter("batchNum");
        String detailData = (String) getParameter("detailData");

        if (StringUtils.isNotBlank(batchNum) && StringUtils.isNotBlank(detailData)) {
            try {
                detailData = new String(detailData.getBytes("iso-8859-1"), "utf-8");
                detailData = detailData.replace("|", "#");
                refundRequest = AliPayService.doAliRefund(batchNum, detailData);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return dispatch();
        } else {
            return redirect("/pay/refund/manage.jhtml");
        }
    }

    public Result subAliRefund() {

        String idStr = (String) getParameter("idStr");

        if (!StringUtils.isNotBlank(idStr)) {
            simpleResult(map, false, "请求参数有误！");
            return jsonResult(map);
        }
        String[] strings = idStr.split(",");
        for (String str : strings) {
            ids.add(Long.parseLong(str));
        }
        map  = refundLogService.doMakeRefundRequest(ids);
        if (map != null) {
            simpleResult(map, true, "提交成功！");
            return jsonResult(map);
        } else {
            simpleResult(map, false, "表单返回错误！");
            return jsonResult(map);
        }
    }
    @NotNeedLogin
    public Result refundBack() {
        HttpServletRequest request = getRequest();
        Map<String, String> paramMap = AliPayService.doRefundBackParam(request);
        String result = paramMap.get("refundFlag");
        if ("success".equals(result)) {
            refundFlag = result;
            refundLogService.updateByBack(paramMap.get("resultDetails"), true);
        } else {
            refundFlag = result;
        }
        return text(refundFlag);
    }

    public Result refundList() {

        Page pageInfo = new Page(page, rows);

        String orderNo = (String) getParameter("orderNo");
        String orderName = (String) getParameter("orderName");
        String channel = (String) getParameter("channel");
        String result = (String) getParameter("result");

        String rStartTime = (String) getParameter("rStartTime");
        String rEndTime = (String) getParameter("rEndTime");

        if (StringUtils.isNotBlank(channel)) {
            refundLog.setChannel(refundLog.getFmtChannel(channel));
        }

        if (StringUtils.isNotBlank(result)) {
            refundLog.setResult(refundLog.getFmtResult(result));
        }

        if (StringUtils.isNotBlank(orderNo)) {
            refundLog.setOrderNo(orderNo);
        }

        if (StringUtils.isNotBlank(orderName)) {
            refundLog.setOrderName(orderName);
        }

        if (StringUtils.isNotBlank(rStartTime)) {
            Date startTime = DateUtils.getDate(rStartTime, "yyyy-MM-dd HH:mm:ss");
            refundLog.setStartTime(startTime);
        }

        if (StringUtils.isNotBlank(rStartTime)) {
            Date startTime = DateUtils.getDate(rStartTime, "yyyy-MM-dd HH:mm:ss");
            refundLog.setStartTime(startTime);
        }

        if (StringUtils.isNotBlank(rEndTime)) {
            Date endTime = DateUtils.getDate(rEndTime, "yyyy-MM-dd HH:mm:ss");
            refundLog.setEndTime(endTime);
        }

        List<RefundLog> refundLogs = refundLogService.findList(refundLog, pageInfo);

        return datagrid(refundLogs, pageInfo.getTotalCount());
    }

    public Result manage() {
        return dispatch();
    }

    public String getRefundRequest() {
        return refundRequest;
    }

    public void setRefundRequest(String refundRequest) {
        this.refundRequest = refundRequest;
    }

    public String getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag;
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

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public RefundLog getRefundLog() {
        return refundLog;
    }

    public void setRefundLog(RefundLog refundLog) {
        this.refundLog = refundLog;
    }

}
