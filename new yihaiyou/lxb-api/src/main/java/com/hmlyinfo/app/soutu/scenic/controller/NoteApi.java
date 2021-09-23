package com.hmlyinfo.app.soutu.scenic.controller;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.service.NoteService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshijie on 2014/8/25.
 */
@Controller
@RequestMapping("api/auth/note")
public class NoteApi {

    @Autowired
    NoteService noteService;

    /**
     * 增加一条游记评论，关联于游记图片
     * <ul>
     *     <li>必选：noteId 游记Id</li>
     *     <li>可选：noteImageId 游记图片id</li>
     *     <li>必选：content 内容</li>
     *     <li>可选：replyTo 回复的用户的id</li>
     * </ul>
     *
     */
    @RequestMapping("/comment/insert")
    @ResponseBody
    public ActionResult insertComment(HttpServletRequest request) {
        Validate.notNull(request.getParameter("noteId"), ErrorCode.ERROR_51001);
        Validate.notNull(request.getParameter("content"), ErrorCode.ERROR_51001);

        Long userId = MemberService.getCurrentUserId();

        Map<String, Object> params = HttpUtil.parsePageMap(request);
        params.put("userId", userId);
        noteService.addComment(params);
        return ActionResult.createSuccess(new HashMap<String,Object>());
    }

    /**
     * 删除一条评论
     * <ul>
     * <li>必选：id评论id</li>
     * </ul>
     */
    @RequestMapping("/comment/delete")
    @ResponseBody
    public ActionResult deleteComment(HttpServletRequest request) {
        Validate.notNull(request.getParameter("id"));
        noteService.deleteComment(request.getParameter("id"));
        return ActionResult.createSuccess();
    }

    /**
     * <ul>
     *     <li>必选：noteImageId 游记图片id</li>
     * </ul>
     */
    @RequestMapping("/good")
    @ResponseBody
    public ActionResult addGood(HttpServletRequest request) {
        Validate.notNull(request.getParameter("noteImageId"));
        noteService.addGood(Long.parseLong(request.getParameter("noteImageId")));
        return ActionResult.createSuccess();
    }
}
