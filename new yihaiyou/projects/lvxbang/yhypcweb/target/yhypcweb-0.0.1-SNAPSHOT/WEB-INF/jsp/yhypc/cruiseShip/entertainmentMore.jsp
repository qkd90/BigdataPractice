<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en"><head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>更多玩乐</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/idangerous.swiper2.7.6.css">
    <link rel="stylesheet" href="/css/cruiseship/entertainment_more.css">
</head>
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
            <label class="pull-left">玩乐:</label>
            <ul class="cabin-nav pull-left clearfix">
                <li class="active pull-left" data-food="all">全部</li>
                <c:forEach items="${listCruiseShipProject}" var="listCruiseShipProjectItem">

                    <li class="pull-left" data-food="${listCruiseShipProjectItem.name}">${listCruiseShipProjectItem.name}</li>
                </c:forEach>
                <%--<li class="pull-left" data-food="运动健身">运动健身</li>--%>
                <%--<li class="pull-left" data-food="海上休闲">海上休闲</li>--%>
                <%--<li class="pull-left" data-food="else">其他</li>--%>
            </ul>
        </div>
    </div>
</div>
<div class="server-box">
    <ul class="server-list clearfix">
        <c:forEach items="${listCruiseShipProject}" var="listCruiseShipProjectItem">
            <li class="pull-left" data-category="${listCruiseShipProjectItem.name}">
                <a href="#">
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
        <%--<li class="pull-left" data-category="运动健身">--%>
            <%--<a href="#">--%>
                <%--<img src="../images/product_details_img.png">--%>
                <%--<h5>运动健身</h5>--%>
                <%--<div class="tips clearfix">--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--楼层：5.6--%>
                    <%--</div>--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--容纳：4346人--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="tips clearfix">--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--消费：免费--%>
                    <%--</div>--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--开放：按照到港情况供应--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="server-txt">--%>
                    <%--设计大师匠心独具，融欧式奢华共天海一色。结合中外时节打造美味不绝，按照时节利用各种国内外的地方性原材料烹制西式菜肴，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，--%>
                <%--</div>--%>
            <%--</a>--%>
        <%--</li>--%>
        <%--<li class="pull-left" data-category="海上休闲">--%>
            <%--<a href="#">--%>
                <%--<img src="../images/product_details_img.png">--%>
                <%--<h5>海上休闲</h5>--%>
                <%--<div class="tips clearfix">--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--楼层：5.6--%>
                    <%--</div>--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--容纳：4346人--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="tips clearfix">--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--消费：免费--%>
                    <%--</div>--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--开放：按照到港情况供应--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="server-txt">--%>
                    <%--设计大师匠心独具，融欧式奢华共天海一色。结合中外时节打造美味不绝，按照时节利用各种国内外的地方性原材料烹制西式菜肴，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，--%>
                <%--</div>--%>
            <%--</a>--%>
        <%--</li>--%>
        <%--<li class="pull-left" data-category="else">--%>
            <%--<a href="#">--%>
                <%--<img src="../images/product_details_img.png">--%>
                <%--<h5>其他</h5>--%>
                <%--<div class="tips clearfix">--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--楼层：5.6--%>
                    <%--</div>--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--容纳：4346人--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="tips clearfix">--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--消费：免费--%>
                    <%--</div>--%>
                    <%--<div class="tips-group pull-left">--%>
                        <%--开放：按照到港情况供应--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="server-txt">--%>
                    <%--设计大师匠心独具，融欧式奢华共天海一色。结合中外时节打造美味不绝，按照时节利用各种国内外的地方性原材料烹制西式菜肴，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，丰富唇齿感官碰撞如画美景，让宾客留下美好回忆。无需预约，--%>
                <%--</div>--%>
            <%--</a>--%>
        <%--</li>--%>
    </ul>
</div>
<!--主体部分  ending-->

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/entertainment_more.js"></script>
</body></html>