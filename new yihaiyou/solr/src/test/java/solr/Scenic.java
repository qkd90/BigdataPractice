package solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "scenic")
public class Scenic {
	@Field
	private String	data_source_url;
	@Field
	private String	address;
	@Field
	private String	name;
	@Field
	private String	data_source;
	@Field
	private Long	id;

	public String getData_source_url() {
		return data_source_url;
	}

	public void setData_source_url(String data_source_url) {
		this.data_source_url = data_source_url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
