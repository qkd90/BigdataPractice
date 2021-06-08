package com.example.chengying.service.globalexception;


import com.example.chengying.service.ExceptionInfoInterface;
import cn.trasen.CommonException;

/**
 * @author rq
 */
public class CommonUnknownException extends CommonException {

    private  ExceptionInfoInterface exceptionInfo;

    public CommonUnknownException(ExceptionInfoInterface exceptionInfo,int code,String type,String subtype
            ,String detail,Throwable cause) {
        super(code,type,subtype,detail,cause);
    }

    public ExceptionInfoInterface getExceptionInfo() {
        return exceptionInfo;
    }
}

