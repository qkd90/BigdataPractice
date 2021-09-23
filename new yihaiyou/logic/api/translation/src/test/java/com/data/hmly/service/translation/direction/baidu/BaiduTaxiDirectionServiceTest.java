package com.data.hmly.service.translation.direction.baidu;

import com.data.hmly.service.translation.direction.baidu.pojo.BaiduTaxiDirection;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sane on 16/4/19.
 */
@Ignore
public class BaiduTaxiDirectionServiceTest {
    private static final String MODE_DRIVE = "driving";

//    private static ApplicationContext ac;

    @Test
    public void getBaiduTaxiMap() throws Exception {

//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        String key = "p2FOUcg91ZU7pLNssqP4yKWL";
        String cityName = "上海市";
        double slat = 31.240630689483893;
        double slng = 121.48144184858513;
        double elat = 31.225971079046968;
        double elng = 121.48150118602845;

        BaiduTaxiDirection result = BaiduTaxiDirectionService.getBaiduTaxiMap(cityName, cityName, slat, slng, elat, elng, MODE_DRIVE, key);

        BaiduTaxiDirection.Route route = result.result.routes[0];
        BaiduTaxiDirection.Taxi taxi = result.result.taxi;
        if (taxi == null) {
            System.out.println("距离:" + route.distance);
        } else {
            System.out.println("距离:" + taxi.distance);

        }
    }
}