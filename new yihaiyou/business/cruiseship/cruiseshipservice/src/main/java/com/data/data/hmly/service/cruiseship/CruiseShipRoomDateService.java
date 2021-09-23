package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao;
import com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;
import com.framework.hibernate.util.Criteria;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CruiseShipRoomDateService {
    @Resource
    private CruiseShipRoomDateDao cruiseShipRoomDateDao;
    @Resource
    private CruiseShipDateDao cruiseShipDateDao;

    /**
     * 列表查询
     */
    public List<CruiseShipRoomDate> listCruiseShipRoomDates(CruiseShipRoomDate cruiseShipRoomDate) {
        return cruiseShipRoomDateDao.listCruiseShipRoomDates(cruiseShipRoomDate);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    public CruiseShipRoomDate findById(Long id) {
        return cruiseShipRoomDateDao.load(id);
    }

    /**
     * 保存或更新
     */
    public Float saveOrUpdate(CruiseShipRoomDate cruiseShipRoomDate) {
        if (cruiseShipRoomDate.getId() != null) {
            cruiseShipRoomDateDao.update(cruiseShipRoomDate);
        } else {
            cruiseShipRoomDateDao.save(cruiseShipRoomDate);
        }
        Float discountPrice = cruiseShipRoomDateDao.findMinValue(cruiseShipRoomDate.getCruiseShipDate().getId(), new Date(), null, "discountPrice");
        Float salePrice = cruiseShipRoomDateDao.findMinValue(cruiseShipRoomDate.getCruiseShipDate().getId(), new Date(), null, "salePrice");
        Float marketPrice = cruiseShipRoomDateDao.findMinValue(cruiseShipRoomDate.getCruiseShipDate().getId(), new Date(), null, "marketPrice");
        cruiseShipRoomDate.getCruiseShipDate().setMinDiscountPrice(discountPrice);
        cruiseShipRoomDate.getCruiseShipDate().setMinSalePrice(salePrice);
        cruiseShipRoomDate.getCruiseShipDate().setMinMarketPrice(marketPrice);
        cruiseShipDateDao.save(cruiseShipRoomDate.getCruiseShipDate());
        return discountPrice;
    }

    /**
     * 删除
     */
    public Float delCruiseShipRoomDate(Long id) {
        CruiseShipRoomDate cruiseShipRoomDate = cruiseShipRoomDateDao.load(id);
        cruiseShipRoomDateDao.delete(cruiseShipRoomDate);
        Float discountPrice = cruiseShipRoomDateDao.findMinValue(cruiseShipRoomDate.getCruiseShipDate().getId(), new Date(), null, "discountPrice");
        Float salePrice = cruiseShipRoomDateDao.findMinValue(cruiseShipRoomDate.getCruiseShipDate().getId(), new Date(), null, "salePrice");
        Float marketPrice = cruiseShipRoomDateDao.findMinValue(cruiseShipRoomDate.getCruiseShipDate().getId(), new Date(), null, "marketPrice");
        cruiseShipRoomDate.getCruiseShipDate().setMinDiscountPrice(discountPrice);
        cruiseShipRoomDate.getCruiseShipDate().setMinSalePrice(salePrice);
        cruiseShipRoomDate.getCruiseShipDate().setMinMarketPrice(marketPrice);
        cruiseShipDateDao.save(cruiseShipRoomDate.getCruiseShipDate());
        return discountPrice;
    }


    public CruiseShipRoomDate findByRoomAndShipDateId(Long roomId, Long dateId) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        criteria.eq("cruiseShipRoom.id", roomId);
        criteria.eq("cruiseShipDate.id", dateId);
        return cruiseShipRoomDateDao.findUniqueByCriteria(criteria);
    }

    public CruiseShipRoomDate findByRoomIdAndDate(Long roomId, Date date) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        criteria.eq("cruiseShipRoom.id", roomId);
        criteria.eq("date", date);
        return cruiseShipRoomDateDao.findUniqueByCriteria(criteria);
    }

    public List<CruiseShipRoomDate> findByDateId(Long dateId, String... orderParams) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        criteria.eq("cruiseShipDate.id", dateId);
        if (orderParams != null && orderParams.length > 0) {
            if (orderParams.length == 1) {
                criteria.orderBy(Order.asc(orderParams[0]));
            } else if (orderParams.length == 2) {
                criteria.orderBy(orderParams[0], orderParams[1]);
            }
        }
        return cruiseShipRoomDateDao.findByCriteria(criteria);
    }

    public List<CruiseShipRoom> queryRoomList(Long dateId) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        criteria.createCriteria("cruiseShipDate", "cruiseShipDate");
        criteria.createCriteria("cruiseShipRoom", "cruiseShipRoom");
        criteria.eq("cruiseShipDate.id", dateId);
        List<CruiseShipRoomDate> cruiseShipRoomDates = cruiseShipRoomDateDao.findByCriteria(criteria);
        List<CruiseShipRoom> cruiseShipRoomList = Lists.transform(cruiseShipRoomDates, new Function<CruiseShipRoomDate, CruiseShipRoom>() {
            @Override
            public CruiseShipRoom apply(CruiseShipRoomDate cruiseShipRoomDate) {
                CruiseShipRoom cruiseShipRoom = cruiseShipRoomDate.getCruiseShipRoom();
                cruiseShipRoom.setMinPrice(cruiseShipRoomDate.getDiscountPrice());
                return cruiseShipRoom;
            }
        });
        return cruiseShipRoomList;
    }
    public List<CruiseShipRoom> getRoomList(Long dateId, CruiseShipRoomType roomType, Date date) {
        Criteria<CruiseShipRoomDate> criteria = new Criteria<CruiseShipRoomDate>(CruiseShipRoomDate.class);
        criteria.createCriteria("cruiseShipDate", "cruiseShipDate");
        criteria.createCriteria("cruiseShipRoom", "cruiseShipRoom");
        criteria.eq("cruiseShipDate.id", dateId);
        if (roomType != null) {
            criteria.eq("cruiseShipRoom.roomType", roomType);
        }
        if (date != null) {
            criteria.ge("cruiseShipDate.date", date);
        }
        List<CruiseShipRoomDate> cruiseShipRoomDates = cruiseShipRoomDateDao.findByCriteria(criteria);
        List<CruiseShipRoom> cruiseShipRoomList = Lists.transform(cruiseShipRoomDates, new Function<CruiseShipRoomDate, CruiseShipRoom>() {
            @Override
            public CruiseShipRoom apply(CruiseShipRoomDate cruiseShipRoomDate) {
                CruiseShipRoom cruiseShipRoom = cruiseShipRoomDate.getCruiseShipRoom();
                cruiseShipRoom.setMinPrice(cruiseShipRoomDate.getSalePrice());
                return cruiseShipRoom;
            }
        });
        return cruiseShipRoomList;
    }
}
