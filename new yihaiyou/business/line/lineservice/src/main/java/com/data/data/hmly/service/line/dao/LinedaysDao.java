package com.data.data.hmly.service.line.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.line.entity.Linedays;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class LinedaysDao extends DataAccess<Linedays> {
	
	/**
	 * 删除线路天
	 * @author caiys
	 * @date 2015年10月26日 上午10:22:07
	 * @param lineId
	 */
	public void delByLineId(Long lineId) {
		String hql = " delete Linedays where lineId = ? ";
		updateByHQL(hql, lineId);
	}
	
	/**
	 * 查询线路天
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param linedays
	 * @return
	 */
	public List<Linedays> findLinedays(Linedays linedays) {
		Criteria<Linedays> criteria = new Criteria<Linedays>(Linedays.class);
		if (linedays.getLineexplain() != null && linedays.getLineexplain().getId() != null) {
			criteria.eq("lineexplain.id", linedays.getLineexplain().getId());
		}
		if (linedays.getLineId() != null) {
			criteria.eq("lineId", linedays.getLineId());
		}
		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}

	public void delNotExistIdList(List<Long> linedayIds, Long lineId) {
		List params = new ArrayList();
		String hql = "delete Linedays";
		if (!linedayIds.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			sb.append(" where id not in (");
			for (int i = 0; i < linedayIds.size(); i++) {
				if (i < linedayIds.size() - 1) {
					sb.append("?,");
					params.add(linedayIds.get(i));
				} else {
					sb.append("?");
					params.add(linedayIds.get(i));
				}
			}
			sb.append(")");
			if (lineId != null) {
				sb.append(" and lineId=?");
				params.add(lineId);
			}
			updateByHQL(hql + sb.toString(), params.toArray());
		}

	}
}
