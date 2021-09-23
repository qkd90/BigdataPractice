package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.AdsStatus;
import com.data.data.hmly.enums.ResourceMapType;
import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/12/16.
 */
@Service
public class AdsBuilder {

	private static final String LVXBNAG_INDEX_BANNER_TEMPLATE = "/lvxbang/ads/index.ftl";
	private static final String LVXBANG_INDEX_BANNER_TARGET = "/lvxbang/ads/index.htm";

	private static final String LVXBANG_RECOMMENDPLAN_BANNER_TEMPLATE = "/lvxbang/ads/recplan.ftl";
	private static final String LVXBANG_RECOMMENDPLAN_BANNER_TARGET = "/lvxbang/ads/recplan.htm";

	private static final String LVXBANG_DESTINATION_BANNER_TEMPLATE = "/lvxbang/ads/destination.ftl";
	private static final String LVXBANG_DESTINATION_BANNER_TARGET = "/lvxbang/ads/destination.htm";

	private static final String LVXBANG_TRAFFIC_BANNER_TEMPLATE = "/lvxbang/ads/traffic.ftl";
	private static final String LVXBANG_TRAFFIC_BANNER_TARGET = "/lvxbang/ads/traffic.htm";

	private static final String LVXBANG_HOTEL_BANNER_TEMPLATE = "/lvxbang/ads/hotel.ftl";
	private static final String LVXBANG_HOTEL_BANNER_TARGET = "/lvxbang/ads/hotel.htm";

    private static final String LVXBANG_HOTEL_SIDE_BANNER_TEMPLATE = "/lvxbang/ads/hotelSide.ftl";
	private static final String LVXBANG_HOTEL_SIDE_BANNER_TARGET = "/lvxbang/ads/hotelSide.htm";

	private static final String LVXBANG_SCENIC_BANNER_TEMPLATE = "/lvxbang/ads/scenic.ftl";
	private static final String LVXBANG_SCENIC_BANNER_TARGET = "/lvxbang/ads/scenic.htm";

	private static final String LVXBANG_DELICACY_BANNER_TEMPLATE = "/lvxbang/ads/delicacy.ftl";
	private static final String LVXBANG_DELICACY_BANNER_TARGET = "/lvxbang/ads/delicacy.htm";

    private static final String LVXBANG_PLAN_BANNER_TEMPLATE = "/lvxbang/ads/plan.ftl";
	private static final String LVXBANG_PLAN_BANNER_TARGET = "/lvxbang/ads/plan.htm";


	private static final String LVXBANG_INDEX_BANNER = "lvxbang_index_banner";
	private static final String LVXBANG_RECOMMENDPLAN_BANNER = "lvxbang_recplanplan_banner";
	private static final String LVXBANG_DESTINATION_BANNER = "lvxbang_destination_banner";
	private static final String LVXBANG_TRAFFIC_BANNER = "lvxbang_traffic_banner";
    public static final String LVXBANG_HOTEL_BANNER = "lvxbang_hotel_banner";
    public static final String LVXBANG_HOTEL_SIDE_BANNER = "lvxbang_hotel_side_banner";
	private static final String LVXBANG_SCENIC_BANNER = "lvxbang_scenic_banner";
	public  static final String LVXBANG_DELICACY_BANNER = "lvxbang_delicacy_banner";
	private static final String LVXBANG_PLAN_BANNER = "lvxbang_plan_banner";

	//	private static final String LVXBANG_SCENIC_BANNER = "lvxbang_scenic_banner";
//	private static final String LVXBANG_DESTINATION_BANNER = "lvxbang_destination_banner";


	@Resource
	private AdsService adsService;
	@Resource
	private SysResourceMapService sysResourceMapService;

	public void buildLXBIndex() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_INDEX_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBNAG_INDEX_BANNER_TEMPLATE, LVXBANG_INDEX_BANNER_TARGET);
	}

	public void buildLXBRecplan() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_RECOMMENDPLAN_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_RECOMMENDPLAN_BANNER_TEMPLATE, LVXBANG_RECOMMENDPLAN_BANNER_TARGET);
	}

	public void buildLXBDestination() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_DESTINATION_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_DESTINATION_BANNER_TEMPLATE, LVXBANG_DESTINATION_BANNER_TARGET);
	}

	public void buildLXBTraffic() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_TRAFFIC_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_TRAFFIC_BANNER_TEMPLATE, LVXBANG_TRAFFIC_BANNER_TARGET);
	}

	public void buildLXBHotel() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_HOTEL_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_HOTEL_BANNER_TEMPLATE, LVXBANG_HOTEL_BANNER_TARGET);
	}

    public void buildLXBHotelSide() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_HOTEL_SIDE_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_HOTEL_SIDE_BANNER_TEMPLATE, LVXBANG_HOTEL_SIDE_BANNER_TARGET);
	}

	public void buildLXBScenic() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_SCENIC_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_SCENIC_BANNER_TEMPLATE, LVXBANG_SCENIC_BANNER_TARGET);
	}

	public void buildLXBDelicacy() {
		Map<Object, Object> data = Maps.newHashMap();
		List<Ads> adses = getAds(LVXBANG_DELICACY_BANNER);
		data.put("adses", adses);
		FreemarkerUtil.create(data, LVXBANG_DELICACY_BANNER_TEMPLATE, LVXBANG_DELICACY_BANNER_TARGET);
	}

    public void buildPlanIndex() {
        Map<Object, Object> data = Maps.newHashMap();
        List<Ads> adses = getAds(LVXBANG_PLAN_BANNER);
        data.put("adses", adses);
        FreemarkerUtil.create(data, LVXBANG_PLAN_BANNER_TEMPLATE, LVXBANG_PLAN_BANNER_TARGET);
    }

	public List<Ads> getAds(String desc) {
		SysResourceMap sysResourceMap = new SysResourceMap();
		sysResourceMap.setDescription(desc);
		sysResourceMap.setResourceType(ResourceMapType.ADPOSITION);
		List<SysResourceMap> list = sysResourceMapService.find(sysResourceMap);
		if (list.isEmpty()) {
			return Lists.newArrayList();
		}
		Ads ads = new Ads();
		ads.setSysResourceMap(list.get(0));
		ads.setAdStatus(AdsStatus.UP);
		ads.setForDisplay(true);
        return adsService.getAdsList(ads, new Page(0, 6));
	}
}
