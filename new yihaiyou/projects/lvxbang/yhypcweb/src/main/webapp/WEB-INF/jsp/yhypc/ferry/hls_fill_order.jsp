 <%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <title>轮渡船票</title>
    <link rel="stylesheet" href="/css/ferry/lj-fill-order.css">
</head>
<body>
<div class="hotelIndex">
<%@include file="../../yhypc/public/order_header.jsp" %>
<div class="nav">
    <ul class="clearfix">
        <li>
            <a href="#">
                填写订单
                <span class="triangle1"></span>
            </a>
        </li>
        <li>
            <a href="#">
                <span class="triangle2"></span>
                在线支付
                <span class="triangle3"></span>
            </a>
        </li>
        <li>
            <a href="#">
                <span class="triangle4"></span>
                订单完成
            </a>
        </li>
    </ul>
</div>
<div class="fill-order-wrap">
    <h3 class="fill-order-head">预定信息</h3>
    <form>
        <div class="fill-order-body">
            <h4>航班信息
                <span class="head-bottom-line"></span>
            </h4>
            <div class="fill-order-content">
                <div class="fill-order-content-part1">
                    <div class="input-group clearfix">
                        <label><i>名</i><i>称</i>：</label>
                        <span>胡里山炮台景区-手机语音导游</span>
                    </div>
                    <div class="input-group clearfix">
                        <label><i>票</i><i>价</i>：</label>
                        <span>￥2.0</span>
                    </div>
                    <div class="input-group clearfix">
                        <label>下单时间：</label>
                        <span>${orderTime}</span>
                    </div>
                </div>
                <div class="customer-name">
                    <%--<h5>选择乘客</h5>--%>
                    <div class="customer-name-check clearfix">
                        <label class="pull-left"><sub>*</sub>姓名：</label>
                        <input class="pull-left" id="userName" type="text" name="userName">
                        <label class="pull-left"><sub>*</sub>手机号码：</label>
                        <input class="pull-left" id="mobile" type="text" name="mobile">
                        <input type="hidden" id="productId" value="${productId}">
                    </div>
                    <p class="tips">温馨提示：购买成功后，我们会将手机语音导游短信授权码发送至该手机号。</p>
                </div>
            </div>
        </div>
        <div class="fill-order-footer clearfix">
            <div class="fill-order-footer-left">
                <div class="input-group clearfix">
                    <input type="checkbox" name="agree" class="agree" id="agree" checked>
                    <span class="fill-order-footer-radio"></span>
                    <label for="agree">我已阅读并接受合同条款、补充条款及其他所有内容</label>
                </div>
            </div>
            <div class="fill-order-footer-right clearfix">
                <div class="fill-order-cost clearfix">
                    <label>订单总额:</label>
                    <span id="total-span"><sup>￥</sup>2</span>
                </div>
                <a href="#" onclick="Tourist.submitLvji()">提交订单</a>
            </div>
        </div>
    </form>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>

<%@include file="../../yhypc/public/footer.jsp"%>
<script src="/js/public/tourist.js"></script>
