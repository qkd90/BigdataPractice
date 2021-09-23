package com.zuipin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.xerces.impl.dv.util.Base64;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {
    private final static Log log = LogFactory.getLog(HttpUtils.class);
    private final static String UTF8 = "UTF-8";

    public static String post(String url, Map<String, Object> paramsMap) {
        int i = 0;
        do {
            CloseableHttpClient httpClient = null;

            try {
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                }).build();

                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                System.setProperty ("jsse.enableSNIExtension", "false");

                if (httpClient != null) {
                    HttpPost post = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for (Entry<String, Object> entry : paramsMap.entrySet()) {
                        params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                    }
                    UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(params, "UTF-8");
                    post.setEntity(postEntity);

                    CloseableHttpResponse response = httpClient.execute(post);

                    try {
                        HttpEntity entity = response.getEntity();
                        String str = "";
                        if (entity != null) {
                            str = EntityUtils.toString(entity);
                        }
                        return str;
                    } finally {
                        response.close();
                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
//            try {
//                System.setProperty("jsse.enableSNIExtension", "false");
//                RequestConfig requestConfig = RequestConfig.custom()
//                        .setConnectTimeout(2000) //连接超时
//                        .setSocketTimeout(6000)    //读取超时
//                        .build();
//                HttpClientBuilder client = HttpClientBuilder.create();
//                CloseableHttpClient closeableHttpClient = client.build();
//                client.setDefaultRequestConfig(requestConfig);
//                HttpPost post = new HttpPost(url);
//                post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                for (Entry<String, Object> entry : paramsMap.entrySet()) {
//                    params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
//                }
//                UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(params, "UTF-8");
//                post.setEntity(postEntity);
//                CloseableHttpResponse response = closeableHttpClient.execute(post);
//                // int statusCode = response.getStatusLine().getStatusCode();
//                // System.out.println("statusCode:" + statusCode);
//                HttpEntity entity = response.getEntity();
//                String str = "";
//                if (entity != null) {
//                    str = EntityUtils.toString(entity);
//                }
//                closeableHttpClient.close();
//                return str;
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
        } while (i < 3);
        return "";
    }

    public String readString(HttpServletRequest request) {
        try {
            request.setCharacterEncoding(UTF8);
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), UTF8));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String post(String urlStr, String str) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
//            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/json");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), UTF8);
            String xmlInfo = str;
//            System.out.println("urlStr=" + urlStr);
//            System.out.println("xmlInfo=" + xmlInfo);
            out.write(xmlInfo);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), UTF8));
            StringBuilder sb = new StringBuilder();
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String postHttpBasic(String url, Map<String, Object> paramsMap, String name, String password) {
        int i = 0;
        do {
            try {
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(2000) //连接超时
                        .setSocketTimeout(6000)    //读取超时
                        .build();
                HttpClientBuilder client = HttpClientBuilder.create();
                CloseableHttpClient closeableHttpClient = client.build();
                client.setDefaultRequestConfig(requestConfig);
                HttpPost post = new HttpPost(url);
                post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                post.addHeader("Authorization", "Basic " + Base64.encode((name + ":" + password).getBytes()));
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (Entry<String, Object> entry : paramsMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(params, UTF8);
                post.setEntity(postEntity);
                CloseableHttpResponse response = closeableHttpClient.execute(post);
                HttpEntity entity = response.getEntity();
                String str = "";
                if (entity != null) {
                    str = EntityUtils.toString(entity, UTF8);
                }
                closeableHttpClient.close();
                return str;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (i < 3);
        return "";
    }

    public static String readPost(HttpServletRequest request) {
        try {
            request.setCharacterEncoding(UTF8);
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), UTF8));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
