//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.05 at 01:48:07 PM CST 
//


package com.data.data.hmly.service.ctripflight.request.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliverInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliverInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeliveryType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SendTicketCityID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PJS" type="{}PJSType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliverInfoType", propOrder = {
    "deliveryType",
    "sendTicketCityID",
    "orderRemark",
    "pjs"
})
public class FltOrderDeliverInfoType {

    @XmlElement(name = "DeliveryType", required = true)
    protected String deliveryType;
    @XmlElement(name = "SendTicketCityID", required = true)
    protected String sendTicketCityID;
    @XmlElement(name = "OrderRemark", required = true)
    protected String orderRemark;
    @XmlElement(name = "PJS", required = true)
    protected FltOrderPJSType pjs;

    /**
     * Gets the value of the deliveryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryType() {
        return deliveryType;
    }

    /**
     * Sets the value of the deliveryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryType(String value) {
        this.deliveryType = value;
    }

    /**
     * Gets the value of the sendTicketCityID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendTicketCityID() {
        return sendTicketCityID;
    }

    /**
     * Sets the value of the sendTicketCityID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendTicketCityID(String value) {
        this.sendTicketCityID = value;
    }

    /**
     * Gets the value of the orderRemark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderRemark() {
        return orderRemark;
    }

    /**
     * Sets the value of the orderRemark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderRemark(String value) {
        this.orderRemark = value;
    }

    /**
     * Gets the value of the pjs property.
     * 
     * @return
     *     possible object is
     *     {@link FltOrderPJSType }
     *     
     */
    public FltOrderPJSType getPJS() {
        return pjs;
    }

    /**
     * Sets the value of the pjs property.
     * 
     * @param value
     *     allowed object is
     *     {@link FltOrderPJSType }
     *     
     */
    public void setPJS(FltOrderPJSType value) {
        this.pjs = value;
    }

}
