package com.data.data.hmly.service.mall;

import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.entity.SysSite;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/20.
 */
@Service
public class MallService {

	@Resource
	private SysSiteService sysSiteService;

	public List getLineList() {

		return new ArrayList();
	}

	public List getScencList() {

		return new ArrayList();
	}

	public List getHotelList() {

		return new ArrayList();
	}

	public List getRestaurantList() {

		return new ArrayList();
	}

	public List getSupplierList() {

		return new ArrayList();
	}

	public SysSite getSysSiteByUrl(String url) {
		List<SysSite> sysSites = sysSiteService.findByUrl(url);
		if (!sysSites.isEmpty()) {
			return sysSites.get(0);
		}
		return null;
	}

	public SysSite getDefaultSite() {
		return sysSiteService.getDefaultSite();
	}
}
