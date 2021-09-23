package com.data.spider.service.ctrip;

import com.data.spider.service.pojo.ctrip.CtripRegions;
import org.junit.Test;

import java.util.List;

/**
 * Created by Sane on 16/4/27.
 */
public class CtripWebServiceTest {
    @Test
    public void search() throws Exception {
    }

    @Test
    public void getAdviceTime() throws Exception {

    }

    @Test
    public void getCtripRegions() throws Exception {
        CtripWebService ctripWebService = new CtripWebService();
        CtripRegions ctripRegions = ctripWebService.getCtripRegions(1);

        System.out.println(ctripRegions.getABCDE().size());


    }

    @Test
    public void getRegions() throws Exception {
        CtripWebService ctripWebService = new CtripWebService();
        List<CtripRegions.RegionEntity> ctripRegions = ctripWebService.getRegions(1);

        System.out.println(ctripRegions.size());

        for (CtripRegions.RegionEntity regionEntity : ctripRegions) {
            System.out.println(regionEntity.getName() + "\t" + regionEntity.getLng() + "," + regionEntity.getLat() + "\t" + regionEntity.getDesc());
            System.out.println(regionEntity.getPath());
            System.out.println(regionEntity.getPath().toString());
        }


    }

}