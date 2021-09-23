package com.data.data.hmly.service.line.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.line.entity.Lineplaytitle;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class LineplaytitleDao extends DataAccess<Lineplaytitle> {
	
	/**
	 * 删除线路标签
	 * @author caiys
	 * @date 2015年10月26日 上午10:22:07
	 * @param lineId
	 */
	public void delByLineId(Long lineId) {
		String hql = " delete Lineplaytitle where lineId = ? ";
		updateByHQL(hql, lineId);
	}
	
	/**
	 * 查询线路主题
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param lineplaytitle
	 * @return
	 */
	public List<Lineplaytitle> findLineplaytitle(Lineplaytitle lineplaytitle) {
		Criteria<Lineplaytitle> criteria = new Criteria<Lineplaytitle>(Lineplaytitle.class);
		if (lineplaytitle.getLineId() != null) {
			criteria.eq("lineId", lineplaytitle.getLineId());
		}
		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}
	
}
