package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao;
import com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.DateUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CruiseShipDateService {
    @Resource
    private CruiseShipDateDao cruiseShipDateDao;
    @Resource
    private CruiseShipRoomDateDao cruiseShipRoomDateDao;

    /**
     * 列表查询
     */
    public List<CruiseShipDate> listCruiseShipDates(Long cruiseShipId, Date dateStart, Date dateEnd) {
        return cruiseShipDateDao.listCruiseShipDates(cruiseShipId, dateStart, dateEnd);
    }

    /**
     * 列表条件查询
     */
    public List<CruiseShipDate> dateList(Page page, String sortName, String sortOrder, Long brandId, Long routeId, Date startTime){

        Criteria<CruiseShipDate> criteria = new Criteria<CruiseShipDate>(CruiseShipDate.class);
        criteria.createCriteria("cruiseShip");
        criteria.eq("cruiseShip.status", ProductStatus.UP);
//        if (startDate != null) {
//            criteria.ge("date", startDate);
//        }
        if(sortName != null && sortOrder != null) {
            criteria.orderBy(sortName, sortOrder);
        }
        if (brandId != null){
            criteria.eq("cruiseShip.brand.id", brandId);
        }
        if(routeId != null){
            criteria.eq("cruiseShip.route.id", routeId);
        }
        if(startTime != null){
            criteria.eq("date", startTime);
        }
        return cruiseShipDateDao.findByCriteria(criteria, page);

    }

    /**
     * 查询
     * @param id
     * @return
     */
    public CruiseShipDate findById(Long id) {
        return cruiseShipDateDao.load(id);
    }

    /**
     * 新增
     */
    public void saveCruiseShipDate(CruiseShipDate cruiseShipDate) {
        cruiseShipDateDao.delBy(cruiseShipDate.getCruiseShip().getId(), cruiseShipDate.getDate());  // 先删除，以防止重复日期
        cruiseShipDateDao.save(cruiseShipDate);
    }

    /**
     * 删除
     */
    public void delCruiseShipDate(Long id) {
        cruiseShipRoomDateDao.delByDateId(id);
        cruiseShipDateDao.delete(id, CruiseShipDate.class);
    }


    public List<CruiseShipDate> listByCruiseShipId(Long shipId, Date startDate) {
        Criteria<CruiseShipDate> criteria = new Criteria<CruiseShipDate>(CruiseShipDate.class);
        if (shipId != null) {
            criteria.eq("cruiseShip.id", shipId);
        }
        if (startDate != null) {
            criteria.ge("date", startDate);
        }
        criteria.orderBy(Order.asc("date"));
        return cruiseShipDateDao.findByCriteria(criteria);
    }

    public Map<String, Object> checkPriceExists(Map<String, Object> result, CruiseShip cruiseShip) {
        Criteria<CruiseShipDate> criteria = new Criteria<CruiseShipDate>(CruiseShipDate.class);
        criteria.eq("cruiseShip.id", cruiseShip.getId());
        criteria.ge("date", new Date());
        criteria.setProjection(Projections.rowCount());
        Long priceCount = cruiseShipDateDao.findLongCriteria(criteria);
        if (priceCount <= 0) {
            result.put("success", false);
            result.put("msg", "邮轮[" + cruiseShip.getName() + "]没有可用的报价数据, 请检查后重试!");
        } else {
            result.put("success", true);
            result.put("msg", "");
        }
        return result;
    }

    public CruiseShipDate addCollectNum(Long id) {
        CruiseShipDate date = findById(id);
        CruiseShip ship = date.getCruiseShip();
        Integer collectNum = ship.getCollectionNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        ship.setCollectionNum(collectNum);
        cruiseShipDateDao.update(date);
        return date;
    }

    public CruiseShipDate delCollectNum(Long id) {
        CruiseShipDate date = findById(id);
        CruiseShip ship = date.getCruiseShip();
        Integer collectNum = ship.getCollectionNum();
        if (collectNum != null && collectNum > 0) {
            collectNum--;
        } else {
            collectNum = 0;
        }
        ship.setCollectionNum(collectNum);
        cruiseShipDateDao.update(date);
        return date;
    }

    public CruiseShipDate getMinPriceDate(CruiseShip cruiseShip, Date startDate) {
        Criteria<CruiseShipDate> criteria = new Criteria<CruiseShipDate>(CruiseShipDate.class);
        // 邮轮标识
        criteria.eq("cruiseShip.id", cruiseShip.getId());
        if (startDate != null) {
            criteria.ge("date", startDate);
        }
        criteria.orderBy("minDiscountPrice", "asc");
        List<CruiseShipDate> dateList = cruiseShipDateDao.findByCriteria(criteria);
        if (dateList.isEmpty()) {
            return null;
        }
        return dateList.get(0);
    }

    public List<Long> getIdListByCruiseshipId(final Long cruiseShipId) {
        List<CruiseShipDate> cruiseShipDates = listByCruiseShipId(cruiseShipId, null);
        List<Long> idList = Lists.transform(cruiseShipDates, new Function<CruiseShipDate, Long>() {
            @Override
            public Long apply(CruiseShipDate cruiseShipDate) {
                return cruiseShipDate.getId();
            }
        });
        return idList;
    }

    public Integer deleteCollect(Long favoriteId) {
        CruiseShipDate cruiseShipDate = findById(favoriteId);
        CruiseShip cruiseShip = cruiseShipDate.getCruiseShip();
        Integer collectNum = cruiseShip.getCollectionNum();
        if (collectNum == null || collectNum == 0) {
            return 0;
        }
        collectNum--;
        cruiseShip.setCollectionNum(collectNum);
        return collectNum;
    }

    public Integer doAddCollect(Long favoriteId) {
        CruiseShipDate cruiseShipDate = findById(favoriteId);
        CruiseShip cruiseShip = cruiseShipDate.getCruiseShip();
        Integer collectNum = cruiseShip.getCollectionNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        cruiseShip.setCollectionNum(collectNum);
        return collectNum;
    }

    public List<CruiseShipDate> getInIdRange(Long currentId, Long endId, Date date, int pageSize) {

        Criteria<CruiseShipDate> criteria = new Criteria<CruiseShipDate>(CruiseShipDate.class);
        // 邮轮标识
        criteria.gt("id", currentId);
        if (endId != null) {
            criteria.le("id", endId);
        }
        if (date != null) {
            criteria.ge("date", date);
        }
       return cruiseShipDateDao.findByCriteria(criteria, new Page(1, pageSize));
    }
}
