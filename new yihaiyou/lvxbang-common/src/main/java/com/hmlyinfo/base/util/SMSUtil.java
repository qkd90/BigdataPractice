package com.hmlyinfo.base.util;

import com.hmlyinfo.app.soutu.base.properties.Config;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by guoshijie on 2015/6/10.
 */
public class SMSUtil {
	private static Logger logger = Logger.getLogger(SMSUtil.class);

	private static final String UID = Config.get("sms.uid");
	private static final String KEY = Config.get("sms.key");
	private static final String URL = Config.get("sms.url");


	public static void send(String phone, String content) {

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(URL);
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); //在头文件中设置转码
		NameValuePair[] data = {new NameValuePair("Uid", UID),
			new NameValuePair("Key", KEY),
			new NameValuePair("smsMob", phone),
			new NameValuePair("smsText", content)};
		post.setRequestBody(data);

		try {
			client.executeMethod(post);

			int statusCode = post.getStatusCode();
			logger.debug("statusCode:" + statusCode);
			String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
			int status = Integer.parseInt(result);
			if (status < 0) {
				logger.error("短信发送失败:" + statusCode);
			}
			System.out.println(result); //打印返回消息状态
		} catch (IOException e) {
			logger.error("短信发送失败", e);
		} finally {
			post.releaseConnection();
		}


	}
}
