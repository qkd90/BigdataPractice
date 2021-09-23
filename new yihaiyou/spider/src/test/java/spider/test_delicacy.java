package spider;

import com.data.spider.service.baidu.*;
import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
import com.data.spider.service.pojo.baidu.Dining.BaiduDining;
import com.data.spider.service.pojo.baidu.Poi.BaiduPoiList;
import com.data.spider.service.pojo.tb.TbDelicacy;
import com.data.spider.service.tb.DelicacyService;
import com.data.spider.service.tb.RestaurantService;
import com.data.spider.service.tb.TbCtripCityService;
import com.data.spider.service.pojo.tb.TbCtripCity;
import com.data.spider.service.pojo.tb.TbRestaurant;
import com.data.spider.service.pojo.baidu.Direction.BaiduPlace;
import com.data.spider.service.pojo.baidu.Direction.BaiduPlaces;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.hibernate.criterion.MatchMode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sane on 15/9/8.
 */
public class test_delicacy {
    private static ApplicationContext ac;

    public static void main(String[] args) {
//        saveImage();
//        crawlDelicacyAndRestaurant();
//        updateCityCode();
    }

    /**
     * 美食图片传到七牛
     */
    public static void saveImage() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DelicacyService delicacyService = SpringContextHolder.getBean("delicacyService");
        Criteria<TbDelicacy> c = new Criteria<TbDelicacy>(TbDelicacy.class);
        c.like("foodPicture","baidu", MatchMode.ANYWHERE);

        List<TbDelicacy> delicacies = delicacyService.gets(5000, c);

