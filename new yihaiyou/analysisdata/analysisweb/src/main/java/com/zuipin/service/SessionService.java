package com.zuipin.service;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Service;

import com.framework.hibernate.util.Page;
import com.zuipin.action.session.SessionForm;
import com.zuipin.pojo.LoginInOutHBase;
import com.zuipin.solr.IndexPathLock;
import com.zuipin.solr.query.SolrCriteria;
import com.zuipin.solr.query.SolrDao;
import com.zuipin.util.JsonUtils;
import com.zuipin.util.StringUtils;

@Service
public class SessionService {
	@Resource
	private SolrDao	solrDao;
	
	public JSONArray find(SessionForm form) {
		SolrCriteria solrCriteria = new SolrCriteria();
		solrCriteria.setFl(new String[] { LoginInOutHBase.SearchFields.ACCESSTIME, LoginInOutHBase.SearchFields.PV, LoginInOutHBase.SearchFields.USERID,
				LoginInOutHBase.SearchFields.COSTTIME, LoginInOutHBase.SearchFields.OUTTIME, LoginInOutHBase.SearchFields.REFERRER, LoginInOutHBase.SearchFields.REFERERDOMAIN,
				LoginInOutHBase.SearchFields.SESSIONID, LoginInOutHBase.SearchFields.BROWSER, LoginInOutHBase.SearchFields.SYSTEM });
		solrCriteria.setPage(new Page(form.getPage(), form.getRows()));
		if (solrCriteria.getISPARENT()) {
			solrCriteria.eq(LoginInOutHBase.SearchFields.ISPARENT, true);
		}
		if (StringUtils.isNotBlank(String.valueOf(form.getStartPv())) && StringUtils.isNotBlank(String.valueOf(form.getEndPv()))) {
			solrCriteria.between(LoginInOutHBase.SearchFields.PV, form.getStartPv(), form.getEndPv());
		} else {
			if (StringUtils.isNotBlank(String.valueOf(form.getStartPv()))) {
				solrCriteria.ge(LoginInOutHBase.SearchFields.PV, String.valueOf(form.getStartPv()));
			} else if (StringUtils.isNotBlank(String.valueOf(form.getStartPv()))) {
				solrCriteria.ge(LoginInOutHBase.SearchFields.PV, String.valueOf(form.getStartPv()));
			}
		}
		solrCriteria.between(LoginInOutHBase.SearchFields.ACCESSTIME, form.getStartTime(), form.getEndTime());
		if (StringUtils.isNotBlank(form.getUid())) {
			solrCriteria.eq(LoginInOutHBase.SearchFields.USERID, form.getUid());
		}
		if (StringUtils.isNotBlank(form.getReferer())) {
			solrCriteria.eq(LoginInOutHBase.SearchFields.REFERRER, form.getReferer());
		}
		if (StringUtils.isNotBlank(String.valueOf(form.getStartCost())) && StringUtils.isNotBlank(String.valueOf(form.getEndCost()))) {
			solrCriteria.between(LoginInOutHBase.SearchFields.COSTTIME, form.getStartCost(), form.getEndCost());
		} else {
			if (StringUtils.isNotBlank(String.valueOf(form.getStartPv()))) {
				solrCriteria.ge(LoginInOutHBase.SearchFields.COSTTIME, String.valueOf(form.getStartCost()));
			} else if (StringUtils.isNotBlank(String.valueOf(form.getStartCost()))) {
				solrCriteria.ge(LoginInOutHBase.SearchFields.COSTTIME, String.valueOf(form.getStartCost()));
			}
		}
		
		QueryResponse response = solrDao.find(solrCriteria, null, IndexPathLock.WebRequest.getServer());
		JSONArray array = new JSONArray();
		for (SolrDocument doc : response.getResults()) {
			array.add(JsonUtils.fromObject(doc, true));
		}
		return array;
	}
}
