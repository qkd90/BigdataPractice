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
 * Created by zzl on 2016/7/21.
 */
@Service
public class DriveTourMobileService {

    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private LineService lineService;
    @Resource
    private AreaService areaService;


    public Map<String, Object> getDtiData() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Ads> topBannerAds = advertisingService.getMobileDtiBanner();
        List<Line> tuijianLines = lineService.getLineIndexLine("自驾游首页-精品推荐", new Page(1, 2));

        List<TbArea> remenAreas = areaService.getLineIndexArea("自助自驾-面板-热门目的地", new Page(1, 11));
        List<TbArea> moreAreas = areaService.getLineIndexArea("自助自驾-面板-热门目的地", new Page(2, 11));

//        List<TbArea> remenAreas1 = areaService.getLineIndexArea("自助自驾-面板-热门目的地", new Page(1, 8));
//        List<TbArea> remenAreas2 = areaService.getLineIndexArea("自助自驾-面板-周边自助自驾", new Page(1, 3));
//        List<TbArea> remenAreas = new ArrayList<TbArea>();
//        remenAreas.addAll(remenAreas1);
//        remenAreas.addAll(remenAreas2);
//        List<TbArea> moreAreas = areaService.getLineIndexArea("自助自驾-面板-国内自助自驾", new Page(1, 8));
        List<TbArea> zhoubianAreas = areaService.getLineIndexArea("自助自驾-面板-周边自助自驾", new Page(1, 8));
        List<TbArea> guoneiAreas = areaService.getLineIndexArea("自助自驾-面板-国内自助自驾", new Page(1, 8));
        List<TbArea> chujingAreas1 = areaService.getLineIndexArea("自助自驾-面板-出境短线自助自驾", new Page(1, 8));
        List<TbArea> chujingAreas2 = areaService.getLineIndexArea("自助自驾-面板-出境长线自助自驾", new Page(1, 8));
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
