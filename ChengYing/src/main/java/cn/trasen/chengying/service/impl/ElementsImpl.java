package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.pagedesigner.*;
import cn.trasen.chengying.service.Elements;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author rq
 */
@Slf4j
@Service
public class ElementsImpl implements Elements {
    @Autowired
    private MongoTemplate mongoTemp;

    @Override
    public String createElements(InputCreate request){
        log.info("存入数据库的数据:request = {} ", request);
        mongoTemp.insert(request,"Elements");
        log.info("保存成功");
        return request.getId();
    }

    @Override
    public ReturnElements readElements(InputOnlyId request) {
        log.info("查询基础组件的id:request = {} ", request);
        Query query = new Query(Criteria.where("_id").is(request.getId()));
        log.info("查询体 query = {} ", query);
        ReturnElements returnElements = mongoTemp.findById(new ObjectId(request.getId()), ReturnElements.class);
        if (returnElements == null) {
            log.warn("查询无该基础组件!");
            return null;
        }
        log.info("查询结果返回 = {} ", returnElements);
        return returnElements;
    }

    @Override
    public String updateElements(InputUpdate request) {
        log.info("更改基础组件的id:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        Update update = new Update();
        update.set("name", request.getUserData().getName());
        mongoTemp.upsert(query,update,"Elements");
        update.set("object", request.getUserData().getObject());
        mongoTemp.upsert(query,update,"Elements");
        log.info("修改成功");
        return request.getId();
    }

    @Override
    public String deleteElements(InputUpdate request) {
        log.info("删除基础组件的id:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        mongoTemp.remove(query,"Elements");
        log.info("删除成功");
        return request.getId();
    }

    @Override
    public List<ReturnList> readElementsList() {
        Query query = new Query(Criteria.where("_id").exists(true));
        List<ReturnList> returnList = mongoTemp.find(query, ReturnList.class);
        log.info("删除成功");
        return returnList;
    }

}
