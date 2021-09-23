package spider;

import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.data.DataCityService;
import com.data.spider.service.pojo.ctrip.H5ScheduleDetail;
import com.data.spider.service.pojo.ctrip.H5ScheduleList;
import com.data.spider.service.pojo.ctrip.TravelList;
import com.data.spider.service.pojo.data.DataCity;
import com.data.spider.service.pojo.tb.RecommendPlan;
import com.data.spider.service.pojo.tb.RecommendPlanDay;
import com.data.spider.service.pojo.tb.RecommendPlanTrip;
import com.data.spider.service.tb.RecommendPlanDayService;
import com.data.spider.service.tb.RecommendPlanPhotoService;
import com.data.spider.service.tb.RecommendPlanService;
import com.data.spider.service.tb.RecommendPlanTripService;
import com.data.spider.util.HttpClientUtils;
import com.data.spider.util.HttpUtil;
import com.data.spider.util.QiniuUtil;
import com.data.spider.util.UUIDUtil;
import com.data.spider.util.qiniuImageInfo;
import com.framework.hibernate.util.Criteria;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

//import com.data.spider.service.pojo.tb.RecommendPlanPhoto;

//import com.data.spider.service.data.DataRecplanDayService;
//import com.data.spider.service.data.DataRecplanPhotoService;
//import com.data.spider.service.data.DataRecplanService;
//import com.data.spider.service.data.DataRecplanTripService;
//import com.data.spider.service.pojo.data.DataRecplan;
//import com.data.spider.service.pojo.data.DataRecplanDay;
//import com.data.spider.service.pojo.data.DataRecplanPhoto;
//import com.data.spider.service.pojo.data.DataRecplanTrip;

/**
 * Created by Sane on 15/10/27.
 */
public class test_plan_ctrip {
    private static ApplicationContext ac;


    public static void main(String[] args) {
//        countPlans();
//        crawlPlan();
        updateTripCover();
    }

    //统计行程
    public static void countPlans() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CtripService ctripService = new CtripService();
        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
        //遍历城市
        List<DataCity> dataCities = getDataCities(dataCityService);
        for (DataCity city : dataCities) {
            int ctripId = city.getCtripCityId();
            //该城市的所有推荐行程，存入recplan
            List<TravelList.ResultEntity> results = ctripService.getType1TravelAll(ctripId);
            System.err.println(city.getCityName() + "\t" + ctripId + "\t" + results.size());
            //更新data_city
            city.setCtripPlanUsed(results.size());
            dataCityService.update(city);
        }
    }


    //采集行程
    public static void crawlPlan() {
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
            List<H5ScheduleList.ResultsEntity> results = ctripService.getH5ScheduleAll(ctripId);
            if (results == null) {
                System.out.println(city.getCityName() + "   无行程");
                continue;
            }
            for (H5ScheduleList.ResultsEntity result : results) {
                H5ScheduleDetail detail = ctripService.getH5ScheduleDetail(result.getId());
                RecommendPlan recplan = getRecommendPlan(city, result);
                recplan.setStatus(-100);
                recommendPlanService.save(recplan);
//                //推荐行程详情，存入recplan_day
                int dayNum = detail.getNodeList().size();
                for (int i = 0; i < dayNum; i++) {
                    H5ScheduleDetail.NodeListEntity node = detail.getNodeList().get(i);
                    RecommendPlanDay day = getRecommendPlanDay(String.valueOf(city.getId()), recplan.getId(), node, (short) i);
                    if (i == dayNum - 1) {
                        day.setIsLast("T");
                    } else {
                        day.setIsLast("F");
                    }
                    recommendPlanDayService.save(day);
                    //行程的各个节点：景点、美食、餐厅、交通，存入recplan_trip
                    for (H5ScheduleDetail.NodeListEntity.ContentsEntity content : node.getContents()) {
                        RecommendPlanTrip trip = getRecommendPlanTrip(recplan.getId(), day.getId(), String.valueOf(city.getId()), content);
                        recommendPlanTripService.save(trip);
                    }
                }
            }
            //更新data_city
            city.setCtripPlanUsed(1);
            dataCityService.update(city);
        }
    }

