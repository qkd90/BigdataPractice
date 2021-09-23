package com.data.spider.service.data;

import com.data.spider.service.dao.DataRecplanTagDao;
import com.data.spider.service.pojo.data.DataRecplanTag;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRecplanTagService {

    @Autowired
    private DataRecplanTagDao dataRecplanTagDao;

    public void saveAll(List<DataRecplanTag> dataRecplanTag) {
        dataRecplanTagDao.save(dataRecplanTag);
    }

    public void save(DataRecplanTag dataRecplanTag) {
        dataRecplanTagDao.save(dataRecplanTag);
    }

    public void update(DataRecplanTag dataRecplanTag) {
        dataRecplanTagDao.update(dataRecplanTag);
    }

    public List<DataRecplanTag> gets(int size) {
        Page page = new Page(1, size);
        Criteria<DataRecplanTag> c = new Criteria<DataRecplanTag>(DataRecplanTag.class);
        c.eq("status", -100);
        List<DataRecplanTag> dis = dataRecplanTagDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<DataRecplanTag> gets(int size, Criteria<DataRecplanTag> c) {
        Page page = new Page(1, size);
        List<DataRecplanTag> dis = dataRecplanTagDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
