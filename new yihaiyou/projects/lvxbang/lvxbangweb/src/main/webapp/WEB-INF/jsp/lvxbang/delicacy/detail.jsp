<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="jx" uri="/includeTag" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jx:include fileAttr="${LVXBANG_DELICACY_HEAD}" targetObject="lvXBangBuildService" targetMethod="buildOneDelicacy" objs="${param.delicacyId}" validDay="60"></jx:include>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/plan.map.css" rel="stylesheet" type="text/css">
    <link href="/css/detail.css" rel="stylesheet" type="text/css">

</head>

<body myname="strategy" class="Food_Detail"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<jx:include fileAttr="${LVXBANG_DELICACY_DETAIL}" targetObject="lvXBangBuildService" targetMethod="buildOneDelicacy" objs="${param.delicacyId}" validDay="60"></jx:include>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<div class="map-container fr whole-map hqxqdtx hqxqdtxx">
    <a href="#" class="close"><i></i></a>
    <div class="fl" style="width:0;height:0;" id="baidu-map-main"></div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/map.jsp"></jsp:include>
<!--comment-->
<jsp:include page="/WEB-INF/jsp/lvxbang/comment/comment.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>

<script type="text/html" id="tpl-map-delicacy">
    <div class="posiA map_plan_day map_plan_day_{{day}}">
        <div class="posiR Pop_ups_posiR">
            <div class="Pop_ups posiA">
                <div class="posiR Pop_ups_div banner_product">
                    <div class="bacSlideBox">
                        <div class="bacSlideBox_div fl posiR"><ul class="Pop_ups_ul posiR"><li><p class="img"><img src="{{cover}}"></p></li></ul></div>
                    </div>
                    <div class="Pop_ups_nr">
                        <div class="Pop_ups_nr_div">
                            <div class="Pop_ups_fl fl">
                                <p class="name fs14 b"><a href="#" class="b">{{name}}</a></p>
                                <p class="add">电话：{{tel}}</p>
                                <p class="add">地址：{{address}}</p>
                            </div>
                            <p class="cl fs13 comment">{{if comment}}{{comment | briefText:28}}{{else}}&nbsp;&nbsp;{{/if}}<img src="/images/left.png" align="absmiddle" class="ml5"></p>
                            <p class="cl"></p>
                        </div>

                    </div>
                </div>
            </div>

            <div class="location location_{{id}} on">
                <label class="fl location_l {{color}}"></label>
                <div class="fl">{{name}}</div>
            </div>
        </div>
    </div>
</script>
<script src="/js/lvxbang/delicacy/detail.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>