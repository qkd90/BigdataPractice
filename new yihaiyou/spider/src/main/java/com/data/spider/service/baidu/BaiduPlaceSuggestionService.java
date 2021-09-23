package com.data.spider.service.baidu;

import com.data.spider.service.pojo.baidu.Direction.BaiduPlaceSuggestion;
import com.data.spider.service.pojo.baidu.Direction.BaiduPlaceSuggestions;
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
public class BaiduPlaceSuggestionService {

    private final String api = "http://api.map.baidu.com/place/v2/suggestion?query=%s&region=%s&output=json&ak=%s";

    public BaiduPlaceSuggestions getBaiduSuggestions(String q, String region, String key) {
        String url = String.format(api, q, region, key);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Gson gson = new Gson();
            BaiduPlaceSuggestions places = gson.fromJson(baiduStr, BaiduPlaceSuggestions.class);
            return places;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        BaiduPlaceSuggestionService s = new BaiduPlaceSuggestionService();
        BaiduPlaceSuggestions suggestions = s.getBaiduSuggestions("天安门广场", "北京", "PXhzqOZRCWLy6dzlwQuF3gpV");
        for (BaiduPlaceSuggestion suggestion : suggestions.result) {
            System.out.println(suggestion.name + "\t" + suggestion.district);
        }
    }


}
