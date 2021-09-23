<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第二步</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
<link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet' />
<link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script src='/fullcalendar-2.4.0/lang-all.js'></script>
<link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
<script type="text/javascript" src="/js/traffic/trafficUtil.js"></script>
<script type="text/javascript" src="/js/traffic/editStep2.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px;">
		<form id="editForm" method="post">
		<input id="priceId" name="trafficPrice.id" type="hidden" value="${trafficPrice.id}"/>
		<input id="productId" name="trafficPrice.traffic.id" type="hidden" value="${trafficPrice.traffic.id}"/>
		<input id="dateSourceId" name="dateSource" type="hidden" value=""/>
		<div id="typePriceDate"></div>
		<table>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>座位类型:</td>
			   	<td>
			   		<input class="easyui-textbox" name="trafficPrice.seatType" style="width:200px;" value="${trafficPrice.seatType}" required="true" data-options="validType:'maxLength[20]'">
			   	</td>
			</tr>
			<s:if test="#trafficPrice.traffic.proType == 'flight'">
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>燃油附加费:</td>
					<td>
						<input  class="easyui-textbox" name="trafficPrice.additionalFuelTax" style="width:200px;" value="${trafficPrice.additionalFuelTax}" required="true" data-options="validType:'maxLength[20]'">
					</td>
				</tr>
				<tr>
					<td class="label"><font color="red">*&nbsp;</font>机场建设费:</td>
					<td>
						<input class="easyui-textbox" name="trafficPrice.airportBuildFee" style="width:200px;" value="${trafficPrice.airportBuildFee}" required="true" data-options="validType:'maxLength[20]'">
					</td>
				</tr>
			</s:if>
			<tr>
				<td class="label"><font color="red">*&nbsp;</font>座位名称:</td>
				<td>
					<input class="easyui-textbox" name="trafficPrice.seatName" style="width:200px;" value="${trafficPrice.seatName}" required="true" data-options="validType:'maxLength[20]'">
				</td>
			</tr>
			<tr>
				<td class="label"><font color="red">*&nbsp;</font>座位代码:</td>
				<td>
					<input class="easyui-textbox" name="trafficPrice.seatCode" style="width:200px;" value="${trafficPrice.seatCode}" required="true" data-options="validType:'maxLength[20]'">
				</td>
			</tr>
			<tr>
				<td class="label">改签政策:</td>
				<td>
					<textarea id="changePolicy" name="trafficPrice.changePolicy"
							  style="width: 700px; height: 150px; visibility: hidden;">${trafficPrice.changePolicy}</textarea>
					<span>还可以输入<span id="text_count0" style="font-weight: 600;">120</span>个字符</span>
				</td>
			</tr>
			<tr>
				<td class="label">退票政策:</td>
				<td>
					<textarea id="backPolicy" name="trafficPrice.backPolicy"
							  style="width: 700px; height: 150px; visibility: hidden;">${trafficPrice.backPolicy}</textarea>
					<span>还可以输入<span id="text_count1" style="font-weight: 600;">120</span>个字符</span>
				</td>
			</tr>
	    	</form>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>价格:</td>
			   	<td>
			   		<div class="price-set" style="height: 150px;">
		        		<div>
							<input type="hidden" id="flightTime" value="${trafficPrice.traffic.flightTime}">
							<input type="hidden" id="leaveTime" value="${trafficPrice.traffic.leaveTime}">
							<input type="hidden" id="arriveTime" value="${trafficPrice.traffic.arriveTime}">
                            <span><font color="red">*</font>指定时间段：</span>
                            <input id="dateStart" type="text" class="Wdate" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',maxDate:'#F{$dp.$D(\'dateEnd\')}'})" style="width:100px;" value="${dateStartStr }"/>
                            <span>至</span>
                            <input id="dateEnd" type="text" class="Wdate" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'dateStart\')}',maxDate:'%y-{%M+6}-%ld'})" style="width:100px;" value="${dateEndStr }"/>
                            <a href="javascript:void(0)" onclick="editStep2.doAddPriceDate()" class="easyui-linkbutton line-btn" data-options="" style="margin-left: 50px;">添加</a>
	                        <a href="javascript:void(0)" onclick="editStep2.doClearPriceDate()" class="easyui-linkbutton line-btn" data-options="" style="margin-left: 10px;">清除所有报价</a>
                        </div>
						<div>
					   		<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value=""/><span class="ck-label">整周</span></div>
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="1"/><span class="ck-label">周一</span></div>
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="2"/><span class="ck-label">周二</span></div>
					   		<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="3"/><span class="ck-label">周三</span></div>
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="4"/><span class="ck-label">周四</span></div>
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="5"/><span class="ck-label">周五</span></div>
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="6"/><span class="ck-label">周六</span></div>
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="0"/><span class="ck-label">周日</span></div>
	                    </div>
	                    <div class="clr-float">
	                   	    <fieldset class="fieldset">
							    <legend><font color="red">*</font>设置分销价</legend>
							    <span>价格：</span>
		                        <input class="easyui-numberbox" id="price" style="width:80px;" data-options="min:0,precision:2">
						    </fieldset>
							<fieldset class="fieldset">
								<legend><font color="red">*</font>设置市场价</legend>
								<span>价格：</span>
								<input class="easyui-numberbox" id="marketPrice" style="width:80px;" data-options="min:0,precision:2">
							</fieldset>
							<fieldset class="fieldset">
								<legend><font color="red">*</font>设置C端加价</legend>
								<span>价格：</span>
								<input class="easyui-numberbox" id="cPrice" style="width:80px;" data-options="min:0,precision:2">
							</fieldset>
							<fieldset class="fieldset">
								<legend><font color="red">*</font>设置库存</legend>
								<span>数量：</span>
								<input class="easyui-numberbox" id="inventory" style="width:80px;" data-options="min:0">
							</fieldset>
	                    </div>
			   		</div>
			   		<div style="width: 700px;margin-top: 10px;">
			   			<div id='calendar'></div>
			   		</div>
			   	</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
		<div style="text-align:left;padding:5px;height:30px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep2.nextGuide()">保存座位类型</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep2.doBackList()">返回座位类型列表</a>
		</div>  
	</div>
</div>
	
</body>
</html>
