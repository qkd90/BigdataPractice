<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/12/31
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>旅行帮游记列表</title>
    <meta name="keywords" content="游记列表"/>
    <meta name="description" content="游记列表，更多好游记尽在旅行帮"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/list.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="recomplan_list">
<%@include file="../common/header.jsp" %>
<!-- #EndLibraryItem -->
<div class="main cl mt10">
    <div class="w1000">
        <!--筛选-->
        <div class="mod mb10">
            <div class="mod_div h40 lh40 posiR" id="destination">
                    <b class="fl mr10">目的地：</b>
                    <c:forEach items="${areas}" var="area">
                        <div class="fl checkbox checked" destination="${area.name}" data-id="${area.id}">
                            <span class="checkbox_i" input="options"><i></i></span>${area.name}</div>
                    </c:forEach>
                    <a href="javascript:;" class="fl ml10 mod_add add_more_city_button" id="add_more_city">＋添加更多城市</a>
                    <jsp:include page="/WEB-INF/jsp/lvxbang/common/citySelector.jsp"></jsp:include>
                <c:if test="${relatedId != null}">
                    <br>
                    <a href="javascript:;" class="related_item" style="color:white;" data-staute="related"
                       relatedType="${type}" relatedId="${relatedId}">
                        <i class="close"></i>相关游记: ${name}</a>
                </c:if>
            </div>
            <div class="searchBox cl posiR" id="searchBox">
                <%--<p class="more"><a href="javaScript:;" class="posiR"><span>更多查询条件</span><b class="ico"></b></a></p>--%>

                <div class="select" style="height: auto;">
                    <div class="select_div">
                        <label>必&nbsp;&nbsp;去</label>
                        <ul class="fore sel" id="tag_t10">
                        </ul>
                    </div>
                    <div class="select_div">
                        <label>出发日期</label>
                        <ul class="fore sel" id="month_range">
                            <li class="whole"><a title="全部" href="#" class="checked" name="dayRange"
                                                 data-status="1">全部</a></li>
                            <li><a href="#" name="monthRange" monthRange="1-3">1-3月</a></li>
                            <li><a href="#" name="monthRange" monthRange="4-6">4-6月</a></li>
                            <li><a href="#" name="monthRange" monthRange="7-9">7-9月</a></li>
                            <li><a href="#" name="monthRange" monthRange="10-12">10-12月</a></li>
                        </ul>
                    </div>
                    <div class="select_div">
                        <label>出行天数</label>
                        <ul class="fore sel" id="day_range">
                            <li class="whole"><a title="全部" href="#" class="checked" name="dayRange"
                                                 data-status="1">全部</a></li>
                            <li><a href="#" name="dayRange" dayRange="0-3">3天以下</a></li>
                            <li><a href="#" name="dayRange" dayRange="4-7">4-7天</a></li>
                            <li><a href="#" name="dayRange" dayRange="8-14">8-14天</a></li>
                            <li><a href="#" name="dayRange" dayRange="15">14天以上</a></li>
                        </ul>
                    </div>
                    <%--<div class="select_div">--%>
                        <%--<label>行程主题</label>--%>
                        <%--<ul class="fore sel" id="theme_tag">--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                    <%--<div class="select_div">--%>
                        <%--<label>人均费用</label>--%>
                        <%--<ul class="fore sel" id="cost_range">--%>
                            <%--<li><a title="全部" href="#" class="checked" name="costRange" data-status="1">全部</a></li>--%>
                            <%--<li><a href="#" name="costRange" costRange="1-999">1-999元</a></li>--%>
                            <%--<li><a href="#" name="costRange" costRange="1000-3000">1000-3000元</a></li>--%>
                            <%--<li><a href="#" name="costRange" costRange="3000-5000">3000-5000元</a></li>--%>
                            <%--<li><a href="#" name="costRange" costRange="5001">5000元以上</a></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                    <%--<div class="select_div">--%>
                        <%--<label>推 荐 人</label>--%>
                        <%--<ul class="fore sel" id="user_tag">--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>

        <!--搜索-->
        <div class="h50" id="nav">
            <div class="select_list nav">
                <ul class="fl" id="orderType">
                    <li class="desc checked" orderType="desc" OrderBy="createTime">最新游记<i></i></li>
                    <li class="desc" orderType="desc" orderBy="viewNum">浏览次数<i></i></li>
                    <li class="desc" orderType="desc" orderBy="quoteNum">复制次数<i></i></li>
                    <li class="desc" orderType="desc" orderBy="collectNum">收藏次数<i></i></li>
                </ul>

                <div class="section_s fr posiR">
                    <div class="posiR categories" data-url="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/suggest.jhtml">
                        <input type="text" placeholder="输入游记名字精确搜索" value="${recommendPlanSearchRequest.name}" class="input" id="r_name">

                        <div class="posiA categories_div KeywordTips">
                            <ul>
                            </ul>
                        </div>
                        <!--错误-->
                        <div class="posiA categories_div cuowu textL">
                            <p class="cl">抱歉未找到相关的结果！</p>
                        </div>
                    </div>
                    <a href="javaScript:getRecommendPlanList();" class="but"></a>
                </div>
            </div>
        </div>

        <div class="cl disB textC" style="margin-top:70px;color:#666;" id="loading">
            <img src="/images/loadingx.gif"/>

            <p class="mt20">
                正在为您加载游记信息...
            </p>
        </div>
        <ul class="ft_list mb30" id="recommendPlan_item">
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
<%@include file="../common/footer.jsp" %>
<%--<jsp:include page="/WEB-INF/jsp/lvxbang/popup/popup.jsp"></jsp:include>--%>
<script type="text/html" id="recommendPlan_item_template">
    <li>
        <p class="img fl">
            <a href="${RECOMMENDPLAN_PATH}/guide_detail_{{id}}.html" target="_blank">
                <img data-original="{{cover | recommendPlanTripListImg}}" original="{{cover | recommendPlanTripListImg}}" alt="{{name}}">
            </a>
        </p>

        <div class="nr fr lh30">
            <a href="${RECOMMENDPLAN_PATH}/guide_detail_{{id}}.html" target="_blank">
                <p class="name b fl title_name{{if name.length > 30}} title_hover{{/if}}">{{formatComment name 30}}</p></a>

            <div style="height: 30px"><span class="collect2 fl favorite" data-favorite-id="{{id}}" data-favorite-type="recplan">收藏</span></div>

            <p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{nickName}}</span></p>

            <p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
            {{if passScenics != null && passScenics != ""}}
            <p class="js cl"><label>主要景点：</label><span>{{passScenics}}</span></p>
            {{/if}}
            {{if description !=null && description.length > 0}}
            <p class="synopsis posiR mt10"><i></i><span class="fl long{{if description.length > 76}} is_hover{{/if}}">{{formatComment description 76}}</span><i
                    class="fl"></i></p>
            {{/if}}
            <br>
            <div class="cl r_info_div">
                <ul class="r_list_service_u mb10">
                    <li class="r_info viewNum"><i class="view_icon"></i><span>{{viewNum}}</span></li>
                    <li class="r_info collectNum"><i class="collect_icon"></i><span>{{collectNum}}</span></li>
                    <li class="r_info quoteNum"><i class="quote_icon"></i><span>{{quoteNum}}</span></li>
                </ul>
                <p class="time textR">{{createTime}}</p>
            </div>
        </div>
        <p class="cl"></p>
    </li>
</script>
<script src="/js/lib/template.helper.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/recommendPlan/recommendPlanList.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/sort.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
</body>
</html>
