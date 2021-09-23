<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>酒店预订_酒店查询_旅行帮</title>
    <meta name="keywords" content="酒店预订，酒店查询，酒店点评，宾馆预订"/>
    <meta name="description" content="旅行帮提供国内1000多个城市，80多个国际知名品牌的酒店查询预订服务，是您网上预订酒店的首选。"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="mall" class="Hotels" onload="pageReload();">

<input type="hidden" value="0" id="page_reload"/><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!-- #EndLibraryItem -->

    <jx:include fileAttr="${LVXBANG_HOTEL_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildHotelIndex" validDay="7"></jx:include>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/popup/map_popup.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script type="text/html" id="tpl-visited-hotel">
    <li>

        <a href="{{path}}"><p class="img fl"><img data-original="{{imgPath}}" alt="{{title}}"/></p></a>
        <div class="nr posiR fr">
            <a href="{{path}}"><p class="name b">{{title}}</p></a>
            <p class="hstar"><i style="width: {{star*10}}px"></i></p>
            <p class="fraction"><b>{{score/20}}分/5分</b> <a style="font-size: 10px;color:#666;" href="{{path}}#comment">
                <%--来自{{count}}人点评--%>
            </a>  </p>
        </div>

        <p class="cl"></p>
    </li>
</script>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script src="/js/lib/slides.js" type="text/javascript" ></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript" ></script>
<script src="/js/lvxbang/hotel/index.js" type="text/javascript" ></script>
<script src="/js/lvxbang/hash.js" type="text/javascript"></script>
<script type="application/javascript">
    //后退是刷新页面
    function pageReload(){
        if($('#page_reload').val()==0){
            $('#page_reload').val('1')
        }
    }
</script>


