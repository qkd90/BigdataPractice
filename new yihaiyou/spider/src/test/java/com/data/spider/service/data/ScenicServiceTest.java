package com.data.spider.service.data;

import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Sane on 16/1/25.
 */
@Ignore
public class ScenicServiceTest {

    private static ApplicationContext ac;

    @Test
    public void testSaveScenicArea() throws Exception {

        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ScenicService scenicService = SpringContextHolder.getBean("scenicService");

//        scenicService.saveScenicArea("1101");
//        scenicService.saveScenicArea("1102");
//        scenicService.saveScenicArea("1201");
//        scenicService.saveScenicArea("1202");
//        scenicService.saveScenicArea("1301");
//        scenicService.saveScenicArea("1303");
//        scenicService.saveScenicArea("1304");
//        scenicService.saveScenicArea("1307");
//        scenicService.saveScenicArea("1308");
//        scenicService.saveScenicArea("1309");
//        scenicService.saveScenicArea("1310");
//        scenicService.saveScenicArea("1401");
//        scenicService.saveScenicArea("1402");
//        scenicService.saveScenicArea("1405");
//        scenicService.saveScenicArea("1407");
//        scenicService.saveScenicArea("1409");
//        scenicService.saveScenicArea("1410");
//        scenicService.saveScenicArea("1411");
//        scenicService.saveScenicArea("1501");
//        scenicService.saveScenicArea("1502");
//        scenicService.saveScenicArea("1504");
//        scenicService.saveScenicArea("1506");
//        scenicService.saveScenicArea("1507");
//        scenicService.saveScenicArea("1522");
//        scenicService.saveScenicArea("1525");
//        scenicService.saveScenicArea("2101");
//        scenicService.saveScenicArea("2102");
//        scenicService.saveScenicArea("2106");
//        scenicService.saveScenicArea("2111");
//        scenicService.saveScenicArea("2112");
//        scenicService.saveScenicArea("2114");
//        scenicService.saveScenicArea("2201");
//        scenicService.saveScenicArea("2202");
//        scenicService.saveScenicArea("2203");
//        scenicService.saveScenicArea("2205");
//        scenicService.saveScenicArea("2206");
//        scenicService.saveScenicArea("2207");
//        scenicService.saveScenicArea("2208");
//        scenicService.saveScenicArea("2224");
//        scenicService.saveScenicArea("2301");
//        scenicService.saveScenicArea("2306");
//        scenicService.saveScenicArea("2307");
//        scenicService.saveScenicArea("2310");
//        scenicService.saveScenicArea("2311");
//        scenicService.saveScenicArea("2327");
//        scenicService.saveScenicArea("3101");
//        scenicService.saveScenicArea("3102");
//        scenicService.saveScenicArea("3201");
//        scenicService.saveScenicArea("3202");
//        scenicService.saveScenicArea("3203");
//        scenicService.saveScenicArea("3204");
//        scenicService.saveScenicArea("3205");
//        scenicService.saveScenicArea("3207");
//        scenicService.saveScenicArea("3209");
//        scenicService.saveScenicArea("3210");
//        scenicService.saveScenicArea("3211");
//        scenicService.saveScenicArea("3212");
//        scenicService.saveScenicArea("3301");
//        scenicService.saveScenicArea("3302");
//        scenicService.saveScenicArea("3303");
//        scenicService.saveScenicArea("3304");
//        scenicService.saveScenicArea("3305");
//        scenicService.saveScenicArea("3306");
//        scenicService.saveScenicArea("3307");
//        scenicService.saveScenicArea("3309");
//        scenicService.saveScenicArea("3310");
//        scenicService.saveScenicArea("3311");
//        scenicService.saveScenicArea("3401");
//        scenicService.saveScenicArea("3402");
//        scenicService.saveScenicArea("3408");
//        scenicService.saveScenicArea("3410");
//        scenicService.saveScenicArea("3417");
//        scenicService.saveScenicArea("3501");
//        scenicService.saveScenicArea("3502");
//        scenicService.saveScenicArea("3503");
//        scenicService.saveScenicArea("3504");
//        scenicService.saveScenicArea("3505");
//        scenicService.saveScenicArea("3506");
//        scenicService.saveScenicArea("3507");
//        scenicService.saveScenicArea("3508");
//        scenicService.saveScenicArea("3509");
//        scenicService.saveScenicArea("3601");
//        scenicService.saveScenicArea("3602");
//        scenicService.saveScenicArea("3604");
//        scenicService.saveScenicArea("3606");
//        scenicService.saveScenicArea("3607");
//        scenicService.saveScenicArea("3608");
//        scenicService.saveScenicArea("3611");
//        scenicService.saveScenicArea("3701");
//        scenicService.saveScenicArea("3702");
//        scenicService.saveScenicArea("3704");
//        scenicService.saveScenicArea("3706");
//        scenicService.saveScenicArea("3708");
//        scenicService.saveScenicArea("3709");
//        scenicService.saveScenicArea("3710");
//        scenicService.saveScenicArea("3711");
//        scenicService.saveScenicArea("3714");
//        scenicService.saveScenicArea("3717");
//        scenicService.saveScenicArea("4101");
//        scenicService.saveScenicArea("4102");
//        scenicService.saveScenicArea("4103");
//        scenicService.saveScenicArea("4105");
//        scenicService.saveScenicArea("4112");
//        scenicService.saveScenicArea("4117");
//        scenicService.saveScenicArea("4201");
//        scenicService.saveScenicArea("4203");
//        scenicService.saveScenicArea("4205");
//        scenicService.saveScenicArea("4206");
//        scenicService.saveScenicArea("4210");
//        scenicService.saveScenicArea("4290");
//        scenicService.saveScenicArea("4301");
//        scenicService.saveScenicArea("4302");
//        scenicService.saveScenicArea("4303");
//        scenicService.saveScenicArea("4304");
//        scenicService.saveScenicArea("4306");
//        scenicService.saveScenicArea("4308");
//        scenicService.saveScenicArea("4310");
//        scenicService.saveScenicArea("4312");
//        scenicService.saveScenicArea("4331");
//        scenicService.saveScenicArea("4401");
//        scenicService.saveScenicArea("4402");
//        scenicService.saveScenicArea("4403");
//        scenicService.saveScenicArea("4404");
//        scenicService.saveScenicArea("4405");
//        scenicService.saveScenicArea("4406");
//        scenicService.saveScenicArea("4408");
//        scenicService.saveScenicArea("4413");
//        scenicService.saveScenicArea("4501");
//        scenicService.saveScenicArea("4502");
//        scenicService.saveScenicArea("4503");
//        scenicService.saveScenicArea("4505");
//        scenicService.saveScenicArea("4511");
//        scenicService.saveScenicArea("4601");
//        scenicService.saveScenicArea("4602");
//        scenicService.saveScenicArea("4603");
//        scenicService.saveScenicArea("5001");
//        scenicService.saveScenicArea("5002");
//        scenicService.saveScenicArea("5101");
//        scenicService.saveScenicArea("5111");
//        scenicService.saveScenicArea("5115");
//        scenicService.saveScenicArea("5119");
//        scenicService.saveScenicArea("5132");
//        scenicService.saveScenicArea("5133");
//        scenicService.saveScenicArea("5201");
//        scenicService.saveScenicArea("5203");
//        scenicService.saveScenicArea("5204");
//        scenicService.saveScenicArea("5301");
//        scenicService.saveScenicArea("5303");
//        scenicService.saveScenicArea("5304");
//        scenicService.saveScenicArea("5305");
//        scenicService.saveScenicArea("5306");
//        scenicService.saveScenicArea("5307");
//        scenicService.saveScenicArea("5308");
//        scenicService.saveScenicArea("5309");
//        scenicService.saveScenicArea("5323");
//        scenicService.saveScenicArea("5325");
//        scenicService.saveScenicArea("5326");
//        scenicService.saveScenicArea("5328");
//        scenicService.saveScenicArea("5329");
//        scenicService.saveScenicArea("5331");
//        scenicService.saveScenicArea("5333");
//        scenicService.saveScenicArea("5334");
//        scenicService.saveScenicArea("5401");
//        scenicService.saveScenicArea("5402");
//        scenicService.saveScenicArea("5421");
//        scenicService.saveScenicArea("5422");
//        scenicService.saveScenicArea("5424");
//        scenicService.saveScenicArea("5425");
//        scenicService.saveScenicArea("5426");
//        scenicService.saveScenicArea("6101");
//        scenicService.saveScenicArea("6106");
//        scenicService.saveScenicArea("6107");
//        scenicService.saveScenicArea("6108");
//        scenicService.saveScenicArea("6201");
//        scenicService.saveScenicArea("6202");
//        scenicService.saveScenicArea("6207");
//        scenicService.saveScenicArea("6209");
//        scenicService.saveScenicArea("6301");
//        scenicService.saveScenicArea("6302");
//        scenicService.saveScenicArea("6322");
//        scenicService.saveScenicArea("6325");
//        scenicService.saveScenicArea("6326");
//        scenicService.saveScenicArea("6327");
//        scenicService.saveScenicArea("6328");
//        scenicService.saveScenicArea("6401");
//        scenicService.saveScenicArea("6405");
//        scenicService.saveScenicArea("6501");
//        scenicService.saveScenicArea("6502");
//        scenicService.saveScenicArea("6521");
//        scenicService.saveScenicArea("6522");
//        scenicService.saveScenicArea("6523");
//        scenicService.saveScenicArea("6527");
//        scenicService.saveScenicArea("6528");
//        scenicService.saveScenicArea("6529");
//        scenicService.saveScenicArea("6530");
//        scenicService.saveScenicArea("6531");
//        scenicService.saveScenicArea("6532");
//        scenicService.saveScenicArea("6540");
//        scenicService.saveScenicArea("6542");
//        scenicService.saveScenicArea("6543");
//        scenicService.saveScenicArea("7119");

    }
}