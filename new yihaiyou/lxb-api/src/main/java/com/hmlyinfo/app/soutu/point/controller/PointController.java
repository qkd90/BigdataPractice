package com.hmlyinfo.app.soutu.point.controller;

import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.point.domain.Point;
import com.hmlyinfo.app.soutu.point.domain.PointHistory;
import com.hmlyinfo.app.soutu.point.service.PointHistoryService;
import com.hmlyinfo.app.soutu.point.service.PointService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/15.
 */
@Controller
@RequestMapping("api/auth/point")
public class PointController {

    @Autowired
    PointService pointService;
    @Autowired
    PointHistoryService pointHistoryService;
    @Autowired
    UserService userService;

    /**
     * 给用户增加积分
     *
     * @param request
     * @return url:/api/auth/point/addPoint
     */
    @RequestMapping("/addPoint")
    @ResponseBody
    public ActionResult addPoint(HttpServletRequest request) {

        Validate.notNull(request.getParameter("user_id"), ErrorCode.ERROR_51001);
        Validate.notNull(request.getParameter("type"), ErrorCode.ERROR_51001);

        Long userId = Long.valueOf(request.getParameter("user_id"));
        PointHistory.PointType pointType = PointHistory.PointType.nameOf(Integer.parseInt(request.getParameter("type")));

        int point = pointHistoryService.addPointHistory(userId, pointType);
        Validate.isTrue(point > 0, ErrorCode.ERROR_54001);
        pointService.add(userId, point);
        return ActionResult.createSuccess();
    }

    /**
     * 查询用户积分
     *
     * @param request
     * @return 用户积分
     * url:/api/auth/point/getPoint
     */
    @RequestMapping("/getPoint")
    @ResponseBody
    public ActionResult getPoint(HttpServletRequest request) {

        Validate.notNull(request.getParameter("user_id"), ErrorCode.ERROR_51001);

        Point point = pointService.info(Long.valueOf(request.getParameter("user_id")));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("point", point.getPoint());
        return ActionResult.createSuccess(map);
    }
}
