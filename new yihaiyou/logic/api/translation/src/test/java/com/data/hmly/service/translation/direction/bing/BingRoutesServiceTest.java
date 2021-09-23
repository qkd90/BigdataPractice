package com.data.hmly.service.translation.direction.bing;

import com.data.hmly.service.translation.direction.bing.pojo.BingRoutesResult;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sane on 16/4/26.
 */
@Ignore
public class BingRoutesServiceTest {
    @Test
    public void getBingRoutes() throws Exception {

    }

    @Test
    public void getBingRoutesWithBaiduCoordinate() throws Exception {
        String key = "ArRaBtq89edluwSzzT7jf9nOJxaFWplojHPvIux8BFqhq8DUB80Vh6OMdUSf9FkE";
//        double baidu_slat = 22.25642715517411;
//        double baidu_slng = 120.9008509166567;
//        double baidu_elat = 21.911980712331008;
//        double baidu_elng = 120.85944314137797;


        double baidu_slat = 31.240630689483893;
        double baidu_slng = 121.48144184858513;
        double baidu_elat = 31.225971079046968;
        double baidu_elng = 121.48150118602845;

        BingRoutesResult result = BingRoutesService.getBingRoutesWithBaiduCoordinate(baidu_slat, baidu_slng, baidu_elat, baidu_elng, key);
        BingRoutesResult.ResourceSetsEntity.ResourcesEntity route1 = result.getResourceSets().get(0).getResources().get(0);
        System.out.println("距离:" + route1.getTravelDistance() + route1.getDistanceUnit());

    }

}