package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.singleData.RequestGetFlowChart;
import cn.trasen.chengying.entity.singleData.ReturnGraph;

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

