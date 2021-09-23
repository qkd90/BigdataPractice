package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelToUpdateDao;
import com.data.data.hmly.service.hotel.entity.HotelToUpdate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class HotelToUpdateService {
    @Resource
    private HotelToUpdateDao hotelToUpdateDao;

    public List<HotelToUpdate> listAll() {
        return list(new HotelToUpdate(), null);
    }

    public List<HotelToUpdate> list(HotelToUpdate hotel, Page page, String... orderProperties) {
        Criteria<HotelToUpdate> criteria = createCriteria(hotel, orderProperties);
        if (page == null) {
            return hotelToUpdateDao.findByCriteria(criteria);
        }
        return hotelToUpdateDao.findByCriteria(criteria, page);
    }

    public Criteria<HotelToUpdate> createCriteria(HotelToUpdate hotel, String... orderProperties) {
        Criteria<HotelToUpdate> criteria = new Criteria<HotelToUpdate>(HotelToUpdate.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(hotel.getName())) {
            criteria.eq("name", hotel.getName());
        }
        if (hotel.getStatus() != null) {
            criteria.eq("status", hotel.getStatus());
        }

        if (hotel.getUpdateTime() != null) {
            criteria.gt("updateTime", hotel.getUpdateTime());
        }
        return criteria;
    }

    public void clear() {
        hotelToUpdateDao.getHibernateTemplate().clear();
    }

}
