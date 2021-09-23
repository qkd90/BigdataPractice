package com.data.data.hmly.action.wechat;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.WechatDataImgService;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.service.wechat.entity.WechatDataImg;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.FileUtils;
import com.zuipin.util.PropertiesManager;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatDataImgAction extends FrameBaseAction implements ModelDriven<WechatDataImg> {
	private static final long serialVersionUID = -617072372295001263L;
	@Resource
    private WechatDataTextService textService;
	@Resource
    private WechatDataImgService dataImgService;
	@Resource
	private PropertiesManager propertiesManager;
    private Map<String, Object> map = new HashMap<String, Object>();
    private WechatDataImg wechatDataImg = new WechatDataImg();
    
    private File resource;
	private String resourceFileName;
	private String resourceContentType;	// image/jpeg
	private String oldFilePath;	// 旧图片路径
	private String folder;		// 图片目录
    
   
    
	@AjaxCheck
    public Result saveText() {
		
		return jsonResult(map);
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
		simpleResult(map, true, "");
		return jsonResult(map);
	}
	
	@AjaxCheck
	public Result upload() {
		if (checkFileType()) {
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix;
//			String staticPath = getServletContext("/").getRealPath("");
			String staticPath = propertiesManager.getString("IMG_DIR");
//			String staticPath = getRealyPath("/static");
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
			map.put("url", "/static" + newFilePath);
			simpleResult(map, true, "");
			return jsonResult(map);
		} else {
			simpleResult(map, false, "图片格式不正确");
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
		WechatDataImg di = new WechatDataImg();
		di.setChildFolder(folder);
		SysUser user = getLoginUser();
		List<WechatDataImg> dataImgList = dataImgService.findDataImgs(di, user);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (WechatDataImg sechatDataImg : dataImgList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("is_dir", false);
			m.put("has_file", false);
			//m.put("filesize", file.length());
			m.put("is_photo", true);
			String fileExt = sechatDataImg.getImg_path().substring(sechatDataImg.getImg_path().lastIndexOf(".") + 1).toLowerCase();
			m.put("filetype", fileExt);
			String filename = sechatDataImg.getImg_path().substring(sechatDataImg.getImg_path().lastIndexOf("/") + 1).toLowerCase();
			m.put("filename", filename);
			m.put("datetime", DateUtils.format(sechatDataImg.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			list.add(m);
		}
		//map.put("moveup_dir_path", moveupDirPath);
		map.put("current_dir_path", "/static" + folder);
		map.put("current_url", "/static" + folder);
//		map.put("current_dir_path", ""+folder);
//		map.put("current_url", ""+folder);
		map.put("total_count", list.size());
		map.put("file_list", list);
		return jsonResult(map);
	}
	
	@AjaxCheck
	public Result uploadImg() {
		try {
			if (checkFileType()) {
				
				String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis() + suffix; //getServletContext().getContext("/").getRealPath("")
				String staticPath = propertiesManager.getString("IMG_DIR");
				String newFilePath = StringUtils.defaultString(folder) + newFileName;
				FileUtils.copy(resource, staticPath + newFilePath);
				
				WechatDataImg wechatDataImg = new WechatDataImg();
				wechatDataImg.setChildFolder(StringUtils.defaultString(folder));
				wechatDataImg.setImg_path(newFilePath);
				wechatDataImg.setUser(getLoginUser());
				wechatDataImg.setCreateTime(new Date());
				wechatDataImg.setUpdateTime(new Date());
				dataImgService.save(wechatDataImg);
				
				map.put("error", 0);
				map.put("url", "/static" + newFilePath);
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


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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


	@Override
	public WechatDataImg getModel() {
		// TODO Auto-generated method stub
		return null;
	}


	public WechatDataImg getWechatDataImg() {
		return wechatDataImg;
	}


	public void setWechatDataImg(WechatDataImg wechatDataImg) {
		this.wechatDataImg = wechatDataImg;
	}

	
}
