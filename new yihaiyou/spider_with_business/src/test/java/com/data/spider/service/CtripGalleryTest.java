package com.data.spider.service;

import com.data.data.hmly.service.scenic.ScenicGalleryService;
import com.data.data.hmly.service.scenic.entity.ScenicGallery;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.ctrip.Image;
import com.data.spider.util.QiniuUtil;
import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Sane on 16/4/11.
 */
@Ignore
public class CtripGalleryTest {
    private static ApplicationContext ac;


    @Test
    public void downloadCtripImage() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        downloadCtripImage(1060090, 1488041, 0);
        downloadCtripImage(1060091, 107566, 0);
        downloadCtripImage(1060092, 1409208, 0);
        downloadCtripImage(1060093, 1408269, 0);
        downloadCtripImage(1060094, 1410073, 0);
        downloadCtripImage(1060095, 1676161, 0);
        downloadCtripImage(1060096, 1479989, 0);
        downloadCtripImage(1060097, 1424669, 0);
        downloadCtripImage(1060098, 1700765, 0);
        downloadCtripImage(1060099, 1677054, 0);
        downloadCtripImage(1060100, 1787316, 0);
        downloadCtripImage(1060101, 1685140, 0);
        downloadCtripImage(1060102, 70444, 0);
        downloadCtripImage(1060103, 1708679, 0);
        downloadCtripImage(1060104, 1486238, 0);
        downloadCtripImage(1060105, 1700796, 0);
        downloadCtripImage(1060106, 1833324, 0);
        downloadCtripImage(1060107, 1739901, 0);
        downloadCtripImage(1060108, 1700764, 0);
        downloadCtripImage(1060109, 1829762, 0);
        downloadCtripImage(1060110, 126458, 0);
        downloadCtripImage(1060111, 1835473, 0);
        downloadCtripImage(1060112, 1830303, 0);
        downloadCtripImage(1060113, 1829954, 0);
        downloadCtripImage(1060114, 1835677, 0);
        downloadCtripImage(1060115, 1835309, 0);
        downloadCtripImage(1060116, 1830733, 0);
        downloadCtripImage(1060117, 1830148, 0);
        downloadCtripImage(1060118, 1830486, 0);
        downloadCtripImage(1060119, 1832655, 0);
        downloadCtripImage(1060120, 1830066, 0);
        downloadCtripImage(1060121, 1832232, 0);
        downloadCtripImage(1060122, 1830381, 0);
        downloadCtripImage(1060123, 1714280, 0);
        downloadCtripImage(1060124, 1830682, 0);
        downloadCtripImage(1060125, 1830569, 0);
        downloadCtripImage(1060126, 1830680, 0);
        downloadCtripImage(1060127, 1835302, 0);
        downloadCtripImage(1060128, 1714345, 0);
        downloadCtripImage(1060129, 1831026, 0);
        downloadCtripImage(1060130, 1753531, 0);
        downloadCtripImage(1060131, 1831730, 0);
        downloadCtripImage(1060132, 1714349, 0);
        downloadCtripImage(1060133, 1832198, 0);
        downloadCtripImage(1060134, 1833691, 0);
        downloadCtripImage(1060135, 1835225, 0);
        downloadCtripImage(1060136, 1835346, 0);
    }


    public boolean downloadCtripImage(long id, long ctripId, int imageStart) {
        System.out.println("started : " + id);

        List<Image> images = CtripService.getCtripImagesById(String.valueOf(ctripId), String.valueOf(imageStart));
        if (images == null || images.size() == 0) {
            return false;
        }
        for (Image image : images) {
            String address = QiniuUtil.UploadToQiniu(image.SourceUrl, "gallery", String.valueOf(id));
            saveToGalleryImage(id, address, image.SourceWidth, image.SourceHeight, image.Title);
        }
        return true;
    }


    private void saveToGalleryImage(long id, String address, int width, int height, String title) {
        ScenicGallery galleryImage = new ScenicGallery();
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setId(id);
        galleryImage.setScenicInfo(scenicInfo);
        galleryImage.setWidth(width);
        galleryImage.setHeight(height);
        galleryImage.setDescription(title);
        galleryImage.setImgUrl(address);
//        galleryImage.setAddressMedium(address + "?imageView2/1/w/200/h/160/q/75");
//        galleryImage.setAddressSmall(address + "?imageView2/1/w/100/h/80/q/75");
        ScenicGalleryService scenicGalleryService = SpringContextHolder.getBean("scenicGalleryService");
        scenicGalleryService.save(galleryImage);
    }
}
