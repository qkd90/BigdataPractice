package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sane on 15/12/16.
 */
@Entity
@Table(name = "tb_area_extend")
public class TbAreaExtend  extends com.framework.hibernate.util.Entity {
    private int id;
    private String bestVisitTime;
    private String adviceTime;
    private String abs;
    private String history;
    private String art;
    private String weather;
    private String geography;
    private String environment;
    private String culture;
    private String language;
    private String festival;
    private String religion;
    private String nation;
    private String cover;

    @Id
//    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "best_visit_time", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getBestVisitTime() {
        return bestVisitTime;
    }

    public void setBestVisitTime(String bestVisitTime) {
        this.bestVisitTime = bestVisitTime;
    }

    @Basic
    @Column(name = "advice_time", nullable = true, insertable = true, updatable = true, length = 200)
    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    @Basic
    @Column(name = "abs", nullable = true, insertable = true, updatable = true, length = 5000)
    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }



    @Basic
    @Column(name = "history", nullable = true, insertable = true, updatable = true, length = 500)
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Basic
    @Column(name = "art", nullable = true, insertable = true, updatable = true, length = 500)
    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    @Basic
    @Column(name = "weather", nullable = true, insertable = true, updatable = true, length = 500)
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Basic
    @Column(name = "geography", nullable = true, insertable = true, updatable = true, length = 500)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @Basic
    @Column(name = "environment", nullable = true, insertable = true, updatable = true, length = 500)
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Basic
    @Column(name = "culture", nullable = true, insertable = true, updatable = true, length = 500)
    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    @Basic
    @Column(name = "language", nullable = true, insertable = true, updatable = true, length = 500)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "festival", nullable = true, insertable = true, updatable = true, length = 500)
    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    @Basic
    @Column(name = "religion", nullable = true, insertable = true, updatable = true, length = 500)
    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    @Basic
    @Column(name = "nation", nullable = true, insertable = true, updatable = true, length = 500)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }


    @Basic
    @Column(name = "cover", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbAreaExtend that = (TbAreaExtend) o;

        if (id != that.id) return false;
        if (bestVisitTime != null ? !bestVisitTime.equals(that.bestVisitTime) : that.bestVisitTime != null)
            return false;
        if (adviceTime != null ? !adviceTime.equals(that.adviceTime) : that.adviceTime != null) return false;
        if (abs != null ? !abs.equals(that.abs) : that.abs != null) return false;
        if (history != null ? !history.equals(that.history) : that.history != null) return false;
        if (art != null ? !art.equals(that.art) : that.art != null) return false;
        if (weather != null ? !weather.equals(that.weather) : that.weather != null) return false;
        if (geography != null ? !geography.equals(that.geography) : that.geography != null) return false;
        if (environment != null ? !environment.equals(that.environment) : that.environment != null) return false;
        if (culture != null ? !culture.equals(that.culture) : that.culture != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (festival != null ? !festival.equals(that.festival) : that.festival != null) return false;
        if (religion != null ? !religion.equals(that.religion) : that.religion != null) return false;
        if (nation != null ? !nation.equals(that.nation) : that.nation != null) return false;
        if (cover != null ? !cover.equals(that.cover) : that.cover != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (bestVisitTime != null ? bestVisitTime.hashCode() : 0);
        result = 31 * result + (adviceTime != null ? adviceTime.hashCode() : 0);
        result = 31 * result + (abs != null ? abs.hashCode() : 0);
        result = 31 * result + (history != null ? history.hashCode() : 0);
        result = 31 * result + (art != null ? art.hashCode() : 0);
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (geography != null ? geography.hashCode() : 0);
        result = 31 * result + (environment != null ? environment.hashCode() : 0);
        result = 31 * result + (culture != null ? culture.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (festival != null ? festival.hashCode() : 0);
        result = 31 * result + (religion != null ? religion.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }
}
