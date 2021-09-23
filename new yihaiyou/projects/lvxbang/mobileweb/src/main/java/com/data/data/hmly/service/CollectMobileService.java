package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.response.CollectResponse;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.impression.ImpressionService;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class CollectMobileService {
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private ImpressionService impressionService;

    public List<CollectResponse> collectList(Long userId, Page page) {
        List<OtherFavorite> otherFavorites = otherFavoriteService.findOtherFavoriteBy(userId, page, ProductType.impression, ProductType.recplan);
        List<CollectResponse> list = Lists.newArrayList();
        for (OtherFavorite favorite : otherFavorites) {
            CollectResponse response = new CollectResponse(favorite);
            if (response.getTargetType().equals(ProductType.recplan)) {
                response.setBrowsingNum(recommendPlanService.getBrowsingNum(response.getTargetId()));
            } else if (response.getTargetType().equals(ProductType.impression)) {
                response.setBrowsingNum(impressionService.getBrowsingNum(response.getTargetId()));
            }
            list.add(response);
        }
        return list;
    }
}
