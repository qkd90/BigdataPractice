package com.data.hmly.service.translation.direction.baidu;

import com.data.hmly.service.translation.direction.baidu.pojo.BaiduTaxiDirection;
import com.data.hmly.service.translation.util.CommonUtils;
import com.google.gson.Gson;

/**
 * Created by Sane on 16/4/19.
 */
public class BaiduTaxiDirectionService {


    public static BaiduTaxiDirection getBaiduTaxiMap(String scity, String ecity, double slat, double slng, double elat, double elng, String mode, String key) {
        String origin = slat + "," + slng;
        String destination = elat + "," + elng;
        String baiduUrl = "http://api.map.baidu.com/direction/v1?mode=" + mode + "&origin=" + origin + "&destination=" + destination
                + "&origin_region=" + scity + "&destination_region=" + ecity + "&output=json&ak=" + key;
        try {

            String baiduStr = CommonUtils.getJson(baiduUrl);
            BaiduTaxiDirection taxiDirection = new Gson().fromJson(baiduStr, BaiduTaxiDirection.class);
            return taxiDirection;
        } catch (Exception e) {

        }
        return null;
    }
}
