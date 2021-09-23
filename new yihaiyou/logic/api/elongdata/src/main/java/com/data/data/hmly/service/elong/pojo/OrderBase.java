//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.03 at 10:34:35 AM CST 
//


package com.data.data.hmly.service.elong.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>Java class for OrderBase complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="OrderBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HotelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RoomTypeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RatePlanId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ArrivalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CustomerType" type="{}EnumGuestTypeCode"/>
 *         &lt;element name="PaymentType" type="{}EnumPaymentType"/>
 *         &lt;element name="NumberOfRooms" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumberOfCustomers" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EarliestArrivalTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LatestArrivalTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CurrencyCode" type="{}EnumCurrencyCode"/>
 *         &lt;element name="TotalPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ConfirmationType" type="{}EnumConfirmationType"/>
 *         &lt;element name="NoteToHotel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoteToElong" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderBase", propOrder = {
        "hotelId",
        "roomTypeId",
        "ratePlanId",
        "arrivalDate",
        "departureDate",
        "customerType",
        "paymentType",
        "numberOfRooms",
        "numberOfCustomers",
        "earliestArrivalTime",
        "latestArrivalTime",
        "currencyCode",
        "totalPrice",
        "confirmationType",
        "noteToHotel",
        "noteToElong"
})
@XmlSeeAlso({
        OrderSummary.class,
        CreateOrderCondition.class,
        OrderDetailResult.class
})
public class OrderBase {

    @JSONField(name = "HotelId")
    protected String hotelId;
    @JSONField(name = "RoomTypeId")
    protected String roomTypeId;
    @JSONField(name = "RatePlanId")
    protected int ratePlanId;
    @JSONField(name = "ArrivalDate")
    @XmlSchemaType(name = "dateTime")
    protected java.util.Date arrivalDate;
    @JSONField(name = "DepartureDate")
    @XmlSchemaType(name = "dateTime")
    protected java.util.Date departureDate;
    @JSONField(name = "CustomerType")
    @XmlSchemaType(name = "string")
    protected EnumGuestTypeCode customerType;
    @JSONField(name = "PaymentType")
    @XmlSchemaType(name = "string")
    protected EnumPaymentType paymentType;
    @JSONField(name = "NumberOfRooms")
    protected int numberOfRooms;
    @JSONField(name = "NumberOfCustomers")
    protected int numberOfCustomers;
    @JSONField(name = "EarliestArrivalTime")
    @XmlSchemaType(name = "dateTime")
    protected java.util.Date earliestArrivalTime;
    @JSONField(name = "LatestArrivalTime")
    @XmlSchemaType(name = "dateTime")
    protected java.util.Date latestArrivalTime;
    @JSONField(name = "CurrencyCode")
    @XmlSchemaType(name = "string")
    protected EnumCurrencyCode currencyCode;
    @JSONField(name = "TotalPrice")
    protected BigDecimal totalPrice;
    @JSONField(name = "ConfirmationType")
    @XmlSchemaType(name = "string")
    protected EnumConfirmationType confirmationType;
    @JSONField(name = "NoteToHotel")
    protected String noteToHotel;
    @JSONField(name = "NoteToElong")
    protected String noteToElong;

    /**
     * Gets the value of the hotelId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getHotelId() {
        return hotelId;
    }

    /**
     * Sets the value of the hotelId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHotelId(String value) {
        this.hotelId = value;
    }

    /**
     * Gets the value of the roomTypeId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRoomTypeId() {
        return roomTypeId;
    }

    /**
     * Sets the value of the roomTypeId property.
     * V1.10更新：对应搜索接口(hotel.list、hotel.detail)接口中的RatePlan.RoomTypeId
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRoomTypeId(String value) {
        this.roomTypeId = value;
    }

    /**
     * Gets the value of the ratePlanId property.
     */
    public int getRatePlanId() {
        return ratePlanId;
    }

    /**
     * Sets the value of the ratePlanId property.
     */
    public void setRatePlanId(int value) {
        this.ratePlanId = value;
    }

