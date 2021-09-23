
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>旅行帮</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>

</head>
<body class="Favorites member_xx member_sc">
<div class="nextpage ff_yh fs36 textC">
    <a href="javaScript:;" class="close"></a>
    <img src="/images/g_ico.jpg"/>
</div><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!-- #EndLibraryItem -->
<!--banner-->
<link href="/css/tBase.css" rel="stylesheet" type="text/css">
<link href="/css/announcement.css" rel="stylesheet" type="text/css">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<link href="/css/float.css" rel="stylesheet" type="text/css">
<link href="/css/member.css" rel="stylesheet" type="text/css">
<div class="main cl">

    <jsp:include page="/WEB-INF/jsp/lvxbang/user/personalHeader.jsp"></jsp:include>

    <div class="w1000 cl">
        <div class="Favorites_div">
            <div class="Favorites_div_fl fl" id="nav">
                <p class="nav">
                    <a href="javaScript:;" class="checked"
                       data-favoriteType=""><em></em><i></i><span>全部</span><span id="favorite_all">${allCount}</span></a>
                    <a href="javaScript:;" class="two"
                       data-favoriteType="plan"><em></em><i></i><span>行程</span><span id="favorite_plan">${planCount}</span></a>
                    <a href="javaScript:;" class="three"
                       data-favoriteType="recplan"><em></em><i></i><span>游记</span><span id="favorite_recplan">${recplanCount}</span></a>
                    <a href="javaScript:;" class="four"
                       data-favoriteType="scenic"><em></em><i></i><span>景点</span><span id="favorite_scenic">${scenicCount}</span></a>
                    <a href="javaScript:;" class="fives"
                       data-favoriteType="hotel"><em></em><i></i><span>酒店</span><span id="favorite_hotel">${hotelCount}</span></a>
                    <a href="javaScript:;" class="six"
                       data-favoriteType="delicacy"><em></em><i></i><span>美食</span><span id="favorite_delicacy">${delicacyCount}</span></a>
                    <a href="javaScript:;" class="two"
                       data-favoriteType="line"><em></em><i></i><span>线路</span><span id="favorite_line">${lineCount}</span></a>
                </p>
            </div>

            <div class="Favorites_div_fr fr">
                <div class="mailTablePlan">
                    <ul class="Favorites_div_fr_ul" id="favorites_">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_">
                    </div>
                    <!--pager end-->
                </div>

                <!--行程-->
                <div class="mailTablePlan disn">
                    <ul class="Favorites_div_fr_ul" id="favorites_plan">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_plan"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_plan">
                    </div>
                    <!--pager end-->
                </div>

                <!--游记-->
                <div class="mailTablePlan disn">
                    <ul class="Favorites_div_fr_ul" id="favorites_recplan">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_recplan"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_recplan">
                    </div>
                    <!--pager end-->
                </div>

                <!--景点-->
                <div class="mailTablePlan disn">
                    <ul class="Favorites_div_fr_ul" id="favorites_scenic">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_scenic"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_scenic">
                    </div>
                    <!--pager end-->
                </div>

                <!--酒店-->
                <div class="mailTablePlan disn">
                    <ul class="Favorites_div_fr_ul" id="favorites_hotel">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_hotel"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_hotel">
                    </div>
                    <!--pager end-->
                </div>

                <!--美食-->
                <div class="mailTablePlan disn">
                    <ul class="Favorites_div_fr_ul" id="favorites_delicacy">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_delicacy"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_delicacy">
                    </div>
                    <!--pager end-->
                </div>

                <!--线路-->
                <div class="mailTablePlan disn">
                    <ul class="Favorites_div_fr_ul" id="favorites_line">

                    </ul>
                    <%--<div class="m-pager st cl" id="favorites_pager_plan"></div>--%>
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl" id="favorites_pager_line">
                    </div>
                    <!--pager end-->
                </div>
            </div>

            <p class="cl"></p>
        </div>
        <p class="cl"></p><!-- #BeginLibraryItem "/lbi/paging.lbi" -->
        <p class="cl h30"></p><!-- #EndLibraryItem -->
    </div>
</div>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/popup/map_popup.jsp"></jsp:include>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/favorite/favorites.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>

<script type="text/javascript">
    $(".personal").removeClass("checked");
    $("#favorite-panel").addClass("checked");
</script>

