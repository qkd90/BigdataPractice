package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/17.
 */
@Entity
@Table(name = "tb_restaurant_comment")
public class TbRestaurantComment {
    private int id;
    private Long userId;
    private String nickname;
    private String userFace;
    private long restaurantId;
    private Integer tasteScore;
    private Integer environmentScore;
    private Integer serviceScore;
    private Integer averageScore;
    private String content;
    private Timestamp createTime;
    private Timestamp modifyTime;

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "nickname", nullable = true, insertable = true, updatable = true, length = 255)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "user_face", nullable = true, insertable = true, updatable = true, length = 255)
    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    @Basic
    @Column(name = "restaurant_id", nullable = false, insertable = true, updatable = true)
    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Basic
    @Column(name = "taste_score", nullable = true, insertable = true, updatable = true)
    public Integer getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(Integer tasteScore) {
        this.tasteScore = tasteScore;
    }

    @Basic
    @Column(name = "environment_score", nullable = true, insertable = true, updatable = true)
    public Integer getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(Integer environmentScore) {
        this.environmentScore = environmentScore;
    }

    @Basic
    @Column(name = "service_score", nullable = true, insertable = true, updatable = true)
    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    @Basic
    @Column(name = "average_score", nullable = true, insertable = true, updatable = true)
    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    @Basic
    @Column(name = "content", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbRestaurantComment that = (TbRestaurantComment) o;

        if (id != that.id) return false;
        if (restaurantId != that.restaurantId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (userFace != null ? !userFace.equals(that.userFace) : that.userFace != null) return false;
        if (tasteScore != null ? !tasteScore.equals(that.tasteScore) : that.tasteScore != null) return false;
        if (environmentScore != null ? !environmentScore.equals(that.environmentScore) : that.environmentScore != null)
            return false;
        if (serviceScore != null ? !serviceScore.equals(that.serviceScore) : that.serviceScore != null) return false;
        if (averageScore != null ? !averageScore.equals(that.averageScore) : that.averageScore != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (userFace != null ? userFace.hashCode() : 0);
        result = 31 * result + (int) (restaurantId ^ (restaurantId >>> 32));
        result = 31 * result + (tasteScore != null ? tasteScore.hashCode() : 0);
        result = 31 * result + (environmentScore != null ? environmentScore.hashCode() : 0);
        result = 31 * result + (serviceScore != null ? serviceScore.hashCode() : 0);
        result = 31 * result + (averageScore != null ? averageScore.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }
}
