<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/22
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            margin: 10px 20px;
            font-weight: 600;
        }
        .div_label {

        }
        .div_config_btn {
            margin: 20px 20px;
        }
        .div_config_btn a {
            margin-right: 20px;
        }
    </style>
    <script src="/js/balance/paySuccess.js" type="text/javascript"></script>
</head>
<body style="background-color: white">

<div class="row_hd">
    <div style="width: 10%;float: left;"><span>提交申请>></span></div>
    <div style="width: 10%;float: left;"><span>确认充值>></span></div>
    <div style="width: 10%;float: left;"><span>充值>></span></div>
    <div style="width: 10%;float: left;color: #0092DC"><span>充值失败</span></div>
</div>

<div style="padding: 20px">
    <h2 style="font-size: x-large;color:red;">充值失败</h2>
</div>
    <div class="div_content">
        <label>充值编号：</label><label class="div_label" >${order.orderNo}</label>
    </div>
    <div class="div_content">

        <label>充值金额：</label><label class="div_label">${order.price}</label>
    </div>
    <div class="div_content">
        <label>账户余额：</label><label class="div_label">${user.balance}</label>
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
<div class="div_config_btn">
    <input type="hidden" id="hipt_orderId" value="${order.id}"/>
    <%--<input type="hidden" id="hipt_productId" value="${product.id}">--%>
    <a id="btn1" href="#" class="easyui-linkbutton" onclick="PaySuccess.backManage()" >返回首页</a>
    <a id="btn2" href="#" class="easyui-linkbutton" onclick="PaySuccess.toCreateOrder()" >重新充值</a>
</div>
</body>
</html>
