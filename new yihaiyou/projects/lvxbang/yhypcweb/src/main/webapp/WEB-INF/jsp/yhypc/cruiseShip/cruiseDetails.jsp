<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en"><head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>舱房详情</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/idangerous.swiper2.7.6.css">
    <link rel="stylesheet" href="/css/cruiseship/cruise_details.css">
</head>
<%@include file="../../yhypc/public/nav_header.jsp"%>
<body>
<!--主体部分-->
<div class="wrap-box">
    <div class="wrap">
        <div class="bread-nav clearfix">
            <span class="pull-left">您在这里：</span>
            <%--<i class="pull-left"> &gt; </i>--%>
            <%--<a class="pull-left" href="#">天海邮轮预订</a>--%>
            <%--<i class="pull-left"> &gt; </i>--%>
            <%--<a class="pull-left" href="#">新世纪号预订</a>--%>
            <i class="pull-left"> &gt; </i>
            <a class="pull-left" href="#">${cruiseShip.name}</a>
        </div>
    </div>
</div>
<div class="banner-wrap" style="margin-bottom: 0px;">
    <div class="banner-swiper">
        <div class="swiper-wrapper" style="width: 5900px; height: 460px; transform: translate3d(-4720px, 0px, 0px); transition-duration: 0.3s;">

            <c:forEach items="${cruiseShip.productimage}" var="productimageItem">

                <div class="swiper-slide" style="width: 1180px; height: 460px;">
                    <a href="#">
                        <img src="${QINIU_BUCKET_URL}${productimageItem.path}">
                    </a>
                </div>
            </c:forEach>

        </div>
        <div class="pagination"><span class="swiper-pagination-switch swiper-visible-switch swiper-active-switch"></span><span class="swiper-pagination-switch"></span><span class="swiper-pagination-switch"></span></div>
    </div>
    <div class="banner-txt">
        <div class="txt-header clearfix">
            <img src="${QINIU_BUCKET_URL}${cruiseShip.coverImage}" class="logo">
            <div class="txt-title">
                <%--<p>SKYSEA CRUISE LINE GOLDEN ERA</p>--%>
                <h6>${cruiseShip.name}<a href="javascript:;" class="details-btn">简介<i></i></a></h6>
            </div>
            <div class="score">
                <span>4.8<sub>/5分</sub></span>
                <p>5467人点评</p>
            </div>
        </div>
        <div class="txt-content" style="display: none;">
            ${cruiseShip.services}
        </div>
        <%--<div class="txt-tips">--%>
            <%--<span><i class="icon01"></i>高性价比邮轮</span>--%>
            <%--<span><i class="icon02"></i>舌尖上的邮轮</span>--%>
            <%--<span><i class="icon03"></i>五星服务邮轮</span>--%>
            <%--<span><i class="icon04"></i>"海上微信"App</span>--%>
        <%--</div>--%>
        <%--<div class="cruise-info">--%>
            <%--<span>吨位：71,545吨</span>--%>
            <%--<i></i>--%>
            <%--<span>载客：1,814人</span>--%>
            <%--<i></i>--%>
            <%--<span>首航：2015年</span>--%>
            <%--<i></i>--%>
            <%--<span>楼层：15层</span>--%>
            <%--<i></i>--%>
            <%--<span>长度：246米</span>--%>
            <%--<i></i>--%>
            <%--<span>宽度：32米</span>--%>
            <%--<i></i>--%>
            <%--<span>船速：19节</span>--%>
        <%--</div>--%>
    </div>
</div>
<!--导航-->
<ul class="cruise-nav clearfix">
    <li class="pull-left active"><a href="javascript:;">舱房介绍</a></li>
    <li class="pull-left"><a href="javascript:;">海上美食</a></li>
    <li class="pull-left"><a href="javascript:;">邮轮玩乐</a></li>
    <li class="pull-left"><a href="javascript:;">邮轮服务</a></li>
    <li class="pull-left"><a href="javascript:;">甲板导航</a></li>
    <li class="pull-left"><a href="javascript:;">常见问题</a></li>
