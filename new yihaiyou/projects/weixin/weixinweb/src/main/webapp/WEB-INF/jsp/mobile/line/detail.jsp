<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>${line.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/line/plan.detail.css?${mallConfig.resourceVersion}">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/calendar.css?${mallConfig.resourceVersion}">
    <jsp:include page="/WEB-INF/jsp/mobile/common/baiduCount.jsp"></jsp:include>
</head>
<body class="rcd-body">
<%@ include file="/WEB-INF/jsp/mobile/common/loading.jsp" %>
<input type="hidden" value="${empty CURRENT_LOGIN_USER_MEMBER ? '0' : '1' }" id="J_userInfo"/>
<input type="hidden" value="${priceDateId}" id="priceDateId">
<input type="hidden" value="${priceTypeId}" id="priceTypeId">
<input type="hidden" id="planId" value="${line.id}"/>
<input type="hidden" id="img_url" value="<c:if test="${line.productimage.size()>0}"> ${line.productimage.iterator().next().path}</c:if>"/>
<div class="plan-detail">
    <div class="header-wrap">
        <a class="left-btn" href="javascript:history.go(-1)"></a>
        <h1 class="pageTitle">${line.name}</h1>
    </div>
    <div class="main-wrap">
        <!-- 行程总览 -->
        <div class="plan-summary auto-height-panel cf" id="J_plan_summary">
            <div class="cont">
                <div class="guidei-title">
                    <span class="title-name">行程总览</span>
                </div>
                <div class="guide-wrap auto-height-panel1">
                    <ul class="guidei-first-menu" id="J_summary-list"></ul>
                </div>
                <a class="sum-arrow" onclick="hideSummary();"></a>
            </div>
            <div class="transparent" onclick="hideSummary();"></div>
        </div>
        <div class="plan-title cf">
            <span class="title-span time">行程共<span class="plan-total-dayCount blue">${line.playDay}</span>天</span>
            <span class="fl">|</span>
            <span class="title-span count"><span class="plan-total-scenicCount blue"></span>个景点</span>
            <span class="fl">|</span>
				<span class="title-span price cost-detail-span" onclick="showCostDetail(this);">线路价格
                    <span class="red">￥</span>
                    <span class="plan-total-cost red">
                        <c:if test="${priceDateId!=null && priceDateId>0}">
                            <c:forEach items="${line.linetypeprices}" var="price">
                                <c:forEach items="${price.linetypepricedate}" var="datePrice">
                                    <c:if test="${datePrice.id == priceDateId}">
                                        ${datePrice.discountPrice}
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </c:if>

                    </span><i class="down"></i>
				</span>
        </div>
        <div class="plan-title cf start-time-wrap">
            <span class="left-tag"></span>
            <span class="title-span time">
                出发日期：
                <span class="plan-total-dayCount blue">
                     <c:if test="${priceDateId!=null && priceDateId>0}">
                         <c:forEach items="${line.linetypeprices}" var="price">
                             <c:forEach items="${price.linetypepricedate}" var="datePrice">
                                 <c:if test="${datePrice.id == priceDateId}">
                                     <a href="/mobile/line/date.jhtml?lineId=${lineId}&priceTypeId=${priceTypeId}">
                                         <fmt:formatDate value="${datePrice.day}" pattern="yyyy-MM-dd"></fmt:formatDate>
                                     </a>
                                 </c:if>
                             </c:forEach>
                         </c:forEach>
                     </c:if>
                </span>
            </span>
        </div>
        <div class="detail-wrap">
            <ul class="everyDay-ul">
                <c:forEach items="${line.lineexplain.linedays}" var="daytrips" varStatus="dayIndex">
                    <li id="day-${dayIndex.index + 1}" class="every-day">
                        <div class="title msg-box cf">
                            <c:if test="${!empty daytrips.linedaysplan}">
                                <a class="map-btn blue" href="/plan/dayMap?planId=${line.id}&dayId=${daytrips.id}&cityName=<c:if test="${daytrips.linedaysplan.size()>0}">${daytrips.linedaysplan.iterator().next().cityId}</c:if>&dayIndex=${daytrips.lineDay}">行程图</a>
                            </c:if>
                            <p>第${dayIndex.index + 1}天：
                                <%--<c:if test="${daytrips.cityCode>0}">${lc:cityDict(daytrips.cityCode)}</c:if>--%>
                            </p>
                            <c:choose>
                                <c:when test="${daytrips.planTime==0}">
                                    <span class="max-span">游玩<span class="about-time blue">${daytrips.planTime/60}</span>小时</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="max-span">游玩<span class="about-time blue"><fmt:formatNumber type="number" value="${daytrips.planTime/60}" maxFractionDigits="1"/></span>小时</span>
                                </c:otherwise>
                            </c:choose>
                            <%--<span class="fl ml5">|</span>--%>
                            <%--<span class="max-span">--%>
                                <%--&lt;%&ndash;<span class="day-scenic-count blue">${daytrips.scenicCount}</span>个景点&ndash;%&gt;--%>
                            <%--</span>--%>
                            <%--<span class="fl ml5">|</span>--%>
								<%--&lt;%&ndash;<span class="cost-detail-span max-span" onclick="showCostDetail(this);">大约花费<span class="red">￥</span><span class="about-price red">${daytrips.ticketCost+daytrips.hotelCost+daytrips.trafficCost}</span><i class="down small"></i>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<div class="cost-detail hide">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<i></i>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<span class="cost-span">住宿<span class="red">￥</span><span class="day-hotel-cost red">${daytrips.hotelCost}</span></span>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<span class="cost-span ml20">门票<span class="red">￥</span><span class="day-ticket-cost red">${daytrips.ticketCost}</span></span>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<span class="cost-span ml20">交通<span class="red">￥</span><span class="day-travel-cost red">${daytrips.trafficCost}</span></span>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</span>&ndash;%&gt;--%>
                        </div>
                        <c:set var="key" value="day${daytrips.lineDay}"></c:set>

                        <c:choose>
                            <c:when test="${empty daytrips.linedaysplan}">
                                <div class="scenic-empty">
                                    <span class="empty-tips">当天暂无行程安排！</span>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <ul class="scenic-items-wrap">
                                    <c:if test="${!empty daytrips.hotelId}">
                                        <li class="item-signel item-hotel" scenicIds="${daytrips.hotelId}"
                                            <%--todo: should fill with real hotel coordinate--%>
                                            <%--lng="${daytrips.hotel.longitude}" lat="${daytrips.hotel.latitude}"--%>
                                            sname="${daytrips.hotelName}" tripType="3" trafficType="1">
                                            <div class="item-body">
                                                <div class="hotel-wrap">
                                                    <div class="top-title">
                                                        <span class="left-tag"></span>
                                                        <span class="title-name">${daytrips.hotelName}</span>
                                                        <%--<a class="right-btn" href="http://u.ctrip.com/union/CtripRedirect.aspx?TypeID=2&Allianceid=110730&sid=555035&OUID=&jumpUrl=http://hotels.ctrip.com/hotel/${trips.detail.id}.html">--%>
                                                            <%--<c:if test="${daytrips.hotel.lowestHotelPrice>0}">--%>
                                                                <%--<span>￥<fmt:formatNumber type="number" value="${daytrips.hotel.lowestHotelPrice}" pattern="#"/></span>|<span>预订</span>--%>
                                                            <%--</c:if>--%>
                                                            <%--<c:if test="${daytrips.hotel.lowestHotelPrice==0 || empty daytrips.hotel.lowestHotelPrice}">--%>
                                                                <%--<span>暂无报价</span>|<span>预订</span>--%>
                                                            <%--</c:if>--%>
                                                        <%--</a>--%>
                                                    </div>
                                                    <div class="mid-img-cont">
                                                        <div class="mid-img-wrap">
                                                            <%--<img class="lazy" onclick="showImg();" data-original="${daytrips.hotel.imgUrl}" bigsrc="${daytrips.hotel.imgUrl)" src="/static/image/transparent-bg.png"/>--%>
                                                            <%--<p class="img-text"><c:if test="${empty daytrips.hotel.addr}">暂无地址信息</c:if>${daytrips.hotel.addr}</p>--%>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <ul class="more-detail">
                                                <li class="scenic-detail" onclick="location.href='/hotel/detail/${daytrips.hotelId}'">酒店详情</li>
                                                <%--todo: fill in "howGo" with real data --%>
                                                <li class="how-go" onclick="howGo('lng','lat','name','address');">到这里去</li>
                                                <li class="more-func" onclick="showMore(this)">更多功能
                                                    <%--<div class="more-func_div hide">--%>
                                                        <%--<a class="more-bg"></a>--%>
                                                        <%--<ul class='more-list'>--%>
                                                                <%--&lt;%&ndash;  <c:if test="${_CURRENT_MEMBER.id==allTripList.userId}">--%>
                                                                     <%--<li class="nav-li"><a class="nav-a del" onclick="delScenicTips(this);">删除酒店</a></li>--%>
                                                                 <%--</c:if> &ndash;%&gt;--%>
                                                            <%--<li class="nav-li"><a class="nav-a hotel" href="/vicinity/hotel?lat=${daytrips.hotel.gcjLat}&lng=${daytrips.hotel.gcjLng}&cityCode=${daytrips.hotel.cityCode}">周边酒店</a></li>--%>
                                                            <%--<li class="nav-li"><a class="nav-a food" href="/vicinity/food?lat=${daytrips.hotel.gcjLat}&lng=${daytrips.hotel.gcjLng}&cityCode=${daytrips.hotel.cityCode}">周边美食</a></li>--%>
                                                            <%--<li class="nav-li"><a class="nav-a scenic" href="/vicinity/scenic?lat=${daytrips.hotel.gcjLat}&lng=${daytrips.hotel.gcjLng}&cityCode=${daytrips.hotel.cityCode}">周边景点</a></li>--%>
                                                            <%--<li class="nav-li"><a class="nav-a more-func">更多功能</a></li>--%>
                                                        <%--</ul>--%>
                                                    <%--</div>--%>
                                                </li>

                                            </ul>
                                            <div class="traffic-panel">
                                            </div>
                                        </li>
                                    </c:if>
                                    <c:forEach items="${daytrips.linedaysplan}" var="trips" varStatus="scenicIndex">
                                        <c:if test="${trips.type=='scenic'}">
                                            <li class="item-signel item-scenic" scenicIds="${trips.typeId}"
                                                lng="${trips.scenicInfo.longitude}" lat="${trips.scenicInfo.latitude}"
                                                sname="${trips.typeName}" tripType="${trips.type}"
                                                <%--pryid="${trips.detail.firstTicketId}" prytype="${trips.detail.firstTicketSource}"--%>
                                                    >
                                                <a name="_${dayIndex.index + 1}_${trips.scenicInfo.id}" class="point-a"></a>
                                                <div class="item-body">
                                                    <div class=" scenic-wrap">
                                                        <div class="top-title">
                                                            <span class="left-tag scenic-index">${scenicIndex.index + 1}</span>
                                                            <span class="title-name">${trips.typeName}</span>
                                                        </div>
                                                        <div class="mid-img-cont">
                                                            <div class="mid-img-wrap">
                                                                <img class="lazy" onclick="showImg();" data-original="${trips.scenicInfo.coverMedium}" bigsrc="${trips.scenicInfo.coverLarge}" src="/static/image/transparent-bg.png"/>
                                                                <c:if test="${!empty trips.scenicInfo.adviceTime}">
                                                                    <p class="img-text">建议游玩${trips.scenicInfo.adviceTime}</p>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <ul class="more-detail cf">
                                                    <li class="scenic-detail" onclick="scenicDetail(${trips.scenicInfo.id});">景点详情</li>
                                                    <li class="how-go" onclick="howGo(${trips.scenicInfo.gcjLng}, ${trips.scenicInfo.gcjLat}, '${trips.scenicInfo.name}', '${trips.scenicInfo.address}');">到这里去</li>
                                                    <li class="more-func" onclick="showMore(this)">更多功能
                                                        <%--<div class="more-func_div hide">--%>
                                                            <%--<a class="more-bg"></a>--%>
                                                            <%--<ul class='more-list'>--%>
                                                                    <%--&lt;%&ndash; <c:if test="${_CURRENT_MEMBER.id==allTripList.userId}">--%>
                                                                        <%--<li class="nav-li"><a class="nav-a del" onclick="delScenicTips(this);">删除景点</a></li>--%>
                                                                    <%--</c:if> &ndash;%&gt;--%>
                                                                <%--<li class="nav-li"><a class="nav-a hotel" href="/vicinity/hotel?lat=${trips.detail.gcjLat}&lng=${trips.detail.gcjLng}&cityCode=${daytrips.cityCode}">周边酒店</a></li>--%>
                                                                <%--<li class="nav-li"><a class="nav-a food" href="/vicinity/food?lat=${trips.detail.gcjLat}&lng=${trips.detail.gcjLng}&cityCode=${daytrips.cityCode}">周边美食</a></li>--%>
                                                                <%--<li class="nav-li"><a class="nav-a scenic" href="/vicinity/scenic?lat=${trips.detail.gcjLat}&lng=${trips.detail.gcjLng}&cityCode=${daytrips.cityCode}">周边景点</a></li>--%>
                                                                <%--<li class="nav-li"><a class="nav-a more-func">更多功能</a></li>--%>
                                                            <%--</ul>--%>
                                                        <%--</div>--%>
                                                    </li>

                                                </ul>
                                            </li>
                                        </c:if>
                                        <c:if test="${trips.type=='restaurant'}">
                                            <%--todo: should be restore when there is restaurant --%>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
            <div class="load-all">已加载全部内容</div>
        </div>
    </div>
    <div class="footer-wrap cf">
        <div class="cf">
            <div class="btn-wrap order-btn">
                <a class="posts-btn">下单</a>
            </div>
            <%--<div  class="btn-wrap  fl">--%>
                <%--<a class="route-btn disable"><i></i>0人有类似路线</a>--%>
            <%--</div>--%>
        </div>
    </div>
    <%-- <a class="publish-btn" href="/friends/post/${allTripList.id}">约伴互助</a> --%>
    <%--<a class="publish-btn" href="/?type=2">我的行程</a>--%>
    <%--<a class="summary-btn" onclick="showSummary();">行程单</a>--%>
</div>
<div class="dayInfoMap hide">
    <div id="J_now-map"></div>
</div>

<!-- 日历控件 -->
<div class="calendar_div"></div>

<!-- 旅行贴士 -->
<script type="text/html" id="tpl-travel-tips">
    <div class="travel-tips" style="margin-top: -40%;">
        <div class="pop-title green">旅行贴士</div>
        <div class="pop-content" style="max-height: 200px; overflow: auto; -webkit-overflow-scrolling: touch;">
            <label class="label-wrap ui-confirm-msg"><p><c:if test="${empty allTripList.tipsContent}">作者暂时未编写旅行贴士~</c:if>${allTripList.tipsContent}</p></label>
        </div>
        <a onclick="returnDetail();">关闭</a>
    </div>
</script>

<!-- 推荐套票 -->
<script type="text/html" id="tpl-rmd-sticket">
    <li>
        <a id="season_day{{day}}_{{i}}" class="hidden_hash"></a>
        {{if i==1}}
        <i class="session-ticket"></i>
        {{/if}}
        <span class="rctitle green">套票{{i}}推荐</span>
        <div>
            <span>当天行程包含{{includeNames}}等景点，小帮智能推荐如下套票，为您节省{{saveAmount}}元</span>
        </div>
    </li>
    <li>
        <div class="sticket-des">
            <span>{{seasonTicketName}}（含{{scenicNames}}等）</span>
            <a class="right-btn" href="/order/write/qunar/{{seasoTicketId}}">
                <span>￥{{seasonTicketPrice}}</span>|<span>预订</span>
            </a>
        </div>
    </li>
</script>

<!-- 行程总览 -->
<script type="text/html" id="tpl-summary-scenics">
    {{each days}}
    <li class="first-menu-cont">
        <a onclick="showScenic({{$value.day}});" class="which-day">第<i>{{$value.day}}</i>天</a>
        <ul class="guidei-second-menu">
            {{each $value.scenics as trips index}}
            <li onclick="showScenic({{$value.day}}, {{index}});"><a>{{trips.name}}</a></li>
            {{/each}}
        </ul>
    </li>
    {{/each}}
    <li class="first-menu-cont"><span class="which-day">The End</span></li>
</script>

<%@ include file="/WEB-INF/jsp/mobile/common/common-script.jsp" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/simple.ui.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/calendar.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/mobile/plan/plan.detail.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/mobile/plan/plan.baiduroute.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/map_util.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js?${mallConfig.resourceVersion}"></script>

<%@ include file="/WEB-INF/jsp/mobile/common/imageshow.jsp" %>

</body>
</html>