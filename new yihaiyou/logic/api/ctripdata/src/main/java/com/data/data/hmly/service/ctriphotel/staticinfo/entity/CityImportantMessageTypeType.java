//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:32:04 PM CST 
//


package com.data.data.hmly.service.ctriphotel.staticinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CityImportantMessageTypeType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CityImportantMessageTypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessageContent">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="此期间到三亚免税店购物的海南籍居民，凭本人身份证，可办理9折优惠购物卡；学生凭本人身份证和有效学生证可办理8.8折优惠卡。"/>
 *               &lt;enumeration value="此期间预定入住，可享受三亚免税店离岛9折优惠；需提前两天下午15点前预约，提供离岛客人姓名+身份证号码+携程订单号+联系电话，                                     第三天到免税店前台报“国旅海南及旅客姓名” 办理折扣优惠卡。预约热线 18808956503   (此联系方式仅预约免税店优惠促销活动，                                     工作时间：09:00至18:00）。"/>
 *               &lt;enumeration value="此期间通过携程预订可享受三亚免税店“暑期购物节，欢乐缤纷礼”活动：1.学生集结号；2.本岛居民优惠；3.家庭总动员；                                     4.大牌五折购；5.携程优享-免税赠礼：进三亚免税店购物需先凭携程预订短信前往携程专区办理，方可享受购物优惠活动（此短信转发无效，                                     入住期间有效）以上需凭携程成功预订短信确认后6小时才方可享受此优惠活动.详情请咨询免税店客服：400-699-6956 咨询时间：09:00-22：00"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="StartDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityImportantMessageTypeType", propOrder = {
    "messageContent"
})
public class CityImportantMessageTypeType {

    @XmlElement(name = "MessageContent", required = true)
    protected String messageContent;
    @XmlAttribute(name = "StartDate")
    protected String startDate;
    @XmlAttribute(name = "EndDate")
    protected String endDate;

    /**
     * 获取messageContent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 设置messageContent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageContent(String value) {
        this.messageContent = value;
    }

    /**
     * 获取startDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 设置startDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * 获取endDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置endDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

}
