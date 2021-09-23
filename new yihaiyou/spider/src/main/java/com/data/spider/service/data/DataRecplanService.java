package com.data.spider.service.data;

import com.data.spider.service.dao.DataRecplanDao;
import com.data.spider.service.pojo.data.DataRecplan;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRecplanService {

    @Autowired
    private DataRecplanDao dataRecplanDao;

    public void saveAll(List<DataRecplan> dataRecplan) {
        dataRecplanDao.save(dataRecplan);
    }

    public void save(DataRecplan dataRecplan) {
        dataRecplanDao.save(dataRecplan);
    }

    public void update(DataRecplan dataRecplan) {
        dataRecplanDao.update(dataRecplan);
    }

    public List<DataRecplan> gets(int size) {
        Page page = new Page(1, size);
        Criteria<DataRecplan> c = new Criteria<DataRecplan>(DataRecplan.class);
        c.eq("status", -100);
        List<DataRecplan> dis = dataRecplanDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<DataRecplan> gets(int size, Criteria<DataRecplan> c) {
        Page page = new Page(1, size);
        List<DataRecplan> dis = dataRecplanDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
