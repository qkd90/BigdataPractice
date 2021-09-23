package com.data.data.hmly.service.ctripuser.dao;

import com.data.data.hmly.service.ctripuser.entity.CtripUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CtripUserDao extends DataAccess<CtripUser> {

	public CtripUser findUniqueCtripUser(String allianceId, String sid, String uidKey) {
		Criteria<CtripUser> criteria = new Criteria<CtripUser>(CtripUser.class);
		criteria.eq("allianceId", allianceId);
		criteria.eq("sid", sid);
		criteria.eq("uidKey", uidKey);
		List<CtripUser> ctripUsers = findByCriteria(criteria);
		if (ctripUsers.size() > 0) {
			return ctripUsers.get(0);
		}
		return null;
	}
}
