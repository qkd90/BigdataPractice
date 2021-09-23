package com.data.hmly.service.translation.direction.amap;

import com.data.hmly.service.translation.direction.amap.pojo.AmapTaxiDirectionResult;
import com.data.hmly.service.translation.util.CommonUtils;
import com.data.hmly.service.translation.util.CoordinateUtil;
import com.google.gson.Gson;

/**
 * Created by Sane on 16/4/19.
 */
public class AmapDirectionService {
    //http://restapi.amap.com/v3/direction/walking?origin=116.434307,39.90909&destination=116.434446,39.90816&key=1c061beb50cf259298c21e0d8a34df9f
    public static AmapTaxiDirectionResult getAmapTaxiDirection(double slat, double slng, double elat, double elng, String key) {
        String origin = slat + "," + slng;
        String destination = elat + "," + elng;
        String amapUrl = "http://restapi.amap.com/v3/direction/driving?origin=" + origin
                + "&destination=" + destination + "&output=json&key=" + key;
        try {
            String json = CommonUtils.getJson(amapUrl);
            AmapTaxiDirectionResult taxiDirection = new Gson().fromJson(json, AmapTaxiDirectionResult.class);
            return taxiDirection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static AmapTaxiDirectionResult getAmapTaxiDirectionWithBaiduCoordinate(double baidu_slat, double baidu_slng, double baidu_elat, double baidu_elng, String key) {
        double slat = CoordinateUtil.getGoogleLat(baidu_slat, baidu_slng);
        double slng = CoordinateUtil.getGoogleLng(baidu_slat, baidu_slng);
        double elat = CoordinateUtil.getGoogleLat(baidu_elat, baidu_elng);
        double elng = CoordinateUtil.getGoogleLng(baidu_elat, baidu_elng);
        return getAmapTaxiDirection(slat, slng, elat, elng, key);
    }

}
