<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2016/1/13
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv=“X-UA-Compatible” content=“chrome=1″/>
        <%--<meta http-equiv=“X-UA-Compatible” content=“IE=8″>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>旅行帮线路预订</title>
    <meta name="keywords" content="行程预订">
    <meta name="description" content="行程中所需酒店，机票，火车票，景点门票预订">
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/order2.css" rel="stylesheet" type="text/css">

    <link href="js/Time/skin/WdatePicker.css" rel="stylesheet" type="text/css">
</head>
<body class="free_exercise" onload="pageReload();">
<input type="hidden" value="${newOne}" id="newOne"/>
<input type="hidden" id="startTime">
<input type="hidden" value="0" id="page_reload"/><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!-- #EndLibraryItem -->
<form action="${TRAFFIC_PATH}/lvxbang/traffic/planbookingFlight.jhtml" method="post" id="change-plane-form" class="hide">
    <input type="hidden" name="json" value='${json}'/>
    <input type="hidden" class="leaveCity" name="leaveCity" value=""/>
    <input type="hidden" class="leaveCityName" name="leaveCityName" value=""/>
    <input type="hidden" class="arriveCity" name="arriveCity" value=""/>
    <input type="hidden" class="arriveCityName" name="arriveCityName" value=""/>
    <input type="hidden" class="leaveDate" name="leaveDate" value=""/>
    <input type="hidden" class="firstLeaveDate" name="firstLeaveDate" value=""/>
</form>
<form action="${TRAFFIC_PATH}/lvxbang/traffic/planBoolingTrain.jhtml" method="post" id="change-train-form" class="hide">
    <input type="hidden" name="json" value='${json}'/>
    <input type="hidden" class="leaveCity" name="leaveCity" value=""/>
    <input type="hidden" class="leaveCityName" name="leaveCityName" value=""/>
    <input type="hidden" class="arriveCity" name="arriveCity" value=""/>
    <input type="hidden" class="arriveCityName" name="arriveCityName" value=""/>
    <input type="hidden" class="leaveDate" name="leaveDate" value=""/>
    <input type="hidden" class="firstLeaveDate" name="firstLeaveDate" value=""/>
</form>
<form action="${HOTEL_PATH}/hotel_list.html" method="post" id="change-hotel-form" class="hide">
    <input type="hidden" name="json" value='${json}'/>
    <input type="hidden" class="cityId" name="cityId" value=""/>
    <input type="hidden" class="startDate" name="startDate" value=""/>
    <input type="hidden" class="star" name="star" value=""/>
    <input type="hidden" class="endDate" name="endDate" value=""/>
    <input type="hidden" class="minDate" name="minDate" value=""/>
    <input type="hidden" class="maxDate" name="maxDate" value=""/>
    <input type="hidden" class="firstLeaveDate" name="firstLeaveDate" value=""/>
    <input type="hidden" class="coreScenic" name="coreScenic" value=""/>
