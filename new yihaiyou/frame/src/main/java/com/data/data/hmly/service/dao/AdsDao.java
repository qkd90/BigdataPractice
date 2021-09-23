package com.data.data.hmly.service.dao;

import com.data.data.hmly.enums.AdsOpenType;
import com.data.data.hmly.enums.AdsStatus;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysUnit;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/26.
 */

@Repository
public class AdsDao extends DataAccess<Ads> {

    public Ads get(Long id) {
        Criteria<Ads> criteria = new Criteria<Ads>(Ads.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    public void save(Ads ads) {
        saveOrUpdate(ads, ads.getId());
    }

    public List<Ads> getList(SysUnit companyUnit, Page page, long location, Date sTime, Date eTime, AdsOpenType openType, AdsStatus status, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        Criteria<Ads> criteria = new Criteria<Ads>(Ads.class);
        // 数据过滤
        if (!isSupperAdmin) {
            criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
            criteria.eq("sysUnit.sysSite.id", companyUnit.getSysSite().getId());
            if (!isSiteAdmin) {
                criteria.eq("sysUnit.id", companyUnit.getId());
            }
        }

        criteria.createCriteria("sysResourceMap", "sysResourceMap", JoinType.INNER_JOIN);
        if (location != 0) {
            criteria.eq("sysResourceMap.id", location);
        }
        if (sTime != null) {
            criteria.gt("startTime", sTime);
        }
        if (eTime != null) {
            criteria.lt("endTime", eTime);
        }
        if (openType != null) {
            criteria.eq("openType", openType);
        }
        if (status != null) {
            criteria.eq("adStatus", status);
        } else {
            criteria.ne("adStatus", AdsStatus.DEL);
        }

        return findByCriteria(criteria, page);
    }

    public List<Ads> getByIds(String ids) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : ids.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<Ads> criteria = new Criteria<Ads>(Ads.class);
        criteria.in("id", idList);
        return findByCriteria(criteria);
    }
}
