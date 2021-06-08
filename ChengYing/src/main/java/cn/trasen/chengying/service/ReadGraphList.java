package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.singleData.ReturnGraphList;

import java.util.List;

/**
 * @author rq
 */
public interface ReadGraphList {
    /**
     * fetch data by rule id
     *
     * @return Result<GetFlowChartDTO>
     */
    List<ReturnGraphList> readGraphList();
}
