package spider;

import com.data.spider.service.baidu.BaiduDestinationService;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.ctrip.CtripWebService;
import com.data.spider.service.data.DataCityService;
import com.data.spider.service.pojo.baidu.Destination.BaiduDestinationDetail;
import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
import com.data.spider.service.pojo.ctrip.District;
import com.data.spider.service.pojo.ctrip.PoiTip;
import com.data.spider.service.pojo.ctrip.SearchResult;
import com.data.spider.service.pojo.ctrip.WebSearchResult;
import com.data.spider.service.pojo.data.DataCity;
import com.data.spider.service.pojo.tb.TbArea;
import com.data.spider.service.pojo.tb.TbAreaExtend;
import com.data.spider.service.tb.TbAreaExtendService;
import com.data.spider.service.tb.TbAreaService;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/9/8.
 */
public class test_city {

    private static ApplicationContext ac;

    /**
     * 百度城市信息抓取，tb_area_extend表
     *
     */
    public static void getBaiduCityInfo() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbAreaService tbAreaService = SpringContextHolder.getBean("tbAreaService");
        TbAreaExtendService tbAreaExtendService = SpringContextHolder.getBean("tbAreaExtendService");
        TbArea areaCondition = new TbArea();
        areaCondition.setLevel(2);
        areaCondition.setId(350700L);

        List<TbArea> areas = tbAreaService.list(areaCondition, null);

//        TbAreaExtend extend1 = new TbAreaExtend();
//        extend1.setLevel(2);
//        List<TbAreaExtend> areas = tbAreaExtendService.list(areaCondition, null);

        for (TbArea area : areas) {
            BaiduPoiSuggestion poiSuggestion = BaiduDestinationService.getPoiSug(area.getName());
            if (poiSuggestion != null) {
                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
                    if (suglist.getScene_layer().equals("4")) {
                        String sid = suglist.getSid();
                        BaiduDestinationDetail destinationDetail = BaiduDestinationService.getDestinationDetail(sid);
                        if (destinationDetail.getData().getNew_geography_history() == null)
                            continue;
                        TbAreaExtend extend = getExtend(destinationDetail, area, new TbAreaExtend());
                        try {
                            tbAreaExtendService.save(extend);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        tbAreaExtendService.
                    }

                }
            }

        }
    }

    /**
     * 百度城市信息抓取，tb_area_extend表
     *
     */
    public static void getBaiduCityInfo2() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbAreaService tbAreaService = SpringContextHolder.getBean("tbAreaService");
        TbAreaExtendService tbAreaExtendService = SpringContextHolder.getBean("tbAreaExtendService");
        Criteria<TbAreaExtend> c = new Criteria<TbAreaExtend>(TbAreaExtend.class);
//        c.isNull("bestVisitTime");
//        c.isNotNull("bestVisitTime");
        c.in("id", new Integer[]{110000, 120000, 130000, 140000, 150000, 210000, 220000, 230000, 310000, 320000, 330000, 340000, 350000, 360000, 370000, 410000, 420000, 430000, 440000, 450000, 460000, 500000, 510000, 520000, 530000, 540000, 610000, 620000, 630000, 640000, 650000, 710000, 810000, 820000});
