package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.pagedesigner.EventsDTO;
import cn.trasen.chengying.entity.pagedesigner.InputEvents;
import cn.trasen.chengying.entity.pagedesigner.InputOnlyId;
import cn.trasen.chengying.entity.pagedesigner.InputUpdateEvents;

/**
 * @author rq
 */
public interface Events {
    /**
     * 新增复合组件
     *
     * @param request 请求参数
     * @return 字符串
     */
    EventsDTO createEvents(InputEvents request);
    /**
     * 查询复合组件
     * @param version 版本
     * @param id id编号
     * @return String
     */
    String readEvents(String version,String id);
    /**
     * 更改复合组件
     *
     * @param request 请求参数
     * @return 字符串
     */
    String updateEvents(InputUpdateEvents request);
    /**
     * 删除复合组件
     *
     * @param request 请求参数
     * @return 字符串
     */
    String deleteEvents(InputOnlyId request);
}
