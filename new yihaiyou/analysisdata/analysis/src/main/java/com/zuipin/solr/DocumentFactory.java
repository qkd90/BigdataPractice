package com.zuipin.solr;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.spark.service.hbase.pojo.HBaseEntity;
import com.spark.service.hbase.pojo.WebRequest;
import com.zuipin.pojo.LoginInOutHBase;
import com.zuipin.solr.comparators.BaseCompare;

public enum DocumentFactory implements Serializable {
	instance;
	private ExecutorService		executorService	= Executors.newCachedThreadPool();
	private final static Log	log				= LogFactory.getLog(DocumentFactory.class);
	private final static String	AND				= " AND ";
	private final static String	ALL				= "*:*";
	
	/**
	 * 添加索引
	 * 
	 * @param entity
	 * @throws IOException
	 */
	public void appendIndex(HBaseEntity entity) {
		if (entity == null)
			return;
		SolrServer server = null;
		try {
			server = IndexPathLock.findIndexPathLock(entity.getIndexPath()).getServer();
			server.add(entity.getSolrDoc());
			server.commit();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				server.rollback();
			} catch (Exception e1) {
				log.error(e.getMessage(), e1);
			}
		}
	}
	
	/**
	 * 添加索引
	 * 
	 * @param entity
	 * @throws IOException
	 */
	public void appendDoc(SolrInputDocument doc, String path) {
		if (doc == null || doc.isEmpty())
			return;
		SolrServer server = null;
		try {
			server = IndexPathLock.findIndexPathLock(path).getServer();
			server.add(doc);
			server.commit();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				server.rollback();
			} catch (Exception e1) {
				log.error(e.getMessage(), e1);
			}
		}
	}
	
	/**
	 * 添加索引
	 * 
	 * @param entity
	 * @throws IOException
	 */
	public void appendDocuments(List<SolrInputDocument> docs, String indexPath) {
		if (docs == null || docs.isEmpty())
			return;
		SolrServer server = null;
		try {
			server = IndexPathLock.findIndexPathLock(indexPath).getServer();
			server.add(docs);
			server.commit();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				server.rollback();
			} catch (Exception e1) {
				log.error(e.getMessage(), e1);
			}
		}
	}
	
	/**
	 * 添加索引
	 * 
	 * @param entitys
	 * @throws IOException
	 */
	public void appendIndex(List<? extends HBaseEntity> entitys) {
		if (entitys == null || entitys.isEmpty())
			return;
		SolrServer server = null;
		try {
			String indexPath = entitys.get(0).getIndexPath();
			server = IndexPathLock.findIndexPathLock(indexPath).getServer();
			List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (HBaseEntity entity : entitys) {
				docs.add(entity.getSolrDoc());
			}
			server.add(docs);
			server.commit();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				server.rollback();
			} catch (Exception e1) {
				log.error(e.getMessage(), e1);
			}
		}
	}
	
	/**
	 * 重新创建生成索引
	 * 
	 * @param entity
	 * @throws IOException
	 */
	public void buildIndex(HBaseEntity entity) throws IOException {
		if (entity == null)
			return;
		
		FSDirectory dir = null;
		IndexWriter writer = null;
		try {
			entity.getWriteLock().lock();
			dir = FSDirectory.open(new File(entity.getIndexPath()));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			writer = new IndexWriter(dir, iwc);
			writer.addDocument(entity.getDoc(), analyzer);
			writer.maybeMerge();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			entity.getWriteLock().unlock();
			try {
				if (writer != null) {
					writer.close();
				}
				if (dir != null) {
					dir.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 重新创建生成索引
	 * 
	 * @param entitys
	 * @throws IOException
	 */
	public synchronized void buildIndex(Collection<? extends HBaseEntity> entitys) {
		if (entitys.isEmpty()) {
			return;
		}
		FSDirectory dir = null;
		IndexWriter writer = null;
		WriteLock writeLock = null;
		try {
			String path = null;
			HBaseEntity baseEntity = (HBaseEntity) entitys.toArray()[0];
			writeLock = baseEntity.getWriteLock();
			writeLock.lock();
			path = baseEntity.getIndexPath();
			dir = FSDirectory.open(new File(path));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			writer = new IndexWriter(dir, iwc);
			for (HBaseEntity entity : entitys) {
				writer.addDocument(entity.getDoc(), analyzer);
			}
			writer.maybeMerge();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				writeLock.unlock();
				if (writer != null) {
					writer.close();
				}
				if (dir != null) {
					dir.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	private StringBuilder[] createParentQuery(Map<String, List<BaseCompare>> map) {
		// TODO Auto-generated method stub
		StringBuilder qStr = new StringBuilder();
		StringBuilder fqStr = new StringBuilder("{!parent which='ISPARENT:true'}");
		StringBuilder qAppendStr = new StringBuilder();
		StringBuilder fqAppendStr = new StringBuilder();
		
		for (Entry<String, List<BaseCompare>> entry : map.entrySet()) {
			List<BaseCompare> compares = entry.getValue();
			for (BaseCompare baseCompare : compares) {
				try {
					baseCompare.createQuery();
					StringBuilder q = baseCompare.getQ();
					StringBuilder fq = baseCompare.getFq();
					log.info(q);
					log.info(fq);
					if (baseCompare.isParent) {
						if (q.length() > 0) {
							if (qAppendStr.length() == 0) {
								qAppendStr.append(q);
							} else {
								qAppendStr.append(AND).append(q);
							}
						}
						if (fq.length() > 0) {
							if (qAppendStr.length() == 0) {
								qAppendStr.append(fq);
							} else {
								qAppendStr.append(AND).append(fq);
							}
						}
					} else {
						if (q.length() > 0) {
							if (fqAppendStr.length() == 0) {
								fqAppendStr.append(q);
							} else {
								fqAppendStr.append(AND).append(q);
							}
						}
						if (fq.length() > 0) {
							if (fqAppendStr.length() == 0) {
								fqAppendStr.append(fq);
							} else {
								fqAppendStr.append(AND).append(fq);
							}
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		if (qAppendStr.length() == 0) {
			qStr.append(ALL);
		} else {
			qStr.append(qAppendStr);
		}
		fqStr.append(fqAppendStr);
		return new StringBuilder[] { qStr, fqStr };
	}
	
	private StringBuilder[] createChildrenQuery(Map<String, List<BaseCompare>> map) {
		// TODO Auto-generated method stub
		StringBuilder qStr = new StringBuilder("ISPARENT: false");
		StringBuilder fqStr = new StringBuilder();
		
		for (Entry<String, List<BaseCompare>> entry : map.entrySet()) {
			List<BaseCompare> compares = entry.getValue();
			for (BaseCompare baseCompare : compares) {
				try {
					baseCompare.createQuery();
					StringBuilder q = baseCompare.getQ();
					StringBuilder fq = baseCompare.getFq();
					log.info(q);
					log.info(fq);
					if (q.length() > 0) {
						if (qStr.length() == 0) {
							qStr.append(q);
						} else {
							qStr.append(AND).append(q);
						}
					}
					if (fq.length() > 0) {
						if (fqStr.length() == 0) {
							fqStr.append(fq);
						} else {
							fqStr.append(AND).append(fq);
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return new StringBuilder[] { qStr, fqStr };
	}
	
	private StringBuilder[] createQuery(Map<String, List<BaseCompare>> map) {
		// TODO Auto-generated method stub
		StringBuilder qAppendStr = new StringBuilder();
		StringBuilder fqAppendStr = new StringBuilder();
		
		for (Entry<String, List<BaseCompare>> entry : map.entrySet()) {
			List<BaseCompare> compares = entry.getValue();
			for (BaseCompare baseCompare : compares) {
				try {
					baseCompare.createQuery();
					StringBuilder q = baseCompare.getQ();
					StringBuilder fq = baseCompare.getFq();
					log.info(q);
					log.info(fq);
					if (baseCompare.isParent) {
						if (qAppendStr.length() == 0) {
							qAppendStr.append(q);
						} else {
							qAppendStr.append(AND).append(q);
						}
					} else {
						if (qAppendStr.length() == 0) {
							fqAppendStr.append(fq);
						} else {
							fqAppendStr.append(AND).append(fq);
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		if (qAppendStr.length() == 0) {
			qAppendStr.append(ALL);
		}
		return new StringBuilder[] { qAppendStr, fqAppendStr };
	}
	
	public static void main(String[] args) throws SolrServerException, IOException {
		String baseURL = "http://master.khadoop.com:8983/solr/%s/";
		SolrServer server = new HttpSolrServer(String.format(baseURL, "webrequest"));
		LoginInOutHBase in = new LoginInOutHBase();
		in.setAccessTime(new Date());
		// SolrInputDocument(fields: [ACCESSTIME=Mon Jun 08 11:47:22 CST 2015, COSTTIME=9841, OUTTIME=Mon Jun 08 11:47:32 CST 2015, REFERRER=http://localhost:29999/test/, USERID=0,
		// PV=1, ID= 02015-06-08 11:47:22, ISPARENT=true, SESSIONID=ofh6iiity1cha4cyyzv667hc, REFERERDOMAIN=localhost:29999, BROWSER=Chrome, SYSTEM=Windows 7, TITLE=明前茶, TYPE=0])
		in.setBrowser("browser");
		in.setCostTime(3L);
		in.setOutTime(new Date());
		in.setReferrer("referer");
		in.setUserId(1L);
		in.setPv(1);
		in.setId("id");
		in.setIsParent(true);
		in.setSessionId("12345678");
		in.setRefererDomain("localhost");
		// doc.addField(SearchFields.V, getV());
		in.setBrowser("chrome");
		in.setSystem("Win7");
		in.setTitle("title");
		in.setType(0);
		WebRequest request = new WebRequest();
		request.setId("id");
		request.setAccessTime(new Date());
		request.setCostTime(2L);
		request.setUserId(0L);
		request.setReferrer("referer");
		request.setUrl("url");
		request.setIsParent(false);
		request.setSessionId("sessionid");
		request.setRefererDomain("localhost");
		request.setIsFirstPage(true);
		request.setType(0);
		SolrInputDocument doc = in.getSolrDoc();
		doc.addChildDocument(request.getSolrDoc());
		server.add(doc);
		server.commit();
	}
	
}
