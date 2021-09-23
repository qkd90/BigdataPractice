<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!Doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${plan.name}</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/detail.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Itinerary">
<div class="similar">
    <div class="similar_div">
        <div class="similar_wx mb10 textC">
            <b class="color6 fs14">同步至手机</b>

            <p><img src="/images/wx.jpg"/></p>
        </div>
        <i class="similar_i fl mr15"></i>

        <p class=" similar_p b fl mt20">
            <i class="similar_i2 fl"></i>
            <label class="oval4 fl disB">你还可以<a href="#">查看相似线路</a></label>
        </p>
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
        <p class="n_title h40 lh40">
            您在这里：&nbsp;<a href="#">目的地</a>
            &nbsp;&gt;&nbsp;<a href="#">${plan.city.name}</a>
            &nbsp;&gt;&nbsp;<a href="#">${plan.city.name}线路</a>&nbsp;&gt;&nbsp;${plan.name}
        </p>
        <c:if test="${plan.user.id == CURRENT_LOGIN_USER_MEMBER.id}">
            <div class="detail_top posiR">
                <p class="img fl">
                    <c:if test="${plan.user.head==null || plan.user.head==''}">
                        <img src="/images/portrait2.jpg" style="display: inline;">
                    </c:if>
                    <c:if test="${plan.user.head!=null && plan.user.head!=''}">
                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.user.head}?imageView2/1/w/48/h/48" style="display: inline;">
                    </c:if>
                </p>

                <div class="fl ml10 nr">
                    <b class="name">${plan.name}</b>

                    <p class="time">规划师：<span class="ml10">${plan.user.nickName}</span></p>
                </div>
                <%--<a href="#" class="oval4 but fr b">保存行程</a>--%>
                <a href="${PLAN_PATH}/lvxbang/plan/edit.jhtml?planId=${plan.id}" class="oval4 mr20 l_line fr b"><i></i>修改线路</a>
                <a href="${PLAN_PATH}/lvxbang/plan/booking.jhtml?planId=${plan.id}" class="oval4 mr20 o_line fr b"><i></i>购买线路</a>
            </div>
        </c:if>

        <div class="cl">
            <!--d_banner-->
            <div class="d_banner posiR fl TripPlanning_banner">
                <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}?imageView2/1/w/680/h/400"/>

                <div class="d_banner_d posiA">
                    <p class="name ff_yh b">${plan.name}</p>
                    <c:if test="${recommendPlan!=null}">
                        <div class="cl lh20">来自游记《${recommendPlan.planName}》
                            <br/><span class="mr20">作者：${recommendPlan.user.nickName}</span>${plan.createTime}
                        </div>
                    </c:if>
                    <ul class="service_u fr mt10">
                        <li class="checked"><i></i><span>浏览：${plan.planStatistic.viewNum}</span></li>
                        <li><i class="ico2"></i><span>收藏：${plan.planStatistic.collectNum}</span></li>
                        <li><i class="ico3"></i><span>引用：${plan.planStatistic.quoteNum}</span></li>
                    </ul>
                </div>
            </div>

            <script type="text/javascript"
                    src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
            <div class="fr" style="width:310px;height:398px;border:#ccc solid 1px;" id="dituContent"></div>
            <!-- 地图-->
        </div>
        <p class="h20 cl"></p>
    </div>

    <!--行程概括-->
    <div class="h50 mb20 cl" id="nav">
        <div class="nav d_select">
            <div class="w1000 posiR">
                <dl class="d_select_d fl mr40 fs16 b">
                    <dd class="span1 checked">线路概括</dd>
                    <dd class="span2">线路详情</dd>
                </dl>
                <a href="#" class="but2 b fl mt15 fs16">查看游记详情 >></a>
                <c:if test="${CURRENT_LOGIN_USER_MEMBER==null || CURRENT_LOGIN_USER_MEMBER.id!=plan.user.id}">
                    <a href="/" class="d_stroke choose fr mt5"><i></i>引用线路<br>${plan.planStatistic.quoteNum}</a>
                    <a href="javascript:;" class="d_collect choose fr mt5 mr20 favorite" data-favorite-id="${plan.id}"
                       data-favorite-type="plan" data-favorite-name="${plan.name}"
                       data-favorite-img="${plan.coverPath}"><i></i>收藏<br></a>
                </c:if>
            </div>
        </div>
    </div>

    <!--行程概括-->
    <div class="w1000 general" id="id1">
        <dl class="color6 general_dl">
            <c:forEach items="${plan.planDayList}" var="planDay" varStatus="status">
                <dd <c:if test="${status.index>2}">style="display: none;"</c:if>>
                    <p class="title b"><i></i><span class="mr20">第${planDay.days}天</span>厦门</p>
                    <c:if test="${planDay.traffic != null}">
                        <p class="nr">
                            <b class="mr20">交通</b>${planDay.traffic.leaveCity.name}
                            至${planDay.traffic.arriveCity.name} ${planDay.traffic.trafficCode}
                        </p>
                    </c:if>

                    <p class="nr"><b class="mr20">住宿</b>${planDay.hotel.name}</p>

                    <p class="nr"><b class="mr20">景点</b>
                        <c:forEach items="${planDay.planTripList}" var="planTrip" varStatus="status">
                            <c:if test="${status.index!=0}">--</c:if>${planTrip.scenicInfo.name}
                        </c:forEach>

                    </p>
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
                                    ${plan.startCity.name}>${planDay.city.name}
                                </c:if>
                                <c:if test="${status.index != 0}">
                                    ${plan.planDayList[status.index-1].city.name}>${planDay.city.name}
                                </c:if>

                            </span>

                    <div class="add fl"><i class="d_ico disB d_xq11 fl mr5"></i><span class="fl">今日地图</span></div>
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

                    <label class="fl name disB">交通</label>
                    <ul class="oval4 jt_ul fl">
                        <li class="li_w1">
                            <b>${planDay.traffic.leaveCity.name}</b><i
                                class="d_ico disB fl d_xq8"></i><b>${planDay.traffic.arriveCity.name}</b>
                        </li>
                        <li class="li_w2">
                            <b>线路</b>

                            <p>起 ${planDay.traffic.leaveTime} ${planDay.traffic.leaveTransportation.name}<br/>
                                降 ${planDay.traffic.arriveTime} ${planDay.traffic.arriveTransportation.name} </p>
                        </li>
                        <li class="li_w3">
                            <b>班次</b>
                            <c:if test="${planDay.traffic.trafficType == 'AIRPLANE'}">
                                <p>${planDay.traffic.company}<br/>航班${planDay.traffic.trafficCode}</p>
                            </c:if>
                            <c:if test="${planDay.traffic.trafficType == 'TRAIN'}">
                                <p>${planDay.traffic.trafficCode}</p>
                            </c:if>
                        </li>
                        <li class="li_w4">
                            <b>用时</b>

                            <p><fmt:formatNumber type="number" value="${planDay.traffic.flightTime/60}"
                                                 maxFractionDigits="0"/>小时
                                    ${planDay.traffic.flightTime%60}分</p>
                        </li>
                        <li class="li_w5">
                            <b>价格</b>

                            <p>￥${planDay.trafficCost}</p>
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
                        <div class="xcxq_xq_div_cl b">两地相距${planTrip.travelMileage}</div>
                    </c:if>
                    <div class="xcxq_xq_div pt10 trip-node"
                         lng="${planTrip.scenicInfo.scenicGeoinfo.baiduLng}"
                         lat="${planTrip.scenicInfo.scenicGeoinfo.baiduLat}"
                         tid="${planTrip.scenicInfo.id}"
                         tname="${planTrip.scenicInfo.name}"
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

                                            <div class="food_p scenic-li fl">${planTrip.scenicInfo.ticket}</div>
                                        </div>
                                    </li>
                                </c:if>
                                <c:if test="${planTrip.scenicInfo.scenicOther.description!=null}">
                                    <li class="posiR"><i class="d_ico disB d_xq7 fl mr10"></i>

                                        <div class="xcxq_xq_fl_div fl">
                                            <label class="disB fl">简介：</label>

                                            <div class="food_p scenic-li fl">${planTrip.scenicInfo.scenicOther.description}</div>
                                        </div>
                                    </li>
                                </c:if>
                            </ul>
                            <p class="h30 mb10 cl"></p>
                        </div>
                        <p class="fl xcxq_xq_fr">
                            <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${planTrip.scenicInfo.cover}?imageView2/1/w/240/h/240"/>
                        </p>

                        <p class="cl"></p>
                    </div>
                </c:forEach>
                <c:if test="${planDay.planTripList.size() != 0}">
                    <div class="xcxq_xq_div_cl b">两地相距</div>
                </c:if>
                <div class="xcxq_xq_div pt10 trip-node"
                     lng="${planDay.hotel.extend.longitude}"
                     lat="${planDay.hotel.extend.latitude}"
                     tid="${planDay.hotel.id}"
                     tname="${planDay.hotel.name}"
                        >
                    <i class="i_btop"></i>
                    <i class="i_bottom"></i>

                    <div class="fl xcxq_xq_fl">
                        <p class="name"><span>第${planDay.planTripList.size() + 1}站</span>${planDay.hotel.name}</p>
                        <ul>
                            <li><i class="d_ico disB d_xq5 fl mr10"></i>

                                <div class="xcxq_xq_fl_div fl">
                                    <label>地址：${planDay.hotel.extend.address}</label></div>
                            </li>
                        </ul>
                        <p class="h30 mb10 cl"></p>
                    </div>
                    <p class="fl xcxq_xq_fr">
                        <img src="${planDay.hotel.cover}"/>
                    </p>

                    <p class="cl"></p>
                </div>

                <p class="cl"></p>
            </div>
        </c:forEach>

        <%--<div class="oval4 xcxq_xq">--%>
            <%--<div class="xcxq_xq_div pt10">--%>
                <%--<i class="d_ico disB d_xq10 posiA"></i>--%>

                <%--<div class="xlqd fs13">--%>
                    <%--<label class="name b fl">行李清单--这里还没有处理</label>--%>
                    <%--<ul class="fl">--%>
                        <%--<li>--%>
                            <%--<span class="b">文件类</span>--%>

                            <%--<div class="fl">--%>
                                <%--<a href="#">手机</a>--%>
                                <%--<a href="#">相机/DV</a>--%>
                                <%--<a href="#">移动电源</a>--%>
                                <%--<a href="#">数据线</a>--%>
                                <%--<a href="#">备用电池</a>--%>
                                <%--<a href="#">现金</a>--%>
                            <%--</div>--%>
                            <%--<p class="cl"></p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<span class="b">电子电器</span>--%>

                            <%--<div class="fl">--%>
                                <%--<a href="#">手机</a>--%>
                                <%--<a href="#">相机/DV</a>--%>
                                <%--<a href="#">移动电源</a>--%>
                                <%--<a href="#">数据线</a>--%>
                                <%--<a href="#">备用电池</a>--%>
                                <%--<a href="#">现金</a>--%>
                            <%--</div>--%>
                            <%--<p class="cl"></p>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<p class="cl"></p>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
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
<%--<script type="text/javascript" src="/js/lvxbang/plan/map.js"></script>--%>
<script type="text/javascript" src="/js/lvxbang/edit.map.js"></script>
<script type="text/javascript" src="/js/lvxbang/plan/detail.js"></script>
<script type="text/javascript" src="/js/lvxbang/collect.js"></script>
</body>
</html>
