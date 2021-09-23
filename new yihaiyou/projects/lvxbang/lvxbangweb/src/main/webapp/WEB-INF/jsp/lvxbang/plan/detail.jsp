<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${plan.name}-旅游线路_旅行帮</title>
    <meta name="keywords" content="${plan.name}，${plan.city.name}旅游线路，${plan.city.name}旅行线路，${plan.city.name}旅游路线，${plan.city.name}旅行路线"/>
    <meta name="description" content="${plan.name}，更多旅游线路尽在旅行帮"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/detail.css" rel="stylesheet" type="text/css">
    <link href="/css/plan.map.css" rel="stylesheet" type="text/css">
</head>
<body myname="TripPlanning" class="Itinerary">
<input type="hidden" id="completeFlag" value="${plan.completeFlag}"/>
<%--<input type="hidden" id="planId" value="${plan.id}"/>--%>
<form action="${PLAN_PATH}/lvxbang/plan/booking.jhtml" method="post" id="booking-form">
    <input type="hidden" value="true" id="newOne"/>
    <input type="hidden" value="" name="json" id="booking-form-json"/>
</form>
<input type="hidden" id="startDate" value="<fmt:formatDate value="${plan.startTime}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
<!-- 二维码-->
<div class="similar">

   <div class="similar_div">
       <input type="hidden" id="code" value="${MOBILE_PATH}/mobile/index/indexIndex.jhtml?route=/xing-xianlu-info/${plan.id}">
       <div class="similar_wx mb10 textC">
           <b class="color6 fs14">同步至手机</b>

           <p id="toMobile"></p>
       </div>
       <%--<i class="similar_i fl mr15"></i>--%>

       <%--<p class=" similar_p b fl mt20">--%>
           <%--<i class="similar_i2 fl"></i>--%>
           <%--<label class="oval4 fl disB">你还可以<a href="#">查看相似行程</a></label>--%>
       <%--</p>--%>
   </div>
</div>

<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>

