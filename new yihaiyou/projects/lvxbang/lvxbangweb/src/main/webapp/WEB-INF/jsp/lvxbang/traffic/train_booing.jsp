<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${leaveCityName}到${arriveCityName}火车时刻表_${leaveCityName}到${arriveCityName}高铁时刻表查询-旅行帮</title>
    <meta name="keywords" content="${leaveCityName}到${arriveCityName}火车时刻表，${leaveCityName}到${arriveCityName}火车票查询，
     ${leaveCityName}到${arriveCityName}火车票预订，${leaveCityName}到${arriveCityName}高铁票查询，${leaveCityName}到${arriveCityName}高铁时刻表查询"/>
    <meta name="description" content="旅行帮提供全国火车时刻表查询、火车票查询、火车票预订、高铁票查询、高铁时刻表查询及高铁票预订服务。旅行帮交通频道是您网上订票的首选。"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order.css" rel="stylesheet" type="text/css">
</head>
<body myname="mall" class="Order_hc_dc">
<!--head  end--><!-- #EndLibraryItem --><!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="main cl">
    <!--头部-->
    <div class="Order_hc_top" style="display: none;">
        <div class="w1225">
            <p class="n_title"><a href="${TRAFFIC_PATH}">交通</a>&nbsp;&gt;&nbsp;火车票&nbsp;&gt;&nbsp;${leaveCityName}到${arriveCityName}</p>
            <div style="display: none;" class="o_select cl">
                <form id="searchForm-1" action="toTrain.jhtml" method="post" class="trainForm">
                    <div class="o_select_fl fl">
                        <div class="categories o_select_input order_cat posiR fl w1">
                            <i class="ico3"></i>
                            <input type="text" placeholder="单程" value="" class="input" readonly="readonly">
                            <div class="posiA categories_div">
                                <ul>
                                    <li><a href="javaScript:;" onclick="changeModule(1)">单程</a></li>
                                    <li><a href="javaScript:;" onclick="changeModule(2)">往返</a></li>
                                    <li><a href="javaScript:;" onclick="changeModule(3)">联程</a></li>
                                </ul>
                            </div>
                        </div>

                        <%--<div class="departure o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="leaveCityName" id="leaveCityName" placeholder="出发城市"--%>
                        <%--value="${leaveCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="leaveCity" name="leaveCity"--%>
                        <%--value="${leaveCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="leaveCity-1" name="leaveCity"
                                   value="${leaveCity}" class="hideValue">
                            <input type="text" name="leaveCityName" placeholder="出发城市" value="${leaveCityName}"
                                   class="input clickinput" data-areaId="${leaveCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="leaveCityName-1">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                        <i class="huan fl"></i>

                        <%--<div class="arrivals o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="arriveCityName" id="arriveCityName" placeholder="到达城市"--%>
                        <%--value="${arriveCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="arriveCity" name="arriveCity"--%>
                        <%--value="${arriveCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="arriveCity-1" name="arriveCity"
                                   value="${arriveCity}" class="hideValue">
                            <input type="text" name="arriveCityName" placeholder="到达城市" value="${arriveCityName}"
                                   class="input clickinput" data-areaId="${arriveCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="arriveCityName-1">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                        <div class="time o_select_input fl"><i class="time_ico in_time"></i>
                            <input type="text" placeholder="出发时间" name="leaveDate" id="leaveDate-1" value="${leaveDate}"
                                   readOnly="true" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"
                                   class="input">
                        </div>
                        <div class="time o_select_input fl fan fan-background-color"><i class="time_ico in_time"></i>
                            <input type="text" placeholder="返回时间" name="returnDate" value=""
                                   class="input fan-background-color" readonly="true">
                        </div>
                    </div>
                    <input type="button" class="submitSearch but fr" value="重新搜索" onclick="submitTrafficForm(1)"/>
                </form>
                <form id="searchForm-2" action="toTrain.jhtml" method="post" class="trainForm display-none">
                    <div class="o_select_fl fl">
                        <div class="categories o_select_input order_cat posiR fl w1">
                            <i class="ico3"></i>
                            <input type="text" placeholder="往返" value="" class="input" readonly="readonly">
                            <div class="posiA categories_div">
                                <ul>
                                    <li><a href="javaScript:;" onclick="changeModule(1)">单程</a></li>
                                    <li><a href="javaScript:;" onclick="changeModule(2)">往返</a></li>
                                    <li><a href="javaScript:;" onclick="changeModule(3)">联程</a></li>
                                </ul>
                            </div>
                        </div>

                        <%--<div class="departure o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="leaveCityName" id="leaveCityName" placeholder="出发城市"--%>
                        <%--value="${leaveCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="leaveCity" name="leaveCity"--%>
                        <%--value="${leaveCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="leaveCity-2" name="leaveCity"
                                   value="${leaveCity}" class="hideValue">
                            <input type="text" name="leaveCityName" placeholder="出发城市" value="${leaveCityName}"
                                   class="input clickinput" data-areaId="${leaveCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="leaveCityName-2">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                        <i class="huan fl"></i>

                        <%--<div class="arrivals o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="arriveCityName" id="arriveCityName" placeholder="到达城市"--%>
                        <%--value="${arriveCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="arriveCity" name="arriveCity"--%>
                        <%--value="${arriveCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="arriveCity-2" name="arriveCity"
                                   value="${arriveCity}" class="hideValue">
                            <input type="text" name="arriveCityName" placeholder="到达城市" value="${arriveCityName}"
                                   class="input clickinput" data-areaId="${arriveCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="arriveCityName-2">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                        <div class="time o_select_input fl"><i class="time_ico in_time"></i>
                            <input type="text" placeholder="出发时间" name="leaveDate" id="leaveDate-2" value="${leaveDate}"
                                   readOnly="true" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"
                                   class="input">
                        </div>
                        <div class="time o_select_input fl"><i class="time_ico in_time"></i>
                            <input type="text" placeholder="返回时间" name="returnDate" id="returnDate-2"
                                   value="${returnDate}"
                                   readOnly="true" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})" class="input">
                        </div>
                    </div>
                    <input type="button" class="submitSearch but fr" value="重新搜索" onclick="submitTrafficForm(2)"/>
                </form>
                <form id="searchForm-3" action="toTrain.jhtml" method="post" class="trainForm display-none">
                    <div class="o_select_fl fl">
                        <div class="categories o_select_input order_cat posiR fl w1">
                            <i class="ico3"></i>
                            <input type="text" placeholder="联程" value="" class="input" readonly="readonly">
                            <div class="posiA categories_div">
                                <ul>
                                    <li><a href="javaScript:;" onclick="changeModule(1)">单程</a></li>
                                    <li><a href="javaScript:;" onclick="changeModule(2)">往返</a></li>
                                    <li><a href="javaScript:;" onclick="changeModule(3)">联程</a></li>
                                </ul>
                            </div>
                        </div>

                        <%--<div class="departure o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="leaveCityName" id="leaveCityName" placeholder="出发城市"--%>
                        <%--value="${leaveCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="leaveCity" name="leaveCity"--%>
                        <%--value="${leaveCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="leaveCity-3" name="leaveCity"
                                   value="${leaveCity}" class="hideValue">
                            <input type="text" name="leaveCityName" placeholder="出发城市" value="${leaveCityName}"
                                   class="input clickinput" data-areaId="${leaveCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="leaveCityName-3">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                        <i class="huan fl"></i>

                        <%--<div class="arrivals o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="arriveCityName" id="arriveCityName" placeholder="到达城市"--%>
                        <%--value="${arriveCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="arriveCity" name="arriveCity"--%>
                        <%--value="${arriveCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="arriveCity-3" name="arriveCity"
                                   value="${arriveCity}" class="hideValue">
                            <input type="text" name="arriveCityName" placeholder="到达城市" value="${arriveCityName}"
                                   class="input clickinput" data-areaId="${arriveCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="arriveCityName-3">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                        <div class="time o_select_input fl"><i class="time_ico in_time"></i>
                            <input type="text" placeholder="出发时间" name="leaveDate" id="leaveDate-3" value="${leaveDate}"
                                   readOnly="true" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"
                                   class="input">
                        </div>

                        <input type="hidden" name="returnDate" id="returnDate-3"
                               value="${leaveDate}"
                               class="input">
                        <%--<div class="arrivals o_select_input fl select_area"--%>
                        <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                        <%--<input type="text" name="arriveCityName" id="transitCityName" placeholder="中转城市"--%>
                        <%--value="${transitCityName}" class="input displayName">--%>
                        <%--<input type="hidden" id="transitCity" name="transitCity"--%>
                        <%--value="${transitCity}" class="hideValue">--%>
                        <%--<div class="list_citys_div" style="position: absolute;">--%>
                        <%--<ul></ul>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="departure o_select_input fl posiR">

                            <input type="hidden" id="transitCity" name="transitCity"
                                   value="${transitCity}" class="hideValue">
                            <input type="text" name="transitCityName" placeholder="到达城市" value="${transitCityName}"
                                   class="input clickinput" data-areaId="${transitCity}"
                                   data-url="${DESTINATION_PATH}/lvxbang/destination/getTrainAreaList.jhtml" id="transitCityName">

                            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                            <!--目的地 clickinput input01 input-->
                            <div class="posiA Addmore categories_Addmore2">
                                <i class="close"></i>
                                <%--<div class="Addmore_d">--%>
                                <%--搜索历史：<span>厦门</span>--%>
                                <%--</div>--%>
                                <dl class="Addmore_dl">
                                    <dt>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                            <li><a href="javaScript:;">A-E</a></li>
                                            <li><a href="javaScript:;">F-J</a></li>
                                            <li><a href="javaScript:;">K-P</a></li>
                                            <li><a href="javaScript:;">Q-W</a></li>
                                            <li><a href="javaScript:;">X-Z</a></li>
                                        </ul>
                                    </div>
                                    </dt>
                                    <dd>
                                        <label></label>
                                        <div class="Addmore_nr">
                                            <ul>
                                                <c:forEach items="${hotDestinations}" var="aArea">
                                                    <li data-id="${aArea.id}">
                                                        <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </dd>
                                    <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                        <dd class="disn">
                                            <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                                <label>${lrArea.name}</label>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <c:forEach items="${lrArea.list}" var="aArea">
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;"
                                                                   title="${aArea.name}">${aArea.name}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </dd>
                                    </c:forEach>
                                </dl>
                                <p class="cl"></p>
                            </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                            <!--关键字提示 clickinput input input01-->
                            <div class="posiA categories_div  KeywordTips">
                                <ul>

                                </ul>
                            </div>

                            <!--错误-->
                            <div class="posiA categories_div cuowu textL">
                                <p class="cl">抱歉未找到相关的结果！</p>
                            </div><!-- #EndLibraryItem -->
                        </div>
                    </div>
                    <input id="flightSearch" type="button" class="submitSearch but fr" value="重新搜索" onclick="submitTrafficForm(3)"/>
                </form>
            </div>
            <p class="cl"></p>
        </div>
    </div>
    <div id="noResultLeave" class="dc" style="display: none">
        <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的车次，您可以更换出行时间或目的地重新查询!
    </div>
    <div id="loadingLeave" class="dc" style="margin-top:70px;color:#666;">
        <img src="/images/loadingx.gif">
        <p class="mt20">
            正在为您加载火车票信息...
        </p>
    </div>
    <!--中-->
    <div class="w1225 Order_hc_center" id="nav" style="margin-bottom: 70px; margin-top: 20px;">
        <!--左-->
        <div id="leave" style="display:none" class="Order_hc_c_fl fl">
            <div class="Order_hc_ss cl posiR">
                <a href="javaScript:;" class="Order_left fl"></a>
                <div class="Order_hc_ss_d fl posiA">
                    <ul id="Order_fj_date_ul" class="Order_hc_ss_d_ul" num="1">
                    </ul>
                </div>
                <%--<div class="calendar fs13 b posiA textC"><i></i>日历</div>--%>
                <a href="javaScript:;" class="Order_right fr"></a>
                <p class="cl"></p>
            </div>

            <div class="Order_hc_list">
                <div class="Order_hc_list_t">
                    <div class="w1 fl">车次</div>
                    <div class="w2 fl">
                        <span class="posiR selected">
                            发时
                            <i class="ioc3 dc-sort-type" id="dc-sort-leave"
                               onclick="Sort.changeSort('Order_hc_list_ul', 'dc-sort-leave', 'sortLeave', 'dc-sort-type', 0)"></i>
                        </span>
                        <span class="posiR" style="display:none;">到时
                            <i
                                    class="ioc3 dc-sort-type" id="dc-sort-arrive"
                                    onclick="Sort.changeSort('Order_hc_list_ul', 'dc-sort-arrive', 'sortArrive', 'dc-sort-type', 1)"></i>
                        </span>
                    </div>
                    <div class="w3 fl">
                        出发/到达
                    </div>
                    <div class="w4 fl">
                        <span class="posiR">运行时长
                            <i
                                    class="ioc3 dc-sort-type" id="dc-sort-time"
                                    onclick="Sort.changeSort('Order_hc_list_ul', 'dc-sort-time', 'sortTime', 'dc-sort-type', 2)"></i>
                        </span>
                    </div>
                    <div class="w5 fl">
                        <span class="posiR">票价(元)
                            <i
                                    class="ioc3 dc-sort-type" id="dc-sort-price"
                                    onclick="Sort.changeSort('Order_hc_list_ul', 'dc-sort-price', 'sortPrice', 'dc-sort-type', 3)"></i>
                        </span>
                    </div>
                    <%--<div class="w6 fl">--%>
                        <%--剩余票数--%>
                    <%--</div>--%>
                    <div id="available-check" class="w7 fr">
                        <label class="fl checkbox posiR disB" input="options"><i
                                onclick="selectTicket('available-check', 'Order_hc_list_ul')"></i></label>
                        <p class="fl">仅有票</p>
                    </div>
                </div>
                <div class="Order_hc_list_div">
                    <ul id="Order_hc_list_ul" class="Order_hc_list_ul">
                    </ul>
                </div>
                <p class="cl"></p>
            </div>
        </div>
        <!--右-->
        <div class="Order_hc_c_fr fr">
        </div>
        <p class="cl"></p>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script type="text/html" id="tpl-traffic-train-item">
    <li>
        <input type="hidden" name="sortLeave" value="{{sortLeave}}">
        <input type="hidden" name="sortArrive" value="{{sortArrive}}">
        <input type="hidden" name="sortTime" value="{{sortTime}}">
        <input type="hidden" name="sortPrice" value="{{sortPrice}}">
        <div class="Order_hc_list_ul_div fs13">
            <div class="w1 fl">
                <b class="name ff_yh fs20">{{trafficCode}}</b>
                <span class="posiR more fs12">经停站<i></i></span>
            </div>
            <div class="w2 fs16 fl">
                <b class="time ff_yh fs20">{{leaveTime}}</b>
                <p>{{arriveTime}}</p>
            </div>
            <div class="w3 fl" id="traffic_leaveAndArrive_{{hashCode}}">
            </div>
            <div class="w4 fl">{{totalTime}}</div>
            <div class="w5 fl">
                <dl class="Order_hc_list_ul_dl" id="traffic_price_{{hashCode}}">
                </dl>
            </div>
            <p class="cl"></p>
        </div>
        <div class="Order_hc_list_ul_hide">
            <input type="hidden" class="vTCode" value="{{trafficCode}}"/>
            <input type="hidden" class="vTSName" value="{{leaveTransportation.name}}"/>
            <input type="hidden" class="vTEName" value="{{arriveTransportation.name}}"/>
            <dl class="textC">
                <dt>
                <div class="w1 fl">站次</div>
                <div class="w2 fl">站名</div>
                <div class="w3 fl">到达时间</div>
                <div class="w4 fl">开车时间</div>
                <div class="w5 fl">停留时间</div>
                </dt>
            </dl>
            <a href="javaScript:;" class="shouqi posiA">收起<i></i></a>
            <p class="cl"></p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-traffic-price-item">
    <dd>
        <label class="type">{{seatName}}</label>
        <span class="price b fs16">{{price}}</span>
        <p class="mingx"><span>{{seatNum}}</span></p>
        <form action="${PLAN_PATH}/lvxbang/plan/booking.jhtml" method="post" class="traffic_flight_train">
            <input type="hidden" id="hashCode" name="code" value="{{hashCode}}"/>
            <a href="javaScript:;" class="but fr textC" onclick="formToPlanPage(this);">选择</a>
        </form>
    </dd>
</script>
<script type="text/html" id="tpl-traffic-flight-date">
    <li>
        <p class="time">{{month}}-{{day}}</p>
        <input style="display:none" name="dateStr" value="{{dateStr}}"/>
        <span>{{dayOfWeek}}</span>
    </li>
</script>
<script src="/js/lvxbang/traffic/trafficCookie.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/common.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/train_dc.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/sort.js" type="text/javascript"></script>

<div style="display: none;">
    <input id="json" value='${json}'>
    <input id="city" value="${leaveCity}">
    <input id="trafficType" value="TRAIN">
    <input id="firstLeave" value="${firstLeaveDate}">
    <input id="secondLeave" value="${leaveDate}">
</div>
</body>
</html>
