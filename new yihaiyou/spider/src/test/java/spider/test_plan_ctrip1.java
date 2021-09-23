package spider;

import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.data.DataCityService;
import com.data.spider.service.pojo.ctrip.TravelDetail;
import com.data.spider.service.pojo.ctrip.TravelList;
import com.data.spider.service.pojo.data.DataCity;
import com.data.spider.service.pojo.tb.RecommendPlan;
import com.data.spider.service.pojo.tb.RecommendPlanDay;
import com.data.spider.service.pojo.tb.RecommendPlanPhoto;
import com.data.spider.service.pojo.tb.RecommendPlanTrip;
import com.data.spider.service.tb.RecommendPlanDayService;
import com.data.spider.service.tb.RecommendPlanPhotoService;
import com.data.spider.service.tb.RecommendPlanService;
import com.data.spider.service.tb.RecommendPlanTripService;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sane on 15/10/27.
 */
public class test_plan_ctrip1 {

    public static void main(String[] args) {
//        crawlPlans();
        updateImagesSize();
    }

    private static ApplicationContext ac;

    private static void updateImagesSize() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
        Criteria<RecommendPlan> c = new Criteria<RecommendPlan>(RecommendPlan.class);
        c.eq("dataSource", "ctrip_travel");
        c.eq("status", 2);
//        c.eq("id", 152378L);
        List<RecommendPlan> recommendPlanList = recommendPlanService.gets(1, c);
        CtripService ctripService = new CtripService();
        for (RecommendPlan plan : recommendPlanList) {
            System.out.println("----------------------------------");
            Map<String, String> imagesSize = ctripService.getNoteImagesSize(Integer.parseInt(plan.getDataSourceId()));
            System.out.println(plan.getId() + "\t" + plan.getPlanName() + "\t" + plan.getDataSourceId());
            Criteria<RecommendPlanPhoto> c1 = new Criteria<RecommendPlanPhoto>(RecommendPlanPhoto.class);
            c1.eq("recplanId", plan.getId());
            List<RecommendPlanPhoto> photos = recommendPlanPhotoService.gets(1000, c1);
            for (RecommendPlanPhoto photo : photos) {
                String url = photo.getPhotoLarge();
                System.out.println(url);
                if (imagesSize.containsKey(url)) {
                    String size = imagesSize.get(url);
                    photo.setWidth(Integer.parseInt(size.split("__")[0]));
                    System.out.println(photo.getId() + "\t" + size + "\t" + url);
                    photo.setHeight(Integer.parseInt(size.split("__")[1]));
                    recommendPlanPhotoService.update(photo);
                }
            }
        }
    }

    //采集行程
    public static void crawlPlans() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CtripService ctripService = new CtripService();
        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
        RecommendPlanDayService recommendPlanDayService = SpringContextHolder.getBean("recommendPlanDayService");
        RecommendPlanTripService recommendPlanTripService = SpringContextHolder.getBean("recommendPlanTripService");
        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
        //遍历城市
        List<DataCity> dataCities = getDataCities(dataCityService);
        for (DataCity city : dataCities) {
            int ctripId = city.getCtripCityId();
            //该城市的所有推荐行程，存入recplan
            List<TravelList.ResultEntity> results = ctripService.getType1TravelAll(ctripId);
            System.err.println(city.getCityName() + "\t" + ctripId + "\t" + results.size());
            for (TravelList.ResultEntity result : results) {
                try {
                    System.out.print(result.getId() + "\t" + result.getTravelType() + "\t" + result.getTitle());
                    System.out.println("-----------------------------------");
                    TravelDetail detail = ctripService.getTravelDetail(result.getId());
                    crawlPlan(detail, recommendPlanService, recommendPlanDayService, recommendPlanTripService, recommendPlanPhotoService, city);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            city.setCtripPlanUsed(results.size());
            dataCityService.update(city);
        }
    }

    private static void crawlPlan(TravelDetail detail, RecommendPlanService recommendPlanService, RecommendPlanDayService recommendPlanDayService, RecommendPlanTripService recommendPlanTripService, RecommendPlanPhotoService recommendPlanPhotoService, DataCity city) {

        TravelDetail.ResultEntity detailResult = detail.getResult().get(0);
        int nodeSize = detailResult.getNodes().size();
        RecommendPlan recplan = getDataRecplan(city, detailResult);
        recommendPlanService.save(recplan);
        //推荐行程详情，存入recplan_day
        int dayNum = detailResult.getDayCount();
//                List<RecommendPlanDay> days = new ArrayList<RecommendPlanDay>();
        int sum = 0;
        for (int i = 1; i < dayNum + 1 && sum < nodeSize; i++) {
//                    List<RecommendPlanTrip> trips = new ArrayList<RecommendPlanTrip>();
            TravelDetail.ResultEntity.NodesEntity fnode = detailResult.getNodes().get(sum);
            RecommendPlanDay day = getDataRecplanDay(String.valueOf(city.getId()), recplan, fnode, (short) i);
            recommendPlanDayService.save(day);
            boolean newPoi, hasCover = false, hasDesc = false, hasSave = false;
            RecommendPlanTrip trip = null;
            String dateTmp = "";
            for (int j = sum; j < nodeSize; j++, sum++) {
                System.out.print(sum + "\t" + j + "\t");
                TravelDetail.ResultEntity.NodesEntity node = detailResult.getNodes().get(j);
                if (!"".equals(dateTmp) && !dateTmp.equals(node.getDate())) {
                    if (trip != null && !hasSave) {
                        System.out.println("save trip" + trip.getScenicName());
                        recommendPlanTripService.save(trip);
                    }
                    System.out.println("day + 1" + dateTmp);
                    break;
                }
                dateTmp = node.getDate();
                if (node.getNodeType() == 1) {
                    newPoi = true;
                    hasCover = false;
                    hasSave = false;
                    hasDesc = false;
                } else {
                    newPoi = false;
                }
                if (newPoi) {
                    if (trip != null && !hasSave) {
                        System.out.println("save trip" + trip.getScenicName());
                        recommendPlanTripService.save(trip);
                    }
                    trip = getDataRecplanTrip(recplan, day, String.valueOf(city.getId()), node);
                    if (trip == null) {
                        System.out.println("did not add trip" + node.getPoi().getPoi().getName());
                    } else {
                        System.out.println("add trip" + node.getPoi().getPoi().getName());

                    }
                } else {
                    if (trip == null) {
                        System.out.println("trip is null");
                        continue;
                    }
                    if (node.getNodeType() == 3 && !hasDesc) {
                        if (node.getText() == null || "".equals(node.getText()))
                            continue;
                        System.out.println("add desc3\t" + node.getText());
                        hasDesc = true;
                        trip.setTripDesc(node.getText().length() > 200 ? node.getText().substring(0, 200) : node.getText());
                        if (hasCover && !hasSave) {
                            System.out.println("save trip3" + trip.getScenicName());
                            recommendPlanTripService.save(trip);
                            hasSave = true;
                        }
                    } else if (node.getNodeType() == 2) {
                        if (!hasCover) {
                            System.out.println("add cover2\t" + node.getPicture().getPicture().getUrl());
                            trip.setCoverImg(dealImagePath(node.getPicture().getPicture().getUrl()));
                            String text = node.getPicture().getText();
                            if (text != null && !"".equals(text))
                                trip.setTripDesc(text);
                            hasCover = true;
                            if (hasDesc && !hasSave) {
                                System.out.println("save trip2" + trip.getScenicName());
                                recommendPlanTripService.save(trip);
                                hasSave = true;
                            }
                        } else {
                            if (!hasSave) {
                                System.out.println("add cover1\t" + node.getPicture().getPicture().getUrl());
                                trip.setCoverImg(dealImagePath(node.getPicture().getPicture().getUrl()));
                                System.out.println("save trip1" + trip.getScenicName());
                                recommendPlanTripService.save(trip);
                                hasSave = true;
                            }
                            System.out.println("add pic");
                            TravelDetail.ResultEntity.NodesEntity.PictureEntity.PictureDetail pic = node.getPicture().getPicture();
                            RecommendPlanPhoto photo = new RecommendPlanPhoto(recplan.getId(), day.getId(), trip.getId(), pic.getBaseUrl(), pic.getWitdh(), pic.getHeight());
                            recommendPlanPhotoService.save(photo);
                        }
                    }
                }
            }
        }
    }

    private static RecommendPlanTrip getDataRecplanTrip(RecommendPlan recommendPlan, RecommendPlanDay day, String cityId, TravelDetail.ResultEntity.NodesEntity content) {
        TravelDetail.ResultEntity.NodesEntity.PoiEntity.PoiDetail poi = content.getPoi().getPoi();
        RecommendPlanTrip trip = new RecommendPlanTrip();
//        if (poi.getBusinessId() == 0) {
//            return null;
//        }
        trip.setTripType(0);
        trip.setScenicId(2000L);
//        //poiType:餐厅2、酒店1、景点3、其他0、城市或目的地4、商圈5、玩乐6、机场7、码头8、火车站9、汽车站19
        switch (poi.getPoiType()) {
            case 1:
                trip.setTripType(3);
                break;
            case 2:
                trip.setTripType(2);
                break;
            case 3:
                trip.setTripType(1);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                trip.setTripType(4);
                break;
            case 9:
                trip.setTripType(4);
                break;
            case 10:
                trip.setTripType(4);
                break;
        }


        trip.setRecplanId(recommendPlan.getId());
        trip.setRecdayId(day.getId());
        trip.setCityCode(cityId);
        trip.setScenicName(poi.getName());
        trip.setOrderNum(poi.getSortIndex());
        trip.setSort(poi.getSortIndex());

        trip.setDataSource("ctrip_travel");
        trip.setDataSourceId(poi.getBusinessId());
        trip.setDataSourceType(String.valueOf(poi.getPoiType()));
        return trip;
    }

    private static RecommendPlanDay getDataRecplanDay(String cityId, RecommendPlan recommendPlan, TravelDetail.ResultEntity.NodesEntity node, int dayIndex) {

        RecommendPlanDay day = new RecommendPlanDay();
        day.setRecplanId(recommendPlan.getId());
        day.setDay((short) dayIndex);
//        day.setScenics(node.getContents().size());
        day.setCitys(cityId);
        if (node.getNodeType() == 3) {
            day.setDescription(node.getText());
        }
        return day;
    }

    private static List<DataCity> getDataCities(DataCityService dataCityService) {
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
//        c.isNotNull("ctripPlanUsed");
        c.isNotNull("ctripCityId");
        c.eq("ctripPlanUsed", 1);
//        c.eq("id", 330800);
//        c.eq("id", 440100);
//        c.eq("id", 350200);
        return dataCityService.gets(10000, c);
    }

    //
    private static RecommendPlan getDataRecplan(DataCity city, TravelDetail.ResultEntity result) {
        RecommendPlan recplan = new RecommendPlan();
        recplan.setPlanName(result.getTitle());

        recplan.setCoverPath(dealImagePath(result.getCoverImageUrl()));
        recplan.setDays(result.getTravelDays());
//        recplan.setTagStr(tags);
        recplan.setStatus(-11);
        recplan.setCityId(city.getId() * 1L);
//        recplan.setCityId((long) city.getId());
        recplan.setCityIds(String.valueOf(city.getId()));
        recplan.setDataSource("ctrip_travel");
        recplan.setDataSourceId(String.valueOf(result.getId()));
//        recplan.setData(data);
        recplan.setMark(3);
//        recplan.setUserId(-1L);
        TravelDetail.ResultEntity.NodesEntity node = result.getNodes().get(0);

        if (node.getNodeType() == 3) {
            recplan.setDescription(node.getText());
        } else {
            recplan.setDescription(result.getTravelSeoTitle());
        }
        recplan.setCreateTime(new Date(Long.valueOf(result.getPublishTime().substring(6, 19))));
        recplan.setStartTime(new Date(Long.valueOf(result.getNodes().get(0).getDate().substring(6, 19))));
        recplan.setStatus(-101);
        return recplan;
    }


    private static String dealImagePath(String url) {
        if (url == null)
            return null;
        if (url.contains("")) {
            url = url.replace("_R_240_240.jpg.jpg", ".jpg");
        }
        if (url.contains("chanyouji.cn")) {
            //http://p.chanyouji.cn/125115/1400858533665p18okqicdn1lbve35176c385vctn.jpg?imageView/2/w/600/h/339
            //http://m.chanyouji.cn/plans/3.jpg?imageView/2/w/600/h/339
            return url.split("\\?")[0];
        }
        if (url.contains("c-ctrip.com")) {
            //http://dimg08.c-ctrip.com/images/tg/109/301/235/450e2cce579346eaaeb84d7a8b6de0c1_C_600_339.jpg
            return url.split("_C")[0] + ".jpg";
        }
//        return UploadToQiniu(url);
        return url;
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
