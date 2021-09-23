package com.hmlyinfo.app.soutu.hotel.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.hotel.service.CtripImageService;
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
@RequestMapping("/api/pub/hotel/image")
public class CtripImageApi {

    @Autowired
    CtripImageService ctripImageService;

    /**
     * 查询图片列表
     * <ul>
     *     <li>可选：hotelId</li>
     * </ul>
     */
    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(ctripImageService.listImg(params));
    }

    /**
     * 查询图片分类数量
     * <ul>
     *     <li>可选：hotelId</li>
     * </ul>
     */
    @RequestMapping("/counttype")
    @ResponseBody
    public ActionResult countType(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(ctripImageService.countByType(params));
    }

    /**
     * 查询图片数量
     * <ul>
     *     <li>可选：hotelId</li>
     * </ul>
     */
    @RequestMapping("/count")
    @ResponseBody
    public ActionResult count(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ctripImageService.imgCount(params);
    }

    /**
     * 查询图片
     * <ul>
     *     <li>必选：id</li>
     * </ul>
     */
    @RequestMapping("/info")
    @ResponseBody
    public ActionResult info(HttpServletRequest request) {
        Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
        return ActionResult.createSuccess(ctripImageService.info(Long.valueOf(request.getParameter("id"))));
    }

    /**
     * 查询所有图片信息
     * <ul>
     *     <li>可选：hotelId</li>
     * </ul>
     */
    @RequestMapping("/listall")
    @ResponseBody
    public ActionResult listAll(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(ctripImageService.listAll(params));
    }

}
