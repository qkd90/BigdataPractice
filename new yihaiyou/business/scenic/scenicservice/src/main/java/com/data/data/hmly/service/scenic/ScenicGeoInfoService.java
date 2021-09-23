package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.ScenicGeoInfoDao;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jonathan.Guo
 */
@Service
public class ScenicGeoInfoService {

    @Resource
    private ScenicGeoInfoDao scenicGeoInfoDao;

    public ScenicGeoinfo get(Long id) {
        return scenicGeoInfoDao.load(id);
    }

    public void save(ScenicGeoinfo scenicGeoinfo) {
        this.scenicGeoInfoDao.save(scenicGeoinfo);
    }
}
