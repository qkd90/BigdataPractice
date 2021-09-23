package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.dao.FeedBackDao;
import com.data.data.hmly.service.lxbcommon.entity.Feedback;
import com.data.data.hmly.service.lxbcommon.entity.enums.FeedBackStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/4/15.
 */
@Service
public class FeedbackService {

    @Resource
    private FeedBackDao feedBackDao;


    public void save(Feedback feedback) {
        feedBackDao.save(feedback);
    }

    public void update(Feedback feedback) {
        feedBackDao.update(feedback);
    }

    public void doReplyFeedback(Feedback feedback) {
        feedBackDao.update(feedback);
    }

    public Feedback get(Long id) {
        return feedBackDao.load(id);
    }

    public List<Feedback> getFeedBackList(Feedback condition, Page page, String...orderProperties) {
        Criteria<Feedback> criteria = createCriteria(condition, orderProperties);
        criteria.ne("delFlag", 1);
        if (page != null) {
            return feedBackDao.findByCriteria(criteria, page);
        }

        return feedBackDao.findByCriteria(criteria);
    }

    public Criteria<Feedback> createCriteria(Feedback condition, String... orderProperty) {
        Criteria<Feedback> criteria = new Criteria<Feedback>(Feedback.class);
        if (orderProperty != null) {
            if (orderProperty.length > 1) {
                criteria.orderBy(orderProperty[0], orderProperty[1]);
            } else {
                criteria.orderBy(orderProperty[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (StringUtils.isNotBlank(condition.getContact())) {
            criteria.like("contact", condition.getContact(), MatchMode.ANYWHERE);
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }

        if (condition.getCreator() != null) {
            DetachedCriteria creatorCriteria = criteria.createCriteria("creator");
            Member user = condition.getCreator();
            if (StringUtils.hasText(user.getUserName())) {
                creatorCriteria.add(Restrictions.like("userName", user.getUserName(), MatchMode.ANYWHERE));
//                criteria.like("creator.userName", user.getUserName());
            }
            if (StringUtils.hasText(user.getNickName())) {
                creatorCriteria.add(Restrictions.like("nickName", user.getNickName(), MatchMode.ANYWHERE));
//                criteria.like("creator.nickName", user.getNickName());
            }
        }
        if (condition.getReplier() != null) {
            DetachedCriteria replierCriteria = criteria.createCriteria("replier");
            SysUser user = condition.getReplier();
            if (StringUtils.hasText(user.getUserName())) {
                replierCriteria.add(Restrictions.like("userName", user.getUserName(), MatchMode.ANYWHERE));
//                criteria.like("replier.userName", user.getUserName());
            }
        }
        return criteria;
    }

}
