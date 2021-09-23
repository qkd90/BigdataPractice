//package spider;
//
//import com.data.spider.service.ctrip.CtripService;
//import com.data.spider.service.data.DataCityService;
//import com.data.spider.service.data.DataRecplanDayService;
//import com.data.spider.service.data.DataRecplanService;
//import com.data.spider.service.data.DataRecplanTripService;
//import com.data.spider.service.pojo.ctrip.H5ScheduleDetail;
//import com.data.spider.service.pojo.ctrip.H5ScheduleList;
//import com.data.spider.service.pojo.data.DataCity;
//import com.data.spider.service.pojo.data.DataRecplan;
//import com.data.spider.service.pojo.data.DataRecplanDay;
//import com.data.spider.service.pojo.data.DataRecplanTrip;
//import com.data.spider.util.HttpClientUtils;
//import com.data.spider.util.QiniuUtil;
//import com.data.spider.util.UUIDUtil;
//import com.framework.hibernate.util.Criteria;
//import com.google.gson.Gson;
//import com.zuipin.util.SpringContextHolder;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.util.EntityUtils;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.List;
//
///**
// * Created by Sane on 15/10/27.
// */
//public class test_plan {
//    private static ApplicationContext ac;
//
//    //采集行程的标签
//    public static void main1111(String[] args) {
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        CtripService ctripService = new CtripService();
//        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
//
//        //遍历城市
//        List<DataCity> dataCities = getDataCities(dataCityService);
//        for (DataCity city : dataCities) {
//            int ctripId = city.getCtripCityId();
////            System.err.println(city.getCityName() + "\t" + ctripId);
//            //该城市的所有推荐行程，存入recplan
//            List<H5ScheduleList.ResultsEntity> results = ctripService.getH5ScheduleAll(ctripId);
//            for (H5ScheduleList.ResultsEntity result : results) {
//
//                List<H5ScheduleList.ResultsEntity.TagListEntity> tagLsit = result.getTagList();
//
//                for (H5ScheduleList.ResultsEntity.TagListEntity tag : tagLsit) {
//                    int id = tag.getTagId();
//                    String name = tag.getTagName();
//                    System.out.println(city.getCityName() + "\t" + city.getId() + "\t" + id + "\t" + name);
//                }
//            }
//        }
//    } //采集行程的标签
//
//    public static void main3(String[] args) {
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        CtripService ctripService = new CtripService();
//        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
//
//        //遍历城市
//        List<DataCity> dataCities = getDataCities(dataCityService);
//        for (DataCity city : dataCities) {
//            int ctripId = city.getCtripCityId();
////            System.err.println(city.getCityName() + "\t" + ctripId);
//            //该城市的所有推荐行程，存入recplan
//            List<H5ScheduleList.ResultsEntity> results = ctripService.getH5ScheduleAll(ctripId);
//            if (results == null) {
//                System.out.println(city.getCityName() + "   无行程");
//                continue;
//            }
//            for (H5ScheduleList.ResultsEntity result : results) {
//
//                System.out.println(city.getCityName() + "\t" + city.getId() + "\t" + result.getId() + result.getTitle());
//            }
//        }
//    }
//
//    //采集行程
//    public static void main(String[] args) {
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        CtripService ctripService = new CtripService();
//        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
//        DataRecplanService dataRecplanService = SpringContextHolder.getBean("dataRecplanService");
//        DataRecplanDayService dataRecplanDayService = SpringContextHolder.getBean("dataRecplanDayService");
//        DataRecplanTripService dataRecplanTripService = SpringContextHolder.getBean("dataRecplanTripService");
//
//
//        //遍历城市
//        List<DataCity> dataCities = getDataCities(dataCityService);
//        for (DataCity city : dataCities) {
//            int ctripId = city.getCtripCityId();
//            System.err.println(city.getCityName() + "\t" + ctripId);
//            //该城市的所有推荐行程，存入recplan
//            List<H5ScheduleList.ResultsEntity> results = ctripService.getH5ScheduleAll(ctripId);
//            if (results == null) {
//                System.out.println(city.getCityName() + "   无行程");
//                continue;
//            }
//            for (H5ScheduleList.ResultsEntity result : results) {
//                H5ScheduleDetail detail = ctripService.getH5ScheduleDetail(result.getId());
//                DataRecplan recplan = getDataRecplan(city, result, new Gson().toJson(detail));
//                recplan.setStatus(-100);
//                dataRecplanService.save(recplan);
////                //推荐行程详情，存入recplan_day
////                int dayNum = detail.getNodeList().size();
////                for (int i = 0; i < dayNum; i++) {
////                    H5ScheduleDetail.NodeListEntity node = detail.getNodeList().get(i);
////                    DataRecplanDay day = getDataRecplanDay(String.valueOf(city.getId()), recplan.getId(), node);
////                    if (i == dayNum - 1) {
////                        day.setIsLast("T");
////                    } else {
////                        day.setIsLast("F");
////                    }
////                    dataRecplanDayService.save(day);
////                    //行程的各个节点：景点、美食、餐厅、交通，存入recplan_trip
////                    for (H5ScheduleDetail.NodeListEntity.ContentsEntity content : node.getContents()) {
////                        DataRecplanTrip trip = getDataRecplanTrip(recplan.getId(), day.getId(), String.valueOf(city.getId()), content);
////                        dataRecplanTripService.save(trip);
////                    }
////                }
//            }
//            //更新data_city
//            city.setCtripPlanUsed(1);
//            dataCityService.update(city);
//        }
//    }
//
//    private static DataRecplanTrip getDataRecplanTrip(long recplanId, long dayId, String cityId, H5ScheduleDetail.NodeListEntity.ContentsEntity content) {
//        DataRecplanTrip trip = new DataRecplanTrip();
//
//
//        List<H5ScheduleDetail.NodeListEntity.ContentsEntity.ExtraInfoListEntity> extractInfoLists = content.getExtraInfoList();
//        //contentType:11景点，6城市，18娱乐演出消费，13购物
//        if (content.getContentType() == 14) {//酒店
//            //descType:6评分11价格9地址
////            String price = "";
////            trip.setOldPrice(Double.parseDouble(price));
//            trip.setTripType(3);
//        } else if (content.getContentType() == 12) {//美食
//            //descType:0人均价格，1菜系
////            String price = "";
////            trip.setOldPrice(Double.parseDouble(price));
//            trip.setTripType(2);
//        } else {
//            //景点及其它未识别的类型
//            //descType:3开放时间4景点类别
////            String price = "";
////            trip.setOldPrice(Double.parseDouble(price));
//            trip.setTripType(1);
//        }
//
//        trip.setRecplanId(recplanId);
//        trip.setRecdayId(dayId);
//        trip.setCityCode(cityId);
//        trip.setScenicName(content.getPoiName());
//        trip.setOrderNum(content.getSortIndex());
//        trip.setSort(content.getSortIndex());
//        trip.setExa(content.getIntroduction());
////        trip.setTripDesc(content.getIntroduction());
//        trip.setCoverImg(dealImagePath(content.getCoverImageUrl()));
//        trip.setDataSource("ctrip");
//        trip.setDataSourceId(content.getBusinessId());
//        trip.setDataSourceType(String.valueOf(content.getContentType()));
//        return trip;
//    }
//
//    private static DataRecplanDay getDataRecplanDay(String cityId, long recplanId, H5ScheduleDetail.NodeListEntity node) {
//        DataRecplanDay day = new DataRecplanDay();
//        day.setRecplanId(recplanId);
//        day.setDay((short) node.getDayIndex());
//        day.setScenics(node.getContents().size());
//        day.setCitys(cityId);
//        day.setDescription(node.getIntroduction());
//        return day;
//    }
//
//    private static List<DataCity> getDataCities(DataCityService dataCityService) {
//        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
////        c.ne("ctripPlanUsed", 1);
////        c.isNotNull("ctripPlanUsed");
//        c.isNotNull("ctripCityId");
////        c.eq("id", 330800);
//        return dataCityService.gets(10000, c);
//    }
//
//    private static DataRecplan getDataRecplan(DataCity city, H5ScheduleList.ResultsEntity result, String data) {
//        DataRecplan recplan = new DataRecplan();
//        recplan.setPlanName(result.getTitle());
//        recplan.setDescription(result.getIntroduce());
//        recplan.setCoverPath(dealImagePath(result.getCoverImageUrl()));
//        recplan.setDays(result.getDays());
//        recplan.setScenics(result.getPoiCount());
//        String tags = "";
//        for (H5ScheduleList.ResultsEntity.TagListEntity tag : result.getTagList()) {
//            tags += tag.getTagName() + ",";
//        }
//        recplan.setTagStr(tags);
//        recplan.setStatus(1);
//        recplan.setCityId((long) city.getId());
//        recplan.setCityIds(String.valueOf(city.getId()));
//        recplan.setDataSource("ctrip");
//        recplan.setDataSourceId(String.valueOf(result.getId()));
//        recplan.setData(data);
//        recplan.setMark(3);
//        return recplan;
//    }
//
//
//    private static String dealImagePath(String url) {
//        if (url == null)
//            return null;
//        if (url.contains("chanyouji.cn")) {
//            //http://p.chanyouji.cn/125115/1400858533665p18okqicdn1lbve35176c385vctn.jpg?imageView/2/w/600/h/339
//            //http://m.chanyouji.cn/plans/3.jpg?imageView/2/w/600/h/339
//            return url.split("\\?")[0];
//        }
//        if (url.contains("c-ctrip.com")) {
//            //http://dimg08.c-ctrip.com/images/tg/109/301/235/450e2cce579346eaaeb84d7a8b6de0c1_C_600_339.jpg
//            return url.split("_C")[0] + ".jpg";
//        }
//        return UploadToQiniu(url);
//    }
//
//
////    //更新coverImg
////    public static void main(String[] args) {
////        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//////        CtripService ctripService = new CtripService();
//////        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
//////        DataRecplanService dataRecplanService = SpringContextHolder.getBean("dataRecplanService");
//////        DataRecplanDayService dataRecplanDayService = SpringContextHolder.getBean("dataRecplanDayService");
////        DataRecplanTripService dataRecplanTripService = SpringContextHolder.getBean("dataRecplanTripService");
////        Criteria<DataRecplanTrip> c = new Criteria<DataRecplanTrip>(DataRecplanTrip.class);
////        c.like("coverImg", "youji");
////        List<DataRecplanTrip> trips = dataRecplanTripService.gets(10321, c);
////        for (DataRecplanTrip trip:trips){
////            System.out.println(trip.getScenicName() +"\t"+trip.getCoverImg());
////            trip.setCoverImg(UploadToQiniu(trip.getCoverImg()));
////            dataRecplanTripService.update(trip);
////        }
////    }
////    //用introduction修正trip的exa
////    public static void main(String[] args) {
////        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//////        CtripService ctripService = new CtripService();
//////        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
////        DataRecplanService dataRecplanService = SpringContextHolder.getBean("dataRecplanService");
//////        DataRecplanDayService dataRecplanDayService = SpringContextHolder.getBean("dataRecplanDayService");
////        DataRecplanTripService dataRecplanTripService = SpringContextHolder.getBean("dataRecplanTripService");
////        Criteria<DataRecplan> c = new Criteria<DataRecplan>(DataRecplan.class);
////        List<DataRecplan> plans = dataRecplanService.gets(10321, c);
////        for (DataRecplan plan:plans){
////            String data = plan.getData();
////            System.out.println(plan.getId() +"\t"+data);
////            H5ScheduleDetail detail = new Gson().fromJson(data,H5ScheduleDetail.class);
////            Criteria<DataRecplanTrip> c1 = new Criteria<DataRecplanTrip>(DataRecplanTrip.class);
////            List<DataRecplanTrip>  trips = dataRecplanTripService.gets(1000,c1);
////            for(DataRecplanTrip trip:trips){
////
////            }
////
////        }
////    }
//
//    //更新coverImg
////    public static void main(String[] args) {
////        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
////        DataRecplanService dataRecplanService = SpringContextHolder.getBean("dataRecplanService");
////        Criteria<DataRecplan> c = new Criteria<DataRecplan>(DataRecplan.class);
////        c.like("coverPath", "http");
////        List<DataRecplan> trips = dataRecplanService.gets(10321, c);
////        for (DataRecplan trip:trips){
////            System.out.println(trip.getPlanName() +"\t"+trip.getCoverPath());
////            trip.setCoverPath(UploadToQiniu(trip.getCoverPath()));
////            dataRecplanService.update(trip);
////        }
////    }
//
//    //    //更新coverImg
////    public static void main(String[] args) {
////        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//////        CtripService ctripService = new CtripService();
//////        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
//////        DataRecplanService dataRecplanService = SpringContextHolder.getBean("dataRecplanService");
//////        DataRecplanDayService dataRecplanDayService = SpringContextHolder.getBean("dataRecplanDayService");
////        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
////        DataRecplanTripService dataRecplanTripService = SpringContextHolder.getBean("dataRecplanTripService");
////        Criteria<DataRecplanTrip> c = new Criteria<DataRecplanTrip>(DataRecplanTrip.class);
////        c.like("coverImg", "http");
////        List<DataRecplanTrip> trips = dataRecplanTripService.gets(10000, c);
////        for (DataRecplanTrip trip:trips){
////            System.out.println(trip.getScenicName() +"\t"+trip.getCoverImg());
////            trip.setCoverImg(UploadToQiniu(trip.getCoverImg()));
////            dataRecplanTripService.update(trip);
////        }
////    }
//    //更新plan的coverImg
//    public static void main31(String[] args) {
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        DataRecplanService dataRecplanService = SpringContextHolder.getBean("dataRecplanService");
//        Criteria<DataRecplan> c = new Criteria<DataRecplan>(DataRecplan.class);
//        c.like("coverPath", "http");
//        List<DataRecplan> trips = dataRecplanService.gets(500, c);
//        for (DataRecplan trip : trips) {
//            System.out.println(trip.getPlanName() + "\t" + trip.getCoverPath());
//            trip.setCoverPath(UploadToQiniu(trip.getCoverPath()));
//            dataRecplanService.update(trip);
//        }
//    }
//
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
//}
