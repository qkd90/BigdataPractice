package com.hmlyinfo.app.soutu.scenic.controller;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.image.ImageService;
import com.hmlyinfo.base.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by guoshijie on 2014/9/18.
 */
@Controller
@RequestMapping("api/pub/note/image")
public class NoteImagePubApi {

    @Autowired
    ImageService imageService;

    @RequestMapping("/upload")
    @ResponseBody
    public ActionResult uploadImage(MultipartFile file) {
        try {
            String imageUrl = imageService.saveImage(file.getBytes(), "/note/sign", UUIDUtil.getUUID() + ".jpg");
            return ActionResult.createSuccess(imageUrl);
        } catch (IOException e) {
            ActionResult result = new ActionResult();
            result.setErrorCode(-1);
            return result;
        }

    }
}
