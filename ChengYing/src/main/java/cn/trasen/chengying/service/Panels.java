package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.pagedesigner.InputCreate;
import cn.trasen.chengying.entity.pagedesigner.InputUpdate;
import cn.trasen.chengying.entity.pagedesigner.InputOnlyId;
import cn.trasen.chengying.entity.pagedesigner.ReturnPanels;

/**
 * @author rq
 */
public interface Panels {

    /**
     * 新增面板
     *
     * @param request 请求参数
     * @return 字符串
     */
    String createPanels(InputCreate request);
    /**
     * 查询面板
     *
     * @param request 请求参数
     * @return ReturnFindPage
     */
    ReturnPanels readPanels(InputOnlyId request);
    /**
     * 更改面板
     *
     * @param request 请求参数
     * @return 字符串
     */
    String updatePanels(InputUpdate request);
}
