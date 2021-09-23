package com.hmlyinfo.app.soutu.account.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hmlyinfo.app.soutu.common.service.ICAPTCHAService;

@Controller
@RequestMapping("/passport/checkCodeAction/")
public class CaptchaApi{

	@Autowired
	private ICAPTCHAService captchaService;
	
    @RequestMapping("captcha")
    public void showCaptcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的响应是图片
        response.setContentType("image/jpeg");
        
        BufferedImage image = captchaService.createCAPTCHAImg(request.getSession(true), 200, 60);
        
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

}