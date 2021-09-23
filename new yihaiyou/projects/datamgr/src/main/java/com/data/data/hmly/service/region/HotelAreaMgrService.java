package com.data.data.hmly.service.region;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.hotel.dao.HotelAreaDao;
import com.data.data.hmly.service.hotel.entity.HotelArea;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zzl on 2015/12/7.
 */
@Service
public class HotelAreaMgrService extends BaseService<HotelArea> {

    @Resource
    private HotelAreaDao hotelAreaDao;

    public void insert(HotelArea scenicArea) {
        hotelAreaDao.save(scenicArea);
    }

    public HotelArea selByScenicId(Long scenicId) {
        Criteria<HotelArea> c = new Criteria<HotelArea>(HotelArea.class);
        c.eq("hotel.id", scenicId);
        return hotelAreaDao.findUniqueByCriteria(c);
    }

    public void deleteByAreaId(Long areaId) {
        String deleteSql = "delete from hotel_area where area_id =?";
        hotelAreaDao.updateBySQL(deleteSql, areaId);
    }

    @Override
    public DataAccess<HotelArea> getDao() {
        return hotelAreaDao;
    }

    @Override
    public Criteria<HotelArea> makeCriteria(Map<String, Object> paramMap, Criteria<HotelArea> c) {
        DetachedCriteria dc_region = c.createCriteria("region", "region");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("areaid") && Long.parseLong(paramMap.get("areaid").toString()) > 0) {
            dc_region.add(Restrictions.eq("id", Long.parseLong(paramMap.get("areaid").toString())));
        }
        if (paramMap.containsKey("hotelId") && Long.parseLong(paramMap.get("hotelId").toString()) > 0) {
//            dc_scenic.add(Restrictions.eq("id", Long.parseLong(paramMap.get("hotelId").toString())));
            c.eq("hotelId", Long.parseLong(paramMap.get("hotelId").toString()));
        }
        if (paramMap.containsKey("areaName") && !"".equals(paramMap.get("areaName"))) {
            c.eq("description", paramMap.get("description").toString());
        }
        if (paramMap.containsKey("ranking") && Integer.parseInt(paramMap.get("ranking").toString()) > 0) {
            c.eq("ranking", Integer.parseInt(paramMap.get("ranking").toString()));
        }
        if (paramMap.containsKey("ids")) {
            String[] ids = (String[]) paramMap.get("ids");
            if (ids.length > 0) {
                c.in("id", ids);
            }
        }
        return c;
    }
}
