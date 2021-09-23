<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>民宿管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<script type="text/javascript" src="/js/hotel/hotelManage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
		<div id="storage_tb">
			<div style="padding:2px 5px;">
				<form action="" id="storage_searchform">
					<%--<input class="easyui-combobox" id="storage_qry_source" data-options="--%>
							<%--prompt:'产品来源',--%>
							<%--valueField:'id',--%>
							<%--editable:false,--%>
							<%--&lt;%&ndash;, QUNAR, ELONG, JUHE;&ndash;%&gt;--%>
							<%--textField:'value',--%>
							<%--panelHeight: 'auto',--%>
							<%--data: [{--%>
								<%--id: '',--%>
								<%--value: '全部'--%>
							<%--},{--%>
								<%--id: 'CTRIP',--%>
								<%--value: '携程网'--%>
							<%--},{--%>
								<%--id: 'ELONG',--%>
								<%--value: '艺龙网'--%>
							<%--},{--%>
								<%--id: 'QUNAR',--%>
								<%--value: '去哪儿网'--%>
							<%--},{--%>
								<%--id: 'JUHE',--%>
								<%--value: '聚合'--%>
							<%--},{--%>
								<%--id: 'LXB',--%>
								<%--value: '本平台'--%>
							<%--}]--%>

						<%--" style="width:130px;" />--%>
					<input id="storage_qry_name" class="easyui-textbox" data-options="prompt:'民宿名称'" style="width:200px;">
					<input class="easyui-combobox" id="storage_qry_status" data-options="
							prompt:'产品状态',
							valueField:'id',
							textField:'value',
							data: [{
								id: 'DOWN',
								value: '已下架'
							},{
								id: 'UP',
								value: '已上架'
							}]
						" style="width:130px;"/>
					<input id="storage_qry_star" class="easyui-combobox"
						   data-options="prompt:'星级',
											valueField:'id',
											textField:'text',
											data:[
												{id:'0',text:'未评级'},
												{id:'1',text:'一星级'},
												{id:'2',text:'二星级'},
												{id:'3',text:'三星级'},
												{id:'4',text:'四星级'},
												{id:'5',text:'五星级'}
											]
						" style="width:120px; visibility: hidden;">

					<input class="easyui-datetimebox"  id="storage_qry_startTime" style="width:160px;" data-options="prompt:'更新时间始', editable:false" >
					—
					<input class="easyui-datetimebox"  id="storage_qry_endTime" style="width:160px;" data-options="prompt:'更新时间末', editable:false" >

					<a href="javascript:void(0)" id="storage_search" class="easyui-linkbutton"  onclick="HotelManage.doSearchStorage()">查询</a>
					<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="HotelManage.doClearStorage()">重置</a>
				</form>
			</div>
			<%--<div style="padding:2px 5px;">
				<a href="javascript:void(0)" onclick="HotelManage.doSubChecking('storage_dg')" class="easyui-linkbutton" >提交审核</a>
				<a href="javascript:void(0)" onclick="HotelManage.doAdd('storage_dg')" class="easyui-linkbutton" >新增</a>
				<a href="javascript:void(0)" onclick="HotelManage.doDel('storage_dg');" class="easyui-linkbutton" >删除</a>
				<a href="javascript:void(0)" onclick="HotelManage.doSetHide('storage_dg');" class="easyui-linkbutton" >下架</a>
			</div>--%>
		</div>

		<div data-options="region:'center',border:false">
			<table id="storage_dg"></table>
		</div>
	</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="" 
        data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false"> 
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>  
	</div>

	<div id="editDetailPanel" class="easyui-dialog" title=""
		 data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
		<iframe name="editDetailIframe" id="editDetailIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
	</div>


</body>
</html>