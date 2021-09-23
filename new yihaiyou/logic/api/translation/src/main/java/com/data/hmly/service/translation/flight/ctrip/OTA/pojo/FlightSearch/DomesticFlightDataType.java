//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 01:46:02 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DomesticFlightDataType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DomesticFlightDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DepartCityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ArriveCityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TakeOffTime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="2013-05-20T07:55:00"/>
 *               &lt;enumeration value="2013-05-20T08:55:00"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ArriveTime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="2013-05-20T10:15:00"/>
 *               &lt;enumeration value="2013-05-20T11:15:00"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Flight">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="CA1858"/>
 *               &lt;enumeration value="CA1590"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CraftType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirlineCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Class">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SubClass">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="B"/>
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="W"/>
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="A"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DisplaySubclass">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="B"/>
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="W"/>
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="A"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Rate">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="0.90"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="0.82"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Price">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1020"/>
 *               &lt;enumeration value="1130"/>
 *               &lt;enumeration value="3160"/>
 *               &lt;enumeration value="2600"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="StandardPrice">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1130.0000"/>
 *               &lt;enumeration value="3160.0000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ChildStandardPrice">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="570"/>
 *               &lt;enumeration value="1580"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="BabyStandardPrice">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="110"/>
 *               &lt;enumeration value="320"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MealType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AdultTax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BabyTax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChildTax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AdultOilFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BabyOilFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChildOilFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DPortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="APortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DPortBuildingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="APortBuildingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StopTimes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nonrer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nonend">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="H"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Nonref" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Rernote">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="起飞（含）前同等舱位更改需收取票面价10％的更改费，起飞后需收取票面价20％的更改费，改期费与升舱费同时发生时，则需同时收取改期费和升舱差额。收费更改请到国航直属售票处办理。"/>
 *               &lt;enumeration value="起飞（含）前同等舱位免费更改，起飞后需收取票面价5％的更改费，改期费与升舱费同时发生时，则需同时收取改期费和升舱差额。收费更改请到国航直属售票处办理。"/>
 *               &lt;enumeration value="同舱同价更改每次收取公布运价5%的改期费，同舱不同价补齐差价并收取公布运价5%改期费，收费改期至国航直属售票处或致电国航客服办理。"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Endnote">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="不得签转。"/>
 *               &lt;enumeration value="允许签转，如变更后承运人适用票价高于国航票价，需补齐差额后进行变更，若低于国航票价，差额不退。"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Refnote">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="起飞（含）前需收取票面价20％的退票费，起飞后需收取票面价30％的退票费。"/>
 *               &lt;enumeration value="起飞（含）前需收取票面价5％的退票费，起飞后需收取票面价10％的退票费（婴儿、儿童免收退票费）。"/>
 *               &lt;enumeration value="起飞（含）前免收退票费，起飞后需收取票面价10％的退票费（婴儿、儿童免收退票费）。"/>
 *               &lt;enumeration value="需收取公布运价10%退票费。"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Remarks">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="儿童加CHD"/>
 *               &lt;enumeration value="特殊舱位儿童加CHD。"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TicketType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BeforeFlyDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Quantity">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="6"/>
 *               &lt;enumeration value="10"/>
 *               &lt;enumeration value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PriceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InventoryType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RouteIndex" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NeedApplyString" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Recommend" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RefundFeeFormulaID">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="16"/>
 *               &lt;enumeration value="118"/>
 *               &lt;enumeration value="117"/>
 *               &lt;enumeration value="53"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CanUpGrade" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CanSeparateSale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CanNoDefer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsFlyMan" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OnlyOwnCity">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="false"/>
 *               &lt;enumeration value="true"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IsLowestPrice">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="true"/>
 *               &lt;enumeration value="false"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IsLowestCZSpecialPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PunctualityRate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PolicyID">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="496006"/>
 *               &lt;enumeration value="496019"/>
 *               &lt;enumeration value="496018"/>
 *               &lt;enumeration value="496009"/>
 *               &lt;enumeration value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AllowCPType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OutOfPostTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OutOfSendGetTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OutOfAirlineCounterTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CanPost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CanAirlineCounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CanSendGet" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsRebate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RebateAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RebateCPCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DomesticFlightDataType", propOrder = {
    "departCityCode",
    "arriveCityCode",
    "takeOffTime",
    "arriveTime",
    "flight",
    "craftType",
    "airlineCode",
    "clazz",
    "subClass",
    "displaySubclass",
    "rate",
    "price",
    "standardPrice",
    "childStandardPrice",
    "babyStandardPrice",
    "mealType",
    "adultTax",
    "babyTax",
    "childTax",
    "adultOilFee",
    "babyOilFee",
    "childOilFee",
    "dPortCode",
    "aPortCode",
    "dPortBuildingID",
    "aPortBuildingID",
    "stopTimes",
    "nonrer",
    "nonend",
    "nonref",
    "rernote",
    "endnote",
    "refnote",
    "remarks",
    "ticketType",
    "beforeFlyDate",
    "quantity",
    "priceType",
    "productType",
    "productSource",
    "inventoryType",
    "routeIndex",
    "needApplyString",
    "recommend",
    "refundFeeFormulaID",
    "canUpGrade",
    "canSeparateSale",
    "canNoDefer",
    "isFlyMan",
    "onlyOwnCity",
    "isLowestPrice",
    "isLowestCZSpecialPrice",
    "punctualityRate",
    "policyID",
    "allowCPType",
    "outOfPostTime",
    "outOfSendGetTime",
    "outOfAirlineCounterTime",
    "canPost",
    "canAirlineCounter",
    "canSendGet",
    "isRebate",
    "rebateAmount",
    "rebateCPCity"
})
public class DomesticFlightDataType {

