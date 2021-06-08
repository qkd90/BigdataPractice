package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.singleData.ReturnGraphList;
import cn.trasen.chengying.service.ReadGraphList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private  MongoTemplate mongoTemp;

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
