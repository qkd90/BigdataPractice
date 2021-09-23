package com.data.spider.service.pojo.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/10/27.
 */
@Entity
@javax.persistence.Table(name = "data_recplan")
public class DataRecplan extends com.framework.hibernate.util.Entity {
    private long id;
    private Long userId;
    private Long scenicId;
    private Long cityId;
    private String cityIds;
    private String province;
    private Integer shareNum;
    private Integer quoteNum;
    private Integer collectNum;
    private String planName;
    private String description;
    private String coverPath;
    private String coverSmall;
    private String phoneCoverPath;
    private String phoneCoverSmall;
    private Timestamp createTime;
    private Timestamp modifyTime;
    private Integer days;
    private Integer scenics;
    private Double hotelCost;
    private Double travelCost;
    private String tags;
    private Integer weight;
    private Integer selected;
    private Double cost;
    private Double includeSeasonticketCost;
    private Double ticketCost;
    private Double seasonticketCost;
    private Integer recLoc;
    private Integer status;
    private String bestTime;
    private String advClothes;
    private String necessity;
    private String attention;
    private Double foodCost;
    private Integer mark;
    private String tagStr;
    private String dataSource;
    private String dataSourceId;
    private String data;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true, insertable = true, updatable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "scenic_id", nullable = true, insertable = true, updatable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Basic
    @Column(name = "city_id", nullable = true, insertable = true, updatable = true)
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "city_ids", nullable = true, insertable = true, updatable = true, length = 512)
    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    @Basic
    @Column(name = "province", nullable = true, insertable = true, updatable = true, length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "share_num", nullable = true, insertable = true, updatable = true)
    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    @Basic
    @Column(name = "quote_num", nullable = true, insertable = true, updatable = true)
    public Integer getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }

    @Basic
    @Column(name = "collect_num", nullable = true, insertable = true, updatable = true)
    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Basic
    @Column(name = "plan_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "cover_path", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    @Basic
    @Column(name = "cover_small", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    @Basic
    @Column(name = "phone_cover_path", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPhoneCoverPath() {
        return phoneCoverPath;
    }

    public void setPhoneCoverPath(String phoneCoverPath) {
        this.phoneCoverPath = phoneCoverPath;
    }

    @Basic
    @Column(name = "phone_cover_small", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPhoneCoverSmall() {
        return phoneCoverSmall;
    }

    public void setPhoneCoverSmall(String phoneCoverSmall) {
        this.phoneCoverSmall = phoneCoverSmall;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "days", nullable = true, insertable = true, updatable = true)
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Basic
    @Column(name = "scenics", nullable = true, insertable = true, updatable = true)
    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    @Basic
    @Column(name = "hotel_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(Double hotelCost) {
        this.hotelCost = hotelCost;
    }

    @Basic
    @Column(name = "travel_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Double travelCost) {
        this.travelCost = travelCost;
    }


    @Basic
    @Column(name = "tags", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "weight", nullable = true, insertable = true, updatable = true)
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "selected", nullable = true, insertable = true, updatable = true)
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Basic
    @Column(name = "cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "include_seasonticket_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getIncludeSeasonticketCost() {
        return includeSeasonticketCost;
    }

    public void setIncludeSeasonticketCost(Double includeSeasonticketCost) {
        this.includeSeasonticketCost = includeSeasonticketCost;
    }

    @Basic
    @Column(name = "ticket_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Double ticketCost) {
        this.ticketCost = ticketCost;
    }

    @Basic
    @Column(name = "seasonticket_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getSeasonticketCost() {
        return seasonticketCost;
    }

    public void setSeasonticketCost(Double seasonticketCost) {
        this.seasonticketCost = seasonticketCost;
    }

    @Basic
    @Column(name = "rec_loc", nullable = true, insertable = true, updatable = true)
    public Integer getRecLoc() {
        return recLoc;
    }

    public void setRecLoc(Integer recLoc) {
        this.recLoc = recLoc;
    }

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "best_time", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    @Basic
    @Column(name = "adv_clothes", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getAdvClothes() {
        return advClothes;
    }

    public void setAdvClothes(String advClothes) {
        this.advClothes = advClothes;
    }

    @Basic
    @Column(name = "necessity", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    @Basic
    @Column(name = "attention", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    @Basic
    @Column(name = "food_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Double foodCost) {
        this.foodCost = foodCost;
    }

    @Basic
    @Column(name = "mark", nullable = true, insertable = true, updatable = true)
    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Basic
    @Column(name = "tagStr", nullable = true, insertable = true, updatable = true, length = 200)
    public String getTagStr() {
        return tagStr;
    }

    public void setTagStr(String tagStr) {
        this.tagStr = tagStr;
    }

    @Basic
    @Column(name = "data_source", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Basic
    @Column(name = "data_source_id", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Basic
    @Column(name = "data", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataRecplan that = (DataRecplan) o;

        if (id != that.id) return false;
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
        if (foodCost != null ? !foodCost.equals(that.foodCost) : that.foodCost != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (tagStr != null ? !tagStr.equals(that.tagStr) : that.tagStr != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataSourceId != null ? !dataSourceId.equals(that.dataSourceId) : that.dataSourceId != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;

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
        result = 31 * result + (foodCost != null ? foodCost.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (tagStr != null ? tagStr.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataSourceId != null ? dataSourceId.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
