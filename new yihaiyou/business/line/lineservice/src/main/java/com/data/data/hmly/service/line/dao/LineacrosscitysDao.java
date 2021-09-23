package com.data.data.hmly.service.line.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.line.entity.Lineacrosscitys;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class LineacrosscitysDao extends DataAccess<Lineacrosscitys> {
	
	/**
	 * 删除途径城市
	 * @author caiys
	 * @date 2015年10月26日 上午10:22:07
	 * @param lineId
	 */
	public void delByLineId(Long lineId) {
		String hql = " delete Lineacrosscitys where line.id = ? ";
		updateByHQL(hql, lineId);
	}
	
	/**
	 * 途径城市列表
	 * @author caiys
	 * @date 2015年10月21日 下午2:21:01
	 * @param line
	 * @param page
	 * @return
	 */
	public List<Lineacrosscitys> findLineacrosscitysList(Lineacrosscitys lineacrosscitys) {
		Criteria<Lineacrosscitys> criteria = new Criteria<Lineacrosscitys>(Lineacrosscitys.class);
		// 线路标识
		if (lineacrosscitys.getLine() != null && lineacrosscitys.getLine().getId() != null) {
			criteria.eq("line.id", lineacrosscitys.getLine().getId());
		}
//		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}
}
