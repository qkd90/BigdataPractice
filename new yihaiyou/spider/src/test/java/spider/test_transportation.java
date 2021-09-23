package spider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.data.spider.service.tb.TbTransportationService;
import com.data.spider.service.baidu.BaiduGeoCoderService;
import com.data.spider.service.baidu.BaiduPlaceService;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.baidu.Direction.BaiduPlace;
import com.data.spider.service.pojo.baidu.Direction.BaiduPlaces;
import com.data.spider.service.pojo.baidu.GeoCoder.RederReverse;
import com.data.spider.service.pojo.ctrip.Airport;
import com.data.spider.service.pojo.ctrip.SearchResult;
import com.data.spider.service.pojo.ctrip.Trainstation;
import com.data.spider.service.pojo.tb.TbTransportation;
import com.data.spider.service.pojo.tb.TmpTransportation;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;

/**
 * Created by Sane on 15/9/8.
 */
public class test_transportation {

    public static void main(String[] args) {
//        crawlTransportation();
//        updateAddressAndTelephone();
        updateCityCode();
    }
    private static ApplicationContext ac;
    private static String key = "PXhzqOZRCWLy6dzlwQuF3gpV";
    //更新交通枢纽的地址、行政区划等
    public static void updateAddressAndTelephone() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbTransportationService transportationService = SpringContextHolder.getBean("tbTransportationService");
        Criteria<TbTransportation> c = new Criteria<TbTransportation>(TbTransportation.class);
        c.eq("type", 2);////交通枢纽类型: 2为飞机场,1为火车站
        List<TbTransportation> transportations = transportationService.gets(6000, c);
        for (TbTransportation transportation : transportations) {
            BaiduPlaces places = new BaiduPlaceService().getBaiduPlaces(transportation.getName().trim(),
                    transportation.getRegionName()==null?"":transportation.getRegionName().trim(), 1, key);
            if (places == null || places.results == null || places.results.length == 0) {
                transportation.setDataStatus(-1);
                transportationService.update(transportation);
                continue;
            }
            BaiduPlace place = places.results[0];
            System.out.println(place.address);
            System.out.println(place.telephone);
            if (place.telephone != null && transportation.getTelephone() == null)
                transportation.setTelephone(place.telephone);

            if (place.location != null) {
                if (place.location.lat != null && transportation.getLat() == null)
                    transportation.setLat(place.location.lat);
                if (place.location.lng != null && transportation.getLng() == null)
                    transportation.setLng(place.location.lng);
            }

            if (place.address != null && transportation.getAddress() == null)
                transportation.setAddress(place.address);
            transportation.setDataStatus(1);
            transportationService.update(transportation);
        }
    }


    //行政区划（百度坐标反向编码）
    public static void updateCityCode() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbTransportationService transportationService = SpringContextHolder.getBean("tbTransportationService");
        Criteria<TbTransportation> c = new Criteria<TbTransportation>(TbTransportation.class);
        c.eq("type", 2);////交通枢纽类型: 2为飞机场,1为火车站
        c.isNull("cityCode");
        c.isNotNull("lng");
        c.isNotNull("lat");
        List<TbTransportation> transportations = transportationService.gets(6000, c);
        for (TbTransportation transportation : transportations) {
            if (transportation.getLng() == null)
                continue;
            BaiduGeoCoderService s = new BaiduGeoCoderService();
            RederReverse reverse = s.getRederReverse(transportation.getLat().toString(), transportation.getLng().toString(), key);
            if (reverse == null)
                continue;
            System.err.println(transportation.getId() + "\t" + transportation.getName() + "\t" + reverse.result.addressComponent.district);
//            if (tmpScenicInfo.getAddress() ==null || "".equals(tmpScenicInfo.getAddress() )){
//                tmpScenicInfo.setAddress(reverse.result.formatted_address);
//            }
            transportation.setRegionName(reverse.result.addressComponent.district);
//            transportation.setCityName(reverse.result.addressComponent.city);
            transportationService.update(transportation);
        }
    }

    //交通枢纽的数据补充（携程app中搜索）
    public static void crawlTransportation() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbTransportationService transportationService = SpringContextHolder.getBean("tbTransportationService");
        Criteria<TbTransportation> c = new Criteria<TbTransportation>(TbTransportation.class);
        c.eq("type", 2);////交通枢纽类型: 2为飞机场,1为火车站
        c.eq("dataSource", "juhe");////交通枢纽类型: 2为飞机场,1为火车站
        List<TbTransportation> transportations = transportationService.gets(6000, c);
        System.out.println("名称\t区域\t匹配类型\t匹配名称\t匹配区域\turl");
        CtripService ctripService = new CtripService();
        for (TbTransportation transportation : transportations) {
            String keyword = transportation.getName();
            ////按关键字获得搜索结果
            SearchResult searchResult = ctripService.search(keyword.trim());
            //交通枢纽类型变量
            String ctripType = "";
            if (transportation.getType() == 1) {
                ctripType = "trainstation"; //火车站

            } else if (transportation.getType() == 2) {
//                System.out.println(transportation.getDataSourceUrl());
                ctripType = "airport";
            } else if (transportation.getType() == 3) {
                ctripType = "busstation";
            }
            boolean matched = false;
            String poi = "";
            ////交通枢纽类型匹配
            for (SearchResult.Datum data : searchResult.data) {
                if (ctripType.equals(data.type)) {
                    matched = true;
                    Pattern p = Pattern.compile("detail-(\\d+)");
                    Matcher m = p.matcher(data.url);
                    if (m.find()) {
                        poi = m.group(1);
                    }
                    System.out.println(transportation.getName() + "\t" + transportation.getRegionName() + "\t"
                            + data.type + "\t" + data.word + "\t" + data.districtname + "\t" + data.url);
                    if (data.word.contains(keyword.replace("火车站", "").replace("机场", ""))) {
                        break;
                    }
                }
            }
            if (matched) {
                if (transportation.getType() == 1) {
                    Trainstation trainstation = ctripService.getTrainstation(poi);

                    if (transportation.getLat() == null) {
                        transportation.setLat(trainstation.TrainStationInfo.Lat);
                        transportation.setLng(trainstation.TrainStationInfo.Lon);
                    }
                    if (trainstation.TrainStationInfo.Picture != null&& trainstation.TrainStationInfo.Picture.length > 0) {
                        String pic = QiniuUtil.UploadToQiniu(trainstation.TrainStationInfo.Picture[0], "transportation");
                        transportation.setCoverImg(pic);
                    }
                    if (transportation.getAddress() == null) {
                        transportation.setAddress(trainstation.TrainStationInfo.Address);
                    }
//                    transportationService.update(transportation);
                } else if (transportation.getType() == 2) {
                    Airport airport = ctripService.getAirport(poi);

                    if (transportation.getLat() == null) {
                        transportation.setLat(airport.AirportDetailInfo.Lat);
                        transportation.setLng(airport.AirportDetailInfo.Lon);
                    }
                    if (transportation.getCoverImg() == null &&
                            airport.AirportDetailInfo.Picture != null && airport.AirportDetailInfo.Picture.length > 0) {
                        String pic = QiniuUtil.UploadToQiniu(airport.AirportDetailInfo.Picture[0], "transportation");
                        transportation.setCoverImg(pic);
                    }
                    if (transportation.getAddress() == null) {
                        transportation.setAddress(airport.AirportDetailInfo.Address);
                    }
                    if (transportation.getCode()==null){
                        transportation.setCode(airport.AirportDetailInfo.ThreeCode);
                    }
                    if (transportation.getRegionName()==null){
                        transportation.setRegionName(airport.AirportDetailInfo.DistrictName);
                    }
                    if (transportation.getCityName()==null){
                        transportation.setCityName(airport.AirportDetailInfo.DistrictName);
                    }

                    transportationService.update(transportation);
                } else if (transportation.getType() == 3) {
                }
            } else {
                System.err.println(keyword + "\t" + "未匹配");
            }

//            transportation.setRegionName(reverse.result.addressComponent.district);
//            transportation.setCityName(reverse.result.addressComponent.city);
//            transportationService.update(transportation);
        }
    }
    



}
