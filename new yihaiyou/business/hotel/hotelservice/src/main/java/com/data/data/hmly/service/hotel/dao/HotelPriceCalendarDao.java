package com.data.data.hmly.service.hotel.dao;

import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/5/26.
 */
@Repository
public class HotelPriceCalendarDao extends DataAccess<HotelPriceCalendar> {

    /**
     * 根据酒店价格、报价时间起始删除
     * @author caiys
     * @date 2015年10月23日 下午3:02:34
     * @param hotelPriceId
     * @param dateStart
     * @param dateEnd
     */
    public void delByPriceIdAndDate(Long hotelPriceId, Date dateStart, Date dateEnd) {
        String hql = " delete HotelPriceCalendar where hotelPrice.id = ? and date >= ? and date <= ? ";
        updateByHQL(hql, hotelPriceId, dateStart, dateEnd);
    }

    /**
     * 获取房型价格时间在限制时间后有数据的最小价格
     * @param hotelId
     * @param start
     * @return
     */
    public Float findMinValue(Long hotelId, Long hotelPriceId, Date start) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelId", hotelId);
        if (hotelPriceId != null) {
            criteria.eq("hotelPrice.id", hotelPriceId);
        }
        if (start != null) {
            criteria.ge("date", start);
        }
        criteria.gt("date", new Date()); // 限制明天起
        criteria.setProjection(Projections.min("member"));
        return (Float) findUniqueCriteria(criteria);
    }

    public Float findMinValue(Long hotelId, Date startDate) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelId", hotelId);
        criteria.ge("date", startDate);
        criteria.setProjection(Projections.min("member"));
        return (Float) findUniqueCriteria(criteria);
    }

    public Date findMaxDateByPirce(HotelPrice hotelPrice) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelPrice.id", hotelPrice.getId());
        criteria.setProjection(Projections.max("date"));
        return (Date) this.findUniqueValue(criteria);
    }
    public Date findMinDateByPirce(HotelPrice hotelPrice) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelPrice.id", hotelPrice.getId());
        criteria.setProjection(Projections.min("date"));
        return (Date) this.findUniqueValue(criteria);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<HotelPriceCalendar> criteria) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }

    public List<HotelPriceCalendar> findTypePriceDate(Long typeRriceId, Date dateStart, Date dateEnd) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelPrice.id", typeRriceId);
        if (dateStart != null) {
            criteria.ge("date", dateStart);
        }
        if (dateEnd != null) {
            criteria.lt("date", dateEnd);
        }
        criteria.orderBy("date", "asc");
        return findByCriteria(criteria);
    }

    public List<HotelPriceCalendar> findTypePriceDate(Long typeRriceId, Date dateStart, Date dateEnd, Integer num) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelPrice.id", typeRriceId);
        if (dateStart != null) {
            criteria.ge("date", dateStart);
        }
        if (dateEnd != null) {
            criteria.lt("date", dateEnd);
        }
        if (num != null) {
            criteria.or(Restrictions.eq("inventory", -1), Restrictions.ge("inventory", num), Restrictions.isNull("inventory"));
        }
        criteria.orderBy("date", "asc");
        return findByCriteria(criteria);
    }

    public void dePriceDate(HotelPrice hotelPrice) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelPrice.id", hotelPrice.getId());
        criteria.gt("date", new Date()); // 只删除今天之后(不含今天)的价格日历
        List<HotelPriceCalendar> calendars = findByCriteria(criteria);
        if (!calendars.isEmpty()) {
            deleteAll(calendars);
        }
    }

    public List<HotelPriceCalendar> getCalendarList(Long typePriceId, Date date) {
        Criteria<HotelPriceCalendar> criteria = new Criteria<HotelPriceCalendar>(HotelPriceCalendar.class);
        criteria.eq("hotelPrice.id", typePriceId);
        criteria.ge("date", date);
        return findByCriteria(criteria);
    }
}
