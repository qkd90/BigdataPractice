package com.data.data.hmly.service.line.entity;

import com.data.data.hmly.service.line.entity.enums.LineInsuranceStatus;
import com.data.data.hmly.service.sales.entity.Insurance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by zzl on 2016/7/7.
 */
@Entity
@Table(name = "line_insurance")
public class LineInsurance extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LineInsuranceStatus status;

    @Column(name = "is_rec")
    private Boolean isRec;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public LineInsuranceStatus getStatus() {
        return status;
    }

    public void setStatus(LineInsuranceStatus status) {
        this.status = status;
    }

    public Boolean getIsRec() {
        return isRec;
    }

    public void setIsRec(Boolean isRec) {
        this.isRec = isRec;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
