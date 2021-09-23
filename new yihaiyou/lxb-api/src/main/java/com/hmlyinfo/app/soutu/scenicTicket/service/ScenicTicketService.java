package com.hmlyinfo.app.soutu.scenicTicket.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenicTicket.custom.domain.TicketCustom;
import com.hmlyinfo.app.soutu.scenicTicket.custom.service.TicketCustomService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicRecommendTicket;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicket;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketSubOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.TicketRenwoyou;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarPriceService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarSightService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarTicketService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.ResultList;

@Service
public class ScenicTicketService {

	public static final int				TYPE_RENWOYOU				= 1;
	public static final int				TYPE_CTRIP					= 2;
	public static final int				TYPE_QUNAR					= 3;
	public static final int				TYPE_CUSTOM					= 4;

	public static final String			NAME_RENWOYOU				= "renwoyou";
	public static final String			NAME_CUSTOM					= "custom";
	public static final String			NAME_QUNAR					= "qunar";

	public static final int				RECOMMEND_FLAG_SEASONTICKET	= 1;
	public static final int				RECOMMEND_FLAG_PRIMARY		= 2;
	public static final int				RECOMMEND_FLAG_SECNIC		= 3;

	@Autowired
	private PlanTripService				planTripService;
	@Autowired
	private PlanDaysService				planDaysService;
	@Autowired
	private ScenicTicketOrderService	scenicTicketOrderService;
	@Autowired
	private TicketRenwoyouService		ticketRenwoyouService;
	@Autowired
	private QunarTicketService			qunarTicketService;
	@Autowired
	private TicketCustomService			ticketCustomService;
	@Autowired
	private ScenicInfoService			scenicService;
	@Autowired
	private RenwoyouService				renwoyouService;
	@Autowired
	private QunarPriceService			qunarPriceService;
	@Autowired
	private QunarSightService			qunarSightService;

	public ScenicTicket info(Long id, int type) {
		switch (type) {
		case TYPE_RENWOYOU:
			return ticketRenwoyouService.info(id);
		case TYPE_QUNAR:
			return qunarTicketService.info(id);
		}
		return null;
	}

	public int count(long scenicId) {
		List<Long> idList = Lists.newArrayList();
		// 处理子景点门票
		scenicService.prepareScenic(idList, scenicId);
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("scenicIds", idList);

		int rc = ticketRenwoyouService.count(paramMap);
		int qc = qunarTicketService.ticketCount(paramMap, scenicId);
		paramMap.put("status", "1");
		int cc = ticketCustomService.count(paramMap);

		return rc + qc + cc;
	}

	public List list(Map<String, Object> params) {
		List list = null;
		long scenicId = Long.valueOf(params.get("scenicId").toString());
		// int type = (Integer)params.get("type");
		int type = Integer.parseInt(params.get("type").toString());
		params = prepareScenic(scenicId);

		if (type == TYPE_RENWOYOU) {
			list = ticketRenwoyouService.list(params);
		} else if (type == TYPE_QUNAR) {
			list = qunarTicketService.listDetail(params, scenicId);
		}

		return list;
	}

