package com.data.data.hmly.service.line.dao;

import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LinetypepricedateDao extends DataAccess<Linetypepricedate> {

	/**
	 * 根据类别报价、报价时间起始删除
	 * @author caiys
	 * @date 2015年10月23日 下午3:02:34
	 * @param priceId
	 * @param dateStart
	 * @param dateEnd
	 */
	public void delByPriceIdAndDay(Long priceId, Date dateStart, Date dateEnd) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
		hql.append(" delete Linetypepricedate where linetypeprice.id = ? ");
        params.add(priceId);
		if (dateStart != null) {
			hql.append("and day >= ? ");
			params.add(dateStart);
		}
		if (dateEnd != null) {
			hql.append("and day <= ? ");
			params.add(dateStart);
		}
		updateByHQL(hql.toString(), params.toArray());
	}
	
	/**
	 * 查询报价时间
	 * @author caiys
	 * @date 2015年10月27日 下午7:18:19
	 * @param linetypepricedate
	 * @return
	 */
	public List<Linetypepricedate> findTypePriceDate(Linetypepricedate linetypepricedate) {
		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		foramtCond(linetypepricedate, criteria);
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
	public void foramtCond(Linetypepricedate linetypepricedate, Criteria<Linetypepricedate> criteria) {
		// 价格类型标识
		if (linetypepricedate.getLinetypeprice() != null && linetypepricedate.getLinetypeprice().getId() != null) {
			criteria.eq("linetypeprice.id", linetypepricedate.getLinetypeprice().getId());
		}
		// 线路标识
		if (linetypepricedate.getLineId() != null) {
			criteria.eq("lineId", linetypepricedate.getLineId());
		}
	}
	
	/**
	 * 校验是否存在报价时间，日期范围第二天和下个月最后一天
	 * @author caiys
	 * @date 2015年11月6日 下午4:42:09
	 * @param lineId
	 * @param linetypepriceId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public Integer checkCount(Long lineId, Long linetypepriceId, Date dateStart, Date dateEnd) {
		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		criteria.eq("lineId", lineId);
		if (linetypepriceId != null) {
			criteria.eq("linetypeprice.id", linetypepriceId);
		}
		criteria.ge("day", dateStart);
		criteria.le("day", dateEnd);
		criteria.setProjection(Projections.rowCount());
		Long count = findLongCriteria(criteria);
		if (count == null) {
			return 0;
		} else {
			return count.intValue();
		}
	}
	
	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}
	
	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}
	
	@Override
	public void save(List<?> objs) {
		// TODO Auto-generated method stub
		super.save(objs);
	}
}
