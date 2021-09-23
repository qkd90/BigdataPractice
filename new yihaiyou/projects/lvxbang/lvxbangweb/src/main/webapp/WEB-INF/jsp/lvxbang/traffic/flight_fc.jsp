<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${leaveCityName}到${arriveCityName}航班查询_${leaveCityName}到${arriveCityName}机票查询-旅行帮</title>
    <meta name="keywords" content="${leaveCityName}到${arriveCityName}航班查询，${leaveCityName}到${arriveCityName}机票查询，
    ${leaveCityName}到${arriveCityName}特价机票，${leaveCityName}到${arriveCityName}机票预订，${leaveCityName}到${arriveCityName}飞机票查询"/>
    <meta name="description" content="旅行帮为您提供${leaveCityName}到${arriveCityName}特价机票以及${leaveCityName}到${arriveCityName}航班查询服务。旅行帮交通频道是您网上订票的首选。"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/order.css" rel="stylesheet" type="text/css">
</head>
<body myname="mall" class="Order_hc_zz Order_fj Order_fj_zz Order_fj_fc">
<!--head  end--><!-- #EndLibraryItem --><!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>

<div class="main cl">
    <!--头部-->
    <jsp:include page="/WEB-INF/jsp/lvxbang/traffic/Order_fj_top.jsp"></jsp:include>


    <div class="w1225 Order_hc_center" id="nav" style="margin-bottom: 70px;">
        <!--左-->
        <div id="loadingLeave" style="margin-top: 7%">
            <img align="absmiddle" src="/images/loadingx.gif">
            <p class="mt10">
                正在为您加载航班信息...
            </p>
        </div>
        <div id="noResultLeave" style="display: none;margin-top: 17%;">
            <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的航班，您可以更换出行时间或目的地重新查询!
        </div>
        <div id="leave" style="display:none" class="Order_hc_c_fl Order_hc_zz_fl fl">
            <div class="Order_title">
                <b class="ff_yh">去程</b>
                <span>${leaveCityName}->${arriveCityName}</span><span id="span-leaveDate"
                                                                      style="color: #999999;margin-left: 10px">${leaveDate}</span>（共<span
                    id="flight_count" style="color: #ff6000"></span>个航班）
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
                <%--<div class="Order_hc_jg">--%>
                <%--<!--搜索 列表-->--%>
                <%--<div class="searchBox cl ">--%>
                <%--<!--搜索 内容-->--%>
                <%--<div class="select posiR">--%>
                <%--<!--<p class="more"><a href="javaScript:;" class="posiR"><span>更多查询条件</span><b class="ico"></b></a></p>-->--%>
                <%--<div class="select_div" num="1">--%>
                <%--<label>起飞机场</label>--%>
                <%--<ul class="fore sel cond_port" id="conditions_leavePort">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="2">--%>
                <%--<label>航空公司</label>--%>
                <%--<ul class="fore sel cond_company" id="conditions_company">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="3">--%>
                <%--<label>舱&nbsp;&nbsp;&nbsp;&nbsp;位</label>--%>
                <%--<ul class="fore sel cond_seat" id="conditions_seat">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="经济舱" href="javaScript:;">经济舱</a></li>--%>
                <%--<li><a title="商务舱" href="javaScript:;">商务舱</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="4">--%>
                <%--<label>起飞时段</label>--%>
                <%--<ul class="fore sel cond_time" id="conditions_time">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">0-6</a></li>--%>
                <%--<li><a title="" href="javaScript:;">6-12</a></li>--%>
                <%--<li><a title="" href="javaScript:;">12-18</a></li>--%>
                <%--<li><a title="" href="javaScript:;">18-24</a></li>--%>
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
                        <div class="w1 fl">航班</div>
                        <div class="w2 fl"><span class="posiR selected">
                            出发
                            <i class="ioc3 wf-sort-type-left" id="wf-sort-leave-left"
                               onclick="Sort.changeSort('Order_fj_list_ul', 'wf-sort-leave-left', 'sortLeave', 'wf-sort-type-left', 0);leaveRadioClick()">
                            </i></span>
                        </div>
                        <div class="w3 fl"><span class="posiR">到达
                            <i class="ioc3 wf-sort-type-left" id="wf-sort-arrive-left"
                               onclick="Sort.changeSort('Order_fj_list_ul', 'wf-sort-arrive-left', 'sortArrive', 'wf-sort-type-left', 1);leaveRadioClick()"></i>
                        </span></div>
                        <div class="w4 fl"><span class="posiR">价格(元)
                            <i class="ioc3 wf-sort-type-left" id="wf-sort-price-left"
                               onclick="Sort.changeSort('Order_fj_list_ul', 'wf-sort-price-left', 'sortPrice', 'wf-sort-type-left', 2);leaveRadioClick()">
                            </i></span></div>

                    </div>

                    <div class="Order_fj_list_div">
                        <ul id="Order_fj_list_ul" class="Order_fj_list_ul">
                        </ul>
                    </div>
                    <p class="cl"></p>
                </div>

                <p class="cl"></p>
            </div>
        </div>

        <!--右-->
        <div id="loadingReturn" class="Order_hc_c_fl Order_hc_zz_fr fr" style=" margin-top:7%;color:#666;">
            <img align="absmiddle" src="/images/loadingx.gif">
            <p class="mt10">
                正在为您加载航班信息...
            </p>
        </div>
        <div id="noResultReturn" style="display: none;margin-top: 17%;">
            <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的航班，您可以更换出行时间或目的地重新查询!
        </div>
        <div id="return" style="display:none" class="Order_hc_c_fl Order_hc_zz_fr fr">
            <div class="Order_title">
                <b class="ff_yh">返程</b>
                <span>${arriveCityName}->${leaveCityName}</span><span id="span-returnDate"
                                                                      style="color: #999999;margin-left: 10px">${returnDate}</span>（共<span
                    id="flight_return_count" style="color: #ff6000"></span>个航班）
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

                <%--<div class="Order_hc_jg">--%>
                <%--<!--搜索 列表-->--%>
                <%--<div class="searchBox cl ">--%>
                <%--<!--搜索 内容-->--%>
                <%--<div class="select posiR">--%>
                <%--<!--<p class="more"><a href="javaScript:;" class="posiR"><span>更多查询条件</span><b class="ico"></b></a></p>-->--%>
                <%--<div class="select_div" num="1">--%>
                <%--<label>起飞机场</label>--%>
                <%--<ul class="fore sel cond_port" id="conditions_return_leavePort">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="2">--%>
                <%--<label>航空公司</label>--%>
                <%--<ul class="fore sel cond_company" id="conditions_return_company">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="3">--%>
                <%--<label>舱&nbsp;&nbsp;&nbsp;&nbsp;位</label>--%>
                <%--<ul class="fore sel cond_seat" id="conditions_return_seat">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="经济舱" href="javaScript:;">经济舱</a></li>--%>
                <%--<li><a title="商务舱" href="javaScript:;">商务舱</a></li>--%>
                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--<div class="select_div" num="4">--%>
                <%--<label>起飞时段</label>--%>
                <%--<ul class="fore sel cond_time" id="conditions_return_time">--%>
                <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
                <%--<li><a title="" href="javaScript:;">0-6</a></li>--%>
                <%--<li><a title="" href="javaScript:;">6-12</a></li>--%>
                <%--<li><a title="" href="javaScript:;">12-18</a></li>--%>
                <%--<li><a title="" href="javaScript:;">18-24</a></li>--%>
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
                        <div class="w1 fl">航班</div>
                        <div class="w2 fl"><span class="posiR selected">出发
                            <i class="ioc3 wf-sort-type-right" id="wf-sort-leave-right"
                               onclick="Sort.changeSort('Order_fj_list_ul_back', 'wf-sort-leave-right', 'sortLeave', 'wf-sort-type-right', 0);returnRaioClick()"></i>
                        </span></div>
                        <div class="w3 fl"><span class="posiR">到达
                            <i class="ioc3 wf-sort-type-right" id="wf-sort-arrive-right"
                               onclick="Sort.changeSort('Order_fj_list_ul_back', 'wf-sort-arrive-right', 'sortArrive', 'wf-sort-type-right', 1);returnRaioClick()">
                            </i>
                        </span></div>
                        <div class="w4 fl"><span class="posiR">价格(元)
                            <i class="ioc3 wf-sort-type-right" id="wf-sort-price-right"
                               onclick="Sort.changeSort('Order_fj_list_ul_back', 'wf-sort-price-right', 'sortPrice', 'wf-sort-type-right', 2);returnRaioClick()"></i>
                        </span></div>

                    </div>

                    <div class="Order_fj_list_div">
                        <ul id="Order_fj_list_ul_back" class="Order_fj_list_ul">
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
                        <p class="xc qu fs16 b">去</p>
                        <p class="xh" id="go_flight_code"></p>
                        <p class="name" id="go_flight_name">${leaveCityName}->${arriveCityName}</p>
                        <p class="time" id="go_time"> --:-- 出发</p>
                        <p class="time" id="go_arrive_time" style="display: none"> --:-- 到达</p>
                        <p class="price" id="go_seat"><i></i><span id="go_price">￥0</span></p>
                    </li>
                    <li>
                        <p class="xc fan fs16 b">返</p>
                        <p class="xh" id="return_flight_code"></p>
                        <p class="name" id="return_flight_name">${arriveCityName}->${leaveCityName}</p>
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
                    <%--<div class="w4 fl">{{price}} <input type="submit" class="but" id="submitButton" value="预订"/></div>--%>
                    <input type="button" class="but fr textC" id="submitButton" value="预订"
                           disabled="disabled" onclick="formToOrderPage(this);"/>
                    <p class="fr price b fs16">总价<span class="ff_yh" id="total_price">￥0</span>
                </form>

            </div>
        </div>
    </div>
