package com.data.data.hmly.action.mall;

import com.data.data.hmly.action.mall.vo.MallConfig;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;

/**
 * Created by guoshijie on 2015/10/23.
 */
public class IndexWebAction extends MallAction {

	public Result index() {
		getIndexPageResource();
		return dispatch();
	}

	private void getIndexPageResource() {
		MallConfig mallConfig = getSessionAttribute("mallConfig");
		setAttribute(MallConstants.INDEX_TOP_BANNER_KEY, FileUtil.loadHTML(MallConstants.INDEX_TOP_BANNER + mallConfig.getSiteId()));
		setAttribute(MallConstants.INDEX_TICKET_KEY, FileUtil.loadHTML(MallConstants.INDEX_TICKET + mallConfig.getSiteId()));
		setAttribute(MallConstants.INDEX_LINE_KEY, FileUtil.loadHTML(MallConstants.INDEX_LINE + mallConfig.getSiteId()));
		setAttribute(MallConstants.INDEX_SUPPLIER_KEY, FileUtil.loadHTML(MallConstants.INDEX_SUPPLIER + mallConfig.getSiteId()));
	}
}
