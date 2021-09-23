package spider;

import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.ctrip.DistrictList;
import com.data.spider.service.pojo.ctrip.RankDestInfo;
import com.data.spider.service.pojo.tb.TbArea;
import com.data.spider.service.tb.TbAreaService;
import com.data.spider.service.tripAdvisor.TripAdvisorService;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Sane on 15/9/8.
 */
public class test_city_oversea {

    private static ApplicationContext ac;

    public static void main(String[] args) {
        ctripCity();
//        tripAdvisorCity();
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(i +"\t" +(i/100+1));
//        }
//        File file = new File("tripAdvisorCity1.txt");
//        System.out.println(file.getAbsolutePath());
    }

    /**
     * 携程城市
     */
    public static void ctripCity() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CtripService ctripService = new CtripService();
//        int[] ctripIds = {110000, 120001, 120002, 120003, 120004, 120005, 120006, 120481};
        Map<Integer, String> map = new HashMap<Integer, String>();
//        map.put(110000, "国内");
        map.put(120001, "亚洲");
        map.put(120002, "欧洲");
        map.put(120003, "大洋洲");
        map.put(120004, "南美洲");
        map.put(120005, "北美洲");
        map.put(120006, "非洲");
        map.put(120481, "南极洲");
        System.out.println("洲\t国家\t地区\t城市\t英文名\tid");
        for (int ctripId : map.keySet()) {
            DistrictList districtList = ctripService.getDistrictList(ctripId);
//            System.out.println(districtList.getTotalCount());
            for (DistrictList.ResultEntity resultEntity : districtList.getResult()) {
                RankDestInfo rankDestInfo = ctripService.getRankDestInfo(resultEntity.getId());
                for (RankDestInfo.RankDestInfoEntity rankDestInfoEntity : rankDestInfo.getRankDestInfo()) {
                    System.out.println(map.get(ctripId) + "\t" + resultEntity.getName() + "\t" + rankDestInfoEntity.getParentName() + "\t" + rankDestInfoEntity.getName() + "\t" + rankDestInfoEntity.getEName() + "\t" + rankDestInfoEntity.getDistrictId());
                }
            }
        }
    }

    /**
     * tripAdvisor城市
     */
    public static void tripAdvisorCity() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TripAdvisorService tripAdvisorService = new TripAdvisorService();
        TbAreaService tbAreaService = SpringContextHolder.getBean("tbAreaService");
        String result = "";
        Map<String, String> worldPlaces = tripAdvisorService.getWorldPlaces();
        long idTmp = 1010000L, idTmp2 = 0, idTmp3 = 0;
        for (String worldPlace : worldPlaces.keySet()) {
            String url = "https://cn.tripadvisor.com" + worldPlace;
            result += "1\t" + worldPlaces.get(worldPlace) + "\t" + url + "\n";
            System.out.println("1\t" + worldPlaces.get(worldPlace) + "\t" + url);

            TbArea country = new TbArea();
            country.setId(idTmp);
            country.setName(worldPlaces.get(worldPlace));
            country.setLevel(1);
            country.setTripAdvisorURL(worldPlace);
            tbAreaService.save(country);
            idTmp2 = idTmp + 100;
            Map<String, String> links2 = tripAdvisorService.getPlacesFromPage(url);
            for (String link2 : links2.keySet()) {
                if (link2.contains("/Tourism")) {
                    continue;
                }
                String url2 = "https://cn.tripadvisor.com" + link2;
                System.out.println("2\t" + links2.get(link2) + "\t" + url2 + "\t" + worldPlaces.get(worldPlace) + "\t" + country.getId());
                result += "2\t" + links2.get(link2) + "\t" + url2 + "\t" + worldPlaces.get(worldPlace) + "\t" + country.getId() + "\n";

                TbArea area = new TbArea();
                area.setId(idTmp2);
                area.setFather(country);
                area.setLevel(2);
                area.setName(links2.get(link2));
                area.setTripAdvisorURL(link2);
                tbAreaService.save(area);
                idTmp3 = idTmp2 + 1;
                Map<String, String> links3 = tripAdvisorService.getRegionsFromPage(url2);
                for (String link3 : links3.keySet()) {

                    String url3 = "https://cn.tripadvisor.com" + link3;
                    result += "3\t" + links3.get(link3) + "\t" + url3 + "\t" + links2.get(link2) + "\t" + worldPlaces.get(worldPlace) + "\t" + area.getId() + "\n";
                    System.out.println("3\t" + links3.get(link3) + "\t" + url3 + "\t" + links2.get(link2) + "\t" + worldPlaces.get(worldPlace) + "\t" + area.getId());
                    TbArea region = new TbArea();
                    region.setId(idTmp3++);
                    region.setFather(area);
                    region.setLevel(3);
                    region.setName(links3.get(link3));
                    region.setTripAdvisorURL(link3);
                    tbAreaService.save(region);
                }
                idTmp2 += 100 * ((idTmp3 - idTmp2) / 100 + 1);
            }
            idTmp += 10000 * ((idTmp2 - idTmp) / 10000 + 1);
        }


        try {
            File file = new File("tripAdvisorCity.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(result);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
