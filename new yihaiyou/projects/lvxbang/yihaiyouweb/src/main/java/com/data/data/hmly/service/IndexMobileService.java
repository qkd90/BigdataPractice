package com.data.data.hmly.service;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.request.LineSearchRequest;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/7/18.
 */
@Service
public class IndexMobileService {

    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private AreaService areaService;
    @Resource
    private LineService lineService;

    /**
     * 新app首页各数据获取
     *
     * @return
     */
    public Map<String, Object> getIndexData() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Ads> topBannerAds = advertisingService.getMobileIndexBanner();
        List<TbArea> middleAreas = areaService.getLineIndexArea("首页-目的地", new Page(1, 6));
        List<TbArea> zhoubianAreas = areaService.getLineIndexArea("首页-周边游目的地", new Page(1, 8));
        List<TbArea> guoneiAreas = areaService.getLineIndexArea("首页-国内游目的地", new Page(1, 8));
        List<TbArea> chujingAreas = areaService.getLineIndexArea("首页-出境游目的地", new Page(1, 8));
        result.put("topBannerAds", topBannerAds);
        result.put("middleAreas", middleAreas);
        result.put("zhoubianAreas", zhoubianAreas);
        result.put("guoneiAreas", guoneiAreas);
        result.put("chujingAreas", chujingAreas);
        return result;
    }

    public List<LineSolrEntity> getIndexAreaLineData(LineSearchRequest lineSearchRequest, Page page) {
        List<LineSolrEntity> lineList = lineService.listFromSolr(lineSearchRequest, page);
        return lineList;
    }

}
