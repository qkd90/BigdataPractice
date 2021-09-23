package com.data.data.hmly.action.lvxbang;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/1/8.
 */
public class UploadWebAction extends LxbAction {

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
        if (StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("section"))) {
            section = multiPartRequestWrapper.getParameter("section");
        } else {
            throw new RuntimeException("图片存储路径缺少类别名称, 无法存储, 请检查!");
        }
        path = section + "/" + UUIDUtil.getUUID() + suffix;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage != null) {
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();
            } else {
                throw new RuntimeException("无法读取图片! 需要重新上传!");
            }

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
}
