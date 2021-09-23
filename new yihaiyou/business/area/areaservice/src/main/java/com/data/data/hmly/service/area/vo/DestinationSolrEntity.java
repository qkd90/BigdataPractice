package com.data.data.hmly.service.area.vo;

import com.data.data.hmly.service.area.entity.Destination;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by guoshijie on 2015/12/18.
 */
@SolrDocument(solrCoreName = "destination")
public class DestinationSolrEntity {

	@Field
	public Long id;
	@Field
	public String name;

	public DestinationSolrEntity(Destination destination) {

		this.id = destination.getId();
		this.name = destination.getName();
	}
}
