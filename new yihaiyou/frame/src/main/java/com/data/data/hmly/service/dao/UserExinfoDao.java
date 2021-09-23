package com.data.data.hmly.service.dao;

import com.data.data.hmly.service.entity.UserExinfo;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/14.
 */

@Repository
public class UserExinfoDao extends DataAccess<UserExinfo> {

    public UserExinfo get(Long id) {
        Criteria<UserExinfo> criteria = new Criteria<UserExinfo>(UserExinfo.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    public UserExinfo getByUserId(Long userId) {
        Criteria<UserExinfo> criteria = new Criteria<UserExinfo>(UserExinfo.class);
        criteria.eq("user.id", userId);
        return findUniqueByCriteria(criteria);
    }

    public List<UserExinfo> getByTelephone(String telephone) {
        Criteria<UserExinfo> criteria = new Criteria<UserExinfo>(UserExinfo.class);
        criteria.eq("telephone", telephone);
        return findByCriteria(criteria);
    }
    public Integer countByTelephone(String telephone) {
        Criteria<UserExinfo> criteria = new Criteria<UserExinfo>(UserExinfo.class);
        criteria.eq("telephone", telephone);
        criteria.setProjection(Projections.rowCount());
        Integer count = findIntegerCriteria(criteria);
        return count == null ? 0 : count;
    }

}
