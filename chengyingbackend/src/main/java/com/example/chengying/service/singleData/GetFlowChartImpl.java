package com.example.chengying.service.singleData;

import com.example.chengying.entity.singleData.RequestGetFlowChart;
import com.example.chengying.entity.singleData.ReturnGraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author rq
 */
@Slf4j
@Service
public class GetFlowChartImpl implements GetFlowChart {
    private final MongoTemplate mongoTemp;

    public GetFlowChartImpl(MongoTemplate mongoTemp) {
        this.mongoTemp = mongoTemp;
    }

    @Override
    public ReturnGraph getFlowChart(RequestGetFlowChart request) {
        log.info("读取流程图id:request = {} ", request);
        Query query = new Query(Criteria.where("data.id").is(request.getId()));
        log.info("查询体 query = {} ", query);
        ReturnGraph returnGraph = mongoTemp.findOne(query, ReturnGraph.class);
        if (returnGraph == null) {
            log.warn("查询无流程图!");
            return null;
        }
        log.info("结果返回 serviceAddressInfo = {} ", returnGraph);
        return returnGraph;
    }
}
