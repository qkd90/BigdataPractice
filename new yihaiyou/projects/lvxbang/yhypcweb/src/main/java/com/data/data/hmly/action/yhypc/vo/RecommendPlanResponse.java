package com.data.data.hmly.action.yhypc.vo;

import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;

import javax.persistence.Column;

/**
 * Created by dy on 2017/1/13.
 */
public class RecommendPlanResponse {
    private Long id;
    private String name;
    private String coverPath;
    private Integer shareNum;
    private Integer quoteNum;
    private Integer collectNum;
    private Integer viewNum;
    private String description;

    public RecommendPlanResponse (){}
    public RecommendPlanResponse (RecommendPlanSolrEntity recommendPlanSolrEntity) {
        this.id = recommendPlanSolrEntity.getId();
        this.name = recommendPlanSolrEntity.getName();
        this.coverPath = recommendPlanSolrEntity.getCover();
        this.shareNum = recommendPlanSolrEntity.getShareNum();
        this.quoteNum = recommendPlanSolrEntity.getQuoteNum();
        this.viewNum = recommendPlanSolrEntity.getViewNum();
        this.collectNum = recommendPlanSolrEntity.getCollectNum();
        this.description = recommendPlanSolrEntity.getDescription();
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

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
