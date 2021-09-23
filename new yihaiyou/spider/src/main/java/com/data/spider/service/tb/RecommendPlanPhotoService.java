package com.data.spider.service.tb;


import com.data.spider.service.dao.RecommendPlanPhotoDao;
import com.data.spider.service.pojo.tb.RecommendPlanPhoto;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecommendPlanPhotoService {

    @Resource
    private RecommendPlanPhotoDao recommendPlanPhotoDao;


    public void save(RecommendPlanPhoto recommendPlanPhoto) {
        recommendPlanPhotoDao.save(recommendPlanPhoto);

    }


    public void update(RecommendPlanPhoto photo) {
        recommendPlanPhotoDao.update(photo);
    }

    public List<RecommendPlanPhoto> gets(int size, Criteria<RecommendPlanPhoto> c) {
        Page page = new Page(1, size);

        List<RecommendPlanPhoto> dis = recommendPlanPhotoDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }
}
