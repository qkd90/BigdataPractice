package com.data.data.hmly.service.impression;

import com.data.data.hmly.service.impression.dao.ImpressionGalleryDao;
import com.data.data.hmly.service.impression.entity.ImpressionGallery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
@Service
public class ImpressionGalleryService {
    @Resource
    private ImpressionGalleryDao impressionGalleryDao;

    public ImpressionGallery get(Long id) {
        return impressionGalleryDao.load(id);
    }

    public List<ImpressionGallery> list(ImpressionGallery impressionGallery) {
        return impressionGalleryDao.list(impressionGallery);
    }

    public void save(ImpressionGallery impressionGallery) {
        impressionGalleryDao.saveOrUpdate(impressionGallery, impressionGallery.getId());
    }

    public void delete(ImpressionGallery impressionGallery) {
        impressionGalleryDao.delete(impressionGallery);
    }

    public void deleteById(Long id) {
        impressionGalleryDao.delete(id, ImpressionGallery.class);
    }
}
