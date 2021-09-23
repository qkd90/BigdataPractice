//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:13:44 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.AirportInfos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AirportInfosListType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AirportInfosListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AirportInfoEntity" type="{}AirportInfoEntityType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirportInfosListType", propOrder = {
    "airportInfoEntity"
})
public class AirportInfosListType {

    @XmlElement(name = "AirportInfoEntity", required = true)
    protected AirportInfoEntityType airportInfoEntity;

    /**
     * 获取airportInfoEntity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AirportInfoEntityType }
     *     
     */
    public AirportInfoEntityType getAirportInfoEntity() {
        return airportInfoEntity;
    }

    /**
     * 设置airportInfoEntity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AirportInfoEntityType }
     *     
     */
    public void setAirportInfoEntity(AirportInfoEntityType value) {
        this.airportInfoEntity = value;
    }

}
