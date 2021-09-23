package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipProject;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Think on 2017/6/7.
 */
@Repository
public class CruiseShipProjectDao extends DataAccess<CruiseShipProject> {

    /**
     * 列表查询
     */
    /*public List<CruiseShipProject> listCruiseShipProjects(CruiseShipProject cruiseShipProject) {
        Criteria<CruiseShipProject> criteria = new Criteria<CruiseShipProject>(CruiseShipProject.class);
        criteria.createCriteria("cruiseShipProjectClassify");
        // 项目标识
        if (cruiseShipProject.getClassifyId() != null) {
            criteria.eq("cruiseShipProjectClassify.id", cruiseShipProject.getClassifyId());
        }
        if(cruiseShipProject.getParentClassifyId() != null){
            criteria.eq("cruiseShipProjectClassify.cruiseShipProjectClassify.id", cruiseShipProject.getParentClassifyId());
        }
        if(cruiseShipProject.getShipId() != null){
            criteria.eq("cruiseShip.id", cruiseShipProject.getShipId());
        }
        criteria.orderBy("updateTime", "desc");
        return findByCriteria(criteria);
    }*/
}
