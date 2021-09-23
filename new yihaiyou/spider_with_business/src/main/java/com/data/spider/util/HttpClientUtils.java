package com.data.spider.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HttpClientUtils {
    private static final Log log = LogFactory.getLog(HttpClientUtils.class);
    private static ThreadSafeClientConnManager cm = null;

    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
                .getSocketFactory()));

        cm = new ThreadSafeClientConnManager(schemeRegistry);
        try {
            int maxTotal = 500;
            cm.setMaxTotal(maxTotal);
        } catch (NumberFormatException e) {
            log.error(
                    "Key[httpclient.max_total] Not Found in systemConfig.properties",
                    e);
        }
        // 每条通道的并发连接数设置（连接池）
        try {
            int defaultMaxConnection = 500;
            cm.setDefaultMaxPerRoute(defaultMaxConnection);
        } catch (NumberFormatException e) {
            log.error(
                    "Key[httpclient.default_max_connection] Not Found in systemConfig.properties",
                    e);
        }
    }

    public static HttpClient getHttpClient() {
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 3000ms
        //System.out.println("--------80------------");
        return new DefaultHttpClient(cm, params);
    }

    public static HttpClient getHttpClient(int timeout) {
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout); // 3000ms
        //System.out.println("--------80------------");
        return new DefaultHttpClient(cm, params);
    }

    public static void release() {
        if (cm != null) {
            cm.shutdown();
        }
    }

    public static void main(String[] args) throws ClientProtocolException,
            IOException {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            long l1 = System.currentTimeMillis();
            HttpClient client = getHttpClient();

            HttpGet get = new HttpGet("http://www.baidu.com/s?wd="
                    + r.nextInt(5000));
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                long l = entity.getContentLength();
                System.out.println("��Ӧ����:" + l);
            }
            System.out.println("��ѯ��ʱ" + (System.currentTimeMillis() - l1));
        }
    }

    public static String getHttps(String url, String charset) throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
        HttpClient httpclient = getHttpClient();
        SSLContext ctx = SSLContext.getInstance("SSL");
        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] arg0, String arg1)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] arg0, String arg1)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                // TODO Auto-generated method stub
                return null;
            }
        };

        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
        ClientConnectionManager ccm = httpclient.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));

        HttpGet httpget = new HttpGet(url);

        HttpResponse response = httpclient.execute(httpget);

        return EntityUtils.toString(response.getEntity(), charset);
    }

    public static String getHttps(String url) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        return getHttps(url, "utf-8");
    }

    public static String getHttp(String url) throws ClientProtocolException, IOException {
        HttpClient httpclient = getHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpclient.execute(get);

        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
}
