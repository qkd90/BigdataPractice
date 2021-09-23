//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.04 at 10:51:42 PM CST 
//


package com.data.data.hmly.service.ctripflight.request.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchCriteriaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchCriteriaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrigDestRequestList" type="{}OrigDestRequestListType"/>
 *         &lt;element name="TravelerRequestList" type="{}TravelerRequestListType"/>
 *         &lt;element name="CabinClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RequestedCabinClassOnly" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TicketDeliveryCityID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchCriteriaType", propOrder = {
    "origDestRequestList",
    "travelerRequestList",
    "cabinClass",
    "requestedCabinClassOnly",
    "ticketDeliveryCityID"
})
public class IntlFlightSearchCriteriaType {

    @XmlElement(name = "OrigDestRequestList", required = true)
    protected IntlFlightOrigDestRequestListType origDestRequestList;
    @XmlElement(name = "TravelerRequestList", required = true)
    protected IntlFlightTravelerRequestListType travelerRequestList;
    @XmlElement(name = "CabinClass", required = true)
    protected String cabinClass;
    @XmlElement(name = "RequestedCabinClassOnly", required = true)
    protected String requestedCabinClassOnly;
    @XmlElement(name = "TicketDeliveryCityID", required = true)
    protected String ticketDeliveryCityID;

    /**
     * Gets the value of the origDestRequestList property.
     * 
     * @return
     *     possible object is
     *     {@link OrigDestRequestListType }
     *     
     */
    public IntlFlightOrigDestRequestListType getOrigDestRequestList() {
        return origDestRequestList;
    }

    /**
     * Sets the value of the origDestRequestList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrigDestRequestListType }
     *     
     */
    public void setOrigDestRequestList(IntlFlightOrigDestRequestListType value) {
        this.origDestRequestList = value;
    }

    /**
     * Gets the value of the travelerRequestList property.
     * 
     * @return
     *     possible object is
     *     {@link TravelerRequestListType }
     *     
     */
    public IntlFlightTravelerRequestListType getTravelerRequestList() {
        return travelerRequestList;
    }

    /**
     * Sets the value of the travelerRequestList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TravelerRequestListType }
     *     
     */
    public void setTravelerRequestList(IntlFlightTravelerRequestListType value) {
        this.travelerRequestList = value;
    }

    /**
     * Gets the value of the cabinClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCabinClass() {
        return cabinClass;
    }

    /**
     * Sets the value of the cabinClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCabinClass(String value) {
        this.cabinClass = value;
    }

    /**
     * Gets the value of the requestedCabinClassOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedCabinClassOnly() {
        return requestedCabinClassOnly;
    }

    /**
     * Sets the value of the requestedCabinClassOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedCabinClassOnly(String value) {
        this.requestedCabinClassOnly = value;
    }

    /**
     * Gets the value of the ticketDeliveryCityID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketDeliveryCityID() {
        return ticketDeliveryCityID;
    }

    /**
     * Sets the value of the ticketDeliveryCityID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketDeliveryCityID(String value) {
        this.ticketDeliveryCityID = value;
    }

}