	/**
	 * 所有类型的门票列表
	 *
	 * @return
	 */
	public Map<String, List> listAll(Map<String, Object> params) {

		long scenicId = Long.valueOf(params.get("scenicId").toString());
		Map<String, List> listAllMap = Maps.newHashMap();

		Map<String, Object> scenicMap = prepareScenic(scenicId);
		params.remove("scenicId");
		params.put("scenicIds", scenicMap.get("scenicIds"));

		List<TicketCustom> ticketCustomList = ticketCustomService.list(params);
		for (int i = 0; i < ticketCustomList.size(); i++) {
			TicketCustom ticketCustom = ticketCustomList.get(i);
			if (ticketCustom.getScenicId() == scenicId) {
				ticketCustom.setParentFlag(ScenicTicket.PARENT_YES);
			} else {
				ticketCustom.setParentFlag(ScenicTicket.PARENT_NO);
			}
		}

		List<TicketRenwoyou> ticketRenwoyouList = ticketRenwoyouService.list(params);
		for (int i = 0; i < ticketRenwoyouList.size(); i++) {
			TicketRenwoyou renwoyou = ticketRenwoyouList.get(i);
			if (renwoyou.getScenicId() == scenicId) {
				renwoyou.setParentFlag(ScenicTicket.PARENT_YES);
			} else {
				renwoyou.setParentFlag(ScenicTicket.PARENT_NO);
			}
		}

		List<QunarTicket> ticketQunarList = qunarTicketService.listDetail(params, scenicId);
		// 添加标志表示是否属于子景点门票
		for (int i = 0; i < ticketQunarList.size(); i++) {
			QunarTicket qunarTicket = ticketQunarList.get(i);
			if (qunarTicket.getSeasonTicketFlag().equals("T")) {
				continue;
			}
			if (qunarTicket.getScenicId() == scenicId) {
				qunarTicket.setParentFlag(ScenicTicket.PARENT_YES);
			} else {
				qunarTicket.setParentFlag(ScenicTicket.PARENT_NO);
			}
		}

		listAllMap.put(NAME_CUSTOM, ticketCustomList);
		listAllMap.put(NAME_RENWOYOU, ticketRenwoyouList);
		listAllMap.put(NAME_QUNAR, ticketQunarList);

		return listAllMap;
	}

	/**
	 * 条件预处理
	 * 
	 * @param scenicId
	 * @return
	 */
	private Map<String, Object> prepareScenic(long scenicId) {
		Map<String, Object> paramMap = Maps.newHashMap();
		List<Long> idList = Lists.newArrayList();
		idList.add(scenicId);
		scenicService.prepareScenic(idList, scenicId);

		paramMap.put("scenicIds", idList);

		return paramMap;
	}

	/**
	 * 按照行程编号获取门票列表
	 */
	public Map<Long, Map<String, List>> listByPlan(Long planId) {
		List<PlanTrip> planTripList = planTripService.list(Collections.<String, Object> singletonMap("planId", planId));
		List<Long> scenicIdList = new ArrayList<Long>();
		Map<Long, Map<String, List>> scenicTicketMap = new HashMap<Long, Map<String, List>>();
		for (PlanTrip planTrip : planTripList) {
			if (planTrip.getTripType() != TripType.SCENIC.value()) {
				continue;
			}
			scenicIdList.add(planTrip.getScenicId());
			Map<String, List> scenicTicket = new HashMap<String, List>();
			scenicTicket.put(NAME_RENWOYOU, new ArrayList());
			scenicTicketMap.put(planTrip.getScenicId(), scenicTicket);
		}
		List<TicketRenwoyou> ticketRenwoyouList = ticketRenwoyouService.list(Collections.<String, Object> singletonMap("scenicIds",
				scenicIdList));
		// 分拆任我游数据
		for (TicketRenwoyou ticketRenwoyou : ticketRenwoyouList) {
			Map<String, List> scenicTicket = scenicTicketMap.get(ticketRenwoyou.getScenicId());
			List list = scenicTicket.get(NAME_RENWOYOU);
			list.add(ticketRenwoyou);
		}
		// todo: 补充其他类型票务信息
		return scenicTicketMap;
	}

