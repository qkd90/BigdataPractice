package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.plan.dao.RecommendPlanDayDao;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2015/12/24.
 */
@Service
public class RecommendPlanDayService {

    @Resource
    private RecommendPlanDayDao recommendPlanDayDao;

    public void save(RecommendPlanDay recommendPlanDay) {
        recommendPlanDayDao.save(recommendPlanDay);
    }

    public void deleteOneDay(Integer day, Long recplanId, Long recommendPlanDayId) {
        String updateSql = "update recommend_plan_day set day = day - 1 where recplan_id =? and day>?";
        recommendPlanDayDao.updateBySQL(updateSql, recplanId, day);
        recommendPlanDayDao.delete(recommendPlanDayId, RecommendPlanDay.class);
    }

    public void updateAfterAddTrip(Long recommendPlanDayId) {
        String sql = "update recommend_plan_day set scenics = scenics + 1 where id=?";
        recommendPlanDayDao.updateBySQL(sql, recommendPlanDayId);
    }

    public void updateAfterDeleteTrip(Long recommendPlanDayId) {
        String updateSql = "update recommend_plan_day set scenics = scenics - 1 where id=?";
        recommendPlanDayDao.updateBySQL(updateSql, recommendPlanDayId);
    }

    public List<RecommendPlanDay> list(Criteria<RecommendPlanDay> criteria) {
        return recommendPlanDayDao.findByCriteria(criteria);
    }

    public List<RecommendPlanDay> getPlanDaysList(RecommendPlanDay recommendPlanDay) {
        Criteria<RecommendPlanDay> criteria = new Criteria<RecommendPlanDay>(RecommendPlanDay.class);
        if (recommendPlanDay != null && recommendPlanDay.getRecommendPlan() != null) {
            criteria.eq("recommendPlan.id", recommendPlanDay.getRecommendPlan().getId());
        }
        return recommendPlanDayDao.findByCriteria(criteria);
    }
}
