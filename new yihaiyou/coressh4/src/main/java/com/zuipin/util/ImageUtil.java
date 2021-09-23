package com.zuipin.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zuipin.entity.ProNewPic;

public class ImageUtil {
	
	/**
	 * @return
	 * @author:qiujingyan
	 * @email:qjyan@xiangyu.cn
	 * @创建日期:2012-8-16
	 * @功能说明：获取文件的后缀名
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	/**
	 * @return
	 * @author:qiujingyan
	 * @throws IOException
	 * @email:qjyan@xiangyu.cn
	 * @创建日期:2012-8-16
	 * @功能说明：文件复制
	 */
	public static void copy(File src, File dst) throws IOException {
		
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), Constants.BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), Constants.BUFFER_SIZE);
			byte[] buffer = new byte[Constants.BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		}
	}
	
	/**
	 * 多图片上传处理
	 * 
	 * @param ploads
	 * @param ploadsFileName
	 * @param tProNewPics
	 * @return
	 * @throws IOException
	 * @author:lining
	 * @email:lining@xiangyu.cn
	 * @创建日期:2012-8-28
	 * @功能说明：
	 */
	public static List<ProNewPic> uploadMultiImage(List<File> ploads, List<String> ploadsFileName, List<ProNewPic> tProNewPics) throws IOException {
		String imagePath = null;
		List<String> path = new ArrayList<String>();
		List<String> imageFilePath = new ArrayList<String>();
		File imageFiles = null;
		int index = 0;
		PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
		String master = propertiesManager.getString(Constants.MEMBER_MASTER);
		String shaidanPath = propertiesManager.getString(Constants.MEMBER_SHAIDAN);
		File dir = new File(master + shaidanPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (String fileName : ploadsFileName) {
			String fileNames = new Date().getTime() + index + getExtention(fileName);
			imagePath = shaidanPath + fileNames;
			path.add(imagePath);
			imageFilePath.add(master + imagePath.replace(" ", ""));
			index++;
		}
		for (int i = 0; i < imageFilePath.size(); i++) {
			imageFiles = new File(imageFilePath.get(i));
			ImageUtil.copy(ploads.get(i), imageFiles);
			tProNewPics.get(i).setPath(path.get(i));
		}
		return tProNewPics;
	}
	
	public static String formatImages(String url, String patternMag) {
		return url + patternMag + getExtention(url);
	}

	public static Boolean checkImage(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		if (suffix.equals(".png") || suffix.equals(".PNG") || suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".gif")) {
			return true;
		} else {
			return false;
		}
	}
}
