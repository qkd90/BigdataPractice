package com.hmlyinfo.app.soutu.scenic.controller;

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
import java.util.Map;

@Controller
@RequestMapping("/api/pub/note")
public class NotePubApi
{
	@Autowired
	private NoteService service;

	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/note/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/note/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(final long id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001);
		
		return ActionResult.createSuccess(service.info(id));
	}
	
	/**
	 * 查询游记详细信息
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/note/detail</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/detail")
	public @ResponseBody ActionResult detail(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001);
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(service.detail(params));
	}

    /**
     * 查询游记评论
     * <ul>
     *     <li>必选：noteId 游记Id</li>
     *     <li>可选：noteImageId 游记图片Id</li>
     * </ul>
     */
    @RequestMapping("/comment/list")
    @ResponseBody
    public ActionResult listComment(HttpServletRequest request) {
        Validate.notNull(request.getParameter("noteId"));
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(service.listComment(params));
    }

    /**
     * 查询对整个游记的评论
     * <ul>
     * <li>必选：noteId 游记Id</li>
     * </ul>
     */
    @RequestMapping("/comment/listMain")
    @ResponseBody
    public ActionResult listMainComment(HttpServletRequest request) {
        Validate.notNull(request.getParameter("noteId"));
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(service.listMainComment(params));
    }

}
