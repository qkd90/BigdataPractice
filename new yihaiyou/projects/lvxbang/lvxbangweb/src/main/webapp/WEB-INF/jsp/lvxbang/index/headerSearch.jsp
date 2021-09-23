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
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
    <link href="/css/member.css" rel="stylesheet" type="text/css">
<div class="main cl">
      <div class="w1000 cl">
		  <p class="title b ff_yh">关于"<span class="Orange" id="keyword">${keyword}</span>"的搜索结果</p>
		  <input type="hidden" id="type" value="${type}"/>
         <div class="Favorites_div">
         	 <div class="Favorites_div_fl fl" id="nav">
             	<p class="nav">
                    <a href="javaScript:;" data-favoriteType=""><em></em><i></i><span>全部</span>${allCount}</a>
                    <a href="javaScript:;" class="two" data-favoriteType="plan"><em></em><i></i><span>行程</span>${planCount}</a>
                    <a href="javaScript:;" class="three" data-favoriteType="recommend_plan"><em></em><i></i><span>游记</span>${recplanCount}</a>
                    <a href="javaScript:;" class="four" data-favoriteType="scenic_info"><em></em><i></i><span>景点</span>${scenicCount}</a>
                    <a href="javaScript:;" class="fives" data-favoriteType="hotel"><em></em><i></i><span>酒店</span>${hotelCount}</a>
                    <a href="javaScript:;" class="six" data-favoriteType="delicacy"><em></em><i></i><span>美食</span>${delicacyCount}</a>
					<a href="javaScript:;" class="two" data-favoriteType="line"><em></em><i></i><span>线路</span>${lineCount}</a>
                </p>
             </div>
             
             <div class="Favorites_div_fr fr">
             	<div class="mailTablePlan">
             	  <ul class="Favorites_div_fr_ul" id="favorites_">
                    
                  </ul>
                    <p class="cl"></p>
                    <img src="/images/food2.jpg" class="fr foodxx" style="margin-right: 73px; display: inline;">
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
             	  <ul class="Favorites_div_fr_ul" id="favorites_recommend_plan">
             	  
                  </ul>
                    <p class="cl"></p>
                    <img src="/images/food2.jpg" class="fr foodxx" style="margin-right: 73px; display: inline;">
                  <div class="m-pager st cl" id="favorites_pager_recommend_plan"></div>
                </div> 
                
                <!--景点-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_scenic_info">
                    
                  </ul>
                    <p class="cl"></p>
                    <img src="/images/food2.jpg" class="fr foodxx" style="margin-right: 73px; display: inline;">
                  <div class="m-pager st cl" id="favorites_pager_scenic_info"></div>
                </div>  
                
                <!--酒店-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_hotel">
                    
                  </ul>
                    <p class="cl"></p>
                    <img src="/images/food2.jpg" class="fr foodxx" style="margin-right: 73px; display: inline;">
                  <div class="m-pager st cl" id="favorites_pager_hotel"></div>
                </div>  
                
                <!--美食-->
                <div class="mailTablePlan disn">
             	  <ul class="Favorites_div_fr_ul" id="favorites_delicacy">
                    
                  </ul>
                    <p class="cl"></p>
                    <img src="/images/food2.jpg" class="fr foodxx" style="margin-right: 73px; display: inline;">
                  <div class="m-pager st cl" id="favorites_pager_delicacy"></div>
                </div>

				 <!--出游-->
				 <div class="mailTablePlan disn">
					 <ul class="Favorites_div_fr_ul" id="favorites_line">

					 </ul>
					 <div class="m-pager st cl" id="favorites_pager_line"></div>
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
<script src="/js/lvxbang/index/headerSearch.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>

