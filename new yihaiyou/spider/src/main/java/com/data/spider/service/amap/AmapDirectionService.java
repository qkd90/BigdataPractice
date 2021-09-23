package com.data.spider.service.amap;

import com.data.spider.service.pojo.amap.AmapDirection;
import com.data.spider.util.CoordinateUtil;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/12/07.
 * 高德官方api，
 * 开车、步行等交通数据等
 * http://lbs.amap.com/api/webservice/reference/direction/
 */
public class AmapDirectionService {
    private static final String test_key = "1c061beb50cf259298c21e0d8a34df9f";
    private static final String direction_api = "http://restapi.amap.com/v3/direction/%s?key=%s&origin=%s,%s&destination=%s,%s";

    //开车、步行等交通数据等
    public static AmapDirection getAmapDirection(String mode, String key, double slat, double slng, double elat, double elng) {
        String url = String.format(direction_api, mode, key, slat, slng, elat, elng);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Gson gson = new Gson();
            AmapDirection direction = gson.fromJson(baiduStr, AmapDirection.class);
            return direction;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AmapDirection getAmapTaxiDirectionWithBaiduCoordinate(String mode, String key, double baidu_slat, double baidu_slng, double baidu_elat, double baidu_elng) {
        double slat = CoordinateUtil.getGoogleLat(baidu_slat, baidu_slng);
        double slng = CoordinateUtil.getGoogleLng(baidu_slat, baidu_slng);
        double elat = CoordinateUtil.getGoogleLat(baidu_elat, baidu_elng);
        double elng = CoordinateUtil.getGoogleLng(baidu_elat, baidu_elng);
        return getAmapDirection(mode, key, slat, slng, elat, elng);
    }


    public static void main(String[] args) {
        AmapDirectionService s = new AmapDirectionService();
        AmapDirection reverse = s.getAmapDirection("driving", test_key, 116.481028, 39.989643, 116.434446, 39.90816);
        System.out.println(reverse.getRoute().getTaxi_cost());
    }


}
