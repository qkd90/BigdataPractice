<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一步</title>
	<%@ include file="../../common/common141.jsp"%>
<%--<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>--%>
	<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css"/>
	<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/hotel/addWizard.css"/>
	<link rel="stylesheet" type="text/css" href="/css/hotel/iconfont/iconfont.css"/>

	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
	<script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
	<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
	<script type="text/javascript" src="/js/extends/jquery.radio.js"></script>
	<script type="text/javascript" src="/js/hotel/hotelUtil.js"></script>
	<script type="text/javascript" src="/js/ferry/addStep1.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 10px" style="width:100%;">
		<form id="editForm" method="post">
			<input id="productId" name="productId" type="hidden"/>
			<table style="float:left;">
				<tr>
					<td class="label">名称:</td>
					<td>
						<input class="easyui-textbox" name="traffic.name" style="width:200px;" data-options="validType:['maxLength[40]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>航班号:</td>
					<td>
						<input class="easyui-textbox" name="traffic.trafficCode" style="width:200px;" data-options="required:true, validType:['maxLength[20]']">
					</td>
				</tr>
				<%--<tr>--%>
					<%--<td class="label"><font color="red">*&nbsp;</font>交通类型:</td>--%>
					<%--<td>--%>
						<%--<div class="easyui-radio" id="radio">--%>
							<%--<input type="radio" name="traffic.trafficType" value="TRAIN" onselect="addStep1.resetProt()" checked  label="火车">--%>
							<%--<input type="radio" name="traffic.trafficType" value="AIRPLANE" onselect="addStep1.resetProt()"  label="飞机">--%>
							<%--<input type="radio" name="traffic.trafficType" value="SHIP" checked onselect="addStep1.resetProt()"  label="游轮">--%>
							<%--<input type="radio" name="traffic.trafficType" value="BUS" onselect="addStep1.resetProt()"  label="汽车">--%>
						<%--</div>--%>
					<%--</td>--%>
				<%--</tr>--%>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>型号:</td>
					<td>
						<input class="easyui-textbox" name="traffic.trafficModel" style="width:200px;" data-options="required:true, validType:['maxLength[20]']">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>出发城市:</td>
					<td>
						<input type="hidden" id="hidden_startCityId" name="traffic.leaveCity.id">
						<input id="startCityId" class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false,prompt:'点击选择出发城市'" style="width:200px" data-country="" data-province="" data-city="">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>到达城市:</td>
					<td>
						<input type="hidden" id="hidden_arriveCityId" name="traffic.arriveCity.id">
						<input id="arriveCityId" class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false, prompt:'点击选择到达城市'" style="width:200px" data-country="" data-province="" data-city="">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>出发码头:</td>
					<td>
						<input type="hidden" name="traffic.startPort" id="hid_leavePortName">
						<input id="leavePort" data-type="leaveProt" required="required" name="traffic.leaveTransportation.id" class="easyui-combobox"
							   data-options="prompt:'出发码头',
								   			mode: 'remote',
											valueField:'id',
											textField:'name',
											loader: addStep1.transportLoader" style="width:150px;">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>到达码头:</td>
					<td>
						<input type="hidden" name="traffic.endPort" id="hid_endPortName">
						<input id="arrivePort" data-type="arriveProt" required="required" name="traffic.arriveTransportation.id" class="easyui-combobox"
							   data-options="prompt:'到达码头',
								   		mode: 'remote',
										valueField:'id',
										textField:'name',
										loader: addStep1.transportLoader" style="width:150px;">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>出发时间:</td>
					<td>
						<input id="leaveTimeId" class="easyui-timespinner" name="traffic.leaveTime"  style="width:80px;"
							   required="required" data-options="min:'00:00',showSeconds:false, value:'00:00'" />
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>到达时间:</td>
					<td>
						第<input id="daysId" class="easyui-numberspinner" style="width:80px;"
								required="required" data-options="min:1, value:0" />天
						<input id="arriveTimeId" class="easyui-timespinner" name="traffic.arriveTime"  style="width:80px;"
							   required="required" data-options="min:'00:00',showSeconds:false, value:'23:59'" />到达<span style="color: #808080;margin-left: 12px;font-size: 12px;">(注意：第1天表示当天到达)</span>
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>乘坐时长:</td>
					<td>
						<input id="flightTime" class="easyui-textbox" name="traffic.flightTime" data-options="editable:false" style="width:200px;" data-options=""/>
					</td>
				</tr>
				<tr>
					<td class="label">所属公司:</td>
					<td>
						<input class="easyui-textbox" name="traffic.company"  style="width:200px;" data-options=""/>
					</td>
				</tr>
				<tr>
					<td class="label">公司代码:</td>
					<td>
						<input class="easyui-textbox" name="traffic.companyCode"  style="width:150px;" data-options=""/>
					</td>
				</tr>
			</table>
	    </form>
	</div>
	<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
		<div style="text-align:left;padding:5px;height:30px;margin-left: 77px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="addStep1.nextGuide()">保存，下一步</a>
		</div>  
	</div>
</div>
</body>
</html>
