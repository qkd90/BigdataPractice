package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.ScenicstatisticsDao;
import com.data.data.hmly.service.scenic.entity.ScenicStatistics;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.util.Date;
import java.util.Map;

/**
 * Created by dy on 2016/8/3.
 */
@Service
public class ScenicStatisticsMgrService extends BaseService<ScenicStatistics> {

    @Resource
    private ScenicstatisticsDao scenicstatisticsDao;



    @Override
    public Criteria<ScenicStatistics> makeCriteria(Map<String, Object> paramMap, Criteria<ScenicStatistics> c) {
        DetachedCriteria dcscenic = c.createCriteria("scenicInfo", "s");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0){
            dcscenic.add(Restrictions.eq("id", Long.parseLong(paramMap.get("id").toString())));
        }
        if (paramMap.containsKey("commentNum") && !"".equals(paramMap.get("commentNum"))) {
            c.eq("commentNum", paramMap.get("commentNum"));
        }

        if (paramMap.containsKey("goingCount") && !"".equals(paramMap.get("goingCount"))) {
            c.eq("goingCount", paramMap.get("goingCount"));
        }

        if (paramMap.containsKey("cameCount") && !"".equals(paramMap.get("cameCount"))) {
            c.eq("cameCount", paramMap.get("cameCount"));
        }

        if (paramMap.containsKey("satisfaction") && !"".equals(paramMap.get("satisfaction"))) {
            c.eq("satisfaction", paramMap.get("satisfaction"));
        }

        if (paramMap.containsKey("orderNum") && !"".equals(paramMap.get("orderNum"))) {
            c.eq("orderNum", paramMap.get("orderNum"));
        }
        return c;
    }

    @Override
    public DataAccess<ScenicStatistics> getDao() {
        return scenicstatisticsDao;
    }
}
