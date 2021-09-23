package com.data.hmly.service.translation.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by Sane on 16/1/7.
 */
public class CommonUtils {


    private final static Log LOG = LogFactory.getLog(CommonUtils.class);

    /**
     * 执行请求并返回结果
     *
     * @return
     * @author caiys
     * @date 2015年12月2日 下午9:37:09
     */
    public static String getJson(String url) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8").replace("{\"nil\":\"1\",\"xsi\":\"http://www.w3.org/2001/XMLSchema-instance\"}", "null");
                return resultStr;
            }
        } catch (SocketTimeoutException e1) {
            LOG.info(String.format("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX Time Out %s", url));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String postJson(String url, String request) {
        String resultStr;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept-Encoding", "gzip");
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(new StringEntity(request, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            return null;
        }
        if (resultStr == null) {
            return null;
        }
        return resultStr;
    }


}