    @XmlElement(name = "DepartCityCode", required = true)
    protected String departCityCode;
    @XmlElement(name = "ArriveCityCode", required = true)
    protected String arriveCityCode;
    @XmlElement(name = "TakeOffTime", required = true)
    protected String takeOffTime;
    @XmlElement(name = "ArriveTime", required = true)
    protected String arriveTime;
    @XmlElement(name = "Flight", required = true)
    protected String flight;
    @XmlElement(name = "CraftType", required = true)
    protected String craftType;
    @XmlElement(name = "AirlineCode", required = true)
    protected String airlineCode;
    @XmlElement(name = "Class", required = true)
    protected String clazz;
    @XmlElement(name = "SubClass", required = true)
    protected String subClass;
    @XmlElement(name = "DisplaySubclass", required = true)
    protected String displaySubclass;
    @XmlElement(name = "Rate", required = true)
    protected String rate;
    @XmlElement(name = "Price", required = true)
    protected String price;
    @XmlElement(name = "StandardPrice", required = true)
    protected String standardPrice;
    @XmlElement(name = "ChildStandardPrice", required = true)
    protected String childStandardPrice;
    @XmlElement(name = "BabyStandardPrice", required = true)
    protected String babyStandardPrice;
    @XmlElement(name = "MealType", required = true)
    protected String mealType;
    @XmlElement(name = "AdultTax", required = true)
    protected String adultTax;
    @XmlElement(name = "BabyTax", required = true)
    protected String babyTax;
    @XmlElement(name = "ChildTax", required = true)
    protected String childTax;
    @XmlElement(name = "AdultOilFee", required = true)
    protected String adultOilFee;
    @XmlElement(name = "BabyOilFee", required = true)
    protected String babyOilFee;
    @XmlElement(name = "ChildOilFee", required = true)
    protected String childOilFee;
    @XmlElement(name = "DPortCode", required = true)
    protected String dPortCode;
    @XmlElement(name = "APortCode", required = true)
    protected String aPortCode;
    @XmlElement(name = "DPortBuildingID", required = true)
    protected String dPortBuildingID;
    @XmlElement(name = "APortBuildingID", required = true)
    protected String aPortBuildingID;
    @XmlElement(name = "StopTimes", required = true)
    protected String stopTimes;
    @XmlElement(name = "Nonrer", required = true)
    protected String nonrer;
    @XmlElement(name = "Nonend", required = true)
    protected String nonend;
    @XmlElement(name = "Nonref", required = true)
    protected String nonref;
    @XmlElement(name = "Rernote", required = true)
    protected String rernote;
    @XmlElement(name = "Endnote", required = true)
    protected String endnote;
    @XmlElement(name = "Refnote", required = true)
    protected String refnote;
    @XmlElement(name = "Remarks", required = true)
    protected String remarks;
    @XmlElement(name = "TicketType", required = true)
    protected String ticketType;
    @XmlElement(name = "BeforeFlyDate", required = true)
    protected String beforeFlyDate;
    @XmlElement(name = "Quantity", required = true)
    protected String quantity;
    @XmlElement(name = "PriceType", required = true)
    protected String priceType;
    @XmlElement(name = "ProductType", required = true)
    protected String productType;
    @XmlElement(name = "ProductSource", required = true)
    protected String productSource;
    @XmlElement(name = "InventoryType", required = true)
    protected String inventoryType;
    @XmlElement(name = "RouteIndex", required = true)
    protected String routeIndex;
    @XmlElement(name = "NeedApplyString", required = true)
    protected String needApplyString;
    @XmlElement(name = "Recommend", required = true)
    protected String recommend;
    @XmlElement(name = "RefundFeeFormulaID", required = true)
    protected String refundFeeFormulaID;
    @XmlElement(name = "CanUpGrade", required = true)
    protected String canUpGrade;
    @XmlElement(name = "CanSeparateSale", required = true)
    protected String canSeparateSale;
    @XmlElement(name = "CanNoDefer", required = true)
    protected String canNoDefer;
    @XmlElement(name = "IsFlyMan", required = true)
    protected String isFlyMan;
    @XmlElement(name = "OnlyOwnCity", required = true)
    protected String onlyOwnCity;
    @XmlElement(name = "IsLowestPrice", required = true)
    protected String isLowestPrice;
    @XmlElement(name = "IsLowestCZSpecialPrice", required = true)
    protected String isLowestCZSpecialPrice;
    @XmlElement(name = "PunctualityRate", required = true)
    protected String punctualityRate;
    @XmlElement(name = "PolicyID", required = true)
    protected String policyID;
    @XmlElement(name = "AllowCPType", required = true)
    protected String allowCPType;
    @XmlElement(name = "OutOfPostTime", required = true)
    protected String outOfPostTime;
    @XmlElement(name = "OutOfSendGetTime", required = true)
    protected String outOfSendGetTime;
    @XmlElement(name = "OutOfAirlineCounterTime", required = true)
    protected String outOfAirlineCounterTime;
    @XmlElement(name = "CanPost", required = true)
    protected String canPost;
    @XmlElement(name = "CanAirlineCounter", required = true)
    protected String canAirlineCounter;
    @XmlElement(name = "CanSendGet", required = true)
    protected String canSendGet;
    @XmlElement(name = "IsRebate", required = true)
    protected String isRebate;
    @XmlElement(name = "RebateAmount", required = true)
    protected String rebateAmount;
    @XmlElement(name = "RebateCPCity", required = true)
    protected String rebateCPCity;

