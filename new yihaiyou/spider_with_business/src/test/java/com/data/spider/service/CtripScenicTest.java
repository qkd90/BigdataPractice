package com.data.spider.service;

import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.ctrip.ScenicDetail;
import com.framework.hibernate.util.Page;
import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sane on 16/4/11.
 */
@Ignore
public class CtripScenicTest {
    private static ApplicationContext ac;

    @Test
    public void getScenic() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");

        ScenicInfoService scenicInfoService = SpringContextHolder.getBean("scenicInfoService");
//        ScenicInfo scenicInfo = scenicInfoService.get(1277l);
//        System.out.println(scenicInfo.getName());

        ScenicInfo condition = new ScenicInfo();
        condition.setScore(0);
        Page page = new Page();
        page.setPageSize(100);
        page.setPageIndex(1);
        List<ScenicInfo> scenicInfoList = scenicInfoService.list(condition, page);
        System.out.println(scenicInfoList.size());
    }

    @Test
    public void updateScore() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");

        ScenicInfoService scenicInfoService = SpringContextHolder.getBean("scenicInfoService");

        ScenicInfo condition = new ScenicInfo();
        condition.setScore(0);
        Page page = new Page();
        page.setPageSize(100);
        page.setPageIndex(1);
        List<ScenicInfo> scenicInfoList = scenicInfoService.list(condition, page);
        ScenicInfo scenic = scenicInfoService.get(1270l);
        System.out.println(scenic.getId() + "\t" + scenic.getName());


        for (ScenicInfo scenicInfo : scenicInfoList) {
            int newScore = getRanting(String.valueOf(scenicInfo.getCtripScenicId()));
            System.out.println(scenicInfo.getId() + "###" + scenicInfo.getName() + "\t" + scenicInfo.getScore() + "~~~" + newScore);
            scenicInfo.setScore(newScore);
            scenicInfoService.update(scenicInfo);
        }
    }

    @Test
    public void getScores() throws Exception {
        Map<String, String> ids = new HashMap<String, String>();
        File file = new File("/Users/Sane/Downloads/a.txt");
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file.getAbsoluteFile());
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String a = line.split("\t")[0];
                    String b = line.split("\t")[1];
                    ids.put(a, b);
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = "";
        for (String id : ids.keySet()) {
//            result += "UPDATE scenic set score = " + getRanting(ids.get(id)) + " WHERE id = " + id + ";\n";
            result += "UPDATE scenic set score = 123 WHERE id = " + id + ";\n";
            System.out.println(result);
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(result);
        bw.close();


    }

    public int getRanting(String ctripId) {
//        System.out.println("started : " + id);
        ScenicDetail scenicDetail = CtripService.getScenicDetail(ctripId);

        return (int) scenicDetail.getSightDetailAggregate().getRating() * 20;
    }


}
