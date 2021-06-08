package cn.trasen.chengying.service.impl;


import cn.trasen.chengying.entity.singleData.RequestWriteGraph;
import cn.trasen.chengying.service.WriteGraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author rq
 */
@Slf4j
@Service
public  class WriteGraphImpl implements WriteGraph {
    @Autowired
    private  MongoTemplate mongoTemp;

    @Override
    public Integer writeGraph(RequestWriteGraph request) {
        log.info("保存流程图内容 request = {} ", request);
        mongoTemp.insert(request,"Graph");
        log.info("保存成功");
        return 1;
    }
}
