package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.framework.hibernate.util.Page;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Sane on 16/1/16.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class RecommendPlanServiceTest {
    @Resource
    private RecommendPlanService recommendPlanService;

    //select * from tb_area where name in ('漳州市','南平市','泉州市','张家口市','乌海市','保山市');
    @Test
    public void testIndexRecommendPlan() throws Exception {
        RecommendPlan condition = new RecommendPlan();
        Long[] cityIds = {130700L, 150300L, 350500L, 350600L, 350700L, 530500L};
        for (Long cityId : cityIds) {
            TbArea city = new TbArea();
            city.setId(cityId);
            condition.setCity(city);
            recommendPlanService.indexRecommendPlan(condition, new Page(0, 10000));
        }
    }

    @Test
    public void test() throws Exception {
        recommendPlanService.indexRecommendPlan(null, null);
    }
}