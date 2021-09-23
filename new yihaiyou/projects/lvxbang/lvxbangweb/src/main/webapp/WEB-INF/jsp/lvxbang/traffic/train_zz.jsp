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
<body myname="mall" class="Order_hc_zz">
<!--head  end--><!-- #EndLibraryItem --><!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/traffic/Order_hc_top.jsp"></jsp:include>

    <!--中-->
    <div class="w1225 Order_hc_center" id="nav" style="margin-bottom: 70px">
        <!--左-->
        <div id="loadingLeave" style="margin-top: 11%;">
            <img align="absmiddle" src="/images/loadingx.gif">
            <p class="mt10">
                正在为您加载车次信息...
            </p>
        </div>
        <div id="noResultLeave" style="display: none;margin-top: 17%;">
            <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的车次，您可以更换出行时间或目的地重新查询!
        </div>
        <div id="leave" style="display:none" class="Order_hc_c_fl Order_hc_zz_fl fl">
            <div class="Order_title">
                <b class="ff_yh">第一程</b>
                <span>${leaveCityName}->${transitCityName}</span><span id="span-leaveDate">${leaveDate}</span>（共<span
                    id="flight_count"></span>个车次）
            </div>

            <div class="Order_hc_ss cl posiR">
                <a href="javaScript:;" class="Order_left fl"></a>
                <div class="Order_hc_ss_d fl posiA">
                    <ul id="Order_fj_date_ul" class="Order_hc_ss_d_ul" num="1">
                    </ul>
                </div>
                <a href="javaScript:;" class="Order_right fr"></a>
                <p class="cl"></p>
            </div>

            <div class="bot_l">
                <%--搜索条件,暂不需要--%>
                <%--<div class="Order_hc_jg">--%>
                <%--<!--搜索 列表-->--%>
                <%--<div class="searchBox cl ">--%>
                <%--<!--搜索 内容-->--%>
                <%--<div class="select posiR">--%>
                <%--<p class="more"><a href="javaScript:;" class="posiR"><span>更多查询条件</span><b class="ico"></b></a>--%>
                <%--</p>--%>
                <%--<div class="select_div" num="1">--%>
                <%--<label>车&nbsp;&nbsp;&nbsp;&nbsp;型</label>--%>
                <%--<ul class="fore sel cond_type" id="conditions_type">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">G-高铁</a></li>--%>
                <%--<li><a title="" href="javaScript:;">D-动车</a></li>--%>
                <%--<li><a title="" href="javaScript:;">K-普快</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="2">--%>
                <%--<label>座&nbsp;&nbsp;&nbsp;&nbsp;次</label>--%>
                <%--<ul class="fore sel cond_seat" id="conditions_seat">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">商务座</a></li>--%>
                <%--<li><a title="" href="javaScript:;">一等座</a></li>--%>
                <%--<li><a href="javaScript:;">二等座</a></li>--%>
                <%--<li><a href="javaScript:;">高级软卧</a></li>--%>
                <%--<li><a href="javaScript:;">软卧</a></li>--%>
                <%--<li><a href="javaScript:;">软卧</a></li>--%>
                <%--<li><a href="javaScript:;">硬座</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="3">--%>
                <%--<label>出发车站</label>--%>
                <%--<ul class="fore sel cond_port" id="conditions_leavePort">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="4">--%>
                <%--<label>到达车站</label>--%>
                <%--<ul class="fore sel cond_arrive_port" id="conditions_arrivePort">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="5">--%>
                <%--<label>出发时段</label>--%>
                <%--<ul class="fore sel cond_time" id="conditions_leaveTime">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">6-12点</a></li>--%>
                <%--<li><a title="" href="javaScript:;">12-18点 </a></li>--%>
                <%--<li><a title="" href="javaScript:;">18-24点</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="6">--%>
                <%--<label>到达时段</label>--%>
                <%--<ul class="fore sel cond_arrive_time" id="conditions_arriveTime">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">6-12点</a></li>--%>
                <%--<li><a title="" href="javaScript:;">12-18点 </a></li>--%>
                <%--<li><a title="" href="javaScript:;">18-24点</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="7">--%>
                <%--<label>是否始发</label>--%>
                <%--<ul class="fore sel cond_start" id="conditions_start">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">始发</a></li>--%>
                <%--<li><a title="" href="javaScript:;">过路</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="Order_hc_jg_div">--%>
                <%--<label>您已选择</label>--%>
                <%--<div class="Order_hc_jg_div_d">--%>
                <%--<ul class="Order_hc_jg_div_ul fl">--%>
                <%--</ul>--%>
                <%--<a href="javaScript:;" class="close fl">清空条件</a>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <div class="Order_hc_list">
                    <div class="Order_hc_list_t">
                        <div class="w1 fl">车次</div>
                        <div class="w2 fl">
                        <span class="posiR selected">
                            发时
                            <i class="ioc3 fc-sort-type-left" id="fc-sort-leave-left"
                               onclick="Sort.changeSort('Order_hc_list_ul', 'fc-sort-leave-left', 'sortLeave', 'fc-sort-type-left', 0);leaveRadioClick()"></i>
                        </span>
                        <span class="posiR" style="display: none;">到时
                            <i
                                    class="ioc3 fc-sort-type-left" id="fc-sort-arrive-left"
                                    onclick="Sort.changeSort('Order_hc_list_ul', 'fc-sort-arrive-left', 'sortArrive', 'fc-sort-type-left', 1);leaveRadioClick()"></i>
                        </span>
                        </div>
                        <div class="w3 fl">
                            出发/到达
                        </div>
                        <div class="w4 fl">
                        <span class="posiR">运行时长
                            <i
                                    class="ioc3 fc-sort-type-left" id="fc-sort-time-left"
                                    onclick="Sort.changeSort('Order_hc_list_ul', 'fc-sort-time-left', 'sortTime', 'fc-sort-type-left', 2);leaveRadioClick()"></i>
                        </span>
                        </div>
                        <div class="w5 fl">
                        <span class="posiR">票价(元)
                            <i
                                    class="ioc3 fc-sort-type-left" id="fc-sort-price-left"
                                    onclick="Sort.changeSort('Order_hc_list_ul', 'fc-sort-price-left', 'sortPrice', 'fc-sort-type-left', 3);leaveRadioClick()"></i>
                        </span>
                        </div>
                        <%--<div class="w6 fl" style="width: 60px;">--%>
                            <%--余票--%>
                        <%--</div>--%>
                        <div id="zz-left" class="w7 fr" style="width: 70px;">
                            <label class="fl checkbox posiR disB" input="options"><i
                                    onclick="selectTicket('zz-left', 'Order_hc_list_ul')"></i></label>
                            <p class="fl" style="width: 50px;">仅有票</p>
                        </div>
                    </div>

                    <div class="Order_hc_list_div">
                        <ul id="Order_hc_list_ul" class="Order_hc_list_ul">
                        </ul>
                    </div>
                    <p class="cl"></p>
                </div>

                <p class="cl"></p>
            </div>
        </div>

        <!--右-->
        <div id="loadingReturn" class="Order_hc_c_fl Order_hc_zz_fr fr" style=" margin-top:11%;color:#666;">
            <img align="absmiddle" src="/images/loadingx.gif">
            <p class="mt10">
                正在为您加载车次信息...
            </p>
        </div>
        <div id="noResultReturn" style="display: none;margin-top: 17%;">
            <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的车次，您可以更换出行时间或目的地重新查询!
        </div>
        <div id="return" style="display:none" class="Order_hc_c_fl Order_hc_zz_fr fr">
            <div class="Order_title">
                <b class="ff_yh">第二程</b>
                <span>${transitCityName}->${arriveCityName}</span><span id="span-returnDate">${leaveDate}</span>（共<span
                    id="flight_return_count"></span>个车次）
            </div>
            <div class="Order_hc_ss cl posiR">
                <a href="javaScript:;" class="Order_left fl"></a>
                <div class="Order_hc_ss_d fl posiA">
                    <ul id="Order_fj_date_ul_back" class="Order_hc_ss_d_ul" num="1">
                    </ul>
                </div>
                <a href="javaScript:;" class="Order_right fr"></a>
                <p class="cl"></p>
            </div>

            <div class="bot_l">

                <%--搜索条件,暂不需要--%>
                <%--<div class="Order_hc_jg">--%>
                <%--<!--搜索 列表-->--%>
                <%--<div class="searchBox cl ">--%>
                <%--<!--搜索 内容-->--%>
                <%--<div class="select posiR">--%>
                <%--<p class="more"><a href="javaScript:;" class="posiR"><span>更多查询条件</span><b class="ico"></b></a>--%>
                <%--</p>--%>
                <%--<div class="select_div" num="1">--%>
                <%--<label>车&nbsp;&nbsp;&nbsp;&nbsp;型</label>--%>
                <%--<ul class="fore sel cond_type" id="conditions_return_type">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">G-高铁</a></li>--%>
                <%--<li><a title="" href="javaScript:;">D-动车</a></li>--%>
                <%--<li><a title="" href="javaScript:;">K-普快</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="2">--%>
                <%--<label>座&nbsp;&nbsp;&nbsp;&nbsp;次</label>--%>
                <%--<ul class="fore sel cond_seat" id="conditions_return_seat">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">商务座</a></li>--%>
                <%--<li><a title="" href="javaScript:;">一等座</a></li>--%>
                <%--<li><a href="javaScript:;">二等座</a></li>--%>
                <%--<li><a href="javaScript:;">高级软卧</a></li>--%>
                <%--<li><a href="javaScript:;">软卧</a></li>--%>
                <%--<li><a href="javaScript:;">软卧</a></li>--%>
                <%--<li><a href="javaScript:;">硬座</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="3">--%>
                <%--<label>出发车站</label>--%>
                <%--<ul class="fore sel cond_port" id="conditions_return_leavePort">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="4">--%>
                <%--<label>到达车站</label>--%>
                <%--<ul class="fore sel cond_arrive_port" id="conditions_return_arrivePort">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="5">--%>
                <%--<label>出发时段</label>--%>
                <%--<ul class="fore sel cond_time" id="conditions_return_leaveTime">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">6-12点</a></li>--%>
                <%--<li><a title="" href="javaScript:;">12-18点 </a></li>--%>
                <%--<li><a title="" href="javaScript:;">18-24点</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="6">--%>
                <%--<label>到达时段</label>--%>
                <%--<ul class="fore sel cond_arrive_time" id="conditions_return_arriveTime">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">6-12点</a></li>--%>
                <%--<li><a title="" href="javaScript:;">12-18点 </a></li>--%>
                <%--<li><a title="" href="javaScript:;">18-24点</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="7">--%>
                <%--<label>是否始发</label>--%>
                <%--<ul class="fore sel cond_start" id="conditions_return_start">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">始发</a></li>--%>
                <%--<li><a title="" href="javaScript:;">过路</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="Order_hc_jg_div">--%>
                <%--<label>您已选择</label>--%>
                <%--<div class="Order_hc_jg_div_d">--%>
                <%--<ul class="Order_hc_jg_div_ul fl">--%>
                <%--</ul>--%>
                <%--<a href="javaScript:;" class="close fl">清空条件</a>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <div class="Order_hc_list">
                    <div class="Order_hc_list_t">
                        <div class="w1 fl">车次</div>
                        <div class="w2 fl">
                        <span class="posiR selected">
                            发时
                            <i class="ioc3 fc-sort-type-right" id="fc-sort-leave-right"
                               onclick="Sort.changeSort('Order_hc_list_ul_back', 'fc-sort-leave-right', 'sortLeave', 'fc-sort-type-right', 0);returnRaioClick();"></i>
                        </span>
                        <span class="posiR" style="display: none;">到时
                            <i
                                    class="ioc3 fc-sort-type-right" id="fc-sort-arrive-right"
                                    onclick="Sort.changeSort('Order_hc_list_ul_back', 'fc-sort-arrive-right', 'sortArrive', 'fc-sort-type-right', 1);returnRaioClick();"></i>
                        </span>
                        </div>
                        <div class="w3 fl">
                            出发/到达
                        </div>
                        <div class="w4 fl">
                        <span class="posiR">运行时长
                            <i
                                    class="ioc3 fc-sort-type-right" id="fc-sort-time-right"
                                    onclick="Sort.changeSort('Order_hc_list_ul_back', 'fc-sort-time-right', 'sortTime', 'fc-sort-type-right', 2);returnRaioClick();"></i>
                        </span>
                        </div>
                        <div class="w5 fl">
                        <span class="posiR">票价(元)
                            <i
                                    class="ioc3 fc-sort-type-right" id="fc-sort-price-right"
                                    onclick="Sort.changeSort('Order_hc_list_ul_back', 'fc-sort-price-right', 'sortPrice', 'fc-sort-type-right', 3);returnRaioClick();"></i>
                        </span>
                        </div>
                        <%--<div class="w6 fl" style="width: 60px;">--%>
                            <%--余票--%>
                        <%--</div>--%>
                        <div id="zz-right" class="w7 fr" style="width: 70px;">
                            <label class="fl checkbox posiR disB" input="options"><i
                                    onclick="selectTicket('zz-right', 'Order_hc_list_ul_back')"></i></label>
                            <p class="fl" style="width: 50px;">仅有票</p>
                        </div>
                    </div>
                    <div class="Order_hc_list_div">
                        <ul id="Order_hc_list_ul_back" class="Order_hc_list_ul">
                        </ul>
                    </div>
                    <p class="cl"></p>
                </div>
                <p class="cl"></p>
            </div>
        </div>

        <p class="cl"></p>
    </div>

    <div class="Order_hc_bottom cl" id="bottom">
        <div class="w1225">
            <div class="Order_hc_bottom_ul fl">
                <ul>
                    <li>
                        <p class="xc" style="width: 62px;height: 22px;font-size: 14px;text-align: center">第一程</p>
                        <p class="xh" id="go_flight_code"></p>
                        <p class="name" id="go_flight_name">${leaveCityName}->${transitCityName}</p>
                        <p class="time" id="go_time"> --:-- 出发</p>
                        <p class="time" id="go_arrive_time" style="display: none"> --:-- 到达</p>
                        <p class="price" id="go_seat"><i></i><span id="go_price">￥0</span></p>
                    </li>
                    <li>
                        <p class="xc" style="width: 62px;height: 22px;font-size: 14px;text-align: center">第二程</p>
                        <p class="xh" id="return_flight_code"></p>
                        <p class="name" id="return_flight_name">${transitCityName}->${arriveCityName}</p>
                        <p class="time" id="return_time"> --:-- 出发</p>
                        <p class="price" id="return_seat"><i></i><span id="return_price">￥0</span></p>
                    </li>
                </ul>
            </div>
            <div class="Order_hc_bottom_fr fr">
                <form action="${TRAFFIC_PATH}/lvxbang/traffic/orderReturnFlight.jhtml" method="post"
                      onsubmit="return checkReturnFlight(this);"
                      class="traffic_flight_train">
                    <input type="hidden" name="trafficKey" value=""/>
                    <input type="hidden" name="returnTrafficKey" value=""/>
                    <input type="hidden" name="singleTrafficId" value=""/>
                    <input type="hidden" name="singleTrafficPriceId" value=""/>
                    <input type="hidden" name="returnTrafficId" value=""/>
                    <input type="hidden" name="returnTrafficPriceId" value=""/>
                    <%--<a href="#" class="but fr ff_yh fs20 mt20 textC checked">预订</a>--%>
                    <%--<div class="w4 fl">{{price}} <input type="submit" class="but" id="submitButton" value="预订"/></div>--%>
                    <input type="button" class="but fr textC" id="submitButton" value="预订"
                           disabled="disabled" onclick="formToOrderPage(this);"/>
                    <p class="fr price b fs16">总价<span class="ff_yh" id="total_price">￥0</span>
                </form>
            </div>
        </div>
    </div>
