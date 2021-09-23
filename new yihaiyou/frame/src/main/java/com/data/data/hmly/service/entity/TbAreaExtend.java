package com.data.data.hmly.service.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "tb_area_extend")
public class TbAreaExtend extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private TbArea tbArea;
    @Column(name = "best_visit_time")
    private String bestVisitTime;
    @Column(name = "advice_time")
    private String adviceTime;
    @Column(name = "abs")
    private String abs;
    @Column(name = "history")
    private String history;
    @Column(name = "art")
    private String art;
    @Column(name = "weather")
    private String weather;
    @Column(name = "geography")
    private String geography;
    @Column(name = "environment")
    private String environment;
    @Column(name = "culture")
    private String culture;
    @Column(name = "language")
    private String language;
    @Column(name = "festival")
    private String festival;
    @Column(name = "religion")
    private String religion;
    @Column(name = "nation")
    private String nation;
    @Column(name = "cover")
    private String cover;

    public TbArea getTbArea() {
        return tbArea;
    }

    public void setTbArea(TbArea tbArea) {
        this.tbArea = tbArea;
    }

    public String getBestVisitTime() {
        return bestVisitTime;
    }

    public void setBestVisitTime(String bestVisitTime) {
        this.bestVisitTime = bestVisitTime;
    }

    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
