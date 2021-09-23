//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:14:36 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.AirlineInfos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AirlineInfoEntityType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AirlineInfoEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AirLine" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirLineCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirLineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirLineEName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GroupId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StrictType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AddonPriceProtected" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsSupportAirPlus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OnlineCheckinUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirlineInfoEntityType", propOrder = {
    "airLine",
    "airLineCode",
    "airLineName",
    "airLineEName",
    "shortName",
    "groupId",
    "groupName",
    "strictType",
    "addonPriceProtected",
    "isSupportAirPlus",
    "onlineCheckinUrl"
})
public class AirlineInfoEntityType {

    @XmlElement(name = "AirLine", required = true)
    protected String airLine;
    @XmlElement(name = "AirLineCode", required = true)
    protected String airLineCode;
    @XmlElement(name = "AirLineName", required = true)
    protected String airLineName;
    @XmlElement(name = "AirLineEName", required = true)
    protected String airLineEName;
    @XmlElement(name = "ShortName", required = true)
    protected String shortName;
    @XmlElement(name = "GroupId", required = true)
    protected String groupId;
    @XmlElement(name = "GroupName", required = true)
    protected String groupName;
    @XmlElement(name = "StrictType", required = true)
    protected String strictType;
    @XmlElement(name = "AddonPriceProtected", required = true)
    protected String addonPriceProtected;
    @XmlElement(name = "IsSupportAirPlus", required = true)
    protected String isSupportAirPlus;
    @XmlElement(name = "OnlineCheckinUrl", required = true)
    protected String onlineCheckinUrl;

    /**
     * 获取airLine属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirLine() {
        return airLine;
    }

    /**
     * 设置airLine属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirLine(String value) {
        this.airLine = value;
    }

    /**
     * 获取airLineCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirLineCode() {
        return airLineCode;
    }

    /**
     * 设置airLineCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirLineCode(String value) {
        this.airLineCode = value;
    }

    /**
     * 获取airLineName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirLineName() {
        return airLineName;
    }

    /**
     * 设置airLineName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirLineName(String value) {
        this.airLineName = value;
    }

    /**
     * 获取airLineEName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirLineEName() {
        return airLineEName;
    }

    /**
     * 设置airLineEName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirLineEName(String value) {
        this.airLineEName = value;
    }

    /**
     * 获取shortName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置shortName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * 获取groupId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置groupId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * 获取groupName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置groupName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * 获取strictType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrictType() {
        return strictType;
    }

    /**
     * 设置strictType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrictType(String value) {
        this.strictType = value;
    }

    /**
     * 获取addonPriceProtected属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddonPriceProtected() {
        return addonPriceProtected;
    }

    /**
     * 设置addonPriceProtected属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddonPriceProtected(String value) {
        this.addonPriceProtected = value;
    }

    /**
     * 获取isSupportAirPlus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSupportAirPlus() {
        return isSupportAirPlus;
    }

    /**
     * 设置isSupportAirPlus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSupportAirPlus(String value) {
        this.isSupportAirPlus = value;
    }

    /**
     * 获取onlineCheckinUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlineCheckinUrl() {
        return onlineCheckinUrl;
    }

    /**
     * 设置onlineCheckinUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlineCheckinUrl(String value) {
        this.onlineCheckinUrl = value;
    }

}
