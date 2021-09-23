package com.data.data.hmly.service.vo;

import com.data.data.hmly.enums.TargetType;

/**
 * Created by zzl on 2016/2/22.
 */
public class LabelItemsVo {

    private Long id;
    private Long labelId;
    private Long targetId;
    private TargetType targetType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
}
