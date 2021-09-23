package com.data.data.hmly.service.restaurant;

import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyRestaurant;
import com.data.data.hmly.service.restaurant.request.DelicacySearchRequest;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.sql.JoinType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMLY on 2015/12/25.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class DelicacyServiceTest {

    @Resource
    DelicacyService delicacyService;


    // @Test
    // @Transactional
    public void testDelicacyById() throws Exception {
        Criteria<Delicacy> criteria = new Criteria<Delicacy>(Delicacy.class);
        criteria.createCriteria("restaurants", "restaurants", JoinType.LEFT_OUTER_JOIN);
        criteria.eq("id", 1L);
        Delicacy delicacy = delicacyService.get(1l);
//        System.out.println("---------------");
//        System.out.println(delicacy.getName());
//        System.out.println("---------------");
        List<DelicacyRestaurant> list1 = delicacy.getRestaurants();
        Assert.isTrue(!list1.isEmpty());
        Assert.notNull(delicacy);
    }

    @Test
    public void testDelicacyList() throws Exception {
        DelicacySearchRequest delicacyRequest = new DelicacySearchRequest();
        delicacyRequest.setName("九龙 豆腐");

        List<Integer> priceRange = new ArrayList<Integer>();
        priceRange.add(0);
        priceRange.add(1000000);
        delicacyRequest.setCuisine("");
        delicacyRequest.setPriceRange(priceRange);
//        List<String> cities = new ArrayList<String>();
//        cities.add("北京市");
//        delicacyRequest.setCities(cities);
        Page page = new Page(1, 10);
        List<DelicacySolrEntity> list = delicacyService.listFromSolr(delicacyRequest, page);
        for (DelicacySolrEntity delicacy : list) {
            System.out.println(delicacy.getName());
        }


        //for (DelicacySolrEntity ds : list) {
        //System.out.println(ds.getName().trim() + ":" + ds.getCuisine().trim() + ":" + ds.getCity().trim());
        //}
//        Assert.isTrue(!list.isEmpty());


    }

    // @Test
    public void testDelicacyIndex() throws Exception {
        List<Delicacy> recommendDelicacy = delicacyService.getRecommendDelicacy();
        Assert.isTrue(!recommendDelicacy.isEmpty());
//        for(Delicacy d : recommendDelicacy){
//            System.out.println(d.getName().trim());
//            Assert.notNull(d);
//        }

        List<Delicacy> featuredDelicacy = delicacyService.getFeaturedDelicacy("140400");
        Assert.isTrue(!featuredDelicacy.isEmpty());
//        for(Delicacy d : featuredDelicacy){
//            System.out.println(d.getName().trim());
//            Assert.notNull(d);
//        }
        List<Delicacy> topDelicacy = delicacyService.getTopDelicacy(350200l);
        Assert.isTrue(!topDelicacy.isEmpty());
//        for(Delicacy d : topDelicacy){
//            System.out.println(d.getName().trim());
//            Assert.notNull(d);
//        }

    }

    //    @Test
    public void testDelicacyIndex2() throws Exception {
        delicacyService.indexDelicacy();
    }
}
