package com.data.spider.service.data;

import com.data.spider.service.dao.DataRecplanPhotoDao;
import com.data.spider.service.pojo.data.DataRecplanPhoto;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRecplanPhotoService {

    @Autowired
    private DataRecplanPhotoDao dataRecplanPhotoDao;

    public void saveAll(List<DataRecplanPhoto> dataRecplanPhoto) {
        dataRecplanPhotoDao.save(dataRecplanPhoto);
    }

    public void save(DataRecplanPhoto dataRecplanPhoto) {
        dataRecplanPhotoDao.save(dataRecplanPhoto);
    }

    public void update(DataRecplanPhoto dataRecplanPhoto) {
        dataRecplanPhotoDao.update(dataRecplanPhoto);
    }

    public List<DataRecplanPhoto> gets(int size) {
        Page page = new Page(1, size);
        Criteria<DataRecplanPhoto> c = new Criteria<DataRecplanPhoto>(DataRecplanPhoto.class);
        c.eq("status", -100);
        List<DataRecplanPhoto> dis = dataRecplanPhotoDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<DataRecplanPhoto> gets(int size, Criteria<DataRecplanPhoto> c) {
        Page page = new Page(1, size);
        List<DataRecplanPhoto> dis = dataRecplanPhotoDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
