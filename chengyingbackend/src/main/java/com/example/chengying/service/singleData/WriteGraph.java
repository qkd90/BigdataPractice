package com.example.chengying.service.singleData;

import com.example.chengying.entity.singleData.RequestWriteGraph;

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