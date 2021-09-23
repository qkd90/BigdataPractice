<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司拱量维护</title>
<%@ include file="../../common/common141.jsp"%>

<script type="text/javascript" src="/js/quantityUnitNum/quantityUnitManage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
		<!-- 表格工具条 始 -->
		<div id="tb">
			<div style="padding:2px 5px;">
				<form action="" id="searchform">
					<%--<input id="qry_status" style="width:120px;"/>--%>
			        <%--<input id="qry_supplierType" class="easyui-textbox" style="width:120px;"/>--%>
			        <input id="qry_dealurUnitName" class="easyui-textbox" data-options="prompt:'公司名称'" style="width:200px;margin-right: 10px;">
					<input id="qry_supplerUnitName" class="easyui-textbox" data-options="prompt:'操作公司名称'" style="width:200px;margin-right: 10px;">
					<input id="qry_conditionNumStart" class="easyui-numberbox" data-options="prompt:'拱量数量起始值', min:0, max:999999999" style="width:150px;margin-right: 10px;">
					<input id="qry_conditionNumEnd" class="easyui-numberbox" data-options="prompt:'拱量数量结束值', min:0, max:999999999" style="width:150px;margin-right: 10px;">
			        <a href="javascript:void(0)" id="show_search" class="easyui-linkbutton"  onclick="QuantityUnitManage.doSearch()">查询</a>
						<a href="javascript:void(0)" id="show_search" class="easyui-linkbutton"  onclick="QuantityUnitManage.clearSearch()">重置</a>
				</form>
			</div>
			<div style="padding:2px 5px;">
				        <a href="javascript:void(0)" onclick="QuantityUnitManage.doAdd()" class="easyui-linkbutton" >添加公司</a>
		        <%--<a href="javascript:void(0)" onclick="auditList.doBatchAudit()" class="easyui-linkbutton" >通过</a>--%>
		        <%--<a href="javascript:void(0)" onclick="auditList.doOpenReasonDg()" class="easyui-linkbutton" >不通过</a>--%>
				<a href="javascript:void(0)" onclick="QuantityUnitManage.doBatchDel();" class="easyui-linkbutton" >删除</a>
			</div>
		</div>
		<!-- 表格工具条 终 -->
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<table id="dg"></table>
		</div>
		<!-- 数据表格 终-->
	</div>

	<!-- 添加公司 -->
	<div id="addQuantityUnit" class="easyui-dialog" title="<span style='color:red;'>添加公司并设置拱量条件</span>"
		 data-options="resizable:true,modal:true,closed:true" style="width: 500px; height: 350px;">
		<iframe name="addQuantityUnitframe" id="addQuantityUnitIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
	</div>

	<!-- 编辑拱量信息 -->
	<div id="editQuantityNum" class="easyui-dialog" title="编辑拱量数量"
		 data-options="resizable:true,modal:true,closed:true" style="width: 300px; height: 200px; padding:10px;">
		<div style="float: left; margin-right: 10px;">
			<label>拱量数量：</label>
		</div>
		<div>
			<input id="ipt-quantityNum" class="easyui-numberbox" data-options="prompt:'拱量数量不能小于0', min:0, max:999999999" style="width:150px;">
		</div>
	</div>

</body>
</html>