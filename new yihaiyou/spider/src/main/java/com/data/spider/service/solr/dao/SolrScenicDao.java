package com.data.spider.service.solr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import com.data.ikanalysis.utils.IKTokenUtils;
import com.data.spider.service.pojo.Scenic;

@Repository
public class SolrScenicDao {

	public List<Scenic> findByTitle(String key) throws Exception {
		String search = IKTokenUtils.token(key);
		SolrQuery query = new SolrQuery(search);
		QueryResponse response = Solrconfig.SENIC.solrServer.query(query, METHOD.POST);
		SolrDocumentList docs = response.getResults();
		List<Scenic> scenics = new ArrayList<Scenic>();
		for (SolrDocument doc : docs) {

			Long id = (Long) doc.getFieldValue("id");
			String address = (String) doc.getFieldValue("address");
			String name = (String) doc.getFieldValue("name");
			Scenic scenic = new Scenic();
			scenic.setId(id);
			scenic.setAddress(address);
			scenic.setName(name);
			scenics.add(scenic);
		}
		return scenics;
	}

}
