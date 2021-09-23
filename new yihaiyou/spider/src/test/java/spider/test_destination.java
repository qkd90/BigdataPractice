//package spider;
//
//import com.data.spider.service.baidu.BaiduDestinationService;
//import com.data.spider.service.pojo.baidu.Destination.BaiduDestinationDetail;
//import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
//import com.data.spider.service.pojo.tb.TbDestination;
//import com.data.spider.service.tb.TbDestinationService;
//import com.zuipin.util.SpringContextHolder;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import java.util.List;
//
///**
// * Created by Sane on 15/9/8.
// */
//public class test_destination {
//
//    private static ApplicationContext ac;
//
//    /**
//     * 百度城市信息抓取，tb_destination表
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        TbDestinationService tbDestinationService = SpringContextHolder.getBean("tbDestinationService");
//        TbDestination destinationCondition = new TbDestination();
//        destinationCondition.setAbs(null);
//        List<TbDestination> tbDestinations = tbDestinationService.list(destinationCondition, null);
//        for (TbDestination destination : tbDestinations) {
//            System.out.println(destination.getName());
//            BaiduPoiSuggestion poiSuggestion = BaiduDestinationService.getPoiSug(destination.getName());
//            if (destination.getCodeName()==null){
//            }
//            if (poiSuggestion != null) {
//                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
//                    if (suglist.getScene_layer().equals("4") || suglist.getScene_layer().equals("5")) {
//                        destination.setCodeName(suglist.getSurl());
//                        BaiduDestinationDetail destinationDetail = BaiduDestinationService.getDestinationDetail(suglist.getSid());
//                        BaiduDestinationDetail.DataEntity data = destinationDetail.getData();
//                        if (destinationDetail.getData().getNew_geography_history() == null)
//                            continue;
//                        destination.setId(destination.getId());
////                        destination.setAbs(data.getAbs().getDesc());
//                        destination.setAbs(data.getAbs().getInfo().getAbstractX());
//                        destination.setAdviceTime(data.getAbs().getInfo().getRecommend_visit_time());
//                        destination.setBestVisitTime(data.getAbs().getInfo().getBest_visit_time());
//                        String keys = "";
//                        for (BaiduDestinationDetail.DataEntity.NewGeographyHistoryEntity.ListEntity listEntity : destinationDetail.getData().getNew_geography_history().getList()) {
//                            String key = listEntity.getKey();
//                            String desc = listEntity.getDesc();
//                            keys += "\t" + key;
//                            if (key.contains("历史")) {
//                                destination.setHistory(desc.length() < 2000 ? desc : desc.substring(0, 2000));
//                            } else if (key.contains("艺术")) {
//                                destination.setArt(desc.length() < 2000 ? desc : desc.substring(0, 2000));
//                            } else if (key.contains("气候")) {
//                                destination.setWeather(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            } else if (key.contains("地理")) {
//                                destination.setGeography(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            } else if (key.contains("环境")) {
//                                destination.setEnvironment(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            } else if (key.contains("文化")) {
//                                destination.setCulture(desc.length() < 2000 ? desc : desc.substring(0, 2000));
//                            } else if (key.contains("言")) {
//                                destination.setLanguage(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            } else if (key.contains("节")) {
//                                destination.setFestival(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            } else if (key.contains("宗教")) {
//                                destination.setReligion(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            } else if (key.contains("族")) {
//                                destination.setNation(desc.length() < 1000 ? desc : desc.substring(0, 1000));
//                            }
//                        }
//                        try {
//                            tbDestinationService.update(destination);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
////                        TbDestinationExtendService.
//                        System.err.println(destination.getId() + "\t" + destination.getName() + "\t" + "\t" + keys);
//                    }
//
//                }
//            }
//
//        }
//    }
//}
