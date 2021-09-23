package com.data.data.hmly.service.hotel.dao;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/12/11.
 */
@Repository
public class HotelPriceDao extends DataAccess<HotelPrice> {

    /**
     * 查询酒店房型，线路组合使用
     */
    public List<HotelPrice> listHotelPriceForLine(HotelPrice hotelPrice, Page page, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new HotelPrice(hp.id, h.id, h.name, hp.roomName, hp.price) ");
        hql.append("from HotelPrice hp inner join hp.hotel h where 1 = 1 ");
        hql.append("and h.status = :hotelStatus ");
        params.put("hotelStatus", ProductStatus.UP);
        hql.append("and hp.end >= :end ");
        params.put("end", DateUtils.getDateBeginTime(new Date()));
        if (hotelPrice.getHotelId() != null) {
            hql.append("and h.id = :hotelId ");
            params.put("hotelId", hotelPrice.getHotelId());
        }
        if (StringUtils.isNotBlank(hotelPrice.getHotelName())) {
            hql.append("and h.name like '%'||:hotelName||'%' ");
            params.put("hotelName", hotelPrice.getHotelName());
        }
        // 排除已经被选择的记录
        hql.append("and not exists(select 1 from LinedaysProductPrice lp where lp.productType = :productType and lp.linedays.id = :linedaysId and lp.priceId = hp.id) ");
        params.put("linedaysId", hotelPrice.getLinedaysId());
        params.put("productType", ProductType.hotel);
        return findByHQL2ForNew(hql.toString(), page, params);
    }

    /**
     * 获取房型价格时间在限制时间后有数据的最小价格
     * @param hotelId
     * @param endDate
     * @return
     */
    public Float findMinValue(Long hotelId, Date endDate) {
        Criteria<HotelPrice> criteria = new Criteria<HotelPrice>(HotelPrice.class);
        criteria.eq("hotel.id", hotelId);
        criteria.ge("end", endDate);
        criteria.setProjection(Projections.min("price"));
        return (Float) findUniqueCriteria(criteria);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<HotelPrice> criteria) {
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

}
