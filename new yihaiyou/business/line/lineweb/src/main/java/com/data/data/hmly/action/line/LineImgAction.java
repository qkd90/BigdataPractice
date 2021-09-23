package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.line.LineImagesService;
import com.data.data.hmly.service.line.entity.LineImages;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件管理，上传、删除、预览
 * @author caiys
 * @date 2015年10月28日 下午2:23:22
 */
public class LineImgAction extends FrameBaseAction {
	private static final long serialVersionUID = -7464481432040569710L;
	@Resource
	private ProductimageService productimageService;

    @Resource
    private LineImagesService lineImagesService;

	@Resource
	private ProductService productService;

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
			String newFileName = System.currentTimeMillis() + suffix;
			String newFilePath = StringUtils.defaultString(folder) + newFileName;
			QiniuUtil.upload(resource, newFilePath);

//			String staticPath = propertiesManager.getString("IMG_DIR");
//			FileUtils.copy(resource, staticPath + newFilePath);
//			// 删除旧图片
//			if (StringUtils.isNotBlank(oldFilePath)) {
//				try {
//					FileUtils.deleteFile(staticPath + oldFilePath);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}



			map.put("url", QiniuUtil.URL + newFilePath);
			simpleResult(map, true, "");
			return jsonResult(map);
		} else {
			simpleResult(map, false, "图片格式不正确");
			return jsonResult(map);
		}
	}


	/**
	 * 图片上传并保存
	 * @author caiys
	 * @date 2015年10月30日 上午9:01:12
	 * @return
	 */
	@AjaxCheck
	public Result uploadSave() {
		try {
			if (checkFileType()) {

				String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
				String oldFileName = resourceFileName.substring(0, resourceFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis() + suffix;
				String newFilePath = StringUtils.defaultString(folder) + newFileName;
				QiniuUtil.upload(resource, newFilePath);

//				String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
//				String oldFileName = resourceFileName.substring(0, resourceFileName.lastIndexOf(".") - 1);
//				String newFileName = System.currentTimeMillis() + suffix.toLowerCase();
//				String staticPath = propertiesManager.getString("IMG_DIR");
//				String newFilePath = StringUtils.defaultString(folder) + newFileName;
//				FileUtils.copy(resource, staticPath + newFilePath);

				String productId = (String) getParameter("productId");

				Productimage productimage = new Productimage();
				if (StringUtils.isNotBlank(productId)) {
					Product p = productService.get(Long.parseLong(productId));
					productimage.setProduct(p);
				}
				productimage.setPath(newFilePath);
				productimage.setProType(ProductType.line);
				productimage.setChildFolder(folder);
				productimage.setImagDesc(oldFileName);
				productimage.setCoverFlag(true);
				productimage.setUserId(getLoginUser().getId());
				productimage.setCompanyUnitId(getLoginUser().getSysUnit().getCompanyUnit().getId());
				productimage.setCreateTime(new Date());
				productimageService.saveProductimage(productimage);

				map.put("error", 0);
				map.put("url", QiniuUtil.URL + newFilePath);
				map.put("imagDesc", oldFileName);
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

    public Result getLineImageList() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        String lineIdStr = request.getParameter("lineId");
        if (com.zuipin.util.StringUtils.hasText(lineIdStr)) {
            Long lineId = Long.parseLong(lineIdStr);
            List<LineImages> lineImagesList = lineImagesService.listImagesByLineId(lineId, LineImageType.home);
            result.put("success", true);
            result.put("data", lineImagesList);
        } else {
            result.put("success", false);
            result.put("data", null);
        }
        return json(JSONObject.fromObject(result));
    }


	/**
	 * 上传图片
	 * @return
	 */
	public Result uploadPics() {

		final HttpServletRequest request = getRequest();
		MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
		File file = multiPartRequestWrapper.getFiles("file")[0];
		//没有获取到文件,或者无法读取文件或者文件容量太大等问题
		if (file == null || file.length() > 10000000) {
			simpleResult(map, false, "没有获取到文件,或者无法读取文件或者文件容量太大等问题");
			return jsonResult(map);
		}
		//二级栏目类别为空问题
		if (folder == null || "".equals(folder)) {
			simpleResult(map, false, "二级栏目类别为空问题");
			return jsonResult(map);
		}
		//获得上传的原始文件名称
		String filename = multiPartRequestWrapper.getParameter("name");
//		filename = filename.substring(0, filename.lastIndexOf("."));
		String imgName = filename.substring(0, filename.lastIndexOf("."));
		String imgIndex = multiPartRequestWrapper.getParameter("id");


		if (filename != null) {
			filename.startsWith("image/");
			String suffix = filename.substring(filename.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix;
			String newFilePath = StringUtils.defaultString(folder) + newFileName;
			QiniuUtil.upload(file, newFilePath);

			String productId = (String) getParameter("productId");

			Productimage productimage = new Productimage();
			if (StringUtils.isNotBlank(productId)) {
				Product p = productService.get(Long.parseLong(productId));
				productimage.setProduct(p);
			}
			productimage.setPath(newFilePath);
			productimage.setProType(ProductType.line);
			productimage.setChildFolder(folder);
			productimage.setImagDesc(imgName);
			productimage.setCoverFlag(true);
			productimage.setUserId(getLoginUser().getId());
			productimage.setCompanyUnitId(getLoginUser().getSysUnit().getCompanyUnit().getId());
			productimage.setCreateTime(new Date());
			productimageService.saveProductimage(productimage);

			map.put("imgIndex", imgIndex);
			map.put("url", QiniuUtil.URL + newFilePath);
			simpleResult(map, true, "");
			return jsonResult(map);
		} else {
			simpleResult(map, false, "图片格式不正确");
			return jsonResult(map);
		}
	}


    public Result uploadLineImg() {
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

		if (!ImageUtil.checkImage(name)) {
			throw new RuntimeException("图片文件格式不正确, 请检查!");
		}

        String id = UUIDUtil.getUUID();
        path = section + "/" + id + suffix;
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
        result.put("id", id);
        return json(JSONObject.fromObject(result));
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
		simpleResult(map, true, "");
		return jsonResult(map);
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
		//String productId = (String) getParameter("productId");
		Productimage pi = new Productimage();
		pi.setChildFolder(folder);
		pi.setProductIdFlag(true);
		//Product p = new Product();
		//p.setId(Long.valueOf(productId));
		//pi.setProduct(p);
		List<Productimage> productimages = productimageService.findProductimage(pi, getLoginUser());
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Productimage productimage : productimages) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("is_dir", false);
			m.put("has_file", false);
			//m.put("filesize", file.length());
			m.put("is_photo", true);
			String fileExt = productimage.getPath().substring(productimage.getPath().lastIndexOf(".") + 1).toLowerCase();
			m.put("filetype", fileExt);
			String filename = productimage.getPath().substring(productimage.getPath().lastIndexOf("/") + 1).toLowerCase();
			//filename = filename.substring(0, filename.lastIndexOf("."));
			m.put("filename", filename);
			m.put("datetime", DateUtils.format(productimage.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			list.add(m);
		}
		//map.put("moveup_dir_path", moveupDirPath);
		map.put("current_dir_path", "/static" + folder);
		map.put("current_url", "/static" + folder);
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
