package cn.trasen.chengying.entity;

import lombok.Data;

/**
 * @author rq
 */
@Data
public class ResultError {

    private String type;

    private String subtype;

    private StackTraceElement[] trace;

    private String detail;
}