package com.hmlyinfo.app.soutu.delicacy.service;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.domain.RestaurantComment;
import com.hmlyinfo.app.soutu.delicacy.mapper.RestaurantCommentMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantCommentService extends BaseService<RestaurantComment, Long> {

    @Autowired
    private RestaurantCommentMapper<RestaurantComment> mapper;
    @Autowired
    private UserService userService;

    @Override
    public BaseMapper<RestaurantComment> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public void addComment(RestaurantComment comment) {
        Long userId = MemberService.getCurrentUserId();
        User user = userService.info(userId);
        Validate.notNull(user, ErrorCode.ERROR_51004);
        comment.setUserId(userId);
        comment.setUserFace(user.getUserface());
        comment.setNickname(user.getNickname());
        insert(comment);
    }

    public void delete(Long id) {
        RestaurantComment comment = info(id);
        Validate.dataAuthorityCheck(comment);
        del(id.toString());
    }

    public void updateComment(RestaurantComment comment) {
        Validate.dataAuthorityCheck(comment);
        update(comment);
    }
}
