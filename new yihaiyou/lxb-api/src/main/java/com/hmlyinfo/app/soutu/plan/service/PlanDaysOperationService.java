package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现行程天的操作
 */
@Service
public class PlanDaysOperationService {

	@Autowired
	private PlanService planService;
	@Autowired
	private PlanDaysService planDaysService;
	@Autowired
	private PlanTripService planTripService;
	@Autowired
	private PlanOperationService planOperationService;

	/**
	 * 删除某一天
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:第若干天{days}</li>
	 * </ul>
	 *
	 * @return
	 */
	@Transactional
	public boolean delDays(Map<String, Object> paramMap) {
		// 校验用户是否正确
		Plan plan = planService.info(Long.valueOf(paramMap.get("planId").toString()));
		Validate.dataAuthorityCheck(plan);

		List<PlanDay> planDaysList = planDaysService.list(paramMap);
		Validate.isTrue(planDaysList.size() > 0, ErrorCode.ERROR_53002);

		PlanDay planDay = planDaysList.get(0);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("planId", planDay.getPlanId());
		// 更新行程天顺序
		List<PlanDay> dayList = planDaysService.list(resultMap);
		for (PlanDay oldDay : dayList) {
			if (oldDay.getDays() > planDay.getDays()) {
				oldDay.setDays(oldDay.getDays() - 1);
				planDaysService.update(oldDay);
			}
		}
		// 删除当天所有行程
		planTripService.deleteByDay(planDay.getId());
		planDaysService.del(planDay.getId().toString());

		// 更新行程的统计信息
		planOperationService.updatePlanSTATS(planDay.getPlanId() + "");

		return true;
	}
}
