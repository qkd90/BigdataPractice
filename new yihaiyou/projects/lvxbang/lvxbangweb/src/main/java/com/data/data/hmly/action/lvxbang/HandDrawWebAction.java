package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.lvxbang.response.HandDrawScenicResponse;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.handdrawing.HandDrawService;
import com.data.data.hmly.service.handdrawing.entity.HandDrawMap;
import com.data.data.hmly.service.handdrawing.entity.HandDrawScenic;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.request.TicketSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class HandDrawWebAction extends JxmallAction {

	@Resource
	private HandDrawService handDrawService;
	@Resource
	private TbAreaService tbAreaService;
	@Resource
	private ScenicInfoService scenicInfoService;
    @Resource
    private TicketService ticketService;
    @Resource
    private RecommendPlanService recommendPlanService;

	public Long cityId;
	public int level;
	public Long mapId;
	public Long scenicId;
	public TbArea city;
    public List<ScenicInfo> recommendScenicList;

	public Result index() {
        city = tbAreaService.getById(cityId);
        recommendScenicList = scenicInfoService.getTop10Scenic(cityId);
        return dispatch();
	}

	public Result getMaps() {
		List<HandDrawMap> maps = handDrawService.getByCity(cityId);
		return json(JSONArray.fromObject(maps, JsonFilter.getIncludeConfig()));
	}

	public Result getScenic() {
		List<HandDrawScenic> handDrawScenics;
		if (mapId != null) {
			handDrawScenics = handDrawService.listScenicByMap(mapId);
		} else {
			handDrawScenics = handDrawService.listScenicByCity(cityId, level);
		}
		return json(JSONArray.fromObject(handDrawScenics, JsonFilter.getIncludeConfig("")));
	}

	public Result getAScenic() {
		ScenicInfo scenicInfo = scenicInfoService.get(scenicId);
		HandDrawScenicResponse response = new HandDrawScenicResponse(scenicInfo);
        TicketSearchRequest searchRequest = new TicketSearchRequest();
        searchRequest.setScenicId(scenicId);
        List<TicketSolrEntity> tickets = ticketService.listFromSolr(searchRequest, new Page(0, 1));
        if (tickets.isEmpty()) {
            scenicInfo.setPrice(null);
        }
        RecommendPlanSearchRequest planSearchRequest = new RecommendPlanSearchRequest();
        planSearchRequest.setScenicId(scenicId);
        List<RecommendPlanSolrEntity> planList = recommendPlanService.listFromSolr(searchRequest, new Page(0, 1));
        if (!planList.isEmpty()) {
            response.setRecommendPlanName(planList.get(0).getName());
            response.setRecommendPlanId(planList.get(0).getId());
        }
        return json(JSONObject.fromObject(response, JsonFilter.getIncludeConfig()));
	}

    public Result getRecommendScenic() {
        List<ScenicInfo> list = scenicInfoService.getTop10Scenic(cityId);
        return json(JSONArray.fromObject(Lists.transform(list, new Function<ScenicInfo, HandDrawScenicResponse>() {
            @Override
            public HandDrawScenicResponse apply(ScenicInfo scenicInfo) {
                return new HandDrawScenicResponse(scenicInfo);
            }
        })));
    }

}