</div>
<%--<jsp:include page="/WEB-INF/jsp/lvxbang/popup/popup.jsp"></jsp:include>--%>
<%--<jsp:include page="/WEB-INF/jsp/lvxbang/popup/login_register_popup.jsp"></jsp:include>--%>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script type="text/html" id="tpl-traffic-train-item">
    <li>
        <input type="hidden" name="sortLeave" value="{{sortLeave}}">
        <input type="hidden" name="sortArrive" value="{{sortArrive}}">
        <input type="hidden" name="sortTime" value="{{sortTime}}">
        <input type="hidden" name="sortPrice" value="{{sortPrice}}">
        <div class="Order_hc_list_ul_div fs13">
            <div class="w1 fl">
                <b class="name ff_yh fs20" name="trafficCode">{{trafficCode}}</b>
                <span class="posiR more fs12">经停站<i></i></span>
            </div>
            <div class="w2 fs16 fl">
                <b class="time ff_yh fs20" name="leaveTime">{{leaveTime}}</b>
                <p>
                    {{arriveTime}}
                    {{if days>0}}
                        <span class="w3" style="color: #39D6A7 ; font-size: 13px;">
                            +{{days}}
                            <span class="posiA tishi3" style="display: none;left:auto">
                                到达时间为第{{days+1}}天:{{arriveDate}}
                            </span>
                        </span>
                    {{else}}{{/if}}
                </p>
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
        <div href="javaScript:;" class="but fr textC"><span class="radio">
            <input type="hidden" class="vCode" value="{{code}}"/>
            <input type="hidden" class="vSeat" value="{{seatName}}"/>
            <input type="hidden" class="vDate" value="{{leaveTime}}"/>
            <input type="hidden" class="vrDate" value="{{arriveTime}}"/>
            <input type="hidden" class="vPrice" value="{{price}}"/>
            <input type="hidden" class="vBuildFee" value="{{airportBuildFee}}"/>
            <input type="hidden" class="vFuelTax" value="{{additionalFuelTax}}"/>
            <input type="hidden" class="vPriceId" value="{{hashCode}}"/>
            <input type="hidden" class="vTrafficId" value="{{trafficHashCode}}"/>
        </span></div>
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
<script src="/js/lvxbang/traffic/train_zz.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/sort.js" type="text/javascript"></script>
</body>
</html>
