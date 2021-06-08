package com.example.chengying.service.singleData;


import com.example.chengying.entity.singleData.RequestWriteGraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author rq
 */
@Slf4j
@Service
public  class WriteGraphImpl implements WriteGraph {
    private final MongoTemplate mongoTemp;

    public WriteGraphImpl(MongoTemplate mongoTemp) {
        this.mongoTemp = mongoTemp;
    }

    @Override
    public Integer writeGraph(RequestWriteGraph request) {
        log.info("保存流程图内容 request = {} ", request);
        mongoTemp.insert(request,"Graph");
        log.info("保存成功");
        return 1;
    }
}
