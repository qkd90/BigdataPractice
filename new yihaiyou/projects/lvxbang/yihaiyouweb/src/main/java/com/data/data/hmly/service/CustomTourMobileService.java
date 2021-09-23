package com.data.data.hmly.service;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/7/25.
 */
@Service
public class CustomTourMobileService {

    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private LineService lineService;
    @Resource
    private AreaService areaService;


    public Map<String, Object> getCtiData() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Ads> topBannerAds = advertisingService.getMobileCtiBanner();
        List<Line> tuijianLines = lineService.getLineIndexLine("定制精品首页-精品推荐", new Page(1, 2));
        List<TbArea> remenAreas = areaService.getLineIndexArea("定制精品-面板-热门目的地", new Page(1, 11));
        List<TbArea> moreAreas = areaService.getLineIndexArea("定制精品-面板-热门目的地", new Page(2, 11));
        List<TbArea> zhoubianAreas = areaService.getLineIndexArea("定制精品-面板-周边目的地", new Page(1, 8));
        List<TbArea> guoneiAreas = areaService.getLineIndexArea("定制精品-面板-国内目的地", new Page(1, 8));
        List<TbArea> chujingAreas1 = areaService.getLineIndexArea("定制精品-面板-出境短线", new Page(1, 8));
        List<TbArea> chujingAreas2 = areaService.getLineIndexArea("定制精品-面板-出境长线", new Page(1, 8));
        List<TbArea> chujingAreas = new ArrayList<TbArea>();
        chujingAreas.addAll(chujingAreas1);
        chujingAreas.addAll(chujingAreas2);
        result.put("topBannerAds", topBannerAds);
        result.put("tuijianLines", tuijianLines);
        result.put("remenAreas", remenAreas);
        result.put("moreAreas", moreAreas);
        result.put("zhoubianAreas", zhoubianAreas);
        result.put("guoneiAreas", guoneiAreas);
        result.put("chujingAreas", chujingAreas);
        return result;
    }
}
