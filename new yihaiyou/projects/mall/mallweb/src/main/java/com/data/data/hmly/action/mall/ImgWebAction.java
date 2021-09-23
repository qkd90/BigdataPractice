package com.data.data.hmly.action.mall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtils;
import com.zuipin.util.PropertiesManager;

/**
 * 文件管理，上传、删除、预览
 * @author caiys
 * @date 2015年10月28日 下午2:23:22
 */
public class ImgWebAction extends MallAction {
	private static final long serialVersionUID = -7464481432040569710L;
	private File resource;
	private String resourceFileName;
	private String resourceContentType;	// image/jpeg
	private String oldFilePath;	// 旧图片路径
	private String folder;		// 图片目录
	@Resource
	private PropertiesManager propertiesManager;

	// 返回数据
	Map<String, Object>			map					= new HashMap<String, Object>();
	
	/**
	 * 图片上传
	 * @author caiys
	 * @date 2015年10月30日 上午9:01:12
	 * @return
	 */
	@AjaxCheck
	public Result upload() {
		if (checkFileType()) {
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix.toLowerCase();
			String staticPath = propertiesManager.getString("IMG_DIR");
			String newFilePath = StringUtils.defaultString(folder) + newFileName;
			FileUtils.copy(resource, staticPath + newFilePath);
			// 删除旧图片
			if (StringUtils.isNotBlank(oldFilePath)) {
				try {
					FileUtils.deleteFile(staticPath+oldFilePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("url", newFilePath);
			map.put("success", true);
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} else {
			map.put("success", false);
			map.put("errorMsg", "图片格式不正确");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		}
	}
	
	/**
	 * 重新上传时异步删除旧图片
	 * @author caiys
	 * @date 2015年10月30日 上午9:01:22
	 * @return
	 */
	@AjaxCheck
	public Result delFile() {
		// 删除旧图片
		if (StringUtils.isNotBlank(oldFilePath)) {
			String staticPath = propertiesManager.getString("IMG_DIR");
			try {
				FileUtils.deleteFile(staticPath+oldFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("success", true);
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
	/**
	 * 检查是否是图片格式
	 * @author caiys
	 * @date 2015年10月28日 下午3:19:01
	 * @return
	 */
	public boolean checkFileType() {
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

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	
	
}
