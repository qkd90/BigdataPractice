package com.data.data.hmly.service.region;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.ScenicAreaDao;
import com.data.data.hmly.service.scenic.entity.ScenicArea;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/7.
 */
@Service
public class ScenicAreaMgrService extends BaseService<ScenicArea> {

    @Resource
    private ScenicAreaDao scenicAreaDao;

    public void insert(ScenicArea scenicArea) {
        scenicAreaDao.save(scenicArea);
    }

    public ScenicArea selByScenicId(Long scenicId) {
        Criteria<ScenicArea> c = new Criteria<ScenicArea>(ScenicArea.class);
        c.eq("scenicInfo.id", scenicId);
        return scenicAreaDao.findUniqueByCriteria(c);
    }

    public void deleteByAreaId(Long areaId) {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("areaid", areaId);
//        Criteria<ScenicArea> c = new Criteria<ScenicArea>(ScenicArea.class);
//        c = makeCriteria(paramMap, c);
//        List<ScenicArea> scenicAreas = scenicAreaDao.findByCriteria(c);
//        scenicAreaDao.deleteAll(scenicAreas);
        String deleteSql = "delete from scenic_area where area_id =?";
        scenicAreaDao.updateBySQL(deleteSql, areaId);
    }

    @Override
    public DataAccess<ScenicArea> getDao() {
        return scenicAreaDao;
    }

    @Override
    public Criteria<ScenicArea> makeCriteria(Map<String, Object> paramMap, Criteria<ScenicArea> c) {
        DetachedCriteria dc_region = c.createCriteria("region", "region");
        DetachedCriteria dc_scenic = c.createCriteria("scenicInfo", "s");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("areaid") && Long.parseLong(paramMap.get("areaid").toString()) > 0) {
            dc_region.add(Restrictions.eq("id", Long.parseLong(paramMap.get("areaid").toString())));
        }
        if (paramMap.containsKey("scenicId") && Long.parseLong(paramMap.get("scenicId").toString()) > 0) {
            dc_scenic.add(Restrictions.eq("id", Long.parseLong(paramMap.get("scenicId").toString())));
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
