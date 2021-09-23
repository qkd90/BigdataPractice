<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>酒店地图</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/HandDrawing.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js"></script>
</head>
<body class="LargeMap" style="padding-right: -60px">
<!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
<div class="head">
    <!--nav_logo-->
    <a href="${INDEX_PATH}" target="_blank" class="logo"></a>
    <div class="n_select fl posiR">
        <dl class="n_select_d fl">
            <dd class="categories">
                <i class="ico mudidi"></i>
                <input id="destination" type="text" placeholder="目的地" value="${areaName}" data-url="http://pj.destination.test.lvxbang.com/lvxbang/destination/getSeachAreaList.jhtml"
                       class="input add_more_city_button destination input01 Departure clickinput"
                       data-areaid="${cityId}" <c:if test="${json!=null && json!=''}">disabled="disabled"</c:if>/>

                <div class="posiA Addmore categories_Addmore2">
                    <i class="close"></i>
                    <div class="Addmore_d" id="searchHistoryTxt" style="display: none">
                        搜索历史：
                    </div>
                    <jx:include fileAttr="/static/master/lvxbang/traffic/traffic_city.htm"
                                targetObject="lvXBangBuildService" targetMethod="buildTrafficIndex"
                                validDay="7"></jx:include>
                    <p class="cl"></p>
                </div>

                <!--关键字提示 clickinput input input01-->
                <div class="posiA categories_div  KeywordTips">
                    <ul>

                    </ul>
                </div>
                <!--错误-->
                <div class="posiA categories_div cuowu textL">
                    <p class="cl">抱歉未找到相关的结果！</p>
                </div>
            </dd>
            <dd><i class="ico ico2 rili"></i><input type="text" placeholder="入住时间" id="startDate" value="${startDate}"
                                               onfocus="WdatePicker({<c:if test="${minDate != ''}">minDate:'${minDate}',</c:if>
                                               <c:if test="${minDate == '' }">minDate:'%y-%M-{%d+1}',</c:if>
                                               <c:if test="${maxDate != ''}">maxDate:'${maxDate}',</c:if>
                                                       onpicked:function(){$(this).change()}})"
                                               class="input"/></dd>
            <dd><i class="ico ico2 rili"></i><input type="text" placeholder="退房时间" id="endDate" value="${endDate}"
                                               onfocus="WdatePicker({
                                               <c:if test="${maxDate != ''}">maxDate:'${maxDate}',</c:if>
                                               <c:if test="${maxDate == ''}">maxDate:'#F{$dp.$D(\'startDate\',{d:30})}',</c:if>
                                               minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})"
                                               class="input"/></dd>
            <dd class="n_categories"><input type="text" placeholder="关键字" value="${name}" class="input clickinput" data-url="/lvxbang/hotel/suggest.jhtml"
                                            id="keyword"/>
                <!--关键字提示 clickinput input input01-->
                <div class="posiA categories_div  KeywordTips">
                    <ul>

                    </ul>
                </div>
                <!--错误-->
                <div class="posiA categories_div cuowu textL">
                    <p class="cl">抱歉未找到相关的结果！</p>
                </div>
            </dd>
        </dl>
        <a href="javascript:;" class="but fl" id="search-button">搜索</a>
        <c:if test="${planId!=null}">
            <a href="javascript:;" class="but2"><i></i>返回自由行套餐</a>
        </c:if>
    </div>
    <form action="/hotel_list.html" method="post" id="goToListForm">
        <input id="f_cityId" type="hidden" name="cityId" value=""/>
        <input id="f_cities" type="hidden" name="cities" value=""/>
        <input id="f_startDate" type="hidden" name="startDate1" value=""/>
        <input id="f_star" type="hidden" name="star" value="${searchRequest.star}"/>
        <input id="f_endDate" type="hidden" name="endDate1" value=""/>
        <input id="f_name" type="hidden" name="name" value=""/>
        <input type="hidden" name="json" value='${json}'/>
        <input type="hidden" name="minDate" value="${minDate}"/>
        <input type="hidden" name="maxDate" value="${maxDate}"/>
        <input id="hotelPrice" type="hidden" name="priceIndex" value="${priceIndex}"/>
        <input id="firstLeave" type="hidden" name="firstLeaveDate" value="${firstLeaveDate}"/>
        <input type="hidden" name="coreScenic" value="${coreScenic}" id="coreScenic"/>
        <a href="javascript:;" id="goToList" class="fs14 jd fr">去列表页找酒店》</a>
    </form>
    <!--nav_logo end-->
    <p class="cl"></p>
