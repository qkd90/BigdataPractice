package com.hmlyinfo.app.soutu.scenic.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.GalleryImage;
import com.hmlyinfo.app.soutu.scenic.service.GalleryService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.ResultList;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/pub/gallery")
public class GalleryApi
{
	@Autowired
	private GalleryService service;
	/**
	 * 
	 * 系统列出景点相册图片。
	 * <ul>
	 *  <li>必选：景点id{scenicId}或相册id{galleryId}</li>
	 *  <li>必选：相册类型{category:cover,scene}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/gallery/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult getGallery(final HttpServletRequest request)
	{
		Validate.isTrue(request.getParameter("scenicId") != null || 
				request.getParameter("galleryId") != null, ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
				
		List<GalleryImage> resultList = service.getGallery(paramMap);
		
		return ActionResult.createSuccess(resultList);
	}
	
	/**
	 * 
	 * 系统列出景点相册数量。
	 * <ul>
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>url:/api/pub/gallery/count</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/count")
	public @ResponseBody ActionResult count(final HttpServletRequest request)
	{
		Validate.notNull(request, ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("operCount", true);
		int counts = service.countGalleryImgCount(paramMap);
		ActionResult result = new ActionResult();
		ResultList<Object> resultList = new ResultList<Object>();
		resultList.setCounts(counts);
		result.setResultList(resultList);
		
		return result;
	}
}
