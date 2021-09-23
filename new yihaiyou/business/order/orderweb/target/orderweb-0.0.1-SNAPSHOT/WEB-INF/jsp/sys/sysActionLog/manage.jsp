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
<title>角色管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sys/sysActionLog/sysActionLog.js"></script>
<script type="text/javascript" src="/js/sys/sysRight/sysRight.js"></script>
<script type="text/javascript">
	$(function(){
		SysActionLog.initDgList();
	});
</script>
</head>
<body >
	<!--查询区域 始 -->
	<div id="tb" class="easyui-panel" title="查询条件" style="padding: 10px;">
	<form action="" id="searchform">
		<table cellpadding="5" style="width: 100%">
		 		 <tr>
                    <td>操作者姓名:</td>
                    <td><input  type="text" id="log_name" ></input></td>
                    <td>操作者账号:</td>
                    <td><input  type="text" id="log_account" ></input></td>
                    <td>时间:</td>
                    <td><input  type="text" id="log_time" ></input></td>
                </tr>
		</table>
		</form>
		<div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysActionLog.doSearch()">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a>
        </div>
		
	</div>
	<!--查询区域 终  -->
	<!-- 数据表格 始 -->
	<table id="dg"></table>
	<!-- 数据表格 终-->
</body>
</html>