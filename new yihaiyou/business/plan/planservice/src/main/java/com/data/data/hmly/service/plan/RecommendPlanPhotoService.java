package com.data.data.hmly.service.plan;

import com.data.data.hmly.service.plan.dao.RecommendPlanPhotoDao;
import com.data.data.hmly.service.plan.entity.RecommendPlanPhoto;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/1/16.
 */
@Service
public class RecommendPlanPhotoService {

    @Resource
    private RecommendPlanPhotoDao recommendPlanPhotoDao;

    public void save(RecommendPlanPhoto recommendPlanPhoto) {
        recommendPlanPhotoDao.save(recommendPlanPhoto);
    }

    public void delete(RecommendPlanPhoto recommendPlanPhoto) {
        recommendPlanPhotoDao.delete(recommendPlanPhoto);
    }

    public void delete(Long id) {
        recommendPlanPhotoDao.delete(id, RecommendPlanPhoto.class);
    }

    public Integer getMaxSort(Long recommendPlanTripId) {
        Integer nowPhotoNum = 0;
        Integer maxSort = 0;
        String sql = "select max(sort) result from recommend_plan_photo where rectrip_id =?";
        String countSql = "select count(id) result from recommend_plan_photo where rectrip_id =?";
        nowPhotoNum = recommendPlanPhotoDao.findIntegerBySQL(countSql, recommendPlanTripId).intValue();
        if (nowPhotoNum == null || nowPhotoNum <= 0) {
            return 0;
        }
        if (recommendPlanPhotoDao.findIntegerBySQL(sql, recommendPlanTripId) == null) {
            return 0;
        } else {
            maxSort = recommendPlanPhotoDao.findIntegerBySQL(sql, recommendPlanTripId).intValue();
        }
        return maxSort;
    }

    public List<RecommendPlanPhoto> getRecommendPlanPhotoListByReplanId(Long recplanId) {
        Criteria<RecommendPlanPhoto> criteria = new Criteria<RecommendPlanPhoto>(RecommendPlanPhoto.class);
        criteria.eq("recommendPlan.id", recplanId);
        return recommendPlanPhotoDao.findByCriteria(criteria);
    }

}
