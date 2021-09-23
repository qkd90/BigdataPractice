<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2015-12-31,0031
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>旅行帮景点列表页</title>
    <meta name="keywords" content="景点列表，景点查询"/>
    <meta name="description" content="根据用户需要，通过景点等级，景点价格，景点主题来查询景点，更多必去景点尽在旅行帮"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/list.css" rel="stylesheet" type="text/css">
</head>
<body myname="mall" class="Attractions_List fill_orders"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<div class="main cl mt10">
    <div class="w1000">
        <!--筛选-->
        <div class="mod mb10">
            <div class="mod_div h40 lh40 posiR" id="destination">
                <b class="fl mr10">目的地：</b>
                <c:forEach items="${cityList}" var="city">
                    <div class="fl checkbox checked" destination="${city.name}" data-id="${city.id}">
                        <span class="checkbox_i" input="options"><i></i></span>${city.name}</div>
                </c:forEach>
                <a href="javaScript:;" class="fl ml10 mod_add add_more_city_button">+添加更多城市</a>
                <jsp:include page="/WEB-INF/jsp/lvxbang/common/citySelector.jsp"></jsp:include>
            </div>
            <c:if test="${father != null}">
            <div class="mod_div h40 lh40 related_item cl" id="father" data-father="${father}">
                <a href="javaScript:;" style="color: white">
                    <i class="close"></i>父景点：${fatherName}
                </a>
            </div>
            </c:if>
            <div class="searchBox cl posiR">
                <div class="select">
                    <div class="select_div">
                        <label>景点等级</label>
                        <ul class="fore sel" id="level">
                            <li class="whole"><a title="全部" href="javaScript:;" class="checked" level="">全部</a></li>
                            <li><a title="5A" href="javaScript:;" level="AAAAA">5A</a></li>
                            <li><a title="4A" href="javaScript:;" level="AAAA">4A</a></li>
                            <li><a title="3A" href="javaScript:;" level="AAA">3A</a></li>
                            <li><a title="2A" href="javaScript:;" level="AA">2A</a></li>
                        </ul>
                    </div>
                    <div class="select_div">
                        <label>景点主题</label>
                        <input type="hidden" id="selectTheme" value="${theme}"/>
                        <ul class="fore sel" id="theme">
                            <li class="whole"><a title="全部" href="javaScript:;" class="checked" themeName="">全部</a></li>
                        </ul>
                    </div>
                    <div class="select_div">
                        <label>价格区间</label>
                        <ul class="fore sel" id="price">
                            <li class="whole"><a title="全部" href="javaScript:;" class="checked" min="0"
                                                 max="1000000">全部</a></li>
                            <li><a title="50元以下" href="javaScript:;" min="0" max="50">50元以下</a></li>
                            <li><a title="50-100元" href="javaScript:;" min="50" max="100">50-100元</a></li>
                            <li><a title="100元以上" href="javaScript:;" min="100" max="1000000">100元以上</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <%--<div class="select-condition" style="display:none;"></div>--%>

        </div>

        <!--搜索-->
        <div class="h50" id="nav">
            <div class="select_list nav" style="padding-right: 15px;padding-left: 45px;">
                <ul class="fl" id="sort">
                    <li class="desc checked" orderColumn="ranking" orderType="desc">热度<i></i></li>
                    <li class="desc" orderColumn="productScore" orderType="desc">评分<i></i></li>
                    <li class="asc" orderColumn="price" orderType="asc">价格<i></i></li>
                </ul>
                <%--<span class="fr qingkong" onclick="location.reload();" style="height: 20px;line-height: 20px;width: 50px;text-align: center;display: block;background: #ffcfb2;color: #fff;--%>
                       <%--cursor: pointer;margin-top: 13px;margin-left: 5px;border-radius: 3px;">撤销条件</span>--%>
                <%--<a style="z-index: 999;--%>
    <%--z-index: 999;--%>
    <%--background: url(/images/Floating_ico.png) 0 -50px no-repeat;--%>
    <%--width: 15px;--%>
    <%--height: 15px;--%>
    <%--display: none;--%>
    <%--float: right;--%>
    <%--margin-top: 17px;"></a>--%>

                <div class="section_s fr posiR" >
                    <div class="posiR categories" data-url="${SCENIC_PATH}/lvxbang/scenic/suggest.jhtml">
                        <input id="scenicName" style="width:170px;" type="text" placeholder="输入景点名字精确搜索" value="${scenicName}" class="input">
                        <a class="qk" style="z-index: 999;
    z-index: 999;
    background: url(/images/Floating_ico.png) 0 -25px no-repeat;
    width: 15px;
    height: 15px;
    <c:if test="${scenicName != null && scenicName != ''}">display: block;</c:if>
    margin-top: -27px;
    margin-left: 170px;"></a>
                        <div class="posiA categories_div KeywordTips">
                            <ul>
                            </ul>
                        </div>
                        <!--错误-->
                        <div class="posiA categories_div cuowu textL" style="width:82%">
                            <p class="cl">抱歉未找到相关的结果！</p>
                        </div>

                    </div>

                    <a href="javaScript:scenicList();" class="but"></a>

                </div>

            </div>
        </div>

        <div class="cl disB textC" style="margin-top:70px;color:#666;" id="loading">
            <img src="/images/loadingx.gif"/>

            <p class="mt20">
                正在为您加载景点信息...
            </p>
        </div>
        <ul class="ft_list mb30" id="scenic">
        </ul>
        <p class="cl"></p>
        <img src="/images/food2.jpg" class="fr foodxx" style="margin-right:73px;"/>

        <div class="m-pager st cl"></div>

        <!-- #BeginLibraryItem "/lbi/paging.lbi" -->
        <p class="cl h30"></p><!-- #EndLibraryItem --></div>
