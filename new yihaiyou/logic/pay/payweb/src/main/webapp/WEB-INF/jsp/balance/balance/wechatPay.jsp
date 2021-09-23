<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/22
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp" %>
    <title>支付日志查询</title>
    <style type="text/css">
        .row_hd {
            height: 30px;
            width: 100%;
            line-height: 30px;
            border-bottom: 1px solid #ddd;
            background: #f4f4f4;
            /*margin: 5px 15px 10px 15px;*/
            /*padding-left: 10px;*/
            font-weight: 900;
            font-family: "Consolas", "Monaco", "Bitstream Vera Sans Mono", "Courier New", Courier, monospace;
            color: #666;
        }
        .div_content {
            font-size: 16px;
            padding: 5px 20px;
            font-weight: 700;
        }
        .div_label {
            color: rgba(26, 32, 39, 0.93)
        }
        .div_config_btn {
            padding: 5px 20px;
        }
        .div_config_btn a{
            margin-right: 20px;
        }
        .class_weipay {
            font-size: 16px;
            font-weight: 700;
        }
        .introduction {
            margin: 20px 0px;
        }
        .fl {
            font-size: 14px;
        }
    </style>

</head>
<body style="background-color: white">

<div class="row_hd">
    <div style="width: 10%;float: left;"><span>提交申请>></span></div>
    <div style="width: 10%;float: left;"><span>确认充值>></span></div>
    <div style="width: 10%;float: left;color: #0092DC"><span>充值>></span></div>
    <div style="width: 10%;float: left;"><span>充值成功</span></div>
</div>

<div style="width: 100%;height: 500px;padding:10px;">
    <div style="float: left; width:31%;height: 100%;">
        <input type="hidden" id="hipt_status" value="${order.orderNo}">
        <input type="hidden" id="hipt_orderId" value="${order.id}">
        <div style="padding: 20px">
            <h1 style="font-size: x-large;">充值详情</h1>
        </div>
        <div class="div_content">
            <label>充值编号：</label><label class="div_label" >${order.orderNo}</label>
        </div>
        <div class="div_content">

            <label>充值金额：</label><label class="div_label">${order.price}</label>
        </div>
        <div class="div_content">
            <label>备注：</label>
            <c:if test="${order.remark != null}">
                <label class="div_label">${order.remark}</label>
            </c:if>
            <c:if test="${order.remark == null}">
                <label class="div_label">无</label>
            </c:if>
        </div>
    </div>
    <div style="float: right; width:69%;height: 100%;">

        <div style="width: 100%;height: 100%;">
            <div style="width: 40%;height: 100%;float: left">
                <%--<input id="code" type="hidden" value="${payForm}">--%>
                <p class="class_weipay">微信支付</p>
                <input id="code" type="hidden" value="${payForm}">

                <div class="introduction">
                    <%--<p class="fl"><img src="/images/Pay_wx2.png"/></p>--%>
                    <div class="fl">请使用微信扫一扫&nbsp;&nbsp;扫描二维码支付</div>
                </div>
                <div id="qrcode"></div>
            </div>
            <div style="width: 60%;height: 100%;float: right">
                <div class="pay_wx_b_r"><p class="img"><img src="/images/Pay_wx2.jpg"/></p></div>
            </div>
        </div>


    </div>
</div>

<script src="/js/balance/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="/js/balance/webchatPay.js" type="text/javascript"></script>
</body>
</html>
