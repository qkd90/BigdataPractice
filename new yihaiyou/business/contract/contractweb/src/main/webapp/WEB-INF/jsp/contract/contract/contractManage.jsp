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
<title>旅游线路</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script>
	<script type="text/javascript" src="/js/contract/contractUtil.js"></script>
	<script type="text/javascript" src="/js/contract/contractManage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">

	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
				<!-- 表格工具条 始 -->
				<div id="show_tb">
					<div style="padding:2px 5px;">
						<form action="" id="searchform">
							<div>
								<input id="ipq_name" class="easyui-textbox" data-options="prompt:'合同名称'" style="width:200px;">
								<input id="ipq_number" class="easyui-textbox" data-options="prompt:'合同编号'" style="width:200px;">

								<%--<input class="easyui-datebox"  id="ipq_signTimeStart" style="width:160px;" data-options="prompt:'签约时间始', editable:false" >--%>
								<%--—--%>
								<%--<input class="easyui-datebox"  id="ipq_signTimeEnd" style="width:160px;" data-options="prompt:'签约时间末', editable:false" >--%>
								<input id="ipq_status" class="easyui-combobox" data-options="prompt:'合同状态',
												valueField:'id',
										   		textField:'text',
										   		panelHeight:80,
										   		editable:false,
										   		data:ContractConstants.status" style="width:100px;">
								<input class="easyui-datebox"  id="ipq_expireTimeStart" style="width:100px;" data-options="prompt:'到期时间始', editable:false" >
								—
								<input class="easyui-datebox"  id="ipq_expireTimeEnd" style="width:100px;" data-options="prompt:'到期时间末', editable:false" >
								<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ContractManage.doSearchShow()">查询</a>
								<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ContractManage.doClearShow()">重置</a>
							</div>
						</form>
					</div>
					<div style="padding:2px 5px;">
						<a href="javascript:void(0)" onclick="ContractManage.doAdd();" class="easyui-linkbutton" >新增</a>
						<%--<a href="javascript:void(0)" onclick="ContractManage.doEdit();" class="easyui-linkbutton" >编辑</a>--%>
						<a href="javascript:void(0)" onclick="ContractManage.doDel();" class="easyui-linkbutton" >删除</a>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="show_dg"></table>
				</div>
				<!-- 数据表格 终-->
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