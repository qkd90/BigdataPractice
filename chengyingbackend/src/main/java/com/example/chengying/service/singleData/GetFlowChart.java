package com.example.chengying.service.singleData;

import com.example.chengying.entity.singleData.RequestGetFlowChart;
import com.example.chengying.entity.singleData.ReturnGraph;

/**
 * @author rq
 */

public interface GetFlowChart {
    /**
     * fetch data by rule id
     *
     * @param request json format context
     * @return Result<GetFlowChartDTO>
     */
    ReturnGraph getFlowChart(RequestGetFlowChart request);
}

