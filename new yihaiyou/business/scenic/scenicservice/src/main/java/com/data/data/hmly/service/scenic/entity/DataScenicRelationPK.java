package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Objects;

public class DataScenicRelationPK extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    private Long scenicId;
    private String source;

    @Column(name = "scenic_id")
    @Id
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Column(name = "source")
    @Id
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataScenicRelationPK that = (DataScenicRelationPK) o;

        return Objects.equals(scenicId, that.scenicId) && !(source != null ? !source.equals(that.source) : that.source != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (scenicId ^ (scenicId >>> 32));
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
