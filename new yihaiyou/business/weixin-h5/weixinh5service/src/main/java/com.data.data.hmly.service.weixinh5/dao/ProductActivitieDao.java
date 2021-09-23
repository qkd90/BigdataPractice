package com.data.data.hmly.service.weixinh5.dao;

import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.weixinh5.entity.Activities;
import com.data.data.hmly.service.weixinh5.entity.ProductActivity;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by dy on 2016/2/17.
 */
@Repository
public class ProductActivitieDao extends DataAccess<ProductActivity> {


    public List<ProductActivity> getHasLineActivity(Activities activities, List<Line> lines) {

        Criteria<ProductActivity> criteria = new Criteria<ProductActivity>(ProductActivity.class);

        criteria.eq("activitie", activities);
        criteria.in("line", lines);

        return findByCriteria(criteria);
    }

}
