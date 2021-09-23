package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.service.build.YhyIndexService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/12/27.
 */
@Service
public class YhyHotelBuilder {

    private Logger logger = Logger.getLogger(YhyHotelBuilder.class);

    private static final String YHY_HOTEL_INDEX_TEMPLATE = "/yhy/hotel/index.ftl";
    private static final String YHY_HOTEL_INDEX_TARGET = "/yhy/hotel/index.htm";

    @Resource
    private YhyAdsBuilder yhyAdsBuilder;
    @Resource
    private YhyIndexService yhyIndexService;


    public void buildYhyIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        // 广告
        List<Ads> adses = yhyAdsBuilder.getAds(YhyAdsBuilder.YHY_HOTEL_INDEX_TOP_BANNER);
        // 特色民宿
        List<HotelSolrEntity> featuresHotelList = yhyIndexService.getFeaturesHotel();
        // 热门推荐
        List<HotelSolrEntity> hotHotelList = yhyIndexService.getRecHotel();
        // 热门住宿
        Map<String, Object> hotHotelData = yhyIndexService.getHotHotel();
        data.put("adses", adses);
        data.put("featuresHotelList", featuresHotelList);
        data.put("recHotelList", hotHotelList);
        data.put("hotHotelData", hotHotelData);
        FreemarkerUtil.create(data, YHY_HOTEL_INDEX_TEMPLATE, YHY_HOTEL_INDEX_TARGET);
        logger.warn("一海游酒店首页构建完成...!");
    }
}
