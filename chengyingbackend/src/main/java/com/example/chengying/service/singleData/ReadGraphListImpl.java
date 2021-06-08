package com.example.chengying.service.singleData;

import com.example.chengying.entity.singleData.ReturnGraphList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rq
 */
@Slf4j
@Service
public class ReadGraphListImpl implements ReadGraphList {
    private final MongoTemplate mongoTemp;

    public ReadGraphListImpl(MongoTemplate mongoTemp) {
        this.mongoTemp = mongoTemp;
    }

    @Override
    public List<ReturnGraphList> readGraphList() {
        log.info("获取流程图清单");
        Query query = new Query(Criteria.where("_id").exists(true));
        log.info("查询体 query = {} ", query);
        List<ReturnGraphList> returnGraphList = mongoTemp.find(query, ReturnGraphList.class);
        if (returnGraphList.isEmpty() ) {
            log.warn("查询无流程图!");
            return null;
        }
        log.info("结果返回 serviceAddressInfo = {} ", returnGraphList);
        return returnGraphList;
    }
}
