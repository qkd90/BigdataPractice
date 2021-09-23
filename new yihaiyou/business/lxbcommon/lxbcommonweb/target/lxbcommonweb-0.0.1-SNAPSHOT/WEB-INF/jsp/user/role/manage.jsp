<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">

<title>角色管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/user/user.js"></script>
<script type="text/javascript">
	$(function(){
		$("#dg").datagrid({
			
		});
	});
</script>
</head>
<body>
<table id="dg">
	
</table>
</body>
</html>