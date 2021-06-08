package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.pagedesigner.InputUpdate;
import cn.trasen.chengying.service.Pages;
import cn.trasen.chengying.entity.pagedesigner.InputWritePage;
import cn.trasen.chengying.entity.pagedesigner.ReturnFindPage;
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
public class PagesImpl implements Pages {
    @Autowired
    private MongoTemplate mongoTemp;

    @Override
    public String writePage(InputWritePage request) {
        log.info("存入数据库的数据:request = {} ", request);
        mongoTemp.insert(request,"Pages");
        log.info("保存成功");
        return request.getId();
    }

    @Override
    public ReturnFindPage findPage(InputUpdate request) {
        log.info("读取画布的id:request = {} ", request);
        Query query = new Query(Criteria.where("id").is(request.getId()));
        log.info("查询体 query = {} ", query);
        ReturnFindPage returnFindPage = mongoTemp.findById(new ObjectId(request.getId()), ReturnFindPage.class);
        if (returnFindPage == null) {
            log.warn("查询无该画布!");
            return null;
        }
        log.info("结果返回 serviceAddressInfo = {} ", returnFindPage);
        return returnFindPage;
    }


    @Override
    public String updatePage(InputUpdate request) {
        log.info("更改数据库的数据:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        Update update = new Update();
        update.set("data", request.getUserData().getName());
        mongoTemp.upsert(query,update,"Pages");
        log.info("修改成功");
        return request.getId();
    }

}
