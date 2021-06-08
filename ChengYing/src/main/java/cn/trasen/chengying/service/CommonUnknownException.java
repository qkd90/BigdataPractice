package cn.trasen.chengying.service;


/**
 * @author rq
 */
public class CommonUnknownException extends CommonException {

    private  ExceptionInfoInterface exceptionInfo;

    public CommonUnknownException(ExceptionInfoInterface exceptionInfo,int code,String subtype
            ,String detail,Throwable cause) {
        super(code,subtype,detail,cause);
    }

    public ExceptionInfoInterface getExceptionInfo() {
        return exceptionInfo;
    }
}

