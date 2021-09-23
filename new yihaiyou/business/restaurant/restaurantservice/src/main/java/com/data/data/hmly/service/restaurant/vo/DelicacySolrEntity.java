package com.data.data.hmly.service.restaurant.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyExtend;
import com.zuipin.util.PinyinUtil;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by guoshijie on 2015/12/8.
 */
@SolrDocument(solrCoreName = "products")
public class DelicacySolrEntity extends SolrEntity<Delicacy> {

	@Field
	private Long id;
	@Field
	private String name;
	@Field
	private Double price;
	@Field
	private String city;
	@Field
	private Long cityId;
	@Field
	private String cuisine;
	@Field
	private String taste;
	@Field
	private String efficacy;
	@Field
	private String cover;
	@Field
	private Integer recommendNum;
	@Field
	private Integer shareNum;
	@Field
	private List<Long> labelIds;
	@Field
	private Integer agreeNum;
	@Field
	private final SolrType type = SolrType.delicacy;
	@Field
	private String typeid;
	@Field
	private List<String> py;
	@Field
	private String introduction;
	@Field
	private Integer viewNum;
	@Field
	private Integer collectNum;

	public DelicacySolrEntity() {

	}

    public DelicacySolrEntity(Delicacy delicacy) {


        this.id = delicacy.getId();
        this.name = delicacy.getName();
		String spells = PinyinUtil.converterToSpell(this.name);
		this.py = Arrays.asList(spells.split(" "));
        this.price = delicacy.getPrice();
        if (delicacy.getCity() != null) {
            this.city = delicacy.getCity().getName();
			this.cityId = delicacy.getCity().getId();
        }
        this.cuisine = delicacy.getCuisine();
        this.taste = delicacy.getTaste();
        this.efficacy = delicacy.getEfficacy();
        this.cover = delicacy.getCover();
		DelicacyExtend extend = delicacy.getExtend();
        if (extend != null) {
            this.recommendNum = extend.getRecommendNum();
            this.shareNum = extend.getShareNum();
            this.agreeNum = extend.getAgreeNum();
			this.introduction = extend.getIntroduction();
			this.collectNum = extend.getCollectNum();
			this.viewNum = extend.getViewNum();
        }
        this.typeid = this.type.toString() + this.id;
        if (delicacy.getId() != null) {
            Set<LabelItem> itemSets = delicacy.getLabelItems();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getEfficacy() {
		return efficacy;
	}

	public void setEfficacy(String efficacy) {
		this.efficacy = efficacy;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(Integer recommendNum) {
		this.recommendNum = recommendNum;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	public Integer getAgreeNum() {
		return agreeNum;
	}

	public void setAgreeNum(Integer agreeNum) {
		this.agreeNum = agreeNum;
	}

	public SolrType getType() {
		return type;
	}

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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public List getPy() {
		return py;
	}

	public void setPy(List py) {
		this.py = py;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}
}
