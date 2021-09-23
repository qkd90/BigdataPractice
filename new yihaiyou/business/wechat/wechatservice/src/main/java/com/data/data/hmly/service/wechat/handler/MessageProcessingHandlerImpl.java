package com.data.data.hmly.service.wechat.handler;

import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.WechatDataItemService;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.service.wechat.WechatEventService;
import com.data.data.hmly.service.wechat.WechatReceiveMsgLogService;
import com.data.data.hmly.service.wechat.WechatReplyKeywordService;
import com.data.data.hmly.service.wechat.WechatReplyRuleService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.data.data.hmly.service.wechat.entity.WechatReceiveMsgLog;
import com.data.data.hmly.service.wechat.entity.WechatReplyKeyword;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;
import com.gson.bean.Articles;
import com.gson.bean.InMessage;
import com.gson.bean.NewsOutMessage;
import com.gson.bean.OutMessage;
import com.gson.bean.TextOutMessage;
import com.gson.bean.TransInfo;
import com.gson.bean.TransferCustomerServiceOutMessage;
import com.gson.inf.EventTypes;
import com.gson.inf.MessageProcessingHandler;
import com.gson.inf.MsgTypes;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 微信消息处理，类由工具包自动创建
 *
 * @author caiys
 * @date 2015年11月25日 上午10:46:05
 */
public class MessageProcessingHandlerImpl implements MessageProcessingHandler {
    private Log log = LogFactory.getLog(this.getClass());
    private OutMessage outMessage;
    private Long wechatReceiveMsgLogId;    // 接收消息日志标识
    private Long dataItemId;    // 回复消息标识
    private WechatAccount wechatAccount;
    private WechatReceiveMsgLogService wechatReceiveMsgLogService = SpringContextHolder.getBean("wechatReceiveMsgLogService");
    private WechatAccountService wechatAccountService = SpringContextHolder.getBean("wechatAccountService");
    private WechatReplyKeywordService wechatReplyKeywordService = SpringContextHolder.getBean("wechatReplyKeywordService");
    private WechatDataItemService wechatDataItemService = SpringContextHolder.getBean("wechatDataItemService");
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private WechatService wechatService = SpringContextHolder.getBean("wechatService");
    private WechatReplyRuleService wechatReplyRuleService = SpringContextHolder.getBean("wechatReplyRuleService");
    private WechatEventService wechatEventService = SpringContextHolder.getBean("wechatEventService");


