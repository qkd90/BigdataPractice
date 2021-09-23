package com.data.spider.service.data;

import com.data.spider.service.dao.DataRecplanTripDao;
import com.data.spider.service.pojo.data.DataRecplanTrip;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRecplanTripService {

    @Autowired
    private DataRecplanTripDao dataRecplanTripDao;

    public void saveAll(List<DataRecplanTrip> dataRecplanTrip) {
        dataRecplanTripDao.save(dataRecplanTrip);
    }

    public void save(DataRecplanTrip dataRecplanTrip) {
        dataRecplanTripDao.save(dataRecplanTrip);
    }

    public void update(DataRecplanTrip dataRecplanTrip) {
        dataRecplanTripDao.update(dataRecplanTrip);
    }

    public List<DataRecplanTrip> gets(int size) {
        Page page = new Page(1, size);
        Criteria<DataRecplanTrip> c = new Criteria<DataRecplanTrip>(DataRecplanTrip.class);
        c.eq("status", -100);
        List<DataRecplanTrip> dis = dataRecplanTripDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<DataRecplanTrip> gets(int size, Criteria<DataRecplanTrip> c) {
        Page page = new Page(1, size);
        List<DataRecplanTrip> dis = dataRecplanTripDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
