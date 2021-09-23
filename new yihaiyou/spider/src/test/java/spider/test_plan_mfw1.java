package spider;

import com.data.spider.service.mfw.MfwService;
import com.data.spider.service.pojo.mfw.ContentEntity;
import com.data.spider.service.pojo.mfw.TravelnoteDetail;
import com.data.spider.service.pojo.mfw.TravelnoteList;
import com.data.spider.service.pojo.tb.AreaRelation;
import com.data.spider.service.pojo.tb.RecommendPlan;
import com.data.spider.service.pojo.tb.RecommendPlanDay;
import com.data.spider.service.pojo.tb.RecommendPlanPhoto;
import com.data.spider.service.pojo.tb.RecommendPlanTrip;
import com.data.spider.service.tb.AreaRelationService;
import com.data.spider.service.tb.RecommendPlanDayService;
import com.data.spider.service.tb.RecommendPlanPhotoService;
import com.data.spider.service.tb.RecommendPlanService;
import com.data.spider.service.tb.RecommendPlanTripService;
import com.framework.hibernate.util.Criteria;
import com.google.gson.Gson;
import com.zuipin.util.DateUtils;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;


/**
 * Created by Sane on 15/10/27.
 */
public class test_plan_mfw1 {
    private static ApplicationContext ac;

    public static void main(String[] args) {
//        crawlAll();
//        crawlOne();
        updateImagesSize();
//        testGetImagesSize();
    }

