<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/2/23
  Time: 9:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>预览游记:&nbsp;${recplan.planName}</title>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/detail.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Travel_Detail">
<%@include file="../common/header.jsp" %>
<div class="main cl mt10">
    <div class="travel_bottom">
        <div class="travel_bottom_div">
            <%--<span class="ff_yh">游记预览</span>--%>
            <a href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/edit.jhtml?recplanId=${recplan.id}" class="o_line fr oval4">返回</a>
            <p class="cl"></p>
        </div>
    </div>
    <div class="w1000">
        <!--n_title-->
        <p class="n_title h40 lh40">
            您在这里: <a href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/index.jhtml">游记</a>
            &nbsp;&gt;&nbsp;写游记&nbsp;&gt;&nbsp;游记预览
            <%--<a href="${DESTINATION_PATH}/lvxbang/destination/index.jhtml">目的地</a>--%>
            <%--&nbsp;&gt;&nbsp;<a target="_blank" href="${DESTINATION_PATH}/lvxbang/destination/detail_${recplan.cityId}.html">${recplan.cityName}</a>--%>
            <%--&nbsp;&gt;&nbsp;<a target="_blank" href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/list.jhtml?cityIds=${recplan.cityId}">${recplan.cityName}游记</a>--%>
            <%--&nbsp;&gt;&nbsp;预览游记:&nbsp;${recplan.planName}--%>
        </p>
        <input type="hidden" id="recplanId" value="${recplan.id}"/>
        <div class="yjll ff_yh textC mb30 mt30">
            游记预览
        </div>
        <!--detail_top-->
        <div class="detail_top posiR">
            <p class="img fl">
                <c:choose>
                    <c:when test="${CURRENT_LOGIN_USER_MEMBER.head != null && CURRENT_LOGIN_USER_MEMBER.head != ''}">
                        <c:choose>
                            <c:when test="${fn: startsWith(CURRENT_LOGIN_USER_MEMBER.head, 'http')}">
                                <img src="${CURRENT_LOGIN_USER_MEMBER.head}"/>
                            </c:when>
                            <c:otherwise>
                                <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${CURRENT_LOGIN_USER_MEMBER.head}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <img src="/images/toux.PNG"/>
                    </c:otherwise>
                </c:choose>
            </p>
            <div class="fl ml10 nr">
                <b class="name">${CURRENT_LOGIN_USER_MEMBER.nickName}</b>
                <p class="time">线路${recplan.days}天
                    <span class="ml10">
                        <fmt:formatDate value="${recplan.startTime}" />
                    </span>
                </p>
            </div>
        </div>
        <!--d_banner-->
        <div class="d_banner posiR mb10">
            <c:choose>
                <c:when test="${fn: startsWith(recplan.coverPath, 'http')}">
                    <img src="${recplan.coverPath}" style="width:100%"/>
                </c:when>
                <c:otherwise>
                    <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/1000/h/400" style="width:100%; height: 100%"/>
                </c:otherwise>
            </c:choose>
            <div class="d_banner_d posiA">
                <p class="name ff_yh">${recplan.planName}</p>
                <div class="cl lh20" style="width: 640px">
                    ${recplan.description}
                </div>
            </div>
        </div>

    </div>

    <!--游记-->
    <div class="h50" id="nav">
        <div class="nav d_select">
            <div class="w1000 posiR">
                <dl class="d_select_d fl mr40 fs16 b">
                    <dd class="span1 checked">游记</dd>
                    <%--<dd class="span2">游记点评</dd>--%>
                </dl>
                <%--<a href="#" class="but2 b fl mt15 fs16 to_recplan_detail">查看详细行程 >></a>--%>
                <%--<a href="#id2" class="but fr mt5 oval4">我要点评</a>--%>
            </div>
        </div>
    </div>

    <!--游记-->
    <div class="w1000 cl">

        <!--左侧-->
        <div class="travel_d_l fl">
            <!--游记-->
            <div id="travel_list">

            </div>
            <hr class="hr"/>
        </div>

        <!--右侧-->
        <div class="travel_d_r fr">
            <div class="nav">
                <p class="title fs20 b">游记目录</p>
                <div class="travel_d_r_div" id="travel_index">

                </div>
            </div>
        </div>

        <p class="cl h50"></p>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<%--左侧列表游记天数据模板--%>