	/**
	 * 按照行程编号获取有门票的景点id列表
	 *
	 * @param planId
	 * @return scenicIdList
	 */
	public Map<String, Object> listScenicIdList(Long planId) {
		List<PlanTrip> planTripList = planTripService.list(Collections.<String, Object> singletonMap("planId", planId));
		List<Long> resultScenicIdList = new ArrayList<Long>();
		for (PlanTrip planTrip : planTripList) {
			if (planTrip.getTripType() != TripType.SCENIC.value()) {
				continue;
			}
			if (isScenicHasTicket(planTrip.getScenicId())) {
				resultScenicIdList.add(planTrip.getScenicId());
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("scenicIds", resultScenicIdList);
		// todo: 补充其他类型票务信息
		return result;
	}

	public boolean isScenicHasTicket(Long scenicId) {
		List<Long> idList = Lists.newArrayList();
		// 处理子景点门票
		scenicService.prepareScenic(idList, scenicId);
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("scenicIds", idList);

		int rc = ticketRenwoyouService.count(paramMap);
		if (rc > 0) {
			return true;
		}
		return qunarTicketService.hasTicket(paramMap, scenicId);
	}

	public Map<String, Object> listOrdered(Long planId) {
		List<ScenicTicketOrder> orderList = scenicTicketOrderService.listWithDetail(Collections.<String, Object> singletonMap("planId",
				planId));
		Map<String, Object> result = new HashMap<String, Object>();
		List<Long> scenicList = new ArrayList<Long>();
		result.put("planFlag", false);
		for (ScenicTicketOrder scenicTicketOrder : orderList) {
			if (scenicTicketOrder.isPlanFlag()) {
				result.put("planFlag", true);
			}
			for (ScenicTicketSubOrder scenicTicketSubOrder : scenicTicketOrder.getSubOrders()) {
				if (scenicList.contains(scenicTicketSubOrder.getScenicId())) {
					continue;
				}
				scenicList.add(scenicTicketSubOrder.getScenicId());
			}
		}
		result.put("orderedScenicIds", scenicList);
		return result;
	}

	/**
	 * 通过行程id查询推荐套票列表
	 * 
	 * @param planId
	 * @return
	 */
	public Map<Integer, List<List<ScenicRecommendTicket>>> listSeasonTicketByPlan(Long planId) {
		// 查询行程下的景点信息，根据天将景点分组
		List<PlanTrip> planTrips = planTripService.list(Collections.<String, Object> singletonMap("planId", planId));
		Multimap<Long, Long> dayMap = ArrayListMultimap.create();
		for (PlanTrip trip : planTrips) {
			if (trip.getTripType() == TripType.SCENIC.value()) {
				dayMap.put(trip.getPlanDaysId(), trip.getScenicId());
			}
		}

		// 处理行程中每一天的数据
		List<PlanDay> planDays = planDaysService.list(Collections.<String, Object> singletonMap("planId", planId));
		// 定义一个存放每天数据的结构
		Map<Integer, List<List<ScenicRecommendTicket>>> planSeasonResult = Maps.newHashMap();
		for (PlanDay day : planDays) {
			List<Long> scenicCollection = (List<Long>) dayMap.get(day.getId());
			List<QunarTicket> qunarTickets = qunarTicketService.listSeasonTicket(scenicCollection);

			// 查询到相关的套票列表
			if (qunarTickets != null) {
				// 定义一个存放匹配到套票列表的数据结构
				List<List<ScenicRecommendTicket>> tList = Lists.newArrayList();
				// 匹配最适合的套票
				for (QunarTicket t : qunarTickets) {
					Set<Long> seasonContain = useSeason(scenicCollection, t);
					if (seasonContain.size() > 0) {
						// 匹配到的景点需要移除，避免重复匹配
						scenicCollection.removeAll(seasonContain);

						// 生成匹配的套票的数据
						List<ScenicRecommendTicket> stGroup = Lists.newArrayList();
						for (Long sid : seasonContain) {
							ScenicRecommendTicket srt = new ScenicRecommendTicket();
							srt.setScenicId(sid);
							srt.setScenicIds(t.getScenicIdList());
							srt.setSeasonTicketName(t.getProductName());
							srt.setSeasoTicketId(t.getId());
							srt.setSeasonTicketPrice(t.getPrice().getSalePrice());
							srt.setSeasonTicketMarketPrice(t.getPrice().getMarketPrice());

							stGroup.add(srt);
						}
						tList.add(stGroup);
						planSeasonResult.put(day.getDays(), tList);
					}
				}
			}
		}

		return planSeasonResult;
	}

	// 判断是否使用套票，返回在景点列表且在套票中的景点列表，若列表为空表示不使用套票
	public Set<Long> useSeason(List<Long> scenicList, QunarTicket qunarTicket) {
		// 套票中的所有景点都被包含在景点列表中
		if (scenicList.containsAll(qunarTicket.getScenicIdList())) {
			return qunarTicket.getScenicIdList();
		}

		// 包含在套票中的单票票价
		int ticketPrice = 0;
		// 套票价格
		double seasonPrice = qunarTicket.getPrice().getSalePrice();
		// 是否有景点没有最低价和参考价
		boolean flag = false;
		// 在景点列表且在套票中的景点列表
		Set<Long> seasonContain = new HashSet<Long>();
		// 套票包含的景点列表
		Set<Long> qunarTicketScenicIds = qunarTicket.getScenicIdList();
		// 遍历景点列表
		for (long scenicId : scenicList) {
			// 该景点在套票中
			if (!qunarTicketScenicIds.contains(scenicId)) {
				continue;
			}

			seasonContain.add(scenicId);
			// 有景点没有最低价和参考价或者当前景点价格已经超过套票价格
			if (flag || ticketPrice >= seasonPrice) {
				continue;
			}
			Map<String, Object> scenicInfoMap = (Map<String, Object>) scenicService.info(scenicId);
			if (scenicInfoMap.get("lowestPrice") == null || scenicInfoMap.get("lowestPrice").equals(0)) {
				if (scenicInfoMap.get("price") == null || scenicInfoMap.get("price").equals(0)) {
					flag = true;
				} else {
					ticketPrice += (Float) scenicInfoMap.get("price");
				}
				continue;
			}
			ticketPrice += (Float) scenicInfoMap.get("lowestPrice");
		}
		// 有景点在套票中且没有价格数据或者单张票价综合大于套票价格
		if (flag || ticketPrice >= seasonPrice) {
			return seasonContain;
		} else {
			return new HashSet<Long>();
		}
	}

	// 根据门票id和类型查询是否有价格数据
	public ActionResult priceNum(Map<String, Object> paramMap) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		long ticketId = Long.parseLong((String) paramMap.get("ticketId"));
		int type = Integer.parseInt((String) paramMap.get("type"));
		if (type == TYPE_RENWOYOU) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", ticketId);
			return ticketRenwoyouService.countAsResult(params);
		} else if (type == TYPE_QUNAR) {
			QunarTicket qunarTicket = qunarTicketService.info(ticketId);
			if (qunarTicket == null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", -1);
				return qunarPriceService.countAsResult(params);
			} else {
				String productId = qunarTicket.getProductId();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productId", productId);
				return qunarPriceService.countAsResult(params);
			}
		}
		return null;
	}

	// 查询门票库存
	public ActionResult availableCount(Map<String, Object> paramMap) {

		int count = 0;
		// 本地门票id
		long id = Long.parseLong(paramMap.get("ticketId").toString());
		// 门票类型
		int type = Integer.parseInt(paramMap.get("type").toString());
		// 使用日期
		String date = paramMap.get("useDate").toString();
		if (type == TYPE_RENWOYOU) {
			TicketRenwoyou renwoyou = ticketRenwoyouService.info(id);
			String ticketId = renwoyou.getOuterTicketId() + "";
			count = renwoyouService.availableCount(ticketId, date);
		} else if (type == TYPE_QUNAR) {
			QunarTicket qunar = qunarTicketService.info(id);
			String ticketId = qunar.getProductId();
			count = qunarTicketService.availableCount(ticketId, date);
		}

		ActionResult result = new ActionResult();
		ResultList<Object> resultList = new ResultList<Object>();
		resultList.setCounts(count);
		result.setResultList(resultList);

		return result;
	}
}
