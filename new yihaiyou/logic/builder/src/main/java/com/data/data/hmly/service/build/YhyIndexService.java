package com.data.data.hmly.service.build;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.hotel.HotelRegionService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.framework.hibernate.util.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-12-28,0028.
 */
@Service
public class YhyIndexService {

    @Resource
    private HotelService hotelService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelRegionService hotelRegionService;
    @Resource
    private LabelService labelService;

    // 一海游PC端酒店民宿首页数据部分

    // 获取特色民宿
    public List<HotelSolrEntity> getFeaturesHotel() {
        HotelSearchRequest searchRequest = new HotelSearchRequest();
        searchRequest.setOrderType(SolrQuery.ORDER.asc);
        searchRequest.setOrderColumn("price");
        searchRequest.setSource("LXB");
        searchRequest.setPriceRange(new ArrayList<Float>(Arrays.asList(0.0001F)));
        List<HotelSolrEntity> solrEntityList = hotelService.listFromSolr(searchRequest, new Page(0, 5));
        return solrEntityList;
    }

    // 获取热门推荐民宿
    public List<HotelSolrEntity> getRecHotel() {
        HotelSearchRequest searchRequest = new HotelSearchRequest();
        searchRequest.setOrderColumn("productScore");
        searchRequest.setOrderType(SolrQuery.ORDER.desc);
        searchRequest.setPriceRange(new ArrayList<Float>(Arrays.asList(0.0001F)));
        List<HotelSolrEntity> solrEntityList = hotelService.listFromSolr(searchRequest, new Page(0, 6));
        return solrEntityList;
    }

    // 获取热门住宿
    public Map<String, Object> getHotHotel() {
        // 获取厦门商圈
        List<HotelRegion> xmRegions = hotelRegionService.listByCity("350200", new Page(0, 7), "priority", "desc");
        Map<String, Object> solrEntityMap = new HashMap<String, Object>();
        solrEntityMap.put("region", xmRegions);
        for (HotelRegion region : xmRegions) {
            HotelSearchRequest searchRequest = new HotelSearchRequest();
            searchRequest.setRegion(region.getId());
            searchRequest.setOrderColumn("productScore");
            searchRequest.setOrderType(SolrQuery.ORDER.desc);
            searchRequest.setPriceRange(new ArrayList<Float>(Arrays.asList(0.001F)));
            List<HotelSolrEntity> solrEntityList = hotelService.listFromSolr(searchRequest, new Page(0, 6));
            solrEntityMap.put("region_" + region.getId(), solrEntityList);
        }
        return solrEntityMap;
    }

    // 一海游PC端景点首页数据部分

    // 获取热门景点
    public List<ScenicSolrEntity> getHotScenic() {
        ScenicSearchRequest searchRequest = new ScenicSearchRequest();
        searchRequest.setOrderColumn("ranking");
        searchRequest.setOrderType(SolrQuery.ORDER.asc);
        searchRequest.setPriceRange(new ArrayList<Integer>(Arrays.asList(0)));
        searchRequest.setPriceMode("than");
        List<ScenicSolrEntity> solrEntities = scenicInfoService.listFromSolr(searchRequest, new Page(0, 6));
        return solrEntities;
    }

    // 获取主题标签, 6个
    public List<Label> getScenicIndexThemeLabel() {
        Label parentCondition = new Label();
        parentCondition.setName("公共标签_景点主题");
        Label parent = labelService.findUnique(parentCondition);
        List<Label> themeLabels = labelService.getChildLabels(parent, new Page(0, 6));
        return themeLabels;
    }
    // 获取主题推荐景点
    public List<ScenicSolrEntity> getThemeRecScenic() {
        ScenicSearchRequest searchRequest = new ScenicSearchRequest();
        searchRequest.setOrderColumn("productScore");
        searchRequest.setOrderType(SolrQuery.ORDER.desc);
        searchRequest.setPriceRange(new ArrayList<Integer>(Arrays.asList(0)));
        searchRequest.setPriceMode("than");
        List<ScenicSolrEntity> solrEntities = scenicInfoService.listFromSolr(searchRequest, new Page(0, 6));
        return solrEntities;
    }


}
