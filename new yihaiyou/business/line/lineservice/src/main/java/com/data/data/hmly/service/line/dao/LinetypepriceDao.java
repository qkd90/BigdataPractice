package com.data.data.hmly.service.line.dao;

import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class LinetypepriceDao extends DataAccess<Linetypeprice> {


	/**
	 * line获取报价列表
	 * @param line
	 * @return
	 */
	public List<Linetypeprice> getTypePriceList(Line line) {
		Criteria<Linetypeprice> criteria = new Criteria<Linetypeprice>(Linetypeprice.class);
		criteria.eq("line", line);
		return findByCriteria(criteria);
	}
	
	/**
	 * 线路类别报价列表
	 * @author caiys
	 * @date 2015年10月21日 下午2:21:01
	 * @return
	 */
	public List<Linetypeprice> findLinetypepriceList(Linetypeprice linetypeprice) {
		Criteria<Linetypeprice> criteria = new Criteria<Linetypeprice>(Linetypeprice.class);
		foramtCond(linetypeprice, criteria);
		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}
	

	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author caiys
	 * @date 2015年10月16日 上午9:10:28
	 * @param linetypeprice
	 * @param criteria
	 */
	public void foramtCond(Linetypeprice linetypeprice, Criteria<Linetypeprice> criteria) {
		// 线路标识
		if (linetypeprice.getLine() != null && linetypeprice.getLine().getId() != null) {
			criteria.eq("line.id", linetypeprice.getLine().getId());
		}
	}
	
	/**
	 * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
	 * @author caiys
	 * @date 2015年10月21日 下午3:50:34
	 * @param linetypepriceId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public Float findMinValue(Long linetypepriceId, Date dateStart, Date dateEnd, String prop) {
		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		criteria.eq("linetypeprice.id", linetypepriceId);
		if (dateStart != null) {
			criteria.ge("day", dateStart);
		}
		if (dateEnd != null) {
			criteria.le("day", dateEnd);
		}
		criteria.setProjection(Projections.min(prop));
		return (Float) findUniqueCriteria(criteria);
	}

    public Float findMinDicountPriceByLineId(Long lineId, Date startDate, Date endDate) {
        Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
        criteria.eq("lineId", lineId);
        if (null != startDate) {
            criteria.ge("day", startDate);
        }
        if (null != endDate) {
            criteria.le("day", endDate);
        }
        criteria.setProjection(Projections.min("discountPrice"));
        return (Float) findUniqueCriteria(criteria);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<Linetypepricedate> criteria) {
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
