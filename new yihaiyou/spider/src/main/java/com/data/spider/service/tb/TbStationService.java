package com.data.spider.service.tb;

import com.data.spider.service.dao.TbStationDao;
import com.data.spider.service.pojo.TbStation;
import com.data.spider.service.pojo.data.DataCity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbStationService {
    @Autowired
    private TbStationDao tbStationDao;

    public void update(TbStation dis) {
    	tbStationDao.update(dis);
    }

    public List<TbStation>  gets(int size) {
        Page page = new Page(1, size);
        Criteria<TbStation> c = new Criteria<TbStation>(TbStation.class);
//        c.eq("dataStatus", -1);
//        c.eq("type", 3);
        List<TbStation> dis = tbStationDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
    public List<TbStation>  gets(int size,Criteria<TbStation> c ) {
        Page page = new Page(1, size);
        List<TbStation> dis = tbStationDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
    public void save(TbStation tbStation) {
    	tbStationDao.save(tbStation);
    }
}
