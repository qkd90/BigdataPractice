package com.data.data.hmly.service.comment.entity;


import com.data.data.hmly.service.comment.entity.enums.commentScoreTypeStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by HMLY on 2015/12/28.
 */
@Entity(name = "comment_score_type")
public class CommentScoreType extends com.framework.hibernate.util.Entity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "targetType")
    @Enumerated(EnumType.STRING)
    private ProductType targetType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private commentScoreTypeStatus status;
//    @Column(name = "type")
//    @Enumerated(EnumType.STRING)
//    private Type type;


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

    public ProductType getTargetType() {
        return targetType;
    }

    public void setTargetType(ProductType targetType) {
        this.targetType = targetType;
    }

//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }

    public commentScoreTypeStatus getStatus() {
        return status;
    }

    public void setStatus(commentScoreTypeStatus status) {
        this.status = status;
    }

}
