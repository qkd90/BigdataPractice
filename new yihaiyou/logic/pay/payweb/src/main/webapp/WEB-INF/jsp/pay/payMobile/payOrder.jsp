<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/28
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <title>提交订单-分销商</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
  <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/order.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--日期控件css-->
  <link href="${mallConfig.resourcePath}/css/date/default.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="${mallConfig.resourcePath}//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js?v=${mallConfig.resourceVersion}"></script>
  <script src="${mallConfig.resourcePath}//cdn.bootcss.com/respond.js/1.4.2/respond.min.js?v=${mallConfig.resourceVersion}"></script>
  <![endif]-->
</head>
<body>
<div class="header-wrap">
    <a class="left-btn" href="javascript:history.go(-1)"></a>
    <h1 class="pageTitle">产品预定</h1>
</div>
<div class="container">
  <div class="row">
    <div class="col-xs-11">
      <p><img src="images/order3.jpg" class="img-responsive"></p>
      <h4 class="order-info">您的订单旅行社已经确认（2015-10-12 19:20），请尽快完成支付，确保预定成功并享受预定时的优惠价。</h4>
      <!--预定信息-->
      <div class="box-order clearfix">
        <h5>预定产品：<small>
            <c:forEach var="productName" items="${orderMap.productName}" varStatus="status">
              ${productName}
            </c:forEach>
        </small><a href="#" class="pull-right order-info">订单详情</a></h5>
        <div class="box-order-content">
          <div class="media">
            <div class="media-left"><img src="images/alipay.jpg"></div>
            <div class="media-body">
              <h4>在线付款</h4>
              <p><span>该产品无需确认，直接付款即可预定成功。</span></p><br>
              <p>订单总额：<span>${orderMap.cost}</span>元   预定人数：<span>${orderMap.num}</span>人</p><br>
              <p>应付金额：<span class="price">${orderMap.cost}</span>元</p>
            </div>
          </div>
        </div>
      </div>
      <!--预定信息-->
      <div class="row text-center"><div class="total-payment"><a class="btn btn-warning" href="/pay/pay/payRequest.jhtml?orderId=${orderMap.id}">马上支付</a></div></div>
    </div>
  </div>
</div>


<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/zebra_datepicker.js"></script>
<script src="js/custom.js"></script>
</body>
</html>