    private static void updateImagesSize() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
        Criteria<RecommendPlan> c = new Criteria<RecommendPlan>(RecommendPlan.class);
        c.eq("dataSource", "mfw_note");
        c.eq("status", 2);
//        c.eq("id", 152378L);
        List<RecommendPlan> recommendPlanList = recommendPlanService.gets(100000, c);
        MfwService mfwService = new MfwService();
        for (RecommendPlan plan : recommendPlanList) {

            Map<String, String> imagesSize = mfwService.getNoteImagesSize(Integer.parseInt(plan.getDataSourceId()));
            System.out.println("----------------------------------");
            System.out.println(plan.getId() + "\t" + plan.getPlanName() + "\t" + plan.getDataSourceId());

            Criteria<RecommendPlanPhoto> c1 = new Criteria<RecommendPlanPhoto>(RecommendPlanPhoto.class);
            c1.eq("recplanId", plan.getId());
            List<RecommendPlanPhoto> photos = recommendPlanPhotoService.gets(1000, c1);
            for (RecommendPlanPhoto photo : photos) {
                String url = photo.getPhotoLarge();
                System.out.println(url);
                if (imagesSize.containsKey(url)) {
                    String size = imagesSize.get(url);
                    System.out.println(photo.getId() + "\t" + size + "\t" + url);
                    photo.setWidth(Integer.parseInt(size.split("__")[0]));
                    photo.setHeight(Integer.parseInt(size.split("__")[1]));
                    recommendPlanPhotoService.update(photo);
                }
            }
        }
    }

    private static void testGetImagesSize() {
        MfwService mfwService = new MfwService();
        Map<String, String> imagesSize = mfwService.getNoteImagesSize(5330066);
        for (String key : imagesSize.keySet()) {
            System.out.println(imagesSize.get(key) + "\t" + key);
        }
    }

    private static void crawlOne() {

        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        AreaRelation city = new AreaRelation();
        city.setId(110000L);
        city.setName("北京");
        MfwService mfwService = new MfwService();
        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
        RecommendPlanDayService recommendPlanDayService = SpringContextHolder.getBean("recommendPlanDayService");
        RecommendPlanTripService recommendPlanTripService = SpringContextHolder.getBean("recommendPlanTripService");
        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
//        TravelnoteDetail detail = mfwService.getTravelnoteDetail(3442787);
        TravelnoteDetail detail = mfwService.getTravelnoteDetail(3484840);
        crawlPlan(detail, recommendPlanService, recommendPlanDayService, recommendPlanTripService, recommendPlanPhotoService, city);

    }

    //采集行程
    public static void crawlAll() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MfwService mfwService = new MfwService();
        AreaRelationService areaRelationService = SpringContextHolder.getBean("areaRelationService");
        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
        RecommendPlanDayService recommendPlanDayService = SpringContextHolder.getBean("recommendPlanDayService");
        RecommendPlanTripService recommendPlanTripService = SpringContextHolder.getBean("recommendPlanTripService");
        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
        //遍历城市
        List<AreaRelation> dataCities = getDataCities(areaRelationService);
        for (AreaRelation city : dataCities) {
            System.out.println(city.getId() + "\t" + city.getName());
            if (city.getId() % 10000 == 0 || city.getId() % 100 != 0)
                continue;
            int mfwNoteCity = city.getMfwNoteCity();
            //该城市的所有推荐行程，存入recplan
            List<TravelnoteList.DataEntity.ListEntity> results = mfwService.getTravelnodeAll(String.valueOf(mfwNoteCity));
            System.err.println(city.getName() + "\t" + mfwNoteCity + "\t" + results.size());
            for (TravelnoteList.DataEntity.ListEntity result : results) {
                try {
                    System.out.print(result.getId() + "\t" + result.getMddname() + "\t" + result.getTitle());
                    System.out.println("-----------------------------------");
                    TravelnoteDetail detail = mfwService.getTravelnoteDetail(result.getId());
                    crawlPlan(detail, recommendPlanService, recommendPlanDayService, recommendPlanTripService, recommendPlanPhotoService, city);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            city.setCtripPlanUsed(results.size());
//            dataCityService.update(city);
        }
    }

    private static void crawlPlan(TravelnoteDetail detail, RecommendPlanService recommendPlanService, RecommendPlanDayService recommendPlanDayService, RecommendPlanTripService recommendPlanTripService, RecommendPlanPhotoService recommendPlanPhotoService, AreaRelation city) {
        if (detail.getData().getContent() == null) {
            return;
        }
        List<ContentEntity> detailResult = detail.getData().getContent();
        int nodeSize = detailResult.size();
        //推荐行程详情，存入recplan_day
        int sum = 0;
        boolean hasTrip = false, addDay = false, hasDay = false;
        for (int tmp = 0; tmp < nodeSize; tmp++) {
            ContentEntity node = detailResult.get(tmp);
            if ("paragraph".equals(node.getType())) {
                String content = new Gson().toJson(node.getContent());
                System.out.println("paragraph:" + content);
                if (isMatches(content)) {
                    sum = tmp + 1;
                    hasDay = true;
                    break;
                }
            }
        }
        if (!hasDay && detail.getData().getEx().getDays() == 0) {
            return;
        }
        int daySum = 1;
        String nameTmp = "";
        int dayCount = detail.getData().getEx().getDays();
        RecommendPlan recplan = getDataRecplan(city, detail.getData());
        recommendPlanService.save(recplan);
        for (int d = 0; d < dayCount && sum < nodeSize; d++) {
            System.out.println("begin day : " + d);
            RecommendPlanDay day = getDataRecplanDay(String.valueOf(city.getId()), recplan, daySum++);
            recommendPlanDayService.save(day);
            RecommendPlanTrip trip = null;
            for (; sum < nodeSize; sum++) {
                ContentEntity node = detailResult.get(sum);
                System.out.println("-----------" + sum + "-----------" + node.getType());
                RecommendPlanPhoto photo = null;
                if ("image".equals(node.getType()) && node.getImage().getExt() != null) {
                    System.out.println(node.getImage().getPid() + "\t" + node.getImage().getNew_iid());
                    ContentEntity.Image.ExtEntity ext = node.getImage().getExt();
                    //判断景点名
                    if (!nameTmp.equals(ext.getName())) {
                        hasTrip = true;
                        System.out.println("add trip:" + ext.getName());
                        nameTmp = ext.getName();
                        trip = getDataRecplanTrip(recplan, day, String.valueOf(city.getId()), node);
                        System.out.println("save trip:" + trip.getScenicName());
                        recommendPlanTripService.save(trip);
                    } else if (day != null && trip != null) {
                        System.out.println("add image" + ext.getName());
                        ContentEntity.Image pic = node.getImage();
                        photo = new RecommendPlanPhoto(recplan.getId(), day.getId(), trip.getId(), pic.getOimgurl(), pic.getO_width(), pic.getO_height());
                        recommendPlanPhotoService.save(photo);
                    }
                }
                if ("container".equals(node.getType())) {
                    if (trip != null && trip.getExa() == null) {
                        System.out.println("add desc");
                        String txt = "";
                        for (ContentEntity.Container container : node.getContainer()) {
                            txt += container.getContent();
                        }
                        trip.setExa(txt);
                        recommendPlanTripService.update(trip);
                    }
                    if (photo != null && photo.getDescription() == null) {
                        System.out.println("add photo desc");
                        String txt = "";
                        for (ContentEntity.Container container : node.getContainer()) {
                            txt += container.getContent();
                        }
                        photo.setDescription(txt);
                        recommendPlanPhotoService.update(photo);
                    }
                }
                if ("paragraph".equals(node.getType())) {
                    String content = new Gson().toJson(node.getContent());
                    System.out.println("paragraph:" + content);
                    if (isMatches(content)) {
                        addDay = true;
                    }
                }
                if (addDay) {
                    System.out.println("add day");
                    addDay = false;
                    sum++;
                    break;
                }
            }
        }
        if (!hasTrip) {
            recplan.setStatus(-100);
            recommendPlanService.update(recplan);
        }

    }

    private static boolean isMatches(String content) {
        return content.matches(".*\"sn\":\".*(?:第.*?天|D\\d|DAY|第.*?日|\\d天|\\d日|day|Day).*?\".*");
    }

    private static RecommendPlanTrip getDataRecplanTrip(RecommendPlan recommendPlan, RecommendPlanDay day, String cityId, ContentEntity node) {
        RecommendPlanTrip trip = new RecommendPlanTrip();
//        if (poi.getBusinessId() == 0) {
//            return null;
//        }
        ContentEntity.Image.ExtEntity ext = node.getImage().getExt();
        trip.setTripType(0);
//        trip.setScenicId(2000L);
//        //poiType:餐厅1,购物4、酒店2、景点3、其他0、机场火车站6,购物5
        switch (ext.getType_id()) {
            case 1:
                trip.setTripType(2);
                break;
            case 2:
                trip.setTripType(3);
                break;
            case 3:
                trip.setTripType(1);
                break;
            case 4:
                trip.setTripType(0);
                break;
            case 5:
                trip.setTripType(0);
                break;
            case 6:
                trip.setTripType(4);
                break;
        }


        trip.setRecplanId(recommendPlan.getId());
        trip.setRecdayId(day.getId());
        trip.setCityCode(cityId);
        trip.setScenicName(ext.getName());
        trip.setCoverImg(node.getImage().getOimgurl());
//        trip.setOrderNum(poi.getSortIndex());
//        trip.setSort(poi.getSortIndex());

        trip.setDataSource("mfw_note");
        trip.setDataSourceId(ext.getId());
        trip.setDataSourceType(String.valueOf(ext.getType_id()));
        return trip;
    }

    private static RecommendPlanDay getDataRecplanDay(String cityId, RecommendPlan recommendPlan, int dayIndex) {

        RecommendPlanDay day = new RecommendPlanDay();
        day.setRecplanId(recommendPlan.getId());
        day.setDay((short) dayIndex);
        day.setCitys(cityId);
        return day;
    }

    private static List<AreaRelation> getDataCities(AreaRelationService dataCityService) {
        Criteria<AreaRelation> c = new Criteria<AreaRelation>(AreaRelation.class);
        c.isNotNull("mfwNoteCity");
        return dataCityService.gets(2, c);
    }

    //
    private static RecommendPlan getDataRecplan(AreaRelation city, TravelnoteDetail.DataEntity result) {
        RecommendPlan recplan = new RecommendPlan();
        recplan.setPlanName(result.getTitle());

        recplan.setCoverPath(result.getOimgurl());
        recplan.setDays(result.getEx().getDays());
//        recplan.setTagStr(tags);
        recplan.setCityId(city.getId() * 1L);
        recplan.setCityIds(String.valueOf(city.getId()));
        recplan.setDataSource("mfw_note");
        recplan.setDataSourceId(String.valueOf(result.getId()));
        recplan.setMark(3);
//        recplan.setUserId(-1L);
//        ContentEntity node = result.getNodes().get(0);
//        if (node.getNodeType() == 3) {
//            recplan.setDescription(node.getText());
//        } else {
//            recplan.setDescription(result.getTravelSeoTitle());
//        }
        recplan.setCreateTime(DateUtils.getDate(result.getCtime(), "yyyy-MM-dd HH:mm:ss"));
        recplan.setStartTime(DateUtils.getDate(result.getEx().getSdate(), "yyyy/MM/dd"));
        recplan.setUserId(157L);
        recplan.setStatus(2);
        recplan.setDeleteFlag(2);
        return recplan;
    }


//    private static String UploadToQiniu(String imageUrl) {
//        byte[] bytes = downloadBytes(imageUrl);
//        if (bytes == null) {
//            return imageUrl;
//        }
//        String filename = imageUrl;
//        String suffix = "";
//        if (StringUtils.isNotBlank(filename)) {
//            String[] nameArr = filename.split("\\.");
//            suffix = "." + nameArr[nameArr.length - 1];
//        }
//        String path = "recplan/" + UUIDUtil.getUUID() + suffix;
//        QiniuUtil.upload(bytes, path);
//        return path;
//    }
//
//    private static byte[] downloadBytes(String sourceUrl) {
//        HttpGet httpGet = new HttpGet(sourceUrl);
//        HttpResponse response;
//        HttpClient httpClient = HttpClientUtils.getHttpClient();
//        //        //抓包检查调试
////        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
////        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//        try {
//            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3");
//            response = httpClient.execute(httpGet);
//            return EntityUtils.toByteArray(response.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
