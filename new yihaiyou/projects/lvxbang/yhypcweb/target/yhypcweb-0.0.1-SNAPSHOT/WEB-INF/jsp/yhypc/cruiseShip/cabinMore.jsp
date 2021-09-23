<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>舱房更多</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/idangerous.swiper2.7.6.css">
    <link rel="stylesheet" href="/css/cruiseship/cabin_more.css">
</head>
<%@include file="../../yhypc/public/nav_header.jsp"%>
<body>
<!--主体部分-->
<div class="wrap-box">
    <div class="wrap">
        <div class="bread-nav clearfix">
            <span class="pull-left">您在这里：</span>
            <i class="pull-left"> &gt; </i>
            <a class="pull-left" href="#">天海邮轮预订</a>
            <i class="pull-left"> &gt; </i>
            <a class="pull-left" href="#">新世纪号预订</a>
            <i class="pull-left"> &gt; </i>
            <a class="pull-left" href="#">${cruiseShip.name}</a>
        </div>
        <div class="cabin-nav-wrap clearfix">
            <label class="pull-left">房型:</label>
            <ul class="cabin-nav pull-left clearfix">
                <li class="active pull-left" data-cabin="all">全部</li>
                <li class="pull-left" data-cabin="内舱房">内舱房</li>
                <li class="pull-left" data-cabin="海景房">海景房</li>
                <li class="pull-left" data-cabin="阳台房">阳台房</li>
                <li class="pull-left" data-cabin="套房">套房</li>
            </ul>
        </div>
        <div class="cabin-content">
            <div class="cabin-group" data-category="内舱房">
                <h5>内舱房</h5>
                <ul class="cabin-header clearfix">
                    <li class="header-img pull-left">图片</li>
                    <li class="header-name pull-left">名称</li>
                    <li class="header-person pull-left">容纳</li>
                    <li class="heaer-floor pull-left">楼层</li>
                    <li class="header-area pull-left">面积</li>
                    <li class="header-window pull-left">窗型</li>
                </ul>
                <div class="cabin-body">
                    <c:forEach items="${cruiseShipRoomInside}" var="cruiseShipRoomInsideItem">
                        <div class="cabin-body-group">
                            <ul class="body-group clearfix">
                                <li class="cabin-img pull-left">
                                    <img src="${QINIU_BUCKET_URL}${cruiseShipRoomInsideItem.coverImage}">
                                </li>
                                <li class="cabin-name pull-left">
                                        ${cruiseShipRoomInsideItem.name}
                                </li>
                                <li class="cabin-person pull-left">1-${cruiseShipRoomInsideItem.peopleNum}人</li>
                                <li class="cabin-floor pull-left">层</li>
                                <li class="cabin-area pull-left">${cruiseShipRoomInsideItem.area}</li>
                                <li class="cabin-window pull-left">无窗</li>
                                <li class="cabin-operation pull-left">详情<i></i></li>
                            </ul>
                            <div class="cabin-txt" style="display: none;">${cruiseShipRoomInsideItem.facilities}<a href="#">详情</a></div>
                        </div>

                    </c:forEach>
                </div>
            </div>
            <div class="cabin-group" data-category="海景房">
                <h5>海景房</h5>
                <ul class="cabin-header clearfix">
                    <li class="header-img pull-left">图片</li>
                    <li class="header-name pull-left">名称</li>
                    <li class="header-person pull-left">容纳</li>
                    <li class="heaer-floor pull-left">楼层</li>
                    <li class="header-area pull-left">面积</li>
                    <li class="header-window pull-left">窗型</li>
                </ul>
                <div class="cabin-body">
                    <c:forEach items="${cruiseShipRoomSeascape}" var="cruiseShipRoomSeascapeItem">
                        <div class="cabin-body-group">
                            <ul class="body-group clearfix">
                                <li class="cabin-img pull-left">
                                    <img src="${QINIU_BUCKET_URL}${cruiseShipRoomSeascapeItem.coverImage}">
                                </li>
                                <li class="cabin-name pull-left">
                                        ${cruiseShipRoomSeascapeItem.name}
                                </li>
                                <li class="cabin-person pull-left">1-${cruiseShipRoomSeascapeItem.peopleNum}人</li>
                                <li class="cabin-floor pull-left">层</li>
                                <li class="cabin-area pull-left">${cruiseShipRoomSeascapeItem.area}</li>
                                <li class="cabin-window pull-left">无窗</li>
                                <li class="cabin-operation pull-left">详情<i></i></li>
                            </ul>
                            <div class="cabin-txt" style="display: none;">${cruiseShipRoomSeascapeItem.facilities}<a href="#">详情</a></div>
                        </div>

                    </c:forEach>

                </div>
            </div>
            <div class="cabin-group" data-category="阳台房">
                <h5>阳台房</h5>
                <ul class="cabin-header clearfix">
                    <li class="header-img pull-left">图片</li>
                    <li class="header-name pull-left">名称</li>
                    <li class="header-person pull-left">容纳</li>
                    <li class="heaer-floor pull-left">楼层</li>
                    <li class="header-area pull-left">面积</li>
                    <li class="header-window pull-left">窗型</li>
                </ul>
                <div class="cabin-body">
                    <c:forEach items="${cruiseShipRoomBalcony}" var="cruiseShipRoomBalconyItem">
                        <div class="cabin-body-group">
                            <ul class="body-group clearfix">
                                <li class="cabin-img pull-left">
                                    <img src="${QINIU_BUCKET_URL}${cruiseShipRoomBalconyItem.coverImage}">
                                </li>
                                <li class="cabin-name pull-left">
                                        ${cruiseShipRoomBalconyItem.name}
                                </li>
                                <li class="cabin-person pull-left">1-${cruiseShipRoomBalconyItem.peopleNum}人</li>
                                <li class="cabin-floor pull-left">层</li>
                                <li class="cabin-area pull-left">${cruiseShipRoomBalconyItem.area}</li>
                                <li class="cabin-window pull-left">无窗</li>
                                <li class="cabin-operation pull-left">详情<i></i></li>
                            </ul>
                            <div class="cabin-txt" style="display: none;">${cruiseShipRoomBalconyItem.facilities}<a href="#">详情</a></div>
                        </div>

                    </c:forEach>
                </div>
            </div>
            <div class="cabin-group" data-category="套房">
                <h5>套房</h5>
                <ul class="cabin-header clearfix">
                    <li class="header-img pull-left">图片</li>
                    <li class="header-name pull-left">名称</li>
                    <li class="header-person pull-left">容纳</li>
                    <li class="heaer-floor pull-left">楼层</li>
                    <li class="header-area pull-left">面积</li>
                    <li class="header-window pull-left">窗型</li>
                </ul>
                <div class="cabin-body">
                    <c:forEach items="${cruiseShipRoomSuite}" var="cruiseShipRoomSuiteItem">
                        <div class="cabin-body-group">
                            <ul class="body-group clearfix">
                                <li class="cabin-img pull-left">
                                    <img src="${QINIU_BUCKET_URL}${cruiseShipRoomSuiteItem.coverImage}">
                                </li>
                                <li class="cabin-name pull-left">
                                        ${cruiseShipRoomSuiteItem.name}
                                </li>
                                <li class="cabin-person pull-left">1-${cruiseShipRoomSuiteItem.peopleNum}人</li>
                                <li class="cabin-floor pull-left">层</li>
                                <li class="cabin-area pull-left">${cruiseShipRoomSuiteItem.area}</li>
                                <li class="cabin-window pull-left">无窗</li>
                                <li class="cabin-operation pull-left">详情<i></i></li>
                            </ul>
                            <div class="cabin-txt" style="display: none;">${cruiseShipRoomSuiteItem.facilities}<a href="#">详情</a></div>
                        </div>

                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<!--主体部分  ending-->

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/cabin_more.js"></script>
</body>
</html>
<%@include file="../../yhypc/public/nav_footer.jsp"%>