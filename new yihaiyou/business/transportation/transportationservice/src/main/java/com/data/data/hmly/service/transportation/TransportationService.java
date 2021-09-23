package com.data.data.hmly.service.transportation;

import com.data.data.hmly.service.transportation.dao.TransportationDao;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sane on 2015/12/25.
 */
@Service
public class TransportationService {

    @Resource
    private TransportationDao transportationDao;


    public Transportation get(Long id) {
        return transportationDao.load(id);
    }

    public List<Transportation> listAll() {
        return transportationDao.findAll();
    }

    public List<Transportation> findByName(String name) {
        Transportation condition = new Transportation();
        condition.setName(name);
        return list(condition, new Page(1, 10));
    }

    public List<Transportation> findByName(String name, int type) {
        Transportation condition = new Transportation();
        condition.setName(name);
        condition.setType(type);
        return list(condition, new Page(1, 10));
    }

    public List<Transportation> findBySearchName(String searchName, int type) {
        Transportation condition = new Transportation();
        condition.setSearchName(searchName);
        condition.setType(type);
        return list(condition, new Page(1, 10));
    }
    public List<Transportation> findByCity(int cityId, int type) {
        Transportation trafficConditions = new Transportation();
        trafficConditions.setCityId(cityId);
        trafficConditions.setType(type);
        trafficConditions.setStatus(1);
        return list(trafficConditions, new Page(0, 100));
    }

    public Transportation findByCode(String code, int type) {
        Transportation trafficConditions = new Transportation();
        trafficConditions.setCode(code);
        trafficConditions.setType(type);
        trafficConditions.setStatus(1);
        List<Transportation> transportations = list(trafficConditions, new Page(0, 1));
        if (transportations != null)
            return transportations.get(0);
        return null;
    }

    public List<Transportation> findAirportsByCity(int cityId) {
        return findByCity(cityId, 2);
    }

    public List<Transportation> findTrainStationByCity(int cityId) {
        return findByCity(cityId, 1);
    }

    public List<Transportation> list(Transportation transportation, Page page, String... orderProperties) {
        Criteria<Transportation> criteria = createCriteria(transportation, orderProperties);
        if (page == null) {
            return transportationDao.findByCriteria(criteria);
        }
        return transportationDao.findByCriteria(criteria, page);
    }

    public Criteria<Transportation> createCriteria(Transportation transportation, String... orderProperties) {
        Criteria<Transportation> criteria = new Criteria<Transportation>(Transportation.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.asc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(transportation.getName())) {
            criteria.like("name", transportation.getName(), MatchMode.ANYWHERE);
        }
        if (StringUtils.isNotBlank(transportation.getRegionName())) {
            criteria.like("regionName", transportation.getRegionName(), MatchMode.ANYWHERE);
        }
        if (StringUtils.isNotBlank(transportation.getSearchName())) {
            criteria.like("searchName", transportation.getSearchName(), MatchMode.ANYWHERE);
        }
        if (StringUtils.isNotBlank(transportation.getCityName())) {
            criteria.like("cityName", transportation.getCityName(), MatchMode.ANYWHERE);
        }
        if (StringUtils.isNotBlank(transportation.getCityCode())) {
            criteria.eq("cityCode", transportation.getCityCode());
        }
        if (StringUtils.isNotBlank(transportation.getCode())) {
            criteria.eq("code", transportation.getCode());
        }
        if (transportation.getCityId() != null) {
            criteria.eq("cityId", transportation.getCityId());
        }
        if (transportation.getType() != null) {
            criteria.eq("type", transportation.getType());
        }
        if (transportation.getStatus() != null) {
            criteria.eq("status", transportation.getStatus());
        }
        return criteria;
    }

    public void save(Transportation transportation) {
        transportationDao.save(transportation);
    }

    public void update(Transportation transportation) {
        transportationDao.update(transportation);
    }

    public void delTransport(Transportation transportation) {
        transportationDao.delete(transportation);
    }
}
