package com.data.hmly.service.translation.geo.baidu;

import com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder.Reder;
import com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder.RederReverse;
import com.data.hmly.service.translation.util.CommonUtils;
import com.google.gson.Gson;

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
        int i = 0;
        do {
            try {
                String baiduStr = CommonUtils.getJson(url);
                baiduStr = baiduStr.substring(29, baiduStr.length() - 1);
                RederReverse places = new Gson().fromJson(baiduStr, RederReverse.class);
                return places;
            } catch (Exception e) {

            }
        } while (i++ < 3);
        return null;
    }

    //逆地理编码服务
    public Reder getReder(String address, String city, String key) {
        String url = String.format(api, key, address);
        if (city != null) {
            url += "&city=" + city;
        }
        try {
            String baiduStr = CommonUtils.getJson(url);
            baiduStr = baiduStr.substring(27, baiduStr.length() - 1);
            Gson gson = new Gson();
            Reder places = gson.fromJson(baiduStr, Reder.class);
            return places;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
