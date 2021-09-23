<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商审核</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<script type="text/javascript" src="/js/sys/sysUnit/auditListUtil.js"></script>
<script type="text/javascript" src="/js/sys/sysUnit/auditList.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
		<!-- 表格工具条 始 -->
		<div id="tb">
			<div style="padding:2px 5px;">
				<form action="" id="searchform">
					<input id="qry_status" style="width:120px;"/>
			        <input id="qry_supplierType" style="width:120px;"/>
			        <input id="qry_keyword" class="easyui-textbox" data-options="prompt:'供应商名称'" style="width:200px;">
			        <a href="javascript:void(0)" id="show_search" class="easyui-linkbutton"  onclick="auditList.doSearch()">查询</a>
				</form>
			</div>
			<div style="padding:2px 5px;">
				        <a href="javascript:void(0)" onclick="auditList.doAdd()" class="easyui-linkbutton" >添加</a>
		        <a href="javascript:void(0)" onclick="auditList.doBatchAudit()" class="easyui-linkbutton" >审核</a>
				<a href="javascript:void(0)" onclick="auditList.doBatchActivate()" class="easyui-linkbutton" >激活</a>
		        <a href="javascript:void(0)" onclick="auditList.doBatchFreeze()" class="easyui-linkbutton" >冻结</a>
				<a href="javascript:void(0)" onclick="auditList.doBatchDel();" class="easyui-linkbutton" >删除</a>
				<a href="javascript:void(0)" onclick="auditList.resetPassword();" class="easyui-linkbutton" >密码重置</a>
			</div>
		</div>
		<!-- 表格工具条 终 -->
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<table id="dg"></table>
		</div>
		<!-- 数据表格 终-->
	</div>	
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="商户信息"
        data-options="fit:true,resizable:true,modal:true,closed:true" style="height: 100%; width: 100%;">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
	</div> 
	<!-- 查看窗口 -->
	<div id="viewPanel" class="easyui-dialog" title="商户信息"
        data-options="fit:true,resizable:true,modal:true,closed:true"> 
        <iframe name="viewIframe" id="viewIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
	</div> 

	<!-- 冻结窗口 -->
	<div id="reasonDg" title="冻结原因" class="easyui-dialog" style="width:300px;height:180px;padding:5px 5px 5px 5px"
	          closed="true" modal="true" >
	    <table>
	    	<tr>
	    		<td>
					<input id="unitId" name="unitId" type="hidden"/>
	    			<input class="easyui-textbox" id="reason" name="reason" data-options="multiline:true,validType:'maxLength[150]'" style="height:100px;width:276px;"/>
	    		</td>
	    	<tr>
	    	</tr>
	    		<td align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="auditList.doBatchFreeze()" style="margin-top: 5px;">确认</a>
	    		</td>
	    	</tr>
	    </table>
	</div>
</body>
</html>