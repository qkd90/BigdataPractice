package com.data.data.hmly.service.ctripcommon;

import com.data.data.hmly.service.ctripcommon.dao.CtripApiLogDao;
import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caiys on 2016/2/3.
 */
@Service
public class CtripApiLogService {
    @Resource
    private CtripApiLogDao ctripApiLogDao;

    public void updateCtripApiLog(CtripApiLog ctripApiLog) {
        ctripApiLogDao.update(ctripApiLog);
    }

    /**
     * 根据uuid更新错误状态和错误信息
     * @param uuid
     * @param errorCode
     * @param errorMessage
     */
    public void updateErrorInfo(String uuid, String errorCode, String errorMessage) {
        ctripApiLogDao.updateErrorInfo(uuid, errorCode, errorMessage);
    }

    /**
     * 统计数量
     */
    public Long countCtripApiLog(CtripApiLog ctripApiLog) {
        return ctripApiLogDao.countCtripApiLog(ctripApiLog);
    }

    /**
     * 分页查询
     */
    public List<CtripApiLog> listCtripApiLog(CtripApiLog ctripApiLog, Integer pageIndex, Integer pageSize) {
        return ctripApiLogDao.listCtripApiLog(ctripApiLog, pageIndex, pageSize);
    }

}
