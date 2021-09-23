<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<link href="/css/ticket/form.css" rel="stylesheet" type="text/css">
<%@ include file="../../common/common141.jsp"%>

<script type="text/javascript" src="/js/ticket/ticketUtil.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/ticket/ticketEditOfferList.js"></script>

<base href="<%=basePath%>">

<title>用户管理</title>



</head>
<body>
	<style type="text/css">
.text {
	font-size: 13px;
	color: rgb(2, 48, 97);
}
</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true" style="height:1000px;">
			<!--表单区域开始-->
			<form id="userInputForm" name="userInputForm" method="post" action="">
				<input id="ticketPriceId" type="hidden" name="ticketPriceId" value="${ticketPriceId}" />
				<input id="ticketId" type="hidden" name="ticketId" value="${ticket.id}" />
				<input id="ticket_agentId" type="hidden" value="${ticket.agent}" />
				
				<div class="row">
					<div class="first_offer">
						景点门票名称：
					</div>
					<div class="second">
						<label style="font-weight: bold;">${ticket.name}</label>
					</div>
				</div>


				<div class="row">
					<div class="first_offer">
						门票报价：<em></em>
					</div>
					<div class="second">
						<table id="qryResult" style="width:720px;height:300px"></table>
					</div>
				</div>
				<div class="row">
					<div class="first_offer">
						付款方式：<em>*</em>
					</div>
					<div class="second">
					<input type="hidden" id="hidden_payway" value="${ticket.payway}">
						<select class="easyui-combobox" id="sel_payway" name="payway"
                                style="width: 150px; line-height: 22px; border: 1px solid #95B8E7" data-options="
							   prompt:'支付方式',
							   editable:false,
							   valueField: 'id',
							   textField: 'value',
							   panelHeight: 'auto',
							   data: [{
										id: 'allpay',
										value: '全额支付',
										selected:true
									},{
										id: 'offlinepay',
										value: '线下支付'
									},{
										id: 'scenicpay',
										value: '现场支付'
									}]">
                        </select>
					</div>
				</div>


			</form>
			<div style="margin-left: 135px;">
				<a href="javascript:;" onClick="TicketEditOfferList.nextGuide()" class="easyui-linkbutton" id="add_pic_toEditor"
							style="width: 130px;"><i></i>提交保存</a>
			</div>
		</div>
		
	</div>
	
	<div id="dd" class="easyui-dialog" title="修改分类" style="left:0px;top:10px;width:1100px;height:100%;"   
        data-options="resizable:true,modal:true">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
	</div>


	<div id="quantitySales_dialog" class="easyui-dialog" style="left:0px;top:10px;width:550px;height:450px;"
		 data-options="resizable:true,modal:true,closed: true">
		<iframe name="editIframe" id="editIframe1" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
	</div>



</body>
</html>