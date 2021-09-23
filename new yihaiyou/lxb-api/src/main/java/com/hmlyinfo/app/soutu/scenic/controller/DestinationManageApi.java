package com.hmlyinfo.app.soutu.scenic.controller;

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
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/16.
 */
@Controller
@RequestMapping("/api/manager/destination")
public class DestinationManageApi {

    @Autowired
    DestinationService destinationService;

    /**
     * 增加一个目的地
     *
     * @param request
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        //验证景点是否已存在目的地信息
        Validate.notNull(params.get("scenicId"), ErrorCode.ERROR_51001);
        List<Destination> list = destinationService.list(Collections.singletonMap("scenicId", params.get("scenicId")));
        Validate.isTrue(list == null || list.isEmpty(), ErrorCode.ERROR_52006);

        Destination destination = new Destination();
        destination.setScenicId(Long.valueOf(params.get("scenicId").toString()));
        destination.setName(params.get("name").toString());
        destination.setCodeName(params.get("codeName").toString());
        destination.setArea(params.get("area").toString());
        destination.setMonths(params.get("months").toString());
        destination.setStyles(params.get("styles").toString());
        destinationService.insert(destination);
        return ActionResult.createSuccess(destination);
    }

    /**
     * 更新一个目的地
     *
     * @param request
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(HttpServletRequest request) {
        //验证是否存在目的地信息
        Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
        Destination destination = destinationService.info(Long.valueOf(request.getParameter("id")));
        Validate.notNull(destination, ErrorCode.ERROR_52007);

        //验证景点是否已存在目的地信息
        Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
        List<Destination> list = destinationService.list(Collections.<String, Object>singletonMap("scenicId", request.getParameter("scenicId")));
        Validate.isTrue(list == null || list.isEmpty() || list.get(0).getId().equals(Long.valueOf(request.getParameter("id"))), ErrorCode.ERROR_52006);

        destination.setScenicId(Long.valueOf(request.getParameter("scenicId")));
        destination.setName(request.getParameter("name"));
        destination.setCodeName(request.getParameter("codeName"));
        destination.setArea(request.getParameter("area"));
        destination.setMonths(request.getParameter("months"));
        destination.setStyles(request.getParameter("styles"));
        destinationService.update(destination);
        return ActionResult.createSuccess(destination);
    }

    /**
     * 删除一个目的地
     *
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ActionResult delete(HttpServletRequest request) {
        //验证是否存在目的地信息
        Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
        destinationService.del(request.getParameter("id"));
        return ActionResult.createSuccess();
    }
}
