package com.data.hmly.service.translation.direction.bing;

import com.data.hmly.service.translation.direction.bing.pojo.BingRoutesResult;
import com.data.hmly.service.translation.util.CommonUtils;
import com.data.hmly.service.translation.util.CoordinateUtil;
import com.google.gson.Gson;

/**
 * Created by Sane on 16/4/26.
 */
public class BingRoutesService {
    public static BingRoutesResult getBingRoutes(double slat, double slng, double elat, double elng, String key) {
        String origin = slng + "%2C" + slat;
        String destination = elng + "%2C" + elat;
        String amapUrl = "http://dev.virtualearth.net/REST/V1/Routes?wp.0=" + origin
                + "&wp.1=" + destination + "&key=" + key;
        try {
            String json = CommonUtils.getJson(amapUrl);
            BingRoutesResult taxiDirection = new Gson().fromJson(json, BingRoutesResult.class);
            return taxiDirection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static BingRoutesResult getBingRoutesWithBaiduCoordinate(double baidu_slat, double baidu_slng, double baidu_elat, double baidu_elng, String key) {
        double slat = CoordinateUtil.getGoogleLat(baidu_slat, baidu_slng);
        double slng = CoordinateUtil.getGoogleLng(baidu_slat, baidu_slng);
        double elat = CoordinateUtil.getGoogleLat(baidu_elat, baidu_elng);
        double elng = CoordinateUtil.getGoogleLng(baidu_elat, baidu_elng);
        return getBingRoutes(slat, slng, elat, elng, key);
    }

}
