package com.data.data.hmly.action.test;

import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;

public class TestAction extends JxmallAction {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 461451582191316251L;

	public Result test() {
		return text("test");
	}

	public Result test1() {
		return text("test1");
	}

}
