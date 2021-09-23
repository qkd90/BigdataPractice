package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LinedaysplanDao;
import com.data.data.hmly.service.line.entity.Linedaysplan;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/6/23.
 */
@Service
public class LinedaysplanService {

    @Resource
    private LinedaysplanDao linedaysplanDao;

    public void save(Linedaysplan linedaysplan) {
        linedaysplanDao.save(linedaysplan);
    }

    public List<Linedaysplan> getLinePlanDaysBydayId(Long dayId) {
        Criteria<Linedaysplan> criteria = new Criteria<Linedaysplan>(Linedaysplan.class);
        if (dayId != null) {
            criteria.eq("linedays.id", dayId);
        }
        return linedaysplanDao.findByCriteria(criteria);
    }
}
