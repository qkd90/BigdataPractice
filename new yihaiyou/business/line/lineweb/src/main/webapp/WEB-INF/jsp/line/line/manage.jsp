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
<title>旅游线路</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<link rel="stylesheet" type="text/css" href="/css/line/line/manage.css">
<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>
<script type="text/javascript" src="/js/common/labelMgrDg.js"></script>
<script type="text/javascript" src="/js/line/line/manage.js"></script>
</head>
<body>
	<div id="tabs" class="easyui-tabs" fit="true">
		<div id="show" title="上架线路">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
				<!-- 表格工具条 始 -->
				<div id="show_tb">
					<div style="padding:2px 5px;">
						<form action="">
							<input id="show_qry_lineType" style="width:120px;"/>
					        <input id="show_qry_productAttr" style="width:120px;"/>
					        <select class="easyui-combobox" id="show_qry_customType" style="width:130px;">
								<option value="">自定义分类（不限）</option>
								<c:forEach items="${linecategorgs}" var="categorg">
									<option value="${categorg.id}">${categorg.name}</option>
								</c:forEach>
							</select>
					        <input id="show_qry_buypay" style="width:120px;"/>
					        <input id="show_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
					        <a href="javascript:void(0)" id="show_search" class="easyui-linkbutton"  onclick="Line.doSearch('show_dg')">查询</a>
						</form>
					</div>
					<div style="padding:2px 5px;">
						<%--<a href="javascript:void(0)" onclick="Line.doQuickPub()" class="easyui-linkbutton" >快速发布</a>--%>
						<%--<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="Line.onpenQuantitySalesDialog()">设置拱量</a>--%>
				        <a href="javascript:void(0)" onclick="Line.doAdd()" class="easyui-linkbutton" >发布新线路</a>
						<%--<a href="javascript:void(0)" onclick="Line.doDel('show_dg');" class="easyui-linkbutton" >删除</a>--%>
						<%--<a href="javascript:void(0)" onclick="Line.doUpdateOrder('show_dg');" class="easyui-linkbutton" >更新排序</a>--%>
						<!-- <a href="javascript:void(0)" onclick="Line.doCancelRec();" class="easyui-linkbutton" >取消推荐</a> -->
						<a href="javascript:void(0)" onclick="Line.doSetHide('show_dg');" class="easyui-linkbutton" >下架</a>
						<!-- <a href="javascript:void(0)" onclick="Line.doUpdatePrice();" class="easyui-linkbutton" >修改价格</a> -->
						<%--<a href="javascript:void(0)" onclick="Line.doRePub('show_dg');" class="easyui-linkbutton" >重发</a>
						<a href="javascript:void(0)" onclick="Line.doShopCost('show_dg');" class="easyui-linkbutton" >有购物有自费</a> --%>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="show_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>		
		</div>
		<div id="outday" title="过期线路">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
				<!-- 表格工具条 始 -->
				<div id="outday_tb">
					<div style="padding:2px 5px;">
						<form action="">
							<input id="outday_qry_lineType" style="width:120px;"/>
					        <input id="outday_qry_productAttr" style="width:120px;"/>
					        <select class="easyui-combobox" id="outday_qry_customType" 
									style="width:130px;">
									<option value="">自定义分类（不限）</option>
									<c:forEach items="${linecategorgs}" var="categorg">
										<option value="${categorg.id}">${categorg.name}</option>
									</c:forEach>
								</select>
					        <input id="outday_qry_buypay" style="width:120px;"/>
					        <input id="outday_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
					        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Line.doSearch('outday_dg')">查询</a>
						</form>
					</div>
					<div style="padding:2px 5px;">
						<%--<a href="javascript:void(0)" onclick="Line.doDel('outday_dg');" class="easyui-linkbutton" >删除</a>--%>
						<a href="javascript:void(0)" onclick="Line.doSetHide('outday_dg');" class="easyui-linkbutton" >下架</a>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="outday_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>
		</div>
		<div id="hide" title="下架线路">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
				<!-- 表格工具条 始 -->
				<div id="hide_tb">
					<div style="padding:2px 5px;">
						<form action="">
							<input id="hide_qry_lineType" style="width:120px;"/>
					        <input id="hide_qry_productAttr" style="width:120px;"/>
					        <select class="easyui-combobox" id="hide_qry_customType" 
									style="width:130px;">
									<option value="">自定义分类（不限）</option>
									<c:forEach items="${linecategorgs}" var="categorg">
										<option value="${categorg.id}">${categorg.name}</option>
									</c:forEach>
								</select>
					        <input id="hide_qry_buypay" style="width:120px;"/>
					        <input id="hide_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
					        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Line.doSearch('hide_dg')">查询</a>
						</form>
					</div>
					<div style="padding:2px 5px;">
						<%--<a href="javascript:void(0)" onclick="Line.doDel('hide_dg');" class="easyui-linkbutton" >删除</a>
						<a href="javascript:void(0)" onclick="Line.doSetShow('hide_dg');" class="easyui-linkbutton" >上架</a> --%>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="hide_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>
		</div>
		<div id="checkIng" title="待审核线路">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
				<!-- 表格工具条 始 -->
				<div id="checkIng_tb">
					<div style="padding:2px 5px;">
						<form action="" id="checkIng_searchform">
							<input id="checkIng_qry_lineType" style="width:120px;"/>
							<input id="checkIng_qry_productAttr" style="width:120px;"/>
							<select class="easyui-combobox" id="checkIng_qry_customType"
									style="width:130px;">
								<option value="">自定义分类（不限）</option>
								<c:forEach items="${linecategorgs}" var="categorg">
									<option value="${categorg.id}">${categorg.name}</option>
								</c:forEach>
							</select>
							<input id="checkIng_qry_buypay" style="width:120px;"/>
							<input id="checkIng_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
							<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Line.doSearch('hide_dg')">查询</a>
						</form>
					</div>
					<div style="padding:2px 5px;">
						<%--<a href="javascript:void(0)" onclick="Line.doDel('hide_dg');" class="easyui-linkbutton" >删除</a>
						<a href="javascript:void(0)" onclick="Line.doSetShow('hide_dg');" class="easyui-linkbutton" >上架</a>--%>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="checkIng_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>
		</div>
		<div id="all" title="线路仓库">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
				<!-- 表格工具条 始 -->
				<div id="all_tb">
					<div style="padding:2px 5px;">
						<form action="">
							<input id="all_qry_lineType" style="width:120px;"/>
					        <input id="all_qry_productAttr" style="width:120px;"/>
					        <select class="easyui-combobox" id="all_qry_customType" 
									style="width:130px;">
									<option value="">自定义分类（不限）</option>
									<c:forEach items="${linecategorgs}" var="categorg">
										<option value="${categorg.id}">${categorg.name}</option>
									</c:forEach>
								</select>
					        <input id="all_qry_buypay" style="width:120px;"/>
					        <input id="all_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
					        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Line.doSearch('all_dg')">查询</a>
						</form>
					</div>
					<div style="padding:2px 5px;">
						<a href="javascript:void(0)" onclick="Line.doAdd()" class="easyui-linkbutton" >发布新线路</a>
						<a href="javascript:void(0)" onclick="Line.doSetHide('all_dg');" class="easyui-linkbutton" >下架</a>
						<a href="javascript:void(0)" onclick="Line.doDel('all_dg');" class="easyui-linkbutton" >删除</a>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="all_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>
		</div>
		<div id="agent" title="被代理线路">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
				<!-- 表格工具条 始 -->
				<div id="agent_tb">
					<div style="padding:2px 5px;">
						<form action="">
							<input id="agent_qry_lineType" style="width:120px;"/>
					        <input id="agent_qry_productAttr" style="width:120px;"/> 
					        <input id="agent_qry_lineStatus" style="width:120px;"/>
					        <input id="agent_qry_buypay" style="width:120px;"/>
					        <input id="agent_qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
					        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Line.doSearch('agent_dg')">查询</a>
						</form>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="agent_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>
		</div>
	</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="" 
        data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false"> 
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>  
	</div>
</body>
</html>