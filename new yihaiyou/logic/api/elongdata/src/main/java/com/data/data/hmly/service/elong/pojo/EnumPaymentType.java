//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.03 at 10:34:35 AM CST 
//


package com.data.data.hmly.service.elong.pojo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnumPaymentType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="EnumPaymentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="All"/>
 *     &lt;enumeration value="SelfPay"/>
 *     &lt;enumeration value="Prepay"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "EnumPaymentType")
@XmlEnum
public enum EnumPaymentType {

    @XmlEnumValue("All")
    All("All"),
    @XmlEnumValue("SelfPay")
    SelfPay("SelfPay"),
    @XmlEnumValue("Prepay")
    Prepay("Prepay");
    private final String value;

    EnumPaymentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumPaymentType fromValue(String v) {
        for (EnumPaymentType c : EnumPaymentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}