</form>
<div class="main cl" style="margin-bottom: 70px">
    <div class="free_exercise_div cl">
        <p class="n_title">您在这里：&nbsp;<a href="${PLAN_PATH}">私人定制</a>&nbsp;&gt;&nbsp;<a href="${PLAN_PATH}/lvxbang/plan/edit.jhtml">线路编辑</a>&nbsp;&gt;&nbsp;自由行 </p>

        <!--左侧-->
        <div class="free_e_fl fl">
            <c:forEach items="${bookingList}" var="bookingInfo" varStatus="allstatus">
                <!--搜索-->
                <div class="Free_e_t booking-panel" style="padding-bottom: 3px;">
                    <label class="fl b title fs16">
                        <b class="ff_yh">${allstatus.count}</b>
                        &gt;<span class="fromCity">${bookingInfo.fromCityName}</span>至${bookingInfo.cityName}
                    </label>

                    <div class="Free_e_t_d fl" >
                        <input type="hidden" class="core-scenic" value="${bookingInfo.coreScenic}"/>
                        <input type="hidden" class="from-city-id" value="${bookingInfo.fromCityId}"/>
                        <input type="hidden" class="to-city-id" value="${bookingInfo.cityId}"/>
                        <input type="hidden" class="city-days" value="${bookingInfo.days}"/>
                        <dl class="fl">
                            <%--<dd class="categories" data-url="/lvxbang/destination/getSeachAreaList.jhtml">--%>
                            <dd class="list_con" data-url="${DESTINATION_PATH}/lvxbang/destination/getSeachAreaList.jhtml">
                                <%--<input type="text" placeholder="出发地" value="${bookingInfo.fromCityName}" data-areaid="${bookingInfo.fromCityId}" class="input destination">--%>
                                <label class="label" for="booking-from-city-${allstatus.index}">出发地：</label>

                                    <c:if test="${allstatus.index==0}">
                                        <input id="booking-from-city-${allstatus.index}" type="text" placeholder="出发地" value="${bookingInfo.fromCityName}" class="input destination input01 Departure clickinput"
                                               name="leaveCityName" maxlength="20" data-areaid="${bookingInfo.fromCityId}"
                                               data-url="${DESTINATION_PATH}/lvxbang/destination/getSeachAreaList.jhtml" autoComplete="Off">
                                    </c:if>
                                    <c:if test="${allstatus.index>0}">
                                        <input id="booking-from-city-${allstatus.index}" type="text" placeholder="出发地" value="${bookingInfo.fromCityName}" class="input destination input01 Departure clickinput"
                                               name="leaveCityName" maxlength="20" data-areaid="${bookingInfo.fromCityId}"
                                               data-url="${DESTINATION_PATH}/lvxbang/destination/getSeachAreaList.jhtml" autoComplete="Off" disabled="disabled">
                                    </c:if>

                                <%--<div class="posiA categories_div small_div">--%>
                                    <%--<ul>--%>

                                    <%--</ul>--%>
                                <%--</div>--%>
                                    <!--目的地 clickinput input01 input-->
                                    <div class="posiA Addmore categories_Addmore2">
                                        <i class="close"></i>
                                        <jx:include fileAttr="/static/master/lvxbang/traffic/traffic_city.htm" targetObject="lvXBangBuildService" targetMethod="buildTrafficIndex" validDay="7"></jx:include>
                                        <p class="cl"></p>
                                    </div>
                                    <!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                    <!--关键字提示 clickinput input input01-->
                                    <div class="posiA categories_div  KeywordTips">
                                        <ul>

                                        </ul>
                                    </div>

                                    <!--错误-->
                                    <div class="posiA categories_div cuowu textL">
                                        <p class="cl">抱歉未找到相关的结果！</p>
                                    </div><!-- #EndLibraryItem -->

                            </dd>
                            <dd>
                                <label class="label" for="from-date-${allstatus.index}">出发日期：</label>
                                <i class="ico2"></i><input id="from-date-${allstatus.index}" type="text" placeholder="入住时间" <c:if test="${allstatus.index > 0}">disabled="disabled"</c:if> value="<fmt:formatDate value="${bookingInfo.fromDate}" pattern="yyyy-MM-dd"></fmt:formatDate>" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" class="input from-date"/>
                            </dd>
                            <dd class="categories posiR hotel-star">
                                <i class="ico3"></i>
                                <input type="text" placeholder="酒店类型" value="" data-value="" class="input " readonly="readonly">

                                <div class="posiA categories_div" style="display: none;">
                                    <ul>
                                        <li>
                                            <label class="fl" >不限</label>
                                        </li>
                                        <li data-value="1">
                                            <label class="fl" >经济型</label>
                                        </li>
                                        <li data-value="2">
                                            <label class="fl" data-value="2">二星级/其他</label>
                                        </li>
                                        <li data-value="3">
                                            <label class="fl" data-value="3">三星级/舒适</label>
                                        </li>
                                        <li data-value="4">
                                            <label class="fl" data-value="4" >四星级/高档</label>
                                        </li>
                                        <li data-value="5">
                                            <label class="fl" data-value="5">五星级/奢华</label>
                                        </li>
                                    </ul>
                                </div>
                            </dd>
                            <dd class="categories posiR traffic-type" data-value="${bookingInfo.trafficType}">
                                <i class="ico3"></i>
                                <input type="text" placeholder="交通方式" value="${bookingInfo.trafficType=='ALL'?"不限":bookingInfo.trafficType=='TRAIN'?"火车":bookingInfo.trafficType=='AIRPLANE'?"飞机":""}" class="input" readonly="readonly">

                                <div class="posiA categories_div" style="display: none;">
                                    <ul>
                                        <li data-value="ALL">
                                            <label class="fl" data-value="ALL">不限</label>
                                        </li>
                                        <li data-value="TRAIN">
                                            <label class="fl" data-value="TRAIN">火车</label>
                                        </li>
                                        <li data-value="AIRPLANE">
                                            <label class="fl" data-value="AIRPLANE">飞机</label>
                                        </li>
                                    </ul>
                                </div>
                            </dd>
                        </dl>
                    </div>
                    <a href="javaScript:;" class="but b fr oval4 textC recommend-booking<c:if test="${allstatus.index == 0}"> first_day</c:if>" data-is-return="${allstatus.last}" style="font-size: 14px;">帮我推荐</a>
                </div>

                <div class="free_e_fl_c booking-info"
                     data-title="${bookingInfo.fromCityName}—${bookingInfo.cityName}"
                     data-title-reverse="${bookingInfo.cityName}—${bookingInfo.fromCityName}"
                     data-city-id="${bookingInfo.cityId}"
                        >
                    <ul class="free_e_fl_c_ut b">
                        <li class="w1">
                            <%--<span class="fl checkbox" input="selectall"><i></i></span>--%>

                        </li>
                        <li class="w2">日期</li>
                        <li class="w3">单价(元)</li>
                        <li class="w4">操作</li>
                    </ul>
                    <div class="free_e_fl_lm posiR">
                        <!--飞机-->

                        <c:if test="${bookingInfo.trafficType!='TRAIN'}">
                            <div class="free_e_fl_c_title">
                                <span class="fl checkbox" input="selectall_group"><i></i></span>

                                <p class="fl" >飞机</p>
                            </div>

                            <div class="free_e_fl_lm_div plane-booking-info">
                                <c:if test="${fn:length(bookingInfo.planes) == 0}">
                                    <div class="free_null textC ff_yh fs20">暂无信息</div>
                                </c:if>
                                <c:if test="${fn:length(bookingInfo.planes) > 0}">
                                    <ul class="free_e_fl_lm_ul">
                                        <c:forEach items="${bookingInfo.planes}" var="plane" varStatus="status">
                                            <li class="booking <c:if test="${status.index>0}">top_b_d</c:if>" data-id="${plane.id}"
                                                data-price-hash="${plane.priceHash}"
                                                data-traffic-hash="${plane.trafficHash}"
                                                data-type="PLANE"
                                                    <c:if test="${status.index==0}">
                                                        data-from-city="${bookingInfo.fromCityId}"
                                                        data-to-city="${bookingInfo.cityId}"
                                                        data-date="<fmt:formatDate value="${plane.date}" pattern="yyyyMMdd"></fmt:formatDate>">
                                                    </c:if>
                                                    <c:if test="${status.index>0}">
                                                        data-from-city="${bookingInfo.cityId}"
                                                        data-to-city="${bookingInfo.fromCityId}"
                                                        data-date="<fmt:formatDate value="${plane.date}" pattern="yyyyMMdd"></fmt:formatDate>">
                                                    </c:if>

                                            <div class="free_e_fl_lm_ul_div">
                                                <c:choose>
                                                <c:when test="${plane.name != null && plane.name != ''}">
                                                <div class="w1 fl">
                                                    <span class="fl checkbox moren <c:if test="${status.index==0}">go</c:if><c:if test="${status.index>0}">return</c:if>" input="options"><i></i></span>

                                                    <p class="fl">
                                                        <c:if test="${status.index==0}">去程</c:if>
                                                        <c:if test="${status.index>0}">返程</c:if>
                                                    </p>
                                                </div>

                                                <div class="w2 fl fj_name" style="width:120px;">
                                                    <p class="img fl"><img src="/images/airlines/${plane.companyCode}.png"></p>

                                                    <div class="fr name">
                                                        <p>${plane.companyName}</p>
                                                        <span>${plane.name}</span>
                                                    </div>
                                                </div>
                                                <div class="w3 fl">
                                                    <div class="time">
                                                        <p>${plane.startTime}</p>
                                                        <span>${plane.startStation}</span>
                                                    </div>
                                                </div>
                                                <div class="w4 fl">
                                                    <div class="time2">
                                                        <p>
                                                            <c:if test="${status.index==0}"> <fmt:formatDate value="${plane.date}"
                                                                                                             pattern="yyyy-MM-dd"></fmt:formatDate></c:if>
                                                            <c:if test="${status.index>0}"> <fmt:formatDate value="${plane.date}"
                                                                                                            pattern="yyyy-MM-dd"></fmt:formatDate></c:if>

                                                            <i class="ico"></i></p>
                                                        <span>${plane.duration}</span>
                                                    </div>
                                                </div>
                                                <div class="w5 fl">
                                                    <div class="time3">
                                                        <p>${plane.endTime}</p>
                                                        <span>${plane.endStation}</span>
                                                    </div>
                                                </div>
                                                <p class="w6 fl" style="width:61px;">${plane.seat}</p>
                                                    <div class="additional_dc" style="width:61px;float:left;text-align: left;">
                                                        <span>燃油:¥<fmt:formatNumber type="number" value="${plane.additionalFuelTax}" maxFractionDigits="0"/></span><br>
                                                        <span>机建:¥<fmt:formatNumber type="number" value="${plane.airportBuildFee}" maxFractionDigits="0"/></span>
                                                    </div>
                                                    <input type="hidden" value="${plane.additionalFuelTax}" class="additionalFuelTax" />
                                                    <input type="hidden" value="${plane.airportBuildFee}" class="airportBuildFee"/>
                                                <p class="w7 fl price"><fmt:formatNumber type="number" pattern="###.#">${plane.price}</fmt:formatNumber></p>

                                                <p class="w8 fl"><a href="javaScript:;" class="but oval4 change-plane">更换航班</a></p>
                                                </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${status.index==0}"><div class="free_null2 textC ff_yh fs20">暂无去程信息</div></c:if>
                                                        <c:if test="${status.index>0}"><div class="free_null2 textC ff_yh fs20">暂无返程信息</div></c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                                <p class="cl"></p>
                                            </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                        </c:if>

                        <!--火车-->

                        <c:if test="${bookingInfo.trafficType!='AIRPLANE'}">
                            <div class="free_e_fl_c_title">
                                <span class="fl train-check checkbox" input="selectall_group"><i></i></span>

                                <p class="fl" >火车</p>
                            </div>

                            <div class="free_e_fl_lm_div train-booking-info">
                                <c:if test="${fn:length(bookingInfo.trains) == 0}">
                                    <div class="free_null textC ff_yh fs20">暂无信息</div>
                                </c:if>
                                <c:if test="${fn:length(bookingInfo.trains) > 0}">
                                    <ul class="free_e_fl_lm_ul">
                                        <c:forEach items="${bookingInfo.trains}" var="plane" varStatus="status">
                                            <li class="booking <c:if test="${status.index>0}"> top_b_d</c:if>" data-id="${plane.id}"
                                                data-price-hash="${plane.priceHash}"
                                                data-traffic-hash="${plane.trafficHash}"
                                                data-type="TRAIN"
                                                    <c:if test="${status.index==0}">
                                                        data-from-city="${bookingInfo.fromCityId}"
                                                        data-to-city="${bookingInfo.cityId}"
                                                        data-date="<fmt:formatDate value="${bookingInfo.fromDate}" pattern="yyyyMMdd"></fmt:formatDate>">
                                                    </c:if>

                                                    <c:if test="${status.index>0}">
                                                        data-from-city="${bookingInfo.cityId}"
                                                        data-to-city="${bookingInfo.fromCityId}"
                                                        data-date="<fmt:formatDate value="${bookingInfo.toDate}" pattern="yyyyMMdd"></fmt:formatDate>">
                                                    </c:if>
                                            <div class="free_e_fl_lm_ul_div">
                                                <c:choose>
                                                <c:when test="${plane.name != null && plane.name != ''}">
                                                <div class="w1 fl">
                                                    <span class="fl checkbox moren <c:if test="${status.index==0}">go</c:if><c:if test="${status.index>0}">return</c:if>" input="options"><i></i></span>

                                                    <p class="fl">
                                                        <c:if test="${status.index==0}">去程</c:if>
                                                        <c:if test="${status.index>0}">返程</c:if>
                                                    </p>
                                                </div>

                                                <div class="w2 fl fj_name">
                                                    <p class=" fl"></p>

                                                    <div class="fr name">
                                                        <span>${plane.name}</span>
                                                    </div>
                                                </div>
                                                <div class="w3 fl">
                                                    <div class="time">
                                                        <p>${plane.startTime}</p>
                                                        <span>${plane.startStation}</span>
                                                    </div>
                                                </div>
                                                <div class="w4 fl">
                                                    <div class="time2">
                                                        <p>
                                                            <c:if test="${status.index==0}"><fmt:formatDate value="${plane.date}"
                                                                                                            pattern="yyyy-MM-dd"></fmt:formatDate><i
                                                                    class="ico"></i></c:if>
                                                            <c:if test="${status.index>0}"><fmt:formatDate value="${plane.date}"
                                                                                                           pattern="yyyy-MM-dd"></fmt:formatDate><i
                                                                    class="ico"></i></c:if>

                                                        </p>
                                                        <span>${plane.duration}</span>
                                                    </div>
                                                </div>
                                                <div class="w5 fl">
                                                    <div class="time3">
                                                        <p>${plane.endTime}</p>
                                                        <span>${plane.endStation}</span>
                                                    </div>
                                                </div>
                                                <p class="w6 fl">${plane.seat}</p>

                                                <p class="w7 fl price"><fmt:formatNumber type="number" pattern="###.#">${plane.price}</fmt:formatNumber></p>

                                                <p class="w8 fl">
                                                    <a href="javaScript:;" class="but oval4 change-train">更换火车票
                                                    </a>
                                                </p>
                                                </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${status.index==0}"><div class="free_null2 textC ff_yh fs20">暂无去程信息</div></c:if>
                                                        <c:if test="${status.index>0}"><div class="free_null2 textC ff_yh fs20">暂无返程信息</div></c:if>
                                                    </c:otherwise>
                                                </c:choose>

                                                <p class="cl"></p>
                                            </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                        </c:if>

                        <!--酒店-->

                        <div class="free_e_fl_c_title">
                            <%--<span class="fl checkbox" input="selectall_group"><i></i></span>--%>

                            <p class="fl">酒店</p>
                        </div>

                        <div class="free_e_fl_lm_div hotel-booking-info">
                            <c:if test="${fn:length(bookingInfo.hotels) == 0}">
                                <div class="free_null textC ff_yh fs20">暂无信息</div>
                            </c:if>
                            <c:if test="${fn:length(bookingInfo.hotels) > 0}">
                                <ul class="free_e_fl_lm_ul">
                                    <c:forEach items="${bookingInfo.hotels}" var="hotel" varStatus="status">
                                        <li class="hotel-node">
                                            <div class="free_e_fl_lm_ul_div" style="border-bottom: 1px solid #eee;">
                                                <div class="w1 fl">
                                                        <%--<span class="fl hotel-check checkbox" input="options"><i></i></span>--%>

                                                    <p class="fl">入住</p>
                                                </div>
                                                <div class="w2 fl jd_name">
                                                    <b>${hotel.name}</b>

                                                    <p class="hstar cl"><i style="width: ${hotel.star*12}px"></i></p>
                                                </div>
                                                <div class="w3 fl">
                                                    <div class="time">
                                                        <p class="jd_time">入住</p>

                                                        <div class="time_p">
                                                            <input id="hotel-start-date-${allstatus.index}" style="background: url(/images/ico.png) 81px -662px no-repeat;width: 80px;line-height: 19px;text-align: left;" class="hotel-start-date input" type="text" placeholder="入住日期" value="${hotel.checkInDate}"  onclick="WdatePicker({minDate:'<fmt:formatDate pattern="yyyy-MM-dd" value="${bookingInfo.fromDate}" type="both"/>',onpicked:function(){$(this).change()}, maxDate:'#F{$dp.$D(\'hotel-end-date-${allstatus.index}\',{d:-1})}'})"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="w4 fl">
                                                    <div class="time2 jd_time2">
                                                        <p><i class="ico"></i></p>
                                                    </div>
                                                </div>
                                                <div class="w5 fl">
                                                    <div class="time3">
                                                        <p class="jd_time">退房</p>

                                                        <div class="time_p">
                                                            <input type="text" id="hotel-end-date-${allstatus.index}" style="background: url(/images/ico.png) 81px -662px no-repeat;width: 80px;line-height: 19px;text-align: left;" placeholder="退房日期" value="${hotel.checkOutDate}" class="input hotel-end-date" onclick="WdatePicker({minDate:'#F{$dp.$D(\'hotel-start-date-${allstatus.index}\',{d:1})}',onpicked:function(){$(this).change()}, maxDate:'<fmt:formatDate pattern="yyyy-MM-dd" value="${bookingInfo.toDate}" type="both"/>'})"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${bookingInfo.fromDate}" type="both"/>">
                                                <input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${bookingInfo.toDate}" type="both"/>">
                                                <div class="w6 fl jd_fs">
                                                    <p><b>${hotel.score/20}分</b>/5分</p>
                                                        <%--${hotel.commentCount}人点评--%>
                                                </div>
                                                <p class="w7 fl price">
                                                    <span class="default-price"><fmt:formatNumber type="number" pattern="###.#">${hotel.price}</fmt:formatNumber><span style="font-size: 13px;font-weight: 100;"></span></span><span class="selected-price"></span>
                                                </p>

                                                <p class="w8 fl"><a href="javaScript:;" class="but oval4 change-hotel">更换酒店</a></p>

                                                <p class="cl"></p>
                                            </div>

                                            <div class="free_e_fl_lm_ul_div_poen cl" style="height: 40px;font-size: 12px;">
                                                <dl>
                                                    <c:forEach items="${hotel.availableRooms}" var="room" varStatus="status2">
                                                        <%--<dd style="<c:if test="${fn:length(room.roomName) > 11 || fn:length(room.description) >28}">line-height:24px;</c:if>"--%>
                                                        <dd style="line-height:24px;"
                                                            class="booking" data-days="${bookingInfo.days+1}" data-hotel-source="${hotel.source}"  data-id="${room.priceId}" data-hotel-id="${room.hotelId}">
                                                            <div class="w1 fl" <c:if test="${status2.index==0}">style="margin-top: 16px;" </c:if>>
                                                                <span class="fl checkbox room-checkbox <c:if test="${status2.index==0}">moren</c:if>" input="options"><i></i></span>
                                                            </div>
                                                            <p class="w2 fl" <c:if test="${status2.index==0}">style="line-height: 44px;" </c:if>>${room.roomName} </p>

                                                            <p class="w3 fl" style="width:318px;margin-right: 20px;<c:if test="${status2.index==0}">line-height: 44px; </c:if>text-align: left;">&nbsp;${room.description} </p>

                                                            <p class="w4 fl" <c:if test="${status2.index==0}">style="line-height: 44px;" </c:if>>
                                                                <c:if test="${room.breakfast}">含早餐</c:if><c:if test="${!room.breakfast}">不含早餐</c:if></p>

                                                            <p class="w5 fl price" style="width: 50px;margin-left: 23px;<c:if test="${status2.index==0}">line-height: 44px; </c:if>"><fmt:formatNumber type="number" pattern="###.#">${room.price}</fmt:formatNumber></p>
                                                            <p style="<c:if test="${status2.index==0}">margin-top:16px;</c:if>
                                                            <c:if test="${status2.index!=0}">margin-top:6px;</c:if>
                                                                    float:left;
                                                                    line-height: 11px;
                                                                    font-size: 10px;
                                                                    color:white;
                                                            <c:if test="${room.status == 'GUARANTEE'}">background-color: rgba(238, 220, 130, 0.98);</c:if>
                                                            <c:if test="${room.status != 'GUARANTEE'}">background-color: #69A7EB;</c:if>

                                                                    border-radius: 3px;
                                                                    padding: 1px;
                                                                    /*vertical-align: -webkit-baseline-middle;*/
                                                                    ">
                                                                <c:if test="${room.status == 'GUARANTEE'}">担保</c:if>
                                                                <c:if test="${room.status != 'GUARANTEE'}">到付</c:if>
                                                            </p>
                                                            <p class="cl"></p>
                                                        </dd>
                                                    </c:forEach>
                                                </dl>
                                                <c:if test="${fn:length(hotel.availableRooms) > 1}">
                                                <p class="w6 more" style="margin-top: 3px;">
                                                    <a href="javaScript:;" class="but">展开<i></i></a>
                                                </p>
                                                </c:if>
                                            </div>

                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <p class="cl"></p>
                        </div>

                        <div class="free_e_fl_open posiR">
                            <ul>
                                <c:forEach items="${bookingInfo.tripLines}" var="tripLine" varStatus="tripStatlus">
                                    <li>
                                        <p class="time fl">
                                            <i></i>第${tripStatlus.count}天 </p>

                                        <div class="fl nr">
                                            <c:forEach items="${tripLine}" var="scenic" varStatus="scenicStatus"><c:if test="${scenicStatus.index>0}">--</c:if>${scenic}</c:forEach>
                                        </div>
                                    </li>
                                </c:forEach>

                            </ul>
                            <a href="${PLAN_PATH}/lvxbang/plan/edit.jhtml" class="but b">修改景点&gt;</a>

                            <p class="cl"></p>
                        </div>

                        <%--<div class="free_e_fl_botm posiA"><a href="javaScript:;"><span class="more_scenic" scenic_name="展开${bookingInfo.cityName}游玩景点">展开${bookingInfo.cityName}游玩景点</span><i></i></a></div>--%>
                        <p class="cl"></p>
                    </div>
                </div>
            </c:forEach>

            <div class="Free_exercise_div_bottom cl">

                <a href="javaScript:;" style="margin-right:20px;border:0;color:#fff;background:#f66000;width:120px;height:35px;" class="oval4 b fs14 textC fl" onclick="history.go(-1);">上一步</a>
                <%--<p class="fl b">自由行套餐合计(不含酒店费用)：<span><span class="total-summary">0</span></span></p>--%>
                <form id="save-plan-form" action="${PLAN_PATH}/lvxbang/plan/saveTransAndHotel.jhtml" method="post">
                    <input type="hidden" name="json" value="" id="plan-trans-and-hotel"/>
                    <input type="hidden" name="planId" value="${planId}" class="planId"/>
                </form>
                <a href="javascript:;" class="b fr disB textC save-button">完成</a>
                <%--<a href="javascript:history.back();" class="b fr disB textC " style="margin-right: 35px;">返回上一步</a>--%>

                <p class="cl"></p>
            </div>

        </div>


        <!--右侧-->
        <div class="free_e_fr fr" id="nav" style="margin-top: 88px;">
            <div class="nav">
                <p class="title" style="background: #faab7d;">费用明细</p>

                <div class="free_e_fr_div cl" style="font-size: 14px;">
                    <div class="free_e_fr_t">
                        <ul class="free_e_fr_ul">
                            <li class="plane-summary">
                                <b class="name" style="color:#ff6000;">机票</b>

                                <div class="summary-panel">
                                </div>
                            </li>
                            <li class="train-summary">
                                <b class="name" style="color:#ff6000;">火车票</b>

                                <div class="summary-panel">
                                </div>

                            </li>
                            <li class="hotel-summary">
                                <b class="name" style="color:#ff6000;">酒店</b>

                                <div class="summary-panel">
                                </div>
                            </li>
                        </ul>
                        <p class="cl"></p>
                    </div>
                    <div class="free_e_fr_c discount">
                        <div class="cl free_e_fr_c_p" style="height:82px;">
                            <div style="height: 41px;border-bottom: 1px dashed #ccc;margin-bottom: 5px;color:#666;">
                            <span style="line-height: 18px;">订单总价：</span>
                            <span  style="float: right;color:#666;line-height: 18px;" class="ddzj"></span><br/>
                            <span style="line-height: 18px;">酒店到付：</span>
                            <span  style="float: right;color:#666;line-height: 18px;" class="jddf"></span><br/>
                            </div>
                            <label class="fl" style="font-size: 19px;margin-top: 5px;color:#666;font-weight: bolder;">应付总额:</label>

                            <p class="fr fs16" style="margin-top: 5px;"><span class="total-summary"></span></p>
                        </div>
                        <div class="cl free_e_fr_c_p free_e_fr_c_p2 discount display-none">
                            <label class="fl">小帮价:</label>
                            <b class="fr fs20 ff_yh">1988</b>
                            <span class="fr mr5">7.5折</span>
                        </div>

                        <a href="javaScript:;" class="free_e_fr_but ffhover b oval4 booking-button"><span >填写订单</span>
                            <div class="posiR">
                                <div class="posiA tishi">
                                    <img src="/images/xxyd.png" class="fr">

                                    <p class="tishi_p  fs13 cl">
                                        下一步购买自由行套餐，享受小帮价超值优惠。
                                    </p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <form action="${INDEX_PATH}/lvxbang/order/orderPlan.jhtml" method="post" id="booking-order">
                        <input type="hidden" id="planId" class="planId" name="planId" value="${planId}">
                        <input type="hidden" id="json-data" name="data"/>
                    </form>
                    <p class="cl"></p>
                </div>
            </div>
        </div>

        <p class="cl"></p>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script type="text/html" id="tpl-traffic-summary">
    <div class="cl nr">
        <label class="fl qroom" style="height: 16px;">{{name}}</label>
        <b class="price fr qmoney" style="height: 16px;">¥{{price | formatPrice}}</b>
    </div>
