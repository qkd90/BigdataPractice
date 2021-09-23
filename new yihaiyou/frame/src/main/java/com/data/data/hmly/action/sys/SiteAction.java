package com.data.data.hmly.action.sys;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.BusinessModel;
import com.data.data.hmly.service.entity.BusinessScope;
import com.data.data.hmly.service.entity.BusinessType;
import com.data.data.hmly.service.entity.SupplierType;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.TbArea;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;

public class SiteAction extends FrameBaseAction implements ModelDriven<SysUnit> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -932588605955594916L;

	@Resource
	private SysSiteService sysSiteService;
	private SysUnit sysUnit = new SysUnit();
	@Resource
	private SysUnitService sUnitService;
	@Resource
	private TbAreaService tAreaService;

	@AjaxCheck
	public Result addNewSite() {
		sysSiteService.addNewSite(sysUnit);
		JSONObject o = new JSONObject();
		o.put("success", true);
		return json(o);
	}
	
	
	@AjaxCheck
	public Result editSite() {
		JSONObject o = new JSONObject();
		String uid = (String) getParameter("uid");
		String sid = (String) getParameter("sid");
		String sitename = (String) getParameter("sitename");
		String name = (String) getParameter("name");
		String siteurl = (String) getParameter("siteurl");
		String areaid = (String) getParameter("areaid");
		String address = (String) getParameter("address");
		String supplierType = (String) getParameter("supplierType");
		String businessScope = (String) getParameter("businessScope");
		String businessType = (String) getParameter("businessType");
		String telphone = (String) getParameter("telphone");
		String mainBody = (String) getParameter("mainBody");
		String businessModel = (String) getParameter("businessModel");
		String partnerChannel = (String) getParameter("partnerChannel");
		String partnerUrl = (String) getParameter("partnerUrl");
		String partnerAdvantage = (String) getParameter("partnerAdvantage");
		String fax = (String) getParameter("fax");
		
		SysUnit unit = new SysUnit();
		SysUnitDetail unitDetail = null;
		SysSite site = null;
		if(!StringUtils.isEmpty(uid)){
			unit = sUnitService.findUnitById(Long.parseLong(uid));
			unitDetail = unit.getSysUnitDetail();
			site = unit.getSysSite();
			//site
			if(!StringUtils.isEmpty(sitename)){
				site.setSitename(sitename);
			}
			if(!StringUtils.isEmpty(siteurl)){
				site.setSiteurl(siteurl);
			}
			
			if(!StringUtils.isEmpty(areaid)){
				TbArea area = tAreaService.getArea(Long.parseLong(areaid));
				site.setArea(area);
			}
			
			//unit
			if(!StringUtils.isEmpty(name)){
				unit.setName(name);
			}
			if(!StringUtils.isEmpty(address)){
				unit.setAddress(address);
			}
			//unitDetail
			if(!StringUtils.isEmpty(supplierType)){
				unitDetail.setSupplierType(SupplierType.valueOf(supplierType));
			}
			if(!StringUtils.isEmpty(businessScope)){
				unitDetail.setBusinessScope(BusinessScope.valueOf(businessScope));
			}
			if(!StringUtils.isEmpty(businessType)){
				unitDetail.setBusinessType(BusinessType.valueOf(businessType));
			}
			if(!StringUtils.isEmpty(telphone)){
				unitDetail.setTelphone(telphone);
			}
			if(!StringUtils.isEmpty(fax)){
				unitDetail.setFax(fax);
			}
			if(!StringUtils.isEmpty(mainBody)){
				unitDetail.setMainBody(mainBody);
			}
			if(!StringUtils.isEmpty(partnerChannel)){
				unitDetail.setPartnerChannel(partnerChannel);
			}
			if(!StringUtils.isEmpty(businessModel)){
				unitDetail.setBusinessModel(BusinessModel.valueOf(businessModel));
			}
			if(!StringUtils.isEmpty(partnerUrl)){
				unitDetail.setPartnerUrl(partnerUrl);
			}
			if(!StringUtils.isEmpty(partnerAdvantage)){
				unitDetail.setPartnerAdvantage(partnerAdvantage);
			}
			
			sUnitService.update(site,unit,unitDetail);
			
			
			
			o.put("success", true);
			
		}else{
			o.put("success", false);
		}
		
		return json(o);
	}
	

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	@Override
	public SysUnit getModel() {
		// TODO Auto-generated method stub
		return sysUnit;
	}

}
