/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.util;


import com.alibaba.fastjson.JSON;
import com.gson.WeChat;
import com.gson.bean.Attachment;
import com.gson.bean.UserInfo;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * https 请求 微信为https的请求
 *
 * @author L.cm
 * @date 2013-10-9 下午2:40:19
 */ 
public class HttpKit {
    private static Log log = LogFactory.getLog(HttpKit.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
    public static final String CONTENT_TYPE_JSON = "text/json";
    public static final String CONTENT_TYPE_XML = "text/xml";

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParameter(key, params.get(key));
            }
        }

        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnsupportedEncodingException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, params, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String post(String url, Map<String, String> params) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addParameter(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * 发送请求方法
     * @param url       可带参数url
     * @param paramStr  其他参数
     * @return          请求结果
     */
    public static String postHttps(String url, String paramStr, String contentType) {
        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setRequestMethod("POST");
            if (StringUtils.isNotBlank(paramStr)) {
                conn.setRequestProperty("Content-Type", contentType);
            }
            conn.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;   // mark everything as verified
                }
            });
            conn.setDoOutput(true); // 是否输入参数
            conn.setDoInput(true);
            log.info("请求体：" + paramStr);
            if (StringUtils.isNotBlank(paramStr)) {
                byte[] bytes = paramStr.getBytes("UTF-8");
                conn.getOutputStream().write(bytes);    // 输入参数
            }
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            log.info("返回结果：" + sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * 上传媒体文件
     *
     * @param url
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String url, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线
        builder.setHeader("connection", "Keep-Alive");
        builder.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
        builder.setHeader("Charsert", "UTF-8");
        builder.setHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(); // 定义最后数据分隔线
        builder.setBody(new UploadEntityWriter(end_data, file));

        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * 下载资源
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Attachment download(String url) throws ExecutionException, InterruptedException, IOException {
        Attachment att = new Attachment();
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        Future<Response> f = builder.execute();
        if (f.get().getContentType().equalsIgnoreCase( "text/plain")) {
            att.setError(f.get().getResponseBody(DEFAULT_CHARSET));
        } else {
            BufferedInputStream bis = new BufferedInputStream(f.get().getResponseBodyAsStream());
            String ds = f.get().getHeader("Content-disposition");
            String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
            String relName = fullName.substring(0, fullName.lastIndexOf("."));
            String suffix = fullName.substring(relName.length() + 1);

            att.setFullName(fullName);
            att.setFileName(relName);
            att.setSuffix(suffix);
            att.setContentLength(f.get().getHeader("Content-Length"));
            att.setContentType(f.get().getContentType());
            att.setFileStream(bis);
        }
        http.close();
        return att;
    }

    /**
     * 功能描述：微信商户退款接口
     * @param refundUrl 退款接口URL
     * @param filePath  证书路径
     * @param wId       商户ID
     * @param xmlData   请求参数
     * @return
     */
    public static Map<String, Object> refundHttpPost(String refundUrl, String filePath, String wId, String xmlData) {
        Map<String, Object> map = new HashMap<String, Object>();
        KeyStore keyStore  = null;
        FileInputStream instream = null;
        SSLContext sslcontext = null;
        HttpEntity entity = null;
        String status = null;
        SAXReader saxReader = null;
        Document document = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            instream = new FileInputStream(new File(filePath));
            keyStore.load(instream, wId.toCharArray());

            sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, wId.toCharArray())
                    .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            StringEntity se = new StringEntity(xmlData);

            HttpPost httppost = new HttpPost(refundUrl);

            httppost.setEntity(se);

            CloseableHttpResponse responseEntry = httpclient.execute(httppost);

            entity = responseEntry.getEntity();

            status = String.valueOf(responseEntry.getStatusLine());

            saxReader = new SAXReader();
            document = saxReader.read(entity.getContent());

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (entity != null) {
            Element rootElt = document.getRootElement();
            if ("SUCCESS".equals(rootElt.elementText("return_code"))) {
                map.put("return_code", rootElt.elementText("return_code"));
                map.put("result_code", rootElt.elementText("result_code"));
                map.put("sign", rootElt.elementText("sign"));
                map.put("nonce_str", rootElt.elementText("nonce_str"));
                map.put("err_code", rootElt.elementText("err_code"));
                map.put("err_code_des", rootElt.elementText("err_code_des"));
                map.put("transaction_id", rootElt.elementText("transaction_id"));
                map.put("out_trade_no", rootElt.elementText("out_trade_no"));
                map.put("out_refund_no", rootElt.elementText("out_refund_no"));
                map.put("refund_id", rootElt.elementText("refund_id"));
                map.put("refund_channel", rootElt.elementText("refund_channel"));
                map.put("refund_fee", rootElt.elementText("refund_fee"));
                map.put("coupon_refund_fee", rootElt.elementText("coupon_refund_fee"));
                map.put("root", rootElt.getName());
                map.put("status", status);
            } else {
                map.put("status", status);
                map.put("return_code", rootElt.elementText("return_code"));
                map.put("return_msg", rootElt.elementText("return_msg"));
            }
        }
        return map;
    }

    public static String post(String url, String s) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(s);
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }
    
    public static void main(String[] args) throws Exception {
    	String accessToken = "ulhEL9F2CciJezmGj47C-d3hAJZwXiAANctVIwSHwBRK7Z1enIRWeZKZekk8jS5abIkCo2YmMSDlqUFKOKvSaw";
		String openId = "oeZTVt6XlCphRnCI-DlpdTyk27p4";
		UserInfo u = WeChat.user.getUserInfo(accessToken, openId);
		System.out.println(JSON.toJSONString(u));
		//System.out.println(WeChat.message.sendText(accessToken , openId , "测试"));
    	//Map<String, Object> mgs = WeChat.uploadMedia(accessToken, "image", new File("C:\\Users\\郭华\\Pictures\\13.jpg"));
    	//System.out.println(JSON.toJSONString(mgs));
    }
}