    /**
     * 获取departCityCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartCityCode() {
        return departCityCode;
    }

    /**
     * 设置departCityCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartCityCode(String value) {
        this.departCityCode = value;
    }

    /**
     * 获取arriveCityCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArriveCityCode() {
        return arriveCityCode;
    }

    /**
     * 设置arriveCityCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArriveCityCode(String value) {
        this.arriveCityCode = value;
    }

    /**
     * 获取takeOffTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTakeOffTime() {
        return takeOffTime;
    }

    /**
     * 设置takeOffTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTakeOffTime(String value) {
        this.takeOffTime = value;
    }

    /**
     * 获取arriveTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArriveTime() {
        return arriveTime;
    }

    /**
     * 设置arriveTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArriveTime(String value) {
        this.arriveTime = value;
    }

    /**
     * 获取flight属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlight() {
        return flight;
    }

    /**
     * 设置flight属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlight(String value) {
        this.flight = value;
    }

    /**
     * 获取craftType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCraftType() {
        return craftType;
    }

    /**
     * 设置craftType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCraftType(String value) {
        this.craftType = value;
    }

    /**
     * 获取airlineCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * 设置airlineCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirlineCode(String value) {
        this.airlineCode = value;
    }

    /**
     * 获取clazz属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * 设置clazz属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * 获取subClass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubClass() {
        return subClass;
    }

    /**
     * 设置subClass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubClass(String value) {
        this.subClass = value;
    }

    /**
     * 获取displaySubclass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplaySubclass() {
        return displaySubclass;
    }

    /**
     * 设置displaySubclass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplaySubclass(String value) {
        this.displaySubclass = value;
    }

    /**
     * 获取rate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRate() {
        return rate;
    }

    /**
     * 设置rate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRate(String value) {
        this.rate = value;
    }

    /**
     * 获取price属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置price属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * 获取standardPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardPrice() {
        return standardPrice;
    }

    /**
     * 设置standardPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardPrice(String value) {
        this.standardPrice = value;
    }

    /**
     * 获取childStandardPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildStandardPrice() {
        return childStandardPrice;
    }

    /**
     * 设置childStandardPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildStandardPrice(String value) {
        this.childStandardPrice = value;
    }

    /**
     * 获取babyStandardPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBabyStandardPrice() {
        return babyStandardPrice;
    }

    /**
     * 设置babyStandardPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBabyStandardPrice(String value) {
        this.babyStandardPrice = value;
    }

    /**
     * 获取mealType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMealType() {
        return mealType;
    }

    /**
     * 设置mealType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMealType(String value) {
        this.mealType = value;
    }

    /**
     * 获取adultTax属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdultTax() {
        return adultTax;
    }

    /**
     * 设置adultTax属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdultTax(String value) {
        this.adultTax = value;
    }

    /**
     * 获取babyTax属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBabyTax() {
        return babyTax;
    }

    /**
     * 设置babyTax属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBabyTax(String value) {
        this.babyTax = value;
    }

    /**
     * 获取childTax属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildTax() {
        return childTax;
    }

    /**
     * 设置childTax属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildTax(String value) {
        this.childTax = value;
    }

    /**
     * 获取adultOilFee属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdultOilFee() {
        return adultOilFee;
    }

    /**
     * 设置adultOilFee属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdultOilFee(String value) {
        this.adultOilFee = value;
    }

    /**
     * 获取babyOilFee属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBabyOilFee() {
        return babyOilFee;
    }

    /**
     * 设置babyOilFee属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBabyOilFee(String value) {
        this.babyOilFee = value;
    }

    /**
     * 获取childOilFee属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildOilFee() {
        return childOilFee;
    }

    /**
     * 设置childOilFee属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildOilFee(String value) {
        this.childOilFee = value;
    }

    /**
     * 获取dPortCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPortCode() {
        return dPortCode;
    }

    /**
     * 设置dPortCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPortCode(String value) {
        this.dPortCode = value;
    }

    /**
     * 获取aPortCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPortCode() {
        return aPortCode;
    }

    /**
     * 设置aPortCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPortCode(String value) {
        this.aPortCode = value;
    }

    /**
     * 获取dPortBuildingID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPortBuildingID() {
        return dPortBuildingID;
    }

    /**
     * 设置dPortBuildingID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPortBuildingID(String value) {
        this.dPortBuildingID = value;
    }

    /**
     * 获取aPortBuildingID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPortBuildingID() {
        return aPortBuildingID;
    }

    /**
     * 设置aPortBuildingID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPortBuildingID(String value) {
        this.aPortBuildingID = value;
    }

    /**
     * 获取stopTimes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStopTimes() {
        return stopTimes;
    }

    /**
     * 设置stopTimes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopTimes(String value) {
        this.stopTimes = value;
    }

    /**
     * 获取nonrer属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonrer() {
        return nonrer;
    }

    /**
     * 设置nonrer属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonrer(String value) {
        this.nonrer = value;
    }

    /**
     * 获取nonend属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonend() {
        return nonend;
    }

    /**
     * 设置nonend属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonend(String value) {
        this.nonend = value;
    }

    /**
     * 获取nonref属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonref() {
        return nonref;
    }

    /**
     * 设置nonref属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonref(String value) {
        this.nonref = value;
    }

    /**
     * 获取rernote属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRernote() {
        return rernote;
    }

    /**
     * 设置rernote属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRernote(String value) {
        this.rernote = value;
    }

    /**
     * 获取endnote属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndnote() {
        return endnote;
    }

    /**
     * 设置endnote属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndnote(String value) {
        this.endnote = value;
    }

    /**
     * 获取refnote属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefnote() {
        return refnote;
    }

    /**
     * 设置refnote属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefnote(String value) {
        this.refnote = value;
    }

    /**
     * 获取remarks属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置remarks属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * 获取ticketType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketType() {
        return ticketType;
    }

    /**
     * 设置ticketType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketType(String value) {
        this.ticketType = value;
    }

    /**
     * 获取beforeFlyDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeforeFlyDate() {
        return beforeFlyDate;
    }

    /**
     * 设置beforeFlyDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeforeFlyDate(String value) {
        this.beforeFlyDate = value;
    }

    /**
     * 获取quantity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * 设置quantity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * 获取priceType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceType() {
        return priceType;
    }

    /**
     * 设置priceType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceType(String value) {
        this.priceType = value;
    }

    /**
     * 获取productType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductType() {
        return productType;
    }

    /**
     * 设置productType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    /**
     * 获取productSource属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductSource() {
        return productSource;
    }

    /**
     * 设置productSource属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductSource(String value) {
        this.productSource = value;
    }

    /**
     * 获取inventoryType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInventoryType() {
        return inventoryType;
    }

    /**
     * 设置inventoryType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInventoryType(String value) {
        this.inventoryType = value;
    }

    /**
     * 获取routeIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteIndex() {
        return routeIndex;
    }

    /**
     * 设置routeIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteIndex(String value) {
        this.routeIndex = value;
    }

    /**
     * 获取needApplyString属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNeedApplyString() {
        return needApplyString;
    }

    /**
     * 设置needApplyString属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNeedApplyString(String value) {
        this.needApplyString = value;
    }

    /**
     * 获取recommend属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * 设置recommend属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommend(String value) {
        this.recommend = value;
    }

    /**
     * 获取refundFeeFormulaID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefundFeeFormulaID() {
        return refundFeeFormulaID;
    }

    /**
     * 设置refundFeeFormulaID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefundFeeFormulaID(String value) {
        this.refundFeeFormulaID = value;
    }

    /**
     * 获取canUpGrade属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanUpGrade() {
        return canUpGrade;
    }

    /**
     * 设置canUpGrade属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanUpGrade(String value) {
        this.canUpGrade = value;
    }

    /**
     * 获取canSeparateSale属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanSeparateSale() {
        return canSeparateSale;
    }

    /**
     * 设置canSeparateSale属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanSeparateSale(String value) {
        this.canSeparateSale = value;
    }

    /**
     * 获取canNoDefer属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanNoDefer() {
        return canNoDefer;
    }

    /**
     * 设置canNoDefer属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanNoDefer(String value) {
        this.canNoDefer = value;
    }

    /**
     * 获取isFlyMan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFlyMan() {
        return isFlyMan;
    }

    /**
     * 设置isFlyMan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFlyMan(String value) {
        this.isFlyMan = value;
    }

    /**
     * 获取onlyOwnCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlyOwnCity() {
        return onlyOwnCity;
    }

    /**
     * 设置onlyOwnCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlyOwnCity(String value) {
        this.onlyOwnCity = value;
    }

    /**
     * 获取isLowestPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsLowestPrice() {
        return isLowestPrice;
    }

    /**
     * 设置isLowestPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsLowestPrice(String value) {
        this.isLowestPrice = value;
    }

    /**
     * 获取isLowestCZSpecialPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsLowestCZSpecialPrice() {
        return isLowestCZSpecialPrice;
    }

    /**
     * 设置isLowestCZSpecialPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsLowestCZSpecialPrice(String value) {
        this.isLowestCZSpecialPrice = value;
    }

    /**
     * 获取punctualityRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPunctualityRate() {
        return punctualityRate;
    }

    /**
     * 设置punctualityRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPunctualityRate(String value) {
        this.punctualityRate = value;
    }

    /**
     * 获取policyID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyID() {
        return policyID;
    }

    /**
     * 设置policyID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyID(String value) {
        this.policyID = value;
    }

    /**
     * 获取allowCPType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllowCPType() {
        return allowCPType;
    }

    /**
     * 设置allowCPType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllowCPType(String value) {
        this.allowCPType = value;
    }

    /**
     * 获取outOfPostTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutOfPostTime() {
        return outOfPostTime;
    }

    /**
     * 设置outOfPostTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutOfPostTime(String value) {
        this.outOfPostTime = value;
    }

    /**
     * 获取outOfSendGetTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutOfSendGetTime() {
        return outOfSendGetTime;
    }

    /**
     * 设置outOfSendGetTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutOfSendGetTime(String value) {
        this.outOfSendGetTime = value;
    }

    /**
     * 获取outOfAirlineCounterTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutOfAirlineCounterTime() {
        return outOfAirlineCounterTime;
    }

    /**
     * 设置outOfAirlineCounterTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutOfAirlineCounterTime(String value) {
        this.outOfAirlineCounterTime = value;
    }

    /**
     * 获取canPost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanPost() {
        return canPost;
    }

    /**
     * 设置canPost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanPost(String value) {
        this.canPost = value;
    }

    /**
     * 获取canAirlineCounter属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanAirlineCounter() {
        return canAirlineCounter;
    }

    /**
     * 设置canAirlineCounter属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanAirlineCounter(String value) {
        this.canAirlineCounter = value;
    }

    /**
     * 获取canSendGet属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanSendGet() {
        return canSendGet;
    }

    /**
     * 设置canSendGet属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanSendGet(String value) {
        this.canSendGet = value;
    }

    /**
     * 获取isRebate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRebate() {
        return isRebate;
    }

    /**
     * 设置isRebate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRebate(String value) {
        this.isRebate = value;
    }

    /**
     * 获取rebateAmount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRebateAmount() {
        return rebateAmount;
    }

    /**
     * 设置rebateAmount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRebateAmount(String value) {
        this.rebateAmount = value;
    }

    /**
     * 获取rebateCPCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRebateCPCity() {
        return rebateCPCity;
    }

    /**
     * 设置rebateCPCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRebateCPCity(String value) {
        this.rebateCPCity = value;
    }

}
