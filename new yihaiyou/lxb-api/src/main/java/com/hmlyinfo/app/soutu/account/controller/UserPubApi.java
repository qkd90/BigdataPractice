package com.hmlyinfo.app.soutu.account.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.OAuthServerService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

/**
 * Created by guoshijie on 2014/7/25.
 */
@Controller
@RequestMapping("/api/pub/user")
public class UserPubApi {

    @Autowired
    private UserService userService;
    @Autowired
	private OAuthServerService oauthService;

    
    /**
     * 查询用户概要信息，只返回ID、昵称、头像
     * <ul>
     * <li>必选：userId</li>
     * <li>必选(注册时为可选)：userId</li>
     * <li>url:/api/pub/user/summaryinfo</li>
     * </ul>
     * @return
     */
    @RequestMapping("/summaryinfo")
    public @ResponseBody ActionResult summaryinfo(String userId)
    {
    	Validate.notNull(userId, ErrorCode.ERROR_51001);
    	User user = userService.summaryinfo(Long.valueOf(userId));
    	
    	return ActionResult.createSuccess(user);
    }
    
    /**
     * 批量查询用户简要信息
     * <ul>
     * <li>可选：userId</li>
     * <li>可选：ids</li>
     * <li>url:/api/pub/user/list</li>
     * </ul>
     * @return
     */
    @RequestMapping("/list")
    public @ResponseBody ActionResult list(final HttpServletRequest request)
    {
    	Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
    	return ActionResult.createSuccess(userService.list(paramMap));
    }
    
    /**
     * 验证用户名是否被使用过
     * <ul>
     * <li>必选：nickname</li>
     * <li>必选(注册时为可选)：userId</li>
     * <li>url:/api/pub/user/info</li>
     * </ul>
     * @return
     */
    @RequestMapping("/validatenickname")
    @ResponseBody
    public ActionResult validatenickname(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("nickname"), ErrorCode.ERROR_51001);
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        List<User> result = userService.list(params);
        if (result.isEmpty()) {
            return ActionResult.createSuccess();
        }
        Validate.notNull(request.getParameter("userId"), ErrorCode.ERROR_51001);
        if (result.get(0).getId().equals(Long.valueOf(params.get("userId").toString()))) {
            return ActionResult.createSuccess();
        }
        throw new BizValidateException(ErrorCode.ERROR_51009);
    }

    /**
     * 获取用户详细信息
     * <ul>
     * <li>必选：token{token}</li>
     * <li>url:/api/pub/user/info</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/detail")
    public
    @ResponseBody
    ActionResult info(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("userId"), ErrorCode.ERROR_51001);
        Long userId = Long.valueOf(request.getParameter("userId"));

        return ActionResult.createSuccess(userService.detail(userId));
    }
    
    
    /**
     * 根据openId和type获取用户详细信息
     * <ul>
     * <li>必选：openId{openId}</li>
     * <li>必选：bindType{bindType\}</li>
     * <li>url:/api/pub/user/infoopenid</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/infobyopenid")
    public
    @ResponseBody
    ActionResult infoByOpenid(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("openId"), ErrorCode.ERROR_51001);
        Validate.notNull(request.getParameter("bindType"), ErrorCode.ERROR_51001);
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(oauthService.getUserByOpenId(paramMap));
    }
    
    /**
     * 更新经纬度信息
     * <ul>
     * <li>必选：用户id{id}</li>
     * <li>必选：精度{lng}</li>
     * <li>必选：纬度{lat}</li>
     * <li>url:/api/pub/user/updatelocation</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/updatelocation")
    public
    @ResponseBody
    ActionResult updateLocation(final HttpServletRequest request) {
        Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
        Validate.notNull(request.getParameter("lng"), ErrorCode.ERROR_51001);
        Validate.notNull(request.getParameter("lat"), ErrorCode.ERROR_51001);
        User user = new User();
        user.setId(Long.valueOf(request.getParameter("id")));
		user.setLng(Double.valueOf(request.getParameter("lng")));
		user.setLat(Double.valueOf(request.getParameter("lat")));
		Date nowDate = new Date();
		user.setLocModifyTime(nowDate);
        userService.update(user);
        return ActionResult.createSuccess();
    }
    
    /**
     * 查询附近用户列表
    * <ul>
    * <li>必选：精度{lng}</li>
    * <li>必选：维度{lat}</li>
    * <li>url:/api/auth/user/listaround</li>
    * </ul>
    * 
    * @return
    */
	@RequestMapping("/listaround")
	public @ResponseBody ActionResult listAround(final HttpServletRequest request) 
	{
		Validate.notNull(request.getParameter("lng"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("lat"), ErrorCode.ERROR_51001);
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(userService.listAround(params));
	}
}
