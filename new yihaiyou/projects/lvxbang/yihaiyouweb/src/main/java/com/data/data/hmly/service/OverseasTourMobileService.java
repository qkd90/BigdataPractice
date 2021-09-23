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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/7/26.
 */
@Service
public class OverseasTourMobileService {

    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private LineService lineService;
    @Resource
    private AreaService areaService;

    public Map<String, Object> getOtiData() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Ads> topBannerAds = advertisingService.getMobileOtiBanner();
        List<Line> tuijianLines = lineService.getLineIndexLine("出境游首页-精品推荐", new Page(1, 2));
        List<TbArea> renmenAreas = areaService.getLineIndexArea("出境游首页-热门目的地", new Page(1, 12));
        result.put("topBannerAds", topBannerAds);
        result.put("tuijianLines", tuijianLines);
        result.put("renmenAreas", renmenAreas);
        return result;
    }
}
