package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.DataCityDao;
import com.data.data.hmly.service.scenic.entity.DataCity;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zzl on 2015/12/17.
 */
@Service
public class DataCityService extends BaseService<DataCity> {

    @Resource
    private DataCityDao dataCityDao;

    public DataCity selById(Long id){
        return dataCityDao.get(DataCity.class,id);
    }

    @Override
    public DataAccess<DataCity> getDao() {
        return dataCityDao;
    }
    @Override
    public Criteria<DataCity> makeCriteria(Map<String, Object> paramMap, Criteria<DataCity> c) {
        if(paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0){
            c.eq("id",Long.parseLong(paramMap.get("id").toString()));
        }
        return c;
    }
}
