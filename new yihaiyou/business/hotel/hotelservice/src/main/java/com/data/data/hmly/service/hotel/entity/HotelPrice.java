package com.data.data.hmly.service.hotel.entity;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.common.entity.interfaces.ProductPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/11.
 */
@Entity
@Table(name = "hotel_price")
public class HotelPrice extends com.framework.hibernate.util.Entity implements ProductPrice {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotelId")
	private Hotel hotel;
    @Column(name = "origin_id")
    private Long originId;

	@Column(name = "price")
	private Float price;
	@Column(name = "marketPrice")
	private Float marketPrice;
	@Column(name = "cPrice")
	private Float cprice;

	@Column(name = "date")
	private Date date;
	@Column(name = "modifyTime")
	private Date modifyTime;
	@Column(name = "createTime")
	private Date createTime;
	@Column(name = "breakfast")
	private Boolean breakfast;
	@Column(name = "ratePlanCode")
	private String ratePlanCode;
	@Column(name = "roomId")
	private String roomId;
	@Column(name = "roomTypeId")
	private String roomTypeId;
	@Column(name = "start")
	private Date start;
	@Column(name = "end")
	private Date end;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected PriceStatus status;
    @Column(name = "show_status")
    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus;
	@Column(name = "cancelStart")
	private Date cancelStart;
	@Column(name = "cancelEnd")
	private Date cancelEnd;
	@Column(name = "roomName")
	private String roomName;
	@Column(name = "roomDescription")
	private String roomDescription;
	@Column(name = "changeRule")
	private String changeRule;
	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "auditBy")
	private Long auditBy; // 审核人,
	@Column(name = "auditTime")
	private Date auditTime; // 审核时间,
	@Column(name = "auditReason")
	private String auditReason; // 审核原因,
//	@Column(name = "tourPlaceType")
	@Column(name = "showOrder")
	private Integer showOrder;

	// 页面字段
	@Transient
	private Long hotelId;
    @Transient
    private Long linedaysId;
	@Transient
	private String hotelName;
	@Transient
	private Boolean normalStatus;
    @Transient
    private String bntBedType;  // 床型：1-大床,2-双床,3-圆床
	@Transient
	private String statusStr;
    @Transient
    private Float priceLow;
    @Transient
    private Float priceHigh;
	@Transient
	private List<PriceStatus> priceStatusList;

	@Transient
	private List<HotelRoom> hotelRooms;

	@Transient
	private String roomNum;
	@Transient
	private Integer roomAmount;
    @Transient
	private String cover;

    public HotelPrice(Long id, Long hotelId, String hotelName, String roomName, Float price) {
        this.id = id;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomName = roomName;
        this.price = price;
    }

    public HotelPrice( String roomDescription, Float price, Date cancelStart, Date cancelEnd, Boolean breakfast) {
        this.price = price;
        this.breakfast = breakfast;
        this.cancelStart = cancelStart;
        this.cancelEnd = cancelEnd;
        this.roomDescription = roomDescription;
    }

    public HotelPrice() {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    @Override
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public ProductType getProductType() {
        if (hotel != null) {
            return hotel.getProType();
        }
        return null;
	}

	public void setDate(Date date) {
		this.date = date;
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

	@Override
	public Long getProductId() {
        if (hotel != null) {
            return hotel.getId();
        }
        return null;
	}

	@Override
	public Long getPriceId() {
		return id;
	}

	@Override
	public String getName() {
        if (hotel != null) {
            return hotel.getName();
        }
        return null;
	}

	public Boolean getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Boolean breakfast) {
		this.breakfast = breakfast;
	}

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

    public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public PriceStatus getStatus() {
		return status;
	}

	public void setStatus(PriceStatus status) {
		this.status = status;
	}

    public ShowStatus getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(ShowStatus showStatus) {
        this.showStatus = showStatus;
    }

    public Date getCancelStart() {
		return cancelStart;
	}

	public void setCancelStart(Date cancelStart) {
		this.cancelStart = cancelStart;
	}

	public Date getCancelEnd() {
		return cancelEnd;
	}

	public void setCancelEnd(Date cancelEnd) {
		this.cancelEnd = cancelEnd;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getChangeRule() {
		return changeRule;
	}

	public void setChangeRule(String changeRule) {
		this.changeRule = changeRule;
	}

    public Boolean getNormalStatus() {
        return normalStatus;
    }

    public void setNormalStatus(Boolean normalStatus) {
        this.normalStatus = normalStatus;
    }

    public String getBntBedType() {
        return bntBedType;
    }

    public void setBntBedType(String bntBedType) {
        this.bntBedType = bntBedType;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Float getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(Float priceLow) {
        this.priceLow = priceLow;
    }

    public Float getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(Float priceHigh) {
        this.priceHigh = priceHigh;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Long getLinedaysId() {
        return linedaysId;
    }

    public void setLinedaysId(Long linedaysId) {
        this.linedaysId = linedaysId;
    }

	public Float getCprice() {
		return cprice;
	}

	public void setCprice(Float cprice) {
		this.cprice = cprice;
	}

	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

	public List<PriceStatus> getPriceStatusList() {
		return priceStatusList;
	}

	public void setPriceStatusList(List<PriceStatus> priceStatusList) {
		this.priceStatusList = priceStatusList;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

    public Integer getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(Integer roomAmount) {
        this.roomAmount = roomAmount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<HotelRoom> getHotelRooms() {
		return hotelRooms;
	}

	public void setHotelRooms(List<HotelRoom> hotelRooms) {
		this.hotelRooms = hotelRooms;
	}

	public Long getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(Long auditBy) {
		this.auditBy = auditBy;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
}
