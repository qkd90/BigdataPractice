package com.data.data.hmly.service.comment;

import com.data.data.hmly.service.comment.dao.CommentScoreTypeDao;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by HMLY on 2015/12/31.
 */
@Service
public class CommentScoreTypeService {

    @Resource
    private CommentScoreTypeDao commentScoreTypeDao;

    public List<CommentScoreType> list(CommentScoreType commentScoreType, Page page, String... orderProperties) {
        Criteria<CommentScoreType> criteria = createCriteria(commentScoreType, orderProperties);
        if (page == null) {
            return commentScoreTypeDao.findByCriteria(criteria);
        }
        return commentScoreTypeDao.findByCriteria(criteria, page);
    }

    public Criteria<CommentScoreType> createCriteria(CommentScoreType commentScoreType, String... orderProperties) {
        Criteria<CommentScoreType> criteria = new Criteria<CommentScoreType>(CommentScoreType.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (commentScoreType.getTargetType() != null) {
            criteria.eq("targetType", commentScoreType.getTargetType());
        }
        if (commentScoreType.getStatus() != null) {
            criteria.eq("status", commentScoreType.getStatus());
        }
        return criteria;
    }

}
