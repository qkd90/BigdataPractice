package com.data.data.hmly.service.plan.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.google.common.collect.Sets;
import com.zuipin.util.PinyinUtil;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SolrDocument(solrCoreName = "products")
public class RecommendPlanSolrEntity extends SolrEntity<RecommendPlan> {

    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private Long userId;
    @Field
    private String nickName;
    @Field
    private String head;
    @Field
    private String city;
    @Field
    private Long cityId;
    @Field
    private String description;
    @Field
    private String passScenics;
    @Field
    private Integer picNum;
    @Field
    private Integer shareNum;
    @Field
    private Integer quoteNum;
    @Field
    private Integer viewNum;
    @Field
    private Integer collectNum;
    @Field
    private String cover;
    @Field
    private String coverSmall;
    @Field
    private List<Long> labelIds;
    @Field
    private String phoneCoverPath;
    @Field
    private String phoneCoverSmall;
    @Field
    private Date createTime;
    @Field
    private Date startTime;
    @Field
    private Integer startMonth;
    @Field
    private Integer days;
    @Field
    private Integer scenics;
    @Field
    private Integer weight;
    @Field
    private Float cost;
    @Field
    private String[] tags;
    @Field
    private Set<Long> delicacyIds;
    @Field
    private Set<Long> scenicIds;
    @Field
    private Set<Long> hotelIds;
    @Field
    private Set<Long> trafficIds;
    @Field
    private Set<Long> cityIds;
    @Field
    private final SolrType type = SolrType.recommend_plan;
    @Field
    private String typeid;
    @Field
    private List<String>  py;

    public RecommendPlanSolrEntity() {

    }

    public RecommendPlanSolrEntity(RecommendPlan recommendPlan) {
        this.id = recommendPlan.getId();
        this.name = recommendPlan.getPlanName();
        String spells = PinyinUtil.converterToSpell(this.name);
        this.py = Arrays.asList(spells.split(" "));
        if (recommendPlan.getUser() != null) {
            this.userId = recommendPlan.getUser().getId();
//            this.nickName = recommendPlan.getUser().getUserExinfo().getNickName();
            this.nickName = recommendPlan.getUser().getNickName();
            this.head = recommendPlan.getUser().getHead();
        } else {
            this.userId = null;
        }
        if (recommendPlan.getCity() != null) {
            this.city = recommendPlan.getCity().getName();
            this.cityId = recommendPlan.getCity().getId();
        }
        this.cityIds = Sets.newHashSet();
        String[] cityCodes = recommendPlan.getCityIds().split(",");
        for (String cityCodeStr : cityCodes) {
            this.cityIds.add(Long.parseLong(cityCodeStr));
        }
        if (recommendPlan.getId() != null && recommendPlan.getLabelItems() != null) {
            Set<LabelItem> itemSets = recommendPlan.getLabelItems();
            List<Long> labelIds = new ArrayList<Long>();
            for (LabelItem item : itemSets) {
                labelIds.add(item.getLabel().getId());
            }
            this.labelIds = labelIds;
        }
        this.description = recommendPlan.getDescription();
//        this.picNum = recommendPlan.getPicNum();
        this.passScenics = recommendPlan.getPassScenics();
        this.shareNum = recommendPlan.getShareNum();
        this.quoteNum = recommendPlan.getQuoteNum();
        this.viewNum = recommendPlan.getViewNum();
        this.collectNum = recommendPlan.getCollectNum();
        this.cover = recommendPlan.getCoverPath();
        this.coverSmall = recommendPlan.getCoverSmall();
        this.phoneCoverPath = recommendPlan.getPhoneCoverPath();
        this.phoneCoverSmall = recommendPlan.getPhoneCoverSmall();
        this.createTime = recommendPlan.getCreateTime();
        this.days = recommendPlan.getDays();
        this.scenics = recommendPlan.getScenics();
        this.weight = recommendPlan.getWeight();
        this.cost = recommendPlan.getCost();
        this.delicacyIds = Sets.newHashSet();
        this.scenicIds = Sets.newHashSet();
        this.hotelIds = Sets.newHashSet();
        this.trafficIds = Sets.newHashSet();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(recommendPlan.getStartTime() == null ? new Date() : recommendPlan.getStartTime());
        this.startMonth = calendar.get(Calendar.MONTH) + 1;
        this.startTime = recommendPlan.getStartTime();
        if (recommendPlan.getRecommendPlanDays() != null) {
            for (RecommendPlanDay day : recommendPlan.getRecommendPlanDays()) {
                for (RecommendPlanTrip trip : day.getRecommendPlanTrips()) {
                    if (trip.getTripType() != null) {
                        getType(trip);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(recommendPlan.getTags())) {
            this.tags = recommendPlan.getTags().split(",");
        }
        this.typeid = this.type.toString() + this.id;
    }

    private void getType(RecommendPlanTrip trip) {
        if (trip.getTripType() == 2 && trip.getDelicacy() != null && trip.getDelicacy().getId() != null) {
            this.delicacyIds.add(trip.getDelicacy().getId());
        } else if (trip.getTripType() == 1 && trip.getScenicId() != null) {
            this.scenicIds.add(trip.getScenicId());
        } else if (trip.getTripType() == 3 && trip.getScenicId() != null) {
            this.hotelIds.add(trip.getScenicId());
        } else if (trip.getTripType() == 4 && trip.getScenicId() != null) {
            this.trafficIds.add(trip.getScenicId());
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getPhoneCoverPath() {
        return phoneCoverPath;
    }

    public void setPhoneCoverPath(String phoneCoverPath) {
        this.phoneCoverPath = phoneCoverPath;
    }

    public String getPhoneCoverSmall() {
        return phoneCoverSmall;
    }

    public void setPhoneCoverSmall(String phoneCoverSmall) {
        this.phoneCoverSmall = phoneCoverSmall;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public SolrType getType() {
        return type;
    }

    public Set<Long> getDelicacyIds() {

        return delicacyIds;
    }

    public void setDelicacyIds(Set<Long> delicacyIds) {
        this.delicacyIds = delicacyIds;
    }

    public Set<Long> getScenicIds() {
        return scenicIds;
    }

    public void setScenicIds(Set<Long> scenicIds) {
        this.scenicIds = scenicIds;
    }

    public Set<Long> getHotelIds() {
        return hotelIds;
    }

    public void setHotelIds(Set<Long> hotelIds) {
        this.hotelIds = hotelIds;
    }

    public Set<Long> getTrafficIds() {
        return trafficIds;
    }

    public void setTrafficIds(Set<Long> trafficIds) {
        this.trafficIds = trafficIds;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassScenics() {
        return passScenics;
    }

    public void setPassScenics(String passScenics) {
        this.passScenics = passScenics;
    }

    public Integer getPicNum() {
        return picNum;
    }

    public void setPicNum(Integer picNum) {
        this.picNum = picNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Set<Long> getCityIds() {
        return cityIds;
    }

    public void setCityIds(Set<Long> cityIds) {
        this.cityIds = cityIds;
    }

    public List<String>  getPy() {
        return py;
    }

    public void setPy(List<String>  py) {
        this.py = py;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}