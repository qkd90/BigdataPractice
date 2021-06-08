package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.ResultError;
import cn.trasen.chengying.entity.ResultParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author rq
 */
@Slf4j
@RestControllerAdvice

public class CommonUnknownExceptionHandler {

    @ExceptionHandler(value = CommonUnknownException.class)
    public ResultParameters<ResultError> bizExceptionHandler(CommonUnknownException c) {
        log.error("发生业务异常！原因是：{}", (Object) c.getStackTrace());
        ExceptionInfoInterface exceptionInfo = c.getExceptionInfo();
        ResultError resultError = new ResultError();
        resultError.setType(CommonEnum.QueryService.getType());
        resultError.setSubtype(CommonEnum.QueryService.getSubtype());
        resultError.setDetail(CommonEnum.QueryService.getDetail());
        resultError.setTrace(c.getStackTrace());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
        ResultParameters<ResultError> result = new ResultParameters<>();
        result.setCode(CommonEnum.QueryService.getCode());
        result.setMessage(CommonEnum.QueryService.getMessage());
        result.setTime(sdf.format(new Date()));
        result.setData(resultError);
        return result;
    }
}
