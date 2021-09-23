package com.data.data.hmly.service.plan.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.plan.entity.Plan;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zuipin.util.PinyinUtil;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by huangpeijie on 2016-04-29,0029.
 */
@SolrDocument(solrCoreName = "products")
public class PlanSolrEntity extends SolrEntity<Plan> {
    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private Long userId;
    @Field
    private String nickName;
    @Field
    private String city;
    @Field
    private Long cityId;
//    @Field
//    private String startCity;
    @Field
    private Long fromCityId;
    @Field
    private String passScenics;
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
    private List<Long> labelIds = Lists.newArrayList();
    @Field
    private Date createTime;
    @Field
    private Date startTime;
    @Field
    private Integer startMonth;
    @Field
    private Integer days;
//    @Field
//    private Integer scenics = 0;
    @Field
    private Float cost = 0f;
    @Field
    private Set<Long> scenicIds = Sets.newHashSet();
    @Field
    private Set<Long> hotelIds = Sets.newHashSet();
    @Field
    private Set<Long> trafficIds = Sets.newHashSet();
    @Field
    private Set<Long> cityIds = Sets.newHashSet();
    @Field
    private final SolrType type = SolrType.plan;
    @Field
    private String typeid;

    @Field
    private List<String> py;

    public PlanSolrEntity() {
    }

    public PlanSolrEntity(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();

        if (StringUtils.isNotBlank(this.name)) {
            String spells = PinyinUtil.converterToSpell(this.name);
            this.py = Arrays.asList(spells.split(" "));
        }
        if (plan.getUser() != null) {
            this.userId = plan.getUser().getId();
            this.nickName = plan.getUser().getNickName();
        }
        if (plan.getCity() != null) {
            this.city = plan.getCity().getName();
            this.cityId = plan.getCity().getId();
        }
        if (plan.getStartCity() != null) {
//            this.startCity = plan.getStartCity().getName();
            this.fromCityId = plan.getStartCity().getId();
        }
        this.passScenics = plan.getPassScenics();
//        if (plan.getPlanDayList() != null) {
//            for (PlanDay day : plan.getPlanDayList()) {
//                completeFromDay(day);
//            }
//        }
        if (plan.getPlanStatistic() != null) {
            this.cost = plan.getPlanStatistic().getPlanCost();
            this.shareNum = plan.getPlanStatistic().getShareNum();
            this.quoteNum = plan.getPlanStatistic().getQuoteNum();
            this.viewNum = plan.getPlanStatistic().getViewNum();
            this.collectNum = plan.getPlanStatistic().getCollectNum();
        }
        this.cover = plan.getCoverPath();
        this.coverSmall = plan.getCoverSmall();
        this.createTime = plan.getCreateTime();
        this.startTime = plan.getStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(plan.getStartTime() == null ? new Date() : plan.getStartTime());
        this.startMonth = calendar.get(Calendar.MONTH) + 1;
        this.days = plan.getPlanDays();
        this.typeid = this.type.toString() + this.id;
    }

//    private void completeFromDay(PlanDay day) {
//        this.cost += day.getFoodCost() + day.getHotelCost() + day.getTicketCost() + day.getTrafficCost() + day.getReturnTrafficCost();
//        if (day.getHotel() != null) {
//            this.hotelIds.add(day.getHotel().getId());
//        }
//        this.trafficIds.add(day.getTrafficPriceId());
//        this.trafficIds.add(day.getReturnTrafficPriceId());
//        if (day.getCity() != null) {
//            this.cityIds.add(day.getCity().getId());
//        }
//        if (day.getPlanTripList() == null) {
//            return;
//        }
////        this.scenics += day.getPlanTripList().size();
//        for (PlanTrip trip : day.getPlanTripList()) {
//            if (trip.getScenicInfo() == null) {
//                continue;
//            }
//            this.scenicIds.add(trip.getScenicInfo().getId());
//            if (this.passScenics.length() > 20) {
//                break;
//            }
//            if (this.passScenics.length() > 0) {
//                this.passScenics += "-";
//            }
//            this.passScenics += trip.getScenicInfo().getName();
//        }
//    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getPassScenics() {
        return passScenics;
    }

    public void setPassScenics(String passScenics) {
        this.passScenics = passScenics;
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

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
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

//    public Integer getScenics() {
//        return scenics;
//    }
//
//    public void setScenics(Integer scenics) {
//        this.scenics = scenics;
//    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
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

    public Set<Long> getCityIds() {
        return cityIds;
    }

    public void setCityIds(Set<Long> cityIds) {
        this.cityIds = cityIds;
    }

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

    public List<String> getPy() {
        return py;
    }

    public void setPy(List<String> py) {
        this.py = py;
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }
}
