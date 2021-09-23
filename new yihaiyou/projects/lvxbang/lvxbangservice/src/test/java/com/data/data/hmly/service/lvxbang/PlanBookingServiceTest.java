package com.data.data.hmly.service.lvxbang;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class PlanBookingServiceTest {

    @Resource
    private PlanBookingService planBookingService;

    @Test
    public void testBook() throws Exception {
        Long planId = 100218l;
        String ip = "27.154.225.170";
//        List<PlanBookingResponse> responseList = planBookingService.doBook(planId, ip, new HashMap<Long, Map<String, String>>(), null, null);
//        for (PlanBookingResponse response : responseList) {
//            Assert.isTrue(response.getHotels().isEmpty());
//            Assert.isTrue(response.getPlanes().isEmpty());
//            Assert.isTrue(!response.getTrains().isEmpty());
//        }
    }
}