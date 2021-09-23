package com.data.spider.util;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sane on 15/5/30.
 */
public class HttpUtil {

    public static String HttpGetString(String api) throws IOException {
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpGet httpGet = new HttpGet(api);
//        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        HttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

//    public static String HttpGetStringWithGzip(String api) throws IOException {
//        HttpClient httpClient = HttpClientUtils.getHttpClient();
//        HttpGet httpGet = new HttpGet(api);
//        httpGet.setHeader("Accept-Encoding", "gzip");
//        HttpResponse response = httpClient.execute(httpGet);
//        return EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
//    }

//    public static String XMLHttpRequestWithGzip(String api,String json) throws IOException {
//        HttpClient httpClient = HttpClientUtils.getHttpClient();
//        HttpPost httpPost = new HttpPost(api);
//
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Accept-Encoding", "gzip");
//        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
//        httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json");
//        StringEntity se = new StringEntity(json);
////        se.setContentType("application/json");
////        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        httpPost.setEntity(se);
//
//        HttpResponse response = httpClient.execute(httpPost);
//        return EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
//    }

    public static String postStrFromUrl(String url, Map<String, String> paramMap) {
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        String resultStr = null;

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                nvps.add(nvp);
            }
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = httpClient.execute(httpPost);
            resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultStr;
    }

    public static byte[] post(String url, Map<String, String> paramMap) {
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        byte[] resultStr = null;
        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                nvps.add(nvp);
            }
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = httpClient.execute(httpPost);
            resultStr = EntityUtils.toByteArray(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultStr;
    }


    public static String postJson(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        HttpClient httpClient = new DefaultHttpClient();
        try {
            httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json");
            httpPost.setEntity(new StringEntity(json, "utf-8"));
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
