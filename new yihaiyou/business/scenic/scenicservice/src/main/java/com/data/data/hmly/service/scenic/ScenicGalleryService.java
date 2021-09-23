package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.ScenicGalleryDao;
import com.data.data.hmly.service.scenic.entity.ScenicGallery;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScenicGalleryService {

    Logger logger = Logger.getLogger(ScenicGalleryService.class);

    @Resource
    private ScenicGalleryDao scenicGalleryDao;

    public void save(ScenicGallery scenicGallery) {
        scenicGalleryDao.save(scenicGallery);
    }

    public ScenicGallery get(Long id) {
        return scenicGalleryDao.load(id);
    }


}