    /*
     * 记录消息日志
     * (non-Javadoc)
     * @see com.gson.inf.MessageProcessingHandler#allType(com.gson.bean.InMessage)
     */
    @Override
    public void allType(InMessage msg) {
        WechatReceiveMsgLog wechatReceiveMsgLog = new WechatReceiveMsgLog();
        String content = msg.getContent();
        if (StringUtils.isNotBlank(msg.getEvent())) {
            // 模版消息发送任务完成后，微信服务器事件推送的记录，有预存只需更新记录
            if (EventTypes.TEMPLATESENDJOBFINISH.toString().equalsIgnoreCase(msg.getEvent())) {
                templateSendJobFinish(msg, wechatReceiveMsgLog);
                return;
            }
            // 群发消息发送任务完成后，微信服务器事件推送的记录，有预存只需更新记录
            if (EventTypes.MASSSENDJOBFINISH.toString().equalsIgnoreCase(msg.getEvent())) {
                massSendJobFinish(msg, wechatReceiveMsgLog);
                return;
            }

            if (StringUtils.isBlank(content)) {
                content = msg.getEvent();
            }
            String eventKey = msg.getEventKey();
            if (StringUtils.isNotBlank(eventKey)) {
                if (eventKey.indexOf("qrscene_") == 0) {    // 截取参数
                    eventKey = eventKey.substring(eventKey.indexOf("qrscene_") + "qrscene_".length());
                }
                wechatReceiveMsgLog.setEventKey(eventKey);
            }
            WechatAccount wechatAccount = wechatAccountService.findUniqueBy(null, msg.getToUserName(), true);
            wechatEventService.doHandler(msg, wechatAccount);
        }

        // 查找消息的回复设置
        wechatAccount = wechatAccountService.findUniqueBy(null, msg.getToUserName(), true);
        if (wechatAccount != null && StringUtils.isNotBlank(content) && EventTypes.isNeedReplyEvent(msg.getEvent())) {
            WechatReplyKeyword wrk = new WechatReplyKeyword();
            wrk.setKeyword(content);
            wrk.setWechatAccount(wechatAccount);
            List<WechatReplyKeyword> wechatReplyKeywords = new ArrayList<WechatReplyKeyword>();
            if ("subscribe".equals(content)) {
//                Map<String, Object> flag = wechatService.doCheckWechatFistIsSub(msg.getFromUserName(), wechatAccount);
//                if ((boolean) flag.get("success")) {
//                    content = "guanzhuhuodong";
//                }
                wrk.setKeyword(content);
                wechatReplyKeywords = wechatReplyKeywordService.findBy(wrk);
                if (wechatReplyKeywords.size() > 0) {
                    WechatReplyKeyword wechatReplyKeyword = wechatReplyKeywords.get(0);
                    WechatReplyRule replyRule = wechatReplyRuleService.load(wechatReplyKeyword.getReplyRule().getId());
                    WechatDataItem wechatDataItem = replyRule.getDataItem();
                    dataItemId = wechatDataItem.getId();
                    wechatReceiveMsgLog.setType(dataItemId);
                }
            } else {
                if (StringUtils.isNotBlank(wechatReceiveMsgLog.getEventKey())) {
                    wrk.setKeyword(wechatReceiveMsgLog.getEventKey());
                }
                wechatReplyKeywords = wechatReplyKeywordService.findBy(wrk);
                if (wechatReplyKeywords.size() > 0) {
                    WechatReplyKeyword wechatReplyKeyword = wechatReplyKeywords.get(0);
                    WechatReplyRule replyRule = wechatReplyRuleService.load(wechatReplyKeyword.getReplyRule().getId());
                    WechatDataItem wechatDataItem = replyRule.getDataItem();
                    dataItemId = wechatDataItem.getId();
                    wechatReceiveMsgLog.setType(dataItemId);
                }
            }
        }
        if (com.zuipin.util.StringUtils.isNotBlank(msg.getEvent())) {
            wechatReceiveMsgLog.setEvent(EventTypes.valueOf(msg.getEvent()));
        }
        wechatReceiveMsgLog.setMsgType(MsgTypes.valueOf(msg.getMsgType()));
        wechatReceiveMsgLog.setOpenId(msg.getFromUserName());
        wechatReceiveMsgLog.setOriginalId(msg.getToUserName());
        wechatReceiveMsgLog.setStatus(true);
        wechatReceiveMsgLog.setContent(msg.getContent());    // 文本消息内容
        wechatReceiveMsgLog.setCreateTime(new Date());
        wechatReceiveMsgLogService.saveWechatReceiveMsgLog(wechatReceiveMsgLog);
        wechatReceiveMsgLogId = wechatReceiveMsgLog.getId();
    }



    /**
     * 模版消息发送任务完成后，微信服务器事件推送的记录，有预存只需更新记录
     *
     * @param msg
     * @param wechatReceiveMsgLog
     */
    private void templateSendJobFinish(InMessage msg, WechatReceiveMsgLog wechatReceiveMsgLog) {
        wechatReceiveMsgLog = wechatReceiveMsgLogService.loadBy(msg.getMsgID());
        if (wechatReceiveMsgLog != null) {
            wechatReceiveMsgLog.setMsgType(MsgTypes.valueOf(msg.getMsgType()));
            wechatReceiveMsgLog.setEvent(EventTypes.valueOf(msg.getEvent()));
            if ("success".equals(msg.getStatus())) {
                wechatReceiveMsgLog.setStatus(true);
            } else {
                wechatReceiveMsgLog.setStatus(false);
                wechatReceiveMsgLog.setException(msg.getStatus());
            }
            wechatReceiveMsgLog.setOpenId(msg.getFromUserName());
            wechatReceiveMsgLog.setOriginalId(msg.getToUserName());
            wechatReceiveMsgLog.setStatus(true);
//					wechatReceiveMsgLog.setContent(msg.getContent());// 文本消息内容
            wechatReceiveMsgLog.setCreateTime(new Date());
            wechatReceiveMsgLogService.updateWechatReceiveMsgLog(wechatReceiveMsgLog);
            wechatReceiveMsgLogId = wechatReceiveMsgLog.getId();
        } else {
            log.error("找不到msgId=" + msg.getMsgID() + "事件类型为" + EventTypes.TEMPLATESENDJOBFINISH + "对应的记录");
        }
    }

