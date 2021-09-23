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
 * <p>CategoryCodesType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CategoryCodesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SegmentCategory" type="{}SegmentCategoryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryCodesType", propOrder = {
    "segmentCategory"
})
public class CategoryCodesType {

    @XmlElement(name = "SegmentCategory", required = true)
    protected SegmentCategoryType segmentCategory;

    /**
     * 获取segmentCategory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SegmentCategoryType }
     *     
     */
    public SegmentCategoryType getSegmentCategory() {
        return segmentCategory;
    }

    /**
     * 设置segmentCategory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SegmentCategoryType }
     *     
     */
    public void setSegmentCategory(SegmentCategoryType value) {
        this.segmentCategory = value;
    }

}
