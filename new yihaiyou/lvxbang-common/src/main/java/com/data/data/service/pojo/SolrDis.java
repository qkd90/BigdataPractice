package com.data.data.service.pojo;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "dis")
public class SolrDis {

	@Field
	private String	f_to;
	@Field
	private int		areaId;
	@Field
	private int		endCost;
	@Field
	private int		taxi_time;
	@Field
	private long	s_id;
	@Field
	private long	e_id;
	@Field
	private int		order_num;
	@Field
	private int		advice_hours;

	public String getF_to() {
		return f_to;
	}

	public void setF_to(String f_to) {
		this.f_to = f_to;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getEndCost() {
		return endCost;
	}

	public void setEndCost(int endCost) {
		this.endCost = endCost;
	}

	public long getS_id() {
		return s_id;
	}

	public void setS_id(long s_id) {
		this.s_id = s_id;
	}

	public long getE_id() {
		return e_id;
	}

	public void setE_id(long e_id) {
		this.e_id = e_id;
	}

	public int getTaxi_time() {
		return taxi_time / 60;
	}

	public void setTaxi_time(int taxi_time) {
		this.taxi_time = taxi_time;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public static SolrDis build() {
		SolrDis dis = new SolrDis();
		dis.setEndCost(30);
		dis.setTaxi_time(30);
		return dis;
	}

	public int getAdvice_hours() {
		return advice_hours;
	}

	public void setAdvice_hours(int advice_hours) {
		this.advice_hours = advice_hours;
	}

}
