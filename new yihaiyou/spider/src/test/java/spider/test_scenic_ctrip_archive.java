package spider;

import com.data.spider.service.ctrip.CtripArchiveService;
import com.data.spider.service.data.DataCityService;
import com.data.spider.service.pojo.data.DataCity;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.tb.TbScenicInfoService;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Sane on 15/9/8.
 */
public class test_scenic_ctrip_archive {

    private static ApplicationContext ac;
    //携程景点游玩时间抓取抓取（web.archive.org快照）
    public static void main(String[] args) {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        DataCityService dataCityService = SpringContextHolder.getBean("dataCityService");
        Criteria<DataCity> c1 = new Criteria<DataCity>(DataCity.class);
        c1.gt("ctripScenicUsed", 0);
        c1.isNotNull("ctripCityId");
        List<DataCity> dataCitys = dataCityService.gets(1000, c1);
        for (DataCity dataCity : dataCitys) {
            Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
            String cityPy = dataCity.getCtripCityPy();
            int cityId = dataCity.getCtripCityId();
            c.like("cityCode", String.valueOf(dataCity.getId() / 100) + "%");
//            c.eq("status", 99);
            c.isNull("adviceTime");
            c.isNotNull("ctripId");
            List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(3000, c);
            for (TbScenicInfo scenicInfo : scenicInfos) {
                int ctripId = scenicInfo.getCtripId();
                System.err.println("ctripId= " + ctripId);
//                String adviceTime = CtripWebService.getAdviceTime(cityPy, cityId, ctripId);
                String adviceTime = CtripArchiveService.getAdviceTime(dataCity.getId(),cityPy, cityId, ctripId);
                System.err.println(adviceTime);
                if (adviceTime == null)
                    continue;
                System.err.println(scenicInfo.getId() + "\t:" + ctripId + "\t" + adviceTime);
                System.out.println(adviceTime);
                scenicInfo.setAdviceTime(adviceTime);
                tbScenicInfoService.update(scenicInfo);
            }
        }
    }

}
