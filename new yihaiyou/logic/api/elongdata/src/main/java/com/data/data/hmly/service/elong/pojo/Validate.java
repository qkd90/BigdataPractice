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
import java.math.BigDecimal;


/**
 * <p>Java class for Validate complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Validate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResultCode" type="{}EnumValidateResult"/>
 *         &lt;element name="GuaranteeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Validate", propOrder = {
        "resultCode",
        "guaranteeRate"
})
public class Validate {

    @JSONField(name = "ResultCode")
    @XmlSchemaType(name = "string")
    protected EnumValidateResult resultCode;
    @JSONField(name = "GuaranteeRate")
    protected BigDecimal guaranteeRate;

    /**
     * Gets the value of the resultCode property.
     *
     * @return possible object is
     * {@link EnumValidateResult }
     */
    public EnumValidateResult getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     *
     * @param value allowed object is
     *              {@link EnumValidateResult }
     */
    public void setResultCode(EnumValidateResult value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the guaranteeRate property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getGuaranteeRate() {
        return guaranteeRate;
    }

    /**
     * Sets the value of the guaranteeRate property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setGuaranteeRate(BigDecimal value) {
        this.guaranteeRate = value;
    }

}