package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.common.util.UUIDUtil;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zzl on 2017/2/8.
 */
public class UploadWebAction extends YhyAction {
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", ".jpg"); //JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", ".png"); //PNG (png)
        FILE_TYPE_MAP.put("47494638396126026f01", ".gif"); //GIF (gif)
    }

    public Result imageUpload() {
        final HttpServletRequest request = getRequest();
        MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
        Map<String, Object> result = new HashMap<String, Object>();
        File file = null;
        String suffix = "";
        String name = "";
        String section = "";
        String path = "";
        Integer width = 0;
        Integer height = 0;
        Integer fileQuenuLength = multiPartRequestWrapper.getFiles("file").length;
        if (fileQuenuLength != 1) {
            throw new IndexOutOfBoundsException("没有需要上传的文件或队列中文件个数太多! find: " + fileQuenuLength + "allowed: 1");
        } else {
            file = multiPartRequestWrapper.getFiles("file")[0];
        }
        if (StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("name"))) {
            name = multiPartRequestWrapper.getParameter("name");
            suffix = name.substring(name.lastIndexOf("."));
        } else {
            throw new RuntimeException("图片缺少文件名, 无法存储! 请检查!");
        }
        if (StringUtils.isBlank(suffix)) {
            throw new RuntimeException("图片缺少格式, 无法存储! 请检查!");
        }
        if (!FILE_TYPE_MAP.values().contains(suffix)) {
            throw new RuntimeException("图片格式错误, 无法存储! 请检查!");
        }
        if (StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("section"))) {
            section = multiPartRequestWrapper.getParameter("section");
        } else {
            throw new RuntimeException("图片存储路径缺少类别名称, 无法存储, 请检查!");
        }
        path = section + "/" + UUIDUtil.getUUID() + suffix;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null || bufferedImage.getWidth(null) <= 0 || bufferedImage.getHeight(null) <= 0) {
                throw new RuntimeException("无法读取图片! 需要重新上传!");
            }

//            String fileType = getFileType(file);
//            if (fileType == null) {
//                throw new RuntimeException("无法读取图片! 需要重新上传!");
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        QiniuUtil.upload(file, path);
        result.put("path", path);
        result.put("width", width);
        result.put("height", height);
        result.put("name", name);
        return json(JSONObject.fromObject(result));
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private String getFileType(File file){
        String res = null;
        try {
            FileInputStream is = new FileInputStream(file);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);

            System.out.println(fileCode);


            //这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while(keyIter.hasNext()){
                String key = keyIter.next();
                if(key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())){
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
