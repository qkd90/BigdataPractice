package com.data.data.hmly.service.user.util;

import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.zuipin.util.SpringContextHolder;
import org.apache.struts2.ServletActionContext;

import java.util.Date;
import java.util.Random;

/**
 * Created by guoshijie on 2015/10/27.
 */
public class SmsUtil {

	public static final String SMS_CODE_KEY = "sms_verification_code";

	public static void sendRegisterMessage(String mobile) {
		SendingMsgService sendingMsgService = SpringContextHolder.getBean("sendingMsgService");
		int num = new Random().nextInt(1000000);
		while (num < 100000) {
			num = num * 10;
		}
		SendingMsg sendingMsg = new SendingMsg();
		sendingMsg.setContext("您的验证码为：" + num + ", 请在5分钟内使用");
		sendingMsg.setReceivernum(mobile);
		sendingMsg.setStatus(SendStatus.newed);
        sendingMsg.setSendtime(new Date());
		sendingMsgService.save(sendingMsg);
		ServletActionContext.getRequest().getSession().setAttribute(SMS_CODE_KEY, num);
		System.out.println("发送成功: " + num);
	}

    /**
     * 定制需求验证短信
     * @param mobile
     */
	public static void sendCustomRequireMessage(String mobile) {
		SendingMsgService sendingMsgService = SpringContextHolder.getBean("sendingMsgService");
        Integer num = genCode();
		SendingMsg sendingMsg = new SendingMsg();
		sendingMsg.setContext("尊敬的用户：您好！您正在进行手机验证码操作，请输入短信验证码：" + num + "，完成验证。");
		sendingMsg.setReceivernum(mobile);
		sendingMsg.setStatus(SendStatus.newed);
		sendingMsg.setSendtime(new Date());
		sendingMsgService.save(sendingMsg);
		ServletActionContext.getRequest().getSession().setAttribute(SMS_CODE_KEY, num);
        System.out.println("发送成功: " + num);
	}

    /**
     * 自动注册短信
     * @param mobile
     */
    public static void sendAutoRegister(String mobile, String account, String pwd) {
        SendingMsgService sendingMsgService = SpringContextHolder.getBean("sendingMsgService");
        SendingMsg sendingMsg = new SendingMsg();
        sendingMsg.setContext("欢迎成为旅行帮会员！手机号：" + account + "，密码：" + pwd + "。您可使用手机号快捷登录，享受最实惠的旅游预订访问！");
        sendingMsg.setReceivernum(mobile);
        sendingMsg.setStatus(SendStatus.newed);
        sendingMsg.setSendtime(new Date());
        sendingMsgService.save(sendingMsg);
    }

	/**
	 * 定制成功短信
	 * @param mobile
	 */
	public static void sendCustomSuccess(String mobile) {
		SendingMsgService sendingMsgService = SpringContextHolder.getBean("sendingMsgService");
		SendingMsg sendingMsg = new SendingMsg();
		sendingMsg.setContext("您好，小帮已经收到您提交的出游需求，工作人员会尽快联系您，请保持您的手机畅通。如有问题建议拨打服务热线400-0131-770进行咨询，祝您生活愉快！");
		sendingMsg.setReceivernum(mobile);
		sendingMsg.setStatus(SendStatus.newed);
		sendingMsg.setSendtime(new Date());
		sendingMsgService.save(sendingMsg);
//		ServletActionContext.getRequest().getSession().setAttribute(SMS_CODE_KEY, num);
	}

    /**
     * 生成六位码
     * @return
     */
    public static Integer genCode() {
        int num = new Random().nextInt(1000000);
        while (num < 100000) {
            num = num * 10;
        }
        return num;
    }
}
