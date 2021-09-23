<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮轮列表</title>
<%@ include file="../../common/common141.jsp"%>

	<script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
	<script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
	<script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>

<script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
<script type="text/javascript" src="/js/cruiseship/cruiseShip/manage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">

<div id="tabs" class="easyui-tabs" fit="true" style="width:100%;height:100%;">

	<div title="邮轮信息管理" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
		<!-- 表格工具条 始 -->
		<div id="tb">
			<div style="padding:2px 5px;">
				<form action="" id="searchform">
					<input id="qry_status" style="width:120px;"/>
					<input id="qry_keyword" class="easyui-textbox" data-options="prompt:'邮轮名称或编号关键词',validType:'length[0,200]'" style="width:200px;">
					<a href="javascript:void(0)" id="search" class="easyui-linkbutton"  onclick="CruiseShip.doSearch()">查询</a>
				</form>
			</div>
			<div style="padding:2px 5px;">
		        <a href="javascript:void(0)" onclick="CruiseShip.doAdd()" class="easyui-linkbutton" >新增</a>
		        <a href="javascript:void(0)" onclick="CruiseShip.doDel()" class="easyui-linkbutton" >删除</a>
				<a href="javascript:void(0)" onclick="CruiseShip.doBatchShow()" class="easyui-linkbutton" >提交审核</a>
				<a href="javascript:void(0)" onclick="CruiseShip.doBatchHide()" class="easyui-linkbutton" >下架</a>
			</div>
		</div>
		<!-- 表格工具条 终 -->
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<table id="dg"></table>
		</div>
		<!-- 数据表格 终-->
	</div>

	<div id="content" title="分类信息管理" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
		<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
			<input id="parentId" name="parentId" type="hidden" value="<s:property value="cruiseShipProjectClassify.id"/>"/>
			<!-- 项目表格工具条 始 -->
			<div style="padding:2px 5px;">
				<form action="" id="selectform">
					<input id="show_status" style="width:120px;"/>
					<input id="show_keyword" class="easyui-textbox" data-options="prompt:'项目名称或关键词',validType:'length[0,200]'" style="width:200px;">
					<a href="javascript:void(0)" id="select" class="easyui-linkbutton"  onclick="CruiseShip.searchProject()">查询</a>
				</form>
			</div>
			<div id="projectGridtb">
				<div style="padding:2px 5px;">
					<a href="javascript:void(0)" onclick="CruiseShip.searchProject()" class="easyui-linkbutton" >刷新</a>
					<a href="javascript:void(0)" onclick="CruiseShip.openEditProjectDg()" class="easyui-linkbutton" >新增</a>
					<a href="javascript:void(0)" onclick="CruiseShip.doDelProject()" class="easyui-linkbutton" >删除</a>
				</div>
			</div>
			<!-- 项目表格工具条 终 -->
			<!-- 项目数据表格 始 -->
			<div style="margin-left:10px;">
				<table id="projectGrid" style="width:800px; height:400px;"></table>
			</div>
			<!-- 项目数据表格 终-->
		</div>
	</div>

</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title=""
		 data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
		<iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
	</div>

<%--项目信息编辑窗口--%>
<div id="editProjectDg" title="项目信息" class="easyui-dialog" data-options="closed:true,modal:true" style="width:600px;height:400px;padding:6px">
	<form id="projectForm" method="post">
		<input type="hidden" id="projectId" name="cruiseShipProjectClassify.id"/>
		<table>
			<tr>
				<td align="right">分类名称：</td>
				<td>
					<input  name="cruiseShipProjectClassify.classifyName"  style="width:80px;">
				</td>

			</tr>

			<tr>
				<td align="right">项目类型：</td>
				<td>
					<input id="projectType" name="cruiseShipProjectClassify.cruiseShipProjectClassify.id" style="width:80px;">
				</td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<a id="submit_project" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="CruiseShip.saveProject()">保存</a>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>