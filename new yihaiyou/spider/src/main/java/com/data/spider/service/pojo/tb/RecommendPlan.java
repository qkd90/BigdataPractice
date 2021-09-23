package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Sane on 16/1/16.
 */
@Entity
@javax.persistence.Table(name = "recommend_plan")
public class RecommendPlan extends com.framework.hibernate.util.Entity {
    private long id;

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Long userId;

    @Basic
    @javax.persistence.Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long scenicId;

    @Basic
    @javax.persistence.Column(name = "scenic_id", nullable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    private Long cityId;

    @Basic
    @javax.persistence.Column(name = "city_id", nullable = true)
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    private String cityIds;

    @Basic
    @javax.persistence.Column(name = "city_ids", nullable = true, length = 512)
    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    private String province;

    @Basic
    @javax.persistence.Column(name = "province", nullable = true, length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    private Integer shareNum;

    @Basic
    @javax.persistence.Column(name = "share_num", nullable = true)
    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    private Integer quoteNum;

    @Basic
    @javax.persistence.Column(name = "quote_num", nullable = true)
    public Integer getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }

    private Integer collectNum;

    @Basic
    @javax.persistence.Column(name = "collect_num", nullable = true)
    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    private String planName;

    @Basic
    @javax.persistence.Column(name = "plan_name", nullable = true, length = 255)
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    private String description;

    @Basic
    @javax.persistence.Column(name = "description", nullable = true, length = 3000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String coverPath;

    @Basic
    @javax.persistence.Column(name = "cover_path", nullable = true, length = 255)
    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    private String coverSmall;

    @Basic
    @javax.persistence.Column(name = "cover_small", nullable = true, length = 255)
    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    private String phoneCoverPath;

    @Basic
    @javax.persistence.Column(name = "phone_cover_path", nullable = true, length = 255)
    public String getPhoneCoverPath() {
        return phoneCoverPath;
    }

    public void setPhoneCoverPath(String phoneCoverPath) {
        this.phoneCoverPath = phoneCoverPath;
    }

    private String phoneCoverSmall;

    @Basic
    @javax.persistence.Column(name = "phone_cover_small", nullable = true, length = 255)
    public String getPhoneCoverSmall() {
        return phoneCoverSmall;
    }

    public void setPhoneCoverSmall(String phoneCoverSmall) {
        this.phoneCoverSmall = phoneCoverSmall;
    }

    private Date createTime;

    @Basic
    @javax.persistence.Column(name = "create_time", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String modifyTime;

    @Basic
    @javax.persistence.Column(name = "modify_time", nullable = true)
    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private Integer days;

    @Basic
    @javax.persistence.Column(name = "days", nullable = true)
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    private Integer scenics;

    @Basic
    @javax.persistence.Column(name = "scenics", nullable = true)
    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    private Double hotelCost;

    @Basic
    @javax.persistence.Column(name = "hotel_cost", nullable = true, precision = 2)
    public Double getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(Double hotelCost) {
        this.hotelCost = hotelCost;
    }

    private Double travelCost;

    @Basic
    @javax.persistence.Column(name = "travel_cost", nullable = true, precision = 2)
    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }

    private int valid;

    @Basic
    @javax.persistence.Column(name = "valid", nullable = false)
    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    private String tags;

    @Basic
    @javax.persistence.Column(name = "tags", nullable = true, length = 255)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    private Integer weight;

    @Basic
    @javax.persistence.Column(name = "weight", nullable = true)
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    private Integer selected;

    @Basic
    @javax.persistence.Column(name = "selected", nullable = true)
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    private Double cost;

    @Basic
    @javax.persistence.Column(name = "cost", nullable = true, precision = 2)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    private Double includeSeasonticketCost;

    @Basic
    @javax.persistence.Column(name = "include_seasonticket_cost", nullable = true, precision = 2)
    public Double getIncludeSeasonticketCost() {
        return includeSeasonticketCost;
    }

    public void setIncludeSeasonticketCost(Double includeSeasonticketCost) {
        this.includeSeasonticketCost = includeSeasonticketCost;
    }

    private Double ticketCost;

    @Basic
    @javax.persistence.Column(name = "ticket_cost", nullable = true, precision = 2)
    public Double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Double ticketCost) {
        this.ticketCost = ticketCost;
    }

    private Double seasonticketCost;

    @Basic
    @javax.persistence.Column(name = "seasonticket_cost", nullable = true, precision = 2)
    public Double getSeasonticketCost() {
        return seasonticketCost;
    }

    public void setSeasonticketCost(Double seasonticketCost) {
        this.seasonticketCost = seasonticketCost;
    }

    private Integer recLoc;

    @Basic
    @javax.persistence.Column(name = "rec_loc", nullable = true)
    public Integer getRecLoc() {
        return recLoc;
    }

    public void setRecLoc(Integer recLoc) {
        this.recLoc = recLoc;
    }

    private Integer status;

    @Basic
    @javax.persistence.Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String bestTime;

    @Basic
    @javax.persistence.Column(name = "best_time", nullable = true, length = -1)
    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    private String advClothes;

    @Basic
    @javax.persistence.Column(name = "adv_clothes", nullable = true, length = -1)
    public String getAdvClothes() {
        return advClothes;
    }

    public void setAdvClothes(String advClothes) {
        this.advClothes = advClothes;
    }

    private String necessity;

    @Basic
    @javax.persistence.Column(name = "necessity", nullable = true, length = -1)
    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    private String attention;

    @Basic
    @javax.persistence.Column(name = "attention", nullable = true, length = -1)
    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    private int deleteFlag;

    @Basic
    @javax.persistence.Column(name = "delete_flag", nullable = false)
    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    private Long createdBy;

    @Basic
    @javax.persistence.Column(name = "created_by", nullable = true)
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    private Long updatedBy;

    @Basic
    @javax.persistence.Column(name = "updated_by", nullable = true)
    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Double foodCost;

    @Basic
    @javax.persistence.Column(name = "food_cost", nullable = true, precision = 2)
    public Double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Double foodCost) {
        this.foodCost = foodCost;
    }

