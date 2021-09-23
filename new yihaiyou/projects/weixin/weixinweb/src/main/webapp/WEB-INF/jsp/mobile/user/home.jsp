<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/23
  Time: 09:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/index/index.css?${mallConfig.resourceVersion}">
    <link href="/css/mobile/user/home.css" rel="stylesheet">
</head>
<body>
<div>

    <input id="accountId" type="hidden" value="${accountId}">
    <input id="userId" type="hidden" value="${user.id}">
    <%--header--%>
    <div id="header" style="height: 30%; ;background-image: url('/css/mobile/user/back.jpg')" class="back-white">
        <div id="header-content">
            <div id="header-face">
                <div id="face-div" class="">
                    <img id="face-img" src="${wechatFollower.headImgUrl}" class="">
                </div>
            </div>
            <div id="header-detail" class="user-info">
                <div id="user-name">
                    <label>${wechatFollower.nickName}</label>
                    <label>(${user.userType})</label>
                </div>
                <div id="vip-id">
                    <label>会员ID：</label>
                    <label>${user.id}</label>
                </div>
                <div id="follow-time">
                    <label>加入时间：</label>
                    <label>
                        <fmt:formatDate value="${user.createdTime}" pattern="yyyy-MM-dd" />
                    </label>
                </div>
                <div id="recommender">
                    <label>推荐人：</label>
                    <label>
                        <s:if test="parentFollower == null">空（您还是独行侠）</s:if>
                        <s:else>${parentFollower.nickName}</s:else>
                    </label>
                </div>
            </div>
        </div>
    </div>
    <%--nav--%>
    <div id="nav-bar">
        <div id="bar-content" class="back-white cf">
            <li id="my-team" class="center-bar">
                <a href="/mobile/user/team.jhtml" class="bar-a">
                    我的团队
                </a>
            </li>
            <li id="business-card" class="center-bar">
                <a href="#" class="bar-a">
                    我的名片
                </a>
            </li>
            <li id="month-cost" class="center-bar">
                <a href="#" class="bar-a">
                    当月消费
                </a>
            </li>
            <li id="popularize" class="center-bar">
                <a href="#" class="bar-a">
                    推广素材
                </a>
            </li>
            <li id="aftermarket" class="center-bar">
                <a href="#" class="bar-a">
                    退款售后
                </a>
            </li>
        </div>
    </div>

    <%--menu--%>
    <div id="menu" class="back-white">
        <li class="menu-div cf">
            <div class="float-left">
                <a href="#" class="home-menu-a menu-text">电子钱包</a>
            </div>
            <div class="float-right">
                <label class="orange-font menu-text">0.00</label>元
                <a href="#" class="orange-font menu-text">提现</a>
            </div>
        </li>
        <li class="menu-div cf">
            <div class="float-left">
                <a href="#" class="home-menu-a menu-text">积分</a>
            </div>
            <div class="float-right">
                <label class="orange-font menu-text">0.00</label>分
            </div>
        </li>
        <li class="menu-div">
            <div>
                <a href="/mobile/user/order.jhtml" class="home-menu-a menu-text">我的订单</a>
            </div>
        </li>
    </div>

    <div id="share-text">
        <span>
            您可以点击右上角然后选择分享给好友或者分享到朋友圈来邀请好友
        </span>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mobile/common/footer-nav.jsp"></jsp:include>
	<script type="text/javascript">
	var WEIXIN_DOMAIN = '<s:property value="weixinDomain"/>';
	</script> 
<script src="${mallConfig.resourcePath}/js/jquery.min.js?${mallConfig.resourceVersion}"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${mallConfig.resourcePath}/js/mobile/share/share_util.js${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/mobile/user/home.js?${mallConfig.resourceVersion}"></script>
</body>
</html>
