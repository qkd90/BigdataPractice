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
<script type="text/javascript" src="/js/sailboat/ticketEditOffer.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
<link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet' />
<link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script src='/fullcalendar-2.4.0/lang-all.js'></script>

<base href="<%=basePath%>">

<title>船票报价管理</title>
	<style type="text/css">
		.text {
			font-size: 13px;
			color: rgb(2, 48, 97);
		}
		em {
			color:red;
		}
		.offer_table .offer_table_tr{
			margin-bottom: 10px;
		}
		.offer_table .offer_table_tr .offer_table_td {
			line-height: 36px;
		}
		.first-child {
			text-align: right;
			width: 120px;
		}

		.last-child {
			padding-left: 5px;
		}

	</style>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true">
			<!--表单区域开始-->
			<form id="userInputForm" name="userInputForm" method="post" action="">
				<input id="ticketId" type="hidden" name="ticketId" value="${ticketPrice.ticket.id}"/>
<%-- 				<input id="isAgent" type="hidden" name="" value="${agent}"/> --%>
				<input id="isAgent" type="hidden" value="${ticketPrice.ticket.agent}">
				<input id="dateSourceId" type="hidden" value="">
				<input id="ticketPriceId" type="hidden" name="ticketPriceId" value="${ticketPrice.id}"/>
				<div id="typePriceDate"></div>

				<table class="offer_table">
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child">
							船票类型名称：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<input class="easyui-textbox" id="jd_address" name="name" value="${ticketPrice.name}"
								   data-options="prompt:'50个字以内',validType:'maxLength[50]'"
								   style="width: 200px; line-height: 20px; border: 1px solid #ccc">
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child">
							票型：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<input type="hidden" id="hidden_type" value="${ticketPrice.type}">
							<label class="input_radio"> <input name="type" type="radio" value="adult" checked="checked">成人票</label>
							<label class="input_radio"> <input name="type" type="radio" value="student">学生票</label>
							<label class="input_radio"> <input name="type" type="radio" value="child">儿童票</label>
							<label class="input_radio"> <input name="type" type="radio" value="oldman">老人票</label>
							<label class="input_radio"> <input name="type" type="radio" value="team">团队票</label>
							<label class="input_radio"> <input name="type" type="radio" value="taopiao">套票</label>
							<label class="input_radio"> <input name="type" type="radio" value="other">其他</label>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child">
							取票方式：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<input type="hidden" id="hidden_getTicket" value="${ticketPrice.getTicket}">
							<select class="easyui-combobox" id="sel_getTicket" name="getTicket" value="${ticketPrice.getTicket}"
									style="width: 150px; line-height: 22px; border: 1px solid #95B8E7"
									data-options="
							   prompt:'取票方式',
							   editable:false,
							   valueField: 'id',
							   textField: 'value',
							   panelHeight: 'auto',
							   data: [{
										id: 'messageget',
										value: '短信',
										selected:true
									}]" >
							</select>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child">
							条件退款：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<label class="input_radio"><input type="radio" value="1" <s:if test="ticketPrice.isConditionRefund == true">checked</s:if>  name="isConditionRefund">是</label>
							<label class="input_radio"><input type="radio" value="0" <s:if test="ticketPrice.isConditionRefund == false || ticketPrice.isConditionRefund == null">checked</s:if>  name="isConditionRefund">否</label>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child">
							今日可订：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<label class="input_radio"><input type="radio" value="1" <s:if test="ticketPrice.isTodayValid == true">checked</s:if> name="isTodayValid">是</label>
							<label class="input_radio"><input type="radio" value="0" <s:if test="ticketPrice.isTodayValid == false || ticketPrice.isTodayValid == null">checked</s:if> name="isTodayValid">否</label>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child" valign="top">
							预定须知：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<a href="javascript:;" onClick="TicketEditOffer.addOrderKnow('orderknow')" class="easyui-linkbutton" id="add_orderknow"
							   style="width: 130px; margin-right:50px;margin-top: 5px;"><i></i>添加</a>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child" valign="top">
							费用说明：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<a href="javascript:;" onClick="TicketEditOffer.addOrderKnow('feedesc')" class="easyui-linkbutton" id="add_feedesc"
							   style="width: 130px; margin-right:50px;margin-top: 5px;"><i></i>添加</a>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child" valign="top">
							退款说明：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<a href="javascript:;" onClick="TicketEditOffer.addOrderKnow('refunddesc')" class="easyui-linkbutton" id="add_refunddesc"
							   style="width: 130px; margin-right:50px;margin-top: 5px;"><i></i>添加</a>
						</td>
					</tr>
					<tr class="offer_table_tr">
						<td class="offer_table_td first-child" valign="top">
							船票报价：<em>*</em>
						</td>
						<td class="offer_table_td last-child">
							<div class="date_table" style="">

								<div class="price-set">
									<div style="">
										<span><font color="red">*</font>指定时间段：</span>
										<input id="dateStart" type="text" class="Wdate" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}',maxDate:'#F{$dp.$D(\'dateEnd\')}'})" style="width:100px;" value="${dateStartStr }"/>
										<span>至</span>
										<input id="dateEnd" type="text" class="Wdate" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'dateStart\')}'})" style="width:100px;" value="${dateEndStr }"/>
										<a href="javascript:void(0)" id="add" onclick="TicketEditOffer.doAddPriceDate()" class="easyui-linkbutton" data-options="" style="margin-left: 50px;">添加</a>
										<a href="javascript:void(0)" id="clear" onclick="TicketEditOffer.doClearPriceDate()" class="easyui-linkbutton" data-options="" style="margin-left: 10px;">清除所有报价</a>
									</div>
									<div>
										<div class="ck-div"><input onclick="TicketEditOffer.selectAll()" type="checkbox" id="allweekId" class="left-block" name="weekday" value="8"/><span class="ck-label">整周</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="1"/><span class="ck-label">周一</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="2"/><span class="ck-label">周二</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="3"/><span class="ck-label">周三</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="4"/><span class="ck-label">周四</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="5"/><span class="ck-label">周五</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="6"/><span class="ck-label">周六</span></div>
										<div class="ck-div"><input type="checkbox" class="left-block" name="weekday" value="0"/><span class="ck-label">周日</span></div>
									</div>

									<div class="clr-float">

										<span>销售价：</span>
										<font color="red">*</font>
										<%-- 		                        <input class="easyui-numberbox" id="input_childPrice" name="childPrice" value="${ticketPrice.childPrice}" style="width:80px;" data-options="min:0,precision:2"> --%>
										<input class="easyui-numberbox" id="discountPrice" name="discountPrice" value="" style="width:80px;" data-options="min:0,precision:2">
										<span>结算价：</span>
										<font color="red"></font>
										<input class="easyui-numberbox" id="commissionPrice" name="commission" value="" style="width:80px;" data-options="min:0,precision:2">
										<span>市场价：</span>
										<font color="red"></font>
										<input class="easyui-numberbox" id="marketPrice" name="commission" value="" style="width:80px;" data-options="min:0,precision:2">
										<span>库存：</span>
										<font color="red">*</font>
										<%-- 		                        <input class="easyui-numberbox" id="input_childPrice" name="childPrice" value="${ticketPrice.childPrice}" style="width:80px;" data-options="min:0,precision:2"> --%>
										<input class="easyui-numberbox" id="inventory" name="inventory" value="" style="width:80px;" data-options="min:0">

									</div>

								</div>
								<div style="width: 680px;margin-top: 10px;">
									<div id='calendar'></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-left: 133px;margin-bottom: 10px; margin-top: 10px;">
				<a href="javascript:;" onClick="TicketEditOffer.nextGuide()" class="easyui-linkbutton" id="add_pic_toEditor"
							style="width: 130px; margin-right:50px;"><i></i>保存价格类型</a>
				<a href="javascript:;" onClick="TicketEditOffer.preGuide()" class="easyui-linkbutton" id="cancel_pic_toEditor"
							style="width: 130px; "><i></i>关闭</a>
			</div>
		</div>
		
	</div>

</body>
</html>