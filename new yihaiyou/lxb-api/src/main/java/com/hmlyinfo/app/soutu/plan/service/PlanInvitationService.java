package com.hmlyinfo.app.soutu.plan.service;

import com.google.common.collect.Lists;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.service.InvitationService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.persistent.PageDto;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现相关行程的功能
 */
@Service
public class PlanInvitationService {

	@Autowired
	private PlanService planService;
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ScenicInfoService scenicInfoService;


	/**
	 * 根据planId获取相关行程列表
	 *
	 * @param planId
	 * @return
	 */
	public List<Map<String, Object>> involvedPlan(String planId) {

		Plan pl = planService.info(Long.valueOf(planId));
		List<Map<String, Object>> planList = new ArrayList<Map<String, Object>>();
		String cityIds = pl.getCityIds();
		if (StringUtil.isEmpty(cityIds)) {
			return planList;
		}
		String[] cityId = cityIds.split(",");
		Map<String, Map<String, Object>> singlePlanMap = new HashMap<String, Map<String, Object>>();
		for (String singleCityId : cityId) {
			Map<String, Object> cityMap = new HashMap<String, Object>();
			cityMap.put("singleCityId", singleCityId);
			List<Map<String, Object>> sinPlanList = planService.listIds(cityMap);
			if (sinPlanList.isEmpty()) {
				continue;
			}
			for (Map<String, Object> map : sinPlanList) {
				singlePlanMap.put(map.get("id").toString(), map);
			}
		}
		for (Map<String, Object> map : singlePlanMap.values()) {
			planList.add(map);
		}
		Collections.sort(planList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> p1, Map<String, Object> p2) {
				return ((Date) p1.get("createTime")).compareTo((Date) p2.get("createTime"));
			}
		});

		return planList;
	}

	/**
	 * 获取相关结伴帖id列表
	 *
	 * @param paramMap planId：行程id
	 * @return
	 */
	public List<Invitation> involvedInvitation(Map<String, Object> paramMap) {

		String planId = (String) paramMap.get("planId");
		List<Map<String, Object>> planList = this.involvedPlan(planId);
		if (planList.isEmpty()) {
			return null;
		}
		List planIdList = ListUtil.getIdList(planList, "id");
		paramMap.remove("planId");
		paramMap.remove("pageDto");
		paramMap.put("planIds", planIdList);
		List<Invitation> ivList = invitationService.listColumns(paramMap, Lists.newArrayList("id", "create_time"));

		// if (ivList.isEmpty()) {
		// return ivList;
		// }
		// todo: 为什么这里要排序
		// Collections.sort(ivList, new Comparator<Invitation>() {
		// @Override
		// public int compare(Invitation iv1, Invitation iv2) {
		// Date t1 = iv1.getCreateTime();
		// Date t2 = iv2.getCreateTime();
		// if (t1 != null && t2 != null)
		// return t1.compareTo(t2);
		// else {
		// return 0;
		// }
		// }
		// });
		return ivList;
	}

	/**
	 * 获取有交集用户id列表
	 *
	 * @param planId
	 * @return
	 */
	public List<String> involvedUser(String planId) {
		List<String> userList = new ArrayList<String>();
		List<Map<String, Object>> planList = this.involvedPlan(planId);
		for (int i = 0; i < planList.size(); i++) {
			Map<String, Object> pl = planList.get(i);
			if (!userList.contains(pl.get("userId") + ""))
				userList.add(pl.get("userId") + "");
		}
		return userList;
	}

	// 获取相关结伴帖的数量
	public int invitationCount(Map<String, Object> paramMap) {
		List<Invitation> ivList = new ArrayList<Invitation>();
		if (paramMap.get("planId") != null && !(paramMap.get("planId").equals(""))) {
			ivList = involvedInvitation(paramMap);
		} else {
			ivList = involvedInvitationByScenic(paramMap);
		}

		if (ivList == null)
			return 0;
		return ivList.size();
	}

	// 获取相关用户的数量
	public int userCount(Map<String, Object> paramMap) {
		List<String> uidList = new ArrayList<String>();
		if (paramMap.get("planId") != null && !(paramMap.get("planId").equals(""))) {
			String planId = (String) paramMap.get("planId");
			uidList = involvedUser(planId);
		} else {
			uidList = involvedUserByScenic(paramMap);
		}

		if (uidList == null)
			return 0;
		return uidList.size();
	}

	// 获取相关结伴帖的详情
	public List<Map<String, Object>> involvedInvitationDetail(Map<String, Object> paramMap) {
		PageDto pageDto = (PageDto) paramMap.get("pageDto");
		List<Invitation> ivList = new ArrayList<Invitation>();
		if (paramMap.get("planId") != null && !(paramMap.get("planId").equals(""))) {
			ivList = involvedInvitation(paramMap);
		} else {
			ivList = involvedInvitationByScenic(paramMap);
		}
		Map<String, Object> idsMap = new HashMap<String, Object>();
		if (ivList == null || ivList.size() == 0) {
			idsMap.put("id", -1);
		}
		List ids = ListUtil.getIdList(ivList, "id");
		idsMap.put("ids", ids);
		idsMap.put("authorSex", paramMap.get("authorSex"));
		idsMap.put("citys", paramMap.get("citys"));
		idsMap.put("pageDto", pageDto);
		return invitationService.listDetail(idsMap);
	}

	// 获取相关用户的详情
	public List<Map<String, Object>> involvedUserDetail(Map<String, Object> paramMap) {
		List<String> uidList = new ArrayList<String>();
		if (paramMap.get("planId") != null && !(paramMap.get("planId").equals(""))) {
			String planId = (String) paramMap.get("planId");
			uidList = involvedUser(planId);
		} else {
			uidList = involvedUserByScenic(paramMap);
		}

		if (uidList == null)
			return null;
		Map<String, Object> uidMap = new HashMap<String, Object>();
		uidMap.put("ids", uidList);
		uidMap.put("pageDto", paramMap.get("pageDto"));

		List<User> userList = userService.list(uidMap);

		if (paramMap.get("lng") == null || paramMap.get("lat") == null) {
			List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
			for (User user : userList) {
				Map<String, Object> disMap = new HashMap<String, Object>();
				disMap.put("uid", user.getId());
				disMap.put("dis", -1);
				res.add(disMap);
			}
			return ListUtil.listJoin(userList, res, "id=uid", "author", null);
		}

		// 算出用户列表中每个人和当前位置的距离，得到距离列表
		// 地球半径
		double lng = Double.parseDouble((String) paramMap.get("lng"));
		double lat = Double.parseDouble((String) paramMap.get("lat"));
		double EARTH_RADIUS = 6371393;
		List<Map<String, Object>> disList = new ArrayList<Map<String, Object>>();
		for (User user : userList) {
			Map<String, Object> disMap = new HashMap<String, Object>();
			disMap.put("uid", user.getId());
			if (user.getLat() == 0f || user.getLng() == 0f) {
				disMap.put("dis", -1);
				disList.add(disMap);
				continue;
			}
			// 计算用户所在位置和当前位置的距离
			double radLat1 = lat * Math.PI / 180.0;
			double radLat2 = user.getLat() * Math.PI / 180.0;

			double radLon1 = lng * Math.PI / 180.0;
			double radLon2 = user.getLng() * Math.PI / 180.0;

			double a = radLat1 - radLat2;
			double b = radLon1 - radLon2;
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;
			disMap.put("dis", s);
			disList.add(disMap);
		}

		// 合并用户列表和距离列表
		List<Map<String, Object>> resList = ListUtil.listJoin(userList, disList, "id=uid", "author", null);

		return resList;
	}

	// 根据景点id查询相关结伴帖和有交集的人信息

	// 根据scenicId查询相关行程列表
	public List<Map<String, Object>> involvedPlanByScenic(Map<String, Object> paramMap) {

		long scenicId = Long.parseLong((String) paramMap.get("scenicId"));
		Map<String, Object> scenicInfo = (Map<String, Object>) scenicInfoService.info(scenicId);
		String cityIds = (String) scenicInfo.get("cityCode");
		List<Map<String, Object>> planList = new ArrayList<Map<String, Object>>();
		if (StringUtil.isEmpty(cityIds)) {
			return planList;
		}
		String[] cityId = cityIds.split(",");
		Map<String, Map<String, Object>> singlePlanMap = new HashMap<String, Map<String, Object>>();
		for (String singleCityId : cityId) {
			Map<String, Object> cityMap = new HashMap<String, Object>();
			cityMap.put("singleCityId", singleCityId);
			List<Map<String, Object>> sinPlanList = planService.listIds(cityMap);
			if (sinPlanList.isEmpty()) {
				continue;
			}
			for (Map<String, Object> map : sinPlanList) {
				singlePlanMap.put(map.get("id").toString(), map);
			}
		}
		for (Map<String, Object> map : singlePlanMap.values()) {
			planList.add(map);
		}
		Collections.sort(planList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> p1, Map<String, Object> p2) {
				return ((Date) p1.get("createTime")).compareTo((Date) p2.get("createTime"));
			}
		});

		return planList;
	}

	// 根据scenicId查询相关结伴帖列表
	public List<Invitation> involvedInvitationByScenic(Map<String, Object> paramMap) {

		List<Map<String, Object>> planList = this.involvedPlanByScenic(paramMap);
		if (planList.isEmpty()) {
			return null;
		}
		List planIdList = ListUtil.getIdList(planList, "id");
		paramMap.remove("planId");
		paramMap.remove("pageDto");
		paramMap.put("planIds", planIdList);
		List<Invitation> ivList = invitationService.listColumns(paramMap, Lists.newArrayList("id", "create_time"));

		return ivList;
	}

	// 根据scenicId查询有交集用户列表
	public List<String> involvedUserByScenic(Map<String, Object> paramMap) {
		List<String> userList = new ArrayList<String>();
		List<Map<String, Object>> planList = this.involvedPlanByScenic(paramMap);

		for (int i = 0; i < planList.size(); i++) {
			Map<String, Object> pl = planList.get(i);
			if (!userList.contains(pl.get("userId") + ""))
				userList.add(pl.get("userId") + "");
		}
		return userList;
	}
}
