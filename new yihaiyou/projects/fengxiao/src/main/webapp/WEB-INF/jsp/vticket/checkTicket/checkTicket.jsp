<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="checkTicket">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <meta name="browsermode" content="application">
    <meta name="x5-page-mode" content="app">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="telephone=no" name="format-detection">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>验证</title>
    <link rel="stylesheet" href="/lib/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="/css/vticket/checkTicket/checkTicket.css">

    <script src="/lib/angular/angular.min.js"></script>
    <script src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
    <script src="/js/vticket/checkTicket/checkTicket.js"></script>
</head>
<body ng-controller="checkArea">
    <div class="checkTicket" screenheight x="100">
        <div class="checkTitle">一海游验票系统</div>
        <div class="checkCode">
            <input type="num" placeholder="输入验证码" ng-model="code">
        </div>
        <div class="checkCode checkcode2">
            <button ng-click="check()">验证</button>
        </div>
        <div class="checkshadow" ng-show="shadow"></div>
        <!-- 验票成功 -->
        <div class="stateBox successbox" ng-show="success" centerbox>
            <div class="checkcenter">
                <div class="pic"></div>
                <p class="checkstate">验票成功</p>
                <div class="checkBtn">
                    <button class="stop" ng-click="checkClose()">关闭验票</button>
                </div>
                <div class="checkBtn">
                    <button class="stop continue" ng-click="goOnCheck()">继续验票</button>
                </div>
            </div>
        </div>
        <!-- 验票失败 -->
        <div class="stateBox failbox" ng-show="fail" centerbox>
            <div class="checkcenter">
                <div class="pic"></div>
                <p class="checkstate" style="font-size: 1.6rem;">验票失败{{message}}</p>
                <div class="checkBtn">
                    <button class="stop" ng-click="checkClose()">关闭验票</button>
                </div>
                <div class="checkBtn">
                    <button class="stop continue" ng-click="goOnCheck()">继续验票</button>
                </div>
            </div>
        </div>
        <!-- 验票失败已验过 -->
       <%-- <div class="stateBox checkedbox" ng-show="checked" centerbox>
            <div class="checkcenter">
                <div class="pic"></div>
                <p class="checkstate">验票失败！已验过</p>
                <div class="checkBtn">
                    <button class="stop" ng-click="checkClose()">关闭验票</button>
                </div>
                <div class="checkBtn">
                    <button class="stop continue">继续验票</button>
                </div>
            </div>
        </div>--%>
    </div>
</body>
</html>