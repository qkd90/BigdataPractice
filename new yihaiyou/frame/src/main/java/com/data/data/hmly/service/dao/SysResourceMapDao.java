package com.data.data.hmly.service.dao;

import com.data.data.hmly.service.entity.SysResourceMap;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/10/27.
 */

@Repository
public class SysResourceMapDao extends DataAccess<SysResourceMap> {

    public SysResourceMap get(long id){
        Criteria<SysResourceMap> criteria = new Criteria<SysResourceMap>(SysResourceMap.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    public List<SysResourceMap> getList() {
	    return getList(null);
    }

	public List<SysResourceMap> getList(SysResourceMap sysResourceMap) {
		Criteria<SysResourceMap> criteria = createCriteria(sysResourceMap);

		return findByCriteria(criteria);
	}

	public Criteria<SysResourceMap> createCriteria(SysResourceMap sysResourceMap) {
		Criteria<SysResourceMap> criteria = new Criteria<SysResourceMap>(SysResourceMap.class);
		if (sysResourceMap == null) {
			return criteria;
		}
		if (!StringUtils.isBlank(sysResourceMap.getName())) {
			criteria.eq("name", sysResourceMap.getName());
		}
		if (sysResourceMap.getUser() != null) {
			criteria.eq("user.id", sysResourceMap.getUser().getId());
		}
		if (sysResourceMap.getResourceType()!= null) {
			criteria.eq("resourceType", sysResourceMap.getResourceType());
		}
		if (StringUtils.isNotBlank(sysResourceMap.getDescription())) {
			criteria.eq("description", sysResourceMap.getDescription());
		}
		return criteria;
	}


}
