package com.data.data.service.optimize.process;

import java.util.List;

import org.apache.log4j.Logger;

import com.data.data.service.SolrService;
import com.data.data.service.pojo.SolrDis;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.zuipin.util.SpringContextHolder;

public class DisUtils {

	private final static SolrService	solrService	= SpringContextHolder.getBean("solrService");

	private final static Logger			log			= Logger.getLogger(DisUtils.class);

	public static SolrDis getPointToPointDis(Point sPoint, Point ePoint) {
		// SolrService solrService = SpringContextHolder.getBean("solrService");
		SolrDis dis = solrService.getPoint2PointDis(sPoint, ePoint);
		return dis != null ? dis : SolrDis.build();
	}

	public static SolrDis findNearPointScenic(List<Long> ids, Point end, int left) {
		// SolrService solrService = SpringContextHolder.getBean("solrService");
		SolrDis dis = solrService.findAreaTopScenic(ids, end, left);

		return dis;
	}

}
