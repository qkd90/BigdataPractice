package com.example.demo.dao;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.example.demo.CurrentTime.currentTime;


/**
 * @author rq
 */
public class WriteMysql {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void save(String content) {
        String time = currentTime();
        String sql = "insert into tbl_sms_outbox " +
                "(extcode, destaddr, messagecontent, msgsta,reqdeliveryreport, requesttime,mac,msgstatus,flag)"+
                "values('NHYYGJ','15768909468',content,1,1,time,'18684715138')";
        int ans = jdbcTemplate.update(sql);
    }

}
