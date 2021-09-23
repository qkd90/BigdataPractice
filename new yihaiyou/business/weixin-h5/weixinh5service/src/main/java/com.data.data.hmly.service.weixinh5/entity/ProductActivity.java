package com.data.data.hmly.service.weixinh5.entity;

import com.data.data.hmly.service.line.entity.Line;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by dy on 2016/2/17.
 */
@Entity
@Table(name = "product_activity")
public class ProductActivity extends com.framework.hibernate.util.Entity implements Serializable{

    private static final long serialVersionUID = -1690906106930903058L;


//    `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `activity_id` bigint(20) DEFAULT NULL COMMENT '活动编号',
//    `product_id` bigint(20) DEFAULT NULL COMMENT '产品编号',
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;                                //编号

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activities activitie;                   //活动编号


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Line line;                        //产品编号


    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activities getActivitie() {
        return activitie;
    }

    public void setActivitie(Activities activitie) {
        this.activitie = activitie;
    }


}
