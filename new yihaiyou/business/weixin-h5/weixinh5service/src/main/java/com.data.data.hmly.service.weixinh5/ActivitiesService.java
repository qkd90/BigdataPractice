package com.data.data.hmly.service.weixinh5;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.weixinh5.dao.ActivitiesDao;
import com.data.data.hmly.service.weixinh5.entity.Activities;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/2/16.
 */
@Service
public class ActivitiesService {

    @Resource
    private ActivitiesDao activitiesDao;

    public List<Activities> findActivitiesList(Activities activities, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        return activitiesDao.findActivitiesList(activities, page, sysUser, isSiteAdmin, isSupperAdmin);
    }

    public void insertTempNew(Activities activities) {
        activities.setCreateTime(new Date());
        activitiesDao.save(activities);
    }

    public Activities load(Long id) {
        return activitiesDao.load(id);
    }

    public Long getLastId() {


        String hql = "select * from activities l where 1 ORDER BY l.id desc LIMIT 1";

        Criteria<Activities> criteria = new Criteria<Activities>(Activities.class);

        criteria.orderBy("id", "desc");

        List<Activities> activitiesList = activitiesDao.findByCriteria(criteria);
        if (activitiesList.isEmpty()) {
            return 1L;
        }
        return activitiesList.get(0).getId() + 1;

    }

    public void delete(Activities activities) {
        activitiesDao.delete(activities);
    }

    public void update(Activities activities) {
        activities.setUpdateTime(new Date());
        activitiesDao.update(activities);
    }

}