//        c.like("culture", "...");
//        c.gt("id", 450312);
        List<TbAreaExtend> extendList = tbAreaExtendService.gets(10000, c);

        for (TbAreaExtend extend : extendList) {
            TbArea areaCondition = new TbArea();
            areaCondition.setId((long) extend.getId());
            List<TbArea> areas = tbAreaService.list(areaCondition, null);
            TbArea area = areas.get(0);
            String name = area.getName();
            if (name.endsWith("地区")) {
                name = name.substring(0, name.length() - 2);
            }
            if (name.endsWith("市") || name.endsWith("县") || name.endsWith("区") || name.endsWith("省")) {
                name = name.substring(0, name.length() - 1);
            }

            System.out.println(name);

            BaiduPoiSuggestion poiSuggestion = BaiduDestinationService.getPoiSug(name);
            if (poiSuggestion != null) {
                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
                    if (suglist.getScene_layer().equals("2") || suglist.getScene_layer().equals("3") || suglist.getScene_layer().equals("4") || suglist.getScene_layer().equals("5")) {
                        String sid = suglist.getSid();
                        BaiduDestinationDetail destinationDetail = BaiduDestinationService.getDestinationDetail(sid);
                        if (destinationDetail.getData() == null)
                            continue;
                        extend = getExtend(destinationDetail, area, extend);
                        try {
                            tbAreaExtendService.update(extend);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }
            }

        }
    }

    private static TbAreaExtend getExtend(BaiduDestinationDetail destinationDetail, TbArea area, TbAreaExtend extend) {
        BaiduDestinationDetail.DataEntity data = destinationDetail.getData();
        extend.setId(area.getId().intValue());
        extend.setAbs(data.getAbs().getDesc());
        extend.setAbs(data.getAbs().getInfo().getAbstractX());
        extend.setAdviceTime(data.getAbs().getInfo().getRecommend_visit_time());
        extend.setBestVisitTime(data.getAbs().getInfo().getBest_visit_time());
        String keys = "";
        if (destinationDetail.getData().getNew_geography_history() != null)
            for (BaiduDestinationDetail.DataEntity.NewGeographyHistoryEntity.ListEntity listEntity : destinationDetail.getData().getNew_geography_history().getList()) {
                String key = listEntity.getKey();
                String desc = listEntity.getDesc();
                keys += "\t" + key;
                if (key.contains("历史")) {
                    extend.setHistory(desc.length() < 2000 ? desc : desc.substring(0, 2000));
                } else if (key.contains("艺术")) {
                    extend.setArt(desc.length() < 2000 ? desc : desc.substring(0, 2000));
                } else if (key.contains("气候")) {
                    extend.setWeather(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                } else if (key.contains("地理")) {
                    extend.setGeography(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                } else if (key.contains("环境")) {
                    extend.setEnvironment(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                } else if (key.contains("文化")) {
                    extend.setCulture(desc.length() < 2000 ? desc : desc.substring(0, 2000));
                } else if (key.contains("言")) {
                    extend.setLanguage(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                } else if (key.contains("节")) {
                    extend.setFestival(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                } else if (key.contains("宗教")) {
                    extend.setReligion(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                } else if (key.contains("族")) {
                    extend.setNation(desc.length() < 1000 ? desc : desc.substring(0, 1000));
                }
            }
        System.out.println(area.getId() + "\t" + area.getName() + "\t" + area.getFullPath() + "\t" + keys);
        return extend;
    }

    /**
     * 百度目的地信息抓取，tb_destination_extend表
     *
     */
    public static void getBaiduCityInfo3() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        String[] areas = {"杭州"};
        for (String area : areas) {
            boolean hasData = false;
            BaiduPoiSuggestion poiSuggestion = BaiduDestinationService.getPoiSug(area);
            if (poiSuggestion != null) {
                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
                    if (suglist.getScene_layer().equals("6"))
                        continue;
                    hasData = true;
                    BaiduDestinationDetail destinationDetail = BaiduDestinationService.getDestinationDetail(suglist.getSid());
                    BaiduDestinationDetail.DataEntity data = destinationDetail.getData();
                    if (data == null) {
                        System.err.println(area + "：：：无数据");
                    }
                    TbAreaExtend extend = new TbAreaExtend();
                    extend.setAbs(data.getAbs().getInfo().getAbstractX());
                    extend.setAdviceTime(data.getAbs().getInfo().getRecommend_visit_time());
                    extend.setBestVisitTime(data.getAbs().getInfo().getBest_visit_time());
                    String keys = "";
                    if (destinationDetail.getData().getNew_geography_history() != null) {
                        for (BaiduDestinationDetail.DataEntity.NewGeographyHistoryEntity.ListEntity listEntity : destinationDetail.getData().getNew_geography_history().getList()) {
                            String key = listEntity.getKey();
                            keys += "\t" + key;
                        }
                    }
                    System.out.println(area + "\t" + extend.getAbs() + "\t" + extend.getAdviceTime() + "\t" + extend.getBestVisitTime()
                            + "\t" + keys);
                }
            }
            if (!hasData) {
                System.err.println(area + "：无数据");
            }
        }
    }

    /**
     * 携程对应id抓取，data_city表
     *
     */
    public static void getCtripCityID() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DataCityService cityService = SpringContextHolder.getBean("dataCityService");
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
        List<DataCity> citys = cityService.gets(6000, c);
        System.out.println("名称\t区域\t匹配类型\t匹配名称\t匹配区域\turl");
        CtripService ctripService = new CtripService();
        for (DataCity city : citys) {
            String keyword = city.getCityName();
            ////按关键字获得搜索结果
            SearchResult searchResult = ctripService.search(keyword.trim());
            //交通枢纽类型变量
            boolean matched = false;
            String id = "";
            ////交通枢纽类型匹配
            for (SearchResult.Datum data : searchResult.data) {
                if (data.type.contains("district")) {
                    System.out.println(city.getCityName() + "\t"
                            + data.type + "\t" + data.word + "\t" + data.districtname + "\t" + data.url);
                    if (data.word.contains(keyword.replace("市", ""))) {
                        matched = true;
                        Pattern p = Pattern.compile("districtId=(\\d+)");
                        Matcher m = p.matcher(data.url);
                        if (m.find()) {
                            id = m.group(1);
                        }
                        break;
                    }
                }
            }
            if (matched) {
//                city.setCtripCityId(Integer.parseInt(id));
//                city.setCtripScenicUsed(0);
//                cityService.update(city);
            } else {
                System.err.println(keyword + "\t" + "未匹配");
            }

        }
    }

    /**
     * 携程对应py抓取，data_city表
     *
     */
    public static void getCtripPy() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DataCityService cityService = SpringContextHolder.getBean("dataCityService");
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
        c.isNull("ctripCityPy");
        List<DataCity> citys = cityService.gets(6000, c);
        System.out.println("名称\t区域\t匹配类型\t匹配名称\t匹配区域\turl");
        CtripWebService ctripWebService = new CtripWebService();
        for (DataCity city : citys) {
            String keyword = city.getCityName();
            ////按关键字获得搜索结果
            WebSearchResult searchResult = ctripWebService.search(keyword.trim());
            //交通枢纽类型变量
            boolean matched = false;
            String py = "";
            String id = "";
            ////交通枢纽类型匹配
            for (WebSearchResult.ListEntity data : searchResult.getList()) {
                if (data.getType().equals("icon_des")) {
                    System.out.println(city.getCityName() + "\t"
                            + data.getName() + "\t" + data.getDestName() + "\t" + data.getDestId() + "\t" + data.getUrl());
                    if (data.getName().contains(keyword.replace("市", ""))) {
                        matched = true;
                        Pattern p = Pattern.compile("/place/([A-z]+)(\\d+)\\.html");
                        Matcher m = p.matcher(data.getUrl());
                        if (m.find()) {
                            py = m.group(1);
                            id = m.group(2);
                        }
                        break;
                    }
                }
            }
            if (matched) {

                System.err.println(id + "\t" + py);
                if (city.getCtripCityId() == null) {
                    city.setCtripCityId(Integer.parseInt(id));
                    city.setCtripScenicUsed(0);
                    city.setCtripCityPy(py);
                    cityService.update(city);
                } else if (String.valueOf(city.getCtripCityId()).equals(id)) {
                    city.setCtripCityPy(py);
                    cityService.update(city);
                }


            } else {
                System.err.println(keyword + "\t" + "未匹配");
            }

        }
    }


    /**
     * 携程封面抓取，data_city表
     *
     */
    public static void getCityCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbAreaService tbAreaService = SpringContextHolder.getBean("tbAreaService");
        TbAreaExtendService tbAreaExtendService = SpringContextHolder.getBean("tbAreaExtendService");
        Criteria<TbAreaExtend> c = new Criteria<TbAreaExtend>(TbAreaExtend.class);
        c.isNull("cover");
//        c.like("id", "00", MatchMode.END);
//        c.eq("id", 341000);
        c.in("id", new Integer[]{652300, 632300});
        List<TbAreaExtend> extendList = tbAreaExtendService.gets(10000, c);
        System.out.println("名称\t区域\t匹配类型\t匹配名称\t匹配区域\turl");
        CtripService ctripService = new CtripService();
        for (TbAreaExtend extend : extendList) {
            TbArea areaCondition = new TbArea();
            areaCondition.setId((long) extend.getId());
            String name = tbAreaService.list(areaCondition, null).get(0).getName();
            if (name.endsWith("市") || name.endsWith("县") || name.endsWith("区") || name.endsWith("省")) {
                name = name.substring(0, name.length() - 1);
            }
            System.out.println(name);
            PoiTip tip = ctripService.getPoiTip(name);
            if (tip.getPoiList().size() == 0)
                continue;
            int cid = tip.getPoiList().get(0).getDistrictId();
            District district = ctripService.getDistrict(cid);
            if (district == null || district.getDistrictHomePageInfoInTravel() == null || district.getDistrictHomePageInfoInTravel().getDistrictImage() == null)
                continue;
            String img = district.getDistrictHomePageInfoInTravel().getDistrictImage().getImageUrl().replace("_R_640_320", "");
            String pic = downLoadPic(img, name);
            System.err.println(extend.getId() + "\t" + name + "\t" + pic);
            extend.setCover(pic);
            tbAreaExtendService.update(extend);
        }
    }


    private static String downLoadPic(String pic_url, String delicacyName) {
//        byte[] bytes = downloadBytes(pic_url);
//        if(bytes==null)
//            return null;
//        String address = QiniuUtil.UploadToQiniu(delicacyName, pic_url, bytes,"delicacy");
        String address = QiniuUtil.UploadToQiniu(pic_url, "destination", delicacyName);
        return address;
    }
}
