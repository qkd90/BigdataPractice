package com.data.spider.process.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.data.spider.service.pojo.Scenic;

@XmlRootElement ////xml文件的根元素
public class Cncnlist {
	// @XmlElementWrapper(name = "RoomTypeVOs")
	private List<Scenic>	scenics	= new ArrayList<Scenic>();

	@XmlElement(name = "scenic")
	public List<Scenic> getScenics() {
		return scenics;
	}

	public void setScenics(List<Scenic> scenics) {
		this.scenics = scenics;
	}
}
