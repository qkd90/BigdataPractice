package com.data.spider.service.qunar;

import com.data.spider.service.dao.TmpCnCityDao;
import com.data.spider.service.pojo.TmpCnCity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmpCnCityService {
    @Autowired
    private TmpCnCityDao tmpCnCityDao;

    public void update(TmpCnCity dis) {
    	tmpCnCityDao.update(dis);
    }

    public void save(TmpCnCity city){
    	tmpCnCityDao.save(city);
    }
    
    public List<TmpCnCity>  gets(int size) {
        Page page = new Page(1, size);
        Criteria<TmpCnCity> c = new Criteria<TmpCnCity>(TmpCnCity.class);
//        c.eq("dataStatus", -1);
//        c.eq("type", 3);
        List<TmpCnCity> dis = tmpCnCityDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
    public List<TmpCnCity>  gets(int size,Criteria<TmpCnCity> c ) {
        Page page = new Page(1, size);
        List<TmpCnCity> dis = tmpCnCityDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
}