//    private static DataRecplanTrip getDataRecplanTrip(long recplanId, long dayId, String cityId, TravelDetail.ResultEntity.NodesEntity content) {
//        TravelDetail.ResultEntity.NodesEntity.PoiEntity.PoiDetail poi = content.getPoi().getPoi();
//        DataRecplanTrip trip = new DataRecplanTrip();
//        if (poi.getBusinessId() == 0) {
//            return null;
//        }
////        //poiType:餐厅2、酒店1、景点3、其他0、城市或目的地4、商圈5、玩乐6、机场7、码头8、火车站9、汽车站19
////        switch (poi.getPoiType()) {
////            case 1:
////                break;
////            case 2:
////                break;
////            case 3:
////                break;
////            case 4:
////                break;
////            case 5:
////                break;
////            case 6:
////                break;
////            case 7:
////                break;
////            case 8:
////                break;
////            case 9:
////                break;
////            case 10:
////                break;
////        }
//
//
//        trip.setRecplanId(recplanId);
//        trip.setRecdayId(dayId);
//        trip.setCityCode(cityId);
//        trip.setScenicName(poi.getName());
//        trip.setOrderNum(poi.getSortIndex());
//        trip.setSort(poi.getSortIndex());
//
//        trip.setDataSource("ctrip_travel");
//        trip.setDataSourceId(poi.getBusinessId());
//        trip.setDataSourceType(String.valueOf(poi.getPoiType()));
//        return trip;
//    }

    private static RecommendPlanTrip getRecommendPlanTrip(long recplanId, long dayId, String cityId, H5ScheduleDetail.NodeListEntity.ContentsEntity content) {
        RecommendPlanTrip trip = new RecommendPlanTrip();


        List<H5ScheduleDetail.NodeListEntity.ContentsEntity.ExtraInfoListEntity> extractInfoLists = content.getExtraInfoList();
        //contentType:11景点，6城市，18娱乐演出消费，13购物
        if (content.getContentType() == 14) {//酒店
            //descType:6评分11价格9地址
//            String price = "";
//            trip.setOldPrice(Double.parseDouble(price));
            trip.setTripType(3);
        } else if (content.getContentType() == 12) {//美食
            //descType:0人均价格，1菜系
//            String price = "";
//            trip.setOldPrice(Double.parseDouble(price));
            trip.setTripType(2);
        } else {
            //景点及其它未识别的类型
            //descType:3开放时间4景点类别
//            String price = "";
//            trip.setOldPrice(Double.parseDouble(price));
            trip.setTripType(1);
        }

        trip.setRecplanId(recplanId);
        trip.setRecdayId(dayId);
        trip.setCityCode(cityId);
        trip.setScenicName(content.getPoiName());
        trip.setOrderNum(content.getSortIndex());
        trip.setSort(content.getSortIndex());
        trip.setExa(content.getIntroduction());
//        trip.setTripDesc(content.getIntroduction());
        trip.setCoverImg(dealImagePath(content.getCoverImageUrl()));
        trip.setDataSource("ctrip");
        trip.setDataSourceId(content.getBusinessId());
        trip.setDataSourceType(String.valueOf(content.getContentType()));
        return trip;
    }

//    private static DataRecplanDay getDataRecplanDay(String cityId, long recplanId, TravelDetail.ResultEntity.NodesEntity node, short dayIndex) {
//
//        DataRecplanDay day = new DataRecplanDay();
//        day.setRecplanId(recplanId);
//        day.setDay(dayIndex);
////        day.setScenics(node.getContents().size());
//        day.setCitys(cityId);
//        if (node.getNodeType() == 3) {
//            day.setDescription(node.getText());
//        }
//        return day;
//    }

    private static RecommendPlanDay getRecommendPlanDay(String cityId, long recplanId, H5ScheduleDetail.NodeListEntity node, short dayIndex) {

        RecommendPlanDay day = new RecommendPlanDay();
        day.setRecplanId(recplanId);
        day.setDay(dayIndex);
//        day.setScenics(node.getContents().size());
        day.setCitys(cityId);
//        if (node.getNodeType() == 3) {
//            day.setDescription(node.getText());
//        }
        return day;
    }

    private static List<DataCity> getDataCities(DataCityService dataCityService) {
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
//        c.ne("ctripPlanUsed", 1);
//        c.isNotNull("ctripPlanUsed");
        c.isNotNull("ctripCityId");
//        c.eq("ctripPlanUsed", 1);
//        c.eq("id", 330800);
//        c.eq("id", 350700);
//        c.eq("id", 350200);
        return dataCityService.gets(10000, c);
    }

    //
