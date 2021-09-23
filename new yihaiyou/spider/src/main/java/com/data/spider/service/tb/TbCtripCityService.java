package com.data.spider.service.tb;

import com.data.spider.service.dao.TbctripcityDao;
import com.data.spider.service.pojo.tb.TbCtripCity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbCtripCityService {
    @Autowired
    private TbctripcityDao tbctripcityDao;
    public List<TbCtripCity> getCity(int cityCode) {
        Page page = new Page(1, 1);
        Criteria<TbCtripCity> c = new Criteria<TbCtripCity>(TbCtripCity.class);
        c.eq("cityId", (long)cityCode);
        List<TbCtripCity> dis = tbctripcityDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


}