</div>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/popup/map_popup.jsp"></jsp:include>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
</body>
</html>
<script type="text/html" id="tpl-scenic-list-item">
    <li class="scenic-node" data-id="{{id}}" data-type="scenic" data-name="{{name}}" data-img="{{cover}}"
        data-price="{{price}}" data-adviceMinute="{{adviceMinute}}">
        <p class="img fl">
            <a href="${SCENIC_PATH}/scenic_detail_{{id}}.html" target="_blank">
				{{if cover != null && cover != ""}}
                <img class="scenic-node-img" alt="{{name}}"
                     data-original="{{cover | recommendPlanTripListImg}}">
				{{else}}
                <img class="scenic-node-img" alt="{{name}}"
                     data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/300/h/218/q/75"/>
				{{/if}}
            </a>
        </p>

        <div class="nr fr lh30">

            <div class="cl at_name">
                <a href="javaScript:;" class="stroke stroke_open"></a>
                <a href="${SCENIC_PATH}/scenic_detail_{{id}}.html" class="name b fl" target="_blank">{{name}}{{if level.length >
                    0}}({{level.length}}A){{/if}}</a>
                {{if fatherName.length > 0}}
                <label class="fl name_l">[属于{{fatherName}}]</label>
                {{/if}}
                <span class="collect2 fl favorite" data-favorite-id="{{id}}" data-favorite-type="scenic">收藏</span>
            </div>

            <div class="cl at_add">
                <p class="add fl mr10 {{if address.length > 15}}simple is_hover{{/if}}">{{address}}</p>
                {{if id < 2000433}}
                <a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="{{longitude}}"
                   data-ditu-baiduLat="{{latitude}}" data-ditu-name="{{name}}">查看地图</a>
                {{/if}}
            </div>

            <p class="cl at_num fraction">
                {{if id < 2000433}}
                <b class="fs16">{{productScore / 20}}分</b>/5分
                {{/if}}
                <%--(<span>来自{{commentCount}}人点评</span>)--%>
            </p>

            {{if shortComment != null && shortComment.length > 0}}
            <div class="cl at_js">
                <p class="synopsis posiR"><i></i><span class="fl long {{if shortComment.length > 76}}is_hover{{/if}}">{{formatComment shortComment 76}}</span><i
                    class="fl"></i>
                </p>
            </div>
            {{/if}}

            <p class="synopsis2">门票价格：<b class="Orange mr30">{{if price != null && price !=
                0 && id < 2000433}}{{price}}元/人{{else}}未知{{/if}}</b>
                {{if recommendPlanName.length >0}}
                相关游记：<a href="${RECOMMENDPLAN_PATH}/guide_detail_{{recommendPlanId}}.html" target="_blank"><b class="green">
                    {{if recommendPlanName.length >25}}
                    {{recommendPlanName.substring(0,23)}}...
                    {{else}}
                    {{recommendPlanName}}
                    {{/if}}
                </b></a>
                {{/if}}
            </p>
        </div>
        <p class="cl"></p>
    </li>
</script>
<%--<script type="text/html" id="select-condition">--%>
    <%--<a   style="display: inline-block;--%>
                <%--padding-right: 20px;--%>
                <%--height: 20px;--%>
                <%--line-height: 20px;--%>
                <%--margin-left: 5px;--%>
                <%--background: url(http://pic.c-ctrip.com/packages111012/icon_pkg.png?120111.png) #80d4b3 100% -830px no-repeat;--%>
                <%--color: #fff;--%>
                <%--cursor: pointer;--%>
                <%--font-weight: bold;--%>
                <%--margin-bottom: -5px;--%>
                <%--font-weight: normal;--%>
                <%--margin-top: 8px;">--%>
          <%--<span  style="float: left;--%>
                        <%--background-color: #34bf82;--%>
                        <%--padding: 0 5px;--%>
                        <%--margin-right: 10px;--%>
                        <%--font-weight: normal;">--%>
            <%--{{condition}}</span>{{content}}--%>
    <%--</a>--%>
<%--</script>--%>

<script src="/js/lvxbang/scenic/list.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
<script src="/js/lvxbang/sort.js" type="text/javascript"></script>
<script src="/js/lvxbang/hash.js" type="text/javascript"></script>