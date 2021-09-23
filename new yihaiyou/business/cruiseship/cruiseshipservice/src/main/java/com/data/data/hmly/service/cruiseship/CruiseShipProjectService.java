package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipProjectDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProject;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import com.zuipin.util.MapUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Think on 2017/6/7.
 */

@Service
public class CruiseShipProjectService {
    @Resource
    private CruiseShipProjectDao cruiseShipProjectDao;

    public CruiseShipProject get(Long id) {
        return cruiseShipProjectDao.load(id);
    }

    public void update(CruiseShipProject cruiseShipProject) {
        cruiseShipProjectDao.update(cruiseShipProject);
    }

    public void save(CruiseShipProject cruiseShipProject) {
        cruiseShipProjectDao.save(cruiseShipProject);
    }

    public void delete(Long id) {
        cruiseShipProjectDao.delete(id, CruiseShipProject.class);
    }

    public List<CruiseShipProject> listById(Long shipId,Long classifyId) {
        Criteria<CruiseShipProject> criteria = new Criteria<CruiseShipProject>(CruiseShipProject.class);
        criteria.eq("cruiseShip.id", shipId);
        criteria.eq("showStatus",true);
        criteria.createCriteria("cruiseShipProjectClassify");
        criteria.eq("cruiseShipProjectClassify.cruiseShipProjectClassify.id", classifyId);
        criteria.orderBy(Order.asc("level"));
        return cruiseShipProjectDao.findByCriteria(criteria);
    }
    public List<CruiseShipProject> queryById(Long shipId,Long classifyId) {
        Criteria<CruiseShipProject> criteria = new Criteria<CruiseShipProject>(CruiseShipProject.class);
        criteria.eq("cruiseShip.id", shipId);
        criteria.createCriteria("cruiseShipProjectClassify");
        criteria.eq("cruiseShipProjectClassify.id", classifyId);
        return cruiseShipProjectDao.findByCriteria(criteria);
    }
}