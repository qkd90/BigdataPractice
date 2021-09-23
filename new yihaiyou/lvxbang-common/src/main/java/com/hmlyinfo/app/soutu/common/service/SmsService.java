package com.hmlyinfo.app.soutu.common.service;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.exception.BizValidateException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 短信服务
 * Created by Fox on 2015-05-12,0012.
 */
@Service
public class SmsService {
	private static final String UID = "sms9609";
	private static final String KEY = "d0f7b796b036a99ea562";
	private static final String URL = "http://utf8.sms.webchinese.cn/";

	/**
	 * 发送短信
	 * smsMob：手机号码
	 * smsText：短信内容
	 */
	public String sendSms(String smsMob, String smsText) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(URL);
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
		NameValuePair[] data = {new NameValuePair("Uid", UID), new NameValuePair("Key", KEY), new NameValuePair("smsMob", smsMob), new NameValuePair("smsText", smsText)};
		post.setRequestBody(data);
		try {
			client.executeMethod(post);
		} catch (IOException e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
		}
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		String result = null;
		try {
			result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
		} catch (IOException e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
		}
		if (result.equals("1")) {
			return "发送成功！";
		} else if (result.equals("-3")) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "短信数量不足。");
		} else if (result.equals("-14")) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "短信出现非法字符。");
		} else if (result.equals("-4")) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "手机号码格式不正确。");
		} else if (result.equals("-41")) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "手机号码为空。");
		} else if (result.equals("-42")) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "短信内容为空。");
		} else if (result.equals("-51")) {
			throw new BizValidateException(ErrorCode.ERROR_51001, "短信签名格式不正确。");
		}
		return null;
	}

}
