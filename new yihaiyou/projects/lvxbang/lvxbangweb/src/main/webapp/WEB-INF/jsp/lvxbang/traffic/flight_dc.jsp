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
<body myname="mall" class="Order_hc_dc Order_fj Order_fj_dc">
<!--head  end--><!-- #EndLibraryItem --><!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/traffic/Order_fj_top.jsp"></jsp:include>

    <div id="noResultLeave" class="dc" style="display: none;margin-top: 10%;">
        <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的航班，您可以更换出行时间或目的地重新查询!
    </div>
    <div id="loadingLeave" class="dc" style="margin-top:7%;color:#666;">
        <img src="/images/loadingx.gif">
        <p class="mt10">
            正在为您加载航班信息...
        </p>
    </div>
    <!--中-->
    <div class="w1225 Order_hc_center" id="nav" style="margin-bottom: 70px">
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
            <%--<div class="Order_hc_jg">--%>
            <%--<!--搜索 列表-->--%>
            <%--<div class="searchBox cl ">--%>
            <%--<!--搜索 内容-->--%>
            <%--<div class="select posiR">--%>
            <%--&lt;%&ndash;<p class="more"><a href="javaScript:;" class="posiR"><span>更多查询条件</span><b class="ico"></b></a>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</p>&ndash;%&gt;--%>

            <%--<div class="select_div flight_port" num="1">--%>
            <%--<label>起飞机场</label>--%>
            <%--<ul class="fore sel cond_port" id="conditions_leavePort">--%>
            <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
            <%--</ul>--%>
            <%--<p class="cl"></p>--%>
            <%--</div>--%>
            <%--<div class="select_div flight_company" num="2">--%>
            <%--<label>航空公司</label>--%>
            <%--<ul class="fore sel cond_company" id="conditions_company">--%>
            <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
            <%--</ul>--%>
            <%--<p class="cl"></p>--%>
            <%--</div>--%>
            <%--<div class="select_div flight_seat" num="3">--%>
            <%--<label>舱&nbsp;&nbsp;&nbsp;&nbsp;位</label>--%>
            <%--<ul class="fore sel cond_seat" id="conditions_seat">--%>
            <%--<li><a title="不限" href="javaScript:;" class="checked">不限</a></li>--%>
            <%--<li><a title="经济舱" href="javaScript:;">经济舱</a></li>--%>
            <%--<li><a title="商务舱" href="javaScript:;">商务舱</a></li>--%>
            <%--</ul>--%>
            <%--<p class="cl"></p>--%>
            <%--</div>--%>
            <%--<div class="select_div flight_time" num="4">--%>
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
                        <i class="ioc3 dc-sort-type" id="dc-sort-leave"
                           onclick="Sort.changeSort('Order_fj_list_ul', 'dc-sort-leave', 'sortLeave', 'dc-sort-type', 0)">

                        </i></span>
                    </div>
                    <div class="w3 fl"><span class="posiR">到达<i
                            class="ioc3 dc-sort-type" id="dc-sort-arrive"
                            onclick="Sort.changeSort('Order_fj_list_ul', 'dc-sort-arrive', 'sortArrive', 'dc-sort-type', 1)"></i></span>
                    </div>
                    <div class="w4 fl"><span class="posiR">价格(元)<i
                            class="ioc3 dc-sort-type" id="dc-sort-price"
                            onclick="Sort.changeSort('Order_fj_list_ul', 'dc-sort-price', 'sortPrice', 'dc-sort-type', 2)"></i></span>
                    </div>
                </div>
                <div class="Order_fj_list_div">
                    <ul id="Order_fj_list_ul" class="Order_fj_list_ul">
                    </ul>
                </div>
                <p class="cl"></p>
            </div>
        </div>
        <!--右-->
        <div class="Order_hc_c_fr fr">
            <div id="history" class="Order_history nav">
                <p class="title fs16">历史查询</p>
                <ul class="Order_hc_c_fr_li" id="search-history">

                    <%--<li>--%>
                    <%--<p class="name b"><span>往返</span><label>厦门</label>-<label>上海</label></p>--%>

                    <%--<div class="nr">--%>
                    <%--<div class="nr_fl fl">--%>
                    <%--<p>2015-12-06(12-07 周一)</p>--%>

                    <%--<p>2015-12-09(12-07 周四)</p>--%>
                    <%--</div>--%>
                    <%--<p class="nr_fr textR fr">刚刚查询</p>--%>
                    <%--</div>--%>
                    <%--<p class="cl"></p>--%>
                    <%--</li>--%>
                </ul>
                <p class="cl"></p>
            </div>
        </div>
        <p class="cl"></p>
    </div>
