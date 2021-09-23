package com.data.data.hmly.action.ticket;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketImgAction extends FrameBaseAction {

	private static final long serialVersionUID = -7464481432040569710L;
	@Resource
	private ProductimageService productimageService;
	private File resource;
	private String resourceFileName;
	private String resourceContentType;	// image/jpeg
	private String oldFilePath;	// 旧图片路径
	private String folder;		// 图片目录
	@Resource
	private PropertiesManager propertiesManager;

	// 返回数据
	Map<String, Object>			map					= new HashMap<String, Object>();
	
	
	@AjaxCheck
	public Result upload() {
		if (checkFileType()) {
			/*String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix;
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
			map.put("url","/static"+ newFilePath);*/

            String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
            String newFileName = System.currentTimeMillis() + suffix;
            String newFilePath = StringUtils.defaultString(folder) + newFileName;
			QiniuUtil.upload(resource, newFilePath);
            map.put("url", QiniuUtil.URL + newFilePath);
			simpleResult(map, true, "");
			return jsonResult(map);
		} else {
			simpleResult(map, false, "图片格式不正确");
			return jsonResult(map);
		}
	}
	
	
	@AjaxCheck
	public Result uploadImg() {
		try {
			if (checkFileType()) {
/*				String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis() + suffix;
				String staticPath = propertiesManager.getString("IMG_DIR");
				String newFilePath = StringUtils.defaultString(folder) + newFileName;
				FileUtils.copy(resource, staticPath + newFilePath);*/
                String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
                String newFileName = System.currentTimeMillis() + suffix;
                String newFilePath = StringUtils.defaultString(folder) + newFileName;
                QiniuUtil.upload(resource, newFilePath);
				
				Productimage productimage = new Productimage();
				productimage.setChildFolder(StringUtils.defaultString(folder));
				productimage.setCoverFlag(true);
				Date date = new Date();
				productimage.setCreateTime(date);
				productimage.setPath(newFilePath);
				productimage.setProType(ProductType.scenic);
				User user = getLoginUser();
				productimage.setUserId(user.getId());
				productimage.setCompanyUnitId(getLoginUser().getSysUnit().getCompanyUnit().getId());
				
				productimageService.saveProductimage(productimage);
				
				map.put("error", 0);
				map.put("url", QiniuUtil.URL + newFilePath);
				return jsonResult(map);
			} else {
				map.put("error", 1);
				map.put("message", "图片格式不正确");
				return jsonResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "图片上传失败");
			return jsonResult(map);
		}
	}
	
	
	/**
	 * 图库浏览
	 * @author caiys
	 * @date 2015年10月30日 下午2:24:25
	 * @return
	 */
	@AjaxCheck
	public Result imgsView() {
		String folder = (String) getParameter("folder");
		Productimage pi = new Productimage();
		pi.setChildFolder(folder);
		SysUser user = getLoginUser();
		List<Productimage> productimages = productimageService.findProductimage(pi, user);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Productimage productimage : productimages) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("is_dir", false);
			m.put("has_file", false);
			//m.put("filesize", file.length());
			m.put("is_photo", true);
			String fileExt = productimage.getPath().substring(productimage.getPath().lastIndexOf(".") + 1).toLowerCase();
			m.put("filetype", fileExt);
			String filename = productimage.getPath().substring(productimage.getPath().lastIndexOf("/") + 1).toLowerCase();
			m.put("filename", filename);
			m.put("datetime", DateUtils.format(productimage.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			list.add(m);
		}
		//map.put("moveup_dir_path", moveupDirPath);
		map.put("current_dir_path", QiniuUtil.URL + folder);
		map.put("current_url", QiniuUtil.URL + folder);
//		map.put("current_dir_path", ""+folder);
//		map.put("current_url", ""+folder);
		map.put("total_count", list.size());
		map.put("file_list", list);
		return jsonResult(map);
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
