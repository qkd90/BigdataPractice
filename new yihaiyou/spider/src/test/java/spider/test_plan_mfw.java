//package spider;
//
//import com.data.spider.service.mfw.MfwService;
//import com.data.spider.service.pojo.mfw.ContentEntity;
//import com.data.spider.service.pojo.mfw.TravelnoteDetail;
//import com.data.spider.service.pojo.mfw.TravelnoteList;
//import com.data.spider.service.pojo.tb.AreaRelation;
//import com.data.spider.service.pojo.tb.RecommendPlan;
//import com.data.spider.service.pojo.tb.RecommendPlanDay;
//import com.data.spider.service.pojo.tb.RecommendPlanPhoto;
//import com.data.spider.service.pojo.tb.RecommendPlanTrip;
//import com.data.spider.service.tb.AreaRelationService;
//import com.data.spider.service.tb.RecommendPlanDayService;
//import com.data.spider.service.tb.RecommendPlanPhotoService;
//import com.data.spider.service.tb.RecommendPlanService;
//import com.data.spider.service.tb.RecommendPlanTripService;
//import com.framework.hibernate.util.Criteria;
//import com.google.gson.Gson;
//import com.zuipin.util.DateUtils;
//import com.zuipin.util.SpringContextHolder;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.List;
//
//
///**
// * Created by Sane on 15/10/27.
// */
//public class test_plan_mfw {
//    private static ApplicationContext ac;
//
//    public static void main(String[] args) {
////        crawlAll();
//        crawlOne();
//    }
//
//    private static void crawlOne() {
//
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        AreaRelation city = new AreaRelation();
//        city.setId(110000L);
//        city.setName("北京");
//        MfwService mfwService = new MfwService();
//        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
//        RecommendPlanDayService recommendPlanDayService = SpringContextHolder.getBean("recommendPlanDayService");
//        RecommendPlanTripService recommendPlanTripService = SpringContextHolder.getBean("recommendPlanTripService");
//        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
////        TravelnoteDetail detail = mfwService.getTravelnoteDetail(3442787);
//        TravelnoteDetail detail = mfwService.getTravelnoteDetail(3484840);
//        crawlPlan(detail, recommendPlanService, recommendPlanDayService, recommendPlanTripService, recommendPlanPhotoService, city);
//
//    }
//
//    //采集行程
//    public static void crawlAll() {
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        MfwService mfwService = new MfwService();
//        AreaRelationService areaRelationService = SpringContextHolder.getBean("areaRelationService");
//        RecommendPlanService recommendPlanService = SpringContextHolder.getBean("recommendPlanService");
//        RecommendPlanDayService recommendPlanDayService = SpringContextHolder.getBean("recommendPlanDayService");
//        RecommendPlanTripService recommendPlanTripService = SpringContextHolder.getBean("recommendPlanTripService");
//        RecommendPlanPhotoService recommendPlanPhotoService = SpringContextHolder.getBean("recommendPlanPhotoService");
//        //遍历城市
//        List<AreaRelation> dataCities = getDataCities(areaRelationService);
//        for (AreaRelation city : dataCities) {
//            System.out.println(city.getId() + "\t" + city.getName());
//            if (city.getId() % 10000 == 0 || city.getId() % 100 != 0)
//                continue;
//            int mfwNoteCity = city.getMfwNoteCity();
//            //该城市的所有推荐行程，存入recplan
//            List<TravelnoteList.DataEntity.ListEntity> results = mfwService.getTravelnodeAll(String.valueOf(mfwNoteCity));
//            System.err.println(city.getName() + "\t" + mfwNoteCity + "\t" + results.size());
//            for (TravelnoteList.DataEntity.ListEntity result : results) {
//                try {
//                    System.out.print(result.getId() + "\t" + result.getMddname() + "\t" + result.getTitle());
//                    System.out.println("-----------------------------------");
//                    TravelnoteDetail detail = mfwService.getTravelnoteDetail(result.getId());
//                    crawlPlan(detail, recommendPlanService, recommendPlanDayService, recommendPlanTripService, recommendPlanPhotoService, city);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
////            city.setCtripPlanUsed(results.size());
////            dataCityService.update(city);
//        }
//    }
//
//    private static void crawlPlan(TravelnoteDetail detail, RecommendPlanService recommendPlanService, RecommendPlanDayService recommendPlanDayService, RecommendPlanTripService recommendPlanTripService, RecommendPlanPhotoService recommendPlanPhotoService, AreaRelation city) {
//        if (detail.getData().getContent() == null || detail.getData().getEx() == null || detail.getData().getEx().get$aScheduleInfo() == null) {
//            return;
//        }
//        List<ContentEntity> detailResult = detail.getData().getContent();
//        int nodeSize = detailResult.size();
//        RecommendPlan recplan = getDataRecplan(city, detail.getData());
//        recommendPlanService.save(recplan);
//        //推荐行程详情，存入recplan_day
//        int sum = 0;
//
//        //每天与poi之间的关系.可以用来判断天数
////        List<List<String>> poiSummary = detail.getData().getEx().get$aScheduleInfo().getDay_poi_summary();
//
//        int daySum = 1;
//        String nameTmp = "";
//        boolean hasTrip = false, addDay = false;
//        int dayCount = detail.getData().getEx().getDays();
//        for (int tmp = 0; tmp < nodeSize; tmp++) {
//            ContentEntity node = detailResult.get(tmp);
//            if ("paragraph".equals(node.getType())) {
//                String content = new Gson().toJson(node.getContent());
//                System.out.println("paragraph:" + content);
//                if (content.matches(".*\"sn\":\".*(?:第.*?天|D\\d|DAY).*?\".*")) {
//                    sum = tmp + 1;
//                    break;
//                }
//            }
//        }
//        for (int d = 0; d <= dayCount && sum < nodeSize; d++) {
//            System.out.println("begin day : " + d);
////            List<String> summaries = poiSummary.get(d);
//            RecommendPlanDay day = getDataRecplanDay(String.valueOf(city.getId()), recplan, daySum++);
//            recommendPlanDayService.save(day);
////            for (String summary : summaries) {
////                System.out.println("summary:" + summary);
//                RecommendPlanTrip trip = null;
//                for (; sum < nodeSize; sum++) {
//                    ContentEntity node = detailResult.get(sum);
//                    System.out.println("-----------" + sum + "-----------" + node.getType());
//                    if ("image".equals(node.getType()) && node.getImage().getExt() != null) {
//                        System.out.println(node.getImage().getPid() + "\t" + node.getImage().getNew_iid());
//                        ContentEntity.Image.ExtEntity ext = node.getImage().getExt();
//                        //判断景点名
//                        if ( !nameTmp.equals(ext.getName())) {
//                            if (!hasTrip) {
//                                hasTrip = true;
//                                System.out.println("add trip:" + ext.getName());
//                                nameTmp = ext.getName();
//                                trip = getDataRecplanTrip(recplan, day, String.valueOf(city.getId()), node);
//                                System.out.println("save trip:" + trip.getScenicName());
//                                recommendPlanTripService.save(trip);
////                                break;
//                            } else {
//                                System.out.println("add image");
//                                ContentEntity.Image pic = node.getImage();
//                                RecommendPlanPhoto photo = new RecommendPlanPhoto(recplan.getId(), day.getId(), trip.getId(), pic.getOimgurl());
//                                recommendPlanPhotoService.save(photo);
//                            }
//                        }
////                        else if (d < dayCount - 1 && poiSummary.get(d + 1).contains(ext.getName())) {
////                            System.out.println("poiSummary matches:" + (ext.getName()));
////                            addDay = true;
////                            break;
////                        }
//
////                        if (!nameTmp.equals(ext.getName())) {
////                            hasTrip = true;
////                            System.out.println("change trip:" + ext.getName());
////                            nameTmp = ext.getName();
////                            trip = getDataRecplanTrip(recplan, day, String.valueOf(city.getId()), node);
////                            System.out.println("save trip:" + trip.getScenicName());
////                            recommendPlanTripService.save(trip);
//////                            sum--;
//////                            break;
////                        }
//                    }
//
//                    if (hasTrip && "container".equals(node.getType()) && trip.getExa() == null) {
//                        System.out.println("add desc");
//                        String txt = "";
//                        for (ContentEntity.Container container : node.getContainer()) {
//                            txt += container.getContent();
//                        }
//                        trip.setExa(txt);
//                        recommendPlanTripService.update(trip);
//                    }
//                    if ("paragraph".equals(node.getType())) {
//                        String content = new Gson().toJson(node.getContent());
//                        System.out.println("paragraph:" + content);
//                        if (content.matches(".*\"sn\":\".*(?:第.*?天|D\\d|DAY).*?\".*")) {
//                            addDay = true;
//                            break;
//                        }
//                    }
//
//                }
//                if (addDay) {
//                    hasTrip = false;
//                    System.out.println("add day");
//                    addDay = false;
//                    sum++;
//                    break;
//                }
////            }
//        }
//
//    }
//
//    private static RecommendPlanTrip getDataRecplanTrip(RecommendPlan recommendPlan, RecommendPlanDay day, String cityId, ContentEntity node) {
//        RecommendPlanTrip trip = new RecommendPlanTrip();
////        if (poi.getBusinessId() == 0) {
////            return null;
////        }
//        ContentEntity.Image.ExtEntity ext = node.getImage().getExt();
//        trip.setTripType(0);
////        trip.setScenicId(2000L);
////        //poiType:餐厅1,购物4、酒店2、景点3、其他0、机场6,购物5
//        switch (ext.getType_id()) {
//            case 1:
//                trip.setTripType(2);
//                break;
//            case 2:
//                trip.setTripType(3);
//                break;
//            case 3:
//                trip.setTripType(1);
//                break;
//            case 4:
//                trip.setTripType(0);
//                break;
//            case 5:
//                trip.setTripType(0);
//                break;
//        }
//
//
//        trip.setRecplanId(recommendPlan.getId());
//        trip.setRecdayId(day.getId());
//        trip.setCityCode(cityId);
//        trip.setScenicName(ext.getName());
//        trip.setCoverImg(node.getImage().getOimgurl());
////        trip.setOrderNum(poi.getSortIndex());
////        trip.setSort(poi.getSortIndex());
//
//        trip.setDataSource("mfw_note");
//        trip.setDataSourceId(ext.getId());
//        trip.setDataSourceType(String.valueOf(ext.getType_id()));
//        return trip;
//    }
//
//    private static RecommendPlanDay getDataRecplanDay(String cityId, RecommendPlan recommendPlan, int dayIndex) {
//
//        RecommendPlanDay day = new RecommendPlanDay();
//        day.setRecplanId(recommendPlan.getId());
//        day.setDay((short) dayIndex);
//        day.setCitys(cityId);
//        return day;
//    }
//
//    private static List<AreaRelation> getDataCities(AreaRelationService dataCityService) {
//        Criteria<AreaRelation> c = new Criteria<AreaRelation>(AreaRelation.class);
//        c.isNotNull("mfwNoteCity");
//        return dataCityService.gets(2, c);
//    }
//
//    //
//    private static RecommendPlan getDataRecplan(AreaRelation city, TravelnoteDetail.DataEntity result) {
//        RecommendPlan recplan = new RecommendPlan();
//        recplan.setPlanName(result.getTitle());
//
//        recplan.setCoverPath(result.getOimgurl());
//        recplan.setDays(result.getEx().getDays());
////        recplan.setTagStr(tags);
//        recplan.setCityId(city.getId() * 1L);
//        recplan.setCityIds(String.valueOf(city.getId()));
//        recplan.setDataSource("mfw_note");
//        recplan.setDataSourceId(String.valueOf(result.getId()));
//        recplan.setMark(3);
////        recplan.setUserId(-1L);
////        ContentEntity node = result.getNodes().get(0);
////        if (node.getNodeType() == 3) {
////            recplan.setDescription(node.getText());
////        } else {
////            recplan.setDescription(result.getTravelSeoTitle());
////        }
//        recplan.setCreateTime(DateUtils.getDate(result.getCtime(), "yyyy-MM-dd HH:mm:ss"));
//        recplan.setStartTime(DateUtils.getDate(result.getEx().getSdate(), "yyyy/MM/dd"));
//        recplan.setUserId(157L);
//        recplan.setStatus(2);
//        recplan.setDeleteFlag(2);
//        return recplan;
//    }
//
//
////    private static String UploadToQiniu(String imageUrl) {
////        byte[] bytes = downloadBytes(imageUrl);
////        if (bytes == null) {
////            return imageUrl;
////        }
////        String filename = imageUrl;
////        String suffix = "";
////        if (StringUtils.isNotBlank(filename)) {
////            String[] nameArr = filename.split("\\.");
////            suffix = "." + nameArr[nameArr.length - 1];
////        }
////        String path = "recplan/" + UUIDUtil.getUUID() + suffix;
////        QiniuUtil.upload(bytes, path);
////        return path;
////    }
////
////    private static byte[] downloadBytes(String sourceUrl) {
////        HttpGet httpGet = new HttpGet(sourceUrl);
////        HttpResponse response;
////        HttpClient httpClient = HttpClientUtils.getHttpClient();
////        //        //抓包检查调试
//////        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//////        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
////        try {
////            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3");
////            response = httpClient.execute(httpGet);
////            return EntityUtils.toByteArray(response.getEntity());
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
//}
