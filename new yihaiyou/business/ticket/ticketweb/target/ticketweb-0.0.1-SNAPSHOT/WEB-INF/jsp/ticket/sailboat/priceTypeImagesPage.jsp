<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<link href="/css/ticket/sailboat_form.css" rel="stylesheet" type="text/css">
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sailboat/ticketUtil.js"></script>
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
	<script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
	<script type="text/javascript" src="/js/sailboat/priceTypeImages.js"></script>

<base href="<%=basePath%>">

<title>船票报价相册</title>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true" style="padding: 10px;">
			<input id="ticketId" type="hidden" value="${ticket.id}"/>
			<input id="ticketType" type="hidden" value="${ticket.ticketType}"/>
			<input id="ticketPriceId" type="hidden" value="${ticketPriceId}"/>
			<table>
				<tr>
					<td width="60">
						相册
					</td>
					<td width="550">
					</td>
					<td width="60">
						封面
					</td>
					<td width="180">
					</td>
				</tr>
				<tr>
					<td width="60">

					</td>
					<td width="550">
						<div id="imageBox">
							<div id="imagePanel"></div>
						</div>
						<span id="imageContent">
						</span>
					</td>
					<td width="60">

					</td>
					<td width="180" valign="top">
						<div id="coverParent">
							<div id="coverBox"></div>
						</div>
						<%--<input type="hidden" id="coverPath" name="cruiseShip.coverImage">--%>
					</td>
				</tr>
			</table>

			<!--表单区域开始-->
			<div style="background-color: white;bottom: 15px;right: 20px;position: absolute;">
				<a href="javascript:;" onClick="PriceTypeImages.saveImages()" class="easyui-linkbutton" id="add_pic_toEditor"
							style="width: 100px; margin-right:50px;"><i></i>保存图片</a>
				<a href="javascript:;" onClick="PriceTypeImages.cancelSaveImage()" class="easyui-linkbutton" id="cancel_pic_toEditor"
							style="width: 100px; "><i></i>取消</a>
			</div>
		</div>
	</div>
</body>
</html>