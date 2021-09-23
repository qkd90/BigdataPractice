package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipProjectClassifyDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProjectClassify;
import com.framework.hibernate.util.Criteria;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Think on 2017/6/7.
 */

@Service
public class CruiseShipProjectClassifyService {
    @Resource
    private CruiseShipProjectClassifyDao cruiseShipProjectClassifyDao;

    private CruiseShipProjectService cruiseShipProjectService;

    public CruiseShipProjectClassify get(Long id) {

        return cruiseShipProjectClassifyDao.load(id);
    }

    public void update(CruiseShipProjectClassify cruiseShipProjectClassify) {
        cruiseShipProjectClassifyDao.update(cruiseShipProjectClassify);
    }

    public void save(CruiseShipProjectClassify cruiseShipProjectClassify) {

        cruiseShipProjectClassifyDao.save(cruiseShipProjectClassify);
    }

    public void delete(Long id) {
        cruiseShipProjectClassifyDao.delete(id, CruiseShipProjectClassify.class);
    }

    public List<CruiseShipProjectClassify> listById(Long parentId,String keyword) {

        Criteria<CruiseShipProjectClassify> criteria = new Criteria<CruiseShipProjectClassify>(CruiseShipProjectClassify.class);
        criteria.createCriteria("cruiseShipProjectClassify");
        if (parentId != null) {
            criteria.eq("cruiseShipProjectClassify.id", parentId);
        }
        if (StringUtils.isNotBlank(keyword)) {
            criteria.like("classifyName", keyword);
        }

        criteria.eq("showStatus",true);
        criteria.isNotNull("cruiseShipProjectClassify.id");
        criteria.orderBy(Order.asc("sort"));
        return cruiseShipProjectClassifyDao.findByCriteria(criteria);
    }

    public List<CruiseShipProjectClassify> listById(Long parentId) {
        Criteria<CruiseShipProjectClassify> criteria = new Criteria<CruiseShipProjectClassify>(CruiseShipProjectClassify.class);
        if (parentId != null) {
            criteria.eq("cruiseShipProjectClassify.id", parentId);
        }
        criteria.eq("showStatus",true);
        criteria.orderBy(Order.asc("sort"));
        return cruiseShipProjectClassifyDao.findByCriteria(criteria);
    }

    public List<CruiseShipProjectClassify> queryById(Long parentId) {

        Criteria<CruiseShipProjectClassify> criteria = new Criteria<CruiseShipProjectClassify>(CruiseShipProjectClassify.class);
        if (parentId != null) {
            criteria.eq("cruiseShipProjectClassify.cruiseShipProjectClassify.id", parentId);
        }

        criteria.eq("showStatus",true);
        criteria.isNotNull("cruiseShipProjectClassify.id");
        return cruiseShipProjectClassifyDao.findByCriteria(criteria);
    }

    public List<CruiseShipProjectClassify> queryClassifyNameById(Long parentId) {

        Criteria<CruiseShipProjectClassify> criteria = new Criteria<CruiseShipProjectClassify>(CruiseShipProjectClassify.class);
        criteria.createCriteria("cruiseShipProjectClassify");
        criteria.eq("showStatus",true);
        criteria.eq("cruiseShipProjectClassify.id", parentId);
        return cruiseShipProjectClassifyDao.findByCriteria(criteria);
    }

//    public void listClassifyAndProjectById(Long cruiseShipId, Long parentId, List<CruiseShipProjectClassify> listCruiseShipProjectClassify){
//
//        for(CruiseShipProjectClassify listCruiseShipProjectClassifyParentItem:listCruiseShipProjectClassify){
//            listCruiseShipProjectClassifyParentItem.setListCruiseShipProject(cruiseShipProjectService.listById(cruiseShipId , listCruiseShipProjectClassifyParentItem.getId()));
//
//        }
//        return listCruiseShipProjectClassify;
//    }
}
