//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.07 at 11:16:00 AM CST 
//


package com.data.data.hmly.service.ctripflight.response.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header" type="{}HeaderType"/>
 *         &lt;element name="GetCityInfosResponse" type="{}GetCityInfosResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseType", propOrder = {
    "header",
    "getCityInfosResponse"
})
@XmlRootElement(name="Response")
public class GetCityInfosResponse {
    @XmlElement(name = "Header", required = true)
    protected GetCityInfosHeaderType header;
    @XmlElement(name = "GetCityInfosResponse", required = true)
    protected GetCityInfosResponseType getCityInfosResponse;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link GetCityInfosHeaderType }
     *     
     */
    public GetCityInfosHeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCityInfosHeaderType }
     *     
     */
    public void setHeader(GetCityInfosHeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the getCityInfosResponse property.
     * 
     * @return
     *     possible object is
     *     {@link GetCityInfosResponseType }
     *     
     */
    public GetCityInfosResponseType getGetCityInfosResponse() {
        return getCityInfosResponse;
    }

    /**
     * Sets the value of the getCityInfosResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCityInfosResponseType }
     *     
     */
    public void setGetCityInfosResponse(GetCityInfosResponseType value) {
        this.getCityInfosResponse = value;
    }

}
