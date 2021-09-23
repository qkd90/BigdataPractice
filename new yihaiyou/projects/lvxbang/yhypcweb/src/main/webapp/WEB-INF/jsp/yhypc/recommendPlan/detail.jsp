<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

	<%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/public/pager.css">
	<link rel="stylesheet" href="/css/recommendPlan/recommendPlan_detail.css">
	<title>游记详情</title>
</head>
<body  class="travel">
<%@include file="../../yhypc/public/nav_header.jsp"%>
	<div class="hotelIndex">
		<jx:include fileAttr="${YHY_RECOMMEND_PLAN_DETAIL}" targetObject="yhyBuildService" targetMethod="buildOneRecommendPlan" objs="${param.recommendPlanId}" validDay="60"></jx:include>
	</div>
	<%@include file="../../yhypc/public/nav_footer.jsp"%>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
</html>

<script type="text/html" id="tpl-recommendPlan-detail-comment-info">
	<%--<div class="comment-details-star">--%>
		<%--<h4>{{commentAvgScore}}<sub>/&nbsp;5分</sub></h4>--%>
		<%--{{if commentAvgScore > 0 && commentAvgScore <= 1}}--%>
		<%--<i class="icon-star-1"></i>--%>
		<%--{{/if}}--%>
		<%--{{if commentAvgScore > 1 && commentAvgScore <= 2}}--%>
		<%--<i class="icon-star-2"></i>--%>
		<%--{{/if}}--%>
		<%--{{if commentAvgScore > 2 && commentAvgScore <= 3}}--%>
		<%--<i class="icon-star-3"></i>--%>
		<%--{{/if}}--%>
		<%--{{if commentAvgScore > 3 && commentAvgScore <= 4}}--%>
		<%--<i class="icon-star-4"></i>--%>
		<%--{{/if}}--%>
		<%--{{if commentAvgScore > 4 && commentAvgScore <= 5}}--%>
		<%--<i class="icon-star-5"></i>--%>
		<%--{{/if}}--%>
		<%--{{if commentAvgScore == 0}}--%>
		<%--<i class="icon-star-0"></i>--%>
		<%--{{/if}}--%>

	<%--</div>--%>
	<div class="comment-details-num">
		<i class="icon-hand"></i>
		（{{if commentCount != 0 && commentCount != null}}<mark>{{commentCount}}</mark>条评论{{else}}暂无评论{{/if}}）
	</div>
</script>
<script type="text/html" id="tpl-recommendPlan-detail-comment-list">
	<li class="clearfix">
		<div class="comment-details-aside">
			{{if head.length > 0}}
			<img src="{{head}}" class="comment-person-img">
			{{else}}
			<img src="/image/2.png" class="comment-person-img">
			{{/if}}
			<p class="comment-customer-name">
				{{userName}}
			</p>
		</div>
		<div class="comment-details-article">
			<h5 class="clearfix">
				<span class="pull-left">发表时间：{{createTime}}</span>
				<%--{{if avgScore > 0 && avgScore <= 1}}
				<i class="comment-customer star-01 pull-right"></i>
				{{/if}}
				{{if avgScore > 1 && avgScore <= 2}}
				<i class="comment-customer star-02 pull-right"></i>
				{{/if}}
				{{if avgScore > 2 && avgScore <= 3}}
				<i class="comment-customer star-03 pull-right"></i>
				{{/if}}
				{{if avgScore > 3 && avgScore <= 4}}
				<i class="comment-customer star-04 pull-right"></i>
				{{/if}}
				{{if avgScore > 4 && avgScore <= 5}}
				<i class="comment-customer star-05 pull-right"></i>
				{{/if}}
				{{if avgScore == 0}}
				<i class="comment-customer star-00 pull-right"></i>
				{{/if}}--%>
			</h5>
			<p class="comment-aritcle">{{content}}</p>
			{{if comments != null && comments.length > 0}}
			<div class="busiReply">
				<p class="replylineone"><span>商家回复：</span>{{comments[0].createTime}}</p>
				<p class="replylineone replylinetwo">{{comments[0].content}}</p>
			</div>
			{{/if}}
		</div>

	</li>
</script>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/recommendPlan/recommendPlan_detail.js"></script>
<%--<script type="text/javascript" src="../src/js/swiper-3.3.1.min.js"></script>--%>
	<script>
	var mySwiper = new Swiper ('.swiper-container', {
	direction: 'horizontal',
	loop: false,
	autoplay:5000,
	pagination: '.swiper-pagination'
	})
</script>