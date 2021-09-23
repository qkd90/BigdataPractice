package com.data.spider.service.baidu;

import com.data.spider.service.pojo.baidu.Direction.BaiduPlaces;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/9/11.
 * 百度官方api，获取地点信息：地址、电话等
 */
public class BaiduPlaceService {

    private final String api = "http://api.map.baidu.com/place/v2/search?q=%s&scope=2&page_size=%d&region=%s" +
            "&output=json&ak=%s";

    public BaiduPlaces getBaiduPlaces(String q, String region, int size, String key) {
        String url = String.format(api, q, size, region, key);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Gson gson = new Gson();
            BaiduPlaces places = gson.fromJson(baiduStr, BaiduPlaces.class);
            return places;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
