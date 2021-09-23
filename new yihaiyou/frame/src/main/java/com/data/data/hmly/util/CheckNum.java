package com.data.data.hmly.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class CheckNum {

    private final int DEFAULT_WIDTH = 45; // 图片默认宽度
    private final int DEFAULT_HEIGHT = 19; // 图片默认高度
    private final int DEFAULT_BACKGROUND = 0xafcdff; // 默认背景色
    private final Color DEFAULT_BORDER = Color.black; // 默认边框颜色
    private final Color DEFAULT_FONT_COLOR = Color.black; // 默认字体颜色
    private final int CHECKNUM_COUNTS = 4; // 验证码数量
    private final int CONFUSE_POINT = 0; // 干扰点数量

    Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    // 验证码图片中可以出现的字符集,可根据需要修改
    private char mapTable[] = {
            // 'a','b','c','d','e','f',
            // 'g','h','i','j','k','l',
            // 'm','n','o','p','q','r',
            // 's','t','u','v','w','x',
            // 'y','z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 功能:生成彩色验证码图片 参数width为生成的图片的宽度,参数height为生成的图片的高度,参数os为页面的输出流
     */
    public String getCertPic(int width, int height, OutputStream os) {
        if (width <= 0)
            width = DEFAULT_WIDTH;
        if (height <= 0)
            height = DEFAULT_HEIGHT;

//        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int dpi = 96;
        float inch = (float) width / (float) dpi;
        Double perMm = (inch * 25.4) / 4;
        Double fontSize = (inch / CHECKNUM_COUNTS) * 25.4 * 2.843 * 2.4;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 填充背景色
        g.setColor(new Color(DEFAULT_BACKGROUND));
        // g.fillRect(0, 0, width, height);
        //g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 画边框
        g.setColor(DEFAULT_BORDER);
//	g.drawRect(0, 0, width - 1, height - 1);

        // 取随机产生的认证码
        String strEnsure = "";
        // 4代表4位验证码,如果要生成更多位的认证码,则加大数值
        for (int i = 0; i < CHECKNUM_COUNTS; ++i) {
            strEnsure += mapTable[(int) (mapTable.length * Math.random())];
        }

        // 将认证码显示到图象中,如果要生成更多位的认证码,增加drawString语句
        // 设置字体类型和大小
        g.setColor(DEFAULT_FONT_COLOR);
        // g.setFont(new Font("Atlantic Inline",Font.PLAIN,17));
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize.intValue()));
        String str = "";
        for (int j = 0; j < CHECKNUM_COUNTS; j++) {
            str = strEnsure.substring(j, j + 1);
            int x = (int) (j * perMm * 2 + (j + 1) * 5 );
            int y = (int) (height * 0.8);
            g.drawString(str, x, y);
        }

//	String str = strEnsure.substring(0, 1);
//	g.drawString(str, 5, 32);
//	str = strEnsure.substring(1, 2);
//	g.drawString(str, 20, 32);
//	str = strEnsure.substring(2, 3);
//	g.drawString(str, 36, 32);
//	str = strEnsure.substring(3, 4);
//	g.drawString(str, 52, 32);
//	str = strEnsure.substring(4, 5);
//	g.drawString(str, 42, 15);
//	str = strEnsure.substring(5, 6);
//	g.drawString(str, 52, 15);

        // 随机产生10个干扰点
        Random rand = new Random();
        for (int i = 0; i < CONFUSE_POINT; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            g.drawOval(x, y, 1, 1);
        }

        // 释放图形上下文
        g.dispose();

        try {
            // 输出图象到页面
            ImageIO.write(image, "JPEG", os);
        } catch (IOException e) {
            return "";
        }
        return strEnsure;
    }

}
