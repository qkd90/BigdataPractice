<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jx" uri="/includeTag" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${city.name}旅游地图_${city.name}景点地图_${city.name}旅行地图_旅行帮</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="${city.name}旅游地图 ,${city.name}景点地图 ,${city.name}旅行地图,${city.name}美食地图,${city.name}地图,${city.name}手绘地图">
    <meta http-equiv="description" content="旅行帮是国内最著名的旅游地图平台。为旅游爱好者提供纯手绘风格${city.name}景点地图，让${city.name}景点一目了然！让您的旅程增添一份乐趣！">
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <link rel="stylesheet" type="text/css" href="/css/tBase.css" media="screen, projection">
    <link rel="stylesheet" type="text/css" href="/css/announcement.css" media="screen, projection">
    <link rel="stylesheet" type="text/css" href="/css/hand-drawn-map.css">
    <link rel="stylesheet" type="text/css" href="/css/hdmap.css">
    <link rel="stylesheet" type="text/css" href="/css/HandDrawing.css">
</head>
<body class="HandDrawing">

<!--head-->
<div class="head">
    <!--nav_logo-->
    <a href="${INDEX_PATH}" target="_blank" class="logo"></a>
    <p class="add  fl"><b class="fs16">当前城市：${city.name}</b><a class="add_more_city_button" href="javaScript:;">[切换]</a></p>
    <jx:include fileAttr="/static/master/lvxbang/common/handDrawCity.htm" targetObject="lvXBangBuildService" targetMethod="buildCommon"></jx:include>
    <div class="search">
        <div class="searchDiv">
            <form id="search" method="post" action="${SCENIC_PATH}/scenic_list.html">
                <div class="posiR categories" data-url="${SCENIC_PATH}/lvxbang/scenic/suggest.jhtml">
                    <input type="text" id="txtSearch" name="scenicName" placeholder="输入目的地/游记/景点/美食/酒店"  value=""  class="input"/>
                    <div class="posiA categories_div KeywordTips">
                        <ul>
                        </ul>
                    </div>
                    <!--错误-->
                    <div class="posiA categories_div cuowu textL">
                        <p class="cl">抱歉未找到相关的结果！</p>
                    </div>
                </div>
                <input type="submit" value="搜 索" class="searchDivA">
                </form>

        </div>
         <div style="margin-left: 120%;margin-top: -5%;width: 150px;font-size: 20px;font-weight: 800;"><span>${city.name}旅游地图</span></div>
    </div>

    <a href="${SCENIC_PATH}/scenic_list.html?cityCode=${cityId}" class="fs14 jd fr">去列表页找景点》</a>
    <!--nav_logo end-->
    <p class="cl"></p>
</div>
<!--head  end-->
<div class="main cl posiR">

    <input type="hidden" value="${empty _CURRENT_MEMBER ? '0' : '1' }" id="J_userInfo"/>
    <input type="hidden" id="city_scenicId" value="${cityInfo.scenicInfoId}"/>
    <input type="hidden" id="city_code" value="${city.cityCode}"/>
    <input type="hidden" id="city_name" value="${city.name}"/>
    <input type="hidden" id="map-min-level"/>
    <input type="hidden" id="planId"/>
    <input type="hidden" id="plan_path" value="${PLAN_PATH}">
    <%--<ul class="HandDrawing_ul">--%>
    <%--</ul>--%>

    <div class="hd_add">
        <div class="hdmap-content">
            <div id="J_hdmap" class="hand-draw-panel auto-height-panel">
                <div class="hand-draw-box">
                    <div id="allmap">
                    </div>
                    <div id="J_hd-tips-img" class="hd-tips-img"></div>
                </div>
            </div>
        </div>



        <a href="javaScript:;" class="top_attractions"></a>
        <div class="posiA ta_list">
            <div class="ta_list_div posiR" style="background: rgba(0, 0, 0, 0.6);">
                <i class="ico"></i>
                <a href="javaScript:;" class="close"></a>
                <a href="javaScript:;" class="left"></a>
                <a href="javaScript:;" class="right"></a>
                <div class="ta_list_div_d">
                    <ul class="ta_list_ul" num="1">

                    </ul>
                </div>
            </div>
        </div>
    </div>
    <p class="cl"></p>