<script type="text/html" id="tpl-favorite-item-plan">
<li class="ft_list">
	<p class="img fl"><a href="${PLAN_PATH}/plan_detail_{{id}}.html" class="name_a" target="_blank"><img data-original="{{imgPath}}" original="{{imgPath}}"></a></p>
	<div class="nr fr">
		<p class="name b cl">{{name}}</p>
		{{if nickName != null && nickName != ""}}
		<p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{nickName}}</span></p>
		{{/if}}
		{{if days != null && days > 0}}
		<p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
		{{/if}}
		{{if passScenics != null && passScenics != ""}}
		<p class="js cl"><label>主要景点：</label><span>{{passScenics}}</span></p>
		{{/if}}
		<p class="synopsis posiR mt15">{{content}}<img src="/images/left2.png" align="absbottom" class="ml5" /></p>
		<p class="time textR">{{createTime}}</p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-recommend_plan">
<li class="ft_list">
	<p class="img fl"><a href="${RECOMMENDPLAN_PATH}/guide_detail_{{id}}.html" target="_blank">
        <img data-original="{{cover | recommendPlanTripListImg}}" ></a></p>
	<div class="nr fr">
		<a href="${RECOMMENDPLAN_PATH}/guide_detail_{{id}}.html" class="name_a" target="_blank"><p class="name b cl">{{name}}</p></a>
		{{if nickName != null && nickName != ""}}
		<p class="js cl"><label>作&nbsp;&nbsp;者：</label><span class="green">{{nickName}}</span></p>
		{{/if}}
		{{if days != null && days > 0}}
		<p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{days}}天</span></p>
		{{/if}}
		{{if passScenics != null && passScenics != ""}}
		<p class="js cl"><label>主要景点：</label><span>{{passScenics}}</span></p>
		{{/if}}
		{{if description != null && description.length > 0}}
		<div class="food_js"><p class="synopsis posiR mt15 long{{if description.length > 114}} is_hover{{/if}}">{{formatFavoritesCom description 114}}</p></div>
		{{/if}}
		<p class="time textR">{{createTime}}</p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-scenic_info">
<li class="ft_list Attractions_List scenic-node" data-id="{{id}}" data-type="scenic">
	<p class="img fl"><a href="${SCENIC_PATH}/scenic_detail_{{id}}.html" target="_blank">
		{{if cover != null && cover != ""}}
		<img class="scenic-node-img"
			 data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{cover}}?imageView2/1/w/219/h/160/q/75">
		{{else}}
		<img class="scenic-node-img"
			 data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/219/h/160/q/75"/>
		{{/if}}
	</a></p>
	<div class="nr fr">
		<a href="javaScript:;" class="stroke stroke_open"></a>
		<a href="${SCENIC_PATH}/scenic_detail_{{id}}.html" class="name_a" target="_blank"><p class="name b cl">{{name}}</p></a>
		<p class="cl hadd">
			<span class="add fl mr10{{if address.length > 16}} ser_hover is_hover{{/if}}">{{formatComment address 16}}</span>
			<a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="{{longitude}}"
			   data-ditu-baiduLat="{{latitude}}" data-ditu-name="{{name}}">查看地图</a>
		</p>   
		{{if score != null && score > 0}}
		<p class="fraction cl"><b class="fs16">{{score / 20}}分</b>/5分(来自{{scorePeopleNum}}人点评)</p>
		{{/if}}
		{{if shortComment != null && shortComment.length > 0}}
		<div class="food_js"><p class="synopsis posiR long{{if shortComment.length > 58}} is_hover{{/if}}">{{formatFavoritesCom shortComment 58}}</p></div>
		{{/if}}
		<p class="synopsis2">门票价格：{{if price != null && price > 0}}<b class="Orange mr30">{{price}}元/人</b>{{else}}<b class="mr30">未知</b>{{/if}}
		<a href="javascript:;" onclick="goRecommendPlanList({{id}},'scenic','{{name}}')"><span class="Orange">相关游记</span></a></p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-hotel">
