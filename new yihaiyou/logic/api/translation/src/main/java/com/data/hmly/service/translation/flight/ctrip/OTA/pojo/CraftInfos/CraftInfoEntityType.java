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
 * <p>CraftInfoEntityType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CraftInfoEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CraftType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CTName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WidthLevel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MinSeats" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MaxSeats" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Crafttype_ename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CraftKind" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CraftInfoEntityType", propOrder = {
    "craftType",
    "ctName",
    "widthLevel",
    "minSeats",
    "maxSeats",
    "note",
    "crafttypeEname",
    "craftKind"
})
public class CraftInfoEntityType {

    @XmlElement(name = "CraftType", required = true)
    protected String craftType;
    @XmlElement(name = "CTName", required = true)
    protected String ctName;
    @XmlElement(name = "WidthLevel", required = true)
    protected String widthLevel;
    @XmlElement(name = "MinSeats", required = true)
    protected String minSeats;
    @XmlElement(name = "MaxSeats", required = true)
    protected String maxSeats;
    @XmlElement(name = "Note", required = true)
    protected String note;
    @XmlElement(name = "Crafttype_ename", required = true)
    protected String crafttypeEname;
    @XmlElement(name = "CraftKind", required = true)
    protected String craftKind;

    /**
     * 获取craftType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCraftType() {
        return craftType;
    }

    /**
     * 设置craftType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCraftType(String value) {
        this.craftType = value;
    }

    /**
     * 获取ctName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCTName() {
        return ctName;
    }

    /**
     * 设置ctName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCTName(String value) {
        this.ctName = value;
    }

    /**
     * 获取widthLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidthLevel() {
        return widthLevel;
    }

    /**
     * 设置widthLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidthLevel(String value) {
        this.widthLevel = value;
    }

    /**
     * 获取minSeats属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinSeats() {
        return minSeats;
    }

    /**
     * 设置minSeats属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinSeats(String value) {
        this.minSeats = value;
    }

    /**
     * 获取maxSeats属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxSeats() {
        return maxSeats;
    }

    /**
     * 设置maxSeats属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxSeats(String value) {
        this.maxSeats = value;
    }

    /**
     * 获取note属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置note属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * 获取crafttypeEname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrafttypeEname() {
        return crafttypeEname;
    }

    /**
     * 设置crafttypeEname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrafttypeEname(String value) {
        this.crafttypeEname = value;
    }

    /**
     * 获取craftKind属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCraftKind() {
        return craftKind;
    }

    /**
     * 设置craftKind属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCraftKind(String value) {
        this.craftKind = value;
    }

}