<div class="main cl mt10">
    <div class="w1000">
        <div class="color6 day">
            <dl class="day_dl">
                <c:forEach items="${plan.planDayList}" var="planDay" varStatus="status">
                    <dd class="label${planDay.days} <c:if test="${status.index==0}"> checked</c:if>">
                        <span>第${planDay.days}天</span><i></i>

                        <p></p>
                    </dd>
                </c:forEach>
                <dd class="label${plan.planDays+1}">
                    <span>行李清单</span><i></i>
                </dd>
            </dl>
        </div>

        <!--n_title-->
        <p class="n_title h40 lh40" style="display: none;">
            您在这里：&nbsp;
            <a href="${DESTINATION_PATH}">私人定制</a>
            &nbsp;&gt;&nbsp;<a href="${DESTINATION_PATH}/lvxbang/plan/edit.jhtml">线路编辑</a>
            &nbsp;&gt;&nbsp;<a href="${DESTINATION_PATH}/lvxbang/plan/booking.jhtml?planId=${plan.id}">自由行</a>
            <%--&nbsp;&gt;&nbsp;<a href="${RECOMMENDPLAN_PATH}/guide_list.html?cityIds=${plan.city.cityCode}">${plan.city.name}线路</a>--%>
            &nbsp;&gt;&nbsp;${plan.name}
        </p>

            <div class="detail_top posiR">
                <p class="img fl">
                    <c:if test="${plan.user.head==null || plan.user.head==''}">
                        <img src="/images/portrait2.jpg" style="display: inline;" alt="头像">
                    </c:if>
                    <c:if test="${plan.user.head!=null && plan.user.head!='' && fn:indexOf(plan.user.head,'http://') < 0}">
                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.user.head}?imageView2/1/w/48/h/48" style="display: inline;" alt="头像">
                    </c:if>
                    <c:if test="${plan.user.head!=null && plan.user.head!='' && fn:indexOf(plan.user.head,'http://') > -1}">
                        <img src="${plan.user.head}" style="display: inline;" alt="头像">
                    </c:if>
                </p>

                <div class="fl ml10 nr">
                    <b class="name">${plan.name}</b>
                    <c:if test="${plan.user.id == CURRENT_LOGIN_USER_MEMBER.id}">
                        <a style="position: absolute; top: 8px;" href="javascript:;" class="change_name"></a>
                        <div class="name_div" style="display: none; margin-top: 4px; margin-bottom: 4px; border: 1px solid #dcdcdc;">
                           <input class="name_input" style="color: #333; font-size: 14px; font-weight: bold; font-family: '宋体'; margin-top: 2px; margin-bottom: 2px; width: 100%; outline: none;" value="${plan.name}">
                        </div>
                        <div style="display: none; position: absolute; top: 3px;" class="name_button">
                            <a class="name_button_ok" href="javascript:;" style="color: #fff; width: 45px; height: 25px; text-align: center; line-height: 25px; font-size: 12px; background: #38bc82; padding: 5px;">确定</a>
                            <a class="name_button_cancle" href="javascript:;" style="color: #fff; width: 45px; height: 25px; text-align: center; line-height: 25px; font-size: 12px; background: #ccc; padding: 5px;">取消</a>
                        </div>
                    </c:if>
                    <p class="time">规划师：<span class="ml10">${plan.user.nickName}</span></p>
                </div>
                <c:if test="${plan.user.id == CURRENT_LOGIN_USER_MEMBER.id && recommendPlan == null}">
                <%--<a href="#" class="oval4 but fr b">保存行程</a>--%>
                <a href="${PLAN_PATH}/lvxbang/plan/edit.jhtml?planId=${plan.id}" class="oval4 mr20 l_line fr b"><i></i>修改线路</a>

                <form action="${INDEX_PATH}/lvxbang/order/orderPlan.jhtml" method="post" id="booking-order">
                    <input type="hidden" id="planId" class="planId" name="planId" value="${planId}">
                    <input type="hidden" id="json-data" name="data" value='${json}'>
                    <a href="javascript:void 0" class="oval4 mr20 o_line fr b buy-plan"><i></i>购买线路</a>
                </form>
                </c:if>
            </div>

        <div class="cl">
            <!--d_banner-->
            <div class="d_banner posiR fl TripPlanning_banner">
                <c:if test="${fn:indexOf(plan.coverPath, 'http') == 0}">
                <img src="${plan.coverPath}" alt="${plan.name}"/>
                </c:if>
                <c:if test="${fn:indexOf(plan.coverPath, 'http') != 0}">
                <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}?imageView2/1/w/680/h/400" alt="${plan.name}"/>
                </c:if>

                <div class="d_banner_d posiA">
                    <%--<p class="name ff_yh b">${plan.name}</p>--%>
                    <%--<div class="cl lh20">来自游记《最美遇见你——厦门、鼓浪屿、曾厝安、南昌5日闺蜜行》--%>
                        <%--<br><span class="mr20">作者：屎壳郎情调要不要</span>2014-08-06 14:24--%>
                    <%--</div>--%>
                    <ul class="service_u fr mt10">
                        <li class="checked"><i></i><span>浏览：${plan.planStatistic.viewNum == null?0:plan.planStatistic.viewNum}</span></li>
                        <li class="collectNum"><i class="ico3"></i>收藏：<span>${plan.planStatistic.collectNum == null?0:plan.planStatistic.collectNum}</span></li>
                        <li><i class="ico2"></i><span>复制：${plan.planStatistic.quoteNum == null?0:plan.planStatistic.quoteNum}</span></li>
                    </ul>
                </div>


            </div>

            <script type="text/javascript"
                    src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
            <div class="map-container fr whole-map">
                <a href="#" class="close"><i></i></a>
                <div class="fl" style="width:310px;height:398px;
                /*border:#ccc solid 1px;*/
                " id="baidu-map-main"></div>
                <div class="to-bigger-button">
                    查看大地图
                </div>
                <div class="hqxqdtx_fr fl">
                    <p class="title fs16">线路路线</p>
                    <p class="alltravel checked"><i></i>全部线路</p>
                    <ul id="edit-map-day-panel">
                        <c:forEach items="${plan.planDayList}" var="planDay" varStatus="status">
                            <li>
                                <div class="jieshao posiR">
                                    <i></i>
                                    <p class="name"><span>第${status.index + 1}天</span>${planDay.city.name}</p>
                                    <p class="nr">
                                        <c:forEach items="${planDay.planTripList}" var="planTrip" varStatus="tripStatus">
                                            <c:if test="${planTrip.tripType == 1}">
                                                <c:if test="${tripStatus.index!=0}">--</c:if>
                                                <span class="plan-map-scenic" data-id="${planTrip.scenicInfo.id}">
                                                    ${planTrip.scenicInfo.name}
                                                </span>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${planDay.hotel != null}">
                                            --
                                            <span class="plan-map-scenic" data-id="${planDay.hotel.id}-${status.index + 1}">
                                                    ${planDay.hotel.name}
                                            </span>
                                        </c:if>
                                    </p>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <!-- 地图-->
        </div>
        <p class="h20 cl"></p>
    </div>

    <!--行程概括-->
    <div class="h50 mb20 cl" id="nav" style="margin-bottom: 20px;">
        <div class="nav d_select">
            <div class="w1000 posiR">
                <dl class="d_select_d fl mr40 fs16 b">
                    <dd class="span1 checked">线路概括</dd>
                    <dd class="span2">线路详情</dd>
                </dl>
                <c:if test="${plan.user.id != CURRENT_LOGIN_USER_MEMBER.id}">
                <a href="javascript:;" class="d_stroke choose fr mt10 mr20" data-id="${plan.id}" style="color: #4cb7f5;"><i style="background-position-y: -295px;"></i><b style="letter-spacing: 1pt;">复制线路</b><br/>${plan.planStatistic.quoteNum == null?0:plan.planStatistic.quoteNum}
                    <div class="posiR disn" style="display: block;">
                        <div class="posiA tishi2" style="display: block;">
                            <p class="tishi_p2  fs13 cl posiR"><s></s>复制线路后，添加机票、酒店即可生成自己的特色线路。 </p>
                        </div>
                    </div>
                </a>
                <a href="javascript:;" class="d_collect choose fr mt10 mr20 favorite"
                   data-favorite-id="${plan.id}" data-favorite-type="plan"><i></i>收藏<br/></a>
                </c:if>
                <c:if test="${recommendPlan != null && recommendPlan.status == 2}">
                    <a href="${RECOMMENDPLAN_PATH}/guide_detail_${recommendPlan.id}.html" class="but2 b fl mt15 fs16">查看游记详情 &gt;></a>
                </c:if>
                <c:if test="${plan.user.id == CURRENT_LOGIN_USER_MEMBER.id && recommendPlan != null && recommendPlan.status == 1}">
                    <a href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/preview.jhtml?recplanId=${recommendPlan.id}" class="but2 b fl mt15 fs16">查看游记详情 &gt;></a>
                </c:if>
            </div>
        </div>
    </div>

    <!--行程概括-->
    <div class="w1000 general" id="id1">
        <dl class="color6 general_dl">
            <c:forEach items="${plan.planDayList}" var="planDay" varStatus="status">
                <dd <c:if test="${status.index>2}">style="display: none;"</c:if>>
                    <p class="title b"><i></i><span class="mr20">第${planDay.days}天</span>${planDay.city.name}</p>
                    <c:if test="${planDay.traffic != null}">
                        <p class="nr">
                            <b class="mr20">交通</b>${planDay.traffic.leaveCity.name}
                            至 ${planDay.traffic.arriveCity.name} ${planDay.traffic.trafficCode}
                        </p>
                    </c:if>
                    <c:if test="${planDay.hotel != null}">
                        <p class="nr"><b class="mr20">住宿</b>${planDay.hotel.name}</p>
                    </c:if>

                    <p class="nr">
                        <b class="mr20">景点</b><c:forEach items="${planDay.planTripList}" var="planTrip" varStatus="status"><c:if test="${status.index!=0}"> -- </c:if>${planTrip.scenicInfo.name}</c:forEach>
                    </p>
                    <c:if test="${planDay.returnTraffic != null}">
                        <b class="mr20">交通</b>${planDay.returnTraffic.leaveCity.name}
                        至 ${planDay.returnTraffic.arriveCity.name} ${planDay.returnTraffic.trafficCode}
                    </c:if>
                </dd>
            </c:forEach>
        </dl>
        <c:if test="${plan.planDayList.size()>3}">
        <a href="javaScript:;" class="more mt10 disB posiR b cl"><i></i>展开</a>
        </c:if>
    </div>

    <!--行程详情-->
    <div class="w1000 cl xcxq" id="id2">
        <p class="title ff_yh">线路详情</p>
        <c:forEach items="${plan.planDayList}" var="planDay" varStatus="status">
            <div class="xcxq_div">
                <div class="top posiR" id="idx${planDay.days}">
                    <span class="posiA xcxq_s ff_yh b">D${planDay.days}</span>
                            <span class="name fl ff_yh">
                                <c:if test="${status.index == 0}">
                                    <c:if test="${plan.startCity.name != planDay.city.name}">
                                        <c:if test="${plan.startCity.name != null && plan.startCity.name != '' }">
                                            ${plan.startCity.name} >
                                        </c:if>
                                    </c:if>
                                    ${planDay.city.name}
                                </c:if>
                                <c:if test="${status.index != 0}">
                                    <c:if test="${plan.planDayList[status.index-1].city.name != planDay.city.name}">
                                        ${plan.planDayList[status.index-1].city.name}>
                                    </c:if>
                                    ${planDay.city.name}
                                </c:if>

                            </span>

                    <div class="add fl today-map" data-day="${planDay.days}"><i class="d_ico disB d_xq11 fl mr5"></i><span class="fl" >今日地图</span></div>
                </div>
                <%--todo:没有数据这里--%>
                <%--<div class="center posiR">--%>
                    <%--<i class="posiA d_ico disB  d_xq1"></i>--%>

                    <%--<div class="zd cl">--%>
                        <%--<label class="b">今日重点：</label>--%>
                        <%--这个地方也是没有内容的，要怎么搞(?)--%>
                        <%--可以说我最爱的是黄胜记肉松吗~\(≧▽≦)/~因为本身爱吃猪肉脯啊牛肉丝之类的东西，到了黄胜记简直是天堂，因为可以不付钱免费试吃只要面子够厚完全可以吃到撑再默默撤人，哈哈，不好意思的说我真的吃到撑才停口，不过还是买了满满几大包回家啦~--%>
                    <%--</div>--%>
                <%--</div>--%>
                <c:if test="${planDay.traffic != null}">
                    <div class="bottom posiR">
                        <i class="posiA d_ico disB  d_xq2"></i>
                        <label class="fl name disB"></label>
                        <ul class="oval4 jt_ul fl">
                            <li class="li_w1" style="line-height: 76px;margin-right: 6px;">
                                <b>${planDay.traffic.leaveCity.name}</b>
                                <c:if test="${planDay.traffic.trafficType == 'AIRPLANE'}">
                                    <i class="d_ico disB fl d_xq8" style="margin: 27px 12px 0;"></i>
                                </c:if>
                                <c:if test="${planDay.traffic.trafficType == 'TRAIN'}">
                                    <i class="d_ico disB fl d_xq12" style="margin: 27px 12px 0;"></i>
                                </c:if>
                                <b>${planDay.traffic.arriveCity.name}</b>
                            </li>
                            <li class="li_w2" style="margin-top: 17px">
                                <b>线路</b>
                                <c:if test="${planDay.traffic.trafficType == 'AIRPLANE'}">
                                    <p>起飞 ${planDay.traffic.leaveTime} ${planDay.traffic.leaveTransportation.name}<br/>
                                        降落 ${planDay.traffic.arriveTime} ${planDay.traffic.arriveTransportation.name} </p>
                                </c:if>
                                <c:if test="${planDay.traffic.trafficType == 'TRAIN'}">
                                    <p>出发 ${planDay.traffic.leaveTime} ${planDay.traffic.leaveTransportation.name}<br/>
                                       到达 ${planDay.traffic.arriveTime} ${planDay.traffic.arriveTransportation.name} </p>
                                </c:if>
                            </li>
                            <li class="li_w3" style="margin-top: 17px;margin-left: 10px;margin-right: -17px;">
                                <c:if test="${planDay.traffic.trafficType == 'AIRPLANE'}">
                                    <b>班次</b><p>${planDay.traffic.company}<br/>航班${planDay.traffic.trafficCode}</p>
                                </c:if>
                                <c:if test="${planDay.traffic.trafficType == 'TRAIN'}">
                                    <b>车次</b><p style="line-height: 30px;">${planDay.traffic.trafficCode}</p>
                                </c:if>
                            </li>
                            <li class="li_w4" style="margin-top: 17px">
                                <b>用时</b>

                                <p><fmt:formatNumber type="number" value="${planDay.traffic.flightTime/60}"
                                                     maxFractionDigits="0"/>小时
                                        ${planDay.traffic.flightTime%60}分</p>
                            </li>
                            <li class="li_w5" style="margin-top: 17px">
                                <b>价格</b>

                                <p>¥${planDay.trafficCost}</p>
                            </li>
                        </ul>
                        <p class="cl"></p>
                    </div>
                </c:if>
                <p class="cl"></p>
            </div>

            <div class="oval4 xcxq_xq day-node">
                <c:forEach items="${planDay.planTripList}" var="planTrip" varStatus="tripStatus">
                    <c:if test="${tripStatus.index != 0}">
                        <div class="xcxq_xq_div_cl b">
                           <c:if test="${planTrip.travelMileage != null && planTrip.travelMileage != ''}">
                               两地相距约
                               <c:if test="${planTrip.travelMileage >= 1000}">
                                   <fmt:formatNumber type="number" value="${planTrip.travelMileage / 1000}" maxFractionDigits="0"/>
                                   <span style="color: #b9b9b9">km</span>
                               </c:if>
                               <c:if test="${planTrip.travelMileage < 1000}">
                                   ${planTrip.travelMileage}
                                   <span style="color: #b9b9b9">m</span>
                               </c:if>
                           </c:if>
                        </div>
                    </c:if>
                    <div class="xcxq_xq_div trip-node"
                         lng="${planTrip.scenicInfo.scenicGeoinfo.baiduLng}"
                         lat="${planTrip.scenicInfo.scenicGeoinfo.baiduLat}"
                         tid="${planTrip.scenicInfo.id}"
                         tname="${planTrip.scenicInfo.name}"
                         taddress="${planTrip.scenicInfo.scenicOther.address}"
                         tprice="${planTrip.scenicInfo.price}"
                         tcomment="${planTrip.scenicInfo.scenicOther.recommendReason}"
                         tcover="${planTrip.scenicInfo.cover}"
                         tstar="${planTrip.scenicInfo.star}"
                            >
                        <i class="i_btop"></i>
                        <i class="i_bottom"></i>

                        <div class="fl xcxq_xq_fl">
                            <p class="name"><span>第${tripStatus.count}站</span>${planTrip.scenicInfo.name}</p>
                            <ul>
                                <%--<li><i class="d_ico disB d_xq3 fl mr10"></i>--%>

                                    <%--<div class="xcxq_xq_fl_div fl">--%>
                                        <%--<label>作者贴士：这个不需要吧（？）</label>--%>

                                        <%--<p class="b"><span>“</span>鼓浪屿上没有交通工具，完全是步行~所以准备好脚力要慢慢逛哟~<span>”</span></p>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <li><i class="d_ico disB d_xq4 fl mr10"></i>

                                    <div class="xcxq_xq_fl_div fl">
                                        <label>用时参考：${planTrip.scenicInfo.scenicOther.adviceTimeDesc}</label></div>
                                </li>
                                <li><i class="d_ico disB d_xq5 fl mr10"></i>

                                    <div class="xcxq_xq_fl_div fl">
                                        <label>地址：${planTrip.scenicInfo.scenicOther.address}</label></div>
                                </li>
                                <c:if test="${planTrip.scenicInfo.ticket!=null}">
                                    <li class="posiR"><i class="d_ico disB d_xq6 fl mr10"></i>

                                        <div class="xcxq_xq_fl_div fl">
                                            <label class="disB fl">门票：</label>

                                            <div class="scenic-li fl">${planTrip.scenicInfo.ticket}</div>
                                        </div>
                                    </li>
                                </c:if>
                                <c:if test="${planTrip.scenicInfo.scenicOther.description!=null}">
                                    <li class="posiR"><i class="d_ico disB d_xq7 fl mr10"></i>

                                        <div class="xcxq_xq_fl_div fl ">
                                            <label class="disB fl">简介：</label>

                                            <div class="scenic-li fl ">${planTrip.scenicInfo.scenicOther.description}
                                            <%--${fn:substring(planTrip.scenicInfo.scenicOther.description,0,120)}<span class='ellipsis'>...<br/></span>--%>
                                            <%--${planTrip.scenicInfo.scenicOther.description}--%>
                                            </div>
                                            <%--<div class="description" style="width: 470px;position: relative;line-height: 20px;height: auto;word-wrap: break-word;word-break: normal;display: none;margin-left: 38px;">--%>
                                                   <%--${planTrip.scenicInfo.scenicOther.description}--%>
                                            <%--</div>--%>
                                        </div>
                                    </li>
                                </c:if>
                            </ul>
                            <p class="h30 mb10 cl"></p>
                        </div>
                        <p class="fl xcxq_xq_fr" style="margin-left: 16px;">
                            <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${planTrip.scenicInfo.cover}?imageView2/1/w/240/h/240" alt="${planTrip.scenicInfo.name}"/>
                        </p>

                        <p class="cl"></p>
                    </div>
                </c:forEach>
                <c:if test="${planDay.hotel != null}">
                    <%--<c:if test="${planDay.planTripList.size() != 0}">--%>
                        <%--<div class="xcxq_xq_div_cl b">两地相距</div>--%>
                    <%--</c:if>--%>
                    <div class="xcxq_xq_div trip-node"
                         lng="${planDay.hotel.extend.longitude}"
                         lat="${planDay.hotel.extend.latitude}"
                         tid="${planDay.hotel.id}-${status.index + 1}"
                         tname="${planDay.hotel.name}"
                         taddress="${planDay.hotel.extend.address}"
                         tprice="${planDay.hotel.price}"
                         tcomment="${planDay.hotel.shortDesc}"
                         tcover="${planDay.hotel.cover}"
                         tstar="${planDay.hotel.star}"
                            >
                        <i class="i_btop"></i>
                        <i class="i_bottom"></i>

                        <div class="fl xcxq_xq_fl">
                            <i class="d_ico disB d_xq9 posiA" style="top:23px;"></i>
                            <p class="name"><span>第${planDay.planTripList.size() + 1}站</span>${planDay.hotel.name}</p>
                            <ul>
                                <li><i class="d_ico disB d_xq5 fl mr10"></i>

                                    <div class="xcxq_xq_fl_div fl">
                                        <label>地址：${planDay.hotel.extend.address}</label></div>
                                </li>
                            </ul>
                            <p class="h30 mb10 cl"></p>
                        </div>
                        <p class="fl xcxq_xq_fr" style="margin-left: 16px;">
                            <img src="${planDay.hotel.cover}" alt="${planDay.hotel.name}"/>
                        </p>

                        <p class="cl"></p>
                    </div>
                </c:if>
                <p class="cl"></p>
            </div>
            <c:if test="${planDay.returnTraffic != null}">
                <c:choose>
                    <c:when test="${planDay.returnTraffic.trafficType == 'AIRPLANE'}">

                    </c:when>
                    <c:when test="${planDay.returnTraffic.trafficType == 'TRAIN'}">

                    </c:when>
                </c:choose>
                <div class="xcxq_div">
                    <div class="bottom posiR">
                        <i class="posiA d_ico disB  d_xq2"></i>
                        <label class="fl name disB"></label>
                        <ul class="oval4 jt_ul fl">
                            <li class="li_w1" style="line-height: 76px;margin-right: 6px;">
                                <b>${planDay.returnTraffic.leaveCity.name}</b>
                                <c:if test="${planDay.returnTraffic.trafficType == 'AIRPLANE'}">
                                    <i class="d_ico disB fl d_xq8" style="margin: 27px 12px 0;"></i>
                                </c:if>
                                <c:if test="${planDay.returnTraffic.trafficType == 'TRAIN'}">
                                    <i class="d_ico disB fl d_xq12" style="margin: 27px 12px 0;"></i>
                                </c:if>
                                <b>${planDay.returnTraffic.arriveCity.name}</b>
                            </li>
                            <li class="li_w2" style="margin-top: 17px">
                                <b>线路</b>
                                <c:if test="${planDay.returnTraffic.trafficType == 'AIRPLANE'}">
                                    <p>起飞 ${planDay.returnTraffic.leaveTime} ${planDay.returnTraffic.leaveTransportation.name}<br/>
                                       降落 ${planDay.returnTraffic.arriveTime} ${planDay.returnTraffic.arriveTransportation.name} </p>
                                </c:if>
                                <c:if test="${planDay.returnTraffic.trafficType == 'TRAIN'}">
                                    <p>出发 ${planDay.returnTraffic.leaveTime} ${planDay.returnTraffic.leaveTransportation.name}<br/>
                                       到达 ${planDay.returnTraffic.arriveTime} ${planDay.returnTraffic.arriveTransportation.name} </p>
                                </c:if>

                            </li>
                            <li class="li_w3" style="margin-top: 17px;margin-left: 10px;margin-right: -17px;">
                                <c:if test="${planDay.returnTraffic.trafficType == 'AIRPLANE'}">
                                    <b>班次</b>
                                    <p>${planDay.returnTraffic.company}<br/>航班${planDay.returnTraffic.trafficCode}</p>
                                </c:if>
                                <c:if test="${planDay.returnTraffic.trafficType == 'TRAIN'}">
                                    <b>车次</b>
                                    <p style="line-height: 30px;">${planDay.returnTraffic.trafficCode}</p>
                                </c:if>
                            </li>
                            <li class="li_w4" style="margin-top: 17px">
                                <b>用时</b>

                                <p><fmt:formatNumber type="number" value="${planDay.returnTraffic.flightTime/60}"
                                                     maxFractionDigits="0"/>小时
                                        ${planDay.returnTraffic.flightTime%60}分</p>
                            </li>
                            <li class="li_w5" style="margin-top: 17px">
                                <b>价格</b>

                                <p>¥${planDay.returnTrafficCost}</p>
                            </li>
                        </ul>
                        <p class="cl"></p>
                    </div>

                    <p class="cl"></p>
                </div>
            </c:if>
        </c:forEach>

        <div class="oval4 xcxq_xq" id="idx5">
            <div class="xcxq_xq_div pt10">
                <i class="d_ico disB d_xq10 posiA"></i>

                <div class="xlqd fs13">
                    <label class="name b fl">行李清单</label>
                    <ul class="fl">
                        <li>
                            <span class="b">文件类</span>

                            <div class="fl">
                                <a href="#">身份证</a>
                                <a href="#">学生证</a>
                                <a href="#">机票</a>
                                <a href="#">车票</a>
                                <a href="#">银行卡</a>
                                <a href="#">现金</a>
                            </div>
                            <p class="cl"></p>
                        </li>
                        <li>
                            <span class="b">电子电器</span>

                            <div class="fl">
                                <%--<a href="#">手机</a>--%>
                                <a href="#">相机/DV</a>
                                <a href="#">移动电源</a>
                                <a href="#">数据线</a>
                                <a href="#">备用电池</a>
                            </div>
                            <p class="cl"></p>
                        </li>
                    </ul>
                    <p class="cl"></p>
                </div>
            </div>
        </div>
        <p class="cl h50 mb20"></p>
    </div>
