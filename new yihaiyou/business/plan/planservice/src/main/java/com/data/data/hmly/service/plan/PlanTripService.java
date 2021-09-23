package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.plan.dao.PlanTripDao;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/12/8.
 */
@Service
public class PlanTripService {

	@Resource
	private PlanTripDao planTripDao;

	public PlanTrip save(PlanTrip planTrip) {
		planTripDao.save(planTrip);
		return planTrip;
	}

    public PlanTrip update(PlanTrip planTrip) {
        planTripDao.update(planTrip);
        return planTrip;
    }

    public void delete(PlanTrip planTrip) {
        planTripDao.delete(planTrip);
    }

}