</ul>
<!--舱房-->
<div class="content-group">
    <h4>舱房介绍</h4>
    <div class="cabin-nav-wrap">
        <ul class="cabin-nav clearfix">
            <li class="nav-li active">
                <a href="javascript:;">
                    <span>内舱房</span>
                    <span class="count">${fn:length(cruiseShipRoomInside)}间</span>
                </a>
            </li>
            <li class="nav-li">
                <a href="javascript:;">
                    <span>海景房</span>
                    <span class="count">${fn:length(cruiseShipRoomSeascape)}间</span>
                </a>
            </li>
            <li class="nav-li">
                <a href="javascript:;">
                    <span>阳台房</span>
                    <span class="count">${fn:length(cruiseShipRoomBalcony)}间</span>
                </a>
            </li>
            <li class="nav-li">
                <a href="javascript:;">
                    <span>套房</span>
                    <span class="count">${fn:length(cruiseShipRoomSuite)}间</span>
                </a>
            </li>
            <li>

                <a href="/yhypc/cruiseShip/cabinMore.jhtml?cruiseShipId=${cruiseShip.id}" class="cabin-more">更多<i></i></a>
            </li>
        </ul>
    </div>
    <div class="cabin-group" data-category="内舱房">
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
                        <li class="cabin-floor pull-left">${cruiseShipRoomInsideItem.deck}层</li>
                        <li class="cabin-area pull-left">${cruiseShipRoomInsideItem.area}</li>
                        <li class="cabin-window pull-left">无窗</li>
                        <li class="cabin-operation pull-left">详情<i></i></li>
                    </ul>
                    <div class="cabin-txt">${cruiseShipRoomInsideItem.facilities}<a class="more" href="#"></a></div>
                </div>

            </c:forEach>

        </div>
    </div>
    <div class="cabin-group" data-category="海景房" style="display: none;">
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
                        <li class="cabin-floor pull-left">${cruiseShipRoomSeascapeItem.deck}层</li>
                        <li class="cabin-area pull-left">${cruiseShipRoomSeascapeItem.area}</li>
                        <li class="cabin-window pull-left">无窗</li>
                        <li class="cabin-operation pull-left">详情<i></i></li>
                    </ul>
                    <div class="cabin-txt">${cruiseShipRoomSeascapeItem.facilities}<a class="more" href="#">了解更多</a></div>
                </div>

            </c:forEach>

        </div>
    </div>
    <div class="cabin-group" data-category="阳台房" style="display: none;">
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
                        <li class="cabin-floor pull-left">${cruiseShipRoomBalconyItem.deck}层</li>
                        <li class="cabin-area pull-left">${cruiseShipRoomBalconyItem.area}</li>
                        <li class="cabin-window pull-left">无窗</li>
                        <li class="cabin-operation pull-left">详情<i></i></li>
                    </ul>
                    <div class="cabin-txt">${cruiseShipRoomBalconyItem.facilities}<a class="more" href="#">了解更多</a></div>
                </div>

            </c:forEach>

        </div>
    </div>
    <div class="cabin-group" data-category="套房" style="display: none;">
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
                        <li class="cabin-floor pull-left">${cruiseShipRoomSuiteItem.deck}层</li>
                        <li class="cabin-area pull-left">${cruiseShipRoomSuiteItem.area}</li>
                        <li class="cabin-window pull-left">无窗</li>
                        <li class="cabin-operation pull-left">详情<i></i></li>
                    </ul>
                    <div class="cabin-txt">${cruiseShipRoomSuiteItem.facilities}<a class="more" href="#">了解更多</a></div>
                </div>

            </c:forEach>

        </div>
    </div>
