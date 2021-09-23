package com.data.spider.service.baidu;

import com.data.spider.service.pojo.baidu.Dining.BaiduDining;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/9/17.
 * 百度旅游中，抓取百度美食
 */
public class BaiduDiningService {

    private final String diningApi = "http://lvyou.baidu.com/destination/app/sub/dining?" +
            "sid=%s&m=ac3ef160b0f8f6f88e9996079b5eedee" +
            "&netTpye=wifi&LVCODE=%s&T=%d" +
            "&locEnabled=YES&locType=GPS";

    public BaiduDining getDinings(String sid) {
        long timeStamp = System.currentTimeMillis() / 1000;
        String url = String.format(diningApi, sid, BaiduLVCodeService.getLvCode(timeStamp), timeStamp);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            if (!baiduStr.contains("\"errno\": 0"))
                return null;
            BaiduDining baiduPoiSuggestion = new Gson().fromJson(baiduStr, BaiduDining.class);
            return baiduPoiSuggestion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
