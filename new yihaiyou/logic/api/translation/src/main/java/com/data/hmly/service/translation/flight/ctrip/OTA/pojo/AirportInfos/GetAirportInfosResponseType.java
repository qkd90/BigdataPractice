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
 * <p>GetAirportInfosResponseType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="GetAirportInfosResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecordCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AirportInfosList" type="{}AirportInfosListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAirportInfosResponseType", propOrder = {
    "recordCount",
    "airportInfosList"
})
public class GetAirportInfosResponseType {

    @XmlElement(name = "RecordCount", required = true)
    protected String recordCount;
    @XmlElement(name = "AirportInfosList", required = true)
    protected AirportInfosListType airportInfosList;

    /**
     * 获取recordCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordCount() {
        return recordCount;
    }

    /**
     * 设置recordCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordCount(String value) {
        this.recordCount = value;
    }

    /**
     * 获取airportInfosList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AirportInfosListType }
     *     
     */
    public AirportInfosListType getAirportInfosList() {
        return airportInfosList;
    }

    /**
     * 设置airportInfosList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AirportInfosListType }
     *     
     */
    public void setAirportInfosList(AirportInfosListType value) {
        this.airportInfosList = value;
    }

}
