package spider;

import com.data.spider.service.mfw.MfwService;
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
public class test_scenic_mfw {

    private static ApplicationContext ac;

    //蚂蜂窝游玩时间
    public static void main(String[] args) {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.like("cityCode", "5402%");
        c.isNull("adviceTime");
        String mdd = "日喀则";
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(3000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            int mfw = MfwService.getSearchResult(scenicInfo.getName(),mdd);
            if (mfw==0)
                continue;
//            System.err.println(scenicInfo.getName()+"\tmfwId= " + mfw);
            String adviceTime = MfwService.getAdviceTime(mfw);
            if (adviceTime == null)
                continue;
            System.out.println(scenicInfo.getId() + "\t:"+scenicInfo.getName() + "\t:" + mfw + "\t" + adviceTime);
            scenicInfo.setAdviceTime(adviceTime);
            tbScenicInfoService.update(scenicInfo);
        }
    }


}
