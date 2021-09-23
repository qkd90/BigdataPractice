package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;
import com.data.data.hmly.service.entity.SysUser;
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

@Service
public class CruiseShipRoomService {
    @Resource
    private CruiseShipRoomDao cruiseShipRoomDao;

    /**
     * 列表查询
     */
    public List<CruiseShipRoom> listCruiseShipRooms(CruiseShipRoom cruiseShipRoom, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        return cruiseShipRoomDao.listCruiseShipRooms(cruiseShipRoom, sysUser, isSiteAdmin, isSupperAdmin);
    }

    /**
     * 查询
     * @param id
     * @return
     */
    public CruiseShipRoom findById(Long id) {
        return cruiseShipRoomDao.load(id);
    }

    public CruiseShipRoom findFullById(Long id) {
        Criteria<CruiseShipRoom> criteria = new Criteria<CruiseShipRoom>(CruiseShipRoom.class);
        criteria.createCriteria("cruiseShip");
        criteria.eq("id", id);
        return cruiseShipRoomDao.findUniqueByCriteria(criteria);
    }

    /**
     * 保存或更新
     * @param cruiseShipRoom
     */
    public void saveOrUpdate(CruiseShipRoom cruiseShipRoom) {
        if (cruiseShipRoom.getId() != null) {
            cruiseShipRoomDao.update(cruiseShipRoom);
        } else {
            cruiseShipRoomDao.save(cruiseShipRoom);
        }
    }

    public void delete(Long id) {
        cruiseShipRoomDao.delete(id, CruiseShipRoom.class);
    }

    public List<CruiseShipRoom> listByCruiseShipId(Long shipId) {
        Criteria<CruiseShipRoom> criteria = new Criteria<CruiseShipRoom>(CruiseShipRoom.class);
        criteria.eq("cruiseShip.id", shipId);
//        criteria.orderBy(Order.asc(<''))
        return cruiseShipRoomDao.findByCriteria(criteria);
    }

    public List<CruiseShipRoom> getRoomTypeList(Long dateId, Date date) {

/*
        SELECT
        min(csrd.discountPrice) as discountPrice,
        csr.roomType as roomType
                FROM
        cruise_ship_room csr
        LEFT JOIN cruise_ship_room_date csrd ON csrd.roomId = csr.id
        LEFT JOIN cruise_ship_date csd ON csd.id = csrd.dateId
        WHERE
        csrd.dateId = 241
        GROUP BY csr.roomType;
*/
        Page page = new Page(1, Integer.MAX_VALUE);
        List params = Lists.newArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" min(csrd.discountPrice) as minPrice,").append(" csr.roomType as roomTypeStr");
        sql.append(" FROM cruise_ship_room csr");
        sql.append(" LEFT JOIN cruise_ship_room_date csrd ON csrd.roomId = csr.id")
                .append(" LEFT JOIN cruise_ship_date csd ON csd.id = csrd.dateId");
        sql.append(" WHERE");
        sql.append(" csrd.dateId = ?");
        params.add(dateId);
        if (date != null) {
            sql.append(" AND csd.date > ?");
            params.add(DateUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
        }
        sql.append(" GROUP BY csr.roomType");

        List<Map<String, Object>> mapList = cruiseShipRoomDao.findEntitiesBySQL4(sql.toString(), page, params.toArray());

        List<CruiseShipRoom> cruiseShipRooms = Lists.transform(mapList, new Function<Map<String, Object>, CruiseShipRoom>() {
            @Override
            public CruiseShipRoom apply(Map<String, Object> map) {
                try {
                    return (CruiseShipRoom) MapUtils.mapToObject(map, CruiseShipRoom.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new CruiseShipRoom();
            }
        });
        return cruiseShipRooms;
    }


}
