package com.data.data.hmly.service.traffic;

import com.data.hmly.service.translation.train.Qunar.PassBy;
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

/**
 * Created by Sane on 15/12/28.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class TrafficServiceTest {

    @Resource
    private TrafficService trafficService;

    @Test
    public void testFindByCity() throws Exception {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse("2016-02-24");
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        List<Traffic> traffics = trafficService.doQueryAndSaveByCity(350200L, 610000L, TrafficType.AIRPLANE, date);
//        List<Traffic> traffics = trafficService.doQueryAndSaveByCity(350200L, 310100L, TrafficType.TRAIN, date, false);
//        for (Traffic traffic : traffics) {
//            List<TrafficPrice> prices = traffic.getPrices();
//            if (prices != null && prices.size() > 0)
//                System.out.println(prices.get(0).getName() + "\t" + prices.get(0).getPrice());
//            System.out.println(traffic.getTrafficCode());
//        }
//        assertNotNull(traffics);
    }

    @Test
    public void testGetPassBies() {
        List<PassBy> passBies = trafficService.getPassBies("泉州", "福州", "G242", "20160213");
        for (PassBy passBy : passBies) {
            System.out.println(passBy.getSiteName() + "\t" + passBy.getTime() + "\t" + passBy.getLeave() + "\t" + passBy.getArrive() + "\t" + passBy.getNum());
        }
    }
}