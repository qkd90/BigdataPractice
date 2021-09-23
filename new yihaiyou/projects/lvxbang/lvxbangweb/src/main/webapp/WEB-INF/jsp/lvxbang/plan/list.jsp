<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2016-04-29,0029
  Time: 11:56
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
  <title>旅行帮线路列表</title>
  <meta name="keywords" content="线路列表"/>
  <meta name="description" content="线路列表，更多好线路尽在旅行帮"/>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/index.css" rel="stylesheet" type="text/css">
  <link href="/css/float.css" rel="stylesheet" type="text/css">
  <link href="/css/list.css" rel="stylesheet" type="text/css">
</head>
<body myname="TripPlanning" class="plan_list">
<%@include file="../common/header.jsp" %>
<!-- #EndLibraryItem -->
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
        <a href="javascript:;" class="fl ml10 mod_add add_more_city_button" id="add_more_city">＋添加更多城市</a>
        <jsp:include page="/WEB-INF/jsp/lvxbang/common/citySelector.jsp"></jsp:include>
      </div>
      <div class="searchBox cl posiR" id="searchBox">
        <div class="select" style="height: auto;">
          <div class="select_div">
            <label>出行时间</label>
            <ul class="fore sel" id="month">
              <li class="whole"><a title="全部" href="javascript:;" class="checked" monthRange="">全部</a></li>
              <li><a href="javascript:;" monthRange="1-3">1-3月</a></li>
              <li><a href="javascript:;" monthRange="4-6">4-6月</a></li>
              <li><a href="javascript:;" monthRange="7-9">7-9月</a></li>
              <li><a href="javascript:;" monthRange="10-12">10-12月</a></li>
            </ul>
          </div>
          <div class="select_div">
            <label>出行天数</label>
            <ul class="fore sel" id="day">
              <li class="whole"><a title="全部" href="javascript:;" class="checked" dayRange="">全部</a></li>
              <li><a href="javascript:;" dayRange="0-3">3天及以下</a></li>
              <li><a href="javascript:;" dayRange="4-7">4至7天</a></li>
              <li><a href="javascript:;" dayRange="8-14">8至14天</a></li>
              <li><a href="javascript:;" dayRange="15-999">14天以上</a></li>
            </ul>
          </div>
          <div class="select_div">
          <label>人均预算</label>
          <ul class="fore sel" id="cost">
          <li class="whole"><a title="全部" href="javascript:;" class="checked" costRange="">全部</a></li>
          <li><a href="javascript:;" costRange="1-999">1-1000元</a></li>
          <li><a href="javascript:;" costRange="1000-3000">1000-3000元</a></li>
          <li><a href="javascript:;" costRange="3000-5000">3000-5000元</a></li>
          <li><a href="javascript:;" costRange="5000-8000">5000-8000元</a></li>
          <li><a href="javascript:;" costRange="8001-999999">8000元以上</a></li>
          </div>
        </div>
      </div>
    </div>

    <!--搜索-->
    <div class="h50" id="nav">
      <div class="select_list nav">
        <ul class="fl" id="sort">
          <li class="desc checked" orderType="desc" orderColumn="createTime">最新线路<i></i></li>
          <li class="desc" orderType="desc" orderColumn="viewNum">浏览次数<i></i></li>
          <li class="desc" orderType="desc" orderColumn="quoteNum">复制次数<i></i></li>
          <li class="desc" orderType="desc" orderColumn="collectNum">收藏次数<i></i></li>
        </ul>

        <div class="section_s fr posiR">
          <div class="posiR categories" data-url="${PLAN_PATH}/lvxbang/plan/suggest.jhtml">
            <input type="text" placeholder="输入线路名字精确搜索" value="${planSearchRequest.name}" class="input" id="planName">

            <div class="posiA categories_div KeywordTips">
              <ul>
              </ul>
            </div>
            <!--错误-->
            <div class="posiA categories_div cuowu textL">
              <p class="cl">抱歉未找到相关的结果！</p>
            </div>
          </div>
          <a href="javaScript:;" class="but" id="search-button"></a>
        </div>
      </div>
    </div>

    <div class="cl disB textC" style="margin-top:70px;color:#666;" id="loading">
      <img src="/images/loadingx.gif"/>

      <p class="mt20">
        正在为您加载线路信息...
      </p>
    </div>
    <ul class="ft_list mb30" id="plan">
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
<script type="text/html" id="tpl-plan-item">
  <li>
    <p class="img fl">
      <a href="${PLAN_PATH}/plan_detail_{{id}}.html" target="_blank">
        <img data-original="{{cover | recommendPlanTripListImg}}" original="{{cover | recommendPlanTripListImg}}" alt="{{name}}">
      </a>
    </p>

    <div class="nr fr lh30">
      <a href="${PATH}/plan_detail_{{id}}.html" target="_blank">
        <p class="name b fl title_name{{if name.length > 30}} title_hover{{/if}}">{{formatComment name 30}}</p></a>

      <div style="height: 30px"><span class="collect2 fl favorite" data-favorite-id="{{id}}" data-favorite-type="plan">收藏</span></div>

      <p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{nickName}}</span></p>

      <p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>

      <p class="js cl"><label>人均预算：</label><span>¥{{cost}}</span></p>
      {{if passScenics != null && passScenics != ""}}
      <p class="js cl"><label>主要景点：</label><span>{{passScenics}}</span></p>
      {{/if}}
      <br>
      <div class="cl r_info_div">
        <ul class="r_list_service_u mb10">
          <li class="r_info viewNum"><i class="view_icon"></i><span>{{viewNum}}</span></li>
          <li class="r_info collectNum"><i class="collect_icon"></i><span>{{collectNum}}</span></li>
          <li class="r_info quoteNum"><i class="quote_icon"></i><span>{{quoteNum}}</span></li>
        </ul>
      </div>
    </div>
    <p class="cl"></p>
  </li>
</script>
<script src="/js/lib/template.helper.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/plan/list.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/sort.js" type="text/javascript"></script>
<script src="/js/lvxbang/city.selector.js" type="text/javascript"></script>
</body>
</html>