</div>
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
                    <span>{{trafficCode}}</span>
                    <%--<span>{{trafficModel}}</span>--%>
                </div>
            </div>
            <div class="w2 fl">
                <div class="time">
                    <p class="ff_yh">{{leaveTime}}</p>
                    <span>{{leaveTransportation.name}}</span>
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
            <p class="w5 fl fs20 b ff_yh additional_dc">{{sortPrice}}</p>
            <div class="additional_dc" style="text-align: left;">
                <span>燃油:¥{{additionalFuelTax}}</span><br>
                <span>机建:¥{{airportBuildFee}}</span>
            </div>
        </div>
        <div class="Order_fl_list_dl cl posiR" id="traffic_price_{{hashCode}}"
             {{if prices.length
        < 3}}
             style="height: auto"
        {{/if}}>
        </div>
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
            <form action="${TRAFFIC_PATH}/lvxbang/traffic/orderSingleFlight.jhtml"
                  onsubmit="return checkSingleFlight(this);"
                  method="post" class="traffic_flight_train">
                <input type="hidden" name="trafficKey" value=""/>
                <input type="hidden" name="singleTrafficId" value="{{trafficHashCode}}"/>
                <input type="hidden" name="singleTrafficPriceId" value="{{hashCode}}"/>
                <%--<div class="w4 fl">{{price}} <input type="submit" class="but" id="submitButton" value="预订"/></div>--%>
                <div class="w4 fl">{{price}}
                    <input type="button" class="but fr textC" id="submitButton" value="预订"
                           onclick="formToOrderPage(this);"/>
                </div>
            </form>
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

<script type="text/html" id="tpl-traffic-search">
    <li onclick="TrafficCookie.historySearch({{index}})" class="submitSearch">
        {{if searchType == 1}}
        <form id="history-form-{{index}}" action="toFlight.jhtml" method="post" class="display-none">
            <input name="leaveCity" value="{{leaveCity}}"/>
            <input name="leaveCityName" value="{{leaveCityName}}"/>
            <input name="arriveCity" value="{{arriveCity}}"/>
            <input name="arriveCityName" value="{{arriveCityName}}"/>
            <input name="leaveDate" value="{{leaveDate}}"/>
        </form>
        <p class="name b"><span>单程</span><label>{{leaveCityName}}</label>-<label>{{arriveCityName}}</label></p>
        <div class="nr">
            <div class="nr_fl fl">
                <%--<p></p>--%>
                <p>{{leaveDate}}</p>
            </div>
            <p class="nr_fr textR fr" style="margin-top: 0px;">{{stayTime}}</p>
        </div>
        {{/if}}
        {{if searchType == 2}}
        <form id="history-form-{{index}}" action="toFlight.jhtml" method="post" class="display-none">
            <input name="leaveCity" value="{{leaveCity}}"/>
            <input name="leaveCityName" value="{{leaveCityName}}"/>
            <input name="arriveCity" value="{{arriveCity}}"/>
            <input name="arriveCityName" value="{{arriveCityName}}"/>
            <input name="leaveDate" value="{{leaveDate}}"/>
            <input name="returnDate" value="{{returnDate}}"/>
        </form>
        <p class="name b"><span>往返</span><label>{{leaveCityName}}</label>-<label>{{arriveCityName}}</label></p>
        <div class="nr">
            <div class="nr_fl fl">
                <p>{{leaveDate}}</p>

                <p>{{returnDate}}</p>
            </div>
            <p class="nr_fr textR fr">{{stayTime}}</p>
        </div>
        {{/if}}
        {{if searchType == 3}}
        <form id="history-form-{{index}}" action="toFlight.jhtml" method="post" class="display-none">
            <input name="leaveCity" value="{{leaveCity}}"/>
            <input name="leaveCityName" value="{{leaveCityName}}"/>
            <input name="arriveCity" value="{{arriveCity}}"/>
            <input name="arriveCityName" value="{{arriveCityName}}"/>
            <input name="transitCity" value="{{transitCity}}"/>
            <input name="transitCityName" value="{{transitCityName}}"/>
            <input name="leaveDate" value="{{leaveDate}}"/>
            <input name="returnDate" value="{{returnDate}}"/>
        </form>
        <p class="name b"><span>联程</span><label>{{leaveCityName}}</label>-<label>{{transitCityName}}</label>-<label>{{arriveCityName}}</label>
        </p>
        <div class="nr">
            <div class="nr_fl fl">
                <p>{{leaveDate}}</p>

                <p>{{returnDate}}</p>
            </div>
            <p class="nr_fr textR fr">{{stayTime}}</p>
        </div>
        {{/if}}

        <p class="cl"></p>
    </li>
</script>
<script src="/js/lvxbang/traffic/trafficCookie.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/common.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/flight_dc.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/sort.js" type="text/javascript"></script>
</body>
</html>
