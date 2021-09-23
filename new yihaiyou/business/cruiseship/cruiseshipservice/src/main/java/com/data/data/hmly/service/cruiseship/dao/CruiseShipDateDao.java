package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class CruiseShipDateDao extends DataAccess<CruiseShipDate> {

    public void save(CruiseShipDate CruiseShipDate) {
        super.save(CruiseShipDate);
    }

    public void saveAll(List<CruiseShipDate> cruiseShipDateList) {
        super.save(cruiseShipDateList);
    }

    public void update(CruiseShipDate cruiseShipDate) {
        super.update(cruiseShipDate);
    }

    public void updateAll(List<CruiseShipDate> cruiseShipDateList) {
        super.updateAll(cruiseShipDateList);
    }

    /**
     * 列表查询
     */
    public List<CruiseShipDate> listCruiseShipDates(Long cruiseShipId, Date dateStart, Date dateEnd) {
        Criteria<CruiseShipDate> criteria = new Criteria<CruiseShipDate>(CruiseShipDate.class);
        if (cruiseShipId != null) {
            // 邮轮标识
            criteria.eq("cruiseShip.id", cruiseShipId);
        }

        if (dateStart != null) {
            criteria.ge("date", dateStart);
        }

        if (dateEnd != null) {
            criteria.le("date", dateEnd);
        }
        criteria.orderBy("date", "asc");
        return findByCriteria(criteria);
    }

    /**
     * 删除发团日期
     */
    public void delBy(Long cruiseShipId, Date date) {
        String hql = " delete CruiseShipDate where cruiseShip.id = ? and date = ? ";
        updateByHQL(hql, cruiseShipId, date);
    }
}
