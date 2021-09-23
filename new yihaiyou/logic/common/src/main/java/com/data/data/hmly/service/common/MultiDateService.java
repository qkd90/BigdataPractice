package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.MultiDateDao;
import com.data.data.hmly.service.common.entity.MultiDate;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zzl on 2016/10/25.
 */
@Service
public class MultiDateService {

    @Resource
    private MultiDateDao multiDateDao;

    public void save(MultiDate multiDate) {
        multiDateDao.save(multiDate);
    }

    public MultiDate getByDate(Date date) {
        Criteria<MultiDate> criteria = new Criteria<MultiDate>(MultiDate.class);
        criteria.eq("date", date);
        return multiDateDao.findUniqueByCriteria(criteria);
    }

    public boolean isHoliday(Date date) {
        Criteria<MultiDate> criteria = new Criteria<MultiDate>(MultiDate.class);
        criteria.eq("date", date);
        MultiDate multiDate = multiDateDao.findUniqueByCriteria(criteria);
        return multiDate.getIsHoliday();
    }

}
