//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:32:04 PM CST 
//


package com.data.data.hmly.service.ctriphotel.staticinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FacilityInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FacilityInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GuestRooms" type="{}GuestRoomsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityInfoType", propOrder = {
    "guestRooms"
})
public class FacilityInfoType {

    @XmlElement(name = "GuestRooms", required = true)
    protected GuestRoomsType guestRooms;

    /**
     * 获取guestRooms属性的值。
     * 
     * @return
     *     possible object is
     *     {@link GuestRoomsType }
     *     
     */
    public GuestRoomsType getGuestRooms() {
        return guestRooms;
    }

    /**
     * 设置guestRooms属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link GuestRoomsType }
     *     
     */
    public void setGuestRooms(GuestRoomsType value) {
        this.guestRooms = value;
    }

}
