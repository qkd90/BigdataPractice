<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en"><head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>更多玩乐</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/idangerous.swiper2.7.6.css">
    <link rel="stylesheet" href="/css/cruiseship/entertainment_more.css">
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
            <a class="pull-left" href="#">${listCruiseShipProjectClassify[0].listCruiseShipProject[0].cruiseShip.name}</a>
        </div>
        <div class="cabin-nav-wrap clearfix">
            <label class="pull-left">玩乐:</label>
            <ul class="cabin-nav pull-left clearfix">
                <li class="active pull-left" data-food="all">全部</li>
                <c:forEach items="${listCruiseShipProjectClassify}" var="listCruiseShipProjectClassifyItem">
                    <%--<c:if test="${listCruiseShipProjectClassify.cruiseShipProject.size>0}">--%>

                    <li class="pull-left" data-food="${listCruiseShipProjectClassifyItem.classifyName}">${listCruiseShipProjectClassifyItem.classifyName}</li>
                    <%--</c:if>--%>
                </c:forEach>


            </ul>
        </div>
    </div>
</div>
<div class="server-box">
    <ul class="server-list clearfix">
        <c:forEach items="${listCruiseShipProjectClassify}" var="listCruiseShipProjectClassifyItem">
                <c:forEach items="${listCruiseShipProjectClassifyItem.cruiseShipProject}" var="listCruiseShipProjectItem">
                    <li class="pull-left" data-category="${listCruiseShipProjectClassifyItem.classifyName}">
                    <a href="/yhypc/cruiseShip/productDetails.jhtml?projectId=${listCruiseShipProjectItem.id}">
                    <img src="${QINIU_BUCKET_URL}${listCruiseShipProjectItem.coverImage}">
                    <h5>${listCruiseShipProjectItem.name}</h5>
                    <div class="tips clearfix">
                        <div class="tips-group pull-left">
                            楼层：${listCruiseShipProjectItem.level}
                        </div>
                        <div class="tips-group pull-left">
                            容纳：${listCruiseShipProjectItem.peopleNum}人
                        </div>
                    </div>
                    <div class="tips clearfix">
                        <div class="tips-group pull-left">
                            消费：${listCruiseShipProjectItem.costStatus}
                        </div>
                        <div class="tips-group pull-left">
                            开放：${listCruiseShipProjectItem.openStatus}
                        </div>
                    </div>
                    <div class="server-txt">
                            ${listCruiseShipProjectItem.introduction}
                    </div>

                    </a>
                    </li>
                </c:forEach>
        </c:forEach>
    </ul>
</div>
<!--主体部分  ending-->

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/entertainment_more.js"></script>
</body></html>
<%@include file="../../yhypc/public/nav_footer.jsp"%>