package com.data.spider.service.tb;

import com.data.spider.service.dao.ScenicGalleryDao;
import com.data.spider.service.pojo.ScenicGallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenicGalleryService {

    @Autowired
    private ScenicGalleryDao galleryDao;

    public void saveAll(List<ScenicGallery> ScenicGallerys) {
        galleryDao.save(ScenicGallerys);
    }

    public void save(ScenicGallery ScenicGallery) {
        galleryDao.save(ScenicGallery);
    }

    public void update(ScenicGallery dis) {
        galleryDao.update(dis);
    }

}
