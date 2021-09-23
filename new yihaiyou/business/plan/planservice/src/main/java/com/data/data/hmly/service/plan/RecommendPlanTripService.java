package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.plan.dao.RecommendPlanTripDao;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2015/12/24.
 */
@Service
public class RecommendPlanTripService {

    @Resource
    private RecommendPlanTripDao recommendPlanTripDao;


    public void save(RecommendPlanTrip recommendPlanTrip) {
        recommendPlanTripDao.save(recommendPlanTrip);
    }

    public void deleteOneTrip(Integer sort, Long recommendPlanDayId, Long recommendPlanTripId) {
        String updateSql = "update recommend_plan_trip set sort = sort - 1 where recday_id =? and sort >?";
        String deleteSql = "delete from recommend_plan_trip where id=?";
        // 删除当天某个行程时候, 重新排序该天
        recommendPlanTripDao.updateBySQL(updateSql, recommendPlanDayId, sort);
        // 删除该行程节点
        recommendPlanTripDao.updateBySQL(deleteSql, recommendPlanTripId);
    }

    public void deleteTripByDay(Long recommendPlanDayId) {
        String deleteSql = "delete from recommend_plan_trip where recday_id=?";
        // 删除该天下的所有节点
        recommendPlanTripDao.updateBySQL(deleteSql, recommendPlanDayId);
    }

    public List<RecommendPlanTrip> list(Criteria<RecommendPlanTrip> criteria) {
        return recommendPlanTripDao.findByCriteria(criteria);
    }
}
