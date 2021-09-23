<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%@ page import="com.data.data.hmly.service.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>旅行帮</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
	<link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/line/index.css?${mallConfig.resourceVersion}">
	<jsp:include page="/WEB-INF/jsp/mobile/common/baiduCount.jsp"></jsp:include>
</head>
<body>
<%@ include file="/WEB-INF/jsp/mobile/common/loading.jsp" %>
<input type="hidden" value="${empty CURRENT_LOGIN_USER_MEMBER ? '0' : '1' }" id="J_userInfo"/>
<div class="header-wrap">
    <span class="logo"></span>
    <h1 class="pageTitle"><c:if test="${empty planName}">线路列表</c:if><c:if test="${!empty planName}">"${planName}"搜索结果</c:if> </h1>
    <a class="search" href="#search" onclick="showSearch();"></a>
</div>
<div class="main-wrap">
	<div id="J_index_tab" class="ui-tab">
		<div class="recommend-wrap <c:if test='${type == 2 }'>hide</c:if>">
			<div id="J_list_tab" class="list-wrap front-wrap drop-down-wrap">
				<ul class="list-title cf">
					<li class="select-block select-place">
						<span><b><c:if test="${!empty cityCode}">${cityName}</c:if><c:if test="${empty cityCode}">目的地</c:if></b> <i class="icon-arrow-down"></i></span>
					</li>
					<li class="select-block select-content">
						<span><b>天数</b><i class="icon-arrow-down"></i></span>
					</li>
					<li class="select-block select-sortway">
						<span><b>花费</b><i class="icon-arrow-down"></i></span>
					</li>
					<li class="select-block select-brain">
						<span><b>智能排序</b><i class="icon-arrow-down"></i></span>
					</li>
				</ul>
				<form id="J_select-wrap">
				<input type="hidden" id="cityId" name="cityId" value="${cityCode}">
				<input type="hidden" id="dayL" name="lowestDay" value="${lowestDay}">
				<input type="hidden" id="dayU" name="highestDay" value="${highestDay}">
                <input type="hidden" id="cityNames" name="cityNames" value="${cityNames}"/>
				<div class="list-wrap select-wrap">
					<!--城市-->
					<div class="list-content select-place-wrap hide auto-panel">
						<div class="second-menu cf">
							<ul class="left-menu">
								<li class="left-menu-select all-city">
									<span>不限</span>
								</li>
								<li class="on left-menu-select">
									<span>热门<i class="icon-arrow-right"></i></span>
									<ul class="right-menu every-content whole-row">
										<li data-id="" class="none active"></li>
										<li data-id="350200">厦门</li>
										<li data-id="110100" >北京</li>
										<li data-id="310100" >上海</li>
										<li data-id="330100" >杭州</li>
										<li data-id="513225" >九寨沟</li>
										<li data-id="532900" >大理</li>
										<li data-id="460200" >三亚</li>
										<li data-id="210200" >大连</li>
										<li data-id="370200" >青岛</li>
										<li data-id="610100" >西安</li>
										<li data-id="430800" >张家界</li>
										<li data-id="341000" >黄山</li>
									</ul>
								</li>
								<li class="left-menu-select">
									<span>A-G<i class="icon-arrow-right"></i></span>
									<ul class="right-menu every-content whole-row hide">
									    <li data-id="" class="none"></li>
										<!-- <li data-id="110100">北京</li> -->
										<li data-id="450500">北海</li>
										<li data-id="510100">成都</li>
										<li data-id="130800" class="">承德</li>
										<li data-id="500100" class="">重庆</li>
										<li data-id="620982" class="">敦煌</li>
										<!-- <li data-id="210200" class="">大连</li> -->
										<li data-id="350100" class="">福州</li>
										<li data-id="433123" class="">凤凰</li>
										<!-- <li data-id="532900" class="">大理</li> -->
										<li data-id="520100" class="">贵阳</li>
										<li data-id="450300" class="">桂林</li>
									</ul>
								</li>
								<li class="left-menu-select">
									<span>H-L<i class="icon-arrow-right"></i></span>
									<ul class="right-menu every-content whole-row hide">
									    <li data-id="" class="none"></li>
										<li data-id="150100" class="">呼和浩特</li>
										<li data-id="150700" class="">呼伦贝尔</li>
										<!-- <li data-id="341000" class="">黄山</li> -->
										<!-- <li data-id="330100" class="">杭州</li> -->
										<li data-id="460100" class="">海口</li>
										<!-- <li data-id="350200" class="">华山</li> -->
										<!-- <li data-id="513225" class="">九寨沟</li> -->
										<li data-id="360200" class="">景德镇</li>
										<li data-id="370100" class="">济南</li>
										<!-- <li data-id="350200" class="">泸沽湖</li> -->
										<li data-id="410300" class="">洛阳</li>
										<li data-id="540100" class="">拉萨</li>
										<li data-id="620100" class="">兰州</li>
										<li data-id="511100" class="">乐山</li>
										<li data-id="530100" class="">昆明</li>
										<li data-id="530700" class="">丽江</li>
										<li data-id="360402" class="">庐山</li>
										<li data-id="350800" class="">龙岩</li>
									</ul>
								</li>
								<li class="left-menu-select">
									<span>M-T<i class="icon-arrow-right"></i></span>
									<ul class="right-menu every-content whole-row hide">
									    <li data-id="" class="none"></li>
										<li data-id="130300" class="">秦皇岛</li>
										<!-- <li data-id="350200" class="">青海湖</li> -->
										<li data-id="450100" class="">南宁</li>
										<!-- <li data-id="370200" class="">青岛</li> -->
										<li data-id="320100" class="">南京</li>
										<li data-id="360100" class="">南昌</li>
										<li data-id="140728" class="">平遥</li>
										<li data-id="330600" class="">绍兴</li>
										<li data-id="440300" class="">深圳</li>
										<!-- <li data-id="460200" class="">三亚</li> -->
										<!-- <li data-id="310100" class="">上海</li> -->
										<li data-id="320500" class="">苏州</li>
										<li data-id="652101" class="">吐鲁番</li>
										<li data-id="530522" class="">腾冲</li>
										<li data-id="370902" class="">泰山</li>
										<li data-id="120100" class="">天津</li>
										<li data-id="350900">宁德</li>
										<li data-id="350300">莆田</li>
										<li data-id="350500">泉州</li>
										<li data-id="350400">三明</li>
									</ul>	
								</li>
								<li class="left-menu-select">
									<span>W-Z<i class="icon-arrow-right"></i></span>
									<ul class="right-menu every-content whole-row hide">
									    <li data-id="" class="none"></li>
										<li data-id="533421" class="">香格里拉</li>
										<li data-id="650100" class="">乌鲁木齐</li>
										<li data-id="532800" class="">西双版纳</li>
										<li data-id="350782" class="">武夷山</li>
										<!-- <li data-id="350200" class="">五台山</li> -->
										<li data-id="469001" class="">五指山</li>
										<!-- <li data-id="350200" class="">乌镇</li> -->
										<!-- <li data-id="350200" class="">厦门</li> -->
										<li data-id="420100" class="">武汉</li>
										<li data-id="371000" class="">威海</li>
										<li data-id="630100" class="">西宁</li>
										<!-- <li data-id="350200" class="">周庄</li> -->
										<!-- <li data-id="610100" class="">西安</li> -->
										<!-- <li data-id="630100" class="">西藏</li> -->
										<!-- <li data-id="350200" class="">西塘</li> -->
										<li data-id="361130" class="">婺源</li>
										<li data-id="320200" class="">无锡</li>
										<!-- <li data-id="430800" class="">张家界</li> -->
										<li data-id="410100" class="">郑州</li>
										<li data-id="440400" class="">珠海</li>
										<li data-id="640100" class="">银川</li>
										<li data-id="420500" class="">宜昌</li>
										<li data-id="370600" class="">烟台</li>
										<li data-id="321000" class="">扬州</li>
										<li data-id="350600">漳州</li>
									</ul>
								</li>
							</ul>
							
						</div>
						<div class="bottom"></div>
					</div>
					<!-- 天数 -->
					<div class="list-content select-content-wrap hide auto-panel">
						<ul class="every-content whole-row day-counts">
							<li data-id="0" data-day-L="" data-day-U="" class="active">不限</li>
							<li data-id="1" data-day-L="1" data-day-U="3" class="">1-3天</li>
							<li data-id="2" data-day-L="4" data-day-U="7" class="">4-7天</li>
							<li data-id="3" data-day-L="8" data-day-U="14" class="">8-14天</li>
							<li data-id="4" data-day-L="14" data-day-U="" class="">14天以上</li>
						</ul>
						<div class="bottom"></div>
					</div>
					<!-- 花费-->
					<div class="list-content select-spend-wrap hide auto-panel">
						<div class="span-wrap cf">
							<input type="hidden" id="planCostL" name="lowestPrice" value="" placeholder="最低价格"/>
							<input type="hidden" id="planCostU" name="highestPrice" value="" placeholder="最高价格" />
							<ul class="every-content whole-row">
								<li class="active">不限</li>
								<li class="">0~1000</li>
								<li class="">1000~3000</li>
								<li class="">3000~5000</li>
								<li class="">5000~不限</li>
							</ul>
						</div>
						<div class="bottom"></div>
					</div>
					<!-- 智能排序 -->
					<div class="list-content auto-panel select-brain-wrap hide">
						<input type="hidden" id="orderColumn" name="orderColumn" value="" placeholder="排序条件"/>
						<input type="hidden" id="orderType" name="orderType" value="" placeholder="排序顺序"/>
						<ul class="every-content whole-row">
							<li orderColumn="" orderType="" class=" active">智能排序</li>
							<%--<li orderColumn="quote_num" orderType="desc">引用最多</li>--%>
							<li orderColumn="lineDay" orderType="desc">天数最多</li>
							<li orderColumn="lineDay" orderType="asc">天数最少</li>
							<li orderColumn="disCountPrice" orderType="desc">花费最高</li>
							<li orderColumn="disCountPrice" orderType="asc">花费最低</li>
						</ul>
						<div class="bottom"></div>
					</div>
				</div>
				</form>
			</div>
			<div class="trip-list-wrap">
				<ul id="J_recommend_list" class="recommend-list trip-list list-ul">
					<!-- 推荐行程列表 -->
				</ul>
			</div>
		</div>
		
		<div class="my-wrap <c:if test='${type != 2 }'>hide</c:if>">
			<div class="login-wrap ${empty CURRENT_LOGIN_USER_MEMBER ? '' : 'hide'}">
				<p>您还没有登录～</p>
				<a class="login-btn" href="/m/login">立即登录</a>
			</div>
			<!-- <div id="J_my_list_tab" class="list-wrap front-wrap myplan-front "></div> -->
			<div class="trip-list-wrap">
				<ul id="J_my_list" class="recommend-list trip-list myplan list-ul">
				</ul>
			</div>
			<div class="noplan-tips-wrap hide">
                 <img src="/images/indeximg/tuzi.png" width="120px;"/>
                 <p>您还没有属于自己的行程<br>小帮推荐您去...</p>
                 <div class="rcm-tips-wrap cf">
                     <a href="javascript:recommend();">推荐行程</a>
                     <a href="/plan/create">私人定制</a>
                     <a href="/friends/post" class="r-mrg0">约伴互助</a>
                 </div>
             </div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/mobile/common/footer-nav.jsp"></jsp:include>

