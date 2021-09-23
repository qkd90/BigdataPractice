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
<script type="text/javascript" src="/js/ferry/trafficManage.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div id="tabs" class="easyui-tabs" fit="true">
		<div id="show" title="交通管理">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
				<!-- 表格工具条 始 -->
				<div id="show_tb">
					<div style="padding:2px 5px;">
						<input id="leaveTime" class="easyui-datebox"
							   data-options="prompt:'出发日期'" style="width:150px;" value="${currentDate}">
						<input id="trafficCode" class="easyui-textbox" data-options="prompt:'车次/航班号'" style="width:150px;">
						<input class="easyui-combobox" id="show_qry_source" data-options="
							prompt:'产品来源',
							valueField:'id',
							editable:false,
							<%--, QUNAR, ELONG, JUHE;--%>
							textField:'value',
							data: [{
								id: 'JUHE',
								value: '聚合'
							},{
								id: 'LXB',
								value: '旅行帮'
							},{
								id: 'CTRIP',
								value: '携程网'
							},{
								id: 'ELONG',
								value: '艺龙网'
							},{
								id: 'QUNAR',
								value: '去哪儿网'
							}]

						" style="width:130px;" />
						<%--<input id="transportCityName" class="easyui-textbox" data-options="prompt:'城市'" style="width:200px;">--%>
						<input id="leavePort" class="easyui-combobox"
							   data-options="prompt:'出发码头',
										mode: 'remote',
										valueField:'id',
										textField:'name',
										loader: TrafficManage.transportLoader" style="width:150px;">
						<input id="arrivePort" class="easyui-combobox"
							   data-options="prompt:'到达码头',
									mode: 'remote',
									valueField:'id',
									textField:'name',
									loader: TrafficManage.transportLoader" style="width:150px;">


						<%--<input class="easyui-datetimebox"  id="show_qry_startTime" style="width:160px;" data-options="prompt:'上架时间始', editable:false" >--%>
						<%--—--%>
						<%--<input class="easyui-datetimebox"  id="show_qry_endTime" style="width:160px;" data-options="prompt:'上架时间末', editable:false" >--%>

						<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="TrafficManage.doSearch()">查询</a>
						<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="TrafficManage.reset()">重置</a>

					</div>
					<div style="padding:2px 5px;">
						<a href="javascript:void(0)" onclick="TrafficManage.addTraffic();" class="easyui-linkbutton" >新增</a>
						<a href="javascript:void(0)" onclick="TrafficManage.upTraffic();" class="easyui-linkbutton" >上架</a>
						<a href="javascript:void(0)" onclick="TrafficManage.downTraffic();" class="easyui-linkbutton" >下架</a>
						<a href="javascript:void(0)" onclick="TrafficManage.openEditFrame();" class="easyui-linkbutton" >编辑</a>
						<a href="javascript:void(0)" onclick="TrafficManage.delTraffic();" class="easyui-linkbutton" >删除</a>
					</div>
				</div>
				<!-- 表格工具条 终 -->
				<!-- 数据表格 始 -->
				<div data-options="region:'center',border:false">
					<table id="show_dg"></table>
				</div>
				<!-- 数据表格 终-->
			</div>		
		</div>

	</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title=""
        data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
	</div>

	<%--<div id="editDetailPanel" class="easyui-dialog" title=""--%>
		 <%--data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">--%>
		<%--<iframe name="editDetailIframe" id="editDetailIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>--%>
	<%--</div>--%>


</body>
</html>