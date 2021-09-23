<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮轮列表</title>
<%@ include file="../../common/common141.jsp"%>
	<script type="text/javascript">
		var FG_DOMAIN = '<s:property value="fgDomain"/>';
	</script>
<script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
<script type="text/javascript" src="/js/cruiseship/cruiseShip/checkManage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
		<!-- 表格工具条 始 -->
		<div id="tb">
			<div style="padding:2px 5px;">
				<form action="" id="searchform">

					<%--<input id="qry_proNum" class="easyui-textbox" data-options="prompt:'编号',validType:'length[0,200]'" style="width:200px;">--%>
					<input id="qry_proName" class="easyui-textbox" data-options="prompt:'邮轮名称',validType:'length[0,200]'" style="width:200px;">
						<input  class="easyui-combobox" id="store_status" style="width: 100px;line-height:22px;border:1px solid #95B8E7"
								data-options="prompt:'请选择状态',
									editable:false,
									valueField: 'id',
									textField: 'text',
									panelHeight: 'auto',
									data: CruiseShipConstants.productStatus"/>
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="CruiseShip.doSearch()">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="CruiseShip.doClear()">重置</a>
				</form>
			</div>
			<%--<div style="padding:2px 5px;">
		        <a href="javascript:void(0)" onclick="CruiseShip.doAdd()" class="easyui-linkbutton" >新增</a>
		        <a href="javascript:void(0)" onclick="CruiseShip.doDel()" class="easyui-linkbutton" >删除</a>
				<a href="javascript:void(0)" onclick="CruiseShip.doBatchShow()" class="easyui-linkbutton" >上架</a>
				<a href="javascript:void(0)" onclick="CruiseShip.doBatchHide()" class="easyui-linkbutton" >下架</a>
			</div>--%>
		</div>
		<!-- 表格工具条 终 -->
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<table id="dg"></table>
		</div>
		<!-- 数据表格 终-->
	</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title=""
		 data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
		<iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
	</div>
</body>
</html>