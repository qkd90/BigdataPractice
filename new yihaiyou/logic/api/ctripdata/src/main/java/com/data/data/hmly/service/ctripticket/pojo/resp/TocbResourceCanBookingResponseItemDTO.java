//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.03 at 08:22:49 PM CST 
//


package com.data.data.hmly.service.ctripticket.pojo.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ResourceID"/>
 *         &lt;element ref="{}UseDate"/>
 *         &lt;element ref="{}Code"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "resourceID",
    "useDate",
    "code"
})
@XmlRootElement(name = "ResourceCanBookingResponseItemDTO")
public class TocbResourceCanBookingResponseItemDTO {

    @XmlElement(name = "ResourceID", required = true)
    protected BigInteger resourceID;
    @XmlElement(name = "UseDate", required = true)
    protected String useDate;
    @XmlElement(name = "Code")
    protected boolean code;

    /**
     * Gets the value of the resourceID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getResourceID() {
        return resourceID;
    }

    /**
     * Sets the value of the resourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setResourceID(BigInteger value) {
        this.resourceID = value;
    }

    /**
     * Gets the value of the useDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseDate() {
        return useDate;
    }

    /**
     * Sets the value of the useDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseDate(String value) {
        this.useDate = value;
    }

    /**
     * Gets the value of the code property.
     * 
     */
    public boolean isCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     */
    public void setCode(boolean value) {
        this.code = value;
    }

}