<script type="text/html" id="tpl-favorite-item-plan">
    <li class="ft_list">
        <a href="javaScript:;" class="close" data-favoriteId="{{id}}" type="plan"></a>
        <p class="img fl"><a href="${PLAN_PATH}/plan_detail_{{favoriteId}}.html" target="_blank">
            <img data-original="{{imgPath | recommendPlanTripListImg}}"
                 original="{{imgPath | recommendPlanTripListImg}}"></a></p>
        <div class="nr fr">
            <a href="${PLAN_PATH}/plan_detail_{{favoriteId}}.html" class="name_a" target="_blank"><p class="name b cl">{{title}}</p></a>
            {{if author != null && author != ""}}
            <p class="js cl"><label>规&nbsp;划&nbsp;师：</label><span class="green">{{author}}</span></p>
            {{/if}}
            {{if days != null && days > 0}}
            <p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
            {{/if}}
            {{if mainScenics != null && mainScenics != ""}}
            <p class="js cl"><label>主要景点：</label><span>{{mainScenics}}</span></p>
            {{/if}}
            <p class="time textR">{{createTime}}</p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-favorite-item-recplan">
    <li class="ft_list">
        <a href="javaScript:;" class="close" data-favoriteId="{{id}}" type="recplan"></a>`
        <p class="img fl"><a href="${RECOMMENDPLAN_PATH}/guide_detail_{{favoriteId}}.html" target="_blank">
            <img data-original="{{imgPath | recommendPlanTripListImg}}"
                 original="{{imgPath | recommendPlanTripListImg}}"></a></p>
        <div class="nr fr">
            <a href="${RECOMMENDPLAN_PATH}/guide_detail_{{favoriteId}}.html" class="name_a" target="_blank"><p class="name b cl">{{title}}</p></a>
            {{if author != null && author != ""}}
            <p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{author}}</span></p>
            {{/if}}
            {{if days != null && days > 0}}
            <p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
            {{/if}}
            {{if mainScenics != null && mainScenics != ""}}
            <p class="js cl"><label>主要景点：</label><span>{{mainScenics}}</span></p>
            {{/if}}
            {{if content != null && content.length > 0}}
            <div class="food_js"><p class="synopsis posiR long{{if content.length > 62}} is_hover{{/if}}">{{formatFavoritesCom content 62}}</p></div>
            {{/if}}
            <p class="time textR">{{createTime}}</p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-favorite-item-scenic">
    <li class="ft_list Attractions_List scenic-node" data-id="{{favoriteId}}" data-type="scenic">
        <a href="javaScript:;" class="close" data-favoriteId="{{id}}" type="scenic"></a>
        <p class="img fl"><a href="${SCENIC_PATH}/scenic_detail_{{favoriteId}}.html" target="_blank">
            {{if imgPath != null && imgPath != ""}}
            <img class="scenic-node-img"
                 data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}?imageView2/1/w/219/h/160/q/75">
            {{else}}
            <img class="scenic-node-img"
                 data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/219/h/160/q/75"/>
            {{/if}}
        </a></p>
        <div class="nr fr">
            <a href="javaScript:;" class="stroke stroke_open"></a>
            <a href="${SCENIC_PATH}/scenic_detail_{{favoriteId}}.html" class="name_a" target="_blank"><p class="name b cl">{{title}}</p></a>
            <p class="cl hadd">
                <span class="add fl mr10{{if address.length > 16}} fav_hover is_hover{{/if}}">{{formatComment address 16}}</span>
                <a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="{{longitude}}"
                   data-ditu-baiduLat="{{latitude}}" data-ditu-name="{{title}}">查看地图</a>
            </p>
            {{if score != null && score > 0}}
            <p class="fraction cl"><b class="fs16">{{score / 20}}分</b>/5分(来自{{scorePeopleNum}}人点评)</p>
            {{/if}}
            {{if content != null && content.length > 0}}
            <div class="food_js"><p class="synopsis posiR long{{if content.length > 58}} is_hover{{/if}}">{{formatFavoritesCom content 58}}</p></div>
            {{/if}}
            <p class="synopsis2">门票价格：{{if price != null && price > 0}}<b class="Orange mr30">{{price}}元/人</b>{{else}}<b
                    class="mr30">未知</b>{{/if}}
                <a href="javascript:;" onclick="goRecommendPlanList({{favoriteId}},'scenic','{{title}}')"><span class="Orange">相关游记</span></a></p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-favorite-item-hotel">
    <li class="ft_list hotels_list">
        <a href="javaScript:;" class="close" data-favoriteId="{{id}}" type="hotel"></a>
        <p class="img fl"><a href="${HOTEL_PATH}/hotel_detail_{{favoriteId}}.html" target="_blank">
            {{if imgPath != null && imgPath != ""}}
            <img data-original="{{imgPath}}"/>
            {{else}}
            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/219/h/160/q/75"/>
            {{/if}}
            </a></p>
        <div class="nr fr">
            <a href="${HOTEL_PATH}/hotel_detail_{{favoriteId}}.html" class="posiA price round" target="_blank">
                ￥<span class="fs20 b">{{price}}</span>起
                <p class="fs16 b">选择</p>
            </a>
            <a href="${HOTEL_PATH}/hotel_detail_{{favoriteId}}.html" class="name_a" target="_blank"><p class="name b fl">{{title}}</p></a>
                <span class="hstar fl"><i class="star{{if star != null && star > 0}}{{star}}{{else}}1{{/if}}"></i></span>
            <p class="cl hadd">
                <span class="add fl mr10{{if address.length > 16}} fav_hover is_hover{{/if}}">{{formatComment address 16}}</span>
                <a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="{{lng}}"
                   data-ditu-baiduLat="{{lat}}" data-ditu-name="{{title}}">查看地图</a>
            </p>
            {{if score != null && score > 0}}
            <p class="fraction cl"><b class="fs16">{{score / 20}}分</b>/5分(来自{{scorePeopleNum}}人点评)</p>
            {{/if}}
            {{if content != null && content.length > 0}}
            <div class="food_js"><p class="synopsis posiR long{{if content.length > 62}} is_hover{{/if}}">{{formatFavoritesCom content 62}}</p></div>
            {{/if}}
            <p class="synopsis2"><a href="javascript:;"
                                    onclick="goRecommendPlanList({{favoriteId}},'hotel','{{title}}')"><span class="orange">相关游记</span></a></p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script type="text/html" id="tpl-favorite-item-delicacy">
    <li class="ft_list food_List" >
        <a href="javaScript:;" class="close" data-favoriteId="{{id}}" type="delicacy"></a>
        <p class="img fl"><a href="${DELICACY_PATH}/food_detail_{{favoriteId}}.html" target="_blank">
            {{if imgPath != null && imgPath != ""}}
            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}">
            {{else}}
            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/meishi.png?imageView2/1/w/219/h/160/q/75"/>
            {{/if}}
        </a></p>
        <div class="nr fr">
            <a href="${DELICACY_PATH}/food_detail_{{favoriteId}}.html" class="name_a" target="_blank"><p class="name b cl">{{title}}</p></a>
            <p class="js cl"><label>均&nbsp;&nbsp;价：</label><span class="green b">{{price}}元/人</span></p>
            {{if taste != null && taste != ""}}
            <p class="js cl"><label>口&nbsp;&nbsp;感：</label><span>{{taste}}</span></p>
            {{/if}}
            {{if content != null && content.length > 0}}
            <div class="food_js cl"><label>美食简介：</label>
                <div class="food_p{{if content.length > 56}} food_p_hover{{/if}}">{{formatDelicacyIntro content 56}}</div>
            </div>
            {{/if}}
        </div>
        <p class="cl"></p>
    </li>
</script>

<script type="text/html" id="tpl-favorite-item-line">
    <li class="ft_list">
        <a href="javaScript:;" class="close" data-favoriteId="{{id}}" type="line"></a>
        <p class="img fl"><a href="/line_detail_{{favoriteId}}.html" target="_blank">
            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}"
                 original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}"></a></p>
        <div class="nr fr">
            <a href="/line_detail_{{favoriteId}}.html" class="name_a" target="_blank"><p class="name b cl">{{title}}</p></a>
            {{if days != null && days > 0}}
            <p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
            {{/if}}
            {{if price != null && price > 0}}
            <p class="js cl"><label>价&nbsp;&nbsp;格：</label><span>{{price}}元</span></p>
            {{/if}}
            {{if startCity != null && startCity != ""}}
            <p class="js cl"><label>出发城市：</label><span>{{days}}</span></p>
            {{/if}}
            {{if trafficType != null && trafficType != ""}}
            <p class="js cl"><label>交&nbsp;&nbsp;通：</label><span>{{trafficType}}</span></p>
            {{/if}}
            {{if content != null && content.length > 0}}
            <!--<div class="food_js"><p class="synopsis posiR long{{if content.length > 62}} is_hover{{/if}}">{{formatFavoritesCom content 62}}</p></div>-->
            {{/if}}
        </div>
        <p class="cl"></p>
    </li>
</script>


</body>
</html>
