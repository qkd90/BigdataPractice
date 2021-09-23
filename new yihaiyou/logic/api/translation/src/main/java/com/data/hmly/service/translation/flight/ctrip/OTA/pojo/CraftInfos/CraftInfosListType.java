//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:08:56 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.CraftInfos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CraftInfosListType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CraftInfosListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CraftInfoEntity" type="{}CraftInfoEntityType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CraftInfosListType", propOrder = {
    "craftInfoEntity"
})
public class CraftInfosListType {

    @XmlElement(name = "CraftInfoEntity", required = true)
    protected CraftInfoEntityType craftInfoEntity;

    /**
     * 获取craftInfoEntity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CraftInfoEntityType }
     *     
     */
    public CraftInfoEntityType getCraftInfoEntity() {
        return craftInfoEntity;
    }

    /**
     * 设置craftInfoEntity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CraftInfoEntityType }
     *     
     */
    public void setCraftInfoEntity(CraftInfoEntityType value) {
        this.craftInfoEntity = value;
    }

}
