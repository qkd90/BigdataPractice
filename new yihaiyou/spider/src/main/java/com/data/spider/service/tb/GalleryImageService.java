package com.data.spider.service.tb;

import com.data.spider.service.dao.GalleryImageDao;
import com.data.spider.service.pojo.tb.TbGalleryImage;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryImageService {

    @Autowired
    private GalleryImageDao galleryImageDao;

    public void saveAll(List<TbGalleryImage> TbGalleryImages) {
        galleryImageDao.save(TbGalleryImages);
    }

    public void save(TbGalleryImage TbGalleryImage) {
        galleryImageDao.save(TbGalleryImage);

    }

    public void update(TbGalleryImage dis) {
        galleryImageDao.update(dis);
    }

    public List<TbGalleryImage> gets(int size) {
        Page page = new Page(1, size);
        Criteria<TbGalleryImage> c = new Criteria<TbGalleryImage>(TbGalleryImage.class);
        c.eq("status", -100);
        List<TbGalleryImage> dis = galleryImageDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


    public List<TbGalleryImage> gets(int size, Criteria<TbGalleryImage> c) {
        Page page = new Page(1, size);
        List<TbGalleryImage> dis = galleryImageDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
