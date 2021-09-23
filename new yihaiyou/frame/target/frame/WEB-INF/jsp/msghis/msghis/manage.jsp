<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>短信历史</title>
<%@ include file="../../common/common141.jsp"%>

<script type="text/javascript" src="/js/sys/sysMsgHis/sysMsgHis.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysRole/manage.css">
<script type="text/javascript" src="/js/sys/sysRight/sysRight.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>


<body>

	<!--查询区域 始 -->
	<div id="tb" class="easyui-panel" title="查询条件" style="padding: 10px;">
		<form action="" id="searchform">
		
			<table cellpadding="5" style="width: 100%">
			
				<tr>
					<td>号码：</td>
					<td><input type="text" id="receivenum"></input></td>
					<td>文本内容：</td>
					<td><input type="text" id="context"></input></td>
<!-- 					<td>发送日期:</td> -->

					<%-- 					<td><s:select list="#{0:'激活',1:'冻结' }" id="msg_date" --%>
					<%-- 							headerKey="" headerValue="选择角色状态"></s:select></td> --%>
				</tr>
				
			</table>
			
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="SysMsg.doSearch()">查询</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a>
		</div>
	</div>
	<!--查询区域 终  -->

	<!-- 数据表格 始 -->
	<table id="hg"></table>
	<!-- 数据表格 终-->
</body>
</html>