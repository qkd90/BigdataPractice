//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.02 at 07:34:20 PM CST 
//


package com.data.data.hmly.service.ctripticket.pojo.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScenicSpotSearchResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScenicSpotSearchResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LabelSatistics" type="{}LabelSatisticsType"/>
 *         &lt;element name="PagingCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RowCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ScenicSpotListItemList" type="{}ScenicSpotListItemListType"/>
 *         &lt;element name="TabTypeSatistics" type="{}TabTypeSatisticsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScenicSpotSearchResponseType", propOrder = {
    "labelSatistics",
    "pagingCount",
    "rowCount",
    "scenicSpotListItemList",
    "tabTypeSatistics"
})
@XmlRootElement(name="ScenicSpotSearchResponse")
public class ScenicSpotSearchResponseType {

    @XmlElement(name = "LabelSatistics", required = true)
    protected LabelSatisticsType labelSatistics;
    @XmlElement(name = "PagingCount", required = true)
    protected Integer pagingCount;
    @XmlElement(name = "RowCount", required = true)
    protected String rowCount;
    @XmlElement(name = "ScenicSpotListItemList", required = true)
    protected ScenicSpotListItemListType scenicSpotListItemList;
    @XmlElement(name = "TabTypeSatistics", required = true)
    protected TabTypeSatisticsType tabTypeSatistics;

    /**
     * Gets the value of the labelSatistics property.
     * 
     * @return
     *     possible object is
     *     {@link LabelSatisticsType }
     *     
     */
    public LabelSatisticsType getLabelSatistics() {
        return labelSatistics;
    }

    /**
     * Sets the value of the labelSatistics property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelSatisticsType }
     *     
     */
    public void setLabelSatistics(LabelSatisticsType value) {
        this.labelSatistics = value;
    }

    /**
     * Gets the value of the pagingCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getPagingCount() {
        return pagingCount;
    }

    /**
     * Sets the value of the pagingCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPagingCount(Integer value) {
        this.pagingCount = value;
    }

    /**
     * Gets the value of the rowCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowCount() {
        return rowCount;
    }

    /**
     * Sets the value of the rowCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowCount(String value) {
        this.rowCount = value;
    }

    /**
     * Gets the value of the scenicSpotListItemList property.
     * 
     * @return
     *     possible object is
     *     {@link ScenicSpotListItemListType }
     *     
     */
    public ScenicSpotListItemListType getScenicSpotListItemList() {
        return scenicSpotListItemList;
    }

    /**
     * Sets the value of the scenicSpotListItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenicSpotListItemListType }
     *     
     */
    public void setScenicSpotListItemList(ScenicSpotListItemListType value) {
        this.scenicSpotListItemList = value;
    }

    /**
     * Gets the value of the tabTypeSatistics property.
     * 
     * @return
     *     possible object is
     *     {@link TabTypeSatisticsType }
     *     
     */
    public TabTypeSatisticsType getTabTypeSatistics() {
        return tabTypeSatistics;
    }

    /**
     * Sets the value of the tabTypeSatistics property.
     * 
     * @param value
     *     allowed object is
     *     {@link TabTypeSatisticsType }
     *     
     */
    public void setTabTypeSatistics(TabTypeSatisticsType value) {
        this.tabTypeSatistics = value;
    }

}
