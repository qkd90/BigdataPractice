package hmly.service.hotel.test;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.service.result.HotelDetail;
import com.data.data.hmly.service.elong.util.Tool;
import com.data.data.hmly.service.hotel.HotelElongGuaranteeService;
import com.data.data.hmly.service.hotel.HotelElongService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.zuipin.util.DateUtils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vacuity on 15/12/31.
 */

@Ignore
public class HotelElongServiceTest extends TestCase {
    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
    private final HotelElongService hotelElongService = (HotelElongService) applicationContext.getBean("hotelElongService");
    private final HotelElongGuaranteeService hotelElongGuaranteeService = (HotelElongGuaranteeService) applicationContext.getBean("hotelElongGuaranteeService");
    private final ElongHotelService elongHotelService = (ElongHotelService) applicationContext.getBean("elongHotelService");
    private final HotelService hotelService = (HotelService) applicationContext.getBean("hotelService");

    @Override
    protected void tearDown() throws Exception {
        ((ClassPathXmlApplicationContext) applicationContext).close();
    }

    public void testGetHotelIdList() {
//        hotelElongService.doElongToLxbHotel();

        // 获取酒店id及状态列表
//        Map<String, String> hotelIds = elongHotelService.getStaticsHotelList(new Date().getTime(), false);
//        String idInfo = hotelIds.get("90969120");
//        Assert.assertTrue(getHotelStatus(idInfo));
        // 酒店可用数
//        Map<String, String> hotelIds = elongHotelService.getStaticsHotelList(new Date().getTime(), false);
//        Set<String> set = hotelIds.keySet();
//        Iterator<String> its = set.iterator();
//        int count = 0;
//        while (its.hasNext()) {
//            String key = its.next();
//            String idInfo = hotelIds.get(key);
//            if (getHotelStatus(idInfo)) {
//                count++;
//            }
//        }
//        System.out.println(">>可用酒店数：" + count);

        // 索引酒店
//        hotelElongService.indexHotel();

        // 索引单条酒店（待测试）
        System.setProperty("http.maxConnections", "15");    // see org.apache.http.impl.client.SystemDefaultHttpClient.createClientConnectionManager()
        Hotel hotel = new Hotel();
        hotel.setId(1902270L);
        hotel.setStatus(ProductStatus.UP);
        hotelService.indexHotel(hotel);
    }

    private boolean getHotelStatus(String line) {
        Pattern p = Pattern.compile("Status=\"(\\d+)\"");
        Matcher m = p.matcher(line);
        if (m.find()) {
            String status = m.group(1);
            return "0".equals(status);  // 可用状态: 0--可用，1--不可用
        }
        return false;
    }

    public void testSingleHotelGet() {
        // 酒店详情
//        HotelType elongHotel = elongHotelService.getStaticsHotelDetail("90245448");
//        Assert.assertNotNull(elongHotel);

        // 酒店价格详情
        Date date = new Date();
        String dateStr = DateUtils.format(date, "yyyyMMdd");
        Date arriveDate = Tool.addDate(DateUtils.getDate(dateStr, "yyyyMMdd"), 1);
        Date departureDate = Tool.addDate(DateUtils.getDate(dateStr + " 23:59:59", "yyyyMMdd HH:mm:ss"), 3);  // 加载30天数据
        HotelDetail hotelDetail = elongHotelService.getHotelDetail(Long.parseLong("51401056"), arriveDate, departureDate);
        Assert.assertNotNull(hotelDetail);
    }

    public void testUpdate() {
        Map<String, String> ids = new HashMap<String, String>();
//        System.setProperty("http.proxyHost", "localhost");
//        System.setProperty("http.proxyPort", "8888");
//        System.setProperty("https.proxyHost", "localhost");
//        System.setProperty("https.proxyPort", "8888");
//        ids.put("90594631", "Status=\"0\"");
//        ids.put("90894867", "Status=\"0\"");
        ids.put("90659452", "Status=\"0\"");
        ids.put("90668458", "Status=\"0\"");
        ids.put("90658860", "Status=\"0\"");
        ids.put("90658848", "Status=\"0\"");
        ids.put("90975633", "Status=\"0\"");
        ids.put("90646380", "Status=\"0\"");
        ids.put("90666702", "Status=\"0\"");
        hotelElongService.doElongToLxbHotel(ids);
    }

    public void testUpdateByCity() {
        //艺龙的大理id,详情https://open.elong.com/xml/v2.0/hotel/geo_cn.xml
        String cityId = "2505";
        String[] elongIds = hotelElongService.getElongHotelIdsByElongCity(cityId);
        Map<String, String> ids = new HashMap<String, String>();
        for (String elongId : elongIds) {
            ids.put(elongId, "Status=\"0\"");
        }
        hotelElongService.doElongToLxbHotel(ids);
    }

    public void testUpdatePrice() {
        //TODO:这里传的是product的id
        List<Long> ids = new ArrayList<>();
        ids.add(1510691l);
        File file = new File("FILEPATH");
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file.getAbsoluteFile());
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    ids.add(Long.parseLong(line));
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hotelElongGuaranteeService.doUpdatePrices(ids);
    }

    //    public void testUpdateByTime() {
////        2016/1/27 10:50:30
////        Date date = new Date(1453863030000L);
//        //        2016/1/27 23:30:0
////        Date date = new Date(1453908600000L);
//        //2016/1/27 23:50:30
////        Date date = new Date(1453909830000L);
////                2016/1/28 00:00:00
//        Date date = new Date(1456333200000L);
//        hotelElongService.doElongToLxbHotel(date);
//    }
//

    public void testUpdateAll() {
        hotelElongService.doElongToLxbHotel();
    }

    public void testUpdateEmptyPrices() {
        hotelElongService.updateEmptyPrices();
    }

    /**
     * 测试更新单条酒店价格更新
     */
    public void testUpdateEmptyPrice() {
//        Map<Long, Long> hotels = hotelElongService.getHotelsEmptyPrices();
//        Long hotelId =  1902270L;
//        String elongId = "51401056";
//        Hotel hotel = hotelService.get(hotelId);
//        hotelElongService.doGetAndSavePrices(String.valueOf(elongId), hotel);
//        hotelService.updateTransactional(hotel);
    }


}
