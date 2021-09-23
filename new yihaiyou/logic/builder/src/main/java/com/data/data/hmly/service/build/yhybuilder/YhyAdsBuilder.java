package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.enums.AdsStatus;
import com.data.data.hmly.enums.ResourceMapType;
import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/12/28.
 */
@Service
public class YhyAdsBuilder {

    @Resource
    private SysResourceMapService sysResourceMapService;
    @Resource
    private AdsService adsService;

    // PC端首页广告1920*400
    public static final String YHY_INDEX_TOP_BANNER = "new_pc_index_top_banner";
    // PC端DIY行程首页广告1920*350
    public static final String YHY_DIY_INDEX_TOP_BANNE = "new_pc_diy_index_top_banner";
    // PC端酒店民宿首页广告1920*350
    public static final String YHY_HOTEL_INDEX_TOP_BANNER = "new_pc_hotel_index_top_banner";
    // PC端船票预定首页广告1920*350
    public static final String YHY_FERRY_INDEX_TOP_BANNER = "new_pc_ferry_index_top_banner";
    // PC端游艇帆船首页广告1920*350
    public static final String YHY_SAILBOAT_INDEX_TOP_BANNER = "new_pc_sailboat_index_top_banner";
    // PC端景点门票首页广告1920*350
    public static final String YHY_SCENIC_INDEX_TOP_BANNER = "new_pc_scenic_index_top_banner";
    // PC端邮轮旅游首页广告1920*350
    public static final String YHY_CRUISESHIP_INDEX_TOP_BANNER = "new_pc_cruiseship_index_top_banner";
    // PC端美食旅游首页广告1920*350
    public static final String YHY_DELICACY_INDEX_TOP_BANNER = "new_pc_delicacy_index_top_banner";
    // PC端导游预约首页广告1920*350
    public static final String YHY_TOURIST_GUIDE_INDEX_TOP_BANNER = "new_pc_tourist_guide_index_top_banner";
    // PC端游记攻略首页广告1920*350
    public static final String YHY_RECPLAN_INDEX_TOP_BANNER = "new_pc_recplan_index_top_banner";


    public List<Ads> getAds(String desc) {
        SysResourceMap sysResourceMap = new SysResourceMap();
        sysResourceMap.setDescription(desc);
        sysResourceMap.setResourceType(ResourceMapType.ADPOSITION);
        List<SysResourceMap> sysResourceMaps = sysResourceMapService.find(sysResourceMap);
        if (sysResourceMaps.isEmpty()) {
            return new ArrayList<Ads>();
        }
        Ads ads = new Ads();
        ads.setSysResourceMap(sysResourceMaps.get(0));
        ads.setAdStatus(AdsStatus.UP);
        ads.setForDisplay(true);
        return adsService.getAdsList(ads, new Page(0, 6));
    }

}


