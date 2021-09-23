package com.hmlyinfo.app.soutu.common.controller;

import com.hmlyinfo.base.image.ImageService;
import com.hmlyinfo.base.util.UUIDUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by guoshijie on 2014/9/12.
 */
@Controller
@RequestMapping("/image")
public class ImageApi {

    @Autowired
    ImageService imageService;

    @RequestMapping("/upload")
    @ResponseBody
    public String uploadImage(MultipartFile file, String path) {
        try {
            return imageService.saveImage(file.getBytes(), path, UUIDUtil.getUUID() + ".jpg");
        } catch (IOException e) {
            return null;
        }

    }
    @RequestMapping("/uploadBase64")
    @ResponseBody
    public String upload(String file,String path) throws IOException, ServletException {

        byte[] result = GenerateImage(file.substring(file.indexOf("base64,") + 7));
        try {
            return imageService.saveImage(result, path, UUIDUtil.getUUID() + ".jpg");
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] GenerateImage(String imgStr) {//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return null;
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }
}