</script>
<script type="text/html" id="tpl-traffic-summary2">
    <div class="cl nr" style="height:65px;margin-bottom: 0px;">
        <label class="fl" style="height: 16px;">{{name}}
            <%--<br/>--%>
            <%--<span>票价：</span>--%>
            <%--<span class="fr">{{price}}</span><br/>--%>
            <%--<span>燃油费：</span>--%>
            <%--<span class="fr" class="additionalFuelTaxPrice">{{additionalFuelTax}}</span><br/>--%>
            <%--<span >机建费：</span>--%>
            <%--<span class="fr" class="airportBuildFeePrice">{{airportBuildFee}}</span>--%>
        </label>
        <b class="price fr" style="height: 16px;">¥{{calPlanPrice price additionalFuelTax airportBuildFee}}</b>
        <br/>
        <span style="color:#999;font-size: 12px;">机票价</span>
        <span class="fr" style="color:#999;font-size: 12px;">{{price}}</span><br/>
        <span style="color:#999;font-size: 12px;">燃油费</span>
        <span class="fr" class="additionalFuelTaxPrice" style="color:#999;font-size: 12px;">{{additionalFuelTax}}</span><br/>
        <span style="color:#999;font-size: 12px;">机建费</span>
        <span class="fr" class="airportBuildFeePrice" style="color:#999;font-size: 12px;">{{airportBuildFee}}</span>
        <%--<div>--%>
            <%--<br/>--%>
            <%--<span>燃油费：</span>--%>
            <%--<span class="fr" id="additionalFuelTaxPrice">${additionalFuelTax}</span><br/>--%>
            <%--<span >机建费：</span>--%>
            <%--<span class="fr" id="airportBuildFeePrice">${airportBuildFee}</span>--%>
        <%--</div>--%>
    </div>
