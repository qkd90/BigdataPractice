package com.data.spider.service.baidu;

import com.data.spider.service.pojo.baidu.Poi.BaiduPoiDetail;
import com.data.spider.service.pojo.baidu.Poi.BaiduPoiList;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/9/17.
 * 抓取百度地点，目前是美食餐厅列表
 */
public class BaiduPoiService {

    private final String poiListApi = "http://lvyou.baidu.com/destination/app/new/poilist?sid=%s" +
            "&m=ac3ef160b0f8f6f88e9996079b5eedee&netTpye=wifi&LVCODE=%s" +
            "&T=%d&locEnabled=YES&locType=GPS&fill_default=1&rn=15&sort=rating&from_type=1&pn=0" +
            "&tag_need=0&type=1&format=app&q=1399K";

    private final String poiDetailApi = "http://lvyou.baidu.com/destination/app/view?apiv=v3&sid=%s&pv=v2.10&from=30" +
            "&format=app&d=android&w=1080&h=1776&u=Sony+Xperia+Z+-+4.2.2+-+API+17+-+1080x1920&v=6.2.2&i=0&s=4.2.2" +
            "&q=1399K&m=ac3ef160b0f8f6f88e9996079b5eedee&netTpye=wifi" +
            "&LVCODE=%s&T=%d&locEnabled=YES&locType=GPS";

    public BaiduPoiList getPoiList(String sid) {
        long timeStamp = System.currentTimeMillis() / 1000;
        String url = String.format(poiListApi, sid, BaiduLVCodeService.getLvCode(timeStamp), timeStamp);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            if (!baiduStr.contains("\"errno\": 0"))
                return null;
            BaiduPoiList baiduPoiSuggestion = new Gson().fromJson(baiduStr, BaiduPoiList.class);
            return baiduPoiSuggestion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BaiduPoiDetail getPoiDetail(String sid) {
        long timeStamp = System.currentTimeMillis() / 1000;
        String url = String.format(poiDetailApi, sid, BaiduLVCodeService.getLvCode(timeStamp), timeStamp);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            if (!baiduStr.contains("\"errno\": 0"))
                return null;
            BaiduPoiDetail baiduPoiSuggestion = new Gson().fromJson(baiduStr, BaiduPoiDetail.class);
            return baiduPoiSuggestion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
