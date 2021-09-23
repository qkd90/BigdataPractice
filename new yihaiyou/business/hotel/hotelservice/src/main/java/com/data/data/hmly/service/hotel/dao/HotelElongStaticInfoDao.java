package com.data.data.hmly.service.hotel.dao;

import com.data.data.hmly.service.hotel.entity.HotelElongStaticInfo;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/10/28.
 */
@Repository
public class HotelElongStaticInfoDao extends DataAccess<HotelElongStaticInfo> {

    /**
     * 查询区域酒店的艺龙标识
     */
    public Map<String, String> findElongHotelIdList(Integer cityId) {
        Criteria<HotelElongStaticInfo> criteria = new Criteria<HotelElongStaticInfo>(HotelElongStaticInfo.class);
        criteria.ge("cityId", cityId);
        List<HotelElongStaticInfo> list = findByCriteria(criteria);

        Map<String, String> ids = new HashMap<String, String>();
        for (HotelElongStaticInfo info : list) {
            ids.put(String.valueOf(info.getElongHotelId()), info.getLine());
        }
        return ids;
    }
}
