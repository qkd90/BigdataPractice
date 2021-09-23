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
<title>模块管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/sys/sysResource/menuSelect.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysMenu/manage.css">
</head>
<body  >
	<div class="easyui-layout" style="width: 100%;height: 100%;">
    <!--查询区域 始 页面上方-->
    <div data-options="region:'north',title:'查询条件 ',split:true" id="tb" style="height:100px;padding: 10px;">
	<form action="" id="searchform2">
		<table cellpadding="5" style="width: 100%">
		 	<tr>
		 		 <tr>
                    <td>模块名:</td>
                    <td><input  type="text" id="m_menuname" ></input></td>
                    <td>链接:</td>
                    <td><input  type="text" id="m_url" ></input></td>
                    <td>模块状态:</td>
                    <td><s:select list="#{0:'激活',1:'冻结' }" id="m_status" headerKey="" headerValue="选择模块状态"></s:select></td>
                </tr>
		 	</tr>
		</table>
		</form>
		<div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysMenu.doSearch()">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#searchform2').form('clear')">重置</a>
        </div>
    </div>   
	<!--查询区域 终  -->
	<!-- 左边模块树 -->
    <div data-options="region:'west',title:'模块树',split:true" style="width:200px;">
    	<div id="reloadBtn">
    		<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="SysMenu.initMenuTree()">刷新</a>
    	</div>
    	<div id="tt2"></div>
    </div>   
    <!-- 主面板 -->
    <div id="eastPanel2" data-options="region:'center',title:'模块列表'" style="padding:5px;background:#eee;">
	    <!-- 数据表格 始 -->
		<table id="dg2"></table>
		<!-- 数据表格 终-->
    </div>   
    </div>
    
</body>
</html>