    /**
     * Gets the value of the arrivalDate property.
     *
     * @return possible object is
     * {@link java.util.Date }
     */
    public java.util.Date getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 凌晨入住，入住日期是前一天
     *
     * @param value allowed object is
     *              {@link java.util.Date }
     */
    public void setArrivalDate(java.util.Date value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureDate property.
     *
     * @return possible object is
     * {@link java.util.Date }
     */
    public java.util.Date getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 建议入住日期和离店日期可以最长选择20天
     *
     * @param value allowed object is
     *              {@link java.util.Date }
     */
    public void setDepartureDate(java.util.Date value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the customerType property.
     *
     * @return possible object is
     * {@link EnumGuestTypeCode }
     */
    public EnumGuestTypeCode getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * All=统一价；
     * Chinese =内宾价；
     * OtherForeign =外宾价；
     * HongKong =港澳台客人价
     * Japanese=日本客人价
     * <p/>
     * 需要和RatePlanId对应的RatePlan（参考hotel.list#RatePlan、 hotel.detail#RatePlan、 hotel.data.rp#RatePlan）中的该属性一致。
     *
     * @param value allowed object is
     *              {@link EnumGuestTypeCode }
     */
    public void setCustomerType(EnumGuestTypeCode value) {
        this.customerType = value;
    }

    /**
     * Gets the value of the paymentType property.
     *
     * @return possible object is
     * {@link EnumPaymentType }
     */
    public EnumPaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * SelfPay-前台自付、Prepay-预付
     *
     * @param value allowed object is
     *              {@link EnumPaymentType }
     */
    public void setPaymentType(EnumPaymentType value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the numberOfRooms property.
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets the value of the numberOfRooms property.
     * 有几个房间数量，对应几个OrderRoom节点数组，每一订单房间数量最好不要超过5间
     */
    public void setNumberOfRooms(int value) {
        this.numberOfRooms = value;
    }

    /**
     * Gets the value of the numberOfCustomers property.
     */
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    /**
     * Sets the value of the numberOfCustomers property.
     */
    public void setNumberOfCustomers(int value) {
        this.numberOfCustomers = value;
    }

    /**
     * Gets the value of the earliestArrivalTime property.
     *
     * @return possible object is
     * {@link java.util.Date }
     */
    public java.util.Date getEarliestArrivalTime() {
        return earliestArrivalTime;
    }

    /**
     * Sets the value of the earliestArrivalTime property.
     * 1、两者都是必填字段,可让用户选择两个时间点，也可以只让客人选择最晚到店时间系统根据下面的规则计算出最早到店时间
     * 2、最早到店时间范围：入住日6:00(建议14:00,因一般酒店接待开始时间是14点)-23:59；
     * 　　最晚到店时间范围：入住日7:00-23:59和次日1:00-6:00，都必须是整点或半点或23:59
     * 3、最早到店时间须晚于当前时间, 最晚到店时间须晚于最早到店时间，一般相差3个小时
     * 4、如果客人到店时间是入住日期的第二天的00:00┅06:00之间，请配置最早到店时间为入住日期的23:59，最晚到店时间为入住日期的第二天的06:00
     * 5、更多信息请参考FAQ
     *
     * @param value allowed object is
     *              {@link java.util.Date }
     */
    public void setEarliestArrivalTime(java.util.Date value) {
        this.earliestArrivalTime = value;
    }

    /**
     * Gets the value of the latestArrivalTime property.
     *
     * @return possible object is
     * {@link java.util.Date }
     */
    public java.util.Date getLatestArrivalTime() {
        return latestArrivalTime;
    }

    /**
     * Sets the value of the latestArrivalTime property.
     *
     * @param value allowed object is
     *              {@link java.util.Date }
     */
    public void setLatestArrivalTime(java.util.Date value) {
        this.latestArrivalTime = value;
    }

    /**
     * Gets the value of the currencyCode property.
     *
     * @return possible object is
     * {@link EnumCurrencyCode }
     */
    public EnumCurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 请和获取价格的地方保持一致。 参考Currency
     *
     * @param value allowed object is
     *              {@link EnumCurrencyCode }
     */
    public void setCurrencyCode(EnumCurrencyCode value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the totalPrice property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * RatePlan的TotalPrice * 房间数；单位是元；房间单价可能是小数，总价也可能是带小数
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setTotalPrice(BigDecimal value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the confirmationType property.
     *
     * @return possible object is
     * {@link EnumConfirmationType }
     */
    public EnumConfirmationType getConfirmationType() {
        return confirmationType;
    }

    /**
     * Sets the value of the confirmationType property.
     * NotAllowedConfirm 不允许确认(合作伙伴自查订单状态后自行联系客人)
     * SMS_cn  ----艺龙发短信给客人,出现订单问题的时候会主动联系
     * NoNeed -- 艺龙发短信给客人,出现订单问题的时候不主动联系
     * 注：除了NotAllowedConfirm，其余的选项艺龙都会发送短信，下单时如果输入了邮箱那么都会发送邮件
     *
     * @param value allowed object is
     *              {@link EnumConfirmationType }
     */
    public void setConfirmationType(EnumConfirmationType value) {
        this.confirmationType = value;
    }

    /**
     * Gets the value of the noteToHotel property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNoteToHotel() {
        return noteToHotel;
    }

    /**
     * Sets the value of the noteToHotel property.
     * 客人给酒店的备注，尽量不填写，以免影响房间确认速度，并可减少投诉；若必须要填，尽量对要求进行规范，便于沟通和处理。
     * 香港澳门的酒店此处备注不能包含："务必", "一定", "必须", "非要", "只能", "最晚", "百分之百", "不然就不去住"
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNoteToHotel(String value) {
        this.noteToHotel = value;
    }

    /**
     * Gets the value of the noteToElong property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNoteToElong() {
        return noteToElong;
    }

    /**
     * Sets the value of the noteToElong property.
     * 给艺龙备注
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNoteToElong(String value) {
        this.noteToElong = value;
    }

}
