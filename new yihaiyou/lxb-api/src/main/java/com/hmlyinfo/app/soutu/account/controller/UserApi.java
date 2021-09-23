package com.hmlyinfo.app.soutu.account.controller;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/user")
public class UserApi {

    @Autowired
    private UserService userService;


    /**
     * 获取登录信息
     * <ul>
     * <li>必选：token{token}</li>
     * <li>url:/api/auth/user/info</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/info")
    public
    @ResponseBody
    ActionResult info(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("access_token"), ErrorCode.ERROR_51001);

        Long userId = MemberService.getCurrentUserId();

        return ActionResult.createSuccess(userService.info(userId));
    }
    
    /**
     * 获取用户详细信息
     * <ul>
     * <li>必选：token{token}</li>
     * <li>url:/api/auth/user/detail</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/detail")
    public
    @ResponseBody
    ActionResult detail(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("access_token"), ErrorCode.ERROR_51001);

        Long userId = MemberService.getCurrentUserId();

        return ActionResult.createSuccess(userService.detail(userId));
    }

    /**
     * 更新用户详细信息
     * <ul>
     * <li>必选：token{token}</li>
     * <li>url:/api/auth/user/update</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/update")
    public
    @ResponseBody
    ActionResult update(final HttpServletRequest request, User user) {
        Validate.notNull(request.getParameter("access_token"), ErrorCode.ERROR_51002);

        Long userId = MemberService.getCurrentUserId();
        Validate.notNull(userId, ErrorCode.ERROR_51003);
        Validate.isTrue(userId.equals(user.getId()), ErrorCode.ERROR_51001);
        
        if(user.getLat() != 0f || user.getLng() != 0f)
        {
        	java.util.Date nowDate = new java.util.Date();
    		user.setLocModifyTime((Date) nowDate);
        }
        userService.update(user);
        return ActionResult.createSuccess();
    }

    /**
     * 更新用户密码
     * <ul>
     * <li>必选：token{token}</li>
     * <li>旧密码：oldpassword</li>
     * <li>新密码：newpassword</li>
     * <li>url:/api/auth/user/updatepassword</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/updatepassword")
    public
    @ResponseBody
    ActionResult updatePassword(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("access_token"), ErrorCode.ERROR_51001);

        Long userId = MemberService.getCurrentUserId();
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        if (!userId.equals(Long.valueOf(params.get("id").toString()))) {
            throw new BizValidateException(ErrorCode.ERROR_51001);
        }
        userService.updatePassword(params);
        return ActionResult.createSuccess();
    }
    

}