    /**
     * 群发消息发送任务完成后，微信服务器事件推送的记录，有预存只需更新记录
     *
     * @param msg
     * @param wechatReceiveMsgLog
     */
    private void massSendJobFinish(InMessage msg, WechatReceiveMsgLog wechatReceiveMsgLog) {
        wechatReceiveMsgLog = wechatReceiveMsgLogService.loadBy(msg.getMsgID());
        if (wechatReceiveMsgLog != null) {
            wechatReceiveMsgLog.setMsgType(MsgTypes.valueOf(msg.getMsgType()));
            wechatReceiveMsgLog.setEvent(EventTypes.valueOf(msg.getEvent()));
            if ("sendsuccess".equals(msg.getStatus())) {
                wechatReceiveMsgLog.setStatus(true);
            } else {
                wechatReceiveMsgLog.setStatus(false);
                wechatReceiveMsgLog.setException(msg.getStatus());
            }
            StringBuilder result = new StringBuilder();
            result.append("TotalCount:").append(msg.getTotalCount())
                    .append(",FilterCount").append(msg.getFilterCount())
                    .append("SentCount").append(msg.getSentCount())
                    .append("ErrorCount").append(msg.getErrorCount());

            wechatReceiveMsgLog.setOpenId(msg.getFromUserName());
            wechatReceiveMsgLog.setOriginalId(msg.getToUserName());
            wechatReceiveMsgLog.setStatus(true);
            wechatReceiveMsgLog.setContent(result.toString());
            wechatReceiveMsgLog.setCreateTime(new Date());
            wechatReceiveMsgLogService.updateWechatReceiveMsgLog(wechatReceiveMsgLog);
            wechatReceiveMsgLogId = wechatReceiveMsgLog.getId();
        } else {
            log.error("找不到msgId=" + msg.getMsgID() + "事件类型为" + EventTypes.MASSSENDJOBFINISH + "对应的记录");
        }
    }

    @Override
    public void textTypeMsg(InMessage msg) {
        buildOutMessage(msg);
    }

