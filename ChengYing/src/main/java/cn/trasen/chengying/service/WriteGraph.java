package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.singleData.RequestWriteGraph;

/**
 * @author rq
 */

public interface WriteGraph {
    /**
     * fetch data by rule id
     *
     * @param request json format context
     * @return Result<GetFlowChartDTO>
     */

    Integer writeGraph(RequestWriteGraph request);
}