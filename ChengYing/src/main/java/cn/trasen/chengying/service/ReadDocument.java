package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.RequestServiceInfoQuery;

/**
 * @author rq
 */
public interface ReadDocument {
    /**
     * 请求微服务文档
     *
     * @param request 请求参数
     * @return 字符串
     */
    String findByName(RequestServiceInfoQuery request);
    /**
     * 请求sql文档
     *
     * @param request 请求参数
     * @return 字符串
     */
    String readSql(RequestServiceInfoQuery request);
}


