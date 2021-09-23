<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <title>轮渡船票</title>
    <link rel="stylesheet" href="/css/ferry/ferry-order-complete.css">
</head>
<body>
<div class="hotelIndex">
<%@include file="../../yhypc/public/nav_header.jsp"%>
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
<div class="order-complete-wrap">
    <div class="order-complete-info clearfix">
        <div class="order-complete-info-left clearfix">
            <i class="order-complete-icon"></i>
            <div class="order-complete-info-data">
                <h4>恭喜您，订单预订成功！我们将尽快给你发送确认短信。</h4>
            </div>
        </div>
        <div class="order-complete-info-right clearfix">
            <label>订单总额：</label>
            <span><sub>￥</sub>238</span>
        </div>
    </div>
    <div class="order-complete-link">
        <div class="order-complete-link-wrap clearfix">
            <div class="order-complete-link-group">
                <label>订单号码：</label>
                <span class="order-num">160993183921000007 </span>
            </div>
            <div class="order-complete-link-group">
                <label>出发日期：</label>
                <span>2016年12月13日<i>12:00后</i></span>
            </div>
            <div class="order-complete-link-group">
                <label>支付方式：</label>
                <span>在线支付 </span>
            </div>
            <div class="order-complete-link-group">
                <label>购买数量：</label>
                <span>2</span>
            </div>
        </div>
        <div class="order-complete-link-btn">
            <a class="color-yellow">查看订单详情</a>
            <a class="color-blue">返回首页<i></i></a>
        </div>
    </div>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script src="/js/ferry/ferry_order_complete.js"></script>