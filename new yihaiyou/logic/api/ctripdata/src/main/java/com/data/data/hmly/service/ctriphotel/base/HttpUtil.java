package com.data.data.hmly.service.ctriphotel.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Sane on 15/5/30.
 */
public class HttpUtil {

    public static String HttpGetString(String api) throws IOException {
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpGet httpGet = new HttpGet(api);
        HttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    public static String HttpGetStringWithGzip(String api) throws IOException {
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpGet httpGet = new HttpGet(api);
        httpGet.setHeader("Accept-Encoding", "gzip");
        HttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
    }

    public static String HttpRequestWithGzip(String api, String json) throws IOException {
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpPost httpPost = new HttpPost(api);

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Accept-Encoding", "gzip");
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json");
        StringEntity se = new StringEntity(json);
//        se.setContentType("application/json");
//        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);

        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
    }
}
