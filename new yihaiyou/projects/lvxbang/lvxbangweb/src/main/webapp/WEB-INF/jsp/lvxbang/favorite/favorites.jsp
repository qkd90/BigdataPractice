<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>旅行帮</title>
    <meta name="keywords" content="index" />
    <meta name="description" content="index" />

</head>
<body class="Favorites">
<div class="nextpage ff_yh fs36 textC">
    <a href="javaScript:;" class="close"></a>
    <img src="/images/g_ico.jpg"/>
</div><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!-- #EndLibraryItem -->
<!--banner-->
<link href="/css/tBase.css" rel="stylesheet" type="text/css">
<link href="/css/announcement.css" rel="stylesheet" type="text/css">
<link href="/css/index.css" rel="stylesheet" type="text/css">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
<div class="main cl">
      <div class="w1000 cl">
    	 <p class="title">收藏夹</p>
         <div class="Favorites_div">
         	 <div class="Favorites_div_fl fl" id="nav">
             	<p class="nav">
                    <a href="javaScript:;" class="checked" data-favoriteType=""><em></em><i></i><span>全部</span>${allCount}</a>
                    <a href="javaScript:;" class="two" data-favoriteType="plan"><em></em><i></i><span>线路</span>${planCount}</a>
                    <a href="javaScript:;" class="three" data-favoriteType="recplan"><em></em><i></i><span>游记</span>${recplanCount}</a>
                    <a href="javaScript:;" class="four" data-favoriteType="scenic"><em></em><i></i><span>景点</span>${scenicCount}</a>
                    <a href="javaScript:;" class="fives" data-favoriteType="hotel"><em></em><i></i><span>酒店</span>${hotelCount}</a>
                    <a href="javaScript:;" class="six" data-favoriteType="delicacy"><em></em><i></i><span>美食</span>${delicacyCount}</a>
                </p>
             </div>
             
             <div class="Favorites_div_fr fr">
             	<div class="mailTablePlan">
             	  <ul class="Favorites_div_fr_ul" id="favorites_">
                    
                  </ul>
                  <div class="m-pager st cl" id="favorites_pager_"></div>
                </div>  
                
                <!--行程-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_plan">
             	  
                  </ul>
                  <div class="m-pager st cl" id="favorites_pager_plan"></div>
                </div> 
                
                <!--游记-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_recplan">
             	  
                  </ul>
                  <div class="m-pager st cl" id="favorites_pager_recplan"></div>
                </div> 
                
                <!--景点-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_scenic">
                    
                  </ul>
                  <div class="m-pager st cl" id="favorites_pager_scenic"></div>
                </div>  
                
                <!--酒店-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_hotel">
                    
                  </ul>
                  <div class="m-pager st cl" id="favorites_pager_hotel"></div>
                </div>  
                
                <!--美食-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_delicacy">
                    
                  </ul>
                  <div class="m-pager st cl" id="favorites_pager_delicacy"></div>
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
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/favorite/favorites.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>

<script type="text/html" id="tpl-favorite-item-plan">
<li class="ft_list">
	<a href="javaScript:;" class="close" data-favoriteId="{{id}}"></a>
	<p class="img fl"><a href="${PLAN_PATH}/plan_detail_{{favoriteId}}.html">
		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}" original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}"></a></p>
	<div class="nr fr">
		<p class="name b cl">{{title}}</p>
		{{if author != null && author != ""}}
		<p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{author}}</span></p>
		{{/if}}
		{{if days != null && days > 0}}
		<p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
		{{/if}}
		{{if mainScenics != null && mainScenics != ""}}
		<p class="js cl"><label>主要景点：</label><span>{{mainScenics}}</span></p>
		{{/if}}
		<p class="synopsis posiR">{{content}}<img src="/images/left2.png" align="absbottom" class="ml5" /></p>
		<p class="time textR">{{createTime}}</p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-recplan">
