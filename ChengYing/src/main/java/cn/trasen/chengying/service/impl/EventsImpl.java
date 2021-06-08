package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.pagedesigner.*;
import cn.trasen.chengying.service.Events;
import lombok.extern.slf4j.Slf4j;
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
public class EventsImpl implements Events {
    @Autowired
    private MongoTemplate mongoTemp;

    @Override
    public EventsDTO createEvents(InputEvents request){
        log.info("存入数据库的数据:request = {} ", request);
        EventsDTO eventsDTO = new EventsDTO();
        Location location = new Location();
        location.setEs6(request.getCode());
        location.setEs5(request.getCode());
        eventsDTO.setLocation(location);
        mongoTemp.insert(eventsDTO,"Events");
        String name = eventsDTO.getId();
        log.info("保存成功,name：{}",name);
        location.setEs6("http://chengying.trasen.cn/designer/v1/storage/view/events/"+name+"/es6.js");
        location.setEs5("http://chengying.trasen.cn/designer/v1/storage/view/events/"+name+"/es5.js");
        eventsDTO.setLocation(location);
        return eventsDTO;
    }

    @Override
    public String readEvents(String version, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        log.info("查询体 query = {} ", query);
        EventsDTO eventsDTO = mongoTemp.findOne(query, EventsDTO.class);
        if (eventsDTO == null) {
            log.warn("查询无该链接!");
            return null;
        }
        String str = "es5";
        if (version.equals(str)){
            log.info("查询结果返回 = {} ", eventsDTO.getLocation().getEs5());
            return eventsDTO.getLocation().getEs5();
        }else {
            log.info("查询结果返回 = {} ", eventsDTO.getLocation().getEs6());
            return eventsDTO.getLocation().getEs6();
        }
    }

    @Override
    public String updateEvents(InputUpdateEvents request) {
        log.info("更改事件的id:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        Update update = new Update();
        update.set( "object.es5", request.getObject().getCode() );
        mongoTemp.upsert(query,update,"Events");
        update.set( "object.es6", request.getObject().getCode() );
        mongoTemp.upsert(query,update,"Events");
        log.info("修改成功");
        return request.getId();
    }

    @Override
    public String deleteEvents(InputOnlyId request) {
        log.info("删除事件的id:request = {} ", request);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request.getId()));
        mongoTemp.remove(query,"Events");
        log.info("删除成功");
        return request.getId();
    }

}