</div>
<!--banner end-->

<%--<jx:include fileAttr="${LVXBANG_INDEX}"></jx:include>--%>

<%--<jsp:include page="/WEB-INF/jsp/lvxbang/popup/popup.jsp"></jsp:include>--%>
<%--<jsp:include page="/WEB-INF/jsp/lvxbang/popup/login_register_popup.jsp"></jsp:include>--%>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>

<script type="text/html" id="tpl-traffic-flight-item">
    <li>
        <input type="hidden" name="sortLeave" value="{{sortLeave}}">
        <input type="hidden" name="sortArrive" value="{{sortArrive}}">
        <input type="hidden" name="sortPrice" value="{{sortPrice}}">
        <div class="Order_fj_list_d">
            <div class="w1 fl fj_name">
                <p class=""><img src="/images/airlines/{{companyCode}}.png"></p>
                <div class="name">
                    <p>{{company}}</p>
                    <span name="trafficCode">{{trafficCode}}</span>
                    <%--<span>{{trafficModel}}</span>--%>
                </div>
            </div>
            <div class="w2 fl">
                <div class="time">
                    <p class="ff_yh" name="leaveTime">{{leaveTime}}</p>
                    <span name="leavePort">{{leaveTransportation.name}}</span>
                </div>
            </div>
            <div class="w3 fl">
                <div class="time2">
                    <p><i class="ico"></i></p>
                    <span>{{totalTime}}</span>
                </div>
            </div>
            <div class="w4 fl">
                <div class="time3">
                    <p class="ff_yh">{{arriveTime}}</p>
                    <span>{{arriveTransportation.name}}</span>
                </div>
            </div>
            <p class="w5 fl fs20 b ff_yh">{{sortPrice}}</p>
            <div class="additional_dc" style="text-align: left;">
                <span>燃油:¥{{additionalFuelTax}}</span><br>
                <span>机建:¥{{airportBuildFee}}</span>
            </div>
        </div>
        <div class="Order_fl_list_dl cl posiR" id="traffic_price_{{hashCode}}" {{if prices.length < 3}}style="height: auto"{{/if}}></div>
        {{if prices.length > 3}}
        <p class="shouqi" style="right: 10px"><a href="javaScript:;" class="posiR"><span
                style="right: 5px">更多舱位</span><b class="ico"></b></a></p>
        {{/if}}
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-traffic-price-item">
    <dl class="textC">
        <dd>
            <div class="w1 fl">{{seatName}}</div>
            <div class="w2 fl">{{seatNum}}</div>
            <div class="w3 fl">
                <a href="#">退改签规则</a>
                <div class="posiA tishi3" style="display: none">
                    {{backPolicy}}
                    {{changePolicy}}
                </div>
            </div>
            <div class="w4 fl" name="price">{{price}}
                <div href="javaScript:;" class="but fr textC">
                    <span class="radio">
                        <input type="hidden" class="vCode" value="{{code}}"/>
                        <input type="hidden" class="vSeat" value="{{seatName}}"/>
                        <input type="hidden" class="vDate" value="{{leaveTime}}"/>
                        <input type="hidden" class="vrDate" value="{{arriveTime}}"/>
                        <input type="hidden" class="vPrice" value="{{price}}"/>
                        <input type="hidden" class="vBuildFee" value="{{airportBuildFee}}"/>
                        <input type="hidden" class="vFuelTax" value="{{additionalFuelTax}}"/>
                        <input type="hidden" class="vPriceId" value="{{hashCode}}"/>
                        <input type="hidden" class="vTrafficId" value="{{trafficHashCode}}"/>
                    </span>
                </div>
            </div>
        </dd>
    </dl>
    <p class="cl"></p>
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
<script src="/js/lvxbang/traffic/flight_fc.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/sort.js" type="text/javascript"></script>
</body>
</html>
