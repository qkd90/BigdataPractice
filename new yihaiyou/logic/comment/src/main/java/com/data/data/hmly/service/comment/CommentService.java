package com.data.data.hmly.service.comment;

import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.comment.dao.CommentDao;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.comment.vo.CommentSumarryVo;
import com.data.data.hmly.service.entity.User;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/12/3.
 */
@Service
public class CommentService {

    @Resource
    private CommentDao commentDao;
    @Resource
    private CommentScoreService commentScoreService;
    @Resource
    private CommentPhotoService commentPhotoService;
    @Resource
    private UserService userService;

    public void save(Comment comment) {
        commentDao.save(comment);
    }

    public void saveComment(Comment comment) {
        save(comment);
        if (comment.getRepliedId() == null && comment.getCommentScores() != null && !comment.getCommentScores().isEmpty()) {
            for (CommentScore commentScore : comment.getCommentScores()) {
                commentScore.setCommentId(comment.getId());
                commentScoreService.save(commentScore);
            }
        }
        if (comment.getCommentPhotos() != null && comment.getCommentPhotos() != null && !comment.getCommentPhotos().isEmpty()) {
            for (CommentPhoto commentPhoto : comment.getCommentPhotos()) {
                commentPhoto.setComment(comment);
                commentPhotoService.save(commentPhoto);
            }
        }
    }

