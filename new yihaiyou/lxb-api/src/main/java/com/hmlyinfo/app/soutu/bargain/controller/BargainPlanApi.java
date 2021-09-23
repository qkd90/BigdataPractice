package com.hmlyinfo.app.soutu.bargain.controller;

import com.hmlyinfo.app.soutu.bargain.service.BargainPlanService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
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
@RequestMapping("/api/pub/bargain/plan")
public class BargainPlanApi {

    @Autowired
    BargainPlanService bargainPlanService;
    /**
     * 查询列表
     * <ul>
     * <li>可选：分页大小{pageSize=10}</li>
     * <li>可选：请求页码{page=1}</li>
     * <li>url:/api/pay/list</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public  ActionResult list(final HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

        return ActionResult.createSuccess(bargainPlanService.list(paramMap));
    }

    /**
     * 查询对象
     * <ul>
     * <li>必选:标识{id}</li>
     * <li>url:/api/pay/info</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ActionResult info(Long id) {
        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        return ActionResult.createSuccess(bargainPlanService.info(Long.valueOf(id)));
    }

    /**
     * 详情信息
     * <ul>
     * <li>必选:标识{id}</li>
     * <li>url:/api/pay/info</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ActionResult detail(Long id) {
        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        return ActionResult.createSuccess(bargainPlanService.detail(Long.valueOf(id)));
    }


}
