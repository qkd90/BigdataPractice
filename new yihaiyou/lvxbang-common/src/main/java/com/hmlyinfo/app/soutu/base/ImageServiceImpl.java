package com.hmlyinfo.app.soutu.base;

import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.image.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

	@Override
	public String saveImage(byte[] file, String path, String fileName)
		throws IOException {

		String targetPath = Config.get("savePicUrl") + path;

		//获得存储路径
		File dirPath = new File(targetPath);
		//判断是否存在文件夹
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		//复制文件到目录
		File uploadFile = new File(targetPath + "/" + fileName);
		FileCopyUtils.copy(file, uploadFile);
		return path + "/" + fileName;
	}

}
