package com.data.data.hmly.service.ctripticket.dao;

import com.data.data.hmly.service.ctripticket.entity.CtripScenicSpot;
import com.data.data.hmly.service.ctripticket.entity.RowStatus;
import com.framework.hibernate.DataAccess;

import java.util.ArrayList;
import java.util.List;

//@Repository
public class CtripScenicSpotDao extends DataAccess<CtripScenicSpot> {
	
	/**
	 * 批量更新行状态
	 * @author caiys
	 * @date 2015年12月3日 上午11:40:53
	 * @param rowStatus
	 */
	public void updateRowStatus(RowStatus rowStatus, Integer districtID) {
		List<Object> params = new ArrayList<Object>();
		String hql = "update CtripScenicSpot set rowStatus = ?, updateTime = now()";
		params.add(rowStatus);
		if (districtID != null && districtID > 0) {
			hql = hql + " where districtID = ?";
			params.add(districtID);
		}
		updateByHQL(hql, params.toArray());
	}

}
