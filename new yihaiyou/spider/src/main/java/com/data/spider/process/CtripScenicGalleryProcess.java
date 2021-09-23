//package com.data.spider.process;
//
//import com.data.spider.process.entity.MakeBy;
//import com.data.spider.service.DatataskService;
//import com.data.spider.service.ctrip.CtripService;
//import com.data.spider.service.pojo.Datatask;
//import com.data.spider.service.pojo.DatataskStatus;
//import com.data.spider.service.pojo.ctrip.Image;
//import com.data.spider.service.pojo.tb.TbGallery;
//import com.data.spider.service.pojo.tb.TbGalleryImage;
//import com.data.spider.service.tb.GalleryImageService;
//import com.data.spider.service.tb.GalleryService;
//import com.data.spider.util.BaseSpiderProcess;
//import com.data.spider.util.HttpClientUtils;
//import com.data.spider.util.QiniuUtil;
//import com.data.spider.util.TaskFetcher;
//import com.framework.hibernate.util.Criteria;
//import com.zuipin.util.SpringContextHolder;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.util.EntityUtils;
//
//import java.util.List;
//import java.util.concurrent.Semaphore;
//
//public class CtripScenicGalleryProcess extends BaseSpiderProcess {
//
//
//    private GalleryService galleryService = SpringContextHolder.getBean("galleryService");
//    private GalleryImageService galleryImageService = SpringContextHolder.getBean("galleryImageService");
//    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
//
//    public CtripScenicGalleryProcess(Datatask datatask) {
//        super(datatask);
//
//    }
//
//    @Override
//    public Datatask call() throws Exception {
//
//        String id = datatask.getUrl();//在url字段中存着
//        String ctripId = datatask.getName();//在name字段中存着
//        if (ctripId.contains("sight")){
//            String[] arr = ctripId.split("/");
//            ctripId = arr[arr.length-1].replace(".html","");
//        }
//        String imageStart = datatask.getInfo();//在info字段中存着
//        boolean b = downloadCtripImage(id,ctripId, imageStart);
//        System.out.println("SUCCESS:" + b);
//        if (b) {
//            datatask.setStatus(DatataskStatus.SUCCESSED);
//            addNextTask(id, ctripId, String.valueOf(Integer.parseInt(imageStart)+18));
//        } else {
//            datatask.setStatus(DatataskStatus.FAILED);
//        }
//        if (datatask.getMadeby() == MakeBy.DB) {
//            datataskService.updateTask(datatask);
//        }
//        return datatask;
//    }
//
//    private void addNextTask(String id,String ctripId, String imageStart) {
//        Datatask task = cloneTask();
//        task.setUrl(id);
//        task.setName(ctripId);
//        task.setMadeby(MakeBy.RUNTIME);
//        task.setInfo(imageStart);
//        TaskFetcher.instance.addTask(task);
//    }
//
//    private boolean downloadCtripImage(String id,String ctripId, String imageStart) {
//        System.out.println("started");
//        TbGallery gallery = getGallery(id);
//        List<Image> images = CtripService.getCtripImagesById(ctripId, imageStart);
//        if(images == null || images.size() == 0){
//            return false;
//        }
//        for (Image image : images) {
////            byte[] bytes = downloadBytes(image.SourceUrl);
////            if(bytes==null)
////                continue;
////            String address = QiniuUtil.UploadToQiniu(id, image.Title, bytes, "gallery");
//
//            String address = QiniuUtil.UploadToQiniu(image.SourceUrl, "gallery",id);
//            SaveToGalleryImage(gallery, address,image.SourceWidth,image.SourceHeight,image.Title);
//        }
//        return true;
//    }
//
//    private TbGallery getGallery(String id) {
//        Criteria<TbGallery> c = new Criteria<TbGallery>(TbGallery.class);
//        c.eq("scenicId", Long.parseLong(id));
//        c.eq("category", "scene");
//        List<TbGallery> gallerys = galleryService.gets(1, c);
//        if (gallerys == null || gallerys.size() == 0) {
//            TbGallery gallery = new TbGallery();
//            gallery.setCategory("scene");
//            gallery.setScenicId(Long.parseLong(id));
//            gallery.setContent("1");
//            galleryService.save(gallery);
//            return gallery;
//        } else {
//            return gallerys.get(0);
//        }
//    }
//
//
//    private void  SaveToGalleryImage(TbGallery gallery, String address,int width,int height,String title) {
//        TbGalleryImage galleryImage = new TbGalleryImage();
//        galleryImage.setTbGalleryByGalleryId(gallery);
//        galleryImage.setWidth(width);
//        galleryImage.setHeight(height);
//        galleryImage.setTitle(title);
//        galleryImage.setAddressLarge(address);
//        galleryImage.setAddressMedium(address + "?imageView2/1/w/200/h/160/q/75");
//        galleryImage.setAddressSmall(address + "?imageView2/1/w/100/h/80/q/75");
//        galleryImageService.save(galleryImage);
//    }
//
//
//
//    private byte[] downloadBytes(String sourceUrl) {
//        HttpGet httpGet = new HttpGet(sourceUrl);
//        HttpResponse response;
//        HttpClient httpClient = HttpClientUtils.getHttpClient();
//        try {
//            response = httpClient.execute(httpGet);
//            return EntityUtils.toByteArray(response.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//
//
//
//
//
//    @Override
//    public String getSource() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    protected Semaphore getMutex() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void execute() {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public String getXmlName() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public static void main(String[] args) {
//
//    }
//
//}
