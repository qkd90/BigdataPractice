package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.pagedesigner.InputUpdate;
import cn.trasen.chengying.entity.pagedesigner.InputWritePage;
import cn.trasen.chengying.entity.pagedesigner.ReturnFindPage;

/**
 * @author rq
 */
public interface Pages {
    /**
     * 请求微服务文档
     *
     * @param request 请求参数
     * @return ReturnFindPage
     */
    ReturnFindPage findPage(InputUpdate request);
    /**
     * 请求微服务文档
     *
     * @param request 请求参数
     * @return 字符串
     */
    String writePage(InputWritePage request);
    /**
     * 请求微服务文档
     *
     * @param request 请求参数
     * @return 字符串
     */
    String updatePage(InputUpdate request);
}
