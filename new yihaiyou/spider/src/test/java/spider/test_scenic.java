package spider;

import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.ctrip.ScenicDetail;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.pojo.tb.TbScenicOther;
import com.data.spider.service.tb.TbScenicInfoService;
import com.data.spider.service.tb.TbScenicOtherService;
import com.data.spider.util.HttpUtil;
import com.data.spider.util.QiniuUtil;
import com.data.spider.util.qiniuImageInfo;
import com.framework.hibernate.util.Criteria;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/9/8.
 */
public class test_scenic {

    private static ApplicationContext ac;


    public static void main(String[] args) {
        updateAdviceHours();
//        updateChangyoujiCover();
//        updateCtripCover();
//        updateIntroduction();
//        updateInvalidCover();
//        updateScenicLabels();
    }

    /**
     * 根据游玩时间advice_time，更新游玩分钟数advice_hours
     */
    public static void updateAdviceHours() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.isNotNull("adviceTime");
        c.ne("adviceTime", "");
//        c.isNull("adviceHours");
        c.eq("adviceHours", 0);
        List<TbScenicInfo> scenicInfoList = tbScenicInfoService.gets(1000, c);
        for (TbScenicInfo scenicInfo : scenicInfoList) {
            String adviceTime = replaceChzNums(scenicInfo.getAdviceTime());
            int minutes = getMinutes(adviceTime);
//            if (minutes != scenicInfo.getAdviceHours()) {
            System.out.println("");
            System.out.printf(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + scenicInfo.getAdviceTime() + "\t" + scenicInfo.getAdviceHours() + "\t" + minutes);
            scenicInfo.setAdviceHours(minutes);
            tbScenicInfoService.update(scenicInfo);
//            }

        }
    }


    /**
     * theme拆分到label
     */
    public static void updateScenicLabels() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.isNotNull("theme");
        List<TbScenicInfo> scenicInfoList = tbScenicInfoService.gets(100000, c);
        String result = "";
        List<String> labels = new ArrayList<String>();
        for (TbScenicInfo scenicInfo : scenicInfoList) {
            String theme = scenicInfo.getTheme();
            System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName());
//            if (theme.contains(",")) {
            String[] arr = theme.split(",");
            for (String label : arr) {
                if (!labels.contains(label)) {
                    System.err.println(label);
                    labels.add(label);
                    result += label + "\n";
                }
            }
