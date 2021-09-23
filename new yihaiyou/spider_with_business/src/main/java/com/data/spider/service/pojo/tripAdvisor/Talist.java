package com.data.spider.service.pojo.tripAdvisor;

import com.data.data.hmly.service.scenic.entity.DataScenic;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

//@XmlRootElement
@XmlRootElement
public class Talist {
    private List<DataScenic> scenics = new ArrayList<DataScenic>();

    @XmlElement(name = "scenic")
    public List<DataScenic> getScenics() {
        return scenics;
    }
    public void setScenics(List<DataScenic> scenics) {
        this.scenics = scenics;
    }

}
