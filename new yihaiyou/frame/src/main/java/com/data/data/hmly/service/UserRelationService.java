package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.UserRelationDao;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserRelation;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/11/23.
 */

@Service
public class UserRelationService {

    @Resource
    private UserRelationDao dao;
    @Resource
    private UserService userService;

    public UserRelation getParent(long childId) {
        Criteria<UserRelation> criteria = new Criteria<UserRelation>(UserRelation.class);
        criteria.eq("level", 1);
        criteria.eq("childUser.id", childId);
        return dao.findUniqueByCriteria(criteria);
    }

    public List<User> getChild(long parentId, int level) {
    	StringBuilder hql = new StringBuilder();
    	hql.append("select new User(u.id, f.nickName, f.headImgUrl) from UserRelation ur, User u, WechatFollower f ");
    	hql.append("where ur.childUser.id = u.id and u.account = f.openId and ur.parentUser.id = ? and ur.level = ? ");
    	return dao.findByHQL(hql.toString(), parentId, level);
        /*Criteria<UserRelation> criteria = new Criteria<UserRelation>(UserRelation.class);
        criteria.eq("parentUser.id", parentId);
        criteria.eq("level", level);
        List<UserRelation> userRelations = dao.findByCriteria(criteria);
        List<User> children = new ArrayList<User>();
        for (UserRelation userRelation : userRelations) {
            children.add(userRelation.getChildUser());
        }
        return children;*/
    }

    public List<UserRelation> getAllChild(List<Long> parentIds) {
        Criteria<UserRelation> criteria = new Criteria<UserRelation>(UserRelation.class);
	    if (!parentIds.isEmpty()) {
		    criteria.in("parentUser.id", parentIds);
	    }
        List<UserRelation> userRelations = dao.findByCriteria(criteria);
        return userRelations;
    }

    public Boolean canBeInvited(User user) {
        Criteria<UserRelation> criteria = new Criteria<UserRelation>(UserRelation.class);
        criteria.Add(Restrictions.or(Restrictions.eq("parentUser.id", user.getId()), Restrictions.eq("childUser.id", user.getId())));
        criteria.setProjection(Projections.rowCount());
        Long count = dao.findLongCriteria(criteria);
        if (count > 0) {
            return false;
        }
        return true;
    }

    public void insertRelation(Long userId, User currUser) {
        User parentUser = userService.get(userId);
        User user = userService.get(currUser.getId());	// 被邀请用户

        // 新增关联
        List<UserRelation> userRelationList = new ArrayList<UserRelation>();
        // 一级关联
        UserRelation levelOne = new UserRelation();
        levelOne.setLevel(1);
        levelOne.setParentUser(parentUser);
        levelOne.setChildUser(user);
        levelOne.setCreatedTime(new Date());
        userRelationList.add(levelOne);
        // 更新被邀请用户的上级
        user.setParent(parentUser);
        if (parentUser.getParent() != null) {
            // 二级关联
            UserRelation levelTwo = new UserRelation();
            levelTwo.setLevel(2);
            levelTwo.setParentUser(parentUser.getParent());
            levelTwo.setChildUser(user);
            levelTwo.setCreatedTime(new Date());
            userRelationList.add(levelTwo);

            // 更新被邀请用户的上上级
            user.setGrand(parentUser.getParent());
        }
        if (parentUser.getGrand() != null) {
            // 三级关联
            UserRelation levelThree = new UserRelation();
            levelThree.setLevel(3);
            levelThree.setParentUser(parentUser.getGrand());
            levelThree.setChildUser(user);
            levelThree.setCreatedTime(new Date());
            userRelationList.add(levelThree);
        }

        userService.update(user);
        dao.save(userRelationList);
    }

    /**
     * 查询层级人员
     * @param parentId
     * @param level
     * @return
     */
    public List<Member> listChildren(Long parentId, Integer level, Page page) {
        return dao.listChildren(parentId, level, page);
    }

    /**
     * 统计层级人员数
     * @param parentId
     * @param level
     * @return
     */
    public Long countChildren(Long parentId, Integer level) {
        return dao.countChildren(parentId, level);
    }
}
