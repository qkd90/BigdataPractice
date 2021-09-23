package com.data.data.hmly.action.mall.util;

import com.data.data.hmly.action.mall.vo.MallConfig;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.mall.MallService;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/10/21.
 */
@Component
public class MallUtil {

	@Resource
	private        MallService       mallService;
	@Resource
	private        PropertiesManager propertiesManager;
	private static MallUtil          mallUtil;

	private static SysSite sysSite;

	@PostConstruct
	public void init() {
		mallUtil = this;
		mallUtil.mallService = this.mallService;
		mallUtil.propertiesManager = this.propertiesManager;

	}

	public static void changeSite(MallConfig mallConfig) {

//		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
//		applicationContext.getBean("beanId");

		SysSite sysSite = mallUtil.mallService.getSysSiteByUrl(mallConfig.getSite());
		if (sysSite == null) {
			mallConfig.setSiteId(1l);
			mallConfig.setTitle(mallUtil.propertiesManager.getString("mall.title"));
			mallConfig.setSite(mallUtil.propertiesManager.getString("mall.site"));
			mallConfig.setLogoPath(mallUtil.propertiesManager.getString("mall.logo"));
		} else {
			setSysSite(sysSite);
			mallConfig.setSiteId(sysSite.getId());
			mallConfig.setSysSite(sysSite);
			mallConfig.setTitle(sysSite.getSitename());
			mallConfig.setSite(sysSite.getSiteurl());
			//todo: 修改logo
			mallConfig.setLogoPath(mallUtil.propertiesManager.getString("mall.logo"));
		}
		mallConfig.setImguriPreffix(mallUtil.propertiesManager.getString("mall.imguri.preffix"));
	}

	public static MallConfig getConfig(String host) {
		MallConfig mallConfig = new MallConfig();
		SysSite sysSite = mallUtil.mallService.getSysSiteByUrl(host);
		if (sysSite == null) {
			sysSite = mallUtil.mallService.getDefaultSite();
		}
		if (sysSite == null) {
			mallConfig.setTitle(mallUtil.propertiesManager.getString("mall.title"));
			mallConfig.setSite(mallUtil.propertiesManager.getString("mall.site"));
			mallConfig.setLogoPath(mallUtil.propertiesManager.getString("mall.logo"));
		} else {
			setSysSite(sysSite);
			mallConfig.setSiteId(sysSite.getId());
			mallConfig.setSysSite(sysSite);
			mallConfig.setTitle(sysSite.getSitename());
			mallConfig.setSite(sysSite.getSiteurl());
			mallConfig.setLogoPath(mallUtil.propertiesManager.getString("mall.logo"));
		}
		mallConfig.setResourcePath(mallUtil.propertiesManager.getString("mall.resource"));
		mallConfig.setResourceVersion(mallUtil.propertiesManager.getString("mall.version"));
		mallConfig.setImguriPreffix(mallUtil.propertiesManager.getString("mall.imguri.preffix"));
		return mallConfig;
	}

	private static void setSysSite(SysSite site) {
		sysSite = site;
	}

	public static SysSite getSysSite() {
		return sysSite;
	}
}