//            }
        }

        System.out.println(labels);
        File file = new File("labels.sh");
        try {
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

    /**
     * 携程封面
     */
    public static void updateCtripCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.like("coverLarge", "%ctrip.com%");
        c.gt("id", 1053781L);
        List<TbScenicInfo> scenicInfoList = tbScenicInfoService.gets(100000, c);
        for (TbScenicInfo scenicInfo : scenicInfoList) {
            String coverUrl = scenicInfo.getCoverLarge().replace("_C_200_200", "");
            System.out.println(coverUrl);
            String cover_large = QiniuUtil.UploadToQiniu(coverUrl, "gallery/" + scenicInfo.getId());
            scenicInfo.setCoverLarge(cover_large);
            tbScenicInfoService.update(scenicInfo);
        }
    }

    /**
     * 查找携程的默认封面图（模糊的640*320），更新成null
     */
    public static void updateInvalidCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.isNotNull("coverLarge");
        c.isNull("coverMedium");
        c.gt("id", 1057170L);
        List<TbScenicInfo> scenicInfoList = tbScenicInfoService.gets(100000, c);
        for (TbScenicInfo scenicInfo : scenicInfoList) {
            String img = "http://7u2inn.com2.z0.glb.qiniucdn.com/" + scenicInfo.getCoverLarge().replace(" ", "%20");
            try {
//                System.out.println(img + "?imageInfo");
                String json = HttpUtil.HttpGetString(img + "?imageInfo");
                if (!json.contains("error")) {
                    qiniuImageInfo imageInfo = new Gson().fromJson(json, qiniuImageInfo.class);
                    if (imageInfo.getWidth() == 640 && imageInfo.getHeight() == 320) {
                        System.out.println(scenicInfo.getId() + "\t~~~" + img);
                        scenicInfo.setCoverLarge(null);
                        scenicInfo.setCoverMedium("~~~");
                        tbScenicInfoService.update(scenicInfo);
                    }
                } else {
                    System.out.println(scenicInfo.getId() + "\t--- " + json);
//                    int ctripId = scenicInfo.getCtripId();
//                    ScenicDetail detail = CtripService.getScenicDetail(String.valueOf(ctripId));
//                    String coverUrl = detail.getSightDetailAggregate().getImageCoverUrl();
                    scenicInfo.setCoverMedium("---");
                    tbScenicInfoService.update(scenicInfo);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 更新七牛报错“Document not found”的封面图
     * 可能都是chanyouji.com的图
     */
    public static void updateChangyoujiCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.isNotNull("ctripId");
        c.eq("coverMedium", "--------");
//        c.eq("id", 1049468L);
        List<TbScenicInfo> scenicInfoList = tbScenicInfoService.gets(100000, c);
        for (TbScenicInfo scenicInfo : scenicInfoList) {
            System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName());
            int ctripId = scenicInfo.getCtripId();
            ScenicDetail detail = CtripService.getScenicDetail(String.valueOf(ctripId));
            String coverUrl = detail.getSightDetailAggregate().getImageCoverUrl().replace("_C_200_200", "");
            if (coverUrl.contains("?")) {
                coverUrl = coverUrl.split("\\?")[0];
                System.out.println(coverUrl);
                String covery_large = QiniuUtil.UploadToQiniu(coverUrl, "gallery/" + scenicInfo.getId());
                System.out.println("http://7u2inn.com2.z0.glb.qiniucdn.com/" + covery_large);
                scenicInfo.setCoverLarge(covery_large);
                scenicInfo.setCoverMedium("-------------");
                tbScenicInfoService.update(scenicInfo);
            } else {
                System.out.println(coverUrl);
                String covery_large = QiniuUtil.UploadToQiniu(coverUrl, "gallery/" + scenicInfo.getId());
                scenicInfo.setCoverLarge(covery_large);
                scenicInfo.setCoverMedium("----");
//                tbScenicInfoService.update(scenicInfo);
            }
        }
    }

    /**
     * 去除景点描述中的a标签
     */
    public static void updateIntroduction() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicOtherService tbScenicOtherService = SpringContextHolder.getBean("tbScenicOtherService");
        Criteria<TbScenicOther> c = new Criteria<TbScenicOther>(TbScenicOther.class);
        c.like("introduction", "%<a %");
        List<TbScenicOther> scenicOthers = tbScenicOtherService.gets(2000, c);
        for (TbScenicOther scenicOther : scenicOthers) {
            String introduction = scenicOther.getIntroduction();
            System.out.println(introduction);
            introduction = introduction.replaceAll("<[Aa] .*?>", "").replace("</a>", "").replace("</A>", "");
            System.out.println(introduction);
            scenicOther.setIntroduction(introduction);
            tbScenicOtherService.update(scenicOther);
            System.out.println("----------------------------");
            System.out.println("----------------------------");
        }
    }

    private static String replaceChzNums(String adviceTime) {
        adviceTime = adviceTime.replace("半", "0.5").replace("全天", "1天").replace("一", "1").replace("二", "2").replace("三", "3")
                .replace("四", "4").replace("五", "5").replace("六", "6").replace("七", "7").replace("八", "8")
                .replace("九", "9").replace("十", "10");
        return adviceTime;
    }

    private static int getMinutes(String adviceTime) {
        int minutes = 0;
        String[] rs = {
                "[-至到](\\d+\\.\\d+|\\d+)个?\\s?(分钟|小时|天)"
                , "(\\d+\\.\\d+|\\d+)个?\\s?(分钟|小时|天)"
                , "(\\d+\\.\\d+|\\d+)"
        };
        for (String r : rs) {
            Pattern p = Pattern.compile(r);
            Matcher m = p.matcher(adviceTime);
            double num;
            String unit = "";
            if (m.find()) {
                num = Double.parseDouble(m.group(1));
                if (m.groupCount() == 2)
                    unit = m.group(2);
                if (unit.equals("天")) {
                    minutes = (int) (num * 60 * 8);
                } else if (unit.equals("小时")) {
                    minutes = (int) (num * 60);
                } else {
                    minutes = (int) num;
                }
                break;
            }
        }
        return minutes;
    }




}
