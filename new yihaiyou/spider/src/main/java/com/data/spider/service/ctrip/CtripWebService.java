package com.data.spider.service.ctrip;

import com.data.spider.service.pojo.ctrip.*;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Created by Sane on 15/12/14.
 * 携程网页中的结果，
 * search:搜索返回SearchResult类型，其中包含汽车站、火车站、机场、酒店等结果
 * ；
 */
public class CtripWebService {
    public static String searchApi = "http://you.ctrip.com/SearchSite/Service/Tip2" +
            "?Jsoncallback=jQuery171027422445081174374_1450087835356" +
            "&keyword=%s&_=1450088009554";
    public static String archiveApi = "http://web.archive.org/web/http://you.ctrip.com/sight/%s%d/%d.html";

    public WebSearchResult search(String keyword) {
        String url = String.format(searchApi, keyword);
        String result = getResponse(url);
        if (result == null) {
            return null;
        } else {
            result = result.substring(0, result.length() - 1);
            result = result.replaceAll("jQuery\\d+_\\d+\\(", "");

        }

        return new Gson().fromJson(result, WebSearchResult.class);
    }

    public static String getResponse(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        try {
            response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAdviceTime(String cityPy, int cityId, int ctripId) {

        String html = getResponse(String.format(archiveApi, cityPy, cityId, ctripId));
        Pattern p = Pattern.compile(
                "(建议\\d+.*)[\r\n]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        if (m.find()) {
            return m.group(1).replace("建议","");
        }
        return null;

    }


    public static void main(String[] args) {

//        String html = getHttpsResponse("https://web.archive.org/web/20150323174822/http://you.ctrip.com/sight/lushan20/2286.html");
        String html = getResponse("http://web.archive.org/web/20150323174822/http://you.ctrip.com/sight/jiujiang877/2286.html");
        System.out.println(html);
        System.out.println("~~~~~~");
        if (html.contains("游玩时间")){
            Pattern p = Pattern.compile(
                    "(建议\\d+.*)[\r\n]", Pattern.CASE_INSENSITIVE);

            Matcher m = p.matcher(html);
            if (m.find()) {
                System.out.println("------");
                String adviceTime =  m.group(1);
                System.out.println(adviceTime);
                System.out.println("------");
            }
        }

    }
}
