package com.data.spider.service.baidu;

import com.data.spider.service.pojo.baidu.GeoCoder.Reder;
import com.data.spider.service.pojo.baidu.GeoCoder.RederReverse;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/9/11.
 * 百度官方api，
 * 逆地理编码服务等
 */
public class BaiduGeoCoderService {

    private final String rever_api = "http://api.map.baidu.com/geocoder/v2/?ak=%s&callback=renderReverse&location=%s,%s&output=json&pois=0";
    private final String api = "http://api.map.baidu.com/geocoder/v2/?ak=%s&callback=renderOption&output=json&address=%s";

    //逆地理编码服务
    public RederReverse getRederReverse(String lat, String lng, String key) {
        String url = String.format(rever_api, key, lat, lng);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            baiduStr = baiduStr.substring(29, baiduStr.length() - 1);
            Gson gson = new Gson();
            RederReverse places = gson.fromJson(baiduStr, RederReverse.class);
            return places;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //逆地理编码服务
    public Reder getReder(String address, String city, String key) {
        String url = String.format(api, key, address );
        if (city!=null){
            url +="&city="+city;
        }
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            baiduStr = baiduStr.substring(27, baiduStr.length() - 1);
            Gson gson = new Gson();
            Reder places = gson.fromJson(baiduStr, Reder.class);
            return places;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        BaiduGeoCoderService s = new BaiduGeoCoderService();
        RederReverse reverse = s.getRederReverse("39.911835558087695", "116.32298703399", "PXhzqOZRCWLy6dzlwQuF3gpV");
        RederReverse.Addresscomponent addresscomponent = reverse.result.addressComponent;
        System.out.println(reverse.result.formatted_address);
        System.out.println(addresscomponent.district);
    }


}