</div>
<!--美食-->
<c:if test="${listCruiseShipProjectClassifyFood.size() > 0}">
<div class="content-group food-box">
    <img src="/image/banner.png" class="food-box-bg">
    <div class="food-wrap">
        <div class="food">
            <h4>海上美食</h4>
            <div class="food-nav-wrap">
                <ul class="cabin-nav clearfix">
                    <c:forEach items="${listCruiseShipProjectClassifyFood}" var="foodClassify">
                    <li class="nav-li active">
                        <a href="javascript:;">
                            <span>${foodClassify.classifyName}</span>
                            <span class="count">${foodClassify.cruiseShipProject.size()}间</span>
                        </a>
                    </li>
                    </c:forEach>

                    <li>
                        <a href="/yhypc/cruiseShip/productMore.jhtml?parentId=2" class="cabin-more">更多<i></i></a>
                    </li>
                </ul>
            </div>
            <c:forEach items="${listCruiseShipProjectClassifyFood}" var="listCruiseShipProjectClassifyFoodItem" varStatus="status">
                <c:if test="${status.index < 3}">
            <div class="main-restaurant restaurant-group" data-food="${listCruiseShipProjectClassifyFoodItem.classifyName}">
                <a href="javascript:;" class="main-prev prev"></a>
                <div class="swiper-wrapper" style="width: 1597.33px; height: 385px;">
                    <c:forEach items="${listCruiseShipProjectClassifyFoodItem.cruiseShipProject}" var="project">
                    <div class="swiper-slide swiper-slide-visible swiper-slide-active" style="width: 399.333px; height: 385px;">
                        <img src="${QINIU_BUCKET_URL}${project.coverImage}">
                        <h6>${project.name}</h6>
                        <div class="tips clearfix">
                            <span class="pull-left">楼层：${project.level}</span>
                            <i class="pull-left">|</i>
                            <span class="pull-left">容纳：${project.peopleNum}人</span>
                            <i class="pull-left">|</i>
                            <span class="pull-left">消费：${project.costStatus}</span>
                        </div>
                        <div class="txt">
                            ${project.introduction}
                        </div>
                    </div>
                    </c:forEach>
                </div>
                <a href="javascript:;" class="main-next next"></a>
            </div>
                </c:if>
            </c:forEach>

        </div>
    </div>
</div>
</c:if>
<!--娱乐-->
<c:if test="${listCruiseShipProjectClassifyEntertainmen.size() > 0}">
<div class="content-group play">
    <h4>邮轮娱乐</h4>
    <div class="play-nav-wrap">
        <ul class="cabin-nav clearfix">

            <c:forEach items="${listCruiseShipProjectClassifyEntertainmen}" var="entainmentClassify">
            <li class="nav-li active">
                <a href="javascript:;">
                    <span>${entainmentClassify.classifyName}</span>
                    <span class="count">${entainmentClassify.cruiseShipProject.size()}间</span>
                </a>
            </li>
                </c:forEach>

            <li>
                <a href=""/yhypc/cruiseShip/productMore.jhtml?parentId=3"" class="cabin-more">更多<i></i></a>
            </li>
        </ul>
    </div>
    <c:forEach items="${listCruiseShipProjectClassifyEntertainmen}" var="listCruiseShipProjectClassifyEntainmentItem" varStatus="status">
        <c:if test="${status.index < 3}">
        <div class="play-boat play-group" data-food="${listCruiseShipProjectClassifyEntertainmenItem.classifyName}">
            <a href="javascript:;" class="boat-prev prev"></a>
            <div class="swiper-wrapper" style="width: 1597.33px; height: 385px;">
        <c:forEach items="${listCruiseShipProjectClassifyEntainmentItem.cruiseShipProject}" var="project">
                <div class="swiper-slide swiper-slide-visible swiper-slide-active" style="width: 399.333px; height: 385px;">
                    <img src="${QINIU_BUCKET_URL}${project.coverImage}">
                    <h6>${project.name}</h6>
                    <div class="tips clearfix">
                        <span class="pull-left">楼层：${project.level}</span>
                        <i class="pull-left">|</i>
                        <span class="pull-left">容纳：${project.peopleNum}人</span>
                        <i class="pull-left">|</i>
                        <span class="pull-left">消费：${project.costStatus}</span>
                    </div>
                    <div class="txt">
                            ${project.introduction}
                    </div>
                </div>
            </c:forEach>
            </div>
            <a href="javascript:;" class="boat-next next"></a>
        </div>
        </c:if>
    </c:forEach>



