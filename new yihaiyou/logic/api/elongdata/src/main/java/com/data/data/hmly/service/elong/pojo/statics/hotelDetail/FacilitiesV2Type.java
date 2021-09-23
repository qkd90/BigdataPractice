//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.28 时间 04:38:59 PM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FacilitiesV2Type complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FacilitiesV2Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GeneralAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecreationAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ServiceAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilitiesV2Type", propOrder = {
        "generalAmenities",
        "recreationAmenities",
        "serviceAmenities"
})
public class FacilitiesV2Type {

    @XmlElement(name = "GeneralAmenities", required = true)
    protected String generalAmenities;
    @XmlElement(name = "RecreationAmenities", required = true)
    protected String recreationAmenities;
    @XmlElement(name = "ServiceAmenities", required = true)
    protected String serviceAmenities;

    /**
     * 获取generalAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneralAmenities() {
        return generalAmenities;
    }

    /**
     * 设置generalAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneralAmenities(String value) {
        this.generalAmenities = value;
    }

    /**
     * 获取recreationAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecreationAmenities() {
        return recreationAmenities;
    }

    /**
     * 设置recreationAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecreationAmenities(String value) {
        this.recreationAmenities = value;
    }

    /**
     * 获取serviceAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceAmenities() {
        return serviceAmenities;
    }

    /**
     * 设置serviceAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceAmenities(String value) {
        this.serviceAmenities = value;
    }

}
