package com.hmlyinfo.app.soutu.delicacy.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.domain.RestaurantComment;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantCommentService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guoshijie on 2015/5/12.
 */
@Controller
@RequestMapping("/api/auth/restaurantcomment")
public class RestaurantCommentAuthApi {

    @Autowired
    private RestaurantCommentService service;

    /**
     * 新增
     * <ul>
     * <li>url:/api/restaurantcomment/add</li>
     * </ul>
     *
     */
    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(final RestaurantComment restaurantComment) {

        service.addComment(restaurantComment);

        return ActionResult.createSuccess(restaurantComment);
    }

    /**
     * 删除
     * <ul>
     * <li>必选:标识{id}<li>
     * <li>url:/api/restaurantcomment/del</li>
     * </ul>
     *
     */
    @RequestMapping("/del")
    @ResponseBody
    public ActionResult del(final String id) {

        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        service.delete(Long.valueOf(id));

        return ActionResult.createSuccess();
    }

    /**
     * 编辑
     * <ul>
     * <li>必选:标识{id}<li>
     * <li>url:/api/restaurantcomment/edit</li>
     * </ul>
     *
     */
    @RequestMapping("/update")
    @ResponseBody
    public ActionResult edit(final RestaurantComment restaurantComment) {

        Validate.notNull(restaurantComment.getId(), ErrorCode.ERROR_50001, "标识{id}不能为空");
        service.updateComment(restaurantComment);

        return ActionResult.createSuccess(restaurantComment);
    }
}
