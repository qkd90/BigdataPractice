//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 07:09:07 PM CST 
//


package com.data.data.hmly.service.ctriphotel.priceinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HotelResponseType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HotelResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OTA_HotelRatePlanRS" type="{}OTA_HotelRatePlanRSType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelResponseType", propOrder = {
    "otaHotelRatePlanRS"
})
public class HotelResponseType {

    @XmlElement(name = "OTA_HotelRatePlanRS", required = true)
    protected OTAHotelRatePlanRSType otaHotelRatePlanRS;

    /**
     * 获取otaHotelRatePlanRS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OTAHotelRatePlanRSType }
     *     
     */
    public OTAHotelRatePlanRSType getOTAHotelRatePlanRS() {
        return otaHotelRatePlanRS;
    }

    /**
     * 设置otaHotelRatePlanRS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OTAHotelRatePlanRSType }
     *     
     */
    public void setOTAHotelRatePlanRS(OTAHotelRatePlanRSType value) {
        this.otaHotelRatePlanRS = value;
    }

}
