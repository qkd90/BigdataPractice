package com.hmlyinfo.app.soutu.scenic.controller;

import com.hmlyinfo.app.soutu.scenic.domain.NoteImage;
import com.hmlyinfo.app.soutu.scenic.service.NoteImageService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.image.ImageService;
import com.hmlyinfo.base.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by guoshijie on 2014/9/17.
 */
@Controller
@RequestMapping("/api/auth/note/image")
public class NoteImageApi {

    @Autowired
    NoteImageService noteImageService;
    @Autowired
    ImageService imageService;

    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(noteImageService.list(paramMap));
    }

    /**
     * <ul>
     *     <li>必选：行程节点id tripId</li>
     *     <li>必选：图片地址 address</li>
     * </ul>
     * @param request
     * @return
     */
    @RequestMapping("/insertTripImage")
    @ResponseBody
    public ActionResult insert(HttpServletRequest request) {
        NoteImage noteImage = new NoteImage();
        noteImage.setPlanTripId(Long.valueOf(request.getParameter("tripId")));
        noteImage.setScenicId(Long.valueOf(request.getParameter("scenicId")));
        noteImage.setAddressLarge(request.getParameter("address"));
        noteImage.setImageWidth(Integer.parseInt(request.getParameter("width")));
        noteImage.setImageHeight(Integer.parseInt(request.getParameter("height")));
        noteImageService.insert(noteImage);
        return ActionResult.createSuccess();
    }
}
