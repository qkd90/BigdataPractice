package spider;

import com.data.spider.service.pojo.baidu.Direction.BaiduTaxiDirection;
import com.data.spider.service.pojo.tb.TbCtripCity;
import com.data.spider.service.pojo.tb.TbDis;
import com.data.spider.service.tb.TbCtripCityService;
import com.data.spider.service.tb.TbDisService;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Sane on 15/9/8.
 */
public class test_dis {

    private static ApplicationContext ac;
    private static String key = "PXhzqOZRCWLy6dzlwQuF3gpV";
    private static final String MODE_TRANSIT = "transit";
    private static final String MODE_DRIVE = "driving";

    public static void main(String[] args) {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbDisService tbDisService = SpringContextHolder.getBean("tbDisService");
        TbCtripCityService tbCtripCityService = SpringContextHolder.getBean("tbCtripCityService");

        get(2071, tbDisService, tbCtripCityService);
        get(2168, tbDisService, tbCtripCityService);
//        get(2645, tbDisService, tbCtripCityService);
//        get(4018, tbDisService, tbCtripCityService);
    }

    private static void get(int id, TbDisService tbDisService, TbCtripCityService tbCtripCityService) {

        List<TbDis> diss = tbDisService.getDis(id);

        TbDis dis = diss.get(0);
//        if (dis.getTaxiCost() != null) {
//            return;
//        }
        List<TbCtripCity> citys = tbCtripCityService.getCity(dis.getCity_code());
        String cityName = citys.get(0).getName();
        BaiduTaxiDirection result = getBaiduTaxiMap(cityName, dis.getS_lat(), dis.getS_lng(), dis.getE_lat(),
                dis.getE_lng(), MODE_DRIVE, key);
        if (result == null || result.result == null || result.result.taxi == null) {
            System.out.println("false");
            return;
        }
        BaiduTaxiDirection.Route route = result.result.routes[0];
        BaiduTaxiDirection.Taxi taxi = result.result.taxi;
        System.err.println("价格" + taxi.detail[0].total_price);
//        System.out.println("价格"+ Double.parseDouble(taxi.detail[0].total_price) % 100);
        System.err.println("时间1:" + taxi.duration);
        System.err.println("时间2:" + route.duration);
        System.err.println("距离" + taxi.distance);
    }


    public static BaiduTaxiDirection getBaiduTaxiMap(String cityName, double slat, double slng, double elat, double elng, String mode, String key) {

        String scityname = cityName;
        String ecityname = cityName;
        String origin = slat + "," + slng;
        String destination = elat + "," + elng;
        String baiduUrl = "http://api.map.baidu.com/direction/v1?mode=" + mode + "&origin=" + origin + "&destination=" + destination
                + "&origin_region=" + scityname + "&destination_region=" + ecityname + "&output=json&ak=" + key;
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();
            HttpGet get = new HttpGet(baiduUrl);
            HttpResponse response = httpClient.execute(get);
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            BaiduTaxiDirection taxiDirection = new Gson().fromJson(baiduStr, BaiduTaxiDirection.class);
            return taxiDirection;
        } catch (Exception e) {

        }
        return null;
    }

}
