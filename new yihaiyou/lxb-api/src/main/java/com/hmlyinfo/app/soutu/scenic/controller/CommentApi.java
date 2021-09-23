package com.hmlyinfo.app.soutu.scenic.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.Comment;
import com.hmlyinfo.app.soutu.scenic.service.CommentService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;


@Controller
@RequestMapping("/api")
public class CommentApi
{
	@Autowired
	private CommentService service;
	
	/**
	 * <ul>
	 * 系统列出评论缩略信息。传入景点scenicId，默认以点赞数排序。
	 * 输出Comment类中，imageList图片列表，ifCanGood是否还能点赞
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/comment/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/pub/comment/list")
	public @ResponseBody ActionResult getComment(HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		List<Map<String, Object>> commentList = service.getComment(paramMap);
		
		return ActionResult.createSuccess(commentList);
	}
	
	/**
	 * <ul>
	 * 查询评论的数量
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>url:/api/pub/comment/count</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/pub/comment/count")
	public @ResponseBody ActionResult countComment(String scenicId)
	{
		Validate.notNull(scenicId, ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("scenicId", scenicId);
		
		return service.countAsResult(paramMap);
	}
	
	/**
	 * 游客对评论点“有用”
	 *  <li>必选：评论Id{commentId}</li>
	 * 	<li>url:/api/auth/comment/insertgood</li>
	 *
	 * @return
	 */
	@RequestMapping("/auth/comment/insertgood")
	public @ResponseBody void commentGood(final HttpServletRequest request, long commentId, long commentUserId)
	{
		Validate.notNull(commentId, ErrorCode.ERROR_51001);
		Validate.notNull(commentUserId, ErrorCode.ERROR_51001);
		service.userGood(commentId,commentUserId);
	}
	
	/**
	 * 插入评论
	 * 还需要加入审核
	 *  <li>必选：scenicId</li>
	 * 	<li>url:/api/auth/comment/new</li>
	 * @return
	 */
	@RequestMapping("/auth/comment/new")
	public @ResponseBody ActionResult New(HttpServletRequest request){
		
		//验证传入参数是否为空
		String scenicId = request.getParameter("scenicId");
		String content = request.getParameter("content");
		Validate.notNull(scenicId, ErrorCode.ERROR_51001);
		Validate.notNull(content, ErrorCode.ERROR_51001);
		
		Comment comment = new Comment();
		comment.setScenicId(Long.valueOf(scenicId));
		comment.setContent(content);
		Long userId = MemberService.getCurrentUserId();
		comment.setUserId(userId);
		service.insert(comment);
		
		return ActionResult.createSuccess(comment);
	}
	
	/**
	 * 插入图片
	 *  <li>必选：file图片文件,commentId</li>
	 * 	<li>url:/api/auth/comment/newimage</li>
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("pub/comment/newimage")
	public @ResponseBody ActionResult newImage(HttpServletRequest request) throws IOException{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		Validate.notNull(paramMap, ErrorCode.ERROR_51001);
		
		//取得返回的路径和commentId
		String filepath = paramMap.get("filePath").toString();
		Long commentId = Long.valueOf(paramMap.get("commentId").toString());
		
		//插入数据库
		String filePath = service.newImage(filepath, commentId);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("path", filePath);
		
		return ActionResult.createSuccess(resultMap);
	}
	
	/**
	 * 删除某一条评论
	 * <ul>
	 * 	<li>必选:评论id{commentId}</li>
	 *  <li>url:/api/auth/comment/del</li>
	 * </ul>
	 * @return 
	 */
	@RequestMapping("/auth/comment/del")
	public @ResponseBody  ActionResult delCom(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("commentId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		boolean ifDeleted = service.delCom(paramMap);
		
		return ActionResult.createSuccess(ifDeleted);
	}
	
	
}
