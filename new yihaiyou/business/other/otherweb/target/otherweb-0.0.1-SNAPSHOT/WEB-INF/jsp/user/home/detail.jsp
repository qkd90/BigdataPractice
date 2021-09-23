<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/16
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>个人中心</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/geren.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <!--日期控件css-->
    <link href="${mallConfig.resourcePath}/css/date/default.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="container-bg clearfix">
    <div class="container">
        <div class="row">
            <jsp:include page="left-nav.jsp"></jsp:include>
            <div class="col-xs-10">
                <div class="gr-order-box clearfix">
                    <div class="order-info">
                        <h5 class="sku">订单编号：${order.id} <a href="javascript:history.go(-1)" class="btn btn-primary pull-right">返回</a></h5>
                        <h5>订单状态：${order.status.getDescription()}</h5>
                        <hr>
                        <h5>游客信息</h5>
                        <p>下单时间：${order.createTime}</p>
                        <p>联 系  人：${order.recName}</p>
                        <p>手 机  号：${order.mobile}</p>
                        <p class="clearfix">
                        <form>
                            <label for="inputEmail3" class="control-label">补充说明：</label>
                            <textarea class="form-control" rows="8">${order.remark}</textarea>
                        </form>
                        </p>
                        <br>
                        <hr>
                        <h5>预定信息</h5>
                        <c:forEach items="${order.orderDetails}" var="detail">
                            <p>产品名称：${detail.product.name}</p>
                            <p>产品类型：${detail.product.proType.getDescription()}</p>
                            <p>
                                销 售  价：${detail.finalPrice}
                                <%--<span class="col-xs-offset-2">分销价：90</span>--%>
                            </p>
                            <p>预订数量：${detail.num}</p>
                            <p>预订日期：${detail.playDate}</p>
                            <br>
                        </c:forEach>
                        <hr>
                        <h5>支付信息</h5>
                        <p>订单总额：<b class="price">${totalPayAmount}</b></p>
                    </div>
                </div>
            </div>
            <!------------------->
        </div>
    </div>
</div>

<!--底部-->
<jsp:include page="footer.jsp"></jsp:include>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?${mallConfig.resourceVersion}"></script>
</body>
</html>
