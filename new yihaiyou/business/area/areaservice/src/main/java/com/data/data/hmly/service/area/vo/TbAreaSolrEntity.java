package com.data.data.hmly.service.area.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.zuipin.util.PinyinUtil;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SolrDocument(solrCoreName = "products")
public class TbAreaSolrEntity extends SolrEntity<TbArea> {


    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private List<String> py;
//    @Field
//    private String cityCode;
//    @Field
//    private String father;
    @Field
	private List<Long> labelIds;
    @Field
    private Integer level;



    //    @Field
//    private String fullPath;
    @Field
    private final SolrType type = SolrType.area;
    @Field
    private String typeid;


    public TbAreaSolrEntity() {

    }

    public TbAreaSolrEntity(TbArea tbArea) {
        this.id = tbArea.getId();
        this.name = tbArea.getName();
        String spells = PinyinUtil.converterToSpell(this.name);
        this.py = Arrays.asList(spells.split(" "));
        this.level = tbArea.getLevel();
//        this.cityCode = tbArea.getCityCode();
//        if (tbArea.getFather() != null) {
//            this.father = tbArea.getFather().getName();
//        }
//        this.fullPath = tbArea.getFullPath();
        this.typeid = this.type.toString() + this.id;
        if (tbArea.getId() != null) {
            Set<LabelItem> itemSets = tbArea.getLabelItems();
            List<Long> laIds = new ArrayList<Long>();
            for (LabelItem item : itemSets) {
                laIds.add(item.getLabel().getId());
            }
            this.labelIds = laIds;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getCityCode() {
//        return cityCode;
//    }
//
//    public void setCityCode(String cityCode) {
//        this.cityCode = cityCode;
//    }
//
//    public String getFather() {
//        return father;
//    }
//
//    public void setFather(String father) {
//        this.father = father;
//    }
//
//    public String getFullPath() {
//        return fullPath;
//    }
//
//    public void setFullPath(String fullPath) {
//        this.fullPath = fullPath;
//    }

    @Override
    public SolrType getType() {
        return type;
    }

    @Override
    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

	public List<Long> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<Long> labelIds) {
		this.labelIds = labelIds;
	}

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<String>  getPy() {
        return py;
    }

    public void setPy(List<String>  py) {
        this.py = py;
    }
}