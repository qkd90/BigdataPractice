package com.data.data.hmly.service.common;

import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.common.entity.MsgTemplate;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.util.MsgTemplateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by zzl on 2016/12/22.
 */
@Service
public class YhyMsgService {

    private final Logger logger = Logger.getLogger(YhyMsgService.class);
    @Resource
    private MsgTemplateService msgTemplateService;
    @Resource
    private SendingMsgService sendingMsgService;

    /**
     * 一海游订单短信发送
     * @param data
     * @param mobile
     * @param templateKey
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doSendSMS(Map<String, Object> data, String mobile, String templateKey) {
        // find msg template
        MsgTemplate msgTemplate = msgTemplateService.findByKey(templateKey);
        logger.error("短信模板内容: " + msgTemplate.getContent());
        // construct msg content
        String msgContent = MsgTemplateUtil.createContent(data, msgTemplate.getContent());
        this.saveSMS(msgContent, mobile);
    }


    private void saveSMS(String content, String mobile) {
        SendingMsg sendingMsg = new SendingMsg();
        sendingMsg.setReceivernum(mobile);
        sendingMsg.setContext(content);
        sendingMsg.setSendtime(new Date());
        sendingMsg.setStatus(SendStatus.newed);
        sendingMsgService.save(sendingMsg);
    }


}
