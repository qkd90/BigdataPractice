package com.data.spider.service.tb;

import com.data.spider.service.dao.TbRestaurantDao;
import com.data.spider.service.pojo.tb.TbRestaurant;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private TbRestaurantDao tbRestaurantDao;

    public void saveAll(List<TbRestaurant> tbDelicacys) {
        tbRestaurantDao.save(tbDelicacys);
    }

    public void save(TbRestaurant tbDelicacy) {
        tbRestaurantDao.save(tbDelicacy);
    }

    public void update(TbRestaurant dis) {
        tbRestaurantDao.update(dis);
    }


    public List<TbRestaurant> gets(int size, Criteria<TbRestaurant> c) {
        Page page = new Page(1, size);
        List<TbRestaurant> dis = tbRestaurantDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
