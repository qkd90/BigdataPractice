package cn.trasen.chengying.entity;

import lombok.Data;


/**
 * @author rq
 */
@Data
public class ResultParameters<T> {
    /**
    状态码
    */
    private int code;
    /**
    成功标志
    */
    private String message;
    /**
    接口响应时间
    */
    private  String  time;
    /**
    输出参数
    */
    private  T  data;
}
