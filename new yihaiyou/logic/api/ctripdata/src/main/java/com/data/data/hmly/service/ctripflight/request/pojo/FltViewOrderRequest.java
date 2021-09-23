//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.06 at 12:37:54 AM CST 
//


package com.data.data.hmly.service.ctripflight.request.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header" type="{}HeaderType"/>
 *         &lt;element name="FltViewOrderRequest" type="{}FltViewOrderRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "header",
    "fltViewOrderRequest"
})
@XmlRootElement(name="Request")
public class FltViewOrderRequest {

    @XmlElement(name = "Header", required = true)
    protected FltViewOrderHeaderType header;
    @XmlElement(name = "FltViewOrderRequest", required = true)
    protected FltViewOrderRequestType fltViewOrderRequest;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link FltViewOrderHeaderType }
     *     
     */
    public FltViewOrderHeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link FltViewOrderHeaderType }
     *     
     */
    public void setHeader(FltViewOrderHeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the fltViewOrderRequest property.
     * 
     * @return
     *     possible object is
     *     {@link FltViewOrderRequestType }
     *     
     */
    public FltViewOrderRequestType getFltViewOrderRequest() {
        return fltViewOrderRequest;
    }

    /**
     * Sets the value of the fltViewOrderRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link FltViewOrderRequestType }
     *     
     */
    public void setFltViewOrderRequest(FltViewOrderRequestType value) {
        this.fltViewOrderRequest = value;
    }

}
