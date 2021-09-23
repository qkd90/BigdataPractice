package com.data.data.hmly.service.build;

import com.data.data.hmly.service.build.builder.CommonBuilder;
import com.data.data.hmly.service.build.builder.IndexBuilder;
import com.data.data.hmly.service.build.builder.LineBuilder;
import com.data.data.hmly.service.build.builder.MobileIndexBuilder;
import com.data.data.hmly.service.build.builder.SupplierBuilder;
import com.data.data.hmly.service.build.builder.TicketBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/10/20.
 */
@Service
public class MallBuildService {

	@Resource
	private CommonBuilder   commonBuilder;
	@Resource
	private IndexBuilder    indexBuilder;
	@Resource
	private LineBuilder     lineBuilder;
	@Resource
	private TicketBuilder   ticketBuilder;
	@Resource
	private SupplierBuilder supplierBuilder;
	@Resource
	private MobileIndexBuilder mobileIndexBuilder;


	public void buildAll() {

		buildCommon();
		buildIndex();
		buildLine();
		buildTicket();
		buildSupplier();
	}

	public void buildCommon() {
		commonBuilder.build();
	}

	public void buildLine() {
		lineBuilder.build();
		//build line static page shown in index page
	}

	public void buildLine(Long id) {
		lineBuilder.buildOne(id);
	}

	public void buildSupplier() {
		supplierBuilder.build();
		//build supplier static page shown in index page
	}
	public void buildSupplier(Long id) {
		supplierBuilder.buildOne(id);
		//build supplier static page shown in index page
	}

	public void buildTicket() {
		ticketBuilder.build();
	}

	public void buildTicket(Long id) {
		ticketBuilder.buildOne(id);
	}

	public void buildIndex() {
		indexBuilder.build();
	}

	public void buildMobileIndex() {
		mobileIndexBuilder.build();
	}

	public void buildTest() {
		System.out.println("build once");
	}


}
