<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/personal/personal_head.css">
	<link rel="stylesheet" href="/css/personal/personalIndex.css">
    <link rel="stylesheet" href="/css/public/pager.css">
	<title>我的订单-个人中心</title>
</head>
<body data-page-obj="YhyUserOrder" data-page-class="pc-myorder" class="judgebottom">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f3f3">
            <%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="headbox">
				<ul class="clearfix" id="status_sel">
					<li class="li_active">全部订单</li>
					<li data-order-status="SUCCESS">预订成功（<span></span>）</li>
					<li data-order-status="WAIT">待支付（<span></span>）</li>
					<li data-order-status="CANCELED">已取消（<span></span>）</li>
					<li data-order-status="NOCOMMENT">待评价（<span></span>）</li>
					<li data-order-status="COMMENT">已评价（<span></span>）</li>
				</ul>
			</div>
			<div class="mainBox">
				<div class="mainbox_nav">
					<p id="type_sel">
						<label class="le_title">类型：</label>
						<span class="s_active">全部</span>|
                        <span data-order-type="ticket">景点门票</span>|
                        <span data-order-type="hotel">酒店民宿</span>|
                        <span data-order-type="ferry">船票</span>|
                        <%--<span data-order-type="cruiseship">邮轮</span>|--%>
						<span data-order-type="plan">DIY行程</span>|
						<span data-order-type="sailboat,yacht,huanguyou">海上休闲</span>|
						<%--<span data-order-type="shenzhou">专车</span>--%>
					</p>
				</div>
				<div class="table_header">
					<ul class="clearfix">
						<li class="pro_mess">产品信息</li>
						<li class="pro_code">订单号</li>
						<li class="pro_pri">订单金额</li>
						<li class="pro_time">下单时间</li>
						<li class="pro_state">订单状态</li>
						<li class="pro_opera">订单操作</li>
					</ul>
				</div>
				<div class="table_body" id="order_info_content"></div>
                <div class="paging m-pager"></div>
			</div>
            <%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/personal/personalIndex.js"></script>
<script type="text/javascript" src="/js/personal/user_order.js"></script>
<script type="text/html" id="order_info_tpl">
    <ul class="fir_ul clearfix">
        <li class="pro_mess" title="{{name}}">{{name | briefText:15}}</li>
        <li class="pro_code">{{orderNo}}</li>
        <li class="pro_pri">¥<span>{{price}}</span></li>
        <li class="pro_time">{{createTime}}</li>
        <li class="pro_state">{{status}}</li>
        <li class="pro_opera">
			<a target="_blank" href="/yhypc/personal/{{type}}OrderDetail.jhtml?id={{id}}&type={{type}}">查看订单</a>&nbsp;&nbsp;
            {{if status == "已取消" || status == "无效订单"}}
			<a class="delOrder" data-order-id="{{id}}" data-order-type="{{type}}">删除</a>
            {{/if}}
        </li>
    </ul>
</script>
</html>