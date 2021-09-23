package com.data.data.hmly.action.mall.util;

import org.apache.struts2.ServletActionContext;

import java.util.Random;

/**
 * Created by guoshijie on 2015/10/27.
 */
public class SmsUtil {

	public static String SMS_CODE_KEY = "sms_verification_code";

	public static void sendRegisterMessage() {
		int num = new Random().nextInt(1000000);
		while (num < 100000) {
			num = num * 10;
		}

		ServletActionContext.getRequest().getSession().setAttribute(SMS_CODE_KEY, num);
		System.out.println("发送成功: " + num);
	}
}
