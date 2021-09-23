//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 07:09:07 PM CST 
//


package com.data.data.hmly.service.ctriphotel.priceinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>BookingRuleType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="BookingRuleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Viewerships" type="{}ViewershipsType" minOccurs="0"/>
 *         &lt;element name="LengthsOfStay" type="{}LengthsOfStayType" minOccurs="0"/>
 *         &lt;element name="BookingRule" type="{}BookingRuleType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="MinAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LaterReserveTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingRuleType", propOrder = {
    "viewerships",
    "lengthsOfStay",
    "bookingRule"
})
public class BookingRuleType {

    @XmlElement(name = "Viewerships")
    protected ViewershipsType viewerships;
    @XmlElement(name = "LengthsOfStay")
    protected LengthsOfStayType lengthsOfStay;
    @XmlElement(name = "BookingRule")
    protected BookingRuleType bookingRule;
    @XmlAttribute(name = "MinAdvancedBookingOffset")
    protected String minAdvancedBookingOffset;
    @XmlAttribute(name = "LaterReserveTime")
    protected String laterReserveTime;

    /**
     * 获取viewerships属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ViewershipsType }
     *     
     */
    public ViewershipsType getViewerships() {
        return viewerships;
    }

    /**
     * 设置viewerships属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ViewershipsType }
     *     
     */
    public void setViewerships(ViewershipsType value) {
        this.viewerships = value;
    }

    /**
     * 获取lengthsOfStay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LengthsOfStayType }
     *     
     */
    public LengthsOfStayType getLengthsOfStay() {
        return lengthsOfStay;
    }

    /**
     * 设置lengthsOfStay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LengthsOfStayType }
     *     
     */
    public void setLengthsOfStay(LengthsOfStayType value) {
        this.lengthsOfStay = value;
    }

    /**
     * 获取bookingRule属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BookingRuleType }
     *     
     */
    public BookingRuleType getBookingRule() {
        return bookingRule;
    }

    /**
     * 设置bookingRule属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BookingRuleType }
     *     
     */
    public void setBookingRule(BookingRuleType value) {
        this.bookingRule = value;
    }

    /**
     * 获取minAdvancedBookingOffset属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinAdvancedBookingOffset() {
        return minAdvancedBookingOffset;
    }

    /**
     * 设置minAdvancedBookingOffset属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinAdvancedBookingOffset(String value) {
        this.minAdvancedBookingOffset = value;
    }

    /**
     * 获取laterReserveTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaterReserveTime() {
        return laterReserveTime;
    }

    /**
     * 设置laterReserveTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaterReserveTime(String value) {
        this.laterReserveTime = value;
    }

}
