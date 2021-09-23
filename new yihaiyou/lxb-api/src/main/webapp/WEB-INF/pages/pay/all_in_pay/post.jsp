<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<!--
    1、订单可以通过post方式或get方式提交，建议使用post方式；
       提交支付请求可以使用http或https方式，建议使用https方式。
    2、通联支付网关地址、商户号及key值，在接入测试时由通联提供；
       通联支付网关地址、商户号，在接入生产时由通联提供，key值在通联支付网关会员服务网站上设置。
-->
    <!--================= post 方式提交支付请求 start =====================-->
    <!--================= 测试地址为 http://ceshi.allinpay.com/gateway/index.do =====================-->
    <!--================= 生产地址请在测试环境下通过后从业务人员获取 (https://service.allinpay.com/gateway/index.do )=====================-->
    <form id="all_in_pay_form" name="form2" action="${order.serverUrl}" method="post">
        <input type="hidden" name="inputCharset" value="${order.inputCharset}"/>
        <input type="hidden" name="pickupUrl" value="${order.pickupUrl}"/>
        <input type="hidden" name="receiveUrl" value="${order.receiveUrl}"/>
        <input type="hidden" name="version" value="${order.version}"/>
        <input type="hidden" name="language" value="${order.language}"/>
        <input type="hidden" name="signType" value="${order.signType}"/>
        <input type="hidden" name="merchantId" value="${order.merchantId}"/>
        <input type="hidden" name="payerName" value="${order.payerName}"/>
        <input type="hidden" name="payerEmail" value="${order.payerEmail}"/>
        <input type="hidden" name="payerTelephone" value="${order.payerTelephone}"/>
        <input type="hidden" name="payerIDCard" value="${order.payerIDCard}"/>
        <input type="hidden" name="pid" value="${order.pid}"/>
        <input type="hidden" name="orderNo" value="${order.orderNo}"/>
        <input type="hidden" name="orderAmount" value="${order.orderAmount}"/>
        <input type="hidden" name="orderCurrency" value="${order.orderCurrency}"/>
        <input type="hidden" name="orderDatetime" value="${order.orderDatetime}"/>
        <input type="hidden" name="orderExpireDatetime" value="${order.orderExpireDatetime}"/>
        <input type="hidden" name="productName" value="${order.productName}"/>
        <input type="hidden" name="productPrice" value="${order.productPrice}"/>
        <input type="hidden" name="productNum" value="${order.productNum}"/>
        <input type="hidden" name="productId" value="${order.productId}"/>
        <input type="hidden" name="productDesc" value="${order.productDesc}"/>
        <input type="hidden" name="ext1" value="${order.ext1}"/>
        <input type="hidden" name="ext2" value="${order.ext2}"/>
        <input type="hidden" name="payType" value="${order.payType}"/>
        <input type="hidden" name="issuerId" value="${order.issuerId}"/>
        <input type="hidden" name="pan" value="${order.pan}"/>
        <input type="hidden" name="tradeNature" value="${order.tradeNature}"/>
        <input type="hidden" name="signMsg" value="${signMsg}"/>

    </form>
<script type="text/javascript">
    document.getElementById("all_in_pay_form").submit();
</script>
    <!--================= post 方式提交支付请求 end =====================-->
</body>
</html>
