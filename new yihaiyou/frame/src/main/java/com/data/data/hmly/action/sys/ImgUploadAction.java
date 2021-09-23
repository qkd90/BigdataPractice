package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.UUIDUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件管理，上传、删除、预览
 * @author caiys
 * @date 2015年10月28日 下午2:23:22
 */
public class ImgUploadAction extends FrameBaseAction {
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
		if (checkImageFileType()) {
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix.toLowerCase();
			String staticPath = propertiesManager.getString("IMG_DIR");
			String newFilePath = StringUtils.defaultString(folder) + newFileName;
			FileUtils.copy(resource, staticPath + newFilePath);
			// 删除旧图片
			if (StringUtils.isNotBlank(oldFilePath)) {
				try {
					FileUtils.deleteFile(staticPath + oldFilePath);
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
				FileUtils.deleteFile(staticPath + oldFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("success", true);
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}

	/**
	 * 图片上传
	 * @author caiys
	 * @date 2015年10月30日 上午9:01:12
	 * @return
	 */
	@AjaxCheck
	public Result uploadQiniu() {
		if (checkImageFileType()) {
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix.toLowerCase();
			if (StringUtils.isNotBlank(folder)) {
				folder = folder + "/";
			} else {
				folder = "";
			}
			String newFilePath = folder + newFileName;
			QiniuUtil.upload(resource, newFilePath);
			map.put("url", QiniuUtil.URL + newFilePath);
			map.put("path", newFilePath);
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
		if (com.zuipin.util.StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("name"))) {
			name = multiPartRequestWrapper.getParameter("name");
			suffix = name.substring(name.lastIndexOf("."));
		} else {
			throw new RuntimeException("图片缺少文件名, 无法存储! 请检查!");
		}
		if (com.zuipin.util.StringUtils.isBlank(suffix)) {
			throw new RuntimeException("图片缺少格式, 无法存储! 请检查!");
		}
		if (com.zuipin.util.StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("section"))) {
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

	/**
	 * 文件上传
	 * @return
	 */
	@AjaxCheck
	public Result uploadFileQiniu() {
		if (checkFileType()) {
			String type = resourceFileName.substring(resourceFileName.lastIndexOf(".") + 1);
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String fileName = resourceFileName.substring(0, resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix.toLowerCase();
			if (StringUtils.isNotBlank(folder)) {
				folder = folder + "/";
			} else {
				folder = "";
			}
			String newFilePath = folder + newFileName;
			QiniuUtil.upload(resource, newFilePath);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("path", newFilePath);
			if ("xlsx".equals(type) || "xls".equals(type)) {
				resultMap.put("type", "sheet");
			} else if ("doc".equals(type) || "docx".equals(type)) {
				resultMap.put("type", "document");
			} else  if ("pdf".equals(type)){
				resultMap.put("type", "pdf");
			} else  if ("jpg".equals(type) || "JPG".equals(type)
					|| "png".equals(type) || "PNG".equals(type)
					|| "jpeg".equals(type) || "JPEG".equals(type)){
				resultMap.put("type", "image");
			}
			resultMap.put("name", fileName);
			map.put("result", resultMap);
			map.put("success", true);
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} else {
			map.put("success", false);
			map.put("errorMsg", "文件格式不正确");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		}
	}

	public Result deleteFile() {
		QiniuUtil.delete("contract/appendice/1479200474865.xlsx");
		return text("操作成功");
	}

	private boolean checkFileType() {

		if (StringUtils.isBlank(resourceContentType)) {
			return false;
		}
		if (resourceContentType.startsWith("image/")) {
			return true;
		} else if (resourceContentType.endsWith(".document")) {
			return true;
		} else if (resourceContentType.endsWith("/msword")) {
			return true;
		} else if (resourceContentType.endsWith(".sheet")) {
			return true;
		} else if (resourceContentType.endsWith(".ms-excel")) {
			return true;
		} else if (resourceContentType.endsWith("/pdf")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查是否是图片格式
	 * @author caiys
	 * @date 2015年10月28日 下午3:19:01
	 * @return
	 */
	public boolean checkImageFileType() {
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
