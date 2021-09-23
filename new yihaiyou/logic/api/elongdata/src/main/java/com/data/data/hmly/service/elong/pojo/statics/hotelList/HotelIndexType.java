//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.27 时间 10:02:41 AM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HotelIndexType complex type的 Java 类。
 * <p/>
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p/>
 * <pre>
 * &lt;complexType name="HotelIndexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Hotels" type="{}HotelsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelIndexType", propOrder = {
        "hotels"
})
@XmlRootElement(name = "HotelIndex")
public class HotelIndexType {

    @XmlElement(name = "Hotels", required = true)
    protected HotelsType hotels;

    /**
     * 获取hotels属性的值。
     *
     * @return possible object is
     * {@link HotelsType }
     */
    public HotelsType getHotels() {
        return hotels;
    }

    /**
     * 设置hotels属性的值。
     *
     * @param value allowed object is
     *              {@link HotelsType }
     */
    public void setHotels(HotelsType value) {
        this.hotels = value;
    }

}