//    private static DataRecplan getDataRecplan(DataCity city, TravelDetail.ResultEntity result, String data) {
//        DataRecplan recplan = new DataRecplan();
//        recplan.setPlanName(result.getTitle());
//
//        recplan.setCoverPath(dealImagePath(result.getCoverImageUrl()));
//        recplan.setDays(result.getTravelDays());
////        recplan.setTagStr(tags);
//        recplan.setStatus(-11);
//        recplan.setCityId((long) city.getId());
//        recplan.setCityIds(String.valueOf(city.getId()));
//        recplan.setDataSource("ctrip_travel");
//        recplan.setDataSourceId(String.valueOf(result.getId()));
//        recplan.setData(data);
//        recplan.setMark(3);
//        recplan.setUserId(-1L);
//        TravelDetail.ResultEntity.NodesEntity node = result.getNodes().get(0);
//
//        if (node.getNodeType() == 3) {
//            recplan.setDescription(node.getText());
//        } else {
//            recplan.setDescription(result.getBrief());
//        }
//        return recplan;
//    }

    private static RecommendPlan getRecommendPlan(DataCity city, H5ScheduleList.ResultsEntity result) {
        RecommendPlan recplan = new RecommendPlan();
        recplan.setPlanName(result.getTitle());

        recplan.setCoverPath(dealImagePath(result.getCoverImageUrl()));
        recplan.setDays(result.getDays());
//        recplan.setTagStr(tags);
        recplan.setStatus(-11);
        recplan.setCityId((long) city.getId());
        recplan.setCityIds(String.valueOf(city.getId()));
        recplan.setDataSource("ctrip_plan");
        recplan.setDataSourceId(String.valueOf(result.getId()));
//        recplan.setData(data);
        recplan.setMark(3);
        recplan.setUserId(-1L);
//        recplan.setStartTime(result.get);
        recplan.setDescription(result.getIntroduce());
        return recplan;
    }


    private static String dealImagePath(String url) {
        if (url == null)
            return null;
        if (url.contains("chanyouji.cn")) {
            //http://p.chanyouji.cn/125115/1400858533665p18okqicdn1lbve35176c385vctn.jpg?imageView/2/w/600/h/339
            //http://m.chanyouji.cn/plans/3.jpg?imageView/2/w/600/h/339
            return url.split("\\?")[0];
        }
        if (url.contains("c-ctrip.com")) {
            //http://dimg08.c-ctrip.com/images/tg/109/301/235/450e2cce579346eaaeb84d7a8b6de0c1_C_600_339.jpg
            return url.split("_C")[0] + ".jpg";
        }
        return UploadToQiniu(url);
    }


    private static String UploadToQiniu(String imageUrl) {
        byte[] bytes = downloadBytes(imageUrl);
        if (bytes == null) {
            return imageUrl;
        }
        String filename = imageUrl;
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = "recplan/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(bytes, path);
        return path;
    }

    private static byte[] downloadBytes(String sourceUrl) {
        HttpGet httpGet = new HttpGet(sourceUrl);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        //        //抓包检查调试
//        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        try {
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3");
            response = httpClient.execute(httpGet);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateTripCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        RecommendPlanTripService recommendPlanTripService = SpringContextHolder.getBean("recommendPlanTripService");
        Criteria<RecommendPlanTrip> c = new Criteria<RecommendPlanTrip>(RecommendPlanTrip.class);
        c.like("coverImg", "%ctrip.com%");
        c.eq("dataSource", "ctrip");

        List<RecommendPlanTrip> recommendPlanTrips = recommendPlanTripService.gets(1000000, c);
        for (RecommendPlanTrip recommendPlanTrip : recommendPlanTrips) {
            String cover = recommendPlanTrip.getCoverImg().replace("_C_200_200", "");
            if (cover == null)
                continue;
            String covery_large = QiniuUtil.UploadToQiniu(cover, "plan/" + recommendPlanTrip.getId());

            String json = null;
            try {
                json = HttpUtil.HttpGetString("http://7u2inn.com2.z0.glb.qiniucdn.com/" + covery_large + "?imageInfo");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!json.contains("error")) {
                qiniuImageInfo imageInfo = new Gson().fromJson(json, qiniuImageInfo.class);
                if (imageInfo.getWidth() == 640 && imageInfo.getHeight() == 320) {
                    System.out.println(recommendPlanTrip.getId() + "\t~~~" + covery_large);
                    recommendPlanTrip.setCoverImg(null);
                    recommendPlanTripService.update(recommendPlanTrip);
                } else {
                    recommendPlanTrip.setCoverImg(covery_large);
                    recommendPlanTripService.update(recommendPlanTrip);
                    System.out.println(cover);
                    System.out.println(covery_large);
                }
            } else {
                System.out.println(recommendPlanTrip.getId() + "\t--- " + json);
            }
        }
    }

    public static void updatePlanCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
        Criteria<RecommendPlan> c = new Criteria<RecommendPlan>(RecommendPlan.class);
        c.like("coverImg", "%ctrip.com%");
        c.eq("dataSource", "ctrip_plan");
        List<RecommendPlan> recommendPlen = recommendPlanService.gets(1000000, c);
        for (RecommendPlan recommendPlan : recommendPlen) {
            String cover = recommendPlan.getCoverPath().replace("_C_200_200", "");
            if (cover == null)
                continue;
            String covery_large = QiniuUtil.UploadToQiniu(cover, "plan/" + recommendPlan.getId());

            String json = null;
            try {
                json = HttpUtil.HttpGetString("http://7u2inn.com2.z0.glb.qiniucdn.com/" + covery_large + "?imageInfo");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!json.contains("error")) {
                qiniuImageInfo imageInfo = new Gson().fromJson(json, qiniuImageInfo.class);
                if (imageInfo.getWidth() == 640 && imageInfo.getHeight() == 320) {
                    System.out.println(recommendPlan.getId() + "\t~~~" + covery_large);
                    recommendPlan.setCoverPath(null);
                    recommendPlanService.update(recommendPlan);
                } else {
                    recommendPlan.setCoverPath(covery_large);
                    recommendPlanService.update(recommendPlan);
                    System.out.println(cover);
                    System.out.println(covery_large);
                }
            } else {
                System.out.println(recommendPlan.getId() + "\t--- " + json);
            }
        }
    }
}
