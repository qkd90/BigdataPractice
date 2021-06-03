package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

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
        log.debug("alert notify  params: {}", requestInfo);
        JSONObject json = JSON.parseObject(requestInfo);
        JSONObject commonAnnotations = json.getJSONObject("commonAnnotations");
        String content = commonAnnotations.getString("description");
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from db");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
    }


}







