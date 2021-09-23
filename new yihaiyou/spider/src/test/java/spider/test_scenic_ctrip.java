package spider;

import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.data.DataCityService;
import com.data.spider.service.pojo.CtripScenics;
import com.data.spider.service.pojo.ScenicExtend;
import com.data.spider.service.pojo.ctrip.ScenicDetail;
import com.data.spider.service.pojo.ctrip.ScenicList;
import com.data.spider.service.pojo.data.DataCity;
import com.data.spider.service.pojo.tb.TbArea;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.pojo.tb.TbScenicOther;
import com.data.spider.service.tb.CtripScenicsService;
import com.data.spider.service.tb.ScenicExtendService;
import com.data.spider.service.tb.TbAreaService;
import com.data.spider.service.tb.TbScenicInfoService;
import com.data.spider.service.tb.TbScenicOtherService;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sane on 15/9/8.
 */
public class test_scenic_ctrip {

    private static ApplicationContext ac;

    public static void main(String[] args) {
//        crawlArchives();
        crawlScenics();
//        crawlScenicsCover();
//        updateScenicComment();
//        crawlCtripScenics();
    }

    //携程景点快照链接
    public static void crawlArchives() {
        String content = "";
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DataCityService cityService = SpringContextHolder.getBean("dataCityService");
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
        c.isNotNull("ctripCityPy");
//        c.eq("cityName", "九江");
        List<DataCity> citys = cityService.gets(500, c);
        CtripService ctripService = new CtripService();
        for (DataCity city : citys) {
            System.out.println(city.getCityName());
            int ctripCity = city.getCtripCityId();
            int cityCode = city.getId();
            String ctripPy = city.getCtripCityPy();
            List<ScenicList.SightListEntity> list = ctripService.getScenicAll(ctripCity);
            for (ScenicList.SightListEntity result : list) {
                int sightId = result.getSightId();
                String shell = "wget https://web.archive.org/web/http://you.ctrip.com/sight/" + ctripPy + ctripCity + "/" + sightId + ".html" + " -O " + cityCode + "_" + ctripCity + "_" + ctripPy + "_" + sightId + ".html;\n";
                System.out.print(shell);
                content += shell;
            }
        }
        File file = new File("webArchive.sh");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //携程景点抓取
    public static void crawlScenics() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        TbScenicOtherService tbScenicOtherService = SpringContextHolder.getBean("tbScenicOtherService");
        TbAreaService tbAreaService = SpringContextHolder.getBean("tbAreaService");
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
//        c.eq("id", 532800L);
        c.eq("id", 350600L);
        c.ne("ctripId", 0);
        List<TbArea> citys = tbAreaService.gets(10000, c);
        CtripService ctripService = new CtripService();
        for (TbArea city : citys) {
            System.out.println(city.getName());
            int ctripCity = city.getCtripId();
            Criteria<TbScenicInfo> c1 = new Criteria<TbScenicInfo>(TbScenicInfo.class);
            c1.like("cityCode", city.getId() / 100 + "%");
            List<Integer> ctripIds = new ArrayList<Integer>();
            List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(3000, c1);
            for (TbScenicInfo scenicInfo : scenicInfos) {
                ctripIds.add(scenicInfo.getCtripId());
            }
            List<ScenicList.SightListEntity> list = ctripService.getScenicAll(ctripCity);
            for (ScenicList.SightListEntity result : list) {
                int sightId = result.getSightId();
                if (ctripIds.contains(sightId)) {
                    continue;
                }
                ScenicDetail detail = CtripService.getScenicDetail(String.valueOf(sightId));
                if (detail == null) {
                    continue;
                }
                TbScenicInfo scenicInfo = CtripService.getTbScenicInfoBasic(city.getId(), result);
                scenicInfo = CtripService.getScenicDetail(detail, scenicInfo);
                System.err.println(detail.getSightDetailAggregate().getSightId() + "\t" + scenicInfo.getName() + "\t"
                        + scenicInfo.getAdviceTime() + "\t" + scenicInfo.getAddress() + "\t" + scenicInfo.getLongitude() + ","
                        + scenicInfo.getLatitude() + "\t" + scenicInfo.getTicket() + scenicInfo.getTelephone());
                tbScenicInfoService.save(scenicInfo);
                TbScenicOther scenicOther = CtripService.getTbScenicOther(detail);
                scenicOther.setScenicInfoId(scenicInfo.getId());
                tbScenicOtherService.save(scenicOther);
            }
//            city.setCtripScenicUsed(3);
//            cityService.update(city);
        }
    }

