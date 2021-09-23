package com.hmlyinfo.app.soutu.signet.service;   
  
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.stereotype.Service;
  
/**  
 * 图片加水印，设置透明度
 */ 
@Service
public class ImageMarkLogoByIconService {   
  
      
    /**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印位置x,y
     * @param degree 水印大小w,h
     * @param degree 水印图片旋转角度
     * @param degree 水印图片透明度
     */  
    public static void markImageByIcon(Map<String, Object> paramMap) {
    	String iconPath = (String) paramMap.get("iconPath");
    	String srcImgPath = (String) paramMap.get("srcImgPath");
    	String targerPath = (String) paramMap.get("targerPath");
        Integer x = Integer.parseInt((String) paramMap.get("x"));
        Integer y = Integer.parseInt((String) paramMap.get("y"));
        Integer w = Integer.parseInt((String) paramMap.get("w"));
        Integer h = Integer.parseInt((String) paramMap.get("h"));
        Integer degree;
        if(paramMap.get("degree") == null){
        	degree = 0;
        }else{
        	degree = Integer.parseInt((String) paramMap.get("degree"));
        }
        float alpha; // 透明度   
        if(paramMap.get("alpha") == null){
        	alpha = 1f;
        }else{
        	alpha = Float.parseFloat((String) paramMap.get("alpha"));
        }
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath)); 
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB); 
            // 得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree),   
                        (double) buffImg.getWidth() / 2, (double) buffImg   
                                .getHeight() / 2);   
            }   
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度  
            ImageIcon imgIcon = new ImageIcon(iconPath);   
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
            
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                    alpha));   
            // 表示水印图片的位置   
            g.drawImage(img, x, y, w, h, null);   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
            g.dispose();   
            os = new FileOutputStream(targerPath);   
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
} 