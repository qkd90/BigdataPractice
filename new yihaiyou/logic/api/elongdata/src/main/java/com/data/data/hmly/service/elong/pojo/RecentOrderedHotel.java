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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecentOrderedHotel complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="RecentOrderedHotel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HotelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecentBookingTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecentOrderedHotel", propOrder = {
        "hotelId",
        "recentBookingTime"
})
public class RecentOrderedHotel {

    @JSONField(name = "HotelId")
    protected String hotelId;
    @JSONField(name = "RecentBookingTime")
    @XmlSchemaType(name = "dateTime")
    protected java.util.Date recentBookingTime;

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
     * Gets the value of the recentBookingTime property.
     *
     * @return possible object is
     * {@link java.util.Date }
     */
    public java.util.Date getRecentBookingTime() {
        return recentBookingTime;
    }

    /**
     * Sets the value of the recentBookingTime property.
     *
     * @param value allowed object is
     *              {@link java.util.Date }
     */
    public void setRecentBookingTime(java.util.Date value) {
        this.recentBookingTime = value;
    }

}
