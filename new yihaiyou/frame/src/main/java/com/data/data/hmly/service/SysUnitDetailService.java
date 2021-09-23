package com.data.data.hmly.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.dao.SysUnitDetailDao;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;

@Service
public class SysUnitDetailService {
	
	@Resource
	private SysUnitDetailDao detailDao;
	@Resource
	private SysUnitDao sysUnitDao;

	public void insert(SysUnitDetail sysUnitDetail) {
		detailDao.save(sysUnitDetail);
	}

	public void update(SysUnitDetail sysUnitDetail) {
		detailDao.update(sysUnitDetail);
	}

	public List<SysUnitDetail> getSceAccList(String sceId, String cityIdStr, Page pageInfo,SysSite sysSite	) {
		
		Criteria<SysUnitDetail> criteria = new Criteria<SysUnitDetail>(SysUnitDetail.class);
		
		if(!StringUtils.isEmpty(sceId)){
			criteria.eq("scenicid",Long.parseLong(sceId));
		}else{
			criteria.isNotNull("scenicid");
		}
		if(sysSite != null){
			
			Criteria<SysUnit> cSysUnit = new Criteria<SysUnit>(SysUnit.class);
			
			if(!StringUtils.isEmpty(cityIdStr)){
				cSysUnit.eq("area.id", Long.parseLong(cityIdStr));
			}
			cSysUnit.eq("sysSite.id", sysSite.getId());
			
			List<SysUnit> sysUnits = sysUnitDao.findByCriteria(cSysUnit);
			
			Long[] unitIds = new Long[sysUnits.size()];  
			
			for(int i=0;i<sysUnits.size();i++){
				unitIds[i] = sysUnits.get(i).getId();
			}
			
			criteria.in("sysUnit.id", unitIds);
			
		}else{
			criteria.isNotNull("sysUnit.id");
		}
		
		
		
		return detailDao.findByCriteria(criteria, pageInfo);
	}

	public SysUnitDetail findDetailByUid(Long id) {
		
		Criteria<SysUnitDetail> criteria = new Criteria<SysUnitDetail>(SysUnitDetail.class);
		
		criteria.eq("sysUnit.id", id);
		
//		String hql = "from SysUnitDetail where sysUnit.id = " +id;
		SysUnitDetail detail = detailDao.findUniqueByCriteria(criteria);;
//		return detailDao.findUniqueByCriteria(criteria);
		return detail;
	}

}
