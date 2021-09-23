package com.hmlyinfo.app.soutu.scenic.controller;

import com.google.common.collect.ImmutableMap;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.Destination;
import com.hmlyinfo.app.soutu.scenic.service.DestinationService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/15.
 */
@Controller
@RequestMapping("/api/pub/destination")
public class DestinationApi {

    @Autowired
    DestinationService destinationService;

    /**
     * 通过具体条件进行筛选
     * <p/>
     * <ul>精确查询
     *  <li>可选：id</li>
     *  <li>可选：scenicId 景点id</li>
     *  <li>可选：name 名称</li>
     *  <li>可选：codeName 拼音</li>
     *  <li>可选：area 地区</li>
     *  <li>可选：language 语言</li>
     *  <li>可选：daysRecommend 建议游玩天数</li>
     *  <li>可选：consumer 消费指数</li>
     *  <url>/api/pub/destination/list</url>
     *  </ul>
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        List<Destination> list = destinationService.list(params);
        return ActionResult.createSuccess(list);
    }

    /**
     * 通过name/codeName(拼音)/area/months/styles进行模糊筛选，只支持从头往后匹配
     *
     * <ul>
     *  <li>可选：months 月份，以逗号隔开，例如（1,2,3,）</li>
     *  <li>可选：style 主题，以逗号隔开，例如（1,2,3,）</li>
     *  <li>可选：season 季节</li>
     *  <li>可选：key 查询关键字，模糊匹配</li>
     *  <url>/api/pub/destination/listLike</url>
     * </ul>
     * @return
     */
    @RequestMapping("/listLike")
    @ResponseBody
    public ActionResult listLike(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        List<Destination> list = destinationService.listLike(params);

        return ActionResult.createSuccess(list);
    }
    
    /**
     * 目的地详细信息
     *
     * <ul>
     *  <li>必选：目的地ID或景点ID{id&scenicId}</li>
     *  <li>可选：日期{date}(用于查询目的地当天的天气)</li>
     * </ul>
     * <url>/api/pub/destination/detailList</url>
     * @return
     */
    @RequestMapping("/detailList")
    public @ResponseBody ActionResult detailList(final HttpServletRequest request) {
    	Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
    	Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
    	return ActionResult.createSuccess(destinationService.detailList(paramMap));
    }
    
    /**
     * 根据cityCode查询scenicId
     *
     * <ul>
     *  <li>必选：cityCode{cityCode}</li>
     * </ul>
     * <url>/api/pub/destination/getscenicid</url>
     * @return
     */
    @RequestMapping("/getscenicid")
    public @ResponseBody ActionResult getId(final HttpServletRequest request) {
    	Validate.notNull(request.getParameter("cityCode"), ErrorCode.ERROR_51001);
    	Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
    	List<Destination> des = destinationService.list(paramMap);
    	if(des.isEmpty()){
    		return ActionResult.createSuccess();
    	}
    	long scenicId = des.get(0).getScenicId();
    	return ActionResult.createSuccess(ImmutableMap.of("scenicId", scenicId));
    }
}
