<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/css/personal/personal_head.css">
	<link rel="stylesheet" type="text/css" href="/css/personal/personal_Mywallet.css">
    <link rel="stylesheet" href="/css/public/pager.css">
	<title>我的钱包-一海游</title>
</head>
<body data-page-obj="MyWallet" data-page-class="pc-mywallet" class="judgebottom">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f8fe">
            <%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="mainBox">
                <input type="hidden" id="fn" value="${fn}">
				<div class="wallet_header">
					<ul class="clearfix">
						<li class="recharge"><a target="_blank" href="/yhypc/personal/myRecharge.jhtml"><span class="mainword">立即充值</span></a></li>
						<li class="withdraw">
							<span class="inner_withdraw">提现</span>
							<%--<span class="bankar">绑定银行卡</span>--%>
							<%--<p class="bankname">招商银行：**** **** **** 12345 <span class="reban">解绑</span></p>--%>
						</li>
						<li class="setpsword"><span class="inner_setpsword">设置支付密码</span></li>
						<li class="balance">账户余额：<span>¥<label>${balance}</label></span></li>
					</ul>
				</div>
				<h3 class="recodertitle">消费记录</h3>
				<div class="recorder">
					<div class="recorder_header">
						<ul class="clearfix">
							<li class="time">时间</li>
							<li class="type">类别</li>
							<li class="count">金额</li>
							<li class="balance">余额</li>
						</ul>
					</div>
					<div class="recorder_body" id="consume_content"></div>
				</div>
                <div class="paging m-pager"></div>
			</div>
			<div class="shadow"></div>
			<div class="withdrawBox">
				<div class="with_header">
					<span class="closebtn" id="with_close"></span>
				</div>
				<div class="with_body">
					<span class="title">申请提现</span>
					<input class="pri_count withdrawPrice" onkeyup="value=value.replace(/[^\d.]/g,'')" type="text" placeholder="请输入提现金额, 本次最多${balance}">
					<span class="attention"><span class="hidden"><label>*</label>请输入金额</span></span>
					<input class="pri_count payPassword" type="password" placeholder="请输入密码">
					<span class="attention"><span class="hidden"><label>*</label>请输入密码</span></span>
					<span class="submit">提交</span>
				</div>
			</div>
			<div class="setpswordBox">
				<div class="with_header">
					<span class="closebtn" id="psword_close"></span>
				</div>
				<div class="with_body">
                    <form id="setPayPwdForm" enctype="multipart/form-data" action="">
                        <span class="title">设置支付密码</span>
						<span class="nowP">当前支付密码(未设置请留空)</span>
                        <input class="pri_count pri_now" type="password" name="oldPayPwd" placeholder="当前支付密码(未设置请留空)">
                        <span class="attention"></span>
						<span class="newPa">请输入支付密码</span>
                        <input class="pri_count pri_newa" type="password" name="payPwd" placeholder="请输入支付密码">
                        <span class="attention"></span>
						<span class="newPb">请确认支付密码</span>
                        <input class="pri_count pri_newb" type="password" name="cfmPayPwd" placeholder="请确认支付密码">
                        <span class="attention"></span>
                        <span class="submit" id="doSetPayPwdBtn">确认</span>
                    </form>
				</div>
			</div>
            <%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
	<div class="windowShadow"></div>
	<div class="bindBank">
		<h3>提现银行卡 <span class="closebtn"></span></h3>
		<div class="line clearfix historyCard">
			<span class="leftcheck"></span>
			<div class="carid">
				<span class="carname">招商银行：</span>
				<span class="carnum">121212121212121212</span>
				<span class="atten">(常用银行卡)</span>
			</div>
		</div>
		<div class="line clearfix">
			<span class="leftex">请选择银行</span>
			<div class="inputbox bank">
				<span class="contain"></span>
				<ul class="banklist allist">
					<li>中国工商银行</li>
					<li>中国农业银行</li>
					<li>中国银行</li>
					<li>中国建设银行</li>
					<li>交通银行</li>
					<li>中信实业银行</li>
					<li>中国光大银行</li>
					<li>华夏银行</li>
					<li>中国民生银行</li>
					<li>广东发展银行</li>
					<li>深圳发展银行</li>
					<li>招商银行</li>
					<li>兴业银行</li>
					<li>上海浦东发展</li>
					<li>城市商业银行</li>
					<li>农村商业银行</li>
					<li>国家开发银行</li>
					<li>中国进出口银</li>
					<li>中国农业发展</li>
					<li>城市信用社</li>
					<li>农村信用社</li>
					<li>农村合作银行</li>
					<li>邮政储蓄</li>
					<li>其他银行</li>
				</ul>
			</div>
		</div>
		<div class="line clearfix">
			<span class="leftex">请输入卡号</span>
			<div class="inputbox bankNo">
				<input type="tel" class="reinput">
			</div>
		</div>
		<div class="line clearfix">
			<span class="leftex">请输入姓名</span>
			<div class="inputbox holderName">
				<input type="text" class="reinput">
			</div>
		</div>
		<div class="line clearfix">
			<span class="leftex">银行卡省份</span>
			<div class="inputbox province">
				<span class="contain"></span>
				<ul class="provincelist allist clearfix">
				</ul>
			</div>
		</div>
		<div class="line clearfix">
			<span class="leftex">银行卡城市</span>
			<div class="inputbox city">
				<span class="contain"></span>
				<ul class="citylist allist clearfix">
				</ul>
			</div>
		</div>
		<div class="line">
			<span class="bottom">确定</span>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/personal/personal_Mywallet.js"></script>
<script type="text/html" id="consume_item">
    <ul class="clearfix">
        <li class="time">{{createTime}}</li>
        <li class="type">{{type}}{{if type != "充值" &&  type != "提现"}}({{accountType}}){{/if}}</li>
        <li class="count">¥{{money}}</li>
        <li class="balance">¥{{balance}}</li>
    </ul>
</script>
<script type="text/html" id="city_item">
	<li data-id="{{id}}">{{name}}</li>
</script>
</html>