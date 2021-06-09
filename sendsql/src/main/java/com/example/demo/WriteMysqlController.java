package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.MysqlImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author rq
 */
@Slf4j
@RestController
@RequestMapping("sql")
public class WriteMysqlController{

    public static String mobile = "";
    @Autowired
    private MysqlImpl mysqlImpl;

    @PostMapping("send")
    public String sendSql(@RequestBody String requestInfo) {
        log.info("开始运行");
        log.info("alert notify  params: {}", requestInfo);
        JSONObject json = JSON.parseObject(requestInfo);
        JSONObject commonAnnotations = json.getJSONObject("commonAnnotations");
        String content = commonAnnotations.getString("description");
        log.info("content: {}", content);
        String flag = mysqlImpl.save(content);
        log.info(" flag: {}",flag);
        return flag;
    }

    @GetMapping("update")
    public String updateSms() {
        String mobiles = "";
        log.info("开始更新电话号码");
        log.info("alert notify  mobiles: {}",mobiles);
        mobiles = readFileContent("phone.txt");
        mobile = mobiles;
        return "修改成功";
    }

    private static String readFileContent(String filename) {
        log.info("目录地址："+System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir"), filename);
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
                sbf.append(",");
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}







