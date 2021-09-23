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
<link href="/css/ticket/form.css" rel="stylesheet" type="text/css">
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sailboat/ticketPublishSuccess.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>

<base href="<%=basePath%>">

<title>船票发布成功</title>



</head>
<body>
	<style type="text/css">
.text {
	font-size: 13px;
	color: rgb(2, 48, 97);
}
</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true">
			<!--表单区域开始-->
			<form id="userInputForm" name="userInputForm" method="post" action="">
				<input id="userId" type="hidden" name="user.id" value="" />
				
				<div class="title">
					<div class="title_name">
						船票名称：
					</div>
					<div class="pro_input">
						<label>${ticket.name}</label>
						<input type="hidden" id="ticketId" value="${ticket.id}">
					</div>
				</div>

				<div class="pub">
					<div class="pub_img">
							<img alt="" src="/images/pub_success.jpg" width="130" height="130" class="left-block">
					</div>
		   			<div class="left-block">
		   				<h2 class="hd">船票发布成功！</h2>
		   				<%--<p><b>√</b> 已正常展示分销网</p>--%>
		   				<div class="pub_btn">
		   					<a href="javascript:;" class="easyui-linkbutton" onclick="TicketPublishSuccess.reToList()">返回管理列表</a>
		   					<a href="javascript:;" class="easyui-linkbutton" onclick="TicketPublishSuccess.editTicket()">重新编辑船票</a>
		   					<a href="javascript:;" class="easyui-linkbutton" onclick="TicketPublishSuccess.addNewTicket()">添加新的船票</a>
		   				</div>
		   			</div>
			   	</div>
				
			</form>
			
		</div>
		
	</div>

	
</body>
</html>