    // 批量删除
    public void delByIds(String commentIds, User user) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : commentIds.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<Comment> criteria = new Criteria<Comment>(Comment.class);
        criteria.in("id", idList);
        criteria.eq("user.id", user.getId());
        List<Comment> commentList = commentDao.findByCriteria(criteria);
        if (!commentList.isEmpty()) {
            for (Comment comment : commentList) {
                comment.setStatus(CommentStatus.DELETED);
            }
            commentDao.save(commentList);
        }
    }

    public List<Comment> list(Comment comment, Page page, String... orderProperties) {
        Criteria<Comment> criteria = createCriteria(comment, orderProperties);
        if (page == null) {
            return commentDao.findByCriteria(criteria);
        }
        return commentDao.findByCriteria(criteria, page);
    }

    public Long count(Comment comment) {
        Criteria<Comment> criteria = createCriteria(comment);
        criteria.setProjection(Projections.rowCount());
        return (Long) commentDao.findUniqueValue(criteria);
    }

    public Long countMyComment(Comment comment, String... orderProperties) {
        Criteria<Comment> criteria = createCriteria(comment, orderProperties);
        criteria.setProjection(Projections.rowCount());
        return commentDao.findLongCriteria(criteria);
    }

    public Criteria<Comment> createCriteria(Comment comment, String... orderProperties) {
        Criteria<Comment> criteria = new Criteria<Comment>(Comment.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (comment.getUser() != null) {
            criteria.eq("user.id", comment.getUser().getId());
        }
        if (comment.getType() != null) {
            criteria.eq("type", comment.getType());
        }
        if (comment.getId() != null) {
            criteria.eq("id", comment.getId());
        }
        if (comment.getRepliedId() == null) {
            criteria.isNull("repliedId");
        }
        if (comment.getTargetId() != null) {
            criteria.eq("targetId", comment.getTargetId());
        }
        if (comment.getPriceId() != null) {
            criteria.eq("priceId", comment.getPriceId());
        }
        if (comment.getStatus() != null) {
            criteria.eq("status", comment.getStatus());
        }
        if (comment.getTargetIdList() != null && !comment.getTargetIdList().isEmpty()) {
            criteria.in("targetId", comment.getTargetIdList());
        }
        if (comment.getOrderId() != null) {
            criteria.eq("orderId", comment.getOrderId());
        }
        if (comment.getOrderDetailId() != null) {
            criteria.eq("orderDetailId", comment.getOrderDetailId());
        }
        criteria.ne("status", CommentStatus.DELETED);
        criteria.orderBy(Order.desc("createTime"));
        return criteria;
    }

    /**
     * 根据标识查询评论
     *
     * @param id
     * @return
     * @author caiys
     * @date 2015年12月25日 下午1:41:18
     */
    public Comment getComment(Long id) {
        return commentDao.load(id);
    }


    public Integer getAvgScore(Comment comment) {
        List<Comment> commentList = list(comment, null);
        Integer tatolScore = 0;
        Integer tatolCounts = 0;
        for (Comment c : commentList) {
            List<CommentScore> commentScores = c.getCommentScores();
            for (CommentScore cs : commentScores) {
                for (CommentScore commentscore : commentScores) {
                    tatolScore += commentscore.getScore();
                }
            }
            tatolCounts += commentScores.size();
        }

        Integer avgSocre = 0;
        if (tatolScore > 0) {
            avgSocre = tatolScore/tatolCounts;
        }
        return avgSocre;
    }


    public List<Comment> getCommentList(Comment comment, Page page) {

        Criteria<Comment> criteria = new Criteria<Comment>(Comment.class);
        if (comment.getTargetId() != null) {
            criteria.eq("targetId", comment.getTargetId());
        }
        if (comment.getPriceId() != null) {
            criteria.eq("priceId", comment.getPriceId());
        }
        if (StringUtils.isNotBlank(comment.getContent())) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("content", comment.getContent(), MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("orderNo", comment.getContent(), MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }
        if (comment.getTargetIdList() != null && !comment.getTargetIdList().isEmpty()) {
            criteria.in("targetId", comment.getTargetIdList());
        }
        if (comment.getReplyStatus() != null) {
            criteria.eq("replyStatus", comment.getReplyStatus());
        }
        criteria.ne("status", CommentStatus.DELETED);
        criteria.isNull("repliedId");

        criteria.orderBy("createTime", "desc");

        return commentDao.findByCriteria(criteria, page);
        /*Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
//        c.id as id, c.targetId as targetId, c.content as content, c.type as type, c.status as status, c.repliedId as repliedId, c.orderNo as orderNo
        sql.append("SELECT * FROM COMMENT c WHERE EXISTS ( SELECT 1 FROM ticketprice tp WHERE tp.id = c.targetId");

        if (comment.getTargetId() != null) {
            sql.append(" and tp.id =:targetId");
            params.put("targetId", comment.getTargetId());
        }
        sql.append(" and exists(select 1 from product p where p.id = tp.ticketId");
        if (comment.getCompanyUnitId() != null) {
            sql.append(" and p.companyUnitId=:companyUnitId");
            params.put("companyUnitId", comment.getCompanyUnitId());
        }
        if (comment.getProductId() != null) {
            sql.append(" and p.id=:productId");
            params.put("productId", comment.getProductId());
        }
        sql.append(")");
        sql.append(")");
        if (StringUtils.isNotBlank(comment.getContent())) {
            sql.append(" and (c.content like '%"+ comment.getContent() +"%' or c.orderNo like '%"+ comment.getContent() +"%')");
        }
        if (comment.getReplyStatus() != null) {
            sql.append(" and c.replyStatus= :replyStatus");
            params.put("replyStatus", comment.getReplyStatus());
        }
        sql.append(" and c.status!=:notDelete");
        params.put("notDelete", CommentStatus.DELETED.toString());
        sql.append(" and c.repliedId is null");
        return commentDao.findEntitiesBySQL2(sql.toString(), page, Comment.class, params);*/
    }

    public List<Comment> getCommentReplies(Comment comment) {
        Criteria<Comment> criteria = new Criteria<Comment>(Comment.class);
        criteria.eq("repliedId", comment.getId());
        criteria.ne("status", CommentStatus.DELETED);
        return commentDao.findByCriteria(criteria);
    }

    public List<CommentSumarryVo> getHotelCommentSumarryList(Comment comment, Page page) {
//      select count(c.id), avg(cs.score), p.* from ticketprice p left join comment c on c.targetId = p.id left join comment_score cs on cs.commentId = c.id
//      where c.type = 'sailboat' and c.repliedId is null and p.ticketId=5001047 group by p.id;
        StringBuffer sb = new StringBuffer();
        sb.append("select pd.id as targetId, p.id as priceId, p.roomName as targetName, count(c.id) as count, avg(cs.score) as score from hotel_price p left join product pd on pd.id=p.hotelId left join comment c on c.priceId = p.id left join comment_score cs on cs.commentId = c.id");
        sb.append(" where c.repliedId is null");
        List paramsList = Lists.newArrayList();
        Map<String, Object> params = new HashMap<String, Object>();
        if (comment.getType() != null) {
            sb.append(" and c.type =?");
            paramsList.add(comment.getType().toString());
        }
        if (StringUtils.isNotBlank(comment.getTargetName())) {
            sb.append(" and p.roomName like '%" +comment.getTargetName().trim()+ "%'");
        }
        sb.append(" and c.status <> '").append(CommentStatus.DELETED).append("'");
        if (comment.getTargetId() != null) {
            sb.append(" and pd.id =?");
            paramsList.add(comment.getTargetId());
        }
        if (comment.getCompanyUnitId() != null) {
            sb.append(" and pd.companyUnitId =?");
            paramsList.add(comment.getCompanyUnitId());
        }
        sb.append(" group by p.id order by score desc");
        return commentDao.findEntitiesBySQL3(sb.toString(), page, CommentSumarryVo.class, paramsList.toArray());
    }



    public List<CommentSumarryVo> getSailboatCommentSumarryList(Comment comment, Page page) {
//      select count(c.id), avg(cs.score), p.* from ticketprice p left join comment c on c.targetId = p.id left join comment_score cs on cs.commentId = c.id
//      where c.type = 'sailboat' and c.repliedId is null and p.ticketId=5001047 group by p.id;
        StringBuffer sb = new StringBuffer();
        sb.append("select pd.id as targetId, p.id as priceId, p.name as targetName, count(c.id) as count, avg(cs.score) as score from ticketprice p left join product pd on pd.id=p.ticketId left join comment c on c.priceId = p.id left join comment_score cs on cs.commentId = c.id");
        sb.append(" where c.repliedId is null");
        List paramsList = Lists.newArrayList();
        Map<String, Object> params = new HashMap<String, Object>();
        if (comment.getType() != null) {
            sb.append(" and c.type =?");
            paramsList.add(comment.getType().toString());
        }
        if (StringUtils.isNotBlank(comment.getTargetName())) {
            sb.append(" and p.name like '%" +comment.getTargetName().trim()+ "%'");
        }
        sb.append(" and c.status <> '").append(CommentStatus.DELETED).append("'");
        if (comment.getTargetId() != null) {
            sb.append(" and pd.id =?");
            paramsList.add(comment.getTargetId());
        }
        if (comment.getCompanyUnitId() != null) {
            sb.append(" and pd.companyUnitId =?");
            paramsList.add(comment.getCompanyUnitId());
        }
        sb.append(" group by p.id order by score desc");
        return commentDao.findEntitiesBySQL3(sb.toString(), page, CommentSumarryVo.class, paramsList.toArray());
    }

    public void update(Comment oldComment) {
        commentDao.update(oldComment);
    }
}
