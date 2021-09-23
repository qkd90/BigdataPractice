package com.data.spider.service.tb;

import com.data.spider.service.dao.GalleryDao;
import com.data.spider.service.pojo.tb.TbGallery;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {

    @Autowired
    private GalleryDao galleryDao;

    public void saveAll(List<TbGallery> TbGallerys) {
        galleryDao.save(TbGallerys);
    }

    public void save(TbGallery TbGallery) {
        galleryDao.save(TbGallery);
    }

    public void update(TbGallery dis) {
        galleryDao.update(dis);
    }

    public List<TbGallery> gets(int size) {
        Page page = new Page(1, size);
        Criteria<TbGallery> c = new Criteria<TbGallery>(TbGallery.class);
        c.eq("status", -100);
        List<TbGallery> dis = galleryDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<TbGallery> gets(int size, Criteria<TbGallery> c) {
        Page page = new Page(1, size);
        List<TbGallery> dis = galleryDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
