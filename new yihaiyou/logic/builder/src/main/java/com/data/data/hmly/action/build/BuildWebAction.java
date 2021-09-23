package com.data.data.hmly.action.build;

import com.data.data.hmly.service.build.MallBuildService;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/10/22.
 */
public class BuildWebAction extends JxmallAction {

	@Resource
	private MallBuildService buildService;

	private Long id;

	public Result buildAll() {
		buildService.buildAll();
		return text("success");
	}

	public Result buildIndex() {
		buildService.buildIndex();
		return text("success");
	}

	public Result buildCommon() {
		buildService.buildCommon();
		return text("success");
	}

	public Result buildLine() {
		buildService.buildLine();
		return text("success");
	}

	public Result buildTicket() {
		buildService.buildTicket();
		return text("success");
	}

	public Result buildSupplier() {
		buildService.buildSupplier();
		return text("success");
	}

	public Result buildOneLine() {
		buildService.buildLine(id);
		return text("success");
	}

	public Result buildOneTicket() {
		buildService.buildTicket(id);
		return text("success");
	}

	public Result buildOneSupplier() {
		buildService.buildSupplier(id);
		return text("success");
	}

	public Result buildMobileIndex() {
		buildService.buildMobileIndex();
		return text("success");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
