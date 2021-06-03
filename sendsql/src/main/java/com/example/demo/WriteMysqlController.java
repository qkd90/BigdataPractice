package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.WriteMysql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rq
 */
@Slf4j
@RestController
@RequestMapping("sql")
public class WriteMysqlController{
    @RequestMapping("send")
    public String sendSql(@RequestBody String requestInfo) {
        log.info("开始运行");
        log.info("alert notify  params: {}", requestInfo);
        JSONObject json = JSON.parseObject(requestInfo);
        JSONObject commonAnnotations = json.getJSONObject("commonAnnotations");
        String content = commonAnnotations.getString("description");
        log.info("content: {}", content);
        WriteMysql writeMysql = new WriteMysql();
        String flag = writeMysql.save(content);
        log.info(" flag: {}",flag);
        return flag;
    }


}







