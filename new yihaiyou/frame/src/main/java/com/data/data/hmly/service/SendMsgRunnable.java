package com.data.data.hmly.service;

import com.data.data.hmly.enums.SmsStatusCode;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.SendingMsgHis;
import com.data.data.hmly.service.entity.SysSite;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendMsgRunnable implements Callable<SendingMsg> {
	private SendingMsg sendingMsg;
	private SendingMsgHis msgHistory = new SendingMsgHis();
	private final Log log = LogFactory.getLog(SendMsgRunnable.class);

    private final String keywordFile = "/smsKeyword.info";
    private static Set<String> keywordSet;

	public SendMsgRunnable(SendingMsg sendingMsg) {
		this.sendingMsg = sendingMsg;
	}

	@Override
	public SendingMsg call() throws Exception {
		SendingMsgService sendingMsgService = SpringContextHolder.getBean("sendingMsgService");
		MsgHistoryService msgHistoryService = SpringContextHolder.getBean("msgHistoryService");
		// 1.更新状态为发送中
//		sendingMsg.setSendtime(new Date());
		sendingMsg.setStatus(SendStatus.sending);
		sendingMsgService.update(sendingMsg);
        // 2.发送短信
        String statusCode = SmsStatusCode.SUCCESS.getCode();
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String content = sendingMsg.getContext();
        if (Boolean.parseBoolean(propertiesManager.getString("sendmsg", "false"))) {
            // 判断是否转换短信内容关键字
            if (Boolean.parseBoolean(propertiesManager.getString("sms.convert", "false"))) {
                content = convertKeyWord(content);
            }
            statusCode = sendMsg(sendingMsg.getReceivernum(), content);
        }
        // 3.如果是成功，复制sendingMsg中的信息到msgHistory中；否则重发次数+1
        statusCode = StringUtils.defaultString(statusCode); // 处理为null的时候，以免空指针异常
        log.error("短信发送状态码: " + statusCode);
        Pattern p = Pattern.compile("[1-9]+");    // 正数表示成功
        Matcher m = p.matcher(statusCode);
        boolean b = m.matches();
        if (b) {    // 成功
//            BeanUtils.copyProperties(msgHistory, sendingMsg);
            msgHistory.setContext(sendingMsg.getContext());
            msgHistory.setReceivernum(sendingMsg.getReceivernum());
            msgHistory.setSendtime(new Date());
            msgHistory.setOrderNo(sendingMsg.getOrderNo());
            msgHistory.setRealContext(content);
            msgHistory.setStatus(SendStatus.done);
            if (msgHistory.getRetry() == null) {
                msgHistory.setRetry(0);
            }
            msgHistoryService.insert(msgHistory);
            sendingMsgService.delete(sendingMsg);
        } else {    // 异常
            sendingMsg.setStatusCode(statusCode);
            if (sendingMsg.getRetry() == null) {    // 重发次数+1
                sendingMsg.setRetry(1);
            } else {
                sendingMsg.setRetry(sendingMsg.getRetry() + 1);
            }
            sendingMsgService.update(sendingMsg);
        }
		return sendingMsg;
	}

    /**
     * 发送短信
     * @param num
     * @param text
     * @return 短信状态码，参见com.data.data.hmly.enums.SmsStatusCode
     */
	public String sendMsg(String num, String text) {
        HttpClientBuilder client = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = client.build();
        try {
            PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
            String uid = propertiesManager.getString("sms.uid");
            String key = propertiesManager.getString("sms.key");
            String url = propertiesManager.getString("sms.url");
            /*if (siteId != null) {
                SysSiteService sysSiteService = SpringContextHolder.getBean("sysSiteService");
                SysSite site = sysSiteService.finSiteById(siteId);
                if (StringUtils.isNotBlank(site.getSmsUid()) && StringUtils.isNotBlank(site.getSmsKey())) {
                    uid = site.getSmsUid();
                    key = site.getSmsKey();
                }
            }*/

            HttpPost post = new HttpPost(url);
            post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");	// 在头文件中设置转码
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Uid", uid));
            params.add(new BasicNameValuePair("Key", key));
            params.add(new BasicNameValuePair("smsMob", num));
            params.add(new BasicNameValuePair("smsText", text));
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(params, "UTF-8");
            post.setEntity(postEntity);
            //执行请求
            CloseableHttpResponse response = closeableHttpClient.execute(post);
            //获取响应消息实体
            HttpEntity entity = response.getEntity();
            //判断响应实体是否为空
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                log.info(">>>> 返回结果：" + resultStr);
                return resultStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流并释放资源
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SmsStatusCode.ERROR_SERVER_EXCEPTION.getCode();
	}

    public String convertKeyWord(String content) {
        BufferedReader bfReader = null;
        try {
            // 读取关键词文件
            if (keywordSet == null) {
                keywordSet = new HashSet<String>();
                bfReader = new BufferedReader(new InputStreamReader(
                        SendMsgRunnable.class.getResourceAsStream(keywordFile), "UTF-8"));
                String line = null;
                while ((line = bfReader.readLine()) != null) {
                    if (StringUtils.isNotBlank(line)) {
                        String[] keywords = line.split("/");
                        for (String kw : keywords) {
                            if (StringUtils.isNotBlank(kw)) {
                                keywordSet.add(kw.trim());
                            }
                        }
                    }
                }
            }
            // 关键词转换
            Iterator<String> its = keywordSet.iterator();
            while (its.hasNext()) {
                String it = its.next();
                if (content.indexOf(it) > -1) {
                    String[] kwArray = it.split("");
                    StringBuilder sb = new StringBuilder();
                    for (String kw : kwArray) {
                        if (StringUtils.isNotBlank(kw)) {
                            sb.append(kw).append("~");
                        }
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    content = content.replaceAll(it, sb.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bfReader != null) {
                try {
                    bfReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

}
