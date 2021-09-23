package com.solr.service.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.zuipin.solr.IndexPathLock;

public abstract class MyBaseQuery implements Runnable, Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6260380464341696401L;
	protected StringBuilder		q;
	protected StringBuilder		fq;
	protected String			indexPath;
	protected CountDownLatch	downLatch;
	private final static Log	log					= LogFactory.getLog(MyBaseQuery.class);
	private StopWatch			stopWatch			= new StopWatch();
	
	public MyBaseQuery(StringBuilder[] query2) {
		this.q = query2[0];
		this.fq = query2[1];
	}
	
	public Boolean hasConditions() {
		return (q.length() > 0 || fq.length() > 0) && !q.toString().contains("*:*") && !q.toString().equals("ISPARENT: false");
	}
	
	@Override
	public void run() {
		try {
			stopWatch.start();
			SolrQuery query = createSolrQuery();
			query.setQuery(q.toString());
			query.setFilterQueries(fq.toString());
			query.setFields(getCollector());
			query.setStart(0);
			query.setRows(Integer.MAX_VALUE);
			
			SolrServer server = IndexPathLock.findIndexPathLock(indexPath).getServer();
			QueryResponse response = server.query(query, METHOD.POST);
			stopWatch.stop();
			log.info(this.getClass() + " Cost " + stopWatch);
			fetchObject(response);
		} catch (Exception e) {
			log.error(this.getClass() + " : " + e.getMessage(), e);
		} finally {
			downLatch.countDown();
		}
	}
	
	/**
	 * @return
	 */
	protected SolrQuery createSolrQuery() {
		return new SolrQuery();
	}
	
	public abstract void fetchObject(QueryResponse response);
	
	public abstract String[] getCollector();
	
	/**
	 * @param list1must
	 *            be sorted before
	 * @param list2
	 *            must be sorted before
	 * @return
	 */
	public List<Long> mergeList(List<Long> list1, List<Long> list2) {
		List<Long> list = new ArrayList<Long>();
		int size1 = list1.size();
		int size2 = list2.size();
		int i = 0;
		int j = 0;
		
		while (i < size1) {
			while (j < size2) {
				Long long1 = list1.get(i);
				Long long2 = list2.get(j);
				if (long1.longValue() < long2.longValue()) {
					i++;
					break;
				} else if (long1.longValue() > long2.longValue()) {
					j++;
					continue;
				} else {
					list.add(long1);
					i++;
					j++;
					break;
				}
				
			}
			if (i == size1 || j == size2) {
				break;
			}
		}
		log.debug(String.format("merge %d to %d hit size %d", size1, size2, list.size()));
		return list;
	}
	
	/**
	 * @param list1must
	 *            be sorted before
	 * @param list2
	 *            must be sorted before
	 * @return
	 */
	public List<Long> mergeList(Set<Long> set, List<Long> list2) {
		List<Long> list = new ArrayList<Long>();
		int size1 = set.size();
		int size2 = list2.size();
		int i = 0;
		int j = 0;
		Object[] objs = set.toArray();
		while (i < size1) {
			while (j < size2) {
				Long long1 = Long.parseLong(String.valueOf(objs[i]));
				Long long2 = list2.get(j);
				if (long1.longValue() < long2.longValue()) {
					i++;
					break;
				} else if (long1.longValue() > long2.longValue()) {
					j++;
					continue;
				} else {
					list.add(long1);
					i++;
					j++;
					break;
				}
				
			}
			if (i == size1 || j == size2) {
				break;
			}
		}
		log.debug(String.format("merge %d to %d hit size %d", size1, size2, list.size()));
		return list;
	}
	
	public StringBuilder getQ() {
		return q;
	}
	
	public void setQ(StringBuilder q) {
		this.q = q;
	}
	
	public StringBuilder getFq() {
		return fq;
	}
	
	public void setFq(StringBuilder fq) {
		this.fq = fq;
	}
	
	public String getIndexPath() {
		return indexPath;
	}
	
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
	public CountDownLatch getDownLatch() {
		return downLatch;
	}
	
	public void setDownLatch(CountDownLatch downLatch) {
		this.downLatch = downLatch;
	}
	
}
