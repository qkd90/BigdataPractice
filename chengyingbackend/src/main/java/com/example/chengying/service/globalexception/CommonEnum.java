package com.example.chengying.service.globalexception;

import com.example.chengying.service.ExceptionInfoInterface;
import lombok.extern.slf4j.Slf4j;


/**
 * @author rq
 */
@Slf4j
public enum CommonEnum implements ExceptionInfoInterface {
    //枚举所有的错误
    QueryService(1501,
            false,
            "通用异常",
            "查询服务出入参异常",
            "数据库没有该服务");

    private final int code;

    private final boolean success;

    private final String type;

    private final String subtype;

    private final String detail;

    CommonEnum(int code, boolean success, String type, String subtype,
               String detail) {
        this.code = code;
        this.success = success;
        this.type = type;
        this.subtype = subtype;
        this.detail = detail;
    }

    @Override
    public int getCode() {
        return code;
    }

    public boolean getSuccess() {
        return success;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getSubtype() {
        return subtype;
    }

    @Override
    public String getDetail() {
        return detail;
    }
}