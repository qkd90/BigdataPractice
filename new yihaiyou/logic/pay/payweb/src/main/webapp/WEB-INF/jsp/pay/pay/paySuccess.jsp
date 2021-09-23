<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/28
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN"><head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>提交订单-分销商</title>
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
<%--<%@include file="/WEB-INF/jsp/mall/common/header.jsp"%>--%>

<div class="container">
  <div class="row">
    <div class="col-xs-12">
      <div class="clearfix here">您的位置：<a href="#">首页</a> &gt; <a href="#">产品预定</a></div>
    </div>
  </div>
  <div class="row">
    <div class="col-xs-11">
      <p><img src="/images/order4.jpg" class="img-responsive"></p>
      <!--成功信息-->
      <div class="box-order clearfix">
        <div class="box-order-content" id="order-ok">
          <p class="ok">恭喜您成功预订旅游线路，<br>短信将会10分钟内发生到您的手机，请注意查收！</p>
          <p><a href="/user/home/order.jhtml">查看订单详情 &gt;&gt;</a></p>
        </div>
      </div>
      <!--成功信息-->
    </div>
  </div>
</div>


<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<script src="/js/zebra_datepicker.js"></script>
<script src="/js/custom.js"></script>


</body></html>