    //更新携程景点简介和一句话点评
    public static void updateScenicComment() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

//        ScenicService scenicService = SpringContextHolder.getBean("scenicService");
//        Criteria<Scenic> c1 = new Criteria<TbScenicInfo>(TbScenicInfo.class);
//        c1.between("cityCode", "110000", "119999");
//        c1.eq("status", 1);
//        c1.eq("id", 1050506L);
        ScenicExtendService scenicExtendService = SpringContextHolder.getBean("scenicExtendService");
        Criteria<ScenicExtend> c2 = new Criteria<ScenicExtend>(ScenicExtend.class);
        c2.isNull("recommendReason");
        c2.isNotNull("ctripId");
//        c2.eq("id", 1047618);
        List<ScenicExtend> scenicInfos = scenicExtendService.gets(30000, c2);
        for (ScenicExtend scenicExtend : scenicInfos) {
            ScenicDetail detail = CtripService.getScenicDetail(String.valueOf(scenicExtend.getCtripId()));
            System.out.println("--------------------------------");
            if (detail == null || detail.getSightDetailAggregate() == null) {
                continue;
            }
            System.out.println(detail.getSightDetailAggregate().getSightName());
            if (detail.getSightDetailAggregate().getIntroduction() != null) {
                System.out.println(scenicExtend.getDescription() + "\n" + detail.getSightDetailAggregate().getIntroduction().replaceAll("<a .*?</a>", "").replaceAll("<.*?>", ""));
                scenicExtend.setDescription(detail.getSightDetailAggregate().getIntroduction().replaceAll("<a .*?</a>", "").replaceAll("<.*?>", ""));
            }
            String comment = detail.getSightDetailAggregate().getFeature();
            ScenicDetail.SightDetailAggregateEntity.CommentInfoEntity commentInfo = detail.getSightDetailAggregate().getCommentInfo();
            if (comment == null && commentInfo != null) {
                comment = commentInfo.getContent();
            }
            if (comment != null) {

                if (comment.length() > 1000) {
                    comment = comment.substring(0, 1000);
                }
                scenicExtend.setRecommendReason(comment);
            }
            scenicExtendService.update(scenicExtend);
        }
    }


    //携程景点封面抓取
    public static void crawlScenicsCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c1 = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c1.isNull("coverLarge");
        c1.gt("id", 1035194L);
//        c1.like("cityCode", "3502%");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(3000, c1);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            ScenicDetail detail = CtripService.getScenicDetail(String.valueOf(scenicInfo.getCtripId()));
            if (detail == null || detail.getSightDetailAggregate() == null || detail.getSightDetailAggregate().getImageCoverUrl() == null) {
                continue;
            }
            scenicInfo = CtripService.getScenicDetail(detail, scenicInfo);
//            System.err.println(detail.getSightDetailAggregate().getSightId() + "\t" + scenicInfo.getName() + "\t"
//                    + scenicInfo.getAdviceTime() + "\t" + scenicInfo.getAddress() + "\t" + scenicInfo.getLongitude() + ","
//                    + scenicInfo.getLatitude() + "\t" + scenicInfo.getTicket() + scenicInfo.getTelephone());
//            System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getCoverLarge());
            tbScenicInfoService.update(scenicInfo);
//            TbScenicOther scenicOther = CtripService.getTbScenicOther(detail);
//            scenicOther.setScenicInfoId(scenicInfo.getId());
//            tbScenicOtherService.save(scenicOther);
        }


    }


    //携程景点抓取
    public static void crawlCtripScenics() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CtripScenicsService ctripScenicsService = SpringContextHolder.getBean("ctripScenicsService");

        DataCityService cityService = SpringContextHolder.getBean("dataCityService");
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
//        c.eq("ctripScenicUsed", 1);
        c.eq("id", 532800l);
//        c.isNotNull("ctripCityId");
        List<Integer> ids = new ArrayList<Integer>();
        List<DataCity> citys = cityService.gets(1000, c);
        CtripService ctripService = new CtripService();
        for (DataCity city : citys) {
            System.out.println(city.getCityName());
            int ctripCity = city.getCtripCityId();
            int pageIndex = 1;
            List<ScenicList.SightListEntity> results = new ArrayList<ScenicList.SightListEntity>();
            do {
                try {
                    ScenicList scenicList = ctripService.getScenicList(ctripCity, pageIndex);
                    if (!scenicList.getResponseStatus().getAck().endsWith("Success")) {
                        break;
                    }
                    results.addAll(scenicList.getSightList());
                    for (ScenicList.SightListEntity sight : scenicList.getSightList()) {
                        if (ids.contains(sight.getSightId()))
                            continue;
                        ids.add(sight.getSightId());
                        CtripScenics ctripScenics = new CtripScenics();
                        ctripScenics.setCityId(city.getCtripCityId());
                        ctripScenics.setCityName(scenicList.getDistrictName());
                        ctripScenics.setCityPinyin(scenicList.getPinyin());
                        ctripScenics.setDistrictId(sight.getDistrictId());
                        ctripScenics.setDistrictName(sight.getDistrictName());
                        ctripScenics.setLat(sight.getLat());
                        ctripScenics.setLng(sight.getLng());
                        ctripScenics.setLevel(sight.getLevel());
                        ctripScenics.setId(sight.getSightId());
                        ctripScenics.setRank(sight.getRank());
                        ctripScenics.setSightName(sight.getSightName());
                        ctripScenics.setScore(sight.getCommentScore());
                        ctripScenics.setTagName(sight.getTagName());
                        System.err.println(sight.getSightId() + "\t" + ctripScenics.getSightName()
                                + "\t" + ctripScenics.getLng() + ","
                                + ctripScenics.getLat() + "\t" + ctripScenics.getCityName() + ctripScenics.getDistrictName());
                        try {
                            ctripScenicsService.save(ctripScenics);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (scenicList.getTotalCount() <= results.size())
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (pageIndex++ < 1000);
        }


    }


}
