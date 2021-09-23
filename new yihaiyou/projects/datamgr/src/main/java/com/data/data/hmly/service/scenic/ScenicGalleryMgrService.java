package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.base.util.ListUtil;
import com.data.data.hmly.service.scenic.dao.ScenicGalleryDao;
import com.data.data.hmly.service.scenic.entity.ScenicGallery;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/11/30.
 */
@Service
public class ScenicGalleryMgrService extends BaseService<ScenicGallery> {
    public static final String GALLERY_IMAGE_CATALOG = "gallery";

    @Resource
    private ScenicGalleryDao scenicGalleryDao;
    @Resource
    private ImageServiceCustom imageService;


    public void uploadScenicImage(ScenicGallery galleryImage, MultipartHttpServletRequest multipartRequest) throws IOException {
        MultipartFile uploadFileL = multipartRequest.getFile("uploadL");
        long id = galleryImage.getId();
        long galleryId = galleryImage.getId();
        if (uploadFileL.getSize() > 0) {
            //大图路径
            Map<String, Object> paramMap = imageService.getImageParam(GALLERY_IMAGE_CATALOG, uploadFileL, galleryId, id + "large");
            if (paramMap != null) {
                // 将文件存储到服务器，返回文件路径
                galleryImage.setImgUrl(imageService.newImage(paramMap));
            }
        }
    }

    public void del(Long id) {
        scenicGalleryDao.delete(id, ScenicGallery.class);
    }

    @Override
    public DataAccess<ScenicGallery> getDao() {
        return scenicGalleryDao;
    }

    @Override
    public Criteria<ScenicGallery> makeCriteria(Map<String, Object> paramMap, Criteria<ScenicGallery> c) {
        DetachedCriteria dc_scenic = c.createCriteria("scenicInfo", "s");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("scenicId") && Long.parseLong(paramMap.get("scenicId").toString()) > 0) {
            dc_scenic.add(Restrictions.eq("scenicInfo.id", Long.parseLong(paramMap.get("scenicId").toString())));
        }
        if (paramMap.containsKey("scenicIds")) {
            String[] strs = ((String) paramMap.get("scenicIds")).split(",");
            List<Long> ids = ListUtil.stringArrayTOLongArray(strs);
            if (ids.size() > 0) {
                c.in("scenicInfo.id", ids);
            }
        }
        if (paramMap.containsKey("width")) {
            c.eq("width", Integer.parseInt(paramMap.get("width").toString()));
        }
        if (paramMap.containsKey("height")) {
            c.eq("height", Integer.parseInt(paramMap.get("height").toString()));
        }
        return c;
    }
}
