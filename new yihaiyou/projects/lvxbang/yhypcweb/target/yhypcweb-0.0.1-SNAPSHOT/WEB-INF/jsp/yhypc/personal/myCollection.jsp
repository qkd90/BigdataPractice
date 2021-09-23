<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/personal/personal_head.css">
	<link rel="stylesheet" type="text/css" href="/css/personal/personal_Mycollection.css">
    <link rel="stylesheet" href="/css/public/pager.css">
	<title>我的收藏-一海游</title>
</head>
<body data-page-obj="MyCollection" data-page-class="pc-mycollection" class="judgebottom">
	<div class="hotelIndex">
		<%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f8fe">
            <%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="mainBox">
				<div class="mainbox_nav">
					<p id="favTypeSel">
						<label class="le_title">类型：</label>
						<span class="s_active">全部</span>|
						<span data-fav-type="hotel">酒店民宿</span>|
						<span data-fav-type="scenic">景点门票</span>|
						<span data-fav-type="sailboat">海上休闲</span>|
						<span data-fav-type="cruiseship">邮轮</span>|
						<span data-fav-type="recplan">游记</span>
					</p>
				</div>
				<div id="fav_content"></div>
				<div class="paging m-pager"></div>
			</div>
            <%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/personal/personal_Mycollection.js"></script>
<script type="text/html" id="fav_item">
    <div class="product">
        <div class="picture"><img src="{{cover | imageResize:224,135}}"></div>
        <div class="pic_message">
            <a target="_blank" href="/{{favoriteType}}_detail_{{favoriteId}}.html"><p class="name">{{title}}</p></a>
            <p class="address">{{address}}</p>
            <p class="addr_ex">{{content}}</p>
            <p class="score_comment">
                <span class="score"><span class="now">{{score}}</span>/5分</span>
                <%--<span class="comNum"><span class="count">2369 </span>条评论 <i></i></span>--%>
            </p>
        </div>
        <div class="pic_pri_detai">
			{{if price > 0}}<p class="p_pri">{{else}}<p class="p_pri" style="visibility: hidden">{{/if}}
            <span class="rmb">¥</span><span class="num">{{price | formatDouble}}</span>起</p>
            <p class="p_detai"><span class="checkdetail"><a target="_blank" href="/{{favoriteType}}_detail_{{favoriteId}}.html">查看详情</a></span></p>
        </div>
    </div>
</script>
</html>