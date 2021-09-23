package com.data.data.hmly.action.ticketValidateInfo;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.scemanage.TicketValidateInfoService;
import com.data.data.hmly.service.scemanage.entity.TicketValidateInfo;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 线路
 * @author caiys
 * @date 2015年10月13日 下午4:12:38
 */
public class TicketValidateInfoAction extends FrameBaseAction implements ModelDriven<TicketValidateInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4395061833017783442L;
	
	private Integer			page				= 1;
	private Integer			rows				= 2;
	@Resource
	private TicketService ticketService;
	@Resource
	private TicketValidateInfoService ticketValidateInfoService;
	@Resource
	private MsgService msgService;
	private TicketValidateInfo ticketValidateInfo = new TicketValidateInfo();
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	
	/*
	 * 批量或单条删除验证信息
	 * 
	 * @return
	 */
	public Result delValidateInfos() {
		
		String idsStr = (String) getParameter("ids");
		if (StringUtils.isNotBlank(idsStr)) {
			String[] idsArr = idsStr.split(",");
			ticketValidateInfoService.delValidateInfos(idsArr);
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return jsonResult(map);
	}
	
	
	/*
	 * 验证信息列表
	 * 
	 * @return
	 */
	public Result list() throws ParseException {
		
		
		List<TicketValidateInfo> infos = new ArrayList<TicketValidateInfo>();
		
		Boolean isAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        Page pageInfo = new Page(page, rows);
        
        String createTimeStr = (String) getParameter("createTimeStr");
        String customerPhoneStr = (String) getParameter("customerPhone");
        
		infos = ticketValidateInfoService.getValidateInfolist(getCompanyUnit(), pageInfo, createTimeStr, customerPhoneStr, null, isAdmin, isSiteAdmin);
		
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{});
        return datagrid(infos, pageInfo.getTotalCount(), jsonConfig);
	}
	
	
	/*
	 * 保存门票录入信息 存
	 * 
	 * @return
	 */
	public Result saveValidateTicketInfo() {
		
		ticketValidateInfo.setCompanyUnit(getCompanyUnit());
		ticketValidateInfo.setUser(getLoginUser());
		ticketValidateInfoService.saveOrUpdate(ticketValidateInfo);
		
		ProductValidateCode productValidateCode = new ProductValidateCode();
		Ticket ticket = ticketService.findTicketById(ticketValidateInfo.getProductId());
		productValidateCode.setProduct(ticket);
		productValidateCode.setBuyerName(ticketValidateInfo.getCustomerName());
		productValidateCode.setBuyerMobile(ticketValidateInfo.getCustomerPhone());
		productValidateCode.setOrderCount(ticketValidateInfo.getAmount());

		msgService.addAndSendMsgCode(productValidateCode);
		
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
		JSONObject json = JSONObject.fromObject(ticketValidateInfo, jsonConfig);
		return json(json);
	}
	
	/**
	 * 通过关键字获取门票列表
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Result getProductList() throws UnsupportedEncodingException {

		Object p = getParameter("name_startsWith");
		String keyword = p.toString();
		keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
		if (keyword == null) {
			return text("没有keyword");
		}
		
		SysUser sysUser = getLoginUser();
		SysUnit companyUnit = getCompanyUnit();
		
		List<Ticket> list = new ArrayList<Ticket>(); 
		
		if (sysUser != null && companyUnit != null) {
			list = ticketService.getProductList(sysUser, companyUnit, keyword);
		}

		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
		JSONArray json = JSONArray.fromObject(list,jsonConfig);
		return json(json);
	}
	
	public Result manage() {
		return dispatch();
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public TicketValidateInfo getTicketValidateInfo() {
		return ticketValidateInfo;
	}

	public void setTicketValidateInfo(TicketValidateInfo ticketValidateInfo) {
		this.ticketValidateInfo = ticketValidateInfo;
	}

	@Override
	public TicketValidateInfo getModel() {
		return ticketValidateInfo;
	}

}
