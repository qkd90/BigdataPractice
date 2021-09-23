package com.data.data.hmly.service;

import com.data.data.hmly.enums.AdsOpenType;
import com.data.data.hmly.enums.AdsStatus;
import com.data.data.hmly.service.dao.AdsDao;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/26.
 */

@Service
public class AdsService {

    private static final long DEFAULT_LVXBANG_UNIT_ID = 1;

    @Resource
    private AdsDao adsDao;

    public Ads get(long id) {
        return adsDao.get(id);
    }

	public List<Ads> getByResource(Long resourceMapId) {
		SysResourceMap sysResourceMap = new SysResourceMap();
		sysResourceMap.setId(resourceMapId);
		Ads ads = new Ads();
		ads.setSysResourceMap(sysResourceMap);
		ads.setAdStatus(AdsStatus.UP);
        ads.setForDisplay(true);
		return getAdsList(ads, new Page(0, 6));
	}

	public List<Ads> getAdsList(Ads ads, Page page, Integer... unitId) {
		Criteria<Ads> criteria = new Criteria<Ads>(Ads.class);
        criteria.orderBy("sort", "asc");
		if (ads.getSysResourceMap() != null) {
			criteria.eq("sysResourceMap.id", ads.getSysResourceMap().getId());
		}
		if (ads.getUser() != null) {
			DetachedCriteria userCriteria = criteria.createCriteria("user", "user", JoinType.INNER_JOIN);
			if (ads.getUser().getSysSite() != null) {
				userCriteria.add(Restrictions.eq("sysSite.id", ads.getUser().getSysSite().getId()));
			} else if (ads.getUser() instanceof SysUser) {
				userCriteria.add(Restrictions.eq("sysUnit.id", ((SysUser) ads.getUser()).getSysUnit().getId()));
			}
		}
        if (unitId.length > 0) {
            criteria.in("sysUnit.id", unitId);
        } else {
            criteria.eq("sysUnit.id", DEFAULT_LVXBANG_UNIT_ID);
        }
        if (ads.getAdStatus() != null) {
            criteria.eq("adStatus", ads.getAdStatus());
        }
        if (ads.getForDisplay() != null && ads.getForDisplay()) {
            Date nowDate = new Date();
            criteria.add(Restrictions.or(Restrictions.isNull("startTime"), Restrictions.le("startTime", nowDate)));
            criteria.add(Restrictions.or(Restrictions.isNull("endTime"), Restrictions.ge("endTime", nowDate)));
        }
		return adsDao.findByCriteria(criteria, page);
	}

	public List<Ads> getAdsList(SysUnit companyUnit, Page page, long location, Date sTime, Date eTime, AdsOpenType openType, AdsStatus status, Boolean isSiteAdmin, Boolean isSupperAdmin){
        return adsDao.getList(companyUnit, page, location, sTime, eTime, openType, status, isSiteAdmin, isSupperAdmin);
    }

    public void save(Ads ads) {
        adsDao.save(ads);
    }


    public void delByIds(String ids) {
        List<Ads> adses = adsDao.getByIds(ids);
        for (Ads ads : adses) {
            ads.setAdStatus(AdsStatus.DEL);
        }
        adsDao.updateAll(adses);
    }

    public void doUpByIds(String ids) {
        List<Ads> adses = adsDao.getByIds(ids);
        for (Ads ads : adses) {
            ads.setAdStatus(AdsStatus.UP);
        }
        adsDao.updateAll(adses);
    }

    public void doDownByIds(String ids) {
        List<Ads> adses = adsDao.getByIds(ids);
        for (Ads ads : adses) {
            ads.setAdStatus(AdsStatus.DOWN);
        }
        adsDao.updateAll(adses);
    }

}
