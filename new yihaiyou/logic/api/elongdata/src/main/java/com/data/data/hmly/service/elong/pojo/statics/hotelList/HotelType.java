//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.27 时间 10:02:41 AM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>HotelType complex type的 Java 类。
 * <p/>
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p/>
 * <pre>
 * &lt;complexType name="HotelType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="HotelId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="UpdatedTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Modification" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Products" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelType", propOrder = {
        "value"
})
public class HotelType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "HotelId")
    protected String hotelId;
    @XmlAttribute(name = "UpdatedTime")
    protected String updatedTime;
    @XmlAttribute(name = "Modification")
    protected String modification;
    @XmlAttribute(name = "Products")
    protected String products;
    @XmlAttribute(name = "Status")
    protected String status;

    /**
     * 获取value属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置value属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取hotelId属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getHotelId() {
        return hotelId;
    }

    /**
     * 设置hotelId属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHotelId(String value) {
        this.hotelId = value;
    }

    /**
     * 获取updatedTime属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置updatedTime属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUpdatedTime(String value) {
        this.updatedTime = value;
    }

    /**
     * 获取modification属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getModification() {
        return modification;
    }

    /**
     * 设置modification属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setModification(String value) {
        this.modification = value;
    }

    /**
     * 获取products属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getProducts() {
        return products;
    }

    /**
     * 设置products属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProducts(String value) {
        this.products = value;
    }

    /**
     * 获取status属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