<li class="ft_list">
	<a href="javaScript:;" class="close" data-favoriteId="{{id}}"></a>
	<p class="img fl"><a href="${RECOMMENDPLAN_PATH}/guide_detail_{{favoriteId}}.html">
		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}" original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}"></a></p>
	<div class="nr fr">
		<p class="name b cl">{{title}}</p>
		{{if author != null && author != ""}}
		<p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{author}}</span></p>
		{{/if}}
		{{if days != null && days > 0}}
		<p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
		{{/if}}
		{{if mainScenics != null && mainScenics != ""}}
		<p class="js cl"><label>主要景点：</label><span>{{mainScenics}}</span></p>
		{{/if}}
		<p class="synopsis posiR">{{content}}<img src="/images/left2.png" align="absbottom" class="ml5" /></p>
		<p class="time textR">{{createTime}}</p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-scenic">
<li class="ft_list Attractions_List scenic-node" data-id="{{favoriteId}}" data-type="scenic">
	<a href="javaScript:;" class="close" data-favoriteId="{{id}}"></a><p class="img fl"><a href="${SCENIC_PATH}/scenic_detail_{{favoriteId}}.html">
	<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}" original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}"></a></p>
	<div class="nr fr">
		<a href="javaScript:;" class="stroke stroke_open"></a>
		<p class="name b cl">{{title}}</p>
		<p class="cl hadd">
			<span class="add fl mr10">{{address}}</span>
			<a href="${SCENIC_PATH}/scenic_detail_{{favoriteId}}.html" class="fl add_a">查看地图</a>
		</p>   
		{{if score != null && score > 0}}
		<p class="fraction cl"><b class="fs16">{{score / 20}}分</b>/5分(来自{{scorePeopleNum}}人点评)</p>
		{{/if}}
		<p class="synopsis posiR">{{content}}<img src="/images/left2.png" align="absbottom" class="ml5" /></p>
		<p class="synopsis2">门票价格：{{if price != null && price > 0}}<b class="Orange mr30">{{price}}元/人</b>{{else}}<b class="mr30">免费</b>{{/if}}
		<a href="javascript:;" onclick="goRecommendPlanList({{favoriteId}},'scenic','{{title}}')">相关游记</a></p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-hotel">
<li class="ft_list hotels_list">
	<a href="javaScript:;" class="close" data-favoriteId="{{id}}"></a><p class="img fl"><a href="${HOTEL_PATH}/hotel_detail_{{favoriteId}}.html">
	<img data-original="{{imgPath}}" /></a></p>
	<div class="nr fr">
		<a href="${HOTEL_PATH}/hotel_detail_{{favoriteId}}.html" class="posiA price round">
			￥<span class="fs20 b">{{price}}</span>起
			<p class="fs16 b">选择</p>
		</a>
		<p class="name b fl">{{title}}</p><span class="hstar fl"><i class="star{{if star != null && star > 0}}{{star}}{{else}}1{{/if}}"></i></span>
		<p class="cl hadd">
			<span class="add fl mr10">{{address}}</span><a href="${HOTEL_PATH}/hotel_detail_{{favoriteId}}.html" class="fl add_a">查看地图</a>
		</p>    
		{{if scoreForHotel != null && scoreForHotel > 0}}
		<p class="fraction cl"><b class="fs16">{{scoreForHotel / 20}}分</b>/5分(来自{{scorePeopleNum}}人点评)</p>
		{{/if}}
		<p class="synopsis posiR">{{content}}<img src="/images/left2.png" align="absbottom" class="ml5" /></p>
		<p class="synopsis2"><a href="javascript:;" onclick="goRecommendPlanList({{favoriteId}},'hotel','{{title}}')">相关游记</a></p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-delicacy">
<li class="ft_list food_List">
	<a href="javaScript:;" class="close" data-favoriteId="{{id}}"></a><p class="img fl"><a href="${DELICACY_PATH}/food_detail_{{favoriteId}}.html">
	<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgPath}}"></a></p>
	<div class="nr fr">
		<p class="name b cl">{{title}}</p>
		<p class="js cl"><label>均&nbsp;&nbsp;价：</label><span class="green b">{{price}}元/人</span></p>
		{{if taste != null && taste != ""}}
		<p class="js cl"><label>口&nbsp;&nbsp;感：</label><span>{{taste}}</span></p>
		{{/if}}
		<div class="food_js cl"><label>美食简介：</label>
			<div class="food_p">{{content}}</div>
		</div>
	</div>
	<p class="cl"></p>
</li>
</script>


</body>
</html>
