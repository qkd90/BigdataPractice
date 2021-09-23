package com.data.hmly.service.translation.geo.baidu;

import com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder.RederReverse;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sane on 16/4/19.
 */
@Ignore
public class BaiduGeoCoderServiceTest {
    @Test
    public void getRederReverse() throws Exception {

    }

    @Test
    public void getReder() throws Exception {
        BaiduGeoCoderService s = new BaiduGeoCoderService();
        RederReverse reverse = s.getRederReverse("39.911835558087695", "116.32298703399", "PXhzqOZRCWLy6dzlwQuF3gpV");
        RederReverse.Addresscomponent addresscomponent = reverse.result.addressComponent;
        System.out.println(reverse.result.formatted_address);
        System.out.println(addresscomponent.district);
        System.out.println(addresscomponent.city);
        System.out.println(addresscomponent.province);
        System.out.println(addresscomponent.adcode);
    }

}