package com.data.spider.service.pojo.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/10/27.
 */
@Entity
@javax.persistence.Table(name = "data_recplan_day")
public class DataRecplanDay extends com.framework.hibernate.util.Entity {
    private long id;

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Long recplanId;

    @Basic
    @javax.persistence.Column(name = "recplan_id", nullable = true, insertable = true, updatable = true)
    public Long getRecplanId() {
        return recplanId;
    }

    public void setRecplanId(Long recplanId) {
        this.recplanId = recplanId;
    }

    private Short day;

    @Basic
    @javax.persistence.Column(name = "day", nullable = true, insertable = true, updatable = true)
    public Short getDay() {
        return day;
    }

    public void setDay(Short day) {
        this.day = day;
    }

    private Timestamp modifyTime;

    @Basic
    @javax.persistence.Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    private Timestamp createTime;

    @Basic
    @javax.persistence.Column(name = "create_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private Integer scenics;

    @Basic
    @javax.persistence.Column(name = "scenics", nullable = true, insertable = true, updatable = true)
    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    private Integer hours;

    @Basic
    @javax.persistence.Column(name = "hours", nullable = true, insertable = true, updatable = true)
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    private String citys;

    @Basic
    @javax.persistence.Column(name = "citys", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
    }

    private String description;

    @Basic
    @javax.persistence.Column(name = "description", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String hotel;

    @Basic
    @javax.persistence.Column(name = "hotel", nullable = true, insertable = true, updatable = true, length = 255)
    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    private String food;

    @Basic
    @javax.persistence.Column(name = "food", nullable = true, insertable = true, updatable = true, length = 255)
    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    private Double ticketCost;

    @Basic
    @javax.persistence.Column(name = "ticket_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Double ticketCost) {
        this.ticketCost = ticketCost;
    }

    private Double seasonticketCost;

    @Basic
    @javax.persistence.Column(name = "seasonticket_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getSeasonticketCost() {
        return seasonticketCost;
    }

    public void setSeasonticketCost(Double seasonticketCost) {
        this.seasonticketCost = seasonticketCost;
    }

    private Double trafficCost;

    @Basic
    @javax.persistence.Column(name = "traffic_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getTrafficCost() {
        return trafficCost;
    }

    public void setTrafficCost(Double trafficCost) {
        this.trafficCost = trafficCost;
    }

    private Double hotelCost;

    @Basic
    @javax.persistence.Column(name = "hotel_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(Double hotelCost) {
        this.hotelCost = hotelCost;
    }

    private Integer trafficTime;

    @Basic
    @javax.persistence.Column(name = "traffic_time", nullable = true, insertable = true, updatable = true)
    public Integer getTrafficTime() {
        return trafficTime;
    }

    public void setTrafficTime(Integer trafficTime) {
        this.trafficTime = trafficTime;
    }

    private Double cost;

    @Basic
    @javax.persistence.Column(name = "cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    private Double includeSeasonticketCost;

    @Basic
    @javax.persistence.Column(name = "include_seasonticket_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getIncludeSeasonticketCost() {
        return includeSeasonticketCost;
    }

    public void setIncludeSeasonticketCost(Double includeSeasonticketCost) {
        this.includeSeasonticketCost = includeSeasonticketCost;
    }

    private Double foodCost;

    @Basic
    @javax.persistence.Column(name = "food_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public Double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Double foodCost) {
        this.foodCost = foodCost;
    }

    private String isLast;

    @Basic
    @javax.persistence.Column(name = "isLast", nullable = true, insertable = true, updatable = true, length = 255)
    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataRecplanDay that = (DataRecplanDay) o;

        if (id != that.id) return false;
        if (recplanId != null ? !recplanId.equals(that.recplanId) : that.recplanId != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (scenics != null ? !scenics.equals(that.scenics) : that.scenics != null) return false;
        if (hours != null ? !hours.equals(that.hours) : that.hours != null) return false;
        if (citys != null ? !citys.equals(that.citys) : that.citys != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (hotel != null ? !hotel.equals(that.hotel) : that.hotel != null) return false;
        if (food != null ? !food.equals(that.food) : that.food != null) return false;
        if (ticketCost != null ? !ticketCost.equals(that.ticketCost) : that.ticketCost != null) return false;
        if (seasonticketCost != null ? !seasonticketCost.equals(that.seasonticketCost) : that.seasonticketCost != null)
            return false;
        if (trafficCost != null ? !trafficCost.equals(that.trafficCost) : that.trafficCost != null) return false;
        if (hotelCost != null ? !hotelCost.equals(that.hotelCost) : that.hotelCost != null) return false;
        if (trafficTime != null ? !trafficTime.equals(that.trafficTime) : that.trafficTime != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (includeSeasonticketCost != null ? !includeSeasonticketCost.equals(that.includeSeasonticketCost) : that.includeSeasonticketCost != null)
            return false;
        if (foodCost != null ? !foodCost.equals(that.foodCost) : that.foodCost != null) return false;
        if (isLast != null ? !isLast.equals(that.isLast) : that.isLast != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (recplanId != null ? recplanId.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (scenics != null ? scenics.hashCode() : 0);
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        result = 31 * result + (citys != null ? citys.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (food != null ? food.hashCode() : 0);
        result = 31 * result + (ticketCost != null ? ticketCost.hashCode() : 0);
        result = 31 * result + (seasonticketCost != null ? seasonticketCost.hashCode() : 0);
        result = 31 * result + (trafficCost != null ? trafficCost.hashCode() : 0);
        result = 31 * result + (hotelCost != null ? hotelCost.hashCode() : 0);
        result = 31 * result + (trafficTime != null ? trafficTime.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (includeSeasonticketCost != null ? includeSeasonticketCost.hashCode() : 0);
        result = 31 * result + (foodCost != null ? foodCost.hashCode() : 0);
        result = 31 * result + (isLast != null ? isLast.hashCode() : 0);
        return result;
    }
}
