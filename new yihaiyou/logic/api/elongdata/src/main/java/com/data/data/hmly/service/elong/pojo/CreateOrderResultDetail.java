//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.03 at 10:34:35 AM CST 
//


package com.data.data.hmly.service.elong.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>Java class for CreateOrderResult complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CreateOrderResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="CancelTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GuaranteeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CurrencyCode" type="{}EnumCurrencyCode"/>
 *         &lt;element name="IsInstantConfirm" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PaymentDeadlineTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PaymentMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateOrderResult", propOrder = {
        "OrderId",
        "CancelTime",
        "GuaranteeAmount",
        "CurrencyCode",
        "IsInstantConfirm",
        "PaymentDeadlineTime",
        "PaymentMessage"
})
@XmlSeeAlso({
        UpdateOrderResult.class
})
public class CreateOrderResultDetail {

    @JSONField(name = "OrderId")
    protected long OrderId;
    @JSONField(name = "CancelTime")
    @XmlSchemaType(name = "dateTime")
    protected String CancelTime;
    @JSONField(name = "GuaranteeAmount")
    protected BigDecimal GuaranteeAmount;
    @JSONField(name = "CurrencyCode")
    @XmlSchemaType(name = "string")
    protected EnumCurrencyCode CurrencyCode;
    @JSONField(name = "IsInstantConfirm")
    protected Boolean IsInstantConfirm;
    @JSONField(name = "PaymentDeadlineTime")
    @XmlSchemaType(name = "dateTime")
    protected String PaymentDeadlineTime;
    @JSONField(name = "PaymentMessage")
    protected String PaymentMessage;

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public String getCancelTime() {
        return CancelTime;
    }

    public void setCancelTime(String cancelTime) {
        CancelTime = cancelTime;
    }

    public void setPaymentDeadlineTime(String paymentDeadlineTime) {
        PaymentDeadlineTime = paymentDeadlineTime;
    }

    public BigDecimal getGuaranteeAmount() {
        return GuaranteeAmount;
    }

    public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
        GuaranteeAmount = guaranteeAmount;
    }

    public EnumCurrencyCode getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(EnumCurrencyCode currencyCode) {
        CurrencyCode = currencyCode;
    }

    public Boolean getIsInstantConfirm() {
        return IsInstantConfirm;
    }

    public void setIsInstantConfirm(Boolean isInstantConfirm) {
        IsInstantConfirm = isInstantConfirm;
    }

    public String getPaymentMessage() {
        return PaymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        PaymentMessage = paymentMessage;
    }
}
