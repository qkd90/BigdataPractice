package com.data.data.hmly.service.lxbcommon.entity.vo;

import com.data.data.hmly.service.lxbcommon.entity.enums.CustomArrange;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomType;

import java.util.List;

/**
 * Created by caiys on 2016/6/20.
 */
public class CustomRequireVo {
    private Long id;            // 标识
    private CustomType customType;  // 定制类型：公司、家庭、其他
    private String customTypeName;  // 定制类型：公司、家庭、其他
    private Long startCityId;
    private String startCityName;
    private String startDate;     // 出发日期
    private Integer day;        // 游玩天数
    private Boolean adjustFlag;  // 是否根据实际情况调整
    private CustomArrange arrange; // 行程安排：紧凑、适中、宽松、不确定
    private String arrangeName; // 行程安排：紧凑、适中、宽松、不确定
    private Integer adult;   // 成人数
    private Integer child = 0; // 儿童数
    private Float minPrice; // 预算最低价
    private Float maxPrice; // 预算最高价
    private String budget;  // 预算说明
    private String createTime; // 创建时间
    private CustomStatus status; // 状态：处理中、已处理、已取消
    private String statusName; // 状态：处理中、已处理、已取消
    private String remark; // 备注

    private List<CustomRequireDestinationVo> customRequireDestinationVos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomType getCustomType() {
        return customType;
    }

    public void setCustomType(CustomType customType) {
        this.customType = customType;
    }

    public String getCustomTypeName() {
        return customTypeName;
    }

    public void setCustomTypeName(String customTypeName) {
        this.customTypeName = customTypeName;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public String getStartCityName() {
        return startCityName;
    }

    public void setStartCityName(String startCityName) {
        this.startCityName = startCityName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(Boolean adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    public CustomArrange getArrange() {
        return arrange;
    }

    public void setArrange(CustomArrange arrange) {
        this.arrange = arrange;
    }

    public String getArrangeName() {
        return arrangeName;
    }

    public void setArrangeName(String arrangeName) {
        this.arrangeName = arrangeName;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public CustomStatus getStatus() {
        return status;
    }

    public void setStatus(CustomStatus status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<CustomRequireDestinationVo> getCustomRequireDestinationVos() {
        return customRequireDestinationVos;
    }

    public void setCustomRequireDestinationVos(List<CustomRequireDestinationVo> customRequireDestinationVos) {
        this.customRequireDestinationVos = customRequireDestinationVos;
    }
}
