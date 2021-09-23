package com.data.spider.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.baidu.Direction.BaiduTaxiDirection;
import com.data.spider.service.tb.TbCtripCityService;
import com.data.spider.service.tb.TbDisService;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.tb.TbCtripCity;
import com.data.spider.service.pojo.tb.TbDis;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.data.spider.service.pojo.Datatask;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.HttpClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

/**
 * 百度交通信息（两点距离时间花费）
 */
public class BaiduProcess extends BaseSpiderProcess {

    private TbDisService tbDisService = SpringContextHolder.getBean("tbDisService");
//    private TbCtripCityService tbCtripCityService = SpringContextHolder.getBean("tbCtripCityService");
//	@Autowired
//	private TbdisDao tbdisDao;

    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
    private List<String> keys = new ArrayList<String>();
    private static ObjectMapper om = new ObjectMapper();
    private static final int TYPE_WALK = 3;
    private static final int TYPE_TAXI = 2;
    private static final String MODE_WALK = "walking";
    private static final String MODE_DRIVE = "driving";
    private static final String MODE_TRANSIT = "transit";

//    private ConcurrentHashMap<String, AtomicLong> mutexs = new ConcurrentHashMap<String, AtomicLong>();
//    private static KeyMutex keyMutex;

    public BaiduProcess(Datatask datatask) {
        super(datatask);
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String[] keySpi = propertiesManager.getString("BAIDU_API_KEYS").split(",");
        for (String key : keySpi) {
            keys.add(key);
        }
        KeyMutex.instance.setKeys(keys);

    }

    @Override
    public Datatask call() throws Exception {
        int index = KeyMutex.instance.getDayKey();
        String key = keys.get(index);
        System.err.println(index + "key:" + key);
        long id = datatask.getId();
        boolean b = getBaiduInfo(key, id, datatask.getName());
        if (b) {
            datatask.setStatus(DatataskStatus.SUCCESSED);
        } else {
            datatask.setStatus(DatataskStatus.FAILED);
        }
        if (datatask.getMadeby() == MakeBy.DB) {
            datataskService.updateTask(datatask);
        }
        return datatask;
    }

    private boolean getBaiduInfo(String key, long id, String cityName) {
        List<TbDis> diss = tbDisService.getDis(id);
        TbDis dis = diss.get(0);
        if (dis.getTaxi_cost() != null) {
            return true;
        }
        dis.setOpt_status(-1);
        tbDisService.updateDis(dis);
        BaiduTaxiDirection result = getBaiduTaxiMap(cityName, dis.getS_lat(), dis.getS_lng(), dis.getE_lat(),
                dis.getE_lng(), MODE_DRIVE, key);
        if (result == null || result.result == null || result.result.routes == null)
            return false;
        BaiduTaxiDirection.Route route = result.result.routes[0];
        BaiduTaxiDirection.Taxi taxi = result.result.taxi;
        if (taxi == null) {
            dis.setTaxi_time(route.duration);
            dis.setTaxi_dis(route.distance);
        } else {
            dis.setTaxi_time(taxi.duration);
            dis.setTaxi_dis(taxi.distance);
            dis.setTaxi_cost(Integer.parseInt(taxi.detail[0].total_price));
        }
        dis.setOpt_status(1);
        tbDisService.updateDis(dis);


        return true;


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
//    public Map<String, Object> getBaiduMap(String cityName, double slat, double slng, double elat, double elng, String mode, String key) {
//
//        String scityname = cityName;
//        String ecityname = cityName;
//        String origin = slat + "," + slng;
//        String destination = elat + "," + elng;
//        String baiduUrl = "http://api.map.baidu.com/direction/v1?mode=" + mode + "&origin=" + origin + "&destination=" + destination
//                + "&origin_region=" + scityname + "&destination_region=" + ecityname + "&output=json&ak=" + key;
//        Map<String, Object> result;
//        try {
//            HttpClient httpClient = HttpClientUtils.getHttpClient();
//            HttpGet get = new HttpGet(baiduUrl);
//            HttpResponse response = httpClient.execute(get);
//            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
//            Map<String, Object> drivingMap = om.readValue(baiduStr, Map.class);
//            result = (Map<String, Object>) drivingMap.get("result");
//
//        } catch (Exception e) {
//            result = null;
//        }
//        return result;
//    }

    @Override
    public String getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Semaphore getMutex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getXmlName() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {

    }

}
