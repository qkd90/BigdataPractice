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
<title>站点管理</title>
<%@ include file="../../common/common141.jsp"%>
<link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/scemanage/scemanage.css"/>
<script type="text/javascript" src="/js/scemanager/validateUtil.js"></script>
<script type="text/javascript" src="/js/scemanager/scemanage.js"></script>
</head>
<body style="background-color: white;">

<div id="tb" class="easyui-panel" style="padding: 10px;">
	<div style="float: left;padding:10px;">
		<form action="" id="searchform">
			<div style="margin-left:10px;float:left;">
				<label>景点名称:</label>
				<input class="easyui-combobox"  id="sce_name"
					   data-options="loader: Scemanage.searchSceLoader,
															mode: 'remote',
															valueField: 'id',
															textField: 'name' "
				>
				<input type="hidden" id="hidden_scename" value="">
			</div>
			<div style="margin-left:10px;float:left;">
				<label>城市:</label>
				<input type="hidden" id="hidden_city" value="">
				<input id="city_name" class="easyui-combobox" name="dept"
					   data-options="loader: Scemanage.cityLoader,
														mode: 'remote',
														valueField: 'id',
														textField: 'name' ">
			</div>

			<div style="margin-left:20px;float: left;">
				<input type="hidden" id="hidden_used"  value="">
				状态:
				<input id="isUsed" name="status" type="radio" value="activity" >
				发布
				<input id="isNotUsed" name="status" type="radio" value="lock" >
				冻结
			</div >
		</form>
	</div>

	<div style="padding:10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="search">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Scemanage.clearForm()">重置</a>
		<input type="button" id="add_logo" value="点击上传" style="display:none">

	</div>

</div>

<!-- 数据表格 始 -->
<table id="dg"></table>
<!-- 数据表格 终-->
<!-- 数据表格 按纽组 始 -->
<div id="toolbar" >
	<a id="addbtn1" href="#" onclick="Scemanage.openAddForm();"   class="easyui-linkbutton" >添加景点帐号</a>
	<a id="addbtn2" href="#" onclick="Scemanage.editStatus();"  class="easyui-linkbutton" >发布帐号</a>
	<a id="addbtn3" href="#" onclick="Scemanage.lockStatus();"  class="easyui-linkbutton">冻结帐号</a>
	<a id="showbtn4" href="#" onclick="Scemanage.editPassword();"  class="easyui-linkbutton" >重置密码</a>

</div>
<!-- 数据表格 按纽组 终 -->
	<%--<div id="content" class="easyui-layout" data-options="fit:true"
         style="width:100%;height:100%;">
        
        <div data-options="region:'north',border:false">
        	<!--查询区域 始 -->

			<!--查询区域 终  -->
        </div>
        <div data-options="region:'center',border:false">

        </div>
    </div>--%>


	
	
	
	
	<!-- 编辑框  始-->
	<div class="easyui-dialog" id="edit_panel" closed="true"  onClose="SysSite.clearForm()" style="width:500px;">
		<iframe name="editIframe" id="editIframe" scrolling="yes" frameborder="0"  style="width:100%;height:460px;"></iframe>
    </div>
	<!-- 编辑框 终 -->
	

</body>
</html>