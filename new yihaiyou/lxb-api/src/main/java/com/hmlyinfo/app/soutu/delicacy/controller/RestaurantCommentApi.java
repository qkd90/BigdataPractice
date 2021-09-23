package com.hmlyinfo.app.soutu.delicacy.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.service.DianPingService;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantCommentService;
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
@RequestMapping("/api/pub/restaurantcomment")
public class RestaurantCommentApi {
    @Autowired
    private RestaurantCommentService service;
    @Autowired
	private DianPingService dianpingService;

    /**
     * 查询列表
     * <ul>
     * <li>可选：分页大小{pageSize=10}</li>
     * <li>可选：请求页码{page=1}</li>
     * <li>url:/api/restaurantcomment/list</li>
     * </ul>
     *
     */
    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(final HttpServletRequest request) {

        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

        return ActionResult.createSuccess(service.list(paramMap));
    }

    /**
     * 查询数量
     * <ul>
     * <li>url:/api/restaurantcomment/count</li>
     * </ul>
     */
    @RequestMapping("/count")
    @ResponseBody
    public ActionResult count(final HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

        return service.countAsResult(paramMap);
    }

    /**
     * 查询对象
     * <ul>
     * <li>必选:标识{id}</li>
     * <li>url:/api/restaurantcomment/info</li>
     * </ul>
     *
     */
    @RequestMapping("/info")
    @ResponseBody
    public ActionResult info(final String id) {

        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        return ActionResult.createSuccess(service.info(Long.valueOf(id)));
    }


    @RequestMapping("/insertcom")
	public void insertCom() {
        dianpingService.insertTest();
    }
}
