package com.data.hmly.service.translation.direction.amap;

import com.data.hmly.service.translation.direction.amap.pojo.AmapTaxiDirectionResult;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sane on 16/4/22.
 */
@Ignore
public class AmapDirectionServiceTest {

    @Test
    public void getAmapDistance() throws Exception {

//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        String key = "1c061beb50cf259298c21e0d8a34df9f";
        double baidu_slat = 31.240630689483893;
        double baidu_slng = 121.48144184858513;
        double baidu_elat = 31.225971079046968;
        double baidu_elng = 121.48150118602845;
        AmapTaxiDirectionResult result = AmapDirectionService.getAmapTaxiDirectionWithBaiduCoordinate(baidu_slat, baidu_slng, baidu_elat, baidu_elng, key);
        System.out.println("距离:" + result.getRoute().getPaths().get(0).getDistance());
    }

}