package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.enums.ResourceMapType;
import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/14.
 */
@Service
public class AdvertisingService {

	private static final String INDEX_BANNER = "lvxbang_index_banner";
	private static final String DESTINATION_BANNER = "lvxbang_destination_banner";
	private static final String HOTEL_BANNER = "lvxbang_hotel_banner";
	private static final String SCENIC_BANNER = "lvxbang_scenic_banner";

    // 新版移动端广告
    private static final String NEW_MOBILE_INDEX_TOP_BANNNER = "new_mobile_index_top_banner";
    private static final String NEW_MOBILE_GTI_TOP_BANNER = "new_mobile_gti_top_banner";
    private static final String NEW_MOBILE_DTI_TOP_BANNER = "new_mobile_dti_top_banner";
    private static final String NEW_MOBILE_STI_TOP_BANNER  = "new_mobile_sti_top_banner";
    private static final String NEW_MOBILE_CTI_TOP_BANNER = "new_mobile_cti_top_banner";
    private static final String NEW_MOBILE_ATI_TOP_BANNER = "new_mobile_ati_top_banner";
    private static final String NEW_MOBILE_ITI_TOP_BANNER = "new_mobile_iti_top_banner";
    private static final String NEW_MOBILE_OTI_TOP_BANNER = "new_mobile_oti_top_banner";
    private static final String NEW_MOBILE_TKEI_TOP_BANNER = "new_mobile_tkei_top_banner";
    private static final String NEW_MOBILE_HTEI_TOP_BANNER = "new_mobile_htei_top_banner";
    private static final String NEW_MOBILE_RECPLAN_TOP_BANNER = "new_mobile_recplan_top_banner";
    private static final String NEW_MOBILE_HOME_TOP_BANNER = "new_mobile_home_top_banner";

	@Resource
	private AdsService adsService;
	@Resource
	private SysResourceMapService sysResourceMapService;

	public List<Ads> getIndexBanner() {
		return getAds(INDEX_BANNER);
	}

	public List<Ads> getDestinationBanner() {
		return getAds(DESTINATION_BANNER);
	}

	public List<Ads> getHotelBanner() {
		return getAds(HOTEL_BANNER);
	}
	
    public List<Ads> getScenicBanner() {
    	return getAds(SCENIC_BANNER);
    }

    public List<Ads> getMobileIndexBanner() {
        return getAds(NEW_MOBILE_INDEX_TOP_BANNNER);
    }

    public List<Ads> getMobileGtiBanner() {
        return getAds(NEW_MOBILE_GTI_TOP_BANNER);
    }
    public List<Ads> getMobileDtiBanner() {
        return getAds(NEW_MOBILE_DTI_TOP_BANNER);
    }
    public List<Ads> getMobileStiBanner() {
        return getAds(NEW_MOBILE_STI_TOP_BANNER);
    }
    public List<Ads> getMobileCtiBanner() {
        return getAds(NEW_MOBILE_CTI_TOP_BANNER);
    }
    public List<Ads> getMobileAtiBanner() {
        return getAds(NEW_MOBILE_ATI_TOP_BANNER);
    }
    public List<Ads> getMobileItiBanner() {
        return getAds(NEW_MOBILE_ITI_TOP_BANNER);
    }
    public List<Ads> getMobileOtiBanner() {
        return getAds(NEW_MOBILE_OTI_TOP_BANNER);
    }

    public List<Ads> getMobileTkeiBanner() {
        return getAds(NEW_MOBILE_TKEI_TOP_BANNER);
    }

    public List<Ads> getMobileHteiBanner() {
        return getAds(NEW_MOBILE_HTEI_TOP_BANNER);
    }
    public List<Ads> getMobileRecplanBanner() {
        return getAds(NEW_MOBILE_RECPLAN_TOP_BANNER);
    }
    public List<Ads> getMobileHomeBanner() {
        return getAds(NEW_MOBILE_HOME_TOP_BANNER);
    }

    
	private List<Ads> getAds(String desc) {
		SysResourceMap sysResourceMap = new SysResourceMap();
		sysResourceMap.setDescription(desc);
		sysResourceMap.setResourceType(ResourceMapType.ADPOSITION);
		List<SysResourceMap> list = sysResourceMapService.find(sysResourceMap);
		if (list.isEmpty()) {
			return Lists.newArrayList();
		}
		return adsService.getByResource(list.get(0).getId());
	}
}
