package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipProjectClassify;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Think on 2017/6/7.
 */
@Repository
public class CruiseShipProjectClassifyDao extends DataAccess<CruiseShipProjectClassify> {

    /**
     * 列表查询
     */
    public List<CruiseShipProjectClassify> listCruiseShipProjectClassifys(CruiseShipProjectClassify cruiseShipProjectClassify) {
        Criteria<CruiseShipProjectClassify> criteria = new Criteria<CruiseShipProjectClassify>(CruiseShipProjectClassify.class);
        // 分类标识
        if (cruiseShipProjectClassify.getId() != null) {
            criteria.eq("id", cruiseShipProjectClassify.getId());
        }
        criteria.orderBy("updateTime", "desc");
        return findByCriteria(criteria);
    }
}
