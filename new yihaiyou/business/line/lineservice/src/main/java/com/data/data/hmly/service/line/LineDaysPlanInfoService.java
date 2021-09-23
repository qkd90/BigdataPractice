package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LineDaysPlanInfoDao;
import com.data.data.hmly.service.line.entity.LineDaysPlanInfo;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/6/23.
 */
@Service
public class LineDaysPlanInfoService {


    @Resource
    private LineDaysPlanInfoDao planInfoDao;

    public void delByLineId(Long lineId) {
        String hql = " delete LineDaysPlanInfo where lineId = ? ";
        planInfoDao.updateByHQL(hql, lineId);
    }

    public void saveAll(List<LineDaysPlanInfo> lineDaysPlanInfos) {
        planInfoDao.save(lineDaysPlanInfos);
    }

    public void save(LineDaysPlanInfo lineDaysPlanInfo) {
        planInfoDao.save(lineDaysPlanInfo);
    }

    public List<LineDaysPlanInfo> getLinePlanDaysInfosByTimeId(Long timeId) {

        Criteria<LineDaysPlanInfo> criteria = new Criteria<LineDaysPlanInfo>(LineDaysPlanInfo.class);
        if (timeId != null) {
            criteria.eq("linedaysplan.id", timeId);
        }
        return planInfoDao.findByCriteria(criteria);
    }

    public List<LineDaysPlanInfo> listByLineDaysPlanId(Long lineDaysPlanId) {
        Criteria<LineDaysPlanInfo> criteria = new Criteria<LineDaysPlanInfo>(LineDaysPlanInfo.class);
        criteria.eq("linedaysplan.id", lineDaysPlanId);
        return planInfoDao.findByCriteria(criteria);
    }
}
