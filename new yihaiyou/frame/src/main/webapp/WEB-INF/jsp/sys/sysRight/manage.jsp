<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理</title>
<%@ include file="../../common/common141.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/sys/sysRight/manage.css">
<script type="text/javascript" src="/js/sys/sysRight/sysRight.js"></script>
</head>
<body>
<s:hidden name="roleId" id="roleId"></s:hidden>
	<div id="tt" ></div>
	<div id="rightBar">
		<a id="savebtn" href="#" onclick="SysRight.saveSysRight();"  class="easyui-linkbutton" >保存授权</a>
		<a id="closebtn" href="#" onclick="setRightDialog.dialog('close')"  class="easyui-linkbutton" >关闭</a>
	</div>
	<div id="rightBar2">
		<a id="savebtn" href="#" onclick="SysRight.saveSysRight();"  class="easyui-linkbutton" >保存授权</a>
		<a id="closebtn" href="#" onclick="setRightDialog.dialog('close')"  class="easyui-linkbutton" >关闭</a>
	</div>
</body>
</html>