    private Integer mark;

    @Basic
    @javax.persistence.Column(name = "mark", nullable = true)
    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    private String dataSource;

    @Basic
    @javax.persistence.Column(name = "data_source", nullable = true, length = 45)
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private String dataSourceId;

    @Basic
    @javax.persistence.Column(name = "data_source_id", nullable = true, length = 45)
    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    private String dataSourceCode;

    @Basic
    @javax.persistence.Column(name = "data_source_code", nullable = true, length = -1)
    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    private String tagStr;

    @Basic
    @javax.persistence.Column(name = "tagStr", nullable = true, length = 200)
    public String getTagStr() {
        return tagStr;
    }

    public void setTagStr(String tagStr) {
        this.tagStr = tagStr;
    }

    private Boolean showIndex;

    @Basic
    @javax.persistence.Column(name = "show_index", nullable = true)
    public Boolean getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Boolean showIndex) {
        this.showIndex = showIndex;
    }

    private Integer viewNum;

    @Basic
    @javax.persistence.Column(name = "view_num", nullable = true)
    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    private String passScenics;

    @Basic
    @javax.persistence.Column(name = "pass_scenics", nullable = true)
    public String getPassScenics() {
        return passScenics;
    }

    public void setPassScenics(String passScenics) {
        this.passScenics = passScenics;
    }

    private Date startTime;

    @Basic
    @javax.persistence.Column(name = "start_time", nullable = true)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendPlan that = (RecommendPlan) o;

        if (id != that.id) return false;
        if (valid != that.valid) return false;
        if (deleteFlag != that.deleteFlag) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (scenicId != null ? !scenicId.equals(that.scenicId) : that.scenicId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (cityIds != null ? !cityIds.equals(that.cityIds) : that.cityIds != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (shareNum != null ? !shareNum.equals(that.shareNum) : that.shareNum != null) return false;
        if (quoteNum != null ? !quoteNum.equals(that.quoteNum) : that.quoteNum != null) return false;
        if (collectNum != null ? !collectNum.equals(that.collectNum) : that.collectNum != null) return false;
        if (planName != null ? !planName.equals(that.planName) : that.planName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (coverPath != null ? !coverPath.equals(that.coverPath) : that.coverPath != null) return false;
        if (coverSmall != null ? !coverSmall.equals(that.coverSmall) : that.coverSmall != null) return false;
        if (phoneCoverPath != null ? !phoneCoverPath.equals(that.phoneCoverPath) : that.phoneCoverPath != null)
            return false;
        if (phoneCoverSmall != null ? !phoneCoverSmall.equals(that.phoneCoverSmall) : that.phoneCoverSmall != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (days != null ? !days.equals(that.days) : that.days != null) return false;
        if (scenics != null ? !scenics.equals(that.scenics) : that.scenics != null) return false;
        if (hotelCost != null ? !hotelCost.equals(that.hotelCost) : that.hotelCost != null) return false;
        if (travelCost != null ? !travelCost.equals(that.travelCost) : that.travelCost != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (selected != null ? !selected.equals(that.selected) : that.selected != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (includeSeasonticketCost != null ? !includeSeasonticketCost.equals(that.includeSeasonticketCost) : that.includeSeasonticketCost != null)
            return false;
        if (ticketCost != null ? !ticketCost.equals(that.ticketCost) : that.ticketCost != null) return false;
        if (seasonticketCost != null ? !seasonticketCost.equals(that.seasonticketCost) : that.seasonticketCost != null)
            return false;
        if (recLoc != null ? !recLoc.equals(that.recLoc) : that.recLoc != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (bestTime != null ? !bestTime.equals(that.bestTime) : that.bestTime != null) return false;
        if (advClothes != null ? !advClothes.equals(that.advClothes) : that.advClothes != null) return false;
        if (necessity != null ? !necessity.equals(that.necessity) : that.necessity != null) return false;
        if (attention != null ? !attention.equals(that.attention) : that.attention != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (updatedBy != null ? !updatedBy.equals(that.updatedBy) : that.updatedBy != null) return false;
        if (foodCost != null ? !foodCost.equals(that.foodCost) : that.foodCost != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataSourceId != null ? !dataSourceId.equals(that.dataSourceId) : that.dataSourceId != null) return false;
        if (dataSourceCode != null ? !dataSourceCode.equals(that.dataSourceCode) : that.dataSourceCode != null)
            return false;
        if (tagStr != null ? !tagStr.equals(that.tagStr) : that.tagStr != null) return false;
        if (showIndex != null ? !showIndex.equals(that.showIndex) : that.showIndex != null) return false;
        if (viewNum != null ? !viewNum.equals(that.viewNum) : that.viewNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (cityIds != null ? cityIds.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (quoteNum != null ? quoteNum.hashCode() : 0);
        result = 31 * result + (collectNum != null ? collectNum.hashCode() : 0);
        result = 31 * result + (planName != null ? planName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (coverPath != null ? coverPath.hashCode() : 0);
        result = 31 * result + (coverSmall != null ? coverSmall.hashCode() : 0);
        result = 31 * result + (phoneCoverPath != null ? phoneCoverPath.hashCode() : 0);
        result = 31 * result + (phoneCoverSmall != null ? phoneCoverSmall.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (days != null ? days.hashCode() : 0);
        result = 31 * result + (scenics != null ? scenics.hashCode() : 0);
        result = 31 * result + (hotelCost != null ? hotelCost.hashCode() : 0);
        result = 31 * result + (travelCost != null ? travelCost.hashCode() : 0);
        result = 31 * result + valid;
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (selected != null ? selected.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (includeSeasonticketCost != null ? includeSeasonticketCost.hashCode() : 0);
        result = 31 * result + (ticketCost != null ? ticketCost.hashCode() : 0);
        result = 31 * result + (seasonticketCost != null ? seasonticketCost.hashCode() : 0);
        result = 31 * result + (recLoc != null ? recLoc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (bestTime != null ? bestTime.hashCode() : 0);
        result = 31 * result + (advClothes != null ? advClothes.hashCode() : 0);
        result = 31 * result + (necessity != null ? necessity.hashCode() : 0);
        result = 31 * result + (attention != null ? attention.hashCode() : 0);
        result = 31 * result + deleteFlag;
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (foodCost != null ? foodCost.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataSourceId != null ? dataSourceId.hashCode() : 0);
        result = 31 * result + (dataSourceCode != null ? dataSourceCode.hashCode() : 0);
        result = 31 * result + (tagStr != null ? tagStr.hashCode() : 0);
        result = 31 * result + (showIndex != null ? showIndex.hashCode() : 0);
        result = 31 * result + (viewNum != null ? viewNum.hashCode() : 0);
        return result;
    }
}