</div>
<a href="#" id="jumpToUrl" style="display: none" target="_blank"></a>
</body>
</html>
<script type="text/html" id="tpl-hd-outline">
    <div class="Pop_ups posiA scenic-node" data-id="{{id}}" data-type="scenic" data-name="{{name}}" data-img="{{cover}}" data-price="{{price}}">
        <div class="posiR Pop_ups_div">
            <a href="javaScript:;" class="close"></a>
            <a href="javaScript:;" class="left"></a>
            <a href="javaScript:;" class="right"></a>
            <ul class="Pop_ups_ul" num="1">
                {{each galleryList as gallery i}}
                <li><p class="img"><img src="http://7u2inn.com2.z0.glb.qiniucdn.com/{{gallery}}?imageView2/2/w/350" alt="{{name}}"/></p></li>
                {{/each}}
            </ul>
            <div class="Pop_ups_nr">
                <div class="Pop_ups_fl fl">
                    <p class="name fs14 b"><a href="javaScript:;" class="jumpTo" data-url="${SCENIC_PATH}/scenic_detail_{{id}}.html"><b>{{name}}</b>(5A)</a></p>
                    <p class="add">地址：{{address}}</p>
                </div>
                <div class="Pop_ups_fr fr textR">
                    <p class="fs"><b class="ff_yh">{{score/20}}</b>/5分 </p>
                    <%--<p class="dp"><a href="javaScript:;" class="jumpTo" data-url="${SCENIC_PATH}/scenic_detail_{{id}}.html#comment">{{commentNum}}人点评</a></p>--%>
                </div>

                {{if shortComment != null && shortComment.length > 0}}
                <p class="js cl fs13" data-title="{{shortComment}}">
                    <span class="mr5" ></span>{{shortComment | briefText:19}}<span class="ml5"></span>
                </p>
                {{else}}
                <p class="comment cl fs13"></p>
                {{/if}}

                <%--{{if price}}--%>
                <div class="pript">
                    <span class="fl">门票价格</span>
                    {{if price != null && price > 0}}
                    <p class="price b" style="font-size: 12px"><b class="ff_yh">￥</b><span class="fs14" style="font-size: 16px">{{price}}</span>起</p>
                    {{else}}
                    <p class="price b">免费</p>
                    {{/if}}
                </div>
                <%--{{/if}}--%>
                <a href="javaScript:;" class="but b oval4 stroke_open stroke_handDraw">+加入线路</a>
            </div>
        </div>
    </div>

</script>
<script type="text/html" id="tpl-suggest-item">
    <li data-text="{{name}}" class="suggest-item" data-id="{{id}}" data-type="{{type}}">
        <label class="fl">{{#key}}</label>
        <span class="fr"></span>
    </li>
</script>
<script type="text/html" id="tpl-recommend-scenic-item">
    <li>
        <a class="recommend-scenic-button" data-id="{{scenic.id}}">
            <p class="img"><img src="http://7u2inn.com2.z0.glb.qiniucdn.com/{{scenic.cover}}?imageView2/2/w/350" alt="{{scenic.name}}"/></p>
            <p class="name">{{scenic.name}}</p>
        </a>
    </li>
</script>
<script src="/js/lib/jquery.min.js"></script>
<script src="/js/lib/jquery.lazyload.js"></script>
<script src="/js/lib/jquery.enplaceholder.js"></script>
<script type="text/javascript" src="/js/lib/announcement.js"></script>
<script src="/js/lib/common_util.js"></script>
<script src="/js/lib/template.js"></script>
<script src="/js/lib/template.helper.js"></script>
<script src="/js/lib/jquery-ui-1.10.4.custom.min.js"></script>
<script src="/js/lib/AreaRestriction.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>

<jsp:include page="/WEB-INF/jsp/lvxbang/plan/float.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/popup/popup.jsp"></jsp:include>
<script type="text/javascript" src="/js/lvxbang/hdmap/hdmap.js"></script>
<script type="text/javascript" src="/js/lvxbang/hdmap/hdmap.main.js"></script>
<script type="text/javascript" src="/js/lvxbang/hdmap/hand-draw-info-window.js"></script>
<script type="text/javascript" src="/js/lvxbang/hdmap/hdmap.scenicoverlay.js"></script>
<script type="text/javascript" src="/js/lvxbang/hdmap/hdmap.scenic.search.js"></script>
<script type="text/javascript" src="/js/lvxbang/city.selector.js"></script>

