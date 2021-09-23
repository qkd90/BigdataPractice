package com.data.spider.service.data;

import com.data.spider.service.dao.DataRecplanDayDao;
import com.data.spider.service.pojo.data.DataRecplanDay;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRecplanDayService {

    @Autowired
    private DataRecplanDayDao dataRecplanDayDao;

    public void saveAll(List<DataRecplanDay> dataRecplanDay) {
        dataRecplanDayDao.save(dataRecplanDay);
    }

    public void save(DataRecplanDay dataRecplanDay) {
        dataRecplanDayDao.save(dataRecplanDay);
    }

    public void update(DataRecplanDay dataRecplanDay) {
        dataRecplanDayDao.update(dataRecplanDay);
    }

    public List<DataRecplanDay> gets(int size) {
        Page page = new Page(1, size);
        Criteria<DataRecplanDay> c = new Criteria<DataRecplanDay>(DataRecplanDay.class);
        c.eq("status", -100);
        List<DataRecplanDay> dis = dataRecplanDayDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<DataRecplanDay> gets(int size, Criteria<DataRecplanDay> c) {
        Page page = new Page(1, size);
        List<DataRecplanDay> dis = dataRecplanDayDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
