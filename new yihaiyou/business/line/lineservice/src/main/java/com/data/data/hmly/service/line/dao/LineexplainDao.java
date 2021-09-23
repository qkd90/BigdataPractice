package com.data.data.hmly.service.line.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.line.entity.Lineexplain;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class LineexplainDao extends DataAccess<Lineexplain> {
	
	/**
	 * 查询线路说明
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param lineexplain
	 * @return
	 */
	public List<Lineexplain> findLineexplain(Lineexplain lineexplain) {
		Criteria<Lineexplain> criteria = new Criteria<Lineexplain>(Lineexplain.class);
		if (lineexplain.getLineId() != null) {
			criteria.eq("lineId", lineexplain.getLineId());
		}
		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}
	
}
