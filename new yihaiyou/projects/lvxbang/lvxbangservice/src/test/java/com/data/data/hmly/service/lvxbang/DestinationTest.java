package com.data.data.hmly.service.lvxbang;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;

/**
 * Created by zzl on 2015/12/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
@Ignore
public class DestinationTest {

    @Resource
    private AreaService areaService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private ScenicInfoService scenicInfoService;


    @Test
    public void testHotArea() {
        List<TbArea> hotAreas = areaService.getHotArea();
        for (TbArea area : hotAreas) {
            System.err.println(area.getName());
        }
    }

    @Test
    public void testSeasonHotArea() {
        List<TbArea> seasonHotAreas = areaService.getSeasonHotArea();
        for (TbArea area : seasonHotAreas) {
            System.err.println(area.getName());
        }
    }

    @Test
    public void testAreaDetail() {
        TbArea area = areaService.get(350200L);
        System.err.println(area.getName() + ":" + area.getFullPath());
        List<ScenicInfo> topScenicInfos = scenicInfoService.getTop10Scenic(350200L);
        System.err.println("必游景点TOP10");
        for (ScenicInfo scenicInfo : topScenicInfos) {
            System.err.println(scenicInfo.getName());
        }
        List<Delicacy> topDelicacies = delicacyService.getTopDelicacy(350200L);
        System.err.println("必吃美食TOP10");
        for (Delicacy delicacy : topDelicacies) {
            System.err.println(delicacy.getName() + "口味" +  delicacy.getTaste());
        }
    }
}
