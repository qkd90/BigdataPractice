package com.data.spider.service.tb;

import com.data.spider.service.dao.TbDelicacyResDao;
import com.data.spider.service.pojo.tb.TbDelicacyRes;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelicacyResService {

    @Autowired
    private TbDelicacyResDao tbDelicacyResDao;

    public void saveAll(List<TbDelicacyRes> res) {
        tbDelicacyResDao.save(res);
    }

    public void save(TbDelicacyRes res) {
        tbDelicacyResDao.save(res);
    }

    public void update(TbDelicacyRes res) {
        tbDelicacyResDao.update(res);
    }


    public List<TbDelicacyRes> gets(int size, Criteria<TbDelicacyRes> c) {
        Page page = new Page(1, size);
        List<TbDelicacyRes> dis = tbDelicacyResDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