</div>
</c:if>
<!--邮轮服务-->
<c:if test="${listCruiseShipProjectClassifyService.size() > 0}">
<div class="content-group server">
    <div class="server-wrap">
        <h4>邮轮服务</h4>
        <ul class="server-group clearfix">
            <c:forEach items="${listCruiseShipProjectClassifyService}" var="project" varStatus="status">
                <c:if test="${status.index < 4}">
                    <li class="pull-left">
                        <a href="#">
                            <img src="${QINIU_BUCKET_URL}${project.coverImage}">
                            <div class="title">${project.name}</div>
                            <div class="tips">
                                <span>消费：${project.costStatus}</span>
                                <span>位置：世界套房</span>
                            </div>
                        </a>
                    </li>

                </c:if>
            </c:forEach>

        </ul>
        <div class="server-more">
            <a href=/yhypc/cruiseShip/productMore.jhtml?parentId=3">查看更多<i></i></a>
        </div>
    </div>
</div>
</c:if>
<!--甲板导航-->
<%--<div class="deck content-group">--%>
    <%--<h4>甲板导航</h4>--%>
    <%--<div class="deck-img">--%>
        <%--<img src="/image/cruise_details_deck_img.png">--%>
    <%--</div>--%>
    <%--<a href="#" class="more">查看甲板详情</a>--%>
<%--</div>--%>
<!--常见问题-->
<div class="question-wrap content-group">
    <div class="question">
        <h4>常见问题</h4>
        <div class="question-group">
            <div class="question-wrap clearfix">
                <i class="pull-left">问</i>
                <div class="question-txt pull-left">
                    多大的儿童可以乘坐邮轮？如何选择舱房？
                </div>
            </div>
            <div class="answer-wrap clearfix">
                <i class="pull-left">答</i>
                <div class="answer-txt pull-left">
                    6个月以上的儿童都可以乘坐邮轮旅行（运营极地、跨大洋等航线的邮轮可能会对年龄有更高要求）。根据邮轮公司的规定，儿童必须占一张床，如您一大一小出行，需订双人房，两大一小出行，需订三人房。一般儿童价格与大人一致，部分产品会有儿童优惠、同房第三人优惠，请留意具体产品的优惠说明。
                </div>
            </div>
        </div>
        <div class="question-group">
            <div class="question-wrap clearfix">
                <i class="pull-left">问</i>
                <div class="question-txt pull-left">
                    多大的儿童可以乘坐邮轮？如何选择舱房？
                </div>
            </div>
            <div class="answer-wrap clearfix">
                <i class="pull-left">答</i>
                <div class="answer-txt pull-left">
                    6个月以上的儿童都可以乘坐邮轮旅行（运营极地、跨大洋等航线的邮轮可能会对年龄有更高要求）。根据邮轮公司的规定，儿童必须占一张床，如您一大一小出行，需订双人房，两大一小出行，需订三人房。一般儿童价格与大人一致，部分产品会有儿童优惠、同房第三人优惠，请留意具体产品的优惠说明。
                </div>
            </div>
        </div>
        <div class="question-group">
            <div class="question-wrap clearfix">
                <i class="pull-left">问</i>
                <div class="question-txt pull-left">
                    多大的儿童可以乘坐邮轮？如何选择舱房？
                </div>
            </div>
            <div class="answer-wrap clearfix">
                <i class="pull-left">答</i>
                <div class="answer-txt pull-left">
                    6个月以上的儿童都可以乘坐邮轮旅行（运营极地、跨大洋等航线的邮轮可能会对年龄有更高要求）。根据邮轮公司的规定，儿童必须占一张床，如您一大一小出行，需订双人房，两大一小出行，需订三人房。一般儿童价格与大人一致，部分产品会有儿童优惠、同房第三人优惠，请留意具体产品的优惠说明。
                </div>
            </div>
        </div>
        <%--<a href="#" class="more">查看所有问题</a>--%>
    </div>
</div>
<!--主体部分  ending-->

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/idangerous.swiper2.7.6.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/cruise_details.js"></script>
</body></html><%@include file="../../yhypc/public/nav_footer.jsp"%>