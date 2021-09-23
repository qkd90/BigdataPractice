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
 * <p>LengthsOfStayType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="LengthsOfStayType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LengthOfStay" type="{}LengthOfStayType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LengthsOfStayType", propOrder = {
    "lengthOfStay"
})
public class LengthsOfStayType {

    @XmlElement(name = "LengthOfStay", required = true)
    protected LengthOfStayType lengthOfStay;

    /**
     * 获取lengthOfStay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LengthOfStayType }
     *     
     */
    public LengthOfStayType getLengthOfStay() {
        return lengthOfStay;
    }

    /**
     * 设置lengthOfStay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LengthOfStayType }
     *     
     */
    public void setLengthOfStay(LengthOfStayType value) {
        this.lengthOfStay = value;
    }

}
