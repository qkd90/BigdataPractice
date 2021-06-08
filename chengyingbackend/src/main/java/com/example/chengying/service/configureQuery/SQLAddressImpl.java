package com.example.chengying.service.configureQuery;

import com.example.chengying.entity.configureQuery.SQLAddressDTO;
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
public class SQLAddressImpl implements SQLAddress{
    private final MongoTemplate mongoTemp;

    public SQLAddressImpl(MongoTemplate mongoTemp) {
        this.mongoTemp = mongoTemp;
    }

    @Override
    public SQLAddressDTO findSQLAddress() {
        log.info("查询SQL地址");
        Query query = new Query(Criteria.where("description").is("创星SQL公网测试01"));
        log.info("查询体 query = {} ", query);
        SQLAddressDTO sqlAddressDTO = mongoTemp.findOne(query, SQLAddressDTO.class);
        if (sqlAddressDTO == null) {
            log.warn("查询无服务地址!");
            return null;
        }
        log.info("结果返回 serviceAddressInfo = {} ", sqlAddressDTO.getId());
        return sqlAddressDTO;
    }
}
