package com.data.spider.service;

import com.data.data.hmly.service.common.AreaRelationService;
import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.scenic.RegionService;
import com.data.data.hmly.service.scenic.entity.Region;
import com.data.spider.service.ctrip.CtripWebService;
import com.data.spider.service.pojo.ctrip.CtripRegions;
import com.data.spider.util.CoordinateUtil;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Sane on 16/4/11.
 */
@Ignore
public class CtripRegionTest {
    private static ApplicationContext ac;

    @Test
    public void saveAllRegions() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        RegionService regionService = SpringContextHolder.getBean("regionService");
        CtripWebService ctripWebService = new CtripWebService();
        AreaRelationService areaRelationService = SpringContextHolder.getBean("areaRelationService");
        Criteria<AreaRelation> c = new Criteria<AreaRelation>(AreaRelation.class);
        c.gt("id", 0L);
        c.isNotNull("ctripHotelCity");
        List<AreaRelation> citys = areaRelationService.gets(100000, c);
        for (AreaRelation city : citys) {
            int ctripId = city.getCtripHotelCity().intValue();
            System.out.println("cityCode:" + city.getId() + "\tctripId:" + ctripId);
            List<CtripRegions.RegionEntity> ctripRegions = ctripWebService.getRegions(ctripId);
            doSAveRegions(regionService, ctripRegions, city.getId());
        }

    }

    @Test
    public void saveTestRegions() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        RegionService regionService = SpringContextHolder.getBean("regionService");
        CtripWebService ctripWebService = new CtripWebService();
//        AreaRelationService areaRelationService = SpringContextHolder.getBean("areaRelationService");
//        Criteria<AreaRelation> c = new Criteria<AreaRelation>(AreaRelation.class);
//        c.gt("id", 460199L);
//        c.isNotNull("ctripHotelCity");
//        List<AreaRelation> citys = areaRelationService.gets(10000000, c);
//        for (AreaRelation city : citys) {
        for (int i = 2873; i < 100000; i++) {

            int ctripId = i;
            System.out.println("cityCode:" + ctripId + "\tctripId:" + ctripId);
            List<CtripRegions.RegionEntity> ctripRegions = ctripWebService.getRegions(ctripId);
            doSAveRegions(regionService, ctripRegions, (long) ctripId);
        }

    }

    @Test
    public void saveBeijingRegions() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        RegionService regionService = SpringContextHolder.getBean("regionService");
        CtripWebService ctripWebService = new CtripWebService();
        List<CtripRegions.RegionEntity> ctripRegions = ctripWebService.getRegions(1);
        doSAveRegions(regionService, ctripRegions, 110100l);
    }

    private void doSAveRegions(RegionService regionService, List<CtripRegions.RegionEntity> ctripRegions, Long cityCode) {
        if (ctripRegions == null)
            return;
        for (CtripRegions.RegionEntity regionEntity : ctripRegions) {
            if (regionEntity.getPath() == null)
                continue;
            System.out.println(regionEntity.getName() + "\t" + regionEntity.getLng() + "," + regionEntity.getLat() + "\t" + regionEntity.getDesc());
            Region region = new Region();
            region.setDescription(regionEntity.getDesc());
            region.setName(regionEntity.getName());
            region.setDescription(regionEntity.getDesc());
            String pointStr = "";
            for (CtripRegions.RegionEntity.PathEntity pathEntity : regionEntity.getPath()) {
                double glat = Double.parseDouble(pathEntity.getLat());
                double glng = Double.parseDouble(pathEntity.getLng());
                double lat = CoordinateUtil.getBaiduLat(glat, glng);
                double lng = CoordinateUtil.getBaiduLng(glat, glng);
                pointStr += "[" + lng + "," + lat + "],";
            }
            region.setPointStr(pointStr);
//            region.setPointStr(regionEntity.getPath().toString().replace("[", "").replace("]", "").replace("{lng=", "[").replace("lat=", "").replace("}", "]"));
            region.setCity(new TbArea(cityCode));
            regionService.save(region);
        }
    }


}
