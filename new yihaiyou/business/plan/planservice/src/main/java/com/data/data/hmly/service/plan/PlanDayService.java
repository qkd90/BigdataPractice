package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.plan.dao.PlanDayDao;
import com.data.data.hmly.service.plan.entity.PlanDay;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanDayService {

	@Resource
	private PlanDayDao planDayDao;

	public PlanDay save(PlanDay planDay) {
		planDayDao.save(planDay);
		return planDay;
	}

    public PlanDay update(PlanDay planDay) {
        planDayDao.update(planDay);
        return planDay;
    }

    public void delete(PlanDay planDay) {
        planDayDao.delete(planDay);
    }

}
