package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.ScenicThemeRelationDao;
import com.data.data.hmly.service.scenic.entity.ScenicThemeRelation;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScenicThemeRelationService {

    Logger logger = Logger.getLogger(ScenicThemeRelationService.class);

    @Resource
    private ScenicThemeRelationDao scenicThemeRelationDao;

    public List<ScenicThemeRelation> listAll() {
        return list(new ScenicThemeRelation(), null);
    }

    public List<ScenicThemeRelation> list(ScenicThemeRelation scenicThemeRelation, Page page, String... orderProperties) {
        Criteria<ScenicThemeRelation> criteria = createCriteria(scenicThemeRelation, orderProperties);
        if (page == null) {
            return scenicThemeRelationDao.findByCriteria(criteria);
        }
        return scenicThemeRelationDao.findByCriteria(criteria, page);
        }

    public Criteria<ScenicThemeRelation> createCriteria(ScenicThemeRelation scenicThemeRelation, String... orderProperties) {
        Criteria<ScenicThemeRelation> criteria = new Criteria<ScenicThemeRelation>(ScenicThemeRelation.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        return criteria;
    }

}