    @Override
    public void locationTypeMsg(InMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void imageTypeMsg(InMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void videoTypeMsg(InMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void linkTypeMsg(InMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void voiceTypeMsg(InMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void verifyTypeMsg(InMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eventTypeMsg(InMessage msg) {
        if (EventTypes.subscribe.toString().equalsIgnoreCase(msg.getEvent()) || EventTypes.CLICK.toString().equalsIgnoreCase(msg.getEvent())) {
            buildOutMessage(msg);
        } else if (EventTypes.kf_close_session.toString().equalsIgnoreCase(msg.getEvent())) {  // 客服退出回复
            String kfCloseSessionHint = propertiesManager.getString("WEBCHAT_KF_CLOSE_SESSION_HINT");
            if (StringUtils.isNotBlank(kfCloseSessionHint)) {
                TextOutMessage out = new TextOutMessage();
                out.setToUserName(msg.getFromUserName());
                out.setContent(kfCloseSessionHint);
                wechatService.doCustomSend(wechatAccount.getId(), out);
//                setOutMessage(out);
            }
        }
    }

    @Override
    public void afterProcess(InMessage inMsg, OutMessage outMsg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setOutMessage(OutMessage outMessage) {
        this.outMessage = outMessage;
    }

    @Override
    public OutMessage getOutMessage() {
        return outMessage;
    }

    /**
     * 构建回复消息，需要自行扩展
     *
     * @param inMsg
     * @author caiys
     * @date 2015年11月25日 下午3:52:51
     */
    public void buildOutMessage(InMessage inMsg) {
        if (dataItemId != null) {
            WechatDataItem wechatDataItem = wechatDataItemService.load(dataItemId);
            if (MsgTypes.text == wechatDataItem.getType()) {
                WechatDataTextService wechatDataTextService = SpringContextHolder.getBean("wechatDataTextService");
                WechatDataText wdt = new WechatDataText();
                wdt.setDataItem(wechatDataItem);
                List<WechatDataText> wechatDataTexts = wechatDataTextService.findBy(wdt);
                if (wechatDataTexts.size() > 0) {
                    WechatDataText wechatDataText = wechatDataTexts.get(0);
                    // 回复内容
                    TextOutMessage out = new TextOutMessage();
                    out.setContent(wechatDataText.getContent());
                    // setOutMessage(out);
                    // 是否直接转到客服，是-把用户消息发给客服，同时把回复内容发给普通用户
                    handleOnlineService(out, wechatDataItem, inMsg);
                } else {
                    log.error("找不到wechatDataItemId=" + dataItemId + "类型为" + MsgTypes.text + "对应的回复内容");
                    WechatReceiveMsgLog wechatReceiveMsgLog = wechatReceiveMsgLogService.load(wechatReceiveMsgLogId);
                    wechatReceiveMsgLog.setStatus(false);
                    wechatReceiveMsgLog.setException("找不到wechatDataItemId=" + dataItemId + "类型为" + MsgTypes.text + "对应的回复内容");
                    wechatReceiveMsgLogService.saveWechatReceiveMsgLog(wechatReceiveMsgLog);
                }
            } else if (MsgTypes.news == wechatDataItem.getType()) {
                String domain = propertiesManager.getString("IMG_DOMAIN");
                WechatDataImgTextService wechatDataImgTextService = SpringContextHolder.getBean("wechatDataImgTextService");
                WechatDataNews wdit = new WechatDataNews();
                wdit.setDataItem(wechatDataItem);
                List<WechatDataNews> wechatDataImgTexts = wechatDataImgTextService.findBy(wdit, 10);
                if (wechatDataImgTexts.size() > 0) {
                    // 回复内容
                    List<Articles> articles = new ArrayList<Articles>();
                    for (WechatDataNews wechatDataImgText : wechatDataImgTexts) {
                        Articles article = new Articles();
                        article.setTitle(wechatDataImgText.getTitle());
                        article.setDescription(wechatDataImgText.getContent());
                        article.setPicUrl(domain + wechatDataImgText.getImg_path());
                        if (StringUtils.isNotBlank(wechatDataImgText.getUrl())) {
                            article.setUrl(wechatDataImgText.getUrl());
                        }
                        articles.add(article);
                    }
                    NewsOutMessage out = new NewsOutMessage();
                    out.setArticles(articles);
                    // setOutMessage(out);
                    // 是否直接转到客服，是-把用户消息发给客服，同时把回复内容发给普通用户
                    handleOnlineService(out, wechatDataItem, inMsg);
                } else {
                    log.error("找不到wechatDataItemId=" + dataItemId + "类型为" + MsgTypes.news + "对应的回复内容");
                    WechatReceiveMsgLog wechatReceiveMsgLog = wechatReceiveMsgLogService.load(wechatReceiveMsgLogId);
                    wechatReceiveMsgLog.setStatus(false);
                    wechatReceiveMsgLog.setException("找不到wechatDataItemId=" + dataItemId + "类型为" + MsgTypes.news + "对应的回复内容");
                    wechatReceiveMsgLogService.saveWechatReceiveMsgLog(wechatReceiveMsgLog);
                }
            } else {    // TODO
                log.error("wechatDataItemId=" + dataItemId + "回复消息类型未定义");
                WechatReceiveMsgLog wechatReceiveMsgLog = wechatReceiveMsgLogService.load(wechatReceiveMsgLogId);
                wechatReceiveMsgLog.setStatus(false);
                wechatReceiveMsgLog.setException("wechatDataItemId=" + dataItemId + "回复消息类型未定义");
                wechatReceiveMsgLogService.saveWechatReceiveMsgLog(wechatReceiveMsgLog);
            }
        } else {    // 取默认回复内容，否则不做处理
            String defualtHint = propertiesManager.getString("WEBCHAT_DEFUALT_HINT");
            if (StringUtils.isNotBlank(defualtHint)) {
                TextOutMessage out = new TextOutMessage();
                out.setContent(defualtHint);
                setOutMessage(out);
            } else {
                log.error("配置文件WEBCHAT_DEFUALT_HINT未定义");
                setOutMessage(null);
            }
        }
    }

    /**
     * 是否直接转到客服，是-把用户消息发给客服，同时把回复内容发给普通用户
     */
    public void handleOnlineService(OutMessage outMessage, WechatDataItem wechatDataItem, InMessage inMsg) {
        if (wechatDataItem.getCustomServiceFlag() != null && wechatDataItem.getCustomServiceFlag()) {
            // 获取在线客服
            String kfAccount = wechatService.doGetOnlineKf(wechatAccount.getId());
            if (StringUtils.isBlank(kfAccount)) { // 找不到在线客服，取默认客服 --20170107 取消转接默认客户
//                kfAccount = propertiesManager.getString("WEBCHAT_DEFUALT_KF_ACCOUNT");
//                if (StringUtils.isBlank(kfAccount)) {   // 无默认客服
//                    log.error("配置文件WEBCHAT_DEFUALT_KF_ACCOUNT未定义");
//                    setOutMessage(null);
//                    return;
//                } else {
                    String noOnlineKfHint = propertiesManager.getString("WEBCHAT_NO_ONLINEKF_HINT");
                    TextOutMessage out = new TextOutMessage();
                    out.setContent(noOnlineKfHint);
                    outMessage = out;
//                }
            }
            // 转到客服
            if (StringUtils.isNotBlank(kfAccount)) {
                TransferCustomerServiceOutMessage out = new TransferCustomerServiceOutMessage();
                TransInfo transInfo = new TransInfo();
                transInfo.setKfAccount(kfAccount);
//                out.setEvent(inMsg.getEvent());
//                out.setEventKey(inMsg.getEventKey());
                out.setTransInfo(transInfo);
                setOutMessage(out);
            }
            // 把回复内容给普通用户
            outMessage.setToUserName(inMsg.getFromUserName());
            wechatService.doCustomSend(wechatAccount.getId(), outMessage);
        } else {    // 否则，直接把回复内容发给普通用户
            setOutMessage(outMessage);
        }
    }

}
