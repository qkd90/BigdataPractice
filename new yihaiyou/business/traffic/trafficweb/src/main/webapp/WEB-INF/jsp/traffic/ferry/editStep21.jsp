<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第二步价格类型列表</title>
<%@ include file="../../common/common141.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/hotel/addWizard.css">
<script type="text/javascript" src="/js/ferry/editStep21.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:5px 10px 5px 10px; border-left: 1px solid rgb(204,204,204);">
		<a name="loctop"></a> 
		<form id="editForm" method="post">
			<input type="hidden" id="productId" value="${traffic.id}">
		<table>
        	<tr>
			   	<td class="label">名称:</td>
			   	<td style="line-height: 18px;">
			   		<span id="productName">${traffic.name}</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label">座位类型:</td>
			   	<td>
					<div id="room_tool">
						<a href="javascript:void(0)" onclick="editStep21.prevGuide()" class="easyui-linkbutton line-btn" data-options="">添加</a>
					</div>
					<div style="width: 623px; height: 350px;">
						<div id="room_dg">
						</div>
					</div>

			   	</td>
			</tr>
		</table>
	    </form>
	    <div style="text-align:left;margin:20px;height:30px;">
	       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep21.nextGuide()">保存，下一步</a>
	   	</div>	
	</div>
</div>
</body>
</html>
