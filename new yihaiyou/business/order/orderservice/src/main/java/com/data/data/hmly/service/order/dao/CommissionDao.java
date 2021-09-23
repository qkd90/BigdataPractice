package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.order.entity.Commission;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by vacuity on 15/11/4.
 */

@Repository
public class CommissionDao extends DataAccess<Commission> {

    public Commission get(long id){
        Criteria<Commission> criteria = new Criteria<Commission>(Commission.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    public List<Commission> getList(Commission commission, Page page){
        Criteria<Commission> criteria = makeCriteria(commission);
        if (page != null){
            return findByCriteria(criteria);
        }else {
            return findByCriteria(criteria, page);
        }
    }


    private Criteria<Commission> makeCriteria(Commission commission){
        Criteria<Commission> criteria = new Criteria<Commission>(Commission.class);
        if (commission.getUser() != null){
            criteria.eq("user.id", commission.getUser().getId());
        }
        if (commission.getProduct() != null){
            criteria.eq("product.id", commission.getProduct().getId());
        }
        if (commission.getTopProduct() != null){
            criteria.eq("topProduct.id", commission.getTopProduct().getId());
        }
        if (commission.getOrderDetail() != null){
            criteria.eq("orderDetail.id", commission.getOrderDetail().getId());
        }
        return criteria;
    }
}
