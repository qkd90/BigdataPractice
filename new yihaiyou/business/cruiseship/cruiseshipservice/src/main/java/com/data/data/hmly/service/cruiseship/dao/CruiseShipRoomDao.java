package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CruiseShipRoomDao extends DataAccess<CruiseShipRoom> {

    /**
     * 列表查询
     */
    public List<CruiseShipRoom> listCruiseShipRooms(CruiseShipRoom cruiseShipRoom, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        Criteria<CruiseShipRoom> criteria = new Criteria<CruiseShipRoom>(CruiseShipRoom.class);
        // 邮轮标识
        if (cruiseShipRoom.getCruiseShipId() != null) {
            criteria.eq("cruiseShip.id", cruiseShipRoom.getCruiseShipId());
        }
        criteria.orderBy("roomType", "asc");
        criteria.orderBy("updateTime", "desc");
        return findByCriteria(criteria);
    }

}
