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
	<script type="text/javascript" src="/js/proValidateChanel/channelUtil.js"></script>
	<script type="text/javascript" src="/js/proValidateChanel/channelManage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div class="easyui-tabs" fit="true">
		<div id="ticket_channel_tabs" title="门票验证配置">
			<div id="show_tb">
				<div style="padding:2px 5px;">
					<form action="" id="searchform">
						<div>
							<input type="hidden" id="ipq_proType" value="scenic">
							<input id="ipq_name" class="easyui-textbox" data-options="prompt:'产品名称'" style="width:200px;">
							<input id="ipq_proNum" class="easyui-textbox" data-options="prompt:'产品编号'" style="width:200px;">
							<input id="ipq_channel" class="easyui-combobox" data-options="prompt:'验证通道',
												valueField:'id',
										   		textField:'text',
										   		panelHeight:80,
										   		editable:false,
										   		data:ChannelConstants.chanel" style="width:200px;">
							<input class="easyui-datebox"  id="ipq_updateTimeStart" style="width:160px;" data-options="prompt:'操作时间始', editable:false" >
							—
							<input class="easyui-datebox"  id="ipq_updateTimeEnd" style="width:160px;" data-options="prompt:'操作时间末', editable:false" >
							<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ChannelManage.doSearchShow()">查询</a>
							<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ChannelManage.doClearShow()">重置</a>
						</div>
					</form>
				</div>
			</div>
			<div id="ticket_chanel_dg" style="width: 100%; height: 100%;"></div>
		</div>
	</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="" 
        data-options="resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
		<div style="padding:10px;">
			<table>
				<tr>
					<td>验证通道：</td>
					<td>
						<input id="sel_channel" class="easyui-combobox" data-options="prompt:'验证通道',
												valueField:'id',
										   		textField:'text',
										   		panelHeight:80,
										   		editable:false,
										   		data:[
													{id:'LXB',text:'旅行帮'},
													{id:'CTRIP',text:'携程'},
													{id:'ZYB',text:'智游宝'}
												]" style="width:150px;">
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="editDetailPanel" class="easyui-dialog" title=""
		 data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
		<iframe name="editDetailIframe" id="editDetailIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
	</div>


</body>
</html>