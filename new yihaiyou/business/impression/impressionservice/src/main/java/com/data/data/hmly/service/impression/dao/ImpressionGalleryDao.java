package com.data.data.hmly.service.impression.dao;

import com.data.data.hmly.service.impression.entity.ImpressionGallery;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
@Repository
public class ImpressionGalleryDao extends DataAccess<ImpressionGallery> {

    public List<ImpressionGallery> list(ImpressionGallery impressionGallery) {
        Criteria<ImpressionGallery> criteria = createCriteria(impressionGallery);
        return findByCriteria(criteria);
    }

    private Criteria<ImpressionGallery> createCriteria(ImpressionGallery impressionGallery) {
        Criteria<ImpressionGallery> criteria = new Criteria<ImpressionGallery>(ImpressionGallery.class);
        if (impressionGallery == null) {
            return criteria;
        }
        if (impressionGallery.getImpression() != null) {
            criteria.eq("impression.id", impressionGallery.getImpression().getId());
        }
        return criteria;
    }
}
