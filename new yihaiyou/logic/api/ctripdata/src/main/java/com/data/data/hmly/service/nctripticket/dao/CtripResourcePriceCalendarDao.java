package com.data.data.hmly.service.nctripticket.dao;

import com.data.data.hmly.service.nctripticket.entity.CtripResourcePriceCalendar;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotResource;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/2/2.
 */
@Repository
public class CtripResourcePriceCalendarDao extends DataAccess<CtripResourcePriceCalendar> {

    /**
     * 根据携程门票资源标识和门票产品标识删除，不考虑日期因素，因为资源标识已经改变了，旧数据都清除
     * @param resources
     * @param startDate
     * @param endDate
     */
    public void delByResourceIdAndDay(List<CtripScenicSpotResource> resources, Date startDate, Date endDate) {
        String hql = " delete CtripResourcePriceCalendar where resourceId = ? ";
        for (CtripScenicSpotResource res : resources) {
            updateByHQL(hql.toString(), res.getCtripResourceId());
           // updateByHQL(hql.toString(), res.getCtripResourceId(), res.getProductId());
        }
    }

    /**
     * 携程门票资源如果不存在，则清除对应的价格日历数据
     */
    public void doClearPriceNotResource() {
        String hql = " delete CtripResourcePriceCalendar c "
                + "where not exists (select 1 from CtripScenicSpotResource r where r.id = c.resourceId) ";
        updateByHQL(hql.toString());
    }
}
