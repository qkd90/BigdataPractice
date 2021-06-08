package com.example.chengying.entity;

import lombok.Data;


/**
 * @author rq
 */
@Data
public class ResultWeb<T> {
    /**
    状态码
    */
    private int code;
    /**
    成功标志
    */
    private boolean success;
    /**
    接口响应时间
    */
    private  String  time;
    /**
    输出参数
    */
    private  T  data;
}
