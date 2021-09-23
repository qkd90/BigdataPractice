//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.28 时间 04:38:59 PM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>ReviewType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ReviewType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="Count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Good" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Poor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Score" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReviewType", propOrder = {
        "value"
})
public class ReviewType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "Count")
    protected String count;
    @XmlAttribute(name = "Good")
    protected String good;
    @XmlAttribute(name = "Poor")
    protected String poor;
    @XmlAttribute(name = "Score")
    protected String score;

    /**
     * 获取value属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置value属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取count属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCount() {
        return count;
    }

    /**
     * 设置count属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCount(String value) {
        this.count = value;
    }

    /**
     * 获取good属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGood() {
        return good;
    }

    /**
     * 设置good属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGood(String value) {
        this.good = value;
    }

    /**
     * 获取poor属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoor() {
        return poor;
    }

    /**
     * 设置poor属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoor(String value) {
        this.poor = value;
    }

    /**
     * 获取score属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScore() {
        return score;
    }

    /**
     * 设置score属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScore(String value) {
        this.score = value;
    }

}
