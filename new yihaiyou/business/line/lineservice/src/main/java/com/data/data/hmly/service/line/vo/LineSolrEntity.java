package com.data.data.hmly.service.line.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.line.entity.Line;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HMLY on 2016/6/2.
 */
@SolrDocument(solrCoreName = "products")
public class LineSolrEntity  extends SolrEntity<Line> {

    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private final SolrType type = SolrType.line;
    @Field
    private String typeid;
    @Field
    private Long lineDay;
    @Field
    private String productAttr;
    @Field
    private String lineType;
    @Field
    private String status;
    @Field
    private String productImg;
    @Field
    private String companyUnitId;
    @Field
    private Float price;
    @Field
    private Integer	shareNum;
    @Field
    private Date createTime;
    @Field
    private Date updateTime;
    @Field
    private Long cityId;
    @Field
    private List<String> themes;
    @Field
    private String startCity;
    @Field
    private Long startCityId;
    @Field
    private String trafficType;
    @Field
    private List<String> startDays;
    @Field
    private String appendTitle;
    @Field
    private Integer orderNum;
    @Field
    private Integer satisfaction;
    @Field
    private String combineType;

    @Field
    private String shortComment;

    @Field
    private List<Long> labelIds;


    @Field
    private List<Long> muiltStartCity;

    public LineSolrEntity() {

    }

    public LineSolrEntity(Line line) {
        this.id = line.getId();
        this.name = line.getName();
        this.appendTitle = line.getAppendTitle();
        this.orderNum = line.getOrderSum();
        if (line.getPlayDay() != null) {
            this.lineDay = line.getPlayDay().longValue();
        }
        this.typeid = this.type.toString() + this.id;
        this.productAttr = line.getProductAttr().getDesc();
        if (line.getCombineType() != null) {
            this.combineType = line.getCombineType().toString();
        }
        this.lineType = line.getLineType();
        this.status = line.getLineStatus().toString();
        if (StringUtils.isNotBlank(line.getShortDesc())) {
            Pattern pattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line.getShortDesc());
            this.shortComment = matcher.replaceAll("");
        }
        if (line.getCompanyUnit() != null) {
            this.companyUnitId = line.getCompanyUnit().getId().toString();
        }
        if (line.getLinestatistic() != null) {
            this.shareNum = line.getLinestatistic().getShareNum();
        } else {
            this.shareNum = (int) (Math.random() * 1000);
        }

        this.createTime = line.getCreateTime();
        this.updateTime = line.getUpdateTime();
        this.satisfaction = line.getSatisfaction();
        this.cityId = line.getArriveCityId();
        this.startCityId = line.getStartCityId();
        if (StringUtils.isNotBlank(line.getGoWay(), line.getBackWay())) {
            if (line.getGoWay().equals(line.getBackWay())) {
                this.trafficType = line.getGoWayString() + "往返";
            } else {
                this.trafficType = line.getGoWayString() + "-" + line.getBackWayString();
            }
        }
        fillLabel(line);
    }

    private void fillLabel(Line line) {
        Set<LabelItem> itemSets = line.getLabelItems();
        List<String> scenicTheme = Lists.newArrayList();
        if (itemSets != null) {
            for (LabelItem item : itemSets) {
                if (item.getLabel().getParent() != null && "首页-面板-自助自驾-线路特色".equals(item.getLabel().getParent().getName())) {
                    scenicTheme.add(item.getLabel().getAlias());
                }
            }
        }
        this.themes = scenicTheme;
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


    @Override
    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(String companyUnitId) {
        this.companyUnitId = companyUnitId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getLineDay() {
        return lineDay;
    }

    public void setLineDay(Long lineDay) {
        this.lineDay = lineDay;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public SolrType getType() {
        return type;
    }

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public List<String> getStartDays() {
        return startDays;
    }

    public void setStartDays(List<String> startDays) {
        this.startDays = startDays;
    }

    public String getAppendTitle() {
        return appendTitle;
    }

    public void setAppendTitle(String appendTitle) {
        this.appendTitle = appendTitle;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public List<Long> getMuiltStartCity() {
        return muiltStartCity;
    }

    public void setMuiltStartCity(List<Long> muiltStartCity) {
        this.muiltStartCity = muiltStartCity;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }
}
