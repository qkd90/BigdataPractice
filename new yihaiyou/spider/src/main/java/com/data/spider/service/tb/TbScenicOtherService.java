package com.data.spider.service.tb;

import com.data.spider.service.dao.TbScenicOtherDao;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.pojo.tb.TbScenicOther;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbScenicOtherService {
    @Autowired
    private TbScenicOtherDao tbScenicOtherDao;

    public void update(TbScenicOther dis) {
        tbScenicOtherDao.update(dis);
    }

    public List<TbScenicOther> gets(int size,Criteria<TbScenicOther> c) {
        Page page = new Page(1, size);
        List<TbScenicOther> dis = tbScenicOtherDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }

    public void save(TbScenicOther tbScenicOther) {
        tbScenicOtherDao.save(tbScenicOther);
    }
}
