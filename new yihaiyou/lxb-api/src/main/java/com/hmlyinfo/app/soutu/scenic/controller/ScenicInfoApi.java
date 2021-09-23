package com.hmlyinfo.app.soutu.scenic.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/pub/scenicinfo")
public class ScenicInfoApi
{
	@Autowired
	private ScenicInfoService service;


	/**
	 * 
	 * 系统根据景点id查询景点相关信息
	 * <ul>
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>url:/api/pub/scenicinfo/info</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult infoDetail(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(service.info(
				Long.valueOf(request.getParameter("scenicId"))));
	}
	
	/**
	 * 
	 * 系统列出景点信息(一句话评论，评论人，评级，建议游玩时间，最佳游玩时间，开放时间，票价，封面图片，url)
	 *   系统列出所选城市所有景点（城市名）
	 *   系统列出游客查询景点（景点名）
	 * <ul>
	 *  <li>可选：城市区号{cityCode}（二选一）</li>
	 *  <li>可选：景点名{name}（二选一）</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>可选：排序字段{orderColumn}</li>
	 * 	<li>可选：好评降序{scoreFlag}</li>
	 * 	<li>可选：价格降序{priceFlag}</li>
	 * 	<li>可选：行程id{planId}</li>
	 * 	<li>url:/api/pub/scenicinfo/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult listScenic(final HttpServletRequest request) {	
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("cityCode") != null ||paramMap.get("name") != null || 
				paramMap.get("father") != null, ErrorCode.ERROR_51001);
        return ActionResult.createSuccess(service.getScenicBrief(paramMap));
	}

    @RequestMapping("/listnolimit")
    @ResponseBody
    public ActionResult listByIds(final HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        if (paramMap.containsKey("ids")) {
            return ActionResult.createSuccess(service.listBrief(paramMap));
        }
        return ActionResult.createSuccess(service.getScenicBrief(paramMap));
    }


    @RequestMapping("/listForMap")
	public @ResponseBody ActionResult listScenicForMap(final HttpServletRequest request) {	
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("cityCode") != null ||paramMap.get("name") != null || 
				paramMap.get("father") != null, ErrorCode.ERROR_51001);
        return ActionResult.createSuccess(service.listScenicForMap(paramMap));
	}
		
	
	/**
	 * 
	 * 系统列出景点内部景点
	 * <ul>
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/scenicinfo/listchild</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/listchild")
	public @ResponseBody ActionResult listChild(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.remove("scenicId");
		paramMap.put("father", request.getParameter("scenicId"));
		return ActionResult.createSuccess(service.getScenicBrief(paramMap));
	}
	
	/**
	 * 
	 * 简单的查询景点的信息
	 * <ul>
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/scenicinfo/listsimple</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/listsimple")
	public @ResponseBody ActionResult listSimple(final HttpServletRequest request) {
		
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("cityCode") != null || paramMap.get("sname") != null, ErrorCode.ERROR_51001);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 
	 * 系统根据景点名称判断是否是已有景点
	 * <ul>
	 *  <li>必选：景点名{name}</li>
	 * 	<li>url:/api/pub/scenicinfo/check</li>
	 * </ul>
	 * 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/check")
	public @ResponseBody ActionResult checkScenic(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("name"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		List<ScenicInfo> scenicInfo = service.list(paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ifHasScenic", (scenicInfo.size() > 0));
		return ActionResult.createSuccess(resultMap);
	}
	
	/**
	 * 
	 * 游客模糊查询景点一览
	 * <ul>
	 *  <li>必选：景点名/景点拼音{name}(二选一)</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/scenicinfo/listname</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/listname")
	public @ResponseBody ActionResult listName(final HttpServletRequest request) {
		Validate.isTrue(request.getParameter("name") != null, ErrorCode.ERROR_51001);
		Validate.isTrue(request.getParameter("cityCode") != null, ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		List<Map<String, Object>> resultList = service.listName(paramMap);
		return ActionResult.createSuccess(resultList);
	}
	
	/**
	 * 
	 * 系统查询当前城市景点总数
	 * <ul>
	 *  <li>必选：城市区号{cityCode}</li>
	 * 	<li>url:/api/pub/scenicinfo/count</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/count")
	public @ResponseBody ActionResult cityCount(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("cityCode"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return service.countAsResult(paramMap);
	}
	
	/**
	 * 
	 * 系统查询景点其他信息
	 * <ul>
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>url:/api/pub/scenicinfo/infoother</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/infoother")
	public @ResponseBody ActionResult infoOther(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.infoOther(paramMap));
	}

    @RequestMapping("/listByPosition")
    @ResponseBody
    public ActionResult listByPosition(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(service.listByPosition(params));
    }

	@RequestMapping("/listTopic")
	@ResponseBody
	public ActionResult listTopic(HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.listTopic(paramMap));
	}

	@RequestMapping("/listTopicScenic")
	@ResponseBody
	public ActionResult listTopicScenic(HttpServletRequest request) {
		Validate.notNull(request.getParameter("topicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.listTopicScenic(paramMap));
	}
	
	
	@RequestMapping("/countAmountAndPlayTime")
	@ResponseBody
	public ActionResult countAmountAndPlayTime(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.notNull(paramMap.get("tripJSON"), ErrorCode.ERROR_51001);
		Map<String,Object> resultMap = service.countAmountAndPlayTime(paramMap);
		return ActionResult.createSuccess(resultMap);
	}
	
	@RequestMapping("/listbyids")
	@ResponseBody
	public ActionResult listScenicByIds(HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("ids"), ErrorCode.ERROR_51001, "景点ids不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	@RequestMapping("/updategcj")
	@ResponseBody
	public ActionResult updateGcj()
	{
		service.updateGcj();
		return ActionResult.createSuccess();
	}
	
}
