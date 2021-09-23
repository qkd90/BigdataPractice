package com.data.data.hmly.service.scemanage;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.scemanage.dao.TicketValidateInfoDao;
import com.data.data.hmly.service.scemanage.entity.TicketValidateInfo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.zuipin.util.DateUtils;

@Service
public class TicketValidateInfoService {
	
	@Resource
	private TicketValidateInfoDao validateInfoDao;
	
	public void saveOrUpdate(TicketValidateInfo ticketValidateInfo) {
		
		if (ticketValidateInfo.getId() != null) {
			ticketValidateInfo.setModifyTime(new Date());
			validateInfoDao.update(ticketValidateInfo);
		} else {
			ticketValidateInfo.setCreateTime(new Date());
			ticketValidateInfo.setModifyTime(new Date());
			validateInfoDao.save(ticketValidateInfo);
		}
	}

	public List<TicketValidateInfo> getValidateInfolist(SysUnit companyUnit,
			Page pageInfo, String createTimeStr, String customerPhoneStr, Boolean isCompanyAdmin,
			Boolean isAdmin, Boolean isSiteAdmin) throws ParseException {
		
			Criteria<TicketValidateInfo> criteria = new Criteria<TicketValidateInfo>(TicketValidateInfo.class);
	        DetachedCriteria userCriteria = criteria.createCriteria("user", "user", JoinType.INNER_JOIN);
	        if (!isAdmin){
	            if (!isSiteAdmin) {
	                criteria.eq("companyUnit", companyUnit);
	            } else {
	                DetachedCriteria unitCriteria = criteria.createCriteria("companyUnit", "companyUnit", JoinType.INNER_JOIN);
	                DetachedCriteria siteCriteria = unitCriteria.createCriteria("sysSite", "sysSite", JoinType.INNER_JOIN);
	                siteCriteria.add(Restrictions.eq("id", companyUnit.getSysSite().getId()));
	            }
	
	        }
	
	        
	        if (StringUtils.isNotBlank(customerPhoneStr)) {
	            criteria.eq("customerPhone", customerPhoneStr);
	        }
	        if (StringUtils.isNotBlank(createTimeStr)) {
				Date startDate = DateUtils.getDate(createTimeStr, "yyyy-MM-dd");
				Date endDate = DateUtils.add(startDate, Calendar.DAY_OF_MONTH, 1);
	        	
				criteria.ge("createTime", startDate);
				criteria.lt("createTime", endDate);
			}
	        return validateInfoDao.findByCriteria(criteria, pageInfo);
			
		
	}

	public void delValidateInfos(String[] idsArr) {
		Criteria<TicketValidateInfo> criteria = new Criteria<TicketValidateInfo>(TicketValidateInfo.class);
		
		for (String idstr : idsArr) {
			validateInfoDao.delete(validateInfoDao.load(Long.parseLong(idstr)));
		}
		
	}
	
}
