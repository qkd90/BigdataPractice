package com.data.data.hmly.service.line.dao;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.line.entity.Linedaysplan;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class LinedaysplanDao extends DataAccess<Linedaysplan> {
	
	/**
	 * 删除线路天内行程
	 * @author caiys
	 * @date 2015年10月26日 上午10:22:07
	 * @param lineId
	 */
	public void delByLineId(Long lineId) {
		String hql = " delete Linedaysplan where lineId = ? ";
		updateByHQL(hql, lineId);
	}
	
	/**
	 * 查询线路天内行程
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param lineexplain
	 * @return
	 */
	public List<Linedaysplan> findLinedaysplan(Linedaysplan linedaysplan) {
		Criteria<Linedaysplan> criteria = new Criteria<Linedaysplan>(Linedaysplan.class);
		if (linedaysplan.getLinedays() != null && linedaysplan.getLinedays().getId() != null) {
			criteria.eq("linedays.id", linedaysplan.getLinedays().getId());
		}
		if (linedaysplan.getLineId() != null) {
			criteria.eq("lineId", linedaysplan.getLineId());
		}
		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}
	
	/**
	 * 校验是否存在天内行程
	 * @author caiys
	 * @date 2015年11月6日 下午4:48:12
	 * @param lineId
	 * @param linedaysId
	 * @return
	 */
	public Integer checkCount(Long lineId, Long linedaysId) {
		Criteria<Linedaysplan> criteria = new Criteria<Linedaysplan>(Linedaysplan.class);
		criteria.ge("lineId", lineId);
		if (linedaysId != null) {
			criteria.eq("linedays.id", linedaysId);
		}
		criteria.setProjection(Projections.rowCount());
		Long count = findLongCriteria(criteria);
		if (count == null) {
			return 0;
		} else {
			return count.intValue();
		}
	}
}
