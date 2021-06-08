package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.pagedesigner.InputCreate;
import cn.trasen.chengying.entity.pagedesigner.InputOnlyId;
import cn.trasen.chengying.entity.pagedesigner.InputUpdate;
import cn.trasen.chengying.entity.pagedesigner.ReturnPanels;
import cn.trasen.chengying.service.Panels;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author rq
 */
@Slf4j
@Service
public class PanelsImpl implements Panels {
    @Autowired
    private MongoTemplate mongoTemp;

    @Override
    public String createPanels(InputCreate request){
        log.info("存入数据库的数据:request = {} ", request);
        mongoTemp.insert(request,"Panels");
        log.info("保存成功");
        return request.getId();
    }

    @Override
    public ReturnPanels readPanels(InputOnlyId request) {
        log.info("查询面板的id:request = {} ", request);
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
    public String updatePanels(InputUpdate request) {
        log.info("更改数据库的数据:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        Update update = new Update();
        update.set("name", request.getUserData().getName());
        mongoTemp.upsert(query,update,"Panels");
        update.set("object", request.getUserData().getObject());
        mongoTemp.upsert(query,update,"Panels");
        log.info("修改成功");
        return request.getId();
    }


}
