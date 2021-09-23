package com.data.data.hmly.service.build.util;

import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/11/4.
 */
@Service
public class MallSettings {

	@Resource
	private PropertiesManager propertiesManager;

	public String getPicPath() {
		return propertiesManager.getString("pic.path", "");
	}

	public String getSetting(String name) {
		return propertiesManager.getString(name);
	}
}
