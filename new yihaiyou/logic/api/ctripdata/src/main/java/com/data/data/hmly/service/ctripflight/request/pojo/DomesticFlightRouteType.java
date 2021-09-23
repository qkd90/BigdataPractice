//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 01:46:02 PM CST 
//


package com.data.data.hmly.service.ctripflight.request.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DomesticFlightRouteType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DomesticFlightRouteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecordCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FlightsList" type="{}FlightsListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DomesticFlightRouteType", propOrder = {
    "recordCount",
    "orderBy",
    "direction",
    "flightsList"
})
public class DomesticFlightRouteType {

    @XmlElement(name = "RecordCount", required = true)
    protected String recordCount;
    @XmlElement(name = "OrderBy", required = true)
    protected String orderBy;
    @XmlElement(name = "Direction", required = true)
    protected String direction;
    @XmlElement(name = "FlightsList", required = true)
    protected FlightsListType flightsList;

    /**
     * 获取recordCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordCount() {
        return recordCount;
    }

    /**
     * 设置recordCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordCount(String value) {
        this.recordCount = value;
    }

    /**
     * 获取orderBy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置orderBy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    /**
     * 获取direction属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 设置direction属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

    /**
     * 获取flightsList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FlightsListType }
     *     
     */
    public FlightsListType getFlightsList() {
        return flightsList;
    }

    /**
     * 设置flightsList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FlightsListType }
     *     
     */
    public void setFlightsList(FlightsListType value) {
        this.flightsList = value;
    }

}
