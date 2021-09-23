package com.data.data.service;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import com.data.data.service.pojo.SolrDis;
import com.data.data.solr.MulticoreSolrTemplate;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;

@Service
public class SolrService {

	@Autowired
	private MulticoreSolrTemplate	solrTemplate;

	private final static StopWatch	stopWatch	= new StopWatch();

	private final static Logger		log			= Logger.getLogger(SolrService.class);

	public SolrDis getPoint2PointDis(Point from, Point end) {
		Criteria criteria = new Criteria("f_to");
		criteria.is(String.format("%d-%d", from.scenicInfoId, end.scenicInfoId));
		SimpleQuery query = new SimpleQuery();
		query.addCriteria(criteria);
		stopWatch.reset();
		stopWatch.start();
		// ScoredPage<Scenic> page = solrTemplate.queryForPage(query,
		// Scenic.class);
		SolrDis dis = solrTemplate.queryForObject(query, SolrDis.class);
		stopWatch.stop();
		log.info(String.format("%s - %s Cost %s", from.getName(), end.getName(), stopWatch));
		return dis;
	}

	public SolrDis findAreaTopScenic(List<Long> ids, Point end, int left) {
		// TODO Auto-generated method stub
		SimpleQuery query = new SimpleQuery();
		Criteria areaC = new Criteria("areaId");
		areaC.is(end.getArea().getId());
		query.addCriteria(areaC);
		Criteria eidC = new Criteria("e_id");
		eidC.not().is(ids);
		query.addCriteria(eidC);
		Sort sort = new Sort(Direction.ASC, "ranking");
		query.addSort(sort);
		stopWatch.reset();
		stopWatch.start();
		SolrDis dis = solrTemplate.queryForObject(query, SolrDis.class);
		stopWatch.stop();
		log.info(String.format("Area in %d not exists %s - Cost %s", end.getArea().getId(), ids, stopWatch));
		return dis;
	}

}
