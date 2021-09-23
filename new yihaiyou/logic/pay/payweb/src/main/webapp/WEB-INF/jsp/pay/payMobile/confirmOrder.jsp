<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/28
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html lang="zh-CN"><head>
  <meta charset="utf-8">
  <title>提交订单-分销商</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
  <link href="${mallConfig.resourcePath}/css/mobile/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/order.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/mobile/order_detail.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--日期控件css-->
  <link href="${mallConfig.resourcePath}/css/date/default.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js></script>
  <![endif]-->
</head>
<body>
<div class="header-wrap">
    <a class="left-btn" href="javascript:history.go(-1)"></a>
    <h1 class="pageTitle">产品预定</h1>
</div>
<div class="container">
  <div class="row">
    <div>
      <p><img src="images/order2.jpg" class="img-responsive"></p>
      <h4>订单编号：${order.orderNo}</h4>
      <!--预定信息-->
      <div class="box-order clearfix">
        <h4>预订信息</h4>
        <div class="box-order-content">
          <c:forEach var="detail" items="${order.orderDetails}" varStatus="status">
            <div>
              <p>预定产品：${detail.product.name}</p>
              <p>价格类型：${detail.costName}</p>
              <p>出发日期：<fmt:formatDate value="${detail.playDate}" pattern="yyyy-MM-dd"/></p>
              <p>预定数量：${detail.num}</p>
              <p>订单总额：${detail.finalPrice}</p>
            </div>
          </c:forEach>
        </div>
      </div>
      <!--预定信息-->
      <!--联系方式-->
      <div class="box-order clearfix">
        <h4>联系方式</h4>

          <div class="box-order-content">
              下单时间：<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
              联系人：${order.recName} <br>
              手机号码：${order.mobile}<br>
          </div>
      </div>
      <!--联系方式-->
      <input id="order-id" type="hidden" value="${order.id}">

        <div>
            <c:choose>
                <c:when test="${expired=='true'}">
                    <div>
                        <p class="msg">您的订单已经过期，如需要请重新下单</p>
                    </div>

                </c:when>
                <c:when test="${order.status=='UNCONFIRMED'}">
                    <div>
                        <p class="msg">您的订单需要确认之后才可以支付</p>
                    </div>

                </c:when>
                <c:when test="${order.status=='WAIT'}">
                    <div>
                        <p class="msg">您的订单还未支付，可以点击下方按钮立即支付</p>
                    </div>

                    <div class="row text-center">
                        <div class="total-payment"><a class="btn btn-warning" onclick="Wechatpay.payOrder(${order.id})">全额支付</a>
                        </div>
                    </div>
                </c:when>
                <c:when test="${order.status=='PAYED'}">
                    <div>
                        <p class="msg">您的订单已经成功支付，祝您旅途愉快</p>
                    </div>

                </c:when>
                <c:when test="${order.status=='REFUND'}">
                    <div>
                        <p class="msg">您的订单已经成功退款</p>
                    </div>

                </c:when>
                <c:otherwise>
                    <div class="msg">
                        <span>订单已失效</span>
                    </div>

                </c:otherwise>
            </c:choose>
        </div>

    </div>
  </div>
</div>

<script type="text/javascript" src="/js/wxpay.js"></script>
<script src="/js/jquery.min.js"></script>

</body></html>
