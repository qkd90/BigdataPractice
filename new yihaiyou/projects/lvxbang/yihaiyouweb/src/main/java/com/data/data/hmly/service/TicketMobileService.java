package com.data.data.hmly.service;

import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/8/2.
 */
@Service
public class TicketMobileService {

    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private LabelService labelService;
    @Resource
    private ScenicInfoService scenicInfoService;

    public Map<String, Object> getTkeiData(ScenicSearchRequest scenicSearchRequest) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Ads> topBannerAds = advertisingService.getMobileTkeiBanner();
        Label condition = new Label();
        condition.setName("公共标签_景点主题");
        Label rootThemeLabel = labelService.findUnique(condition);
        List<Label> themeLabels = labelService.getAllChildsLabels(rootThemeLabel);
        if (themeLabels != null && themeLabels.size() > 8) {
            themeLabels = themeLabels.subList(0, 8);
        }
        List<ScenicSolrEntity> scenicList = scenicInfoService.listFromSolr(scenicSearchRequest, new Page(1, 6));
        result.put("topBannerAds", topBannerAds);
        result.put("themeLabels", themeLabels);
        result.put("scenicList", scenicList);
        return result;
    }

    public Map<String, Object> getTkeiNear() {
        Map<String, Object> result = new HashMap<String, Object>();
        Label condition = new Label();
        condition.setName("公共标签_景点主题");
        Label rootThemeLabel = labelService.findUnique(condition);
        List<Label> themeLabels = labelService.getAllChildsLabels(rootThemeLabel);
        result.put("themeLabels", themeLabels);
        return result;
    }

    public List<Label> getScenicList(Label condition) {
        Label rootThemeLabel = labelService.findUnique(condition);
        List<Label> themeLabels = labelService.getAllChildsLabels(rootThemeLabel);
        if (themeLabels != null && themeLabels.size() > 8) {
            themeLabels = themeLabels.subList(0, 8);
        }
        return themeLabels;
    }
}
