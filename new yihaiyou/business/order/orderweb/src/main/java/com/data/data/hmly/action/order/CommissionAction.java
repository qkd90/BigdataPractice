package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.CommissionService;
import com.data.data.hmly.service.order.entity.Commission;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/11/12.
 */
public class CommissionAction extends FrameBaseAction{

	@Resource
	private CommissionService commissionService;

	private int page;
	private int rows;

	public Result index() {
		return dispatch();
	}

	public Result list() {
		User user = getLoginUser();
		Page page = getPage();
		List<Commission> commissions = commissionService.listByUser(user, page);
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("product", "user");
		return datagrid(commissions, page.getTotalCount(), jsonConfig);
	}

	public Page getPage() {
		return new Page(page, rows);
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
