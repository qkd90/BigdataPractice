package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.UUIDUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对组件bootstrap-fileinput v4.3.6上传后台方法
 *
 * @author caiys
 * @date 2015年10月28日 下午2:23:22
 */
public class FileInputUploadAction extends FrameBaseAction {
    private static final long serialVersionUID = -7464481432040569711L;
    private File resource;
    private String resourceFileName;
    private String resourceContentType;    // image/jpeg
    private String oldFilePath;    // 旧图片路径
    private String childFolder;        // 图片目录
    @Resource
    private PropertiesManager propertiesManager;

    // 返回数据
    Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 图片上传
     *
     * @return
     * @author caiys
     * @date 2015年10月30日 上午9:01:12
     */
    @AjaxCheck
    public Result uploadImg() {
        try {
            if (checkImageFileType()) {
                String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
                String uuid = UUIDUtil.getUUID();
                String newFileName = uuid + suffix.toLowerCase();   // 真实图片路径
                String displayFileName = uuid + suffix.toLowerCase();   // 用于临时显示的图片路径
                // 是否配置图片水印
                String watermarkOps = propertiesManager.getString("WATERMARK_OPS");
                if (StringUtils.isNotBlank(watermarkOps)) {
                    newFileName = uuid + QiniuUtil.WATERMARK_SUFFIX + suffix.toLowerCase(); // 水印文件后缀，文件转码成功后才能正常显示
                }
                if (StringUtils.isBlank(childFolder)) {
                    childFolder = "";
                }
                String newFilePath = childFolder + newFileName;
                String displayFilePath = childFolder + displayFileName;
                if (StringUtils.isNotBlank(watermarkOps)) {
                    QiniuUtil.upload(resource, displayFilePath, watermarkOps, newFilePath);
                } else {
                    QiniuUtil.upload(resource, newFilePath);
                }
                List<String> initialPreview = new ArrayList<String>();
                initialPreview.add(QiniuUtil.URL + displayFilePath);
                // 预览信息
                Map<String, Object> extra = new HashMap<String, Object>();
                extra.put("proType", (String) getParameter("proType"));
                extra.put("childFolder", (String) getParameter("childFolder"));
                extra.put("path", newFilePath);
                extra.put("coverFlag", false);
//                extra.put("showOrder", );
                Map<String, Object> cfg = new HashMap<String, Object>();
                cfg.put("key", newFilePath);    // displayFilePath
                cfg.put("homeflag", false);
                cfg.put("extra", extra);
                List<Object> initialPreviewConfig = new ArrayList<Object>();
                initialPreviewConfig.add(cfg);
                map.put("initialPreview", initialPreview);
                map.put("initialPreviewConfig", initialPreviewConfig);
            } else {
                map.put("error", "图片格式不正确");
            }
        } catch (Exception e) {
            map.put("error", "图片上传失败");
            e.printStackTrace();
        }
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 新上传文件异步删除
     *
     * @return
     * @author caiys
     * @date 2015年10月30日 上午9:01:22
     */
    @AjaxCheck
    public Result delFile() {
        try {
            String id = (String) getParameter("id");
            if (StringUtils.isBlank(id)) {
                String filePath = (String) getParameter("key");
                QiniuUtil.delete(filePath);
            }
        } catch (Exception e) {
            map.put("error", "图片删除失败");
            e.printStackTrace();
        }
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    public Result deleteFile() {
        QiniuUtil.delete("contract/appendice/1479200474865.xlsx");
        return text("操作成功");
    }


    /**
     * 检查是否是图片格式
     *
     * @return
     * @author caiys
     * @date 2015年10月28日 下午3:19:01
     */
    public boolean checkImageFileType() {
        // 检查文件后缀名
        if (StringUtils.isBlank(resourceFileName)) {
            return false;
        }
        String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
        if (!".jpg".equalsIgnoreCase(suffix) && !".png".equalsIgnoreCase(suffix) && !".gif".equalsIgnoreCase(suffix)) {
            return false;
        }

        if (StringUtils.isBlank(resourceContentType)) {
            return false;
        }
        return resourceContentType.startsWith("image/");
    }


    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public String getResourceFileName() {
        return resourceFileName;
    }

    public void setResourceFileName(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    public String getResourceContentType() {
        return resourceContentType;
    }

    public void setResourceContentType(String resourceContentType) {
        this.resourceContentType = resourceContentType;
    }

    public String getOldFilePath() {
        return oldFilePath;
    }

    public void setOldFilePath(String oldFilePath) {
        this.oldFilePath = oldFilePath;
    }

    public String getChildFolder() {
        return childFolder;
    }

    public void setChildFolder(String childFolder) {
        this.childFolder = childFolder;
    }
}
