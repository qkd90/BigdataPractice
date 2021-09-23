package com.data.spider.service.tb;

import com.data.spider.service.dao.CtripScenicsDao;
import com.data.spider.service.pojo.CtripScenics;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CtripScenicsService {

    @Autowired
    private CtripScenicsDao CtripScenicsDao;

    public void saveAll(List<CtripScenics> scenics) {
        CtripScenicsDao.save(scenics);
    }

    public void save(CtripScenics scenic) {
        CtripScenicsDao.save(scenic);
    }

    public void update(CtripScenics scenics) {
        CtripScenicsDao.update(scenics);
    }


    public List<CtripScenics> gets(int size, Criteria<CtripScenics> c) {
        Page page = new Page(1, size);
        List<CtripScenics> dis = CtripScenicsDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
