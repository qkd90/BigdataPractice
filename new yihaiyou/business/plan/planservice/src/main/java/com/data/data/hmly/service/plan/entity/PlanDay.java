package com.data.data.hmly.service.plan.entity;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.traffic.entity.Traffic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "plan_day")
public class PlanDay extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
	private Plan plan;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private TbArea city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traffic_id")
    private Traffic traffic;
    @Column(name = "traffic_price_id")
    private Long trafficPriceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_traffic_id")
    private Traffic returnTraffic;
    @Column(name = "return_traffic_price_id")
    private Long returnTrafficPriceId;
    @Column(name = "spend_time")
	private String spendTime;
    @Column(name = "kilometre")
	private String kilometre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
	private Hotel hotel;
    @Column(name = "hotel_code")
    private String hotelCode;
    @Column(name = "date")
	private Date date;
    @Column(name = "days")
	private Integer days;
    @Column(name = "modify_time")
	private Date modifyTime;
    @Column(name = "create_time")
	private Date createTime;
    @Column(name = "ticket_cost")
	private Float ticketCost;
    @Column(name = "include_seasonticket_cost")
	private Float includeSeasonticketCost;
    @Column(name = "traffic_cost")
	private Float trafficCost;
	@Column(name = "return_traffic_cost")
	private Float returnTrafficCost;
    @Column(name = "hotel_cost")
	private Float hotelCost;
    @Column(name = "food_cost")
	private Float foodCost;
    @Column(name = "play_time")
	private Integer playTime;
    @Column(name = "traffic_time")
	private Integer trafficTime;
    @Column(name = "id_backup")
	private Long idBackup;
	@OneToMany(mappedBy = "planDay", fetch = FetchType.LAZY)
	private List<PlanTrip> planTripList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

    public Traffic getTraffic() {
        return traffic;
    }

    public void setTraffic(Traffic traffic) {
        this.traffic = traffic;
    }

    public String getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}

	public String getKilometre() {
		return kilometre;
	}

	public void setKilometre(String kilometre) {
		this.kilometre = kilometre;
	}

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(Float ticketCost) {
		this.ticketCost = ticketCost;
	}

	public Float getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(Float includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	public Float getTrafficCost() {
		return trafficCost;
	}

	public void setTrafficCost(Float trafficCost) {
		this.trafficCost = trafficCost;
	}

	public Float getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(Float hotelCost) {
		this.hotelCost = hotelCost;
	}

	public Float getFoodCost() {
		return foodCost;
	}

	public void setFoodCost(Float foodCost) {
		this.foodCost = foodCost;
	}

	public Integer getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}

	public Integer getTrafficTime() {
		return trafficTime;
	}

	public void setTrafficTime(Integer trafficTime) {
		this.trafficTime = trafficTime;
	}

	public Long getIdBackup() {
		return idBackup;
	}

	public void setIdBackup(Long idBackup) {
		this.idBackup = idBackup;
	}

	public List<PlanTrip> getPlanTripList() {
		return planTripList;
	}

	public void setPlanTripList(List<PlanTrip> planTripList) {
		this.planTripList = planTripList;
	}

    public Long getTrafficPriceId() {
        return trafficPriceId;
    }

    public void setTrafficPriceId(Long trafficPriceId) {
        this.trafficPriceId = trafficPriceId;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public Traffic getReturnTraffic() {
        return returnTraffic;
    }

    public void setReturnTraffic(Traffic returnTraffic) {
        this.returnTraffic = returnTraffic;
    }

    public Long getReturnTrafficPriceId() {
        return returnTrafficPriceId;
    }

    public void setReturnTrafficPriceId(Long returnTrafficPriceId) {
        this.returnTrafficPriceId = returnTrafficPriceId;
    }

	public Float getReturnTrafficCost() {
		return returnTrafficCost;
	}

	public void setReturnTrafficCost(Float returnTrafficCost) {
		this.returnTrafficCost = returnTrafficCost;
	}
}