<script type="text/html" id="left_day_item">
    <div class="travel_d_l_yj">
        <div id="travel{{day}}">
        <div class="travel_title mb20">
            <i class="d_ico d_ico_t mr20 disB fl"></i>
            <p class="fl mt50 pt20">
                <i class="d_ico fl d_ico_l disB"></i>
                <span class="fl fs20 ff_yh b">第{{day}}天</span>
                <i class="d_ico fl d_ico_r disB"></i>
            </p>
        </div>
        <div class="cl color6 fs14 lh25 mb20">{{#description}}</div>
        <div id="left_d{{day_index}}_trip_list">

        </div>
        </div>
    </div>
</script>
<%--左侧列表单天行程节点数据模板--%>
<script type="text/html" id="left_trip_item">
    <p class="travel_t posiR mb20">
        {{if tripType == 1}}
        <i class="d_ico disB fl "></i>
        {{else if tripType == 2}}
        <i class="d_ico disB fl d_cany"></i>
        {{else if tripType == 3}}
        <i class="d_ico disB fl d_jdi"></i>
        {{else if tripType == 4}}
        <i class="d_ico disB fl d_jtx"></i>
        {{/if}}
        {{scenicName}}</p>
    <div class="cl color6 fs14 lh25 mb20">{{#exa}}</div>
    <div id="left_d{{day_index}}t{{trip_index}}_photo_list">

    </div>
</script>
<%--左侧列表游照片数据模板--%>
<script type="text/html" id="left_photo_item">
    <p class="cl mb20">
        <img data-original="{{imgAddr | recommendPlanDetailImg}}"  class="img" height="{{height}}"/>
    </p>
</script>
<%--右侧目录游记天数据模板--%>
<script type="text/html" id="right_day_item">
    <dl class="travel_d_r_dl">
        <dt class="travel{{day}}">第{{day}}天（<span>{{if scenics != null}}{{scenics}}{{else}}0{{/if}}</span>）
            <i class="d_ico disB posiA"></i>
        </dt>
        <dd class="disB" day="{{day}}" id="right_d{{day_index}}_trip_list">
        </dd>
    </dl>
</script>
<%--右侧目录游记单天行程节点数据模板--%>
<script type="text/html" id="right_trip_item">
    <div class="travel_d_r_dl_d">
        <a href="javascript:;"><i class="d_ico d_jt"></i>
            {{if tripType == 1}}
                <i class="d_ico disB fl "></i>
            {{else if tripType == 2}}
                <i class="d_ico disB fl d_cany"></i>
            {{else if tripType == 3}}
                <i class="d_ico disB fl d_jtx"></i>
            {{else if tripType == 4}}
                <i class="d_ico disB fl d_jdi"></i>
            {{else if tripType == 5}}
                <i class="d_ico disB fl d_qt"></i>
            {{/if}}
            <div class="fl nr">
                <p class="name">{{scenicName}}</p>
                    {{if tripType == 1 || tripType == 2}}
                        {{if score >=1 && score < 25}}
                            <span class="hstar cl"><i class="star1"></i>
                        {{else if score >= 25 && score < 50}}
                            <span class="hstar cl"><i class="star2"></i>
                        {{else if score >= 50 && score < 75}}
                            <span class="hstar cl"><i class="star3"></i>
                        {{else if score >= 75 && score < 90}}
                            <span class="hstar cl"><i class="star4"></i>
                        {{else if score >= 90 && score <= 100}}
                            <span class="hstar cl"><i class="star5"></i>
                        {{/if}}
                    {{/if}}
                </span>
            </div>
        </a>
    </div>
</script>
<script src="/js/lib/template.helper.js" type="text/javascript"></script>
<script src="/js/lvxbang/recommendPlan/recommendPlan.js"></script>
<script src="/js/lvxbang/recommendPlan/preview.js"></script>
</body>
</html>
