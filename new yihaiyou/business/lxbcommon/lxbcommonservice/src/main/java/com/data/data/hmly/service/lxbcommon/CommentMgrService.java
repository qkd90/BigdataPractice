package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.comment.dao.CommentScoreTypeDao;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.comment.entity.enums.commentScoreTypeStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lxbcommon.dao.CommentMgrDao;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/4/25.
 */
@Service
public class CommentMgrService {

    @Resource
    private CommentMgrDao commentMgrDao;
    @Resource
    private CommentScoreTypeDao commentScoreTypeDao;

    public Comment get(Long id) {
        return commentMgrDao.load(id);
    }

    public void save(Comment comment) {
        commentMgrDao.save(comment);
    }

    public void delete(Comment comment) {
        commentMgrDao.delete(comment);
    }

    public void update(Comment comment) {
        commentMgrDao.update(comment);
    }

    public List<Comment> listTreeData(Comment condition, Page page, String...orderProperties) {
        Criteria<Comment> idCriteria = createCriteria(condition, orderProperties);
        if (orderProperties != null) {
            if (orderProperties.length > 1) {
                idCriteria.orderBy(orderProperties[0], orderProperties[1]);
            } else {
                idCriteria.orderBy(orderProperties[0], "desc");
            }
        }
        idCriteria.setProjection(Projections.groupProperty("id"));
        List<?> idList;
        if (page != null) {
            idList = commentMgrDao.findByCriteria(idCriteria, page);
        } else {
            idList = commentMgrDao.findByCriteria(idCriteria);
        }
        ListUtils.transformedList(idList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return input;
            }
        });
        Criteria<Comment> criteria = new Criteria<Comment>(Comment.class);
        List<Comment> treeDataList = new ArrayList<Comment>();
        if (!idList.isEmpty()) {
            if (orderProperties != null && orderProperties.length > 1) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties != null && orderProperties.length == 1) {
                criteria.orderBy(orderProperties[0], "desc");
            }
            criteria.in("id", idList);
            treeDataList = commentMgrDao.findByCriteria(criteria);
        }
        return treeDataList;
    }

    public List<CommentScoreType> getScoreTypeList(ProductType targetType) {
        Criteria<CommentScoreType> criteria = new Criteria<>(CommentScoreType.class);
        criteria.eq("targetType", targetType);
        criteria.ne("status", commentScoreTypeStatus.DEL);
        return commentScoreTypeDao.findByCriteria(criteria);
    }

    private Criteria<Comment> createCriteria(Comment condition, String...orderProperties) {
        Criteria<Comment> criteria = new Criteria<Comment>(Comment.class);
        criteria.isNull("repliedId");
        DetachedCriteria commentsCriteria = criteria.createCriteria("comments", "comments", JoinType.LEFT_OUTER_JOIN);
        DetachedCriteria scoreCriteria = criteria.createCriteria("commentScores", "commentScores", JoinType.LEFT_OUTER_JOIN);
        DetachedCriteria commentsScoreCriteria = commentsCriteria.createCriteria("commentScores", "ctsCtScore", JoinType.LEFT_OUTER_JOIN);
        scoreCriteria.createCriteria("commentScoreType", "commentScoreType", JoinType.LEFT_OUTER_JOIN);
        commentsScoreCriteria.createCriteria("commentScoreType", "ctsCtScoreType", JoinType.LEFT_OUTER_JOIN);
        if (condition.getUser() != null) {
            criteria.createCriteria("user", "user");
            commentsCriteria.createCriteria("user", "ctsUser");
            Member member = (Member) condition.getUser();
            if (StringUtils.hasText(member.getUserName())) {
                criteria.or(Restrictions.like("user.userName", member.getUserName(), MatchMode.ANYWHERE),
                        Restrictions.like("ctsUser.userName", member.getUserName(), MatchMode.ANYWHERE));
            }
            if (StringUtils.hasText(member.getNickName())) {
                criteria.or(Restrictions.like("user.nickName", member.getNickName(), MatchMode.ANYWHERE),
                        Restrictions.like("ctsUser.nickName", member.getNickName(), MatchMode.ANYWHERE));
            }
        }
        if (StringUtils.hasText(condition.getContent())) {
            criteria.or(Restrictions.like("content", condition.getContent(), MatchMode.ANYWHERE),
                    Restrictions.like("comments.content", condition.getContent(), MatchMode.ANYWHERE));
        }
        if (condition.getType() != null) {
            criteria.or(Restrictions.eq("type", condition.getType()),
                    Restrictions.eq("comments.type", condition.getType()));
        }
        if (condition.getStatus() != null) {
            criteria.or(Restrictions.eq("status", condition.getStatus()),
                    Restrictions.eq("comments.status", condition.getStatus()));
        }
        if (condition.getHasPic() != null && condition.getHasPic()) {
            criteria.createCriteria("commentPhotos", "commentPhotos");
            criteria.or(Restrictions.isNotEmpty("commentPhotos"),
                    Restrictions.isNotEmpty("comments.commentPhotos"));
        }
        if (condition.getScoreTypeId() != null && condition.getScoreTypeId() > 0) {
            criteria.or(Restrictions.eq("commentScoreType.id", condition.getScoreTypeId()),
                    Restrictions.eq("ctsCtScoreType.id", condition.getScoreTypeId()));
        }
        if (condition.getMinScore() != null && condition.getMinScore() > 0) {
            criteria.or(Restrictions.ge("commentScores.score", condition.getMinScore()),
                    Restrictions.ge("ctsCtScore.score", condition.getMinScore()));
        }
        if (condition.getMaxScore() != null && condition.getMaxScore() > 0) {
            criteria.or(Restrictions.le("commentScores.score", condition.getMaxScore()),
                    Restrictions.le("ctsCtScore.score", condition.getMaxScore()));
        }
        return criteria;
    }
}
