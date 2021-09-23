package com.zuipin.action.session;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.BaseAction;
import com.zuipin.service.SessionService;

public class SessionAction extends BaseAction implements ModelDriven<SessionForm> {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6640587539411764366L;
	private SessionForm			form				= new SessionForm();
	@Resource
	private SessionService		sessionService;
	
	public Result index() {
		return dispatch();
	}
	
	public Result search() {
		JSONArray json = sessionService.find(form);
		return json(json);
	}
	
	public Result test() {
		return text("test");
	}
	
	public Result sessionwindow() {
		return dispatch();
	}
	
	@Override
	public SessionForm getModel() {
		// TODO Auto-generated method stub
		return form;
	}
	
}
