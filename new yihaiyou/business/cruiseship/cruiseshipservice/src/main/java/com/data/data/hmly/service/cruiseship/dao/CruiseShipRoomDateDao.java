package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CruiseShipRoomDateDao extends DataAccess<CruiseShipRoomDate> {

    public void save(CruiseShipRoomDate cruiseShipRoomDate) {
        super.save(cruiseShipRoomDate);
    }

    public void saveAll(List<CruiseShipRoomDate> cruiseShipRoomDateList) {
        super.save(cruiseShipRoomDateList);
    }

    public void update(CruiseShipRoomDate cruiseShipRoomDate) {
        super.update(cruiseShipRoomDate);
    }

    public void updateAll(List<CruiseShipRoomDate> cruiseShipRoomDateList) {
        super.updateAll(cruiseShipRoomDateList);
    }

    /**
     * 列表查询
     */
    public List<CruiseShipRoomDate> listCruiseShipRoomDates(CruiseShipRoomDate cruiseShipRoomDate) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        // 邮轮标识
        if (cruiseShipRoomDate.getDateId() != null) {
            criteria.eq("cruiseShipDate.id", cruiseShipRoomDate.getDateId());
        }
//        criteria.orderBy("updateTime", "desc");
        return findByCriteria(criteria);
    }

    /**
     * 列表查询
     */
    public List<CruiseShipRoomDate> list(Long dateId) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new CruiseShipRoomDate(rm.id, r.id, r.name, r.roomType, rm.discountPrice, rm.salePrice, rm.marketPrice) ");
        hql.append("from CruiseShipRoomDate rm inner join rm.cruiseShipRoom r where 1 = 1 ");
        hql.append("and rm.cruiseShipDate.id = :dateId ");
        hql.append("order by roomType asc ");
        params.put("dateId", dateId);
        return findByHQL2ForNew(hql.toString(), params);
    }

    /**
     * 删除价格信息
     */
    public void delByDateId(Long dateId) {
        String hql = " delete CruiseShipRoomDate where cruiseShipDate.id = ? ";
        updateByHQL(hql, dateId);
    }

    /**
     * 查询最小报价
     */
    public Float findMinValue(Long dateId, Date dateStart, Date dateEnd, String prop) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        criteria.eq("cruiseShipDate.id", dateId);
        if (dateStart != null) {
            criteria.ge("date", dateStart);
        }
        if (dateEnd != null) {
            criteria.le("date", dateEnd);
        }
        criteria.setProjection(Projections.min(prop));
        Float minValue = (Float) findUniqueValue(criteria);
        return minValue == null ? 0f : minValue;
    }

}
