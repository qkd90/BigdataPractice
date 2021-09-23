<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>旅行帮美食列表</title>
    <meta name="keywords" content="旅行帮美食列表，美食查询"/>
    <meta name="description" content="旅行帮美食列表查询，更多美食尽在旅行帮"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/list.css" rel="stylesheet" type="text/css">

</head>
<body myname="strategy" class="food_List"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->
<%--<jsp:include page="/WEB-INF/jsp/lvxbang/plan/edit.jsp"></jsp:include>--%>
<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl mt10">
    <div class="w1000">
        <!--筛选-->
        <div class="mod mb10">
            <div class="mod_div h40 lh40 posiR" id="destination">
                <b class="fl mr10" id="destination_item">目的地：</b>

                <%--<p class="fl checkbox checked"><input checked="checked" type="checkbox" />厦门</p>--%>
                <%--<p class="fl checkbox" ><input type="checkbox" />上海</p>--%>
                <%--<p class="fl checkbox checked"><input checked="checked" type="checkbox" />${cityName}</p>--%>
                <c:forEach items="${cityList}" var="city">
                    <div class="fl checkbox checked" destination="${city.name}" data-id="${city.id}">
                        <span class="checkbox_i" input="options"><i></i></span>${city.name}</div>
                </c:forEach>
                <a href="#" class="fl ml10 mod_add add_more_city_button">+添加更多城市</a>
                <jsp:include page="/WEB-INF/jsp/lvxbang/common/citySelector.jsp"></jsp:include>
            </div>

            <div class="searchBox cl posiR">
                <div class="select">
                    <div class="select_div">
                        <label>菜系：</label>
                        <ul class="fore sel" id="cuisine">
                            <li class="whole"><a title="全部" href="#" class="checked" cuisineId="">全部</a></li>
                            <li><a title="闽菜" href="#" cuisineId="闽菜">闽菜</a></li>
                            <li><a title="川菜" href="#" cuisineId="川菜">川菜</a></li>
                            <li><a title="粤菜" href="#" cuisineId="粤菜">粤菜</a></li>
                            <li><a title="湘菜" href="#" cuisineId="湘菜">湘菜</a></li>
                        </ul>
                    </div>
                    <div class="select_div">
                        <label>价格区间：</label>
                        <ul class="fore sel" id="price">
                            <li class="whole"><a title="全部" href="#" class="checked" min="0" max="1000000">全部</a></li>
                            <li><a title="50元以下" href="#" min="1" max="50">50元以下</a></li>
                            <li><a title="50-100元" href="#" min="50" max="100">50-100元</a></li>
                            <li><a title="100元以上" href="#" min="100" max="1000000">100元以上</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!--搜索-->
        <div class="h50" id="nav">
            <div class="select_list nav">
                <ul class="fl" id="sort">
                    <li class="desc checked" orderColumn="shareNum" orderType="desc">热度<i></i></li>
                    <li class="asc" orderColumn="price" orderType="asc">价格<i></i></li>
                </ul>

                <div class="section_s fr posiR">
                    <div class="posiR categories" data-url="${DELICACY_PATH}/lvxbang/delicacy/suggestList.jhtml">
                        <input type="text" placeholder="输入美食名字精确搜索" id="delicacyName" value="${delicacyRequest.name}"
                               class="input">

                        <div class="posiA categories_div KeywordTips">
                            <ul id="selectDelicacyId">

                            </ul>
                        </div>
                        <!--错误-->
                        <div class="posiA categories_div cuowu textL">
                            <p class="cl">抱歉未找到相关的结果！</p>
                        </div>
                    </div>
                    <a href="javaScript:delicacyList();" class="but"></a>
                </div>
            </div>
        </div>

        <div class="cl disB textC" style="margin-top:70px;color:#666;" id="loading">
            <img src="/images/loadingx.gif"/>

            <p class="mt20">
                正在为您加载美食信息...
            </p>
        </div>
        <ul class="ft_list mb30" id="delicacyList">

        </ul>
        <!-- #BeginLibraryItem "/lbi/paging.lbi" -->
        <!--pager-->
        <p class="cl"></p>
        <img src="/images/food2.jpg" class="fr foodxx" style="margin-right:73px;"/>

        <div class="m-pager st cl">

        </div>
        <!--pager end-->
        <p class="cl h30"></p><!-- #EndLibraryItem --></div>
</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem -->

<script type="text/html" id="tpl-delicacy_list-item">
    <li>
        <p class="img fl">
			<a href="${DELICACY_PATH}/food_detail_{{id}}.html" target="_blank">
				{{if cover != null && cover != ""}}
					<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{cover}}" alt="{{name}}">
				{{else}}
					<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/meishi.png?imageView2/1/w/220/h/160/q/75" alt="{{name}}"/>
				{{/if}}
			</a>
		</p>

        <div class="nr fr lh30">
            <a href="${DELICACY_PATH}/food_detail_{{id}}.html" target="_blank"><p class="name b fl">{{name}}</p></a>
      <span class="collect2 fl favorite" data-favorite-id="{{id}}" data-favorite-type="delicacy">收藏</span>

            <p class="js cl"><label>均&nbsp;&nbsp;价：</label><span class="green b">{{price}}元</span></p>

            <p class="js cl"><label>口&nbsp;&nbsp;感：</label><span>{{taste}}</span></p>

            {{if introduction != null && introduction.length > 0}}
            <div class="food_js cl"><label>美食简介：</label>

                <div class="food_p{{if introduction.length > 110}} is_hover{{/if}}">{{formatDelicacyIntro introduction 110}}
                </div>
            </div>
            {{/if}}

        </div>
        <p class="cl"></p>
    </li>
</script>
<input type="hidden" name="cityName" id="cityName" value=""/>
<%--${cityName}"--%>
<input type="hidden" name="cityCode" id="cityCode" value=""/>
<%--${cityCode}--%>
</body>
</html>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
<script src="/js/lvxbang/delicacy/list.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/sort.js" type="text/javascript"></script>