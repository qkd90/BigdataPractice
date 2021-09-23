package com.data.spider.service;

import com.data.data.hmly.service.common.AreaRelationService;
import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.scenic.ScenicGeoInfoService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.ScenicOtherService;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.data.spider.service.pojo.tripAdvisor.Attractions;
import com.data.spider.service.pojo.tripAdvisor.Location;
import com.framework.hibernate.util.Criteria;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Sane on 16/2/29.
 */
@Ignore
public class TripAdvisorServiceTest {

    private static ApplicationContext ac;

    @Test
    public void testGetLocationDetail() throws Exception {
        TripAdvisorService tripAdvisorService = new TripAdvisorService();
        Location detail = tripAdvisorService.getLocationDetail(10092036);
        if (detail != null) {
            System.out.println(new Gson().toJson(detail));
        }
    }

    @Test
    public void updateAdviceTime() throws Exception {

    }


    @Test
    public void testGetScenicAll() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        ScenicInfoService scenicInfoService = SpringContextHolder.getBean("scenicInfoService");
        ScenicOtherService scenicOtherService = SpringContextHolder.getBean("scenicOtherService");
        ScenicGeoInfoService scenicGeoInfoService = SpringContextHolder.getBean("scenicGeoInfoService");

        AreaRelationService areaRelationService = SpringContextHolder.getBean("areaRelationService");
//        TbAreaService tbAreaService = SpringContextHolder.getBean("tbAreaService");
        Criteria<AreaRelation> c = new Criteria<AreaRelation>(AreaRelation.class);
//        c.eq("id", 2421249L);
        c.gt("id", 1000000L);
        c.isNotNull("tripAdvisorCity");
        List<AreaRelation> citys = areaRelationService.gets(10000000, c);
        TripAdvisorService tripAdvisorService = new TripAdvisorService();
        for (AreaRelation city : citys) {
            String txt = "0";
            System.out.println(city.getName());
            String tripAdvisorCity = city.getTripAdvisorCity();
            List<Attractions.DataEntity> list = tripAdvisorService.getScenicAll(Integer.parseInt(tripAdvisorCity));
            for (Attractions.DataEntity result : list) {
                try {
                    String sightId = result.getLocation_id();
                    Location detail = tripAdvisorService.getLocationDetail(Integer.parseInt(sightId));
                    if (detail == null) {
                        continue;
                    }
                    ScenicInfo scenicInfo = new ScenicInfo();

                    scenicInfo = tripAdvisorService.getScenicDetail(detail, scenicInfo, city);
                    System.err.println(detail.getLocation_id() + "\t" + scenicInfo.getName());

                    scenicInfoService.save(scenicInfo);

                    ScenicOther scenicOther = tripAdvisorService.getTbScenicOther(detail);
                    scenicOther.setId(scenicInfo.getId());
                    scenicOther.setScenicInfo(scenicInfo);
                    scenicOtherService.save(scenicOther);

                    ScenicGeoinfo scenicGeoinfo = new ScenicGeoinfo();
                    scenicGeoinfo.setId(scenicInfo.getId());
                    scenicGeoinfo.setScenicInfo(scenicInfo);
                    if (detail.getLatitude() != null)
                        scenicGeoinfo.setGoogleLat(Double.parseDouble(detail.getLatitude()));
                    if (detail.getLongitude() != null)
                        scenicGeoinfo.setGoogleLng(Double.parseDouble(detail.getLongitude()));
                    scenicGeoInfoService.save(scenicGeoinfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    txt += "\n" + e.getMessage() + "\n";
                }
            }
            txt += "\n" + "SUCCESSED";
            city.setTripAdvisorUsed(txt);
            areaRelationService.update(city);
        }
    }
}