<li class="ft_list hotels_list">
	<p class="img fl"><a href="${HOTEL_PATH}/hotel_detail_{{id}}.html" target="_blank">
		{{if cover != null && cover != ""}}
		<img data-original="{{cover}}"/>
		{{else}}
		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/219/h/160/q/75"/>
		{{/if}}
	</a></p>
	<div class="nr fr">
		<a href="${HOTEL_PATH}/hotel_detail_{{id}}.html" class="posiA price round" target="_blank">
			￥<span class="fs20 b">{{price}}</span>起
			<p class="fs16 b">选择</p>
		</a>
		<a href="${HOTEL_PATH}/hotel_detail_{{id}}.html" class="name_a" target="_blank"><p class="name b fl">{{name}}</p></a>
		<span class="hstar fl"><i class="star{{if star != null && star > 0}}{{star}}{{else}}1{{/if}}"></i></span>
		<p class="cl hadd">
			<span class="add fl mr10{{if address && address.length > 16}} ser_hover is_hover{{/if}}">{{formatComment address 16}}</span>
			<a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="{{lng}}"
			   data-ditu-baiduLat="{{lat}}" data-ditu-name="{{name}}">查看地图</a>
		</p>    
		{{if score != null && score > 0}}
		<p class="fraction cl"><b class="fs16">{{score / 20}}分</b>/5分(来自{{commentCount}}人点评)</p>
		{{/if}}
		{{if comment != null && comment.length > 0}}
		<div class="food_js"><p class="synopsis posiR long{{if comment.length > 114}} is_hover{{/if}}">{{formatFavoritesCom comment 114}}</p></div>
		{{/if}}
		<p class="synopsis2"><a href="javascript:;" onclick="goRecommendPlanList({{id}},'hotel','{{name}}')"><span class="orange">相关游记</span></a></p>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-delicacy">
<li class="ft_list food_List">
	<p class="img fl"><a href="${DELICACY_PATH}/food_detail_{{id}}.html" target="_blank">
		{{if cover != null && cover != ""}}
		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{cover}}">
		{{else}}
		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/meishi.png?imageView2/1/w/219/h/160/q/75"/>
		{{/if}}
	</a></p>
	<div class="nr fr">
		<a href="${DELICACY_PATH}/food_detail_{{id}}.html" class="name_a" target="_blank"><p class="name b cl">{{name}}</p></a>
		<p class="js cl"><label>均&nbsp;&nbsp;价：</label><span class="green b">{{price}}元/人</span></p>
		{{if taste != null && taste != ""}}
		<p class="js cl"><label>口&nbsp;&nbsp;感：</label><span>{{taste}}</span></p>
		{{/if}}
		<div class="food_js cl"><label>美食简介：</label>
			<div class="food_p{{if introduction.length > 56}} food_p_hover{{/if}}">{{formatDelicacyIntro introduction 56}}</div>
		</div>
	</div>
	<p class="cl"></p>
</li>
</script>
<script type="text/html" id="tpl-favorite-item-line">
	<li class="ft_list">
		<p class="img fl"><a href="${INDEX_PATH}/line_detail_{{id}}.html" class="name_a" target="_blank">
			<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{productImg}}" original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{productImg}}"></a></p>
		<div class="nr fr">
			<p class="name b cl">{{name}}</p>
			{{if lineDay != null && lineDay > 0}}
			<p class="js cl"><label>天&nbsp;&nbsp;数：</label><span>{{lineDay}}天</span></p>
			{{/if}}
			{{if price != null && price > 0}}
			<p class="js cl"><label>价&nbsp;&nbsp;格：</label><span>{{price}}元</span></p>
			{{/if}}
			{{if startCity != null && startCity != ""}}
			<p class="js cl"><label>出发城市：</label><span>{{startCity}}</span></p>
			{{/if}}
			{{if trafficType != null && trafficType != ""}}
			<p class="js cl"><label>交&nbsp;&nbsp;通：</label><span>{{trafficType}}</span></p>
			{{/if}}
			<p class="synopsis posiR mt15">{{content}}<img src="/images/left2.png" align="absbottom" class="ml5" /></p>
			<p class="time textR"></p>
		</div>
		<p class="cl"></p>
	</li>
</script>


</body>
</html>
