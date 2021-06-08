package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.pagedesigner.*;
import cn.trasen.chengying.service.Components;
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
public class ComponentsImpl implements Components {
    @Autowired
    private MongoTemplate mongoTemp;

    @Override
    public String createComponents(InputCreate request){
        log.info("存入数据库的数据:request = {} ", request);
        mongoTemp.insert(request,"Components");
        log.info("保存成功");
        return request.getId();
    }

    @Override
    public ReturnPanels readComponents(InputOnlyId request) {
        log.info("查询复合组件的id:request = {} ", request);
        Query query = new Query(Criteria.where("_id").is(request.getId()));
        log.info("查询体 query = {} ", query);
        ReturnPanels returnPanels = mongoTemp.findById(new ObjectId(request.getId()), ReturnPanels.class);
        if (returnPanels == null) {
            log.warn("查询无该面板!");
            return null;
        }
        log.info("查询结果返回 = {} ", returnPanels);
        return returnPanels;
    }

    @Override
    public String updateComponents(InputUpdate request) {
        log.info("更改复合组件的id:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        Update update = new Update();
        update.set("name", request.getUserData().getName());
        mongoTemp.upsert(query,update,"Components");
        update.set("object", request.getUserData().getObject());
        mongoTemp.upsert(query,update,"Components");
        log.info("修改成功");
        return request.getId();
    }

    @Override
    public String deleteComponents(InputUpdate request) {
        log.info("删除复合组件的id:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        mongoTemp.remove(query,"Components");
        log.info("删除成功");
        return request.getId();
    }

    @Override
    public List<ReturnList> readComponentsList() {
        Query query = new Query(Criteria.where("_id").exists(true));
        List<ReturnList> returnList = mongoTemp.find(query, ReturnList.class);
        log.info("删除成功");
        return returnList;
    }

}
