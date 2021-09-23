package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipDeckDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDeck;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CruiseShipDeckService {
    @Resource
    private CruiseShipDeckDao cruiseShipDeckDao;

    public CruiseShipDeck get(Long id) {
        return cruiseShipDeckDao.load(id);
    }

    public void update(CruiseShipDeck cruiseShipDeck) {
        cruiseShipDeckDao.update(cruiseShipDeck);
    }

    public void save(CruiseShipDeck cruiseShipDeck) {
        cruiseShipDeckDao.save(cruiseShipDeck);
    }

    public void delete(Long id) {
        cruiseShipDeckDao.delete(id, CruiseShipDeck.class);
    }
    public List<CruiseShipDeck> listByCruiseShipId(Long shipId) {
        Criteria<CruiseShipDeck> criteria = new Criteria<CruiseShipDeck>(CruiseShipDeck.class);
        criteria.eq("cruiseShip.id", shipId);
        criteria.orderBy(Order.asc("level"));
        return cruiseShipDeckDao.findByCriteria(criteria);
    }
}
