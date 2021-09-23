package com.data.spider.service.tb;

import com.data.spider.service.dao.TbTransportationDao;
import com.data.spider.service.pojo.tb.TbTransportation;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbTransportationService {
    @Autowired
    private TbTransportationDao tbTransportationDao;

    public void update(TbTransportation dis) {
        tbTransportationDao.update(dis);
    }

    public List<TbTransportation>  gets(int size) {
        Page page = new Page(1, size);
        Criteria<TbTransportation> c = new Criteria<TbTransportation>(TbTransportation.class);
//        c.eq("dataStatus", -1);
//        c.eq("type", 3);
        List<TbTransportation> dis = tbTransportationDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
    public List<TbTransportation>  gets(int size,Criteria<TbTransportation> c ) {
        Page page = new Page(1, size);
        List<TbTransportation> dis = tbTransportationDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
}
