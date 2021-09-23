//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.06 at 12:44:44 AM CST 
//


package com.data.data.hmly.service.ctripflight.response.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliverType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliverType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeliverTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeliverTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeliverAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeliverCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeliverDistricts" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeliverFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PrePayType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PrepayTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContactName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContactPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContactMobile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContactEmail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SendTicketETime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SendTicketLTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GetTicketWay" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliverType", propOrder = {
    "deliverTypeName",
    "deliverTime",
    "deliverAddress",
    "deliverCity",
    "deliverDistricts",
    "deliverFee",
    "prePayType",
    "prepayTypeName",
    "contactName",
    "contactPhone",
    "contactMobile",
    "contactEmail",
    "sendTicketETime",
    "sendTicketLTime",
    "getTicketWay"
})
public class FltViewOrderDeliverType {

    @XmlElement(name = "DeliverTypeName", required = true)
    protected String deliverTypeName;
    @XmlElement(name = "DeliverTime", required = true)
    protected String deliverTime;
    @XmlElement(name = "DeliverAddress", required = true)
    protected String deliverAddress;
    @XmlElement(name = "DeliverCity", required = true)
    protected String deliverCity;
    @XmlElement(name = "DeliverDistricts", required = true)
    protected String deliverDistricts;
    @XmlElement(name = "DeliverFee", required = true)
    protected String deliverFee;
    @XmlElement(name = "PrePayType", required = true)
    protected String prePayType;
    @XmlElement(name = "PrepayTypeName", required = true)
    protected String prepayTypeName;
    @XmlElement(name = "ContactName", required = true)
    protected String contactName;
    @XmlElement(name = "ContactPhone", required = true)
    protected String contactPhone;
    @XmlElement(name = "ContactMobile", required = true)
    protected String contactMobile;
    @XmlElement(name = "ContactEmail", required = true)
    protected String contactEmail;
    @XmlElement(name = "SendTicketETime", required = true)
    protected String sendTicketETime;
    @XmlElement(name = "SendTicketLTime", required = true)
    protected String sendTicketLTime;
    @XmlElement(name = "GetTicketWay", required = true)
    protected String getTicketWay;

    /**
     * Gets the value of the deliverTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverTypeName() {
        return deliverTypeName;
    }

    /**
     * Sets the value of the deliverTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverTypeName(String value) {
        this.deliverTypeName = value;
    }

    /**
     * Gets the value of the deliverTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverTime() {
        return deliverTime;
    }

    /**
     * Sets the value of the deliverTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverTime(String value) {
        this.deliverTime = value;
    }

    /**
     * Gets the value of the deliverAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverAddress() {
        return deliverAddress;
    }

    /**
     * Sets the value of the deliverAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverAddress(String value) {
        this.deliverAddress = value;
    }

    /**
     * Gets the value of the deliverCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverCity() {
        return deliverCity;
    }

    /**
     * Sets the value of the deliverCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverCity(String value) {
        this.deliverCity = value;
    }

    /**
     * Gets the value of the deliverDistricts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverDistricts() {
        return deliverDistricts;
    }

    /**
     * Sets the value of the deliverDistricts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverDistricts(String value) {
        this.deliverDistricts = value;
    }

    /**
     * Gets the value of the deliverFee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverFee() {
        return deliverFee;
    }

    /**
     * Sets the value of the deliverFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverFee(String value) {
        this.deliverFee = value;
    }

    /**
     * Gets the value of the prePayType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrePayType() {
        return prePayType;
    }

    /**
     * Sets the value of the prePayType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrePayType(String value) {
        this.prePayType = value;
    }

    /**
     * Gets the value of the prepayTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepayTypeName() {
        return prepayTypeName;
    }

    /**
     * Sets the value of the prepayTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepayTypeName(String value) {
        this.prepayTypeName = value;
    }

    /**
     * Gets the value of the contactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the value of the contactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactName(String value) {
        this.contactName = value;
    }

    /**
     * Gets the value of the contactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * Sets the value of the contactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    /**
     * Gets the value of the contactMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactMobile() {
        return contactMobile;
    }

    /**
     * Sets the value of the contactMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactMobile(String value) {
        this.contactMobile = value;
    }

    /**
     * Gets the value of the contactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the value of the contactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactEmail(String value) {
        this.contactEmail = value;
    }

    /**
     * Gets the value of the sendTicketETime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendTicketETime() {
        return sendTicketETime;
    }

    /**
     * Sets the value of the sendTicketETime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendTicketETime(String value) {
        this.sendTicketETime = value;
    }

    /**
     * Gets the value of the sendTicketLTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendTicketLTime() {
        return sendTicketLTime;
    }

    /**
     * Sets the value of the sendTicketLTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendTicketLTime(String value) {
        this.sendTicketLTime = value;
    }

    /**
     * Gets the value of the getTicketWay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetTicketWay() {
        return getTicketWay;
    }

    /**
     * Sets the value of the getTicketWay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetTicketWay(String value) {
        this.getTicketWay = value;
    }

}
