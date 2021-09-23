<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>私人定制_定制旅游_自由行-旅行帮 </title>
    <meta name="keywords" content="私人定制，自助游，自由行，旅游线路，定制旅游，行程安排"/>
    <meta name="description"
          content="旅行帮是中国领先的C2B旅游电商平台。提供全网最智能、最方便的定制旅游工具，一键定制自由行行程方案。交通，住宿，景点，美食，游记，旅游地图应有尽有，无需查攻略、跨平台比价购买，旅行帮一键帮您搞定。"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/navigation.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="home">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>

<div class="navLogo" id="touchMain"
     style="background-image: url(/images/background.jpg);-moz-background-size:100% 100%;background-size:100% 100%;  ">
    <%--<div class="navLogo" id="touchMain" style="background-image: url(/static${ads.imgPath});">--%>
    <a class="prev" href="javascript:;" stat="prev1001"></a>
    <div class="slide-box textC" id="slideContent">
        <div class="slide">
            <img src="/images/navigation.png" style="width:460px;height:105px;margin: 170px" alt="${ads.adTitle}"/>
        </div>
    </div>
</div>
${NAVIGATION_CONTENT}
<%--<div class="navBanner" xmlns="http://www.w3.org/1999/html">--%>
<%--<div class="nav_bg">--%>
<%--<div class="mailTablePlan">--%>
<%--<div style="margin-top: 0%;">--%>
<%--<div class="searchBox cl nav1">--%>
<%--<div class="select w1000 ">--%>
<%--<div class="select_div">--%>
<%--<label>去旅行</label>--%>
<%--<ul class="fore sel">--%>
<%--<li class="navContent"><a href="${DESTINATION_PATH}">旅游目的地</a></li>--%>
<%--<li class="navContent"><a href="${PLAN_PATH}">私人定制</a></li>--%>
<%--<li class="navContent"><a href="${HANDDRAW_PATH}/map_350200.html">旅游地图</a></li>--%>
<%--<li class="navContent"><a href="${RECOMMENDPLAN_PATH}">游记公略</a></li>--%>
<%--<li class="navContent"><a href="#">写游记</a></li>--%>
<%--<li class="navContent"><a href="${SCENIC_PATH}">门票购买</a></li>--%>
<%--<li class="navContent"><a href="${TRAFFIC_PATH}">机票预订</a></li>--%>
<%--<li class="navContent"><a href="${TRAFFIC_PATH}">火车票预订</a></li>--%>
<%--<li class="navContent"><a href="${HOTEL_PATH}">酒店预定</a></li>--%>
<%--<li class="navContent"><a href="${DELICACY_PATH}">特色美食</a></li>--%>
<%--</ul>--%>
<%--<ul class="fore sel">--%>
<%--<label>查服务</label>--%>
<%--<li class="navContent">--%>
<%--<a href="${INDEX_PATH}/lvxbang/help/index.jhtml?page=10">关于旅行帮</a>--%>
<%--</li>--%>
<%--<li class="navContent">--%>
<%--<a href="${INDEX_PATH}/lvxbang/help/index.jhtml?page=11">联系旅行帮</a>--%>
<%--</li>--%>
<%--<li class="navContent">--%>
<%--<a href="${INDEX_PATH}/lvxbang/help/index.jhtml?page=12">诚聘英才</a>--%>
<%--</li>--%>
<%--<li class="navContent">--%>
<%--<a href="${INDEX_PATH}/lvxbang/help/index.jhtml?page=13">友情链接</a>--%>
<%--</li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="searchBox cl nav2">--%>
<%--<div class="select w1000">--%>
<%--<div class="select_div">--%>
<%--<ul class="fore sel">--%>
<%--<label>热门目的地</label>--%>
<%--<li class="navContent"><a href="#">三亚旅游</a></li>--%>
<%--<li class="navContent"><a href="#">云南旅游</a></li>--%>
<%--<li class="navContent"><a href="#">广西旅游</a></li>--%>
<%--</ul>--%>
<%--<ul class="fore sel">--%>
<%--<label>旅游地图</label>--%>
<%--<li class="navContent"><a href="#">三亚旅游</a></li>--%>
<%--<li class="navContent"><a href="#">云南旅游</a></li>--%>
<%--<li class="navContent"><a href="#">广西旅游</a></li>--%>
<%--</ul>--%>
<%--<ul class="fore sel">--%>
<%--<label>游记攻略</label>--%>
<%--<li class="navContent"><a href="#">三亚旅游</a></li>--%>
<%--<li class="navContent"><a href="#">云南旅游</a></li>--%>
<%--<li class="navContent"><a href="#">广西旅游</a></li>--%>
<%--</ul>--%>
<%--<ul class="fore sel">--%>
<%--<label>热门景点</label>--%>
<%--<li class="navContent"><a href="#">三亚旅游</a></li>--%>
<%--<li class="navContent"><a href="#">云南旅游</a></li>--%>
<%--<li class="navContent"><a href="#">广西旅游</a></li>--%>
<%--</ul>--%>
<%--<ul class="fore sel">--%>
<%--<label>酒店预订</label>--%>
<%--<li class="navContent"><a href="#">三亚旅游</a></li>--%>
<%--<li class="navContent"><a href="#">云南旅游</a></li>--%>
<%--<li class="navContent"><a href="#">广西旅游</a></li>--%>
<%--</ul>--%>
<%--<ul class="fore sel">--%>
<%--<label>特色美食</label>--%>
<%--<li class="navContent"><a href="#">三亚旅游</a></li>--%>
<%--<li class="navContent"><a href="#">云南旅游</a></li>--%>
<%--<li class="navContent"><a href="#">广西旅游</a></li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>

</body>
</html>
