<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>旅行帮</title>
    <meta name="keywords" content="index" />
    <meta name="description" content="index" />
	<link href="/css/tBase.css" rel="stylesheet" type="text/css">
	<link href="/css/announcement.css" rel="stylesheet" type="text/css">
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
      <div class="System w1000 cl">
    	 <b class="title fs16">消息通知</b>
         <ul class="System_ul fs13" id="message_">
         	
         </ul>
         <div class="m-pager st cl"></div>
        <p class="cl"></p><!-- #BeginLibraryItem "/lbi/paging.lbi" -->
<p class="cl h30"></p>
<!-- #EndLibraryItem -->
<p class="stotal">共收到<span id="totalSpan">0</span>条小组消息<a href="javaScript:;" class="clear">重置</a></p>
	</div>
</div>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/message/system.js" type="text/javascript"></script>

<script type="text/html" id="tpl-message-item-comment">
<li>
	<a href="javaScript:;" class="close" data-messageId="{{id}}"></a>
	<p class="img"><img data-original="{{fromUserHead}}" /></p>
	<div class="nr">
		<p><span class="name yellow mr17">{{fromUserName}}</span>回复了你的点评<span class="time mr23">{{createTimeStr}}</span></p>
		<p>关于<span class="yellow mlr9">{{title}}</span>的点评<span class="yellow mlr6">"{{content}}"</span>的回复，快去
		{{if commentTargetType == "delicacy"}}
			<a href='${DELICACY_PATH}/food_detail_{{commentTargetId}}.html' target='_blank'>看看</a>
		{{else if commentTargetType == "hotel"}}
			<a href='${HOTEL_PATH}/hotel_detail_{{commentTargetId}}.html' target='_blank'>看看</a>
		{{else if commentTargetType == "plan"}}
			<a href='${PLAN_PATH}/plan_detail_{{commentTargetId}}.html' target='_blank'>看看</a>
		{{else if commentTargetType == "recplan"}}
			<a href='${RECOMMENDPLAN_PATH}/guide_detail_{{commentTargetId}}.html' target='_blank'>看看</a>
		{{else if commentTargetType == "scenic"}}
			<a href='${SCENIC_PATH}/scenic_detail_{{commentTargetId}}.html' target='_blank'>看看</a>
		{{else}}
			看看
		{{/if}}
		吧...</p>
	</div>
</li>
</script>
<script type="text/html" id="tpl-message-item-order">
<li>
	<a href="javaScript:;" class="close" data-messageId="{{id}}"></a>
	<p class="img gl" style="background: #fff;"><img data-original="/images/p_img4.jpg"  style="margin-left: 17px;"/></p>
	<div class="nr">
		<p><b class="name green mr9">{{title}}</b><span class="time">{{createTimeStr}}</span></p>
		<p>【订单消息】{{content}}<a href="${INDEX_PATH}/lvxbang/user/order.jhtml?orderId={{messageId}}">立即支付</a></p>
	</div>
</li>
</script>
<script type="text/html" id="tpl-message-item-notify">
    <li>
        <a href="javaScript:;" class="close" data-messageId="{{id}}"></a>
        <p class="img gl" style="background: #fff;"><img data-original="/images/p_img4.jpg"  style="margin-left: 17px;"/></p>
        <div class="nr">
            <p><b class="name green mr9">{{title}}</b><span class="time">{{createTimeStr}}</span></p>
            <p>【系统通知】{{content}}</p>
        </div>
    </li>
</script>

</body>
</html>
