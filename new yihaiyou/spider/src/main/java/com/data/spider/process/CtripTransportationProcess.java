package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.tb.GalleryImageService;
import com.data.spider.service.tb.GalleryService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.ctrip.SearchResult;
import com.data.spider.util.*;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.Semaphore;

public class CtripTransportationProcess extends BaseSpiderProcess {
    private String api = "http://m.ctrip.com/restapi/soa2/10011/json/GetPoiImageList";

    private String searchApi = "http://m.ctrip.com/restapi/h5api/searchapp/search?" +
            "lon=-180.0&voice=f&source=globalapp68&fromHistory=t&keyword=%s" +
            "&clientID=32001048310005536757&action=autocomplete&client-system=android&districtid=0&lat=-180.0&cityid=0";

    private String postJson = "{\n" +
            "\t\"PoiType\": \"SIGHT\",\n" +
            "\t\"Start\": %s,\n" +
            "\t\"Count\": 18,\n" +
            "\t\"ImageSize\": [\"R_300_10000\"],\n" +
            "\t\"BusinessId\": \"%s\",\n" +
            "\t\"head\": {\n" +
            "\t\t\"cid\": \"32001048310005536757\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"608.002\",\n" +
            "\t\t\"lang\": \"01\",\n" +
            "\t\t\"sid\": \"8080\",\n" +
            "\t\t\"syscode\": \"32\",\n" +
            "\t\t\"auth\": null\n" +
            "\t},\n" +
            "\t\"contentType\": \"json\"\n" +
            "}\n";

    private GalleryService galleryService = SpringContextHolder.getBean("galleryService");
    private GalleryImageService galleryImageService = SpringContextHolder.getBean("galleryImageService");
    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");

    public CtripTransportationProcess(Datatask datatask) {
        super(datatask);

    }

    @Override
    public Datatask call() throws Exception {

        String id = datatask.getUrl();//在url字段中存着
        String ctripId = datatask.getName();//在url字段中存着
        String imageStart = datatask.getInfo();//在url字段中存着
        boolean b = false;
//        boolean b = downloadCtripImage(id, ctripId, imageStart);
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

//    private boolean downloadCtripImage(String id, String ctripId, String imageStart) {
//        System.out.println("started");
//        TbGallery gallery = getGallery(id);
//        List<Image> images = getCtripImagesById(ctripId, imageStart);
//        if (images == null || images.size() == 0) {
//            return false;
//        }
//        for (Image image : images) {
//            byte[] bytes = downloadBytes(image.SourceUrl);
//            if (bytes == null)
//                continue;
//            String address = QiniuUtil.UploadToQiniu(id, image, bytes,"gallery");
//            SaveToGalleryImage(gallery, address, image.SourceWidth, image.SourceHeight, image.Title);
//        }
//        return true;
//    }
    private SearchResult search(String keyword) {
        String result;
        String url = String.format(searchApi, keyword);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        try {
            response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new Gson().fromJson(result, SearchResult.class);
    }
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
//    private void SaveToGalleryImage(TbGallery gallery, String address, int width, int height, String title) {
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


//    private List<Image> getCtripImagesById(String ctripId, String imageStart) {
//        List<Image> images = new ArrayList<Image>();
//        String result = postForResponse(ctripId, imageStart);
//        CtripImages ctripImages = getEntityFromResponse(result);
//        for (Result r : ctripImages.Result) {
//            images.add(r.Image);
//        }
//        return images;
//    }
//
//
//
//    private CtripImages getEntityFromResponse(String result) {
//        Gson gson = new Gson();
//        return gson.fromJson(result, CtripImages.class);
//    }
//
//    private String postForResponse(String ctripId, String index) {
//        String result = "";
//        HttpPost httpPost = new HttpPost(api);
//        HttpResponse response;
//        HttpClient httpClient = HttpClientUtils.getHttpClient();
//        String nvps = String.format(postJson, index, ctripId);
//        try {
//            httpPost.setEntity(new StringEntity(nvps, "utf-8"));
//            response = httpClient.execute(httpPost);
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

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
