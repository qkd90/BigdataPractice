package spider;

import com.data.spider.service.baidu.BaiduDestinationService;
import com.data.spider.service.baidu.BaiduPoiService;
import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
import com.data.spider.service.pojo.baidu.Poi.BaiduPoiDetail;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.pojo.tb.TbScenicOther;
import com.data.spider.service.tb.TbScenicInfoService;
import com.data.spider.service.tb.TbScenicOtherService;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Sane on 15/9/8.
 */
public class test_scenic_baidu {

    private static ApplicationContext ac;
    private static String key = "PXhzqOZRCWLy6dzlwQuF3gpV";

    public static void main(String[] args) {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        BaiduPoiService poiService = new BaiduPoiService();
        BaiduPoiDetail detail = poiService.getPoiDetail("d61c5707715ac5114632d080");

        System.out.println(detail.getData().getAbs().getInfo().getRecommend_visit_time());
    }

    //更新百度采集到的景点数据，根据百度旅游的地址和描述
    public static void main9(String[] args) {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        TbScenicOtherService tbScenicOtherService = SpringContextHolder.getBean("tbScenicOtherService");
        Criteria<TbScenicInfo> c1 = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        //id在10000~1000000之间，url存着百度的url的参数，如http://lvyou.baidu.com/gulangyu
        c1.isNotNull("url");
        c1.lt("id", 1000000L);
        c1.gt("id", 10000L);
        Timestamp timestamp = Timestamp.valueOf("2015-9-17 00:00:00");
        c1.lt("modifyTime", timestamp);
//        c.lt("modifyTime", "2009-12-11 00:00:00");
        System.out.println("ID\t景点名\t百度url\t百度景点名\t百度id\t百度父景点\t百度区域\t层级\t初步匹配");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(10000, c1);
        BaiduPoiService poiService = new BaiduPoiService();
        for (TbScenicInfo scenicInfo : scenicInfos) {
//            String baiduUrl = scenicInfo.getUrl();
//            System.out.print(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + baiduUrl + "\t");
            String name = scenicInfo.getName();
            ////
            Criteria<TbScenicOther> c2 = new Criteria<TbScenicOther>(TbScenicOther.class);
            c2.eq("scenicInfoId", scenicInfo.getId());
            List<TbScenicOther> tbScenicOthers = tbScenicOtherService.gets(1, c2);
            TbScenicOther tbScenicOther = null;
            if (tbScenicOthers.size() > 0) {
                tbScenicOther = tbScenicOthers.get(0);
            } else {
                continue;
            }
            ////
            BaiduPoiSuggestion poiSuggestion = new BaiduDestinationService().getPoiSug(name);
            if (poiSuggestion != null) {
                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
                    //System.out.println(suglist.sname + "\t" + suglist.sid);
                    if (suglist.getSurl().equals(scenicInfo.getBaiduUrl())) {
//                        System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + baiduUrl + "\t" +suglist.sname + "\t" + suglist.sid + "\t" + suglist.parent_sid + "\t" + suglist.parent_sname + "\t" + suglist.scene_path);

                        BaiduPoiDetail detail = poiService.getPoiDetail(suglist.getSid());
                        if (detail == null || detail.getData() == null || detail.getData().getAbs() == null || detail.getData().getAbs().getInfo() == null)
                            continue;
                        String address = null;
                        String introduction = null;
                        if (detail.getData().getAbs() != null) {
                            address = detail.getData().getAbs().getInfo().getAddress();
                            introduction = detail.getData().getAbs().getDesc();
                        }
                        String oldAddr = scenicInfo.getAddress();
                        if (oldAddr != null) {
                            if (oldAddr.equals(address)) { ////新旧地址一样,不更新
                                System.out.println("equals:" + address);
                                continue;
                            }
                        }
                        int type = 0;
                        if (address.length() > 3) {
                            if (oldAddr.contains(address.substring(0, 4)) || oldAddr.contains(address.substring(address.length() - 3, address.length()))) {
                                type = 1;
                            }
                            //更新地址
                            scenicInfo.setAddress(address);
                            tbScenicInfoService.update(scenicInfo);
                        }
                        //更新简介
                        tbScenicOther.setIntroduction(introduction);
                        tbScenicOtherService.update(tbScenicOther);
                        System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + oldAddr + "\t" + address + "\t" + type);
                        continue;
                    }
                }
            }
//         scenicInfo.setGuide(intro);
//         tbScenicInfoService.update(scenicInfo);
        }
    }

    //百度父子景点
    public static void main11(String[] args) {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c1 = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        //id在10000~1000000之间，url存着百度的url的参数，如http://lvyou.baidu.com/gulangyu
//        c1.isNotNull("url");
//        c1.lt("id", 1000000L);
//        c1.gt("id", 10000L);
        c1.like("cityCode", "360881%");
//        Timestamp timestamp = Timestamp.valueOf("2015-9-17 00:00:00");
//        c1.lt("modifyTime", timestamp);
//        c.lt("modifyTime", "2009-12-11 00:00:00");
        System.out.println("ID\t景点名\t百度url\t百度景点名\t百度id\t百度父景点\t百度区域\t层级\t初步匹配");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(10000, c1);
        BaiduPoiService poiService = new BaiduPoiService();
        for (TbScenicInfo scenicInfo : scenicInfos) {
            String baiduUrl = scenicInfo.getBaiduUrl();
            String name = scenicInfo.getName();

            BaiduPoiSuggestion poiSuggestion = new BaiduDestinationService().getPoiSug(name);
            if (poiSuggestion != null) {
                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
                    //System.out.println(suglist.sname + "\t" + suglist.sid);
                    if (suglist.getScene_path().contains("")) {
//                    System.out.println(suglist.);
                        System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + baiduUrl + "\t" + suglist.getSname() + "\t" + suglist.getSid() + "\t" + suglist.getParent_sid() + "\t" + suglist.getParent_sname() + "\t" + suglist.getScene_path());
                    }
                }
            }
        }
    }
}
