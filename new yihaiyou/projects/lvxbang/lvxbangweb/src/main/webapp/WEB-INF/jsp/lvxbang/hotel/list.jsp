<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>酒店列表</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/list.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
</head>
<body myname="mall" class="hotels_List">
<!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<form action="${PLAN_PATH}/lvxbang/plan/booking.jhtml" method="post" id="change-hotel-form" class="hide">
    <input type="hidden" name="json" value='${json}' id="pre-json"/>
    <input type="hidden" class="city" name="city" value="${cityId}" id="city"/>
    <input type="hidden" class="type" name="type" value="HOTEL"/>
    <input type="hidden" class="code" name="code" value=""/>
    <input type="hidden" id="firstLeave" name="nowDate" value="${firstLeaveDate}">
</form>
<div class="main cl">
    <c:if test="${json==null}">
        <p class="w1000 n_title h40 lh40">
            您在这里：&nbsp;
            <a href="${HOTEL_PATH}">酒店</a>
            <c:if test="${searchRequest.cities[0] != null && searchRequest.cities[0] != ''}">
                <%--&nbsp;&gt;&nbsp;<a href="${DESTINATION_PATH}/city_${cityId}.html" id="destination_name1">${searchRequest.cities[0]}</a>--%>
                &nbsp;&gt;&nbsp;
                <span id="destination_name2">${searchRequest.cities[0]}</span>酒店预订
            </c:if>
            <c:if test="${searchRequest.cities[0] == null || searchRequest.cities[0] == ''}">
                &nbsp;&gt;&nbsp;酒店预订
            </c:if>
        </p>
    </c:if>
    <input type="hidden" value="${DESTINATION_PATH}" id="destination_path"/>
    <input type="hidden" id="planId" value="${planId}"/>
    <!--搜索-->
    <div class="h60" id="nav">
        <div class="nav n_select">
            <div class="w1000 posiR">
                <dl class="n_select_d fl">
                    <dd class="hotel_city_panel">
                        <i class="ico"></i>
                        <input id="destination" type="text" placeholder="目的地" value="${searchRequest.cities[0]}"
                               data-url="http://pj.destination.test.lvxbang.com/lvxbang/destination/getSeachAreaList.jhtml"
                               class="togetherCityName input clickinput add_more_city_button"
                               <c:if test="${json!=null && json!=''}">disabled="disabled"</c:if>/>

                        <!--关键字提示 clickinput input input01-->
                        <div class="posiA categories_div  KeywordTips">
                            <ul>

                            </ul>
                        </div>
                        <!--错误-->
                        <div class="posiA categories_div cuowu textL">
                            <p class="cl">抱歉未找到相关的结果！</p>
                        </div>
                        <jsp:include page="/WEB-INF/jsp/lvxbang/common/citySelector.jsp"></jsp:include>

                    </dd>
                    <c:choose>
                        <c:when test="${json!=null}">
                            <dd><i class="ico ico2"></i><input id="enterDate" type="text" placeholder="入住时间"
                                                               value="${startDate1}"
                                                               onFocus="WdatePicker({minDate:'${minDate}', maxDate:'#F{$dp.$D(\'leaveDate\',{d:-1})}'})"
                                                               class="input"/></dd>
                            <dd><i class="ico ico2"></i><input id="leaveDate" type="text" placeholder="退房时间"
                                                               value="${endDate1}"
                                                               onFocus="WdatePicker({minDate:'#F{$dp.$D(\'enterDate\',{d:1})}', maxDate:'${maxDate}'})"
                                                               class="input"/></dd>
                        </c:when>
                        <c:otherwise>
                            <dd><i class="ico ico2"></i><input id="enterDate" type="text" placeholder="入住时间"
                                                               value="${startDate1}" readOnly="true"
                                                               onFocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change()}});"
                                                               class="input enterDate"/></dd>
                            <dd><i class="ico ico2"></i><input id="leaveDate" type="text" placeholder="退房时间"
                                                               value="${endDate1}"
                                                               readOnly="true"
                                                               onFocus="WdatePicker({minDate:'#F{$dp.$D(\'enterDate\',{d:1})}'})"
                                                               class="input"/></dd>
                        </c:otherwise>
                    </c:choose>
                    <dd class="n_categories" style="background-color: #fff;"><input type="text" placeholder="关键字" value="${searchRequest.name}"
                                                    data-url="/lvxbang/hotel/suggest.jhtml"
                                                    class="input clickinput" id="keyword" style="background-color: #fff;"/>
                        <!--关键字提示 clickinput input input01-->
                        <div class="posiA categories_div  KeywordTips" style="width: 280px">
                            <ul>

                            </ul>
                        </div>
                        <!--错误-->
                        <div class="posiA categories_div cuowu textL" style="width: 239px">
                            <p class="cl">抱歉未找到相关的结果！</p>
                        </div>
                    </dd>

                </dl>
                <a href="javaScript:;" class="but fr" id="search-button">搜索</a>
                <c:if test="${planId!=null}">
                    <a href="javaScript:;" class="but2 oval4"><i></i>返回自由行套餐</a>
                </c:if>
            </div>
        </div>
    </div>

    <div class="w1000">
        <!--筛选-->
        <div class="mod mb10">
            <div class="searchBox cl posiR">
                <div class="select">
                    <div class="select_div posiR" id="position" style="display: none">
                        <label>位&nbsp;&nbsp;&nbsp;&nbsp;置</label>
                        <span class="fr reg_more" style="margin-right: 20px;text-decoration: none;padding: 3px 4px;cursor: pointer;
                        color: #34bf82;cursor: pointer;font-size: 13px;font-weight: bold;">更多</span>
                        <ul class="fore sel" id="regions_ul" style="width:790px;">
                            <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>
                        </ul>

                    </div>
                    <div class="select_div brand" id="brand" style="display: none">
                        <label>品&nbsp;&nbsp;&nbsp;&nbsp;牌</label>
                        <span class="fr bra_more" style="margin-right: 20px;text-decoration: none;padding: 3px 4px;cursor: pointer;
                        color: #34bf82;cursor: pointer;font-size: 13px;font-weight: bold;">更多</span>
                        <ul class="fore sel" id="brand_ul" style="width:790px;">
                            <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>
                        </ul>
                    </div>
                    <div class="select_div brand" id="service" style="display: none">
                        <label>服&nbsp;&nbsp;&nbsp;&nbsp;务</label>
                        <span class="fr ser_more" style="margin-right: 20px;text-decoration: none;padding: 3px 4px;cursor: pointer;
                        color: #34bf82;cursor: pointer;font-size: 13px;font-weight: bold;">更多</span>
                        <ul class="fore sel" id="service_ul" style="width:790px;">
                            <li class="whole"><a title="不限" href="javaScript:;" class="checked">不限</a></li>
                        </ul>
                    </div>
                    <div class="select_div price">
                        <label>价&nbsp;&nbsp;&nbsp;&nbsp;格</label>
                        <ul class="fore sel">
                            <li class="whole"><a title="不限" href="javaScript:;" class="checked" data-price="1">不限</a>
                            </li>
                            <li><a title="¥150以下" href="javaScript:;" data-price="1,150">¥150以下</a></li>
                            <li><a title="¥150-300" href="javaScript:;" data-price="150,300">¥150-300</a></li>
                            <li><a href="javaScript:;" data-price="301,450">¥301-450</a></li>
                            <li><a href="javaScript:;" data-price="451,600">¥451-600</a></li>
                            <li><a title="¥600以上" href="javaScript:;" data-price="600">¥600以上</a></li>
                            <li class="custom">
                                <span>自定义</span>
                                <input type="text" placeholder="¥" value="" class="input" id="price-min"/>
                                <span>-</span>
                                <input type="text" placeholder="¥" value="" class="input" id="price-max"/>
                                <input type="submit" value="确定" class="fix disn search-button"/>
                            </li>
                        </ul>
                    </div>
                    <div class="select_div star">
                        <label>星&nbsp;&nbsp;&nbsp;&nbsp;级</label>
                        <input type="hidden" id="star" value="${searchRequest.star}">
                        <ul class="fore sel">
                            <li class="whole"><a title="不限" href="javaScript:;" class="checked" data-value="0">不限</a>
                            </li>
                            <li><a title="经济型" href="javaScript:;" data-value="1">经济型</a></li>
                            <li><a title="二星级/其他" href="javaScript:;" data-value="2">二星级/其他</a></li>
                            <li><a title="三星级/舒适" href="javaScript:;" data-value="3">三星级/舒适</a></li>
                            <li><a title="四星级/高档" href="javaScript:;" data-value="4">四星级/高档</a></li>
                            <li><a title="五星级/奢华" href="javaScript:;" data-value="5">五星级/奢华</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!--搜索-->
        <div class="select_list">
            <ul class="fl" id="order">
                <li class="desc checked" orderColumn="productScore" orderType="desc">评分<i></i></li>
                <li class="desc " orderColumn="star" orderType="desc">星级<i></i></li>
                <li class="asc" orderColumn="price" orderType="asc">价格<i></i></li>
                <li id="distance" style="display: none;">距离从近到远</li>
            </ul>
        </div>

        <div class="cl disB textC" style="margin-top:70px;color:#666;width: 700px;position: absolute;" id="loading">
            <img src="/images/loadingx.gif"/>

            <p class="mt20">
                正在为您加载酒店信息...
            </p>
        </div>
        <div class="hotels_fl fl">
            <div class="mailTablePlan">
                <ul class="hotels_list ft_list" id="list">
                </ul>
            </div>
        </div>

        <div class="hotels_fr fr mt20" id="di_tu">
            <div class="posiR"><a href="javaScript:toLargeMap();" class="view_map oval4 disB">查看大地图</a></div>
            <div style="width:278px;height:278px;border:#ccc solid 1px;" id="dituContent"></div>

            <div class="recommend mt10">
                <div class="cl title">
                    <label class="fl fs14">推荐酒店</label>

                    <p class="fr textR">
                        <a href="javaScript:;" class="prev2">&lt;</a>
                        <a href="javaScript:;" class="next2">&gt;</a>
                    </p>

                    <p class="fr fs14 en mr10" style="color:#999;"><span style="color:#666" id="count">1</span>/3 </p>
                </div>

                <div class="recommend_div">
                    <div id="bacSlideBox2" style="width: 10000000px;">

                        <div class="bacSlideBox_div2 fl posiR">
                            <ul class="hotels_fr_ul recommendHotel1">
                                <c:forEach items="${recommendHotelList}" var="hotel" varStatus="status">
                                    <c:if test="${status.index<3}">
                                        <li>
                                            <p class="img fl">
                                                <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">

                                                    <c:choose>
                                                        <c:when test="${not empty hotel.cover}">
                                                            <img data-original="${hotel.cover}" alt="${hotel.name}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/60/h/60/q/75"
                                                                 alt="${hotel.name}"/>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </a>
                                            </p>

                                            <div class="nr posiR fr">
                                                <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">
                                                    <div class="fl w100">
                                                        <p class="name b">${hotel.name}</p>

                                                        <p class="fraction">${searchRequest.cities[0]}商圈</p>
                                                    </div>
                                                </a>

                                                <p class="price ff_yh fr mt15"><span>¥<fmt:formatNumber type="number"
                                                                                                        value="${hotel.price}"
                                                                                                        maxFractionDigits="0"/></span>起
                                                </p>
                                            </div>
                                            <p class="cl"></p>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>


                        <div class="bacSlideBox_div2 fl posiR">
                            <ul class="hotels_fr_ul recommendHotel2">
                                <c:forEach items="${recommendHotelList}" var="hotel" varStatus="status">
                                    <c:if test="${status.index>=3 && status.index<6}">
                                        <li>
                                            <p class="img fl">
                                                <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">

                                                    <c:choose>
                                                        <c:when test="${not empty hotel.cover}">
                                                            <img data-original="${hotel.cover}" alt="${hotel.name}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/60/h/60/q/75"
                                                                 alt="${hotel.name}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>
                                            </p>

                                            <div class="nr posiR fr">
                                                <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">
                                                    <div class="fl w100">
                                                        <p class="name b">${hotel.name}</p>

                                                        <p class="fraction">${searchRequest.cities[0]}商圈</p>
                                                    </div>
                                                </a>

                                                <p class="price ff_yh fr mt15"><span>¥<fmt:formatNumber type="number"
                                                                                                        value="${hotel.price}"
                                                                                                        maxFractionDigits="0"/></span>起
                                                </p>
                                            </div>
                                            <p class="cl"></p>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>


                        <div class="bacSlideBox_div2 fl posiR">
                            <ul class="hotels_fr_ul recommendHotel3">
                                <c:forEach items="${recommendHotelList}" var="hotel" varStatus="status">
                                    <c:if test="${status.index>=6 &&status.index<9}">
                                        <li>
                                            <p class="img fl"><a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html"
                                                                 target="_blank"><img
                                                    data-original="${hotel.cover}" alt="${hotel.name}"/></a></p>

                                            <div class="nr posiR fr">
                                                <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">
                                                    <div class="fl w100">
                                                        <p class="name b">${hotel.name}</p>

                                                        <p class="fraction">${searchRequest.cities[0]}商圈</p>
                                                    </div>
                                                </a>

                                                <p class="price ff_yh fr mt15"><span>¥<fmt:formatNumber type="number"
                                                                                                        value="${hotel.price}"
                                                                                                        maxFractionDigits="0"/></span>起
                                                </p>
                                            </div>
                                            <p class="cl"></p>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>


                    </div>
                    <p class="cl"></p>

                </div>

            </div>
        </div>


        <p class="cl h30"></p>

        <div class="m-pager st cl">
        </div>
    </div>