<div id="J_search" class="search-wrap">
	<div class="header-wrap">
		<a class="icon-menu left-btn" href="javascript:closePanel();">返回</a>
		<form id="J_search-form" method="post" action="/planList">
		<div class="search-top-content">
			<div class="search-content">
				<input type="text" placeholder="请输入行程名关键字" id="searchCon" name="searchCon" value="${planName}"/> <label for="searchCon"></label>
			</div>
		</div>
		<a id="J_search-plan" class="right-btn search-btn" href="javascript:searchPlan();">搜索</a>
		</form>
	</div>
	<div class="search-main">
		<ul class="tips-wrap">
			<li><a class="tips-1">厦门</a></li>
			<li><a class="tips-2">鼓浪屿</a></li>
			<li><a class="tips-3">上海</a></li>
			<li><a class="tips-4">北京</a></li>
			<li><a class="tips-1">三日游</a></li>
		</ul>
	</div>
</div>

<%@ include file="/WEB-INF/jsp/mobile/common/common-script.jsp" %>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/simple.ui.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/mobile/line/index.js?${mallConfig.resourceVersion}"></script>
<script type=text/javascript src="${mallConfig.resourcePath}/js/dropdown.select.js?${mallConfig.resourceVersion}"></script>
<script type="text/html" id="tpl_recommend-list">
    <li id="recommend_{{id}}" class="single-list" onclick="recommendDetail({{id}})">
        <img class="signel-img lazy" data-original="{{productImg}}" src="/static/image/transparent-bg.png"/>
        <h3 class="signel-title">{{name}}</h3>
        <p class="signel-p">{{lineDay}}天｜大约花费{{disCountPrice}}元</p>
        <p class="signel-p">城市：{{if crossCitys}}{{crossCitys}}{{else}}暂无城市信息{{/if}}</p>
        <p class="signel-p quote-identifi cf"><i class=""></i>已被（没数据）人引用</p>
    </li>
