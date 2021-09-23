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
    <jsp:include page="/WEB-INF/jsp/lvxbang/traffic/Order_hc_top.jsp"></jsp:include>

    <div id="noResultLeave" class="dc" style="display: none;margin-top: 10%;">
        <img src="/images/tishix.png" align="absmiddle" class="mr10"> 很抱歉，暂未查询到符合您要求的车次，您可以更换出行时间或目的地重新查询!
    </div>
    <div id="loadingLeave" class="dc" style="color:#666;">
        <img src="/images/loadingx.gif">
        <p class="mt10">
            正在为您加载火车票信息...
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
                        <%--余票--%>
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
            <div id="history" class="Order_history nav">
                <p class="title fs16 b">历史查询</p>
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
        <%--<a href="javaScript:;" class="but fr textC">预订</a>--%>
        <form action="${TRAFFIC_PATH}/lvxbang/traffic/orderSingleFlight.jhtml" onsubmit="return checkSingleFlight(this);" method="post" class="traffic_flight_train">
            <input type="hidden" name="trafficKey" value=""/>
            <input type="hidden" name="singleTrafficId" value="{{trafficHashCode}}"/>
            <input type="hidden" name="singleTrafficPriceId" value="{{hashCode}}"/>
            <%--<div class="w4 fl">{{price}}<a href="#" class="but">预订</a></div>--%>
            <%--<div class="w4 fl">{{price}} <input type="submit" class="but" id="submitButton" value="预订"/></div>--%>
            <%--<div class="w4 fl">--%>
            <%--<input type="button" class="but fr textC" id="submitButton" value="预订" onclick="formToOrderPage(this);"/>--%>
            <%--</div>--%>
            <a href="javaScript:;" class="but fr textC" onclick="formToOrderPage(this);">预订</a>
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

<script type="text/html" id="tpl-traffic-search">
    <li onclick="TrafficCookie.historySearch({{index}})" class="submitSearch">
        {{if searchType == 1}}
        <form id="history-form-{{index}}" action="toTrain.jhtml" method="post" class="display-none">
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
        <form id="history-form-{{index}}" action="toTrain.jhtml" method="post" class="display-none">
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
        <form id="history-form-{{index}}" action="toTrain.jhtml" method="post" class="display-none">
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
<script src="/js/lvxbang/traffic/train_dc.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/sort.js" type="text/javascript"></script>
</body>
</html>
