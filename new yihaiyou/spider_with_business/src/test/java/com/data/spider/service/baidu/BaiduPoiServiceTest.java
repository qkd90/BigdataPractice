package com.data.spider.service.baidu;

import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.ScenicOtherService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
import com.data.spider.service.pojo.baidu.Poi.BaiduPoiDetail;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sane on 16/4/21.
 */
@Ignore
public class BaiduPoiServiceTest {

    private static ApplicationContext ac;

    @Test
    public void getPoiDetail() throws Exception {

    }

    @Test
    public void updateAdviceTimeDesc() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        ScenicInfoService scenicInfoService = SpringContextHolder.getBean("scenicInfoService");
        ScenicOtherService scenicOtherService = SpringContextHolder.getBean("scenicOtherService");
        Criteria<ScenicOther> condition = new Criteria<ScenicOther>(ScenicOther.class);
        condition.eq("source", "tripAdvisor");
        condition.isNull("adviceTimeDesc");
        String sql = "";
        List<ScenicOther> scenicOthers = scenicOtherService.listByCriteria(condition, new Page(3, 10000));
        for (ScenicOther scenicOther : scenicOthers) {
            ScenicInfo scenicInfo = scenicInfoService.get(scenicOther.getId());
            BaiduPoiSuggestion poiSuggestion = new BaiduDestinationService().getPoiSug(scenicInfo.getName());
            if (poiSuggestion != null) {
                for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {
                    System.err.println(scenicInfo.getName() + " 匹配了 " + suglist.getSname() + "\tlayer=" + suglist.getScene_layer());
                    if (suglist.getScene_layer().equals("6")) {//6表示景点
                        String sid = suglist.getSid();
                        BaiduPoiService poiService = new BaiduPoiService();
                        BaiduPoiDetail detail = poiService.getPoiDetail(sid);
                        System.out.println(scenicInfo.getName() + " 匹配了 " + suglist.getSname() + "\t" + suglist.getSid() + "\t游玩时间:" + detail.getData().getAbs().getInfo().getRecommend_visit_time());
                        String updateScenic = "UPDATE scenic_extend set advice_time_desc = '" + detail.getData().getAbs().getInfo().getRecommend_visit_time() + "' WHERE id = " + scenicInfo.getId() + ";\n";
                        sql += updateScenic;
                        String updateRelation = "UPDATE scenic_relation set baidu_sid = '" + sid + "' WHERE id = " + scenicInfo.getId() + ";\n";
                        sql += updateRelation;
                        System.out.println(updateScenic + updateRelation);
//                        System.out.println(detail.getData().getAbs().getInfo().getRecommend_visit_time());
                        break;
                    }
                }
            }
        }
        File file = new File("/Users/Sane/Downloads/sql.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sql);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}