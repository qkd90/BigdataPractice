<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第四步</title>
<%@ include file="../../common/common141.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
<script type="text/javascript" src="/js/line/line/editStep4.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:true" style="padding:5px 10px 5px 10px;">
		<table>
        	<tr>
			   	<td class="label"></td>
			   	<td>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label">
			   	</td>
			   	<td>
			   		<div class="pub">
			   			<img alt="" src="/images/pub_success.jpg" width="130" height="130" class="left-block">
			   			<div class="left-block">
			   				<input id="productId" name="productId" type="hidden" value="<s:property value="lineDisplay.id"/>"/>
			   				<h2>线路【<span id="productName"><s:property value="lineDisplay.name"/></span>】修改成功！</h2>
			   				<!-- <p>该线路内容完整度为<span>60%</span></p> -->
			   				<p>您可以继续如下操作：</p>
			   				<div>
			   					<!-- <a href="javascript:void(0)" onclick="" class="easyui-linkbutton line-btn" data-options="">查看线路</a> -->
			   					<a href="javascript:void(0)" onclick="editStep4.doBackLineMgr()" class="easyui-linkbutton line-btn" data-options="">返回线路管理</a>
			   					<a href="javascript:void(0)" onclick="editStep4.doEditLine()" class="easyui-linkbutton line-btn" data-options="">重新编辑线路</a>
			   					<a href="javascript:void(0)" onclick="editStep4.doAddLine()" class="easyui-linkbutton line-btn" data-options="">添加新的线路</a>
			   				</div>
			   			</div>
			   		</div>
			   		
			   	</td>
			</tr>
		</table>
	</div>
</div>
	
</body>
</html>
