package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.pagedesigner.*;

import java.util.List;

/**
 * @author rq
 */
public interface Elements {
    /**
     * 新增复合组件
     *
     * @param request 请求参数
     * @return 字符串
     */
    String createElements(InputCreate request);
    /**
     * 查询复合组件
     *
     * @param request 请求参数
     * @return ReturnFindPage
     */
    ReturnElements readElements(InputOnlyId request);
    /**
     * 更改复合组件
     *
     * @param request 请求参数
     * @return 字符串
     */
    String updateElements(InputUpdate request);
    /**
     * 删除复合组件
     *
     * @param request 请求参数
     * @return 字符串
     */
    String deleteElements(InputUpdate request);
    /**
     * 查询复合组件清单
     *
     * @return ReturnFindPage
     */
    List<ReturnList> readElementsList();
}
