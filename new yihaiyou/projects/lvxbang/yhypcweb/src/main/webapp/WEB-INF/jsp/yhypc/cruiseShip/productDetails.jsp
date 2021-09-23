<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>舱房详情</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/idangerous.swiper2.7.6.css">
    <link rel="stylesheet" href="/css/cruiseship/cabin_details.css">
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
            <a class="pull-left" href="#">${cruiseShipProject.cruiseShip.name}</a>
        </div>
        <div class="product-order">
            <div class="order-body clearfix">
                <div class="order-swiper pull-left">
                    <div class="views">
                        <div class="swiper-wrapper" style="width: 4480px; height: 360px;">

                            <c:forEach items="${cruiseShipProject.cruiseShipProjectImage}" var="cruiseShipProjectImageItem">

                                <div class="swiper-slide" style="width: 640px; height: 360px;">
                                    <img src="${QINIU_BUCKET_URL}${cruiseShipProjectImageItem.path}">
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                    <div class="previews-wrap">
                        <a href="javascript:void(0)" class="btn-prev"><i class="icon-prev"></i></a>
                        <div class="preview">
                            <div class="swiper-wrapper" style="width: 812px; height: 60px;">


                                <c:forEach items="${cruiseShipProject.cruiseShipProjectImage}" var="cruiseShipProjectImageItem">

                                    <div class="swiper-slide swiper-slide-visible" style="width: 116px; height: 60px;">
                                        <img src="${QINIU_BUCKET_URL}${cruiseShipProjectImageItem.path}">
                                    </div>
                                </c:forEach>

                            </div>
                        </div>
                        <a href="javascript:void(0)" class="btn-next"><i class="icon-next"></i></a>
                    </div>
                </div>
                <div class="order-content pull-right">
                    <h5>${cruiseShipProject.name}</h5>
                    <div class="txt-wrap">
                        <div class="txt-mask">
                            <div class="txt">${cruiseShipProject.introduction}</div>
                        </div>
                        <span class="txt-slide"><i></i></span>
                    </div>
                    <div class="tips-group clearfix">
                        <div class="tips pull-left">
                            楼层：${cruiseShipProject.level}
                        </div>
                        <div class="tips pull-left">
                            容纳：${cruiseShipProject.peopleNum}人
                        </div>
                    </div>
                    <div class="tips-group clearfix">
                        <div class="tips pull-left">
                            着装：${cruiseShipProject.suitType}
                        </div>
                    </div>
                    <div class="tips-group clearfix">
                        <div class="tips pull-left">
                            消费：${cruiseShipProject.costStatus}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="another">
            <div class="another-header clearfix">
                <i class="pull-left"></i>
                <span class="pull-left">其他服务</span>
                <i class="pull-right"></i>
            </div>
            <ul class="another-body clearfix">
                <c:forEach items="${projectList}" var="project" >
                    <li class="pull-left">
                        <img class="pull-left" src="${QINIU_BUCKET_URL}${project.coverImage}">
                        <div class="another-txt pull-left">
                            <a href="#" class="title">${project.name}</a>
                            <div class="txt-tips clearfix">
                                <label class="pull-left">楼层</label>
                                <span class="pull-left">${project.level}层</span>
                            </div>
                            <div class="txt-tips clearfix">
                                <label class="pull-left">容纳</label>
                                <span>${project.peopleNum}人</span>
                            </div>
                        </div>
                    </li>
                </c:forEach>

            </ul>
        </div>
    </div>
</div>
<!--主体部分  ending-->

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/idangerous.swiper2.7.6.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/cabin_details.js"></script>
</body>
</html>
<%@include file="../../yhypc/public/nav_footer.jsp"%>