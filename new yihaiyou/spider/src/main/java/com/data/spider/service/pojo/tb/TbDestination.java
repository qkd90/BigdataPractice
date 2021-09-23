package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/12/18.
 */
@Entity
@Table(name = "tb_destination")
public class TbDestination extends com.framework.hibernate.util.Entity {
    private long id;
    private Long scenicId;
    private String name;
    private String codeName;
    private String cityCode;
    private double latitude;
    private Integer mapLevel;
    private Integer hdMinLevel;
    private Integer hdMaxLevel;
    private Timestamp modifyTime;
    private Timestamp createTime;
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

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "scenic_id", nullable = true, insertable = true, updatable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code_name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Basic
    @Column(name = "city_code", nullable = true, insertable = true, updatable = true, length = 20)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "latitude", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "map_level", nullable = true, insertable = true, updatable = true)
    public Integer getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(Integer mapLevel) {
        this.mapLevel = mapLevel;
    }

    @Basic
    @Column(name = "hd_min_level", nullable = true, insertable = true, updatable = true)
    public Integer getHdMinLevel() {
        return hdMinLevel;
    }

    public void setHdMinLevel(Integer hdMinLevel) {
        this.hdMinLevel = hdMinLevel;
    }

    @Basic
    @Column(name = "hd_max_level", nullable = true, insertable = true, updatable = true)
    public Integer getHdMaxLevel() {
        return hdMaxLevel;
    }

    public void setHdMaxLevel(Integer hdMaxLevel) {
        this.hdMaxLevel = hdMaxLevel;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "create_time", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "best_visit_time", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getBestVisitTime() {
        return bestVisitTime;
    }

    public void setBestVisitTime(String bestVisitTime) {
        this.bestVisitTime = bestVisitTime;
    }

    @Basic
    @Column(name = "advice_time", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    @Basic
    @Column(name = "abs", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    @Basic
    @Column(name = "history", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Basic
    @Column(name = "art", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    @Basic
    @Column(name = "weather", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Basic
    @Column(name = "geography", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @Basic
    @Column(name = "environment", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Basic
    @Column(name = "culture", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    @Basic
    @Column(name = "language", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "festival", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }



    @Basic
    @Column(name = "religion", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    @Basic
    @Column(name = "nation", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbDestination that = (TbDestination) o;

        if (id != that.id) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (scenicId != null ? !scenicId.equals(that.scenicId) : that.scenicId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (codeName != null ? !codeName.equals(that.codeName) : that.codeName != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (mapLevel != null ? !mapLevel.equals(that.mapLevel) : that.mapLevel != null) return false;
        if (hdMinLevel != null ? !hdMinLevel.equals(that.hdMinLevel) : that.hdMinLevel != null) return false;
        if (hdMaxLevel != null ? !hdMaxLevel.equals(that.hdMaxLevel) : that.hdMaxLevel != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
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

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (codeName != null ? codeName.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mapLevel != null ? mapLevel.hashCode() : 0);
        result = 31 * result + (hdMinLevel != null ? hdMinLevel.hashCode() : 0);
        result = 31 * result + (hdMaxLevel != null ? hdMaxLevel.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
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
        return result;
    }
}
