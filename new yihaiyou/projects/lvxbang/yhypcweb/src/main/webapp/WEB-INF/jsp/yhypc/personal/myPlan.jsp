<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/css/personal/personal_head.css">
	<link rel="stylesheet" href="/css/personal/personal_Mymarch.css">
    <link rel="stylesheet" href="/css/public/pager.css">
	<title>我的行程-一海游</title>
</head>
<body data-page-obj="MyPlan" data-page-class="pc-myplan" class="judgebottom">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f8fe">
            <%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="mainBox">
				<div class="clearfix" style="width:1160px;margin:auto"><a target="_blank" href="/yhypc/plan/demand.jhtml" class="createMarch">创建行程</a></div>
				<div id="plan_content"></div>
				<div class="paging m-pager"></div>
			</div>
            <%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/personal/personal_Mymarch.js"></script>
<script type="text/html" id="plan_item">
    <div class="product">
        <div class="picture"><img src="{{cover | imageResize:224,135}}"></div>
        <div class="pic_message">
            <a target="_blank" href="/plan_detail_{{id}}.html"><p class="name">{{name}}</p></a>
            <p class="datetime">{{startTime}}</p>
            <p class="address">{{each scenicInfoNames as scenicInfoName}}{{scenicInfoName}}{{if $index != scenicInfoNames.length -1}}<label>·</label>{{/if}}{{/each}}</p>
            <p class="price_daytime"><span class="day">{{planDays}}天</span><span class="price">¥{{price}}</span></p>
        </div>
    </div>
</script>
</html>