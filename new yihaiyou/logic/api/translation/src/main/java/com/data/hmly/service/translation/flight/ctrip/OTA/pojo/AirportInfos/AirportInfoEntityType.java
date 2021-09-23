//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:13:44 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.AirportInfos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AirportInfoEntityType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AirportInfoEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AirPort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirPortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirPortEName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirportInfoEntityType", propOrder = {
    "airPort",
    "airPortName",
    "airPortEName",
    "shortName",
    "cityId",
    "cityName"
})
public class AirportInfoEntityType {

    @XmlElement(name = "AirPort", required = true)
    protected String airPort;
    @XmlElement(name = "AirPortName", required = true)
    protected String airPortName;
    @XmlElement(name = "AirPortEName", required = true)
    protected String airPortEName;
    @XmlElement(name = "ShortName", required = true)
    protected String shortName;
    @XmlElement(name = "CityId", required = true)
    protected String cityId;
    @XmlElement(name = "CityName", required = true)
    protected String cityName;

    /**
     * 获取airPort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirPort() {
        return airPort;
    }

    /**
     * 设置airPort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirPort(String value) {
        this.airPort = value;
    }

    /**
     * 获取airPortName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirPortName() {
        return airPortName;
    }

    /**
     * 设置airPortName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirPortName(String value) {
        this.airPortName = value;
    }

    /**
     * 获取airPortEName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirPortEName() {
        return airPortEName;
    }

    /**
     * 设置airPortEName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirPortEName(String value) {
        this.airPortEName = value;
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
     * 获取cityId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 设置cityId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityId(String value) {
        this.cityId = value;
    }

    /**
     * 获取cityName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置cityName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityName(String value) {
        this.cityName = value;
    }

}
