//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:45:34 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FlightRouteType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FlightRouteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DepartCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ArriveCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DepartDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirlineDibitCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DepartPort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ArrivePort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EarliestDepartTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LatestDepartTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightRouteType", propOrder = {
    "departCity",
    "arriveCity",
    "departDate",
    "airlineDibitCode",
    "departPort",
    "arrivePort",
    "earliestDepartTime",
    "latestDepartTime"
})
public class FlightRouteType {

    @XmlElement(name = "DepartCity", required = true)
    protected String departCity;
    @XmlElement(name = "ArriveCity", required = true)
    protected String arriveCity;
    @XmlElement(name = "DepartDate", required = true)
    protected String departDate;
    @XmlElement(name = "AirlineDibitCode", required = true)
    protected String airlineDibitCode;
    @XmlElement(name = "DepartPort", required = true)
    protected String departPort;
    @XmlElement(name = "ArrivePort", required = true)
    protected String arrivePort;
    @XmlElement(name = "EarliestDepartTime", required = true)
    protected String earliestDepartTime;
    @XmlElement(name = "LatestDepartTime", required = true)
    protected String latestDepartTime;

    /**
     * 获取departCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartCity() {
        return departCity;
    }

    /**
     * 设置departCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartCity(String value) {
        this.departCity = value;
    }

    /**
     * 获取arriveCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArriveCity() {
        return arriveCity;
    }

    /**
     * 设置arriveCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArriveCity(String value) {
        this.arriveCity = value;
    }

    /**
     * 获取departDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartDate() {
        return departDate;
    }

    /**
     * 设置departDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartDate(String value) {
        this.departDate = value;
    }

    /**
     * 获取airlineDibitCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirlineDibitCode() {
        return airlineDibitCode;
    }

    /**
     * 设置airlineDibitCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirlineDibitCode(String value) {
        this.airlineDibitCode = value;
    }

    /**
     * 获取departPort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartPort() {
        return departPort;
    }

    /**
     * 设置departPort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartPort(String value) {
        this.departPort = value;
    }

    /**
     * 获取arrivePort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivePort() {
        return arrivePort;
    }

    /**
     * 设置arrivePort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivePort(String value) {
        this.arrivePort = value;
    }

    /**
     * 获取earliestDepartTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarliestDepartTime() {
        return earliestDepartTime;
    }

    /**
     * 设置earliestDepartTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarliestDepartTime(String value) {
        this.earliestDepartTime = value;
    }

    /**
     * 获取latestDepartTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatestDepartTime() {
        return latestDepartTime;
    }

    /**
     * 设置latestDepartTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatestDepartTime(String value) {
        this.latestDepartTime = value;
    }

}
