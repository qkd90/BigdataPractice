package com.data.spider.service.tb;

import com.data.spider.service.dao.TbDelicacyDao;
import com.data.spider.service.pojo.tb.TbDelicacy;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelicacyService {

    @Autowired
    private TbDelicacyDao tbDelicacyDao;

    public void saveAll(List<TbDelicacy> tbDelicacys) {
        tbDelicacyDao.save(tbDelicacys);
    }

    public void save(TbDelicacy tbDelicacy) {
        tbDelicacyDao.save(tbDelicacy);
    }

    public void update(TbDelicacy dis) {
        tbDelicacyDao.update(dis);
    }


    public List<TbDelicacy> gets(int size, Criteria<TbDelicacy> c) {
        Page page = new Page(1, size);
        List<TbDelicacy> dis = tbDelicacyDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
