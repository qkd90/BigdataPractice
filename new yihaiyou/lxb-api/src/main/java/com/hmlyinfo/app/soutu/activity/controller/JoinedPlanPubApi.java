package com.hmlyinfo.app.soutu.activity.controller;

import com.hmlyinfo.app.soutu.activity.service.JoinedPlanService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by guoshijie on 2014/12/25.
 */
@Controller
@RequestMapping("/api/pub/joinedplan")
public class JoinedPlanPubApi {

    @Autowired
    JoinedPlanService joinedPlanService;

    /**
     * 获取参赛作品的数量
     */
    @RequestMapping("count")
    @ResponseBody
    public ActionResult count(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        return joinedPlanService.countCurrent(paramMap);
    }

    /**
     * 获取参赛作品列表（默认pollCount降序排列）
     * <ul>
     *     <li>sortFlag:排序字段（createTime，modifyTime，pollCount）</li>
     *     <li>sortType：排序方式（asc：升序，desc：降序）</li>
     * </ul>
     */
    @RequestMapping("listDetail")
    @ResponseBody
    public ActionResult listDetail(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(joinedPlanService.listWithDetail(paramMap));
    }

}
