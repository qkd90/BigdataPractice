<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第四步</title>
<%@ include file="../../common/common141.jsp"%>
	<script type="text/javascript">
		var FG_DOMAIN = '<s:property value="fgDomain"/>';
	</script>
	<link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
	<link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet' />
	<link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print' />
	<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
	<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
	<script src='/fullcalendar-2.4.0/lang-all.js'></script>
	<link rel="stylesheet" type="text/css" href="/css/cruiseship/cruiseShip/addWizard.css">
	<script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
	<script type="text/javascript" src="/js/cruiseship/cruiseShip/editStep4.js"></script>
    <style type="text/css">
        .calbtn {border-color: rgb(2,141,210); background-color: rgb(2,141,210); color:white !important; width:60px;text-decoration: none !important;display:block;text-align:center;border-radius:3px 3px 3px 3px;margin-left:auto;margin-right:auto;line-height:20px;font-size: 12px !important;}
        div.fc-content {text-align:center;}
    </style>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
        <input id="productId" name="productId" type="hidden" value="<s:property value="productId"/>"/>
        <input id="today" type="hidden" value="<s:property value="today"/>"/>
        <div class="row_hd">发团计划</div>
        <div style="width: 600px;margin-top: 10px;margin-left: 10px;">
			<div id='calendar'></div>
		</div>
        <div class="row_hd" id="roomDateTitle">房型价格</div>
        <!-- 数据表格 始 -->
        <div style="margin-left:10px;">
            <table id="roomDateGrid" style="width:800px; height:400px;"></table>
        </div>
		<div style="text-align:left;margin:20px;height:30px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep4.nextGuide(5)">编辑完成</a>
		</div>
	</div>
</div>
</body>
</html>
