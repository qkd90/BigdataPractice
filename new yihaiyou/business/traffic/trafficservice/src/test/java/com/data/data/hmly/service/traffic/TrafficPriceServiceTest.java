package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Sane on 15/12/28.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class TrafficPriceServiceTest {

    @Resource
    private TrafficPriceService trafficPriceService;
    @Test
    public void testFindByCityAndDate() throws Exception {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse("2016-02-24");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        List<TrafficPrice> prices = trafficPriceService.findByCityAndDate(110000L, 310000L, date, TrafficType.AIRPLANE);
        for (TrafficPrice price : prices) {
            System.out.println(price.getSeatType() + "\t" + price.getSeatNum() + "\t" + price.getPrice());
        }
        assertNotNull(prices);
    }
}