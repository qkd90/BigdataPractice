package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.framework.hibernate.util.Page;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class ScenicInfoServiceTest {

    @Resource
    ScenicInfoService scenicInfoService;
    @Resource
    TbAreaService tbAreaService;

//    @Test
//    public void testIndexScenicInfos() throws Exception {
//        scenicInfoService.indexScenicInfoAll(null);
//    }

    @Test
    public void testIndexScenicInfo() throws Exception {
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setId(new Long(1270));
        scenicInfoService.indexScenicInfo(scenicInfo);
    }

    @Test
    public void testListFromSolr() throws Exception {
        ScenicSearchRequest request = new ScenicSearchRequest();
//		request.setName("test");
//		request.setName("公园");
//		List<Integer> priceRange=new ArrayList<>();
//		priceRange.add(50);
//		priceRange.add(100);
//		request.setPriceRange(priceRange);
//		request.setLevel("AAAA");
//		request.setTheme("田园山水");
//		List<String> cities=new ArrayList<>();
//		cities.add("厦门市");
//		request.setCities(cities);
        Page page = new Page();
        page.setPageIndex(1);
        page.setPageSize(100);
        List<ScenicSolrEntity> list = scenicInfoService.listFromSolr(request, page);
        for (ScenicSolrEntity scenicSolrEntity : list) {
            System.out.println(scenicSolrEntity.getName());
            System.out.println(scenicSolrEntity.getCity());
            System.out.println(scenicSolrEntity.getAddress());
            System.out.println();
        }
    }

    @Test
    public void testGetSeasonRecommendScenic() throws Exception {
        List<ScenicInfo> list = scenicInfoService.getSeasonRecommendScenic();
        for (ScenicInfo scenicInfo : list) {
            System.out.println(scenicInfo.getName());
        }
    }

    @Test
    public void testGetRecommendScenic() throws Exception {
        List<ScenicInfo> list = scenicInfoService.getRecommendScenic();
        for (ScenicInfo scenicInfo : list) {
            System.out.println(scenicInfo.getName());
        }
    }

    @Test
    public void testGetTop10Scenic() throws Exception {
        List<ScenicInfo> list = scenicInfoService.getTop10Scenic(new Long(370202));
        System.out.println(list.size());
        for (ScenicInfo scenicInfo : list) {
            System.out.println(scenicInfo.getName());
        }
    }
    @Test
    public void testScenic() throws Exception {
        ScenicInfo scenicInfo = scenicInfoService.get(new Long(6130));
        ScenicSolrEntity so = new ScenicSolrEntity(scenicInfo);
        System.out.print(scenicInfo.getPrice());
    }
    @Test
    public void selectScenic() throws Exception {
    }
}