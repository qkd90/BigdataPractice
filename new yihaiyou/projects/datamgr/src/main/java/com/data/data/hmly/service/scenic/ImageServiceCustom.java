package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.exception.BizValidateException;
import com.data.data.hmly.service.base.util.StringUtil;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.zuipin.util.QiniuResult;
import com.zuipin.util.QiniuUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class ImageServiceCustom {

    public static final String IMAGE_TYPE = "jpg,jpeg,bmp,gif,png";

    /**
     * 新增图片
     * <ul>
     * <li>必选File图片文件</li>
     * <li>commentId</li>
     * <li>userId</li>
     * </ul>
     *
     * @return
     * @throws IOException
     */
    public String newImage(Map<String, Object> paramMap) throws IOException {
        byte[] bytefile = (byte[]) paramMap.get("file");
        Long id = (Long) paramMap.get("id");
        String imageName = (String) paramMap.get("imageName");
        String catalog = (String) paramMap.get("catalog");

        StringBuilder storeName = new StringBuilder();
        if (!StringUtil.isEmpty(imageName)) {
            storeName.append(imageName);
        } else {
            storeName.append(UUIDUtil.getUUID());
        }

        //取得文件类型
        //获取图片存储名字
        storeName.append(".").append(paramMap.get("ext"));
        //存储图片到服务器
        String name = catalog;
        if (id != null) {
            name += "/" + id;
        }
        name += "/" + storeName;
        QiniuResult result = QiniuUtil.upload(bytefile, name);
        if (result != null) {
            return name;
        }  else {
            throw new BizValidateException(-1, "图片上传失败");
        }
    }

    public String saveImage(byte[] file, String path, String fileName) throws IOException {
        //获得存储路径
        File dirPath = new File(path);
        //判断是否存在文件夹
        if (!dirPath.exists()) {
            Boolean mks = dirPath.mkdirs();
            System.out.println(mks);
        }
        //复制文件到目录
        File uploadFile = new File(path + "/" + fileName);
        FileCopyUtils.copy(file, uploadFile);
        return uploadFile.getAbsolutePath();
    }

    public Map<String, Object> getImageParam(String catalog, MultipartFile uploadFile, Long id, String imageName) {
        String fileNameL = uploadFile.getOriginalFilename();
        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        String ext = fileNameL.substring(fileNameL.lastIndexOf(".") + 1, fileNameL.length());
        //对扩展名进行小写转换
        ext = ext.toLowerCase();
        if (!IMAGE_TYPE.contains(ext)) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("ext", ext);
            paramMap.put("file", uploadFile.getBytes());
            paramMap.put("id", id);
            paramMap.put("imageName", imageName);
            paramMap.put("catalog", catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paramMap;
    }
}
