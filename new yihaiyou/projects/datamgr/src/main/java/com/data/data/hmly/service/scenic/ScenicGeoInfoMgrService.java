package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.ScenicGeoInfoDao;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zzl on 2015/12/14.
 */
@Service
public class ScenicGeoInfoMgrService extends BaseService<ScenicGeoinfo>{

    @Resource
    private ScenicGeoInfoDao scenicGeoInfoDao;

    public ScenicGeoinfo selByScenicId(Long scenicId){
        Criteria<ScenicGeoinfo> c = new Criteria<ScenicGeoinfo>(ScenicGeoinfo.class);
        c.eq("scenicInfo.id",scenicId);
        return scenicGeoInfoDao.findUniqueByCriteria(c);
    }

    @Override
    public DataAccess<ScenicGeoinfo> getDao() {
        return scenicGeoInfoDao;
    }

    @Override
    public Criteria<ScenicGeoinfo> makeCriteria(Map<String, Object> paramMap, Criteria<ScenicGeoinfo> c) {
        if(paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0){
            c.eq("scenicInfo.id",Long.parseLong(paramMap.get("id").toString()));
        }
        return c;
    }
}
