package com.data.spider.service.data;

import com.data.spider.service.dao.DataCityDao;
import com.data.spider.service.pojo.data.DataCity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataCityService {
    @Autowired
    private DataCityDao dataCityDao;

    public void saveAll(List<DataCity> dataCity) {
        dataCityDao.save(dataCity);
    }

    public void save(DataCity dataCity) {
        dataCityDao.save(dataCity);
    }

    public void update(DataCity dataCity) {
        dataCityDao.update(dataCity);
    }

    public List<DataCity> gets(int size) {
        Page page = new Page(1, size);
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
        c.eq("status", -100);
        List<DataCity> dis = dataCityDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<DataCity> gets(int size, Criteria<DataCity> c) {
        Page page = new Page(1, size);
        List<DataCity> dis = dataCityDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