        for (TbDelicacy d : delicacies) {
            String url = d.getFoodPicture();
            System.out.println(url);
            String address = QiniuUtil.UploadToQiniu(url,"restaurant/");
            d.setFoodPicture(address);
            delicacyService.update(d);
        }
        System.err.println(delicacies.size());
    }
    public static void crawlDelicacyAndRestaurant() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DelicacyService delicacyService = SpringContextHolder.getBean("delicacyService");
        test_delicacy test = new test_delicacy();
        Map<Long,String> map = new HashMap<Long, String>();
        map.put(131100L,"衡水");
        map.put(141100L,"吕梁");
        map.put(152500L,"锡林郭勒盟");
        map.put(210500L,"本溪");
        map.put(211300L,"朝阳");
        map.put(220200L,"吉林");
        map.put(220300L,"四平");
        map.put(232700L,"大兴安岭");
        map.put(321300L,"宿迁");
        map.put(360600L,"鹰潭");
        map.put(371700L,"菏泽");
        map.put(410600L,"鹤壁");
        map.put(410700L,"新乡");
        map.put(420200L,"黄石");
        map.put(420600L,"襄阳");
        map.put(420700L,"鄂州");
        map.put(420800L,"荆门");
        map.put(421100L,"黄冈");
        map.put(442000L,"中山");
        map.put(450700L,"钦州");
        map.put(450800L,"贵港");
        map.put(451000L,"百色");
        map.put(451300L,"来宾");
        map.put(451400L,"崇左");
        map.put(460300L,"三沙");
        map.put(511300L,"南充");
        map.put(520500L,"毕节");
        map.put(520600L,"铜仁");
        map.put(530800L,"普洱");
        map.put(540200L,"日喀则");
        map.put(542100L,"昌都");
        map.put(620300L,"金昌");
        map.put(620400L,"白银");
        map.put(620600L,"武威");
        map.put(621100L,"定西");
        map.put(622900L,"临夏");
        map.put(630200L,"海东");
        map.put(632300L,"黄南");
        map.put(632500L,"海南");
        map.put(632700L,"玉树");
        map.put(632800L,"海西");
        map.put(652300L,"昌吉");
        map.put(652700L,"博尔塔拉");
        map.put(653000L,"克孜勒");
        map.put(654200L,"塔城");
        map.put(710600L,"南投");
        map.put(710700L,"基隆");
        map.put(710800L,"新竹");
        map.put(710900L,"嘉义");
        map.put(711100L,"新北");
        map.put(711200L,"宜兰");
        map.put(711300L,"新竹");
        map.put(711400L,"桃园");
        map.put(711500L,"苗栗");
        map.put(711700L,"彰化");
        map.put(711900L,"嘉义");
        map.put(712100L,"云林");
        map.put(712400L,"屏东");
        map.put(712500L,"台东");
        map.put(712600L,"花莲");
        map.put(712700L,"澎湖");
        map.put(712800L,"连江");


        for (Long key : map.keySet()) {
            boolean success = test.getBaiduDinings(map.get(key),key,delicacyService);
            System.out.println(map.get(key)+"\t"+success);
        }

    }


    private String getSid(String cityName) {
        String sid = "";
        BaiduDestinationService destinationService = new BaiduDestinationService();
        BaiduPoiSuggestion poiSuggestion = destinationService.getPoiSug(cityName);
        if (poiSuggestion != null) {
            sid = poiSuggestion.getData().getSuglist().get(0).getSid();
        }
        return sid;
    }
    private boolean getBaiduDinings(String cityName,Long cityId,DelicacyService delicacyService) {
        String sid = getSid(cityName);//根据城市名在百度旅游中搜索，获得sid
        BaiduDining dining = getDinings(sid);
        if(dining==null)
            return false;
        BaiduPoiList poiList = getPoiList(sid);
        if(dining==null)
            return false;
        if (dining != null) {
            BaiduDining.Food[] foods = dining.data.food;
            if(foods==null)
                return false;
            for (BaiduDining.Food food : foods) {
                List<TbDelicacy> delicacyExist = FindExists(food,cityId,delicacyService);
                if(delicacyExist.size()==0){
                    SaveDining(cityId, poiList, food,delicacyService);
                }
            }

        } else {
            return false;
        }
        return true;
    }

    private List<TbDelicacy> FindExists(BaiduDining.Food food,long cityId,DelicacyService delicacyService) {
        Criteria<TbDelicacy> c = new Criteria<TbDelicacy>(TbDelicacy.class);
        c.eq("foodName", food.key);
        c.eq("cityId", cityId);
        return delicacyService.gets(1,c);
    }

    private void SaveDining(Long cityId, BaiduPoiList poiList, BaiduDining.Food food,DelicacyService delicacyService) {
        TbDelicacy delicacy = new TbDelicacy();
        delicacy.setCityId(cityId);
        delicacy.setFoodName(food.key);
        delicacy.setIntroduction(food.desc);
        try {
//            delicacy.setFoodPicture(food.pic_url);
            String address = downLoadPic(food.pic_url,food.key);
            delicacy.setFoodPicture(address);
        }catch (Exception e){
            System.out.println(e);
        }

        if(food.stores!=null && food.stores.length >0){
            String poid = food.stores[0].poid;
            if(poiList!=null && poiList.data!=null )
                for (BaiduPoiList.Store store:poiList.data.stores){
                    if (store.poid.equals(poid)){
                        delicacy.setPrice((double)store.price);
                        delicacy.setRecommendReson(store.reason);
                    }
                }
        }
        delicacyService.save(delicacy);
    }

    private String downLoadPic(String pic_url,String delicacyName) {
//        byte[] bytes = downloadBytes(pic_url);
//        if(bytes==null)
//            return null;
//        String address = QiniuUtil.UploadToQiniu(delicacyName, pic_url, bytes,"delicacy");
        String address = QiniuUtil.UploadToQiniu(pic_url, "delicacy", delicacyName);
        return address;
    }
    //
    private BaiduDining getDinings(String sid) {
        BaiduDiningService diningService = new BaiduDiningService();
        return diningService.getDinings(sid);
    }

    private BaiduPoiList getPoiList(String sid) {
        BaiduPoiService poiService = new BaiduPoiService();
        return poiService.getPoiList(sid);
    }

    private static String key = "PXhzqOZRCWLy6dzlwQuF3gpV";

    //更新餐厅的地址、行政区划等（有cityCode的）
    public static void updateCityCode() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        RestaurantService restaurantService = SpringContextHolder.getBean("restaurantService");
        TbCtripCityService tbCtripCityService = SpringContextHolder.getBean("tbCtripCityService");
        Criteria<TbRestaurant> c = new Criteria<TbRestaurant>(TbRestaurant.class);
        c.isNotNull("cityCode");
        c.isNull("resLongitude");
        List<TbRestaurant> restaurants = restaurantService.gets(6000, c);
        for (TbRestaurant restaurant : restaurants) {
            List<TbCtripCity> citys = tbCtripCityService.getCity(restaurant.getCityCode());
            if (citys == null || citys.size() == 0)
                continue;
            String cityName = citys.get(0).getName();

            BaiduPlaces places = new BaiduPlaceService().getBaiduPlaces(restaurant.getResName().trim(),
                    cityName, 1, key);
            if (places == null || places.results == null || places.results.length == 0) {
                continue;
            }

            BaiduPlace place = places.results[0];
            System.err.println(restaurant.getId() + "\t" + place.address + "\t" + place.telephone);


            if (place.telephone != null && restaurant.getResPhone() == null)
                restaurant.setResPhone(place.telephone);

            if (place.location != null) {
                if (place.location.lat != null && restaurant.getResLatitude() == null)
                    restaurant.setResLatitude(place.location.lat);
                if (place.location.lng != null && restaurant.getResLongitude() == null)
                    restaurant.setResLongitude(place.location.lng);
            }

            if (place.address != null && restaurant.getResAddress() == null)
                restaurant.setResAddress(place.address);
            restaurantService.update(restaurant);
        }
    }


}
