package com.data.data.hmly.service;

import com.data.data.hmly.service.scenic.dao.ScenicAreaDao;
import com.data.data.hmly.service.scenic.entity.ScenicArea;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2017/1/17.
 */
@Service
public class ScenicAreaService {

    @Resource
    private ScenicAreaDao scenicAreaDao;

    public List<ScenicArea> findByScenic(Long scenicId) {
        Criteria<ScenicArea> criteria = new Criteria<ScenicArea>(ScenicArea.class);
        criteria.createCriteria("scenicInfo", "scenicInfo");
        criteria.eq("scenicInfo.id", scenicId);
        return scenicAreaDao.findByCriteria(criteria);
    }
}
