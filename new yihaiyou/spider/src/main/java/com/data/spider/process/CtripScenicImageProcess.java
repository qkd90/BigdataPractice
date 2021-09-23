package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.ScenicGallery;
import com.data.spider.service.pojo.ctrip.Image;
import com.data.spider.service.tb.ScenicGalleryService;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.HttpUtil;
import com.data.spider.util.QiniuUtil;
import com.data.spider.util.TaskFetcher;
import com.data.spider.util.qiniuImageInfo;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;

import java.util.List;
import java.util.concurrent.Semaphore;

public class CtripScenicImageProcess extends BaseSpiderProcess {


    //    private GalleryService galleryService = SpringContextHolder.getBean("galleryService");
    private ScenicGalleryService scenicGalleryService = SpringContextHolder.getBean("scenicGalleryService");
    //    private GalleryImageService galleryImageService = SpringContextHolder.getBean("galleryImageService");
    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");

    public CtripScenicImageProcess(Datatask datatask) {
        super(datatask);

    }

    @Override
    public Datatask call() throws Exception {

        String id = datatask.getUrl();//在url字段中存着
        String ctripId = datatask.getName();//在name字段中存着
        if (ctripId.contains("sight")) {
            String[] arr = ctripId.split("/");
            ctripId = arr[arr.length - 1].replace(".html", "");
        }
        String imageStart = datatask.getInfo();//在info字段中存着
        boolean b = downloadCtripImage(id, ctripId, imageStart);
        System.out.println("SUCCESS:" + b);
        if (b) {
            datatask.setStatus(DatataskStatus.SUCCESSED);
            addNextTask(id, ctripId, String.valueOf(Integer.parseInt(imageStart) + 18));
        } else {
            datatask.setStatus(DatataskStatus.FAILED);
        }
        if (datatask.getMadeby() == MakeBy.DB) {
            datataskService.updateTask(datatask);
        }
        return datatask;
    }

    private void addNextTask(String id, String ctripId, String imageStart) {
        Datatask task = cloneTask();
        task.setUrl(id);
        task.setName(ctripId);
        task.setMadeby(MakeBy.RUNTIME);
        task.setInfo(imageStart);
        TaskFetcher.instance.addTask(task);
    }

    private boolean downloadCtripImage(String id, String ctripId, String imageStart) {
        System.out.println("started");
        List<Image> images = CtripService.getCtripImagesById(ctripId, imageStart);
        if (images == null || images.size() == 0) {
            return false;
        }
        for (Image image : images) {
//            byte[] bytes = downloadBytes(image.SourceUrl);
//            if(bytes==null)
//                continue;
//            String address = QiniuUtil.UploadToQiniu(id, image.Title, bytes, "gallery");
            String address = QiniuUtil.UploadToQiniu(image.SourceUrl, "gallery", id);
            SaveToGalleryImage(id, address, image.SourceWidth, image.SourceHeight, image.Title);
        }
        return true;
    }


    private void SaveToGalleryImage(String id, String address, int width, int height, String title) {
        ScenicGallery galleryImage = new ScenicGallery();
        galleryImage.setScenicId(Integer.parseInt(id));
        galleryImage.setType("scenic");
        galleryImage.setWidth(width);
        galleryImage.setHeight(height);
        galleryImage.setDescription(title);
        galleryImage.setImgUrl(address);
        galleryImage.setRemoteUrl(address);
        String img = "http://7u2inn.com2.z0.glb.qiniucdn.com/" + address.replace(" ", "%20");
        try {
            String json = HttpUtil.HttpGetString(img + "?imageInfo");
            if (!json.contains("error")) {
                qiniuImageInfo imageInfo = new Gson().fromJson(json, qiniuImageInfo.class);
                if (imageInfo.getWidth() == 640 && imageInfo.getHeight() == 320) {
                }
                galleryImage.setWidth(imageInfo.getWidth());
                galleryImage.setHeight(imageInfo.getHeight());
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        scenicGalleryService.save(galleryImage);
    }


    @Override
    public String getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Semaphore getMutex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getXmlName() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {

    }

}
