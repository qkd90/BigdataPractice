<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/12/14
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的订单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link href="/css/mobile/user/myorder.css" rel="stylesheet">
</head>
<body>

<div>
    <div>
        <ul id="myorders"></ul>
        <div id="no-orders" class="display-none">
            <p>您还没有创建过订单</p>
        </div>
    </div>
</div>
</body>

<script src="${mallConfig.resourcePath}/js/jquery.min.js?${mallConfig.resourceVersion}"></script>
<%--<script src="${mallConfig.resourcePath}/js/mobile/user/home.js?${mallConfig.resourceVersion}"></script>--%>
<script src="${mallConfig.resourcePath}/js/mobile/user/myorder.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jsrender.js?${mallConfig.resourceVersion}"></script>

<script type="text/html" id="order-list">
    <li class="back-white">
        <a href="/pay/payMobile/confirmOrder.jhtml?orderId={{:id}}">
            <div class="order-header border-bottom cf">
                <div class="order-name float-left">{{:name}}</div>
                <div class="float-right">
                    {{:orderDate}}
                </div>
            </div>
            <div class="cf">
                <div class="float-left">
                    <img class="order-cover" src="/css/mobile/user/face.jpg">
                </div>
                <div class="float-left detail-padding-top">
                    <div>
                        <span>订单总价 ¥{{:fee}}(
                            <span class="order-status">
                                {{:status}}
                            </span>
                            )
                        </span>
                    </div>
                    <div>
                        <span>购买数量 {{:count}}</span>
                    </div>
                    <div>
                        <span>游玩日期 {{:date}}</span>
                    </div>

                </div>

            </div>
        </a>
    </li>
</script>
</html>
