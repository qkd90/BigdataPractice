package com.data.spider.process.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mfw {

	private String		introduction;
	private List<Dds>	dds	= new ArrayList<Dds>();

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@XmlElement
	public List<Dds> getDds() {
		return dds;
	}

	public void setDds(List<Dds> dds) {
		this.dds = dds;
	}

}
