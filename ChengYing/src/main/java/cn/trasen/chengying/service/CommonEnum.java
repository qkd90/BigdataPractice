package cn.trasen.chengying.service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author rq
 */
@Slf4j
public enum CommonEnum implements ExceptionInfoInterface {
    //枚举所有的错误
    QueryService(1501,
            "false",
            "通用异常",
            "数据库查询异常",
            "数据库查询没有结果");

    private final int code;

    private final String message;

    private final String type;

    private final String subtype;

    private final String detail;

    CommonEnum(int code, String success, String type, String subtype,
               String detail) {
        this.code = code;
        this.message = success;
        this.type = type;
        this.subtype = subtype;
        this.detail = detail;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
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