package com.data.spider.process.entity;

import com.data.spider.service.pojo.Scenic;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TripAdvisorList {
    // @XmlElementWrapper(name = "RoomTypeVOs")
    private List<Scenic> scenics = new ArrayList<Scenic>();

    @XmlElement(name = "scenic")
    public List<Scenic> getScenics() {
        return scenics;
    }

    public void setScenics(List<Scenic> scenics) {
        this.scenics = scenics;
    }

}
