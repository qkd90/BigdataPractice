package com.data.data.hmly.service.comment;

import com.data.data.hmly.service.comment.dao.AdviceDao;
import com.data.data.hmly.service.comment.entity.Advice;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
@Service
public class AdviceService {

    @Resource
    private AdviceDao adviceDao;

    public Advice get(Long id) {
        return adviceDao.load(id);
    }

    public void update(Advice advice) {
        adviceDao.update(advice);
    }

    public void save(Advice advice) {
        adviceDao.save(advice);
    }

    public List<Advice> list(Advice advice, Page page, String... orderProperties) {
        Criteria<Advice> criteria = createCriteria(advice, orderProperties);
        if (page == null) {
            return adviceDao.findByCriteriaWithOutCount(criteria, new Page(0, Integer.MAX_VALUE));
        }
        return adviceDao.findByCriteriaWithOutCount(criteria, page);
    }

    public Long count(Advice advice, String... orderProperties) {
        Criteria<Advice> criteria = createCriteria(advice, orderProperties);
        criteria.setProjection(Projections.rowCount());
        return adviceDao.findLongCriteria(criteria);
//        return (Long) adviceDao.findByHQL("select count(*) from advice").get(0);
    }

    public Criteria<Advice> createCriteria(Advice advice, String... orderProperties) {
        Criteria<Advice> criteria = new Criteria<Advice>(Advice.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (advice.getUser() != null) {
            criteria.eq("user.id", advice.getUser().getId());
        }
        if (advice.getReply() != null) {
            criteria.eq("reply", advice.getReply());
        }
        if (advice.getAccept() != null) {
            criteria.eq("accept", advice.getAccept());
        }
        return criteria;
    }

}