</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/popup/map_popup.jsp"></jsp:include>
<script type="text/html" id="tpl-recommend-hotel-item">
    <li>
        <p class="img fl"><a href="${HOTEL_PATH}/hotel_detail_{{id}}.html"><img
                src="{{cover}}" alt="{{name}}"/></a></p>

        <div class="nr posiR fr">
            <a href="${HOTEL_PATH}/hotel_detail_{{id}}.html">
                <div class="fl w100">
                    <p class="name b">{{name}}</p>

                    <p class="fraction">{{city}}商圈</p>
                </div>
            </a>

            <p class="price ff_yh fr mt15"><span>¥{{price}}</span>起</p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-hotel-list-item">
    <li>
        <p class="img fl">
            <a href="${HOTEL_PATH}/hotel_detail_{{id}}.html" target="_blank">
                {{if cover != null && cover != ""}}
                <img data-original="{{cover}}" alt="{{name}}"/>
                {{else}}
                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/240/h/175/q/75"
                     alt="{{name}}"/>
                {{/if}}
            </a>
        </p>

        <div class="nr posiR fr lh30">
            {{if change}}
            <a href="javascript:;" class="posiA price round change-hotel" data-id="{{id}}"
               style="text-decoration: none;">
                <span class="fs20 b">¥{{price}}</span>起
                <p class="fs16 b">更换</p>
            </a>
            {{else}}
            <a href="${HOTEL_PATH}/hotel_detail_{{id}}.html" class="posiA price round" target="_blank"
               style="text-decoration: none;">
                <span class="fs20 b">¥{{price}}</span>起
                <p class="fs16 b">预订</p>
            </a>
            {{/if}}
            <a href="${HOTEL_PATH}/hotel_detail_{{id}}.html" target="_blank">
                <p class="name b fl">{{name}}</p><span class="hstar fl"><i
                    style="width: {{10*star}}px"></i></span><br/>
            </a>

            <p class="add fl mr10{{if address.length > 16}} simple is_hover{{/if}}">{{address}}</p>
            <a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="{{lng}}"
               data-ditu-baiduLat="{{lat}}" data-ditu-name="{{name}}">查看地图</a><br/>

            <p class="fraction"><b class="fs16">{{score/20}}分</b>/5分
                <%--(来自{{commentCount}}人点评)--%>
            </p>

            {{if comment != null && comment.length > 0}}
            <p class="synopsis posiR"><i></i><span class="fl long{{if comment.length > 40}} is_hover{{/if}}">{{formatComment comment 40}}</span><i
                    class="fl"></i></p>
            {{/if}}

            <br/>
            {{if recommendPlanName.length > 0}}
            <p class="synopsis2">相关游记：
                <a href="${RECOMMENDPLAN_PATH}/guide_detail_{{recommendPlanId}}.html" target="_blank"><span>{{recommendPlanName}}</span></a>
            </p>
            {{/if}}
        </div>
        <p class="cl"></p>
    </li>
</script>
<form action="${HOTEL_PATH}/lvxbang/hotel/hotelLargeMap.jhtml" method="post" id="toLargeMap">
    <input type="hidden" name="areaName" id="areaName"/>
    <input type="hidden" name="cityId" id="cityId"/>
    <input type="hidden" name="startDate" id="startDate"/>
    <input type="hidden" name="endDate" id="endDate"/>
    <input type="hidden" name="minDate" value="${minDate}"/>
    <input type="hidden" name="maxDate" value="${maxDate}"/>
    <input type="hidden" name="name" id="name"/>
    <input type="hidden" name="star" id="hotelStar"/>
    <input type="hidden" name="priceIndex" id="hotelPrice" value="${priceIndex}"/>
    <input type="hidden" name="json" value='${json}'/>
    <input type="hidden" name="coreScenic" id="coreScenic" value="${coreScenic}"/>
    <input type="hidden" name="firstLeaveDate" value="${firstLeaveDate}">
</form>
</body>
</html>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
<script src="/js/lvxbang/hotel/list.js" type="text/javascript"></script>
<script src="/js/lvxbang/sort.js" type="text/javascript"></script>