</script>
<script type="text/html" id="tpl_myplan-list">
<li id="my_{{id}}" class="single-list" startTime="{{startTime | ldf:'yyyy-MM-dd'}}">
    <a class="del-btn" onclick="delectPlan({{id}})">删除行程</a>
   <div class="right-content" onclick="myplanDetail({{id}})">
        <img class="signel-img lazy" data-original="{{coverSmall | imgSrc}}" src="/static/image/transparent-bg.png"/>
        <h3 class="signel-title">{{planName}}</h3>
        <p class="signel-p">{{planDays}}天｜大约花费{{planCost}}元</p>
        <p class="signel-p">城市：{{if cityName}}{{cityName}}{{else}}暂无城市信息{{/if}}</p>
   </div>
        
    {{if startTime}}
        <a onclick="showDate({{id}})" class="start-time green">{{startTime | ldf:'yyyy-MM-dd 星期w', '暂无出发时间'}}</a>
    {{else}}
        <a class="date-identifi start-time cf" onclick="showDate({{id}})"><i class=""></i>未设置出发日期</a>
    {{/if}}
</li>
</script>

<script type="text/html" id="tpl_myplan-time">
	<a onclick="showDate({{id}})" class="start-time green">{{startTime}}</a>
</script>

<script type="text/html" id="tpl_myplan-null-time">
	<a class="date-identifi start-time cf" onclick="showDate({{id}})"><i class=""></i>未设置出发日期</a>
</script>

<script type="text/html" id="tpl_load-my">
<a id="J_load-my" class="load-my load-more hide" onclick="whichPage(this)">加载更多</a>
</script>

<script type="text/html" id="tpl_load-recommend">
<a id="J_load-recommend" class="load-recommend load-more hide" onclick="whichPage(this)">加载更多</a>
</script>

</body>
</html>