</div>

<script type="text/html" id="tpl_mark_activescenic">
    <div class="mark-active-scenic">
        <a class="normal-scenic-mark"><span></span>

            <div class="active-scenic-labelwrap">
                <div class="active-scenic-labelmask"></div>
                <div class="active-scenic-label">
                    <i class="arrow"></i>

                    <p title="{{name}}"><i class="active-scenic-order">{{order}}</i>{{name}}</p>
                    <i class="active-scenic-allow"></i>
                </div>
            </div>
        </a>
    </div>
</script>

<script type="text/html" id="tpl_mark_normalscenic">
    <div class="mark-normal-scenic">
        <a class="normal-scenic-mark"><span></span>

            <div class="normal-scenic-labelwrap">
                <div class="normal-scenic-labelmask"></div>
                <div class="normal-scenic-label">
                    <i class="arrow"></i>

                    <p title="{{name}}"><i class="normal-scenic-order">{{order}}</i>{{name}}</p>
                    <i class="normal-scenic-allow"></i>
                </div>
            </div>
        </a>
    </div>
</script>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/map.jsp"></jsp:include>
<%--<script type="text/javascript" src="/js/lvxbang/plan/map.js"></script>--%>
<%--<script type="text/javascript" src="/js/lvxbang/edit.map.js"></script>--%>
<script type="text/javascript" src="/js/lvxbang/plan/detail.js"></script>
<script type="text/javascript" src="/js/lvxbang/collect.js"></script>
<script src="/js/lib/jquery.qrcode.min.js" type="text/javascript"></script>
</body>
</html>