</div>
<!--head  end-->
<div class="main cl select_div">
    <ul class="HandDrawing_ul">
        <%--<li>位置<i></i>--%>
        <%--<dl class="posiA categories_dl">--%>
        <%--<dd>鼓浪屿</dd>--%>
        <%--<dd>鼓浪屿</dd>--%>
        <%--<dd>鼓浪屿</dd>--%>
        <%--<dd>鼓浪屿</dd>--%>
        <%--<dd>鼓浪屿</dd>--%>
        <%--</dl>--%>
        <%--</li>--%>
        <li><span class="leixing">价格</span><i class="select-i"></i>
            <dl class="posiA categories_dl price" style="left:18px;top:0px;">
                <dd style="margin-top: 8px;"><span class="leixing">价格</span><i class="select-i" style="margin-left: 18px;"></i></dd>
                <dd><a title="不限" href="javascript:;" class="checked" data-price="0">不限</a></dd>
                <dd><a title="¥150以下" href="javascript:;" data-price="0,150">150元以下</a></dd>
                <dd><a title="¥150-300" href="javascript:;" data-price="150,300">150-300元</a></dd>
                <dd><a href="javascript:;" data-price="301,450">301-450元</a></dd>
                <dd><a href="javascript:;" data-price="451,600">451-600元</a></dd>
                <dd><a title="¥600以上" href="javascript:;" data-price="600">600元以上</a></dd>
                <%--<dd class="custom">¥--%>
                <%--<span>自定义</span>--%>
                <%--<input type="text" placeholder="￥" value="" class="input" id="price-min"/>--%>
                <%--<span>-</span>--%>
                <%--<input type="text" placeholder="￥" value="" class="input" id="price-max"/>--%>
                <%--<input type="submit" value="确定" class="fix disn"/>--%>
                <%--</dd>--%>
            </dl>
        </li>
        <li><span class="leixing">星级</span><i class="select-i"></i>
            <dl class="posiA categories_dl star" style="left:18px;top:0px;">
                <dd style="margin-top: 8px;"><span class="leixing">星级</span><i class="select-i" style="margin-left: 31px;"></i></dd>
                <dd><a title="不限" href="javascript:;" class="checked" data-value="0">不限</a></dd>
                <dd><a title="经济型" href="javascript:;" data-value="1">经济型</a></dd>
                <dd><a title="二星级/其他" href="javascript:;" data-value="2">二星级/其他</a></dd>
                <dd><a title="三星级/舒适" href="javascript:;" data-value="3">三星级/舒适</a></dd>
                <dd><a title="四星级/高档" href="javascript:;" data-value="4">四星级/高档</a></dd>
                <dd><a title="五星级/奢华" href="javascript:;" data-value="5">五星级/奢华</a></dd>
            </dl>
        </li>
    </ul>

    <div class="hd_add posiR">


        <%--<img data-original="images/H_drawing_dt2.jpg" width="100%" height="100%"/>--%>
        <div id="map_canvas" style="width: 80%; height: 100%;"></div>

        <div id="hotelDetail" class="Pop_ups posiA" style="box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);top:10%;left:30%;display: none">
        </div>

        <div class="LargeMapList">
            <ul class="mailTab fs13" id="order" style="width: 350px;">
                <li class="checked" orderColumn="productScore" orderType="desc">评分<i></i></li>
                <li class="OrderAsc" orderColumn="price" orderType="asc">价格<i></i></li>
                <li id="distance" style="display: none;">距离从近到远</li>
            </ul>
            <div class="mailTablePlan cl posiR">
                <ul class="LargeMapUl hotels_list" id="hotelsByScores">
                </ul>
                <div class="pager">
                </div>
            </div>
            <%--<div class="mailTablePlan cl posiR disn">--%>
            <%--<ul class="LargeMapUl" id="hotelsByPrices">--%>
            <%--</ul>--%>
            <%--<div class="pager">--%>
            <%--</div>--%>
            <%--</div>--%>
            <p class="cl"></p>
        </div>
    </div>
    <p class="cl"></p>

    <script src="/js/lib/json2.js" type="text/javascript"></script>
    <script src="/js/lib/jquery.min.js" type="text/javascript"></script>
    <script src="/js/lib/template.js" type="text/javascript"></script>
    <script src="/js/lib/template.helper.js" type="text/javascript"></script>
    <script src="/js/lib/jquery.enplaceholder.js" type="text/javascript"></script>
    <script src="/js/lib/jquery.lazyload.js" type="text/javascript"></script>
    <script src="/js/lib/common_util.js" type="text/javascript"></script>
    <%--<script src="/js/lib/banner.js" type="text/javascript"></script>--%>
    <script src="/js/lib/announcement.js" type="text/javascript"></script>
    <%--<script src="/js/lvxbang/message/no_read_message_count.js" type="text/javascript"></script>--%>


    <script type="text/html" id="tpl-hotel-list-item">
        <li id="{{id}}" lng="{{lng}}" lat="{{lat}}" class="largeMapLi">
            <div class="LargeMapUlDiv">
                <p class="fl num{{num}}">{{sum}}</p>
                <div class="nr posiR fl">
                    <p class="name">{{name}}</p>
                    <p class="hstar"><i class="star5" style="width: {{10*star}}px"></i></p>
                    <p class="fraction">{{score0}}分
                        <%--(来自{{commentCount}}人点评)--%>
                    </p>
                </div>
                <p class="fr pript">
                    <em class="ff_yh">￥</em><span class="ff_yh">{{price}}</span>起
                </p>
                <p class="cl"></p>
            </div>
        </li>
    </script>

</body>
</html>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
<script src="/js/lvxbang/hotel/largeMap.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>