</script>
<script type="text/html" id="tpl-hotel-summary">
    <div class="cl nr" >
        <label class="fl" style="margin-top: 4px;height: 16px;width: 90px;">{{name}}</label>
        <span class="fl" style="height: 25px; line-height: 25px; margin-left: 5px;">{{days}}晚1间</span>
        <b class="price fr" style="height: 25px; line-height: 25px;">¥{{price | formatPrice}}</b>
    </div>
</script>

<script type="text/html" id="tpl-booking-info-item2">
    <!--飞机-->
    {{if !noPlane}}
    <div class="free_e_fl_c_title reload_message">
        <span class="fl checkbox" input="selectall_group"><i></i></span>

        <p class="fl" >飞机</p>
    </div>

    <div class="free_e_fl_lm_div plane-booking-info reload_message">
        {{if planeEmpty }}
        <div class="free_null textC ff_yh fs20">暂无信息</div>
        {{else}}
        <ul class="free_e_fl_lm_ul">
            {{each planes as plane index}}
            <li class="booking {{if index >0}}top_b_d{{/if}}" data-id="{{plane.id}}"
                data-price-hash="{{plane.priceHash}}"
                data-traffic-hash="{{plane.trafficHash}}"
                data-type="PLANE"
                {{if index == 0 }}
                data-from-city="{{fromCityId}}"
                data-to-city="{{cityId}}"
                data-date="{{fromDateStr}}"
                {{else}}
                data-from-city="{{cityId}}"
                data-to-city="{{fromCityId}}"
                data-date="{{toDateStr}}"
                {{/if}}
            >
            <div class="free_e_fl_lm_ul_div">
                {{if plane.name != null && plane.name.length > 0}}
                <div class="w1 fl">
                    <span class="fl checkbox moren {{if index == 0 }}go{{else}}return{{/if}}" input="options"><i></i></span>

                    <p class="fl">
                        {{if index == 0 }}去程{{else}}返程{{/if}}
                    </p>
                </div>
                <div class="w2 fl fj_name" style="width:120px;">
                    <p class="img fl"><img src="/images/airlines/{{plane.companyCode}}.png"></p>

                    <div class="fr name">
                        <p>{{plane.companyName}}</p>
                        <span>{{plane.name}}</span>
                    </div>
                </div>
                <div class="w3 fl">
                    <div class="time">
                        <p>{{plane.startTime}}</p>
                        <span>{{plane.startStation}}</span>
                    </div>
                </div>
                <div class="w4 fl">
                    <div class="time2">
                        <p>
                            {{if index == 0 }} {{fromDate}}{{else}} {{toDate}}{{/if}}
                            <i class="ico"></i></p>
                        <span>{{plane.duration}}</span>
                    </div>
                </div>
                <div class="w5 fl">
                    <div class="time3">
                        <p>{{plane.endTime}}</p>
                        <span>{{plane.endStation}}</span>
                    </div>

                </div>

                <p class="w6 fl" style="width:72px;">{{plane.seat}}</p>
                <div class="additional_dc" style="width:61px;float:left;text-align: left;">
                    <span>燃油:¥{{plane.additionalFuelTax}}</span><br>
                    <span>机建:¥{{plane.airportBuildFee}}</span>
                </div>
                <p class="w7 fl price">{{plane.price}}</p>
                <input type="hidden" value="{{plane.additionalFuelTax}}" class="additionalFuelTax" />
                <input type="hidden" value="{{plane.airportBuildFee}}" class="airportBuildFee"/>
                <p class="w8 fl">
                    <a href="javaScript:;" class="but oval4 change-plane">
                        更换航班
                    </a>
                </p>
                {{else}}
                    <div class="free_null2 textC ff_yh fs20">暂无{{if index == 0 }}去程{{else}}返程{{/if}}信息</div>
                {{/if}}

                <p class="cl"></p>
            </div>
            </li>
            {{/each}}
        </ul>
        {{/if}}

    </div>
    {{/if}}
    <!--火车-->
    {{if !noTrain}}
    <div class="free_e_fl_c_title reload_message">
        <span class="fl train-check checkbox" input="selectall_group"><i></i></span>

        <p class="fl">火车</p>
    </div>

    <div class="free_e_fl_lm_div train-booking-info reload_message">
        {{if trainEmpty }}
        <div class="free_null textC ff_yh fs20">暂无信息</div>
        {{else}}
        <ul class="free_e_fl_lm_ul">
            {{each trains as plane index}}
            <li class="booking {{if index >0}}top_b_d{{/if}}" data-id="{{plane.id}}"
                data-price-hash="{{plane.priceHash}}"
                data-traffic-hash="{{plane.trafficHash}}"
                data-type="TRAIN"
                {{if index == 0 }}
                data-from-city="{{fromCityId}}"
                data-to-city="{{cityId}}"
                data-date="{{fromDateStr}}"
                {{else}}
                data-from-city="{{cityId}}"
                data-to-city="{{fromCityId}}"
                data-date="{{toDateStr}}"
                {{/if}}
            >
            <div class="free_e_fl_lm_ul_div">
                {{if plane.name != null && plane.name.length > 0}}
                <div class="w1 fl">
                    <span class="fl checkbox moren {{if index == 0 }}go{{else}}return{{/if}}" input="options"><i></i></span>

                    <p class="fl">
                        {{if index == 0 }}去程{{else}}返程{{/if}}
                    </p>
                </div>
                <div class="w2 fl fj_name">
                    <p class=" fl"></p>

                    <div class="fr name">
                        <span>{{plane.name}}</span>
                    </div>
                </div>
                <div class="w3 fl">
                    <div class="time">
                        <p>{{plane.startTime}}</p>
                        <span>{{plane.startStation}}</span>
                    </div>
                </div>
                <div class="w4 fl">
                    <div class="time2">
                        <p>
                            {{if index == 0 }}{{fromDate}}{{else}}{{toDate}}{{/if}}

                            <i class="ico"></i></p>
                        <span>{{plane.duration}}</span>
                    </div>
                </div>
                <div class="w5 fl">
                    <div class="time3">
                        <p>{{plane.endTime}}</p>
                        <span>{{plane.endStation}}</span>
                    </div>
                </div>
                <p class="w6 fl">{{plane.seat}}</p>

                <p class="w7 fl price">{{plane.price}}</p>

                <p class="w8 fl">
                    <a href="javaScript:;" class="but oval4 change-train">
                        更换火车票
                    </a>
                </p>
                {{else}}
                <div class="free_null2 textC ff_yh fs20">暂无{{if index == 0 }}去程{{else}}返程{{/if}}信息</div>
                {{/if}}

                <p class="cl"></p>
            </div>
            </li>
            {{/each}}
            {{/if}}
    </div>
    {{/if}}
    <!--酒店-->

    <div class="free_e_fl_c_title reload_message">
        <%--<span class="fl checkbox" input="selectall_group"><i></i></span>--%>

        <p class="fl">酒店</p>
    </div>

    <div class="free_e_fl_lm_div hotel-booking-info reload_message">
        {{if hotelEmpty }}
        <div class="free_null textC ff_yh fs20">暂无信息</div>
        {{else}}
        <ul class="free_e_fl_lm_ul">

            {{each hotels as hotel index}}
            <li class="hotel-node">
                <div class="free_e_fl_lm_ul_div" style="border-bottom: 1px solid #eee;">
                    <div class="w1 fl">
                        <%--<span class="fl hotel-check checkbox" input="options"><i></i></span>--%>

                        <p class="fl">入住</p>
                    </div>
                    <div class="w2 fl jd_name">
                        <b>{{hotel.name}}</b>

                        <p class="hstar cl"><i style="width: {{hotel.star*12}}px;"></i></p>
                    </div>
                    <div class="w3 fl">
                        <div class="time">
                            <p class="jd_time">入住</p>

                            <div class="time_p">
                                <input id="hotel-start-date-{{panelIndex}}" style="background: url(/images/ico.png) 81px -662px no-repeat;width: 80px;line-height: 19px;text-align: left;" class="hotel-start-date input" type="text" placeholder="入住日期" value="{{hotel.checkInDate}}"  onclick="WdatePicker({minDate:'{{hotel.checkInDate}}',onpicked:function(){$(this).change()}, maxDate:'#F{$dp.$D(\'hotel-end-date-{{panelIndex}}\',{d:-1})}'})"/>
                            </div>
                        </div>
                    </div>
                    <div class="w4 fl">
                        <div class="time2 jd_time2">
                            <p><i class="ico"></i></p>
                        </div>
                    </div>
                    <div class="w5 fl">
                        <div class="time3">
                            <p class="jd_time">退房</p>

                            <div class="time_p">
                                <input type="text" id="hotel-end-date-{{panelIndex}}" style="background: url(/images/ico.png) 81px -662px no-repeat;width: 80px;line-height: 19px;text-align: left;" placeholder="退房日期" value="{{hotel.checkOutDate}}" class="input hotel-end-date" onclick="WdatePicker({minDate:'#F{$dp.$D(\'hotel-start-date-{{panelIndex}}\',{d:1})}',onpicked:function(){$(this).change()}, maxDate:'{{hotel.checkOutDate}}'})"/>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" value="{{hotel.checkInDate}}">
                    <input type="hidden" value="{{hotel.checkOutDate}}">
                    <div class="w6 fl jd_fs">
                        <p><b>{{hotel.score/20}}分</b>/5分</p>
                        <%--{{hotel.commentCount}}人点评--%>
                    </div>
                    <p class="w7 fl price">
                    <p class="w7 fl price">
                        <span class="default-price">{{hotel.price}}<span style="font-size: 13px;font-weight: 100;">起</span></span><span class="selected-price"></span>
                    </p>
                    </p>

                    <p class="w8 fl"><a href="javaScript:;" class="but oval4 change-hotel">更换酒店</a></p>

                    <p class="cl"></p>
                </div>

                <div class="free_e_fl_lm_ul_div_poen cl" style="height: 40px;font-size: 12px;">
                    <dl>
                        {{each hotel.availableRooms as room roomIndex}}
                        <%--<dd  {{if (room.roomName.length>11 || room.description.length>28) & roomIndex > 0}}style="line-height:32px;" {{/if}} class="booking"  data-days="{{days+1}}" data-hotel-source="{{hotel.source}}" data-id="{{room.priceId}}" data-hotel-id="{{room.hotelId}}">--%>
                        <dd  style="line-height:32px;"  class="booking"  data-days="{{days+1}}" data-hotel-source="{{hotel.source}}" data-id="{{room.priceId}}" data-hotel-id="{{room.hotelId}}">
                            <div class="w1 fl"{{if roomIndex==0}} style="margin-top: 16px;"{{/if}}>
                                <span class="fl checkbox {{if roomIndex == 0 }}moren{{/if}} room-checkbox" input="options"><i></i></span>
                            </div>
                            <p class="w2 fl"{{if roomIndex==0}} style="line-height: 44px;"{{/if}}>{{room.roomName}} </p>

                            <p class="w3 fl" style="text-align: left;width:318px;margin-right: 20px;{{if roomIndex==0}}line-height: 44px;{{/if}}">&nbsp;{{room.description}} </p>

                            <p class="w4 fl"{{if roomIndex==0}} style="line-height: 44px;"{{/if}}>
                                {{if room.breakfast}}含早餐{{else}}不含早餐{{/if}}
                            <p class="w5 fl price" style="width: 50px;margin-left: 23px;{{if roomIndex==0}}line-height: 44px;{{/if}}">{{room.price}}</p>
                            {{if room.status != 'GUARANTEE'}}
                               {{if roomIndex==0}}
                                <p style="
                                        margin-top:14px;
                                        line-height: 11px;
                                        float:left;
                                        font-size: 10px;
                                        color:white;
                                        background-color: #69A7EB;
                                        border-radius: 3px;
                                        padding: 1px;

                                        ">

                                {{/if}}
                                {{if roomIndex!=0}}
                                <p style="
                                        margin-top:10px;
                                        line-height: 11px;
                                        float:left;
                                        font-size: 10px;
                                        color:white;
                                        background-color: #69A7EB;
                                        border-radius: 3px;
                                        padding: 1px;

                                        ">
                                {{/if}}
                                {{/if}}


                                {{if room.status == 'GUARANTEE'}}
                                {{if roomIndex==0}}
                                    <p style="
                                            line-height: 11px;
                                            margin-top: 14px;
                                            float:left;
                                            font-size: 10px;
                                            color:white;
                                             background-color: #69A7EB;
                                            background-color: rgba(238, 220, 130, 0.98);

                                            border-radius: 3px;
                                            padding: 1px;

                                            ">

                                {{/if}}
                                {{if roomIndex!=0}}
                                    <p style="
                                            line-height: 11px;
                                            margin-top: 10px;
                                            float:left;
                                            font-size: 10px;
                                            color:white;
                                             background-color: #69A7EB;
                                            background-color: rgba(238, 220, 130, 0.98);

                                            border-radius: 3px;
                                            padding: 1px;

                                            ">
                                {{/if}}

                                {{/if}}
                                {{if room.status == 'GUARANTEE'}} 担保{{/if}}
                                {{if room.status != 'GUARANTEE'}} 到付{{/if}}
                                <%--<c:if test="${room.status == 'GUARANTEE'}">担保</c:if>--%>
                                <%--<c:if test="${room.status != 'GUARANTEE'}">到付</c:if>--%>
                            </p>
                            <p class="cl"></p>
                        </dd>
                        {{/each}}
                    </dl>
                    {{if hotel.availableRooms.length > 1}}
                    <p class="w6 more" style="margin-top: 1px;">
                        <a href="javaScript:;" class="but">展开<i></i></a>
                    </p>
                    {{/if}}
                </div>

            </li>
            {{/each}}
        </ul>
        {{/if}}
        <p class="cl"></p>
    </div>

</script>

<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/plan/booking.js" type="text/javascript"></script>
<%--<script src=”http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js”></script>--%>

<div lang="zh-cn" style="position: absolute; z-index: 100002; display: none; top: 807px; left: 647.5px;">
    <iframe hidefocus="true" width="97" height="9" frameborder="0" border="0" scrolling="no" style="width: 366px; height: 198px;"></iframe>
</div>
</body>
</html>

<script>
    //后退是刷新页面
    function pageReload(){
        if($('#page_reload').val()==0){
            $('#page_reload').val('1')
        }else{
            location.reload();
        }
    }


</script>