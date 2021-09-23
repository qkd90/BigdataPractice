package com.data.spider.service.tb;

import com.data.spider.service.dao.ScenicExtendDao;
import com.data.spider.service.pojo.ScenicExtend;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenicExtendService {
    @Autowired
    public ScenicExtendDao scenicExtendDao;

    public void update(ScenicExtend dis) {
        scenicExtendDao.update(dis);
    }

    public List<ScenicExtend> gets(int size, Criteria<ScenicExtend> c) {
        Page page = new Page(1, size);

        List<ScenicExtend> dis = scenicExtendDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }

    public void save(ScenicExtend ScenicExtend) {
        scenicExtendDao.save(ScenicExtend);
    }
}
